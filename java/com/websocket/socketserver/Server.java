package com.websocket.socketserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.websocket.socketserver.contants.DatabaseConfig;
import com.websocket.socketserver.model.User;

public class Server extends WebSocketServer {

	public Server(int port) {
		super(new InetSocketAddress(port));
	}

	@Override
	public void onOpen(WebSocket webSocket, ClientHandshake handshake) {
		webSocket.send("Witaj na Serwerze");
		System.out.println("Klient " + webSocket + " został połączony");
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		System.out.println(conn + " polaczenie zakonczone");
	}

	@Override
	public void onMessage(WebSocket webSocket, String message) {
		if (message.equals("test")) {
			broadcast("Testowa komunikacja");
		} else {
			String resourceUrl = "http://localhost:8080/check?number=" + message;
			String command = "curl -X POST " + resourceUrl;
			try {
				Process process = Runtime.getRuntime().exec(command);
			} catch (IOException e) {
				e.printStackTrace();
			}

			List<User> userList = new ArrayList<>();
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection connection = DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USERNAME, DatabaseConfig.PASSWORD);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM user;");
				while (resultSet.next()) {
					int id = resultSet.getInt("id");
					String fistName = resultSet.getString("first_name");
					String lastName = resultSet.getString("last_name");
					String number = resultSet.getString("number");
					String access = resultSet.getString("access");
					userList.add(new User(id, fistName, lastName, "", "", number, access));
				}
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			boolean isUserExists = false;
			String response = "";
			for (User user : userList) {
				if (user.getNumber().equals(message)) {
					isUserExists = true;
					if(user.getAccess().equals("true")){
						response = "User o ID " + message + " otrzymal dostep ";
					} else response = "User o ID " + message + " nie otrzymal dostepu";
				}
			}

			if (isUserExists) {
				broadcast(response);
			} else {
				broadcast("User o ID " + message + " nie jest znany");
			}
		}

		//		System.out.println(webSocket + ": " + message);
	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		ex.printStackTrace();
	}

	@Override
	public void onStart() {
		System.out.println("*** Start Serwera ***");
		setConnectionLostTimeout(0);
		setConnectionLostTimeout(100);
	}

}