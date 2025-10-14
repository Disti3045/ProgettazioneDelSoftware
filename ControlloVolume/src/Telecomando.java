import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Telecomando {
	public static void main(String[] args) throws IOException, UnknownHostException{
		Socket telecomando = new Socket("", 6767);
		
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(telecomando.getOutputStream(), true);
		while(true) {
			System.out.println("Cambio volume +/-");
			String vol = sc.nextLine();
			if(vol.equals("+")) {
				vol = "vol+";
			}else if(vol.equals("-")) {
				vol = "vol-";
			}else if(vol.equals("=")) {
				vol = "vol";
			}
			pw.println(vol);
			if(vol.equals("CLOSE")) {
				sc.close();
				break;
			}
		}
		telecomando.close();
	}
}
