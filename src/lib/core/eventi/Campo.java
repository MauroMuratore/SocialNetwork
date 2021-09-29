package lib.core.eventi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Campo<T> implements Serializable{

	private String nome;
	private String descrizione;
	private T valore;
	private boolean obbligatorio;


	public static final String FORMATO_SBAGLIATO = "Formato sbagliato";
	public static final String OK = "OK";
	public static final String FORMATO_ORA_SBAGLIATO = "Le ore vanno da 0 a 23";
	public static final String FORMATO_DATA_SBAGLIATO = "Formato della data sbagliato";
	public static final String FORMATO_INTERO_SBAGLIATO = "Formato intero sbagliato";
	public static final String DATA_PASSATA = "data passata";
	public static final String STRINGA_VUOTA = "stringa vuota";

	public Campo(String _nome, String _descrizione, T _valore, boolean _obbl) {
		nome=_nome;
		descrizione=_descrizione;
		valore=_valore;
		obbligatorio=_obbl;
	}

	public Campo(String _nome, String _descrizione, boolean _obbl) {
		nome=_nome;
		descrizione=_descrizione;
		obbligatorio=_obbl;
	}

	public void setValore(T _valore) {
		if(_valore!=null)
			valore=_valore;
		else {
			System.out.println(nome);
		}
			
	}

	public String getNome() {
		return nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public T getValore() {
		return valore;
	}
	public String getValoreString() {
		if(valore==null) {
			return "";
		}
		if(valore.getClass().equals(GregorianCalendar.class)) {
			return ((GregorianCalendar)valore).get(GregorianCalendar.DAY_OF_MONTH) + "/" +
					((GregorianCalendar)valore).get(GregorianCalendar.MONTH) + "/"+ ((GregorianCalendar)valore).get(GregorianCalendar.YEAR) 
					+ " " + ((GregorianCalendar)valore).get(GregorianCalendar.HOUR_OF_DAY) + ":"+((GregorianCalendar)valore).get(GregorianCalendar.MINUTE)
					+"\n";

		}

		return valore.toString();
	}

	public Class getClasseValore() {
		return valore.getClass();
	}
	public boolean isObbligatorio() {
		return obbligatorio;
	}

	public String toString() {
		if(valore==null) {
			return nome.toUpperCase() + ": -";
		}
		if(valore.getClass().equals(GregorianCalendar.class))
			return nome.toUpperCase() + descrizione + ": " + ((GregorianCalendar)valore).get(GregorianCalendar.DAY_OF_MONTH) + "/" +
			((GregorianCalendar)valore).get(GregorianCalendar.MONTH) + "/"+ ((GregorianCalendar)valore).get(GregorianCalendar.YEAR) +"\n";
		return  nome.toUpperCase() + descrizione + ": " + valore.toString() + " \n";
	}


}
