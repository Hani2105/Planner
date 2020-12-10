package pl.nner;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;

import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import javax.swing.table.DefaultTableModel;
import org.ini4j.Wini;

import static pl.nner.PlNner.WO;
import static pl.nner.PlNner.MYDB_DB;
import static pl.nner.PlNner.ini;

public class Beallitasablak extends javax.swing.JDialog {

    int last_index;
    Map<String, String> cimMap = new TreeMap();
    Map<String, String> emilMap = new TreeMap();
    private Point anchorPoint;

    public Beallitasablak(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setCenter();
        setMoveAble();
    }

    private void setMoveAble() {
        /**
         * This handle is a reference to THIS because in next Mouse Adapter
         * "this" is not allowed
         */
        @SuppressWarnings("unused")
        final Beallitasablak handle = Beallitasablak.this;
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

    void setCenter() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(((dim.width - this.getSize().width) / 2), ((dim.height - this.getSize().height) / 2));

    }

    void setTheme() {

        for (int i = 0; i < WO_TAB.getColumnCount(); i++) {
            WO_TAB.getColumnModel().getColumn(i).setCellRenderer(new MyCellRenderer(MyCellRenderer.LEFT));

            if (i > 2) {
                WO_TAB.getColumnModel().getColumn(i).setCellRenderer(new MyCellRenderer(MyCellRenderer.CENTER));
            }
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        beallitas_osztottpanel = new javax.swing.JTabbedPane();
        beallitas_altalanospanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        mysql_combobox = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        mysql_iptext = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        mysql_porttext = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        mysql_usertext = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        mysql_passwordtext = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        mysql_databasetext = new javax.swing.JTextField();
        mysql_mentesgomb = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        mysql_nametext = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        TEXT_DIR = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel8 = new javax.swing.JPanel();
        CH_STARTUP_CONTROL_PANEL = new javax.swing.JCheckBox();
        jLabel28 = new javax.swing.JLabel();
        SL_OPACITY_CONTROL_P = new javax.swing.JSlider();
        jLabel29 = new javax.swing.JLabel();
        DEFAULT_RUNUP_MIN_TEXT = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        LAST_PRODUCED_CHECK_TEXT = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        beallitas_wologpanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        wologbeallitas_nametextfield = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        wologbeallitas_path_textfield = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        wologbeallitas_sheet_textfield = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        wologbeallitas_job_textfield = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        wologbeallitas_pn_textfield = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        wologbeallitas_qty_textfield = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        wologbeallitas_status_textfield = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        WO_TAB = new javax.swing.JTable();
        uj_modositas_gomb = new javax.swing.JButton();
        torles_gomb = new javax.swing.JButton();
        beallitas_ok_gomb = new javax.swing.JButton();
        beallitas_email = new javax.swing.JPanel();
        combo_cimlista = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        cimlistatabla = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        emiltabla = new javax.swing.JTable();
        emailtextfield = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cimllistaformgomb = new javax.swing.JButton();
        emailmentgomb = new javax.swing.JButton();
        emailtorolgomb = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        mailescimtorolgomb = new javax.swing.JButton();
        mailescimmentgomb = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        deselectgomb = new javax.swing.JButton();
        selectallgomb = new javax.swing.JButton();
        beallitas_cimlistak = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabcimlistaedit = new javax.swing.JTable();
        cimlistamentesgomb = new javax.swing.JButton();
        cimlistatorlesgomp = new javax.swing.JButton();
        cimlistatextfield = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        CH_PH_AUTO = new javax.swing.JCheckBox();
        jPanel7 = new javax.swing.JPanel();
        CH_PANEL = new javax.swing.JCheckBox();
        CH_MERNOKI = new javax.swing.JCheckBox();
        jLabel21 = new javax.swing.JLabel();
        TEXT_M_FROM = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        TEXT_M_TO = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        CH_KIT = new javax.swing.JCheckBox();
        CH_QTY_CHECK = new javax.swing.JCheckBox();
        CH_QTY_CHECK_AUTO_CORRECT = new javax.swing.JCheckBox();
        CH_NOW_AND_FUTURE = new javax.swing.JCheckBox();
        CH_PART_PLR_CHECK = new javax.swing.JCheckBox();
        jLabel24 = new javax.swing.JLabel();
        TEXT_POPOUP_DELAY = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        SL_OPACITY_ = new javax.swing.JSlider();
        jLabel27 = new javax.swing.JLabel();

        setTitle("Beállítások");
        setUndecorated(true);
        setOpacity(0.9F);
        setResizable(false);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 102, 255)));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/close.png"))); // NOI18N
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("sansserif", 2, 18)); // NOI18N
        jLabel12.setText("Beállítások");

        beallitas_osztottpanel.setBackground(new java.awt.Color(255, 255, 255));
        beallitas_osztottpanel.setTabPlacement(javax.swing.JTabbedPane.RIGHT);
        beallitas_osztottpanel.setMaximumSize(new java.awt.Dimension(32767, 400));
        beallitas_osztottpanel.setPreferredSize(new java.awt.Dimension(1452, 492));
        beallitas_osztottpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                beallitas_osztottpanelMouseClicked(evt);
            }
        });
        beallitas_osztottpanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                beallitas_osztottpanelComponentShown(evt);
            }
        });

        beallitas_altalanospanel.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("MySql kapcsolat"));

        mysql_combobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mysql_comboboxActionPerformed(evt);
            }
        });

        jLabel13.setText("Adatbázis kiválasztása:");

        jLabel14.setText("IP-cím:");

        jLabel15.setText("Port:");

        jLabel16.setText("Username:");

        jLabel17.setText("Password:");

        jLabel18.setText("Database:");

        mysql_mentesgomb.setText("Mentés");
        mysql_mentesgomb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mysql_mentesgombActionPerformed(evt);
            }
        });

        jLabel19.setText("Name:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(mysql_nametext)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mysql_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(133, 133, 133))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(mysql_porttext)
                            .addComponent(mysql_iptext))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(mysql_passwordtext, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mysql_usertext, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18)
                            .addComponent(mysql_databasetext))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(mysql_mentesgomb, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mysql_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mysql_iptext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mysql_porttext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mysql_usertext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mysql_passwordtext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mysql_databasetext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mysql_nametext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(mysql_mentesgomb)
                .addGap(0, 54, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Program beállítások"));

        jLabel20.setText("A tervek alapértelmezett mentési helye:");

        jCheckBox1.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox1.setSelected(true);
        jCheckBox1.setText("Bejelentkezés megkövetelése program induláskor");
        jCheckBox1.setToolTipText("Kikapcsolás esetén bizonyos funkciók nem elérhetőek");

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Irányítópúlt:"));

        CH_STARTUP_CONTROL_PANEL.setBackground(new java.awt.Color(255, 255, 255));
        CH_STARTUP_CONTROL_PANEL.setText("Autómatikus indítás a programmal");
        CH_STARTUP_CONTROL_PANEL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CH_STARTUP_CONTROL_PANELActionPerformed(evt);
            }
        });

        jLabel28.setText("Ablak átlátszósága:");

        SL_OPACITY_CONTROL_P.setBackground(new java.awt.Color(255, 255, 255));
        SL_OPACITY_CONTROL_P.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                SL_OPACITY_CONTROL_PMouseReleased(evt);
            }
        });
        SL_OPACITY_CONTROL_P.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                SL_OPACITY_CONTROL_PMouseDragged(evt);
            }
        });
        SL_OPACITY_CONTROL_P.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
                SL_OPACITY_CONTROL_PAncestorMoved(evt);
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CH_STARTUP_CONTROL_PANEL, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28)
                    .addComponent(SL_OPACITY_CONTROL_P, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(277, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(CH_STARTUP_CONTROL_PANEL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SL_OPACITY_CONTROL_P, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        jLabel29.setText("Alapértelmezett felfutási idő:");

        DEFAULT_RUNUP_MIN_TEXT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                DEFAULT_RUNUP_MIN_TEXTKeyReleased(evt);
            }
        });

        jLabel30.setText("perc");

        jLabel31.setText("Partnumber figyelése:");

        jLabel32.setText("utolsó gyártás régebbi mint:");

        LAST_PRODUCED_CHECK_TEXT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                LAST_PRODUCED_CHECK_TEXTMouseReleased(evt);
            }
        });
        LAST_PRODUCED_CHECK_TEXT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                LAST_PRODUCED_CHECK_TEXTKeyReleased(evt);
            }
        });

        jLabel33.setText("nap");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TEXT_DIR)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addComponent(jCheckBox1)
                            .addComponent(jLabel29)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(DEFAULT_RUNUP_MIN_TEXT, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel30))
                            .addComponent(jLabel31)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel32)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LAST_PRODUCED_CHECK_TEXT, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel33)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TEXT_DIR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DEFAULT_RUNUP_MIN_TEXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(LAST_PRODUCED_CHECK_TEXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout beallitas_altalanospanelLayout = new javax.swing.GroupLayout(beallitas_altalanospanel);
        beallitas_altalanospanel.setLayout(beallitas_altalanospanelLayout);
        beallitas_altalanospanelLayout.setHorizontalGroup(
            beallitas_altalanospanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(beallitas_altalanospanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        beallitas_altalanospanelLayout.setVerticalGroup(
            beallitas_altalanospanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(beallitas_altalanospanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(beallitas_altalanospanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        beallitas_osztottpanel.addTab("Általános", beallitas_altalanospanel);

        beallitas_wologpanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("WO log file neve:");

        jLabel2.setText("Elérési útvonal:");

        jLabel3.setText("Worksheet neve:");

        jLabel4.setText("Job szám oszlop száma:");

        jLabel5.setText("Part Number oszlop száma:");

        jLabel6.setText("Darabszám oszlop száma:");

        jLabel7.setText("Job státusz oszlop száma:");

        WO_TAB.setAutoCreateRowSorter(true);
        WO_TAB.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        WO_TAB.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        WO_TAB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                WO_TABMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                WO_TABMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(WO_TAB);

        uj_modositas_gomb.setText("Új / Módosítás");
        uj_modositas_gomb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uj_modositas_gombActionPerformed(evt);
            }
        });

        torles_gomb.setText("Törlés");
        torles_gomb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                torles_gombActionPerformed(evt);
            }
        });

        beallitas_ok_gomb.setText("Beállítások mentése");
        beallitas_ok_gomb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                beallitas_ok_gombActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout beallitas_wologpanelLayout = new javax.swing.GroupLayout(beallitas_wologpanel);
        beallitas_wologpanel.setLayout(beallitas_wologpanelLayout);
        beallitas_wologpanelLayout.setHorizontalGroup(
            beallitas_wologpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, beallitas_wologpanelLayout.createSequentialGroup()
                .addGroup(beallitas_wologpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(beallitas_wologpanelLayout.createSequentialGroup()
                        .addGroup(beallitas_wologpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(beallitas_wologpanelLayout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(beallitas_wologpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(beallitas_wologpanelLayout.createSequentialGroup()
                                        .addComponent(torles_gomb)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(uj_modositas_gomb))
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7))
                                .addGap(245, 245, 245))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, beallitas_wologpanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(beallitas_wologpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(wologbeallitas_path_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(wologbeallitas_nametextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(wologbeallitas_sheet_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(wologbeallitas_job_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(wologbeallitas_pn_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(wologbeallitas_qty_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(wologbeallitas_status_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(beallitas_wologpanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(beallitas_ok_gomb)))
                .addGap(10, 10, 10))
        );
        beallitas_wologpanelLayout.setVerticalGroup(
            beallitas_wologpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(beallitas_wologpanelLayout.createSequentialGroup()
                .addGroup(beallitas_wologpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(beallitas_wologpanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wologbeallitas_nametextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wologbeallitas_path_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wologbeallitas_sheet_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wologbeallitas_job_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wologbeallitas_pn_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wologbeallitas_qty_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wologbeallitas_status_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addGroup(beallitas_wologpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(torles_gomb)
                            .addComponent(uj_modositas_gomb)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, beallitas_wologpanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(beallitas_ok_gomb)
                .addContainerGap())
        );

        beallitas_osztottpanel.addTab("WO log", beallitas_wologpanel);

        beallitas_email.setBackground(new java.awt.Color(255, 255, 255));

        combo_cimlista.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_cimlista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_cimlistaActionPerformed(evt);
            }
        });

        cimlistatabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        cimlistatabla.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jScrollPane2.setViewportView(cimlistatabla);

        emiltabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "E-mail", "Kiválasztás"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        emiltabla.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        emiltabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                emiltablaMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(emiltabla);

        emailtextfield.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                emailtextfieldMouseReleased(evt);
            }
        });

        jLabel8.setText("Címlista:");

        jLabel9.setText("E-mail cím:");

        cimllistaformgomb.setText("Címlista szerkesztés");
        cimllistaformgomb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cimllistaformgombActionPerformed(evt);
            }
        });

        emailmentgomb.setText("Módosít/Ment");
        emailmentgomb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailmentgombActionPerformed(evt);
            }
        });

        emailtorolgomb.setText("Törlés");
        emailtorolgomb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailtorolgombActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        mailescimtorolgomb.setText("Törlés");
        mailescimtorolgomb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mailescimtorolgombActionPerformed(evt);
            }
        });

        mailescimmentgomb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/right_arrow16.png"))); // NOI18N
        mailescimmentgomb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mailescimmentgombActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(mailescimmentgomb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mailescimtorolgomb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(mailescimtorolgomb, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mailescimmentgomb)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        deselectgomb.setText("Kiválasztás feloldása");
        deselectgomb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deselectgombActionPerformed(evt);
            }
        });

        selectallgomb.setText("Összes kiválasztása");
        selectallgomb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectallgombActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(352, 352, 352)
                .addComponent(selectallgomb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(deselectgomb)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectallgomb)
                    .addComponent(deselectgomb))
                .addContainerGap(73, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout beallitas_emailLayout = new javax.swing.GroupLayout(beallitas_email);
        beallitas_email.setLayout(beallitas_emailLayout);
        beallitas_emailLayout.setHorizontalGroup(
            beallitas_emailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(beallitas_emailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(beallitas_emailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(beallitas_emailLayout.createSequentialGroup()
                        .addGroup(beallitas_emailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(emailtextfield)
                            .addGroup(beallitas_emailLayout.createSequentialGroup()
                                .addComponent(emailmentgomb)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(emailtorolgomb))
                            .addGroup(beallitas_emailLayout.createSequentialGroup()
                                .addGroup(beallitas_emailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(beallitas_emailLayout.createSequentialGroup()
                                .addComponent(combo_cimlista, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cimllistaformgomb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        beallitas_emailLayout.setVerticalGroup(
            beallitas_emailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(beallitas_emailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(beallitas_emailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(beallitas_emailLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(beallitas_emailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(combo_cimlista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cimllistaformgomb))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(emailtextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(beallitas_emailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(emailmentgomb)
                            .addComponent(emailtorolgomb)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        beallitas_osztottpanel.addTab("E-mail", beallitas_email);

        beallitas_cimlistak.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setText("Címlista:");

        tabcimlistaedit.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tabcimlistaedit.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabcimlistaedit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tabcimlistaeditMouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(tabcimlistaedit);

        cimlistamentesgomb.setText("Mentés");
        cimlistamentesgomb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cimlistamentesgombActionPerformed(evt);
            }
        });

        cimlistatorlesgomp.setText("Törlés");
        cimlistatorlesgomp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cimlistatorlesgompActionPerformed(evt);
            }
        });

        cimlistatextfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cimlistatextfieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout beallitas_cimlistakLayout = new javax.swing.GroupLayout(beallitas_cimlistak);
        beallitas_cimlistak.setLayout(beallitas_cimlistakLayout);
        beallitas_cimlistakLayout.setHorizontalGroup(
            beallitas_cimlistakLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(beallitas_cimlistakLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(beallitas_cimlistakLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(beallitas_cimlistakLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(beallitas_cimlistakLayout.createSequentialGroup()
                            .addComponent(cimlistamentesgomb, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(177, 177, 177)
                            .addComponent(cimlistatorlesgomp, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(cimlistatextfield)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE))
        );
        beallitas_cimlistakLayout.setVerticalGroup(
            beallitas_cimlistakLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(beallitas_cimlistakLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(beallitas_cimlistakLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(beallitas_cimlistakLayout.createSequentialGroup()
                        .addComponent(jScrollPane4)
                        .addContainerGap())
                    .addGroup(beallitas_cimlistakLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cimlistatextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addGroup(beallitas_cimlistakLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cimlistamentesgomb)
                            .addComponent(cimlistatorlesgomp))
                        .addGap(0, 430, Short.MAX_VALUE))))
        );

        beallitas_osztottpanel.addTab("Címlisták", beallitas_cimlistak);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        CH_PH_AUTO.setBackground(new java.awt.Color(255, 255, 255));
        CH_PH_AUTO.setText("Autómatikus indítás a programmal");
        CH_PH_AUTO.setActionCommand("");
        CH_PH_AUTO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CH_PH_AUTOActionPerformed(evt);
            }
        });

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Ellenőrzések"));

        CH_PANEL.setBackground(new java.awt.Color(255, 255, 255));
        CH_PANEL.setText("Panelizációs ellenőrzés");
        CH_PANEL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CH_PANELActionPerformed(evt);
            }
        });

        CH_MERNOKI.setBackground(new java.awt.Color(255, 255, 255));
        CH_MERNOKI.setText("Mérnöki gyártások ellenőrzése");
        CH_MERNOKI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CH_MERNOKIActionPerformed(evt);
            }
        });

        jLabel21.setBackground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Mérnöki gyártás indulása:");

        TEXT_M_FROM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TEXT_M_FROMKeyReleased(evt);
            }
        });

        jLabel22.setText("-");

        TEXT_M_TO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TEXT_M_TOKeyReleased(evt);
            }
        });

        jLabel23.setBackground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("(ó) közé essen");

        CH_KIT.setBackground(new java.awt.Color(255, 255, 255));
        CH_KIT.setText("Kittelési folyamatok ellenőrzése");
        CH_KIT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CH_KITActionPerformed(evt);
            }
        });

        CH_QTY_CHECK.setBackground(new java.awt.Color(255, 255, 255));
        CH_QTY_CHECK.setText("Elhagyott darabszámok ellenőrzése");
        CH_QTY_CHECK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CH_QTY_CHECKActionPerformed(evt);
            }
        });

        CH_QTY_CHECK_AUTO_CORRECT.setBackground(new java.awt.Color(255, 255, 255));
        CH_QTY_CHECK_AUTO_CORRECT.setText("Elhagyott darabszámok autómatikus lecsökkentése");
        CH_QTY_CHECK_AUTO_CORRECT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CH_QTY_CHECK_AUTO_CORRECTActionPerformed(evt);
            }
        });

        CH_NOW_AND_FUTURE.setBackground(new java.awt.Color(255, 255, 255));
        CH_NOW_AND_FUTURE.setText("Hibakeresés a csak a jelen és jövőben betervezett termékekre");
        CH_NOW_AND_FUTURE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CH_NOW_AND_FUTUREActionPerformed(evt);
            }
        });

        CH_PART_PLR_CHECK.setBackground(new java.awt.Color(255, 255, 255));
        CH_PART_PLR_CHECK.setText("Partnumber meglétének ellenőrzése a Mérnöki adatbázisban");
        CH_PART_PLR_CHECK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CH_PART_PLR_CHECKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(CH_PANEL)
                        .addComponent(CH_MERNOKI)
                        .addComponent(CH_KIT)
                        .addComponent(CH_QTY_CHECK)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addGap(22, 22, 22)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(CH_QTY_CHECK_AUTO_CORRECT)
                                .addGroup(jPanel7Layout.createSequentialGroup()
                                    .addComponent(jLabel21)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(TEXT_M_FROM, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel22)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(TEXT_M_TO, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel23))))
                        .addComponent(CH_NOW_AND_FUTURE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(CH_PART_PLR_CHECK, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(79, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(CH_PANEL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CH_MERNOKI)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(TEXT_M_FROM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(TEXT_M_TO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CH_KIT)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CH_QTY_CHECK)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CH_QTY_CHECK_AUTO_CORRECT)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(CH_PART_PLR_CHECK)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CH_NOW_AND_FUTURE)
                .addContainerGap())
        );

        jLabel24.setText("Észrevételek felugró ablakának időzítése:");

        TEXT_POPOUP_DELAY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TEXT_POPOUP_DELAYActionPerformed(evt);
            }
        });
        TEXT_POPOUP_DELAY.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TEXT_POPOUP_DELAYKeyReleased(evt);
            }
        });

        jLabel25.setBackground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("perc");

        jLabel26.setText("Ablak átlátszósága:");

        SL_OPACITY_.setBackground(new java.awt.Color(255, 255, 255));
        SL_OPACITY_.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                SL_OPACITY_MouseReleased(evt);
            }
        });
        SL_OPACITY_.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                SL_OPACITY_MouseDragged(evt);
            }
        });
        SL_OPACITY_.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
                SL_OPACITY_AncestorMoved(evt);
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/anim_s_64.gif"))); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SL_OPACITY_, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TEXT_POPOUP_DELAY, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel25))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CH_PH_AUTO)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 426, Short.MAX_VALUE)
                        .addComponent(jLabel27)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(CH_PH_AUTO)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TEXT_POPOUP_DELAY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SL_OPACITY_, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(132, Short.MAX_VALUE))
        );

        beallitas_osztottpanel.addTab("Tervező segéd", jPanel6);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1055, Short.MAX_VALUE)
                        .addComponent(jLabel11))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(beallitas_osztottpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(beallitas_osztottpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 522, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void beallitas_ok_gombActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beallitas_ok_gombActionPerformed
        int temp = JOptionPane.showConfirmDialog(this, "Biztosan elmanti az adatokat?", "Mentés megerősítés", JOptionPane.YES_NO_OPTION);
        if (temp == JOptionPane.YES_OPTION) {

            try {
                PlNner.ini.put("WO log", "NumberOfCustomer", PlNner.WO.size());
                PlNner.ini.put("WO log", "wologfilenev", kontenerelemekosszefuzese(PlNner.WO, 0));
                PlNner.ini.put("WO log", "wologpath", kontenerelemekosszefuzese(PlNner.WO, 1));
                PlNner.ini.put("WO log", "wologsheetname", kontenerelemekosszefuzese(PlNner.WO, 2));
                PlNner.ini.put("WO log", "wologjoboszlop", kontenerelemekosszefuzese(PlNner.WO, 3));
                PlNner.ini.put("WO log", "wologpnoszlop", kontenerelemekosszefuzese(PlNner.WO, 4));
                PlNner.ini.put("WO log", "wologqtyoszlop", kontenerelemekosszefuzese(PlNner.WO, 5));
                PlNner.ini.put("WO log", "wologstatusoszlop", kontenerelemekosszefuzese(PlNner.WO, 6));

                PlNner.ini.store();
            } catch (IOException ex) {
                Logger.getLogger(Beallitasablak.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_beallitas_ok_gombActionPerformed

    public String kontenerelemekosszefuzese(List<WO_LOG> WO, int valtozoszam) {

        String tempvissza = "";
        for (int i = 0; i < WO.size(); i++) {
            Object temp[] = WO.get(i).get_row();

            tempvissza = tempvissza + temp[valtozoszam].toString() + ",";
        }
        return tempvissza.substring(0, tempvissza.length() - 1);

    }

    private void textfieldtorles(JPanel comp) {
        Component[] comps = comp.getComponents();

        for (Component elem : comps) {
            if (elem.getClass().equals(JTextField.class)) {
                try {
                    if (!((JTextField) elem).getName().equals("TEXT_POPOUP_DELAY")) {
                        ((JTextField) elem).setText(null);
                    }

                } catch (Exception e) {

                }
            }
        }
    }

    private void uj_modositas_gombActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uj_modositas_gombActionPerformed
        if (wologbeallitas_nametextfield.getText().length() > 0 && wologbeallitas_path_textfield.getText().length() > 0 && wologbeallitas_sheet_textfield.getText().length() > 0 && wologbeallitas_job_textfield.getText().length() > 0 && wologbeallitas_pn_textfield.getText().length() > 0 && wologbeallitas_qty_textfield.getText().length() > 0 && wologbeallitas_status_textfield.getText().length() > 0) {
            if (isNumber(wologbeallitas_job_textfield.getText()) == true && isNumber(wologbeallitas_pn_textfield.getText()) == true && isNumber(wologbeallitas_qty_textfield.getText()) == true && isNumber(wologbeallitas_status_textfield.getText()) == true) {
                int temp = JOptionPane.showConfirmDialog(this, "Biztosan elmenti az adatokat?", "Mentés megerősítés", JOptionPane.YES_NO_OPTION);
                if (temp == JOptionPane.YES_OPTION) {

                    Object[] row = new Object[7];
                    row[0] = wologbeallitas_nametextfield.getText();
                    row[1] = wologbeallitas_path_textfield.getText();
                    row[2] = wologbeallitas_sheet_textfield.getText();
                    row[3] = wologbeallitas_job_textfield.getText();
                    row[4] = wologbeallitas_pn_textfield.getText();
                    row[5] = wologbeallitas_status_textfield.getText();
                    row[6] = wologbeallitas_qty_textfield.getText();
                    /*
                     WO_LOG temp_wo = new WO_LOG();
                     temp_wo.setRow(row);
                     */
                    boolean tempb = false;
                    for (int i = 0; i < PlNner.WO.size(); i++) {
                        if (WO.get(i).wologfilenev.equals((String) row[0])) {
                            WO.get(i).setRow(row);
                            tempb = true;

                        }
                    }

                    if (tempb != true) {
                        WO_LOG temp_wo = new WO_LOG();
                        temp_wo.setRow(row);
                        PlNner.WO.add(temp_wo);

                    }

                    DefaultTableModel MODEL = new DefaultTableModel(new WO_LOG().get_header(), 0);

                    for (WO_LOG elem : PlNner.WO) {
                        MODEL.addRow(elem.get_row());
                    }
                    WO_TAB.setModel(MODEL);
                    textfieldtorles((JPanel) beallitas_osztottpanel.getComponentAt(last_index));
    }//GEN-LAST:event_uj_modositas_gombActionPerformed

            } else {

                JOptionPane.showMessageDialog(this, "Az utolsó négy mezőnek számot kell tartalmaznia!", "Hiba!", JOptionPane.ERROR_MESSAGE);
            }

        } else {

            JOptionPane.showMessageDialog(this, "Az összes mezőt ki kell tölteni!", "Hiba!", JOptionPane.ERROR_MESSAGE);
        }

    }
    private void WO_TABMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_WO_TABMouseClicked


    }//GEN-LAST:event_WO_TABMouseClicked

    private void torles_gombActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_torles_gombActionPerformed
        if (WO_TAB.getSelectedRow() > -1) {
            int temp = JOptionPane.showConfirmDialog(this, "Biztosan törli a kiválasztott sor adatait?", "Törlés megerősítés", JOptionPane.YES_NO_OPTION);
            if (temp == JOptionPane.YES_OPTION) {
                PlNner.WO.remove(WO_TAB.getSelectedRow());
                ((DefaultTableModel) WO_TAB.getModel()).removeRow(WO_TAB.getSelectedRow());
                textfieldtorles((JPanel) beallitas_osztottpanel.getComponentAt(last_index));
            }

        } else {
            JOptionPane.showMessageDialog(this, "Nem választottál ki sort a táblázatból!", "Hiba!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_torles_gombActionPerformed

    private void WO_TABMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_WO_TABMouseReleased
        if (WO_TAB.getSelectedRow() > -1) {
            String[] kijeloltsorelemek = new String[WO_TAB.getColumnCount()];
            for (int i = 0; i < WO_TAB.getColumnCount(); i++) {
                kijeloltsorelemek[i] = WO_TAB.getValueAt(WO_TAB.getSelectedRow(), i).toString();
            }

            wologbeallitas_nametextfield.setText(kijeloltsorelemek[0]);
            wologbeallitas_path_textfield.setText(kijeloltsorelemek[1]);
            wologbeallitas_sheet_textfield.setText(kijeloltsorelemek[2]);
            wologbeallitas_job_textfield.setText(kijeloltsorelemek[3]);
            wologbeallitas_pn_textfield.setText(kijeloltsorelemek[4]);
            wologbeallitas_status_textfield.setText(kijeloltsorelemek[5]);
            wologbeallitas_qty_textfield.setText(kijeloltsorelemek[6]);

        }
    }//GEN-LAST:event_WO_TABMouseReleased

    Object[] email_header() {
        return new Object[]{"email"};
    }

    Object[] cimlista_header() {
        return new Object[]{"nev", "email"};
    }


    private void beallitas_osztottpanelComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_beallitas_osztottpanelComponentShown

    }//GEN-LAST:event_beallitas_osztottpanelComponentShown

    private void tabcimlistaeditMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabcimlistaeditMouseReleased

        cimlistatextfield.setText(tabcimlistaedit.getValueAt(tabcimlistaedit.getSelectedRow(), 0).toString());


    }//GEN-LAST:event_tabcimlistaeditMouseReleased

    private void beallitas_osztottpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_beallitas_osztottpanelMouseClicked

        if (last_index != beallitas_osztottpanel.getSelectedIndex()) {

            switch (beallitas_osztottpanel.getSelectedIndex()) {

                case 2:
                    emailtabactivate();

                    break;
                case 3:
                    cimlistatabselect();
                    break;

            }
            last_index = beallitas_osztottpanel.getSelectedIndex();
            textfieldtorles((JPanel) beallitas_osztottpanel.getComponentAt(last_index));
        }
    }//GEN-LAST:event_beallitas_osztottpanelMouseClicked

    private void mailescimtorolgombActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mailescimtorolgombActionPerformed
        int temp = JOptionPane.showConfirmDialog(this, "Biztosan törli a kiválasztott sor(ok) adatait?", "Törlés megerősítés", JOptionPane.YES_NO_OPTION);

        if (temp == JOptionPane.YES_OPTION) {
            int[] selectedrows = cimlistatabla.getSelectedRows();
            String cimID = cimMap.get(combo_cimlista.getSelectedItem().toString());

            for (int i = 0; i < selectedrows.length; i++) {
                String emailID = emilMap.get(cimlistatabla.getValueAt(selectedrows[i], 1).toString());

                MYDB_DB.insertCommand("delete from mailescim where cimlistaid=\"" + cimID + "\"and emailid =\"" + emailID + "\";");
            }
            cimlistatablafeltoltes();

    }//GEN-LAST:event_mailescimtorolgombActionPerformed
    }
    private void emailtorolgombActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailtorolgombActionPerformed
        int kivalasztottelemekszama = 0;
        for (int i = 0; i < emiltabla.getRowCount(); i++) {
            if (emiltabla.getValueAt(i, 1).equals(true)) {
                kivalasztottelemekszama++;
            }
        }
        if (kivalasztottelemekszama > 0) {
            int temp = JOptionPane.showConfirmDialog(this, "Biztosan törli a kiválasztott sor(ok) adatait?", "Törlés megerősítés", JOptionPane.YES_NO_OPTION);

            if (temp == JOptionPane.YES_OPTION) {

                for (int j = 0; j < emiltabla.getRowCount(); j++) {
                    if (emiltabla.getValueAt(j, 1).equals(true)) {

                        MYDB_DB.insertCommand("delete from email where email =\"" + emiltabla.getValueAt(j, 0) + "\";");
                        emailtextfield.setText(null);
                    }
                }
                emailtablafeltoltes("SELECT email,id FROM email;");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nem választottál ki semmit!", "Hiba!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_emailtorolgombActionPerformed

    private void deselectgombActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deselectgombActionPerformed

        for (int i = 0; i < emiltabla.getRowCount(); i++) {
            emiltabla.setValueAt(Boolean.FALSE, i, 1);
        }
    }//GEN-LAST:event_deselectgombActionPerformed

    private void selectallgombActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectallgombActionPerformed

        for (int i = 0; i < emiltabla.getRowCount(); i++) {
            emiltabla.setValueAt(Boolean.TRUE, i, 1);
        }
    }//GEN-LAST:event_selectallgombActionPerformed

    private void mailescimmentgombActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mailescimmentgombActionPerformed

        String cimID = cimMap.get(combo_cimlista.getSelectedItem().toString());
        for (int i = 0; i < emiltabla.getRowCount(); i++) {
            if (emiltabla.getValueAt(i, 1).equals(true)) {
                if (!checkin(cimlistatabla, emiltabla.getValueAt(i, 0).toString(), 1)) {

                    String emailID = emilMap.get(emiltabla.getValueAt(i, 0).toString());
                    MYDB_DB.insertCommand("INSERT into mailescim SET cimlistaid=\"" + cimID + "\", emailid =\"" + emailID + "\";");
                    cimlistatablafeltoltes();

                }
            }

        }
    }//GEN-LAST:event_mailescimmentgombActionPerformed

    private void cimllistaformgombActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cimllistaformgombActionPerformed
        beallitas_osztottpanel.setSelectedIndex(3);
        cimlistatabselect();
    }//GEN-LAST:event_cimllistaformgombActionPerformed

    private void emailtextfieldMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_emailtextfieldMouseReleased

    }//GEN-LAST:event_emailtextfieldMouseReleased

    private void emiltablaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_emiltablaMouseReleased
        emailtextfield.setText(emiltabla.getValueAt(emiltabla.getSelectedRow(), 0).toString());
    }//GEN-LAST:event_emiltablaMouseReleased

    private void combo_cimlistaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_cimlistaActionPerformed
        DefaultTableModel tab2 = MYDB_DB.getDataTableModel("SELECT cimlistak.nev, email.email\n"
                + "FROM cimlistak INNER JOIN (email INNER JOIN mailescim ON email.ID = mailescim.emailid) ON cimlistak.ID = mailescim.cimlistaid\n"
                + "WHERE (((cimlistak.nev)=\"" + combo_cimlista.getSelectedItem() + "\"));");
        cimlistatabla.setModel(tab2);
    }//GEN-LAST:event_combo_cimlistaActionPerformed

    private void emailmentgombActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailmentgombActionPerformed

        if (emailtextfield.getText().contains("@") && emailtextfield.getText().contains(".")) {
            if (!checkin(emiltabla, emailtextfield.getText(), 0)) {
                MYDB_DB.insertCommand("INSERT into email SET email=\"" + emailtextfield.getText() + "\";");
                emailtablafeltoltes("SELECT email,id FROM email;");
                emailtextfield.setText(null);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nem valós E-mail címet adtál meg!");
        }


    }//GEN-LAST:event_emailmentgombActionPerformed

    private void cimlistatextfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cimlistatextfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cimlistatextfieldActionPerformed

    private void cimlistamentesgombActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cimlistamentesgombActionPerformed
        if (cimlistatextfield.getText().length() > 0) {
            MYDB_DB.insertCommand("INSERT into cimlistak SET nev=\"" + cimlistatextfield.getText() + "\";");
            tabcimlistaeditfeltoltes();
            cimlistatextfield.setText(null);
        } else {
            JOptionPane.showMessageDialog(this, "A text mező üres!", "Hiba!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_cimlistamentesgombActionPerformed

    private void cimlistatorlesgompActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cimlistatorlesgompActionPerformed
        if (tabcimlistaedit.getSelectedRow() > -1) {
            int temp = JOptionPane.showConfirmDialog(this, "Biztosan törli a kiválasztott sor adatait?", "Törlés megerősítés", JOptionPane.YES_NO_OPTION);

            if (temp == JOptionPane.YES_OPTION) {

                MYDB_DB.insertCommand("delete from cimlistak where nev =\"" + cimlistatextfield.getText() + "\";");

                tabcimlistaeditfeltoltes();
                cimlistatextfield.setText(null);
            }

        } else {
            JOptionPane.showMessageDialog(this, "Nem választottál ki sort!", "Hiba!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_cimlistatorlesgompActionPerformed

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        setVisible(false);
    }//GEN-LAST:event_jLabel11MouseClicked

    private void mysql_comboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mysql_comboboxActionPerformed
        mysqlteszfieldfeltoltes();
    }//GEN-LAST:event_mysql_comboboxActionPerformed

    private void mysql_mentesgombActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mysql_mentesgombActionPerformed
        int temp = JOptionPane.showConfirmDialog(this, "Biztosan menti az adatokat?", "Mentés megerősítés", JOptionPane.YES_NO_OPTION);
        if (temp == JOptionPane.OK_OPTION) {
            try {
                if (ini.get("Mysql", "name").contains(mysql_nametext.getText())) {

                } else {
                    ini.put("Mysql", "name", ini.get("Mysql", "name") + "," + mysql_nametext.getText());
                }
                PlNner.ini.put("Mysql", mysql_nametext.getText() + "_Ip", mysql_iptext.getText());
                PlNner.ini.put("Mysql", mysql_nametext.getText() + "_Port", mysql_porttext.getText());
                PlNner.ini.put("Mysql", mysql_nametext.getText() + "_user", mysql_usertext.getText());
                PlNner.ini.put("Mysql", mysql_nametext.getText() + "_password", mysql_passwordtext.getText());
                PlNner.ini.put("Mysql", mysql_nametext.getText() + "_database", mysql_databasetext.getText());

                PlNner.ini.store();
                JOptionPane.showMessageDialog(rootPane, "Sikeres mentés!", "Sikeres mentés!", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }

        JOptionPane.showMessageDialog(this, "A beállítások érvényesítéséhez, kérem indítsa újra a programot!", "Figyelem!", JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_mysql_mentesgombActionPerformed

    private void CH_NOW_AND_FUTUREActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CH_NOW_AND_FUTUREActionPerformed
        if (CH_NOW_AND_FUTURE.isSelected()) {
            PlNner.SW_NOW_AND_FUTURE = 1;
        } else {
            PlNner.SW_NOW_AND_FUTURE = 0;
        }
        PlNner.PH.tick();
    }//GEN-LAST:event_CH_NOW_AND_FUTUREActionPerformed

    private void CH_PANELActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CH_PANELActionPerformed
        if (CH_PANEL.isSelected()) {
            PlNner.SW_PAN = 1;
        } else {
            PlNner.SW_PAN = 0;
        }
        PlNner.PH.tick();
    }//GEN-LAST:event_CH_PANELActionPerformed

    private void CH_MERNOKIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CH_MERNOKIActionPerformed
        if (CH_MERNOKI.isSelected()) {
            PlNner.SW_ENGINEER = 1;
        } else {
            PlNner.SW_ENGINEER = 0;
        }
        PlNner.PH.tick();
    }//GEN-LAST:event_CH_MERNOKIActionPerformed

    private void TEXT_M_FROMKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TEXT_M_FROMKeyReleased
        try {
            PlNner.VAL_FROM = Integer.parseInt(TEXT_M_FROM.getText());
        } catch (Exception e) {

        }
        PlNner.PH.tick();
    }//GEN-LAST:event_TEXT_M_FROMKeyReleased

    private void TEXT_M_TOKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TEXT_M_TOKeyReleased
        try {
            PlNner.VAL_TO = Integer.parseInt(TEXT_M_TO.getText());
        } catch (Exception e) {

        }
        PlNner.PH.tick();
    }//GEN-LAST:event_TEXT_M_TOKeyReleased

    private void CH_QTY_CHECK_AUTO_CORRECTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CH_QTY_CHECK_AUTO_CORRECTActionPerformed
        if (CH_QTY_CHECK_AUTO_CORRECT.isSelected()) {
            PlNner.SW_QTY_AUTO_CORRECT = 1;
        } else {
            PlNner.SW_QTY_AUTO_CORRECT = 0;
        }
        PlNner.PH.tick();

    }//GEN-LAST:event_CH_QTY_CHECK_AUTO_CORRECTActionPerformed

    private void SL_OPACITY_CONTROL_PMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SL_OPACITY_CONTROL_PMouseReleased
        Double v = Double.parseDouble(Integer.toString(SL_OPACITY_CONTROL_P.getValue())) / 100;

        PlNner.OPACITY_CONTROL_PANEL = v.floatValue();
        PlNner.CP.setOpacity(v.floatValue());

    }//GEN-LAST:event_SL_OPACITY_CONTROL_PMouseReleased

    private void SL_OPACITY_MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SL_OPACITY_MouseReleased
        Double v = Double.parseDouble(Integer.toString(SL_OPACITY_.getValue())) / 100;

        PlNner.OPACITY_TERV_SEGED = v.floatValue();
        PlNner.PH.setOpacity(v.floatValue());


    }//GEN-LAST:event_SL_OPACITY_MouseReleased

    private void SL_OPACITY_AncestorMoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_SL_OPACITY_AncestorMoved

    }//GEN-LAST:event_SL_OPACITY_AncestorMoved

    private void SL_OPACITY_CONTROL_PAncestorMoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_SL_OPACITY_CONTROL_PAncestorMoved

    }//GEN-LAST:event_SL_OPACITY_CONTROL_PAncestorMoved

    private void SL_OPACITY_CONTROL_PMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SL_OPACITY_CONTROL_PMouseDragged
        Double v = Double.parseDouble(Integer.toString(SL_OPACITY_CONTROL_P.getValue())) / 100;

        PlNner.OPACITY_CONTROL_PANEL = v.floatValue();
        PlNner.CP.setOpacity(v.floatValue());
    }//GEN-LAST:event_SL_OPACITY_CONTROL_PMouseDragged

    private void SL_OPACITY_MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SL_OPACITY_MouseDragged
        Double v = Double.parseDouble(Integer.toString(SL_OPACITY_.getValue())) / 100;

        PlNner.OPACITY_TERV_SEGED = v.floatValue();
        PlNner.PH.setOpacity(v.floatValue());

    }//GEN-LAST:event_SL_OPACITY_MouseDragged

    private void CH_KITActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CH_KITActionPerformed
        if (CH_KIT.isSelected()) {
            PlNner.SW_KIT = 1;
        } else {
            PlNner.SW_KIT = 0;
        }
        PlNner.PH.tick();
    }//GEN-LAST:event_CH_KITActionPerformed

    private void CH_QTY_CHECKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CH_QTY_CHECKActionPerformed
        if (CH_QTY_CHECK.isSelected()) {
            PlNner.SW_QTY_LOST = 1;
        } else {
            PlNner.SW_QTY_LOST = 0;
        }
        PlNner.PH.tick();
    }//GEN-LAST:event_CH_QTY_CHECKActionPerformed

    private void TEXT_POPOUP_DELAYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TEXT_POPOUP_DELAYActionPerformed

    }//GEN-LAST:event_TEXT_POPOUP_DELAYActionPerformed

    private void CH_PH_AUTOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CH_PH_AUTOActionPerformed
        if (CH_PH_AUTO.isSelected()) {
            PlNner.SW_TERVEZOS_SEGED = 1;
        } else {
            PlNner.SW_TERVEZOS_SEGED = 0;
        }
    }//GEN-LAST:event_CH_PH_AUTOActionPerformed

    private void CH_STARTUP_CONTROL_PANELActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CH_STARTUP_CONTROL_PANELActionPerformed
        if (CH_STARTUP_CONTROL_PANEL.isSelected()) {
            PlNner.SW_CONTROL_PANEL = 1;
        } else {
            PlNner.SW_CONTROL_PANEL = 0;
        }
    }//GEN-LAST:event_CH_STARTUP_CONTROL_PANELActionPerformed

    private void TEXT_POPOUP_DELAYKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TEXT_POPOUP_DELAYKeyReleased
        PlNner.ErrorMsgDelay = Integer.parseInt(TEXT_POPOUP_DELAY.getText()) * 60000;
        PlNner.PH.timer.setDelay(PlNner.ErrorMsgDelay);
        PlNner.PH.timer.setInitialDelay(PlNner.ErrorMsgDelay);
        PlNner.PH.tick();
    }//GEN-LAST:event_TEXT_POPOUP_DELAYKeyReleased

    private void CH_PART_PLR_CHECKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CH_PART_PLR_CHECKActionPerformed
       if (CH_PART_PLR_CHECK.isSelected()){
           PlNner.SW_PARTNUMBER_PLR =1;
       }else{
          PlNner.SW_PARTNUMBER_PLR =0; 
       }
       PlNner.PH.tick();
    }//GEN-LAST:event_CH_PART_PLR_CHECKActionPerformed

    private void DEFAULT_RUNUP_MIN_TEXTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DEFAULT_RUNUP_MIN_TEXTKeyReleased
 
        try{
         if (DEFAULT_RUNUP_MIN_TEXT.getText().length()==0){
             PlNner.DEFAULT_RUNUP_MIN=0;
         }else{
         PlNner.DEFAULT_RUNUP_MIN = Integer.parseInt(DEFAULT_RUNUP_MIN_TEXT.getText());
         }
     }catch ( Exception e){
         
     }
    }//GEN-LAST:event_DEFAULT_RUNUP_MIN_TEXTKeyReleased

    private void LAST_PRODUCED_CHECK_TEXTMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LAST_PRODUCED_CHECK_TEXTMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_LAST_PRODUCED_CHECK_TEXTMouseReleased

    private void LAST_PRODUCED_CHECK_TEXTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LAST_PRODUCED_CHECK_TEXTKeyReleased
        try{
         if (LAST_PRODUCED_CHECK_TEXT.getText().length()==0){
             PlNner.LAST_PRODUCED_CHECK=0;
         }else{
         PlNner.LAST_PRODUCED_CHECK = Integer.parseInt(LAST_PRODUCED_CHECK_TEXT.getText());
         }
     }catch ( Exception e){
         
     }
    }//GEN-LAST:event_LAST_PRODUCED_CHECK_TEXTKeyReleased

    private void addtablerow(JTable table, String[] rowData) {

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(rowData);
        table.setModel(model);
    }

    private void removetablerow(JTable table, int sor) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.removeRow(sor);
        table.setModel(model);

    }

    private boolean checkin(JTable tabla, String value, int oszlop) {
        String list = "";

        for (int i = 0; i < tabla.getRowCount(); i++) {

            list = list + tabla.getValueAt(i, oszlop) + ",";

        }

        return list.contains(value);

    }

    public String[] cimlistaelemek() {

        DefaultTableModel tab1 = MYDB_DB.getDataTableModel("SELECT nev,ID FROM cimlistak;");
        String[] temp = new String[tab1.getRowCount()];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = tab1.getValueAt(i, 0).toString();
            cimMap.put(tab1.getValueAt(i, 0).toString(), tab1.getValueAt(i, 1).toString());

        }

        return temp;
    }

    public void init() {

        DefaultTableModel MODEL = new DefaultTableModel(new WO_LOG().get_header(), 0);
        for (int i = 0; i < PlNner.WO.size(); i++) {
            MODEL.addRow(PlNner.WO.get(i).get_row());
        }

        combo_cimlista.setModel(new DefaultComboBoxModel(cimlistaelemek()));

        WO_TAB.setModel(MODEL);

        cimlistatablafeltoltes();

        textfieldtorles((JPanel) beallitas_osztottpanel.getComponentAt(last_index));
        if (ini.get("Mysql", "name") != null) {
            mysql_combobox.setModel(new DefaultComboBoxModel(ini.get("Mysql", "name").split(",")));
        }
        mysqlteszfieldfeltoltes();

        SL_OPACITY_.setValue((int) (PlNner.OPACITY_TERV_SEGED * 100));
        SL_OPACITY_CONTROL_P.setValue((int) (PlNner.OPACITY_CONTROL_PANEL * 100));
        setTheme();

    }

    void cimlistatablafeltoltes() {
        DefaultTableModel tab2 = MYDB_DB.getDataTableModel("SELECT cimlistak.nev, email.email\n"
                + "FROM cimlistak INNER JOIN (email INNER JOIN mailescim ON email.ID = mailescim.emailid) ON cimlistak.ID = mailescim.cimlistaid\n"
                + "WHERE (((cimlistak.nev)=\"" + combo_cimlista.getSelectedItem() + "\"));");
        cimlistatabla.setModel(tab2);

    }

    void emailtablafeltoltes(String Query) {

        DefaultTableModel tab1 = MYDB_DB.getDataTableModel(Query);
        DefaultTableModel model = (DefaultTableModel) emiltabla.getModel();

        for (int i = model.getRowCount() - 1; i > -1; i--) {
            model.removeRow(i);
        }

        for (int i = 0; i < tab1.getRowCount(); i++) {
            Object[] sor = new Object[2];
            sor[0] = tab1.getValueAt(i, 0);
            sor[1] = Boolean.FALSE;
            model.addRow(sor);
            emilMap.put(tab1.getValueAt(i, 0).toString(), tab1.getValueAt(i, 1).toString());
        }

    }

    public void emailtabactivate() {

        emailtablafeltoltes("SELECT email,id FROM email;");

        DefaultTableModel tab2 = MYDB_DB.getDataTableModel("SELECT cimlistak.nev, email.email\n"
                + "FROM cimlistak INNER JOIN (email INNER JOIN mailescim ON email.ID = mailescim.emailid) ON cimlistak.ID = mailescim.cimlistaid\n"
                + "WHERE (((cimlistak.nev)=\"" + combo_cimlista.getSelectedItem() + "\"));");

        //String emailID = emilMap.get(0).toString();
        cimlistatabla.setModel(tab2);
        //System.out.println(emailID);
    }

    void tabcimlistaeditfeltoltes() {
        DefaultTableModel tab3 = MYDB_DB.getDataTableModel("SELECT nev from cimlistak");
        tabcimlistaedit.setModel(tab3);

    }

    public void cimlistatabselect() {

        tabcimlistaeditfeltoltes();

    }

    void mysqlteszfieldfeltoltes() {
        try {

            mysql_iptext.setText(ini.get("Mysql", mysql_combobox.getSelectedItem().toString() + "_Ip"));
            mysql_porttext.setText(ini.get("Mysql", mysql_combobox.getSelectedItem().toString() + "_Port"));
            mysql_usertext.setText(ini.get("Mysql", mysql_combobox.getSelectedItem().toString() + "_user"));
            mysql_passwordtext.setText(ini.get("Mysql", mysql_combobox.getSelectedItem().toString() + "_password"));
            mysql_databasetext.setText(ini.get("Mysql", mysql_combobox.getSelectedItem().toString() + "_database"));
            mysql_nametext.setText(mysql_combobox.getSelectedItem().toString());

        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

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
            java.util.logging.Logger.getLogger(Beallitasablak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Beallitasablak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Beallitasablak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Beallitasablak.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Beallitasablak dialog = new Beallitasablak(new javax.swing.JFrame(), true);
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

    @Override
    public void setVisible(boolean val) {

        if (PlNner.SW_ENGINEER == 1) {
            CH_MERNOKI.setSelected(true);
        } else {
            CH_MERNOKI.setSelected(false);
        }

        if (PlNner.SW_KIT == 1) {
            CH_KIT.setSelected(true);
        } else {
            CH_KIT.setSelected(false);
        }

        if (PlNner.SW_NOW_AND_FUTURE == 1) {
            CH_NOW_AND_FUTURE.setSelected(true);
        } else {
            CH_NOW_AND_FUTURE.setSelected(false);
        }

        if (PlNner.SW_PAN == 1) {
            CH_PANEL.setSelected(true);
        } else {
            CH_PANEL.setSelected(false);
        }

        if (PlNner.SW_QTY_AUTO_CORRECT == 1) {
            CH_QTY_CHECK_AUTO_CORRECT.setSelected(true);
        } else {
            CH_QTY_CHECK_AUTO_CORRECT.setSelected(false);
        }

        if (PlNner.SW_QTY_LOST == 1) {
            CH_QTY_CHECK.setSelected(true);
        } else {
            CH_QTY_CHECK.setSelected(false);
        }

        TEXT_M_FROM.setText(Integer.toString(PlNner.VAL_FROM));
        TEXT_M_TO.setText(Integer.toString(PlNner.VAL_TO));
        TEXT_POPOUP_DELAY.setText(Integer.toString(PlNner.ErrorMsgDelay / 60000));
        DEFAULT_RUNUP_MIN_TEXT.setText(Integer.toString(PlNner.DEFAULT_RUNUP_MIN));
        LAST_PRODUCED_CHECK_TEXT.setText(Integer.toString(PlNner.LAST_PRODUCED_CHECK));

        if (PlNner.SW_CONTROL_PANEL == 1) {
            CH_STARTUP_CONTROL_PANEL.setSelected(true);
        } else {
            CH_STARTUP_CONTROL_PANEL.setSelected(false);
        }

        if (PlNner.SW_TERVEZOS_SEGED == 1) {
            CH_PH_AUTO.setSelected(true);
        } else {
            CH_PH_AUTO.setSelected(false);
        }

        super.setVisible(val);
    }

    public static boolean isNumber(Object value) {
        try {
            if (value != null) {
                Double temp = Double.parseDouble(value.toString());
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox CH_KIT;
    private javax.swing.JCheckBox CH_MERNOKI;
    private javax.swing.JCheckBox CH_NOW_AND_FUTURE;
    private javax.swing.JCheckBox CH_PANEL;
    private javax.swing.JCheckBox CH_PART_PLR_CHECK;
    private javax.swing.JCheckBox CH_PH_AUTO;
    private javax.swing.JCheckBox CH_QTY_CHECK;
    private javax.swing.JCheckBox CH_QTY_CHECK_AUTO_CORRECT;
    private javax.swing.JCheckBox CH_STARTUP_CONTROL_PANEL;
    private javax.swing.JTextField DEFAULT_RUNUP_MIN_TEXT;
    private javax.swing.JTextField LAST_PRODUCED_CHECK_TEXT;
    private javax.swing.JSlider SL_OPACITY_;
    private javax.swing.JSlider SL_OPACITY_CONTROL_P;
    private javax.swing.JTextField TEXT_DIR;
    private javax.swing.JTextField TEXT_M_FROM;
    private javax.swing.JTextField TEXT_M_TO;
    private javax.swing.JTextField TEXT_POPOUP_DELAY;
    private javax.swing.JTable WO_TAB;
    private javax.swing.JPanel beallitas_altalanospanel;
    private javax.swing.JPanel beallitas_cimlistak;
    private javax.swing.JPanel beallitas_email;
    private javax.swing.JButton beallitas_ok_gomb;
    private javax.swing.JTabbedPane beallitas_osztottpanel;
    private javax.swing.JPanel beallitas_wologpanel;
    private javax.swing.JButton cimlistamentesgomb;
    private javax.swing.JTable cimlistatabla;
    private javax.swing.JTextField cimlistatextfield;
    private javax.swing.JButton cimlistatorlesgomp;
    private javax.swing.JButton cimllistaformgomb;
    public javax.swing.JComboBox combo_cimlista;
    private javax.swing.JButton deselectgomb;
    private javax.swing.JButton emailmentgomb;
    private javax.swing.JTextField emailtextfield;
    private javax.swing.JButton emailtorolgomb;
    private javax.swing.JTable emiltabla;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton mailescimmentgomb;
    private javax.swing.JButton mailescimtorolgomb;
    private javax.swing.JComboBox mysql_combobox;
    private javax.swing.JTextField mysql_databasetext;
    private javax.swing.JTextField mysql_iptext;
    private javax.swing.JButton mysql_mentesgomb;
    private javax.swing.JTextField mysql_nametext;
    private javax.swing.JTextField mysql_passwordtext;
    private javax.swing.JTextField mysql_porttext;
    private javax.swing.JTextField mysql_usertext;
    private javax.swing.JButton selectallgomb;
    private javax.swing.JTable tabcimlistaedit;
    private javax.swing.JButton torles_gomb;
    private javax.swing.JButton uj_modositas_gomb;
    private javax.swing.JTextField wologbeallitas_job_textfield;
    private javax.swing.JTextField wologbeallitas_nametextfield;
    private javax.swing.JTextField wologbeallitas_path_textfield;
    private javax.swing.JTextField wologbeallitas_pn_textfield;
    private javax.swing.JTextField wologbeallitas_qty_textfield;
    private javax.swing.JTextField wologbeallitas_sheet_textfield;
    private javax.swing.JTextField wologbeallitas_status_textfield;
    // End of variables declaration//GEN-END:variables
}
