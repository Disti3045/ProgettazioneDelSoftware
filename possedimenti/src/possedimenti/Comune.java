package possedimenti;

public class Comune {
	String nome;
	String codice_catastale;
	
	Immobile[] immobili;
	
	public Comune(String nome, String codice_catastale) {
		this.nome = nome;
		this.codice_catastale = codice_catastale;
	}
	
	public String GetComune() {
		return codice_catastale;
	}
	
	public void PopolaComune(Immobile[] input) {
		this.immobili = input;
	}
}
