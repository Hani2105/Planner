/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.nner;

import com.sun.java.accessibility.util.SwingEventMonitor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.Timer;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import org.joda.time.DateTime;
import static pl.nner.MainForm.TOP;
import static pl.nner.PlNner.MSZAK;

/**
 *
 * @author krisztian_csekme1
 */
public class Plan extends JDesktopPane implements Serializable {

    public List<Station> STATIONS = new ArrayList<>();
    public List<JInternalFrame> WINDOWS = new ArrayList<>();

    public PlanOut OUT = new PlanOut(MSZAK); //12 órás műszak
    public DateTime weekdate;
    private int week_index;
    public DateTime now;
    public Timer orajel;
    public boolean NEED_TO_SAVE;
  
  
    
    @Override
    public void paint(Graphics g) {
 
        Graphics2D gd = (Graphics2D) g;
        
        gd.setColor(Color.DARK_GRAY);
        gd.setFont(new Font(gd.getFont().getName(), Font.PLAIN, 26));
        gd.drawString("Gyártás: " + Integer.toString(week_index) + ". hét.", 20, 30);
        gd.drawString("Gyártás: " + Integer.toString(weekdate.getYear()) + ". év.", 20, 60);
        
        gd.setFont(new Font(gd.getFont().getName(), Font.PLAIN, 16));
        gd.drawString("Terv: " + getName(), 20, 90);
       

        
        super.paint(gd);
    }

    public ActionListener timer = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            now = new DateTime(); //Jelenlegi időpont    
            for (int i = 0; i < WINDOWS.size(); i++) {
                if (WINDOWS.get(i).equals(getSelectedFrame())) {

                    STATIONS.get(i).setSelected(true);

                    STATIONS.get(i).repaint();
                } else {
                    STATIONS.get(i).setSelected(false);
                    STATIONS.get(i).repaint();
                }
            }
        }
    };

    public ActionListener action = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            for (int i = 0; i < WINDOWS.size(); i++) {
                if (WINDOWS.get(i).equals(getSelectedFrame())) {

                    STATIONS.get(i).setSelected(true);
                    try {
                        // now = new DateTime(); //Jelenlegi időpont    
                        STATIONS.get(i).calc();

                    } catch (Exception ef) {

                    }
                    STATIONS.get(i).repaint();
                } else {
                    STATIONS.get(i).setSelected(false);
                    STATIONS.get(i).repaint();
                }
            }

        }
    };

    public int getWeekIndex() {
        return week_index;
    }

    public void setWeekIndex(int year, int week) {
        week_index = week;
        weekdate = PlNner.getFirstMondayDayOfWeek(year, week).plusHours(5);
    }

  
    
    
    
    public Plan() {
        NEED_TO_SAVE = false;
        now = new DateTime();
        setOpaque(false);
        add(OUT, BorderLayout.CENTER);
        SwingEventMonitor.addInternalFrameListener(new InternalFrameAdapter() {
 
        
         @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                System.out.println("internalFrameClosed....");
                //do some cleaning here
                super.internalFrameClosed(e);
               
            }
        
        
        })
                ;
        
        
        OUT.setResizable(true);
        OUT.addMouseListener(new MouseListener() {

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
            }

            @Override
            public void mouseExited(MouseEvent e) {
                try {
                    OUT.setRequestFocusEnabled(false);
                    OUT.setSelected(false);
                    OUT.setFocusable(false);
                } catch (PropertyVetoException ex) {
                    Logger.getLogger(Plan.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        OUT.setIconifiable(true);
        OUT.setTitle("Terv kimenet");
        OUT.setVisible(true);

        try {

            OUT.setIcon(true);
        } catch (PropertyVetoException ex) {

        }

        orajel = new Timer(10, timer);
        orajel.start();

    }

    public int getSelectedStation() {

        for (int i = 0; i < STATIONS.size(); i++) {
            if (STATIONS.get(i).getSelected()) {
                return i;
            }
        }
        return -1;
    }

    public void clearSelection() {
        for (Station elem : STATIONS) {
            elem.setSelected(false);
            elem.repaint();
        }
    }
}
