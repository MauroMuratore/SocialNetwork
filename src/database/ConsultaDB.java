package database;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.LinkedList;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import cervello.Categoria;
import cervello.Evento;
import cervello.Notifica;
import cervello.PartitaCalcioCat;
import cervello.PartitaCalcioEvento;
import cervello.Utente;

public class ConsultaDB {

	private Element nodo;
	private LeggiXML lettura = new LeggiXML();
	private ScriviXML scrittura = new ScriviXML();

	//TODO SI POTREBBE PRIVATO E FARE UN COSTRUTTORE A PARTE CHE FACCIA CREARE UN SOLO OGGETTO INTERROGAZIONE
	public ConsultaDB() {

	}

	/**
	 * lettura degli utenti registrati da file
	 * @param id
	 * @return true se c'è false se non c'è
	 */
	public boolean controllaID(String id) {
		nodo = lettura.leggiUtente(id);
		if(nodo==null) return false;
		return true;
	}

	public boolean controllaPW(byte[] hash) {
		Element hashNode = (Element) nodo.getElementsByTagName(NomiDB.TAG_HASH.getNome()).item(0);
		String conferma = hashNode.getTextContent();
		byte[] confByte=null;
		try {
			confByte = conferma.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(hash.length!=confByte.length) return false;
		for(int i=0; i<hash.length; i++) {
			if(hash[i]!=
					confByte[i])
				return false;
		}
		return true;
	}


	public void aggiungiUtente(String id, byte[] pw, int etaMin, int etaMax, String[] categoriePref)//questo metondo crea e aggiunge un utente al database e ritorna l'oggetto utente creato
	{
		scrittura.aggiungiUtente(id, pw, etaMin, etaMax, categoriePref);
	}

	/**
	 * caricare i dati di un utente
	 * @param id
	 * @return
	 */
	public Utente caricaUtente(String id) {
		return lettura.caricaUtente(id);
	}

	public PartitaCalcioCat getPartitaCalcioCat() {
		return lettura.leggiPartitaCalcioCat();
	}
	
	public void scriviEvento(PartitaCalcioEvento pce) {
		scrittura.scriviPartitaCalcioEvento(pce);
	}
	
	public void salvaUtente(Utente utente) {
		scrittura.salvaUtente(utente);
	}
	
	public boolean controllaEvento(int id) {
		if(lettura.leggiPartitaCalcioEvento(id)!=null) {
			return true;
		}
		return false;
	}

	public void salvaNotifichePendenti(Hashtable<String, LinkedList<Notifica>> notifichePendenti) {
		scrittura.scriviNotifichePendenti(notifichePendenti);
	}

	public void cancellaNotifica(Notifica notifica, Utente utente) {
		scrittura.cancellaNotifica(notifica, utente);
	}
	/*
	public void cancellaEvento(Evento evento) {
		scrittura.cancellaEvento(evento);
	}*/

	public Hashtable<String, LinkedList<Notifica>> leggiNotifichePendenti() {
		return lettura.leggiNotifichePendenti();
		
	}

	public void salvaCategorie(Hashtable<String, Categoria> categorie) {
		for(String key: categorie.keySet()) {
			scrittura.scriviCategoriaPartitaCalcio(categorie.get(key));
		}
		
	}
	
	
}
