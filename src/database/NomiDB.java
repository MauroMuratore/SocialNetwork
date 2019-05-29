package database;

public enum NomiDB {

	CAMPO_TITOLO("titolo"),
	CAMPO_COMPRESO_QUOTA("compreso quota"),
	CAMPO_DATA_FINE("data fine"),
	CAMPO_DATA_INIZIO("data inizio"),
	CAMPO_DURATA("durata"),
	CAMPO_LUOGO("luogo"),
	CAMPO_NOTE("note"),
	CAMPO_PARTECIPANTI("partecipant"),
	CAMPO_PARTECIPANTI_MAX("partecipanti massimi"),
	CAMPO_QUOTA_IND("quota individuale"),
	CAMPO_TERMINE_ULTIMO("termine ultimo"),
	CAMPO_ETA("eta"),
	CAMPO_SESSO("sesso"),
	FILE_LOGIN("data/login.xml"),
	FILE_PARTITA_CALCIO("data/categoriaPartitaCalcio.xml"),
	TAG_UTENTE("utente"),
	TAG_ELENCO("elenco"),
	TAG_HASH("hash"),
	TAG_EVENTO("evento"),
	TAG_CAMPO("campo"),
	TAG_NOME("nome"),
	TAG_ID("id"),
	TAG_DESCRIZIONE("descrizione"),
	TAG_VALORE("valore"),
	TAG_OBBL("obbligatorio"),
	TAG_ANNO("anno"),
	TAG_MESE("mese"),
	TAG_GIORNO("giorno"),
	TAG_ORA("ora"),
	TAG_CATEGORIA("categoria");
	
	
	private String nome;
	
	private NomiDB(String _nome) {
		nome=_nome;
	}
	
	public String getNome() {
		return nome;
	}
}
