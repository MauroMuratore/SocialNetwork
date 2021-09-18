package server;

import java.util.Hashtable;

import lib.core.Categoria;
import lib.core.Notifica;
import lib.core.Utente;
import lib.util.Log;
import lib.util.Nomi;
import server.database.ConsultaDB;

public class GestioneUtente {
	
	private ConsultaDB consultaDB;
	private Utente utente;
	
	public GestioneUtente() {
		consultaDB = ConsultaDB.getInstance();
	}
	
	/**
	 * cancella la notifica dell'utente
	 * @param notifica
	 * @return NOTIFICA_CANCELLATA
	 */
	public Utente cancellaNotifica(int index, Utente _utente) {
		Notifica notifica =_utente.cancellaNotifica(index);
		Log.writeRoutineLog(this.getClass(), "cancello notifica ", Log.MEDIUM_PRIORITY);
		consultaDB.cancellaNotifica(notifica, _utente);

		return _utente;
	}
	
	public String modificaUtente(int etaMin, int etaMax, boolean catPartita, boolean catEscursione, Hashtable<String, Categoria> categorie, Utente _utente) {
		if(etaMin>etaMax)
			return Nomi.SN_ETAMIN_MAGG_ETAMAX.getNome();
		_utente.setEtaMin(etaMin);
		_utente.setEtaMax(etaMax);
		if(catPartita) {
			if(!categorie.get(Nomi.CAT_PARTITA_CALCIO.getNome()).getPersoneInteressate().contains(_utente.getUsername())) {
				categorie.get(Nomi.CAT_PARTITA_CALCIO.getNome()).getPersoneInteressate().add(_utente.getUsername());
				_utente.aggiungiInteresse(Nomi.CAT_PARTITA_CALCIO.getNome());
			}
		}else {
			categorie.get(Nomi.CAT_PARTITA_CALCIO.getNome()).getPersoneInteressate().remove(_utente.getUsername());
			_utente.removeInteresse(Nomi.CAT_PARTITA_CALCIO.getNome());
		}
		if(catEscursione) {
			if(!categorie.get(Nomi.CAT_ESCURSIOME_MONTAGNA.getNome()).getPersoneInteressate().contains(_utente.getUsername())) {
				categorie.get(Nomi.CAT_ESCURSIOME_MONTAGNA.getNome()).getPersoneInteressate().add(_utente.getUsername());
				_utente.aggiungiInteresse(Nomi.CAT_ESCURSIOME_MONTAGNA.getNome());
			}
		}else {
			categorie.get(Nomi.CAT_ESCURSIOME_MONTAGNA.getNome()).getPersoneInteressate().remove(_utente.getUsername());
			_utente.removeInteresse(Nomi.CAT_ESCURSIOME_MONTAGNA.getNome());
		}

		consultaDB.salvaCategorie(categorie);
		consultaDB.salvaUtente(_utente);
		utente=_utente;
		Log.writeRoutineLog(this.getClass(), "modificato utente " + utente.getUsername(), Log.HIGH_PRIORITY);

		return Utente.MODIFICA_RIUSCITA;
	}
	
	public void eliminaUtente(Hashtable<String, Categoria> categorie, String username) {
		for(String key: categorie.keySet()) {
			categorie.get(key).removePersonaInteressata(username);
		}
		consultaDB.eliminaUtente(username);
		consultaDB.salvaCategorie(categorie);
	}
	
	public Utente getUtente() {
		return utente;
	}


}
