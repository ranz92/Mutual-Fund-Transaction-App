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
		
		List<String> success = new ArrayList<String>();
		request.setAttribute("success",success);
		
		DecimalFormat df = new DecimalFormat("#,##0.00");
		
		
		try {
			CustomerBean customer = (CustomerBean) request.getSession(false).getAttribute("customer");
			SellForm form  = formBeanFactory.create(request);
			request.setAttribute("form", form);
			
			errors.addAll(form.getValidationErrors());
			
			TransactionBean transaction = new TransactionBean();
			transaction.setCustomer_id(customer.getCustomerId());
			transaction.setFund_id(Integer.parseInt(form.getFundId()));
			
			try {
				transaction.setShares(Long.parseLong(form.getShares()));
			} catch(NumberFormatException e) {
				errors.add("Please enter numbers");
			}
//			transaction.setFund_id(form.getIdAsInt());; //should obtain from fund table, which is not established so far. So recorded as 0 temporarily here.
//			transaction.setShares(form.getSharesAsLong());
			

			if (transactionDAO.checkEnoughShare(customer.getCustomerId(), transaction.getFund_id(),positionDAO.read(customer.getCustomerId(),transaction.getFund_id()).getShares(), transaction.getShares())) {
				transactionDAO.createSellTransaction(transaction);
			}
			else errors.add("No enough share");
			
			HttpSession session = request.getSession();
			customer = customerDAO.read(customer.getCustomerId());
			session.setAttribute("customer",customer);
			
			TransactionBean[] trans = transactionDAO.getPendingSell(customer.getCustomerId());
			TransactionBean tran = new TransactionBean();
			
			PositionBean[] positions = positionDAO.getPositions();
			PositionOfUser[] pous = new PositionOfUser[trans.length];
			PositionBean position = new PositionBean();
			
			int id = 0;
			long pendingShare = 0;
			for (int i = 0; i<pous.length; i++){
				PositionOfUser pou = new PositionOfUser();
			//	position = positions[i];
				
				tran = trans[i];
				id = tran.getFund_id();
				
				pou.setId(id);
				pou.setName(fundDAO.read(id).getName());
				pou.setShares(tran.getShares());
				pou.setPrice(priceDAO.getLatestPrice(id));
				
				pous[i] = pou;
				pendingShare = tran.getShares();
			}
			

			session.setAttribute("mSellList", pous);
			session.setAttribute("pendingShare",pendingShare);

			if(errors.size() > 0) {
				return "sellFund.jsp";
			}
			
			success.add("You have sold fund successfully."); 
			
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