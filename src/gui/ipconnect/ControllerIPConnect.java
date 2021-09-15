package gui.ipconnect;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

public class ControllerIPConnect implements MouseListener {

	private ViewIPConnect view;
	private String stringAddress;
	private InetAddress inet;

	public ControllerIPConnect() {
		view = new ViewIPConnect();
		view.addMouseListener(this);
	}

	public synchronized InetAddress getIPAddress() {
		try {
			this.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		view.dispose();
		return inet;
	}

	public boolean controllIP(String addr) {
		int contPunto=0;
		for(int i=0; i<addr.length(); i++) {
			if(!((addr.charAt(i)>='0' && addr.charAt(i)<='9') || addr.charAt(i)=='.')) {
				return false;
			}
			if(addr.charAt(i)=='.')
				contPunto++;
		}
		if(contPunto!=3) {
			return false;
		}
		String[] buffer =addr.split(Pattern.quote("."));
		if(buffer.length!=4) {
			return false;
		}
		for(int i=0; i<4; i++) {
			int numero = Integer.parseInt(buffer[i]);
			if(numero>255)
				return false;
		}
		

		return true;
	}

	@Override
	public synchronized void mouseClicked(MouseEvent arg0) {
		if(view.isEmpty()) {
			view.isEmpty();
			return;
		}//if campo vuoto, manca indirizzo

		stringAddress = view.getIPAddress();
		boolean correct = controllIP(stringAddress);
		if(correct) {
			try {
				inet = InetAddress.getByName(stringAddress);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			this.notifyAll();
		}
		else
			view.incorrectInet();

	}
	
	public void incorrectIP() {
		view.incorrectInet();
	}
	
	public void dispose() {
		view.dispose();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
