package controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.CustomFundBean;
import databeans.CustomerBean;
import databeans.FundBean;
import databeans.PositionBean;
import databeans.PriceBean;
import databeans.RawPriceBean;
import databeans.TransactionBean;
import model.CustomerDAO;
import model.Model;
import model.PositionDAO;
import model.PriceDAO;
import model.TransactionDAO;
import model.FundDAO;
import formbeans.BuyForm;
import formbeans.CreateFundForm;
import formbeans.CusRegisterForm;

public class TransitionAction extends Action {
	private FundDAO fundDAO;
	private PriceDAO priceDAO;
	private TransactionDAO transactionDAO;
	private CustomerDAO customerDAO;
	private PositionDAO positionDAO;

	public TransitionAction(Model model) {
		fundDAO = model.getFundDAO();
		priceDAO = model.getPriceDAO();
		transactionDAO = model.getTransactionDAO();
		customerDAO = model.getCustomerDAO();
		positionDAO = model.getPositionDAO();
	}

	public String getName() {
		return "transition.do";
	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		List<String> success = new ArrayList<String>();
		request.setAttribute("success", success);

		try {
			HttpSession session = request.getSession(false);
			FundBean[] funds = fundDAO.getFundList();
			CustomFundBean[] cfbs = new CustomFundBean[funds.length];
			for (int i = 0; i < cfbs.length; i++) {
				CustomFundBean cfb = new CustomFundBean(funds[i]);
				cfb.setLastPrice(priceDAO.getLatestPrice(funds[i].getId()));
				cfbs[i] = cfb;
			}
			session.setAttribute("fundList", cfbs);
			DateFormat format = new SimpleDateFormat("mm/dd/yyyy", Locale.ENGLISH);
			Date d = priceDAO.getLastTransactionDay();
			Calendar c = new GregorianCalendar();
			c.setTime(d);
			c.set(Calendar.HOUR_OF_DAY, 0); //anything 0 - 23
		    c.set(Calendar.MINUTE, 0);
		    c.set(Calendar.SECOND, 0);
		    session.setAttribute("lastTranDay", format.format(d));
			session.setAttribute("nextTranDay", format.format(c.getTime()));
			if (request.getParameter("count") == null) {
				session.setAttribute("count", cfbs.length);
				return "transition.jsp";
			}
			RawPriceBean[] rawPrices = getPrices(request);
			setPriceAttributes(rawPrices, request);

			errors.addAll(getValidationErrors(rawPrices));
			if (errors.size() != 0) {
				return "transition.jsp";
			}
			d = format.parse((String)request.getParameter("date"));
			int count = Integer.parseInt((String) request.getParameter("count"));
			PriceBean price;
			Map<Integer, Long> priceMap = new HashMap<Integer, Long>();

			for (int i=0; i<count; i++){
				price = new PriceBean(rawPrices[i].getFund_id(), d, Long.parseLong(rawPrices[i].getPrice()));
				priceDAO.create(price);
				priceMap.put(rawPrices[i].getFund_id(), Long.parseLong(rawPrices[i].getPrice()));
			}
			
			TransactionBean[] pendingTrans = transactionDAO.getPendingTransactions();
			for (TransactionBean tran : pendingTrans) {
				execute (tran, d, (long) priceMap.get(tran.getFund_id()));
			}
			success.add("You have successfully created prices for transaction day " + (String)request.getParameter("date"));
			for (int i = 0; i < cfbs.length; i++) {
				CustomFundBean cfb = new CustomFundBean(funds[i]);
				cfb.setLastPrice(priceDAO.getLatestPrice(funds[i].getId()));
				cfbs[i] = cfb;
			}
			session.setAttribute("fundList", cfbs);
			d = priceDAO.getLastTransactionDay();
			c = new GregorianCalendar();
			c.set(Calendar.YEAR, d.getYear());
			c.set(Calendar.MONTH, d.getMonth());
			c.set(Calendar.DATE, d.getDay());
		    c.set(Calendar.HOUR_OF_DAY, 0); //anything 0 - 23
		    c.set(Calendar.MINUTE, 0);
		    c.set(Calendar.SECOND, 0);
		    c.add(Calendar.DAY_OF_MONTH, 1);
			session.setAttribute("lastTranDay", format.format(d));
			session.setAttribute("nextTranDay", c.getTime());
			return "transition.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "transition.jsp";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return "transition.jsp";
		}
	}

	private void execute(TransactionBean tran, Date d, long price) throws RollbackException {
		// TODO Auto-generated method stub
		switch (tran.getTransaction_type()){
		case 0:{
			customerDAO.updateCash(tran.getCustomer_id(), 0-tran.getAmount());
			transactionDAO.executeBuy(tran.getTransaction_id(), d, price);
			positionDAO.updatePosition(new PositionBean(tran.getCustomer_id(), tran.getFund_id(), tran.getAmount()/price));
		}
		case 1:{
			positionDAO.updatePosition(new PositionBean(tran.getCustomer_id(), tran.getFund_id(), 0-tran.getShares()));
			transactionDAO.executeSell(tran.getTransaction_id(), d, price);
			customerDAO.updateCash(tran.getCustomer_id(), tran.getShares()*price);
			
			}
		case 2:{
			customerDAO.updateCash(tran.getCustomer_id(), 0-tran.getAmount());
			transactionDAO.executeCheck(tran.getTransaction_id(), d);
		}
		case 3:{
			customerDAO.updateCash(tran.getCustomer_id(), tran.getAmount());
			transactionDAO.executeCheck(tran.getTransaction_id(), d);
		}
		
		}
	}

	private List<String> getValidationErrors(RawPriceBean[] rawPrices) {
		// TODO Auto-generated method stub
		List<String> errors = new ArrayList<String>();

		for (RawPriceBean rpb : rawPrices) {
			if (rpb == null || rpb.getPrice() == null || rpb.getPrice().trim().length() == 0) {
				errors.add("Please enter the price for fund with id "
						+ rpb.getFund_id());
			}
		}
		return errors;
	}

	private void setPriceAttributes(RawPriceBean[] rawPrices,
			HttpServletRequest request) {
		int count = Integer.parseInt((String) request.getParameter("count"));
		for (int i = 0; i < count; i++) {
			request.setAttribute("price" + (i+1), rawPrices[i].getPrice());
		}

	}

	private RawPriceBean[] getPrices(HttpServletRequest request) {
		// TODO Auto-generated method stub
		int count = Integer.parseInt((String) request.getParameter("count"));
		RawPriceBean[] result = new RawPriceBean[count];
		for (int i = 0; i < count; i++) {
			result[i] = new RawPriceBean(i + 1,
					(String) request.getParameter("price" + (i + 1)));
			}
		return result;
	}

}
