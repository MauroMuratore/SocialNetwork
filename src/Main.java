import java.awt.EventQueue;

import cervello.SocialNetwork;
import interfacciaUtente.UserInterface;

public class Main {
	public static void main(String[] args)
	{
		UserInterface UI= new UserInterface();
		SocialNetwork SN= new SocialNetwork();
		boolean rispostaUI=false;;

//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					UserInterface window = new UserInterface();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
		do{		 
			String risposta;

			if(UI.isLog())
			{
				String id=UI.getUS();
				byte[] pw=UI.getPASS();
				risposta=SN.login(id,pw);
				rispostaUI=UI.riceviStringa(risposta) ;//facciamo che il metodo ricevi sringa di UI ritorna un bool: vero se risposta è benvenuto oppure registrazione effettuata, falso se risposta è un messaggio di errore
				//sezioneCategorie();
			}

			else if(UI.isRegistrazione())
			{
				String id = UI.getID();
				byte[] pw = UI.getPW(); //sono due getPW diversi rispetto a quello del login?
				byte[] confermaPW = UI.getConfermaPW();
				risposta=SN.registrazione(id, pw, confermaPW);
				rispostaUI=UI.riceviStringa(risposta);
				//sezioneCategorie();//facciamo che dopo la registrazione il sistema presenta direttamente le categorie, senza passare per il login(login implicito)
			}


		}while(!rispostaUI);//il while cicla fino a che la risposta ui diventa vera

		//il sezione categorie dovremmo metterlo dopo



	}
	/*
	public static void sezioneCategorie()
	{
		UI.riceviCategorie(SN.titoliCategorie());//facciamo che UI abbia un metodo riceviCategorie che riceve un array list di stringhe e si occuperï¿½ della sua visualizzazone sull interfaccia
		Categoria categoriaScelta = UI.selezioneCategoria(); //facciao che UI abbia un metoto seleziona categoria che ritorna la categoria scelta dall utente
		IU.mostraEventi(SN.mostraBacheca(categoriaScelta));//facciamo che UI abbia un metodo mostra eventi ce riceve una lista di eventi e che li mostri sulla view
	}
	 */


}