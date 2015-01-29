package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class EmpRegisterForm extends FormBean{
	private String username;
	private String firstName;
	private String lastName;
	private String password;
	private String confirm ;
	
	public String getFirstName() { return firstName; }
	public String getLastName()  { return lastName;  }
	public String getPassword()  { return password;  }
	public String getConfirm()   { return confirm;   }
	public String getUsername()  { return username;  }
	
	public void setUsername(String s)  { username = s.trim();                   }
	public void setFirstName(String s) { firstName = trimAndConvert(s,"<>\"");  }
	public void setLastName(String s)  { lastName  = trimAndConvert(s,"<>\"");  }
	public void setPassword(String s)  { password  = s.trim();                  }
	public void setConfirm(String s)   { confirm   = s.trim();                  }
	
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		
		if (username == null || username.length() == 0) {
			errors.add("Username is required");
		}
		
		if (firstName == null || firstName.length() == 0) {
			errors.add("First Name is required");
		}

		if (lastName == null || lastName.length() == 0) {
			errors.add("Last Name is required");
		}

		if (password == null || password.length() == 0) {
			errors.add("Password is required");
		}

		if (confirm == null || confirm.length() == 0) {
			errors.add("Confirm Password is required");
		}
		
		if (username.matches(".*[<>*?/\"?].*"))
			errors.add("User Name may not contain angle brackets, quotes or question mark");
		if (firstName.matches(".*[<>*?/\"?].*"))
			errors.add("First Name may not contain angle brackets, quotes or question mark");
		if (lastName.matches(".*[<>*?/\"?].*"))
			errors.add("Last Name may not contain angle brackets, quotes or question mark");
		if (password.matches(".*[<>*?/\"?].*"))
			errors.add("Password may not contain angle brackets, quotes or question mark");
		
		if (!password.equals(confirm)) {
			errors.add("Passwords are not the same");
		}
		
		if (errors.size() > 0) {
			return errors;
		}
	
		return errors;
	}
}
