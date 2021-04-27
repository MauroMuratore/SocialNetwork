package newGUI.body.areaPersonale;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import lib.core.Utente;

public class ControllerAP implements ActionListener{
	
	private ViewAP viewAP;
	
	public ControllerAP(Utente utente) {
		viewAP = new ViewAP(utente, this);
	}
	
	public ViewAP getViewAP() {
		return viewAP;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
