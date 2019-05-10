package cervello;

import java.util.*;

import database.ConsultaDB;

public class SocialNetwork {
	//UML QUANDO E' FINITO
	private Hashtable<String,Categoria> categorie;
	private Utente utente;
	private ConsultaDB CDB;
	
	//FINISHIM
	public SocialNetwork() {
		categorie = new Hashtable<String, Categoria>();
	}
	
	/**
	 * permette di fare il login
	 * @param id
	 * @param hash
	 * @return torna l'esito del login
	 */
	//il login ritorna una stringa, se id e pw sono corretti risponde benvenuto + il nome id  e setta l'utente, se invece sono errati invia un messaggio di errore
	public String login(String id, byte[] hash) 
	{
		
		if(CDB.controllaID(id))
		{
			if(CBD.controllaPW(hash))
			{
				return "benvenuto"+ id;
				setUtente(id);
			}
		}
		else 
				return  "id o password errata";
		
	}
	
	/**
	 * serve per registrare un nuovo utente
	 * @param username
	 * 
	 * @param hash
	 * @param conferma
	 * @return esito della registrazione
	 */
	//FINISHIM	
	public String registrazione(String username, byte[] hash, byte[] conferma)
	{
		if(CDB.controllaIDreg(id))//controllo se cè gia id nel database 
		{
			if(CBD.controllaPWreg(hash))//controllo se la pw soddisfa i requisiti di lunghezza
			{
				if(CBD.controllaPWconferma(hash,conferma))//controlle se le due pw sono uguali, se lo sono aggiungo l'utente al database
				{
					Utente nuovoUtente= new Utente(id);
					CDB.aggiungiUtente(nuovoUtente);
					return " registrazione effettuata"
				}
				else return "le password non corrispondono";
			}
			else return "la password non soddisfa i requisiti di lungheza"
		}
		else
		{
			return "id gia esistente o troppo corto";
		}
	}
	
	/*
	 * private perchÃ¨ non puoi entrare senza autentificarti
	 * 
	 */
	private void setUtente(String username,byte[] pw) 
	{
		utente= new Utente (username,pw);
	}
	
	//UML  
	public void logout() {
		utente = null;
	} 

	/**
	 * @return i titoli delle categorie
	 */
	public List<String> titoliCategorie(){
		ArrayList<String> ritorno = new ArrayList<String>();
		ritorno.addAll(categorie.keySet());
		return ritorno;
	}
	
	/**
	 * @param categoria
	 * @return la bacheca della categoria
	 */
	public List<Evento> mostraBacheca(String categoria){
		return categorie.get(categoria).getBacheca();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
