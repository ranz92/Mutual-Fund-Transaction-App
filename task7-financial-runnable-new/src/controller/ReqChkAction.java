package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.CustomerBean;
import databeans.TransactionBean;
import model.Model;
import model.TransactionDAO;
import formbeans.RequestCheckForm;

public class ReqChkAction extends Action {
	private FormBeanFactory<RequestCheckForm> formBeanFactory = FormBeanFactory.getInstance(RequestCheckForm.class);
	private TransactionDAO transactionDAO;
	
	public ReqChkAction(Model model) {
		transactionDAO = model.getTransactionDAO();
	}
	
	public String getName() {
		return "requestCheck.do";
	}
	
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		try {
			
			CustomerBean customer = (CustomerBean) request.getSession(false).getAttribute("customer");
			RequestCheckForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			
	        TransactionBean transaction = new TransactionBean();
			transaction.setCustomer_id(customer.getCustomerId());
			transaction.setAmount(Long.parseLong(fixBadChars(form.getAmount())));
			transaction.setTransaction_type('R');
			
			if(customer.getCash() < transaction.getAmount()){
				errors.add("The requested amount exceeds your current cash balance.");
			} else {
				transactionDAO.create(transaction);
				customer.setCash(customer.getCash() - transaction.getAmount());
			}
						
			errors.addAll(form.getValidationErrors());
	        if (errors.size() > 0) return "error.jsp";
			
	        request.getSession(true).setAttribute("customer", customer);
			
			return "requestCheck.jsp";
		} catch(RollbackException e) {
			errors.add(e.getMessage());
			return "requestCheck.jsp";
		} catch(FormBeanException e) {
			errors.add(e.getMessage());
			return "requestCheck.jsp";
		}
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
