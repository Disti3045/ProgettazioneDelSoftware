public class Immobile {
	Comune comune;
	String[] proprietari;
	String numero_catastale;
	float rendita_catastale;
	
	public Immobile(Comune comune, String numero_catastale, float rendita_catastale, String[] proprietari) {
		this.comune = comune;
		this.proprietari = proprietari;
		this.numero_catastale = numero_catastale;
		this.rendita_catastale = rendita_catastale;
	}
	
	public void StampaProprietari(Persona[] persone) {
		int numero_proprietari = proprietari.length;
		System.out.println("Proprietari dell'immobile "+numero_catastale);
		if(numero_proprietari>0) {
			for(int i = 0; i<numero_proprietari; i++) {
				System.out.println("Proprietario n."+i+": " + GetPersonaFromCodiceFiscale(proprietari[i], persone));
			}
		}
	}
	
	public String StampaImmobile() {
		return comune + " " + numero_catastale;
	}
	
	public void StampaComune() {
		System.out.println("L'immobile si trova nel comune di " + comune.GetComune() );
	}
	
	public Immobile getImmobileFromProprietario(String cf) {
		Immobile imb = null;
		for(int i = 0; i<proprietari.length; i++) {
			if(proprietari[i].trim().equals(cf)) {
				imb = this;
			}else {
				continue;
			}
		}
		return imb;
	}
	
	public static String GetPersonaFromCodiceFiscale(String cf, Persona[] persone) {
		String nome_cognome = null;
		for(Persona p : persone) {
			if(p.cf.trim().equals(cf.trim())) {
				nome_cognome = p.GetNomeCognome();
			}
		}
		return nome_cognome;
	}
	
	@Override
	public int hashCode() {
		return comune.hashCode();
	}
}

