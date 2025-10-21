import java.util.Scanner;

import javax.swing.JTextArea;

public class ThreadCitta implements Runnable{

	JTextArea textArea;
	Scanner sc;
	boolean output = false;
	
	public ThreadCitta(JTextArea textArea, Scanner sc, boolean output) {
		this.textArea = textArea;
		this.sc = sc;
		this.output = output;
	}
	@Override
	public void run() {
			String msg_server;
			while(sc.hasNextLine()) {
				try {
					Thread.sleep(100);
				} catch(InterruptedException e){
					break;
				}
				msg_server = sc.nextLine();
				if(msg_server.equals("END")) {
					output = true;
				}
				textArea.append(msg_server+"\n");
				System.out.println(msg_server);
			}
	}

}
