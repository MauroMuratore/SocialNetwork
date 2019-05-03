package cervello;

import java.sql.Time;
import java.util.Calendar;

public abstract class Evento {
	
	private Campo<String> titolo;
	private Campo<Integer> partecipanti;
	private Campo<Calendar> termineUltimo;
	private Campo<String> luogo;
	private Campo<Calendar> dataInizio;
	private Campo<Time> orarioInizio;
	//Si può fare così, oppure in dataInizio settare l'ora e poi fare sia il metodo getOrario e getDataInizio
	private Campo<Integer> durata;
	private Campo<Integer> quotaIndividuale;
	private Campo<String> compresoQuota;
	private Campo<Calendar> dataFine;
	private Campo<Time> orarioFine;
	//stesso discorso di dataInizio
	private Campo<String> note;
	
	

}
