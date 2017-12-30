package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import util.IConnection;
import util.IManager;

public class ServerManager implements IManager {
	private byte[] nextChar;
	private String filename;
	private boolean half;
	private IConnection connection;

    /*
    * Costruttore
    */
	public ServerManager() {
	    // Attenzione: il charset UTF-16 ha caratteri da 2 byte
		nextChar = new byte[2];
		filename = "";
		half = false;
	}

    /*
    * Imposta la connessione
    */
	public void setConnection(IConnection connection) {
		this.connection = connection;
	}

    /*
    * Riceve un byte di dati
    */
	public void receiveData(byte data) {
	    // Controlla se il byte è il primo o il secondo del carattere
	    // Usare la variabile booleana half per ricordarlo
	    //false->primo char, true->secondo char
	    if(half==false){
	      nextChar[0] = data;
	      half = true;
	      
	    }
	    else{
	      nextChar[1] = data;
	      half = false;
	      // Se il carattere è finito, usare il costruttore new String(nextChar, StandardCharsets.UTF_16) 
	      // per convertirlo
        //String s = new String(nextChar, StandardCharsets.UTF_16);
        filename = new String(nextChar, StandardCharsets.UTF_16);
        // Controllare se il carattere è quello di controllo "\n" (cosa significa?)
        if(filename.equals("\n"))
          System.out.println("char di controllo -> fine");
	    }
	}

	public void nextFrame(String filename) {
		// Lettura del file (come array di byte)
		File f = new File(filename);
		byte[] buf = new byte[(int)f.length()];
    try{
      FileInputStream fis = new FileInputStream(f);
      fis.read(buf);
      System.out.println("file read");
		  // Invio del file tramite la connessione al client
		  connection.sendData(buf);
    }
    catch(Exception e){
      
    }
	}
}
