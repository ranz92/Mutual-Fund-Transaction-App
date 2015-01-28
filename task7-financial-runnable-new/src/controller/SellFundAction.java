package controller;

import java.text.DecimalFormat;
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
	private TransactionDAO transactionDAO;
	private FundDAO fundDAO;
	private PositionDAO positionDAO;
	private PriceDAO priceDAO;
	public SellFundAction(Model model) {
		transactionDAO = model.getTransactionDAO();
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
		
		DecimalFormat df = new DecimalFormat("#,##0.00");
		
		try {
			if(request.getSession().getAttribute("customer") == null) {
				errors.add("Please log in as a customer.");
				return "login.jsp";
			}
			
			CustomerBean customer = (CustomerBean) request.getSession(false).getAttribute("customer");
			
			HttpSession session = request.getSession();
			session.setAttribute("ownList", positionDAO.getPositions());
			
			PositionBean[] positions = positionDAO.getPositions();
			
			TransactionBean[] trans = transactionDAO.getPendingSell(customer.getCustomerId());
			TransactionBean tran = new TransactionBean();
			PositionOfUser[] pous = new PositionOfUser[trans.length];
			PositionBean position = new PositionBean();
			
			int id = 0;
			long pendingShare = 0;
			
			
			for (int i = 0; i<pous.length; i++){
				PositionOfUser pou = new PositionOfUser();
		//		position = positions[i];
				
				tran = trans[i];
				id = tran.getFund_id();
				
				pou.setName(fundDAO.read(id).getName());
				pou.setPrice(priceDAO.getLatestPrice(id));
				pou.setShares(tran.getShares());
				pou.setId(id);
				pendingShare = tran.getShares();
				
				pous[i] = pou;
				
			}

			
			session.setAttribute("mSellList", pous);
			session.setAttribute("pendingShare", pendingShare);

			return "sellFund.jsp";
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "sellFund.jsp" ;
	}
	

}