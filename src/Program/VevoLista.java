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

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.Color;

@SuppressWarnings("serial")
public class VevoLista extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable tabla;
	private VevoTM ttm;
	DBMetodusok dbm = new DBMetodusok();
	private Ell c = new Ell();

	@SuppressWarnings("unchecked")
	public VevoLista(JFrame f,VevoTM betm) {
		super(f, "Vevõk listája", true);
		ttm =betm;
		setBounds(100, 100, 670, 360);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JButton btnNewButton = new JButton("Bez\u00E1r");
			btnNewButton.setBackground(new Color(169, 169, 169));
			btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			
			
			btnNewButton.setBounds(542, 280, 104, 33);
			contentPanel.add(btnNewButton);
		}
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 636, 260);
		contentPanel.add(scrollPane);
		
		tabla = new JTable(ttm);
		scrollPane.setViewportView(tabla);
		
		TableColumn tc = null;
		for (int i = 0; i < 6; i++) {
		tc = tabla.getColumnModel().getColumn(i);
		
		if (i==0 || i==1 ) tc.setPreferredWidth(20);
		
		else if (i==4) tc.setPreferredWidth(180);
		
		else {tc.setPreferredWidth(100);}
		}
		
		tabla.setAutoCreateRowSorter(true);
		
		JButton btnTorol = new JButton("T\u00F6rl\u00E9s");
		btnTorol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(f.getClass().getName().equals("Program.SQLite")) {
					int db=0,jel=0,x=0;
					
					for (x = 0; x < ttm.getRowCount(); x++)
						if((Boolean)ttm.getValueAt(x, 0)) {db++; jel=x;}
					
					if(db==0) uzenet("Kérem jelöljön ki egy rekordot!");
					
					if(db>1) uzenet("Egyszerre csak egy rekord törölhetõ!");
					
					if(db==1) {
						dbm.kapcs();
						dbm.vevoTorol(RTM(jel,1));
						ttm.removeRow(jel);
						dbm.leKapcs();
						uzenet ("Sikeres törlés!");
					}
				}
				else if(f.getClass().getName().equals("Program.CSV")) {
					int db=0,jel=0,x=0;
					
					for (x = 0; x < ttm.getRowCount(); x++)
						if((Boolean)ttm.getValueAt(x, 0)) {db++; jel=x;}
					
					if(db==0) uzenet("Kérem jelöljön ki egy rekordot!");
					
					if(db>1) uzenet("Egyszerre csak egy rekord törölhetõ!");
					
					if(db==1) {
						ttm.removeRow(jel);
						dbm.csvVevoTorol(ttm);
						dispose();
						c.uzenet("Sikeres törlés!", 1);
					}
				}
			}
		});
		
		
		btnTorol.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnTorol.setBackground(new Color(169, 169, 169));
		btnTorol.setBounds(369, 280, 104, 33);
		contentPanel.add(btnTorol);
		
		JButton btnModosts = new JButton("Modos\u00EDt\u00E1s");
		btnModosts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int db=0, jel=0, x=0;
				for (x = 0; x <ttm.getRowCount(); x++) 
					if((Boolean)ttm.getValueAt(x, 0)) {db++; jel=x;}
				
				if(db==0) uzenet("Kérem válasszon ki egy rekordot!");
				
				if(db>1) uzenet("Maximum egy rekordot lehet kiválasztani!");
				
				if(db==1) {
					if(f.getClass().getName().equals("Program.SQLite")) {
						VevoModosit mt = new VevoModosit(null, RTM(jel,1), RTM(jel,2), RTM(jel,3), 
								RTM(jel,4),  RTM(jel,5),0,ttm,jel);
						mt.setVisible(true);
					}
					
					else if(f.getClass().getName().equals("Program.CSV")) {
						VevoModosit mt = new VevoModosit(null, RTM(jel,1), RTM(jel,2), RTM(jel,3), 
								RTM(jel,4),  RTM(jel,5),1,ttm,jel);
						mt.setVisible(true);
					}
				}
			}
		});
		
		
		btnModosts.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnModosts.setBackground(new Color(169, 169, 169));
		btnModosts.setBounds(134, 280, 104, 33);
		contentPanel.add(btnModosts);
		
		JButton btnMents = new JButton("Ment\u00E9s");
		btnMents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(f.getClass().getName().equals("Program.SQLite")) {
						dbm.kapcs();
						dbm.fajlMegnyit(TipusValaszt.getFile() + "/Vevo.txt");
						dbm.vevoHozzaad();
						dbm.fajlBezar();
						dbm.leKapcs();
						uzenet("Sikeres fájlkiírás!\n " + TipusValaszt.getFile() + "/Vevo.txt");
					}
					else if(f.getClass().getName().equals("Program.CSV")) {
						dbm.fajlMegnyit(TipusValaszt.getFile() + "/Vevo.txt");
						dbm.csvToTXTVevo();
						dbm.fajlBezar();
						uzenet("Sikeres fájlkiírás!\n " + TipusValaszt.getFile() + "/Vevo.txt");
					}
				} catch (Exception e2) {
					uzenet("A fájlkíírás sikertelen volt!\nHiba: "+e2.getMessage());
				}
			}
		});
		
		
		btnMents.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnMents.setBackground(new Color(169, 169, 169));
		btnMents.setBounds(255, 280, 104, 33);
		contentPanel.add(btnMents);
		
		JButton btnBetlts = new JButton("Bet\u00F6lt\u00E9s");
		btnBetlts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			if(f.getClass().getName().equals("Program.SQLite")) {
				dbm.vevoBetolt(TipusValaszt.getFile() + "/Vevo.txt");
				dispose();
			}
			else if(f.getClass().getName().equals("Program.CSV")) {
				dbm.csvtxtVevo(ttm);
				dispose();
			}
			}
		});
		
		
		btnBetlts.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnBetlts.setBackground(new Color(169, 169, 169));
		btnBetlts.setBounds(20, 280, 104, 33);
		contentPanel.add(btnBetlts);
		TableRowSorter<VevoTM> trs =
		(TableRowSorter<VevoTM>)tabla.getRowSorter();
		trs.setSortable(0, false);

		
	}
	
	public String RTM(int row,int col) {
		return ttm.getValueAt(row, col).toString();
	}
	
	public void uzenet(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Üzenet", 2);
	}

}
