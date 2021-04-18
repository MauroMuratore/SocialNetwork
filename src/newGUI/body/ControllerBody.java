package newGUI.body;

import java.util.List;

import lib.core.Categoria;
import lib.net.Channel;
import newGUI.body.bacheca.ControllerBacheca;

public class ControllerBody {

	
	private ControllerBacheca controllerBC;
	private ViewBody viewBody;
	private Channel channel;
	
	public ControllerBody(List<Categoria> cat, Channel _channel) {
		channel =_channel;
		controllerBC = new ControllerBacheca(cat);
		viewBody = new ViewBody(controllerBC.getViewBC());
	}
	
	
	
	
}
