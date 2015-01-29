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
import databeans.PositionBean;
import databeans.PositionOfUser;
import databeans.TransactionBean;
import model.CustomerDAO;
import model.Model;
import model.TransactionDAO;
import model.FundDAO;
import formbeans.BuyForm;

public class BuyFundAction extends Action {
	private FormBeanFactory<BuyForm> formBeanFactory = FormBeanFactory.getInstance(BuyForm.class);
	private CustomerDAO customerDAO;
	private TransactionDAO transactionDAO;
	private FundDAO fundDAO;
	
	public BuyFundAction(Model model) {
		customerDAO = model.getCustomerDAO();
		transactionDAO = model.getTransactionDAO();
		fundDAO = model.getFundDAO();
	}
	
	public String getName() {
		return "buyfund.do";
	}
	
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		HttpSession session = request.getSession();
		
		DecimalFormat df = new DecimalFormat("#,##0.00");
		
		try {
			if (session.getAttribute("employee") != null){
		        session.setAttribute("employee",null);
			}
			
			if(request.getSession().getAttribute("customer") == null) {
				errors.add("Please log in as a customer.");
				return "login.jsp";
			}
			
			CustomerBean customer = (CustomerBean) request.getSession(false).getAttribute("customer");
//			BuyForm form  = formBeanFactory.create(request);
//			request.setAttribute("form", form);
			session.setAttribute("fundList", fundDAO.getFundList());
//			TransactionBean transaction = new TransactionBean();
//			transaction.setCustomer_id(customer.getCustomerId());
//			transaction.setFund_id(Integer.parseInt(form.getFundId()));; //should obtain from fund table, which is not established so far. So recorded as 0 temporarily here.
//			transaction.setAmount(form.getAmount());
//			transactionDAO.createBuyTransaction(transaction);
			TransactionBean[] trans = transactionDAO.getPendingBuy(customer.getCustomerId());
			PositionOfUser[] pous = new PositionOfUser[trans.length];
			TransactionBean tran = new TransactionBean();
			int id = 0;
			long pendingAmount = 0;
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
				pou.setAmount(tran.getAmount());
				
		//		pend = (double)(pou.getAmount()/1000);
				
				pous[i] = pou;
			//	pendingAmount += tran.getAmount();
				pending = (double)(tran.getAmount()/100.00);
			}
		//	String pendingAmountFormat = df.format(pendingAmount);
		//	String availableAmountFormat = df.format(customer.getCash() - pendingAmount);
			
			String pendingAmountFormat = df.format(pending);
			String availableAmountFormat = df.format(customer.getCash() - pending);
			
			session.setAttribute("mFundList", pous);
			session.setAttribute("pendingAmount",pendingAmountFormat);
			session.setAttribute("availableAmount", availableAmountFormat);
			
//			session.setAttribute("pendingAmount", pendingAmount);
//			session.setAttribute("availableAmount", customer.getCash()-pendingAmount);
			
			
			return "buyFund.jsp";
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "buyFund.jsp" ;
	}
	

}
