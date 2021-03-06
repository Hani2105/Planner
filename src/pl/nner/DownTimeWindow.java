/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.nner;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.DecimalFormat;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import static pl.nner.PlNner.PLANS;

/**
 *
 * @author krisztian_csekme1
 */
public class DownTimeWindow extends javax.swing.JDialog {

    public Station selected_station;

    /**
     * Creates new form DownTimeWindow
     */
    public DownTimeWindow(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setCenter();
    }

    
      
    void setCenter() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(((dim.width - this.getSize().width) / 2), ((dim.height - this.getSize().height) / 2));

    }
    
    public void init(Station station) {
        selected_station = station;
        DefaultTableModel model = PlNner.MYDB_DB.getDataTableModel("SELECT * from downtime_types");
        COMBO_TYPE.removeAllItems();
        for (int r=0; r<model.getRowCount(); r++){
            COMBO_TYPE.addItem(model.getValueAt(r, 1));
        }
        SAMPLE_LABEL.setText(COMBO_TYPE.getSelectedItem().toString());
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        BG_COLOR_PANEL = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        FG_COLOR_PANEL = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        SAMPLE = new javax.swing.JPanel();
        SAMPLE_LABEL = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSlider1 = new javax.swing.JSlider();
        COMBO_TYPE = new javax.swing.JComboBox();
        HH = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        align = new javax.swing.JCheckBox();
        COMBO_DAY = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        COMBO_HOUR = new javax.swing.JComboBox();

        setTitle("Állásidő szerkesztő");

        jLabel1.setText("Állásidő hozzáadása:");

        jLabel2.setText("Típus:");

        BG_COLOR_PANEL.setBackground(new java.awt.Color(255, 102, 102));
        BG_COLOR_PANEL.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        BG_COLOR_PANEL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BG_COLOR_PANELMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout BG_COLOR_PANELLayout = new javax.swing.GroupLayout(BG_COLOR_PANEL);
        BG_COLOR_PANEL.setLayout(BG_COLOR_PANELLayout);
        BG_COLOR_PANELLayout.setHorizontalGroup(
            BG_COLOR_PANELLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 52, Short.MAX_VALUE)
        );
        BG_COLOR_PANELLayout.setVerticalGroup(
            BG_COLOR_PANELLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel3.setText("Háttérszín:");

        jButton1.setText("Hozzáadás");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setText("Szövegszín:");

        FG_COLOR_PANEL.setBackground(new java.awt.Color(255, 255, 255));
        FG_COLOR_PANEL.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        FG_COLOR_PANEL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FG_COLOR_PANELMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout FG_COLOR_PANELLayout = new javax.swing.GroupLayout(FG_COLOR_PANEL);
        FG_COLOR_PANEL.setLayout(FG_COLOR_PANELLayout);
        FG_COLOR_PANELLayout.setHorizontalGroup(
            FG_COLOR_PANELLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 53, Short.MAX_VALUE)
        );
        FG_COLOR_PANELLayout.setVerticalGroup(
            FG_COLOR_PANELLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel5.setText("Minta:");

        SAMPLE.setBackground(new java.awt.Color(255, 102, 102));
        SAMPLE.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        SAMPLE_LABEL.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        SAMPLE_LABEL.setForeground(new java.awt.Color(255, 255, 255));
        SAMPLE_LABEL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SAMPLE_LABEL.setText("Karbantartás");

        javax.swing.GroupLayout SAMPLELayout = new javax.swing.GroupLayout(SAMPLE);
        SAMPLE.setLayout(SAMPLELayout);
        SAMPLELayout.setHorizontalGroup(
            SAMPLELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SAMPLELayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SAMPLE_LABEL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        SAMPLELayout.setVerticalGroup(
            SAMPLELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SAMPLELayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SAMPLE_LABEL)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jLabel6.setText("Állásidő hossza:");

        jSlider1.setMaximum(4320);
        jSlider1.setValue(480);
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });

        COMBO_TYPE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                COMBO_TYPEActionPerformed(evt);
            }
        });

        HH.setText("8");
        HH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                HHKeyReleased(evt);
            }
        });

        jLabel7.setText("óra");

        align.setText("igazítás az alábbi időponthoz:");

        COMBO_DAY.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Hétfő", "Kedd", "Szerda", "Csütörtök", "Péntek", "Szombat", "Vasárnap" }));

        jLabel8.setText("Nap:");

        jLabel9.setText("Óra:");

        COMBO_HOUR.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(SAMPLE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel4)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(FG_COLOR_PANEL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabel5)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel3)
                                            .addGap(18, 18, 18)
                                            .addComponent(BG_COLOR_PANEL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(60, 60, 60)))
                            .addComponent(jLabel6)
                            .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(104, 104, 104)
                                .addComponent(HH, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(COMBO_TYPE, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(align)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel9)
                                        .addComponent(jLabel8))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(COMBO_DAY, 0, 110, Short.MAX_VALUE)
                                        .addComponent(COMBO_HOUR, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                        .addGap(0, 57, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(COMBO_TYPE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(BG_COLOR_PANEL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(FG_COLOR_PANEL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SAMPLE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(HH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(12, 12, 12)
                .addComponent(align)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(COMBO_DAY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(COMBO_HOUR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BG_COLOR_PANELMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BG_COLOR_PANELMouseClicked
        Color background = JColorChooser.showDialog(null, "JColorChooser Sample", BG_COLOR_PANEL.getBackground());
        if (background != null) {
            BG_COLOR_PANEL.setBackground(background);
            SAMPLE.setBackground(background);
        }
    }//GEN-LAST:event_BG_COLOR_PANELMouseClicked

    private void FG_COLOR_PANELMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FG_COLOR_PANELMouseClicked
        Color fore = JColorChooser.showDialog(null, "JColorChooser Sample", FG_COLOR_PANEL.getBackground());
        if (fore != null) {
            FG_COLOR_PANEL.setBackground(fore);
            SAMPLE_LABEL.setForeground(fore);
        }

    }//GEN-LAST:event_FG_COLOR_PANELMouseClicked

    private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider1StateChanged
        DecimalFormat df = new DecimalFormat("#.#");
        HH.setText(df.format((double) jSlider1.getValue() / 60));
    }//GEN-LAST:event_jSlider1StateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
           
        int no_ = selected_station.DOWNTIMES.size();
            
            DownTime DT = new DownTime(SAMPLE.getBackground(), SAMPLE_LABEL.getForeground(),  PLANS.get(MainForm.TOP.getSelectedIndex()).weekdate);
            DT.setName(COMBO_TYPE.getSelectedItem().toString() + "_" + no_);
            DT.setSize((jSlider1.getValue() / 60 ) * selected_station.IND_HOUR, 10);
            DT.setLength(jSlider1.getValue());
            
            if (align.isSelected()){
              int day = COMBO_DAY.getSelectedIndex();
              int hour = COMBO_HOUR.getSelectedIndex();
              if (day==0){
               if (hour<6){
                   hour=6;
               }   
              }
                DT.setLocation(selected_station._getBorder() + (day * (selected_station.IND_HOUR * 24)) - (selected_station.IND_HOUR * 6) + (selected_station.IND_HOUR * hour), selected_station._getTop() - DT.getSize().height);
            
            
            }else{
             DT.setLocation(selected_station._getBorder(), selected_station._getTop() - DT.getSize().height);
                
            }
            
            
            selected_station.add(DT);
            selected_station.DOWNTIMES.add(DT);
           
            
             this.setVisible(false);
            
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void HHKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HHKeyReleased
        String value = HH.getText().replace(",", ".");
        double real = Double.parseDouble(value);
        jSlider1.setValue((int)real * 60);
    }//GEN-LAST:event_HHKeyReleased

    private void COMBO_TYPEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_COMBO_TYPEActionPerformed
        try{
        SAMPLE_LABEL.setText(COMBO_TYPE.getSelectedItem().toString());
        }catch(NullPointerException er){
            
        }
    }//GEN-LAST:event_COMBO_TYPEActionPerformed

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
            java.util.logging.Logger.getLogger(DownTimeWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DownTimeWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DownTimeWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DownTimeWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DownTimeWindow dialog = new DownTimeWindow(new javax.swing.JFrame(), true);
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
    private javax.swing.JPanel BG_COLOR_PANEL;
    private javax.swing.JComboBox COMBO_DAY;
    private javax.swing.JComboBox COMBO_HOUR;
    private javax.swing.JComboBox COMBO_TYPE;
    private javax.swing.JPanel FG_COLOR_PANEL;
    private javax.swing.JTextField HH;
    private javax.swing.JPanel SAMPLE;
    private javax.swing.JLabel SAMPLE_LABEL;
    private javax.swing.JCheckBox align;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSlider jSlider1;
    // End of variables declaration//GEN-END:variables
}
