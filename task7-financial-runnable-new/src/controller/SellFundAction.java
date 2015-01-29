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
		HttpSession session = request.getSession();
		
		DecimalFormat df = new DecimalFormat("#,##0.00");
		
		try {
			if (session.getAttribute("employee") != null){
		        session.setAttribute("employee",null);
			}
			if(request.getSession().getAttribute("customer") == null) {
				errors.add("Please log in as a customer.");
				return "login.jsp";
			}
			
			CustomerBean customer = (CustomerBean) request.getSession(false).getAttribute("customer");
			
			session = request.getSession();
			PositionBean[] pbs = positionDAO.getPositions(customer.getCustomerId());
			PositionOfUser[] ownList = new PositionOfUser[pbs.length];
			int id = 0;
			for (int i=0; i<ownList.length; i++){
				id = pbs[i].getFund_id();
				ownList[i] = new PositionOfUser();
				ownList[i].setId(id);
				ownList[i].setName(fundDAO.read(id).getName());
				ownList[i].setSymbol(fundDAO.read(id).getSymbol());
				ownList[i].setShares(pbs[i].getShares()/1000.000);
				ownList[i].setAmount(transactionDAO.getPendingSellEachFund(customer.getCustomerId(), pbs[i].getFund_id()));
			}
			session.setAttribute("ownList", ownList);
			TransactionBean[] trans = transactionDAO.getPendingSell(customer.getCustomerId());
			TransactionBean tran = new TransactionBean();
			PositionOfUser[] pous = new PositionOfUser[trans.length];
			for (int i = 0; i<pous.length; i++){
				PositionOfUser pou = new PositionOfUser();
				tran = trans[i];
				id = tran.getFund_id();
				pou.setName(fundDAO.read(id).getName());
				pou.setSymbol(fundDAO.read(id).getSymbol());
				pou.setShares(tran.getShares()/1000.000);
				pous[i] = pou;				
			}
			session.setAttribute("mSellList", pous);

			return "sellFund.jsp";
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "sellFund.jsp" ;
	}
	

}