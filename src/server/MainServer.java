package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;

import lib.core.Campo;
import lib.core.Categoria;
import lib.core.EscursioneMontagnaEvento;
import lib.core.Evento;
import lib.core.Notifica;
import lib.core.PartitaCalcioEvento;
import lib.net.Channel;
import lib.util.ControlloCampo;
import lib.util.Log;
import lib.util.Nomi;
import lib.net.ModelModificaUtente;

public class MainServer {

	private static int port = 4444;
	private static ServerSocket server;
	private static Channel channel;
	private static GestoreServizi social;
	private static ConversatoreClient convClient;

	public static void main(String[] args) {
		if(args.length>0)
			if(ControlloCampo.controlloIntero(args[0]).equals(Campo.OK)) {
				int verbose = Integer.parseInt(args[0]);
				if(verbose>=0 && verbose<4)
					Log.setVerbose(verbose);

			}
		social = GestoreServizi.getInstance();
		Log.writeRoutineLog(MainServer.class, "Inizio nuova sessione", Log.TOP_PRIORITY);
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
		
		boolean noError=true;
		while(true) {
			System.out.println("In attesa di una connessione \n");
			try {
				channel = new Channel(server.accept());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//invio titoli categorie
			convClient = new ConversatoreClient(channel);
			convClient.invioTitoli();

			noError = convClient.login();
			if(!noError)
				continue;
			convClient.invioCategorie();
			convClient.invioUtente();

			do {
				noError = convClient.loopLife();
			}while(noError);
		}


	}

}
