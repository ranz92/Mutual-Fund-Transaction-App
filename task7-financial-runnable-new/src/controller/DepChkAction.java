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
import databeans.EmployeeBean;
import databeans.FundBean;
import databeans.PositionBean;
import databeans.PositionOfUser;
import databeans.TransactionBean;
import model.CustomerDAO;
import model.FundDAO;
import model.Model;
import model.PositionDAO;
import model.TransactionDAO;
import formbeans.CustomerForm;
import formbeans.DepositCheckForm;
import formbeans.RequestCheckForm;
import formbeans.TransactionHisForm;

public class DepChkAction extends Action {
	private FormBeanFactory<DepositCheckForm> formBeanFactory = FormBeanFactory
			.getInstance(DepositCheckForm.class);
	private TransactionDAO transactionDAO;
	private CustomerDAO cusDAO;

	public DepChkAction(Model model) {
		transactionDAO = model.getTransactionDAO();
		cusDAO = model.getCustomerDAO();
	}

	@Override
	public String getName() {
		return "depositCheck.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		EmployeeBean right = (EmployeeBean) request.getSession(false).getAttribute("employee");
		List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
        List<String> success = new ArrayList<String>();
        request.setAttribute("success",success);
        
        DecimalFormat df = new DecimalFormat("###,###,##0.00");
        
        if(request.getSession().getAttribute("employee") == null) {
			errors.add("Please log in as an employee.");
			return "login.jsp";
		}
        
		if (right == null) {
			errors.add("You don't have the employee right to view the account!");
			return "error.jsp";
		} else {
			try {
				//List the all the customer in the dropdown menu.
				request.setAttribute("customerList", cusDAO.getCustomer());
				TransactionBean[] trans = transactionDAO.getAllPendingDepChk();
				TransactionHisForm[] allPendingDeposits = new TransactionHisForm[trans.length];
				for(int i=0; i<allPendingDeposits.length; i++) 
					allPendingDeposits[i] = new TransactionHisForm();
				for(int i=0; i<allPendingDeposits.length; i++) {
					CustomerBean customer = cusDAO.getCustomer(trans[i].getCustomer_id());
					allPendingDeposits[i].setCustomerName(customer.getFirstname()+" "+customer.getLastname());
					allPendingDeposits[i].setAmount(df.format((double)trans[i].getAmount()/100.0));
				}
				request.setAttribute("mFundList", allPendingDeposits);
				return "depositCheck.jsp";
			} catch (RollbackException e) {
				errors.add(e.getMessage());
				return "depositCheck.jsp";
			} 
		}
	}
}