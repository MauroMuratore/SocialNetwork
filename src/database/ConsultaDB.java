package database;

import java.io.UnsupportedEncodingException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

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
		for(int i=0; i<hash.length; i++) {
			if(hash[i]!=
					confByte[i])
				return false;
		}
		return true;
	}


	public void aggiungiUtente(String id, byte[] pw)//questo metondo crea e aggiunge un utente al database e ritorna l'oggetto utente creato
	{
		scrittura.scriviUtente(id, pw);
	}

	public Utente caricaUtente(String id) {
		return null;
	}

}
