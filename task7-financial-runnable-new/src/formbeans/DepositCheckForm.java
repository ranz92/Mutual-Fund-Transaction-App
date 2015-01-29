package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class DepositCheckForm extends FormBean {
	
	private String userName;
	private String fullName;
	private String amount;
	private String notes;
	private String fundId;
	
	public String getUserName()		{ return userName;  	}
	public String getFullName()  	{ return fullName;  	}
	public String getAmount()  		{ return amount;    	}
	public String getNotes()  		{ return notes;  		}
	public String getFundId() 		{ return fundId; }
	
	public void setUserName(String s) 	{ userName = trimAndConvert(s,"<>\"");  	}
	public void setFullName(String s) 	{ fullName  = trimAndConvert(s,"<>\""); 	}
	public void setAmount(String s)     { amount  = trimAndConvert(s,"<>\"");   	}
	public void setNotes(String s)  	{ notes  = trimAndConvert(s,"<>\"");   		}
	public void setFundId(String s) 	{ fundId = s;}

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

		if (userName == null || userName.length() == 0) {
			errors.add("User name is required");
		}

		if (fullName == null || fullName.length() == 0) {
			errors.add("User's Full name is required");
		}

		if (amount == null || amount.length() == 0) {
			errors.add("Amount is required");
		}
		
		try {
			Integer.parseInt(amount);
		} catch (NumberFormatException e) {
			errors.add("Amount is not an integer");
		}
		
		if (errors.size() > 0) {
			return errors;
		}

		return errors;
	}
}

