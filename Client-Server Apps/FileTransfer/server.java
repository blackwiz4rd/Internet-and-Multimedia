import java.io.*;
import java.net.*;
/*127.0.0.1 -> lo*/

class server {
    public static void main(String argv[]) throws Exception{
      //1. Il server instanzia un socket TCP su una porta prefissata (es: 7777) e si mette in attesa di una richiesta di connessione da parte di un client
      ServerSocket welcomeSocket = new ServerSocket(7780);
      //CREA UN NUOVO SOCKET PER OGNI CONNESSIONE -> multichat accetta più welcome socket
      Socket connectionSocket = welcomeSocket.accept();
              
      //ReadFile readFile = new ReadFile(connectionSocket, "Server");
      //WaitingString str = new WaitingString(connectionSocket, "Server");

      //str.start();
      //readFile.start();
    
      ServerWaitSend s = new ServerWaitSend(connectionSocket, "Server");
      new T1(s);
      new T2(s);
      
      //connectionSocket.close();    
      //->welcomeSocket.close();
    }
}



class T1 implements Runnable {
   ServerWaitSend m;
   
   public T1(ServerWaitSend m) {
      this.m = m;
      new Thread(this, "WaitString").start();
   }

   public void run() {
      m.WaitString();
   }
}


class T2 implements Runnable {
   ServerWaitSend m;
   
   public T2(ServerWaitSend m) {
      this.m = m;
      new Thread(this, "Send").start();
   }

   public void run() {
      m.Send();
   }
}