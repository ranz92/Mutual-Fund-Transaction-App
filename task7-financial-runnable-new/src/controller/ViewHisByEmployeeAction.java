package controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.PositionDAO;
import model.PriceDAO;
import model.TransactionDAO;
import model.Model;
import model.CustomerDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.CustomerBean;
import databeans.EmployeeBean;
import databeans.PositionBean;
import databeans.TransactionBean;
import formbeans.CustomerForm;

public class ViewHisByEmployeeAction extends Action{
	private FormBeanFactory<CustomerForm> formBeanFactory = FormBeanFactory.getInstance( CustomerForm.class);

	private TransactionDAO transacDAO;
	private CustomerDAO cusDAO;
	private PositionDAO posDAO;
	private PriceDAO priceDAO;

	public ViewHisByEmployeeAction (Model model) {
		transacDAO = model.getTransactionDAO();
		cusDAO = model.getCustomerDAO();
        posDAO = model.getPositionDAO();
        priceDAO = model.getPriceDAO();
	}

	public String getName() {
		return "viewHisByEmployee.do";
	}
	
	public String perform(HttpServletRequest request) {
		//EmployeeBean right = (EmployeeBean) request.getSession(false).getAttribute("employee");
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		HttpSession session = request.getSession();

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
				//Get the specific customer from jsp.                
			} catch (RollbackException e) {
				errors.add(e.getMessage());
				return "error.jsp";
			} 
			return "viewAccountHisByEmp.jsp";	
		}
	}
}

