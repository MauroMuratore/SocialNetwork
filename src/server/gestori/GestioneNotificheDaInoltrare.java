package server.gestori;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import lib.core.categorie.Categoria;
import lib.core.eventi.EscursioneMontagnaEvento;
import lib.core.eventi.Evento;
import lib.core.utenti.Invito;
import lib.core.utenti.Notifica;
import lib.core.utenti.Utente;
import lib.util.Log;
import lib.util.Nomi;

public class GestioneNotificheDaInoltrare {
	
	
	
	public GestioneNotificheDaInoltrare() {
		
	}
	
	public void aggiornamentoNotifiche(Notifica notifica, Utente utente, Hashtable<String, LinkedList<Notifica>> not) {
		for (String nome : notifica.getEvento().getPartecipanti()) {
			//calcolo qui quanto deve pagare
			if(notifica.getMessaggio().contains(Notifica.COSTO_FINALE)) {
				EscursioneMontagnaEvento evento = (EscursioneMontagnaEvento) notifica.getEvento();
				int costo = evento.getQuotaIndividuale().getValore().intValue();
				if(evento.getListaPerIstruttore().contains(nome)) {
					costo += evento.getIstruttore().getValore().intValue();
				}
				if(evento.getListaPerAttrezzature().contains(nome)) {
					costo += evento.getAttrezzatura().getValore().intValue();
				}
				notifica = new Notifica(evento, notifica.getMessaggio() + costo);
			}
			if (utente == null)
				return;
			else if (nome.equals(utente.getUsername())) { // se l'utente è quello loggato gli invio la notifica
				utente.riceviNotifica(notifica);
			} else { // altrimenti la salvo il notifiche da inoltrare
				if (not.containsKey(nome)) { // se l'utente ha già altre notifiche da inoltrare
					// aggiungo la notifica
					LinkedList<Notifica> lista = not.get(nome);
					lista.add(notifica);
				} else { // se l'utente non ha altre notifiche creo la lista e aggiungo la coppia nome
					// lista a notificaDaInoltrare
					LinkedList<Notifica> lista = new LinkedList<Notifica>();
					lista.add(notifica);
					not.put(nome, lista);
				}
			}
		}
	}
	

	/**
	 * manda tutte le notifiche presenti in notificheDaInoltrare all'utente
	 */
	public void aggiornamentoUtente(Utente utente, Hashtable<String, LinkedList<Notifica>> not) {
		if (not == null) {
			return;
		} else if (!not.containsKey(utente.getUsername())) {
			return;
		} // se non contiene l'username dell'utente allora non ci sono notifiche per lui
		LinkedList<Notifica> lista = not.get(utente.getUsername());
		for (Notifica notifica : lista) {
			utente.riceviNotifica(notifica);
		}
		not.remove(utente.getUsername()); // dopo che l'utente ha ricevuto tutte le notifiche lo tolgo
	}
	
	/**
	 * aggiorna tutti gli stati di tutti gli eventi inviando in caso le varie notifiche
	 */
	public void aggiornamentoEventi(Hashtable<String, Categoria> categorie, Utente utente, Hashtable<String, LinkedList<Notifica>> notifiche) {
		for (String key : categorie.keySet()) {
			List<Evento> bacheca = categorie.get(key).getBacheca();
			for (int i = 0; i < bacheca.size(); i++) {
				Notifica not = bacheca.get(i).cambioStato();
				if (not != null)
					aggiornamentoNotifiche(not, utente, notifiche);
			}
		}
	}
	
	/**
	 * invia una notifica di un nuovo evento aperto di una certa categoria a tutti
	 * gli utenti che sono interessati
	 * @param categoria
	 * @param evento
	 */
	public void informaInteressati(Evento evento, Hashtable<String, Categoria> categorie, Hashtable<String, LinkedList<Notifica>> not) {
		for(String interessato: (LinkedList<String>)categorie.get(evento.getCategoria()).getPersoneInteressate()) {
			if(not.get(interessato)==null) 
				not.put(interessato, new LinkedList<Notifica>());
			not.get(interessato).add(new Notifica(evento, Notifica.NUOVO_EVENTO_APERTO + " "+evento.getTitolo().getValoreString()));	
		}

		Log.writeRoutineLog(this.getClass(),"persone interessate informate", Log.MEDIUM_PRIORITY);
	}
	
	/**
	 * invia l'invito all'evento a tutte le persone nella lista personeInvitate
	 * @param personeInvitate
	 * @param evento
	 * @return
	 */
	public String invitaUtenti(List<String> personeInvitate, Evento evento, Hashtable<String, LinkedList<Notifica>> not) {
		for(String persona: personeInvitate) {
			if(not.get(persona)==null)
				not.put(persona, new LinkedList<Notifica>());
			not.get(persona).add(new Invito(evento));
		}

		Log.writeRoutineLog(this.getClass(), Nomi.SN_INVITI_SPEDITI.getNome(), Log.MEDIUM_PRIORITY);
		return Nomi.SN_INVITI_SPEDITI.getNome();
	}
	
	

}
