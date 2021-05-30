package gui.login;

public class ModelLogin {

	private String username;
	private String password;
	private String confermaPW;
	private int etaMin;
	private int etaMax;
	private String[] categoriePref;
	
	public ModelLogin() {
		username ="";
		password ="";
		confermaPW ="";
		etaMin=0;
		etaMax=0;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfermaPW() {
		return confermaPW;
	}

	public void setConfermaPW(String confermaPW) {
		this.confermaPW = confermaPW;
	}

	public int getEtaMin() {
		return etaMin;
	}

	public void setEtaMin(int etaMin) {
		this.etaMin = etaMin;
	}

	public int getEtaMax() {
		return etaMax;
	}

	public void setEtaMax(int etaMax) {
		this.etaMax = etaMax;
	}

	public String[] getCategoriePref() {
		return categoriePref;
	}

	public void setCategoriePref(String[] categoriePref) {
		this.categoriePref = categoriePref;
	}

	
	
}
