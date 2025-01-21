package Program;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JPasswordField;
import java.awt.Color;

@SuppressWarnings("serial")
public class Bejelentkezes extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private int db=0;
	DBMetodusok dbm = new DBMetodusok();
	private JTextField felhasznalonev;
	private JPasswordField jelszo;
	private static String curUser;


	public static void main(String[] args) {
		try {
			Bejelentkezes dialog = new Bejelentkezes();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public Bejelentkezes() {
		setBounds(100, 100, 575, 395);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(192, 192, 192));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JButton btnBejelentkezes = new JButton("Bejelentkez\u00E9s");
		btnBejelentkezes.setBounds(181, 269, 205, 60);
		btnBejelentkezes.setBackground(new Color(169, 169, 169));
		btnBejelentkezes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				curUser = felhasznalonev.getText();
				dbm.driverReg1();
				int pc = dbm.bejelentkezes(RTF(felhasznalonev), RTF(jelszo));
				if(pc==1) {
					TipusValaszt choose = new TipusValaszt(Bejelentkezes.this);
					choose.setVisible(true);
					dispose();
				}
				else if (db<4) {
					db++;
					uzenet("A felhasználónév vagy a jelszó helytelen!\nMég "+(5-db)+" próbálkozása maradt.");
				}
				else if (db<5) {
						db++;
						uzenet("Sikertelen bejelentkezés!\nA program most kilép.");
						System.exit(0);
				}
			}
		});
		contentPanel.setLayout(null);
		
		
		btnBejelentkezes.setFont(new Font("Tahoma", Font.BOLD, 16));
		contentPanel.add(btnBejelentkezes);
		
		JLabel lblFelhasznalonev = new JLabel("Felhaszn\u00E1l\u00F3n\u00E9v");
		lblFelhasznalonev.setBounds(181, 45, 205, 32);
		lblFelhasznalonev.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFelhasznalonev.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(lblFelhasznalonev);
		
		JLabel lblJelszo = new JLabel("Jelsz\u00F3");
		lblJelszo.setBounds(181, 133, 205, 32);
		lblJelszo.setHorizontalAlignment(SwingConstants.CENTER);
		lblJelszo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPanel.add(lblJelszo);
		
		felhasznalonev = new JTextField();
		felhasznalonev.setBounds(164, 77, 247, 26);
		felhasznalonev.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(felhasznalonev);
		felhasznalonev.setColumns(10);
		
		// Jelszó láthatóság
				JCheckBox chckbxLathatosag = new JCheckBox("Legyen l\u00E1that\u00F3 a jelsz\u00F3?");
				chckbxLathatosag.setBounds(20, 163, 140, 21);
				chckbxLathatosag.setForeground(Color.WHITE);
				chckbxLathatosag.setFont(new Font("Tahoma", Font.PLAIN, 10));
				chckbxLathatosag.setBackground(Color.BLACK);
				chckbxLathatosag.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (chckbxLathatosag.isSelected()) {
							jelszo.setEchoChar((char)0);
						} else {
							jelszo.setEchoChar('•');
						}
					}
				});
		
		contentPanel.add(chckbxLathatosag);
		
		jelszo = new JPasswordField();
		jelszo.setBounds(166, 163, 246, 22);
		jelszo.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(jelszo);
		
		JButton btnKilepes = new JButton("Kilépés");
		btnKilepes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(null, "Biztosan ki akar lépni?", "Kilépés", JOptionPane.YES_NO_OPTION)==0) {
					dispose();	
				}
			}
		});
		
		btnKilepes.setBounds(466, 327, 85, 21);
		contentPanel.add(btnKilepes);
		
		setTitle("Bejelentkezés");
	}
		
	
	
	public String RTF(JTextField jtf) {
		return jtf.getText();
	}
	public void uzenet(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Üzenet", 2);
	}


	public static String getUser() {
		return curUser;
	}
}
