package cervello;

public class Notifica {
	
	private Evento evento;
	private String messaggio;
	
	public static final String ISCRIZIONE = "Ti sei iscritto all'evento";
	public static final String CHIUSO = "L'evento ha chiuso le iscrizioni";
	public static final String FALLITO = "L'evento non ha raggiunto gli iscritti";
	public static final String CONCLUSO = "L'evento è concluso";
	
	public Notifica(Evento _evento, String mes) {
		evento=_evento;
		messaggio=mes;
	}
	
	public Evento getEvento() {
		return evento;
	}
	
	public String getMessaggio() {
		return messaggio;
	}

}
