package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.CustomerDAO;
import model.Model;

public class ViewByCustomerAction extends Action {
	private CustomerDAO customerDAO;
	
	public ViewByCustomerAction(Model model) {
		customerDAO = model.getCustomerDAO();
	}
	
	public String getName() {
		return "viewCustomer.do";
	}
	
	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		try {
			if(session.getAttribute("customer") != null) {
				return "viewAccountByCus.jsp";
			}
			return "login.do";
		} catch (Exception e) {
			errors.add(e.getMessage());
			return "viewAccountByCus.jsp";
		}
	}

}
