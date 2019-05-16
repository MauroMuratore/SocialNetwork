package database;

public enum NomiDB {

	FILE_LOGIN("login.xml"),
	TAG_UTENTE("utente"),
	TAG_HASH("hash");
	
	
	private String nome;
	
	private NomiDB(String _nome) {
		nome=_nome;
	}
	
	public String getNome() {
		return nome;
	}
}
