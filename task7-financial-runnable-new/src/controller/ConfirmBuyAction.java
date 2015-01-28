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
import databeans.PositionOfUser;
import databeans.TransactionBean;
import model.CustomerDAO;
import model.Model;
import model.TransactionDAO;
import model.FundDAO;
import formbeans.BuyForm;

public class ConfirmBuyAction extends Action {
	private FormBeanFactory<BuyForm> formBeanFactory = FormBeanFactory.getInstance(BuyForm.class);
	private CustomerDAO customerDAO;
	private TransactionDAO transactionDAO;
	private FundDAO fundDAO;
	
	public ConfirmBuyAction(Model model) {
		customerDAO = model.getCustomerDAO();
		transactionDAO = model.getTransactionDAO();
		fundDAO = model.getFundDAO();
	}
	
	public String getName() {
		return "confirmbuy.do";
	}
	
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		List<String> success = new ArrayList<String>();
		request.setAttribute("success",success);
		
		DecimalFormat df = new DecimalFormat("#,##0.00");
		
		try {
			CustomerBean customer = (CustomerBean) request.getSession(false).getAttribute("customer");
			BuyForm form  = formBeanFactory.create(request);
			request.setAttribute("form", form);
			errors.addAll(form.getValidationErrors());
			
//			HttpSession session = request.getSession();
//			session.setAttribute("fundList", fundDAO.getFundList());
			TransactionBean transaction = new TransactionBean();
			transaction.setCustomer_id(customer.getCustomerId());
			transaction.setFund_id(Integer.parseInt(form.getFundId()));
			try {
				transaction.setAmount(Long.parseLong(form.getAmount()));
			} catch(NumberFormatException e) {
				errors.add("Please enter numbers.");
			}
//			transaction.setFund_id(form.getIdAsInt()); //should obtain from fund table, which is not established so far. So recorded as 0 temporarily here.
//			transaction.setAmount(form.getAmountAsLong());
			
			
//			//加一个判断语句：amount<cash
			if (transactionDAO.checkEnoughCash(customer.getCustomerId(), customer.getCash(), transaction.getAmount()))
			transactionDAO.createBuyTransaction(transaction);
			else errors.add("Not enough amount");
//			
//			customerDAO.updateCash(customer.getCustomerId(), 0-form.getAmountAsLong());
			
			HttpSession session = request.getSession();
			customer = customerDAO.read(customer.getCustomerId());
			session.setAttribute("customer",customer);
			
			TransactionBean[] trans = transactionDAO.getPendingBuy(customer.getCustomerId());
			PositionOfUser[] pous = new PositionOfUser[trans.length];
			TransactionBean tran = new TransactionBean();
			int id = 0;
			long pendingAmount = 0;
			FundBean fund = new FundBean();
			for (int i = 0; i<pous.length; i++){
				PositionOfUser pou = new PositionOfUser();
				tran = trans[i];
				id = tran.getFund_id();
				if ((fund=fundDAO.read(id))!=null){
					pou.setName(fund.getName());
					pou.setSymbol(fund.getSymbol());
				}
				else {
					pou.setName("Check Request");
					pou.setName("N/A");
				}
				pou.setAmount(tran.getAmount());
				pous[i] = pou;
				pendingAmount += tran.getAmount();
			}
			String pendingAmountFormat = df.format(pendingAmount);
			String availableAmountFormat = df.format(customer.getCash() - pendingAmount);
			
			session.setAttribute("mFundList", pous);
			session.setAttribute("pendingAmount",pendingAmountFormat);
			session.setAttribute("availableAmount", availableAmountFormat);
			
//			session.setAttribute("pendingAmount", pendingAmount);
//			session.setAttribute("availableAmount", customer.getCash()-pendingAmount);
			
			if(errors.size() > 0) {
				return "buyFund.jsp";
			} else 
				success.add("You have bought fund successfully.");
			
			return "buyFund.jsp";
		} catch(FormBeanException e) {
			errors.add(e.getMessage());
			return "buyFund.jsp";
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "buyFund.jsp";
		
	}
	

}
