package cervello;
//un utente � dafinito da username e pasword 
public class Utente {
	
	private String username;
	private byte[] password;
	
	public Utente(String id,byte[] pw)
	{
		username=id;
		password=pw;
	}
	public byte[] getPassword() {
		return password;
	}
	public void setPassword(byte[] password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

}
