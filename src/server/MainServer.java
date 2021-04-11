package server;

import java.io.IOException;
import java.net.ServerSocket;
import net.Channel;
import server.core.SocialNetwork;

public class MainServer {

	private static int port = 4444;
	private static ServerSocket server;
	private static Channel channel;
	private static SocialNetwork social;
	
	public static void main(String[] args) {
		
		social = SocialNetwork.getInstance();
		try {
			server = new ServerSocket(port);
			channel = new Channel(server.accept());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		//invio titoli categorie
		channel.write(social.titoliCategorie());
		
			
		
	}
}
