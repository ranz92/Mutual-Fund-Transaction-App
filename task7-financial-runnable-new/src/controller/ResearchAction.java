package controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.CustomerBean;
import databeans.FundBean;
import databeans.PriceBean;
import databeans.TransactionBean;
import model.CustomerDAO;
import model.Model;
import model.PriceDAO;
import model.TransactionDAO;
import model.FundDAO;
import formbeans.BuyForm;
import formbeans.ResearchForm;

public class ResearchAction extends Action {
	private FormBeanFactory<ResearchForm> formBeanFactory = FormBeanFactory.getInstance(ResearchForm.class);
	//private CustomerDAO customerDAO;
	private PriceDAO priceDAO;
	private FundDAO fundDAO;
	
	public ResearchAction(Model model) {
		priceDAO = model.getPriceDAO();
		
		fundDAO = model.getFundDAO();
	}
	
	public String getName() {
		return "research.do";
	}
	
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		try {
			
			if(request.getSession().getAttribute("customer") == null) {
				errors.add("Please log in as a employee.");
				return "login.jsp";
			}
			
			//CustomerBean customer = (CustomerBean) request.getSession(false).getAttribute("customer");
			ResearchForm form  = formBeanFactory.create(request);
			request.setAttribute("form", form);
			HttpSession session = request.getSession();
			
			
			session.setAttribute("fundList", fundDAO.getFundList());
			PriceBean price = new PriceBean();
			
			
			price.setFund_id(1);
			Calendar c = new GregorianCalendar();
		    c.set(Calendar.HOUR_OF_DAY, 0); //anything 0 - 23
		    c.set(Calendar.MINUTE, 0);
		    c.set(Calendar.SECOND, 0);
		    
		   
			//session.setAttribute("priceList", priceDAO.getPrice(form.getIdAsInt()));
					
//			TransactionBean transaction = new TransactionBean();
//			transaction.setCustomer_id(customer.getCustomerId());
//			transaction.setFund_id(Integer.parseInt(form.getFundId()));; //should obtain from fund table, which is not established so far. So recorded as 0 temporarily here.
//			transaction.setAmount(form.getAmount());
//			//加一个判断语句：amount<cash
//			transactionDAO.createBuyTransaction(transaction);

			return "research.jsp";
		} catch (FormBeanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "research.jsp" ;
	}
	

}
