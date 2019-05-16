package interfacciaUtente;

public class UserInterface {
	
	public boolean isLogin() {
		return false;
	}
	
	//potremmo fare che se non è login allora è registrazione e "risparmiamo" un metodo
	public boolean isRegistrazione() {
		return false;
	}

	public String getID() {
		return null;
	}

	public byte[] getPW() {
		return null;
	}

	public byte[] getConfermaPW() {
		return null;
	}

	public boolean riceviStringa(String risposta) {
		return false;
	}
	

}
