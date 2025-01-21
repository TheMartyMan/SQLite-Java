package Program;

import java.awt.BorderLayout;


import javax.swing.JButton;
import javax.swing.JDialog;

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
public class Szuro3Lista extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private Szuro3TM sz3tm;
	DBMetodusok dbm = new DBMetodusok();

	@SuppressWarnings("unchecked")
	public Szuro3Lista(Szuro szuro,Szuro3TM betm) {
		super(szuro, "Filmek listája", true);
		sz3tm =betm;
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
				table = new JTable(sz3tm);
				
				scrollPane.setViewportView(table);
				
				TableColumn tc = null;
				for (int i = 0; i < 6; i++) {
				tc = table.getColumnModel().getColumn(i);
				if (i==0 || i==1 ) tc.setPreferredWidth(20);
				else if (i==2 ||i==3) tc.setPreferredWidth(180);
				else if (i==4 ) tc.setPreferredWidth(80);
				else {tc.setPreferredWidth(100);}
				}
				
				table.setAutoCreateRowSorter(true);
				TableRowSorter<FilmTM> trs =
						(TableRowSorter<FilmTM>)table.getRowSorter();
				trs.setSortable(0, false);

			}
		}
		
		
	}
	
	public void uzenet(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Üzenet", 2);
	}

}
