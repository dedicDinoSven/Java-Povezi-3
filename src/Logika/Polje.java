package Logika;

/**
 * Klasa koja sluzi za kreiranje jednog polja na tabli
 * @author Dino-Sven Dediæ
 */
public class Polje {
	/**
	 * Vrijednost polja
	 */
	private int vrijednost;
	/**
	 * Red u kojem se polje nalazi
	 */
	private int red;
	/**
	 * Kolona u kojoj se polje nalazi
	 */
	private int kolona;
	
	/**
	 * Konstruktor koji generise random polje. Odredjuje boju polja prema prema boji iz enum-a
	 * @param max predstavlja maksimalan broj boja, npr. za max = 2 su dostupne samo 2 boje
	 */
	public Polje (int max) { 
		int n = (int) (Math.random()*max);
		red = -1;
		kolona = -1;
		for(int i=0;i<=7;i++)
		{
			if(i==n) {
				vrijednost = i;
			}
		}
	}
				
	
	/**
	 * Vraca odgovarajucu vrijednost polja
	 * @return vrijednost 
	 */
	public int getVrijednost() {
		return vrijednost;
	}
	
	/**
	 * Postavlja vrijednost polju
	 * @param 
	 */
	public void setVrijednost(int vrijednost1) {
		vrijednost = vrijednost1;
	}
	
	/**
	 * Vraca u kojem se redu polje nalazi
	 * @return red
	 */
	public int getRed() {
		return red;
	}
	
	/**
	 * Vraca u kojoj se koloni polje nalazi
	 * @return kolona
	 */
	public int getKolona() {
		return kolona;
	}
	
	
	/**
	 * Postavlja red polja na odgovarajuci red
	 * @param red
	 */
	public void setRed(int red) {
		this.red = red;
	}
	
	/**
	 * Postavlja kolonu polja na odgovarajucu kolonu
	 * @param kolona
	 */
	public void setKolona(int kolona) {
		this.kolona = kolona;
	}
	
	/**
	 * Ispituje da li su boje 2 polja iste, vraca true ako jesu, false ako nisu
	 * @return true ili false
	 */
	public boolean equals(Object o) {
		if(o == null || !(o instanceof Polje)) {
			return false;
		}
		Polje drugoPolje = (Polje) o;
		return vrijednost == drugoPolje.getVrijednost();
	}
	
}