package cervello;

import java.util.GregorianCalendar;
import java.util.LinkedList;

import database.ConsultaDB;
import database.NomiDB;

public class EscursioneMontagnaEvento extends Evento {
	
	private Campo<Integer> istruttore;
	private Campo<Integer> attrezzatura;
	private LinkedList<String> listaPerIstruttore;
	private LinkedList<String> listaPerAttrezzature;
	
	public EscursioneMontagnaEvento() {
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
		tolleranzaPartecipanti = new Campo<Integer>(NomiDB.CAMPO_TOLLERANZA.getNome(), "", false);
		termineUltimoRitiro = new Campo<GregorianCalendar>(NomiDB.CAMPO_TERMINE_ULTIMO_RITIRO.getNome(), "", false);
		istruttore = new Campo<Integer>(NomiDB.CAMPO_ISTRUTTORE.getNome(), "", false);
		attrezzatura = new Campo<Integer>(NomiDB.CAMPO_ATTREZZATURA.getNome(), "", false);
		listaPerAttrezzature = new LinkedList<String>();
		listaPerIstruttore = new LinkedList<String>();
	}
	
	public EscursioneMontagnaEvento(int _idEvento, Campo<String> _titolo, Campo<Integer> _partecipantiMax, LinkedList<String> _partecipanti, String _proprietario, 
			Campo<GregorianCalendar> _termineUltimo, Campo<GregorianCalendar> _termineUltimoRitiro, Campo<String> _luogo, Campo<GregorianCalendar> _dataInizio,
			Campo<Integer> _durata, Campo<Integer> _quotaIndividuale, Campo<String> _compresoQuota, Campo<GregorianCalendar> _dataFine, Campo<String> _note, 
			Campo<Integer> _tolleranzaPartecipanti, Campo<Integer> _istruttore, Campo<Integer> _attrezzatura, LinkedList<String> _listaPerIstruttore, LinkedList<String> _listaPerAttrezzatura,
			StatoEvento _stato) {
		super(_idEvento, _titolo, _partecipantiMax, _partecipanti, _proprietario, _termineUltimo, _termineUltimoRitiro, _luogo, _dataInizio, _durata, _quotaIndividuale, _compresoQuota,
				_dataFine, _note, _tolleranzaPartecipanti, _stato);
		istruttore = _istruttore;
		attrezzatura=_attrezzatura;
		listaPerIstruttore=new LinkedList<String>(_listaPerIstruttore);
		listaPerAttrezzature=new LinkedList<String>(_listaPerAttrezzatura);
	}
	
	public Campo<Integer> getIstruttore(){
		return istruttore;
	}
	
	public Campo<Integer> getAttrezzatura(){
		return attrezzatura;
	}
	
	public LinkedList<String> getListaPerIstruttore(){
		return listaPerIstruttore;
	}
	
	public LinkedList<String> getListaPerAttrezzature(){
		return listaPerAttrezzature;
	}
	
	public String setIstruttore(String costoIstruttore) {
		if(!Campo.controlloIntero(costoIstruttore).equals(Campo.OK))
			return Campo.controlloIntero(costoIstruttore);
		istruttore.setValore(Integer.parseInt(costoIstruttore));
		return OK;
	}
	
	public String setAttrezzatura(String costoAttrezzatura) {
		if(!Campo.controlloIntero(costoAttrezzatura).equals(Campo.OK))
			return Campo.controlloIntero(costoAttrezzatura);
		attrezzatura.setValore(Integer.parseInt(costoAttrezzatura));
		return OK;
	}
	
	public String iscrizione(String nome, boolean istrut, boolean attrez) {
		String ritorno = super.iscrizione(nome);
		if(ritorno.equals(Notifica.ISCRIZIONE)) {
			if(istrut)
				listaPerIstruttore.add(nome);
			if(attrez)
				listaPerAttrezzature.add(nome);
		}
		return ritorno;
	}
	
	public Notifica cambioStato() {
		Notifica ritorno = super.cambioStato();
		if(ritorno==null)
			return ritorno;
		if(ritorno.getMessaggio().equals(Notifica.CHIUSO)) {
			ritorno = new Notifica(this, Notifica.CHIUSO + Notifica.COSTO_FINALE);
		}
		return ritorno;
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
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

}
