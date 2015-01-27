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
	public int getIdAsInt() {
		try {
			return Integer.parseInt(fundId);
		} catch (NumberFormatException e) {
			// call getValidationErrors() to detect this
			return -1;
		}
	}
	public long getAmountAsLong() {
		try {
			return Long.parseLong(amount);
		} catch (NumberFormatException e) {
			// call getValidationErrors() to detect this
			return -1;
		}
	}
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		
		
		if(amount == null) {
			errors.add("Amount is required");
		}
		
		try {
			Long.parseLong(amount);
		} catch (NumberFormatException e) {
			errors.add("Amount cannot be parsed");
		}
		
		return errors;
	}

}
