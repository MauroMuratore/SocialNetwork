package newGUI.body;

import java.io.IOException;
import java.util.List;

import lib.core.Categoria;
import lib.core.Evento;
import lib.net.Channel;
import lib.util.Nomi;
import newGUI.body.bacheca.ControllerBacheca;

public class ControllerBody {


	private ControllerBacheca controllerBC;
	private ViewBody viewBody;
	private Channel channel;

	public ControllerBody(Channel _channel) {
		channel =_channel;
		controllerBC = new ControllerBacheca(leggiCat());
		viewBody = new ViewBody(controllerBC.getViewBC());
	}

	public void cicloVita() {
		do {
			String azione = controllerBC.getAzione();
			
			
			channel.write(azione);
			channel.write(controllerBC.getEvento());
			String esito=null;
			try {
				esito = (String) channel.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			controllerBC.mostraEsito(esito);
			controllerBC.updateCat(leggiCat());
			


		}while(true);
	}
	
	public List<Categoria> leggiCat(){
		List<Categoria> categorie=null;
		try {
			categorie = (List<Categoria>) channel.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return categorie;
	}




}
