package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.CustomerBean;
import databeans.FundBean;
import databeans.PositionBean;
import databeans.PositionOfUser;
import databeans.TransactionBean;
import model.CustomerDAO;
import model.Model;
import model.PositionDAO;
import model.PriceDAO;
import model.TransactionDAO;
import model.FundDAO;
import formbeans.BuyForm;

public class SellFundAction extends Action {
	private FormBeanFactory<BuyForm> formBeanFactory = FormBeanFactory.getInstance(BuyForm.class);
	//private CustomerDAO customerDAO;
	//private TransactionDAO transactionDAO;
	private FundDAO fundDAO;
	private PositionDAO positionDAO;
	private PriceDAO priceDAO;
	public SellFundAction(Model model) {
//		customerDAO = model.getCustomerDAO();
//		transactionDAO = model.getTransactionDAO();
		fundDAO = model.getFundDAO();
		positionDAO = model.getPositionDAO();
		priceDAO = model.getPriceDAO();
	}
	
	public String getName() {
		return "sellfund.do";
	}
	
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		try {
			CustomerBean customer = (CustomerBean) request.getSession(false).getAttribute("customer");
//			BuyForm form  = formBeanFactory.create(request);
//			request.setAttribute("form", form);
			HttpSession session = request.getSession();
			PositionBean[] positions = positionDAO.getPositions();
			PositionOfUser[] pous = new PositionOfUser[positions.length];
			PositionBean position = new PositionBean();
			int id = 0;
			for (int i = 0; i<pous.length; i++){
				PositionOfUser pou = new PositionOfUser();
				position = positions[i];
				id = position.getFund_id();
				pou.setId(id);
				if (fundDAO.read(id)!=null)
				pou.setName(fundDAO.read(id).getName());
				pou.setShares(position.getShares());
				pou.setPrice(priceDAO.getLatestPrice(id));
				pous[i] = pou;
			}
			
			session.setAttribute("posList", pous);
//			TransactionBean transaction = new TransactionBean();
//			transaction.setCustomer_id(customer.getCustomerId());
//			transaction.setFund_id(Integer.parseInt(form.getFundId()));; //should obtain from fund table, which is not established so far. So recorded as 0 temporarily here.
//			transaction.setAmount(form.getAmount());
//			//加一个判断语句：amount<cash
//			transactionDAO.createBuyTransaction(transaction);

			return "sellFund.jsp";
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "sellFund.jsp" ;
	}
	

}
