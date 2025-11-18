package possedimenti;

public class Persona {
	String nome;
	String cognome;
	String cf;
	
	Immobile[] immobili;
	
	public Persona(String nome, String cognome, String cf, Immobile[] immobili) {
		this.nome = nome;
		this.cognome = cognome;
		this.cf = cf;
		this.immobili = immobili;
	}
	
	public void PrintImmobili() {
		int numero_immobili = immobili.length;
		System.out.println("Immobili di " + nome + " " + cognome + ":");
		if(numero_immobili>0) {
			for(int i = 0; i<numero_immobili; i++) {
				System.out.println("Immobile n." + i + ": " + immobili[i].StampaImmobile());
			}
		}
	}
	
	public String GetNomeCognome() {
		return nome + " " + cognome;
	}
	
}
