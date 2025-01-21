package Program;

import java.awt.BorderLayout;


import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


@SuppressWarnings("serial")
public class UjFelhasznalo extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private static JTextField nev;
	private JTextField jelszo;
	DBMetodusok dbm = new DBMetodusok();
	private JLabel lblJelszo;

	
	
	public UjFelhasznalo(JFrame f) {
		super(f,"�j felhaszn�l� regisztr�l�sa", true);
		setTitle("�j felhaszn�l� regisztr�l�sa");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(192, 192, 192));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNev = new JLabel("Felhaszn\u00E1l\u00F3n\u00E9v:");
			lblNev.setBounds(10, 23, 156, 26);
			lblNev.setFont(new Font("Tahoma", Font.BOLD, 18));
			contentPanel.add(lblNev);
		}

		{
			
			// Adatfelv�tel, mez�ellen�rz�s, ha megfelelnek a felt�telek, felv�tel
			JButton btnReg = new JButton("Regisztr\u00E1ci\u00F3");
			btnReg.setBounds(134, 150, 149, 40);
			btnReg.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(!filledTF(nev)) uzenet("A felhaszn�l�n�v nem lehet �res!");
					else if(!hossz(nev)) uzenet("A felhaszn�l�n�v t�l r�vid! Minimum 5 karakternek kell lennie.");
					else if(!filledTF(jelszo)) uzenet("A jelsz� nem lehet �res!");
					else if(!hossz(jelszo)) uzenet("A jelsz� t�l r�vid! Minimum 5 karakternek kell lennie.");
					
					else {
					dbm.ujUser(RTF(nev), RTF(jelszo));
					dispose();
					}
				}}
			);
			
			
			btnReg.setBackground(new Color(169, 169, 169));
			btnReg.setFont(new Font("Tahoma", Font.BOLD, 16));
			contentPanel.add(btnReg);
		}
		{
			nev = new JTextField();
			nev.setBounds(165, 25, 107, 29);
			nev.setColumns(10);
			contentPanel.add(nev);
		}
		
		jelszo = new JTextField();
		jelszo.setBounds(79, 89, 107, 25);
		jelszo.setColumns(10);
		contentPanel.add(jelszo);
		{
			lblJelszo = new JLabel("Jelsz\u00F3:");
			lblJelszo.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblJelszo.setBounds(10, 85, 156, 26);
			contentPanel.add(lblJelszo);
		}
	}
	
	public void uzenet(String msg) {
		JOptionPane.showMessageDialog(null, msg, "�zenet", 1);
	}
	
	
	public boolean hossz(JTextField jtf) {
		String s = jtf.getText();
		if(s.length()>=5) return true;
		return false;
	}
	
	
	public String RTF(JTextField jtf) {
		return jtf.getText();
	}
	
	
	public static String getNev() {
		return nev.getText();
	}
	
	
	public boolean filledTF(JTextField jtf) {
		String s = jtf.getText();
		if(s.length()>0) return true;
		return false;
	}

}
