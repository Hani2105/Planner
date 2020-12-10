/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.nner;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.DecimalFormat;
import javax.swing.Timer;
import org.joda.time.DateTime;
import static pl.nner.PlNner.PLANS;

/**
 *
 * @author krisztian_csekme1
 */
public class SplitWindow extends javax.swing.JDialog {
private Product pro;
private Point anchorPoint;
    /**
     * Creates new form SplitWindow
     */
    public SplitWindow(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setCenter();
        setMoveAble();
         Timer timer = new Timer(50,action);
        timer.start();
    }
    
      void setCenter() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(((dim.width - this.getSize().width) / 2), ((dim.height - this.getSize().height) / 2));
       
    }
    
      ActionListener action = new ActionListener() {

    @Override
    public void actionPerformed(ActionEvent e) {
        
        int QT = CUT_SIZE.getValue();
        int QT2 = CUT_SIZE.getMaximum() - QT;
       
        if (pro!=null){
        
         first_lbl.setText( "<html>" + Integer.toString(QT) +  "db<br>" + new DecimalFormat("#.##").format( (double) QT / (double)pro.getQtyPHour()  )  + " óra</html>");
         second_lbl.setText("<html>" + Integer.toString(QT2) + "db<br>" + new DecimalFormat("#.##").format( (double)QT2 / (double)pro.getQtyPHour()  )  + " óra</html>");
         
        }else{
            first_lbl.setText(null);
            second_lbl.setText(null);
        }
        double per = 450 /  (double)CUT_SIZE.getMaximum();
        
        first.setSize((int)(CUT_SIZE.getValue() * per), 16);
        second.setSize( (int)(((double)CUT_SIZE.getMaximum()*per)) - (int)(CUT_SIZE.getValue() * per) ,16);
        second.setLocation(first.getLocation().x + first.getSize().width, first.getLocation().y);
    }
};
    

    public void init(Product prod){
        pro=prod;
        CUT_SIZE.setMaximum((int)pro.getQty());
        CUT_SIZE.setMinimum(1);
        CUT_SIZE.setValue((int)pro.getQty());
        LABEL_JOB.setText("Job szám: " + pro.getJobnumber());
        LABEL_PARTNUM.setText("Partnumber: " + pro.getPartnumber());
        LABEL_SEQ.setText("Szekvencia: " + PlNner.SEQUENCES[pro.getSequence()]);
        LABEL_TENY.setText("Eddig megvalósult: " +  (int)pro.getSumTenyMSzak()+ " darab");
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        LABEL_JOB = new javax.swing.JLabel();
        LABEL_PARTNUM = new javax.swing.JLabel();
        LABEL_SEQ = new javax.swing.JLabel();
        LABEL_TENY = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        first_lbl = new javax.swing.JLabel();
        second_lbl = new javax.swing.JLabel();
        first = new javax.swing.JPanel();
        CUT_SIZE = new javax.swing.JSlider();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        second = new javax.swing.JPanel();

        setTitle("Termék szétbontása");
        setUndecorated(true);
        setOpacity(0.9F);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/close.png"))); // NOI18N
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Termék adatok:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(0, 0, 0))); // NOI18N

        LABEL_JOB.setText("Job:");

        LABEL_PARTNUM.setText("Termék:");

        LABEL_SEQ.setText("Szekvencia:");

        LABEL_TENY.setText("Eddig megvalósult:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LABEL_JOB)
                    .addComponent(LABEL_PARTNUM)
                    .addComponent(LABEL_SEQ)
                    .addComponent(LABEL_TENY))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LABEL_JOB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LABEL_PARTNUM)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LABEL_SEQ)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LABEL_TENY)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setText("Termék kettébontása:");

        first_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        first_lbl.setText("jLabel2");
        first_lbl.setMaximumSize(new java.awt.Dimension(225, 16));
        first_lbl.setMinimumSize(new java.awt.Dimension(225, 16));

        second_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        second_lbl.setText("jLabel4");
        second_lbl.setMaximumSize(new java.awt.Dimension(225, 16));
        second_lbl.setMinimumSize(new java.awt.Dimension(225, 16));

        first.setBackground(new java.awt.Color(204, 204, 255));
        first.setMaximumSize(new java.awt.Dimension(450, 16));
        first.setMinimumSize(new java.awt.Dimension(0, 16));

        javax.swing.GroupLayout firstLayout = new javax.swing.GroupLayout(first);
        first.setLayout(firstLayout);
        firstLayout.setHorizontalGroup(
            firstLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );
        firstLayout.setVerticalGroup(
            firstLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 16, Short.MAX_VALUE)
        );

        CUT_SIZE.setBackground(new java.awt.Color(255, 255, 255));
        CUT_SIZE.setMaximumSize(new java.awt.Dimension(450, 21));
        CUT_SIZE.setMinimumSize(new java.awt.Dimension(450, 21));
        CUT_SIZE.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                CUT_SIZEMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CUT_SIZEMouseReleased(evt);
            }
        });
        CUT_SIZE.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                CUT_SIZEStateChanged(evt);
            }
        });
        CUT_SIZE.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                CUT_SIZEMouseMoved(evt);
            }
        });
        CUT_SIZE.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                CUT_SIZECaretPositionChanged(evt);
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        CUT_SIZE.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
                CUT_SIZEAncestorMoved(evt);
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        CUT_SIZE.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                CUT_SIZEKeyReleased(evt);
            }
        });
        CUT_SIZE.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                CUT_SIZEVetoableChange(evt);
            }
        });

        jButton2.setText("Mégse");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setText("Rendben");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/bulb.png"))); // NOI18N
        jLabel1.setText("<html>A billentyű jobbra, balra gombjával<br> lehet finom lépésekkel állítani.</html>");

        second.setBackground(new java.awt.Color(204, 255, 204));
        second.setMaximumSize(new java.awt.Dimension(32767, 16));
        second.setMinimumSize(new java.awt.Dimension(0, 16));
        second.setPreferredSize(new java.awt.Dimension(0, 16));

        javax.swing.GroupLayout secondLayout = new javax.swing.GroupLayout(second);
        second.setLayout(secondLayout);
        secondLayout.setHorizontalGroup(
            secondLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        secondLayout.setVerticalGroup(
            secondLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 16, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(first, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(second, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(CUT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1)
                                .addGap(28, 28, 28))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)))
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(first_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(second_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(second_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(first_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(first, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CUT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton2)
                                .addComponent(jButton1))
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(second, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void CUT_SIZEStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_CUT_SIZEStateChanged
   double per = 450 /  (double)CUT_SIZE.getMaximum();
        
        first.setSize((int)(CUT_SIZE.getValue() * per), 16);
        second.setSize( (int)(((double)CUT_SIZE.getMaximum()*per)) - (int)(CUT_SIZE.getValue() * per) ,16);
        second.setLocation(first.getLocation().x + first.getSize().width, first.getLocation().y);  
         
    }//GEN-LAST:event_CUT_SIZEStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      int QT = CUT_SIZE.getValue();
      int QT2 = CUT_SIZE.getMaximum() - QT;
      
      
        Product pro2 = new Product(PLANS.get(MainForm.TOP.getSelectedIndex()).weekdate);
            
            pro2.setJobnumber(pro.getJobnumber());
            pro2.setPartNumber(pro.getPartnumber());
            pro2.setSequence(pro.getSequence());
            pro2.setQty(QT2);
            pro2.setPanelization(pro.getPanelization());
            pro2.setCycle(pro.getCycle());
            pro2.setEff(pro.getEff());
            pro2.setStartUpMin(pro.getStartUpMin());
            pro2.setDownTimeMin(pro.getDownTimeMin());
            pro2.setFGColor(pro.getFGColor());
            pro2.setBGColor(pro.getBGColor());
            
            Station station = PLANS.get(MainForm.TOP.getSelectedIndex()).STATIONS.get(PlNner.PLANS.get(MainForm.TOP.getSelectedIndex()).getSelectedStation());

            
            station.runcode++;
            pro2.setID(Integer.toString(new DateTime().getYear()).substring(2) + String.format("%02d", PLANS.get(MainForm.TOP.getSelectedIndex()).getWeekIndex()) +String.format("%03d", station.runcode) + "#" + station.station_id );
            
            
            station.addProductWithLocation(pro2, pro.getLocation().x, pro.getLocation().y + 20);
            pro2.resize();
            station.sort();
            
      
      pro.setQty(QT);
      
      pro.resize();
        setVisible(false);
      
    }//GEN-LAST:event_jButton1ActionPerformed

    
    
     private void setMoveAble() {
        /**
         * This handle is a reference to THIS because in next Mouse Adapter
         * "this" is not allowed
         */
        @SuppressWarnings("unused")
        final SplitWindow handle = SplitWindow.this;
        handle.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                anchorPoint = e.getPoint();
            }

            @Override
            public void mouseDragged(MouseEvent me) {
                me.translatePoint(me.getComponent().getLocation().x, me.getComponent().getLocation().y);

                handle.setLocation(me.getX() - anchorPoint.x, me.getY() - anchorPoint.y);

            }

        });
    }
    
    
    
    
    private void CUT_SIZEMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CUT_SIZEMouseMoved
           
    }//GEN-LAST:event_CUT_SIZEMouseMoved

    private void CUT_SIZEMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CUT_SIZEMousePressed
        
    }//GEN-LAST:event_CUT_SIZEMousePressed

    private void CUT_SIZEMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CUT_SIZEMouseReleased
     
    }//GEN-LAST:event_CUT_SIZEMouseReleased

    private void CUT_SIZEAncestorMoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_CUT_SIZEAncestorMoved
      
    }//GEN-LAST:event_CUT_SIZEAncestorMoved

    private void CUT_SIZECaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_CUT_SIZECaretPositionChanged
         
    }//GEN-LAST:event_CUT_SIZECaretPositionChanged

    private void CUT_SIZEKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CUT_SIZEKeyReleased
          
    }//GEN-LAST:event_CUT_SIZEKeyReleased

    private void CUT_SIZEVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_CUT_SIZEVetoableChange
          
    }//GEN-LAST:event_CUT_SIZEVetoableChange

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        setVisible(false);
    }//GEN-LAST:event_jLabel6MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SplitWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SplitWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SplitWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SplitWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SplitWindow dialog = new SplitWindow(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSlider CUT_SIZE;
    private javax.swing.JLabel LABEL_JOB;
    private javax.swing.JLabel LABEL_PARTNUM;
    private javax.swing.JLabel LABEL_SEQ;
    private javax.swing.JLabel LABEL_TENY;
    private javax.swing.JPanel first;
    private javax.swing.JLabel first_lbl;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel second;
    private javax.swing.JLabel second_lbl;
    // End of variables declaration//GEN-END:variables
}