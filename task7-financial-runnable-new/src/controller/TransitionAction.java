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
		HttpSession session = request.getSession(false);

		List<String> success = new ArrayList<String>();
		request.setAttribute("success", success);

		try {
			if (session.getAttribute("customer") != null){
		        session.setAttribute("customer",null);
			}
			if(request.getSession().getAttribute("employee") == null) {
				errors.add("Please log in as an employee.");
				return "login.jsp";
			}
			
			FundBean[] funds = fundDAO.getFundList();
			CustomFundBean[] cfbs = new CustomFundBean[funds.length];
			for (int i = 0; i < cfbs.length; i++) {
				CustomFundBean cfb = new CustomFundBean(funds[i]);
				cfb.setLastPrice(priceDAO.getLatestPrice(funds[i].getId()));
				cfbs[i] = cfb;
			}
			session.setAttribute("fundList", cfbs);
			DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
			Date d = priceDAO.getLastTransactionDay();
			Calendar c = new GregorianCalendar();
			c.set(1900, 01, 01);
		    session.setAttribute("count", cfbs.length);
		    if (d == null){
		    	session.setAttribute("lastTranDay", "No transition day occurred");
		    }
		    else {
		    	
				c.setTime(d);
				c.set(Calendar.HOUR_OF_DAY, 0); //anything 0 - 23
			    c.set(Calendar.MINUTE, 0);
			    c.set(Calendar.SECOND, 0);
			    c.add(Calendar.DATE, 1);
		    	session.setAttribute("lastTranDay", format.format(d));
		    }
			session.setAttribute("nextTranDay", format.format(c.getTime()));
		    if (request.getParameter("count") == null) {
				return "transition.jsp";
			}
			RawPriceBean[] rawPrices = getPrices(request);
			//setPriceAttributes(rawPrices, request);

			errors.addAll(getValidationErrors(request));
			if (errors.size() != 0) {
				return "transition.jsp";
			}
			d = format.parse((String)request.getParameter("date"));
			int count = Integer.parseInt((String) request.getParameter("count"));
			if (count != fundDAO.getCount()){
				errors.add("You haven't processed all the funds");
				return "transition.jsp";
			}
			PriceBean price;
			Map<Integer, Long> priceMap = new HashMap<Integer, Long>();
			long priceL;
			for (int i=0; i<count; i++){
				double a = Double.valueOf(rawPrices[i].getPrice());
				double a1 = a*100;
				priceL = (new Double(a1)).longValue();
				price = new PriceBean(rawPrices[i].getFund_id(), d, priceL);
				priceDAO.createPrice(price);
				priceMap.put(rawPrices[i].getFund_id(), priceL);
			}
			
			TransactionBean[] pendingTrans = transactionDAO.getPendingTransactions();
			for (TransactionBean tran : pendingTrans) {
				if (tran.getFund_id() > 0){
				execute (tran, d, (long) priceMap.get(tran.getFund_id()));
				}
				else 
					execute (tran, d, 0);
			}
			success.add("You have successfully ended a transaction day " + (String)request.getParameter("date"));
			for (int i = 0; i < cfbs.length; i++) {
				CustomFundBean cfb = new CustomFundBean(funds[i]);
				cfb.setLastPrice(priceDAO.getLatestPrice(funds[i].getId()));
				cfbs[i] = cfb;
			}
			session.setAttribute("fundList", cfbs);
			d = priceDAO.getLastTransactionDay();
			
		    session.setAttribute("count", cfbs.length);
		    if (d == null){
		    	session.setAttribute("lastTranDay", "No transition day occurred");
		    }
		    else {
		    	c.setTime(d);
				c.set(Calendar.HOUR_OF_DAY, 0); //anything 0 - 23
			    c.set(Calendar.MINUTE, 0);
			    c.set(Calendar.SECOND, 0);
			    c.add(Calendar.DATE, 1);
		    	session.setAttribute("lastTranDay", format.format(d));
		    }session.setAttribute("nextTranDay", format.format(c.getTime()));
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
			double share = new Double(tran.getAmount())/new Double(price);
			tran.setShares((long)(share*1000));positionDAO.updatePosition(new PositionBean(tran.getCustomer_id(), tran.getFund_id(), (long)(share*1000)));
			break;
		}
		case 1:{
			positionDAO.updatePosition(new PositionBean(tran.getCustomer_id(), tran.getFund_id(), 0-tran.getShares()));
			transactionDAO.executeSell(tran.getTransaction_id(), d, price);
			double amount = new Double(tran.getShares())/1000.000*new Double (price);
			System.out.println(new Double(tran.getShares()));
			System.out.println(new Double(tran.getShares())/1000.000);
			System.out.println(new Double (price));
			System.out.println(new Double(tran.getShares())/1000.000*new Double (price));
			customerDAO.updateCash(tran.getCustomer_id(), (long)amount);
			break;
			}
		case 2:{
			customerDAO.updateCash(tran.getCustomer_id(), 0-tran.getAmount());
			transactionDAO.executeCheck(tran.getTransaction_id(), d);
			break;
		}
		case 3:{
			customerDAO.updateCash(tran.getCustomer_id(), tran.getAmount());
			transactionDAO.executeCheck(tran.getTransaction_id(), d);
			break;
		}
		
		}
	}

	private List<String> getValidationErrors(HttpServletRequest request) throws ParseException, RollbackException {
		// TODO Auto-generated method stub
		List<String> errors = new ArrayList<String>();
		if (request.getParameter("date") == null || request.getParameter("date").length() == 0) {
			errors.add("Please choose a transition day!");
		}
		else {
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
		Date d = format.parse(request.getParameter("date"));
		if (priceDAO.getLastTransactionDay()!=null && !d.after(priceDAO.getLastTransactionDay())){
			errors.add("Please enter a transition day after " + format.format(priceDAO.getLastTransactionDay()));
		}
		}
		long price;
		RawPriceBean[] rawPrices = getPrices(request);
		for (RawPriceBean rpb : rawPrices) {
			if (rpb == null || rpb.getPrice() == null || rpb.getPrice().trim().length() == 0) {
				errors.add("Please enter the price for fund with id "
						+ rpb.getFund_id());
				continue;
			}
			try {
				double a = Double.valueOf(rpb.getPrice());
				double a1 = a*100;
				price = (new Double(a1)).longValue();
				
				if (price < 1 || price > 100000) {
					errors.add("Please enter a valid price for fund with id " + rpb.getFund_id() + " between 0.01 and 1000");
				}
			} catch (NumberFormatException e) {
				errors.add("Please enter a valid price for fund with id " + rpb.getFund_id());
			}
		}
		return errors;
	}

//	private void setPriceAttributes(RawPriceBean[] rawPrices,
//			HttpServletRequest request) {
//		int count = Integer.parseInt((String) request.getParameter("count"));
//		for (int i = 0; i < count; i++) {
//			request.setAttribute("price" + (i+1), rawPrices[i].getPrice());
//		}
//
//	}

	private RawPriceBean[] getPrices(HttpServletRequest request) {
		// TODO Auto-generated method stub
		int count = Integer.parseInt((String) request.getParameter("count"));
		RawPriceBean[] result = new RawPriceBean[count];
		for (int i = 0; i < count; i++) {
			result[i] = new RawPriceBean(i + 1,
					((String) request.getParameter("price" + (i + 1))).trim());
			}
		return result;
	}

}
