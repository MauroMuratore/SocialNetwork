package cervello;

import java.util.ArrayList;
import java.util.List;

/*
 * IDEA POTREMMO METTERLO GENERICS, COSÌ GLI EVENTI SARANNO SOLO DELLA CATEGORIA GIUSTA 
 */

public abstract class Categoria {
	
	private String nome;
	private String descrizione;
	private ArrayList<Evento> bacheca;
	
	public Categoria(String _nome, String _descrizione) {
		nome= _nome;
		descrizione=_descrizione;
		bacheca = new ArrayList();
	}
	
	public void aggiungiEvento(Evento evento) {
		bacheca.add(evento);
	}
	
	public ArrayList<Evento> getBacheca(){
		return bacheca;
	}

}
