package Program;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class UjAdat extends JDialog {

	private final JPanel contentPanel = new JPanel();

	
	public UjAdat(JFrame f) {
		setTitle("Új adat felvitele");
		setBounds(100, 100, 450, 300);
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
			btnBezar.setBounds(327, 217, 99, 36);
			contentPanel.add(btnBezar);
		}
		{
			JButton btnVevo = new JButton("Vevõ");
			btnVevo.setBackground(new Color(169, 169, 169));
			btnVevo.setFont(new Font("Tahoma", Font.BOLD, 14));
			btnVevo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(f.getClass().getName().equals("Program.SQLite")) {
						UjVevo nt = new UjVevo(null);
						nt.setVisible(true);
					}
					else if(f.getClass().getName().equals("Program.CSV")) {
						UjVevo nt = new UjVevo(null);
						nt.setVisible(true);
					}
				}
			});
			
			
			btnVevo.setBounds(157, 158, 115, 47);
			contentPanel.add(btnVevo);
		}
		{
			JButton btnFilm = new JButton("Film");
			btnFilm.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(f.getClass().getName().equals("Program.SQLite")) {
						UjFilm nk = new UjFilm(null);
						nk.setVisible(true);
					}
					else if(f.getClass().getName().equals("Program.CSV")) {
						UjFilm nk = new UjFilm(null);
						nk.setVisible(true);
					}
				}
			});
			btnFilm.setFont(new Font("Tahoma", Font.BOLD, 14));
			btnFilm.setBackground(new Color(169, 169, 169));
			btnFilm.setBounds(157, 101, 115, 47);
			contentPanel.add(btnFilm);
		}
		{
			JLabel lblValaszt = new JLabel("K\u00E9rem v\u00E1lasszon!");
			lblValaszt.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblValaszt.setHorizontalAlignment(SwingConstants.CENTER);
			lblValaszt.setBackground(new Color(192, 192, 192));
			lblValaszt.setBounds(31, 10, 156, 47);
			contentPanel.add(lblValaszt);
		}
		{
			JLabel lblMilyenTipus = new JLabel("Milyen tipus\u00FA \u00FAj adatot szeretne felvinni?");
			lblMilyenTipus.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblMilyenTipus.setBackground(new Color(192, 192, 192));
			lblMilyenTipus.setHorizontalAlignment(SwingConstants.CENTER);
			lblMilyenTipus.setBounds(22, 53, 288, 50);
			contentPanel.add(lblMilyenTipus);
		}
	}

}
