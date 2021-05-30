package server;

import java.util.Hashtable;

import lib.core.Categoria;
import lib.core.Utente;
import lib.util.Nomi;
import server.database.ConsultaDB;

public class Logger {
	
	private ConsultaDB consultaDB;
	private Hashtable<String, Categoria> categorie;
	
	public Logger() {
		consultaDB = ConsultaDB.getInstance();
	}
	
	public String login(String id, String hash) {
		if (consultaDB.controllaID(id)) {
			if (consultaDB.controllaPW(hash, id)) {
				
				return Nomi.SN_BENVENUTO.getNome();
			} else
				return Nomi.SN_PW_SBAGLIATA.getNome();
		} else
			return Nomi.SN_ID_INESISTENTE.getNome();
	}
	
	public String registrazione(String username, String hash, String conferma,int minEta,int maxEta,String[] categoriePref, Hashtable<String, Categoria> categorie) {
		if (!consultaDB.controllaID(username))// controllo se ce gia id nel database
		{
			if (username.length() < 7)
				return Nomi.SN_ID_CORTO.getNome();
			if (hash.length() < 7) {
				return Nomi.SN_PW_CORTA.getNome();
			}
			boolean uguali = true;
			if (hash.length() != conferma.length())
				return Nomi.SN_PW_DIVERSE.getNome();
			else if(!hash.equals(conferma))
				return Nomi.SN_PW_DIVERSE.getNome();

			if(minEta<0 || maxEta<0) {
				return Nomi.SN_NUMERO_NEGATIVO.getNome();
			}
			if(minEta>maxEta)
				return Nomi.SN_ETAMIN_MAGG_ETAMAX.getNome();

			Utente nuovoUtente = new Utente (username, hash, minEta, maxEta, categoriePref);
			consultaDB.salvaUtente(nuovoUtente);
			for(String cat: categoriePref) {
				categorie.get(cat).addPersonaInteressata(username);
			}
			consultaDB.salvaCategorie(categorie);
			
			return Nomi.SN_BENVENUTO.getNome();


		} else
			return Nomi.SN_ID_IN_USO.getNome();
	}

}
