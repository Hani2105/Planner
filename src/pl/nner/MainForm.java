/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.nner;

import com.jgoodies.forms.layout.Sizes;
import java.awt.Color;
import java.awt.*;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javafx.scene.control.Dialog;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.ToolTipManager;
import static jdk.nashorn.internal.objects.NativeRegExp.source;
import org.joda.time.DateTime;
import pl.nner.DateSpan;

/**
 *
 * @author krisztian_csekme1
 */
public class MainForm extends javax.swing.JFrame {

    public Product PROD_CLIP;
    public boolean PROD_CLIP_KITTED;
    public boolean PROD_CLIP_ENGINEER;

    public Timer orajel;
    // public static ImageIcon st = new ImageIcon(Toolkit.getDefaultToolkit().getImage(PlNner.class.getResource("IMG/standard_bg.jpg")));
    public int SWITCH = 0;

    /**
     * Creates new form MainForm
     */
    ActionListener timer2 = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            CALENDAR.setDaysOfTheWeek(new String[]{"Vas", "Hét", "Ke", "Sze", "Csü", "Pé", "Szo"});
            CALENDAR.setMonthNames(new String[]{"Január", "Február", "Április", "Május", "Június", "Július", "Augusztus", "Szeptember", "Október", "November", "December"});

            if (SWITCH == 0) {
                if (SPLIT.getDividerLocation() < 1000) {
                    SPLIT.setDividerLocation(SPLIT.getDividerLocation() + 200);
                }
            } else {
                if (SPLIT.getDividerLocation() > 0) {
                    if (SPLIT.getDividerLocation() < 200) {
                        SPLIT.setDividerLocation(0);
                    } else {
                        SPLIT.setDividerLocation(SPLIT.getDividerLocation() - 200);
                    }
                }
            }

        }
    };

    ActionListener timer = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                //temp mentese a terveknek
                PlNner.doTempSave();
            } catch (InterruptedException ex) {
                Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (PlNner.REMINDER == 0) {
                try {

                    PlNner.doVersionCheck();
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                    PlNner.VER_FOLLOW = null;
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }

                if (PlNner.VER_FOLLOW != null) {

                    if (PlNner.VERSION.equals(PlNner.VER_FOLLOW)) {
                        PlNner.isUPDATED = true;

                    } else {
                        PlNner.isUPDATED = false;
                        PlNner.REMINDER++;
                        //system_software_update
                        ImageIcon ico = new ImageIcon(this.getClass().getResource("IMG/system_software_update.png"));
                        String message = "<html>Figyelem, változott a program verziója " + PlNner.VER_FOLLOW + " -ra.<br>Kérem indítsa újra a programot!<br><br>A program az alábbi frissítéseket tartalmazza: <br> ---------------------------------------------- <br>" + PlNner.VER_COMMENT.replace(",", "<br>") + "</html>";
                        System.out.println("Frissítés értesítés...");

                        JOptionPane.showMessageDialog(null, message, "Figyelem!", JOptionPane.INFORMATION_MESSAGE, ico);

                    }

                }
            }

        }

    };

    public void getEventsFromPlan() {
        StringBuilder SB = new StringBuilder();
        SB.append("<HTML>").append("<BODY>");

        for (int p = 0; p < PlNner.PLANS.size(); p++) {

            for (int s = 0; s < PlNner.PLANS.get(p).STATIONS.size(); s++) {
                PlNner.PLANS.get(p).STATIONS.get(s).recalcJobs();

                for (int i = 0; i < PlNner.PLANS.get(p).STATIONS.get(s).JOBS.size(); i++) {

                    //Product pro = PlNner.PLANS.get(p).STATIONS.get(s).JOBS.get(i);
                    long T = CALENDAR.getSelectedDateSpan().getStart();
                    DateTime T1 = new DateTime(T);

                    DateTime E = PlNner.PLANS.get(p).STATIONS.get(s).JOBS.get(i).getStart();
                    if (!SEL_HOUR_AROUND.isSelected()) {
                        if ((E.getMillis() >= T1.getMillis()) && (E.getMillis() <= T1.plusHours(24).getMillis())) {
                            SB.append(PlNner.getCurrentDateTime(E));
                            SB.append(" >").append(PlNner.PLANS.get(p).STATIONS.get(s).getName().toUpperCase()).append(" állomáson: ").append(" gyártás indítása, Jobszám: ").append(PlNner.PLANS.get(p).STATIONS.get(s).JOBS.get(i).jobname).append(" | Partnumber: ").append(PlNner.PLANS.get(p).STATIONS.get(s).JOBS.get(i).partnumber).append(" | Mennyiség: ").append(PlNner.PLANS.get(p).STATIONS.get(s).JOBS.get(i).getQty()).append(" | Várható befejezés: ").append(PlNner.getCurrentDateTime(PlNner.PLANS.get(p).STATIONS.get(s).JOBS.get(i).getStop())).append("<br>");
                            SB.append("<br>");
                        }
                    } else {
                        if ((E.getMillis() >= new DateTime().minusHours(1).getMillis()) && (E.getMillis() <= new DateTime().plusHours(1).getMillis())) {
                            SB.append(PlNner.getCurrentDateTime(E));
                            SB.append(" >").append(PlNner.PLANS.get(p).STATIONS.get(s).getName().toUpperCase()).append(" állomáson: ").append(" gyártás indítása, Jobszám: ").append(PlNner.PLANS.get(p).STATIONS.get(s).JOBS.get(i).jobname).append(" | Partnumber: ").append(PlNner.PLANS.get(p).STATIONS.get(s).JOBS.get(i).partnumber).append(" | Mennyiség: ").append(PlNner.PLANS.get(p).STATIONS.get(s).JOBS.get(i).getQty()).append(" | Várható befejezés: ").append(PlNner.getCurrentDateTime(PlNner.PLANS.get(p).STATIONS.get(s).JOBS.get(i).getStop())).append("<br>");
                            SB.append("<br>");
                        }
                    }

                    //  PlNner.PLANS.get(p).STATIONS.get(s).JOBS.get(i).getStart().
                }
            }

        }

        SB.append("<BODY>");
        SB.append("<HTML>");
        EVENTS_PRODUCTION.setText(SB.toString());
    }

    public MainForm() {

        try {
            //    UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ToolTipManager.sharedInstance().setDismissDelay(600000);
        ToolTipManager.sharedInstance().setInitialDelay(0);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screen_width = (int) screenSize.getWidth();
        int screen_height = (int) screenSize.getHeight();

        PlNner.NEW_PROD = new NewProduct(this, true);
        PlNner.PROP = new Properties(this, true);
        PlNner.LOADER = new Loader(this, false);
        PlNner.DOWN_TIME_WINDOW = new DownTimeWindow(this, true);
        PlNner.SQL = new SqlWindow(this, true);
        PlNner.SE = new StationEditor(this, true);
        PlNner.NPW = new NewPlanWindow(this, true);
        PlNner.NSW = new NewStationWindow(this, true);
        PlNner.SP = new SplitWindow(this, false);
        PlNner.LS = new LengthSetup(this, false);
        PlNner.BEALLITAS_ABLAK = new Beallitasablak(this, true);
        PlNner.JRW = new JobRiportWindow(this, false);
        PlNner.NOTE = new Note(this, false);
        PlNner.OP = new OpenProject(this, false);
        PlNner.SW = new SearchWindow(this, false);
        PlNner.SProp = new StationProperties(this, true);
        PlNner.CP = new ControlPanel(this, false);
        PlNner.PH = new PlanHelper(this, false);
        PlNner.EW = new ErrorWindow(this, false);
        PlNner.EW.init();
        PlNner.CP.setLocation(screen_width - PlNner.CP.getWidth() - 50, 130);
        PlNner.PH.setLocation(30, 130);
        PlNner.UPDATE_NOTES = new UpdateNotes(this, true);
        //PlNner.UPDATE_NOTES.setVisible(true);
        PlNner.MS = new MailSender(this, false);
        PlNner.NKW = new NewKitWindow(this, false);
        PlNner.MSG = new ERRORMESSAGE(this, false);
        PlNner.USR_SET = new USERSETUP(this, false);
        PlNner.WS = new Welcome_Screen(this, false);
        PlNner.PnM = new PnMaintenance(this, false);
        PlNner.NewPW = new NewPartnumberWindow(null, true);
        PlNner.CP.tick();
        PlNner.ABOUT = new About(this, true);
        PlNner.WPW = new WhereProducedWindow(this, false);
        PlNner.SIB = new StationInformationBox(this, false);
        PlNner.kapocsiha = new Kapocshiba(this, true);


        /*Partnumber Adatbázis karbantartó*/
        PlNner.PNDATA = new PN_DATA_WINDOW();

        setIconImage(new ImageIcon(this.getClass().getResource("IMG/ico.png")).getImage()); //zoom out);
        initComponents();

        CALENDAR.setSelectedDateSpan(new DateSpan(new Date(), new Date()));
        TOOLHELP.setVisible(false);
        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        TabReorderHandler.enableReordering(TOP);

        setTitle("Pl@nner v" + PlNner.VERSION);
        orajel = new Timer(60000, timer);
        Timer t2 = new Timer(100, timer2);
        t2.start();
        orajel.start();

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent we) {
                ExitProgram();
            }
        });

        if (PlNner.SW_CONTROL_PANEL == 1) {
            PlNner.CP.setVisible(true);
        }
        if (PlNner.SW_TERVEZOS_SEGED == 1) {
            PlNner.PH.setVisible(true);
        }

        PlNner.CP.setOpacity(PlNner.OPACITY_CONTROL_PANEL);
        PlNner.PH.setOpacity(PlNner.OPACITY_TERV_SEGED);

        PlNner.WS.setVisible(true);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        SPLIT = new javax.swing.JSplitPane();
        TOPPAN = new javax.swing.JPanel();
        TOPPANDIV = new javax.swing.JSplitPane();
        TOP = new javax.swing.JTabbedPane();
        TOOLHELP = new javax.swing.JTabbedPane();
        CAL = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        CALENDAR = new pl.nner.JMonth();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        EVENTS_PRODUCTION = new javax.swing.JEditorPane();
        jPanel6 = new javax.swing.JPanel();
        SEL_HOUR_AROUND = new javax.swing.JCheckBox();
        jButton8 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        SFDC_PANEL = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        MESH_TABLE = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        JOB_NUMBER = new javax.swing.JTextField();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        BOTTOM = new javax.swing.JTabbedPane();
        Desktop = new javax.swing.JScrollPane();
        jPanel1 = new JPanel(){

            @Override
            public void paint(Graphics g){
                Graphics2D g2 = (Graphics2D)g;
                super.paint(g2);

            }

        };
        jMenuBar = new javax.swing.JMenuBar();
        jMenuProgram = new javax.swing.JMenu();
        jMNewPlan = new javax.swing.JMenuItem();
        jMExit = new javax.swing.JMenuItem();
        jMEdit = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pl@nner");
        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(java.awt.Color.white);
        setMinimumSize(new java.awt.Dimension(900, 500));

        jToolBar1.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar1.setBorder(null);
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setBorderPainted(false);
        jToolBar1.setMaximumSize(new java.awt.Dimension(13, 25));
        jToolBar1.setMinimumSize(new java.awt.Dimension(13, 25));

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/new16.png"))); // NOI18N
        jButton1.setToolTipText("Új terv");
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
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/open16.png"))); // NOI18N
        jButton2.setToolTipText("Terv megnyitása");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/save16.png"))); // NOI18N
        jButton5.setToolTipText("Terv mentése");
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton5);

        jButton6.setBackground(new java.awt.Color(255, 255, 255));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/setup16.png"))); // NOI18N
        jButton6.setToolTipText("Beállítások");
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton6);

        jButton7.setBackground(new java.awt.Color(255, 255, 255));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/find16.png"))); // NOI18N
        jButton7.setToolTipText("Kiterjesztett kereső...");
        jButton7.setFocusable(false);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton7);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/properites16.png"))); // NOI18N
        jButton3.setToolTipText("Állomás szerkesztő");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/toolbox16.png"))); // NOI18N
        jButton4.setToolTipText("Partnumber karbantartó");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        SPLIT.setDividerLocation(500);
        SPLIT.setDividerSize(0);
        SPLIT.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        TOPPAN.setBackground(new java.awt.Color(102, 102, 102));

        TOPPANDIV.setDividerLocation(300);
        TOPPANDIV.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        TOP.setBackground(new java.awt.Color(255, 255, 255));
        TOP.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        TOP.setOpaque(true);
        TOP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TOPMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TOPMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                TOPMouseReleased(evt);
            }
        });
        TOP.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                TOPMouseMoved(evt);
            }
        });
        TOPPANDIV.setTopComponent(TOP);

        TOOLHELP.setBackground(new java.awt.Color(102, 102, 102));
        TOOLHELP.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        CAL.setBackground(new java.awt.Color(102, 102, 102));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/planner2logo.png"))); // NOI18N

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Jelmagyarázat:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), java.awt.Color.white)); // NOI18N
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("sansserif", 0, 10)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/ind_plan_a16.png"))); // NOI18N
        jLabel2.setText("<html>Első terv. Első élesítést követően eltűnik. <br>A következő alkalommal a terv már aktívnak számít<html>");
        jLabel2.setToolTipText("null");

        jLabel3.setFont(new java.awt.Font("sansserif", 0, 10)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/ind_plan_active16.png"))); // NOI18N
        jLabel3.setText("Aktív terv, már módosított.");
        jLabel3.setToolTipText("null");

        jLabel6.setFont(new java.awt.Font("sansserif", 0, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/kitted.png"))); // NOI18N
        jLabel6.setText("Termék kittelve van");
        jLabel6.setToolTipText("null");

        jLabel7.setFont(new java.awt.Font("sansserif", 0, 10)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/eye16.png"))); // NOI18N
        jLabel7.setText("Termék látható a tervben");
        jLabel7.setToolTipText("null");

        jLabel8.setFont(new java.awt.Font("sansserif", 0, 10)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/inf16.png"))); // NOI18N
        jLabel8.setText("Tervezői komment beszúrva");
        jLabel8.setToolTipText("null");

        jLabel4.setFont(new java.awt.Font("sansserif", 0, 10)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/note16.png"))); // NOI18N
        jLabel4.setText("A terv tartalmaz saját megjegyzést!");
        jLabel4.setToolTipText("null");

        jLabel5.setFont(new java.awt.Font("sansserif", 0, 10)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/engineering16.png"))); // NOI18N
        jLabel5.setText("Mérnöki gyártásnak lett megjelölve");
        jLabel5.setToolTipText("null");

        jLabel10.setFont(new java.awt.Font("sansserif", 0, 10)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/warning16.png"))); // NOI18N
        jLabel10.setText("Kívűl esik  a gyártási intervallumon");
        jLabel10.setToolTipText("null");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel10))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(102, 102, 102));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Kalendár:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), java.awt.Color.white)); // NOI18N

        CALENDAR.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        CALENDAR.setForeground(new java.awt.Color(102, 102, 102));
        CALENDAR.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CALENDARMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout CALENDARLayout = new javax.swing.GroupLayout(CALENDAR);
        CALENDAR.setLayout(CALENDARLayout);
        CALENDARLayout.setHorizontalGroup(
            CALENDARLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 224, Short.MAX_VALUE)
        );
        CALENDARLayout.setVerticalGroup(
            CALENDARLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 176, Short.MAX_VALUE)
        );

        EVENTS_PRODUCTION.setBackground(new java.awt.Color(102, 102, 102));
        EVENTS_PRODUCTION.setContentType("text/html"); // NOI18N
        jScrollPane1.setViewportView(EVENTS_PRODUCTION);

        jTabbedPane1.addTab("Események a gyártásban:", jScrollPane1);

        jPanel6.setBackground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 24, Short.MAX_VALUE)
        );

        SEL_HOUR_AROUND.setForeground(new java.awt.Color(255, 255, 255));
        SEL_HOUR_AROUND.setText("Események +/- 1 órában");
        SEL_HOUR_AROUND.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SEL_HOUR_AROUNDActionPerformed(evt);
            }
        });

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/refresh16.png"))); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/email.png"))); // NOI18N
        jButton10.setToolTipText("Eseményről levél írás:");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(CALENDAR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(SEL_HOUR_AROUND)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CALENDAR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton8)
                    .addComponent(SEL_HOUR_AROUND)
                    .addComponent(jButton10))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton11.setText("jButton11");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout CALLayout = new javax.swing.GroupLayout(CAL);
        CAL.setLayout(CALLayout);
        CALLayout.setHorizontalGroup(
            CALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CALLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jButton11))
                .addContainerGap())
        );
        CALLayout.setVerticalGroup(
            CALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CALLayout.createSequentialGroup()
                .addGroup(CALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CALLayout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(jButton11)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(CALLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(CALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(CALLayout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );

        TOOLHELP.addTab("Kalendár", CAL);

        SFDC_PANEL.setBackground(new java.awt.Color(102, 102, 102));

        jPanel5.setBackground(new java.awt.Color(102, 102, 102));

        MESH_TABLE.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(MESH_TABLE);

        jPanel8.setBackground(new java.awt.Color(102, 102, 102));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Jobszám:");

        JOB_NUMBER.setToolTipText("Vesszővel elválasztva több jobszámot is megadhat");

        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/search_bg.png"))); // NOI18N
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/nner/IMG/reset_arrow.png"))); // NOI18N
        jButton13.setToolTipText("Jobszámok bekérése az aktuális tervből");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setText("jButton14");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JOB_NUMBER, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(180, 180, 180)
                .addComponent(jButton14))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton14)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(JOB_NUMBER, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton12)
                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 992, Short.MAX_VALUE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Szeriaszámok", jPanel5);

        javax.swing.GroupLayout SFDC_PANELLayout = new javax.swing.GroupLayout(SFDC_PANEL);
        SFDC_PANEL.setLayout(SFDC_PANELLayout);
        SFDC_PANELLayout.setHorizontalGroup(
            SFDC_PANELLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SFDC_PANELLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );
        SFDC_PANELLayout.setVerticalGroup(
            SFDC_PANELLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SFDC_PANELLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );

        TOOLHELP.addTab("SFDC", SFDC_PANEL);

        TOPPANDIV.setRightComponent(TOOLHELP);
        TOOLHELP.getAccessibleContext().setAccessibleName("jPanel2");

        javax.swing.GroupLayout TOPPANLayout = new javax.swing.GroupLayout(TOPPAN);
        TOPPAN.setLayout(TOPPANLayout);
        TOPPANLayout.setHorizontalGroup(
            TOPPANLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TOPPANLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(TOPPANDIV))
        );
        TOPPANLayout.setVerticalGroup(
            TOPPANLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TOPPANLayout.createSequentialGroup()
                .addComponent(TOPPANDIV)
                .addGap(0, 0, 0))
        );

        SPLIT.setTopComponent(TOPPAN);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1075, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 839, Short.MAX_VALUE)
        );

        Desktop.setViewportView(jPanel1);

        BOTTOM.addTab("Tervező asztal", Desktop);

        SPLIT.setBottomComponent(BOTTOM);

        getContentPane().add(SPLIT, java.awt.BorderLayout.CENTER);

        jMenuBar.setBackground(new java.awt.Color(255, 255, 255));

        jMenuProgram.setText("Program");

        jMNewPlan.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMNewPlan.setText("Új terv");
        jMNewPlan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMNewPlanActionPerformed(evt);
            }
        });
        jMenuProgram.add(jMNewPlan);

        jMExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F12, 0));
        jMExit.setText("Kilépés");
        jMExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMExitActionPerformed(evt);
            }
        });
        jMenuProgram.add(jMExit);

        jMenuBar.add(jMenuProgram);

        jMEdit.setText("Szerkesztés");

        jMenuItem4.setText("Beállítások");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMEdit.add(jMenuItem4);

        jMenuBar.add(jMEdit);

        jMenu1.setText("Súgó");

        jMenuItem7.setText("Frissítési jegyzék");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem7);

        jMenuItem3.setText("A programról...");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem9.setText("Hol gyártottuk...");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem9);

        jMenuBar.add(jMenu1);

        setJMenuBar(jMenuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void newPlan(Plan plan, String name, int year, int week) {
        if (name != null) {

            plan.setName(name);
            plan.setWeekIndex(year, week);

            PlNner.PLANS.add(plan); //Új terv hozzáadása
            TOP.add(plan);// Tabulátorhoz hozzáadjuk az új tervet jDesktopPane
            plan.NEED_TO_SAVE = true;
            // jTabbedPane.setSelectedIndex(jTabbedPane.getTabCount() - 1);
            PlNner.CP.tick();

        } else {
            JOptionPane.showMessageDialog(null, "Nem adott nevet a tervnek!", "Figyelem!", JOptionPane.ERROR_MESSAGE);
        }
    }

    static Object res;

    public static void newPlan(Plan plan, String name, int week) {
        if (name != null) {

            plan.setName(name);

            int year = 2000;
            try {
                String[] _c = name.split("_");

                String Y = _c[2];

                Y = Y.substring(0, 2);

                year += Integer.parseInt(Y);
            } catch (Exception e) {
                year = 2016;
            }

            /*res = JOptionPane.showInputDialog(this, "Probléma volt a fájl kiolvasásánál, kérem adja meg a tervhez tartozó év-et. pl: 2016", "Figyelem", JOptionPane.QUESTION_MESSAGE);*/
            plan.setWeekIndex(year, week);

            PlNner.PLANS.add(plan); //Új terv hozzáadása
            TOP.add(plan);// Tabulátorhoz hozzáadjuk az új tervet jDesktopPane
            plan.NEED_TO_SAVE = true;
            // jTabbedPane.setSelectedIndex(jTabbedPane.getTabCount() - 1);
            PlNner.CP.tick();

        } else {
            JOptionPane.showMessageDialog(null, "Nem adott nevet a tervnek!", "Figyelem!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void newPlan() {
        PlNner.NPW.init();
        PlNner.NPW.setVisible(true);

    }

    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    public static int getMaxLengthOfString(String[] strs) {
        int len = 0;

        for (String elem : strs) {

            if (elem.length() > len) {
                len = elem.length();
            }

        }

        return len;

    }

    public static void addStation(final Plan plan, final Station station, int station_id, Dimension dim, Point location) {
        station.station_id = station_id;

        if (station.loader.length < 20) {

            Object[][] arr = new Object[20][100];
            for (int r = 0; r < 100; r++) {
                arr[0][r] = station.loader[0][r];
                arr[1][r] = station.loader[1][r];
                arr[2][r] = station.loader[2][r];
                arr[3][r] = station.loader[3][r];
                arr[4][r] = station.loader[4][r];
                arr[5][r] = station.loader[5][r];

            }
            station.loader = arr;
        }

        final Window window = new Window(station.getName());

        final JScrollPane pane = new JScrollPane();

        JPanel panel = new JPanel() {

            @Override
            public void paint(Graphics g) {

                Graphics2D gd = (Graphics2D) g;
                gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                setOpaque(false);

                gd.setColor(station.BG_COLOR);
                gd.fillRect(0, 0, getWidth(), getHeight());

                int screenwidth = Toolkit.getDefaultToolkit().getScreenSize().width;

                if (station.BG_IMAGE != null) {
                    BufferedImage BI = toBufferedImage(station.BG_IMAGE.getImage());

                    switch (station.img_pos) {
                        case 0: //középre igazított

                            gd.drawImage(BI, (this.getWidth() / 2) - (station.BG_IMAGE.getIconWidth() / 2), 0, null);
                            gd.drawImage(BI, (this.getWidth() / 2) - (station.BG_IMAGE.getIconWidth() / 2), station.BG_IMAGE.getIconHeight(), null);
                            break;

                        case 1: //ismétlő           

                            if ((screenwidth > station.BG_IMAGE.getIconWidth())) {

                                double xszer = screenwidth / station.BG_IMAGE.getIconWidth();

                                double yszor = getHeight() / station.BG_IMAGE.getIconHeight();

                                for (int x = 0; x < xszer + 2; x++) {
                                    for (int y = 0; y < yszor + 2; y++) {
                                        gd.drawImage(BI, station.BG_IMAGE.getIconWidth() * x, station.BG_IMAGE.getIconHeight() * y, null);
                                    }
                                }
                            } else {
                                //  JOptionPane.showMessageDialog(null, "Túl nagy a kép mérete hogy az ismétlő rajzolást használja!");
                            }
                            break;

                    }

                } else {

                }

                //gd.drawImage(new ImageIcon(this.getClass().getResource("IMG/inf_bg.png")).getImage(), station.inf_x - 100 , station.inf_y + 10 , null);
                String all = "";

                String allomas = "Állomás: " + station.getName().toUpperCase();
                String gyartasi_het = "Év: " + station.getPlan().weekdate.getYear() + " hét: " + station.getPlan().weekdate.getWeekOfWeekyear();
                String atallasok_szama = "Átállások száma: xx db";
                String atallasok_oraban = "Átállások órában: xx ó";
                all = allomas + "," + gyartasi_het + "," + atallasok_szama;

                int maxlength = getMaxLengthOfString(all.split(","));

                gd.setFont(new Font("Serif", Font.PLAIN, 10));

                int strwidth = gd.getFontMetrics().charWidth('o') * maxlength + 20;

                gd.setColor(new Color(0.3f, 0.3f, 0.3f, 0.5f));
                gd.fillRoundRect(station.inf_x - strwidth - 10, station.inf_y + 10, strwidth, 155, 8, 8);

                gd.setColor(Color.white);
                gd.setFont(new Font("Serif", Font.PLAIN, 10));
                gd.drawString(allomas, station.inf_x - strwidth - 5, station.inf_y + 25);
                gd.drawString(gyartasi_het, station.inf_x - strwidth - 5, station.inf_y + 40);

                gd.drawString("Sor terhelése:", station.inf_x - strwidth - 5, station.inf_y + 55);
                gd.drawString(atallasok_szama.replace("xx", Integer.toString(station.getValtasokSzama())), station.inf_x - strwidth - 5, station.inf_y + 135);
                gd.drawString(atallasok_oraban.replace("xx", new DecimalFormat("##.#").format(station.getValtasokOraban())), station.inf_x - strwidth - 5, station.inf_y + 150);

                if (station.isPlanA) {
                    gd.drawImage(new ImageIcon(this.getClass().getResource("IMG/ind_plan_a.png")).getImage(), station.inf_x - strwidth - 10, station.inf_y - 24, null);
                } else {
                    gd.drawImage(new ImageIcon(this.getClass().getResource("IMG/ind_plan_active.png")).getImage(), station.inf_x - strwidth - 10, station.inf_y - 24, null);
                }

                if (station.getComment().length() > 0) {
                    gd.drawImage(new ImageIcon(this.getClass().getResource("IMG/note24.png")).getImage(), station.inf_x - strwidth - 10 + 28, station.inf_y - 24, null);
                }
                station._CTRL = window.CTRL;
                station._SHIFT = window.SHIFT;
                station.can_drag = window.SHIFT;
                if (window.CTRL) {
                    gd.drawImage(new ImageIcon(this.getClass().getResource("IMG/CTRL.png")).getImage(), station.inf_x - strwidth - 10, station.inf_y + 10 + 160, null);

                }

                if (window.SHIFT) {
                    gd.drawImage(new ImageIcon(this.getClass().getResource("IMG/SHIFT.png")).getImage(), station.inf_x - strwidth - 10 + 40, station.inf_y + 10 + 160, null);

                }

                double degree = 360 * station.getPercentOfProducts();
                double sz = Math.min(60, 60);
                double cx = (station.inf_x - strwidth - 10) + (strwidth) / 2;
                double cy = station.inf_y + 90;
                double or = sz * .5;
                double ir = or * .5;
                Shape inner = new Ellipse2D.Double(cx - ir, cy - ir, ir * 2, ir * 2);
                Shape outer = new Ellipse2D.Double(cx - or, cy - or, sz, sz);
                Shape sector = new Arc2D.Double(cx - or, cy - or, sz, sz, 90 - degree, degree, Arc2D.PIE);
                Area foreground = new Area(sector);
                Area background = new Area(outer);
                Area hole = new Area(inner);

                foreground.subtract(hole);
                background.subtract(hole);
                gd.setPaint(new Color(0xDDDDDD));
                gd.fill(background);

                if (station.getPercentOfProducts() * 100 <= 50) {
                    gd.setPaint(Color.green);
                } else if (station.getPercentOfProducts() * 100 <= 90) {
                    gd.setPaint(Color.blue);
                } else {
                    gd.setPaint(Color.red);
                }

                gd.fill(foreground);
                String percent = new DecimalFormat("#.#").format(station.getPercentOfProducts() * 100) + "%";
                gd.drawString(percent, (int) cx - (gd.getFontMetrics().stringWidth(percent) / 2), (int) cy + 3);
                super.paint(gd);
            }
        };

        panel.setBackground(Color.white);
        panel.add(station);
        window.add(pane);
        pane.getViewport().add(panel);

        plan.STATIONS.add(station);
        plan.WINDOWS.add(window);

        window.setBackground(Color.white);
        window.setResizable(true);
        //window.setLayout(null);
        window.setSize(dim);
        window.setLocation(location);
        window.setIconifiable(true);
        window.setMaximizable(true);
        window.setVisible(true);
        plan.add(window);
        panel.repaint();

    }

    @Override
    public void setVisible(boolean value) {

        super.setVisible(value);

    }

    public void savePlanOld() {

        Plan pl = PlNner.PLANS.get(MainForm.TOP.getSelectedIndex());
        for (int i = 0; i < pl.STATIONS.size(); i++) {
            for (int x = 0; x < 4; x++) {
                pl.STATIONS.get(i).Z_out.actionPerformed(null);
            }
        }

        FileOutputStream out = null;
        try {

            FileDialog fd = new FileDialog(this, "Pojekt mentése", FileDialog.SAVE);
            fd.setDirectory(PlNner.DIR);
            fd.setFile(PlNner.PLANS.get(MainForm.TOP.getSelectedIndex()).getName() + ".plan");
            fd.setLocation(50, 50);
            fd.setVisible(true);

            if (fd.getFile() != null) {

                new File(fd.getDirectory() + "\\" + fd.getFile()).delete();

                Plan plan = PlNner.PLANS.get(MainForm.TOP.getSelectedIndex());

                Plan2StreamV5 PS = new Plan2StreamV5();
                PS.load(plan);

                byte[] buf = new byte[1024];
                int counter = 0;
                int len;

                FileOutputStream FOS = new FileOutputStream(fd.getDirectory() + "\\" + fd.getFile());
                //ZOS.putNextEntry(new ZipEntry(fd.getFile()));
                BufferedOutputStream BOS = new BufferedOutputStream(FOS);
                ObjectOutputStream s = new ObjectOutputStream(BOS);

                s.writeObject(PS);
                plan.NEED_TO_SAVE = false;
                s.flush();
                BOS.flush();
                s.close();
                BOS.close();
                JOptionPane.showMessageDialog(this, "Terv mentésre került az alábbi fájlnévvel " + fd.getDirectory() + "\\" + fd.getFile(), "Figyelem", JOptionPane.INFORMATION_MESSAGE);

            }
        } catch (FileNotFoundException ex) {
        } catch (NotSerializableException er) {
            System.out.println(er.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void savePlan() {

        Plan pl = PlNner.PLANS.get(MainForm.TOP.getSelectedIndex());
        for (int i = 0; i < pl.STATIONS.size(); i++) {
            for (int x = 0; x < 4; x++) {
                pl.STATIONS.get(i).Z_out.actionPerformed(null);
            }
        }

        FileOutputStream out = null;
        try {

            FileDialog fd = new FileDialog(this, "Pojekt mentése", FileDialog.SAVE);
            fd.setDirectory(PlNner.DIR);
            fd.setFile(PlNner.PLANS.get(MainForm.TOP.getSelectedIndex()).getName() + ".plan");
            fd.setLocation(50, 50);
            fd.setVisible(true);
            fd.requestFocus();

            if (fd.getFile() != null) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date = new Date(System.currentTimeMillis());
                //letrehozzuk a planner progi konyvtarat ha meg nincs
                //könyvtár létrehozása
                new File(System.getProperty("user.home") + "\\Pl@nner\\ArchiveTervek").mkdir();
                new File(fd.getDirectory() + "\\" + fd.getFile()).delete();
                try {
                    new File(System.getProperty(System.getProperty("user.home") + "\\Pl@nner\\ArchiveTervek\\" + fd.getFile().replace(".plan", formatter.format(date) + ".plan"))).delete();
                } catch (Exception e) {
                }

                Plan plan = PlNner.PLANS.get(MainForm.TOP.getSelectedIndex());

                Plan2StreamV6 PS = new Plan2StreamV6();
                PS.load(plan);

                byte[] buf = new byte[1024];
                int counter = 0;
                int len;
                //írjuk ki lokálba is
                //System.out.println(formatter.format(date));
                ZipOutputStream ZOS = new ZipOutputStream(new FileOutputStream(System.getProperty("user.home") + "\\Pl@nner\\ArchiveTervek\\" + fd.getFile().replace(".plan", formatter.format(date) + ".plan")));
                ZOS.putNextEntry(new ZipEntry(fd.getFile()));
                BufferedOutputStream BOS = new BufferedOutputStream(ZOS);
                ObjectOutputStream s = new ObjectOutputStream(BOS);

                s.writeObject(PS);
                plan.NEED_TO_SAVE = false;
                s.flush();
                BOS.flush();

                ZOS.closeEntry();
                s.close();
                BOS.close();
                //másoljuk át a filet a hálózati helyre
                //copy files using java.nio FileChannel
                File source = new File(System.getProperty("user.home") + "\\Pl@nner\\ArchiveTervek\\" + fd.getFile().replace(".plan", formatter.format(date) + ".plan"));
                File dest = new File(fd.getDirectory() + "\\" + fd.getFile());
                long start = System.nanoTime();
                copyFileUsingChannel(source, dest);
                //System.out.println("Time taken by Channel Copy = " + (System.nanoTime() - start));

                JOptionPane.showMessageDialog(this, "<html>Terv másolásra került az alábbi fájlnévvel " + fd.getDirectory() + "\\" + fd.getFile() + "<br> " + ((System.nanoTime() - start)) / 1000000000 + " sec alatt!</html>", "Figyelem", JOptionPane.INFORMATION_MESSAGE);
                byte[] f1 = Files.readAllBytes(Paths.get(System.getProperty("user.home") + "\\Pl@nner\\ArchiveTervek\\" + fd.getFile().replace(".plan", formatter.format(date) + ".plan")));
                byte[] f2 = Files.readAllBytes(Paths.get(fd.getDirectory() + "\\" + fd.getFile()));

                if (Arrays.equals(f1, f2)) {

                    JOptionPane.showMessageDialog(this, "A két file egyforma!", "Figyelem", JOptionPane.INFORMATION_MESSAGE);
                } else {

                    JOptionPane.showMessageDialog(this,
                            "Hiba a mentés során, próbáld újra!",
                            "Hiba!",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (NotSerializableException er) {
            er.printStackTrace();
            System.out.println(er.getMessage());
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void copyFileUsingChannel(File source, File dest) throws IOException {
        FileChannel sourceChannel = null;
        FileChannel destChannel = null;
        try {
            sourceChannel = new FileInputStream(source).getChannel();
            destChannel = new FileOutputStream(dest).getChannel();
            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        } finally {
            sourceChannel.close();
            destChannel.close();
        }
    }

    public void OpenPlan126() {
        try {
            FileInputStream in = null;

            FileDialog fd = new FileDialog(this, "Projekt betöltése", FileDialog.LOAD);

            fd.setLocation(50, 50);
            fd.setVisible(true);
            in = new FileInputStream(fd.getDirectory() + "\\" + fd.getFile());
            ObjectInputStream s = null;
            s = new ObjectInputStream(in);
            try {
                Plan2StreamV4 v4 = (Plan2StreamV4) s.readObject();
                //Plan2Stream ps = (Plan2Stream) s.readObject();
                v4.restore();
                MainForm.TOP.setSelectedIndex(MainForm.TOP.getTabCount());
                PlNner.CP.PLAN_LIST.setSelectedIndex(MainForm.TOP.getSelectedIndex());

            } catch (ClassCastException e) {

                in.close();
                s.close();

                in = new FileInputStream(fd.getDirectory() + "\\" + fd.getFile());
                s = new ObjectInputStream(in);

                Plan2StreamV3 ps = (Plan2StreamV3) s.readObject();
                ps.restore();
                MainForm.TOP.setSelectedIndex(MainForm.TOP.getTabCount());
                PlNner.CP.PLAN_LIST.setSelectedIndex(MainForm.TOP.getSelectedIndex());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotSerializableException er) {

        } catch (IOException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }

        PlNner.CP.tick();

    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        newPlan();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        PlNner.OP.setVisible(PlNner.DIR);
    }//GEN-LAST:event_jButton2ActionPerformed

    public void ExitProgram() {

        String ObjButtons[] = {"Igen", "Nem"};
        int PromptResult = JOptionPane.showOptionDialog(null, "Biztos vagy benne hogy ki akarsz lépni?", "Figyelem!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
        if (PromptResult == JOptionPane.YES_OPTION) {

            for (int i = MainForm.TOP.getTabCount() - 1; i > -1; i--) {
                MainForm.TOP.setSelectedIndex(i);
                ClosePlan();
            }
            PlNner.session.pullValue();
            try {
                new File(System.getProperty("user.home") + "\\Pl@nner").mkdir();
                FileOutputStream FOS = new FileOutputStream(new File(System.getProperty("user.home") + "\\Pl@nner\\session.dat"));
                ObjectOutputStream OOS = new ObjectOutputStream(FOS);

                OOS.writeObject(PlNner.session);
                OOS.flush();

            } catch (Exception ex) {
                System.out.println(ex.toString());
            }

            System.exit(0);
        }

    }


    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        savePlan();
        Thread t = new ErrorLogger();
        t.start();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        PlNner.BEALLITAS_ABLAK.init();
        PlNner.BEALLITAS_ABLAK.setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    public void ClosePlan() {

        if (MainForm.TOP.getSelectedIndex() > -1) {

            Plan plan = PlNner.PLANS.get(MainForm.TOP.getSelectedIndex());

            if (plan.NEED_TO_SAVE == true) {

                String ObjButtons[] = {"Igen", "Nem"};
                int PromptResult = JOptionPane.showOptionDialog(this, "<html>A(z) " + plan.getName() + " tervben változások történtek! <br> Szeretné bezárás elött menteni?</html>", "Figyelem!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
                if (PromptResult == JOptionPane.YES_OPTION) {
                    savePlan();
                    ClosePlan();
                    PlNner.PH.tick();
                    PlNner.CP.tick();
                } else {

                    plan.removeAll();
                    plan.setVisible(false);
                    PlNner.PLANS.remove(MainForm.TOP.getSelectedIndex());
                    plan = null;
                    MainForm.TOP.remove(MainForm.TOP.getSelectedIndex());
                    PlNner.CP.tick();
                    PlNner.PH.tick();
                }
            } else {
                plan.removeAll();
                plan.setVisible(false);
                PlNner.PLANS.remove(MainForm.TOP.getSelectedIndex());
                plan = null;
                MainForm.TOP.remove(MainForm.TOP.getSelectedIndex());
            }

        }
    }

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        PlNner.SW.setVisible(true);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void TOPMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TOPMousePressed
        PlNner.CP.PLAN_LIST.setSelectedIndex(MainForm.TOP.getSelectedIndex());
    }//GEN-LAST:event_TOPMousePressed

    private void TOPMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TOPMouseReleased
        PlNner.CP.PLAN_LIST.setSelectedIndex(MainForm.TOP.getSelectedIndex());
        PlNner.SIB.setVisible(false);
    }//GEN-LAST:event_TOPMouseReleased

    private void TOPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TOPMouseClicked
        PlNner.CP.PLAN_LIST.setSelectedIndex(MainForm.TOP.getSelectedIndex());
    }//GEN-LAST:event_TOPMouseClicked

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        PlNner.ABOUT.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        PlNner.UPDATE_NOTES.setVisible(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        PlNner.WPW.setVisible(true);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void TOPMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TOPMouseMoved


    }//GEN-LAST:event_TOPMouseMoved

    private void CALENDARMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CALENDARMouseClicked
        getEventsFromPlan();
    }//GEN-LAST:event_CALENDARMouseClicked

    private void SEL_HOUR_AROUNDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SEL_HOUR_AROUNDActionPerformed
        getEventsFromPlan();
    }//GEN-LAST:event_SEL_HOUR_AROUNDActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        getEventsFromPlan();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        PlNner.MS.MESSAGE.setText(EVENTS_PRODUCTION.getText());
        PlNner.MS.SUBJECT.setText(PlNner.USER.name + " eseménykezelője ");
        PlNner.MS.init();
        PlNner.MS.refresh();
        PlNner.MS.station = null;
        PlNner.MS.setVisible(true);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        TOP.setVisible(false);

        TOPPAN.setMinimumSize(new Dimension(1920, 1080));
        TOPPAN.setPreferredSize(new Dimension(1920, 1080));
        TOPPAN.setSize(new Dimension(1920, 1080));

        TOOLHELP.setMinimumSize(new Dimension(1920, 1080));
        TOOLHELP.setPreferredSize(new Dimension(1920, 1080));
        TOOLHELP.setSize(new Dimension(1920, 1080));

        CAL.setMinimumSize(new Dimension(1920, 1080));
        CAL.setPreferredSize(new Dimension(1920, 1080));
        CAL.setSize(new Dimension(1920, 1080));

    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        MeshWebInterface MESH = new MeshWebInterface();
        if (JOB_NUMBER.getText().length() > 0) {
            MESH_TABLE.setModel(MESH.getJobResult(JOB_NUMBER.getText().split(",")));
        }


    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        JOB_NUMBER.setText("");
        MeshWebInterface MESH = new MeshWebInterface();

        MESH_TABLE.setModel(MESH.getJobResult(PlNner.getAllJobNumberFromOpenedPlan()));


    }//GEN-LAST:event_jButton13ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        PlNner.BEALLITAS_ABLAK.init();
        PlNner.BEALLITAS_ABLAK.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMExitActionPerformed
        ExitProgram();
    }//GEN-LAST:event_jMExitActionPerformed

    private void jMNewPlanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMNewPlanActionPerformed

        newPlan();
    }//GEN-LAST:event_jMNewPlanActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        PlNner.SE.init();
        PlNner.SE.setVisible(true);

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        PlNner.PNDATA.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane BOTTOM;
    public static javax.swing.JPanel CAL;
    private pl.nner.JMonth CALENDAR;
    private javax.swing.JScrollPane Desktop;
    private javax.swing.JEditorPane EVENTS_PRODUCTION;
    private javax.swing.JTextField JOB_NUMBER;
    private javax.swing.JTable MESH_TABLE;
    private javax.swing.JCheckBox SEL_HOUR_AROUND;
    private javax.swing.JPanel SFDC_PANEL;
    private javax.swing.JSplitPane SPLIT;
    private javax.swing.JTabbedPane TOOLHELP;
    public static javax.swing.JTabbedPane TOP;
    private javax.swing.JPanel TOPPAN;
    private javax.swing.JSplitPane TOPPANDIV;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMEdit;
    private javax.swing.JMenuItem jMExit;
    private javax.swing.JMenuItem jMNewPlan;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JMenu jMenuProgram;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
