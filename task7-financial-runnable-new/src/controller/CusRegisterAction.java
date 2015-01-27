package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.CustomerBean;
import databeans.EmployeeBean;
import formbeans.CusRegisterForm;
import model.CustomerDAO;
import model.Model;

public class CusRegisterAction extends Action{
	private FormBeanFactory<CusRegisterForm> formBeanFactory = FormBeanFactory.getInstance(CusRegisterForm.class);

	private CustomerDAO cusDAO;

	public CusRegisterAction(Model model) {
		cusDAO = model.getCustomerDAO();
	}

	public String getName() {
		return "cusRegister.do";
	}

	public String perform(HttpServletRequest request) {
		EmployeeBean right = (EmployeeBean) request.getSession(false).getAttribute("employee");
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		if (right == null) {
			errors.add("You don't have the employee right to create the account!");
			return "error.jsp";
		} else {
			try {
				CusRegisterForm form = formBeanFactory.create(request);
				request.setAttribute("form", form);
				
				if (!form.isPresent()) {
					return "cusRegister.jsp";
				}
				
				// Any validation errors?
				errors.addAll(form.getValidationErrors());
				if (errors.size() != 0) {
					return "cusRegister.jsp";
				}
				
				// Create the user bean
				CustomerBean customer = new CustomerBean(); 
				if ((cusDAO.read(form.getUsername())) != null) {
					errors.add("The username has existed");
					return "cusRegister.jsp";
				} else {

					customer.setUsername(form.getUsername());
					customer.setFirstname(form.getFirstName());
					customer.setLastname(form.getLastName());
					customer.setPassword(form.getPassword());
					customer.setAddrL1(form.getAddrL1());
					customer.setAddrL2(form.getAddrL2());
					customer.setState(form.getState());
					customer.setCity(form.getCity());
					customer.setZip(form.getZip());

					cusDAO.create(customer);
	                String success = "New customer, " + form.getUsername() + "has been created";


					// Attach (this copy of) the user bean to the session
					HttpSession session = request.getSession(false);
					session.setAttribute("customer", customer);
					session.setAttribute("message", success);

					return "success.jsp";  //return to the research page?!!
				}

			} catch (RollbackException e) {
				errors.add(e.getMessage());
				return "error.jsp";
			} catch (FormBeanException e) {
				errors.add(e.getMessage());
				return "error.jsp";
			}
		}
	}
}
