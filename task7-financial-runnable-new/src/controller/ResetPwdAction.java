package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import model.CustomerDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.CustomerBean;
import databeans.EmployeeBean;
import formbeans.CustomerForm;

public class ResetPwdAction extends Action{
	//private FormBeanFactory<ChangePwdForm> formBeanFactory = FormBeanFactory.getInstance(ChangePwdForm.class);
	private FormBeanFactory<CustomerForm> formBeanFactory = FormBeanFactory.getInstance( CustomerForm.class);

	private CustomerDAO cusDAO;

	public ResetPwdAction(Model model) {
		cusDAO = model.getCustomerDAO();
	}
	
	public String getName() {
		return "resetPwd.do";
	}
	
	public String genRandomNum(int pwd_len){ //Produce the random number.
		  final int  maxNum = 36;
		  int i;  
		  int count = 0; //pwd length.
		  char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
		    'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
		    'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		  
		  StringBuffer pwd = new StringBuffer("");
		  Random r = new Random();
		  while(count < pwd_len){
		   
		   i = Math.abs(r.nextInt(maxNum));  //the largest produced is 36-1.
		   
		   if (i >= 0 && i < str.length) {
		    pwd.append(str[i]);
		    count ++;
		   }
		  }		  
		  return pwd.toString();
		 }
	
	public String perform(HttpServletRequest request) {
		EmployeeBean employee = (EmployeeBean) request.getSession(false).getAttribute("employee");

		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		if(request.getSession().getAttribute("employee") == null) {
			errors.add("Please log in as an customer.");
			return "login.jsp";
		}
		
		if (employee == null) {
			errors.add("You don't have the employee right to view the account!");
			return "error.jsp";
		} else {
			try {
				//List the all the customer in the dropdown menu.
				request.setAttribute("customerList", cusDAO.getCustomer());
				//Get the specific customer from jsp.
				CustomerForm form = formBeanFactory.create(request);
				
				if (!form.isPresent()) {
					return "resetPwd.jsp";
				}
				
				errors.addAll(form.getValidationErrors());
				if (errors.size() != 0) {
						return "resetPwd.jsp";
				}
				String username = form.getUsername();
                if(username != null) {
                    CustomerBean customer = cusDAO.read(username);
					
					//ChangePwdForm form = formBeanFactory.create(request);
					//request.setAttribute("form", form);
					/*
					if(!form.isPresent()) {
						return "resetPwd.jsp";
					}
					*/
					//errors.addAll(form.getValidationErrors());
			//		if (errors.size() != 0) {
			//			return "resetPwd.jsp";
			//		}
                    String pwd = genRandomNum(8);
					customer.setPassword(pwd);
					cusDAO.update(customer);
					String message = "Succesfully reset the password " + "<font color='red'>"+ pwd+ "</font>"+ " for " + username;
					request.setAttribute("message", message);
					
					return "success.jsp";
                } else {
                	errors.add("A customer should be specified!");
					return "error.jsp";
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
