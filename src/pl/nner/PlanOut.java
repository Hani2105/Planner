/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.nner;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.Serializable;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import static pl.nner.PlNner.list_napok;
/**
 *
 * @author Csekme
 */
public class PlanOut extends JInternalFrame implements Serializable{
    public JTable table;
    public Object[] head;
    private JScrollPane pane;
    Point anchorPoint;
    
    private String[] szak = new String[]{"Dé","Du","Éjj"};
    
    
    
    
    
    
    
    public void setTheme(){
                 for (int i = 0; i < table.getColumnCount(); i++) {

                        switch (i) {
                            case 0:
                                table.getColumnModel().getColumn(i).setWidth(60);
                                table.getColumnModel().getColumn(i).setPreferredWidth(60);
                                break;

                            case 1:
                                table.getColumnModel().getColumn(i).setWidth(200);
                                table.getColumnModel().getColumn(i).setPreferredWidth(200);
                                break;

                            case 2:
                                table.getColumnModel().getColumn(i).setWidth(40);
                                table.getColumnModel().getColumn(i).setPreferredWidth(40);
                                break;
                            case 3:
                                table.getColumnModel().getColumn(i).setWidth(80);
                                table.getColumnModel().getColumn(i).setPreferredWidth(80);
                                break;
                            default:
                                table.getColumnModel().getColumn(i).setHeaderRenderer(new MyTableHeaderCellVerticalRenderer(null, Color.darkGray));
                                table.getColumnModel().getColumn(i).setCellRenderer(new own_jTable_Cell_Format_Model());
                                table.getColumnModel().getColumn(i).setWidth(43);
                                table.getColumnModel().getColumn(i).setPreferredWidth(43);
                                break;

                        }

                    }
    }
    
    
    
    public PlanOut(int oras_mszak){
        super();
        String fejlec="ID,Partnumber,Seq,Job,Terv/Tény,";
        //String nap=list_napok[0];
        int szak_i=0;
        int nap_i=0;
        
        for (int i=0; i<168; i++){
          if (oras_mszak==12){
              if (i%12==0){
               
               fejlec += list_napok[nap_i] + " - " + szak[szak_i] + ",";
               szak_i++;
               if (szak_i==2){
                   szak_i=0;
                   nap_i++;
               }
              }
          }    
                  
        }
        fejlec = fejlec.substring(0,fejlec.length()-1);
        head = fejlec.split(",");
        table = new JTable(new DefaultTableModel(head,0));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectionBackground(Color.red);
        table.setCellSelectionEnabled(true);
        table.setColumnSelectionAllowed(true);
        
        
        setSize(1200, 400);
        pane = new JScrollPane();
        this.add(pane);
        pane.getViewport().add(table);
        setTheme();
        
    }
    
    
    
    
       private void setMoveAble() {
        /**
         * This handle is a reference to THIS because in next Mouse Adapter
         * "this" is not allowed
         */
        @SuppressWarnings("unused")
        final PlanOut handle = PlanOut.this;
        handle.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                anchorPoint = e.getPoint();
                
                
           
                
                
            }

            @Override
            public void mouseDragged(MouseEvent me) {
                me.translatePoint(me.getComponent().getLocation().x, me.getComponent().getLocation().y );
                handle.setLocation(me.getX() - anchorPoint.x, me.getY() - anchorPoint.y);
                setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            }

            
        });
    }
    
    
    
    
    
    
}
