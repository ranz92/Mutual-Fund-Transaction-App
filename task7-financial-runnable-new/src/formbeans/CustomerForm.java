package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class CustomerForm extends FormBean{
    private String username;
    
    public String getUsername()           { return username;     }
    
    public void setUsername(String username) { this.username = username; }
    
    public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (username == null) {
			errors.add("Customer should be specified!");
		}
		
		return errors;
	}
}
