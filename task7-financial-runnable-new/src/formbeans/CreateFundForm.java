package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class CreateFundForm extends FormBean {
	private String fundName;
	private String ticker;
	
	public String getFundName() { return fundName; }
	public String getTicker() { return ticker; }
	
	public void setFundName(String s) {
		
		fundName = s.replaceAll("<(/?\\S+)\\s*?[^<]*?(/?)>","<$1$2>");
		}
	public void setTicker(String l) { 
		
		ticker = l.replaceAll("<(/?\\S+)\\s*?[^<]*?(/?)>","<$1$2>");}
	
	

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		
		
		if((fundName == null) || (fundName.length() ==0) ) {
			errors.add("Fund name is required");
		}
		
		if((ticker == null) || (ticker.length() == 0 )) {
			errors.add("Ticker is required");
		}
		if (ticker.trim().length() < 1 || ticker.trim().length() > 5){
			errors.add("Ticker length must be between 1 and 5");
		}
		
		if (fundName.matches(".*[<>&?*\"].*"))
	            errors.add("Fund Name may not contain special characters");
	    if (ticker.matches(".*[<>?&*\"].*"))
	            errors.add("Ticker may not contain special characters");
	    
    	
		return errors;
	}

}
