package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Category;
import beans.Post;
import beans.User;
import utils.DatabaseManager;
import utils.SessionManager;

/**
 * Servlet implementation class CategoryServlet
 */
@WebServlet("/CategoryServlet")
public class CategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(SessionManager.checkUserSession(request)) {
			int id = Integer.parseInt(request.getParameter("cid"));
			User me = SessionManager.getUserFromSession(request);
			DatabaseManager dm = new DatabaseManager();
			Category category = dm.getCategoryForId(id);
			ArrayList<Category> list = dm.getCategories(null);
			ArrayList<Post> posts = dm.getPostsForCategory(id);
			int unread = dm.getUnreadNo(me.getId());
			
			request.setAttribute("posts", posts);
			request.setAttribute("unread", unread);
			request.setAttribute("user", me);
			request.setAttribute("category", category);
			request.setAttribute("categories", list);
			getServletContext().getRequestDispatcher("/category.jsp").forward(request, response);
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
