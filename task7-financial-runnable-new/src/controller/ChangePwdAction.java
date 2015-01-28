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
import formbeans.ChangePwdForm;

public class ChangePwdAction extends Action {
	private FormBeanFactory<ChangePwdForm> formBeanFactory = FormBeanFactory.getInstance(ChangePwdForm.class);
	private CustomerDAO customerDAO;
	
	public ChangePwdAction(Model model) {
		customerDAO = model.getCustomerDAO();
	}
	
	public String getName() {
		return "cusChangePwd.do";
	}
	
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		List<String> success = new ArrayList<String>();
		request.setAttribute("success", success);
		
		try {
			if(request.getSession().getAttribute("customer") == null) {
				errors.add("Please log in as a employee.");
				return "login.jsp";
			}
			
			CustomerBean customer = (CustomerBean) request.getSession().getAttribute("customer");
			ChangePwdForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			
			if(!form.isPresent()) {
				return "cusChangePwd.jsp";
			}
			
			errors.addAll(form.getValidationErrors());
			if(errors.size()>0) {
				return "cusChangePwd.jsp";
			}
			
			customer.setPassword(form.getNewPassword());
			customerDAO.update(customer);
			success.add("You have succesfully changed password");
		
			return "cusChangePwd.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		} catch(FormBeanException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		}
	}

}
