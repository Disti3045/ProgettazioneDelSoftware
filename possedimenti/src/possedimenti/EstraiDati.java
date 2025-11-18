package possedimenti;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class EstraiDati {
	
	public static void main(String[] args) {
		

	}
	
	public static Comune[] EstraiComune(String file) throws IOException{
		Scanner sc = new Scanner(new File(file));
		int i = 0;
		Comune[] c = new Comune[10];
		while(sc.hasNextLine()) {
			c[i] = new Comune("Roma", sc.nextLine());
			i++;
		}
		return c;
	}
}
