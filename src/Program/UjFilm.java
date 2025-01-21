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
import java.awt.event.ActionEvent;


@SuppressWarnings("serial")
public class UjFilm extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField filmid;
	private JTextField cim;
	private JTextField rendezo;
	private JTextField mdatum;
	private JTextField vevoid;
	DBMetodusok dbm = new DBMetodusok();

	
	
	public UjFilm(JFrame f) {
		super(f,"Új film listához adása", true);
		setTitle("\u00DAj film list\u00E1hoz ad\u00E1sa");
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
			JLabel lblCim = new JLabel("C\u00EDm:");
			lblCim.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblCim.setBounds(10, 46, 122, 26);
			contentPanel.add(lblCim);
		}
		{
			JLabel lblRendezo = new JLabel("Rendez\u0151:");
			lblRendezo.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblRendezo.setBounds(10, 82, 122, 26);
			contentPanel.add(lblRendezo);
		}
		{
			JLabel lblMdatum = new JLabel("Megjelen\u00E9s d\u00E1tuma:");
			lblMdatum.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblMdatum.setBounds(10, 118, 137, 26);
			contentPanel.add(lblMdatum);
		}
		{
			JLabel lblVevoID = new JLabel("Hozz\u00E1rendelt Vev\u0151 ID:");
			lblVevoID.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblVevoID.setBounds(10, 154, 149, 26);
			contentPanel.add(lblVevoID);
		}
		{
			filmid = new JTextField();
			filmid.setBounds(60, 13, 107, 25);
			contentPanel.add(filmid);
			filmid.setColumns(10);
		}
		{
			cim = new JTextField();
			cim.setColumns(10);
			cim.setBounds(60, 46, 233, 25);
			contentPanel.add(cim);
		}
		{
			rendezo = new JTextField();
			rendezo.setColumns(10);
			rendezo.setBounds(80, 82, 233, 25);
			contentPanel.add(rendezo);
		}
		{
			mdatum = new JTextField();
			mdatum.setColumns(10);
			mdatum.setBounds(143, 121, 107, 25);
			contentPanel.add(mdatum);
		}
		{
			
			// Adatfelvétel, mezõellenõrzés, ha megfelelnek a feltételek, felvétel
			JButton btnFelvesz = new JButton("Felv\u00E9tel");
			btnFelvesz.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(!filledTF(filmid)) uzenet("A kód mezõ nem lehet üres!");
					else if(!szamEll(filmid)) uzenet("A kód mezõ nem számot tartalmaz!");
					else if(!filledTF(cim)) uzenet("A cím mezõ nem lehet üres!");
					else if(!filledTF(rendezo)) uzenet("A szerzõ mezõ nem lehet üres!");
					else if(!filledTF(mdatum)) uzenet("A dátum mezõ nem lehet üres!");
					else if(!datumEll(mdatum)) uzenet("A dátum nem megfelelõ formátumban lett megadva!");
					else if(!filledTF(vevoid)) uzenet("A vevõ kód mezõje nem lehet üres!");
					else if(!szamEll(vevoid)) uzenet("A vevõ kód mezõje nem számot tartalmaz!");
					else {
						dbm.addFilm(RTF(filmid), RTF(cim), RTF(rendezo), RTF(mdatum), RTF(vevoid));
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
			vevoid = new JTextField();
			vevoid.setColumns(10);
			vevoid.setBounds(153, 157, 107, 25);
			contentPanel.add(vevoid);
		}
	}
	
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		String s = RTF(jtf);
		Date tesztdátum = null;
		try {
			tesztdátum = sdf.parse(s);
		} catch (ParseException e) {return false;}
		if(sdf.format(tesztdátum).equals(s)) return true;
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
