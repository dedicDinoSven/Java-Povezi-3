package konzola;

import java.util.Arrays;
import java.util.Scanner;

import Logika.Igra;
import Logika.Polje;

/**
 * Klasa u kojoj se nalazi konzolna verzija aplikacije zajedno sa svim potrebnim
 * metodama, ukljucujuci onu za pokretanje.
 * 
 * @author Dino-Sven Dediæ
 */
public class IgraKonzola {

	/**
	 * Tabla za igru
	 */
	private static Igra tabla;
	/**
	 * Cuva polje na koje je kliknuto
	 */
	private static Polje odabranoPolje;
	/**
	 * Cuva informaciju o tome da li je igra u toku
	 */
	private static boolean igraTraje;

	/**
	 * Sluzi za pokretanje igrice. Postavlja dimezije i broj poteza.
	 * Popunjava i ispisuje tablu za igru po prvi put. Dok igra traje obavlja interakciju sa igracem:
	 * a) Odigravanje novog poteza (zamjena polja)
	 * 		odigrava novi potez ako je moguce i osvjezava tablu pozivom metoda update() i ispisiTablu()
	 * 		ako nema vise poteza zavrsava igru i ispisuje broj osvojenih bodova
	 * b) Restartovanje ili kraj igre
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		igraTraje = true;
		tabla = new Igra(6);
		tabla.setPotezi(20);
		tabla.popuniTablu();
		ispisiTablu();

		System.out.println("Povezi 3");
		System.out.println("Za kraj igre unesite: 'kraj'");
		System.out.println("Za novu igru unesite: 'restart'");

		while (igraTraje) {
			System.out.println("Unesi vrijednost: ");
			String x = getInput();
			if (x.equals("kraj")) {
				System.out.println("Kraj igre");
				System.exit(0);
				break;
			} else if (x.equals("restart")) {
				System.out.println("Nova igra\n");
				main(args);
				break;
			} else {
				String y = getInput();
				if (y.equals("kraj")) {
					System.out.println("Kraj igre");
					System.exit(0);
					break;
				} else if (y.equals("restart")) {
					System.out.println("Nova igra\n");
					main(args);
					break;
				}
				if (odabranoPolje == null) {
					odabranoPolje = tabla.getPolje(Integer.parseInt(x), Integer.parseInt(y));
					odabranoPolje.setRed(Integer.parseInt(x));
					odabranoPolje.setKolona(Integer.parseInt(y));
					System.out.println("Izabrano:" + odabranoPolje.getVrijednost());
				} else {
					try {
						System.out.println(
								"Izabrano:" + tabla.getPolje(Integer.parseInt(x), Integer.parseInt(y)).getVrijednost());
						System.err.println();
						tabla.getPolje(Integer.parseInt(x), Integer.parseInt(y)).setRed(Integer.parseInt(x));
						tabla.getPolje(Integer.parseInt(x), Integer.parseInt(y)).setKolona(Integer.parseInt(y));
						tabla.zamijeniVrijednosti(odabranoPolje.getRed(), odabranoPolje.getKolona(),
								tabla.getPolje(Integer.parseInt(x), Integer.parseInt(y)).getRed(),
								tabla.getPolje(Integer.parseInt(x), Integer.parseInt(y)).getKolona());
						tabla.setPotezi(tabla.getPotezi() - 1);
						tabla.update();
						ispisiTablu();
					} catch (Exception ee) {
						System.err.println(ee.getMessage());
					} finally {
						odabranoPolje = null;
						if (tabla.getPotezi() == 0) {
							System.out.println("Kraj igre.");
			   	 			System.out.println("Osvojili ste: " + tabla.getPoeni());
			   	 			System.exit(0);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Uzima vrijednosti koje unosi igrac 
	 * @return str vrijednosti koordinata polja koje je igrac odabrao
	 */
	private static String getInput() {
		Scanner sc = new Scanner(System.in);
		String str = "";
		try {
			str = sc.nextLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * Ispisuje tablu za igru
	 */
	public static void ispisiTablu() {
		for (int[] red : tabla.getPolja())
			System.out.println(Arrays.toString(red));
		System.out.println("");
	}
}
