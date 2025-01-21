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

	
	// Kapcsol�d�s
		public void kapcs() {
			try {
				String url = "jdbc:sqlite:src/beadandodb.db";
				conn = DriverManager.getConnection(url);
			} catch (SQLException e) {
				uzenet("Kapcsol�d�si hiba!\nOk: " + e.getMessage());
			}
		}

		// Lekapcsol�d�s
		public void leKapcs() {
			try {
				conn.close();
			} catch (SQLException e) {
				uzenet("Sikertelen lekapcsol�d�s!\nHiba: " +e.getMessage());
			}
		}
		

		// Driver regiszt�r�l�s gombhoz
		public void driverReg() {
			try {
				Class.forName("org.sqlite.JDBC");
				uzenet("Sikeres driver regisztr�ci�!");
			} catch (ClassNotFoundException e) {
				uzenet("Hiba l�pett fel a driver regisztr�ci� sor�n!\nOk: " + e.getMessage());
			}
		}

		// Driver regiszt�r�l�s
		public void driverReg1() {
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				uzenet("Hiba l�pett fel a driver regisztr�ci� sor�n!\nOk: " + e.getMessage());
			}
		}

	// �zenet ki�r�s
	public void uzenet(String msg) {
		JOptionPane.showMessageDialog(null, msg, "�zenet", 2);
	}

	
	// Bejelentkez�s
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
				uzenet("Sikertelen bejelentkez�s!\nHiba:" + e.getMessage());
			}
			leKapcs();
			return pc;
		}
		
		
		// �j felhaszn�l� regisztr�l�sa
		public void ujUser(String nev, String jelszo) {
			kapcs();
			String sql = "INSERT INTO Felhasznalo VALUES ('"+ nev + "', '" + jelszo + "');";
			try {
				
				s = conn.createStatement();
				s.executeUpdate(sql);
				s.close();
				
			}
			catch (Exception e) {
				
				uzenet("Sikertelen volt " + UjFelhasznalo.getNev() + " felhaszn�l� regisztr�l�sa!\nHiba: " + e.getMessage());
				 success = false;
				 leKapcs();
			}
			if (success) {
			uzenet(UjFelhasznalo.getNev() + " felhaszn�l� sikeresen regisztr�lva!");
			leKapcs();
			}
		}
		
		
		

	// Vev�k t�bla adatok kiolvas�sa
	public VevoTM osszAdatVevo() {
		Object vevotmn[] = { "Jel", "K�d", "N�v", "Telefonsz�m", "Lakc�m", "Sz�let�si id�" };
		VevoTM vtm = new VevoTM(vevotmn, 0);
		String nev = "", tel = "", lakcim = "", sz�lido = "";
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
				sz�lido = rs.getString("sz�lido");
				vtm.addRow(new Object[] { false, vevoid, nev, tel, lakcim, sz�lido });
			}
			rs.close();
		} catch (SQLException e) {
			uzenet(e.getMessage());
		}
		leKapcs();
		return vtm;
	}

	// Film t�bla adatok kiolvas�sa
	public FilmTM osszAdatFilm() {
		Object filmtmn[] = { "Jel", "K�d", "Filmc�m", "Rendez�", "Megjelen�s d�tuma", "Vev� ID" };
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

	// Vev� t�rl�se
		public void vevoTorol(String vevoid) {
			String sql = "DELETE from Vevo WHERE vevoid=" + vevoid;
			try {
				s = conn.createStatement();
				s.execute(sql);
			} catch (Exception e) {
				uzenet("Vev� rekord t�rl�s sikertelen: " + e.getMessage());
			}
		}

	// Film adat t�rl�se
		public void filmTorol(String filmid) {
			String sql = "DELETE from Film where filmid=" + filmid;
			try {
				s = conn.createStatement();
				s.execute(sql);
			} catch (Exception e) {
				uzenet("Film rekord t�rl�s sikertelen: " + e.getMessage());
			}
		}

		
		// Vev� t�bla m�dos�t�sa
		public void updateVevo(String vevoid, String nev, String tel, String lakcim, String sz�lido) {
			kapcs();
			String sql = "UPDATE Vevo SET nev= '" + nev + "', tel= '" + tel + "', lakcim= '" + lakcim + "', sz�lido= '"
					+ sz�lido + "' WHERE vevoid='" + vevoid + "'";
			try {
				s = conn.createStatement();
				s.execute(sql);
				uzenet("Vev� t�bla sikeresen m�dos�tva!");
			} catch (Exception e) {
				uzenet("Adatm�dos�t�s sikertelen: " + e.getMessage());
			}
			leKapcs();
		}

		// Film t�bla m�dos�t�sa
		public void updateFilm(String filmid, String filmcim, String rendezo, String mdatum, String vevoid) {
			kapcs();
			String sql = "UPDATE Film set filmcim= '" + filmcim + "', rendezo= '" + rendezo + "', mdatum= '" + mdatum + "', vevoid= '"
					+ vevoid + "' WHERE filmid='" + filmid + "'";
			try {
				s = conn.createStatement();
				s.execute(sql);
				uzenet("Film t�bla sikeresen m�dos�tva!");
			} catch (Exception e) {
				uzenet("Adatm�dos�t�s sikertelen: " + e.getMessage());
			}
			leKapcs();
		}

		// F�jlmegnyit�s
		public void fajlMegnyit(String txt) {
			try {
				x = new Formatter(txt);
			} catch (Exception e) {
				uzenet(e.getMessage());
			}
		}

	// F�jl Bez�r�sa
	public void fajlBezar() {
		x.close();
	}

	// Vevok.txt felt�lt�se
		public void vevoHozzaad() {
			String nev = "", tel = "", lakcim = "", sz�lido = "";
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
					sz�lido = rs.getString("sz�lido");
					x.format(vevoid + ";" + nev + ";" + tel + ";" + lakcim + ";" + sz�lido + "\n");
				}
				rs.close();
			} catch (SQLException e) {
				uzenet(e.getMessage());
			}
		}

		// Vevok.txt bet�lt�se
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
				uzenet("Adatok sikeresen bet�ltve!");
			} catch (IOException | SQLException e) {
				uzenet("Az adatok bet�lt�se sikertelen: " + e.getMessage());
			}
		}

		// Filmek.txt felt�lt�se
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

		
		// Filmek.txt bet�lt�se
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
				uzenet("Adatok sikeresen bet�ltve!");
			} catch (IOException | SQLException e) {
				uzenet("Az adatok bet�lt�se sikertelen: " + e.getMessage());
			}
		}

		// Vev�k k�djai a leg�rd�l� ablakokhoz
		public ArrayList<String> vevoidOlvas() {
			String sql = "SELECT vevoid from Vevo";
			ArrayList<String> vevoidStr = new ArrayList<String>();
			vevoidStr.add("K�rem v�lasszon!");
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

		
		// Minden adat beolvas�sa
		public MindenTM mindentOlvas() {
			Object alltmn[] = { "Jel", "K�d", "Filmc�m", "Rendez�", "Megjel. d�tum", "Vev� ID", "N�v", "Telefonsz�m", "Lakc�m",
					"Sz�l. id�" };
			MindenTM atm = new MindenTM(alltmn, 0);
			String nev = "", tel = "", vevocim = "", sz�lido = "";
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
					sz�lido = rs.getString("sz�lido");
					atm.addRow(new Object[] { false, filmid, filmcim, rendezo, mdatum, vevoid, nev, tel, vevocim, sz�lido });
				}
				rs.close();
			} catch (SQLException e) {
				uzenet(e.getMessage());
			}
			leKapcs();
			return atm;
		}

		// T�bl�k nev�nek lek�r�se
		public void getTabla() {
			kapcs();
			try {
				int i = 1;
				DatabaseMetaData metaData = conn.getMetaData();
				String[] types = { "TABLE" };
				ResultSet tables = metaData.getTables(null, null, "%", types);
				while (tables.next()) {
					uzenet("A(z) "+i + ". t�bla neve: " + tables.getString("TABLE_NAME"));
					i++;
				}
			} catch (Exception e) {
				uzenet(e.getMessage());
			}
			leKapcs();
		}

		
		
	// Vev� felv�tel
		public void addVevo(String vevoid, String nev, String tel, String cim, String szulido) throws IOException {
			kapcs();
			String sql = "INSERT INTO Vevo VALUES (" + vevoid + ", '" + nev + "', '" + tel + "', '" + cim + "', '"
					+ szulido + "')";
			try {
				s = conn.createStatement();
				s.executeUpdate(sql);
				uzenet("�j vev� sikeresen hozz�adva!");
			} catch (SQLException e) {
				uzenet("�j vev� hozz�ad�sa sikertelen: " + e.getMessage());
			}
			leKapcs();
		}

		
		
		// Film felv�tel
		public void addFilm(String filmid, String cim, String rendezo, String mdatum, String vevoid) {
			kapcs();
			String sql = "INSERT INTO Film VALUES (" + filmid + ", '" + cim + "', '" + rendezo + "', '" + mdatum + "', " + vevoid
					+ ")";
			try {
				s = conn.createStatement();
				s.executeUpdate(sql);
				uzenet("�j film sikeresen hozz�adva!");
			} catch (SQLException e) {
				uzenet("�j film hozz�ad�sa sikertelen: " + e.getMessage());
			}
			leKapcs();
		}

		
		
		// minden.txt felt�lt�se
		public void addOsszes() {
			String nev = "", tel = "", tcim = "", sz�lido = "";
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
					sz�lido = rs.getString("sz�lido");
					x.format(filmid + ";" + kcim + ";" + rendezo + ";" + mdatum + ";" + vevoid + ";" + nev + ";" + tel + ";"
							+ tcim + ";" + sz�lido + "\n");
				}
				rs.close();
			} catch (SQLException e) {
				uzenet(e.getMessage());
			}
			
		}

		// 1. sz�r�: V�rosra keres�s, Vev�k list�z�s�ra
		public Szuro1TM szuro1(String varos) {
			Object sz1tmn[] = { "Jel", "K�d", "N�v", "Telefonsz�m", "Lakc�m", "Sz�let�si id�" };
			Szuro1TM sz1tm = new Szuro1TM(sz1tmn, 0);
			String nev = "", tel = "", lakcim = "", sz�lido = "";
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
					sz�lido = rs.getString("sz�lido");
					sz1tm.addRow(new Object[] { false, vevoid, nev, tel, lakcim, sz�lido });
					db++;
				}
				rs.close();
			} catch (SQLException e) {
				uzenet(e.getMessage());
			}
			if (db == 0) {
				JOptionPane.showMessageDialog(null, "Nincs adat!", "�zenet", 2);
			}
			return sz1tm;
		}

		
		// 2. sz�r�: Vev�k sz�let�si ideje
		public Szuro2TM szuro2(String alsohatar, String felsohatar) {
			Object sz2tmn[] = { "Jel", "K�d", "N�v", "Telefonsz�m", "Lakc�m", "Sz�let�si id�" };
			Szuro2TM sz2tm = new Szuro2TM(sz2tmn, 0);
			String nev = "", tel = "", lakcim = "", sz�lido = "";
			
			
			int vevoid = 0, db = 0;
			String sql = "SELECT * FROM Vevo WHERE sz�lido BETWEEN '" + alsohatar + "' AND '" + felsohatar + "'";
			try {
				s = conn.createStatement();
				rs = s.executeQuery(sql);
				while (rs.next()) {
					vevoid = rs.getInt("vevoid");
					nev = rs.getString("nev");
					tel = rs.getString("tel");
					lakcim = rs.getString("lakcim");
					sz�lido = rs.getString("sz�lido");
					sz2tm.addRow(new Object[] { false, vevoid, nev, tel, lakcim, sz�lido });
					db++;
				}
				rs.close();
			} catch (SQLException e) {
				uzenet(e.getMessage());
			}
			if (db == 0) {
				JOptionPane.showMessageDialog(null, "Nincs adat!", "�zenet", 2);
			}
			
			return sz2tm;
		}


		// 3. sz�r�: Rendez�re keres�s, filmjeinek list�ja
		public Szuro3TM szuro3(String rendezo) {
			Object sz3tmn[] = { "Jel", "K�d", "Filmc�m", "Rendez�", "Megjelen�s d�tuma", "Vev� ID" };
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
				JOptionPane.showMessageDialog(null, "Nincs adat!", "�zenet", 2);
			}
			return sz3tm;
		}

		
		
		// 4. sz�r�: Filmek megjelen�si d�tuma (intervallummal)
		public Szuro4TM szuro4(String alsohatar, String felsohatar) {
			Object sz4tmn[] = { "Jel", "K�d", "Filmc�m", "Rendez�", "Megjelen�s D�tuma", "Vev� ID" };
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
				JOptionPane.showMessageDialog(null, "Nincs adat!", "�zenet", 2);
			}
			return sz4tm;
		}

		
		
		// 5. sz�r�: Vev� nev�re sz�r�s, vev� filmek list�ja
		public Szuro5TM szuro5(String vevonev) {
			Object sz5tmn[] = { "Jel", "N�v", "Filmc�m", "Rendez�" };
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
				JOptionPane.showMessageDialog(null, "Nincs adat!", "�zenet", 2);
			}

			return sz5tm;
		}

	
	// CSV Vev�k olvas�sa
	public VevoTM csvOlvasVevo() {
		Object vevotmn[] = { "Jel", "K�d", "N�v", "Telefonsz�m", "Lakc�m", "Sz�let�si id�" };
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
			uzenet("Sikertelen CSV olvas�s: " + e.getMessage());
		}
		return vtm;
	}
	
	
	// CSV Filmek olvas�sa
	public FilmTM csvOlvasFilm() {
		Object filmtmn[] = { "Jel", "K�d", "Filmc�m", "Rendez�", "Megjelen�s d�tuma", "Vev� ID" };
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
			uzenet("Sikertelen CSV olvas�s: " + e.getMessage());
			
		}
		return ftm;
	}
	
	
	// CSV Minden olvas�sa
	public MindenTM csvMindentOlvas() {
		Object alltmn[] = { "Jel", "K�d", "Filmc�m", "Rendez�", "Megjelen�si d�tum", "Vev� ID", "N�v", "Telefonsz�m", "Lakc�m","Sz�l. id�" };
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
			uzenet("Hiba a CSV f�jl olvas�sakor!\nOk: " + e.getMessage());
		}
		return atm;
	}
	
	// CS-b�l TXT-be Filmek eset�n
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
	
	// CS-b�l TXT-be Vev�k eset�n
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
	
	//TXT bet�lt�se Film eset�n
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
	
	
	
	//TXT bet�lt�se Vev� eset�n
	@SuppressWarnings("resource")
	public void csvtxtVevo(VevoTM vtm) {
		String vevoid, nev, tel, cim, sz�lido, x=";";
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
				sz�lido=st[4];
				out.println(vevoid+x+nev+x+tel+x+cim+x+sz�lido);
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
	
	//Vev� felvitel
	public void csAddVevo(String vevoid, String nev, String tel, String lakcim, String sz�lido) {
		String x=";";
		
		try {
			PrintStream out = new PrintStream(new FileOutputStream(TipusValaszt.getFile() + "/Vevo.csv",true));
			out.println(vevoid+x+nev+x+tel+x+lakcim+x+sz�lido);
			out.close();
			uzenet("Sikeres adatfelvitel!");
		} catch (IOException e) {
			uzenet("Az adatfelvitel sikertelen!\nHiba: " + e.getMessage());
		}
	}
	
	//Film t�rl�s
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
			uzenet("Sikeres t�rl�s!");
		} catch (IOException e) {
			uzenet("T�rl�s sikertelen! Hiba: "+e.getMessage());
		}
	}
	
	//Vev� t�rl�s
	public void csvVevoTorol(VevoTM vtm) {
		String x = ";";
		try {
			PrintStream out = new PrintStream(new FileOutputStream(TipusValaszt.getFile() + "/Vevo.csv"));
			for (int i = 0; i < vtm.getRowCount(); i++) {
				String vevoid=vtm.getValueAt(i, 1).toString();
				String nev=vtm.getValueAt(i, 2).toString();
				String tel=vtm.getValueAt(i, 3).toString();
				String lakcim=vtm.getValueAt(i, 4).toString();
				String sz�lido=vtm.getValueAt(i, 5).toString();
				out.println(vevoid+x+nev+x+tel+x+lakcim+x+sz�lido);
			}
			out.close();
			uzenet("Sikeres t�rl�s!");
		} catch (IOException e) {
			uzenet("T�rl�s sikertelen! Hiba: "+e.getMessage());
		}
	}
	
	//Film m�dos�t�s
	public void csvFilmMod(String filmid, String filmcim, String rendezo, String mdatum, String vevoid, FilmTM ftm, int jel) {
			if(c.uresE(filmid)) ftm.setValueAt(filmid, jel, 1);
			if(c.uresE(filmcim)) ftm.setValueAt(filmcim, jel, 2);
			if(c.uresE(rendezo)) ftm.setValueAt(rendezo, jel, 3);
			if(c.uresE(mdatum)) ftm.setValueAt(mdatum, jel, 4);
			if(c.uresE(vevoid)) ftm.setValueAt(vevoid, jel, 5);
			this.csvFilmTorol(ftm);
			uzenet("Sikeres m�dos�t�s!");
			}
	
	//Vev� m�dos�t�s
	public void csvVevoMod(String vevoid, String nev, String tel, String lakcim, String szulido, VevoTM vtm, int jel) {
		if(c.uresE(vevoid)) vtm.setValueAt(vevoid, jel, 1);
		if(c.uresE(nev)) vtm.setValueAt(nev, jel, 2);
		if(c.uresE(tel)) vtm.setValueAt(tel, jel, 3);
		if(c.uresE(lakcim)) vtm.setValueAt(lakcim, jel, 4);
		if(c.uresE(szulido)) vtm.setValueAt(szulido, jel, 5);
		this.csvVevoTorol(vtm);
		uzenet("Sikeres m�dos�t�s!");
	}
	
	
	//Film hibakeres�
	public HFilmTM csvDebugFilm() {
		Object filmhibatmn[] = {"Jel", "K�d", "Filmc�m", "Rendez�", "Megjelen�s D�tuma", "Vev� ID"};
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
			uzenet(db + " darab hib�t tal�tam a Film.csv f�jlban!");
			in.close();
		} catch (IOException e) {
			uzenet("Sikertelen hibakeres�s: " + e.getMessage());
			
		}
		return fhtm;
	}
	
	//Vev� hibakeres�
	public HVevoTM csvDebugVevo() {
		Object vevohibatmn[] = {"Jel", "K�d", "N�v", "Telefonsz�m", "Lakc�m", "Sz�let�si id�"};
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
			uzenet(db + " darab hib�t tal�tam a Vevo.csv f�jlban!");
			in.close();
		} catch (IOException e) {
			uzenet("Sikertelen hibakeres�s!\nHiba: " + e.getMessage());
			
		}
		return vhtm;
	}
	
	
	//Filmhiba.txt felt�lt�se
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
			uzenet("Sikeres f�jlki�r�s!\n "+ TipusValaszt.getFile() + "/Filmhiba.txt");
		} catch (Exception e) {
			uzenet("Sikertelen f�jlki�r�s!\nHiba: " + e.getMessage());
		}
	}
	
	
	//Vevohiba.txt felt�lt�se
	public void hibatoTXTVevo(HVevoTM vhtm) {
		String vevoid, nev, tel, lakcim, sz�lido, x=";";
		try {
			PrintStream out = new PrintStream(new FileOutputStream(TipusValaszt.getFile() + "/Vevohiba.txt"));
			for (int i = 0; i < vhtm.getRowCount(); i++) {
				vevoid=vhtm.getValueAt(i, 1).toString();
				nev=vhtm.getValueAt(i, 2).toString();
				tel=vhtm.getValueAt(i, 3).toString();
				lakcim=vhtm.getValueAt(i, 4).toString();
				sz�lido=vhtm.getValueAt(i, 5).toString();
				out.println(vevoid+x+nev+x+tel+x+lakcim+x+sz�lido);
				
			}
			out.close();
			uzenet("Sikeres f�jlki�r�s!\n "+ TipusValaszt.getFile() +"/Vevohiba.txt");
		} catch (Exception e) {
			uzenet("Sikertelen f�jlki�r�s!\nHiba: " + e.getMessage());
		}
	}
	
	
	//PDF l�trehoz�sa
	public void createPDF() {
		try {
			@SuppressWarnings("unused")
			Rectangle pageSize = new Rectangle(1000, 720);
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(TipusValaszt.getFile() + "/Egybe.pdf"));

			document.open();
			document.addTitle("Vev�k �s Filmek");
			PdfPTable vevo = new PdfPTable(5);
			addTablaHeaderVevo(vevo);
			addSorokVevo(vevo);
			PdfPTable film = new PdfPTable(5);
			addTablaHeaderFilm(film);
			addSorokFilm(film);
			
			document.add(vevo);
			document.add(film);
			document.close();
			uzenet("F�jlki�r�s sikeres!\n "+ TipusValaszt.getFile() +"/Egybe.pdf");
		} catch (Exception e) {
			uzenet("Sikertelen f�jlki�r�s!\nHiba: " + e.getMessage());
		}
	}
	
	
	
	//PDF Vev� t�bla hozz�ad�s
	private void addTablaHeaderVevo(PdfPTable tabla) {
	    Stream.of("K�d", "N�v", "Telefonsz�m", "Lakc�m", "Sz�let�si id�")
	      .forEach(columnTitle -> {
	        PdfPCell header = new PdfPCell();
	        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        header.setBorderWidth(2);
	        header.setPhrase(new Phrase(columnTitle));
	        tabla.addCell(header);
	    });
	}
	
	
	
	//PDF Film t�bla hozz�ad�s
	private void addTablaHeaderFilm(PdfPTable tabla) {
	    Stream.of( "K�d", "Filmc�m", "Rendez�", "Megjelen�s d�tuma", "Vev� ID")
	      .forEach(columnTitle -> {
	        PdfPCell header = new PdfPCell();
	        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        header.setBorderWidth(2);
	        header.setPhrase(new Phrase(columnTitle));
	        tabla.addCell(header);
	    });
	}
	
	
	
	//PDF Film t�blasorok hozz�ad�sa
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
			uzenet("T�blasorok sikeresen hozz�adva!");
		} catch (Exception e) {
			uzenet("Sikertelen t�blasor hozz�ad�s!\nHiba: "+e.getMessage());
		}
	}
	
	//PDF Vev� t�blasorok hozz�ad�s
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
			uzenet("Sikertelen t�blasor hozz�ad�s!\nHiba: "+e.getMessage());
		}
	}
	
}
	


