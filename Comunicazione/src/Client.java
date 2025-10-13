import java.io.IOException;
import java.net.UnknownHostException;

//---------------------------

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;


public class Client {
	public static void main(String[] args) throws IOException, UnknownHostException{
		ClientUI();
		/*//Dichiarazione
		Socket s;
		int portaServer = 6767;
		String nomeServer = "";
		
		//Richiesta di connessione al server
		System.out.println("C: Tentativo di connessione al server");
		s = new Socket(nomeServer, portaServer);
		System.out.println("C: Connessione eseguita al server");	
		
		Scanner sc = new Scanner(s.getInputStream());
		PrintWriter pw = new PrintWriter(s.getOutputStream(), true); //true fa flush in automatico
		
		pw.println("Messaggio");
		//pw.flush();
		
		String msg;
		msg = sc.nextLine();
		System.out.println(msg);
		
		sc.close();
		s.close(); //Chiusura del socket
		System.out.println("C: Connessione terminata al server");	*/
	}
	
	public static void ClientUI() {
		JFrame finestra = new JFrame("Client");
		finestra.setLayout(new FlowLayout());
		
		JPanel top = new JPanel();
		JPanel mid = new JPanel();
		JPanel bot = new JPanel();
		
		JButton send = new JButton("Send");
		bot.add(send);
		
		JButton connect = new JButton("Connect");
		bot.add(connect);
		
		JButton disconnect = new JButton("Disconnect");
		bot.add(disconnect);
		
		JLabel i_lb = new JLabel("Input");
		JTextField input = new JTextField(20);
		mid.add(i_lb);
		mid.add(input);
		
		JLabel o_lb = new JLabel("Output");
		JTextField output = new JTextField(20);
		output.setEditable(false);
		top.add(o_lb);
		top.add(output);
		
		finestra.add(top);
		finestra.add(mid);
		finestra.add(bot);
		
		ActionListener al;
		al = new Ascoltatore(input, output);
		
		send.addActionListener(al);
		connect.addActionListener(al);
		disconnect.addActionListener(al);
		
		finestra.pack();
		finestra.setVisible(true);
		finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
