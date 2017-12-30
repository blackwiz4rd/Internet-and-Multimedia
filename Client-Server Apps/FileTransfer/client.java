import java.io.*;
import java.net.*;

class client {
    public static void main(String argv[]) throws Exception{
      /*2. Il client apre il socket verso il server*/
      Socket clientSocket = new Socket("127.0.0.1", 7780);
      
      ServerReadWait s = new ServerReadWait(clientSocket, "Server");
      new TC1(s);
      new TC2(s);
      //clientSocket.close(); 
    }
}



class TC1 implements Runnable {
   ServerReadWait m;
   
   public TC1(ServerReadWait m) {
      this.m = m;
      new Thread(this, "ReadInput").start();
   }

   public void run() {
      m.ReadInput();
   }
}


class TC2 implements Runnable {
   ServerReadWait m;
   
   public TC2(ServerReadWait m) {
      this.m = m;
      new Thread(this, "WaitFile").start();
   }

   public void run() {
      m.WaitFile();
   }
}