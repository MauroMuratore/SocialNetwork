package cervello;

import java.util.ArrayList;

public class PartitaCalcioCat<PartitaCalcioEvento> extends Categoria {

	public PartitaCalcioCat(String _nome, String _descrizione) {
		super(_nome, _descrizione);
	}
	
	public PartitaCalcioCat(String _nome, String _descrizione, ArrayList<PartitaCalcioEvento> _lista) {
		super(_nome, _descrizione, _lista);
	}
	

}
