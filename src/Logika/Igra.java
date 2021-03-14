package Logika;

import java.util.ArrayList;


/**
 * Klasa koja ubacuje polja na tablu te omogucava njihove zamjene i povezivanje ako postoji niz od 3 ili vise polja 
 * iste boje
 * @author Dino-Sven Dediæ
 */
public class Igra {
	/**
	 * Matrica koja sadrzi sva polja na tabli
	 */
	private Polje[][] tabla;
	/**
	 * Matrica koja cuva vrijednosti svih polja na tabli
	 */
	private int[][] polja;
	/**
	 * Dimenzije table
	 */
	private int dimenzije;
	/**
	 * Ukupan broj osvojenih poena
	 */
	private int poeni;
	/**
	 * Najbolji rezultat
	 */
	private int rekord;

	/**
	 * Broj poteza
	 */
	private int potezi;
	/**
	 * Koordinate polja koja ce se mijenjati
	 */
	private int r1 = 0, k1 = 0, r2 = 0, k2 = 0;
	
	
	/**
	 * Konstuktor koji kreira novu tablu zadanih dimnzija i poziva metodu resetuj()
	 * @param dim dimenzija table za igru
	 */
	public Igra (int dim) {
		dimenzije = dim;
		tabla = new Polje[dimenzije][dimenzije];
		polja = new int[dimenzije][dimenzije];
		for (int i = 0; i < dimenzije; i++) {
			for (int j = 0; j < dimenzije; j++) {
				polja[i][j] = 0;
			}
		}	
		resetuj();
	}
	
	/**
	 * Vraca dimenzije table za igru
	 * @return tabla.length dimenzija table
	 */
	public int getDimenzije() {
		return tabla.length;
	}
	
	/**
	 * Vraca polje na presjeku odgovarajuceg reda i kolone
	 * @param red
	 * @param kolona
	 * @return tabla[red][kolona] polje na presjeku reda i kolone
	 */
	public Polje getPolje(int red, int kolona) {
		return tabla[red][kolona];
	}
	
	/**
	 * Vraca niz sa vrijedostima polja
	 * @return polja
	 */
	public int[][] getPolja() {
		return polja;
	}
	
	/**
	 * Vraca vrijednost polja na presjeku odgovarajuceg reda i kolone
	 * @param red
	 * @param kolona
	 * @return tabla[red][kolona] polje na presjeku reda i kolone
	 */
	public int getVrijednostPolja(int red, int kolona) {
		return tabla[red][kolona].getVrijednost();
	}
	
	/**
	 * Vraca trenutni broj poena
	 * @return poeni
	 */
	public int getPoeni() {
		return poeni;
	}
	
	/**
	 * Postavlja novi broj poena
	 * @param poeni1
	 */
	public void setPoeni(int poeni1) {
		poeni = poeni1;
	}

	/**
	 * Vraca najbolji rezultat 
	 * @return rekord
	 */
	public int getRekord() {
		return rekord;
	}

	/**
	 * Postavlja novi najbolji rezultat
	 * @param rekord1
	 */
	public void setRekord(int rekord1) {
		rekord = rekord1;
	}

	/**
	 * Vraca preostali broj poteza
	 * @return potezi
	 */
	public int getPotezi() {
		return potezi;
	}
	
	/**
	 * Potavlja novi broj poteza
	 * @param potezi1
	 */
	public void setPotezi(int potezi1) {
		potezi = potezi1;
	}
	
	/**
	 * Metoda koja vraca true ako su sva polja u datom nizu jednaka, false ako nisu
	 * @return true/false
	 */
	public boolean equals (Object o) {
		if(o == null || !(o instanceof Igra)) {
			return false;
		}
		Igra igra1 = (Igra) o;
		if (igra1.getDimenzije() != this.getDimenzije()) {
			return false;
		}
		for(int i = 0; i < tabla.length; i++) {
			for (int j = 0; j < tabla.length; j++) {
				if(!this.getPolje(i,j).equals(igra1.getPolje(i,j))) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Metoda koja popunjava tablu sa random generisanim poljima tako da ne postoji niz od 3+ povezanih polja 
	 * i koja vraca poene na 0.
	 */
	public void resetuj () {
		for(int i = 0; i < tabla.length; i++) {
			for (int j = 0; j < tabla[i].length; j++) {
				do {
					tabla[i][j] = new Polje(7);
				} while(brojPoljaNiz(i, j) >= 3);
			}
		}
		setPoeni(0);
	}
	
	/**
	 * Cuva smjerove u kojima se moze vrsiti zamjena polja
	 */
	private enum Smjer { 
		LIJEVO, GORE, DESNO, DOLE
	}
	
	/**
	 * Metoda koja vraca najveci broj polja u nizu u odnosu na odredjeno polje u bilo kojem smjeru
	 * @param red odgovarajuci red
	 * @param kolona odgovarajuca kolona
	 */
	public int brojPoljaNiz (int red, int kolona) {
		int horizontalno = brojPoljaNizHorizontalno(red, kolona);
		int vertikalno = brojPoljaNizVertikalno(red, kolona);
		if(horizontalno > vertikalno) {
			return horizontalno;
		} else {
			return vertikalno;
		}
	}
	
	/**
	 * Vraca najveci broj polja u horizontalnom nizu
	 * @param red odgovarajuci red
	 * @param kolona odgovarajuca kolona
	 */
	public int brojPoljaNizHorizontalno (int red, int kolona) {
		return brojPoljaNiz(red, kolona - 1, tabla[red][kolona].getVrijednost(), Smjer.LIJEVO) + 
			brojPoljaNiz(red, kolona + 1, tabla[red][kolona].getVrijednost(), Smjer.DESNO) + 1;
	}
	
	/**
	 * Vraca najveci broj polja u vertikalnom nizu
	 * @param red odgovarajuci red
	 * @param kolona odgovarajuca kolona
	 */
	public int brojPoljaNizVertikalno (int red, int kolona) {
		return brojPoljaNiz(red - 1, kolona, tabla[red][kolona].getVrijednost(), Smjer.GORE)
			+ brojPoljaNiz(red + 1, kolona, tabla[red][kolona].getVrijednost(), Smjer.DOLE) + 1;
	}
	
	/**
	 * Rekurzivna metoda koja vraca najveci broj polja u nizu u odnosu na zadani smjer. Poziva se u svim ostalim metodama
	 * brojPoljaNiz*
	 * @param red odgovarajuci red
	 * @param kolona odgovarajuca kolona
	 * @param vrijednost vrijednost polja
	 * @param smjer smjer u kojm se posmatra
	 */
	public int brojPoljaNiz (int red, int kolona, int vrijednost, Smjer smjer) { 
		if(red < 0 || red >= tabla.length || kolona < 0 || kolona >= tabla.length) { 
			return 0;
		}
		if(tabla[red][kolona] == null || vrijednost != tabla[red][kolona].getVrijednost()) { 
			return 0;
		}
		if (smjer == Smjer.GORE) { 
			return brojPoljaNiz(red - 1, kolona, vrijednost, Smjer.GORE) + 1;
		} else if (smjer == Smjer.DOLE){
			return brojPoljaNiz(red + 1, kolona, vrijednost, Smjer.DOLE) + 1;
		} else if (smjer == Smjer.LIJEVO) {
			return brojPoljaNiz(red, kolona - 1, vrijednost, Smjer.LIJEVO) + 1;
		} else {
			return brojPoljaNiz(red, kolona + 1, vrijednost, Smjer.DESNO) + 1;
		}
	}

	/**
	 * Pokusava zamijeniti dva polja na zadatim redovima i kolonama. Ako su polja predaleko ili nisu iste boje 
	 * baca izuzetak. Koristi se u konzolnoj verziji.
	 * @param r1 red prvog
	 * @param k1 kolona prvog
	 * @param r2 red drugog
	 * @param k2 kolona drugog
	 * @throws Exception
	 */
	public void zamijeniVrijednosti(int r1, int k1, int r2, int k2) throws Exception {
		
		this.r1 = r1;
		this.k1 = k1;
		this.r2 = r2;
		this.k2 = k2;
		if((r1 == r2 && Math.abs(k2 - k1) == 1) || (Math.abs(r2 - r1) == 1 && k1 == k2)) {
			Polje pom = tabla[r1][k1];
			tabla[r1][k1] = tabla[r2][k2];
			tabla[r2][k2] = pom;
			
			if((r1 > r2 ? !poveziTri(r1, k1) & !poveziTri(r2, k2) : !poveziTri(r2, k2) & !poveziTri(r1, k1))) {
				pom = tabla[r1][k1];
				tabla[r1][k1] = tabla[r2][k2];
				tabla[r2][k2] = pom;
				throw new Exception("Nema niza od 3 ili vise polja");
			}
		} else {
			throw new Exception(r1 + "," + k1 + " " + r2 + "," + k2 + " su predaleko");
		}
	}
	
	/**
	 * Ako postoje nizovi od 3+ povezanih polja oznaci ih kao spremne za unistavanje te ih unisti. Ako je bilo unistavanja
	 * poziva metodu pomjeriDole()
	 * @param red odgovarajuci red
	 * @param kolona odgovarajuca kolona
	 * @return true/false
	 */
	public boolean poveziTri (int red, int kolona) {
		Polje p = tabla[red][kolona];
		int horizontalno = brojPoljaNizHorizontalno(red, kolona);
		int vertikalno = brojPoljaNizVertikalno(red, kolona);
		if (vertikalno < 3 && horizontalno < 3) {
			return false;
		}
		
		if(horizontalno >= 3) {
			for(int i = kolona - 1; i >= 0 && i < tabla.length && p.equals(tabla[red][i]); i--) { 
				unisti(red, i);
				unisti(red, i+1);
			}
			for(int i = kolona + 1; i >= 0 && i < tabla.length && p.equals(tabla[red][i]); i++) { 
				unisti(red, i);
				unisti(red, i-1);
			}
		}
		if (vertikalno >= 3) {
			for(int i = red - 1; i >= 0 && i < tabla.length && p.equals(tabla[i][kolona]); i--) { 
				unisti(i, kolona);
				unisti(i+1, kolona);
			}
			for(int i = red + 1; i >= 0 && i < tabla.length && p.equals(tabla[i][kolona]); i++) { 
				unisti(i, kolona);
				unisti(i-1, kolona);
			}
		}
		pomjeriDole();
		return true;
	}
	
	/**
	 * Popunjava sve praznine na tabli spustajuci polja iznad prema dole i generisuci nova random polja na vrhu.
	 * Unistava nove kombinacije ako su se pojavile pri pomjeranju ostalih polja.
	 */
	public void pomjeriDole () {
		ArrayList<Integer> redovi = new ArrayList<Integer>();
		ArrayList<Integer> kolone = new ArrayList<Integer>();
		for (int kol = 0; kol < tabla.length; kol++) {
			for (int red = tabla.length - 1; red >= 0; red --) {
				if(tabla[red][kol] == null) {
					redovi.add(red);
					kolone.add(kol);
					for (int i = red; i >= 0; i--) {
						if(tabla[i][kol] != null) {
							tabla[red][kol] = tabla[i][kol];
							tabla[i][kol] = null;
							break;
						}
					}
					if(tabla[red][kol] == null) {
						tabla[red][kol] = new Polje(7);	
					}
				}
			}
		}
		for(int i = 0; i < redovi.size(); i++) {
			poveziTri(redovi.get(i), kolone.get(i));
		}
	}
	
	/**
	 * Unistava polje i povecava broj poena
	 * @param red odgovarajuci red
	 * @param kolona odgovarajuca kolona
	 */
	public void unisti (int red, int kolona) {
		Polje polje = tabla[red][kolona];
		if(polje == null) {
			return;
		}
		tabla[red][kolona] = null; 
		setPoeni(getPoeni() + 100);
		System.err.println("Unistava " + red + "," + kolona); 
	}
	
	/**
	 * Popunjava tablu za igru u konzolnoj verziji sa odgovarajucim vrijednostima
	 */
	public void popuniTablu() {
		for (int i = 0; i < dimenzije; i++) {
			for (int j = 0; j < dimenzije; j++) {
				polja[i][j] = getVrijednostPolja(i, j);
			}
		}
	}
	
	/**
	 * Azurira tablu za igru u konzolnoj verziji. Prikazuje broj preostalih poteza i poena te postavlja nove vrijednosti
	 */
	public void update() {
		System.out.println("Potezi: " + getPotezi());
		System.out.println("Rezultat: " + getPoeni());
		for (int i = 0; i < dimenzije; i++) {
			for (int j = 0; j < dimenzije; j++) {
				polja[i][j] = getVrijednostPolja(i, j);
			}
		}
	}

}
