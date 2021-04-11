package newGUI.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ControllerLogin implements ActionListener {
	
	
	private ViewLogin view;
	private ModelLogin model;
	private String username;
	private String password;
	private String confermaPW;
	private int etaMin;
	private int etaMax;
	private String[] categoriePref;
	private boolean isReg;
	
	public ControllerLogin(String[] cat) {
		model = new ModelLogin();
		view = new ViewLogin(cat);
		view.addActionListener(this);
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
			model.setCategoriePref(view.getCategoriePref());;
			//categoriePref = view.getCategoriePref().clone();
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
	
	public void dispose() {
		view.dispose();
	}

	
}
