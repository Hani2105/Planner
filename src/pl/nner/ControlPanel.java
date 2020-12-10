/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.nner;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author krisztian_csekme1
 */
class ComplexCellRenderer implements ListCellRenderer {

    ImageIcon icon_progress = new ImageIcon(this.getClass().getResource("IMG/ajax-loader.gif"));
    ImageIcon empty = new ImageIcon(this.getClass().getResource("IMG/empty.png"));
    protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

    public Component getListCellRendererComponent(JList list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {
        boolean calc = false;
        String theText = null;

        JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
                isSelected, cellHasFocus);

        renderer.setHorizontalAlignment(JLabel.CENTER);
        if (value instanceof Object[]) {
            Object values[] = (Object[]) value;
            theText = (String) values[0];
            calc = (boolean) values[1];

        } else {
            theText = "";
        }
        if (!isSelected) {
            renderer.setForeground(Color.BLUE);
        }
        if (calc == true) {
            renderer.setIcon(icon_progress);
        } else {
            renderer.setIcon(null);
        }
        renderer.setText(theText);
        return renderer;
    }
}

public class ControlPanel extends javax.swing.JDialog {

    private Point anchorPoint;
    ImageIcon need_to_save_icon = new ImageIcon(this.getClass().getResource("IMG/save48.png"));
    ImageIcon bulb = new ImageIcon(this.getClass().getResource("IMG/bulb.png"));

    /**
     * Creates new form ControlPanel
     */
    public ControlPanel(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setMoveAble();
        Timer orajel = new Timer(200, timer);
        orajel.start();
    }

    ActionListener timer = new ActionListener()  {

        @Override
        public void actionPerformed(ActionEvent e) {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {

                    if (PLAN_LIST.getSelectedIndex() > -1) {
                        try{
                        Plan plan = PlNner.PLANS.get(PLAN_LIST.getSelectedIndex());

                        if (plan.NEED_TO_SAVE == true) {
                            TOOLTIP.setIcon(need_to_save_icon);
                            TOOLTIP.setToolTipText("A tervben változás történt menteni kell!");
                        } else {
                            TOOLTIP.setIcon(bulb);
                            TOOLTIP.setToolTipText(getRandomTip());
                        }
                        }catch(IndexOutOfBoundsException e){
                                
                                }
                    }

                    ControlPanel.this.repaint();

                }
            });
        }
    };

    public void tick() {
        
         Image img =  PlNner.getPICS( PlNner.USER.id).getImage();
                Image dimg = img.getScaledInstance(SCREEN.getWidth(), SCREEN.getHeight(), Image.SCALE_SMOOTH);
                SCREEN.setIcon( new ImageIcon(dimg));
        
         
        DefaultListModel MODEL = new DefaultListModel();
        ListCellRenderer renderer = new ComplexCellRenderer();
        PLAN_LIST.setCellRenderer(renderer);
        MODEL.clear();
        if (MainForm.TOP!=null){
        for (int i = 0; i < MainForm.TOP.getTabCount(); i++) {
            Object[] row = new Object[2];

            boolean active = false;
            for (int j = 0; j < PlNner.PLANS.get(i).STATIONS.size(); j++) {

                if (PlNner.PLANS.get(i).STATIONS.get(j).szamol == true) {
                    active = true;
                }
            }
            String name = MainForm.TOP.getComponentAt(i).getName();

            row[0] = name;
            row[1] = active;

            MODEL.addElement(row);
        }
        }
        
        
        PLAN_LIST.setModel(MODEL);
        PLAN_LIST.repaint();

    }

    private void setMoveAble() {
        /**
         * This handle is a reference to THIS because in next Mouse Adapter
         * "this" is not allowed
         */
        @SuppressWarnings("unused")
        final ControlPanel handle = ControlPanel.this;
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        PLAN_LIST = new javax.swing.JList();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        LAST_RELEASED = new javax.swing.JList();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        PANEL = new javax.swing.JPanel();
        TOOLTIP = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        SCREEN = new javax.swing.JLabel();

        jLabel2.setText("jLabel2");

        setTitle("ControlPanel");
        setMinimumSize(new java.awt.Dimension(180, 566));
        setUndecorated(true);
        setOpacity(0.8F);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 102, 255)));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(179, 566));

        PLAN_LIST.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 204, 255)));
        PLAN_LIST.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        PLAN_LIST.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                PLAN_LISTMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(PLAN_LIST);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 204, 255)));

        jLabel1.setFont(new java.awt.Font("sansserif", 0, 10)); // NOI18N
        jLabel1.setText("Utolsó kiküldött dátum:");

        LAST_RELEASED.setFont(new java.awt.Font("Times New Roman", 0, 8)); // NOI18N
        LAST_RELEASED.setForeground(new java.awt.Color(51, 51, 51));
        LAST_RELEASED.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(LAST_RELEASED);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setBorderPainted(false);

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/save16.png"))); // NOI18N
        jButton1.setToolTipText("Terv mentése");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/close16.png"))); // NOI18N
        jButton2.setToolTipText("Terv bezárása...");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        PANEL.setBackground(new java.awt.Color(255, 255, 255));

        TOOLTIP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout PANELLayout = new javax.swing.GroupLayout(PANEL);
        PANEL.setLayout(PANELLayout);
        PANELLayout.setHorizontalGroup(
            PANELLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PANELLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TOOLTIP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        PANELLayout.setVerticalGroup(
            PANELLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TOOLTIP, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 45, Short.MAX_VALUE))
                            .addComponent(PANEL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PANEL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel3.setFont(new java.awt.Font("sansserif", 2, 12)); // NOI18N
        jLabel3.setText("Irányítópult");

        SCREEN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SCREEN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/USR_001.png"))); // NOI18N
        SCREEN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SCREENMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                SCREENMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                SCREENMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(SCREEN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SCREEN, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public String getRandomTip() {
        Random randomGenerator = new Random();
        switch (randomGenerator.nextInt(5)) {
            case 0:
                return "Ha eszembe jut valami akkor itt javaslatokat teszek!";
            case 1:
                return "Kérlek kilépés előtt mindíg ellenőrizd az utolsó közzététel dátumát!";
            case 2:
                return "Ha bármi programhibát észlelsz azonnal jelezd a fejlesztőnek!";
            case 3:
                return "Ha lehetséges ne használj alternatív adatbázist tervezéskor!";
            case 4:
                return "Ha újító ötleted támadna kérlek oszd meg a tervezőmmel!";
        }

        return "";
    }


    private void PLAN_LISTMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PLAN_LISTMouseReleased
        PlNner.SIB.setVisible(false);
        MainForm.TOP.setSelectedIndex(PLAN_LIST.getSelectedIndex());
        DefaultListModel list = new DefaultListModel();

        for (int i = 0; i < PlNner.PLANS.get(PLAN_LIST.getSelectedIndex()).STATIONS.size(); i++) {
            Station st = PlNner.PLANS.get(PLAN_LIST.getSelectedIndex()).STATIONS.get(i);
            String line = "[" + st.getName() + "]: \n";
            line += PlNner.MYDB_DB.getCellValue("SELECT refresh FROM stations WHERE id=" + st.station_id + ";");
            list.addElement(line);
        }

        Plan plan = PlNner.PLANS.get(PLAN_LIST.getSelectedIndex());

        if (plan.NEED_TO_SAVE == true) {
            TOOLTIP.setIcon(need_to_save_icon);
            TOOLTIP.setToolTipText("A tervben változás történt menteni kell!");
        } else {
            TOOLTIP.setIcon(bulb);
            TOOLTIP.setToolTipText(getRandomTip());
        }

        LAST_RELEASED.setModel(list);


    }//GEN-LAST:event_PLAN_LISTMouseReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        if (PLAN_LIST.getSelectedIndex() > -1) {
            PlNner.MF.savePlan();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (PLAN_LIST.getSelectedIndex() > -1) {
            PlNner.MF.ClosePlan();
            tick();
            PlNner.PH.tick();
            LAST_RELEASED.setModel(new DefaultListModel());
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void SCREENMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SCREENMouseEntered
       SCREEN.setBorder(BorderFactory.createLineBorder(Color.blue));
    }//GEN-LAST:event_SCREENMouseEntered

    private void SCREENMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SCREENMouseExited
    SCREEN.setBorder(null);
    }//GEN-LAST:event_SCREENMouseExited

    private void SCREENMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SCREENMouseClicked
       PlNner.USR_SET.setVisible(true);
    }//GEN-LAST:event_SCREENMouseClicked

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
            java.util.logging.Logger.getLogger(ControlPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ControlPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ControlPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ControlPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ControlPanel dialog = new ControlPanel(new javax.swing.JFrame(), true);
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
    private javax.swing.JList LAST_RELEASED;
    private javax.swing.JPanel PANEL;
    public javax.swing.JList PLAN_LIST;
    private javax.swing.JLabel SCREEN;
    private javax.swing.JLabel TOOLTIP;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
