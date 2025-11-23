import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class EstraiDati {
	
	public static void main(String[] args) throws IOException {
		String citta = "Roma";
		Comune[] comuni = EstraiComune(citta, "C:/elimnb/eclipse/Immobili/src/citta.txt", "C:/elimnb/eclipse/Immobili/src/immobili.txt");
		Persona[] persone = EstraiPersone("C:/elimnb/eclipse/Immobili/src/persone.txt", comuni);
		
		new InterfacciaGrafica(comuni, persone);
	}
	
	public static Comune[] EstraiComune(String citta, String file1, String file2) throws IOException{
		Scanner sc = new Scanner(new File(file1));
		int i = 0;
		Comune[] c = new Comune[100];
		while(sc.hasNextLine()) {
			String cod_cat = sc.nextLine();
			c[i] = new Comune(citta, cod_cat, i);
			c[i].immobili = EstraiImmobili(file2, c[i]);
			i++;
		}
		
		//Ridimensionamento
		Comune[] ret = new Comune[i];
		for(int f=0; f<i; f++) {
			ret[f]=c[f];
		}

		sc.close();
		return ret;
	}
	
	public static Immobile[] EstraiImmobili(String file, Comune c) throws IOException{
		Scanner sc = new Scanner(new File(file));
		int i = 0;
		Immobile[] imb = new Immobile[100];
		while(sc.hasNextLine()) {
			String linea = sc.nextLine();
			if(linea.contains(c.codice_catastale)) {
				String[] format_imb = linea.split(" ");
				imb[i] = new Immobile(c, format_imb[1], Float.parseFloat(format_imb[2]), format_imb[3].split(","));
				i++;
			}else {
				continue;
			}
		}
		
		//Ridimensionamento
		Immobile[] ret = new Immobile[i];
		for(int f=0; f<i; f++) {
			ret[f]=imb[f];
		}
		
		sc.close();
		return ret;
	}
	
	public static Persona[] EstraiPersone(String file, Comune[] cmn) throws IOException{
		Scanner sc = new Scanner(new File(file));
		int i = 0;
		Persona[] prs = new Persona[100];
		while(sc.hasNextLine()) {
			String[] dettagli_prs = sc.nextLine().split(",");
			prs[i] = new Persona(dettagli_prs[1].trim(), dettagli_prs[0].trim(), dettagli_prs[2].trim(), PopolaImmobili(dettagli_prs[2].trim(), cmn));
			i++;
		}
		
		//Ridimensionamento
		Persona[] ret = new Persona[i];
		for(int f=0; f<i; f++) {
			ret[f]=prs[f];
		}
		
		sc.close();
		return ret;
	}
	
	public static Immobile[] PopolaImmobili(String cf, Comune[] cmn) {
		Immobile[] imb = new Immobile[100];
		int i = 0;
		for(Comune c : cmn) {
			for(Immobile im : c.immobili) {
				Immobile imb_vis = im.getImmobileFromProprietario(cf);
				if(imb_vis!=null) {
					imb[i] = imb_vis;
					i++;
				}
			}
		}
		
		//Ridimensionamento
		Immobile[] ret = new Immobile[i];
		for(int f=0; f<i; f++) {
			ret[f]=imb[f];
		}
		
		return ret;
	}
	
}
