//Jiayi Zhu
//jiayiz
//08-600
//Dec 10, 2014

package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class ChangePwdForm extends FormBean {
	private String confirmPassword;
	private String newPassword;
	
	public String getConfirmPassword() { return confirmPassword; }
	public String getNewPassword()     { return newPassword;     }
	
	public void setConfirmPassword(String s) { confirmPassword = s.trim(); }
	public void setNewPassword(String s)     { newPassword     = s.trim(); }

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (newPassword == null || newPassword.length() == 0) {
			errors.add("New Password is required");
		}
		
		if (confirmPassword == null || confirmPassword.length() == 0) {
			errors.add("Confirm Password is required");
		}
		
		if (newPassword.matches(".*[<>\"].*"))
			errors.add("Password may not contain angle brackets or quotes");
		
		if (errors.size() > 0) {
			return errors;
		}
		
		if (!newPassword.equals(confirmPassword)) {
			errors.add("Passwords do not match");
		}

		return errors;
	}
}
