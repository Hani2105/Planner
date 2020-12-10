/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.nner;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import org.joda.time.DateTime;

/**
 *
 * @author krisztian_csekme1
 */
public class TimeStamp extends JLabel {

    Station station;
    DateTime time;
    MyToolTip tip;
    String product_id=null;
    Product prod;
    PopupMenu menu;
    MenuItem menu_del;
    
    private int no;
   
    private Point anchorPoint;

    
    ActionListener A_DEL = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (prod!=null){
            prod.ts=null;
            product_id = "";
            }
            for (int i=0; i<station.TIMESTAMPES.size(); i++){
                if (station.TIMESTAMPES.get(i).no == TimeStamp.this.no){
                    station.TIMESTAMPES.remove(i);
                }
            }
            
             Component[] comps = station.TOP_PANEL.getComponents();
        for (Component cmp : comps) {
            try {
                TimeStamp tm = (TimeStamp) cmp;
                if (tm.no==TimeStamp.this.no) {
                    station.TOP_PANEL.remove(cmp);
                }
            } catch (ClassCastException ex) {

            }
        }
         System.out.println("");
            
        }
    };
    
    TimeStamp(Station station, int x) {
        menu = new PopupMenu();
        menu_del = new MenuItem("Törlés");
        menu_del.addActionListener(A_DEL);
        menu.add(menu_del);
        add(menu);
        this.station = station;
        if (this.station.IND_HOUR>0)
        this.time = this.station.getPlan().weekdate.plusMinutes((x  - this.station._getBorder()) * (60/this.station.IND_HOUR));
        setSize(new Dimension(21, 33));
        setMinimumSize(new Dimension(21, 33));
        setMaximumSize(new Dimension(21, 33));

        setOpaque(false);
        tip = new MyToolTip(280, 25);

        setMoveAble();
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
                    menu.show(TimeStamp.this, e.getX(), e.getY());
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                TimeStamp.this.station.sortTimeStamp();
                tip.update(new Point(e.getLocationOnScreen()), "No.: " + Integer.toString(no) + "   <" +  PlNner.getCurrentDateTime(TimeStamp.this.time)+">",MyToolTip.IGAZITAS.Középre);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                tip.setVisible(false);
            }
        });

    }

    
    
    public void setNo(int no){
        this.no = no;
    }
    
    public int getNo(){
        return no;
    }
    
    
    public void init(){
        if (prod==null){
         for (int i=0; i<station.PRODUCTS.size(); i++){
                  if (station.PRODUCTS.get(i).getID().equals(product_id)){
                      this.prod = station.PRODUCTS.get(i);
                      this.prod.setTimeStamp(this);
                      
                      
                  }
              }
        }
        
        if (prod!=null){
         prod.setLocation(this.getLocation().x +10, prod.getLocation().y);
         prod.storedProduceMin=false;
         prod.drag=false;
         station.drag=false;
         prod.resize();
        }
         
    }
    
    public void addProduct(String product_id) {
        if (this.product_id == null) {
            this.product_id = product_id;
            
                 init(); 
             station.sortTimeStamp();
            
            
        } else {
            init();
            JOptionPane.showMessageDialog(null, "Ehhez az időbélyeghez már foglalt");
        }
    }

    public void relocate() {
         time = station.getPlan().weekdate.plusMinutes((getLocation().x +10 - station._getBorder()) * (60 / station.IND_HOUR));
       // setLocation((int) ((double) ((Minutes.minutesBetween(station.getPlan().weekdate, time).getMinutes() / 60) * (double) (station.IND_HOUR)) + station._getBorder()-10 ), getLocation().y);
        //TOP_PANEL.setToolTipText(getPlan().weekdate.plusMinutes((P_MOUSE_X - _getBorder()) * (60/IND_HOUR)).toString());
    }

    public void add() {

        station.TOP_PANEL.add(this);

    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graph = (Graphics2D) g;
        
        if (prod!=null){
        graph.setColor(prod.getBGColor());
        graph.fillOval(3, 3, 15, 15);
        }
        graph.drawImage(new ImageIcon(this.getClass().getResource("IMG/time_stamp.png")).getImage(), 0, 0, this);
        
        super.paint(graph);

    }

    private void setMoveAble() {
        /**
         * This handle is a reference to THIS because in next Mouse Adapter
         * "this" is not allowed
         */
        @SuppressWarnings("unused")
        final TimeStamp handle = TimeStamp.this;
        handle.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                anchorPoint = e.getPoint();
               
                station.sortTimeStamp();

            }

            @Override
            public void mouseDragged(MouseEvent me) {
                if (station.can_drag){
                
                me.translatePoint(me.getComponent().getLocation().x, me.getComponent().getLocation().y);
                
                handle.setLocation(me.getX() - anchorPoint.x, getLocation().y);
                
                time = station.getPlan().weekdate.plusMinutes((getLocation().x +10 - station._getBorder()) * (60 / station.IND_HOUR));
                relocate() ;
                tip.update(new Point(me.getLocationOnScreen()), "No.: " + Integer.toString(no) + "   <" +  PlNner.getCurrentDateTime(TimeStamp.this.time)+">",MyToolTip.IGAZITAS.Középre);
                init();
                }

            }

        });
    }

}
