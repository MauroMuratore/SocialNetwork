package cervello;

import java.sql.Time;
import java.util.Calendar;
import java.util.GregorianCalendar;

//FINISHIM
public abstract class Evento {
	
	private int idEvento;
	private Campo<String> titolo;
	private Campo<Integer> partecipantiMax;
	private Campo<Integer> partecipanti;
	private Campo<GregorianCalendar> termineUltimo;
	private Campo<String> luogo;
	private Campo<GregorianCalendar> dataInizio;
	private Campo<Integer> durata;
	private Campo<Integer> quotaIndividuale;
	private Campo<String> compresoQuota;
	private Campo<GregorianCalendar> dataFine;
	private Campo<String> note;
	
	
	
	public Evento(int idEvento, Campo<String> titolo, Campo<Integer> partecipantiMax, Campo<Integer> partecipanti,
			Campo<GregorianCalendar> termineUltimo, Campo<String> luogo, Campo<GregorianCalendar> dataInizio,
			Campo<Integer> durata, Campo<Integer> quotaIndividuale, Campo<String> compresoQuota,
			Campo<GregorianCalendar> dataFine, Campo<String> note) {
		super();
		this.idEvento = idEvento;
		this.titolo = titolo;
		this.partecipantiMax = partecipantiMax;
		this.termineUltimo = termineUltimo;
		this.luogo = luogo;
		this.partecipanti=partecipanti;
		this.dataInizio = dataInizio;
		this.durata = durata;
		this.quotaIndividuale = quotaIndividuale;
		this.compresoQuota = compresoQuota;
		this.dataFine = dataFine;
		this.note = note;
	}

	public int getIdEvento() {
		return idEvento;
	}
	
	public Campo<String> getTitolo() {
		return titolo;
	}
	public Campo<Integer> getPartecipantiMax() {
		return partecipantiMax;
	}
	public Campo<GregorianCalendar> getTermineUltimo() {
		return termineUltimo;
	}
	public Campo<String> getLuogo() {
		return luogo;
	}
	public Campo<GregorianCalendar> getDataInizio() {
		return dataInizio;
	}
	
	public Campo<Integer> getDurata() {
		return durata;
	}
	public Campo<Integer> getPartecipanti(){
		return partecipanti;
	}
	public Campo<Integer> getQuotaIndividuale() {
		return quotaIndividuale;
	}
	public Campo<String> getCompresoQuota() {
		return compresoQuota;
	}
	public Campo<GregorianCalendar> getDataFine() {
		return dataFine;
	}
	public Campo<String> getNote() {
		return note;
	}
	
	public String toString() {
		return titolo.toString() + partecipantiMax.toString() + termineUltimo.toString() + luogo.toString() + dataInizio.toString() +durata.toString() + quotaIndividuale.toString() +
				compresoQuota.toString() + dataFine.toString() + note.toString();
	}
	

}
