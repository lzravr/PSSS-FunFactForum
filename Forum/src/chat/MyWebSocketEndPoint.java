package chat;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint(value="/chat")
public class MyWebSocketEndPoint {

	private static final String USERNAME_KEY = "username";
	private static final String FIRSTNAME_KEY = "firstname";
	private static final String LASTNAME_KEY = "lastname";
	private static Map<String, Session> clients = Collections.synchronizedMap(new LinkedHashMap<String, Session>());
	
	@OnOpen
	public void onOpen(Session session) throws Exception {
		
		String newUsername = session.getRequestParameterMap().get(USERNAME_KEY).get(0);
		String newFirstName = session.getRequestParameterMap().get(FIRSTNAME_KEY).get(0);
		String newLastName = session.getRequestParameterMap().get(LASTNAME_KEY).get(0);
		
		System.out.println(newUsername + " " + newFirstName + " " + newLastName);
		
		clients.put(newUsername, session);
	    
	    session.getUserProperties().put(USERNAME_KEY, newUsername);
	    session.getUserProperties().put(FIRSTNAME_KEY, newFirstName);
	    session.getUserProperties().put(LASTNAME_KEY, newLastName);

	    for (Session client : clients.values()) {
	    	if(client == session) continue;
	    	session.getBasicRemote().sendText("newUser|" + client.getUserProperties().get(USERNAME_KEY) + "#" + client.getUserProperties().get(FIRSTNAME_KEY) + "#" + client.getUserProperties().get(LASTNAME_KEY) );
	    	
	    }

	    for (Session client : clients.values()) {
	        if(client == session) continue;
	        client.getBasicRemote().sendText("newUser|" + newUsername + "#" + newFirstName + "#" + newLastName);
	    }
	}
	
	
	@OnMessage
	public void onMessage(Session session, String message) throws Exception {
	    
	    String[] data = message.split("\\|");
	    
	    String destination = data[0];
	    String messageContent = data[1];

	    String sender = (String)session.getUserProperties().get(USERNAME_KEY);
//	    String response = "message|" + sender + "|" + messageContent;
//	    
//	    for(Session client: clients.values()) {
//	    	
//	        client.getBasicRemote().sendText(response);
//	    }
	    
        Session client = clients.get(destination);
        String response = "message|" + sender + "|" + messageContent;
        client.getBasicRemote().sendText(response);
	    
	}
	
	@OnClose
	public void onClose(Session session) throws Exception {
		
	    String username = (String)session.getUserProperties().get(USERNAME_KEY);
	    clients.remove(username);
	    
	    for (Session client : clients.values()) {
	        client.getBasicRemote().sendText("removeUser|" + username);
	    }
	
	}
	
	
}
