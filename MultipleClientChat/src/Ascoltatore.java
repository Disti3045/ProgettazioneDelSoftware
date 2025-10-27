import java.awt.event.ActionListener;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

class ThreadAscolto implements Runnable{
	boolean connesso;
	Scanner sc;
	JTextArea output;
	
	public ThreadAscolto(boolean connesso, Scanner sc, JTextArea output) {
		this.connesso = connesso;
		this.sc = sc;
		this.output = output;
	}
	
	@Override
	public void run() {
		try {
	        while (connesso && sc.hasNextLine()) {
	            String risposta = sc.nextLine();
	            javax.swing.SwingUtilities.invokeLater(() -> { //metodo per ritardare in modo sicuro la stampa di messaggi da Thread esterni
	                output.append(risposta+"\n");
	            });
	            System.out.println(risposta);
	        }
	    } catch (Exception ex) {
	        System.out.println("Errore nel thread di ascolto: " + ex);
	    }
	}
}

public class Ascoltatore implements ActionListener{
	JTextField input;
	JTextArea output;
	
	private PrintWriter pw;
	private Scanner sc;
	private Socket socket;
	private boolean connesso = false;
	
	public Ascoltatore(JTextField input, JTextArea output) {
		this.input = input;
		this.output = output;
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		
		if(e.getActionCommand().equals("Send")) {
			if(connesso) {
				String msg = input.getText();
		        pw.println(msg);
		        input.setText("");
		        System.out.println("C: Messaggio inviato al server: " + msg);
		        if(msg.equals("CLOSE")) {
		        	try {
                    pw.close();
                    sc.close();
                    socket.close();
                    connesso = false;
                    System.out.println("C: Connessione con il server chiusa");
                    output.append("Connessione al server terminata"+ "\n");
		        	} catch (IOException ex) {
		        		System.out.println(ex);
		        	}
                }else if(msg.equals("SHUTDOWN")) {
		        	try {
	                    pw.close();
	                    sc.close();
	                    socket.close();
	                    connesso = false;
	                    System.out.println("C: Connessione con il server chiusa");
	                    System.out.println("C: Server Chiuso");
	                    output.append("Server terminato" + "\n");
			        	} catch (IOException ex) {
			        		System.out.println(ex);
			        	}
	                }
			}else {
				output.append("Non sei connesso al server!" + "\n");
			}
		}else if(e.getActionCommand().equals("Connect")) {
			if(connesso) {
				System.out.println("C: Client già connesso");
				output.append("Client già connesso al server" + "\n");
			}else {
				try {
				int porta = Integer.parseInt(input.getText());
				
				System.out.println("C: Tentativo di connessione al server");
				socket = new Socket("localhost", porta);
				System.out.println("C: Connessione eseguita al server sulla porta " + porta);
				
				sc = new Scanner(socket.getInputStream());
				pw = new PrintWriter(socket.getOutputStream(), true);
				
				connesso = true;
				
				//Ricezione messaggi asincrona
				ThreadAscolto ricezioneMessaggi = new ThreadAscolto(connesso, sc, output);
				Thread t = new Thread(ricezioneMessaggi);
				t.start();
				//
				
				output.append("Client connesso al server" + "\n");
				input.setText("");
				} catch (IOException ex) {
					System.out.println(ex);
					output.append("Porta invalida" + "\n");
				}
			}
		}else if(e.getActionCommand().equals("Disconnect")) {
			try {
				if(connesso) {
                    pw.println("CLOSE");
                    pw.close();
                    sc.close();
                    socket.close();
                    connesso = false;
                    output.append("Client disconnesso dal server" + "\n");
					System.out.println("C: Connessione terminata al server");
				}
			} catch(IOException ex) {
				System.out.println(ex);
			}
		}
	}
}