package newGUI.body;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lib.core.Categoria;
import lib.core.EscursioneMontagnaCat;
import lib.core.EscursioneMontagnaEvento;
import lib.core.Evento;
import lib.core.Notifica;
import lib.core.PartitaCalcioCat;
import lib.core.PartitaCalcioEvento;
import lib.core.Utente;
import lib.net.Channel;
import lib.util.Nomi;
import newGUI.body.areaPersonale.ControllerAP;
import newGUI.body.bacheca.ControllerBacheca;

public class ControllerBody implements ActionListener {


	private ControllerBacheca controllerBC;
	private ControllerAP controllerAP;
	private ViewBody viewBody;
	private Channel channel;
	private int controller;
	private String azione;
	private boolean toContinue =false;

	public static final int CONTROLLER_BC = 0;
	public static final int CONTROLLER_AP = 1;


	public ControllerBody(Channel _channel) {
		channel =_channel;
		List<Categoria> cat = leggiCat();
		Utente utente = leggiUtente();
		controllerBC = new ControllerBacheca(cat, this, utente.getUsername());
		controllerAP = new ControllerAP(utente, this);
		viewBody = new ViewBody(controllerBC.getViewBC(), controllerAP.getViewAP(), this);

	}

	public synchronized void cicloVita() {
		do {
			azione = null;
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(toContinue) continue;


			if(controller==CONTROLLER_BC) {
				azione =controllerBC.getAzione();
			}
			else if(controller==CONTROLLER_AP) 
				azione = controllerAP.getAzione();



			if(controller==CONTROLLER_BC) {
				Evento eventoDaInviare = controllerBC.getEvento();
				if(azione.equals(Nomi.AZIONE_CREA_EVENTO.getNome())) {
					if(!eventoDaInviare.valido()) {
						continue;
					}
					channel.write(azione);
					channel.write(eventoDaInviare);

					channel.write(controllerBC.getInviti());
				}
				else {
					channel.write(azione);
					channel.write(eventoDaInviare);
				}
			}

			else if(controller==CONTROLLER_AP) {
				channel.write(azione);
				if(azione.equals(Nomi.AZIONE_CANCELLA_NOTIFICA.getNome())) {
					channel.write(controllerAP.getIndiceNotifica());
				}else if(azione.equals(Nomi.AZIONE_MODIFICA_PROFILO.getNome())) {
					channel.write(controllerAP.getMMU());
				}

			}

			String esito=null;
			try {
				esito = (String) channel.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(controller==CONTROLLER_BC) {
				controllerBC.mostraEsito(esito);

			}

			else if(controller==CONTROLLER_AP) {
				controllerAP.mostraEsito(esito);

			}

			controllerBC.update(leggiCat());
			controllerAP.updateNotifiche(leggiNot());
			viewBody.updateGUI();

		}while(true);
	}

	@Override
	public synchronized void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Logout" : channel.write(Nomi.NET_CHIUSURA_SOCKET.getNome());
		System.exit(0);
		break;

		case "Area Personale": controller = CONTROLLER_AP;
		viewBody.showView(CONTROLLER_AP);
		toContinue=true;
		break;

		case "Vista Bacheca": controller = CONTROLLER_BC;
		viewBody.showView(CONTROLLER_BC);
		toContinue=true;
		break;

		default: 
			toContinue=false;
		}
		;

		this.notifyAll();
	}

	public Utente leggiUtente() {
		Utente ritorno = null;
		try {
			ritorno = (Utente) channel.read();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return ritorno;
	}

	public List<Notifica> leggiNot(){
		List<Notifica> ritorno = new LinkedList<Notifica>();
		try {
			do {
				String mex = (String) channel.read();
				if(mex.equals(Nomi.NET_EOL.getNome()))
					break;
				ritorno.add((Notifica) channel.read());
			}while(true);
		} catch(IOException e) {
			e.printStackTrace();
		}
		return ritorno;
	}

	public List<Categoria> leggiCat(){
		List<Categoria> categorie= new LinkedList<Categoria>();
		try {
			do {
				String eol = (String) channel.read();
				if(eol.equals(Nomi.NET_EOL.getNome()))
					break;

				String nomeCat = (String) channel.read();
				String descrizione = (String) channel.read();
				Categoria cat = null;
				if(nomeCat.equals(Nomi.CAT_PARTITA_CALCIO.getNome())) {
					cat = new PartitaCalcioCat<>(nomeCat, descrizione);
				} else if(nomeCat.equals(Nomi.CAT_ESCURSIOME_MONTAGNA.getNome())) {
					cat = new EscursioneMontagnaCat<>(nomeCat, descrizione);
				}
				do {
					String mex = (String) channel.read();
					if(mex.equals(Nomi.NET_EOL.getNome()))
						break;
					String interessato = (String) channel.read();
					cat.addPersonaInteressata(interessato);
				}while(true);

				if(nomeCat.equals(Nomi.CAT_PARTITA_CALCIO.getNome())) {
					do {
						String mex = (String) channel.read();
						if(mex.equals(Nomi.NET_EOL.getNome()))
							break;
						PartitaCalcioEvento pce = (PartitaCalcioEvento) channel.read();
						cat.aggiungiEvento(pce);
					}while(true);

				}else if(nomeCat.equals(Nomi.CAT_ESCURSIOME_MONTAGNA.getNome())) {
					do {
						String mex = (String) channel.read();
						if(mex.equals(Nomi.NET_EOL.getNome()))
							break;
						EscursioneMontagnaEvento eme = (EscursioneMontagnaEvento) channel.read();
						cat.aggiungiEvento(eme);
					}while(true);

				}
				categorie.add(cat);
			}while (true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return categorie;
	}






}
