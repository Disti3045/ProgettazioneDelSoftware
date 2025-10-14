import java.awt.GridLayout;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Televisore {
	public static void main(String[] args) throws IOException, UnknownHostException{
		int volume = 50;
		boolean connesso = false;
		int volMax = 100;
		int volMin = 0;
		
		//ServerUI();
		ServerSocket tv = new ServerSocket(7878);
		System.out.println("Televisore acceso sulla porta 7878");
		Socket telecomando;
		while(true) {
			telecomando = tv.accept();
			System.out.println("Telecomando connesso");
			
			Scanner sc = new Scanner(telecomando.getInputStream());
			PrintWriter pw = new PrintWriter(telecomando.getOutputStream(), true);
			
			if(telecomando!=null) {
				connesso = true;
			}
			
			while(connesso) {
				String cambioVolume = "";
				cambioVolume = sc.nextLine();
				if(cambioVolume!=null &&!cambioVolume.equals("CLOSE")) {
					if(cambioVolume.equals("vol+")) {
						if(volume<volMax) {
							volume++;
						}
						pw.println("Volume:" + volume);
						System.out.println("Volume aumentato: " + volume);
					}else if(cambioVolume.equals("vol-")) {
						if(volume>volMin) {
							volume--;
						}
						pw.println("Volume:" + volume);
						System.out.println("Volume diminuito: " + volume);
					}else if(cambioVolume.equals("vol")) {
						pw.println("Volume:" + volume);
						System.out.println("Volume corrente: " + volume);
					}
					cambioVolume = "";
				}else {
					System.out.println("Telecomando disconnesso");
					connesso = false;
					telecomando.close();
					break;
				}
			}
			
			sc.close();
		}
	}
	
	public static void ServerUI() {
		JFrame output = new JFrame();
		JTextField volume = new JTextField(3);
		volume.setEditable(false);
		JLabel vol_lb = new JLabel("Televisione");
		
		output.setVisible(true);
		output.setLayout(new GridLayout(2,1));
		
		output.add(vol_lb);
		output.add(volume);
		
		output.pack();
		output.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}
}
