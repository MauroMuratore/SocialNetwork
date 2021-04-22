package newGUI;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import lib.core.Categoria;
import lib.net.Channel;
import newGUI.body.*;
import newGUI.body.bacheca.ViewBC;

public class MainTestClient {

	private static Channel channel;

	public static void main(String[] args) {

		Socket socket=null;
		try {
			socket = new Socket(InetAddress.getLocalHost(), 4444);


		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		channel = new Channel(socket);
		
		ControllerBody cb = new ControllerBody(channel);
		cb.cicloVita();
		

	}

}
