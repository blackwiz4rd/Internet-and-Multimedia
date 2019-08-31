import java.io.*;
import java.net.*;

class WaitingString implements Runnable{
  //Socket clientSocket;
  Thread th;
  String id;
  BufferedReader inFromServer;

  public WaitingString(Socket clientSocket, String id){
    //this.clientSocket = clientSocket;
    this.id = id;
	try{
		inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
  	}
	catch(Exception e){
	}
  }

  public void run(){
      System.out.println("I am always waiting for a string from " + id);
      //7. Il client riceve la stringa, la mostra a schermo, e chiude la connessione TCP.*/
      while(true){  
        try{
          String sentence = inFromServer.readLine();
          System.out.println("\n" + sentence);
          System.out.print(id + ": ");

          th.sleep(1000);
        }
        catch(Exception e){
          System.out.println(e);
        }
      }
  }

  public void start(){
    th = new Thread(this, "WaitingString");
    th.start();
  }      
}
