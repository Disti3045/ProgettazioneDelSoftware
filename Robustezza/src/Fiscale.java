public class Fiscale {
	private String caratteri;
	private String scadenza;
	private Persona titolare;
	
	public Fiscale(String caratteri, String scadenza) {
		this.scadenza = scadenza;
		this.caratteri = caratteri;
		this.titolare = null;
	}
	
	public void Set_titolare(Persona persona, Gettone gt) {
		if(gt==null) {
			throw new NullPointerException("Operazione non consentita, Gettone nullo");
		}else if(persona == null) {
			throw new NullPointerException("Operazione non consentita, Persona nulla");
		}else if(this.titolare==null){
			this.titolare = persona;
		}else {
			throw new RuntimeException("Titolare già assegnato");
		}
	}
	
	public Persona GetTitolare() {
		return titolare;
	}
	
	@Override
	public String toString() {
		return caratteri + " " + scadenza;
	}
}
