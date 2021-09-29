package lib.core.utenti;

import java.io.Serializable;

import lib.core.eventi.Evento;

public class Notifica implements Serializable{
	
	private Evento evento;
	private String messaggio;
	private boolean letta;
	
	public static final String INVITO = "SEI STATO INVITATO a";
	public static final String ISCRIZIONE = "Ti sei iscritto a ";
	public static final String CHIUSO = " ha chiuso le iscrizioni ";
	public static final String COSTO_FINALE = " ha un costo finale di ";
	public static final String FALLITO = " non ha raggiunto gli iscritti ";
	public static final String CONCLUSO = " e' concluso " ;
	public static final String ERRORE_DI_ISCRIZIONE = "errore di iscrizione ";
	public static final String PROPRIETARIO_DIVERSO = " ha un proprietario diverso ";
	public static final String EVENTO_CANCELLATO =" è stato cancellato cancellato ";
	public static final String OLTRE_TUR = "impossibile, superato il ultimo di ritiro di ";
	public static final String NON_ISCRITTO = "utente non iscritto a ";
	public static final String REVOCA_ISCRIZIONE = "revocata iscrizione con successo da ";
	public static final String ISCRIZIONE_GIA_FATTA = "iscrizione gia fatta a ";
	public static final String NUOVO_EVENTO_APERTO = "nuovo evento aperto ";
	public static final String NUMERO_MAX_PARTECIPANTI = " ha già raggiunto il numero massimo partecipanti";
	public static final String TUI_PASSATO = "Termine ultimo iscrizione passato";
	
	public Notifica(Evento _evento, String mes) {
		evento=_evento;
		messaggio=mes;
		letta=false;
	}
	
	public Notifica(Evento _evento, String mes, boolean letto) {
		evento =_evento;
		messaggio=mes;
		letta = letto;
	}
	
	public Evento getEvento() {
		return evento;
	}
	
	public String getMessaggio() {
		return messaggio;
	}
	
	public void letta() {
		letta=true;
	}
	
	public boolean getLetta() {
		return letta;
	}

}
