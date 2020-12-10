/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.nner;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.text.DecimalFormat;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import pl.nner.MyToolTip.IGAZITAS;

/**
 *
 * @author krisztian_csekme1
 */
class MyToolTip extends JDialog {
  String text;
  IGAZITAS Arrange;
  public static enum IGAZITAS{BALRA,Középre};
     

    JPanel panel = new JPanel() {

        @Override
        public void paint(Graphics g) {
            Graphics2D graph = (Graphics2D) g;
             
            graph.setColor(new Color(254, 255, 188));
            graph.fillRect(0, 0, getWidth(), getHeight());
            graph.setColor(Color.BLACK);
            graph.setFont(new Font("Arial", Font.PLAIN,10));
            
            String[] lines = text.split("<br>".toLowerCase());
             if (Arrange == IGAZITAS.BALRA){
                 for (int l=0; l<lines.length; l++){
              graph.drawString(lines[l], 10, (l * 15) + 15 );
            } 
             }else if (Arrange == IGAZITAS.Középre){
                for (int l=0; l<lines.length; l++){
              graph.drawString(lines[l], (getWidth()/2)-graph.getFontMetrics().stringWidth(lines[l])/2 , (l * 15) + 15 );
            } 
             }
            
            super.paint(graph);
        }

    };

    
    
    public void update(Point p, String text, IGAZITAS Arrange){
        this.text = text;
        this.Arrange = Arrange;
        setPosition(p);
        repaint();
    }
    
    
    public MyToolTip(int width, int height) {
        setSize(width, height);
        setModal(false);
        setUndecorated(true);
        setOpacity(0.80f);
        panel.setOpaque(false);
        setContentPane(panel);
    }

    public void setPosition(Point p) {
        
            setLocation(p.x + 20, p.y  + 10);
       

        setVisible(true);

    }

}
