package controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
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

public class ConfirmBuyAction extends Action {
	private FormBeanFactory<BuyForm> formBeanFactory = FormBeanFactory.getInstance(BuyForm.class);
	private CustomerDAO customerDAO;
	private TransactionDAO transactionDAO;
	private FundDAO fundDAO;
	
	public ConfirmBuyAction(Model model) {
		customerDAO = model.getCustomerDAO();
		transactionDAO = model.getTransactionDAO();
		fundDAO = model.getFundDAO();
	}
	
	public String getName() {
		return "confirmbuy.do";
	}
	
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		HttpSession session = request.getSession();

		
		List<String> success = new ArrayList<String>();
		request.setAttribute("success",success);
		
		
		try {
			if (session.getAttribute("employee") != null){
		        session.setAttribute("employee",null);
			}
			if(session.getAttribute("customer") == null) {
				errors.add("Please log in as a customer.");
				return "login.jsp";
			}
			
			CustomerBean customer = (CustomerBean) request.getSession(false).getAttribute("customer");
			BuyForm form  = formBeanFactory.create(request);
			request.setAttribute("form", form);
			errors.addAll(form.getValidationErrors());
			
			TransactionBean transaction = new TransactionBean();
			transaction.setCustomer_id(customer.getCustomerId());
			transaction.setFund_id(Integer.parseInt(form.getFundId()));
			try {
				double a = Double.valueOf(form.getAmount());
				double a1 = a*100;
				long l = (new Double(a1)).longValue();
				transaction.setAmount(l);
			} catch(NumberFormatException e) {
				errors.add("Please enter numbers.");
			}
			
			
//			if (transactionDAO.checkEnoughCash(customer.getCustomerId(), customer.getCash(), transaction.getAmount()))
//			transactionDAO.createBuyTransaction(transaction);
//			else errors.add("Not enough amount");
			
			if(!transactionDAO.checkEnoughCash(customer.getCustomerId(), customer.getCash(), transaction.getAmount()))
				errors.add("Not enough amount");
			
			transactionDAO.createBuyTransaction(transaction);
		
			if(errors.size() > 0) {
				
				return "buyFund.jsp";
			} else {
				
				success.add("You have bought fund successfully.");
				form.setAmount("");
				session.setAttribute("fundList", fundDAO.getFundList());
				TransactionBean[] trans = transactionDAO.getPendingBuy(customer.getCustomerId());
				PositionOfUser[] pous = new PositionOfUser[trans.length];
				TransactionBean tran = new TransactionBean();
				int id = 0;
				double pending = 0;
				
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
						pou.setName("Check Request");
						pou.setName("N/A");
					}
					pou.setAmount((double)tran.getAmount()/100.00);
					pous[i] = pou;
					pending += (double)(tran.getAmount()/100.00);
				}
				session.setAttribute("mFundList", pous);
				session.setAttribute("pendingAmount",pending);
			}
			return "buyFund.jsp";
		} catch(FormBeanException e) {
			errors.add(e.getMessage());
			return "buyFund.jsp";
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "buyFund.jsp";
		
	}
	

}
