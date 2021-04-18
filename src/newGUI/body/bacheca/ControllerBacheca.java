package newGUI.body.bacheca;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreePath;

import lib.core.Categoria;
import lib.core.EscursioneMontagnaEvento;
import lib.core.Evento;
import lib.core.PartitaCalcioEvento;
import lib.util.Nomi;

public class ControllerBacheca implements ActionListener {

	private ViewBC viewBC;
	private List<Categoria> categorie;
	private Evento evento;
	private String azioneInCorso;

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
				String cat = selPath.getPathComponent(selPath.getPathCount()-2).toString(); //ricevo la categoria
				if(cat.equals(Nomi.CAT_PARTITA_CALCIO.getNome())) {
					if(selPath.getLastPathComponent().toString().equals("Crea Evento")) {
						
					

					}
					else {
						int index;
						for(index=0; index<categorie.size(); index++) {
							if(categorie.get(index).getNome().equals(Nomi.CAT_PARTITA_CALCIO.getNome()))
								break;
						}
						for(PartitaCalcioEvento partita: (List<PartitaCalcioEvento>) categorie.get(index).getBacheca()) {
							if(selPath.getLastPathComponent().toString().equals(partita.getTitolo().getValore())) {
								evento = partita;
							}
						}

						viewBC.addPanel(new PanelVistaEvento(evento, this));

					}
				}
				else if(cat.equals(Nomi.CAT_ESCURSIOME_MONTAGNA.getNome())) {
					if(selPath.getLastPathComponent().toString().equals("Crea Evento")) {

					}
					else {
						int index;
						for(index=0; index<categorie.size(); index++) {
							if(categorie.get(index).getNome().equals(Nomi.CAT_ESCURSIOME_MONTAGNA.getNome()))
								break;
						}
						for(EscursioneMontagnaEvento escursione: (List<EscursioneMontagnaEvento>)categorie.get(index).getBacheca()) {
							if(selPath.getLastPathComponent().toString().equals(escursione.getTitolo().getValore()))
								evento = escursione;
						}
						
						viewBC.addPanel(new PanelVistaEvento(evento, this));

					}
				}
			}
		}
		
		
		
		this.notifyAll();

	}
	
	public synchronized String getAzione() {
		try {
			this.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return azioneInCorso;
	}



}
