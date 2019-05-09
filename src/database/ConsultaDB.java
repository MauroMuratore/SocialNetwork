package database;

import org.w3c.dom.NodeList;

public class ConsultaDB {
	
	NodeList listaNodi;
	
	//TODO SI POTREBBE PRIVATO E FARE UN COSTRUTTORE A PARTE CHE FACCIA CREARE UN SOLO OGGETTO INTERROGAZIONE
	public ConsultaDB() {
		
	}
	
	public boolean controllaID(String id) {
		return false;
	}
	
	public boolean controllaPW(byte[] hash) {
		return false;
	}
	
	public boolean isPresentID(String id) {
		return false;
	}
	

}
