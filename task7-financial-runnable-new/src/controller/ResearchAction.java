package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import model.Model;
import model.FundDAO;

public class ResearchAction extends Action {
	private FundDAO fundDAO;
	
	public ResearchAction(Model model) {
		fundDAO = model.getFundDAO();
	}
	
	public String getName() {
		return "research.do";
	}
	
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		HttpSession session = request.getSession();

		try {
			session.setAttribute("fundList", fundDAO.getFundList());
			return "research.jsp";
		}  catch (RollbackException e) {
			e.printStackTrace();
		}
		return "research.jsp" ;
	}
	

}
