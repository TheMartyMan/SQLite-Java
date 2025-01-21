package Program;

import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class VevoTM extends DefaultTableModel {
	public VevoTM (Object fildNames[], int rows){
		super(fildNames, rows);
		}
	
	public boolean isCellEditable(int row, int col) {
		if (col == 0) {return true;}
		return false;
		}
	
	public Class<?> getColumnClass(int index){
		if (index == 0) return(Boolean.class);
		else if (index==1) return(Integer.class);
		return(String.class);
		}
}
