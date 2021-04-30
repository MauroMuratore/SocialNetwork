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
	private Object synchroToken;


	public static final int CONTROLLER_BC = 0;
	public static final int CONTROLLER_AP = 1;


	public ControllerBody(Channel _channel) {
		channel =_channel;
		controllerBC = new ControllerBacheca(leggiCat(), this);
		controllerAP = new ControllerAP(leggiUtente(), this);
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

			System.out.println(" --- " + azione);
			channel.write(azione);

			if(controller==CONTROLLER_BC) {
				channel.write(controllerBC.getEvento());
				if(azione.equals(Nomi.AZIONE_CREA_EVENTO.getNome())) {
					channel.write(controllerBC.getInviti());
				}
			}

			else if(controller==CONTROLLER_AP) {
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
		System.out.println("sono stato premuto " + e.getActionCommand() );
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
			System.out.println(e.getActionCommand());
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
		System.out.println("ricevuto l'utente con " + ritorno.getNotifiche().size() + " notifiche");
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
		System.out.println("ricevo le notifiche utente " +ritorno.size());
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
