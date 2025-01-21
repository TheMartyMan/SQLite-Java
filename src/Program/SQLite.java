package Program;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class SQLite extends JFrame {

	private JPanel contentPane;
	DBMetodusok dbm = new DBMetodusok();
	private VevoTM vtm;
	private FilmTM ftm;
	private MindenTM otm;


	public SQLite(TipusValaszt tipusValaszt) {
		super("Vevõk és filmek - SQLite");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 350);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(192, 192, 192));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JButton btnDriverReg = new JButton("Driver regisztrálása");
		btnDriverReg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbm.driverReg();
			}
		});
		
		
		
		btnDriverReg.setBackground(new Color(169, 169, 169));
		btnDriverReg.setBounds(223, 199, 185, 55);
		btnDriverReg.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnDriverReg.setForeground(SystemColor.desktop);
		contentPane.add(btnDriverReg);
		
		JButton btnMinden = new JButton("Minden adat listázása");
		btnMinden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				otm = dbm.mindentOlvas();
				MindentListaz al = new MindentListaz(SQLite.this, otm);
				al.setVisible(true);
			}
		});
		
		
		
		btnMinden.setForeground(Color.BLACK);
		btnMinden.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnMinden.setBackground(new Color(169, 169, 169));
		btnMinden.setBounds(10, 59, 166, 60);
		contentPane.add(btnMinden);
		
		JButton btnVevoLista = new JButton("Vev\u0151k list\u00E1z\u00E1sa");
		btnVevoLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vtm = dbm.osszAdatVevo();
				VevoLista tl = new VevoLista(SQLite.this, vtm);
				tl.setVisible(true);
			}
		});
		
		
		
		btnVevoLista.setForeground(Color.BLACK);
		btnVevoLista.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnVevoLista.setBackground(new Color(169, 169, 169));
		btnVevoLista.setBounds(186, 59, 166, 60);
		contentPane.add(btnVevoLista);
		
		JButton btnFilmLista = new JButton("Filmek list\u00E1z\u00E1sa");
		btnFilmLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ftm = dbm.osszAdatFilm();
				FilmLista kl = new FilmLista(SQLite.this, ftm);
				kl.setVisible(true);
			}
		});
		
		
		
		btnFilmLista.setForeground(Color.BLACK);
		btnFilmLista.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnFilmLista.setBackground(new Color(169, 169, 169));
		btnFilmLista.setBounds(360, 59, 166, 60);
		contentPane.add(btnFilmLista);
		
		JButton btnSzuro = new JButton("Sz\u0171r\u00E9s");
		btnSzuro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Szuro sz = new Szuro();
				sz.setVisible(true);
			}
		});
		
		
		
		btnSzuro.setForeground(Color.BLACK);
		btnSzuro.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSzuro.setBackground(new Color(169, 169, 169));
		btnSzuro.setBounds(185, 129, 166, 60);
		contentPane.add(btnSzuro);
		
		JButton btnTablak = new JButton("T\u00E1bl\u00E1k");
		btnTablak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbm.getTabla();
			}
		});
		
		
		
		btnTablak.setForeground(Color.BLACK);
		btnTablak.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnTablak.setBackground(new Color(169, 169, 169));
		btnTablak.setBounds(360, 129, 166, 60);
		contentPane.add(btnTablak);
		
		JButton btnUjAdat = new JButton("\u00DAj adat felvitele");
		btnUjAdat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UjAdat ni = new UjAdat(SQLite.this);
				ni.setVisible(true);
			}
		});
		
		
		
		btnUjAdat.setForeground(Color.BLACK);
		btnUjAdat.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnUjAdat.setBackground(new Color(169, 169, 169));
		btnUjAdat.setBounds(10, 129, 166, 60);
		contentPane.add(btnUjAdat);
		
		JLabel lblKilepes = new JLabel("Kil\u00E9p\u00E9s");
		lblKilepes.setHorizontalAlignment(SwingConstants.CENTER);
		lblKilepes.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblKilepes.setBounds(452, 286, 74, 17);
		
		lblKilepes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblKilepes.setForeground(new Color(255,0,0));
				
			}
			public void mouseExited(MouseEvent e) {
				lblKilepes.setForeground(new Color(0,0,0));
			}
			public void mouseClicked(MouseEvent arg0) {
				if(JOptionPane.showConfirmDialog(null, "Biztosan vissza szeretne lépni a formátumválasztóba?", "", JOptionPane.YES_NO_OPTION)==0) {
					TipusValaszt ct = new TipusValaszt(null);
					ct.setVisible(true);
					dispose();
				}
			}
		});
		
		
		
		contentPane.add(lblKilepes);
		
		JButton btnToPDF = new JButton("Adatok PDF-be \u00EDr\u00E1sa");
		btnToPDF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbm.createPDF();
			}
		});
		
		
		
		btnToPDF.setForeground(Color.BLACK);
		btnToPDF.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnToPDF.setBackground(new Color(169, 169, 169));
		btnToPDF.setBounds(10, 199, 203, 55);
		contentPane.add(btnToPDF);
		
		JLabel lblMenu = new JLabel("SQLite kezel\u0151 men\u00FC");
		lblMenu.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblMenu.setBounds(10, 10, 240, 39);
		contentPane.add(lblMenu);
		
		JButton btnKijelentkezes = new JButton("Kijelentkez\u00E9s");
		btnKijelentkezes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(null, "Biztosan ki akar jelentkezni?", Bejelentkezes.getUser()+" felhasználó kijelentkezése", JOptionPane.YES_NO_OPTION)==0) {
					dispose();
					JOptionPane.showMessageDialog(null, Bejelentkezes.getUser()+" felhasználó sikeresen kijelentkezett!", "Üzenet", 2);
					Bejelentkezes.main(null);
					}
			}
		});
		
		btnKijelentkezes.setFont(new Font("Tahoma", Font.ITALIC, 13));
		btnKijelentkezes.setBounds(10, 275, 115, 28);
		contentPane.add(btnKijelentkezes);
		
		Object vevotmn[] = {"Jel", "Kód", "Név", "Telefonszám", "Cím", "Születési idõ"};
		vtm = new VevoTM(vevotmn,0);
		
		Object filmtmn[] = {"Jel", "Kód", "Cím", "Rendezõ", "Megjelenés Dátuma", "Akik megvették a jegyet"};
		ftm = new FilmTM(filmtmn, 0);
		
		Object alltmn[] = {"Jel", "Kód", "Cím", "Rendezõ", "Megjel. dátum", "Vevõ ID", "Név", "Telefonszám", "Cím", "Szül. idõ"};
		otm = new MindenTM(alltmn, 0);
		
	}
}
