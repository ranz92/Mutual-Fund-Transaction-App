package controller;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
import databeans.EmployeeBean;
import databeans.FundBean;
import databeans.PositionBean;
import databeans.PositionOfUser;
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
import formbeans.CustomerForm;
import formbeans.TransactionHisForm;

public class TransactionHistoryAction extends Action {
	private FormBeanFactory<CustomerForm> formBeanFactory = FormBeanFactory.getInstance( CustomerForm.class);
	private FundDAO fundDAO;
	private PriceDAO priceDAO;
	private TransactionDAO transactionDAO;
	private CustomerDAO customerDAO;
	private PositionDAO positionDAO;

	public TransactionHistoryAction(Model model) {
		fundDAO = model.getFundDAO();
		priceDAO = model.getPriceDAO();
		transactionDAO = model.getTransactionDAO();
		customerDAO = model.getCustomerDAO();
		positionDAO = model.getPositionDAO();
	}

	public String getName() {
		return "transactionHistory.do";
	}

	public String perform(HttpServletRequest request) {
//		if(request.getSession().getAttribute("customer") == null) {
//			errors.add("Please log in as a customer.");
//			return "login.jsp";
//		}
		List<String> errors = new ArrayList<String>();
		EmployeeBean admin = (EmployeeBean) request.getSession(false).getAttribute("employee");
		CustomerBean customer = (CustomerBean) request.getSession(false).getAttribute("customer");
		DecimalFormat dfAmount = new DecimalFormat("###,###,##0.00");
		DecimalFormat dfShare = new DecimalFormat("###,###,##0.000");
		DecimalFormat dfPrice = new DecimalFormat("###,###,##0.00");
		try {
			
			TransactionBean[] allTransactions = null;
			
			if(admin != null) {
				CustomerForm form = formBeanFactory.create(request);
				String username = form.getUsername();
				int customerId = customerDAO.getCustomer(username).getCustomerId();
				allTransactions = transactionDAO.getTransactions(customerId);
			}
			else if(customer != null) {
				int userId = customer.getCustomerId();
				allTransactions = transactionDAO.getTransactions(userId);
			}
			
			Arrays.sort(allTransactions, Collections.reverseOrder(new Comparator<TransactionBean>(){

				@Override
				public int compare(TransactionBean t1, TransactionBean t2) {
					if(t1.getExecute_date()==null && t2.getExecute_date()!=null)	return 1;
					if(t1.getExecute_date()!=null && t2.getExecute_date()==null)	return -1;
					if(t1.getExecute_date()!=null && t2.getExecute_date()!=null)
						return t1.getExecute_date().compareTo(t2.getExecute_date());
					return 0;
				}
				
			}));
			
			TransactionHisForm[] histories = new TransactionHisForm[allTransactions.length];
			for(int i=0;i<histories.length;i++) {
				CustomerBean customerTemp = customerDAO.getCustomer(allTransactions[i].getCustomer_id());
				histories[i] = new TransactionHisForm();
				histories[i].setCustomerName(customerTemp.getFirstname()+" "+customerTemp.getLastname());
				histories[i].setExecuteDate(allTransactions[i].getExecute_date());
				histories[i].setAmount(dfAmount.format(allTransactions[i].getAmount()/100.0));
				if(allTransactions[i].getExecute_date() == null)
					histories[i].setStatus("Pending");
				else
					histories[i].setStatus("Completed");
				switch(allTransactions[i].getTransaction_type()) {
				case(0):
					histories[i].setTransactionType("Buy Fund");
					break;
				case(1):
					histories[i].setTransactionType("Sell Fund");
					break;
				case(2):
					histories[i].setTransactionType("Request Check");
					break;
				case(3):
					histories[i].setTransactionType("Deposit Check");
				}
				
				// If this is fund transaction
				FundBean fund = new FundBean();
				int fundId = allTransactions[i].getFund_id();
				if ((fund=fundDAO.read(fundId))!=null){
					histories[i].setFundName(fund.getName());
					if(allTransactions[i].getExecute_date() != null) {
						PriceBean[] prices = priceDAO.getPrice(fundId);
						long thePrice = -1;
						for(PriceBean price : prices) {
							if(price.getPrice_date().equals(allTransactions[i].getExecute_date())){
								thePrice = price.getPrice();
								break;
							}
						}
						histories[i].setSharePrice(dfPrice.format(thePrice/100.0));
						if(allTransactions[i].getTransaction_type() == 0){
							histories[i].setNumShares(dfShare.format((double)allTransactions[i].getAmount()/thePrice));
						} else if (allTransactions[i].getTransaction_type() == 1) {
							histories[i].setNumShares(dfShare.format((double)allTransactions[i].getShares()/100.0));
						}
						else{
							histories[i].setNumShares(dfShare.format("0"));
						}
					}
				} else {
					
				}
				
			}
			request.setAttribute("transactions", histories);
//			System.out.println("--------------->"+allTransactions.length);
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			errors.add(e.getMessage());
			return "error.jsp";
		} catch (FormBeanException e) {
			// TODO Auto-generated catch block
			errors.add(e.getMessage());
			return "error.jsp";
		}
		if(admin != null) {
			return "transactionHistoryEmp.jsp";
		} else if(customer != null) {
			return "transactionHistoryCus.jsp";
		}
		return "login.do";
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
