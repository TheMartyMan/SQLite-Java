package Program;

import java.awt.BorderLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.Date;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class UjVevo extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField vevoid, nev, tel, lakcim, szulido;
	DBMetodusok dbm = new DBMetodusok();

	
	
	public UjVevo(JFrame f) {
		super(f,"Új vevõ felvétele", true);
		setTitle("\u00DAj vev\u0151 list\u00E1hoz ad\u00E1sa");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(192, 192, 192));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblKod = new JLabel("K\u00F3d:");
			lblKod.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblKod.setBounds(10, 10, 122, 26);
			contentPanel.add(lblKod);
		}
		{
			JLabel lblNev = new JLabel("N\u00E9v:");
			lblNev.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblNev.setBounds(10, 46, 122, 26);
			contentPanel.add(lblNev);
		}
		{
			JLabel lblTelefon = new JLabel("Telefonsz\u00E1m:");
			lblTelefon.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblTelefon.setBounds(10, 82, 122, 26);
			contentPanel.add(lblTelefon);
		}
		{
			JLabel lblLakcim = new JLabel("Lakc\u00EDm:");
			lblLakcim.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblLakcim.setBounds(10, 118, 122, 26);
			contentPanel.add(lblLakcim);
		}
		{
			JLabel lblSzulido = new JLabel("Sz\u00FClet\u00E9si id\u0151:");
			lblSzulido.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblSzulido.setBounds(10, 154, 122, 26);
			contentPanel.add(lblSzulido);
		}
		{
			vevoid = new JTextField();
			vevoid.setBounds(142, 13, 107, 25);
			contentPanel.add(vevoid);
			vevoid.setColumns(10);
		}
		{
			nev = new JTextField();
			nev.setColumns(10);
			nev.setBounds(142, 46, 233, 25);
			contentPanel.add(nev);
		}
		{
			tel = new JTextField();
			tel.setColumns(10);
			tel.setBounds(142, 82, 160, 25);
			contentPanel.add(tel);
		}
		{
			lakcim = new JTextField();
			lakcim.setColumns(10);
			lakcim.setBounds(142, 118, 233, 25);
			contentPanel.add(lakcim);
		}
		{
			
			// Adatfelvétel, mezõellenõrzés, ha megfelelnek a feltételek, felvétel
			JButton btnFelvesz = new JButton("Felv\u00E9tel");
			btnFelvesz.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(!filledTF(vevoid)) uzenet("A kód mezõ nem lehet üres!");
					else if(!szamEll(vevoid)) uzenet("A kód mezõ nem szám!");
					else if(!filledTF(nev)) uzenet("A név mezõ nem lehet üres!");
					else if(!filledTF(tel)) uzenet("A telefonszám mezõ nem lehet üres!");
					else if(!filledTF(lakcim)) uzenet("A cím mezõ nem lehet üres!");
					else if(!filledTF(szulido)) uzenet("A születési idõ mezõ nem lehet üres!");
					else if(!datumEll(szulido)) uzenet("A születési idõ formátuma helytelen!");
					else {
					try {
						dbm.addVevo(RTF(vevoid), RTF(nev), RTF(tel), RTF(lakcim), RTF(szulido));
					} catch (IOException e1) {
						e1.printStackTrace();
						uzenet("Sikertelen adatfelvétel!\n");
					}
					dispose();
					}
				}
			});
			
			
			btnFelvesz.setBackground(new Color(169, 169, 169));
			btnFelvesz.setFont(new Font("Tahoma", Font.BOLD, 16));
			btnFelvesz.setBounds(245, 199, 130, 40);
			contentPanel.add(btnFelvesz);
		}
		{
			szulido = new JTextField();
			szulido.setColumns(10);
			szulido.setBounds(142, 154, 107, 25);
			contentPanel.add(szulido);
		}
	}
	
	
	// Üzenet, üresség-dátum-szám ellenörzõ metódusok (Több osztálynál is ezeket használom)
	public void uzenet(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Hibaüzenet", 2);
	}
	
	public String RTF(JTextField jtf) {
		return jtf.getText();
	}
	
	public boolean filledTF(JTextField jtf) {
		String s = RTF(jtf);
		if(s.length()>0) return true;
		return false;
	}
	
	public boolean datumEll(JTextField jtf) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.mm.dd");
		String s = RTF(jtf);
		Date tesztdatum = null;
		try {
			tesztdatum = sdf.parse(s);
		} catch (ParseException e) {return false;}
		if(sdf.format(tesztdatum).equals(s)) return true;
		else return false;
	}
	
	public boolean szamEll(JTextField jtf) {
		String s = RTF(jtf);
		try {
			Integer.parseInt(s); return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
