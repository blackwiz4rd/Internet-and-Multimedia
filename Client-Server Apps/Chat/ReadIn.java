import java.io.*;
import java.net.*;

class ReadIn implements Runnable{
  Socket clientSocket;
  Thread th;
  String id;
  BufferedReader inFromUser;
  DataOutputStream outToServer;

  public ReadIn(Socket clientSocket, String id){
    this.clientSocket = clientSocket;
    this.id = id;
    inFromUser = new BufferedReader(new InputStreamReader(System.in));
	try{		
		outToServer = new DataOutputStream(clientSocket.getOutputStream()); 
	}
	catch(Exception e){
	}  
}

  public void run(){
    System.out.println("Hi " + id + " , enter input to send");
    while(true){  
      /*3. Il client legge una stringa da tastiera*/
      try{
        System.out.print(id + ": ");
        String sentence = inFromUser.readLine();
        //String sentence = "test";        
        //4. Il client invia la stringa al server tramite il socket TCP
        outToServer.writeBytes(id + ": " + sentence + "\n");           
      }
      catch(Exception e){

      }
    }
  }    

  public void start(){
    th = new Thread(this, "ReadIn");
    th.start();
  }      
}
