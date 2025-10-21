import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JTextArea;

import java.awt.event.ActionEvent;

public class Ascoltatore implements ActionListener{
	
	JTextArea textArea;
	Scanner sc;
	PrintWriter pw;
	
	Socket con;
	boolean connected = false;
	ThreadCitta thread;
	Thread th;
	boolean output;
	
	public Ascoltatore(JTextArea textArea) {
		this.textArea = textArea;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		if(comando.equals("Connect") && connected == false){
			connected = true;
			try {
				con = new Socket("192.168.51.90", 4400);
				sc = new Scanner(con.getInputStream());
				pw = new PrintWriter(con.getOutputStream(), true);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			textArea.append("Utente connesso\n");
		}else if(comando.equals("Disconnect") && connected == true) {
			if(pw!=null) {
				pw.println("DISCONNECT");
				try {
					con.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}else if(comando.equals("Libri") && connected == true) {
			pw.println("START");
			output = false;
			thread = new ThreadCitta(textArea, sc, output);
			th = new Thread(thread);
			th.start();
			if(output == true) {
				th.interrupt();
			}
		}else if(comando.equals("Stop") && connected == true && th.isAlive()) {
			pw.println("STOP");
			output = false;
			//sc.nextLine();
			th.interrupt();
		}else {
			textArea.append("Utente non connesso\n");
		}
		
	}
	
}
