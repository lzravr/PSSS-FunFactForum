package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Category;
import beans.Message;
import beans.User;
import utils.DatabaseManager;
import utils.SessionManager;

/**
 * Servlet implementation class MessageServlet
 */
@WebServlet("/MessageServlet")
public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User me = SessionManager.getUserFromSession(request);
		
		if(SessionManager.checkUserSession(request)) {
			DatabaseManager dm = new DatabaseManager();
			ArrayList<Category> list = dm.getCategories(null);
			ArrayList<Message> receivedMsg = dm.getReceivedMessagesForUser(me.getId());
			ArrayList<Message> sentMsg = dm.getSentMessagesForUser(me.getId());
			int unread = dm.getUnreadNo(me.getId());
			
			request.setAttribute("unread", unread);
			request.setAttribute("sentMessages", sentMsg);
			request.setAttribute("messages", receivedMsg);
			request.setAttribute("sessionUser", me);
			request.setAttribute("categories", list);
			getServletContext().getRequestDispatcher("/messages.jsp").forward(request, response);
		}else {
			response.sendRedirect(request.getContextPath() + "LoginServlet");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DatabaseManager dm = new DatabaseManager();
		
		String action = request.getParameter("action");
				
		if(action.equalsIgnoreCase("read")) {
			int mid = Integer.parseInt(request.getParameter("mid"));
			if(dm.markAsRead(mid)) {
				response.sendRedirect("MessageServlet");
			}
		}
		else {
			int id1 = Integer.parseInt(request.getParameter("me"));
			int id2 = Integer.parseInt(request.getParameter("who"));
			String where = request.getParameter("where");
			String content = request.getParameter("message");
			
			
			if(dm.addMessage(id1, id2, content)) {
				if(where.equalsIgnoreCase("profile"))
					response.sendRedirect("ProfileServlet?uid=" + id2);
				else if(where.equalsIgnoreCase("messages")) {
					int mid = Integer.parseInt(request.getParameter("mid"));
					dm.markAsRead(mid);
					response.sendRedirect("MessageServlet");
				}
			}
			else 
				response.sendRedirect("error.jsp");
		}
	}

}
