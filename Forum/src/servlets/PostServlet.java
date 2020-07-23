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
 * Servlet implementation class PostServlet
 */
@WebServlet("/PostServlet")
public class PostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User me = SessionManager.getUserFromSession(request);
		int pid = Integer.parseInt(request.getParameter("pid"));
		
		if(SessionManager.checkUserSession(request)) {
			DatabaseManager dm = new DatabaseManager();
			ArrayList<Category> list = dm.getCategories(null);
			int unread = dm.getUnreadNo(me.getId());
			Post post = dm.getPostForId(pid);
			boolean isliked = dm.isLiked(me.getId(), pid);
			
			request.setAttribute("isLiked", isliked);
			request.setAttribute("post", post);
			request.setAttribute("unread", unread);	
			request.setAttribute("sessionUser", me);
			request.setAttribute("categories", list);
			getServletContext().getRequestDispatcher("/post.jsp").forward(request, response);
		}else {
			response.sendRedirect(request.getContextPath() + "LoginServlet");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		DatabaseManager dm = new DatabaseManager();
		User me = SessionManager.getUserFromSession(request);
		
		if(action.equals("post")) {
			int categoryId = Integer.parseInt(request.getParameter("category"));
			String subject = request.getParameter("subject");
			String content = request.getParameter("content");
			
			if(dm.addPost(me.getId(), categoryId, subject, content)) {
				response.sendRedirect("CategoryServlet?cid=" + categoryId);
			}
			else 
				response.sendRedirect("/error.jsp");
		}
		else if(action.equals("like")) {
			int pid = Integer.parseInt(request.getParameter("pid"));
			if(dm.like(me.getId(), pid))
				response.sendRedirect("PostServlet?pid=" + pid);
			else
				response.sendRedirect("/error.jsp");
		}
		else if(action.equals("unlike")) {
			int pid = Integer.parseInt(request.getParameter("pid"));
			if(dm.unlike(me.getId(), pid))
				response.sendRedirect("PostServlet?pid=" + pid);
			else
				response.sendRedirect("/error.jsp");
		}
	}

}
