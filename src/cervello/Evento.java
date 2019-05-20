package cervello;

import java.sql.Time;
import java.util.Calendar;

public abstract class Evento {
	
	private int idEvento;
	private Campo<String> titolo;
	private Campo<Integer> partecipanti;
	private Campo<Calendar> termineUltimo;
	private Campo<String> luogo;
	private Campo<Calendar> dataInizio;
	private Campo<Time> orarioInizio;
	//Si può fare così, oppure in dataInizio settare l'ora e poi fare sia il metodo getOrario e getDataInizio
	private Campo<Integer> durata;
	private Campo<Integer> quotaIndividuale;
	private Campo<String> compresoQuota;
	private Campo<Calendar> dataFine;
	private Campo<Time> orarioFine;
	//stesso discorso di dataInizio
	private Campo<String> note;
	
	
	
	public int getIdEvento() {
		return idEvento;
	}
	
	public Campo<String> getTitolo() {
		return titolo;
	}
	public Campo<Integer> getPartecipanti() {
		return partecipanti;
	}
	public Campo<Calendar> getTermineUltimo() {
		return termineUltimo;
	}
	public Campo<String> getLuogo() {
		return luogo;
	}
	public Campo<Calendar> getDataInizio() {
		return dataInizio;
	}
	public Campo<Time> getOrarioInizio() {
		return orarioInizio;
	}
	public Campo<Integer> getDurata() {
		return durata;
	}
	public Campo<Integer> getQuotaIndividuale() {
		return quotaIndividuale;
	}
	public Campo<String> getCompresoQuota() {
		return compresoQuota;
	}
	public Campo<Calendar> getDataFine() {
		return dataFine;
	}
	public Campo<Time> getOrarioFine() {
		return orarioFine;
	}
	public Campo<String> getNote() {
		return note;
	}
	
	

}
