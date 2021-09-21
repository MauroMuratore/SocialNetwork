package gui;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import gui.body.ControllerBody;
import gui.ipconnect.ControllerIPConnect;
import gui.login.ControllerLogin;
import gui.login.ModelLogin;
import lib.net.Channel;
import lib.util.Nomi;

public class MainClient {

	private static Channel channel;
	private static ControllerIPConnect controllerIP;
	private static ControllerLogin controllerLogin;
	private static ControllerBody controllerBody;
	private static int port = 4444;

	public static void main(String[] args) {
		Socket socket=null;
		boolean condition=false;
		do {
			controllerIP = new ControllerIPConnect();
			InetAddress inet = controllerIP.getIPAddress();

			try {
				socket = new Socket(inet, port);
				condition=false;
			} catch (ConnectException e) {
				e.printStackTrace();
				controllerIP.incorrectIP();
				condition=true;
			}
			catch (UnknownHostException e) {
				e.printStackTrace();
				controllerIP.incorrectIP();
				condition=true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}while(condition);
		channel = new Channel(socket);
		controllerLogin = new ControllerLogin(channel);
		controllerLogin.login();

		controllerBody = new ControllerBody(channel);
		controllerBody.cicloVita();

	}

}
