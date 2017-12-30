package client;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

import util.IChannel;
import util.IConnection;
import util.IFrame;
import util.IManager;
import util.Frame;

public class StreamingManager implements IManager {
	private byte[] frameData;   //per un solo frame
	private int size;
	private int toDownload;
	private int frameRate;
	private int segment;
	private double bandwidth;
	
	private ArrayList<ArrayList<IFrame>> frames;  //per tutti i frame ho i vari livelli di qt
	private IConnection connection;
	private Player player;

    /*
    * Metodo costruttore: crea il manager e legge il file MPD contenente dimensioni,
    * qualità e nome file dei vari frame
    */
	public StreamingManager(int frameRate) {
	    // frames è una lista che contiene, per ogni segmento, i frame a tutti i livelli di compressione
		frames = new ArrayList<ArrayList<IFrame>>();
		
		segment = 0;
		bandwidth = -1;
		this.frameRate = frameRate;
		
		// Creazione dello scanner che legge l'MPD
		Scanner scanner = null;
		int frame_num = 1;
		try {
			scanner = new Scanner(new File("mpd.dat"));
		} catch (FileNotFoundException e) {
			System.out
					.println("Media presentation description file not found!");
			System.exit(1);
		}
		
		// Lettura dell'MPD
		while (scanner.hasNext()) {
			ArrayList<IFrame> frame_versions = new ArrayList<IFrame>();
			String quality_str = scanner.next();
			String[] quality_array = quality_str.split(",");

			for (int i = 0; i < quality_array.length; i++) {
				int frame_size = Integer.parseInt(quality_array[i]);
				frame_versions.add(new Frame("RBSvideoQ" + (i * 10) + "/frame"
						+ frame_num + ".jpg", frame_size, i * 10));
			}

			frame_num++;
			frames.add(frame_versions);
		}
		scanner.close();
	}

    /*
    * Imposta la connessione e crea l'oggetto player
    */
	public void setConnection(IConnection connection) {
		this.connection = connection;
		
		// Crea il player
		player = new Player(segment,frameRate);
		// Inizia a scaricare il video (come?)
		player.receiveFrame(frameData);
	}

    /*
    * Riceve un byte di dati
    */
	public void receiveData(byte data) {
		// Riceve il byte e lo aggiungo al frame
		frameData[toDownload--] = data;
		// Cosa succede se tutto il frame è stato scaricato?
		if(toDownload == 0)
		  nextFrame();
	}

    /*
    * Imposta il canale
    */
	public void setChannel(IChannel channel) {
		bandwidth = channel.getBandwidth();
		//bandwidth *= (1 / frameRate - 2 * channel.getDelay()) * frameRate;
		bandwidth /= (1 - channel.getLoss());
	}

    /*
    * Metodo che decide come scaricare il prossimo frame
    */
	public void nextFrame() {
		// Passa il frame al player (attenzione: cosa succede la prima volta che viene chiamato nextFrame()?)
    player.receiveFrame(frameData);
		// Cosa succede se ho già scaricato tutti i frame?
    

		int level = 8;
		if (bandwidth > 0) {
			// Scelta della qualità (il livello default se non ho info sulla banda è 8, il massimo)
		}

		// Trova il frame scelto nella lista frames e imposto toDownload e frameData
    
    
        // Stampa le info sul frame
		//System.out.println("Segment " + segment + ": level " + level	+ ", quality " + quality);

		// Manda il nome del file che contiene il frame scelto al server 
		
		// Aggiunge un carattere di controllo e converte la stringa in byte
		//name = name + "\n";
		//bytes[] packet = name.getBytes(StandardCharsets.UTF_16);

        // Invia il pacchetto al server
	}
}
