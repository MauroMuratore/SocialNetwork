package cervello;

import java.util.GregorianCalendar;

public class Campo<T> {
	
	private String nome;
	private String descrizione;
	private T valore;
	private boolean obbligatorio;
	
	public Campo(String _nome, String _descrizione, T _valore, boolean _obbl) {
		nome=_nome;
		descrizione=_descrizione;
		valore=_valore;
		obbligatorio=_obbl;
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
		return valore.toString();
	}
	
	public Class getClasseValore() {
		return valore.getClass();
	}
	public boolean isObbligatorio() {
		return obbligatorio;
	}
	
	public String toString() {
		if(valore.getClass().equals(GregorianCalendar.class))
			return "Campo " + nome + " " + descrizione + " " + ((GregorianCalendar)valore).get(GregorianCalendar.DAY_OF_MONTH) + "/" +
					((GregorianCalendar)valore).get(GregorianCalendar.MONTH) + "/"+ ((GregorianCalendar)valore).get(GregorianCalendar.YEAR) +"\n";
		return "Campo " + nome + " " + descrizione + " " + valore.toString() + " \n";
	}
	
	

}
