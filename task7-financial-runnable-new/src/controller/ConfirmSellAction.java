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
		HttpSession session = request.getSession();
		
		List<String> success = new ArrayList<String>();
		request.setAttribute("success",success);
		
		//DecimalFormat df = new DecimalFormat("#,##0.00");
		
		
		try {
			if (session.getAttribute("employee") != null){
		        session.setAttribute("employee",null);
			}
			if(request.getSession().getAttribute("customer") == null) {
				errors.add("Please log in as a customer.");
				return "login.jsp";
			}
			
			CustomerBean customer = (CustomerBean) request.getSession(false).getAttribute("customer");
			SellForm form  = formBeanFactory.create(request);
			request.setAttribute("form", form);
			
			errors.addAll(form.getValidationErrors());
			
			TransactionBean transaction = new TransactionBean();
			transaction.setCustomer_id(customer.getCustomerId());
			transaction.setFund_id(Integer.parseInt(form.getFundId()));
			
			try {
		//		transaction.setShares(Long.parseLong(form.getShares()));
				double a = Double.valueOf(form.getShares());
				double a1 = a*1000;
				long l = (new Double(a1)).longValue();
				transaction.setShares(l);
				
			} catch(NumberFormatException e) {
	//			errors.add("Please enter numbers");
				e.printStackTrace();
			}
			

//			if (transactionDAO.checkEnoughShare(customer.getCustomerId(), transaction.getFund_id(),positionDAO.read(customer.getCustomerId(),transaction.getFund_id()).getShares(), transaction.getShares())) {
//				transactionDAO.createSellTransaction(transaction);
//			}
//			else errors.add("No enough share");
			
			if(!transactionDAO.checkEnoughShare(customer.getCustomerId(), transaction.getFund_id(),positionDAO.read(customer.getCustomerId(),transaction.getFund_id()).getShares(), transaction.getShares()))
				errors.add("No enough share");
			
			if(errors.size() > 0) {
				return "sellFund.jsp";
			}

			transactionDAO.createSellTransaction(transaction);

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
			success.add("You have sold fund successfully."); 
			form.setShares("");
			
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