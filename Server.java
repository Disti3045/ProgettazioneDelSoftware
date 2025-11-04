import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class Server {

	public static void main(String[] args) throws IOException {
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(8080);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(true) {
			Socket s = ss.accept();
			System.out.println("Connessione accettata");

			Eseguibile tt = new Eseguibile(s);
			Thread t = new Thread(tt);
			t.start();
			
		}
	}

}

class Eseguibile implements Runnable{
	boolean moneta;
	Socket s;
	
	public Eseguibile(Socket s) {
		this.s = s;
	}
	
	@Override
	public void run() {
		Scanner sc = null;
		PrintWriter pw = null;
		Random random = new Random();
		
		try {
			sc = new Scanner(s.getInputStream());
			pw = new PrintWriter(s.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(true) {
			moneta = random.nextBoolean();
			String client = null;
			if(sc.hasNextLine()) {
				client = sc.nextLine();
				
				if(client.equals(tostringbool(moneta))) {
					pw.println("Si");
				}else if(client.equals("end")) {
					try {
						s.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					sc.close();
					Thread.currentThread().interrupt();
				}
			}
		}
	}
	
	public static String tostringbool(boolean bool) {
		if(bool==true) {
			return "True";
		}
		return "False";
	}
	
}
	
