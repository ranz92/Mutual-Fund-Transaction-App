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
		HttpSession session = request.getSession(false);

		//EmployeeBean right = (EmployeeBean) request.getSession(false).getAttribute("employee");
		List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
        List<String> success = new ArrayList<String>();
        request.setAttribute("success",success);
        
        if (session.getAttribute("customer") != null){
	        session.setAttribute("customer",null);
		}
        
		if (session.getAttribute("employee") == null) {
			errors.add("You don't have the employee right to view the account!");
			return "error.jsp";
		} else {
			try {
				//List the all the customer in the dropdown menu.
				request.setAttribute("customerList", cusDAO.getCustomer());
				return "depositCheck.jsp";
			} catch (RollbackException e) {
				errors.add(e.getMessage());
				return "depositCheck.jsp";
			} 
		}
	}
}