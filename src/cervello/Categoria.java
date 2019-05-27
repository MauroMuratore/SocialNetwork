package cervello;

import java.util.ArrayList;
import java.util.List;

/*
 * IDEA POTREMMO METTERLO GENERICS, COSÌ GLI EVENTI SARANNO SOLO DELLA CATEGORIA GIUSTA 
 */

public abstract class Categoria<E extends Evento> {
	
	private String nome;
	private String descrizione;
	protected ArrayList<E> bacheca;
	
	
	//UML DA MODIFICARE IL FATTO CHE SIA UN GENERICS
	public Categoria(String _nome, String _descrizione) {
		nome= _nome;
		descrizione=_descrizione;
		bacheca = new ArrayList<E>();
	}
	

	public Categoria(String _nome, String _descrizione, ArrayList<E> lista) {
		nome=_nome;
		descrizione=_descrizione;
		bacheca= new ArrayList<E>(lista);
	}
	
	public void aggiungiEvento(E evento) {
		bacheca.add(evento);
	}
	
	public ArrayList<E> getBacheca(){
		return bacheca;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getDescrizione() {
		return descrizione;
	}

}
