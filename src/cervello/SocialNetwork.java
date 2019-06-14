package cervello;

import java.util.*;

import database.ConsultaDB;
import database.NomiDB;

public class SocialNetwork {
	// UML QUANDO E' FINITO
	private Hashtable<String, Categoria> categorie;
	private Utente utente;
	private ConsultaDB consultaDB = new ConsultaDB();
	private Hashtable<String, LinkedList<Notifica>> notificheDaInoltrare;
	public static final String IMPOSSIBILE_CANCELLARE_EVENTO = "impossibile cancellare evento";

	public static final String BENVENUTO = "BENVENUTO";
	public static final String ID_INESISTENTE = "ATTENZIONE! : username inesistente";
	public static final String ID_CORTO = "ATTENZIONE! : id troppo corto";
	public static final String PW_SBAGLIATA = "ATTENZIONE! : password sbalgiata";
	public static final String PW_CORTA = "ATTENZIONE! : passoword troppo corta";
	public static final String ID_IN_USO = "ATTENZIONE! : username in uso";
	public static final String PW_DIVERSE = "ATTENZIONE! : password diverse";
	public static final String NOTIFICA_CANCELLATA = "Notifica cancellata";
	public static final String ETAMIN_MAGG_ETAMAX = "Eta minima maggiore di eta massima";
	public static final String INVITI_SPEDITI = "Inviti spediti";

	public SocialNetwork() {
		categorie = new Hashtable<String, Categoria>();
		PartitaCalcioCat pdc = (PartitaCalcioCat) consultaDB.leggiCategoria(NomiDB.CAT_PARTITA_CALCIO.getNome());
		EscursioneMontagnaCat emc = (EscursioneMontagnaCat) consultaDB.leggiCategoria(NomiDB.CAT_ESCURSIOME_MONTAGNA.getNome());
		categorie.put(pdc.getNome(), pdc);
		categorie.put(emc.getNome(), emc);
		notificheDaInoltrare = consultaDB.leggiNotifichePendenti();
		aggiornamentoEventi(); // aggiorna tutti gli eventi
		// quando vengono caricate gli eventi bisogna fare un controllo sulle notifiche
	}

	/**
	 * permette di fare il login
	 * @param id
	 * @param hash
	 * @return torna l'esito del login
	 * BENVENUTO se e' riuscito
	 * PW_SBAGLIATA se la password e' sbagliata
	 * ID_INESISTENTE se l'id non esiste
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
	 * ID_CORTO l'id troppo corto
	 * PW_CORTA password troppo corta
	 * PW_DIVERSE password diverse
	 * ETAMIN_MAGG_ETAMAX eta' minima maggiore di quella massim
	 * BENVENUTO se la registrazione avviene con successo
	 * ID_IN_USO id gia' in uso
	 */
	public String registrazione(String username, byte[] hash, byte[] conferma,String minEta,String maxEta,String[] categoriePref) {
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

			if(!Campo.controlloIntero(minEta).equals(Campo.OK))
				return Campo.controlloIntero(minEta);
			if(!Campo.controlloIntero(maxEta).equals(Campo.OK))
				return Campo.controlloIntero(maxEta);
			int etaMin = Integer.parseInt(minEta);
			int etaMax = Integer.parseInt(maxEta);
			if(etaMin>etaMax)
				return ETAMIN_MAGG_ETAMAX;
			consultaDB.aggiungiUtente(username, hash, etaMin, etaMax, categoriePref);//da aggiungere minEta , maxEta, categoriePref !!!!
			for(String cat: categoriePref) {
				categorie.get(cat).addPersonaInteressata(username);
			}
			consultaDB.salvaCategorie(categorie);
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
		System.out.println("login di " + utente.getUsername());
		aggiornamentoUtente();
	}

	public void logout() {
		salvaTutto();
		utente = null;

	}

	/**
	 * salva tutto lo stato di social network
	 * @return
	 */
	public int salvaTutto() {
		consultaDB.salvaUtente(utente);
		consultaDB.salvaNotifichePendenti(notificheDaInoltrare);
		consultaDB.salvaCategorie(categorie);
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

	/**
	 * caso specifico di iscrizione per gli oggetti EscursioneMontagnaEvento
	 * @param evento
	 * @param istr
	 * @param attr
	 * @return
	 */
	public String iscrizione(EscursioneMontagnaEvento evento, boolean istr, boolean attr) {
		String messaggio = evento.iscrizione(utente.getUsername(), istr, attr);
		utente.riceviNotifica(new Notifica(evento, messaggio));
		if (evento.cambioStato() != null)
			aggiornamentoNotifiche(evento.cambioStato());
		consultaDB.scriviEvento(evento, NomiDB.CAT_ESCURSIOME_MONTAGNA.getNome());
		System.out.print("scrittura iscrizione emc");
		consultaDB.salvaUtente(utente);		
		return messaggio;
	}


	/**
	 * serve per far iscrivere l'utente all'evento, invia la notifica all'utente e
	 * se l'evento si chiude aggiorna anche tutti gli altri utenti tramite notifica
	 * @param evento
	 * @return l'esito dell'iscrizione:
	 * Notifica.ISCRIZIONE se e' andata a buon fine
	 * Notifica.ISCRIZIONE_GIA_FATTA se l'utente era gia' precedentemente iscritto
	 * Notifica.ERRORE_DI_ISCRIZIONE se ci sono stati errori nell'iscrizione
	 */
	public String iscrizione(Evento evento) {

		String messaggio = evento.iscrizione(utente.getUsername());
		utente.riceviNotifica(new Notifica(evento, messaggio));
		if (evento.cambioStato() != null)
			aggiornamentoNotifiche(evento.cambioStato());
		consultaDB.scriviEvento(evento, NomiDB.CAT_PARTITA_CALCIO.getNome());
		System.out.print("scrittura iscrizione ");
		consultaDB.salvaUtente(utente);
		return messaggio;
	}

	/**
	 * crea un evento e viene aggiunta alla bacheca dalla propria categoria
	 * manda un invito a tutte le persone che sono state invitate, una notifica
	 * alle persone che sono interessate a quella categoria di eventi e salva 
	 * l'evento
	 * 
	 * @param evento
	 * @param personeInvitate
	 */
	public void addEvento(Evento evento, LinkedList<String> personeInvitate) {
		String nome = utente.getUsername();
		Notifica notificaIscrizione = new Notifica(evento, evento.iscrizione(nome));
		evento.setProprietario(nome);
		if (evento.getClass().equals(PartitaCalcioEvento.class))
			consultaDB.scriviEvento(evento, NomiDB.CAT_PARTITA_CALCIO.getNome());
		else if(evento.getClass().equals(EscursioneMontagnaEvento.class))
			consultaDB.scriviEvento(evento, NomiDB.CAT_ESCURSIOME_MONTAGNA.getNome());
		utente.riceviNotifica(notificaIscrizione);
		utente.creaEvento(evento.getIdEvento());
		invitaUtenti(personeInvitate, evento);
		if (evento.getClass().equals(PartitaCalcioEvento.class)) {
			categorie.get(NomiDB.CAT_PARTITA_CALCIO.getNome()).aggiungiEvento(evento);
			informaInteressati(NomiDB.CAT_PARTITA_CALCIO.getNome(), evento);
		}
		else if(evento.getClass().equals(EscursioneMontagnaEvento.class)) {
			categorie.get(NomiDB.CAT_ESCURSIOME_MONTAGNA.getNome()).aggiungiEvento(evento);
			informaInteressati(NomiDB.CAT_ESCURSIOME_MONTAGNA.getNome(), evento);
		}

	}

	/**
	 * invia una notifica a tutti gli utenti iscritti all'evento
	 * @param notifica
	 */
	public void aggiornamentoNotifiche(Notifica notifica) {
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
	 * aggiorna tutti gli stati di tutti gli eventi inviando in caso le varie notifiche
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
	 * cancella la notifica dell'utente
	 * @param notifica
	 * @return NOTIFICA_CANCELLATA
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

	/**
	 * se l'utente e' il proprietario dell'evento viene cancellato l'evento e 
	 * tutti i partecipanti vengono avvertiti tramite notifica, se e' scaduto
	 * il termine ultimo di ritiro non viene cancellato, se l'utente non
	 * è' il proprietario non succede nulla, in tutti i casi all'utente arriva
	 * una notifica con l'esito dell'operazione
	 * @param evento
	 * @return
	 * Notifica.PROPRIETARIO_DIVERSO se l'utente non è il proprietario dell'evento
	 * Notifica.EVENTO_CANCELLATO se l'evento e' stato cancellato
	 * Notifica.OLTRE_TUR se oltre il tur
	 */
	public String cancellaEvento(Evento evento) {
		Notifica ritorno = evento.cancella(utente.getUsername());
		utente.riceviNotifica(ritorno);
		aggiornamentoNotifiche(ritorno);
		System.out.print("cancella evento scrittura ");
		if (evento.getClass().equals(PartitaCalcioEvento.class))
			consultaDB.scriviEvento(evento, NomiDB.CAT_PARTITA_CALCIO.getNome());
		else if(evento.getClass().equals(EscursioneMontagnaEvento.class))
			consultaDB.scriviEvento(evento, NomiDB.CAT_ESCURSIOME_MONTAGNA.getNome());
		return ritorno.getMessaggio();
	}

	/**
	 * se l'utente è iscritto all'evento viene tolto dall'elenco dei partecipanti,
	 * se però e' scaduto il termine ultimo per ritirarsi non viene tolto, mentre 
	 * nel caso in cui non sia iscritto non succede nulla, in tutti e tre i casi 
	 * gli arriva la notifica del risultato del metodo
	 * @param evento
	 * @return il messaggio della notifica
	 * Notifica.OLTRE_TUR se la revoca viene fatta oltre il TUR
	 * Notifica.NON_ISCRITTO se l'utente non e' iscritto
	 * Notifica.REVOCA_ISCRIZIONE se viene revocata l'iscrizione
	 */
	public String revocaIscrizione(Evento evento) {
		Notifica ritorno = evento.revocaIscrizione(utente.getUsername());
		utente.riceviNotifica(ritorno);
		System.out.print("revoca iscrizione scrittura ");
		salvaTutto();
		return ritorno.getMessaggio();
	}

	/**
	 * si possono modificare gli attributi modificabili dell'utente, etaMin, etaMax
	 * e aggiungere e togliere interessi
	 * @param tipoAttributo
	 * @param attributoUtente
	 * @return
	 */
	public String modificaUtente(int tipoAttributo, String attributoUtente) {
		if(attributoUtente.equals(""))
			return utente.MODIFICA_RIUSCITA;
		if(tipoAttributo==Utente.ETA_MIN) {
			if(!Campo.controlloIntero(attributoUtente).equals(Campo.OK))
				return Campo.controlloIntero(attributoUtente);
			int etaMin = Integer.parseInt(attributoUtente);
			if(etaMin>utente.getEtaMax())
				return ETAMIN_MAGG_ETAMAX;
			utente.setEtaMin(etaMin);
		}
		else if(tipoAttributo==Utente.ETA_MAX) {
			if(!Campo.controlloIntero(attributoUtente).equals(Campo.OK))
				return Campo.controlloIntero(attributoUtente);
			int etaMax = Integer.parseInt(attributoUtente);
			if(etaMax<utente.getEtaMin())
				return ETAMIN_MAGG_ETAMAX;
			utente.setEtaMax(etaMax);
		}
		else if(tipoAttributo==Utente.AGGIUNGI_INTERESSE) {
			categorie.get(attributoUtente).addPersonaInteressata(utente.getUsername());
			consultaDB.salvaCategorie(categorie);
			utente.aggiungiInteresse(attributoUtente);
		}
		else if(tipoAttributo==Utente.RIMUOVI_INTERESSE) {
			categorie.get(attributoUtente).removePersonaInteressata(utente.getUsername());
			consultaDB.salvaCategorie(categorie);
			utente.removeInteresse(attributoUtente);
		}

		consultaDB.salvaUtente(getUtente());

		return Utente.MODIFICA_RIUSCITA;
	}


	/**
	 * crea la lista di persone che l'utente può invitare, quindi tutti i 
	 * partecipanti di qualsiasi altro suo evento
	 * @param categoria dell'evento
	 * @return lista degli invitabili
	 */
	public LinkedList<String> getPersoneInvitabili(String categoria){
		LinkedList<String> personeInvitabili = new LinkedList<String> ();
		Categoria cat = categorie.get(categoria);
		for(Evento e : (ArrayList<Evento>)cat.getBacheca()) { //poi tutti gli eventi
			for(String partecipante: e.getPartecipanti()) { //con tutti i loro partecipanti
				if(!personeInvitabili.contains(partecipante))
					personeInvitabili.add(partecipante);
			}
		}


		personeInvitabili.remove(utente.getUsername());
		return personeInvitabili;
	}

	/**
	 * invia l'invito all'evento a tutte le persone nella lista personeInvitate
	 * @param personeInvitate
	 * @param evento
	 * @return
	 */
	public String invitaUtenti(LinkedList<String> personeInvitate, Evento evento) {
		for(String persona: personeInvitate) {
			if(notificheDaInoltrare.get(persona)==null)
				notificheDaInoltrare.put(persona, new LinkedList<Notifica>());
			notificheDaInoltrare.get(persona).add(new Invito(evento));
		}

		System.out.println(INVITI_SPEDITI);
		return INVITI_SPEDITI;
	}

	/**
	 * invia una notifica di un nuovo evento aperto di una certa categoria a tutti
	 * gli utenti che sono interessati
	 * @param categoria
	 * @param evento
	 */
	public void informaInteressati(String categoria, Evento evento) {
		for(String interessato: (LinkedList<String>)categorie.get(categoria).getPersoneInteressate()) {
			if(notificheDaInoltrare.get(interessato)==null) 
				notificheDaInoltrare.put(interessato, new LinkedList<Notifica>());
			notificheDaInoltrare.get(interessato).add(new Notifica(evento, Notifica.NUOVO_EVENTO_APERTO));	
		}
		System.out.println("persone interessate informate");
	}















































}
