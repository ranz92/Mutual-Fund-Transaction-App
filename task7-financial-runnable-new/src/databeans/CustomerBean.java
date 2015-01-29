package databeans;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.genericdao.PrimaryKey;

@PrimaryKey("customerId")
public class CustomerBean implements Comparable<CustomerBean>{
	private int customerId;
	private String username;
	private String  hashedPassword = "*";
	private int     salt           = 0;
	private String firstname;
	
	private String lastname;
	private String addrL1;
	private String addrL2;
	private String city;
	private String state;
	private String zip;
	private long cash;
	
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public boolean checkPassword(String password) {
		return hashedPassword.equals(hash(password));
	}
	
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String  getHashedPassword() { return hashedPassword; }
	public int     getSalt()           { return salt;           }


	public String getAddrL1() {
		return addrL1;
	}
	public void setAddrL1(String addrL1) {
		this.addrL1 = addrL1;
	}
	public String getAddrL2() {
		return addrL2;
	}
	public void setAddrL2(String addrL2) {
		this.addrL2 = addrL2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public long getCash() {
		return cash;
	}
	public void setCash(long cash) {
		this.cash = cash;
	}
	public int     hashCode()          { return username.hashCode(); }

	public void setHashedPassword(String x)  { hashedPassword = x; }
	public void setPassword(String s)        { salt = newSalt(); hashedPassword = hash(s); }
	public void setSalt(int x)               { salt = x;           }

	@Override
	public int compareTo(CustomerBean o) {
		// TODO Auto-generated method stub
		return username.compareTo(o.username);
	}
	private String hash(String clearPassword) {
		if (salt == 0) return null;

		MessageDigest md = null;
		try {
		  md = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
		  throw new AssertionError("Can't find the SHA1 algorithm in the java.security package");
		}

		String saltString = String.valueOf(salt);
		
		md.update(saltString.getBytes());
		md.update(clearPassword.getBytes());
		byte[] digestBytes = md.digest();

		// Format the digest as a String
		StringBuffer digestSB = new StringBuffer();
		for (int i=0; i<digestBytes.length; i++) {
		  int lowNibble = digestBytes[i] & 0x0f;
		  int highNibble = (digestBytes[i]>>4) & 0x0f;
		  digestSB.append(Integer.toHexString(highNibble));
		  digestSB.append(Integer.toHexString(lowNibble));
		}
		String digestStr = digestSB.toString();

		return digestStr;
	}

	private int newSalt() {
		Random random = new Random();
		return random.nextInt(8192)+1;  // salt cannot be zero
	}
	
}
