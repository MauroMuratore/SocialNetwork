
package server.core;

import java.util.GregorianCalendar;

public class Invito extends Notifica {

	private GregorianCalendar dataInvito;
	
	public Invito(Evento _evento) {
		super(_evento, Notifica.INVITO + _evento.getProprietario());
		dataInvito = new GregorianCalendar();
	}
	
	public Invito(Evento _evento, String messaggio, boolean letto, GregorianCalendar _dataInvito) {
		super(_evento, messaggio, letto);
		dataInvito = _dataInvito;
	}
	
	public GregorianCalendar getDataInvito() {
		return dataInvito;
	}

}
