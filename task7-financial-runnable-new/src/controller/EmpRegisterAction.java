package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.EmployeeDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.EmployeeBean;
import formbeans.EmpRegisterForm;

public class EmpRegisterAction extends Action{
	private FormBeanFactory<EmpRegisterForm> formBeanFactory = FormBeanFactory.getInstance(EmpRegisterForm.class);

	private EmployeeDAO employeeDAO;

	public EmpRegisterAction(Model model) {
		employeeDAO = model.getEmployeeDAO();
	}

	public String getName() {
		return "empRegister.do";
	}

	public String perform(HttpServletRequest request) {
		EmployeeBean right = (EmployeeBean) request.getSession(false).getAttribute("employee");
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		if(request.getSession().getAttribute("employee") == null) {
			errors.add("Please log in as an customer.");
			return "login.jsp";
		}
		
		if (right == null) {
			
			errors.add("You don't have the employee right to create the account!");
			return "error.jsp";
		} else {
			try {
				EmpRegisterForm form = formBeanFactory.create(request);
				request.setAttribute("form", form);
				
				if (!form.isPresent()) {
					return "empRegister.jsp";
				}
				
				// Any validation errors?
				errors.addAll(form.getValidationErrors());
				if (errors.size() != 0) {
					return "empRegister.jsp";
				}
				
				// Create the user bean
				EmployeeBean employee = new EmployeeBean(); 
				if ((employeeDAO.read(form.getUsername())) != null) {
					errors.add("The username has existed");
					return "empRegister.jsp";
				} else {

					employee.setUsername(form.getUsername());
					employee.setFirstname(form.getFirstName());
					employee.setLastname(form.getLastName());
					employee.setPassword(form.getPassword());

					employeeDAO.create(employee);
	                String success = "A new Employee " +"/'"+ form.getUsername() +"/'"+ "has been created";
			
					request.setAttribute("message", success);
					
					return "success.jsp";  
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
