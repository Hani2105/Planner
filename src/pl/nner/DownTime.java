/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.nner;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.text.DecimalFormat;
import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import static pl.nner.PlNner.PLANS;

/**
 *
 * @author krisztian_csekme1
 */
public class DownTime extends JPanel {
    
    private Point anchorPoint;
    Color BG, FG;
    DateTime StartTime, EndTime, weekdate;
    public int m;
    private  JPopupMenu POP;
    
    public int getLength() {
        return m;
    }
    
    public void setLength(int len) {
        m = len;
    }
    
   private ActionListener _DEL_A = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
             Station st =  (Station)DownTime.this.getParent();
            /*
             public void deleteProduct() {
             int res = JOptionPane.showConfirmDialog(null, "Biztos vagy benne hogy törölni szeretnéd a " + PRODUCTS.get(getSelectedProduct()).getPartnumber(), "Figyelem!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

             if (res == JOptionPane.YES_OPTION) {
             Component[] comps = getComponents();
             for (Component cmp : comps) {
             try {
             Product pr = (Product) cmp;
             if (pr.equals(PRODUCTS.get(getSelectedProduct()))) {
             remove(cmp);
             }
             } catch (ClassCastException ex) {

             }
             }

             PRODUCTS.remove(getSelectedProduct());
             sort();
             setVSize();
             repaint();
             }

             }
             */
            int res = JOptionPane.showConfirmDialog(null, "Biztos vagy benne hogy törölni szeretnéd a " + getName() + " állást?", "Figyelem!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (res == JOptionPane.YES_OPTION) {
                for (int i = 0; i < st.DOWNTIMES.size(); i++) {
                    if (st.DOWNTIMES.get(i).getName().equals(getName())) {
                        st.DOWNTIMES.remove(i);
                    }
                }
                
                Component[] comps = st.getComponents();
                for (Component cmp : comps) {
                    
                    try {
                        DownTime dt = (DownTime) cmp;
                        if (dt.getName().equals(getName())) {
                            st.remove(cmp);
                        }
                        
                    } catch (ClassCastException ex) {
                        
                    }
                }
            }
            
        }
        
    };
    
    public DownTime(Color BACK, Color FORE, DateTime week) {
        weekdate = week;
        BG = BACK;
        FG = FORE;
        setOpaque(false);
        setMoveAble();
        
        POP = new JPopupMenu();
        JMenuItem _DEL = new JMenuItem("Törlés");
        _DEL.addActionListener(_DEL_A);
        POP.add(_DEL);
        
        
        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            if (e.isPopupTrigger()){
                POP.show(DownTime.this, e.getX(),e.getY());
            }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                calc();
                int min = Minutes.minutesBetween(StartTime, EndTime).getMinutes();
                double h = min / 60;
                String[] _t = DownTime.this.getName().split("_");
                DownTime.this.setToolTipText(_t[0] + " " + new DecimalFormat("#.##").format(h) + " óra");
                
                setBorder(BorderFactory.createLineBorder(Color.yellow));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBorder(BorderFactory.createEmptyBorder());
            }
        });
    }
    
    @Override
    public void paint(Graphics g) {
        Graphics2D gd = (Graphics2D) g;
        
        gd.setColor(BG);
        
        gd.fillRect(0, 0, getWidth(), getHeight());
        
        gd.setColor(FG);
        
        gd.setFont(new Font(gd.getFont().getName(), Font.BOLD, 10));
        String[] _t = getName().split("_");
        gd.drawString(_t[0], (getWidth() / 2) - (gd.getFontMetrics().stringWidth(getName()) / 2), 8);
        
        super.paint(gd);
    }
    
    public void calc() {

        /*
         Station st = PLANS.get(MainForm.jTabbedPane.getSelectedIndex()).STATIONS.get(PLANS.get(MainForm.jTabbedPane.getSelectedIndex()).getSelectedStation());
         //System.out.println();
         //  double m = _Qty / (60 / _Cycle) ; //Standard gyártási hossz
         double m = _Qty / (60 / _Cycle * ((double) _Eff / 100) * _Panelization) + StartUpMin + DownTimeMin; //Standard gyártási hossz
         // setSize((int) (m / 12), 25);
         setSize((int) (m / (60/st.IND_HOUR)), 25);
         //double full_pos_start = ((double) getLocation().x - (double) PLANS.get(MainForm.jTabbedPane.getSelectedIndex()).STATIONS.get(PLANS.get(MainForm.jTabbedPane.getSelectedIndex()).getSelectedStation())._getBorder()) / (double) PLANS.get(MainForm.jTabbedPane.getSelectedIndex()).STATIONS.get(PLANS.get(MainForm.jTabbedPane.getSelectedIndex()).getSelectedStation()).getZ_Level();
         double full_pos_start = (((double) getLocation().x - (double) st._getBorder()))
         //   / (double) PLANS.get(MainForm.jTabbedPane.getSelectedIndex()).STATIONS.get(PLANS.get(MainForm.jTabbedPane.getSelectedIndex()).getSelectedStation()).getZ_Level();
         / st.IND_HOUR;
         int hour_start = (int) full_pos_start;
         //  double min_start=0;
         double min_start = 60 * (full_pos_start - hour_start);
            
         //System.out.println("min_Start: " + min_start);
         StartTime = weekdate.plusHours(hour_start).plusMinutes((int) min_start);
         EndTime = StartTime.plusMinutes((int) m);
         */
         Station st =  (Station)DownTime.this.getParent();
        double full_pos_start = ((double) getLocation().x - (double) st._getBorder()) / (double) st.getZ_Level();
        int hour_start = (int) full_pos_start;
        double min_start = 60 * (full_pos_start - hour_start);
        
        StartTime = weekdate.plusHours(hour_start).plusMinutes((int) min_start);
        EndTime = StartTime.plusMinutes((int) m);
        
    }
    
    private void setMoveAble() {
        /**
         * This handle is a reference to THIS because in next Mouse Adapter
         * "this" is not allowed
         */
        @SuppressWarnings("unused")
        final DownTime handle = DownTime.this;
        handle.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                anchorPoint = e.getPoint();
                
            }
            
            @Override
            public void mouseDragged(MouseEvent me) {
                
                me.translatePoint(me.getComponent().getLocation().x, me.getComponent().getLocation().y);
                
                handle.setLocation(me.getX() - anchorPoint.x, handle.getY());
                calc();
                
                setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                
            }
            
        });
    }
    
}
