
package lib.core;

import java.io.Serializable;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import lib.util.ControlloCampo;
import lib.util.Log;
import lib.util.Nomi;
import server.database.ConsultaDB;

//FINISHIM
public abstract class Evento implements Serializable {

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
	public static final String DATA_PASSATA ="La data è già passata";

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
		if(termineUltimoRitiro!=null)
			this.termineUltimoRitiro = termineUltimoRitiro;
		else
			this.termineUltimoRitiro = termineUltimo;
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
		System.out.println(titolo.getValoreString() + termineUltimo.getValoreString());
		if(termineUltimoRitiro!=null)
			this.termineUltimoRitiro = termineUltimoRitiro;
		else
			this.termineUltimoRitiro = termineUltimo;
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

	public void setProprietario(String _proprietario) {
		proprietario=_proprietario;
	}

	/**
	 * DA USARE DOPO SetTermineUltimo
	 * @param _termineUltimoRitiro
	 * @return
	 */
	public String setTermineUltimoRitiro(String _termineUltimoRitiro) {
		if(_termineUltimoRitiro.equals("")) {
			return OK;
		}
		if(!ControlloCampo.controlloData(_termineUltimoRitiro).equals(Campo.OK))
			return Nomi.CAMPO_TERMINE_ULTIMO_RITIRO.getNome() + ControlloCampo.controlloData(_termineUltimoRitiro);

		Date date = new Date(System.currentTimeMillis()); 
		GregorianCalendar oggi = new GregorianCalendar();
		oggi.setTime(date);

		GregorianCalendar dataCampo = ControlloCampo.assumiData(_termineUltimoRitiro);
		if(dataCampo.before(oggi))
			return DATA_PASSATA;

		termineUltimoRitiro.setValore(dataCampo);
		return OK;
	}

	public String setPartecipantiNecessari(String _partecipantiNecessari) {
		_partecipantiNecessari.trim();
		if(ControlloCampo.controlloIntero(_partecipantiNecessari).equals(Campo.FORMATO_INTERO_SBAGLIATO)) {
			return Nomi.CAMPO_PARTECIPANTI_MAX.getNome() + Campo.FORMATO_INTERO_SBAGLIATO;
		}
		int partNec = Integer.parseInt(_partecipantiNecessari);
		if(partNec<=0) {
			return Nomi.CAMPO_PARTECIPANTI_MAX.getNome() + PARTECIPANTI_NECESSARI_MIN;
		}
		partecipantiNecessari.setValore(Integer.parseInt(_partecipantiNecessari));
		return OK;
	}


	public String setTermineUltimo(String data) {
		if(!ControlloCampo.controlloData(data).equals(Campo.OK))
			return Nomi.CAMPO_TERMINE_ULTIMO.getNome() + ControlloCampo.controlloData(data);
		Date date = new Date(System.currentTimeMillis()); 
		GregorianCalendar oggi = new GregorianCalendar();
		oggi.setTime(date);

		GregorianCalendar dataCampo = ControlloCampo.assumiData(data);
		if(dataCampo.before(oggi))
			return DATA_PASSATA;

		if(termineUltimoRitiro.getValore()==null)
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
		if(!ControlloCampo.controlloOra(data, ora).equals(Campo.OK))
			return Nomi.CAMPO_DATA_INIZIO.getNome() + ControlloCampo.controlloOra(data, ora);
		Date date = new Date(System.currentTimeMillis()); 
		GregorianCalendar oggi = new GregorianCalendar();
		oggi.setTime(date);
		
		GregorianCalendar dataCampo = ControlloCampo.assumiOra(data, ora);
		if(dataCampo.before(oggi))
			return DATA_PASSATA;
		
		dataInizio.setValore(ControlloCampo.assumiOra(data, ora));
		return OK;
	}

	public String setDurata(String _durata) {
		if(_durata.equals("")){
			return OK;
		}
		if(ControlloCampo.controlloIntero(_durata).equals(Campo.FORMATO_INTERO_SBAGLIATO))
			return Nomi.CAMPO_DURATA.getNome() + Campo.FORMATO_INTERO_SBAGLIATO;
		durata.setValore(Integer.parseInt(_durata));
		return OK;
	}

	public String setQuotaIndividuale(String _quotaIndividuale) {
		if(ControlloCampo.controlloIntero(_quotaIndividuale).equals(Campo.FORMATO_INTERO_SBAGLIATO))
			return Nomi.CAMPO_QUOTA_IND.getNome() + Campo.FORMATO_INTERO_SBAGLIATO;
		quotaIndividuale.setValore(Integer.parseInt(_quotaIndividuale));
		return OK;
	}

	public String setCompresoQuota(String _compresoQuota) {
		compresoQuota.setValore(_compresoQuota);
		return OK;
	}

	public String setDataFine(String data, String ora) {
		if(data.equals("")) {
			return OK;
		}
		if(!ControlloCampo.controlloOra(data, ora).equals(Campo.OK))
			return Nomi.CAMPO_DATA_FINE.getNome() + ControlloCampo.controlloOra(data, ora);
		dataFine.setValore(ControlloCampo.assumiOra(data, ora));
		return OK;
	}

	public String setNote(String _note) {
		note.setValore(_note);
		return OK;
	}

	public String setTolleranzaPartecipanti(String _tolleranzaPartecipanti) {
		if(_tolleranzaPartecipanti.equals("")) {
			tolleranzaPartecipanti.setValore(0);
			return OK;
		}
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

			if(termineUltimo.getValore().after(oggi)
					|| termineUltimo.getValore().equals(oggi)) {
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
			int limitePartecipanti = partecipantiNecessari.getValore().intValue();
			if(tolleranzaPartecipanti!=null)
				limitePartecipanti = limitePartecipanti + tolleranzaPartecipanti.getValore().intValue();
			if(partecipanti.size()<limitePartecipanti) {
				partecipanti.add(nome);
				ritorno = Notifica.ISCRIZIONE;
			}
			else
				ritorno = Notifica.NUMERO_MAX_PARTECIPANTI;

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
