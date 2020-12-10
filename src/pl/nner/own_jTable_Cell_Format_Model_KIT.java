package pl.nner;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.Color;
import javax.swing.ImageIcon;
import static pl.nner.PlNner.PLANS;

/**
 *
 * @author krisztian_csekme Speciális cella formázására létrehozott cella render
 */
public class own_jTable_Cell_Format_Model_KIT extends DefaultTableCellRenderer {
Station station;
ImageIcon icon = new ImageIcon(this.getClass().getResource("IMG/kitted.png"));
    
    public own_jTable_Cell_Format_Model_KIT(Station st){
        station = st;
    }
    
    
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

//A tábla eredeti cell render komponense létrehozva és boxolva egy JLabel componensé
        JLabel lbl = ((JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column));
        lbl.setOpaque(true);
        
        if (value!=null){
             lbl.setText((String) value);
            for (int i=0; i<station.JOBS.size(); i++){
                if (station.isKitted(value.toString())){
                    lbl.setIcon(icon);
                }else{
                    lbl.setIcon(null);
                }
            }
            
        }

     

      

        return lbl;
    }

}
