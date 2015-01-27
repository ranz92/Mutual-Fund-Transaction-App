//package controller;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import model.CustomerDAO;
//import model.EmployeeDAO;
//import model.Model;
//
//import org.genericdao.RollbackException;
//import org.mybeans.form.FormBeanException;
//import org.mybeans.form.FormBeanFactory;
//
//import databeans.CustomerBean;
//import databeans.EmployeeBean;
//import formbeans.CusLoginForm;
//
//public class EmpLoginAction extends Action {
//	private FormBeanFactory<CusLoginForm> formBeanFactory = FormBeanFactory.getInstance(CusLoginForm.class);
//	
//	private EmployeeDAO employeeDAO;
//	
//	public EmpLoginAction(Model model) {
//		employeeDAO = model.getEmployeeDAO();
//	}
//	
//	public String getName() {
//		return "emplogin.do";
//	}
//	
//	public String perform(HttpServletRequest request) {
//		List<String> errors = new ArrayList<String>();
//		request.setAttribute("errors",errors);
//		
//		try {
//			HttpSession session = request.getSession();
//			if (session.getAttribute("employee") != null)
//			return "viewByEmployee.do";
//			
//			
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
//				System.out.print(errors);
//				
//				return "login.jsp";
//			}
//			
//			EmployeeBean employee = employeeDAO.read(form.getUserName());
//			System.out.print("username:"+form.getUserName());
//			if(employee == null) {
//				System.out.print("Username not found");
//				
//				errors.add("Username not found");
//				return "login.jsp";
//			}
//			
//			if(!employee.checkPassword(form.getPassword())) {
//				System.out.print("Incorrect password");
//				
//				errors.add("Incorrect password");
//				return "login.jsp";
//			}
//			
//			session.setAttribute("employee",employee);
//			System.out.print("good!");
//			//return "viewAccountByEmp.jsp";
//			return "viewByEmployee.do";
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
