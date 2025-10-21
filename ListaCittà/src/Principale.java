import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Principale {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Client");
		
		JButton connect = new JButton("Connect");
		JButton disconnect = new JButton("Disconnect");
		JButton libri = new JButton("Libri");
		JButton stop = new JButton("Stop");
		
		JTextArea listOutput = new JTextArea(100, 100);
		listOutput.setEditable(false);
		Ascoltatore al = new Ascoltatore(listOutput);
		
		JPanel bottoni = new JPanel();
		bottoni.add(connect);
		bottoni.add(disconnect);
		bottoni.add(libri);
		bottoni.add(stop);
		bottoni.setLayout(new FlowLayout());
		
		connect.addActionListener(al);
		disconnect.addActionListener(al);
		libri.addActionListener(al);
		stop.addActionListener(al);
		
		JLabel txt_lb = new JLabel("Server Output");
		
		frame.add(txt_lb);
		frame.add(listOutput);
		frame.add(bottoni);
		
		frame.setLayout(new GridLayout(3, 1));
		frame.setSize(400, 200);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
