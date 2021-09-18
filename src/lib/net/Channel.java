package lib.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import lib.util.Log;
import lib.util.Nomi;

public class Channel implements AutoCloseable{

	private ObjectInputStream input;
	private ObjectOutputStream output;
	private Socket socket;

	public Channel(Socket socket) {
		this.socket=socket;
		setOutput();
		setInput();
		Log.writeRoutineLog(getClass(), "creato nuovo channel", Log.TOP_PRIORITY);

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

	public void write(Object obj){

		try {
			output.writeObject(obj);
		} catch (IOException e) {
			Log.writeErrorLog(this.getClass(), "errore IO nella scrittura in " + output.getClass().getName()) ;
			e.printStackTrace();
		}
	}

	public Object read() throws IOException {
		if(socket.isClosed())
			throw new IOException();
		
		
		try {
			Object ritorno = input.readObject();
			if(ritorno.equals(Nomi.NET_CHIUSURA_SOCKET))
				throw new IOException();
			
			



			return ritorno;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}/* catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/		return null;

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
		
		Log.writeRoutineLog(getClass(), "Chiusura channel", Log.TOP_PRIORITY);
	}

}
