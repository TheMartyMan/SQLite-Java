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

@SuppressWarnings("serial")
public class MindentListaz extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private MindenTM atm;
	DBMetodusok dbm = new DBMetodusok();
	
	
	public MindentListaz(JFrame f, MindenTM betm) {
		super(f,"Összes adat", true);
		setTitle("Összes adat");
		atm = betm;
		
		setBounds(100, 100, 1100, 320);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JButton btnClose = new JButton("Bez\u00E1r");
		btnClose.setBackground(new Color(169, 169, 169));
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); 
			}
		});
		
		
		
		btnClose.setBounds(549, 252, 85, 24);
		contentPanel.add(btnClose);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 1066, 232);
		contentPanel.add(scrollPane);
		
		table = new JTable(atm);
		scrollPane.setViewportView(table);
		
		TableColumn tc = null;
		for (int i = 0; i < 10; i++) {
		tc = table.getColumnModel().getColumn(i);
		if (i==0 || i==1|| i==5 ) tc.setPreferredWidth(10);
		else if (i==4 || i==9 ) tc.setPreferredWidth(60);
		else if (i==2 || i==6) tc.setPreferredWidth(120);
		else if (i==8) tc.setPreferredWidth(140);
		else {tc.setPreferredWidth(100);}
		}
		
		
		table.setAutoCreateRowSorter(true);
		
		JButton btnSave = new JButton("Ment\u00E9s");
		btnSave.setBackground(new Color(169, 169, 169));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					dbm.kapcs();
					dbm.fajlMegnyit(TipusValaszt.getFile() + "/minden.txt");
					dbm.addOsszes();
					dbm.fajlBezar();
					dbm.leKapcs();
					uzenet("Fájlkiírás sikeres!\n " + TipusValaszt.getFile() + "/minden.txt");
				} catch (Exception e2) {
					uzenet("A fájlkiírás sikertelen!\nHiba: "+e2.getMessage());
				}
			}
		});
		
		
		
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnSave.setBounds(454, 252, 85, 24);
		contentPanel.add(btnSave);
		
		
		@SuppressWarnings("unchecked")
		TableRowSorter<MindenTM> trs = (TableRowSorter<MindenTM>)table.getRowSorter();
		trs.setSortable(0, false);
	}
	
	
	
	public void uzenet(String msg) {
		JOptionPane.showMessageDialog(null, msg, "", 2);
	}
}
