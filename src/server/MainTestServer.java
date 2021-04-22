package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import lib.core.Categoria;
import lib.core.EscursioneMontagnaEvento;
import lib.core.Evento;
import lib.core.PartitaCalcioEvento;
import lib.net.Channel;
import lib.util.Nomi;

public class MainTestServer {

	private static int port = 4444;
	private static ServerSocket server;
	private static Channel channel;
	private static SocialNetwork social;

	public static void main(String[] args) {

		social = SocialNetwork.getInstance();
		
		social.login("mauro123", "123123123");
		social.stampaEventi();
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

		
		
		
		do {
			String messaggio=null;
			try {
				messaggio = (String) channel.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(messaggio.contains(Nomi.AZIONE_ISCRIZIONE.getNome())) {
				try {
					Evento evento = (Evento) channel.read();
					String esito =null;
					if(messaggio.equals(Nomi.AZIONE_ISCRIZIONE.getNome()))
						esito = social.iscrizione(evento);
					else if(messaggio.equals(Nomi.AZIONE_ISCRIZIONE_ATT.getNome())) {
						esito = social.iscrizione((EscursioneMontagnaEvento) evento, false, true);
					}
					else if(messaggio.equals(Nomi.AZIONE_ISCRIZIONE_IST.getNome())) {
						esito = social.iscrizione((EscursioneMontagnaEvento) evento, true, false);
					}
					else if(messaggio.equals(Nomi.AZIONE_ISCRIZIONE_ATT_IST.getNome())) {
						esito =social.iscrizione((EscursioneMontagnaEvento) evento, true, true);
					}
					channel.write(esito);
					channel.write(social.getCategorie());
					System.out.println(esito);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}		

			else if(messaggio.equals(Nomi.AZIONE_DISISCRIZIONE.getNome())) {
				try {
					Evento evento = (Evento) channel.read();
					String esito = social.revocaIscrizione(evento);
					channel.write(esito);
					channel.write(social.getCategorie());
					System.out.println(esito);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			else if(messaggio.equals(Nomi.AZIONE_CANCELLA_EVENTO.getNome())) {
				try {
					Evento evento = (Evento) channel.read();
					String esito = social.cancellaEvento(evento);
					channel.write(esito);
					channel.write(social.getCategorie());
					System.out.println(esito);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
					
			
		}while(true);
		
		
		/*
		social.login("mauro123", "123123123");

		//EscursioneMontagnaEvento evento = new EscursioneMontagnaEvento();
		PartitaCalcioEvento evento = new PartitaCalcioEvento();
		String ritorno = evento.setTitolo("prova Escursione Montagna");
		System.out.println(ritorno);
		ritorno = evento.setPartecipantiNecessari("10");
		System.out.println(ritorno);
		ritorno = evento.setTermineUltimo("12/3/2030");
		System.out.println(ritorno);
		ritorno = evento.setTermineUltimoRitiro("10/3/2030");
		System.out.println(ritorno);
		ritorno = evento.setLuogo("oratorio");
		System.out.println(ritorno);
		ritorno = evento.setDataInizio("15/3/2030", "15:30");
		System.out.println(ritorno);
		ritorno = evento.setDataFine("15/3/2030", "16:30");
		System.out.println(ritorno);
		ritorno = evento.setDurata("1");
		System.out.println(ritorno);
		ritorno = evento.setQuotaIndividuale("10");
		System.out.println(ritorno);
		ritorno = evento.setTolleranzaPartecipanti("2");
		System.out.println(ritorno);
		ritorno = evento.setNote("");
		System.out.println(ritorno);
		ritorno = evento.setSesso("5");
		System.out.println(ritorno);
		ritorno = evento.setEta("12");

		social.addEvento(evento, new ArrayList<String>());
		*/
	}

}
