
package lib.core;

import java.util.ArrayList;
import java.util.LinkedList;

public class EscursioneMontagnaCat<EscursioneMontagnaEvento> extends Categoria {

	public EscursioneMontagnaCat(String nome, String descrizione) {
		super(nome, descrizione);
	}
	
	public EscursioneMontagnaCat(String _nome, String _descrizione, ArrayList<EscursioneMontagnaEvento> lista,	LinkedList<String> _personeInteressate) {
		super(_nome, _descrizione, lista, _personeInteressate);
		// TODO Auto-generated constructor stub
	}
	

}
