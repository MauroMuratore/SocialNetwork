package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import util.Log;

public class Channel {
	
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private Socket socket;
	
	public Channel(Socket socket) {
		this.socket=socket;
		setOutput();
		setInput();
		
	}
	
	private void setInput() {
		try {
			input = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			Log.writeErrorLog(this.getClass(), "errore channel input");
			e.printStackTrace();
		}
	}
	
	private void setOutput() {
		try {
			output = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			Log.writeErrorLog(this.getClass(), "errore channel output");
			e.printStackTrace();
		}
	}
	
	public void write(Object obj) {
		try {
			output.writeObject(obj);
		} catch (IOException e) {
			Log.writeErrorLog(this.getClass(), "errore IO nella scrittura in " + output.getClass().getName()) ;
			e.printStackTrace();
		}
	}
	
	public Object read() {
		try {
			return input.readObject();
		} catch (ClassNotFoundException e) {
			Log.writeErrorLog(this.getClass(), "classe non trovata nella lettura in " +input.getClass().getName());
			e.printStackTrace();
		} catch(IOException e) {
			Log.writeErrorLog(this.getClass(), "errore IO nella lettura in " +input.getClass().getName());
			e.printStackTrace();
		}
		return null;
	}

}
