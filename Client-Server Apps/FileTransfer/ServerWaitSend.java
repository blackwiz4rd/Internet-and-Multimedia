import java.io.*;
import java.net.*;

public class ServerWaitSend {
	Socket clientSocket;
  	String id;
  	//BufferedInputStream inFromUser;
  	DataOutputStream outToServer;
  	BufferedReader inFromServer;
  	String filepath;	//used by both methods

  	boolean flag = false;	//flags which thread is running
  							//false -> waitString

	public ServerWaitSend(Socket clientSocket, String id){
		this.id = id;
		this.clientSocket = clientSocket;
	    
		//WaitingString
		try{
			inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
  			outToServer = new DataOutputStream(clientSocket.getOutputStream()); 
  		}
		catch(Exception e){
		}

		//inFromUser = new BufferedInputStream(System.in);
	}

	public synchronized void Send(){
		System.out.println("Hi " + id + " , I am sending the file to client");
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

	    try{
			//legge da tastiera e non dall'output dell'altro thread
	        System.out.println(filepath+" on send");
			    // Si crea un oggetto f che punta al file “filepath”
		      File f=new File(filepath);
		      // Si crea un array di byte di lunghezza pari a quella del file
		      byte[] buf = new byte[(int)f.length()+1];
	        // Si crea un oggetto “fis” di tipo FileInputStream che è associato al file 
		      FileInputStream fis = new FileInputStream(f);
		      // Si carica il buffer buf con i primi “buf.length” byte del file, cioè tutto il file 
		      fis.read(buf,1,(int)f.length()); 
		      // A questo punto, “buf” è un array di byte che contiene i byte del file
	          buf[0] = (byte)f.length();
	          
		      //Il server invia i bytes al client tramite il socket TCP
		      System.out.println((int)f.length());
		      System.out.println(buf[0]);
		      System.out.println(buf.length);
		      
			    //outToServer.writeBytes(""+(int)f.length());
			  outToServer.write(buf);	//sends byte by byte to the server 

	    }
	    catch(Exception e){

	    }


	      flag = false;
	      notify();
	}

	public synchronized void WaitString(){
		System.out.println("I am always waiting for a string from " + id);
	    if(flag){
	        try{
	      	  wait();
	        }
	        catch(Exception e){
	        }
	    }
	    
	    //while(!flag){
	    	try{
	    		filepath = inFromServer.readLine();
	    		System.out.println(filepath);
	    		filepath = "toSend/"+filepath;
		    }
		    catch(Exception e){
		    }
	    //}
	    
	    flag = true;
	    notify();

	}
}