package cervello;

import java.util.*;

import database.ConsultaDB;

public class SocialNetwork {
	// UML QUANDO E' FINITO
	private Hashtable<String, Categoria> categorie;
	private Utente utente;
	private ConsultaDB consultaDB = new ConsultaDB();
	private Hashtable<String, LinkedList<Notifica>> notificheDaInoltrare;

	public static final String KEY_CATEGORIA_PARTITA_CALCIO = "Partita calcio";

	public static final String BENVENUTO = "BENVENUTO";
	public static final String ID_INESISTENTE = "ATTENZIONE! : username inesistente";
	public static final String ID_CORTO = "ATTENZIONE! : id troppo corto";
	public static final String PW_SBAGLIATA = "ATTENZIONE! : password sbalgiata";
	public static final String PW_CORTA = "ATTENZIONE! : passoword troppo corta";
	public static final String ID_IN_USO = "ATTENZIONE! : username in uso";
	public static final String PW_DIVERSE = "ATTENZIONE! : password diverse";
	public static final String NOTIFICA_CANCELLATA = "Notifica cancellata";
	public static final String IMPOSSIBILE_CANCELLARE_EVENTO = "impossibile cancellare evento";

	public SocialNetwork() {
		categorie = new Hashtable<String, Categoria>();
		PartitaCalcioCat pdc = consultaDB.getPartitaCalcioCat();
		categorie.put(pdc.getNome(), pdc);
		notificheDaInoltrare = new Hashtable<String, LinkedList<Notifica>>();
		aggiornamentoEventi(); // aggiorna tutti gli eventi
		// quando vengono caricate gli eventi bisogna fare un controllo sulle notifiche
	}

	/**
	 * permette di fare il login
	 * 
	 * @param id
	 * @param hash
	 * @return torna l'esito del login
	 */
	public String login(String id, byte[] hash) {

		if (consultaDB.controllaID(id)) {
			if (consultaDB.controllaPW(hash)) {
				setUtente(id);
				return BENVENUTO;
			} else
				return PW_SBAGLIATA;
		} else
			return ID_INESISTENTE;
	}

	/**
	 * serve per registrare un nuovo utente
	 * 
	 * @param username
	 * 
	 * @param hash
	 * @param conferma
	 * @return esito della registrazione
	 */
	public String registrazione(String username, byte[] hash, byte[] conferma) {
		if (!consultaDB.controllaID(username))// controllo se ce gia id nel database
		{
			if (username.length() < 7)
				return ID_CORTO;
			String hashString = new String(hash);
			if (hashString.length() < 7) {
				return PW_CORTA;
			}
			boolean uguali = true;
			if (hash.length != conferma.length)
				return PW_DIVERSE;
			else {
				for (int i = 0; i < hash.length; i++) {
					if (hash[i] != conferma[i]) // controllo byte per byte se hash e conferma sono uguali
						uguali = false;
				}
				if (uguali = false)
					return PW_DIVERSE;
			}

			consultaDB.aggiungiUtente(username, hash);
			setUtente(username);
			return BENVENUTO;
		} else
			return ID_IN_USO;
	}

	/*
	 * private perchè non puoi entrare senza autentificarti
	 * 
	 */
	private void setUtente(String id) {
		utente = consultaDB.caricaUtente(id);
		System.out.println(utente.getUsername());
		aggiornamentoUtente();
	}

	// TUTTI I SALVATAGGI VANNO FATTI
	public void logout() {
		utente = null;
	}

	public int salvaTutto() {
		consultaDB.salvaUtente(utente);
		consultaDB.salvaNotifichePendenti(notificheDaInoltrare);
		return 0;
	}

	/**
	 * @return i titoli delle categorie
	 */
	public List<String> titoliCategorie() {
		ArrayList<String> ritorno = new ArrayList<String>();
		ritorno.addAll(categorie.keySet());
		return ritorno;
	}

	/**
	 * @param categoria
	 * @return la categoria
	 */
	public Categoria mostraCategoria(String categoria) {
		return categorie.get(categoria);
	}

	public String iscrizione(Evento evento) {

		String messaggio = evento.iscrizione(utente.getUsername());
		utente.riceviNotifica(new Notifica(evento, messaggio));
		if (evento.cambioStato() != null)
			aggiornamentoNotifiche(evento.cambioStato());
		consultaDB.scriviEvento((PartitaCalcioEvento) evento);
		System.out.print("scrittura iscrizione ");
		consultaDB.salvaUtente(utente);
		return messaggio;
	}

	public void addEvento(Evento evento) {
		String nome = utente.getUsername();
		Notifica notificaIscrizione = new Notifica(evento, evento.iscrizione(nome));
		evento.setProprietario(nome);
		utente.riceviNotifica(notificaIscrizione);
		Notifica notificaStato = evento.cambioStato();
		if (notificaStato != null)
			utente.riceviNotifica(notificaStato);
		consultaDB.scriviEvento((PartitaCalcioEvento) evento);
		if (evento.getClass() == PartitaCalcioEvento.class)
			categorie.get(KEY_CATEGORIA_PARTITA_CALCIO).aggiungiEvento((PartitaCalcioEvento) evento);

	}

	/**
	 * invia una notifica agli utenti iscritti
	 * 
	 * @param notifica
	 */
	public void aggiornamentoNotifiche(Notifica notifica) {
		for (String nome : notifica.getEvento().getPartecipanti()) {
			if (utente == null)
				return;
			else if (nome.equals(utente.getUsername())) { // se l'utente è quello loggato gli invio la notifica
				utente.riceviNotifica(notifica);
			} else { // altrimenti la salvo il notifiche da inoltrare
				if (notificheDaInoltrare.containsKey(nome)) { // se l'utente ha già altre notifiche da inoltrare
																// aggiungo la notifica
					LinkedList<Notifica> lista = notificheDaInoltrare.get(nome);
					lista.add(notifica);
				} else { // se l'utente non ha altre notifiche creo la lista e aggiungo la coppia nome
							// lista a notificaDaInoltrare
					LinkedList<Notifica> lista = new LinkedList<Notifica>();
					lista.add(notifica);
					notificheDaInoltrare.put(nome, lista);
				}

			}

		}
	}

	/**
	 * manda tutte le notifiche presenti in notificheDaInoltrare all'utente
	 */
	public void aggiornamentoUtente() {
		if (notificheDaInoltrare == null) {
			return;
		} else if (!notificheDaInoltrare.containsKey(utente.getUsername())) {
			return;
		} // se non contiene l'username dell'utente allora non ci sono notifiche per lui
		LinkedList<Notifica> lista = notificheDaInoltrare.get(utente.getUsername());
		for (Notifica notifica : lista) {
			utente.riceviNotifica(notifica);
		}
		notificheDaInoltrare.remove(utente.getUsername()); // dopo che l'utente ha ricevuto tutte le notifiche lo tolgo
	}

	/**
	 * aggiorna tutti gli stati di tutti gli eventi inviando le varie notifiche
	 */
	public void aggiornamentoEventi() {
		for (String key : categorie.keySet()) {
			List<Evento> bacheca = categorie.get(key).getBacheca();
			for (int i = 0; i < bacheca.size(); i++) {
				Notifica not = bacheca.get(i).cambioStato();
				if (not != null)
					aggiornamentoNotifiche(not);
			}
		}
	}

	/**
	 * cancella la notifica
	 * 
	 * @param notifica
	 * @return
	 */
	public String cancellaNotifica(Notifica notifica) {
		utente.cancellaNotifica(notifica);
		System.out.print("cancello notifica ");
		consultaDB.cancellaNotifica(notifica, utente);

		return NOTIFICA_CANCELLATA;
	}

	public Utente getUtente() {
		return utente;
	}

	public String cancellaEvento(Evento evento) {
		Notifica ritorno = evento.cancella(utente.getUsername());
		utente.riceviNotifica(ritorno);
		aggiornamentoNotifiche(ritorno);
		System.out.print("cancella evento scrittura ");
		consultaDB.scriviEvento((PartitaCalcioEvento) evento);
		//consultaDB.cancellaEvento(evento);
		return ritorno.getMessaggio();
	}

	public String revocaIscrizione(Evento evento) {
		Notifica ritorno = evento.revocaIscrizione(utente.getUsername());
		utente.riceviNotifica(ritorno);
		System.out.print("revoca iscrizione scrittura ");
		salvaTutto();
		return ritorno.getMessaggio();
	}

}
