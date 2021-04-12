package server;

import java.io.IOException;
import java.net.ServerSocket;
import net.Channel;
import server.core.SocialNetwork;
import util.Log;
import util.Nomi;

public class MainServer {

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

		while(true) {
			System.out.println("In attesa di una connessione \n");
			try {
				channel = new Channel(server.accept());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//invio titoli categorie
			channel.write(social.titoliCategorie());

			login();
		}



	}


	public static void login() {
		String risposta;
		String username = null, password=null, confermaPW = null;
		String[] cat = null;
		int etaMin=0, etaMax = 0;
		boolean isReg = false;
		do{
			try {
				username = (String) channel.read();
				password = (String) channel.read();
				isReg = (boolean) channel.read();
			}catch(IOException e) {
				Log.writeErrorLog(channel.getClass(), "Impossibile leggere dal client. Chiusura socket improvvisa");
				e.printStackTrace();
				return;
			}
			if(isReg) {

				try {
					confermaPW = (String) channel.read();
					etaMin = (int) channel.read();
					etaMax = (int) channel.read();
					cat = (String[]) channel.read();
				}catch(IOException e) {
					Log.writeErrorLog(channel.getClass(), "Impossibile leggere dal client. Chiusura socket improvvisa");
					e.printStackTrace();
					return;
				}
				risposta = social.registrazione(username, password, confermaPW, etaMin, etaMax, cat);
			}
			else
				risposta = social.login(username, password);

			channel.write(risposta);

		}while(!risposta.equals(Nomi.SN_BENVENUTO.getNome()));

	}
}
