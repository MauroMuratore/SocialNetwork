package newGUI.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerLogin implements ActionListener {
	
	
	private ViewLogin view;
	
	private String username;
	private String password;
	private String confermaPW;
	private int etaMin;
	private int etaMax;
	private String[] categoriePref;
	private boolean isReg;
	
	public ControllerLogin() {
		view = new ViewLogin();
		view.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Login")) {
			username = view.getUsernameLog();
		}
			
	}
	

}
