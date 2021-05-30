package server;

import java.util.*;

import lib.core.Campo;
import lib.core.Categoria;
import lib.core.EscursioneMontagnaCat;
import lib.core.EscursioneMontagnaEvento;
import lib.core.Evento;
import lib.core.Invito;
import lib.core.Notifica;
import lib.core.PartitaCalcioCat;
import lib.core.Utente;
import lib.util.ControlloCampo;
import lib.util.Log;
import lib.util.Nomi;
import server.database.ConsultaDB;

public class SocialNetwork {
	// UML QUANDO E' FINITO
	private static SocialNetwork sn;
	private Hashtable<String, Categoria> categorie;
	private Utente utente;
	private ConsultaDB consultaDB;
	private Hashtable<String, LinkedList<Notifica>> notificheDaInoltrare;
	
	private Logger logger;
	private GestisciNotificheDaInoltrare gestisciNotifiche;
	private GestioneUtente gestioneUtente;
	private GestioneEventi gestioneEventi;

	private SocialNetwork(ConsultaDB cDB) {
		this.consultaDB=cDB;
		categorie = new Hashtable<String, Categoria>();
		PartitaCalcioCat pdc = (PartitaCalcioCat) consultaDB.leggiCategoria(Nomi.CAT_PARTITA_CALCIO.getNome());
		EscursioneMontagnaCat emc = (EscursioneMontagnaCat) consultaDB.leggiCategoria(Nomi.CAT_ESCURSIOME_MONTAGNA.getNome());
		categorie.put(pdc.getNome(), pdc);
		categorie.put(emc.getNome(), emc);
		notificheDaInoltrare = consultaDB.leggiNotifichePendenti();
		logger = new Logger();
		gestisciNotifiche = new GestisciNotificheDaInoltrare();
		gestioneUtente = new GestioneUtente();
		gestioneEventi = new GestioneEventi();

		gestisciNotifiche.aggiornamentoEventi(categorie, utente, notificheDaInoltrare); // aggiorna tutti gli eventi
		// quando vengono caricate gli eventi bisogna fare un controllo sulle notifiche
	}

	public static SocialNetwork getInstance() {
		if(sn== null) 
			sn=new SocialNetwork(ConsultaDB.getInstance()) ;
		return sn;
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
	public String login(String id, String hash) {
		String ritorno = logger.login(id, hash);
		if(ritorno==Nomi.SN_BENVENUTO.getNome()) 
			setUtente(id);
		return ritorno;		
	}

	/**
	 * serve per registrare un nuovo utente
	 * @param username
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
	public String registrazione(String username, String hash, String conferma,int minEta,int maxEta,String[] categoriePref) {
		String ritorno = logger.registrazione(username, hash, conferma, minEta, maxEta, categoriePref, categorie);
		if(ritorno==Nomi.SN_BENVENUTO.getNome()) 
			setUtente(username);
		return ritorno;
	}

	/*
	 * private perchè non puoi entrare senza autentificarti
	 * 
	 */
	private void setUtente(String id) {
		utente = consultaDB.caricaUtente(id);
		Log.writeRoutineLog(this.getClass(), "login di " + utente.getUsername(), Log.MEDIUM_PRIORITY);
		gestisciNotifiche.aggiornamentoUtente(utente, notificheDaInoltrare);
	}

	public void logout() {
		salvaTutto();
		Log.writeRoutineLog(this.getClass(), "logut di " + utente.getUsername(), Log.MEDIUM_PRIORITY);
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

	public void eliminaUtente(String username) {
		gestioneUtente.eliminaUtente(categorie, username);
	}

	public void eliminaEvento(Evento evento) {
		consultaDB.eliminaEvento(evento);
	}

	/**
	 * @return i titoli delle categorie
	 */
	public List<String> titoliCategorie() {
		ArrayList<String> ritorno = new ArrayList<String>();
		ritorno.addAll(categorie.keySet());
		return ritorno;
	}

	public List<Categoria> getCategorie(){
		ArrayList<Categoria> ritorno = new ArrayList<Categoria>();
		for(String key : categorie.keySet()) {
			ritorno.add(categorie.get(key));
		}
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
	 * check degli utenti registrati da file
	 * @param id
	 * @return true se c'è false se non c'è
	 */
	public boolean controllaID(String username) {
		return consultaDB.controllaID(username);
	}


	/**
	 * caso specifico di iscrizione per gli oggetti EscursioneMontagnaEvento
	 * @param evento
	 * @param istr
	 * @param attr
	 * @return
	 */
	public String iscrizione(EscursioneMontagnaEvento evento, boolean istr, boolean attr) {
		String ritorno = gestioneEventi.iscrizione(evento, istr, attr, categorie, utente, notificheDaInoltrare);
		return ritorno;
	}


	/**
	 * serve per far iscrivere l'utente all'evento, invia la notifica all'utente e
	 * se l'evento si chiude aggiorna anche tutti gli altri
	 * @param evento
	 * @return l'esito dell'iscrizione:
	 * Notifica.ISCRIZIONE se e' andata a buon fine
	 * Notifica.ISCRIZIONE_GIA_FATTA se l'utente era gia' precedentemente iscritto
	 * Notifica.ERRORE_DI_ISCRIZIONE se ci sono stati errori nell'iscrizione
	 */
	public String iscrizione(Evento evento) {
		String ritorno = gestioneEventi.iscrizione(evento, categorie, utente, notificheDaInoltrare);
		return ritorno;
	}

	/**
	 * crea un evento e viene aggiunta alla bacheca della propria categoria
	 * manda un invito a tutte le persone che sono state invitate, una notifica
	 * alle persone che sono interessate a quella categoria di eventi e salva 
	 * l'evento
	 * 
	 * @param evento
	 * @param personeInvitate
	 */
	public String creaEvento(Evento evento, List<String> personeInvitate) {
		utente = gestioneEventi.creaEvento(evento, personeInvitate, categorie, utente, notificheDaInoltrare);
		return "Evento creato";

	}

	/**
	 * cancella la notifica dell'utente
	 * @param notifica
	 * @return NOTIFICA_CANCELLATA
	 */
	public String cancellaNotifica(int index) {
		utente = gestioneUtente.cancellaNotifica(index, utente);
		return Nomi.SN_NOTIFICA_CANCELLATA.getNome();
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
		
		return gestioneEventi.cancellaEvento(evento, utente, categorie, notificheDaInoltrare);
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
		return gestioneEventi.revocaIscrizione(evento, utente, categorie);
	}

	
	public String modificaUtente(int etaMin, int etaMax, boolean catPartita, boolean catEscursione) {
		String ritorno = gestioneUtente.modificaUtente(etaMin, etaMax, catPartita, catEscursione, categorie, utente);
		if(ritorno.equals(Utente.MODIFICA_RIUSCITA))
			utente = gestioneUtente.getUtente();		
		return ritorno;
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
				if(!utente.getUsername().equals(e.getProprietario()))
					continue;
				if(!personeInvitabili.contains(partecipante))
					personeInvitabili.add(partecipante);
			}
		}
		if(personeInvitabili.contains(utente.getUsername()))
			personeInvitabili.remove(utente.getUsername());
		return personeInvitabili;
	}

	public void aggiornaCategoria(Evento e) {
		String cat = e.getCategoria();
		categorie.put(cat, consultaDB.leggiCategoria(cat));
	}

	public void stampaEventi() {
		for(String key: categorie.keySet()) {
			for(Evento e: (List<Evento>) categorie.get(key).getBacheca()) {
				System.out.println(e.getTitolo().getValore() + " " +  e.getStato());
			}
		}
	}














}
