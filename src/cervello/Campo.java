package cervello;

public class Campo<T> {
	
	private String nome;
	private String descrizione;
	private T valore;
	private boolean obbligatorio;
	
	
	public String getNome() {
		return nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public T getValore() {
		return valore;
	}
	public boolean isObbligatorio() {
		return obbligatorio;
	}
	
	public String toString() {
		return valore.toString();
	}
	
	

}
