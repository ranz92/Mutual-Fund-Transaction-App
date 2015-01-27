package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class DepositCheckForm extends FormBean {
	
	private String userName;
	private String fullName;
	private String amount;
	private String notes;
	private String executedBy;
	private String executedOn;
	private String remarks;
	
	public String getUserName()		{ return userName;  	}
	public String getFullName()  	{ return fullName;  	}
	public String getAmount()  		{ return amount;    	}
	public String getNotes()  		{ return notes;  		}
	public String getExecutedBy() 	{ return executedBy;	}
	public String getExecutedOn() 	{ return executedOn;	}
	public String getRemarks() 		{ return remarks;		}

	
	public void setUserName(String s) 	{ userName = trimAndConvert(s,"<>\"");  	}
	public void setFullName(String s) 	{ fullName  = trimAndConvert(s,"<>\""); 	}
	public void setAmount(String s)     { amount  = trimAndConvert(s,"<>\"");   	}
	public void setNotes(String s)  	{ notes  = trimAndConvert(s,"<>\"");   		}
	public void setExecutedBy(String s)	{ executedBy = trimAndConvert(s,"<>\"");	}
	public void setExecutedOn(String s)	{ executedOn = trimAndConvert(s,"<>\"");	}
	public void setRemarks(String s)	{ remarks = trimAndConvert(s,"<>\"");	}
	

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
		
		if (executedBy == null || executedBy.length() == 0) {
			errors.add("Employee name is required");
		}

		if (executedOn == null || executedOn.length() == 0) {
			errors.add("Date executed is required");
		}
		
		if (errors.size() > 0) {
			return errors;
		}

		return errors;
	}
}

