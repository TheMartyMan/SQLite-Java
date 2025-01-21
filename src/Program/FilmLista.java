package Program;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class FilmLista extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable tabla;
	private FilmTM ftm;
	DBMetodusok dbm = new DBMetodusok();
	private Ell c = new Ell();

	@SuppressWarnings("unchecked")
	public FilmLista(JFrame f,FilmTM betm) {
		super(f, "Filmek listája", true);
		ftm =betm;
		setBounds(100, 100, 670, 360);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(192, 192, 192));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JButton btnBezar = new JButton("Bez\u00E1r");
			btnBezar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			
			
			btnBezar.setBackground(new Color(169, 169, 169));
			btnBezar.setFont(new Font("Tahoma", Font.PLAIN, 12));
			btnBezar.setBounds(552, 293, 94, 30);
			contentPanel.add(btnBezar);
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 10, 636, 273);
			contentPanel.add(scrollPane);
			{
				tabla = new JTable(ftm);
				
				scrollPane.setViewportView(tabla);
				
				TableColumn tc = null;
				for (int i = 0; i < 6; i++) {
				tc = tabla.getColumnModel().getColumn(i);
				if (i==0 || i==1 ) tc.setPreferredWidth(20);
				else if (i==2 ||i==3) tc.setPreferredWidth(180);
				else if (i==4 ) tc.setPreferredWidth(80);
				else {tc.setPreferredWidth(100);}
				}
				
				tabla.setAutoCreateRowSorter(true);
				{
					JButton btnTorles = new JButton("T\u00F6rl\u00E9s");
					btnTorles.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if(f.getClass().getName().equals("Program.SQLite")) {
								int db=0,jel=0,x=0;
								for (x = 0; x < ftm.getRowCount(); x++)
									if((Boolean)ftm.getValueAt(x, 0)) {db++; jel=x;}
								if(db==0) uzenet("Kérem jelöljön ki valamit!");
								if(db>1) uzenet("Egyszerre csak egy adat törölhetõ!");
								if(db==1) {
									dbm.kapcs();
									dbm.filmTorol(RTM(jel,1));
									ftm.removeRow(jel);
									c.uzenet("Rekord törölve!", 1);
									dbm.leKapcs();
								}
							}
							else if(f.getClass().getName().equals("Program.CSV")) {
								int db=0,jel=0,x=0;
								for (x = 0; x < ftm.getRowCount(); x++)
									if((Boolean)ftm.getValueAt(x, 0)) {db++; jel=x;}
								if(db==0) uzenet("Kérem jelöljön ki valamit!");
								if(db>1) uzenet("Egyszerre csak egy adat törölhetõ!");
								if(db==1) {
									ftm.removeRow(jel);
									dbm.csvFilmTorol(ftm);
									dispose();
									c.uzenet("Rekord törölve!", 1);
								}
							}
						}
					});
					
					
					btnTorles.setFont(new Font("Tahoma", Font.BOLD, 12));
					btnTorles.setBackground(new Color(169, 169, 169));
					btnTorles.setBounds(330, 293, 94, 30);
					contentPanel.add(btnTorles);
				}
				{
					JButton btnModositas = new JButton("M\u00F3dos\u00EDt\u00E1s");
					btnModositas.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							int db=0, jel=0, x=0;
							for (x = 0; x <ftm.getRowCount(); x++) 
								if((Boolean)ftm.getValueAt(x, 0)) {db++; jel=x;}
							if(db==0) uzenet("Kérem válasszon ki egy rekordot!");
							if(db>1) uzenet("Maximum egy rekordot lehet kiválasztani!");
							if(db==1) {								
								if(f.getClass().getName().equals("Program.SQLite")) {
									FilmModosit mt = new FilmModosit(null, RTM(jel,1), RTM(jel,2), RTM(jel,3),  RTM(jel,4),  RTM(jel,5),0,ftm,jel);
									mt.setVisible(true);
								}
								else if(f.getClass().getName().equals("Program.CSV")) {
									FilmModosit mt = new FilmModosit(null, RTM(jel,1), RTM(jel,2), RTM(jel,3),  RTM(jel,4),  RTM(jel,5),1,ftm,jel);
									mt.setVisible(true);
								}
							}
						}
					});
					
					
					btnModositas.setFont(new Font("Tahoma", Font.PLAIN, 12));
					btnModositas.setBackground(new Color(169, 169, 169));
					btnModositas.setBounds(122, 293, 94, 30);
					contentPanel.add(btnModositas);
				}
				{
					JButton btnMentes = new JButton("Ment\u00E9s");
					btnMentes.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							try {
								if(f.getClass().getName().equals("Program.SQLite")) {
									dbm.kapcs();
									dbm.fajlMegnyit(TipusValaszt.getFile() + "/Film.txt");
									dbm.filmHozzaad();
									dbm.fajlBezar();
									dbm.leKapcs();
									uzenet("Sikeres fájlkiírás!\n " + TipusValaszt.getFile() + "/Film.txt");
								}
								else if(f.getClass().getName().equals("Program.CSV")) {
									dbm.fajlMegnyit(TipusValaszt.getFile() + "/Film.txt");
									dbm.csvToTXTFilm();
									dbm.fajlBezar();
									uzenet("Sikeres fájlkiírás!\n " + TipusValaszt.getFile() + "/Film.txt");
								}
							} catch (Exception e2) {
								uzenet("A fájlkíírás sikertelen\nHiba: "+e2.getMessage());
							}
						}
					});
					
					
					btnMentes.setFont(new Font("Tahoma", Font.PLAIN, 12));
					btnMentes.setBackground(new Color(169, 169, 169));
					btnMentes.setBounds(226, 293, 94, 30);
					contentPanel.add(btnMentes);
				}
				{
					JButton btnBetoltes = new JButton("Bet\u00F6lt\u00E9s");
					btnBetoltes.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if(f.getClass().getName().equals("Program.SQLite")) {
								dbm.filmBetolt(TipusValaszt.getFile() + "/Film.txt");
								dispose();
							}
							else if(f.getClass().getName().equals("Program.CSV")) {
								dbm.csvtxtFilm(ftm);
								dispose();
							}							
						}
					});
					
					
					btnBetoltes.setFont(new Font("Tahoma", Font.PLAIN, 12));
					btnBetoltes.setBackground(new Color(169, 169, 169));
					btnBetoltes.setBounds(20, 293, 94, 30);
					contentPanel.add(btnBetoltes);
				}
				TableRowSorter<FilmTM> trs =
						(TableRowSorter<FilmTM>)tabla.getRowSorter();
				trs.setSortable(0, false);

			}
		}
		
		
	}
	
	public String RTM(int row,int col) {
		return ftm.getValueAt(row, col).toString();
	}
	public void uzenet(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Üzenet", 1);
	}

}
