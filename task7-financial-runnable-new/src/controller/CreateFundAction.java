package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.CustomerBean;
import databeans.FundBean;
import databeans.TransactionBean;

import model.CustomerDAO;
import model.Model;
import model.TransactionDAO;
import model.FundDAO;
import formbeans.BuyForm;
import formbeans.CreateFundForm;
import formbeans.CusRegisterForm;

public class CreateFundAction extends Action {
	private FormBeanFactory<CreateFundForm> formBeanFactory = FormBeanFactory.getInstance(CreateFundForm.class);
	private FundDAO fundDAO;
	
	public CreateFundAction(Model model) {
		fundDAO = model.getFundDAO();
	}
	
	public String getName() {
		return "createFund.do";
	}
	
	
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
        List<String> success = new ArrayList<String>();
        request.setAttribute("success",success);

        try {
        	if(request.getSession().getAttribute("employee") == null) {
				errors.add("Please log in as an customer.");
				return "login.jsp";
			}
        	
        	HttpSession session = request.getSession(false);
        	session.setAttribute("fundList", fundDAO.getFundList());
	        
        	CreateFundForm form = formBeanFactory.create(request);
	        request.setAttribute("form",form);
	        // If no params were passed, return with no errors so that the form will be
	        // presented (we assume for the first time).
	        if (!form.isPresent()) {
	            return "createFund.jsp";
	        }
	
	        // Any validation errors?
	        errors.addAll(form.getValidationErrors());
	        if (errors.size() != 0) {
	            return "createFund.jsp";
	        }
	
	        // Create the user bean
	        FundBean fund = new FundBean();
	        fund.setName(form.getFundName());
	        fund.setSymbol(form.getTicker());
	       
        	fundDAO.create(fund);
        	success.add("You have successfully created fund\"" + form.getFundName() + "\"");
        	form.setFundName("");
        	form.setTicker("");
        	request.setAttribute("form",form);
	        
        	session.setAttribute("fundList", fundDAO.getFundList());
	        

        	 return "createFund.jsp";
        } catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "createFund.jsp";
        } catch (FormBeanException e) {
        	errors.add(e.getMessage());
        	return "createFund.jsp";
        }
    }

		
	}
	


