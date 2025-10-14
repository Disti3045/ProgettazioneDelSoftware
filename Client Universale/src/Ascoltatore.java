import java.awt.event.ActionListener;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Ascoltatore implements ActionListener{
	JTextField input;
	JTextField output;
	
	private PrintWriter pw;
	private Scanner sc;
	private Socket socket;
	private boolean connesso = false;
	
	public Ascoltatore(JTextField input, JTextField output) {
		this.input = input;
		this.output = output;
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		
		if(e.getActionCommand().equals("Send")) {
			if(connesso) {
				//Messaggio da Utente
				String msg = input.getText();
		        pw.println(msg);
		        input.setText("");
		        System.out.println("C: Messaggio inviato al server: " + msg);
		        //Risposta da Server
		        if(sc.hasNextLine()) {
                    String risposta = sc.nextLine();
                    output.setText(risposta);
                    System.out.println(risposta);
                }
		        //Caso Chiusura
		        if(msg.equals("CLOSE")) {
		        	try {
                    pw.close();
                    sc.close();
                    socket.close();
                    connesso = false;
                    System.out.println("C: Connessione con il server chiusa");
                    output.setText("Connessione al server terminata");
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
	                    output.setText("Server terminato");
			        	} catch (IOException ex) {
			        		System.out.println(ex);
			        	}
	                }
			}else {
				output.setText("Non sei connesso al server!");
			}
		}else if(e.getActionCommand().equals("Connect")) {
			if(connesso) {
				System.out.println("C: Client già connesso");
				output.setText("Client già connesso al server");
			}else {
				try {
				int porta = Integer.parseInt(input.getText());
				
				System.out.println("C: Tentativo di connessione al server");
				socket = new Socket("localhost", porta);
				System.out.println("C: Connessione eseguita al server sulla porta " + porta);
				
				sc = new Scanner(socket.getInputStream());
				pw = new PrintWriter(socket.getOutputStream(), true);
				
				connesso = true;
				
				output.setText("Client connesso al server");
				input.setText("");
				} catch (IOException ex) {
					System.out.println(ex);
					output.setText("Porta invalida");
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
                    output.setText("Client disconnesso dal server");
					System.out.println("C: Connessione terminata al server");
				}
			} catch(IOException ex) {
				System.out.println(ex);
			}
		}
	}
}