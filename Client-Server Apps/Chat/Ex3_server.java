import java.io.*;
import java.net.*;
/*127.0.0.1 -> lo*/

class Ex3_server {
    public static void main(String argv[]) throws Exception{
      //1. Il server instanzia un socket TCP su una porta prefissata (es: 7777) e si mette in attesa di una richiesta di connessione da parte di un client
      ServerSocket welcomeSocket = new ServerSocket(7780);
      //CREA UN NUOVO SOCKET PER OGNI CONNESSIONE -> multichat accetta più welcome socket
      Socket connectionSocket = welcomeSocket.accept();
              
      ReadIn readIn = new ReadIn(connectionSocket, "Server");
      WaitingString str = new WaitingString(connectionSocket, "Server");

      str.start();
      readIn.start();
    
      //connectionSocket.close();    
      //welcomeSocket.close();
    }
}
