package newGUI.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.List;

import lib.net.Channel;
import lib.util.Nomi;
import newGUI.JError;

public class ControllerLogin implements ActionListener, WindowListener {

	private Channel channel;
	private ViewLogin view;
	private ModelLogin model;
	private boolean isReg;

	public ControllerLogin(Channel _channel) {
		channel = _channel;
		List<String> listCat = null;;

		try {
			listCat = (List<String>) channel.read();
		} catch (IOException e) {
			System.err.println("perdita connessione con il server");
			new JError("perdita connessione con il server");
			e.printStackTrace();

		}
		String[] cat = new String[listCat.size()];
		for(int i=0; i<listCat.size(); i++) {
			cat[i]=listCat.get(i);
		}

		model = new ModelLogin();
		view = new ViewLogin(cat);
		view.addActionListener(this);
		view.addWindowListener(this);
	}

	@Override
	public synchronized void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Login")) {
			model.setUsername(view.getUsernameLog());
			model.setPassword(view.getPasswordLog());
			isReg = false;
		}
		else if(e.getActionCommand().equals("Registrazione")) {
			model.setUsername(view.getUsernameReg());
			model.setPassword(view.getPasswordReg());
			model.setConfermaPW(view.getConfermaPW());
			model.setEtaMin(view.getEtaMin());
			model.setEtaMax(view.getEtaMax());
			model.setCategoriePref(view.getCategoriePref());
			isReg = true;
		}
		this.notifyAll();
	}

	public synchronized ModelLogin getModel() {
		try {
			this.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}

	public boolean getIsReg() {
		return isReg;
	}

	public void displayError(String error) {
		new JError(error);
	}

	public void dispose() {
		view.dispose();
	}

	public void login() {
		String risposta = null;
		do{
			getModel();

			//invio prima username e password, che è comune a login e registrazione
			channel.write(model.getUsername());
			channel.write(model.getPassword());
			//invio boolean per sapere se è un login o registrazione
			channel.write(isReg);



			//se è una registrazione mando anche tutti gli altri dati
			if(isReg) {
				channel.write(model.getConfermaPW());
				channel.write(model.getEtaMin());
				channel.write(model.getEtaMax());
				channel.write(model.getCategoriePref());

			}


			try {
				risposta = (String) channel.read();
			} catch (IOException e) {
				System.err.println("perdita connessione con il server");
				new JError("perdita connessione con il server");
				e.printStackTrace();
			}
			if(!risposta.equals(Nomi.SN_BENVENUTO.getNome())) {
				new JError(risposta);
			}


		}while(!risposta.equals(Nomi.SN_BENVENUTO.getNome()));
		dispose();
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {

	}

	@Override
	public void windowClosing(WindowEvent e) {
		channel.write(Nomi.NET_CHIUSURA_SOCKET);
		channel.close();
		System.exit(0);

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}


}
