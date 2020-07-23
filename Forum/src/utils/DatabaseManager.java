package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beans.Category;
import beans.Message;
import beans.Post;
import beans.User;

public class DatabaseManager {
	Connection conn = null;

	public DatabaseManager() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/forum", "root", "");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String loginUser(String username, String password) {

		ResultSet rs;

		try {
			rs = conn.createStatement().executeQuery("SELECT * FROM users");

			while (rs.next()) {
				if (username.equals(rs.getString("Username")) && password.equals(rs.getString("Password"))) {
					User u = new User(rs.getInt("Id"), rs.getString("Name"), rs.getString("Username"),
							rs.getString("Email"), rs.getString("Picture"), rs.getString("Type"));
					return Util.getXmlFromObject(u);
				}
			}
			rs.beforeFirst();
			while (rs.next()) {
				if (username.equals(rs.getString("Username")) && !password.equals(rs.getString("Password"))) {
					return "password";
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "no user";
	}

	public boolean addUser(String name, String username, String password, String email) {

		String msg = this.loginUser(username, password);
		if (msg.equalsIgnoreCase("no user")) {

			try {
				String sql = "INSERT INTO users (Name, Username, Email, Password, Picture, Type) VALUES ('" + name
						+ "','" + username + "','" + email + "','" + password + "','" + "default.png" + "','" + "user"
						+ "')";
				conn.createStatement().executeUpdate(sql);
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return false;
	}
	
	public boolean deleteUser(int id) {

		try {
			String sql = "DELETE FROM users WHERE Id = " + id;
			conn.createStatement().executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return false;
	}
	
	public boolean deleteCategory(int id) {

		try {
			String sql = "DELETE FROM categories WHERE Id = " + id;
			conn.createStatement().executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return false;
	}

	public boolean updateUser(int id, String name, String username, String password, String email) {

		try {
			String sql = "UPDATE users SET Name='" + name + "', Username='" + username + "' , Email='" + email + "' , Password='" + password +
					"' WHERE Id = " + id;
			conn.createStatement().executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
	
	public boolean addCategory(String name, String desc) {

		try {
			String sql = "INSERT INTO categories (Name, Description) VALUES ('" + name + "', '" + desc + "')";
			
			conn.createStatement().executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
	
	
	public boolean updateProfilePicture(int id, String picture) {
		
		try {
			String sql = "UPDATE users SET Picture='" + picture + "' WHERE Id = " + id;
			
			conn.createStatement().executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public ArrayList<Category> getCategories(String filter) {

		ArrayList<Category> list = new ArrayList<Category>();

		ResultSet rs = null;

		try {
			String sql = "";
			if(filter == null)
				sql = "SELECT * FROM categories ORDER BY Name";
			else
				sql = "SELECT * FROM categories WHERE Name like '%" + filter + "%' ORDER BY Name";
			rs = conn.createStatement().executeQuery(sql);
			while (rs.next()) {
				list.add(new Category(rs.getInt("Id"), rs.getString("Name"), rs.getString("Description")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public Category getCategoryForId(int id) {

		Category c = null;

		ResultSet rs = null;

		try {
			rs = conn.createStatement().executeQuery("SELECT * FROM categories WHERE Id = " + id);
			while (rs.next()) {
				c = new Category(rs.getInt("Id"), rs.getString("Name"), rs.getString("Description"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return c;
	}

	public User getUserForId(int id) {

		ResultSet rs = null;

		try {
			rs = conn.createStatement().executeQuery("SELECT * FROM users WHERE Id = " + id);
			while (rs.next()) {
				return new User(rs.getInt("Id"), rs.getString("Name"), rs.getString("Username"), rs.getString("Email"),
						rs.getString("Picture"), rs.getString("Type"));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public ArrayList<User> getAllUsers(String filter) {
		ArrayList<User> list = new ArrayList<User>();

		ResultSet rs = null;

		try {
			String sql = "";
			if(filter == null)
				sql = "SELECT * FROM users ORDER BY Name";
			else
				sql = "SELECT * FROM users WHERE Username like '%" + filter + "%' OR Name like '%" + filter + "%' OR Email like '%" + filter + "%' ORDER BY Name ASC";
			
			rs = conn.createStatement().executeQuery(sql);
			while (rs.next()) {
				list.add(new User(rs.getInt("Id"), rs.getString("Name"), rs.getString("Username"),
						rs.getString("Email"), rs.getString("Picture"), rs.getString("Type")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
	
	public int getMaxIdForPost() {
		ResultSet rs = null;
		try {
			String sql = "SELECT max(Id) as 'max' from posts";
			rs = conn.createStatement().executeQuery(sql);
			while (rs.next()) {
				return rs.getInt("max");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	public boolean follow(int id1, int id2) {

		try {
			String sql = "INSERT INTO follows (Id1, Id2) VALUES (" + id1 + ", " + id2 + ")";
			conn.createStatement().executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean unfollow(int id1, int id2) {

		if (isFollowing(id1, id2))
			try {
				String sql = "DELETE FROM follows WHERE Id1=" + id1 + " AND Id2=" + id2;
				conn.createStatement().executeUpdate(sql);
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return false;
	}

	public boolean isFollowing(int me, int id) {
		ResultSet rs = null;

		try {
			rs = conn.createStatement().executeQuery("SELECT * FROM follows WHERE Id1=" + me + " AND Id2=" + id);
			while (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public ArrayList<User> getFollowsForUser(int id, String filter) {
		ArrayList<User> list = new ArrayList<User>();

		ResultSet rs = null;

		try {
			String sql = "";
			if(filter == null)
				sql = "SELECT *\r\n" + "FROM follows f join users u on f.Id2 = u.Id\r\n"
						+ "WHERE f.Id1 = " + id + " ORDER BY Name";
			else
				sql = "SELECT *\r\n" + "FROM follows f join users u on f.Id2 = u.Id\r\n"
						+ "WHERE f.Id1 = " + id + " AND ( u.Name like '%" + filter + "%' OR u.Username like '%" + filter + "%' OR\r\n"
						+ "u.Email like '%" + filter + "%') ORDER BY u.Name";
				
			
			rs = conn.createStatement().executeQuery(sql);
			while (rs.next()) {

				list.add(new User(rs.getInt("Id"), rs.getString("Name"), rs.getString("Username"),
						rs.getString("Email"), rs.getString("Picture"), rs.getString("Type")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList<User> getFollowersForUser(int id, String filter) {
		ArrayList<User> list = new ArrayList<User>();

		ResultSet rs = null;

		try {
			String sql = "";
			if(filter == null)
				sql = "SELECT *\r\n" + "FROM follows f join users u on f.Id1 = u.Id\r\n"
						+ "WHERE f.Id2 = " + id + " ORDER BY Name";
			else
				sql = "SELECT *\r\n" + "FROM follows f join users u on f.Id1 = u.Id\r\n"
						+ "WHERE f.Id2 = " + id + " AND ( u.Name like '%" + filter + "%' OR u.Username like '%" + filter + "%' OR\r\n"
						+ "u.Email like '%" + filter + "%') ORDER BY u.Name";
				
			
			rs = conn.createStatement().executeQuery(sql);
			while (rs.next()) {

				list.add(new User(rs.getInt("Id"), rs.getString("Name"), rs.getString("Username"),
						rs.getString("Email"), rs.getString("Picture"), rs.getString("Type")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList<User> getWhoToFollowForId(int id, String filter) {
		ArrayList<User> list = new ArrayList<User>();

		ResultSet rs = null;

		try {
			String sql = "";
			if(filter == null)
				sql = "SELECT *\r\n" + "FROM users\r\n" + "where Id not in (\r\n" + "SELECT Id\r\n"
						+ "FROM follows f join users u on f.Id2 = u.Id \r\n" + "WHERE f.Id1 = " + id + "\r\n"
						+ ") AND Id <>" + id + " ORDER BY Name";
			else
				sql = "SELECT *\r\n" + "FROM users\r\n" + "where Id not in (\r\n" + "SELECT Id\r\n"
						+ "FROM follows f join users u on f.Id2 = u.Id \r\n" + "WHERE f.Id1 = " + id + "\r\n"
						+ ") AND Id <>" + id + " AND ( Name like '%" + filter + "%' OR Username like '%" + filter + "%' OR\r\n"
								+ "Email like '%" + filter + "%') ORDER BY Name";
			
			rs = conn.createStatement().executeQuery(sql);
			while (rs.next()) {

				list.add(new User(rs.getInt("Id"), rs.getString("Name"), rs.getString("Username"),
						rs.getString("Email"), rs.getString("Picture"), rs.getString("Type")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
	
	public boolean addMessage(int sid, int rid, String content) {
		try {
			String sql = "INSERT INTO messages (Sender, Receiver, Content) VALUES (" + sid + ", " + rid + ", '" + content + "')";
			//System.out.println(sql);
			conn.createStatement().executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	public ArrayList<Message> getReceivedMessagesForUser(int id) {
		ArrayList<Message> list = new ArrayList<Message>();

		ResultSet rs = null;

		try {
			rs = conn.createStatement()
					.executeQuery("SELECT *\r\n" + 
							"FROM messages m join users u on m.Sender = u.Id\r\n" + 
							"WHERE m.Receiver = " + id + " ORDER BY m.Read DESC, m.Time DESC");
			while (rs.next()) {
				
				String content = rs.getString("Content");
				String time = rs.getString("Time");
				int read = rs.getInt("Read");
				list.add(new Message(rs.getInt("m.Id"), new User(rs.getInt("u.Id"), rs.getString("Name"), rs.getString("Username"),
						rs.getString("Email"), rs.getString("Picture"), rs.getString("Type")), null, content, time, read));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
	
	
	public ArrayList<Message> getSentMessagesForUser(int id) {
		ArrayList<Message> list = new ArrayList<Message>();

		ResultSet rs = null;

		try {
			rs = conn.createStatement()
					.executeQuery("SELECT *\r\n" + 
							"FROM messages m join users u on m.Receiver = u.Id\r\n" + 
							"WHERE m.Sender = " + id + " ORDER BY m.Time DESC");
			while (rs.next()) {
				
				String content = rs.getString("Content");
				String time = rs.getString("Time");
				int read = rs.getInt("Read");
				list.add(new Message(rs.getInt("m.Id"), new User(rs.getInt("u.Id"), rs.getString("Name"), rs.getString("Username"),
						rs.getString("Email"), rs.getString("Picture"), rs.getString("Type")), null, content, time, read));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
	
	public int getUnreadNo(int id) {
		
		ResultSet rs = null;

		try {
			rs = conn.createStatement().executeQuery("SELECT count(*) as 'broj' FROM messages m WHERE m.Read = 1 AND m.Receiver=" + id);
			while (rs.next()) {
				
				return rs.getInt("broj");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return -1;
	}
	
	public boolean markAsRead(int id) {
		try {
			String sql = "UPDATE messages m SET m.Read=0 WHERE Id = " + id;
			conn.createStatement().executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
	
	public boolean addPost(int owner, int categoryId, String subject, String content) {
		try {
			String sql = "INSERT INTO posts (Owner, CategoryId, Subject, Content) VALUES (" + owner + ", " + categoryId + ", '" + subject + "', '" + content + "')";
			conn.createStatement().executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
	
	
	public ArrayList<Post> getPostsForCategory(int id) {
		ArrayList<Post> list = new ArrayList<Post>();

		ResultSet rs = null;

		try {
			rs = conn.createStatement()
					.executeQuery("SELECT p.Id, u.Id, u.Name, u.Username, u.Email, u.Picture, u.Type, p.CategoryId, p.Subject, p.Content, (SELECT count(*) FROM likes l where l.PostId = p.Id) as 'likes'\r\n" + 
							"FROM posts p join users u on p.Owner = u.Id\r\n" + 
							"WHERE p.CategoryId = " + id + " ORDER BY likes DESC");
			while (rs.next()) {
				
				User user = new User(rs.getInt("u.Id"), rs.getString("u.Name"), rs.getString("u.Username"),
						rs.getString("u.Email"), rs.getString("u.Picture"), rs.getString("u.Type"));
				String content = rs.getString("p.Content");
				String desc = "";
				if(content.length() > 101)
					desc = content.substring(0, 100) + "...";
				else 
					desc = content;
				list.add(new Post(rs.getInt("p.Id"), user, rs.getString("p.Subject"), desc, content, rs.getInt("likes")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
	
	public boolean isLiked(int uid, int pid) {
		ResultSet rs = null;
		try {
			String sql = "SELECT * FROM likes WHERE UserId = " + uid + " AND PostId = " + pid;
			rs = conn.createStatement().executeQuery(sql);
			while (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
	
	
	public Post getPostForId(int pid) {

		ResultSet rs = null;

		try {
			rs = conn.createStatement()
					.executeQuery("SELECT p.Id, u.Id, u.Name, u.Username, u.Email, u.Picture, u.Type, p.CategoryId, p.Subject, p.Content, (SELECT count(*) FROM likes l where l.PostId = p.Id) as 'likes'\r\n" + 
							"FROM posts p join users u on p.Owner = u.Id\r\n" + 
							"WHERE p.Id = " + pid + " ORDER BY likes DESC");
			while (rs.next()) {
				
				User user = new User(rs.getInt("u.Id"), rs.getString("u.Name"), rs.getString("u.Username"),
						rs.getString("u.Email"), rs.getString("u.Picture"), rs.getString("u.Type"));
				String content = rs.getString("p.Content");
				String desc = "";
				if(content.length() > 101)
					desc = content.substring(0, 100) + "...";
				else 
					desc = content;
				return new Post(rs.getInt("p.Id"), user, rs.getString("p.Subject"), desc, content, rs.getInt("likes"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	public boolean like(int uid, int pid) {
		try {
			String sql = "INSERT INTO likes (UserId, PostId) VALUES (" + uid + " , " + pid + ")";
			conn.createStatement().executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public boolean unlike(int uid, int pid) {
		try {
			String sql = "DELETE FROM likes WHERE UserId = " + uid + " AND PostId = " + pid;
			conn.createStatement().executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
	
	public void close() {
		try {
			this.conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
