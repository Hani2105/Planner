/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.nner;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author krisztian_csekme1
 */
public class MyCellRendererPanelization extends DefaultTableCellRenderer {

    public static enum ALIGN {

        LEFT, CENTER, RIGHT
    };
    private int _align = 0;

    public MyCellRendererPanelization(int align) {
        _align = align;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel label = ((JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column));
         label.setBackground(Color.white);
      
        if (value != null) {
            
            label.setText(value.toString());
            
            
        if (value.toString().equals("OK")){
            label.setBackground(Color.GREEN);
        }
        
        if (value.toString().equals("NG")){
            label.setBackground(Color.red);
        }
            
            
            
            
        } else {
            label.setText("");
        }
      
        

        switch (_align) {
            case LEFT:
                label.setHorizontalAlignment(JLabel.LEFT);
                break;
            case CENTER:
                label.setHorizontalAlignment(JLabel.CENTER);
                break;
            case RIGHT:
                label.setHorizontalAlignment(JLabel.RIGHT);
                break;

        }

        return label;

    }

}
