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
		HttpSession session = request.getSession();

		
		List<String> success = new ArrayList<String>();
		request.setAttribute("success",success);
		
		DecimalFormat df = new DecimalFormat("#,##0.00");
		
		try {
			if (session.getAttribute("employee") != null){
		        session.setAttribute("employee",null);
			}
			if(session.getAttribute("customer") == null) {
				errors.add("Please log in as a customer.");
				return "login.jsp";
			}
			
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
			//	transaction.setAmount((long)Double.parseDouble(form.getAmount()));
				//System.out.println(form.getAmount());
			//	System.out.println(Double.valueOf(form.getAmount()));
//				Double a = Double.valueOf(form.getAmount());
//				Double a1 = a*1000;
//				long l = (new Double(a1)).longValue();
//				transaction.setAmount(l);
			//	System.out.println((long)Double.parseDouble(form.getAmount()));
			//	System.out.println(l);
			} catch(NumberFormatException e) {
			//	System.out.println(e.getStackTrace());
				errors.add("Please enter numbers.");
			//	System.out.println(request.getAttribute("errors"));
			}
//			transaction.setFund_id(form.getIdAsInt()); //should obtain from fund table, which is not established so far. So recorded as 0 temporarily here.
//			transaction.setAmount(form.getAmountAsLong());
			
			
//			//åŠ ä¸€ä¸ªåˆ¤æ–­è¯­å�¥ï¼šamount<cash
			if (transactionDAO.checkEnoughCash(customer.getCustomerId(), customer.getCash(), transaction.getAmount()))
			transactionDAO.createBuyTransaction(transaction);
			else errors.add("Not enough amount");
//			
//			customerDAO.updateCash(customer.getCustomerId(), 0-form.getAmountAsLong());
			
			customer = customerDAO.read(customer.getCustomerId());
			session.setAttribute("customer",customer);
			
			TransactionBean[] trans = transactionDAO.getPendingBuy(customer.getCustomerId());
			PositionOfUser[] pous = new PositionOfUser[trans.length];
			TransactionBean tran = new TransactionBean();
			int id = 0;
			
		//	Double pend = null;
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

		//		pend = (double)(pou.getAmount()/1000);
				pous[i] = pou;
				
				pendingAmount += tran.getAmount();
		//		pend += pend;
		//		Double pend1 = pend*1000;
				
		//		pendingAmount = (new Double(pend1)).longValue();
			//	System.out.println(pendingAmount);
//				Double avail = (double) ((customer.getCash()-tran.getAmount())/1000);
//				System.out.println(avail);
		//		System.out.println(pendingAmount);
		//		System.out.println(pend);
		//		pendingAmountD = (double)pendingAmount/1000;
				
			}
			String pendingAmountFormat = df.format(pendingAmount);
		//	String pendingAmountFormat1 = df.format(pendingAmount/1000);	
		//	System.out.println(pendingAmountD);
			String availableAmountFormat = df.format(customer.getCash() - pendingAmount);
		//	String pendingAmountFormat = df.format(pend);
		//	String availableAmountFormat = df.format(customer.getCash() - pend);
		//	System.out.println(availableAmountFormat);
			
//			Double avail = (double) (customer.getCash()-(tran.getAmount()/1000));
//			System.out.println(avail);
		//	System.out.println(pendingAmount);
			
			session.setAttribute("mFundList", pous);
			session.setAttribute("pendingAmount",pendingAmountFormat);
//			session.setAttribute("pendingAmount",pendingAmountFormat1);
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
