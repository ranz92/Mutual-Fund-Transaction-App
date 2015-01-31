//Jiayi Zhu
//jiayiz
//08-600
//Dec 10, 2014

package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class CusRegisterForm extends FormBean {
	private String username;
	private String firstName;
	private String lastName;
	//private String email;
	private String password;
	private String confirm ;
	private String addrL1;
	private String addrL2;
	private String city;
	private String state;
	private String zip;
	private String cash;
	
	public String getFirstName() { return firstName; }
	public String getLastName()  { return lastName;  }
	//public String getEmail()  { return email;  }
	public String getPassword()  { return password;  }
	public String getConfirm()   { return confirm;   }
	public String getUsername()  { return username;  }
	public String getAddrL1()     { return addrL1;  }
	public String getAddrL2()     { return addrL2;  }
	public String getCity()     { return city;  }
	public String getState()     { return state;  }
	public String getZip()     { return zip;  }
	public String getCash()     { return cash;  }

	
	public void setUsername(String s)  { username = s.trim();                   }
	public void setFirstName(String s) { firstName = trimAndConvert(s,"<>\"");  }
	public void setLastName(String s)  { lastName  = trimAndConvert(s,"<>\"");  }
	//public void setEmail(String s)  { email  = trimAndConvert(s,"<>\"");  }
	public void setPassword(String s)  { password  = s.trim();                  }
	public void setConfirm(String s)   { confirm   = s.trim();                  }
	public void setAddrL1(String s)     { addrL1   = s.trim();               }
	public void setAddrL2(String s)     { addrL2   = s.trim();               }
	public void setCity(String s)      { city   = s.trim();                     }
	public void setState(String s)     { state   = s.trim();                    }
	public void setZip(String s)       { zip   = s.trim();    }
	public void setCash(String s)      { confirm   = s.trim();                  }

	
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
		
		
		
		if (errors.size() > 0) {
			return errors;
		}
		
		if (username.matches(".*[<>!@#$%^&*()-+=<>,/?`~\"?~#%&].*"))
			errors.add("User Name may not contain special characters");
		if (firstName.matches(".*[<>!@#$%^&*()-+=_<>,/?`~\"?~#%&].*"))
			errors.add("First Name may not contain special characters");
		if (lastName.matches(".*[<>!@#$%^&*()-+=_<>,/?`~\"?~#%&].*"))
			errors.add("Last Name may not contain special characters");
		if (password.matches(".*[<>!@#$%^&*()-+=_<>,/?`~\"?~#%&].*"))
			errors.add("Password may not contain special characters");
		if ((addrL1 != null && addrL1.matches(".*[<>!@#$%^&*()-+=_<>,/?`~\"?~#%&].*")) || (addrL2 != null && addrL2.matches(".*[<>\"?].*"))  )
			errors.add("Address may not contain special characters");
		if (city != null && city.matches(".*[<>!@#$%^&*()-+=_<>,/?`~\"?~#%&].*"))
			errors.add("City may not contain special characters");
		if (state != null && state.matches(".*[<>!@#$%^&*()-+=_<>,/?`~\"?~#%&].*"))
			errors.add("State may not contain special characters");
		if (zip != null && zip.matches(".*[<>!@#$%^&*()+=_<>,/?`~\"?~#%&].*"))
			errors.add("Zip Code may not contain special characters");
		
		if (!password.equals(confirm)) {
			errors.add("Passwords are not the same");
		}
		
		return errors;
	}
}
