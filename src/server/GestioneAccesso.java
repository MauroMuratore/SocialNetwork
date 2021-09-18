package server;

import java.util.Hashtable;

import lib.core.Categoria;
import lib.core.Utente;
import lib.util.Log;
import lib.util.Nomi;
import server.database.ConsultaDB;

public class GestioneAccesso {

	private ConsultaDB consultaDB;
	private Hashtable<String, Categoria> categorie;

	public GestioneAccesso() {
		consultaDB = ConsultaDB.getInstance();
	}

	public String login(String id, String hash) {
		String esito;
		if (consultaDB.controllaID(id)) {
			if (consultaDB.controllaPW(hash, id)) {

				esito= Nomi.SN_BENVENUTO.getNome();
			} else
				esito= Nomi.SN_PW_SBAGLIATA.getNome();
		} else
			esito= Nomi.SN_ID_INESISTENTE.getNome();

		Log.writeRoutineLog(getClass(), "esito login " + esito, Log.HIGH_PRIORITY);
		return esito;
	}

	public String registrazione(String username, String hash, String conferma,int minEta,int maxEta,String[] categoriePref, Hashtable<String, Categoria> categorie) {
		String esito;
		if (!consultaDB.controllaID(username))// controllo se ce gia id nel database
		{
			if (username.length() < 7) {
				esito= Nomi.SN_ID_CORTO.getNome();
				Log.writeRoutineLog(getClass(), "esito registrazione " + esito, Log.HIGH_PRIORITY);
				return esito;
			}
			if (hash.length() < 7) {
				esito= Nomi.SN_PW_CORTA.getNome();
				Log.writeRoutineLog(getClass(), "esito registrazione " + esito, Log.HIGH_PRIORITY);
				return esito;
			}
			boolean uguali = true;
			if (hash.length() != conferma.length()) {
				esito= Nomi.SN_PW_DIVERSE.getNome();
				Log.writeRoutineLog(getClass(), "esito registrazione " + esito, Log.HIGH_PRIORITY);
				return esito;
			}
			else if(!hash.equals(conferma)) {
				esito= Nomi.SN_PW_DIVERSE.getNome();
				Log.writeRoutineLog(getClass(), "esito registrazione " + esito, Log.HIGH_PRIORITY);
				return esito;
			}
			if(minEta<0 || maxEta<0) {
				esito= Nomi.SN_NUMERO_NEGATIVO.getNome();
				Log.writeRoutineLog(getClass(), "esito registrazione " + esito, Log.HIGH_PRIORITY);
				return esito;
			}
			if(minEta>maxEta) {
				esito= Nomi.SN_ETAMIN_MAGG_ETAMAX.getNome();
				Log.writeRoutineLog(getClass(), "esito registrazione " + esito, Log.HIGH_PRIORITY);
				return esito;
			}
			Utente nuovoUtente = new Utente (username, hash, minEta, maxEta, categoriePref);
			consultaDB.salvaUtente(nuovoUtente);
			for(String cat: categoriePref) {
				categorie.get(cat).addPersonaInteressata(username);
			}
			consultaDB.salvaCategorie(categorie);

			esito= Nomi.SN_BENVENUTO.getNome();
			Log.writeRoutineLog(getClass(), "esito registrazione " + esito, Log.HIGH_PRIORITY);
			return esito;


		} else {
			esito= Nomi.SN_ID_IN_USO.getNome();

			Log.writeRoutineLog(getClass(), "esito registrazione " + esito, Log.HIGH_PRIORITY);
			return esito;
		}
	}

}
