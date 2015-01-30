package formbeans;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mybeans.form.FormBean;

public class RequestCheckForm extends FormBean {
	private String fundId;
	private String amount;
	
	public String getFundId() { return fundId; }
	public String getAmount() { return amount; }
	
	public void setFundId(String s) {fundId = s;}
	public void setAmount(String l) { amount = l;}
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
			return Long.parseLong(fixBadChars(amount));
		} catch (NumberFormatException e) {
			// call getValidationErrors() to detect this
			return -1;
		}
	}
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		
		
		if(amount == null) {
			errors.add("Amount is required");
		}
		
		try {
			Long.parseLong(amount);
		} catch (NumberFormatException e) {
			errors.add("Amount cannot be parsed");
		}
		
		return errors;
	}

	private String fixBadChars(String s) {
		if (s == null || s.length() == 0) return s;
		
		Pattern p = Pattern.compile("[<>\"&]");
        Matcher m = p.matcher(s);
        StringBuffer b = null;
        while (m.find()) {
            if (b == null) b = new StringBuffer();
            switch (s.charAt(m.start())) {
                case '<':  m.appendReplacement(b,"&lt;");
                           break;
                case '>':  m.appendReplacement(b,"&gt;");
                           break;
                case '&':  m.appendReplacement(b,"&amp;");
                		   break;
                case '"':  m.appendReplacement(b,"&quot;");
                           break;
                default:   m.appendReplacement(b,"&#"+((int)s.charAt(m.start()))+';');
            }
        }
        
        if (b == null) return s;
        m.appendTail(b);
        return b.toString();
    }
}
