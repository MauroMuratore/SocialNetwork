
package lib.core;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Invito extends Notifica implements Serializable{

	private GregorianCalendar dataInvito;
	
	public Invito(Evento _evento) {
		super(_evento, Notifica.INVITO + _evento.getTitolo().getValoreString() + " da " +  _evento.getProprietario());
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
