package database;

public enum NomiDB {

	FILE_LOGIN("data/login.xml"),
	FILE_EVENTI("data/eventi.xml"),
	TAG_UTENTE("utente"),
	TAG_ELENCO("elenco"),
	TAG_HASH("hash"),
	TAG_EVENTO("evento"),
	TAG_CAMPO("campo"),
	TAG_NOME("nome"),
	TAG_DESCRIZIONE("descrizione"),
	TAG_VALORE("valore"),
	TAG_OBBL("obbligatorio");
	
	
	private String nome;
	
	private NomiDB(String _nome) {
		nome=_nome;
	}
	
	public String getNome() {
		return nome;
	}
}
