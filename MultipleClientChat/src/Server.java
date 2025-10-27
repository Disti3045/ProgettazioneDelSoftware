import java.net.Socket;
import java.net.ServerSocket;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JTextArea;

import java.util.List;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

class ThreadClient implements Runnable{
	Socket s;
	int i;
	JTextArea textarea;
	
	public ThreadClient(Socket s, int i, JTextArea textarea) {
		this.s = s;
		this.i = i;
		this.textarea = textarea;
	}
	
	@Override
	public void run() {
		PrintWriter pw;
		Scanner sc;
		String msg="";
		
		Thread.currentThread().setName("Thread Client " + i);
		
		try {
			sc = new Scanner(this.s.getInputStream());
			pw = new PrintWriter(this.s.getOutputStream(), true);
			
			Server.aggiungiPrintWriter(pw);
			
			while(sc.hasNextLine()) {
                msg = sc.nextLine();
                System.out.println("C: " + msg);
                Server.invioMessaggioGlobale("Client " + i + ": " + msg);
                textarea.append("Client " + i+ ":" + msg+"\n");

                if(msg.equals("CLOSE")) {
                    System.out.println("S: Ricevuto CLOSE, chiusura socket");
                    textarea.append("=== Chiusura Forzata Socket ===" + "\n");
                    break;
                }
                if(msg.equals("SHUTDOWN")) {
                	System.out.println("S: Ricevuto SHUTDOWN, chiusura server");
                	textarea.append("=== Chiusura Del Server da Socket ===" + "\n");
                	Server.rimuoviPrintWriter(pw);
                	s.close();
    				sc.close();
    				break;
                }
            }
			sc.close();
			if(msg.equals("CLOSE")) {
				Server.rimuoviPrintWriter(pw);
				s.close();
				Thread.currentThread().interrupt();
			}	
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}

public class Server {
	
	public static List<PrintWriter> membriChat = new ArrayList<>();
	
	public static synchronized void aggiungiPrintWriter(PrintWriter pw) {
		membriChat.add(pw);
    }
	
    public static synchronized void rimuoviPrintWriter(PrintWriter pw) {
    	membriChat.remove(pw);
    }
    
    public static synchronized void invioMessaggioGlobale(String msg) {
        // Invia il messaggio a tutti i client connessi
        for (PrintWriter pw : membriChat) {
            pw.println(msg);
        }
    }

	
	public static void main(String[] args) throws IOException, UnknownHostException{
		JTextArea serverArea = new JTextArea();
		String outmsg="";
		boolean connesso = false;
		ServerFrame(serverArea, outmsg, connesso);
		
		//Dichiarazioni
		ServerSocket ss;
		Socket s;
		
		int porta = 6767;
		
		//Avvio del server
		ss = new ServerSocket(porta);
		System.out.println("S: Server in ascolto sulla porta " + porta);
		
		int i = 0; //Numero Connessioni Effettuate
		
		while(true) { //Ciclo accetta connessioni
			System.out.println("S: Tentativo di connessione del Socket" + i );
			s = ss.accept(); //Accetta connessioni da qualunque socket si connetta alla porta
			System.out.println("S: Connessione" + i + "accettata");
			
			ThreadClient connessione = new ThreadClient(s, i, serverArea);
			Thread t = new Thread(connessione);
			t.start();
			
			i++;
			UpdateServerArea(serverArea, "=== Nuovo Socket Connesso ===");
			if(outmsg.equals("SHUTDOWN")) {
				break;
			}
		}
		if(outmsg.equals("SHUTDOWN")) {
			ss.close();
		}
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
