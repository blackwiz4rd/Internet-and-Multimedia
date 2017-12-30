package server;

import java.io.IOException;

public class ServerApp {

	public static void main(String[] args){
		System.out.println("Starting up...");
		ServerManager manager = new ServerManager();
		
		// Crea due server iperf (usa Runtime per chiamare comandi bash)
		
		// Imposta la connessione (inserire l'IP del client al posto di 127.0.0.1)
		byte[] address = {(byte)127,0,0,1};
		int port = 50000;
		ServerDataConnection connection = new ServerDataConnection();
		
		// Inizializza la connessione
		connection.initialize(address, port, manager);
		System.out.println("Connection correctly initialized");
				
		manager.setConnection(connection);
		
	}

}
