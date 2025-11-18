package possedimenti;

public class Immobile {
	Comune comune;
	Persona[] proprietari;
	String numero_catastale;
	float rendita_catastale;
	
	public Immobile(Comune comune, Persona[] proprietari, String numero_catastale, float rendita_catastale) {
		this.comune = comune;
		this.proprietari = proprietari;
		this.numero_catastale = numero_catastale;
		this.rendita_catastale = rendita_catastale;
	}
	
	public void StampaProprietari() {
		int numero_proprietari = proprietari.length;
		System.out.println("Proprietari dell'immobile "+numero_catastale);
		if(numero_proprietari>0) {
			for(int i = 0; i<numero_proprietari; i++) {
				System.out.println("Proprietario n."+i+": " + proprietari[i].GetNomeCognome());
			}
		}
	}
	
	public String StampaImmobile() {
		return comune + " " + numero_catastale;
	}
	
	public void PrintComune() {
		System.out.println("L'immobile si trova nel comune di " + comune.GetComune() );
	}
	
}
