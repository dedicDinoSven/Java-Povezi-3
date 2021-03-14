package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Logika.Igra;
import Logika.Polje;
/**
 * Klasa koja sluzi za generisanje table za igru
 * @author Dino-Sven Dediæ
 */
public class Tabla extends JFrame implements ActionListener {
	
	/**
	 * Za povezivanje sa klasom Igra iz Logike
	 */
	private Igra tablaIgra;
	/**
	 * Polje na koje je igrac kliknuo
	 */
	private Polje odabranoPolje;
	/**
	 * Panel u koji se smjestaju polja razlicitih boja
	 */
	private JPanel grid;
	/**
	 * Panel u koji se smjestaju informacije namijenjene igracu
	 */
	private JPanel info;
	/**
	 * Panel u koji se smjesta broj preostalih poteza
	 */
	private JPanel poteziPanel;
	/**
	 * Informacija o broju preostalih poteza
	 */
	private JLabel poteziPrikaz;
	/**
	 * Informacija o trenutnom rezultatu
	 */
	private JLabel rezultatPrikaz;
	/**
	 * Informacija o najboljem rezultatu
	 */
	private JLabel rekordPrikaz;
	/**
	 * Dugme koje se pojavi kad nema vise poteza i omogucava novu igru
	 */
	private JButton restart;
	/**
	 * Varijabla koja govori da li je igra u toku
	 */
	private boolean igraTraje;
	/**
	 * Matrica u koju se smjestaju polja pri generisanju table
	 */
	private JButton[][] polja;
	/**
	 * Niz koji cuva moguce boje polja
	 */
	private Color[] boje = {Color.green, Color.red, Color.cyan, Color.yellow, Color.pink, Color.blue, Color.magenta};
	
	/**
	 * Konstruktor koji sluzi za namjestanje prikaza igre. Prvo generise citav prozor za igru. Nakon toga generise se
	 * sama igra te postavlja broj poteza i dimenzije table. Nakon toga, generise grid sa poljima za igru po prvi put i 
	 * dodaje im boju a onda i dodaje polja u prozor.
	 * Na kraju, generise panel koji cuva informacije bitne igracu na vrhu table.
	 */
	public Tabla () {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Povezi 3");
		setSize(640, 640);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		tablaIgra = new Igra(6);
		tablaIgra.setPotezi(20);
		tablaIgra.popuniTablu();
		polja = new JButton[tablaIgra.getDimenzije()][tablaIgra.getDimenzije()];
		grid = new JPanel(new GridLayout(tablaIgra.getDimenzije(), tablaIgra.getDimenzije()));
		for(int i = 0; i < tablaIgra.getDimenzije(); i++) {
			for (int j = 0; j < tablaIgra.getDimenzije(); j++) {
				
				polja[i][j] = new JButton();
				polja[i][j].setActionCommand(
	                    String.valueOf(i) + " " + String.valueOf(j));
				polja[i][j].addActionListener(this);
				polja[i][j].setBackground(boje[tablaIgra.getVrijednostPolja(i, j)]);
				grid.add(polja[i][j]);
			}
		}
		this.add(grid, BorderLayout.CENTER);
		
		info = new JPanel(new GridLayout(1, 3));
		poteziPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		poteziPrikaz = new JLabel("Preostalo poteza: " + tablaIgra.getPotezi());
		restart = new JButton("Restart");
		restart.setBackground(Color.RED);
		restart.setForeground(Color.WHITE);
		restart.addActionListener(this);
		igraTraje = true;
		poteziPanel.add(poteziPrikaz);
		JPanel rezultatPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); 
		rekordPrikaz = new JLabel("Najbolji rezultat: " + tablaIgra.getRekord());
		rezultatPrikaz = new JLabel("Rezultat: 0");
		rezultatPanel.add(rekordPrikaz);
		rezultatPanel.add(rezultatPrikaz);
		info.add(poteziPanel);
		info.add(rezultatPanel);
		this.add(info, BorderLayout.PAGE_START);
	}
	

	/**
	 * Metoda koja sluzi kao listener za svu dugmad na tabli. 
	 * Ako je kliknuto dugme restart poziva se metoda restartujIgru(). Ako je igra u toku postoje 2 mogucnosti: 
	 *  a) ako nije odabrano ni jedno polje, nakon klika postavi vrijednost varijable odabranoPolje na to polje i postavi 
	 *  okvir kliknutom polju.
	 *  b) ako je vec odabrano neko polje, pokusa zamijeniti ta 2 polja te ako uspije onda povecava broj bodova, 
	 *  smanjuje broj poteza te azurira tablu.
	 *  Izuzetak je bacen ako polja nisu jedno kraj drugog ili ako zamjena ne poveze 3 ili vise istih polja.
	 *  Ako je sve proslo uklanja okvir oko odgovarajuceg polja, uklanja selekciju sa polja, a ako nema vise poteza 
	 *  poziva metodu krajIgre()
	 * @param e dogadjaj koji poziva metodu
	 */
	public void actionPerformed (ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Restart":
			restartujIgru(); 
			return; 
		default:
			String[] klikVrijednost = e.getActionCommand().split(" ");
	        int r = Integer.parseInt(klikVrijednost[0]);
	        int k = Integer.parseInt(klikVrijednost[1]);
	        System.out.println("Izabrano: " + r + "," + k);
			JButton kliknuto = (JButton) e.getSource();	
			if(igraTraje) {
				if(odabranoPolje == null) { 
					odabranoPolje = tablaIgra.getPolje(r, k);
					odabranoPolje.setRed(r);
					odabranoPolje.setKolona(k);
					kliknuto.setBorder(BorderFactory.createLineBorder(Color.black, 4));
				} else {
					try {
						tablaIgra.getPolje(r, k).setRed(r);
						tablaIgra.getPolje(r, k).setKolona(k);
						tablaIgra.zamijeniVrijednosti(odabranoPolje.getRed(), odabranoPolje.getKolona(),
								tablaIgra.getPolje(r, k).getRed(), tablaIgra.getPolje(r, k).getKolona());
						tablaIgra.setPotezi(tablaIgra.getPotezi() - 1);
						
						update();
					} catch (Exception ee) {
						System.err.println(ee.getMessage());
					} finally {
						kliknuto.setBorder(BorderFactory.createEmptyBorder());
						odabranoPolje = null;
						if (tablaIgra.getPotezi() == 0) {
							krajIgre();
						}
					}
				}
			}	
		}
	}
	
	/**
	 * Azurira JFrame nakon svake promjene na tabli za igru. Azurira se prikaz broja poteza i osvojenih poena.
	 * Mijenja centralni JPanel sa novim JPanelom sa ponovno nacrtanim poljima.
	 */
	public void update () {
		poteziPrikaz.setText("Potezi: " + tablaIgra.getPotezi());
		rezultatPrikaz.setText("Rezultat: " + tablaIgra.getPoeni());
		this.remove(grid);
		grid = new JPanel( new GridLayout(tablaIgra.getDimenzije(), tablaIgra.getDimenzije()));
		for(int i = 0; i < tablaIgra.getDimenzije(); i++) {
			for (int j = 0; j < tablaIgra.getDimenzije(); j++) {
				
				polja[i][j] = new JButton();
				if(polja[i][j].getActionListeners().length == 0) {
					polja[i][j].setActionCommand(
		                    String.valueOf(i) + " " + String.valueOf(j));
					polja[i][j].addActionListener(this);
				}
				polja[i][j].setBackground(boje[tablaIgra.getVrijednostPolja(i, j)]);
				grid.add(polja[i][j]);
			}
		}
		
		this.add(grid, BorderLayout.CENTER);		
		this.validate();
		this.repaint();
	}
	
	/**
	 * Priprema igraca za novu igru. Restartuje tablu, poteze i bodove. Postavlja igraTraje na true. 
	 * Uklanja restart dugme.
	 */
	public void restartujIgru() {
		tablaIgra.resetuj();
		tablaIgra.setPotezi(20);
		poteziPrikaz.setText("Potezi: " + tablaIgra.getPotezi());
		poteziPanel.remove(restart);
		update();
		igraTraje = true;
	}
	
	/**
	 * Zavrsava trenutnu partiju ako nema vise poteza. Postavlja igraTraje na false. Dodaje dugme za restart.
	 * Postavlja novi najbolji rezultat.
	 */
	public void krajIgre() {
		igraTraje = false;
		poteziPrikaz.setText("Nema vise poteza!");
		poteziPanel.add(restart);
		if(tablaIgra.getPoeni() > tablaIgra.getRekord()) {
			tablaIgra.setRekord(tablaIgra.getPoeni());
			rekordPrikaz.setText("Najbolji rezultat: " + tablaIgra.getRekord());
		}
	}
	
}