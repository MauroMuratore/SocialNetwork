package server.core;

public class Notifica {
	
	private Evento evento;
	private String messaggio;
	private boolean letta;
	
	public static final String INVITO = "SEI STATO INVITATO";
	public static final String ISCRIZIONE = "Ti sei iscritto all'evento";
	public static final String CHIUSO = "L'evento ha chiuso le iscrizioni ";
	public static final String COSTO_FINALE = "il costo dell'evento e' ";
	public static final String FALLITO = "L'evento non ha raggiunto gli iscritti ";
	public static final String CONCLUSO = "L'evento e' concluso " ;
	public static final String ERRORE_DI_ISCRIZIONE = "errore di iscrizione ";
	public static final String PROPRIETARIO_DIVERSO = "proprietario diverso ";
	public static final String EVENTO_CANCELLATO ="evento cancellato ";
	public static final String OLTRE_TUR = "impossibile, oltre termine ultimo di ritiro ";
	public static final String NON_ISCRITTO = "utente non iscritto ";
	public static final String REVOCA_ISCRIZIONE = "revocata iscrizione con successo ";
	public static final String ISCRIZIONE_GIA_FATTA = "iscrizione gia fatta ";
	public static final String NUOVO_EVENTO_APERTO = "nuovo evento aperto ";
	
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
