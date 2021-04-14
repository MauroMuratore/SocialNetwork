package server;

import java.io.IOException;
import java.net.ServerSocket;

import lib.net.Channel;

public class MainTestServer {

	private static int port = 4444;
	private static ServerSocket server;
	private static Channel channel;
	private static SocialNetwork social;
	
	public static void main(String[] args) {
		
		social = SocialNetwork.getInstance();
		//creazione del server socket
		try {
			server = new ServerSocket(port);

		} catch (IOException e) {
			try {
				server.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		try {
			channel = new Channel(server.accept());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		channel.write(social.getCategorie());
		
	}

}
