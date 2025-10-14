import java.net.Socket;
import java.net.ServerSocket;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.UnknownHostException;

import java.util.Scanner;

import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Server {
	public static void main(String[] args) throws IOException, UnknownHostException{
		JTextArea serverArea = new JTextArea();
		String msg="";
		boolean connesso = false;
		ServerFrame(serverArea, msg, connesso);
		
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
			UpdateServerArea(serverArea, "=== Nuovo Socket Connesso ===");
			connesso = true;
			Scanner sc = new Scanner(s.getInputStream());
			PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
		
            while(sc.hasNextLine()) {
                msg = sc.nextLine();
                System.out.println("C: " + msg);
                pw.println("S: " + msg);
                UpdateServerArea(serverArea, msg);

                if(msg.equals("CLOSE")) {
                    System.out.println("S: Ricevuto CLOSE, chiusura socket");
                    UpdateServerArea(serverArea, "=== Chiusura Forzata Socket ===");
                    connesso = false;
                    break;
                }
                if(msg.equals("SHUTDOWN")) {
                	System.out.println("S: Ricevuto SHUTDOWN, chiusura server");
                	UpdateServerArea(serverArea, "=== Chiusura Del Server da Socket ===");
                	s.close();
    				sc.close();
    				ss.close();
    				break;
                }
            }
            if(msg.equals("SHUTDOWN")) {
            	break;
            }
			if(s!=null && sc!=null) {
				s.close();
				sc.close();
			}
				connesso = false;
				System.out.println("S: Fine connessione del Socket");
		}
		System.out.println("S: Server Terminato");
		System. exit(0);
	}
	
	public static void ServerFrame(JTextArea serverArea, String msg, boolean connesso) {
		JFrame serverFrame = new JFrame("Server");
		JScrollPane scrollPane = new JScrollPane(serverArea);
		JPanel panel = new JPanel();
		
		serverArea.setEditable(false);
		
		ActionListener al2;
		al2 = new Ascoltatore2(msg, connesso);
		
		JButton shutDown = new JButton("Shutdown");
		shutDown.addActionListener(al2);
		panel.add(shutDown);
		
		JLabel ta_lb = new JLabel("Server Chat");
		
		serverFrame.add(ta_lb, BorderLayout.NORTH);
		serverFrame.add(scrollPane, BorderLayout.CENTER);
		serverFrame.setSize(500, 500);
		serverFrame.add(panel, BorderLayout.SOUTH);
		
		serverFrame.setVisible(true);
		serverFrame.pack();
		serverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void UpdateServerArea(JTextArea serverArea, String string) {
		if(!serverArea.equals(null) && !string.equals("")) {
			serverArea.append(string+"\n");
		}
	}
}
