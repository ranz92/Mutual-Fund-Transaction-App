//Jiayi Zhu
//jiayiz
//08-600
//Dec 10, 2014

package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;

import model.CustomerDAO;
import model.EmployeeDAO;
import model.FundDAO;
import model.Model;
import model.PositionDAO;
import model.PriceDAO;
import databeans.CustomerBean;
import databeans.EmployeeBean;
import databeans.FundBean;
import databeans.PositionBean;
import databeans.PriceBean;


@SuppressWarnings("serial")
public class Controller extends HttpServlet {

	public void init() throws ServletException {
		Model model = new Model(getServletConfig());

//		Action.add(new CusLoginAction(model));
//		Action.add(new EmpLoginAction(model));
		Action.add(new LoginAction(model));
		Action.add(new LogoutAction(model));
		Action.add(new BuyFundAction(model));
		Action.add(new ResearchAction(model));
		Action.add(new ShowPerformanceAction(model));
		Action.add(new ChangePwdAction(model));
		Action.add(new ChangePwdEmpAction(model));
		Action.add(new ConfirmBuyAction(model));
		Action.add(new SellFundAction(model));
		Action.add(new ConfirmSellAction(model));
		Action.add(new CreateFundAction(model));
		Action.add(new EmpRegisterAction(model));
		Action.add(new CusRegisterAction(model));
		Action.add(new ViewByEmployeeAction(model));
		Action.add(new ViewByCustomerAction(model));
		Action.add(new TransitionAction(model));
		Action.add(new ResetPwdAction(model));
		Action.add(new ReqChkAction(model));
		Action.add(new DepChkAction(model));
		Action.add(new ConfirmRequestCheckAction(model));
		Action.add(new ConfirmDepositCheckAction(model));
		Action.add(new TransactionHistoryAction(model));
		Action.add(new ViewHisByEmployeeAction(model));
		
		CustomerDAO customerDAO = model.getCustomerDAO();
		EmployeeDAO employeeDAO = model.getEmployeeDAO();
		FundDAO fundDAO = model.getFundDAO();
		PositionDAO positionDAO = model.getPositionDAO();
		PriceDAO priceDAO = model.getPriceDAO();
		
		try {
			// Create the user bean

			CustomerBean customer = new CustomerBean();
			customer.setUsername("customer");
			customer.setPassword("abc");
			customer.setFirstname("First");
			customer.setLastname("Customer");
			customer.setAddrL1("5030 Centre Ave");
			customer.setAddrL2("APT 101");
			customer.setCity("Pittsburgh");
			customer.setState("PA");
			customer.setZip("15213");
			customer.setCash(1000);
			customerDAO.create(customer);
			
			EmployeeBean employee = new EmployeeBean();
			employee.setUsername("admin");
			employee.setPassword("abc");
			employee.setFirstname("First");
			employee.setLastname("Employee");
			
			employeeDAO.createEmp(employee);
			
			FundBean fund = new FundBean();
			
			for (int i=1; i<11; i++){
			fund.setName("Fund No." + i);
			fund.setSymbol("VODLN");
			
			fundDAO.create(fund);
			}
			Random random = new Random();
			
			PositionBean position = new PositionBean();
			for (int i=0; i<10; i++){
				position.setCustomer_id(1);;
				position.setFund_id(i+1);
				position.setShares(((long)random.nextInt(10)+1));
			positionDAO.updatePosition(position);
			}
			
			PriceBean price = new PriceBean();
			
			for (int i=0; i<10; i++){
				price.setFund_id(i);
				Calendar c = new GregorianCalendar();
			    c.set(Calendar.HOUR_OF_DAY, 0); //anything 0 - 23
			    c.set(Calendar.MINUTE, 0);
			    c.set(Calendar.SECOND, 0);
			    for (int j=0; j<10; j++){
					c.add(Calendar.DAY_OF_MONTH, -1);
					price.setPrice_date(c.getTime());
					price.setPrice(((long)random.nextInt(10)+1));
					priceDAO.createPrice(price);
				}
				
			}
			
			
		} catch (RollbackException e) {
			e.printStackTrace();
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nextPage = performTheAction(request);
		sendToNextPage(nextPage, request, response);
	}

	/*
	 * Extracts the requested action and (depending on whether the user is
	 * logged in) perform it (or make the user login).
	 * 
	 * @param request
	 * 
	 * @return the next page (the view)
	 */
	private String performTheAction(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		String servletPath = request.getServletPath();
		CustomerBean customer = (CustomerBean) session.getAttribute("customer");
		EmployeeBean employee = (EmployeeBean) session.getAttribute("employee");
		//FundBean fund = (FundBean) session.getAttribute("fund");
		
		String action = getActionName(servletPath);

		// System.out.println("servletPath="+servletPath+" requestURI="+request.getRequestURI()+"  user="+user);

	//	if (action.equals("cuslogin.do") || action.equals("emplogin.do")) {
		if(action.equals("login.do") || action.equals("research.do") || action.equals("showPerformance.do")) {	
			// Allow these actions without logging in
			return Action.perform(action, request);
		}

		if (customer == null && employee == null) {
			// If the user hasn't logged in, direct him to the login page
			return Action.perform("login.do", request);
		}
		
		if(customer != null) {
			session.setAttribute("employee",null);
		}

		if(employee != null) {
			session.setAttribute("customer",null);
		}

		// Let the logged in user run his chosen action
		return Action.perform(action, request);
	}

	/*
	 * If nextPage is null, send back 404 If nextPage ends with ".do", redirect
	 * to this page. If nextPage ends with ".jsp", dispatch (forward) to the
	 * page (the view) This is the common case
	 */
	private void sendToNextPage(String nextPage, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		if (nextPage == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND,
					request.getServletPath());
			return;
		}

		if (nextPage.endsWith(".do")) {
			response.sendRedirect(nextPage);
			return;
		}

		if (nextPage.endsWith(".jsp")) {
			RequestDispatcher d = request.getRequestDispatcher("WEB-INF/"
					+ nextPage);
			d.forward(request, response);
			return;
		} else {
			response.sendRedirect("http://" + nextPage);
			return;
		}

	}

	/*
	 * Returns the path component after the last slash removing any "extension"
	 * if present.
	 */
	private String getActionName(String path) {
		// We're guaranteed that the path will start with a slash
		int slash = path.lastIndexOf('/');
		return path.substring(slash + 1);
	}
}
