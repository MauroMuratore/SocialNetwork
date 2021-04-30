package lib.net;

import java.io.Serializable;

public class ModelModificaUtente implements Serializable{
	
	private int etaMin;
	private int etaMax;
	private boolean catPartita;
	private boolean catEscursione;
	
	public ModelModificaUtente() {
		etaMin=0;
		etaMax=0;
		catPartita=false;
		catEscursione=false;
	}
	
	public int getEtaMin() {
		return etaMin;
	}
	public void setEtaMin(int etaMin) {
		this.etaMin = etaMin;
	}
	public int getEtaMax() {
		return etaMax;
	}
	public void setEtaMax(int etaMax) {
		this.etaMax = etaMax;
	}
	public boolean isCatPartita() {
		return catPartita;
	}
	public void setCatPartita(boolean catPartita) {
		this.catPartita = catPartita;
	}
	public boolean isCatEscursione() {
		return catEscursione;
	}
	public void setCatEscursione(boolean catEscursione) {
		this.catEscursione = catEscursione;
	}
	
	

}
