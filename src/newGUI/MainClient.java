package newGUI;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import lib.net.Channel;
import lib.util.Nomi;
import newGUI.login.ControllerLogin;
import newGUI.login.ModelLogin;

public class MainClient {

	private static Channel channel;
	private static ControllerLogin controllerLogin;
	private static int port = 4444;

	public static void main(String[] args) {
		Socket socket=null;
		try {
			socket = new Socket(InetAddress.getLocalHost(), port);


		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		channel = new Channel(socket);
		controllerLogin = new ControllerLogin(channel);
		controllerLogin.login();




	}
	




}
