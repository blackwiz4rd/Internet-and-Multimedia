package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import util.IConnection;
import util.IManager;

public class ServerDataConnection implements IConnection {
	
	private Socket socket;
	private OutputStream out;
	private InputStream in;
	private IManager manager;

    /*
    * Inizializza la connessione (l'indirizzo non viene usato, è un server)
    */
	public void initialize(byte[] address, int port, IManager manager){
		// Crea il socket -> 
		//1. Il server instanzia un socket TCP su una porta prefissata (es: 7777) e si mette in attesa di una richiesta di connessione da parte di un client
    try{
      ServerSocket welcome = new ServerSocket(port);
      socket = welcome.accept();
    
          // Estrae gli stream di input e output
      out = socket.getOutputStream();
      in = socket.getInputStream();
  
          // Crea un oggetto Listener e fa partire il thread
      Listener listener = new Listener();
      Thread thread = new Thread(listener);
      thread.start();
      thread.join();
      
          // Imposta il manager
      this.manager = manager;
  
  		System.out.println("Listening on port " + port);
    }
    catch(Exception e){
      
    }
  }
	
    public boolean sendData(byte[] data) {
        // Manda i dati tramite il socket e ritorna false in caso di problemi
        //4. Il server invia il file MDP al client
        try{
			    System.out.println("sending data...");
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
			  catch(Exception e){
			    
			  }
			   
	  }
			
		}
	}


}
