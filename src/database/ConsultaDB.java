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
	
	public boolean controllaIDreg(String id) //questo metodo controlla se l' id è gia presente nel database e se la lunghezza dell id non è inferiorea 5 caratteri
	{
		return false;
	}
	public boolean controllaPWconferma(byte[] pw,byte[] pwConferma)//questo metodo controlla se le 2 ps sono uguali
	{
		return false
	}
	public Utente aggiungiUtente(String id, byte[] pw)//questo metondo crea e aggiunge un utente al database e ritorna l'oggetto utente creato
	{
		
	}

}
