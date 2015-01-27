//Jiayi Zhu
//jiayiz
//08-600
//Dec 10, 2014

package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Model;


/*
 * Logs out by setting the "user" session attribute to null.
 * (Actions don't be much simpler than this.)
 */
public class LogoutAction extends Action {

	public LogoutAction(Model model) { }

	public String getName() { return "logout.do"; }

	public String perform(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
        session.setAttribute("customer",null);
        session.setAttribute("employee",null);
        //request.setAttribute("message","You are now logged out");
        return "login.jsp";
    }
}
