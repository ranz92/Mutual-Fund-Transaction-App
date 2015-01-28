package controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import model.FundDAO;
import model.Model;
import model.TransactionDAO;
import formbeans.RequestCheckForm;

public class ReqChkAction extends Action {
	private FormBeanFactory<RequestCheckForm> formBeanFactory = FormBeanFactory.getInstance(RequestCheckForm.class);
	private TransactionDAO transactionDAO;
	private CustomerDAO customerDAO;
	private FundDAO fundDAO;
	
	public ReqChkAction(Model model) {
		customerDAO = model.getCustomerDAO();
		transactionDAO = model.getTransactionDAO();
		fundDAO = model.getFundDAO();
	}
	
	public String getName() {
		return "requestCheck.do";
	}
	
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
        List<String> success = new ArrayList<String>();
        request.setAttribute("success",success);
        
        DecimalFormat df = new DecimalFormat("###,###,###.00");
		
		try {
			
			CustomerBean customer = (CustomerBean) request.getSession(false).getAttribute("customer");
			HttpSession session = request.getSession();
	
			TransactionBean[] trans = transactionDAO.getPendingBuy(customer.getCustomerId());
	        TransactionBean tran = new TransactionBean();
	        PositionOfUser[] pous = new PositionOfUser[trans.length];
	        int id = 0;
			long pendingAmount = 0;
			for (int i = 0; i<pous.length; i++){
				PositionOfUser pou = new PositionOfUser();
				tran = trans[i];
				id = tran.getFund_id();
				FundBean fund = new FundBean();
				if ((fund=fundDAO.read(id))!=null){
					pou.setName(fund.getName());
				}
				else {
					pou.setName("Check Request");
				}
				pou.setAmount(tran.getAmount());
				pous[i] = pou;
				pendingAmount += tran.getAmount();
			}
			session.setAttribute("mFundList", pous);
			String availableAmount = df.format(customer.getCash() - pendingAmount);
			
	        if (errors.size() > 0) return "error.jsp";
			
	        request.getSession(true).setAttribute("customer", customer);
	        request.getSession(true).setAttribute("availableAmount", availableAmount);
			
			return "requestCheck.jsp";
		} 
		catch(Exception e) {return "requestCheck.jsp";}
	}
}