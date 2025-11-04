import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Client {

	public static void main(String[] args) {
		JFrame principale = new JFrame("principale");
		JButton nuovo = new JButton("Nuovo");
		JButton esci = new JButton("Esci");		
		JPanel bottoni = new JPanel();		
		JLabel etichetta1 = new JLabel("Eseguire il lancio di una moneta?");
		
		Ascoltatore al = new Ascoltatore(principale);
		nuovo.addActionListener(al);
		esci.addActionListener(al);
		
		bottoni.add(nuovo);	
		bottoni.add(esci);
		
		
		principale.add(etichetta1, BorderLayout.NORTH);
		principale.add(bottoni, BorderLayout.SOUTH);
		
		principale.setVisible(true);
		principale.pack();
		principale.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
}

class Ascoltatore implements ActionListener{
	JFrame finestra;
	int i = 0; //counter lanci
	public Ascoltatore(JFrame finestra) {
		this.finestra=finestra;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Esci")) {
			int id = WindowEvent.WINDOW_CLOSING;
			WindowEvent close= new WindowEvent(this.finestra, id);
			this.finestra.dispatchEvent(close);
		}else if(e.getActionCommand().equals("Nuovo")) {
			//creazione nuovaConnessione
			Socket s = null;
			try {
				s = new Socket("127.0.0.1", 8080);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			//crea nuovo lancio moneta
			JFrame lancio = new JFrame("Lancio " + i);
			i++;
			
			JLabel etichetta2 = new JLabel("");
			
			JButton testa = new JButton("Testa");
			JButton croce = new JButton("Croce");
			JButton fine = new JButton("Fine");
			
			JPanel bottoni = new JPanel();
			bottoni.add(testa);
			bottoni.add(croce);
			bottoni.add(fine);
			
			AscoltatoreLancio all = new AscoltatoreLancio(etichetta2, lancio, s);
			testa.addActionListener(all);
			croce.addActionListener(all);
			fine.addActionListener(all);
			
			lancio.add(bottoni, BorderLayout.SOUTH);
			lancio.add(etichetta2, BorderLayout.NORTH);
			
			lancio.setVisible(true);
			lancio.pack();
			lancio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	}
	
}

class AscoltatoreLancio implements ActionListener{
	JLabel etichetta;
	JFrame finestra;
	Socket s;
	
	public AscoltatoreLancio(JLabel etichetta, JFrame finestra, Socket s) {
		this.etichetta = etichetta;
		this.finestra = finestra;
		this.s = s;
	}
	@Override
	public void actionPerformed(ActionEvent e){
		PrintWriter pw = null;
		Scanner sc = null;
		try {
			pw = new PrintWriter(s.getOutputStream(), true);
			sc = new Scanner(s.getInputStream());
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
		if(e.getActionCommand().equals("Testa")) {
			pw.println("true");
			etichetta.setText(sc.nextLine());
		}else if(e.getActionCommand().equals("Croce")) {
			pw.println("false");
			etichetta.setText(sc.nextLine());
		}else if(e.getActionCommand().equals("Fine")) {
			//termina connession e chiude finestra
			pw.println("end");
			try {
				s.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			int id = WindowEvent.WINDOW_CLOSING;
			WindowEvent close= new WindowEvent(this.finestra, id);
			this.finestra.dispatchEvent(close);
		}
	}
}
