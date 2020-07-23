package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Category;
import beans.User;
import utils.DatabaseManager;
import utils.SessionManager;

/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//ne iz sesije nego get user for id, i iz sesije, pa ako su isti dati opcije za izmenu, u suprotnom follow
		int id = Integer.parseInt(request.getParameter("uid"));	
		User me = SessionManager.getUserFromSession(request);
		boolean isFollowing = false;
		
		if(SessionManager.checkUserSession(request)) {
			DatabaseManager dm = new DatabaseManager();
			ArrayList<Category> list = dm.getCategories(null);
			
			User reqUser = dm.getUserForId(id);
			isFollowing = dm.isFollowing(me.getId(), id);
			int unread = dm.getUnreadNo(me.getId());
			
			request.setAttribute("unread", unread);
			request.setAttribute("isFollowing", isFollowing);
			request.setAttribute("user", reqUser);
			request.setAttribute("sessionUser", me);
			request.setAttribute("categories", list);
			getServletContext().getRequestDispatcher("/profile.jsp").forward(request, response);
		}else {
			response.sendRedirect(request.getContextPath() + "LoginServlet");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String repeat = request.getParameter("repeatPassword");
		
		DatabaseManager dm = new DatabaseManager();
		
		if(password.equals(repeat) && dm.updateUser(id, name, username, password, email)) {
			response.sendRedirect("ProfileServlet?uid=" + id);
		}
		else {
			response.sendRedirect("error.jsp");
		}
	}

}
