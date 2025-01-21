package Program;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Szuro extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField varos, also1, also2, felso1, felso2, rendezo, vevonev;
	private JLabel lblVevoFilmjei;
	private JButton btnBezar, btnSzures, btnSzures2, btnSzures3, btnSzures4, btnSzures5;
	DBMetodusok dbm = new DBMetodusok();
	private Szuro1TM sz1tm;
	private Szuro2TM sz2tm;
	private Szuro3TM sz3tm;
	private Szuro4TM sz4tm;
	private Szuro5TM sz5tm;
	

	// Az 5 féle szûrés szövegei és gombjai
	public Szuro() {
		setTitle("Sz\u0171r\u00E9s");
		setBounds(100, 100, 800, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(128, 128, 128));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		
		JLabel lblVarosszures = new JLabel("K\u00E9rem \u00EDrjon be egy v\u00E1rosnevet,"
				+ " \u00EDgy megkapja az ott \u00E9l\u0151 vev\u0151ket");
		lblVarosszures.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblVarosszures.setBackground(new Color(192, 192, 192));
		lblVarosszures.setBounds(10, 10, 431, 30);
		contentPanel.add(lblVarosszures);
		
		varos = new JTextField();
		varos.setHorizontalAlignment(SwingConstants.CENTER);
		varos.setBounds(451, 17, 177, 19);
		contentPanel.add(varos);
		varos.setColumns(10);
		
		
		JLabel lblVevoSzul = new JLabel("Azok a vev\u0151k akik"
				+ "                        \u00E9s                        k\u00F6z\u00F6tt sz\u00FCletek");
		lblVevoSzul.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblVevoSzul.setBackground(Color.LIGHT_GRAY);
		lblVevoSzul.setBounds(10, 67, 450, 30);
		contentPanel.add(lblVevoSzul);
		
		
		also1 = new JTextField();
		also1.setBounds(139, 74, 77, 19);
		contentPanel.add(also1);
		also1.setColumns(10);
		
		felso1 = new JTextField();
		felso1.setColumns(10);
		felso1.setBounds(247, 74, 77, 19);
		contentPanel.add(felso1);
		
		
		JLabel lbFilmSzerzo = new JLabel("\u00CDrjon be egy rendez\u0151nevet \u00E9s megkapja az \u00E1ltala rendezett filmeket");
		lbFilmSzerzo.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbFilmSzerzo.setBackground(Color.LIGHT_GRAY);
		lbFilmSzerzo.setBounds(9, 129, 451, 30);
		contentPanel.add(lbFilmSzerzo);
		
		
		JLabel lblFilmMdatum = new JLabel("Azok a filmek amiknek a megjelen\u00E9si d\u00E1tuma"
				+ "                       \u00E9s                        k\u00F6z\u00F6tt van");
		lblFilmMdatum.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblFilmMdatum.setBackground(Color.LIGHT_GRAY);
		lblFilmMdatum.setBounds(10, 196, 584, 30);
		contentPanel.add(lblFilmMdatum);
		
		
		rendezo = new JTextField();
		rendezo.setHorizontalAlignment(SwingConstants.CENTER);
		rendezo.setColumns(10);
		rendezo.setBounds(477, 136, 177, 19);
		contentPanel.add(rendezo);
		
		also2 = new JTextField();
		also2.setColumns(10);
		also2.setBounds(312, 203, 77, 19);
		contentPanel.add(also2);
		
		felso2 = new JTextField();
		felso2.setColumns(10);
		felso2.setBounds(422, 203, 77, 19);
		contentPanel.add(felso2);
		
		
		lblVevoFilmjei = new JLabel("\u00CDrja be az egyik vev\u0151 nev\u00E9t \u00E9s megkapja a filmjeit");
		lblVevoFilmjei.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblVevoFilmjei.setBackground(Color.LIGHT_GRAY);
		lblVevoFilmjei.setBounds(10, 254, 450, 30);
		contentPanel.add(lblVevoFilmjei);
		
		
		vevonev = new JTextField();
		vevonev.setHorizontalAlignment(SwingConstants.CENTER);
		vevonev.setColumns(10);
		vevonev.setBounds(342, 261, 177, 19);
		contentPanel.add(vevonev);
		
		
		btnBezar = new JButton("Bez\u00E1r");
		btnBezar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		
		btnBezar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnBezar.setBackground(new Color(169, 169, 169));
		btnBezar.setBounds(660, 322, 116, 31);
		contentPanel.add(btnBezar);
		
		btnSzures = new JButton("Sz\u0171r\u00E9s");
		btnSzures.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!filledTF(varos)) uzenet("Írjon be egy városnevet!");
				else if(!stringEll(varos)) uzenet("Kérem csak szöveget írjon be!");
				else {
					dbm.kapcs();
					sz1tm = dbm.szuro1(RTF(varos));
					dbm.leKapcs();
					Szuro1Lista sz1 = new Szuro1Lista(Szuro.this,sz1tm);
					sz1.setVisible(true);
				}
			}
		});
		
		
		btnSzures.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSzures.setBackground(new Color(169, 169, 169));
		btnSzures.setBounds(645, 10, 104, 30);
		contentPanel.add(btnSzures);
		
		
		btnSzures2 = new JButton("Sz\u0171r\u00E9s");
		btnSzures2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!datumEll(also1) && !datumEll(felso1) ) uzenet("Valamely dátum formátuma helytelen!");
				else {
					String alsohatar = RTF(also1);
					String felsohatar = RTF(felso1);
					dbm.kapcs();
					sz2tm = dbm.szuro2(alsohatar, felsohatar);
					dbm.leKapcs();
					Szuro2Lista sz2 = new Szuro2Lista(Szuro.this,sz2tm);
					sz2.setVisible(true);	
				}
			}
		});
		
		
		btnSzures2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSzures2.setBackground(new Color(169, 169, 169));
		btnSzures2.setBounds(454, 67, 104, 30);
		contentPanel.add(btnSzures2);
		
		
		btnSzures3 = new JButton("Sz\u0171r\u00E9s");
		btnSzures3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!filledTF(rendezo)) uzenet("Írjon be egy nevet!");
				else if(!stringEll(rendezo)) uzenet("Kérem csak szöveget írjon be!");
				else {
					dbm.kapcs();
					sz3tm = dbm.szuro3(RTF(rendezo));
					dbm.leKapcs();
					Szuro3Lista sz3 = new Szuro3Lista(Szuro.this,sz3tm);
					sz3.setVisible(true);
				}
			}
		});
		
		
		btnSzures3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSzures3.setBackground(new Color(169, 169, 169));
		btnSzures3.setBounds(672, 129, 104, 30);
		contentPanel.add(btnSzures3);
		
		
		btnSzures4 = new JButton("Sz\u0171r\u00E9s");
		btnSzures4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!datumEll(also2) && !datumEll(felso2) ) uzenet("Valamely dátum formátuma helytelen!");
				else{
				String alsohat = RTF(also2);
				String felsohat = RTF(felso2);
				dbm.kapcs();
				sz4tm = dbm.szuro4(alsohat, felsohat);
				dbm.leKapcs();
				Szuro4Lista sz4 = new Szuro4Lista(Szuro.this,sz4tm);
				sz4.setVisible(true);
				}
			}
		});
		
		
		btnSzures4.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSzures4.setBackground(new Color(169, 169, 169));
		btnSzures4.setBounds(597, 196, 104, 30);
		contentPanel.add(btnSzures4);
		
		btnSzures5 = new JButton("Sz\u0171r\u00E9s");
		btnSzures5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbm.kapcs();
				sz5tm = dbm.szuro5(RTF(vevonev));
				dbm.leKapcs();
				Szuro5Lista sz5 = new Szuro5Lista(Szuro.this,sz5tm);
				sz5.setVisible(true);
			}
		});
		
		
		btnSzures5.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSzures5.setBackground(new Color(169, 169, 169));
		btnSzures5.setBounds(529, 254, 104, 30);
		contentPanel.add(btnSzures5);
		
		Object sz1tmn[] = {"Jel","Kód","Név","Telefonszám","Lakcím","Születési idõ"};
		sz1tm = new Szuro1TM(sz1tmn, 0);
		
		Object sz2tmn[] = {"Jel","Kód","Név","Telefonszám","Lakcím","Születési idõ"};
		sz2tm = new Szuro2TM(sz2tmn, 0);
		
		Object sz3tmn[] = {"Jel","Kód","Filmcím","Rendezõ","Megjelenés dátuma","Vevõ ID"};
		sz3tm = new Szuro3TM(sz3tmn, 0);
		
		Object sz4tmn[] = {"Jel","Kód","Filmcím","Rendezõ","Megjelenés dátuma","Vevõ ID"};
		sz4tm = new Szuro4TM(sz4tmn, 0);
		
		Object sz5tmn[] = {"Jel","Kód","Filmcím","Rendezõ"};
		sz5tm = new Szuro5TM(sz5tmn, 0);
		
	}
	
	
	
	public String RTF(JTextField jtf) {
		return jtf.getText();
	}
	
	
	public String RTF2(JTextField jtf2, JTextField jtf) {
		if(jtf2.getText().length()==0)
			return jtf.getText();
		return jtf2.getText();
	}
	
	
	public void uzenet(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Üzenet", 2);
	}
	
	
	public boolean filledTF(JTextField jtf) {
		String s = RTF(jtf);
		if(s.length()>0) return true;
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
	
	
	public boolean stringEll(JTextField jtf) {
		String s = RTF(jtf);
		try {
			Integer.parseInt(s); return false;
		} catch (Exception e) {
			return true;
		}
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
}
