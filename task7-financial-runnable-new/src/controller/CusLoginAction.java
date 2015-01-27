//package controller;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import model.CustomerDAO;
//import model.Model;
//
//import org.genericdao.RollbackException;
//import org.mybeans.form.FormBeanException;
//import org.mybeans.form.FormBeanFactory;
//
//import databeans.CustomerBean;
//import formbeans.CusLoginForm;
//
//public class CusLoginAction extends Action {
//	private FormBeanFactory<CusLoginForm> formBeanFactory = FormBeanFactory.getInstance(CusLoginForm.class);
//	
//	private CustomerDAO customerDAO;
//	
//	public CusLoginAction(Model model) {
//		customerDAO = model.getCustomerDAO();
//	}
//	
//	public String getName() {
//		return "cuslogin.do";
//	}
//	
//	public String perform(HttpServletRequest request) {
//		List<String> errors = new ArrayList<String>();
//		request.setAttribute("errors",errors);
//		
//		try {
//			HttpSession session = request.getSession();
//			if (session.getAttribute("customer") != null)
//			return "viewAccountByCus.jsp";
//			CusLoginForm form = formBeanFactory.create(request);
//			request.setAttribute("form",form);
//			
////			if(!form.isPresent()) {
////				System.out.print("form not present");
////				return "index.jsp";
////			}
//			
//			errors.addAll(form.getValidationErrors());
//			if(errors.size() != 0) {
//				System.out.print("error");
//				
//				return "login.jsp";
//			}
//			
//			CustomerBean customer = customerDAO.read(form.getUserName());
//			System.out.print("username:"+form.getUserName());
//			if(customer == null) {
//				System.out.print("Username not found");
//				
//				errors.add("Username not found");
//				return "login.jsp";
//			}
//			
//			if(!customer.checkPassword(form.getPassword())) {
//				System.out.print("Incorrect password");
//				
//				errors.add("Incorrect password");
//				return "login.jsp";
//			}
//			
//			session.setAttribute("customer",customer);
//			System.out.print("good!");
//			return "viewAccountByCus.jsp";
//		} catch (RollbackException e) {
//			errors.add(e.getMessage());
//			return "error.jsp";
//		} catch (FormBeanException e) {
//			errors.add(e.getMessage());
//			return "error.jsp";
//		}
//	}
//
//}
