package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;

import lib.core.Categoria;
import lib.core.EscursioneMontagnaEvento;
import lib.core.Evento;
import lib.core.Notifica;
import lib.core.PartitaCalcioEvento;
import lib.net.Channel;
import lib.net.ModelModificaUtente;
import lib.util.Log;
import lib.util.Nomi;

public class ConversatoreClient {
	
	
	private Channel channel;
	private GestoreServizi social;
	
	public ConversatoreClient(Channel ch) {
		channel=ch;
		social = GestoreServizi.getInstance();
	}
	
	public boolean login() {
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
				Log.writeErrorLog(MainServer.class, "Impossibile leggere dal client. Chiusura socket improvvisa");
				e.printStackTrace();
				return false;
			}
			if(isReg) {

				try {
					confermaPW = (String) channel.read();
					etaMin = (int) channel.read();
					etaMax = (int) channel.read();
					cat = (String[]) channel.read();
				}catch(IOException e) {
					Log.writeErrorLog(MainServer.class, "Impossibile leggere dal client. Chiusura socket improvvisa");
					e.printStackTrace();
					return false;
				}
				risposta = social.registrazione(username, password, confermaPW, etaMin, etaMax, cat);
			}
			else
				risposta = social.login(username, password);

			channel.write(risposta);

		}while(!risposta.equals(Nomi.SN_BENVENUTO.getNome()));
		return true;
	}
	
	public boolean loopLife() {
		String messaggio=null;
		try {
			messaggio = (String) channel.read();
		} catch (IOException e) {
			Log.writeErrorLog(MainServer.class, "Impossibile leggere dal client. Chiusura socket improvvisa");
			if(social.getUtente()!=null) {
				social.logout();
			}
			e.printStackTrace();
			return false;
		}
		System.out.println(messaggio);
		if(messaggio.contains(Nomi.NET_CHIUSURA_SOCKET.getNome())) {
			Log.writeRoutineLog(MainServer.class, "Logout utente", Log.TOP_PRIORITY);
			social.logout();
			return false;
		}
		else if(messaggio.contains(Nomi.AZIONE_ISCRIZIONE.getNome())) {
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
				Log.writeErrorLog(MainServer.class, "Impossibile leggere dal client. Chiusura socket improvvisa");
				if(social.getUtente()!=null) {
					social.logout();
				}
				e.printStackTrace();
				return false;
			}
		}		

		else if(messaggio.equals(Nomi.AZIONE_DISISCRIZIONE.getNome())) {
			try {
				Evento evento = (Evento) channel.read();
				String esito = social.revocaIscrizione(evento);
				channel.write(esito);

				System.out.println(esito);
			} catch (IOException e) {
				Log.writeErrorLog(MainServer.class, "Impossibile leggere dal client. Chiusura socket improvvisa");
				if(social.getUtente()!=null) {
					social.logout();
				}
				e.printStackTrace();
				return false;
			}

		}
		else if(messaggio.equals(Nomi.AZIONE_CANCELLA_EVENTO.getNome())) {
			try {
				Evento evento = (Evento) channel.read();
				String esito = social.cancellaEvento(evento);
				channel.write(esito);

				System.out.println(esito);
			} catch (IOException e) {
				Log.writeErrorLog(MainServer.class, "Impossibile leggere dal client. Chiusura socket improvvisa");
				if(social.getUtente()!=null) {
					social.logout();
				}
				e.printStackTrace();
				return false;
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
				Log.writeErrorLog(MainServer.class, "Impossibile leggere dal client. Chiusura socket improvvisa");
				if(social.getUtente()!=null) {
					social.logout();
				}
				e.printStackTrace();
				return false;
			}
		}

		else if(messaggio.equals(Nomi.AZIONE_CANCELLA_NOTIFICA.getNome())) {
			try {
				Integer index = (Integer) channel.read();
				String esito = social.cancellaNotifica(index.intValue());
				channel.write(esito);
				System.out.println(esito);
			}catch(IOException e) {
				Log.writeErrorLog(MainServer.class, "Impossibile leggere dal client. Chiusura socket improvvisa");
				if(social.getUtente()!=null) {
					social.logout();
				}
				e.printStackTrace();
				return false;
			}
		}

		else if(messaggio.equals(Nomi.AZIONE_MODIFICA_PROFILO.getNome())) {
			try {
				ModelModificaUtente mmu = (ModelModificaUtente) channel.read();
				String esito = social.modificaUtente(mmu.getEtaMin(), mmu.getEtaMax(), mmu.isCatPartita(), mmu.isCatEscursione());
				channel.write(esito);
				System.out.println(esito);
			}catch(IOException e) {
				Log.writeErrorLog(MainServer.class, "Impossibile leggere dal client. Chiusura socket improvvisa");
				if(social.getUtente()!=null) {
					social.logout();
				}
				e.printStackTrace();
				return false;
			}
		}

		Log.writeRoutineLog(MainServer.class, "invio aggiornamenti", 2);
		

		invioCategorie();
		invioNotifiche();

		return true;
	}
	
	public void invioCategorie() {
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
				}
				channel.write(Nomi.NET_EOL.getNome());
			}
			else if(cat.getNome().equals(Nomi.CAT_ESCURSIOME_MONTAGNA.getNome())) {
				for(EscursioneMontagnaEvento eme: (List<EscursioneMontagnaEvento>) cat.getBacheca()) {
					channel.write(Nomi.NET_LIST_CONTINUA.getNome());
					channel.write(eme);
				}
				channel.write(Nomi.NET_EOL.getNome());
			}


		}
		channel.write(Nomi.NET_EOL.getNome());
	}

	public void invioNotifiche() {
		for(Notifica not: social.getUtente().getNotifiche()) {
			channel.write(Nomi.NET_LIST_CONTINUA.getNome());
			channel.write(not);
		}
		channel.write(Nomi.NET_EOL.getNome());
	}
	
	public void invioTitoli() {
		channel.write(social.titoliCategorie());
	}
	
	public void invioUtente() {
		channel.write(social.getUtente());
	}

}
