
package lib.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class Categoria<E extends Evento> implements Serializable{

	private String nome;
	private String descrizione;
	protected ArrayList<E> bacheca;
	protected LinkedList<String> personeInteressate;

	//UML DA MODIFICARE IL FATTO CHE SIA UN GENERICS
	public Categoria(String _nome, String _descrizione) {
		nome= _nome;
		descrizione=_descrizione;
		bacheca = new ArrayList<E>(); 
		personeInteressate = new LinkedList<String>();
	}


	public Categoria(String _nome, String _descrizione, ArrayList<E> lista, LinkedList<String> _personeInteressate) {
		nome=_nome;
		descrizione=_descrizione;
		bacheca= new ArrayList<E>(lista);

		personeInteressate = new LinkedList<String>(_personeInteressate);
	}

	public void addPersonaInteressata(String _personaInteressata) {
		if(!personeInteressate.contains(_personaInteressata))
			personeInteressate.add(_personaInteressata);
	}

	public void removePersonaInteressata(String _personaInteressata) {
		if(personeInteressate.contains(_personaInteressata)) {
			personeInteressate.remove(_personaInteressata);
		}
	}

	public LinkedList<String> getPersoneInteressate() {
		return personeInteressate;
	}

	public void aggiungiEvento(E evento) {
		for(E e : bacheca) {
			if(e.getIdEvento()==evento.getIdEvento())
				return;
		}
		bacheca.add(evento);
	}

	public ArrayList<E> getBacheca(){
		return bacheca;
	}

	public String getNome() {
		return nome;
	}

	public String getDescrizione() {
		if(descrizione==null) {
			return " ";
		}
		return descrizione;
	}



}
