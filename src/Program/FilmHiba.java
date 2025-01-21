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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class FilmHiba extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private HFilmTM eftm;
	DBMetodusok dbm = new DBMetodusok();
	@SuppressWarnings("unused")
	private Ell c = new Ell();
	
	
	public FilmHiba(JFrame f, HFilmTM eft) {
		super(f,"Filmhibák listája", true);
		setUndecorated(true);
		eftm = eft;
		setBounds(100, 100, 670, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JButton btnBezar = new JButton("Bez\u00E1r");
		btnBezar.setBackground(new Color(169, 169, 169));
		btnBezar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnBezar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); 
			}
		});
		
		
		btnBezar.setBounds(575, 252, 85, 24);
		contentPanel.add(btnBezar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 650, 232);
		contentPanel.add(scrollPane);
		
		table = new JTable(eft);
		scrollPane.setViewportView(table);
		
		TableColumn tc = null;
		for (int i = 0; i < 6; i++) {
		tc = table.getColumnModel().getColumn(i);
		if (i==0 || i==1 ) tc.setPreferredWidth(20);
		else if (i==4) tc.setPreferredWidth(180);
		else {tc.setPreferredWidth(100);}
		}
		
		table.setAutoCreateRowSorter(true);
		
		JButton btnMentes = new JButton("Ment\u00E9s");
		btnMentes.setBackground(new Color(169, 169, 169));
		btnMentes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					dbm.fajlMegnyit(TipusValaszt.getFile() + "/Filmhiba.txt");
					dbm.hibatoTXTFilm(eftm);
					dbm.fajlBezar();
					SM("Sikeres fájlkiírás!\n "+ TipusValaszt.getFile() + "/Film.txt");
				} catch (Exception e2) {
					SM("Sikertelen fájlkiírás!\nHiba: "+e2.getMessage());
				}
			}
		});
		
		
		
		btnMentes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnMentes.setBounds(10, 252, 85, 24);
		contentPanel.add(btnMentes);
		
		JLabel lblHibasFilmek = new JLabel("Hib\u00E1s filmek");
		lblHibasFilmek.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblHibasFilmek.setBounds(264, 252, 137, 24);
		contentPanel.add(lblHibasFilmek);
		@SuppressWarnings("unchecked")
		TableRowSorter<MindenTM> trs = (TableRowSorter<MindenTM>)table.getRowSorter();
		trs.setSortable(0, false);
	}
	
	public void SM(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Üzenet", 2);
	}
}
