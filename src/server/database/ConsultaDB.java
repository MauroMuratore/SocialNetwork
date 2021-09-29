package server.database;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import lib.core.Categoria;
import lib.core.Evento;
import lib.core.Notifica;
import lib.core.PartitaCalcioCat;
import lib.core.PartitaCalcioEvento;
import lib.core.Utente;
import lib.util.Nomi;

public class ConsultaDB {

	private static ConsultaDB istanza;
	private Element nodo;
	private LeggiXML lettura = new LeggiXML();
	private ScriviXML scrittura = new ScriviXML();
	
	public static ConsultaDB getInstance() {
		if(istanza==null) {
			istanza = new ConsultaDB();
		}
		
		return istanza;
	}
	
	private ConsultaDB() {

	}

	/**
	 * lettura degli utenti registrati da file
	 * @param id
	 * @return true se c'è false se non c'è
	 */
	public boolean controllaID(String username) {
		return lettura.controllaUtente(username);
	}

	public boolean controllaPW(String pw, String username) {
		return lettura.controllaPW(pw, username);
		
	}

	/**
	 * caricare i dati di un utente
	 * @param id
	 * @return
	 */
	public Utente caricaUtente(String id) {
		return lettura.caricaUtente(id);
	}

	public Categoria leggiCategoria(String categoria) {
		return lettura.leggiCategoria(categoria);
	}
	
	public void scriviEvento(Evento e) {
		scrittura.scriviEvento(e);
	}
	
	public void salvaUtente(Utente utente) {
		scrittura.salvaUtente(utente);
	}
	
	public boolean controllaEvento(int id) {
		boolean ritorno=false;
		if(lettura.cercaEvento(id, Nomi.CAT_PARTITA_CALCIO.getNome())==null) {
			ritorno= true;
		}else if(lettura.cercaEvento(id, Nomi.CAT_ESCURSIOME_MONTAGNA.getNome())==null) {
			ritorno=true;
		}
		return ritorno;
	}

	public void salvaNotifichePendenti(Map<String, LinkedList<Notifica>> notificheDaInoltrare) {
		scrittura.scriviNotifichePendenti(notificheDaInoltrare);
	}

	public void cancellaNotifica(Notifica notifica, Utente utente) {
		scrittura.cancellaNotifica(notifica, utente);
	}
	
	public void eliminaUtente(String username) {
		scrittura.cancellaUtente(username);
	}
	
	public void eliminaEvento(Evento evento) {
		scrittura.eliminaEvento(evento);
	}
	
	public Hashtable<String, LinkedList<Notifica>> leggiNotifichePendenti() {
		return lettura.leggiNotifichePendenti();
		
	}

	public void salvaCategorie(Map<String, Categoria> categorie) {
		for(String key: categorie.keySet()) {
			scrittura.scriviCategoria(categorie.get(key));
		}
		
	}

	
}
