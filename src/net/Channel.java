package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import util.Log;

public class Channel implements AutoCloseable{

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	public void close() {
		try {
			output.close();
			input.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
