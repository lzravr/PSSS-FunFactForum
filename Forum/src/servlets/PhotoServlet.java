package servlets;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
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
 * Servlet implementation class PhotoServlet
 */
@WebServlet("/PhotoServlet")
@MultipartConfig
public class PhotoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PhotoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		User me = SessionManager.getUserFromSession(request);
		Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
	    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
	    InputStream fileContent = filePart.getInputStream();
	    
	    String path = Config.imgSavePath + fileName;
        FileOutputStream fos = new FileOutputStream(path);
	    
        int b;
		while ((b = fileContent.read()) != -1) {
			fos.write(b);
		}
		
		fos.flush();
		fos.close();
		
		DatabaseManager dm = new DatabaseManager();
		
		if(dm.updateProfilePicture(me.getId(), fileName)) {
			dm.close();
			response.sendRedirect("ProfileServlet?uid=" + me.getId());
		}
		else
			response.sendRedirect("/erros.jsp");
		
		
	}

}
