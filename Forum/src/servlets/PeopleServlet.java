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
 * Servlet implementation class PeopleServlet
 */
@WebServlet("/PeopleServlet")
public class PeopleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PeopleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User me = SessionManager.getUserFromSession(request);
		
		if(SessionManager.checkUserSession(request)) {
			
			String searchText = request.getParameter("search");
			DatabaseManager dm = new DatabaseManager();
			
			String text;
			
			if(searchText != null)
				text = searchText;
			else
				text = null;
								
			ArrayList<Category> list = dm.getCategories(null);
			ArrayList<User> following = dm.getFollowsForUser(me.getId(), text);
			ArrayList<User> whoToFollow = dm.getWhoToFollowForId(me.getId(), text);
			ArrayList<User> followers = dm.getFollowersForUser(me.getId(), text);
			int unread = dm.getUnreadNo(me.getId());
			
			request.setAttribute("unread", unread);		
			request.setAttribute("followers", followers);
			request.setAttribute("following", following);
			request.setAttribute("whoToFollow", whoToFollow);
			request.setAttribute("sessionUser", me);
			request.setAttribute("categories", list);
			getServletContext().getRequestDispatcher("/people.jsp").forward(request, response);
		}else {
			response.sendRedirect(request.getContextPath() + "LoginServlet");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DatabaseManager dm = new DatabaseManager();
		
		int id1 = Integer.parseInt(request.getParameter("me"));
		int id2 = Integer.parseInt(request.getParameter("who"));
		String where = request.getParameter("where");
		String action = request.getParameter("action");
		
		if(action.equals("follow")) 		
			if(dm.follow(id1, id2)) 
				if(where.equals("profile"))
					response.sendRedirect("ProfileServlet?uid=" + id2);
				else
					doGet(request, response);
			else
				response.sendRedirect("error.jsp");
		else
			if(dm.unfollow(id1, id2)) 
				if(where.equals("profile"))
					response.sendRedirect("ProfileServlet?uid=" + id2);
				else
					doGet(request, response);
			else
				response.sendRedirect("error.jsp");
	}

}
