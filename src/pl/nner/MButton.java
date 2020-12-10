/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.nner;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

/**
 *
 * @author krisztian_csekme1
 */
public class MButton extends JLabel {
    
    
    
    public MButton(){
        
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        
        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                LineBorder border = (LineBorder)MButton.this.getBorder();
                setBackground(         border.getLineColor()     );
                setOpaque(false);
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }
    
    
    
}
