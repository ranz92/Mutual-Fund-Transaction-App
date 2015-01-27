package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.CustomerDAO;
import model.EmployeeDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.CustomerBean;
import databeans.EmployeeBean;
import formbeans.LoginForm;

public class LoginAction extends Action {
	private FormBeanFactory<LoginForm> formBeanFactory = FormBeanFactory.getInstance(LoginForm.class);
	
	private EmployeeDAO employeeDAO;
	private CustomerDAO customerDAO;
	
	public LoginAction(Model model) {
		employeeDAO = model.getEmployeeDAO();
		customerDAO = model.getCustomerDAO();
	}
	
	public String getName(){
		return "login.do";
	}
	
	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors",errors);
		
		try {
			LoginForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);

			if(!form.isPresent()) {
				return "login.jsp";
			}

			errors.addAll(form.getValidationErrors());
			if(errors.size() != 0) {
				return "login.jsp";
			}

			if(form.isEmployee()) {
	
				EmployeeBean employee = employeeDAO.read(form.getUserName());
				
				if(employee == null) {
					errors.add("Username not found");
					return "login.jsp";
				}
				
				if(!employee.checkPassword(form.getPassword())) {
					errors.add("Incorrect password");
					return "login.jsp";
				}
				
				session.setAttribute("employee",employee);
				return "viewAccountByEmp.jsp";
			} else {
				CustomerBean customer = customerDAO.read(form.getUserName());
				
				if(customer == null) {
					errors.add("Username not found");
					return "login.jsp";
				}
				
				if(!customer.checkPassword(form.getPassword())) {
					errors.add("Incorrect password");
					return "login.jsp";
				}
				
				session.setAttribute("customer",customer);
				return "viewAccountByCus.jsp";
			}
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "login.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "login.jsp";
		}
	}

}
