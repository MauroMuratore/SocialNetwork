package cervello;

import java.util.LinkedList;

//un utente � dafinito da username e pasword 
public class Utente {
	
	private String username;
	private byte[] password;
	private LinkedList<Notifica> notifiche;
	
	public Utente(String id,byte[] pw)
	{
		username=id;
		password=pw;
		notifiche = new LinkedList<Notifica>();
	}
	
	public Utente(String id, byte[] pw, LinkedList<Notifica> _notifiche) {
		username=id;
		password=pw;
		notifiche = new LinkedList<Notifica>(_notifiche);
	}
	
	public byte[] getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}
	
	public LinkedList<Notifica> getNotifiche(){
		return notifiche;
	}
	
	public boolean riceviNotifica(Notifica not) {
		return notifiche.add(not);
	}

	public void cancellaNotifica(Notifica notifica) {
		if(notifiche.remove(notifica))
			System.out.println("rimossa notifica");;
		
	}
	
	
	
	

}
