
package lib.core;

import java.util.GregorianCalendar;
import java.util.LinkedList;

import lib.util.ControlloCampo;
import lib.util.Nomi;
import server.database.ConsultaDB;

public class EscursioneMontagnaEvento extends Evento {
	
	private Campo<Integer> istruttore;
	private Campo<Integer> attrezzatura;
	private LinkedList<String> listaPerIstruttore;
	private LinkedList<String> listaPerAttrezzature;
	
	public EscursioneMontagnaEvento() {
		super();
		titolo = new Campo<String>(Nomi.CAMPO_TITOLO.getNome(), "", true);
		partecipantiNecessari = new Campo<Integer>(Nomi.CAMPO_PARTECIPANTI_MAX.getNome(), "", true);
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
		istruttore = new Campo<Integer>(Nomi.CAMPO_ISTRUTTORE.getNome(), "", false);
		attrezzatura = new Campo<Integer>(Nomi.CAMPO_ATTREZZATURA.getNome(), "", false);
		listaPerAttrezzature = new LinkedList<String>();
		listaPerIstruttore = new LinkedList<String>();
		categoria=Nomi.CAT_ESCURSIOME_MONTAGNA.getNome();
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
		categoria=Nomi.CAT_ESCURSIOME_MONTAGNA.getNome();
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
		
		if(!ControlloCampo.controlloIntero(costoIstruttore).equals(Campo.OK))
			return ControlloCampo.controlloIntero(costoIstruttore);
		istruttore.setValore(Integer.parseInt(costoIstruttore));
		return OK;
	}
	
	public String setAttrezzatura(String costoAttrezzatura) {
		if(!ControlloCampo.controlloIntero(costoAttrezzatura).equals(Campo.OK))
			return ControlloCampo.controlloIntero(costoAttrezzatura);
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
	
	public Notifica revocaIscrizione(String username) {
		Notifica ritorno = super.revocaIscrizione(username);
		if(ritorno.getMessaggio().equals(Notifica.REVOCA_ISCRIZIONE)) {
			listaPerAttrezzature.remove(username);
			listaPerIstruttore.remove(username);
		}
		
		return ritorno;
			
	}
	
	
	@Override
	public boolean valido() {
		ConsultaDB cdb = ConsultaDB.getInstance();
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
		else if(cdb.controllaEvento(getIdEvento()))
			return false;

		return true;
	}

}
