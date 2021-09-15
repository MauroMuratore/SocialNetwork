package server;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import lib.core.Categoria;
import lib.core.EscursioneMontagnaEvento;
import lib.core.Evento;
import lib.core.Notifica;
import lib.core.Utente;
import lib.util.Log;
import server.database.ConsultaDB;

public class GestioneEventi {
	
	private ConsultaDB consultaDB;
	private GestioneNotificheDaInoltrare gestisciNotifiche;
	
	public GestioneEventi() {
		consultaDB = ConsultaDB.getInstance();
		gestisciNotifiche = new GestioneNotificheDaInoltrare();
	}
	
	public void aggiornaCategoria(Evento e, Hashtable<String, Categoria> categorie) {
		String cat = e.getCategoria();
		categorie.put(cat, consultaDB.leggiCategoria(cat));
	}
	
	/**
	 * caso specifico di iscrizione per gli oggetti EscursioneMontagnaEvento
	 * @param evento
	 * @param istr
	 * @param attr
	 * @return
	 */
	public String iscrizione(EscursioneMontagnaEvento evento, boolean istr, boolean attr, Hashtable<String, Categoria> categorie, Utente utente, Hashtable<String, LinkedList<Notifica>> notificheDaInoltrare) {
		String messaggio = evento.iscrizione(utente.getUsername(), istr, attr);
		gestisciIscrizione(evento, messaggio, utente, notificheDaInoltrare);	
		aggiornaCategoria(evento, categorie);
		return messaggio;
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
	public String iscrizione(Evento evento, Hashtable<String, Categoria> categorie, Utente utente, Hashtable<String, LinkedList<Notifica>> notificheDaInoltrare) {
		String messaggio = evento.iscrizione(utente.getUsername());
		gestisciIscrizione(evento, messaggio, utente, notificheDaInoltrare);
		aggiornaCategoria(evento, categorie);
		return messaggio;
	}
	
	public void gestisciIscrizione(Evento evento, String messaggio, Utente utente, Hashtable<String, LinkedList<Notifica>> notificheDaInoltrare) {
		utente.riceviNotifica(new Notifica(evento, messaggio));
		Notifica not = evento.cambioStato();
		if (not!= null) {
			gestisciNotifiche.aggiornamentoNotifiche(not, utente, notificheDaInoltrare);
		}
		consultaDB.scriviEvento(evento);
		consultaDB.salvaUtente(utente);
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
	public String cancellaEvento(Evento evento, Utente utente, Hashtable<String, Categoria> categorie, Hashtable<String, LinkedList<Notifica>> notificheDaInoltrare) {
		Notifica ritorno = evento.cancella(utente.getUsername());
		utente.riceviNotifica(ritorno);
		gestisciNotifiche.aggiornamentoNotifiche(ritorno, utente, notificheDaInoltrare);
		Log.writeRoutineLog(this.getClass(), ritorno.getMessaggio(), Log.HIGH_PRIORITY);
		consultaDB.scriviEvento(evento);
		aggiornaCategoria(evento, categorie);
		return ritorno.getMessaggio();
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
	public Utente creaEvento(Evento evento, List<String> personeInvitate, Hashtable<String, Categoria> categorie ,Utente utente, Hashtable<String, LinkedList<Notifica>> notificheDaInoltrare) {
		String nome = utente.getUsername();
		evento.iscrizione(nome);
		Notifica notificaIscrizione = new Notifica(evento, nome);
		evento.setProprietario(nome);
		consultaDB.scriviEvento(evento);
		utente.riceviNotifica(notificaIscrizione);
		utente.creaEvento(evento.getIdEvento());
		gestisciNotifiche.invitaUtenti(personeInvitate, evento, notificheDaInoltrare);
		categorie.get(evento.getCategoria()).aggiungiEvento(evento);
		gestisciNotifiche.informaInteressati(evento, categorie, notificheDaInoltrare);

		return utente;

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
	public String revocaIscrizione(Evento evento, Utente utente , Hashtable<String, Categoria> categorie) {
		Notifica ritorno = evento.revocaIscrizione(utente.getUsername());
		utente.riceviNotifica(ritorno);
		Log.writeRoutineLog(this.getClass(), "revoca iscrizione scrittura ", Log.MEDIUM_PRIORITY);
		consultaDB.scriviEvento(evento);
		aggiornaCategoria(evento, categorie);
		return ritorno.getMessaggio();
	}

}
