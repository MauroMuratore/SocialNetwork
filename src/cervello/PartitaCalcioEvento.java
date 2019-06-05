package cervello;

import java.util.GregorianCalendar;
import java.util.LinkedList;

import database.ConsultaDB;
import database.NomiDB;
public class PartitaCalcioEvento extends Evento {

	private Campo<String> sesso;
	private Campo<Integer> eta;

	public PartitaCalcioEvento(int idEvento, Campo<String> titolo, Campo<Integer> partecipantiMax, LinkedList<String> partecipanti,
			Campo<GregorianCalendar> termineUltimo, Campo<String> luogo, Campo<GregorianCalendar> dataInizio,
			Campo<Integer> durata, Campo<Integer> quotaIndividuale, Campo<String> compresoQuota,
			Campo<GregorianCalendar> dataFine, Campo<String> note, Campo<String> sesso, Campo<Integer> eta, StatoEvento stato) {


		super(idEvento, titolo, partecipantiMax, partecipanti, termineUltimo, luogo, dataInizio, durata, quotaIndividuale, compresoQuota,
				dataFine, note, stato);
		this.sesso=sesso;
		this.eta=eta;
	}

	public PartitaCalcioEvento(Campo<String> titolo, Campo<Integer> partecipantiMax,
			Campo<GregorianCalendar> termineUltimo, Campo<String> luogo, Campo<GregorianCalendar> dataInizio,
			Campo<Integer> durata, Campo<Integer> quotaIndividuale, Campo<String> compresoQuota,
			Campo<GregorianCalendar> dataFine, Campo<String> note, Campo<String> sesso, Campo<Integer> eta) {
		super(titolo, partecipantiMax, termineUltimo, luogo, dataInizio, durata, quotaIndividuale, compresoQuota, dataFine, note);
		this.sesso=sesso;
		this.eta=eta;
	}

	public PartitaCalcioEvento() {
		super();
		titolo = new Campo<String>(NomiDB.CAMPO_TITOLO.getNome(), "", false);
		partecipantiNecessari = new Campo<Integer>(NomiDB.CAMPO_PARTECIPANTI_MAX.getNome(), "", true);
		termineUltimo = new Campo<GregorianCalendar>(NomiDB.CAMPO_TERMINE_ULTIMO.getNome(), "", true);
		luogo = new Campo<String>(NomiDB.CAMPO_LUOGO.getNome(), "", true);
		dataInizio = new Campo<GregorianCalendar>(NomiDB.CAMPO_DATA_INIZIO.getNome(), "", true);
		durata = new Campo<Integer>(NomiDB.CAMPO_DURATA.getNome(), "", false);
		quotaIndividuale = new Campo<Integer>(NomiDB.CAMPO_QUOTA_IND.getNome(), "", true);
		compresoQuota = new Campo<String>(NomiDB.CAMPO_COMPRESO_QUOTA.getNome(), "", false);
		dataFine = new Campo<GregorianCalendar>(NomiDB.CAMPO_DATA_FINE.getNome(), "", false);
		note = new Campo<String>(NomiDB.CAMPO_NOTE.getNome(), "", false);
		sesso = new Campo<String>(NomiDB.CAMPO_SESSO.getNome(), "", true);
		eta = new Campo<Integer>(NomiDB.CAMPO_ETA.getNome(), "",false);
	}

	public boolean valido() {
		ConsultaDB cdb = new ConsultaDB();
		if(partecipantiNecessari.getValore()==null) {
			return false;
		}
		else if(termineUltimo.getValore()==null) {
			return false;
		}
		else if(luogo.getValore()==null) {
			return false;
		}
		else if(dataInizio.getValore()==null) {
			return false;
		}
		else if(quotaIndividuale.getValore()==null) {
			return false;
		}
		else if(cdb.controllaEvento(getIdEvento()))
			return false;
		return true;
	}



	public Campo getSesso() {
		// TODO Auto-generated method stub
		return sesso;
	}

	public String setSesso(String _sesso) {
		if(_sesso.equals("")) {
			return sesso.getNome() + VUOTO;
		}
		sesso.setValore(_sesso);
		return OK;
	}

	public Campo getEta() {
		return eta;
	}

	public String setEta(String _eta) {
		if(Campo.controlloIntero(_eta).equals(Campo.FORMATO_INTERO_SBAGLIATO))
			return Campo.FORMATO_INTERO_SBAGLIATO;
		eta.setValore(Integer.parseInt(_eta));
		return OK;
	}

	public String toString() {
		return super.toString() + sesso.toString() + eta.toString();
	}
//	public String setEta(String et�) {
//		if(!Campo.controlloEta(eta).equals(Campo.OK))
//			return Campo.controlloEta(eta);
//		eta= Integer.getInteger(et�);
//		return OK;
//	}


}

