package test;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import lib.core.Categoria;
import lib.core.EscursioneMontagnaEvento;
import lib.core.Evento;
import lib.core.Notifica;
import lib.core.PartitaCalcioEvento;
import lib.net.Channel;
import lib.util.Nomi;
import lib.net.ModelModificaUtente;
import server.SocialNetwork;

public class MainTestServer {

	private static int port = 4444;
	private static ServerSocket server;
	private static Channel channel;
	private static SocialNetwork social;

	public static void main(String[] args) {

		social = SocialNetwork.getInstance();

		social.login("mauro123", "123123123");
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

		invioCategorie();
		channel.write(social.getUtente());




		do {
			String messaggio=null;
			try {
				messaggio = (String) channel.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(messaggio);
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

					System.out.println(esito);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(messaggio.equals(Nomi.AZIONE_CREA_EVENTO.getNome())) {
				try {
					Evento evento = (Evento) channel.read();
					List<String> inviti = (List<String>) channel.read();
					String esito = social.creaEvento(evento, inviti);
					channel.write(esito);

					System.out.println(esito);
				} catch(IOException e) {
					e.printStackTrace();
				}
			}

			else if(messaggio.equals(Nomi.AZIONE_CANCELLA_NOTIFICA.getNome())) {
				try {
					Integer index = (Integer) channel.read();
					String esito = social.cancellaNotifica(index.intValue());
					channel.write(esito);
					System.out.println(esito);
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
			
			else if(messaggio.equals(Nomi.AZIONE_MODIFICA_PROFILO.getNome())) {
				try {
					ModelModificaUtente mmu = (ModelModificaUtente) channel.read();
					String esito = social.modificaUtente(mmu.getEtaMin(), mmu.getEtaMax(), mmu.isCatPartita(), mmu.isCatEscursione());
					channel.write(esito);
					System.out.println(esito);
				}catch(IOException e) {
					e.printStackTrace();
				}
			}

			System.out.println("invio aggiornamenti");
			System.out.println("l'utente ha " +  social.getUtente().getNotifiche().size() + " notifiche");
			
			invioUpdate();

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
	
	public static void invioUpdate() {
		invioCategorie();
		invioNotifiche();		
	}
	
	public static void invioCategorie() {
		for(Categoria cat: social.getCategorie()) {
			channel.write(Nomi.NET_LIST_CONTINUA.getNome());
			channel.write(cat.getNome());
			channel.write(cat.getDescrizione());
		
			for(String persone: (List<String>) cat.getPersoneInteressate()) {
				channel.write(Nomi.NET_LIST_CONTINUA.getNome());
				channel.write(persone);
			}
			channel.write(Nomi.NET_EOL.getNome());
			
			if(cat.getNome().equals(Nomi.CAT_PARTITA_CALCIO.getNome())) {
				for(PartitaCalcioEvento pce: (List<PartitaCalcioEvento>) cat.getBacheca()) {
					channel.write(Nomi.NET_LIST_CONTINUA.getNome());
					channel.write(pce);
					System.out.println(pce.getTitolo().getValoreString());
				}
				channel.write(Nomi.NET_EOL.getNome());
			}
			else if(cat.getNome().equals(Nomi.CAT_ESCURSIOME_MONTAGNA.getNome())) {
				for(EscursioneMontagnaEvento eme: (List<EscursioneMontagnaEvento>) cat.getBacheca()) {
					channel.write(Nomi.NET_LIST_CONTINUA.getNome());
					channel.write(eme);
					System.out.println(eme.getTitolo().getValoreString());
				}
				channel.write(Nomi.NET_EOL.getNome());
			}
			
			
		}
		channel.write(Nomi.NET_EOL.getNome());
	}
	
	public static void invioNotifiche() {
		for(Notifica not: social.getUtente().getNotifiche()) {
			channel.write(Nomi.NET_LIST_CONTINUA.getNome());
			channel.write(not);
		}
		channel.write(Nomi.NET_EOL.getNome());
	}

}
