package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class DepositCheckForm extends FormBean {
	
	private String amount;
	private String customerId;
	
	public String getAmount()  		{ return amount;    	}
	public String getCustomerId() 		{ return customerId; }
	
	public void setAmount(String s)     { amount  = trimAndConvert(s,"<>\"");   	}
	public void setCustomerId(String s) 	{ customerId = s;}

	public int getIdAsInt() {
		try {
			return Integer.parseInt(customerId);
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

		if (amount == null || amount.length() == 0) {
			errors.add("Amount is required");
		}
		
		if (errors.size() > 0) {
			return errors;
		}

		return errors;
	}
}

