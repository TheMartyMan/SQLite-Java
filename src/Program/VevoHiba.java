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
public class VevoHiba extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable tabla;
	private HVevoTM hvtm;
	DBMetodusok dbm = new DBMetodusok();
	@SuppressWarnings("unused")
	private Ell c = new Ell();
	
	
	@SuppressWarnings("unchecked")
	public VevoHiba(JFrame f, HVevoTM ett) {
		super(f,"Vevõhibák listája", true);
		setUndecorated(true);
		hvtm = ett;
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
		
		tabla = new JTable(ett);
		scrollPane.setViewportView(tabla);
		
		TableColumn tc = null;
		for (int i = 0; i < 6; i++) {
		tc = tabla.getColumnModel().getColumn(i);
		if (i==0 || i==1 ) tc.setPreferredWidth(20);
		else if (i==4) tc.setPreferredWidth(180);
		else {tc.setPreferredWidth(100);}
		}
		
		tabla.setAutoCreateRowSorter(true);
		
		JButton btnMentes = new JButton("Ment\u00E9s");
		btnMentes.setBackground(new Color(169, 169, 169));
		btnMentes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					dbm.fajlMegnyit(TipusValaszt.getFile() + "/Vevohiba.txt");
					dbm.hibatoTXTVevo(hvtm);
					dbm.fajlBezar();
					uzenet("Sikeres fájlkiírás!"+ TipusValaszt.getFile() + "/Vevohiba.txt");
				} catch (Exception e2) {
					uzenet("A fájlkiírás sikertelen!\nHiba: "+e2.getMessage());
				}
			}
		});
		btnMentes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnMentes.setBounds(10, 252, 85, 24);
		contentPanel.add(btnMentes);
		
		JLabel lblHibasVevok = new JLabel("Vev\u0151hib\u00E1k");
		lblHibasVevok.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblHibasVevok.setBounds(278, 251, 137, 24);
		contentPanel.add(lblHibasVevok);
		TableRowSorter<MindenTM> trs = (TableRowSorter<MindenTM>)tabla.getRowSorter();
		trs.setSortable(0, false);
	}
	
	public void uzenet(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Üzenet", 2);
	}
}
