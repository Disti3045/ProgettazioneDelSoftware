public class Persona {
	private String nome;
	private String cognome;
	private Fiscale cf;
	
	public Persona(String nome, String cognome) {
		this.nome = nome;
		this.cognome = cognome;
		this.cf = null;
	}
	
	public String GetNome() {
		return nome;
	}
	public String GetCognome() {
		return cognome;
	}
	public void Set_cf(Fiscale cf, Gettone gt) {
		if(gt==null) {
			throw new NullPointerException("Operazione non consentita, Gettone nullo");
		}else if(cf == null) {
			throw new NullPointerException("Operazione non consentita, Codice Fiscale nullo");
		}else {
			this.cf = cf;
		}
	}
	public Fiscale GetFiscale() {
		return cf;
	}
	public void PrintFiscale() {
		if(cf!=null) {
			System.out.println(cf.toString());
		}
	}
}
