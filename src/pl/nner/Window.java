/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.nner;

import java.awt.AWTEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import static pl.nner.PlNner.PLANS;

/**
 *
 * @author krisztian_csekme1
 */
public class Window extends JInternalFrame implements AWTEventListener {
    
    public boolean CTRL;
    public boolean SHIFT;
    

    public Window(String name) {
        setName(name);
        setTitle("[ " + name + " ] - állomás szerkesztő");
        this.getToolkit().addAWTEventListener(this, AWTEvent.KEY_EVENT_MASK);
        
        
        
        
    }
 
    
    
    @Override
    public void eventDispatched(AWTEvent event) {
 
        if (event instanceof KeyEvent) {
            KeyEvent key = (KeyEvent) event;
            
            
            
            if (key.getID() == KeyEvent.KEY_PRESSED) { //Handle key presses
                if (key.isControlDown()) {
                    CTRL = true;
                }
                
                if (key.isShiftDown()) {
                    
                     SHIFT = !SHIFT;                    
                    
                    
                }
                
                
                

            }
        

            if (key.getID() == KeyEvent.KEY_RELEASED) { //Handle key presses
                    
                CTRL = false;
                    
            }
        }
     }
}
