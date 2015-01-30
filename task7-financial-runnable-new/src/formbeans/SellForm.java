package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class SellForm extends FormBean {
	private String fundId;
	private String shares;
//	private String amount;
	
	public String getFundId() { return fundId; }
	public String getShares() { return shares; }
//	public String getAmount() { return amount; }
	
	public void setFundId(String s) {fundId = s;}
//	public void setAmount(String l) { amount = l;}
	public void setShares(String l) { shares = l;}

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
//	public long getSharesAsLong() {
//		try {
//			return Long.parseLong(shares);
//		} catch (NumberFormatException e) {
//			// call getValidationErrors() to detect this
//			return -1;
//		}
//	}
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		
		
//		if(shares == null) {
//			errors.add("Share is required");
//		}
//		
//		try {
//			Long.parseLong(shares);
//		} catch (NumberFormatException e) {
//			errors.add("Share cannot be parsed");
//		}
		
		
		if(shares == null || shares.length() == 0) {
			errors.add("Please enter shares to sell.");
		}
		
		if (shares != null && shares.matches(".*[<>!@#$%^&*()-+=_<>,/?`~\"?~#%&].*"))
			errors.add("Special characters are not allowed. Please enter numbers for share.");
		
		try {
        	double d = Double.parseDouble(shares);
        	if (d <= 0) {
        		errors.add("Shares should be a non-negative number");        	}
        } catch (NumberFormatException e) {
        	 errors.add("Invalide number, please input a number for the share.");
        }
		
//		if (shares != null && shares.matches(".*[<>\"].*"))
//			errors.add("Please enter numbers for shares.");
		
		
		return errors;
	}

}
