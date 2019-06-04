package cervello;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import database.ConsultaDB;
import database.NomiDB;

//FINISHIM
public abstract class Evento {

	private int idEvento;
	private StatoEvento stato;
	protected LinkedList<String> partecipanti;
	protected Campo<String> titolo;
	protected Campo<Integer> partecipantiNecessari;
	protected Campo<GregorianCalendar> termineUltimo;
	protected Campo<String> luogo;
	protected Campo<GregorianCalendar> dataInizio;
	protected Campo<Integer> durata;
	protected Campo<Integer> quotaIndividuale;
	protected Campo<String> compresoQuota;
	protected Campo<GregorianCalendar> dataFine;
	protected Campo<String> note;


	public static final String EVENTO_VALIDO = "Evento valido";
	public static final String EVENTO_ESISTENTE = "ATTENZIONE: l'evento � gia esistente";
	public static final String VUOTO = "ATTENZIONE: il campo � vuoto";
	public static final String OK = "it's ok";
	public static final String FORMATO_SBAGLIATO = "ATTENZIONE: il formato � errato";
	public static final String PARTECIPANTI_NECESSARI_MIN = null;

	/**
	 * Da usare per la lettura da disco
	 * 
	 */
	public Evento(int idEvento, Campo<String> titolo, Campo<Integer> partecipantiNecessari, LinkedList<String> partecipanti,
			Campo<GregorianCalendar> termineUltimo, Campo<String> luogo, Campo<GregorianCalendar> dataInizio,
			Campo<Integer> durata, Campo<Integer> quotaIndividuale, Campo<String> compresoQuota,
			Campo<GregorianCalendar> dataFine, Campo<String> note) {
		super();
		this.idEvento = idEvento;
		this.titolo = titolo;
		this.partecipantiNecessari = partecipantiNecessari;
		this.partecipanti=(LinkedList<String>) partecipanti.clone();
		this.termineUltimo = termineUltimo;
		this.luogo = luogo;
		this.dataInizio = dataInizio;
		this.durata = durata;
		this.quotaIndividuale = quotaIndividuale;
		this.compresoQuota = compresoQuota;
		this.dataFine = dataFine;
		this.note = note;
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
	public Evento(Campo<String> titolo, Campo<Integer> partecipantiMax,
			Campo<GregorianCalendar> termineUltimo, Campo<String> luogo, Campo<GregorianCalendar> dataInizio,
			Campo<Integer> durata, Campo<Integer> quotaIndividuale, Campo<String> compresoQuota,
			Campo<GregorianCalendar> dataFine, Campo<String> note) {
		super();
		this.titolo = titolo;
		this.partecipantiNecessari = partecipantiMax;
		this.termineUltimo = termineUltimo;
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
	}

	public Evento() {
		this.idEvento = (int) System.currentTimeMillis();
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


	public String setTitolo(String _titolo) {
		if(_titolo.equals("")) {
			return titolo.getNome() + VUOTO;
		}
		titolo.setValore(_titolo);
		return OK;
	}

	public String setPartecipantiNecessari(String _partecipantiNecessari) {
		_partecipantiNecessari.trim();
		if(Campo.controlloIntero(_partecipantiNecessari).equals(Campo.FORMATO_INTERO_SBAGLIATO)) {
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
		if(!Campo.controlloData(data).equals(Campo.OK))
			return Campo.controlloData(data);
		termineUltimo.setValore(Campo.assumiData(data));
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
		if(!Campo.controlloData(data, ora).equals(Campo.OK))
			return Campo.controlloData(data, ora);
		dataInizio.setValore(Campo.assumiData(data, ora));
		return OK;
	}

	public String setDurata(String _durata) {
		if(Campo.controlloIntero(_durata).equals(Campo.FORMATO_INTERO_SBAGLIATO))
			return Campo.FORMATO_INTERO_SBAGLIATO;
		durata.setValore(Integer.parseInt(_durata));
		return OK;
	}

	public String setQuotaIndividuale(String _quotaIndividuale) {
		if(Campo.controlloIntero(_quotaIndividuale).equals(Campo.FORMATO_INTERO_SBAGLIATO))
			return Campo.FORMATO_INTERO_SBAGLIATO;
		quotaIndividuale.setValore(Integer.parseInt(_quotaIndividuale));
		return OK;
	}

	public String setCompresoQuota(String _compresoQuota) {
		if(_compresoQuota.equals("")) {
			return compresoQuota.getNome() + VUOTO;
		}
		luogo.setValore(_compresoQuota);
		return OK;
	}

	public String setDataFine(String data, String ora) {
		if(!Campo.controlloData(data, ora).equals(Campo.OK))
			return Campo.controlloData(data, ora);
		dataFine.setValore(Campo.assumiData(data, ora));
		return OK;
	}

	public String setNote(String _note) {
		if(_note.equals("")) {
			return note.getNome() + VUOTO;
		}
		note.setValore(_note);
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
	 * @return null se lo stato non è cambiato
	 */
	public Notifica cambioStato() {
		Date date = new Date(System.currentTimeMillis()); 
		GregorianCalendar oggi = new GregorianCalendar();
		oggi.setTime(date);
		//passaggio da aperto a
		if(stato==StatoEvento.APERTO) {
			//chiuso
			if(termineUltimo.getValore().after(oggi) || termineUltimo.getValore().equals(oggi)) {
				if(partecipantiNecessari.getValore().intValue() == partecipanti.size()) {
					stato=StatoEvento.CHIUSO;
					return new Notifica(this, Notifica.CHIUSO);
				}					
			}
			//fallito
			if(termineUltimo.getValore().before(oggi)){
				stato=StatoEvento.FALLITO;
				return new Notifica(this, Notifica.FALLITO);
			}
		}
		else if(stato==StatoEvento.CONCLUSO) {
			if(dataFine.getValore().before(oggi)) {
				stato=StatoEvento.CONCLUSO;
				return new Notifica(this, Notifica.CONCLUSO);
			}
		}

		return null;
	}

	/**
	 * iscrive un nuovo utente all'evento
	 * dopo richiamare subito cambiaStato
	 * @param nome
	 * @return
	 */
	public Notifica iscrizione(String nome) {
		if(stato==StatoEvento.APERTO) {
			partecipanti.add(nome);
			return new Notifica(this, Notifica.ISCRIZIONE);
		}
		return null;
	}





}
