
public class Main() {
	public static void main(String[] args)
	{
		UserInterface UI= new UserInterface();
		SocialNetwork SN= new SocialNetwork();
		String risposta;
		
		do{		 
			Bool rispostaUI =false;
		
			 if(UI.isLogin())
			 {
				 String id=UI.getID();
				 byte[] pw=UI.getPW();
			 	 risposta=SN.login(id,pw);
			 	 rispostaUI=UI.riceviStringa(risposta) ;//facciamo che il metodo ricevi sringa di UI ritorna un bool: vero se risposta è benvenuto + id oppure registrazione effettuata, falso se risposta è un messaggio di errore
			 	 sezioneCategorie();
			  }
			  
			 else if(UI.isRegistrazione())
			 {
				 String id = UI.getID();
				 byte[] pw = UI.getPW();
				 byte[] confermaPW = UI.getConfermaPW;
			 	 rispostaUI=SN.registrazione(id, pw, confermaPW);
			 	 UI.riceviStringa(risposta);
			   	 sezioneCategorie();//facciamo che dopo la registrazione il sistema presenta direttamente le categorie, senza passare per il login(login implicito)
			 }
			 
			 
	 		}while(!rispostaUI);//il while cicla fino a che la risposta ui diventa vera
		
		
		public static void sezioneCategorie()
		{
			UI.riceviCategorie(SN.titoliCategorie());//facciamo che UI abbia un metodo riceviCategorie che riceve un array list di stringhe e si occuperà della sua visualizzazone sull interfaccia
			Categoria categoriaScelta = UI.selezioneCategoria(); //facciao che UI abbia un metoto seleziona categoria che ritorna la categoria scelta dall utente
			IU.mostraEventi(SN.mostraBacheca(categoriaScelta));//facciamo che UI abbia un metodo mostra eventi ce riceve una lista di eventi e che li mostri sulla view
		}
		
		
		
		
	}
	
}
