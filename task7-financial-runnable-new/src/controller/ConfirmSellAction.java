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
import formbeans.SellForm;

public class ConfirmSellAction extends Action {
	private FormBeanFactory<SellForm> formBeanFactory = FormBeanFactory.getInstance(SellForm.class);
	private CustomerDAO customerDAO;
	private TransactionDAO transactionDAO;
	private FundDAO fundDAO;
	private PositionDAO positionDAO;
	private PriceDAO priceDAO;
	public ConfirmSellAction(Model model) {
		customerDAO = model.getCustomerDAO();
		transactionDAO = model.getTransactionDAO();
		fundDAO = model.getFundDAO();
		positionDAO = model.getPositionDAO();
		priceDAO = model.getPriceDAO();
	}
	
	public String getName() {
		return "confirmsell.do";
	}
	
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		try {
			CustomerBean customer = (CustomerBean) request.getSession(false).getAttribute("customer");
			SellForm form  = formBeanFactory.create(request);
			request.setAttribute("form", form);
//			HttpSession session = request.getSession();
//			session.setAttribute("fundList", fundDAO.getFundList());
			TransactionBean transaction = new TransactionBean();
			transaction.setCustomer_id(customer.getCustomerId());
			transaction.setFund_id(form.getIdAsInt());; //should obtain from fund table, which is not established so far. So recorded as 0 temporarily here.
			transaction.setShares(form.getAmountAsLong());
			//加一个判断语句：amount<cash
			if (transactionDAO.checkEnoughShare(customer.getCustomerId(), transaction.getFund_id(),positionDAO.read(customer.getCustomerId(),transaction.getFund_id()).getShares(), transaction.getShares()))
			transactionDAO.createSellTransaction(transaction);
//			
			//customerDAO.updateCash(customer.getCustomerId(), 0-form.getAmountAsLong());
//			PositionBean newPosition = new PositionBean();
//			newPosition.setFund_id(form.getIdAsInt());
//			newPosition.setCustomer_id(customer.getCustomerId());
//			newPosition.setShares(0-form.getAmountAsLong());
//			System.out.print(0-form.getSharesAsLong());
//			positionDAO.updatePosition(newPosition);
			
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
				pou.setName(fundDAO.read(id).getName());
				pou.setShares(position.getShares());
				pou.setPrice(priceDAO.getLatestPrice(id));
				pous[i] = pou;
			}
			
			session.setAttribute("posList", pous);
			return "sellFund.jsp";
		} catch(FormBeanException e) {
			errors.add(e.getMessage());
			return "sellFund.jsp";
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "sellFund.jsp";
		
	}
	

}
