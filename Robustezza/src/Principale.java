public class Principale {

	public static void main(String[] args) {
		Persona p = new Persona("Alberto", "Alberti");
		Persona q = new Persona("Pelandro", "Alberti");
		
		Fiscale p_cf = new Fiscale("LBRLRT82B28A150U", "26/11/2031");
		Fiscale q_cf = new Fiscale("LBRPLN82B28A150U", "26/11/2031");

		Gettone.Set(p_cf, p);
		Gettone.Set(q_cf, q);
		p.PrintFiscale();
		q.PrintFiscale();
	}

}
