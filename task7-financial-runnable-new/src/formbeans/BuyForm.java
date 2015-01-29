package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class BuyForm extends FormBean {
	private String fundId;
	private String amount;
	
	public String getFundId() { return fundId; }
	public String getAmount() { return amount; }
	
	public void setFundId(String s) {fundId = s;}
	public void setAmount(String l) { amount = l;}
//	public int getIdAsInt() {
//		try {
//			return Integer.parseInt(fundId);
//		} catch (NumberFormatException e) {
//			// call getValidationErrors() to detect this
//			return -1;
//		}
//	}
//	public long getAmountAsLong() {
//		try {
//			return Long.parseLong(amount);
//		} catch (NumberFormatException e) {
//			// call getValidationErrors() to detect this
//			return -1;
//		}
//	}
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		
		
		if(amount == null || amount.length() == 0) {
			errors.add("Please enter an amount.");
		} 
		
		/*
		if (amount != null && amount.matches(".*[<>\"?~#%&].*"))
			errors.add("Special mark are not allowed. Please enter numbers for amount.");
		*/
//		try {
//			Long.parseLong(amount);
//		} catch (NumberFormatException e) {
//			errors.add("Amount cannot be parsed");
//		}
		try {
			Integer.parseInt(amount);
		} catch (NumberFormatException e) {
			errors.add("Amount is not an integer");
		}
		
		try {

			double amt = Double.parseDouble(amount);
			amt = Math.round(amt * 100);
			amt = amt / 100;

			if (amt > 1000000000) {
				errors.add("Please enter an amount that is less than $1000000000");
			}
		} catch (NumberFormatException nfe) {
			errors.add("Please enter numbers for amount.");
		}
		
		return errors;
	}

}
