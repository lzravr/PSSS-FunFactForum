package servlets;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import beans.User;
import utils.Config;
import utils.DatabaseManager;
import utils.SessionManager;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/AdminServlet")
@MultipartConfig
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String text = request.getParameter("search");
		String filter = "";

		if (text != null)
			filter = text;
		else
			filter = null;

		DatabaseManager dm = new DatabaseManager();
		if (SessionManager.checkUserSession(request)) {
			User user = SessionManager.getUserFromSession(request);
			request.setAttribute("user", user);
			if (user.getType().equalsIgnoreCase("admin")) {
				request.setAttribute("users", dm.getAllUsers(filter));
				request.setAttribute("categories", dm.getCategories(null));
				int unread = dm.getUnreadNo(user.getId());
				request.setAttribute("unread", unread);
				getServletContext().getRequestDispatcher("/admin.jsp").forward(request, response);
			} else {
				response.sendRedirect("error.jsp");
			}
		} else {
			response.sendRedirect(request.getContextPath() + "LoginServlet");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		if(action == null) {			
			String catName = request.getParameter("catName");
			String desc = request.getParameter("desc");
			
			Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
		    InputStream fileContent = filePart.getInputStream();
		    
		    String path = Config.imgSavePath + catName + ".jpg";
	        FileOutputStream fos = new FileOutputStream(path);
	        
		    
	        int b;
			while ((b = fileContent.read()) != -1) {
				fos.write(b);
			}
			
			fos.flush();
			fos.close();
			
			DatabaseManager dm = new DatabaseManager();
			
			if(dm.addCategory(catName, desc)) {
				dm.close();
				response.sendRedirect("AdminServlet");
			}
			else
				response.sendRedirect("/erros.jsp");
		}
		else
		if (action.equals("delete")) {
			String object = request.getParameter("object");
			DatabaseManager dm = new DatabaseManager();

			if (object.equals("user")) {
				int id = Integer.parseInt(request.getParameter("id"));
				if (dm.deleteUser(id))
					response.sendRedirect("AdminServlet");
			} else if (object.equals("category")) {
				int id = Integer.parseInt(request.getParameter("id"));
				if (dm.deleteCategory(id))
					response.sendRedirect("AdminServlet");
			}
		}

	}

}
