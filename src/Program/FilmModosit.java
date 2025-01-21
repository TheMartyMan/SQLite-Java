package Program;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
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
public class FilmModosit extends JDialog {

	private final JPanel contentPanel = new JPanel();
	DBMetodusok dbm = new DBMetodusok();
	private JTextField filmid, filmcim, rendezo, mdatum, vevoid, filmcim2, rendezo2, mdatum2, vevoid2;
	DBMetodusok dbm1 = new DBMetodusok();
	private Ell c = new Ell();
	
	

	public FilmModosit(JDialog d, String befilmid, String befilmcim, String berendezo, String bemdatum,
			String bevevoid, int n, FilmTM ftm, int jel) {
		super(d, "Film adatok módosítása", true);
		setBounds(100, 100, 588, 324);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(192, 192, 192));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblKod = new JLabel("K\u00F3d:");
		lblKod.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblKod.setBounds(10, 27, 45, 42);
		contentPanel.add(lblKod);
		
		JLabel lblFilmcim = new JLabel("Filmc\u00EDm:");
		lblFilmcim.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFilmcim.setBounds(10, 79, 59, 13);
		contentPanel.add(lblFilmcim);
		
		JLabel lblRendezo = new JLabel("Rendez\u0151:");
		lblRendezo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRendezo.setBounds(10, 117, 72, 13);
		contentPanel.add(lblRendezo);
		
		JLabel lblMdatum = new JLabel("Megjelen\u00E9s d\u00E1tuma :");
		lblMdatum.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMdatum.setBounds(10, 161, 131, 13);
		contentPanel.add(lblMdatum);
		
		JLabel lblVevoID = new JLabel("Hozz\u00E1rendelt Vev\u0151 ID-ja:");
		lblVevoID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblVevoID.setBounds(10, 198, 168, 13);
		contentPanel.add(lblVevoID);
		
		filmid = new JTextField(befilmid);
		filmid.setFont(new Font("Tahoma", Font.PLAIN, 14));
		filmid.setBounds(65, 39, 164, 19);
		filmid.setHorizontalAlignment(SwingConstants.CENTER);
		filmid.setEditable(false);
		contentPanel.add(filmid);
		filmid.setColumns(10);
		
		filmcim = new JTextField(befilmcim);
		filmcim.setFont(new Font("Tahoma", Font.PLAIN, 14));
		filmcim.setBounds(79, 76, 164, 19);
		filmcim.setHorizontalAlignment(SwingConstants.CENTER);
		filmcim.setEditable(false);
		filmcim.setColumns(10);
		contentPanel.add(filmcim);
		
		rendezo = new JTextField(berendezo);
		rendezo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rendezo.setBounds(92, 113, 135, 21);
		rendezo.setHorizontalAlignment(SwingConstants.CENTER);
		rendezo.setEditable(false);
		rendezo.setColumns(10);
		contentPanel.add(rendezo);
		
		mdatum = new JTextField(bemdatum);
		mdatum.setFont(new Font("Tahoma", Font.PLAIN, 14));
		mdatum.setBounds(151, 157, 120, 21);
		mdatum.setHorizontalAlignment(SwingConstants.CENTER);
		mdatum.setEditable(false);
		mdatum.setColumns(10);
		contentPanel.add(mdatum);
		
		vevoid = new JTextField(bevevoid);
		vevoid.setFont(new Font("Tahoma", Font.PLAIN, 14));
		vevoid.setBounds(173, 195, 143, 19);
		vevoid.setHorizontalAlignment(SwingConstants.CENTER);
		vevoid.setEditable(false);
		vevoid.setColumns(10);
		contentPanel.add(vevoid);
		
		filmcim2 = new JTextField();
		filmcim2.setBounds(341, 78, 190, 19);
		contentPanel.add(filmcim2);
		filmcim2.setColumns(10);
		
		rendezo2 = new JTextField();
		rendezo2.setBounds(341, 116, 190, 19);
		rendezo2.setColumns(10);
		contentPanel.add(rendezo2);
		
		mdatum2 = new JTextField();
		mdatum2.setBounds(341, 160, 190, 19);
		mdatum2.setColumns(10);
		contentPanel.add(mdatum2);
		
		JButton btnModositas = new JButton("M\u00F3dos\u00EDt\u00E1s!");
		btnModositas.setBounds(232, 238, 107, 39);
		btnModositas.setBackground(new Color(169, 169, 169));
		btnModositas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(n==0) {
					dbm.kapcs();
					if(!goodDate(mdatum2)) uzenet("A dátum formátuma helytelen!");
					else{
					dbm.updateFilm(RTF(filmid), RTF2(filmcim2, filmcim), RTF2(rendezo2, rendezo),
							RTF2(mdatum2, mdatum), RTF2(vevoid2, vevoid));
					dbm.leKapcs();
					}
				}
				else if(n==1) {
					if(pcModosit()>0) {
						dbm.csvFilmMod(RTF(filmid), RTF2(filmcim2, filmcim),
								RTF2(rendezo2, rendezo), RTF2(mdatum2, mdatum),
								RTF2(vevoid2, vevoid), ftm, jel);
						dispose();
					}
				}
			}
		});
		btnModositas.setForeground(new Color(0, 0, 0));
		btnModositas.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPanel.add(btnModositas);
		
		dbm1.kapcs();
		dbm1.vevoidOlvas();
		dbm1.leKapcs();
		
		
		vevoid2 = new JTextField();
		vevoid2.setBounds(341, 196, 190, 21);
		contentPanel.add(vevoid2);
		
		JLabel lblJelenlegiAdat = new JLabel("Jelenlegi adat");
		lblJelenlegiAdat.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblJelenlegiAdat.setBounds(10, 10, 183, 21);
		contentPanel.add(lblJelenlegiAdat);
		
		JLabel lbljUjAdat = new JLabel("\u00DAj adat");
		lbljUjAdat.setFont(new Font("Tahoma", Font.BOLD, 18));
		lbljUjAdat.setBounds(404, 37, 183, 21);
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
	
	@SuppressWarnings("rawtypes")
	public String RTF3(JComboBox jcb, JTextField jtf) {
		if(jcb.getSelectedItem().toString().equals("Kérem válasszon!"))
			return jtf.getText();
		return jcb.getSelectedItem().toString();
	}
	
	public boolean goodDate(JTextField jtf) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		String s = RTF(jtf);
		Date testDate = null;
		try {
			testDate = sdf.parse(s);
		} catch (ParseException e) {return false;}
		if(sdf.format(testDate).equals(s)) return true;
		else return false;
	}
	
	public void uzenet(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Hibaüzenet", 2);
	}
	
	public int pcModosit() {
		int pc = 0;
		if(c.uresE(filmid)) pc++;
		if(c.uresE(filmcim2)) pc++;
		if(c.uresE(rendezo2)) pc++;
		if(c.uresE(mdatum2)) pc++;
		if(c.uresE(vevoid)) pc++;
		return pc;
	}
	
	public boolean filledTF(JTextField jtf) {
		String s = RTF(jtf);
		if(s.length() > 0) return true;
		return false;
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
