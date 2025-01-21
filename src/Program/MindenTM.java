package Program;

import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class MindenTM extends DefaultTableModel {
	public MindenTM (Object mezonevek[], int sor){
		super(mezonevek, sor);
		}
	
	public boolean isCellEditable(int sor, int oszlop) {
		if (oszlop == 0) {return true;}
		return false;
		}
	
	public Class<?> getColumnClass(int index){
		if (index == 0) return(Boolean.class);
		else if (index == 1 || index == 5 || index == 6 || index == 10) return(Integer.class);
		return(String.class);
		}
}
