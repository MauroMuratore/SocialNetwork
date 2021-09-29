package gui.body.areaPersonale;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import gui.JAvviso;
import gui.body.ControllerBody;
import lib.core.Notifica;
import lib.core.Utente;
import lib.net.ModelModificaUtente;
import lib.util.Nomi;

public class ControllerAP implements ActionListener{
	
	private ViewAP viewAP;
	private Utente utente;
	private String azione;
	private Integer indiceNotifica;
	private PanelModificaUtente pmu;
	private lib.net.ModelModificaUtente mmu;
	private ActionListener father;
	
	
	public ControllerAP(Utente _utente, ActionListener _father) {
		utente = _utente;
		viewAP = new ViewAP(utente, this, father);
		azione=null;
		indiceNotifica = null;
		mmu = new lib.net.ModelModificaUtente();
		father =_father;
		viewAP.setActionListener(_father);
	}
	
	public ViewAP getViewAP() {
		return viewAP;
	}

	@Override
	public synchronized void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals(Nomi.AZIONE_MODIFICA_PROFILO.getNome())) {
			azione = Nomi.AZIONE_MODIFICA_PROFILO.getNome();
			mmu.setEtaMin(pmu.getEtaMin());
			mmu.setEtaMax(pmu.getEtaMax());
			String[] catPref = pmu.getCatPref();
			for(int i=0; i<catPref.length; i++) {
				if(Nomi.CAT_PARTITA_CALCIO.getNome().equals(catPref[i])) {
					mmu.setCatPartita(true);
				}
				else if(Nomi.CAT_ESCURSIOME_MONTAGNA.getNome().equals(catPref[i])) {
					mmu.setCatEscursione(true);
				}
			}
			viewAP.removePanelModifica(pmu);
			this.notify();
		}
		else if(e.getActionCommand().equals(Nomi.AZIONE_CANCELLA_NOTIFICA.getNome())) {
			String indice = viewAP.getNotifica();
			if(indice==null) return;
			azione = Nomi.AZIONE_CANCELLA_NOTIFICA.getNome();
			int i = selezionaIndiceNotifica(indice);
			indiceNotifica = new Integer(i);
			this.notify();
		}else if(e.getActionCommand().equals("Inizio Modifica")) {
			pmu = new PanelModificaUtente(utente, this, father);
			mmu = new ModelModificaUtente();
			viewAP.addPanelModifica(pmu);		
		}
	}

	public synchronized String getAzione() {
		return azione;
	}
	
	public Integer getIndiceNotifica() {
		System.out.println(indiceNotifica);
		return indiceNotifica;
	}
	
	public ModelModificaUtente getMMU() {
		return mmu;
	}
	
	public int selezionaIndiceNotifica(String input) {
		String daTrasformare ="";
		for(int i=0; i<input.length();i++) {
			char c =input.charAt(i);
			if(c>='0'  && c<='9') {
				daTrasformare = daTrasformare + c;
			}
			else 
				break;
		}
		int ritorno = Integer.parseInt(daTrasformare);
		
		return ritorno;
	}

	public void mostraEsito(String esito) {
		if(esito.equals(Utente.MODIFICA_RIUSCITA)) {
			viewAP.aggiornaUtente(mmu);
		}
		new JAvviso(esito);
		
	}

	public void updateNotifiche(List<Notifica> notifiche) {
		viewAP.aggiornaNotifiche(notifiche);
	}
	

}
