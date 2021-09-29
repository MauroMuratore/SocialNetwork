
package lib.core.eventi;

import java.util.GregorianCalendar;
import java.util.LinkedList;

import lib.util.ControlloCampo;
import lib.util.Nomi;
import server.database.ConsultaDB;
public class PartitaCalcioEvento extends Evento {

	private Campo<String> sesso;
	private Campo<Integer> eta;

	public PartitaCalcioEvento(int _idEvento, Campo<String> _titolo, Campo<Integer> _partecipantiMax, LinkedList<String> _partecipanti, String _proprietario,
			Campo<GregorianCalendar> _termineUltimo,Campo<GregorianCalendar> _termineUltimoRitiro, Campo<String> _luogo, Campo<GregorianCalendar> _dataInizio,
			Campo<Integer> _durata, Campo<Integer> _quotaIndividuale, Campo<String> _compresoQuota,
			Campo<GregorianCalendar> _dataFine, Campo<String> _note, Campo<Integer> _tolleranzaPartecipanti, Campo<String> _sesso, Campo<Integer> _eta, StatoEvento _stato) {
		


		super(_idEvento, _titolo, _partecipantiMax, _partecipanti, _proprietario, _termineUltimo, _termineUltimoRitiro, _luogo, _dataInizio, _durata, _quotaIndividuale, _compresoQuota,
				_dataFine, _note, _tolleranzaPartecipanti, _stato);
		this.sesso=_sesso;
		this.eta=_eta;
		categoria=Nomi.CAT_PARTITA_CALCIO.getNome();
	}

	public PartitaCalcioEvento(Campo<String> titolo, Campo<Integer> partecipantiMax, String proprietario,
			Campo<GregorianCalendar> termineUltimo, Campo<GregorianCalendar> termineUltimoRitiro,Campo<String> luogo, Campo<GregorianCalendar> dataInizio,
			Campo<Integer> durata, Campo<Integer> quotaIndividuale, Campo<String> compresoQuota,
			Campo<GregorianCalendar> dataFine, Campo<String> note,Campo<Integer> tolleranzaPartecipanti, Campo<String> sesso, Campo<Integer> eta) {
		super(titolo, partecipantiMax, proprietario, termineUltimo, termineUltimoRitiro, luogo, dataInizio, durata, quotaIndividuale, compresoQuota, dataFine, note, tolleranzaPartecipanti);
		this.sesso=sesso;
		this.eta=eta;
		categoria=Nomi.CAT_PARTITA_CALCIO.getNome();
	}

	public PartitaCalcioEvento() {
		super();
		titolo = new Campo<String>(Nomi.CAMPO_TITOLO.getNome(), "", true);
		partecipantiNecessari = new Campo<Integer>(Nomi.CAMPO_PARTECIPANTI_MIN.getNome(), "", true);
		termineUltimo = new Campo<GregorianCalendar>(Nomi.CAMPO_TERMINE_ULTIMO.getNome(), "", true);
		luogo = new Campo<String>(Nomi.CAMPO_LUOGO.getNome(), "", true);
		dataInizio = new Campo<GregorianCalendar>(Nomi.CAMPO_DATA_INIZIO.getNome(), "", true);
		durata = new Campo<Integer>(Nomi.CAMPO_DURATA.getNome(), "", false);
		quotaIndividuale = new Campo<Integer>(Nomi.CAMPO_QUOTA_IND.getNome(), "", true);
		compresoQuota = new Campo<String>(Nomi.CAMPO_COMPRESO_QUOTA.getNome(), "", false);
		dataFine = new Campo<GregorianCalendar>(Nomi.CAMPO_DATA_FINE.getNome(), "", false);
		note = new Campo<String>(Nomi.CAMPO_NOTE.getNome(), "", false);
		tolleranzaPartecipanti = new Campo<Integer>(Nomi.CAMPO_TOLLERANZA.getNome(), "", false);
		termineUltimoRitiro = new Campo<GregorianCalendar>(Nomi.CAMPO_TERMINE_ULTIMO_RITIRO.getNome(), "", false);
		sesso = new Campo<String>(Nomi.CAMPO_SESSO.getNome(), "", true);
		eta = new Campo<Integer>(Nomi.CAMPO_ETA.getNome(), "",false);
		categoria=Nomi.CAT_PARTITA_CALCIO.getNome();
	}

	public boolean valido() {
		if(titolo.getValore()==null) {
			
			return false;
		}
		else if(partecipantiNecessari.getValore()==null) {

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
		else if(sesso.getValore()==null) {
			return false;
		}


		return true;
	}



	public Campo getSesso() {
		// TODO Auto-generated method stub
		return sesso;
	}

	public String setSesso(String _sesso) {
		if(_sesso.equals("")) {
			return Nomi.CAMPO_SESSO.getNome() + VUOTO;
		}
		sesso.setValore(_sesso);
		return OK;
	}

	public Campo getEta() {
		return eta;
	}

	public String setEta(String _eta) {
		if(_eta.equals("")) {
			return OK;
		}
		if(ControlloCampo.controlloIntero(_eta).equals(Campo.FORMATO_INTERO_SBAGLIATO))
			return Campo.FORMATO_INTERO_SBAGLIATO;
		eta.setValore(Integer.parseInt(_eta));
		return OK;
	}

	public String toString() {
		return super.toString() + sesso.toString() + eta.toString();
	}


}

