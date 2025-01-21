package Program;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class CSV extends JFrame {

	private JPanel contentPane;
	private FilmTM ftm;
	private VevoTM vtm;
	private MindenTM atm;
	private HFilmTM hftm;
	private HVevoTM hvtm;
	DBMetodusok dbm = new DBMetodusok();


	public CSV(TipusValaszt choosetype) {
		super("Vevõk és filmek - CSV");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 625, 350);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(192, 192, 192));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnMindenLista = new JButton("Minden adat listázása");
		btnMindenLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atm = dbm.csvMindentOlvas();
				MindentListaz al = new MindentListaz(CSV.this, atm);
				al.setVisible(true);
			}
		});
		
		
		btnMindenLista.setForeground(Color.BLACK);
		btnMindenLista.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnMindenLista.setBackground(new Color(169, 169, 169));
		btnMindenLista.setBounds(10, 88, 199, 60);
		contentPane.add(btnMindenLista);
		
		JButton btnVevoLista = new JButton("Vevõk listázása");
		btnVevoLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vtm = dbm.csvOlvasVevo();
				VevoLista vl = new VevoLista(CSV.this, vtm);
				vl.setVisible(true);
			}
		});
		
		
		btnVevoLista.setForeground(Color.BLACK);
		btnVevoLista.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnVevoLista.setBackground(new Color(169, 169, 169));
		btnVevoLista.setBounds(224, 88, 166, 60);
		contentPane.add(btnVevoLista);
		
		
		JButton btnFilmLista = new JButton("Filmek list\u00E1z\u00E1sa");
		btnFilmLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ftm = dbm.csvOlvasFilm();
				FilmLista fl = new FilmLista(CSV.this, ftm);
				fl.setVisible(true);
			}
		});
		
		
		btnFilmLista.setForeground(Color.BLACK);
		btnFilmLista.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnFilmLista.setBackground(new Color(169, 169, 169));
		btnFilmLista.setBounds(400, 88, 199, 60);
		contentPane.add(btnFilmLista);
		
		JButton btnUjAdat = new JButton("\u00DAj adat felvitele");
		btnUjAdat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UjAdat ni = new UjAdat(CSV.this);
				ni.setVisible(true);
			}
		});
		
		
		btnUjAdat.setForeground(Color.BLACK);
		btnUjAdat.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnUjAdat.setBackground(new Color(169, 169, 169));
		btnUjAdat.setBounds(224, 158, 166, 60);
		contentPane.add(btnUjAdat);
		
		JButton btnToPDF = new JButton("Adatok ki\u00EDr\u00E1sa \r\nPDF-be");
		btnToPDF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbm.createPDF();
			}
		});
		
		
		btnToPDF.setForeground(Color.BLACK);
		btnToPDF.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnToPDF.setBackground(new Color(169, 169, 169));
		btnToPDF.setBounds(10, 158, 199, 60);
		contentPane.add(btnToPDF);
		
		JButton btnHibakereses = new JButton("Hibakeres\u00E9s");
		btnHibakereses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hftm = dbm.csvDebugFilm();
				hvtm = dbm.csvDebugVevo();
				FilmHiba esl = new FilmHiba(CSV.this, hftm);
				VevoHiba erl = new VevoHiba(CSV.this, hvtm);
				if(hftm.getRowCount()>0)
					esl.setVisible(true);
				if(hvtm.getRowCount()>0)
					erl.setVisible(true);
			}
		});
		
		
		btnHibakereses.setForeground(Color.BLACK);
		btnHibakereses.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnHibakereses.setBackground(new Color(169, 169, 169));
		btnHibakereses.setBounds(400, 158, 199, 60);
		contentPane.add(btnHibakereses);
		
		JLabel lblBezar = new JLabel("Bez\u00E1r");
		lblBezar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblBezar.setForeground(new Color(255,0,0));
				
			}
			public void mouseExited(MouseEvent e) {
				lblBezar.setForeground(new Color(0,0,0));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if(JOptionPane.showConfirmDialog(null, "Biztosan vissza szeretne lépni a formátumválasztóba?", "", JOptionPane.YES_NO_OPTION)==0) {
					TipusValaszt tv = new TipusValaszt(null);
					tv.setVisible(true);
					dispose();
				}
			}
		});
		
		
		lblBezar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBezar.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblBezar.setBounds(525, 286, 74, 17);
		contentPane.add(lblBezar);
		
		JLabel lblMenu = new JLabel("CSV kezel\u0151 men\u00FC");
		lblMenu.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblMenu.setBounds(10, 23, 199, 32);
		contentPane.add(lblMenu);
		
		JButton btnKijelentkezes = new JButton("Kijelentkez\u00E9s");
		btnKijelentkezes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(null, "Biztosan ki akar jelentkezni?", Bejelentkezes.getUser()+" felhasználó kijelentkezése", JOptionPane.YES_NO_OPTION)==0) {
					dispose();
					JOptionPane.showMessageDialog(null, Bejelentkezes.getUser()+" felhasználó sikeresen kijelentkezett!", "Üzenet", 1);
					Bejelentkezes.main(null);
					}
			}
		});
		btnKijelentkezes.setFont(new Font("Tahoma", Font.ITALIC, 13));
		btnKijelentkezes.setBounds(10, 261, 115, 28);
		contentPane.add(btnKijelentkezes);
	}
}
