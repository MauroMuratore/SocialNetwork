package cervello;

import java.util.*;

import database.ConsultaDB;

public class SocialNetwork {
	//UML QUANDO E' FINITO
	private Hashtable<String,Categoria> categorie;
	private Utente utente;
	private ConsultaDB consultaDB= new ConsultaDB();




	public static final String BENVENUTO = "BENVENUTO";
	public static final String ID_INESISTENTE= "ATTENZIONE! : username inesistente";
	public static final String ID_CORTO = "ATTENZIONE! : id troppo corto";
	public static final String PW_SBAGLIATA = "ATTENZIONE! : password sbalgiata";
	public static final String PW_CORTA ="ATTENZIONE! : passoword troppo corta";
	public static final String ID_IN_USO = "ATTENZIONE! : username in uso";
	public static final String PW_DIVERSE = "ATTENZIONE! : password diverse";

	
	public SocialNetwork() {
		categorie = new Hashtable<String, Categoria>();
		PartitaCalcioCat pdc = consultaDB.getPartitaCalcioCat();
		categorie.put(pdc.getNome(), pdc);
		//quando vengono caricate gli eventi bisogna fare un controllo sulle notifiche
	}

	/**
	 * permette di fare il login
	 * @param id
	 * @param hash
	 * @return torna l'esito del login
	 */
	//il login ritorna una stringa, se id e pw sono corretti risponde benvenuto e setta l'utente, se invece sono errati invia un messaggio di errore
	public String login(String id, byte[] hash) 
	{

		if(consultaDB.controllaID(id))
		{
			if(consultaDB.controllaPW(hash))
			{
				setUtente(id);
				return BENVENUTO;
			}
			else 
				return PW_SBAGLIATA;
		}
		else 
			return  ID_INESISTENTE;
	}

	/**
	 * serve per registrare un nuovo utente
	 * @param username
	 * 
	 * @param hash
	 * @param conferma
	 * @return esito della registrazione
	 */	
	public String registrazione(String username, byte[] hash, byte[] conferma)
	{
		if(!consultaDB.controllaID(username))//controllo se ce gia id nel database 
		{
			if(username.length()<7)
				return ID_CORTO;
			String hashString = new String(hash);
			if(hashString.length()<7){
				return PW_CORTA;
			}
			boolean uguali=true;
			if(hash.length!=conferma.length) return PW_DIVERSE;
			else {
				for(int i=0; i<hash.length; i++) {
					if(hash[i]!=conferma[i]) //controllo byte per byte se hash e conferma sono uguali
						uguali=false;	
				}if(uguali=false)return PW_DIVERSE;
			}

			consultaDB.aggiungiUtente(username, hash);
			setUtente(username);
			return BENVENUTO;
		}
		else
			return ID_IN_USO;
	}
	/*
	 * private perchè non puoi entrare senza autentificarti
	 * 
	 */
	private void setUtente(String id) 
	{
		utente=consultaDB.caricaUtente(id);
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
	 * @return la categoria
	 */
	public Categoria mostraCategoria(String categoria){
		return categorie.get(categoria);
	}
	
	//monte come mi fai arrivare l'iscrizione a un evento? mi mandi una stringa, l'id dell'evento o altro?
	public String iscrizione(Evento evento) {
		
		return null;
	}

	public void addevento;
	






























}
