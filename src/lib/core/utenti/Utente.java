
package lib.core.utenti;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;


public class Utente implements Serializable {

	private String username;
	private String password;
	private LinkedList<Notifica> notifiche;
	private ArrayList<Integer> eventiCreati;
	private LinkedList<String> interessiCategorie;
	private int etamin;
	private int etamax;

	public static final int ETA_MIN = 10;
	public static final int ETA_MAX = 20;
	public static final int RIMUOVI_INTERESSE = 30;
	public static final int AGGIUNGI_INTERESSE = 40;
	public static final String MODIFICA_RIUSCITA = "Modifica riuscita";

	public Utente(String id,String pw, int _etamin, int _etamax)
	{
		username=id;
		password=pw;
		notifiche = new LinkedList<Notifica>();
		interessiCategorie = new LinkedList<String>();
		eventiCreati = new ArrayList<Integer>();
		etamin=_etamin;
		etamax=_etamax;
	}
	
	public Utente(String id,String pw, int _etamin, int _etamax, String[] ic)
	{
		username=id;
		password=pw;
		notifiche = new LinkedList<Notifica>();
		interessiCategorie = new LinkedList<String>();
		for(String s: ic) {
			interessiCategorie.add(s);
		}
		eventiCreati = new ArrayList<Integer>();
		etamin=_etamin;
		etamax=_etamax;
	}


	public Utente(String id, String pw, LinkedList<Notifica> _notifiche, LinkedList<String> _interessiCategorie, ArrayList<Integer> _eventiCreati, int _etamin, int _etamax) {
		username=id;
		password=pw;
		notifiche = new LinkedList<Notifica>(_notifiche);
		interessiCategorie = new LinkedList<String>(_interessiCategorie);
		eventiCreati = new ArrayList<Integer>(_eventiCreati);
		etamin = _etamin;
		etamax = _etamax;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public LinkedList<Notifica> getNotifiche(){
		return notifiche;
	}

	public void setEtaMin(int min) {
		etamin=min;
	}

	public void setEtaMax(int max) {
		etamax=max;
	}



	public int getEtaMin() {
		return etamin;
	}

	public int getEtaMax() {
		return etamax;
	}

	public boolean riceviNotifica(Notifica not) {
		return notifiche.add(not);
	}

	public Notifica cancellaNotifica(int index) {
		Notifica ritorno = notifiche.get(index);
		notifiche.remove(ritorno);
		return ritorno;
			
	}

	public void aggiungiInteresse(String categoria) {
		if(!interessiCategorie.contains(categoria))
			interessiCategorie.add(categoria);
	}

	public void removeInteresse(String categoria) {
		interessiCategorie.remove(categoria);
	}

	public LinkedList<String> getInteressi(){
		return interessiCategorie;
	}

	public void creaEvento(int idEvento) {
		eventiCreati.add(idEvento);
	}

	public ArrayList<Integer> getIdEventi(){
		return eventiCreati;
	}





}
