package gui.body.bacheca;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreePath;

import gui.JAvviso;
import gui.body.ControllerBody;
import lib.core.categorie.Categoria;
import lib.core.eventi.EscursioneMontagnaEvento;
import lib.core.eventi.Evento;
import lib.core.eventi.PartitaCalcioEvento;
import lib.core.utenti.Notifica;
import lib.util.Nomi;

public class ControllerBacheca implements ActionListener {

	private ViewBC viewBC;
	private List<Categoria> categorie;
	private String nomeCat;
	private Evento evento; //da trasformare in un model in cui ho anche istruttore e attrezzatura
	private String azioneInCorso;
	private PanelCreaEvento pce;
	private boolean attrezzatura = false;
	private boolean istruttore = false;
	private ActionListener father;
	private String utente;
	
	public ControllerBacheca(List<Categoria> cat, ActionListener _father, String _utente) {
		categorie = new ArrayList<Categoria>(cat);
		viewBC = new ViewBC(cat, this);
		father=_father;
		utente = _utente;
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
						pce = new PanelCreaEvento(nomeCat, interessati, this, father, utente);
						viewBC.addPanel(pce);


					}
					else {

						for(PartitaCalcioEvento partita: (List<PartitaCalcioEvento>) categorie.get(index).getBacheca()) {
							if(selPath.getLastPathComponent().toString().equals(partita.getTitolo().getValore())) {
								evento = partita;
							}
						}

						viewBC.addPanel(new PanelVistaEvento(evento, this, father));

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
						pce = new PanelCreaEvento(nomeCat, interessati, this, father, utente);
						viewBC.addPanel(pce);
					}
					else {

						for(EscursioneMontagnaEvento escursione: (List<EscursioneMontagnaEvento>)categorie.get(index).getBacheca()) {
							if(selPath.getLastPathComponent().toString().equals(escursione.getTitolo().getValore()))
								evento = escursione;
						}

						viewBC.addPanel(new PanelVistaEvento(evento, this, father));

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
			
			this.notifyAll();
			
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

			if(esito.equals(Evento.OK)) {
				this.notify();
			}
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
		attrezzatura =false;
		istruttore = false;
		return azioneInCorso;
	}
	

	public synchronized Evento getEvento() {
		return evento;
	}

	public void mostraEsito(String esito) {

		viewBC.removePanelEsterno();
		new JAvviso(esito);
	}

	public void update(List<Categoria> cat) {
		categorie = cat;
		viewBC.setCategorie(cat);

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
		ritorno = evento.setQuotaIndividuale(pce.getQuota());
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
