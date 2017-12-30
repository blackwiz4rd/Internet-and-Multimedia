package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;

import util.IConnection;
import util.IManager;

public class ClientDataConnection implements IConnection {

	private Socket socket;
	private OutputStream out;
	private InputStream in;
	private IManager manager;

    /*
    * Inizializza la connessione
    */
	public void initialize(byte[] address, int port, IManager manager) {
		this.manager = manager;
		
		
    try{
        // Crea il socket
        socket = new Socket(new String(address),port);
        // Estrae gli stream di input e output
        in = socket.getInputStream();
        out = socket.getOutputStream();
        // Crea un oggetto Listener e fa partire il thread
        Listener listener = new Listener();
        Thread thread = new Thread(listener);
        thread.start();
    }
    catch(Exception e){
          
    }
		System.out.println("Listening on port " + port);
	}

	public boolean sendData(byte[] data) {
        // Manda i dati tramite il socket e ritorna false in caso di problemi
        try{
          out.write(data);
        }
        catch(Exception e){
          return false;  
        }
        
        return true;
	}

	private class Listener extends Thread {
		public synchronized void run() {
			// Ciclo finché il socket è aperto e in ascolto
			while(socket.isConnected()){
			  try{
			    byte data;
			    data = (byte)in.read();
			    // Riceve un byte e lo invia al manager (vedi interfaccia IManager)
			    manager.receiveData(data);
			  }
			  catch(Exception e){}
			}
			
		}
	}

}
