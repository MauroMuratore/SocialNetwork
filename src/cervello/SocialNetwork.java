package cervello;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class SocialNetwork {
	//UML QUANDO E' FINITO
	private Hashtable<String,Categoria> categorie;
	private Utente utente;
	
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
	//FINISHIM
	public String login(String id, byte[] hash) {
		return null;
	}
	
	/*//FINISHIM
	 * non mi ricordo come lo volessimo fare, ma se arriva la stringa e l'hash
	 * non serve riceverlo
	 * UML
	*/
	public void riceviIDPWLog(){
	}
	
	/* IDEA:Al posto dei metodi di risposta mettiamo delle costanti stringa e 
	 * facciamo rispondere direttamente al login?
	 * IDEA+:Al posto di fare due query che rispondono vero o falso (metodi controllaIDLog/Reg 
	 * e ControllaPWLog/Reg) facciamo chiedere tutto al login
	 * PSEUDO-CODE
	 * String login{
	 * database db;
	 * if !db.presenteID(id) return NON_PRESENTE;
	 * else if !db.controllaPW(hash) return PW_SBAGLIATA;
	 * return BENVENUTO
	 * }  
	 *  
	 */
	//FINISHIM
	protected boolean controllaIDLog(String id) {
		return false;
	}
	
	//FINISHIM
	protected String rispostaIDLog() {
		return null;
	}
	
	//FINISHIM
	protected boolean controllaPWLog(byte[] hash) {
		return false;
	}
	
	//FINISHIM
	protected String rispostaPWLog() {
		return null;
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
	public String registrazione(String username, byte[] hash, byte[] conferma) {
		return null;
	}
	//FINISHIM
	protected boolean controllaIDReg(String id) {
		return false;
	}
	
	//FINISHIM
	protected String rispondiIDReg() {
		return null;
	}
	
	//FINISHIM
	protected boolean controllaPWReg(byte[] hash, byte[] conferma) {
		return false;
	}
	
	//FINISHIM
	protected String rispondiPWReg() {
		return null;
	}
	
	//FINISHIM
	protected void aggiungiUtente() {
		
	}
	
	/*
	 * private perchè non puoi entrare senza autentificarti
	 * 
	 */
	private void setUtente(Utente _utente) {
		utente=_utente;
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
