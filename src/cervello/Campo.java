package cervello;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Campo<T> {
	
	private String nome;
	private String descrizione;
	private T valore;
	private boolean obbligatorio;
	

	public static final String FORMATO_SBAGLIATO = "Formato sbagliato";
	public static final String OK = "OK";
	private static final String FORMATO_ORA_SBAGLIATO = "Le ore vanno da 0 a 23";
	private static final String FORMATO_DATA_SBAGLIATO = "Formato della data sbagliato";
	
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
		valore=_valore;
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
		if(valore.getClass().equals(GregorianCalendar.class)) {
			return ((GregorianCalendar)valore).get(GregorianCalendar.DAY_OF_MONTH) + "/" +
					((GregorianCalendar)valore).get(GregorianCalendar.MONTH) + "/"+ ((GregorianCalendar)valore).get(GregorianCalendar.YEAR) +"\n";
			
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
		if(valore.getClass().equals(GregorianCalendar.class))
			return "Campo " + nome + " " + descrizione + " " + ((GregorianCalendar)valore).get(GregorianCalendar.DAY_OF_MONTH) + "/" +
					((GregorianCalendar)valore).get(GregorianCalendar.MONTH) + "/"+ ((GregorianCalendar)valore).get(GregorianCalendar.YEAR) +"\n";
		return "Campo " + nome + " " + descrizione + " " + valore.toString() + " \n";
	}
	
	public static String controlloData(String data) {
		if(!data.contains("/")) 
			return FORMATO_SBAGLIATO;
		String[] campiData = data.split("/", 2);
		if(controlloIntero(campiData[0]).equals(FORMATO_SBAGLIATO))
			return FORMATO_SBAGLIATO;	
		else if(controlloIntero(campiData[1]).equals(FORMATO_SBAGLIATO))
			return FORMATO_SBAGLIATO;
		else if(controlloIntero(campiData[2]).equals(FORMATO_SBAGLIATO))
			return FORMATO_SBAGLIATO;			
		int giorno = Integer.parseInt(campiData[0]);
		int mese = Integer.parseInt(campiData[1]);
		int anno = Integer.parseInt(campiData[2]);			
		if(anno%4==0) {
			if(mese==1 ||mese==3 ||mese==5 ||mese==7 ||mese==8 ||mese==10 ||mese==12 ) {
				if(giorno<1 || giorno > 31)
					return FORMATO_DATA_SBAGLIATO;
			}
			else if (mese ==2) {
				if(giorno<1 || giorno > 28) {
					return FORMATO_DATA_SBAGLIATO;
				}
			}
			else {
				if(giorno<1 || giorno > 30) {
					return FORMATO_DATA_SBAGLIATO;
				}
			}
		}
		else {
			if(mese==1 ||mese==3 ||mese==5 ||mese==7 ||mese==8 ||mese==10 ||mese==12 ) {
				if(giorno<1 || giorno > 31)
					return FORMATO_DATA_SBAGLIATO;
			}
			else if (mese ==2) {
				if(giorno<1 || giorno > 27) {
					return FORMATO_DATA_SBAGLIATO;
				}
			}
			else {
				if(giorno<1 || giorno > 30) {
					return FORMATO_DATA_SBAGLIATO;
				}
			}
		}
		return OK;
	}
	
	public static GregorianCalendar assumiData(String data) {
		String[] campiData = data.split("/", 2);
		int giorno = Integer.parseInt(campiData[0]);
		int mese = Integer.parseInt(campiData[1]);
		int anno = Integer.parseInt(campiData[2]);			
		GregorianCalendar gc = new GregorianCalendar();
		gc.set(anno, mese, giorno);
		return gc;
	}
	
	public static String controlloData(String data, String ora) {
		if(!controlloData(data).equals(OK))
			return FORMATO_SBAGLIATO;
		if(controlloIntero(ora).equals(FORMATO_SBAGLIATO))
			return FORMATO_SBAGLIATO;
		int intOra = Integer.parseInt(ora);
		if(intOra<0 || intOra>23)
			return FORMATO_ORA_SBAGLIATO;
		
		return OK;
	}
	
	public static GregorianCalendar assumiData(String data, String ora) {
		GregorianCalendar gc = assumiData(data);
		int intOra = Integer.parseInt(ora);
		gc.set(GregorianCalendar.HOUR, intOra);
		return gc;
	}
	
	public static String controlloIntero(String intero) {
		for(int i=0; i<intero.length(); i++) {
			char c = intero.charAt(i);
			if(c<'0' || c>'9')
				return FORMATO_SBAGLIATO;
		}
		return OK;
	
	}

}
