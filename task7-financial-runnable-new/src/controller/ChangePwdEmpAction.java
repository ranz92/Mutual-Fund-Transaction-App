package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.EmployeeDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.EmployeeBean;
import formbeans.ChangePwdForm;

public class ChangePwdEmpAction extends Action {
	private FormBeanFactory<ChangePwdForm> formBeanFactory = FormBeanFactory.getInstance(ChangePwdForm.class);
	private EmployeeDAO employeeDAO;
	
	public ChangePwdEmpAction(Model model) {
		employeeDAO = model.getEmployeeDAO();
	}
	
	public String getName() {
		return "empChangePwd.do";
	}
	
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		List<String> success = new ArrayList<String>();
		request.setAttribute("success", success);
		
		try {
			
			if(request.getSession().getAttribute("employee") == null) {
				errors.add("Please log in as an customer.");
				return "login.jsp";
			}
			
			EmployeeBean employee = (EmployeeBean) request.getSession().getAttribute("employee");
			ChangePwdForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			
			if(!form.isPresent()) {
				return "empChangePwd.jsp";
			}
			
			errors.addAll(form.getValidationErrors());
			if(errors.size()>0) {
				return "empChangePwd.jsp";
			}
			
			employee.setPassword(form.getNewPassword());
			employeeDAO.update(employee);
			success.add("You have successfully changed password");
			return "empChangePwd.jsp";
		} catch(RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		} catch(FormBeanException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		}
	}

}
