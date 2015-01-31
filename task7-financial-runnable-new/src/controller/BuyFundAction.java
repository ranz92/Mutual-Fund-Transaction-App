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
			customer = customerDAO.getCustomer(customer.getCustomerId());
			session.setAttribute("customer", customer);
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
					pou.setSymbol("N/A");
				}
				pou.setAmount((double)tran.getAmount()/100.00);
				pous[i] = pou;
				pending += (double)(tran.getAmount()/100.00);
			}
			session.setAttribute("mFundList", pous);
			session.setAttribute("pendingAmount",pending);
			
			return "buyFund.jsp";
		} catch (RollbackException e) {
			e.printStackTrace();
		}
		return "buyFund.jsp" ;
	}
	

}
