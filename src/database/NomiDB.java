package database;

public enum NomiDB {

	CAMPO_TITOLO("titolo"),
	CAMPO_COMPRESO_QUOTA("compreso quota"),
	CAMPO_DATA_FINE("data fine"),
	CAMPO_DATA_INIZIO("data inizio"),
	CAMPO_DURATA("durata"),
	CAMPO_LUOGO("luogo"),
	CAMPO_NOTE("note"),
	CAMPO_PARTECIPANTI("partecipanti"),
	CAMPO_PARTECIPANTI_MAX("partecipanti massimi"),
	CAMPO_QUOTA_IND("quota individuale"),
	CAMPO_TERMINE_ULTIMO("termine ultimo"),
	CAMPO_ETA("eta"),
	CAMPO_SESSO("sesso"),
	CAMPO_STATO_EVENTO("stato_evento"),
	CAMPO_TOLLERANZA("tolleranza"),
	CAMPO_PROPRIETARIO("proprietario"),
	CAMPO_ISTRUTTORE("istruttore"),
	CAMPO_ATTREZZATURA("attrezzatura"),
	CAMPO_TERMINE_ULTIMO_RITIRO("termine ultimo ritiro"),
	FILE_UTENTI("data/utenti.xml"),
	FILE_PARTITA_CALCIO("data/categoriaPartitaCalcio.xml"),
	FILE_ESCURSIONE_MONTAGNA("data/escursioneMontagna.xml"),
	FILE_NOTIFICHE_PENDENTI("data/notifichePendenti.xml"),
	TAG_UTENTE("utente"),
	TAG_ELENCO("elenco"),
	TAG_HASH("hash"),
	TAG_EVENTO("evento"),
	TAG_CAMPO("campo"),
	TAG_NOME("nome"),
	TAG_ID("id"),
	TAG_LETTO("letto"),
	TAG_DESCRIZIONE("descrizione"),
	TAG_VALORE("valore"),
	TAG_OBBL("obbligatorio"),
	TAG_ANNO("anno"),
	TAG_MESE("mese"),
	TAG_GIORNO("giorno"),
	TAG_ORA("ora"),
	TAG_DATA("data"),
	TAG_CATEGORIA("categoria"),
	TAG_NOTIFICA("notifica"), 
	STATO_EVENTO_CHIUSO("chiuso"),
	STATO_EVENTO_APERTO("aperto"),
	STATO_EVENTO_CONCLUSO("concluso"),
	STATO_EVENTO_FALLITO("fallito"), 
	STATO_EVENTO("stato"), 
	STATO_EVENTO_CANCELLATO("cancellato"),
	ATT_EVENTI_CREATI("eventi_creati"), 
	ATT_ETA_MIN("eta_min"), 
	ATT_ETA_MAX("eta_max"), 
	ATT_INTERESSI("interessi"), 
	PERSONE_INTERESSATE("persone_interessate"), 
	CAT_PARTITA_CALCIO("partita calcio"),
	CAT_ESCURSIOME_MONTAGNA("escursione in montagna"),
	LISTA_ATTREZZATURE("lista_attrezzature"),
	LISTA_ISTRUTTORE("lista_istruttore");
	
	
	private String nome;
	
	private NomiDB(String _nome) {
		nome=_nome;
	}
	
	public String getNome() {
		return nome;
	}
}
