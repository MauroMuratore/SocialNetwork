
package server.core;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import server.database.ConsultaDB;
import util.Nomi;
import util.ControlloCampo;
import util.Log;

//FINISHIM
public abstract class Evento {

	private int idEvento;
	private StatoEvento stato;
	protected LinkedList<String> partecipanti;
	protected String proprietario;
	protected Campo<String> titolo;
	protected Campo<Integer> partecipantiNecessari;
	protected Campo<GregorianCalendar> termineUltimoRitiro;
	protected Campo<GregorianCalendar> termineUltimo;
	protected Campo<String> luogo;
	protected Campo<GregorianCalendar> dataInizio;
	protected Campo<Integer> durata;
	protected Campo<Integer> quotaIndividuale;
	protected Campo<String> compresoQuota;
	protected Campo<GregorianCalendar> dataFine;
	protected Campo<String> note;
	protected Campo<Integer> tolleranzaPartecipanti;
	protected String categoria;


	public static final String EVENTO_VALIDO = "Evento valido";
	public static final String EVENTO_ESISTENTE = "ATTENZIONE: l'evento � gia esistente";
	public static final String VUOTO = "ATTENZIONE: il campo � vuoto";
	public static final String OK = "OK";
	public static final String FORMATO_SBAGLIATO = "ATTENZIONE: il formato � errato";
	public static final String PARTECIPANTI_NECESSARI_MIN = "Partecipanti minimi necessari";

	/**
	 * Da usare per la lettura da disco
	 * 
	 */
	public Evento(int idEvento, Campo<String> titolo, Campo<Integer> partecipantiNecessari, LinkedList<String> partecipanti, String proprietario,
			Campo<GregorianCalendar> termineUltimo, Campo<GregorianCalendar> termineUltimoRitiro, Campo<String> luogo, Campo<GregorianCalendar> dataInizio,
			Campo<Integer> durata, Campo<Integer> quotaIndividuale, Campo<String> compresoQuota,
			Campo<GregorianCalendar> dataFine, Campo<String> note, Campo<Integer> _tolleranzaPartecipanti, StatoEvento stato) {
		super();
		this.idEvento = idEvento;
		this.titolo = titolo;
		this.partecipantiNecessari = partecipantiNecessari;
		this.partecipanti= new LinkedList<String>(partecipanti);
		this.termineUltimo = termineUltimo;
		this.termineUltimoRitiro = termineUltimoRitiro;
		this.luogo = luogo;
		this.dataInizio = dataInizio;
		this.durata = durata;
		this.quotaIndividuale = quotaIndividuale;
		this.compresoQuota = compresoQuota;
		this.dataFine = dataFine;
		this.note = note;
		this.tolleranzaPartecipanti=_tolleranzaPartecipanti;
		this.stato=stato;
		this.proprietario= proprietario;
	}

	/**
	 * per creare un nuovo evento
	 * @param titolo
	 * @param partecipantiMax
	 * @param termineUltimo
	 * @param luogo
	 * @param dataInizio
	 * @param durata
	 * @param quotaIndividuale
	 * @param compresoQuota
	 * @param dataFine
	 * @param note
	 */
	public Evento(Campo<String> titolo, Campo<Integer> partecipantiMax, String proprietario,
			Campo<GregorianCalendar> termineUltimo, Campo<GregorianCalendar> termineUltimoRitiro,Campo<String> luogo, Campo<GregorianCalendar> dataInizio,
			Campo<Integer> durata, Campo<Integer> quotaIndividuale, Campo<String> compresoQuota,
			Campo<GregorianCalendar> dataFine, Campo<String> note, Campo<Integer> _tolleranzaPartecipanti) {
		super();
		this.titolo = titolo;
		this.partecipantiNecessari = partecipantiMax;
		this.termineUltimo = termineUltimo;
		this.termineUltimoRitiro=termineUltimoRitiro;
		this.luogo = luogo;
		this.dataInizio = dataInizio;
		this.durata = durata;
		this.quotaIndividuale = quotaIndividuale;
		this.compresoQuota = compresoQuota;
		this.dataFine = dataFine;
		this.note = note;
		this.idEvento = (int) System.currentTimeMillis();
		this.partecipanti = new LinkedList<String>();
		this.stato=StatoEvento.APERTO;
		this.tolleranzaPartecipanti=_tolleranzaPartecipanti;
	}

	public Evento() {
		this.idEvento = (int) System.currentTimeMillis();
		this.stato=StatoEvento.APERTO;
		this.partecipanti= new LinkedList<String>();
	}


	public int getIdEvento() {
		return idEvento;
	}

	public Campo<String> getTitolo() {
		return titolo;
	}
	public Campo<Integer> getPartecipantiNecessari() {
		return partecipantiNecessari;
	}
	public Campo<GregorianCalendar> getTermineUltimo() {
		return termineUltimo;
	}
	public LinkedList<String> getPartecipanti(){
		return partecipanti;
	}
	public Campo<String> getLuogo() {
		return luogo;
	}
	public Campo<GregorianCalendar> getDataInizio() {
		return dataInizio;
	}
	public Campo<GregorianCalendar> getTermineUltimoRitiro() {
		return termineUltimoRitiro;
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
	public Campo<GregorianCalendar> getDataFine() {
		return dataFine;
	}
	public Campo<String> getNote() {
		return note;
	}

	public StatoEvento getStato() {
		return stato;
	}
	
	public Campo<Integer> getTolleranzaPartecipanti(){
		return tolleranzaPartecipanti;
	}
	
	public String getProprietario() {
		return proprietario;
	}
	
	public String getCategoria() {
		return categoria;
	}


	public String setTitolo(String _titolo) {
		if(_titolo.equals("")) {
			return titolo.getNome() + VUOTO;
		}
		titolo.setValore(_titolo);
		return OK;
	}
	
	protected void setProprietario(String _proprietario) {
		proprietario=_proprietario;
	}
	
	public String setTermineUltimoRitiro(String _termineUltimoRitiro) {
		if(!ControlloCampo.controlloData(_termineUltimoRitiro).equals(Campo.OK))
			return ControlloCampo.controlloData(_termineUltimoRitiro);
		termineUltimoRitiro.setValore(ControlloCampo.assumiData(_termineUltimoRitiro));
		return OK;
	}

	public String setPartecipantiNecessari(String _partecipantiNecessari) {
		_partecipantiNecessari.trim();
		if(ControlloCampo.controlloIntero(_partecipantiNecessari).equals(Campo.FORMATO_INTERO_SBAGLIATO)) {
			return Campo.FORMATO_INTERO_SBAGLIATO;
		}
		int partNec = Integer.parseInt(_partecipantiNecessari);
		if(partNec<=0) {
			return PARTECIPANTI_NECESSARI_MIN;
		}
		partecipantiNecessari.setValore(Integer.parseInt(_partecipantiNecessari));
		return OK;
	}


	public String setTermineUltimo(String data) {
		if(!ControlloCampo.controlloData(data).equals(Campo.OK))
			return ControlloCampo.controlloData(data);
		termineUltimoRitiro.setValore(ControlloCampo.assumiData(data));
		termineUltimo.setValore(ControlloCampo.assumiData(data));
		return OK;
	}

	public String setLuogo(String _luogo) {
		if(_luogo.equals("")) {
			return luogo.getNome() + VUOTO;
		}
		luogo.setValore(_luogo);
		return OK;
	}

	public String setDataInizio(String data, String ora) {
		if(!ControlloCampo.controlloData(data, ora).equals(Campo.OK))
			return ControlloCampo.controlloData(data, ora);
		dataInizio.setValore(ControlloCampo.assumiData(data, ora));
		return OK;
	}

	public String setDurata(String _durata) {
		if(ControlloCampo.controlloIntero(_durata).equals(Campo.FORMATO_INTERO_SBAGLIATO))
			return Campo.FORMATO_INTERO_SBAGLIATO;
		durata.setValore(Integer.parseInt(_durata));
		return OK;
	}

	public String setQuotaIndividuale(String _quotaIndividuale) {
		if(ControlloCampo.controlloIntero(_quotaIndividuale).equals(Campo.FORMATO_INTERO_SBAGLIATO))
			return Campo.FORMATO_INTERO_SBAGLIATO;
		quotaIndividuale.setValore(Integer.parseInt(_quotaIndividuale));
		return OK;
	}

	public String setCompresoQuota(String _compresoQuota) {
		if(_compresoQuota.equals("")) {
			return compresoQuota.getNome() + VUOTO;
		}
		compresoQuota.setValore(_compresoQuota);
		return OK;
	}

	public String setDataFine(String data, String ora) {
		if(!ControlloCampo.controlloData(data, ora).equals(Campo.OK))
			return ControlloCampo.controlloData(data, ora);
		dataFine.setValore(ControlloCampo.assumiData(data, ora));
		return OK;
	}

	public String setNote(String _note) {
		if(_note.equals("")) {
			return note.getNome() + VUOTO;
		}
		note.setValore(_note);
		return OK;
	}
	
	public String setTolleranzaPartecipanti(String _tolleranzaPartecipanti) {
		if(!ControlloCampo.controlloIntero(_tolleranzaPartecipanti).equals(OK)) {
			return ControlloCampo.controlloIntero(_tolleranzaPartecipanti);
		}
		tolleranzaPartecipanti.setValore(Integer.parseInt(_tolleranzaPartecipanti));
		return OK;
	}

	/**
	 * torna vero se tutti i campi obbligatori sono stati compilati
	 * @return
	 */
	public abstract boolean valido(); 


	public String toString() {
		return titolo.toString() + partecipantiNecessari.toString() + termineUltimo.toString() + luogo.toString() + dataInizio.toString() +durata.toString() + quotaIndividuale.toString() +
				compresoQuota.toString() + dataFine.toString() + note.toString();
	}

	/**
	 * cambia lo stato dell'evento e torna una notifica 
	 * @return 
	 * null se lo stato non è cambiato 
	 * Notifica.CHIUSO se l'evento si e' chiuso
	 * Notifica.FALLITO se l'evento è fallit
	 */
	public Notifica cambioStato() {
		Date date = new Date(System.currentTimeMillis()); 
		GregorianCalendar oggi = new GregorianCalendar();
		oggi.setTime(date);
		Notifica ritorno=null;
		//passaggio da aperto a
		if(stato.equals(StatoEvento.APERTO)) {
			//chiuso
			if(termineUltimo.getValore().after(oggi) || termineUltimo.getValore().equals(oggi)) {
				if(partecipantiNecessari.getValore().intValue() <= partecipanti.size()
						&& partecipantiNecessari.getValore().intValue()+tolleranzaPartecipanti.getValore().intValue()>=partecipanti.size() ) {
					stato=StatoEvento.CHIUSO;
					ritorno= new Notifica(this, Notifica.CHIUSO);
				}					
			}
			//fallito
			if(termineUltimo.getValore().before(oggi)){
				stato=StatoEvento.FALLITO;
				ritorno= new Notifica(this, Notifica.FALLITO);
			}
		}
		else if(stato.equals(StatoEvento.CONCLUSO)) {
			if(dataFine.getValore().before(oggi)) {
				stato=StatoEvento.CONCLUSO;
				ritorno= new Notifica(this, Notifica.CONCLUSO);
			}
		}
		
		
		//devo salvare in caso lo stato sia cambiato
		return ritorno;
	}

	/**
	 * iscrive un nuovo utente all'evento
	 * dopo richiamare subito cambiaStato
	 * @param nome
	 * @return
	 */
	public String iscrizione(String nome) {
		String ritorno;
		if(stato.equals(StatoEvento.APERTO) && !partecipanti.contains(nome)) {
			partecipanti.add(nome);
			ritorno = Notifica.ISCRIZIONE;
			
		}else if(partecipanti.contains(nome))
			ritorno = Notifica.ISCRIZIONE_GIA_FATTA;
		else 
			ritorno = Notifica.ERRORE_DI_ISCRIZIONE;
		return ritorno;
		
	}

	public Notifica cancella(String _proprietario) {
		Date date = new Date(System.currentTimeMillis()); 
		GregorianCalendar oggi = new GregorianCalendar();
		oggi.setTime(date);
		Notifica ritorno;
		if(!proprietario.equals(_proprietario)) {
			Log.writeRoutineLog(this.getClass(), "proprietario diverso " + proprietario );
			ritorno = new Notifica (this, Notifica.PROPRIETARIO_DIVERSO);
		}
		else if(oggi.before(termineUltimoRitiro.getValore()) || oggi.equals(termineUltimoRitiro.getValore())) {
			stato=StatoEvento.CANCELLATO;
			ritorno = new Notifica (this, Notifica.EVENTO_CANCELLATO);
			
		}
		else 
			ritorno= new Notifica (this, Notifica.OLTRE_TUR);
		return ritorno;
	}

	public Notifica revocaIscrizione(String username) {
		Date date = new Date(System.currentTimeMillis()); 
		GregorianCalendar oggi = new GregorianCalendar();
		oggi.setTime(date);
		if(termineUltimoRitiro==null) {
			termineUltimoRitiro=termineUltimo;
		}
		Notifica ritorno=null;
		if(oggi.after(termineUltimoRitiro.getValore())) {
			ritorno = new Notifica(this, Notifica.OLTRE_TUR);
		}
		else if(!partecipanti.contains(username)) {
			ritorno = new Notifica(this, Notifica.NON_ISCRITTO);
		}
		else if(partecipanti.contains(username)) {
			partecipanti.remove(username);
			ritorno = new Notifica(this, Notifica.REVOCA_ISCRIZIONE);
		}
		return ritorno;
	}





}
