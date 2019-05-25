package cervello;

import java.util.GregorianCalendar;

public class PartitaCalcioEvento extends Evento {

	private Campo<String> sesso;
	private Campo<Integer> eta;
	
	public PartitaCalcioEvento(int idEvento, Campo<String> titolo, Campo<Integer> partecipantiMax,
			Campo<GregorianCalendar> termineUltimo, Campo<String> luogo, Campo<GregorianCalendar> dataInizio,
			Campo<Integer> durata, Campo<Integer> quotaIndividuale, Campo<String> compresoQuota,
			Campo<GregorianCalendar> dataFine, Campo<String> note, Campo<String> sesso, Campo<Integer> eta) {
		
		
		super(idEvento, titolo, partecipantiMax, termineUltimo, luogo, dataInizio, durata, quotaIndividuale, compresoQuota,
				dataFine, note);
		this.sesso=sesso;
		this.eta=eta;
	}
	
	
	public Campo getSesso() {
		// TODO Auto-generated method stub
		return sesso;
	}
	
	public Campo getEta() {
		return eta;
	}
	
	public String toString() {
		return super.toString() + sesso.toString() + eta.toString();
	}

}
