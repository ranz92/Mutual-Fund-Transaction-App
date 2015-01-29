package controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.CustomerBean;
import databeans.FundBean;
import databeans.PositionOfUser;
import databeans.PositionOfUser4Check;
import databeans.TransactionBean;
import model.CustomerDAO;
import model.Model;
import model.TransactionDAO;
import model.FundDAO;
import formbeans.BuyForm;
import formbeans.RequestCheckForm;

public class ConfirmRequestCheckAction extends Action {
	private FormBeanFactory<RequestCheckForm> formBeanFactory = FormBeanFactory.getInstance(RequestCheckForm.class);
	private CustomerDAO customerDAO;
	private TransactionDAO transactionDAO;
	private FundDAO fundDAO;
	
	public ConfirmRequestCheckAction(Model model) {
		customerDAO = model.getCustomerDAO();
		transactionDAO = model.getTransactionDAO();
		fundDAO = model.getFundDAO();
	}
	
	public String getName() {
		return "confirmRequestCheck.do";
	}
	
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		DecimalFormat df = new DecimalFormat("###,###,##0.00");
		
		try {
			if(request.getSession().getAttribute("customer") == null) {
				errors.add("Please log in as a customer.");
				return "login.jsp";
			}
			
			CustomerBean customer = (CustomerBean) request.getSession(false).getAttribute("customer");
			RequestCheckForm form  = formBeanFactory.create(request);
			request.setAttribute("form", form);
			TransactionBean transaction = new TransactionBean();
			transaction.setCustomer_id(customer.getCustomerId());
			transaction.setFund_id(form.getIdAsInt()); //should obtain from fund table, which is not established so far. So recorded as 0 temporarily here.
			
			// Special behavior for amount
			try{
				double amt = Double.parseDouble(form.getAmount());
				amt = Math.round(amt * 100.0);
				transaction.setAmount((long)amt);
			} catch (NumberFormatException e) {
				errors.add("Amount should be a valid number");
				return "requestCheck.jsp";
			}
			
			transaction.setTransaction_type(2);
			if(transaction.getAmount() <= 0) {
				errors.add("Amount should be a positive number");
				return "requestCheck.jsp";
			}
			
			if (transactionDAO.checkEnoughCash(customer.getCustomerId(), customer.getCash(), transaction.getAmount()))
				transactionDAO.createReqChkTransaction(transaction);
			else 
				errors.add("Not enough amount");
			
			HttpSession session = request.getSession();
			// customer = customerDAO.read(customer.getCustomerId());
			session.setAttribute("customer",customer);
			
			TransactionBean[] trans = transactionDAO.getPendingBuy(customer.getCustomerId());
			PositionOfUser4Check[] pous = new PositionOfUser4Check[trans.length];
			TransactionBean tran = new TransactionBean();
			int id = 0;
			long pendingAmount = 0;
			Date date = new Date();
			FundBean fund = new FundBean();
			for (int i = 0; i<pous.length; i++){
				PositionOfUser4Check pou = new PositionOfUser4Check();
				tran = trans[i];
				id = tran.getFund_id();
				date = tran.getExecute_date();
				if ((fund=fundDAO.read(id))!=null){
					pou.setName(fund.getName());
					pou.setSymbol(fund.getSymbol());
				}
				else {
					pou.setName("Request Check");
				}
				pou.setDate(date);
				try{
					pou.setAmount(df.format((double)tran.getAmount()/100.0));
					
				} catch (NumberFormatException e){
					errors.add("Amount should be a valid number");
					return "requestCheck.jsp";
				}
				pous[i] = pou;
				pendingAmount += tran.getAmount();
			}
			String availableAmount = df.format((double)(customer.getCash() - pendingAmount)/100.0);
			
			session.setAttribute("mFundList", pous);
			session.setAttribute("availableAmount", availableAmount);
			
			return "requestCheck.jsp";
		} catch(FormBeanException e) {
			errors.add(e.getMessage());
			return "requestCheck.jsp";
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "requestCheck.jsp";
		
	}
	

}