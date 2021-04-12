package server;

import java.io.IOException;
import java.net.ServerSocket;
import net.Channel;
import server.core.SocialNetwork;
import util.Nomi;

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
			try {
				server.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		//invio titoli categorie
		channel.write(social.titoliCategorie());

		login();


	}


	public static void login() {
		String risposta;
		String username = null, password=null, confermaPW = null;
		String[] cat = null;
		int etaMin=0, etaMax = 0;
		boolean isReg = false;
		do{
			username = (String) channel.read();
			password = (String) channel.read();
			isReg = (boolean) channel.read();

			if(isReg) {

				confermaPW = (String) channel.read();
				etaMin = (int) channel.read();
				etaMax = (int) channel.read();
				cat = (String[]) channel.read();
				risposta = social.registrazione(username, password, confermaPW, etaMin, etaMax, cat);
			}
			else
				risposta = social.login(username, password);

			channel.write(risposta);
		}while(!risposta.equals(Nomi.SN_BENVENUTO.getNome()));

	}
}
