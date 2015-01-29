package controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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

public class showPerformanceAction extends Action {
	private FormBeanFactory<ResearchForm> formBeanFactory = FormBeanFactory.getInstance(ResearchForm.class);
	private CustomerDAO customerDAO;
	private PriceDAO priceDAO;
	private FundDAO fundDAO;
	
	public showPerformanceAction(Model model) {
		customerDAO = model.getCustomerDAO();
		priceDAO = model.getPriceDAO();
		fundDAO = model.getFundDAO();
	}
	
	public String getName() {
		return "showPerformance.do";
	}
	
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		try {
			
			ResearchForm form  = formBeanFactory.create(request);
			request.setAttribute("form", form);
//			HttpSession session = request.getSession();
//			session.setAttribute("fundList", fundDAO.getFundList());
		
			HttpSession session = request.getSession();
			PriceBean[] prices = priceDAO.getPrice(form.getIdAsInt());
			
			List<String> d = new ArrayList<String>();
			List<String> in = new ArrayList<String>();
			//List<Long> priceList = new ArrayList<Long>();
			SimpleDateFormat time=new SimpleDateFormat("MM/dd"); 
			DecimalFormat df1 = new DecimalFormat("###0.00");
			double max = 0;
			double tmpP = 0;
			for(PriceBean p:prices) {
				//priceList.add(p.getPrice()/100);
				tmpP = p.getPrice()/100.00;
				d.add(time.format(p.getPrice_date()));
				in.add(df1.format(tmpP));
				if(tmpP>max) {
					max = tmpP;
				}
			}
			
			session.setAttribute("priceList",prices);
			session.setAttribute("dateList",d);
			session.setAttribute("costList",in);
			session.setAttribute("maxPrice",max);
			return "showPerformance.jsp";
		} catch(FormBeanException e) {
			errors.add(e.getMessage());
			return "showPerformance.jsp";
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "showPerformance.jsp";
		
	}
	

}
