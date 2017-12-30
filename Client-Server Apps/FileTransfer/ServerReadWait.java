import java.io.*;
import java.net.*;

class ServerReadWait{
	Socket clientSocket;
  	Thread th;
  	String id;
  	String sentence;
  	BufferedReader inFromUser;
  	DataOutputStream outToServer;
  	BufferedInputStream inFromServer;

  	boolean flag = false;

  	public ServerReadWait(Socket clientSocket, String id){
	    this.clientSocket = clientSocket;
	    this.id = id;
	    inFromUser = new BufferedReader(new InputStreamReader(System.in));
		try{		
			outToServer = new DataOutputStream(clientSocket.getOutputStream());
			inFromServer = new BufferedInputStream(clientSocket.getInputStream());
		}
		catch(Exception e){
		}  
	}

	public synchronized void ReadInput(){
		if(flag){  
	      /*3. Il client legge una stringa da tastiera*/
	      try{
	        //es. gia noto, invio il file
	        //String filepath = "toSend/file1.txt";
	      	wait();
	                  
	      }
	      catch(Exception e){
	      }
	    }


		System.out.println("Hi " + id + " , enter input to send");
	    //while(true){  
	      /*3. Il client legge una stringa da tastiera*/
	      try{
	        System.out.print(id + ": ");
	        sentence = inFromUser.readLine();
	        //String sentence = "test";        
	        //4. Il client invia la stringa al server tramite il socket TCP
	        outToServer.writeBytes(sentence + "\n");           
	      }
	      catch(Exception e){

	      }
	    //}

	   	flag = true;
	    notify();

	}

	public synchronized void WaitFile(){
		if(!flag){  
	      /*3. Il client legge una stringa da tastiera*/
	      try{
	        //es. gia noto, invio il file
	        //String filepath = "toSend/file1.txt";
	      	wait();
	                  
	      }
	      catch(Exception e){
	      }
	    }

		System.out.println("I am always waiting for a file from " + id);
	    //7. Il client riceve la stringa, la mostra a schermo, e chiude la connessione TCP.*/
	    //while(true){  
	        try{
	          //String sentence = inFromServer.readLine();
	          //System.out.println("\n" + sentence);
	          //System.out.print(id + ": ");
	          //Reading bytes from inFromServer
	          int length = inFromServer.read();
	          byte[] buf = new byte[length];
	          inFromServer.read(buf);
	          System.out.println(length);
	          System.out.println(buf);
	          //saving bytes in a file
	          try (FileOutputStream fos = new FileOutputStream("received/"+sentence)) {
	            fos.write(buf);
	            fos.close();
	          }
	          
	          //th.sleep(1000);
	        }
	        catch(Exception e){
	          //System.out.println(e);

	          //always running when there are no byte sent D:
	        }
	     // }

	    flag = false;
	    notify();

	}

}