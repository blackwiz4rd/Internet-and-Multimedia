package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import util.Channel;
import util.IChannel;

public class ClientApp {

	public static void main(String[] args) {
	    // Indirizzo del server
		byte[] address = { (byte) 127, (byte) 0, 0, 1 };
		int port = 50000;

		int frameRate = 25;

        // Creazione dello streaming manager
		System.out.println("Starting up...");
		StreamingManager manager = new StreamingManager(frameRate);

        // Stima del canale con iperf
		IChannel channel;
		try {
			channel = ClientApp.probe(address[0]+"."+address[1]+"."+address[2]+"."+address[3]);
			manager.setChannel(channel);
		} catch (IOException e) {
			System.out.println("Channel estimate error");
		}

		// Setup della connessione
		ClientDataConnection connection = new ClientDataConnection();
		connection.initialize(address, port, manager);
		manager.setConnection(connection);
	}

	public static IChannel probe(String serverAddress) throws IOException {
	    return new Channel(-1,-1);
	    
	    // La stima del canale va implementata da voi: eliminate la prima riga e decommentate il resto del metodo
	    // Il parsing dell'output è già dato, dovete solo chiamare iperf
	
		/* System.out.println("Starting channel estimation...");
		
		// Esecuzione di iperf (TCP) per la stima della banda (usare Runtime per eseguire comandi bash)
		// Il comando ritorna un oggetto Process, chiamatelo bandwidthProbe per far funzionare il parser
		// Usare l'opzione -yc prima di -c per avere output machine readable
		
		// Lettura del dato da iperf
		BufferedReader bandwidthReader = new BufferedReader(new InputStreamReader(bandwidthProbe.getInputStream()));
		String iperf = bandwidthReader.readLine();
		String[] iperf_vals=iperf.split(",");
		double bandwidth=Double.parseDouble(iperf_vals[8]);
		
		// Esecuzione di iperf (UDP) per la stima della packet error rate
		// Riutilizzare il processo bandwidthProbe
		
		// Lettura del dato da iperf
		bandwidthReader = new BufferedReader(new InputStreamReader(bandwidthProbe.getInputStream()));
		iperf = bandwidthReader.readLine();
		iperf = bandwidthReader.readLine();
		iperf_vals=iperf.split(",");
		double pLoss=Double.parseDouble(iperf_vals[12])/100;
		System.out.println("End of channel estimation.");

        
		return new Channel(pLoss, bandwidth/8);
		*/
	}

}
