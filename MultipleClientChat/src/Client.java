import java.io.IOException;
import java.net.UnknownHostException;

//---------------------------

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;


public class Client {
	public static void main(String[] args) throws IOException, UnknownHostException{
		ClientUI();
	}
	
	public static void ClientUI() {
	    JFrame finestra = new JFrame("Client");
	    finestra.setLayout(new BorderLayout());
	    
	    JPanel top = new JPanel(new BorderLayout());
	    JPanel mid = new JPanel();
	    JPanel bot = new JPanel();
	    
	    JButton connect = new JButton("Connect");
	    JButton send = new JButton("Send");
	    JButton disconnect = new JButton("Disconnect");
	    
	    bot.add(connect);
	    bot.add(send);
	    bot.add(disconnect);
	    
	    JLabel o_lb = new JLabel("Output");
	    JTextArea output = new JTextArea(15, 30);
	    output.setEditable(false);
	    
	    top.add(o_lb, BorderLayout.NORTH);
	    top.add(output, BorderLayout.CENTER);
	    
	    JLabel i_lb = new JLabel("Input");
	    JTextField input = new JTextField(20);
	    
	    mid.add(i_lb);
	    mid.add(input);
	    
	    finestra.add(top, BorderLayout.NORTH);
	    finestra.add(mid, BorderLayout.CENTER);
	    finestra.add(bot, BorderLayout.SOUTH);
	    
	    ActionListener al = new Ascoltatore(input, output);
	    send.addActionListener(al);
	    connect.addActionListener(al);
	    disconnect.addActionListener(al);
	    
	    output.setText("Inserisci la porta di connessione");
	    input.setText("6767");
	    
	    finestra.pack();
	    finestra.setVisible(true);
	    finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}