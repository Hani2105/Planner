package pl.nner;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.Color;
import static pl.nner.PlNner.PLANS;

/**
 *
 * @author krisztian_csekme Speciális cella formázására létrehozott cella render
 */
public class own_jTable_Cell_Format_Model extends DefaultTableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

//A tábla eredeti cell render komponense létrehozva és boxolva egy JLabel componensé
        JLabel lbl = ((JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column));
        lbl.setOpaque(true);
        if (PLANS.get(MainForm.TOP.getSelectedIndex()).OUT.table.getValueAt(row, 4).equals("Terv")) {
            //lbl.setBackground(new Color(240,240,240));
            lbl.setBackground(null);
            lbl.setForeground(Color.black);

        } else if (PLANS.get(MainForm.TOP.getSelectedIndex()).OUT.table.getValueAt(row, 4).equals("Tény")) {
            lbl.setBackground(null);
            lbl.setForeground(Color.black);

        }

      //  lbl.setBackground(null);
        if (value == null) {
            value = "";
        }

        if ((value.toString().equals("0.0") || (value.toString().equals("0")))) {
            value = "";
            lbl.setText("");
        } else {
            lbl.setText(value.toString());
        }

        if (!value.equals("")) {

            if (PLANS.get(MainForm.TOP.getSelectedIndex()).OUT.table.getValueAt(row, 4).equals("Terv")) {
                lbl.setBackground(new Color(0f, 1f, 0f, .2f));
                lbl.setForeground(Color.black);

            } else if (PLANS.get(MainForm.TOP.getSelectedIndex()).OUT.table.getValueAt(row, 4).equals("Tény")) {
                lbl.setBackground(new Color(1f, 0f, 0f, .2f));
                lbl.setForeground(Color.black);

            }

        }

        lbl.setHorizontalAlignment(JLabel.CENTER);

        return lbl;
    }

}
