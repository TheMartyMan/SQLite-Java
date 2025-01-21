package Program;

import java.awt.BorderLayout;


import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class VevoModosit extends JDialog {

	private final JPanel contentPanel = new JPanel();
	DBMetodusok dbm = new DBMetodusok();
	private JTextField vevoid, nev, tel, lakcim, szulido, nev2, tel2, lakcim2, szulido2;
	private Ell c = new Ell();
	private JLabel lblJelenlegiAdat;
	private JLabel lbljUjAdat;
	
	
	public VevoModosit(JDialog d, String bevevoid, String benev, String betel, String belakcim,
			String beszulido, int n, VevoTM vtm, int jel) {
		super(d, "Vevõ adatok módosítása", true);
		setBounds(100, 100, 470, 245);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(192, 192, 192));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		
		JLabel lblVevoID = new JLabel("K\u00F3d:");
		lblVevoID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblVevoID.setBounds(10, 38, 45, 13);
		contentPanel.add(lblVevoID);
		
		
		JLabel lblNev = new JLabel("N\u00E9v:");
		lblNev.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNev.setBounds(10, 61, 45, 13);
		contentPanel.add(lblNev);
		
		
		JLabel lblTelefonszam = new JLabel("Telefonsz\u00E1m:");
		lblTelefonszam.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTelefonszam.setBounds(10, 84, 95, 13);
		contentPanel.add(lblTelefonszam);
		
		
		JLabel lblLakcim = new JLabel("Lakcim:");
		lblLakcim.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLakcim.setBounds(10, 107, 72, 13);
		contentPanel.add(lblLakcim);
		
		
		JLabel lblSzulido = new JLabel("Sz\u00FClet\u00E9si id\u0151:");
		lblSzulido.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSzulido.setBounds(10, 130, 95, 13);
		contentPanel.add(lblSzulido);
		
		
		vevoid = new JTextField(bevevoid);
		vevoid.setFont(new Font("Tahoma", Font.PLAIN, 14));
		vevoid.setHorizontalAlignment(SwingConstants.CENTER);
		vevoid.setEditable(false);
		vevoid.setBounds(50, 35, 135, 19);
		contentPanel.add(vevoid);
		vevoid.setColumns(10);
		
		
		nev = new JTextField(benev);
		nev.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nev.setHorizontalAlignment(SwingConstants.CENTER);
		nev.setEditable(false);
		nev.setColumns(10);
		nev.setBounds(50, 58, 164, 19);
		contentPanel.add(nev);
		
		
		tel = new JTextField(betel);
		tel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tel.setHorizontalAlignment(SwingConstants.CENTER);
		tel.setEditable(false);
		tel.setColumns(10);
		tel.setBounds(103, 81, 149, 19);
		contentPanel.add(tel);
		
		
		lakcim = new JTextField(belakcim);
		lakcim.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lakcim.setHorizontalAlignment(SwingConstants.CENTER);
		lakcim.setEditable(false);
		lakcim.setColumns(10);
		lakcim.setBounds(72, 104, 164, 19);
		contentPanel.add(lakcim);
		
		
		szulido = new JTextField(beszulido);
		szulido.setFont(new Font("Tahoma", Font.PLAIN, 14));
		szulido.setHorizontalAlignment(SwingConstants.CENTER);
		szulido.setEditable(false);
		szulido.setColumns(10);
		szulido.setBounds(115, 127, 135, 19);
		contentPanel.add(szulido);
		
		
		nev2 = new JTextField();
		nev2.setBounds(263, 60, 168, 19);
		contentPanel.add(nev2);
		nev2.setColumns(10);
		
		
		tel2 = new JTextField();
		tel2.setColumns(10);
		tel2.setBounds(263, 83, 168, 19);
		contentPanel.add(tel2);
		
		
		lakcim2 = new JTextField();
		lakcim2.setColumns(10);
		lakcim2.setBounds(263, 106, 168, 19);
		contentPanel.add(lakcim2);
		
		
		szulido2 = new JTextField();
		szulido2.setColumns(10);
		szulido2.setBounds(263, 129, 168, 19);
		contentPanel.add(szulido2);
		
		
		JButton btnModosit = new JButton("M\u00F3dos\u00EDt\u00E1s!");
		btnModosit.setBackground(new Color(169, 169, 169));
		btnModosit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(n==0) {
					dbm.kapcs();
					if(!datumEll(szulido2)) uzenet("A születési idõ formátuma helytelen!");
					else {
					dbm.updateVevo(RTF(vevoid), RTF2(nev2, nev), RTF2(tel2, tel), RTF2(lakcim2, lakcim),
							RTF2(szulido2, szulido));
					dbm.leKapcs();
					dispose();
					}
				}
				
				else if(n==1) {
					if(pcModosit()>0) {
						dbm.csvVevoMod(RTF(vevoid), RTF2(nev2, nev), RTF2(tel2, tel), RTF2(lakcim2, lakcim),
								RTF2(szulido2, szulido), vtm, jel);
						dispose();
					}
				}
			}
		});
		
		
		btnModosit.setForeground(new Color(0, 0, 0));
		btnModosit.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnModosit.setBounds(170, 159, 107, 39);
		contentPanel.add(btnModosit);
		
		lblJelenlegiAdat = new JLabel("Jelenlegi adat");
		lblJelenlegiAdat.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblJelenlegiAdat.setBounds(10, 7, 183, 21);
		contentPanel.add(lblJelenlegiAdat);
		
		lbljUjAdat = new JLabel("\u00DAj adat");
		lbljUjAdat.setFont(new Font("Tahoma", Font.BOLD, 18));
		lbljUjAdat.setBounds(305, 30, 183, 21);
		contentPanel.add(lbljUjAdat);
	}
	
	public String RTF(JTextField jtf) {
		return jtf.getText();
	}
	
	public String RTF2(JTextField jtf2, JTextField jtf) {
		if(jtf2.getText().length()==0)
			return jtf.getText();
		return jtf2.getText();
	}
	
	public boolean datumEll(JTextField jtf) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		String s = RTF(jtf);
		Date tesztdatum = null;
		
		try {
			tesztdatum = sdf.parse(s);
			
		} catch (ParseException e) {return false;}
		
		if(sdf.format(tesztdatum).equals(s)) return true;
		else return false;
	}
	
	public void uzenet(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Hibaüzenet", 2);
	}

	public int pcModosit() {
		int pc = 0;
		if(c.uresE(vevoid)) pc++;
		if(c.uresE(nev2)) pc++;
		if(c.uresE(tel2)) pc++;
		if(c.uresE(lakcim2)) pc++;
		if(c.uresE(szulido2)) pc++;
		return pc;
	}
}
