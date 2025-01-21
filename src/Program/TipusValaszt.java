package Program;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@SuppressWarnings("serial")
public class TipusValaszt extends JFrame {

	private JPanel contentPane;
	private static File file = new File(System.getProperty("user.dir"));

	@SuppressWarnings("rawtypes")
	public TipusValaszt(Bejelentkezes bejelentkezes) {
		super("Formátum választó");
		setTitle("Fom\u00E1tumv\u00E1laszt\u00F3");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(192, 192, 192));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		JLabel lblFormatum = new JLabel("K\u00E9rem v\u00E1lassza ki a f\u00E1jlform\u00E1tumot!");
		lblFormatum.setHorizontalAlignment(SwingConstants.CENTER);
		lblFormatum.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFormatum.setBounds(0, 10, 278, 37);
		contentPane.add(lblFormatum);
		
		String[] types = {"Válasszon!", "SQLite", "CSV"};
		
		@SuppressWarnings("unchecked")
		JComboBox comboBox = new JComboBox(types);
		comboBox.setBackground(new Color(169, 169, 169));
		comboBox.setForeground(new Color(0, 0, 0));
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
		comboBox.setBounds(10, 61, 154, 28);
		contentPane.add(comboBox);
		
		
		JButton btnIndit = new JButton("Ind\u00EDt\u00E1s");
		btnIndit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tipus = RTF2(comboBox);
				if(tipus.equals("SQLite")) {
					SQLite abkezel = new SQLite(TipusValaszt.this);
					abkezel.setVisible(true);
					dispose();
				}
				
				else if(tipus.equals("CSV"))
				{
					CSV csvkezel = new CSV(TipusValaszt.this);
					csvkezel.setVisible(true);
					dispose();
				}
			}
		});
		
		
		btnIndit.setBackground(new Color(169, 169, 169));
		btnIndit.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnIndit.setBounds(174, 56, 125, 37);
		contentPane.add(btnIndit);
		
		
		JLabel lblBezar = new JLabel("Bez\u00E1r\u00E1s");
		lblBezar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblBezar.setForeground(new Color(255,0,0));
				
			}
			public void mouseExited(MouseEvent e) {
				lblBezar.setForeground(new Color(0,0,0));
			}
			public void mouseClicked(MouseEvent arg0) {
				if(JOptionPane.showConfirmDialog(null, "Biztosan ki akar lépni?", "Kilépés", JOptionPane.YES_NO_OPTION)==0) {
					System.exit(0);
				}
			}
		});
		
		
		lblBezar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBezar.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblBezar.setBounds(338, 219, 74, 17);
		contentPane.add(lblBezar);
		
		
		// Kijelentkezés
				
				JButton btnKijelentkezes = new JButton("Kijelentkez\u00E9s");
				btnKijelentkezes.setFont(new Font("Tahoma", Font.ITALIC, 13));
				btnKijelentkezes.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(JOptionPane.showConfirmDialog(null, "Biztosan ki akar jelentkezni?", Bejelentkezes.getUser()+" felhasználó kijelentkezése", JOptionPane.YES_NO_OPTION)==0) {
							dispose();
							JOptionPane.showMessageDialog(null, Bejelentkezes.getUser()+" felhasználó sikeresen kijelentkezett!", "Üzenet", 2);
							Bejelentkezes.main(null);
							}
					}
				});
				
				
				btnKijelentkezes.setBounds(10, 214, 115, 28);
				contentPane.add(btnKijelentkezes);
				
				JButton btnZip = new JButton("Minden adat zippel\u00E9se");
				btnZip.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							zipAll(getFile() + "/Film.csv", getFile() + "/Film.txt", getFile() + "/minden.txt",
									getFile() + "/Vevo.csv", getFile() + "/Vevo.txt", getFile() + "/Egybe.pdf");
							uzenet("Sikeres zippelés!\n " + getFile() + "/minden.zip");
						} catch (IOException e2) {
							uzenet("Sikertelen zippelés!\nHiba: "+ e2.getMessage());
						}
					}
				});
				
				
				btnZip.setFont(new Font("SimSun", Font.BOLD, 14));
				btnZip.setBounds(10, 125, 236, 28);
				contentPane.add(btnZip);
				
				JLabel lblFelhasznalo = new JLabel("Bejelentkezve, mint: " + Bejelentkezes.getUser());
				lblFelhasznalo.setFont(new Font("Tahoma", Font.BOLD, 12));
				lblFelhasznalo.setBounds(10, 187, 205, 17);
				contentPane.add(lblFelhasznalo);
				
				JButton btnGyoker = new JButton("F\u00E1jlmappa v\u00E1laszt\u00E1sa");
				btnGyoker.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JFileChooser jfc = new JFileChooser(System.getProperty("user.dir"));
		                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		                int lv1 =jfc.showOpenDialog(null);
		                if (lv1 == JFileChooser.APPROVE_OPTION) {
		                     file = jfc.getSelectedFile();
		                }
					}
				});
						
				
				btnGyoker.setFont(new Font("Tahoma", Font.BOLD, 11));
				btnGyoker.setBounds(261, 116, 151, 46);
				contentPane.add(btnGyoker);
				
				if(Bejelentkezes.getUser().equals("Csucsu")) {
					JLabel lblEasterEgg = new JLabel("?");
					lblEasterEgg.setForeground(new Color(192, 192, 192));
					lblEasterEgg.setHorizontalAlignment(SwingConstants.CENTER);
					lblEasterEgg.setFont(new Font("Yu Gothic", Font.BOLD, 11));
					lblEasterEgg.setBounds(338, 24, 74, 17);
					contentPane.add(lblEasterEgg);
					lblEasterEgg.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseEntered(MouseEvent e) {
							lblEasterEgg.setForeground(new Color(255,0,0));
									
						}
						public void mouseExited(MouseEvent e) {
							lblEasterEgg.setForeground(new Color(192, 192, 192));
						}
						public void mouseClicked(MouseEvent arg0) {
							try {
								URI ee = new URI("https://www.youtube.com/watch?v=t-Qju5Fow9I&t=18s");
								java.awt.Desktop.getDesktop().browse(ee);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				);}
				
				
				
				if(Bejelentkezes.getUser().equals("admin")) {
				JButton btnUjUser = new JButton("\u00DAj felhaszn\u00E1l\u00F3");
				btnUjUser.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						UjFelhasznalo nt = new UjFelhasznalo(null);
						nt.setVisible(true);
					}
				});

				
				btnUjUser.setFont(new Font("Tahoma", Font.ITALIC, 13));
				btnUjUser.setBounds(150, 214, 115, 28);
				contentPane.add(btnUjUser);
				
				
				

				
				
	}
}
	
	
	
	public String RTF2(@SuppressWarnings("rawtypes") JComboBox jcb) {
		String str = jcb.getSelectedItem().toString();
		return str;
	}
	
	public void uzenet(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Üzenet", 1);
	}
	
		//Minden adat zippelése

		private void zipAll(String fajl1, String fajl2, String fajl3, String fajl4,
				String fajl5, String fajl6) throws IOException {
			
			byte[] bajtok = new byte[4096];
            int hossz;
			
			List<String> files = Arrays.asList(fajl1, fajl2, fajl3, fajl4, fajl5, fajl6);
	        FileOutputStream output = new FileOutputStream(getFile() + "/minden.zip");
	        ZipOutputStream zipOut = new ZipOutputStream(output);
	        for (String srcFile : files) {
	            File filesToZip = new File(srcFile);
	            FileInputStream fis = new FileInputStream(filesToZip);
	            ZipEntry zipEntry = new ZipEntry(filesToZip.getName());
	            zipOut.putNextEntry(zipEntry);

	            
	            while((hossz = fis.read(bajtok)) >= 0) {
	                zipOut.write(bajtok, 0, hossz);
	            }
	            fis.close();
	        }
	        zipOut.close();
	        output.close();
	    }



		public static String getFile() {
	
			return file.getAbsolutePath();
			
		}
}
