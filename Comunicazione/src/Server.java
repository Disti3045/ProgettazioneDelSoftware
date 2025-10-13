import java.net.Socket;
import java.net.ServerSocket;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.UnknownHostException;

import java.util.Scanner;

public class Server {
	public static void main(String[] args) throws IOException, UnknownHostException{
		//Dichiarazioni
		ServerSocket ss;
		
		//Avvio del server
		ss = new ServerSocket(6767);
		System.out.println("S: Server in ascolto sulla porta 6767");
		
		while(true) {
			Socket s;
			System.out.println("S: Tentativo di connessione del Socket");
			s = ss.accept(); //Accetta connessioni da qualunque socket si connetta alla porta
			System.out.println("S: Connessione accettata da " + s.getInetAddress());
			
			Scanner sc = new Scanner(s.getInputStream());
			PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
			
			String msg="";
            while(sc.hasNextLine()) {
                msg = sc.nextLine();
                System.out.println("C: " + msg);
                pw.println("S: " + msg);

                if(msg.equals("CLOSE")) {
                    System.out.println("S: Ricevuto CLOSE, chiusura socket");
                    break;
                }
                if(msg.equals("SHUTDOWN")) {
                	System.out.println("S: Ricevuto SHUTDOWN, chiusura server");
                	s.close();
    				sc.close();
    				ss.close();
    				break;
                }
            }
            if(msg.equals("SHUTDOWN"))
            	break;
			if(s!=null && sc!=null) {
				s.close();
				sc.close();
			}
				System.out.println("S: Fine connessione del Socket");
		}
		System.out.println("S: Server Terminato");
	}
}
