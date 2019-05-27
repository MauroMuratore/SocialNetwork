package cervello;

import java.util.*;

import database.ConsultaDB;

public class SocialNetwork {
	//UML QUANDO E' FINITO
	private Hashtable<String,Categoria> categorie;
	private Utente utente;
	private ConsultaDB consultaDB= new ConsultaDB();
	private PartitaCalcioCat<PartitaCalcioEvento> pdc;



	public static final String BENVENUTO = "BENVENUTO";
	public static final String ID_INESISTENTE= "ATTENZIONE! : username inesistente";
	public static final String PW_SBAGLIATA = "ATTENZIONE! : password sbalgiata";
	public static final String ID_IN_USO = "ATTENZIONE! : username in uso";
	public static final String PW_DIVERSE = "ATTENZIONE! : password diverse";

	
	public SocialNetwork() {
		categorie = new Hashtable<String, Categoria>();
		pdc = consultaDB.getPartitaCalcioCat();
		categorie.put(pdc.getNome(), pdc);
//		if(consultaDB.getPartitaCalcioCat() == null) System.out.println("dio cristo"); else System.out.println("ma che cazzz?");

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
			for(int i=0; i<hash.length; i++) {
				if(hash[i]!=conferma[i]) //controllo byte per byte se hash e conferma sono uguali
					return PW_DIVERSE;
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
	public PartitaCalcioCat getPcc() {
		return pdc;
	}
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
	 * @return la bacheca della categoria
	 */
	public List<Evento> mostraBacheca(String categoria){
		return categorie.get(categoria).getBacheca();
	}

	public ConsultaDB getConsultaDB() {
		// TODO Auto-generated method stub
		return consultaDB;
	}






























}
