package Esercizio;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Principale {
	
	public static void main(String[] args) throws IOException {
		ClasseSincronizzata sync = new ClasseSincronizzata();
		ServerSocket ss;
		Socket s;
		
		ss = new ServerSocket(8080);
		while(true) {
			s = ss.accept();
			sync.connetti();
				
			ThreadRunnable th = new ThreadRunnable(s, sync);
			Thread t = new Thread(th);
			t.start();
		}
	}

}

class ClasseSincronizzata {
	public int numeroConnessioni = 0;
	public int MAX_CON = 3;
	public Object lock = new Object();
	
	public synchronized void disconnessione() {
		numeroConnessioni--;
	}
	public synchronized void connetti() {
		numeroConnessioni++;
	}
}

class ThreadRunnable implements Runnable{
	Socket s;
	Scanner sc;
	PrintWriter pw;
	ClasseSincronizzata sync;
	
	public ThreadRunnable(Socket s, ClasseSincronizzata sync){
		this.s = s;
		this.sync = sync;
	}
	@Override
	public void run() {
		
		try {
			pw = new PrintWriter(this.s.getOutputStream(), true);
			sc = new Scanner(this.s.getInputStream());
			
			pw.println("Inizio");	
			pw.println("In Attesa....");
			synchronized(sync.lock) {
				if(sync.numeroConnessioni<=sync.MAX_CON) {
					pw.println("Thread in esecuzione");
					while(sc.hasNextLine()) {
						String msg = sc.nextLine();
						if(msg.equals(("fine"))) {
							pw.println("Fine Connessione");
							sync.disconnessione();
							sync.lock.notifyAll();
							s.close();
							Thread.currentThread().interrupt();
						}else if(msg.equals(("connessioni"))) {
							pw.println("Server Connessioni Attive: " + sync.numeroConnessioni);
						}else {
							int l = msg.length();
							String a = msg.substring(0,1);
							pw.println("Server: " + a + " " + l);
						}
						
					}
				}else {
					sync.lock.wait();
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
}
