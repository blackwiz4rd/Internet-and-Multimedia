import java.io.*;
import java.net.*;
/*127.0.0.1 -> lo*/
/*
——————————————————————————
JAVA esperienza 1b: CHAT
——————————————————————————
Si scriva un programma client-server su socket TCP che implementi una chat.
Si consiglia di strutturare il programma come segue:
1. Il server instanzia un socket TCP su una porta prefissata (es: 7777) e si mette in attesa di una richiesta di connessione da parte di un client
2. Il client apre il socket verso il server

A questo punto, sia il server che il client devono avviare due thread ciascuno: 
uno che legge una stringa da tastiera e la invia sul socket TCP, 
il secondo che attende una stringa dal socket TCP e la stampa a schermo. 


Varianti: 
a) MultiPartyChat: più client si connettono al server e ogni messaggio viene inviato a tutti gli host connessi
b) MyWhatzapp: come MultiPartyChat, ma con storing nel server dei messaggi e invio di quelli mancanti quando un nuovo client si connette
c) Provare a realizzare capitalization e/o chat su socket UDP anziche' TCP

*/
class Ex3_client {
    public static void main(String argv[]) throws Exception{
      /*2. Il client apre il socket verso il server*/
      Socket clientSocket = new Socket("127.0.0.1", 7780);
      
      ReadIn readIn = new ReadIn(clientSocket, "Client");
  //    Thread th1 = new Thread(readIn);
      WaitingString str = new WaitingString(clientSocket, "Client");
//      Thread th2 = new Thread(str);

      str.start();
      readIn.start();

      //clientSocket.close(); 
      
    }
}
