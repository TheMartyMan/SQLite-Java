package Program;

import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class FilmTM extends DefaultTableModel {
	public FilmTM (Object mezonevek[], int sor){
		super(mezonevek, sor);
		}
	
	
	
	public boolean isCellEditable(int sor, int oszlop) {
		if (oszlop == 0) {return true;}
		return false;
		}
	
	
	
	public Class<?> getColumnClass(int index){
		if (index == 0) return(Boolean.class);
		else if (index == 1 || index == 5) return(Integer.class);
		return(String.class);
		}
}
