package Program;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Ell {

	public boolean uresE(JTextField a, String str) {
		String s = RTF(a);
		if (s.length() > 0)
			return true;
		else {
			uzenet("A(z) " + str + " mezõ üres!", 0);
			return false;
		}
	}
	

	
	public boolean uresE(JTextField a) {
		String s = RTF(a);
		if (s.length() > 0) {
			return true;
		}
			
		else {
			return false;
		}
	}
	

	
	public boolean uresE(String str) {
		if (str.length() > 0) {
			return true;
		}
			
		else {
			return false;
		}
			
	}

	
	
	public boolean szamEll(String str) {
		if (str.length() > 0 && str.length() < 13) {
			return true;
		}
			
		else {
			return false;
		}
			
	}
	
	
	
	public boolean telefonEll(JTextField jtf, String str) {
		String s = RTF(jtf);
		boolean bool = true;
		
		if ((s.length()) == 11) {			
			try {
				Long.parseLong(s);
			} catch (NumberFormatException e) {
				uzenet("A(z) " + str + " mezõben hibás az adat!", 0);
				bool = false;
			}
		}
		else {
			uzenet("Hibás adat a(z) "+ str +" mezõben!", 0);
			bool = false;
		}
		return bool;
			
	}
	

	
	public boolean szamEll2(JTextField a, String str) {
		String s = RTF(a);
		boolean bool = uresE(a, str);
		
		if(bool) try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			uzenet("A(z) "+str+" mezõben hibás a szám!", 0);
			bool = false;
		}
		return bool;
	}
	
	
	
	public boolean szamEll(String s, String str) {
		boolean bool = true;
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			uzenet("A(z) "+str+" mezõben hibás a szám!", 0);
			bool = false;
		}
		return bool;
	}
	
	
	
	public boolean szamEll2(String s) {
		boolean bool = true;
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			bool = false;
		}
		return bool;
	}
	
	
	
	public boolean datumEll(JTextField jtf, String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		String s = RTF(jtf);
		Date tesztDatum = null;
		
		try {
			tesztDatum = sdf.parse(s);
		} catch (ParseException e) {
			uzenet("A(z) "+str+" mezõben hibás a dátum!",0);
			return false;
		}
		
		
		if(sdf.format(tesztDatum).equals(s)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	
	public boolean datumEll(String s, String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.mm.dd");
		Date testDate = null;
		try {
			testDate = sdf.parse(s);
		} catch (ParseException e) {
			uzenet("A(z) "+str+" mezõben hibás a dátum!",0);
			return false;
		}
		
		
		if(sdf.format(testDate).equals(s)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	
	public boolean datumEll(String s) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.mm.dd");
		Date testDate = null;
		
		try {
			testDate = sdf.parse(s);
		} catch (ParseException e) {
			return false;
			
		}
		if(sdf.format(testDate).equals(s)) {
			return true;
		}
			
		else {
			return false;
		}
	}

	
	
	public String RTF(JTextField jtf) {
		return jtf.getText();
	}

	
	
	public void uzenet(String msg, int tipus) {
		JOptionPane.showMessageDialog(null, msg, "", tipus);
	}
}
