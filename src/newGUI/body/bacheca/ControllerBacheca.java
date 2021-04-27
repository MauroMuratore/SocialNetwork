package newGUI.body.bacheca;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreePath;

import lib.core.Categoria;
import lib.core.EscursioneMontagnaEvento;
import lib.core.Evento;
import lib.core.Notifica;
import lib.core.PartitaCalcioEvento;
import lib.util.Nomi;
import newGUI.JError;

public class ControllerBacheca implements ActionListener {

	private ViewBC viewBC;
	private List<Categoria> categorie;
	private String nomeCat;
	private Evento evento; //da trasformare in un model in cui ho anche istruttore e attrezzatura
	private String azioneInCorso;
	private PanelCreaEvento pce;
	private boolean attrezzatura = false;
	private boolean istruttore = false;

	public ControllerBacheca(List<Categoria> cat) {
		categorie = new ArrayList<Categoria>(cat);
		viewBC = new ViewBC(cat);
		viewBC.addActionListener(this);
	}

	public ViewBC getViewBC() {
		return viewBC;
	}

	@Override
	public synchronized void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Avanti")) {
			if(viewBC.getSelectionPath()==null) {
				return;
			}
			else {
				TreePath selPath = viewBC.getSelectionPath();
				nomeCat = selPath.getPathComponent(selPath.getPathCount()-2).toString(); //ricevo la categoria
				if(nomeCat.equals(Nomi.CAT_PARTITA_CALCIO.getNome())) {
					int index;
					for(index=0; index<categorie.size(); index++) {
						if(categorie.get(index).getNome().equals(Nomi.CAT_PARTITA_CALCIO.getNome()))
							break;
					}
					if(selPath.getLastPathComponent().toString().equals(Nomi.AZIONE_CREA_EVENTO.getNome())) {
						List<String> interessati = categorie.get(index).getPersoneInteressate();
						pce = new PanelCreaEvento(nomeCat, interessati, this);
						viewBC.addPanel(pce);


					}
					else {

						for(PartitaCalcioEvento partita: (List<PartitaCalcioEvento>) categorie.get(index).getBacheca()) {
							if(selPath.getLastPathComponent().toString().equals(partita.getTitolo().getValore())) {
								evento = partita;
							}
						}

						viewBC.addPanel(new PanelVistaEvento(evento, this));

					}
				}
				else if(nomeCat.equals(Nomi.CAT_ESCURSIOME_MONTAGNA.getNome())) {
					int index;
					for(index=0; index<categorie.size(); index++) {
						if(categorie.get(index).getNome().equals(Nomi.CAT_ESCURSIOME_MONTAGNA.getNome()))
							break;
					}
					if(selPath.getLastPathComponent().toString().equals(Nomi.AZIONE_CREA_EVENTO.getNome())) {
						List<String> interessati = categorie.get(index).getPersoneInteressate();
						pce = new PanelCreaEvento(nomeCat, interessati, this);
						viewBC.addPanel(pce);
					}
					else {

						for(EscursioneMontagnaEvento escursione: (List<EscursioneMontagnaEvento>)categorie.get(index).getBacheca()) {
							if(selPath.getLastPathComponent().toString().equals(escursione.getTitolo().getValore()))
								evento = escursione;
						}

						viewBC.addPanel(new PanelVistaEvento(evento, this));

					}
				}
			}
		}

		else if(e.getActionCommand().equals(Nomi.AZIONE_ISCRIZIONE.getNome())) {
			azioneInCorso = Nomi.AZIONE_ISCRIZIONE.getNome();
			if(attrezzatura) {
				if(istruttore)
					azioneInCorso = Nomi.AZIONE_ISCRIZIONE_ATT_IST.getNome();
				else
					azioneInCorso = Nomi.AZIONE_ISCRIZIONE_ATT.getNome();
			}
			else if(istruttore)
				azioneInCorso = Nomi.AZIONE_ISCRIZIONE_IST.getNome();
			this.notify();
		}

		else if(e.getActionCommand().equals(Nomi.AZIONE_DISISCRIZIONE.getNome())) {
			azioneInCorso = Nomi.AZIONE_DISISCRIZIONE.getNome();
			this.notify();
		}

		else if(e.getActionCommand().equals(Nomi.AZIONE_CANCELLA_EVENTO.getNome())) {
			azioneInCorso = Nomi.AZIONE_CANCELLA_EVENTO.getNome();
			this.notify();
		}

		else if(e.getActionCommand().equals(Nomi.AZIONE_CREA_EVENTO.getNome())) {
			azioneInCorso = Nomi.AZIONE_CREA_EVENTO.getNome();
			String esito;

			esito = setEvento();

			if(esito.equals(Evento.OK))
				this.notify();
			else
				mostraEsito(esito);
				
		}

		else if(e.getActionCommand().equals("Indietro")) {
			viewBC.removePanelEsterno();
		}





		else if(e.getActionCommand().equals("Select Att"))
			attrezzatura = !attrezzatura;

		else if(e.getActionCommand().equals("Select Istr"))
			istruttore = !istruttore;

		//this.notifyAll(); il notify arriva solo dai bottoni iscrizione dis cancella e crea evento

	}

	public synchronized String getAzione() {
		try {
			this.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		attrezzatura =false;
		istruttore = false;
		return azioneInCorso;
	}

	public Evento getEvento() {
		return evento;
	}

	public void mostraEsito(String esito) {

		viewBC.removePanelEsterno();
		new JError(esito);
	}

	public void updateCat(List<Categoria> cat) {
		categorie = cat;
		viewBC.setCategorie(cat);
		viewBC.repaint();

	}

	public String setEvento() {
		String ritorno;
		if(nomeCat.equals(Nomi.CAT_PARTITA_CALCIO.getNome())) {
			evento = new PartitaCalcioEvento();
			ritorno = ((PartitaCalcioEvento) evento).setSesso(pce.getSesso());
			if(!ritorno.equals(Evento.OK))
				return ritorno;
			ritorno =((PartitaCalcioEvento) evento).setEta(pce.getEta());
			if(!ritorno.equals(Evento.OK))
				return ritorno;
		}
		else if(nomeCat.equals(Nomi.CAT_ESCURSIOME_MONTAGNA.getNome())) {
			evento = new EscursioneMontagnaEvento();
			ritorno =((EscursioneMontagnaEvento) evento).setIstruttore(pce.getIstruttore());
			if(!ritorno.equals(Evento.OK))
				return ritorno;
			ritorno =((EscursioneMontagnaEvento) evento).setAttrezzatura(pce.getAttrezzatura());
			if(!ritorno.equals(Evento.OK))
				return ritorno;
		}
		ritorno = evento.setTitolo(pce.getTitolo());
		if(!ritorno.equals(Evento.OK))
			return ritorno;
		ritorno = evento.setPartecipantiNecessari(pce.getPartecipanti());
		if(!ritorno.equals(Evento.OK))
			return ritorno;
		ritorno = evento.setTolleranzaPartecipanti(pce.getTolleranzaPartecipanti());
		if(!ritorno.equals(Evento.OK))
			return ritorno;
		ritorno = evento.setTermineUltimo(pce.getTermineUltimo());
		if(!ritorno.equals(Evento.OK))
			return ritorno;
		ritorno = evento.setTermineUltimoRitiro(pce.getTermineUltimoRitiro());
		if(!ritorno.equals(Evento.OK))
			return ritorno;
		ritorno = evento.setLuogo(pce.getLuogo());
		if(!ritorno.equals(Evento.OK))
			return ritorno;
		ritorno = evento.setDataInizio(pce.getDataInizio(), pce.getOraInizio());
		if(!ritorno.equals(Evento.OK))
			return ritorno;
		ritorno = evento.setDataFine(pce.getDataFine(), pce.getOraFine());
		if(!ritorno.equals(Evento.OK))
			return ritorno;
		ritorno = evento.setDurata(pce.getDurata());
		if(!ritorno.equals(Evento.OK))
			return ritorno;
		ritorno = evento.setDurata(pce.getDurata());
		if(!ritorno.equals(Evento.OK))
			return ritorno;
		ritorno = evento.setCompresoQuota(pce.getCompresoQuota());
		ritorno = evento.setNote(pce.getNote());

		return ritorno;
	}
	
	public List<String> getInviti(){
		return pce.getInviti();
	}


}
