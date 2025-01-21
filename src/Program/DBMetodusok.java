package Program;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.stream.Stream;
import javax.swing.JOptionPane;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;



public class DBMetodusok {
	private Statement s = null;
	private Connection conn = null;
	private ResultSet rs = null;
	private PreparedStatement ps = null;
	private Formatter x;
	boolean success = true;
	private Ell c = new Ell();

	
	// Kapcsolódás
		public void kapcs() {
			try {
				String url = "jdbc:sqlite:src/beadandodb.db";
				conn = DriverManager.getConnection(url);
			} catch (SQLException e) {
				uzenet("Kapcsolódási hiba!\nOk: " + e.getMessage());
			}
		}

		// Lekapcsolódás
		public void leKapcs() {
			try {
				conn.close();
			} catch (SQLException e) {
				uzenet("Sikertelen lekapcsolódás!\nHiba: " +e.getMessage());
			}
		}
		

		// Driver regisztárálás gombhoz
		public void driverReg() {
			try {
				Class.forName("org.sqlite.JDBC");
				uzenet("Sikeres driver regisztráció!");
			} catch (ClassNotFoundException e) {
				uzenet("Hiba lépett fel a driver regisztráció során!\nOk: " + e.getMessage());
			}
		}

		// Driver regisztárálás
		public void driverReg1() {
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				uzenet("Hiba lépett fel a driver regisztráció során!\nOk: " + e.getMessage());
			}
		}

	// Üzenet kiírás
	public void uzenet(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Üzenet", 2);
	}

	
	// Bejelentkezés
		public int bejelentkezes(String nev, String jelszo) {
			kapcs();
			int pc = -1;
			String sql = "SELECT COUNT(*) pc FROM Felhasznalo WHERE nev='" + nev + "' AND jelszo='" + jelszo + "';";
			try {
				s = conn.createStatement();
				rs = s.executeQuery(sql);
				while (rs.next()) {
					pc = rs.getInt("pc");
				}
				rs.close();
			} catch (Exception e) {
				uzenet("Sikertelen bejelentkezés!\nHiba:" + e.getMessage());
			}
			leKapcs();
			return pc;
		}
		
		
		// Új felhasználó regisztrálása
		public void ujUser(String nev, String jelszo) {
			kapcs();
			String sql = "INSERT INTO Felhasznalo VALUES ('"+ nev + "', '" + jelszo + "');";
			try {
				
				s = conn.createStatement();
				s.executeUpdate(sql);
				s.close();
				
			}
			catch (Exception e) {
				
				uzenet("Sikertelen volt " + UjFelhasznalo.getNev() + " felhasználó regisztrálása!\nHiba: " + e.getMessage());
				 success = false;
				 leKapcs();
			}
			if (success) {
			uzenet(UjFelhasznalo.getNev() + " felhasználó sikeresen regisztrálva!");
			leKapcs();
			}
		}
		
		
		

	// Vevõk tábla adatok kiolvasása
	public VevoTM osszAdatVevo() {
		Object vevotmn[] = { "Jel", "Kód", "Név", "Telefonszám", "Lakcím", "Születési idõ" };
		VevoTM vtm = new VevoTM(vevotmn, 0);
		String nev = "", tel = "", lakcim = "", szülido = "";
		int vevoid = 0;
		String sql = "Select * from Vevo";
		kapcs();
		try {
			s = conn.createStatement();
			rs = s.executeQuery(sql);
			while (rs.next()) {
				vevoid = rs.getInt("vevoid");
				nev = rs.getString("nev");
				tel = rs.getString("tel");
				lakcim = rs.getString("lakcim");
				szülido = rs.getString("szülido");
				vtm.addRow(new Object[] { false, vevoid, nev, tel, lakcim, szülido });
			}
			rs.close();
		} catch (SQLException e) {
			uzenet(e.getMessage());
		}
		leKapcs();
		return vtm;
	}

	// Film tábla adatok kiolvasása
	public FilmTM osszAdatFilm() {
		Object filmtmn[] = { "Jel", "Kód", "Filmcím", "Rendezõ", "Megjelenés dátuma", "Vevõ ID" };
		FilmTM ftm = new FilmTM(filmtmn, 0);
		String cim = "", szerzo = "", mdatum = "";
		int filmid = 0, vevoid = 0;
		String sql = "Select * from Film";
		kapcs();
		try {
			s = conn.createStatement();
			rs = s.executeQuery(sql);
			while (rs.next()) {
				filmid = rs.getInt("filmid");
				cim = rs.getString("filmcim");
				szerzo = rs.getString("rendezo");
				mdatum = rs.getString("mdatum");
				vevoid = rs.getInt("vevoid");
				ftm.addRow(new Object[] { false, filmid, cim, szerzo, mdatum, vevoid });
			}
			rs.close();
		} catch (SQLException e) {
			uzenet(e.getMessage());
		}
		leKapcs();
		return ftm;
	}

	// Vevõ törlése
		public void vevoTorol(String vevoid) {
			String sql = "DELETE from Vevo WHERE vevoid=" + vevoid;
			try {
				s = conn.createStatement();
				s.execute(sql);
			} catch (Exception e) {
				uzenet("Vevõ rekord törlés sikertelen: " + e.getMessage());
			}
		}

	// Film adat törlése
		public void filmTorol(String filmid) {
			String sql = "DELETE from Film where filmid=" + filmid;
			try {
				s = conn.createStatement();
				s.execute(sql);
			} catch (Exception e) {
				uzenet("Film rekord törlés sikertelen: " + e.getMessage());
			}
		}

		
		// Vevõ tábla módosítása
		public void updateVevo(String vevoid, String nev, String tel, String lakcim, String szülido) {
			kapcs();
			String sql = "UPDATE Vevo SET nev= '" + nev + "', tel= '" + tel + "', lakcim= '" + lakcim + "', szülido= '"
					+ szülido + "' WHERE vevoid='" + vevoid + "'";
			try {
				s = conn.createStatement();
				s.execute(sql);
				uzenet("Vevõ tábla sikeresen módosítva!");
			} catch (Exception e) {
				uzenet("Adatmódosítás sikertelen: " + e.getMessage());
			}
			leKapcs();
		}

		// Film tábla módosítása
		public void updateFilm(String filmid, String filmcim, String rendezo, String mdatum, String vevoid) {
			kapcs();
			String sql = "UPDATE Film set filmcim= '" + filmcim + "', rendezo= '" + rendezo + "', mdatum= '" + mdatum + "', vevoid= '"
					+ vevoid + "' WHERE filmid='" + filmid + "'";
			try {
				s = conn.createStatement();
				s.execute(sql);
				uzenet("Film tábla sikeresen módosítva!");
			} catch (Exception e) {
				uzenet("Adatmódosítás sikertelen: " + e.getMessage());
			}
			leKapcs();
		}

		// Fájlmegnyitás
		public void fajlMegnyit(String txt) {
			try {
				x = new Formatter(txt);
			} catch (Exception e) {
				uzenet(e.getMessage());
			}
		}

	// Fájl Bezárása
	public void fajlBezar() {
		x.close();
	}

	// Vevok.txt feltöltése
		public void vevoHozzaad() {
			String nev = "", tel = "", lakcim = "", szülido = "";
			int vevoid = 0;
			String sql = "SELECT * from Vevo";
			try {
				s = conn.createStatement();
				rs = s.executeQuery(sql);
				while (rs.next()) {
					vevoid = rs.getInt("vevoid");
					nev = rs.getString("nev");
					tel = rs.getString("tel");
					lakcim = rs.getString("lakcim");
					szülido = rs.getString("szülido");
					x.format(vevoid + ";" + nev + ";" + tel + ";" + lakcim + ";" + szülido + "\n");
				}
				rs.close();
			} catch (SQLException e) {
				uzenet(e.getMessage());
			}
		}

		// Vevok.txt betöltése
		public void vevoBetolt(String fajl) {
			String sql = "";
			kapcs();
			try {
				BufferedReader in = new BufferedReader(new FileReader(fajl));
				String sf = in.readLine();
				sf = in.readLine();
				while (sf != null) {
					String[] st = sf.split(";");
					sql = "REPLACE INTO Vevo VALUES(" + st[0] + ", '" + st[1] + "', '" + st[2] + "', '" + st[3] + "', '"
							+ st[4] + "')";
					s = conn.createStatement();
					s.execute(sql);
					sf = in.readLine();
				}
				in.close();
				uzenet("Adatok sikeresen betöltve!");
			} catch (IOException | SQLException e) {
				uzenet("Az adatok betöltése sikertelen: " + e.getMessage());
			}
		}

		// Filmek.txt feltöltése
		public void filmHozzaad() {
			String filmcim = "", rendezo = "", mdatum = "";
			int filmid = 0, vevoid = 0;
			String sql = "SELECT * from Film";
			try {
				s = conn.createStatement();
				rs = s.executeQuery(sql);
				while (rs.next()) {
					filmid = rs.getInt("filmid");
					filmcim = rs.getString("filmcim");
					rendezo = rs.getString("rendezo");
					mdatum = rs.getString("mdatum");
					vevoid = rs.getInt("vevoid");
					x.format(filmid + ";" + filmcim + ";" + rendezo + ";" + mdatum + ";" + vevoid + "\n");
				}
				rs.close();
			} catch (SQLException e) {
				uzenet(e.getMessage());
			}
		}

		
		// Filmek.txt betöltése
		public void filmBetolt(String fajl) {
			String sql = "";
			kapcs();
			try {
				BufferedReader in = new BufferedReader(new FileReader(fajl));
				String sf = in.readLine();
				sf = in.readLine();
				while (sf != null) {
					String[] st = sf.split(";");
					sql = "REPLACE INTO Film VALUES(" + st[0] + ", '" + st[1] + "', '" + st[2] + "', '" + st[3] + "', '"
							+ st[4] + "')";
					s = conn.createStatement();
					s.execute(sql);
					sf = in.readLine();
				}
				in.close();
				uzenet("Adatok sikeresen betöltve!");
			} catch (IOException | SQLException e) {
				uzenet("Az adatok betöltése sikertelen: " + e.getMessage());
			}
		}

		// Vevõk kódjai a legördülõ ablakokhoz
		public ArrayList<String> vevoidOlvas() {
			String sql = "SELECT vevoid from Vevo";
			ArrayList<String> vevoidStr = new ArrayList<String>();
			vevoidStr.add("Kérem válasszon!");
			try {
				s = conn.createStatement();
				rs = s.executeQuery(sql);
				while (rs.next()) {
					vevoidStr.add(rs.getString("vevoid"));
				}
				rs.close();
			} catch (SQLException e) {
				uzenet(e.getMessage());
			}
			return vevoidStr;
		}

		
		// Minden adat beolvasása
		public MindenTM mindentOlvas() {
			Object alltmn[] = { "Jel", "Kód", "Filmcím", "Rendezõ", "Megjel. dátum", "Vevõ ID", "Név", "Telefonszám", "Lakcím",
					"Szül. idõ" };
			MindenTM atm = new MindenTM(alltmn, 0);
			String nev = "", tel = "", vevocim = "", szülido = "";
			int vevoid = 0;
			String rendezo = "", mdatum = "", filmcim = "";
			int filmid = 0;
			kapcs();
			String sql = "SELECT * from Film f LEFT JOIN Vevo v ON f.filmid = v.vevoid";
			try {
				s = conn.createStatement();
				rs = s.executeQuery(sql);
				while (rs.next()) {
					filmid = rs.getInt("filmid");
					filmcim = rs.getString(2);
					rendezo = rs.getString("rendezo");
					mdatum = rs.getString("mdatum");
					vevoid = rs.getInt(5);
					nev = rs.getString("nev");
					tel = rs.getString("tel");
					vevocim = rs.getString(9);
					szülido = rs.getString("szülido");
					atm.addRow(new Object[] { false, filmid, filmcim, rendezo, mdatum, vevoid, nev, tel, vevocim, szülido });
				}
				rs.close();
			} catch (SQLException e) {
				uzenet(e.getMessage());
			}
			leKapcs();
			return atm;
		}

		// Táblák nevének lekérése
		public void getTabla() {
			kapcs();
			try {
				int i = 1;
				DatabaseMetaData metaData = conn.getMetaData();
				String[] types = { "TABLE" };
				ResultSet tables = metaData.getTables(null, null, "%", types);
				while (tables.next()) {
					uzenet("A(z) "+i + ". tábla neve: " + tables.getString("TABLE_NAME"));
					i++;
				}
			} catch (Exception e) {
				uzenet(e.getMessage());
			}
			leKapcs();
		}

		
		
	// Vevõ felvétel
		public void addVevo(String vevoid, String nev, String tel, String cim, String szulido) throws IOException {
			kapcs();
			String sql = "INSERT INTO Vevo VALUES (" + vevoid + ", '" + nev + "', '" + tel + "', '" + cim + "', '"
					+ szulido + "')";
			try {
				s = conn.createStatement();
				s.executeUpdate(sql);
				uzenet("Új vevõ sikeresen hozzáadva!");
			} catch (SQLException e) {
				uzenet("Új vevõ hozzáadása sikertelen: " + e.getMessage());
			}
			leKapcs();
		}

		
		
		// Film felvétel
		public void addFilm(String filmid, String cim, String rendezo, String mdatum, String vevoid) {
			kapcs();
			String sql = "INSERT INTO Film VALUES (" + filmid + ", '" + cim + "', '" + rendezo + "', '" + mdatum + "', " + vevoid
					+ ")";
			try {
				s = conn.createStatement();
				s.executeUpdate(sql);
				uzenet("Új film sikeresen hozzáadva!");
			} catch (SQLException e) {
				uzenet("Új film hozzáadása sikertelen: " + e.getMessage());
			}
			leKapcs();
		}

		
		
		// minden.txt feltöltése
		public void addOsszes() {
			String nev = "", tel = "", tcim = "", szülido = "";
			int vevoid = 0;
			String rendezo = "", mdatum = "", kcim = "";
			int filmid = 0;
			kapcs();
			String sql = "SELECT * FROM Film CROSS JOIN Vevo";
			try {
				s = conn.createStatement();
				rs = s.executeQuery(sql);
				while (rs.next()) {
					filmid = rs.getInt("filmid");
					kcim = rs.getString(2);
					rendezo = rs.getString("rendezo");
					mdatum = rs.getString("mdatum");
					vevoid = rs.getInt(5);
					nev = rs.getString("nev");
					tel = rs.getString("tel");
					tcim = rs.getString(9);
					szülido = rs.getString("szülido");
					x.format(filmid + ";" + kcim + ";" + rendezo + ";" + mdatum + ";" + vevoid + ";" + nev + ";" + tel + ";"
							+ tcim + ";" + szülido + "\n");
				}
				rs.close();
			} catch (SQLException e) {
				uzenet(e.getMessage());
			}
			
		}

		// 1. szûrõ: Városra keresés, Vevõk listázására
		public Szuro1TM szuro1(String varos) {
			Object sz1tmn[] = { "Jel", "Kód", "Név", "Telefonszám", "Lakcím", "Születési idõ" };
			Szuro1TM sz1tm = new Szuro1TM(sz1tmn, 0);
			String nev = "", tel = "", lakcim = "", szülido = "";
			int vevoid = 0, db = 0;
			String sql = "SELECT * FROM Vevo WHERE lakcim LIKE '" + varos + "%" + "'";
			try {
				s = conn.createStatement();
				rs = s.executeQuery(sql);
				while (rs.next()) {
					vevoid = rs.getInt("vevoid");
					nev = rs.getString("nev");
					tel = rs.getString("tel");
					lakcim = rs.getString("lakcim");
					szülido = rs.getString("szülido");
					sz1tm.addRow(new Object[] { false, vevoid, nev, tel, lakcim, szülido });
					db++;
				}
				rs.close();
			} catch (SQLException e) {
				uzenet(e.getMessage());
			}
			if (db == 0) {
				JOptionPane.showMessageDialog(null, "Nincs adat!", "Üzenet", 2);
			}
			return sz1tm;
		}

		
		// 2. szûrõ: Vevõk születési ideje
		public Szuro2TM szuro2(String alsohatar, String felsohatar) {
			Object sz2tmn[] = { "Jel", "Kód", "Név", "Telefonszám", "Lakcím", "Születési idõ" };
			Szuro2TM sz2tm = new Szuro2TM(sz2tmn, 0);
			String nev = "", tel = "", lakcim = "", szülido = "";
			
			
			int vevoid = 0, db = 0;
			String sql = "SELECT * FROM Vevo WHERE szülido BETWEEN '" + alsohatar + "' AND '" + felsohatar + "'";
			try {
				s = conn.createStatement();
				rs = s.executeQuery(sql);
				while (rs.next()) {
					vevoid = rs.getInt("vevoid");
					nev = rs.getString("nev");
					tel = rs.getString("tel");
					lakcim = rs.getString("lakcim");
					szülido = rs.getString("szülido");
					sz2tm.addRow(new Object[] { false, vevoid, nev, tel, lakcim, szülido });
					db++;
				}
				rs.close();
			} catch (SQLException e) {
				uzenet(e.getMessage());
			}
			if (db == 0) {
				JOptionPane.showMessageDialog(null, "Nincs adat!", "Üzenet", 2);
			}
			
			return sz2tm;
		}


		// 3. szûrõ: Rendezõre keresés, filmjeinek listája
		public Szuro3TM szuro3(String rendezo) {
			Object sz3tmn[] = { "Jel", "Kód", "Filmcím", "Rendezõ", "Megjelenés dátuma", "Vevõ ID" };
			Szuro3TM sz3tm = new Szuro3TM(sz3tmn, 0);
			String filmcim = "", rendez = "", mdatum = "";
			int filmid = 0, vevoid = 0, db = 0;
			String sql = "SELECT * FROM Film WHERE rendezo LIKE '" + rendezo + "%" + "'";
			try {
				s = conn.createStatement();
				rs = s.executeQuery(sql);
				while (rs.next()) {
					filmid = rs.getInt("filmid");
					filmcim = rs.getString("filmcim");
					rendez = rs.getString("rendezo");
					mdatum = rs.getString("mdatum");
					vevoid = rs.getInt("vevoid");
					sz3tm.addRow(new Object[] { false, filmid, filmcim, rendez, mdatum, vevoid });
					db++;
				}
				rs.close();
			} catch (SQLException e) {
				uzenet(e.getMessage());
			}
			if (db == 0) {
				JOptionPane.showMessageDialog(null, "Nincs adat!", "Üzenet", 2);
			}
			return sz3tm;
		}

		
		
		// 4. szûrõ: Filmek megjelenési dátuma (intervallummal)
		public Szuro4TM szuro4(String alsohatar, String felsohatar) {
			Object sz4tmn[] = { "Jel", "Kód", "Filmcím", "Rendezõ", "Megjelenés Dátuma", "Vevõ ID" };
			Szuro4TM sz4tm = new Szuro4TM(sz4tmn, 0);
			String filmcim = "", rendezo = "", mdatum = "";
			int filmid = 0, vevoid = 0, db = 0;
			String sql = "SELECT * FROM Film WHERE mdatum BETWEEN '" + alsohatar + "' AND '" + felsohatar + "'";
			try {
				s = conn.createStatement();
				rs = s.executeQuery(sql);
				while (rs.next()) {
					filmid = rs.getInt("filmid");
					filmcim = rs.getString("filmcim");
					rendezo = rs.getString("rendezo");
					mdatum = rs.getString("mdatum");
					vevoid = rs.getInt("vevoid");
					sz4tm.addRow(new Object[] { false, filmid, filmcim, rendezo, mdatum, vevoid });
					db++;
				}
				rs.close();
			} catch (SQLException e) {
				uzenet(e.getMessage());
			}
			if (db == 0) {
				JOptionPane.showMessageDialog(null, "Nincs adat!", "Üzenet", 2);
			}
			return sz4tm;
		}

		
		
		// 5. szûrõ: Vevõ nevére szûrés, vevõ filmek listája
		public Szuro5TM szuro5(String vevonev) {
			Object sz5tmn[] = { "Jel", "Név", "Filmcím", "Rendezõ" };
			Szuro5TM sz5tm = new Szuro5TM(sz5tmn, 0);
			String filmcim = "", rendezo = "";
			String nev = "";
			int db = 0;
			
			String sql = "SELECT nev, filmcim, rendezo FROM Vevo LEFT JOIN Film ON Vevo.vevoid = Film.vevoid WHERE nev = ?";
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, vevonev);
				rs = ps.executeQuery();
				while (rs.next()) {
					nev = rs.getString("nev");
					filmcim = rs.getString("filmcim");
					rendezo = rs.getString("rendezo");
					sz5tm.addRow(new Object[] { false, nev, filmcim, rendezo });
					db++;
				}
				rs.close();
			} catch (SQLException e) {
				uzenet(e.getMessage());
			}
			if (db == 0) {
				JOptionPane.showMessageDialog(null, "Nincs adat!", "Üzenet", 2);
			}

			return sz5tm;
		}

	
	// CSV Vevõk olvasása
	public VevoTM csvOlvasVevo() {
		Object vevotmn[] = { "Jel", "Kód", "Név", "Telefonszám", "Lakcím", "Születési idõ" };
		VevoTM vtm = new VevoTM(vevotmn, 0);
		try {
			BufferedReader in = new BufferedReader(new FileReader(TipusValaszt.getFile() + "/Vevo.csv"));
			String s = in.readLine();
			while(s!=null) {
				String[] st = s.split(";");
				vtm.addRow(new Object[] {false, st[0], st[1], st[2], st[3], st[4]});
				s=in.readLine();
			}
			in.close();
		} catch (IOException e) {
			uzenet("Sikertelen CSV olvasás: " + e.getMessage());
		}
		return vtm;
	}
	
	
	// CSV Filmek olvasása
	public FilmTM csvOlvasFilm() {
		Object filmtmn[] = { "Jel", "Kód", "Filmcím", "Rendezõ", "Megjelenés dátuma", "Vevõ ID" };
		FilmTM ftm = new FilmTM(filmtmn, 0);;
		try {
			BufferedReader in = new BufferedReader(new FileReader(TipusValaszt.getFile() + "/Film.csv"));
			String s = in.readLine();
			while(s!=null) {
				String[] st = s.split(";");
				ftm.addRow(new Object[] {false, st[0], st[1], st[2], st[3], st[4]});
				s=in.readLine();
			}
			in.close();
		} catch (IOException e) {
			uzenet("Sikertelen CSV olvasás: " + e.getMessage());
			
		}
		return ftm;
	}
	
	
	// CSV Minden olvasása
	public MindenTM csvMindentOlvas() {
		Object alltmn[] = { "Jel", "Kód", "Filmcím", "Rendezõ", "Megjelenési dátum", "Vevõ ID", "Név", "Telefonszám", "Lakcím","Szül. idõ" };
		MindenTM atm = new MindenTM(alltmn, 0);
		try {
			@SuppressWarnings("resource")
			BufferedReader in1 = new BufferedReader(new FileReader(TipusValaszt.getFile() + "/Vevo.csv"));
			String s1 = in1.readLine();
			@SuppressWarnings("resource")
			BufferedReader in2 = new BufferedReader(new FileReader(TipusValaszt.getFile() + "/Film.csv"));
			String s2 = in2.readLine();
			while(s1!=null && s2!=null) {
				String[] st1 = s1.split(";");
				String[] st2 = s2.split(";");
				atm.addRow(new Object[] {false, st2[0], st2[1], st2[2], st2[3], st2[4], st1[1], st1[2], st1[3], st1[4]});
				s1=in1.readLine();
				s2=in2.readLine();
			}
		} catch (Exception e) {
			uzenet("Hiba a CSV fájl olvasásakor!\nOk: " + e.getMessage());
		}
		return atm;
	}
	
	// CS-bõl TXT-be Filmek esetén
	public void csvToTXTFilm() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(TipusValaszt.getFile() + "/Film.csv"));
			String s = in.readLine();
			while(s!=null) {
				String[] st = s.split(";");
				x.format(st[0]+";"+ st[1]+";"+ st[2]+";"+ st[3]+";"+ st[4]+"\n");
				s=in.readLine();
			}
			in.close();
		} catch (Exception e) {
			uzenet(e.getMessage());
		}
	}
	
	// CS-bõl TXT-be Vevõk esetén
	public void csvToTXTVevo() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(TipusValaszt.getFile() + "/Vevo.csv"));
			String s = in.readLine();
			while(s!=null) {
				String[] st = s.split(";");
				x.format(st[0]+";"+ st[1]+";"+ st[2]+";"+ st[3]+";"+ st[4]+"\n");
				s=in.readLine();
			}
			in.close();
		} catch (Exception e) {
			uzenet(e.getMessage());
		}
	}
	
	//TXT betöltése Film esetén
	@SuppressWarnings("resource")
	public void csvtxtFilm(FilmTM ftm) {
		String filmid, cim, rendezo, mdatum, vevoid, x=";";
		try {
			
			BufferedReader in = new BufferedReader(new FileReader(TipusValaszt.getFile() + "/Film.txt"));
			PrintStream out = new PrintStream(new FileOutputStream(TipusValaszt.getFile() + "/Film.csv"));
			String s=in.readLine();
			while(s!=null) {
				String[] st = s.split(";");
				filmid=st[0];
				cim=st[1];
				rendezo=st[2];
				mdatum=st[3];
				vevoid=st[4];
				out.println(filmid+x+cim+x+rendezo+x+mdatum+x+vevoid);
				s=in.readLine();
			}
			uzenet("Sikeres adatfelvitel!");
		} catch (Exception e) {
			uzenet("Az adatfelvitel sikertelen!\nHiba: "+e.getMessage());
		}
	}
	
	
	
	//TXT betöltése Vevõ esetén
	@SuppressWarnings("resource")
	public void csvtxtVevo(VevoTM vtm) {
		String vevoid, nev, tel, cim, szülido, x=";";
		try {
			BufferedReader in = new BufferedReader(new FileReader(TipusValaszt.getFile() + "/Vevo.txt"));
			PrintStream out = new PrintStream(new FileOutputStream(TipusValaszt.getFile()+"/Vevo.csv"));
			String s=in.readLine();
			while(s!=null) {
				String[] st = s.split(";");
				vevoid=st[0];
				nev=st[1];
				tel=st[2];
				cim=st[3];
				szülido=st[4];
				out.println(vevoid+x+nev+x+tel+x+cim+x+szülido);
				s=in.readLine();
			}
			uzenet("Sikeres adatfelvitel!");
		} catch (Exception e) {
			uzenet("Az adatfelvitel sikertelen!\nHiba: "+e.getMessage());
		}
	}
	
	
	//Film felvitel
	public void csvAddFilm(String filmid, String cim, String rendezo, String mdatum, String vevoid) {
		String x=";";
		try {
			PrintStream out = new PrintStream(new FileOutputStream(TipusValaszt.getFile() + "/Film.csv",true));
			out.println(filmid+x+cim+x+rendezo+x+mdatum+x+vevoid);
			out.close();
			uzenet("Sikeres adatfelvitel!");
		} catch (IOException e) {
			uzenet("Az adatfelvitel sikertelen!\nHiba: " + e.getMessage());
		}
	}
	
	//Vevõ felvitel
	public void csAddVevo(String vevoid, String nev, String tel, String lakcim, String szülido) {
		String x=";";
		
		try {
			PrintStream out = new PrintStream(new FileOutputStream(TipusValaszt.getFile() + "/Vevo.csv",true));
			out.println(vevoid+x+nev+x+tel+x+lakcim+x+szülido);
			out.close();
			uzenet("Sikeres adatfelvitel!");
		} catch (IOException e) {
			uzenet("Az adatfelvitel sikertelen!\nHiba: " + e.getMessage());
		}
	}
	
	//Film törlés
	public void csvFilmTorol(FilmTM ftm) {
		String x = ";";
		try {
			PrintStream out = new PrintStream(new FileOutputStream(TipusValaszt.getFile() + "/Film.csv"));
			for (int i = 0; i < ftm.getRowCount(); i++) {
				String filmid=ftm.getValueAt(i, 1).toString();
				String filmcim=ftm.getValueAt(i, 2).toString();
				String rendezo=ftm.getValueAt(i, 3).toString();
				String mdatum=ftm.getValueAt(i, 4).toString();
				String vevoid=ftm.getValueAt(i, 5).toString();
				out.println(filmid+x+filmcim+x+rendezo+x+mdatum+x+vevoid);
			}
			out.close();
			uzenet("Sikeres törlés!");
		} catch (IOException e) {
			uzenet("Törlés sikertelen! Hiba: "+e.getMessage());
		}
	}
	
	//Vevõ törlés
	public void csvVevoTorol(VevoTM vtm) {
		String x = ";";
		try {
			PrintStream out = new PrintStream(new FileOutputStream(TipusValaszt.getFile() + "/Vevo.csv"));
			for (int i = 0; i < vtm.getRowCount(); i++) {
				String vevoid=vtm.getValueAt(i, 1).toString();
				String nev=vtm.getValueAt(i, 2).toString();
				String tel=vtm.getValueAt(i, 3).toString();
				String lakcim=vtm.getValueAt(i, 4).toString();
				String szülido=vtm.getValueAt(i, 5).toString();
				out.println(vevoid+x+nev+x+tel+x+lakcim+x+szülido);
			}
			out.close();
			uzenet("Sikeres törlés!");
		} catch (IOException e) {
			uzenet("Törlés sikertelen! Hiba: "+e.getMessage());
		}
	}
	
	//Film módosítás
	public void csvFilmMod(String filmid, String filmcim, String rendezo, String mdatum, String vevoid, FilmTM ftm, int jel) {
			if(c.uresE(filmid)) ftm.setValueAt(filmid, jel, 1);
			if(c.uresE(filmcim)) ftm.setValueAt(filmcim, jel, 2);
			if(c.uresE(rendezo)) ftm.setValueAt(rendezo, jel, 3);
			if(c.uresE(mdatum)) ftm.setValueAt(mdatum, jel, 4);
			if(c.uresE(vevoid)) ftm.setValueAt(vevoid, jel, 5);
			this.csvFilmTorol(ftm);
			uzenet("Sikeres módosítás!");
			}
	
	//Vevõ módosítás
	public void csvVevoMod(String vevoid, String nev, String tel, String lakcim, String szulido, VevoTM vtm, int jel) {
		if(c.uresE(vevoid)) vtm.setValueAt(vevoid, jel, 1);
		if(c.uresE(nev)) vtm.setValueAt(nev, jel, 2);
		if(c.uresE(tel)) vtm.setValueAt(tel, jel, 3);
		if(c.uresE(lakcim)) vtm.setValueAt(lakcim, jel, 4);
		if(c.uresE(szulido)) vtm.setValueAt(szulido, jel, 5);
		this.csvVevoTorol(vtm);
		uzenet("Sikeres módosítás!");
	}
	
	
	//Film hibakeresõ
	public HFilmTM csvDebugFilm() {
		Object filmhibatmn[] = {"Jel", "Kód", "Filmcím", "Rendezõ", "Megjelenés Dátuma", "Vevõ ID"};
		HFilmTM fhtm = new HFilmTM(filmhibatmn,0);
		try {
			int db=0;
			BufferedReader in = new BufferedReader(new FileReader(TipusValaszt.getFile() + "/Film.csv"));
			String s = in.readLine();
			while(s!=null) {
				String[] st = s.split(";");
				boolean ok = false;
				if(c.uresE(st[0]) && c.uresE(st[1]) && c.uresE(st[2]) && c.uresE(st[3]) && c.uresE(st[4])&& c.datumEll(st[3]) && c.szamEll(st[4]))
						ok = true;
				if(!ok) {
					fhtm.addRow(new Object[] {false, st[0], st[1], st[2], st[3], st[4]});
					db++;
				}
				s=in.readLine();
			}
			uzenet(db + " darab hibát talátam a Film.csv fájlban!");
			in.close();
		} catch (IOException e) {
			uzenet("Sikertelen hibakeresés: " + e.getMessage());
			
		}
		return fhtm;
	}
	
	//Vevõ hibakeresõ
	public HVevoTM csvDebugVevo() {
		Object vevohibatmn[] = {"Jel", "Kód", "Név", "Telefonszám", "Lakcím", "Születési idõ"};
		HVevoTM vhtm = new HVevoTM(vevohibatmn,0);
		try {
			int db=0;
			BufferedReader in = new BufferedReader(new FileReader(TipusValaszt.getFile() + "/Vevo.csv"));
			String s = in.readLine();
			while(s!=null) {
				String[] st = s.split(";");
				boolean ok = false;
				if(c.uresE(st[0]) && c.uresE(st[1]) && c.uresE(st[2]) && c.uresE(st[3]) && c.uresE(st[4]) && c.datumEll(st[4]) && c.szamEll(st[0]) && c.szamEll(st[2]))
						ok = true;
				if(!ok) {
					vhtm.addRow(new Object[] {false, st[0], st[1], st[2], st[3], st[4]});
					db++;
				}
				s=in.readLine();
			}
			uzenet(db + " darab hibát talátam a Vevo.csv fájlban!");
			in.close();
		} catch (IOException e) {
			uzenet("Sikertelen hibakeresés!\nHiba: " + e.getMessage());
			
		}
		return vhtm;
	}
	
	
	//Filmhiba.txt feltöltése
	public void hibatoTXTFilm(HFilmTM fhtm) {
		String filmid, filmcim, rendezo, mdatum, vevoid, x=";";
		try {
			PrintStream out = new PrintStream(new FileOutputStream(TipusValaszt.getFile() + "/Filmhiba.txt"));
			for (int i = 0; i < fhtm.getRowCount(); i++) {
				filmid=fhtm.getValueAt(i, 1).toString();
				filmcim=fhtm.getValueAt(i, 2).toString();
				rendezo=fhtm.getValueAt(i, 3).toString();
				mdatum=fhtm.getValueAt(i, 4).toString();
				vevoid=fhtm.getValueAt(i, 5).toString();
				out.println(filmid+x+filmcim+x+rendezo+x+mdatum+x+vevoid);
			}
			out.close();
			uzenet("Sikeres fájlkiírás!\n "+ TipusValaszt.getFile() + "/Filmhiba.txt");
		} catch (Exception e) {
			uzenet("Sikertelen fájlkiírás!\nHiba: " + e.getMessage());
		}
	}
	
	
	//Vevohiba.txt feltöltése
	public void hibatoTXTVevo(HVevoTM vhtm) {
		String vevoid, nev, tel, lakcim, szülido, x=";";
		try {
			PrintStream out = new PrintStream(new FileOutputStream(TipusValaszt.getFile() + "/Vevohiba.txt"));
			for (int i = 0; i < vhtm.getRowCount(); i++) {
				vevoid=vhtm.getValueAt(i, 1).toString();
				nev=vhtm.getValueAt(i, 2).toString();
				tel=vhtm.getValueAt(i, 3).toString();
				lakcim=vhtm.getValueAt(i, 4).toString();
				szülido=vhtm.getValueAt(i, 5).toString();
				out.println(vevoid+x+nev+x+tel+x+lakcim+x+szülido);
				
			}
			out.close();
			uzenet("Sikeres fájlkiírás!\n "+ TipusValaszt.getFile() +"/Vevohiba.txt");
		} catch (Exception e) {
			uzenet("Sikertelen fájlkiírás!\nHiba: " + e.getMessage());
		}
	}
	
	
	//PDF létrehozása
	public void createPDF() {
		try {
			@SuppressWarnings("unused")
			Rectangle pageSize = new Rectangle(1000, 720);
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(TipusValaszt.getFile() + "/Egybe.pdf"));

			document.open();
			document.addTitle("Vevõk és Filmek");
			PdfPTable vevo = new PdfPTable(5);
			addTablaHeaderVevo(vevo);
			addSorokVevo(vevo);
			PdfPTable film = new PdfPTable(5);
			addTablaHeaderFilm(film);
			addSorokFilm(film);
			
			document.add(vevo);
			document.add(film);
			document.close();
			uzenet("Fájlkiírás sikeres!\n "+ TipusValaszt.getFile() +"/Egybe.pdf");
		} catch (Exception e) {
			uzenet("Sikertelen fájlkiírás!\nHiba: " + e.getMessage());
		}
	}
	
	
	
	//PDF Vevõ tábla hozzáadás
	private void addTablaHeaderVevo(PdfPTable tabla) {
	    Stream.of("Kód", "Név", "Telefonszám", "Lakcím", "Születési idõ")
	      .forEach(columnTitle -> {
	        PdfPCell header = new PdfPCell();
	        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        header.setBorderWidth(2);
	        header.setPhrase(new Phrase(columnTitle));
	        tabla.addCell(header);
	    });
	}
	
	
	
	//PDF Film tábla hozzáadás
	private void addTablaHeaderFilm(PdfPTable tabla) {
	    Stream.of( "Kód", "Filmcím", "Rendezõ", "Megjelenés dátuma", "Vevõ ID")
	      .forEach(columnTitle -> {
	        PdfPCell header = new PdfPCell();
	        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        header.setBorderWidth(2);
	        header.setPhrase(new Phrase(columnTitle));
	        tabla.addCell(header);
	    });
	}
	
	
	
	//PDF Film táblasorok hozzáadása
	@SuppressWarnings("resource")
	private void addSorokFilm(PdfPTable tabla) {
		String kod, filmcim, rendezo, mdatum, vevoid;
		try {
			BufferedReader in = new BufferedReader(new FileReader(TipusValaszt.getFile() + "/Film.txt"));
			String s=in.readLine();
			while(s!=null) {
				String[] st = s.split(";");
				kod=st[0];
				filmcim=st[1];
				rendezo=st[2];
				mdatum=st[3];
				vevoid=st[4];
				tabla.addCell(kod);
				tabla.addCell(filmcim);
				tabla.addCell(rendezo);
				tabla.addCell(mdatum);
				tabla.addCell(vevoid);
				s=in.readLine();
			}
			uzenet("Táblasorok sikeresen hozzáadva!");
		} catch (Exception e) {
			uzenet("Sikertelen táblasor hozzáadás!\nHiba: "+e.getMessage());
		}
	}
	
	//PDF Vevõ táblasorok hozzáadás
	@SuppressWarnings("resource")
	private void addSorokVevo(PdfPTable table) {
		String kod, nev, tel, lakcim, szulido;
		try {
			BufferedReader in = new BufferedReader(new FileReader(TipusValaszt.getFile() + "/Vevo.txt"));
			String s=in.readLine();
			while(s!=null) {
				String[] st = s.split(";");
				kod=st[0];
				nev=st[1];
				tel=st[2];
				lakcim=st[3];
				szulido=st[4];
				table.addCell(kod);
				table.addCell(nev);
				table.addCell(tel);
				table.addCell(lakcim);
				table.addCell(szulido);
				s=in.readLine();
			}
		} catch (Exception e) {
			uzenet("Sikertelen táblasor hozzáadás!\nHiba: "+e.getMessage());
		}
	}
	
}
	


