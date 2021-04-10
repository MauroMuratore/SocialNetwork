package util;

import java.util.GregorianCalendar;

import server.core.Campo;

public class ControlloCampo {
	/**
	 * 
	 * @param data
	 * @return
	 * FORMATO_SBAGLIATO
	 * FORMATO_DATA_SBAGLIATO
	 * DATA_PASSATA
	 * OK
	 */
	public static String controlloData(String data) {
		GregorianCalendar dataG = new GregorianCalendar();
		if(!data.contains("/")) 
			return Campo.FORMATO_SBAGLIATO;
		String[] campiData = data.split("/", 3);
		if(controlloIntero(campiData[0]).equals(Campo.FORMATO_SBAGLIATO))
			return Campo.FORMATO_SBAGLIATO;	
		else if(controlloIntero(campiData[1]).equals(Campo.FORMATO_SBAGLIATO))
			return Campo.FORMATO_SBAGLIATO;
		else if(controlloIntero(campiData[2]).equals(Campo.FORMATO_SBAGLIATO))
			return Campo.FORMATO_SBAGLIATO;			
		int giorno = Integer.parseInt(campiData[0]);
		int mese = Integer.parseInt(campiData[1]);
		int anno = Integer.parseInt(campiData[2]);
		if(giorno<1 || mese<1) {
			return Campo.FORMATO_DATA_SBAGLIATO;
		}
		if(anno%4==0) {
			if(mese==1 ||mese==3 ||mese==5 ||mese==7 ||mese==8 ||mese==10 ||mese==12 ) {
				if(giorno<1 || giorno > 31)
					return Campo.FORMATO_DATA_SBAGLIATO;
			}
			else if (mese ==2) {
				if(giorno<1 || giorno > 28) {
					return Campo.FORMATO_DATA_SBAGLIATO;
				}
			}
			else {
				if(giorno<1 || giorno > 30) {
					return Campo.FORMATO_DATA_SBAGLIATO;
				}
			}
		}
		else {
			if(mese==1 ||mese==3 ||mese==5 ||mese==7 ||mese==9 ||mese==11 ||mese==12 ) {
				if(giorno<1 || giorno > 31)
					return Campo.FORMATO_DATA_SBAGLIATO;
			}
			else if (mese ==2) {
				if(giorno<1 || giorno > 27) {
					return Campo.FORMATO_DATA_SBAGLIATO;
				}
			}
			else {
				if(giorno<1 || giorno > 30) {
					return Campo.FORMATO_DATA_SBAGLIATO;
				}
			}
		}
		GregorianCalendar gc = assumiData(data);
		if(gc.before(System.currentTimeMillis()))
			return Campo.DATA_PASSATA;//data passata
		return Campo.OK;
	}
	
	public static GregorianCalendar assumiData(String data) {
		String[] campiData = data.split("/", 3);
		int giorno = Integer.parseInt(campiData[0]);
		int mese = Integer.parseInt(campiData[1]);
		int anno = Integer.parseInt(campiData[2]);			
		GregorianCalendar gc = new GregorianCalendar();
		gc.clear();
		gc.set(GregorianCalendar.YEAR, anno);
		gc.set(GregorianCalendar.MONTH, mese);
		gc.set(GregorianCalendar.DAY_OF_MONTH, giorno);
		return gc;
	}
	
	/**
	 * 
	 * @param data
	 * @param ora
	 * @return
	 * FORMATO_SBAGLIATO
	 * FORMATO_ORA_SBAGLIATO
	 * OK
	 */
	public static String controlloData(String data, String ora) {
		if(!controlloData(data).equals(Campo.OK))
			return Campo.FORMATO_SBAGLIATO;
		if(controlloIntero(ora).equals(Campo.FORMATO_SBAGLIATO))
			return Campo.FORMATO_SBAGLIATO;
		String[] campiOra = ora.split(":", 2);
		if(!controlloIntero(campiOra[0]).equals(Campo.OK)) {
			return controlloIntero(campiOra[0]);
		
		}
		if(!controlloIntero(campiOra[1]).equals(Campo.OK)) {
			return controlloIntero(campiOra[1]);
		
		}
		int intOra = Integer.parseInt(campiOra[0]);
		int intMinuti = Integer.parseInt(campiOra[1]);
		if(intOra<0 || intOra>23)
			return Campo.FORMATO_ORA_SBAGLIATO;
		if(intMinuti<0 || intOra>59)
			return Campo.FORMATO_ORA_SBAGLIATO;
		return Campo.OK;
	}
	
	public static GregorianCalendar assumiData(String data, String ora) {
		GregorianCalendar gc = assumiData(data);
		String[] campiOra = ora.split(":", 2);
		int intOra = Integer.parseInt(campiOra[0]);
		int intMinuti = Integer.parseInt(campiOra[1]);
		gc.set(GregorianCalendar.HOUR, intOra);
		gc.set(GregorianCalendar.MINUTE, intMinuti);
		return gc;
	}
	
	/**
	 * 
	 * @param intero
	 * @return
	 * STRINGA_VUOTA
	 * FORMATO_INTERO_SBAGLIATO
	 * OK
	 */
	public static String controlloIntero(String intero) {
		if(intero.equals(""))
			return Campo.STRINGA_VUOTA;
		for(int i=0; i<intero.length(); i++) {
			char c = intero.charAt(i);
			if(c<'0' || c>'9')
				return Campo.FORMATO_INTERO_SBAGLIATO;
		}
		return Campo.OK;
		
		}


}
