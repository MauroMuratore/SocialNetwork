package database;

import org.w3c.dom.NodeList;

import cervello.Utente;

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

	public Utente aggiungiUtente(String id, byte[] pw)//questo metondo crea e aggiunge un utente al database e ritorna l'oggetto utente creato
	{
		return null;
	}
	
	public Utente caricaUtente(String id) {
		return null;
	}

}
