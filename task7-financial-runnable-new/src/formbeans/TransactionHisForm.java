package formbeans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mybeans.form.FormBean;

public class TransactionHisForm{
	private String customerName;
	private String amount;
	private String transactionType;
	private String status;
	private Date executeDate;
	private String fundName;
	private String numShares;
	private String sharePrice;
	private String dollarAmount;
	

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getExecuteDate() {
		return executeDate;
	}

	public void setExecuteDate(Date executeDate) {
		this.executeDate = executeDate;
	}

	public String getFundName() {
		return fundName;
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getNumShares() {
		return numShares;
	}

	public void setNumShares(String numShares) {
		this.numShares = numShares;
	}

	public String getSharePrice() {
		return sharePrice;
	}

	public void setSharePrice(String sharePrice) {
		this.sharePrice = sharePrice;
	}

	public String getDollarAmount() {
		return dollarAmount;
	}

	public void setDollarAmount(String dollarAmount) {
		this.dollarAmount = dollarAmount;
	}


}
