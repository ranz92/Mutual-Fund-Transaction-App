package controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import databeans.CustomerBean;
import databeans.PositionBean;
import databeans.TransactionBean;
import model.Model;
import model.PositionDAO;
import model.PriceDAO;
import model.TransactionDAO;

public class ViewByCustomerAction extends Action {
	private TransactionDAO transacDAO;
	private PositionDAO posDAO;
	private PriceDAO priceDAO;

	
	public ViewByCustomerAction(Model model) {
		transacDAO = model.getTransactionDAO();
		posDAO = model.getPositionDAO();
        priceDAO = model.getPriceDAO();
	}
	
	public String getName() {
		return "viewCustomer.do";
	}
	
	public String perform(HttpServletRequest request) {
		CustomerBean customer = (CustomerBean)request.getSession(false).getAttribute("customer");
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		try {
			if(customer == null) {
				return "login.do";
			} else {
				//DecimalFormat df = new DecimalFormat("#,###.00");

				int cusId = customer.getCustomerId();
				TransactionBean[] transactions = transacDAO.getTransactions(cusId);
				
				request.setAttribute("customer", customer);
				if(transactions.length!=0){
					
					request.setAttribute("transaction", transactions[transactions.length-1]); //Return the last trading day.

				}
				//String cash = df.format(customer.getCash());
				//request.setAttribute("cash",cash);
				//System.out.println(cash);
				System.out.println(customer.getZip());


				//Return the fund information.
				PositionBean[] positions = posDAO.getPositions(cusId);
				List<Double> priceList = new ArrayList<Double>();
				for (int i = 0; i<positions.length; i++) {
					int fund_id = positions[i].getFund_id();
					long price = priceDAO.getLatestPrice(fund_id);
					double totalPrice = price * positions[i].getShares();
					
					priceList.add(totalPrice);
				}
				request.setAttribute("positions", positions);
				request.setAttribute("priceList", priceList);
	            return "viewAccountByCus.jsp";	
	            
	            } 
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		}
	}

}
