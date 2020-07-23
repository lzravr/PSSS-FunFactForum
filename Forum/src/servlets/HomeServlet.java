package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.User;
import utils.DatabaseManager;
import utils.SessionManager;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DatabaseManager dm = new DatabaseManager();
		
		if(SessionManager.checkUserSession(request)) {
			User user = SessionManager.getUserFromSession(request);
			request.setAttribute("user", user);
			if(user.getType().equalsIgnoreCase("admin")) {
				response.sendRedirect("AdminServlet");
			}
			else {
				request.setAttribute("categories", dm.getCategories(null));
				int unread = dm.getUnreadNo(user.getId());
				
				request.setAttribute("unread", unread);
				getServletContext().getRequestDispatcher("/home.jsp").forward(request, response);
					
			}
				//response.sendRedirect("home.jsp");
			
			dm.close();
		}else {
			response.sendRedirect(request.getContextPath() + "LoginServlet");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
