package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.User;
import utils.DatabaseManager;
import utils.SessionManager;
import utils.Util;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(SessionManager.checkUserSession(request)) {
				response.sendRedirect("HomeServlet");
		}
		else {
			response.sendRedirect("index.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("inputUsername");
		String password = request.getParameter("inputPassword");
		
		DatabaseManager dm = new DatabaseManager();
		
		String msg = dm.loginUser(username, password);
		
		Object objTmp = null;
		
		if(msg.length() > 9)
			objTmp = Util.getObjectFromString(msg);
		
		if(objTmp instanceof User) {
			User user = (User)objTmp;
			SessionManager.createSession(request, user);
			response.sendRedirect("HomeServlet");
		}
		else {
			
			if(msg.equalsIgnoreCase("password"))
				msg = "Wrong password!";
			else if(msg.equalsIgnoreCase("no user")) 
				msg = "Username you entered does not exist!";
			
			RequestDispatcher dis = request.getRequestDispatcher("index.jsp");
			request.setAttribute("error", msg);
			dis.forward(request, response);
		}
		
		dm.close();
	}

}
