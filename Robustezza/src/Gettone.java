
public class Gettone {
	
	private Gettone() {
		
	}
	
	public static void Set(Fiscale cf, Persona p) {
		Gettone gt = new Gettone();
		cf.Set_titolare(p, gt);
		p.Set_cf(cf, gt);
	}
	
}
