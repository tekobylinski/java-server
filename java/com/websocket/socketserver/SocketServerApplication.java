package com.websocket.socketserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.websocket.socketserver.model.ServerLog;
import com.websocket.socketserver.model.User;
import com.websocket.socketserver.repository.UserRepository;

@SpringBootApplication
public class SocketServerApplication {

	public static void main(String[] args) throws IOException, InterruptedException {

		SpringApplication.run(SocketServerApplication.class, args);

		Server server = new Server(9090);
		server.start();

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			String in = bufferedReader.readLine();
			server.broadcast(in);

			if (in.equals("stop")) {
				server.stop(1000);
				break;
			}
		}
	}

}
