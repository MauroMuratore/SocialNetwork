import java.awt.EventQueue;

import cervello.SocialNetwork;
import interfacciaUtente.UserInterface;

public class Main {

	public static SocialNetwork SN= new SocialNetwork(); //non dovrebbe essere static	2
	static boolean rispostaUI=false;
	public static  UserInterface UI =null; //non dovrebbe essere static

	public static void main(String[] args)
	{

		try {

			UI = new UserInterface(SN);
			UI.getFrame().setVisible(true);

		} catch (Exception e) {
			System.out.println("User interface non disponibile!!");
			e.printStackTrace();
		}	

		cicloDiLogin();

		do{
			pausa(1);
			//System.out.println("sto cercando il logout");
			if (UI.isLogOut())
			{
				System.out.println("ho trovato il logout");
				cicloDiLogin();
			}
		}while(true);

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
	}
	public static void cicloDiLogin(){
		UI.setLogOut(false);
		do{		 
			String risposta;
			pausa(1);
			//System.out.println("sto ciclando");
			if(UI.isLog())
			{

				System.out.println("prendo credenziali");
				String id=UI.getUS();
				byte[] pw=UI.getPASS();
				risposta=SN.login(id,pw);
				System.out.println(risposta);
				rispostaUI=UI.riceviStringa(risposta) ;//facciamo che il metodo ricevi sringa di UI ritorna un bool: vero se risposta ï¿½ benvenuto oppure registrazione effettuata, falso se risposta ï¿½ un messaggio di errore
				UI.setFalse();//ï¿½ un metodo che mette a false isLog e isReg  (senï¿½ continua a prendere le credenziali in loop)

			}

			else if(UI.isRegistrazione())
			{
				System.out.println("prendo credenziali registrazione");
				String id = UI.getIDREG();
				byte[] pw = UI.getPWREG();  
				byte[] confermaPW = UI.getConfermaPWREG();
			    String min=UI.getMin();
			    String max=UI.getMax();
			    String[] categoriePreferite=UI.getCategoriePreferite();
				risposta=SN.registrazione(id, pw, confermaPW,min,max,categoriePreferite);
				System.out.println(risposta);
				rispostaUI=UI.riceviStringa(risposta);
				UI.setFalse();
			}
		}while(!rispostaUI);//il while cicla fino a che la risposta ui diventa vera
		rispostaUI=false;
		//relise4
		sezioneMenu();
	

	}




}
