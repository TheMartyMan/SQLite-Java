package Program;

import java.awt.BorderLayout;


import javax.swing.JButton;
import javax.swing.JDialog;
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
public class Szuro2Lista extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private Szuro2TM sz2tm;


	@SuppressWarnings("unchecked")
	public Szuro2Lista(Szuro szuro,Szuro2TM betm) {
		super(szuro, "Vevõk listája", true);
		setTitle("Vev\u0151k list\u00E1ja");
		sz2tm =betm;
		setBounds(100, 100, 670, 360);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JButton btnBezar = new JButton("Bez\u00E1r");
			btnBezar.setBackground(new Color(169, 169, 169));
			btnBezar.setFont(new Font("Tahoma", Font.PLAIN, 12));
			btnBezar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnBezar.setBounds(542, 280, 104, 33);
			contentPanel.add(btnBezar);
		}
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 636, 260);
		contentPanel.add(scrollPane);
		
		table = new JTable(sz2tm);
		scrollPane.setViewportView(table);
		
		TableColumn tc = null;
		for (int i = 0; i < 6; i++) {
		tc = table.getColumnModel().getColumn(i);
		if (i==0 || i==1 ) tc.setPreferredWidth(20);
		else if (i==4) tc.setPreferredWidth(180);
		else {tc.setPreferredWidth(100);}
		}
		
		table.setAutoCreateRowSorter(true);
		TableRowSorter<VevoTM> trs =
		(TableRowSorter<VevoTM>)table.getRowSorter();
		trs.setSortable(0, false);

		
	}

}
