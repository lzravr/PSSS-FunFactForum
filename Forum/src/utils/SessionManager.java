package utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import beans.User;

public class SessionManager {

	public static void createSession(HttpServletRequest request, User user) {

		HttpSession session = request.getSession(true);
		session.setAttribute("user", user);
	}

	public static boolean checkUserSession(HttpServletRequest request) {

		HttpSession session = request.getSession(false);

		if (session == null)
			return false;

		if (session.getAttribute("user") == null)
			return false;

		return true;
	}

	public static User getUserFromSession(HttpServletRequest request) {

		if (checkUserSession(request)) {
			HttpSession session = request.getSession(false);
			return (User) session.getAttribute("user");
		}

		return null;
	}
}
