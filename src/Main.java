import java.awt.EventQueue;

import cervello.SocialNetwork;
import interfacciaUtente.UserInterface;

public class Main {
	
	public static SocialNetwork SN= new SocialNetwork();
	static boolean rispostaUI=false;
	public static  UserInterface UI =null;

	public static void main(String[] args)
	{
		
		try {
			UI = new UserInterface(SN);
			UI.getFrame().setVisible(true);
		} catch (Exception e) {
			System.out.println("User interface non disponibile!!");
			e.printStackTrace();
		}	
		do{		 
			String risposta;
			pausa(1);
			
			if(UI.isLog())
			{
				System.out.println("prendo credenziali");
				String id=UI.getUS();
				byte[] pw=UI.getPASS();
				risposta=SN.login(id,pw);
				System.out.println(risposta);
				rispostaUI=UI.riceviStringa(risposta) ;//facciamo che il metodo ricevi sringa di UI ritorna un bool: vero se risposta � benvenuto oppure registrazione effettuata, falso se risposta � un messaggio di errore
				UI.setFalse();//� un metodo che mette a false isLog e isReg  (sen� continua a prendere le credenziali in loop)
				
			}
			
			else if(UI.isRegistrazione())
			{
				System.out.println("prendo credenziali registrazione");
				String id = UI.getIDREG();
				byte[] pw = UI.getPWREG(); //sono due getPW diversi rispetto a quello del login?
				byte[] confermaPW = UI.getConfermaPWREG();
				risposta=SN.registrazione(id, pw, confermaPW);
				System.out.println(risposta);
				rispostaUI=UI.riceviStringa(risposta);
				UI.setFalse();
				//sezioneCategorie();//facciamo che dopo la registrazione il sistema presenta direttamente le categorie, senza passare per il login(login implicito)
			}
		}while(!rispostaUI);//il while cicla fino a che la risposta ui diventa vera
		sezioneMenu();
		//il sezione categorie dovremmo metterlo dopo
	}
	static void pausa(int secondi)
	{
		try 
		{
			Thread.sleep(1000*secondi);
			
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public static void sezioneMenu()
	{
		UI.sezioneMenu();
//		UI.riceviCategorie(SN.titoliCategorie());//facciamo che UI abbia un metodo riceviCategorie che riceve un array list di stringhe e si occuper� della sua visualizzazone sull interfaccia
//		Categoria categoriaScelta = UI.selezioneCategoria(); //facciao che UI abbia un metoto seleziona categoria che ritorna la categoria scelta dall utente
//		IU.mostraEventi(SN.mostraBacheca(categoriaScelta));//facciamo che UI abbia un metodo mostra eventi ce riceve una lista di eventi e che li mostri sulla view
	}

	


}