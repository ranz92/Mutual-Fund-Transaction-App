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
import databeans.PositionBean;
import databeans.PositionOfUser;
import databeans.TransactionBean;
import model.CustomerDAO;
import model.Model;
import model.PositionDAO;
import model.PriceDAO;
import model.TransactionDAO;
import model.FundDAO;
import formbeans.DepositCheckForm;
import formbeans.SellForm;
import formbeans.TransactionHisForm;

public class ConfirmDepositCheckAction extends Action {
	private FormBeanFactory<DepositCheckForm> formBeanFactory = FormBeanFactory.getInstance(DepositCheckForm.class);
	private CustomerDAO customerDAO;
	private TransactionDAO transactionDAO;
	private FundDAO fundDAO;
	private PositionDAO positionDAO;
	private PriceDAO priceDAO;
	public ConfirmDepositCheckAction(Model model) {
		customerDAO = model.getCustomerDAO();
		transactionDAO = model.getTransactionDAO();
		fundDAO = model.getFundDAO();
		positionDAO = model.getPositionDAO();
		priceDAO = model.getPriceDAO();
	}
	
	public String getName() {
		return "confirmDepositCheck.do";
	}
	
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		try {
			
			if(request.getSession().getAttribute("employee") == null) {
				errors.add("Please log in as an employee.");
				return "login.jsp";
			}
			
			DepositCheckForm form  = formBeanFactory.create(request);
//			request.setAttribute("form", form);
			TransactionBean transaction = new TransactionBean();
			transaction.setCustomer_id(form.getIdAsInt());
			transaction.setTransaction_type(3);
			transaction.setFund_id(-1);
			try{
				request.setAttribute("customerList", customerDAO.getCustomer());

				double amt = Double.parseDouble(form.getAmount());
				amt = Math.round(amt * 100.0);
				
				if(amt < 0){
					errors.add("Amount should be a positive number");
					try {
						DecimalFormat df = new DecimalFormat("###,###,##0.00");
				 		TransactionBean[] trans;
						trans = transactionDAO.getAllPendingDepChk();
						TransactionHisForm[] allPendingDeposits = new TransactionHisForm[trans.length];
						for(int i=0; i<allPendingDeposits.length; i++) 
							allPendingDeposits[i] = new TransactionHisForm();
						for(int i=0; i<allPendingDeposits.length; i++) {
							CustomerBean customer = customerDAO.getCustomer(trans[i].getCustomer_id());
							allPendingDeposits[i].setCustomerName(customer.getFirstname()+" "+customer.getLastname());
							allPendingDeposits[i].setAmount(df.format((double)trans[i].getAmount()/100.0));
						}
						request.setAttribute("mFundList", allPendingDeposits);
					} catch (RollbackException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return "depositCheck.jsp";
				}
				
				if(amt < 1) {
					errors.add("Amount should be at least $0.01");
					try {
						DecimalFormat df = new DecimalFormat("###,###,##0.00");
				 		TransactionBean[] trans;
						trans = transactionDAO.getAllPendingDepChk();
						TransactionHisForm[] allPendingDeposits = new TransactionHisForm[trans.length];
						for(int i=0; i<allPendingDeposits.length; i++) 
							allPendingDeposits[i] = new TransactionHisForm();
						for(int i=0; i<allPendingDeposits.length; i++) {
							CustomerBean customer = customerDAO.getCustomer(trans[i].getCustomer_id());
							allPendingDeposits[i].setCustomerName(customer.getFirstname()+" "+customer.getLastname());
							allPendingDeposits[i].setAmount(df.format((double)trans[i].getAmount()/100.0));
						}
						request.setAttribute("mFundList", allPendingDeposits);
					} catch (RollbackException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return "depositCheck.jsp";
				}
				if(amt /100> 1000000000) {
					errors.add("Amount should be less than $1,000,000,000.00");
					try {
						DecimalFormat df = new DecimalFormat("###,###,##0.00");
				 		TransactionBean[] trans;
						trans = transactionDAO.getAllPendingDepChk();
						TransactionHisForm[] allPendingDeposits = new TransactionHisForm[trans.length];
						for(int i=0; i<allPendingDeposits.length; i++) 
							allPendingDeposits[i] = new TransactionHisForm();
						for(int i=0; i<allPendingDeposits.length; i++) {
							CustomerBean customer = customerDAO.getCustomer(trans[i].getCustomer_id());
							allPendingDeposits[i].setCustomerName(customer.getFirstname()+" "+customer.getLastname());
							allPendingDeposits[i].setAmount(df.format((double)trans[i].getAmount()/100.0));
						}
						request.setAttribute("mFundList", allPendingDeposits);
					} catch (RollbackException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return "depositCheck.jsp";
				}
				
				transaction.setAmount((long)amt);
				request.setAttribute("customerList", customerDAO.getCustomer());
			} catch (NumberFormatException e) {
				errors.add("Amount should be a valid number");
				try {
					DecimalFormat df = new DecimalFormat("###,###,##0.00");
			 		TransactionBean[] trans;
					trans = transactionDAO.getAllPendingDepChk();
					TransactionHisForm[] allPendingDeposits = new TransactionHisForm[trans.length];
					for(int i=0; i<allPendingDeposits.length; i++) 
						allPendingDeposits[i] = new TransactionHisForm();
					for(int i=0; i<allPendingDeposits.length; i++) {
						CustomerBean customer = customerDAO.getCustomer(trans[i].getCustomer_id());
						allPendingDeposits[i].setCustomerName(customer.getFirstname()+" "+customer.getLastname());
						allPendingDeposits[i].setAmount(df.format((double)trans[i].getAmount()/100.0));
					}
					request.setAttribute("mFundList", allPendingDeposits);
				} catch (RollbackException ee) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return "depositCheck.jsp";
			}
			
			transaction.setTransaction_type(3);
     		transactionDAO.createDepChkTransaction(transaction);
     		
     		

			request.setAttribute("success", "Your check is pending for execution");
			form.setAmount("");
		} catch(FormBeanException e) {
			errors.add(e.getMessage());
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			DecimalFormat df = new DecimalFormat("###,###,##0.00");
	 		TransactionBean[] trans;
			trans = transactionDAO.getAllPendingDepChk();
			TransactionHisForm[] allPendingDeposits = new TransactionHisForm[trans.length];
			for(int i=0; i<allPendingDeposits.length; i++) 
				allPendingDeposits[i] = new TransactionHisForm();
			for(int i=0; i<allPendingDeposits.length; i++) {
				CustomerBean customer = customerDAO.getCustomer(trans[i].getCustomer_id());
				allPendingDeposits[i].setCustomerName(customer.getFirstname()+" "+customer.getLastname());
				allPendingDeposits[i].setAmount(df.format((double)trans[i].getAmount()/100.0));
			}
			request.setAttribute("mFundList", allPendingDeposits);
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "depositCheck.jsp";
		
	}
	
}
