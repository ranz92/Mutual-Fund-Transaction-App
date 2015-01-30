package controller;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import javax.servlet.http.HttpServletRequest;
import org.genericdao.RollbackException;
import databeans.CustomerBean;
import databeans.EmployeeBean;
import databeans.FundBean;
import databeans.PriceBean;
import databeans.TransactionBean;
import model.CustomerDAO;
import model.Model;
import model.PriceDAO;
import model.TransactionDAO;
import model.FundDAO;
import formbeans.TransactionHisForm;

public class TransactionHistoryCusAction extends Action {
	private FundDAO fundDAO;
	private PriceDAO priceDAO;
	private TransactionDAO transactionDAO;
	private CustomerDAO customerDAO;

	public TransactionHistoryCusAction(Model model) {
		fundDAO = model.getFundDAO();
		priceDAO = model.getPriceDAO();
		transactionDAO = model.getTransactionDAO();
		customerDAO = model.getCustomerDAO();
		model.getPositionDAO();
	}

	public String getName() {
		return "transactionHistoryCus.do";
	}

	public String perform(HttpServletRequest request) {
		// if(request.getSession().getAttribute("customer") == null) {
		// errors.add("Please log in as a customer.");
		// return "login.jsp";
		// }

		EmployeeBean admin = (EmployeeBean) request.getSession(false)
				.getAttribute("employee");
		CustomerBean customer = (CustomerBean) request.getSession(false)
				.getAttribute("customer");
		DecimalFormat dfAmount = new DecimalFormat("###,###,##0.00");
		DecimalFormat dfShare = new DecimalFormat("###,###,##0.000");
		DecimalFormat dfPrice = new DecimalFormat("###,###,##0.00");
		try {
			TransactionBean[] allTransactions = null;

			if (admin != null)
				allTransactions = transactionDAO.getAllTransactions();
			else if (customer != null) {
				int userId = customer.getCustomerId();
				allTransactions = transactionDAO.getTransactions(userId);
			}

			Arrays.sort(allTransactions,
					Collections.reverseOrder(new Comparator<TransactionBean>() {

						@Override
						public int compare(TransactionBean t1,TransactionBean t2) {
							if (t1.getExecute_date() == null && t2.getExecute_date() != null)
								return 1;
							if (t1.getExecute_date() != null && t2.getExecute_date() == null)
								return -1;
							if (t1.getExecute_date() != null && t2.getExecute_date() != null)
								return t1.getExecute_date().compareTo(t2.getExecute_date());
							return 0;
						}

					}));

			TransactionHisForm[] histories = new TransactionHisForm[allTransactions.length];
			for (int i = 0; i < histories.length; i++) {
				CustomerBean customerTemp = customerDAO
						.getCustomer(allTransactions[i].getCustomer_id());
				histories[i] = new TransactionHisForm();
				histories[i].setCustomerName(customerTemp.getFirstname() + " " + customerTemp.getLastname());
				histories[i].setExecuteDate(allTransactions[i].getExecute_date());
				histories[i].setAmount(dfAmount.format(allTransactions[i].getAmount() / 100.0));
				if (allTransactions[i].getExecute_date() == null)
					histories[i].setStatus("Pending");
				else
					histories[i].setStatus("Completed");
				switch (allTransactions[i].getTransaction_type()) {
				case (0):
					histories[i].setTransactionType("Buy Fund");
					break;
				case (1):
					histories[i].setTransactionType("Sell Fund");
					break;
				case (2):
					histories[i].setTransactionType("Request Check");
					break;
				case (3):
					histories[i].setTransactionType("Deposit Check");
				}

				// If this is fund transaction
				FundBean fund = new FundBean();
				int fundId = allTransactions[i].getFund_id();
				if ((fund = fundDAO.read(fundId)) != null) {
					histories[i].setFundName(fund.getName());
					if (allTransactions[i].getExecute_date() != null) {
						PriceBean[] prices = priceDAO.getPrice(fundId);
						long thePrice = -1;
						for (PriceBean price : prices) {
							if (price.getPrice_date().equals(allTransactions[i].getExecute_date())) {
								thePrice = price.getPrice();
								break;
							}
						}
						histories[i].setSharePrice(dfPrice.format(thePrice / 100.0));
						if (histories[i].getTransactionType()
								.equals("Buy Fund")) {
							histories[i].setNumShares(dfShare.format((double) (allTransactions[i].getAmount()/100.0)/(thePrice/100.0)));
						} else if (histories[i].getTransactionType().equals("Sell Fund")) {
							histories[i].setNumShares(dfShare.format((double) allTransactions[i].getShares()/1000.0));
						} else {
							histories[i].setNumShares("");
						}

//						System.out.println("numofShares for transaction[" + i + "] is:" + histories[i].getNumShares());
					}
				} else {

				}

			}
			request.setAttribute("transactions", histories);
			// System.out.println("--------------->"+allTransactions.length);
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (admin != null) {
			return "transactionHistoryEmp.jsp";
		} else if (customer != null) {
			return "transactionHistoryCus.jsp";
		}
		return "login.do";
	}

}
