public class Comune {
	String nome;
	String codice_catastale;
	int hashcode;
	Immobile[] immobili;
	
	public Comune(String nome, String codice_catastale, int hashcode) {
		this.nome = nome;
		this.codice_catastale = codice_catastale;
		this.hashcode = hashcode;
	}
	
	public String GetComune() {
		return codice_catastale;
	}
	
	@Override
	public int hashCode() {
		return hashcode;
	}
}
