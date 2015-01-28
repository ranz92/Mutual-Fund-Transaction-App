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
		
		DecimalFormat df = new DecimalFormat("###,###,###.00");
		
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
			transaction.setAmount(form.getAmountAsLong());
			transaction.setTransaction_type(2);
			if(transaction.getAmount() <= 0) {
				errors.add("Amount should be a positive number");
				return "requestCheck.jsp";
			}
				
			if (transactionDAO.checkEnoughCash(customer.getCustomerId(), customer.getCash(), transaction.getAmount()))
			transactionDAO.createReqChkTransaction(transaction);
			else errors.add("Not enough amount");
			
			HttpSession session = request.getSession();
			customer = customerDAO.read(customer.getCustomerId());
			session.setAttribute("customer",customer);
			
			TransactionBean[] trans = transactionDAO.getPendingBuy(customer.getCustomerId());
			PositionOfUser[] pous = new PositionOfUser[trans.length];
			TransactionBean tran = new TransactionBean();
			int id = 0;
			long pendingAmount = 0;
			FundBean fund = new FundBean();
			for (int i = 0; i<pous.length; i++){
				PositionOfUser pou = new PositionOfUser();
				tran = trans[i];
				id = tran.getFund_id();
				if ((fund=fundDAO.read(id))!=null){
					pou.setName(fund.getName());
					pou.setSymbol(fund.getSymbol());
				}
				else {
					pou.setName("Request Check");
				}
				pou.setAmount(tran.getAmount());
				pous[i] = pou;
				pendingAmount += tran.getAmount();
			}
			String availableAmount = df.format(customer.getCash() - pendingAmount);
			
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