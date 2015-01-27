package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.CustomerDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.CustomerBean;
import databeans.EmployeeBean;
import formbeans.ChangePwdForm;
import formbeans.CustomerForm;

public class ResetPwdAction extends Action{
	private FormBeanFactory<ChangePwdForm> formBeanFactory = FormBeanFactory.getInstance(ChangePwdForm.class);
	private FormBeanFactory<CustomerForm> formBeanFactory2 = FormBeanFactory.getInstance( CustomerForm.class);

	private CustomerDAO cusDAO;

	public ResetPwdAction(Model model) {
		cusDAO = model.getCustomerDAO();
	}
	
	public String getName() {
		return "resetPwd.do";
	}
	
	public String perform(HttpServletRequest request) {
		EmployeeBean employee = (EmployeeBean) request.getSession(false).getAttribute("employee");

		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		if (employee == null) {
			errors.add("You don't have the employee right to view the account!");
			return "error.jsp";
		} else {
			try {
				//List the all the customer in the dropdown menu.
				request.setAttribute("customerList", cusDAO.getCustomer());
				//Get the specific customer from jsp.
				CustomerForm form2 = formBeanFactory2.create(request);
				String username = form2.getUsername();
                if(username != null) {
                    CustomerBean customer = cusDAO.read(username);
					
					ChangePwdForm form = formBeanFactory.create(request);
					request.setAttribute("form", form);
					/*
					if(!form.isPresent()) {
						return "resetPwd.jsp";
					}
					*/
					errors.addAll(form.getValidationErrors());
					if (errors.size() != 0) {
						return "resetPwd.jsp";
					}
					
					customer.setPassword(form.getNewPassword());
					cusDAO.update(customer);
					String message = "You have succesfully changed password.";
					request.setAttribute("success", message);
					
					return "success.jsp";
                }
                	
					
				
			}catch (RollbackException e) {
			    errors.add(e.getMessage());
			    return "error.jsp";
		    } catch(FormBeanException e) {
			    errors.add(e.getMessage());
		    	return "error.jsp";
		    }
		}
	}	
}
