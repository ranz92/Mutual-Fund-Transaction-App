package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class ResearchForm extends FormBean {
	private String fundId;
	
	public String getFundId() { return fundId; }
	
	public void setFundId(String s) {fundId = s;}
	public int getIdAsInt() {
		try {
			return Integer.parseInt(fundId);
		} catch (NumberFormatException e) {
			// call getValidationErrors() to detect this
			return -1;
		}
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
	
		return errors;
	}

}
