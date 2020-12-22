/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.nner;

import com.sun.deploy.uitoolkit.impl.fx.ui.resources.ResourceManager;
import java.awt.Toolkit;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import org.ini4j.Wini;
import org.joda.time.DateTime;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author krisztian_csekme1
 */
class WO_LOG {

    public String wologfilenev;
    public String wologpath;
    public String wologsheetname;
    public Integer wologjoboszlop;
    public Integer wologpnoszlop;
    public Integer wologqtyoszlop;
    public Integer wologstatusoszlop;

    public WO_LOG() {

    }

    Object[] get_header() {
        return new Object[]{"Fájlnév", "Elérés", "Sheet név", "Job oszlop", "Partnumber", "Státusz oszlop", "Darabszám"};
    }

    public void setRow(Object[] _row) {
        wologfilenev = (String) _row[0];
        wologpath = (String) _row[1];
        wologsheetname = (String) _row[2];
        wologjoboszlop = Integer.parseInt((String) _row[3]);
        wologpnoszlop = Integer.parseInt((String) _row[4]);
        wologstatusoszlop = Integer.parseInt((String) _row[5]);
        wologqtyoszlop = Integer.parseInt((String) _row[6]);

    }

    Object[] get_row() {
        return new Object[]{wologfilenev, wologpath, wologsheetname, wologjoboszlop, wologpnoszlop, wologstatusoszlop, wologqtyoszlop};
    }

}

public class PlNner {

    public static boolean can = false;
    //public static String VERSION = "2.3621";
    public static String VERSION = "3.0000";
    public static String VER_FOLLOW;
    public static String VER_COMMENT;
    public static boolean isUPDATED;
    public static int REMINDER = 0;
    public static int ErrorMsgDelay = 600000; //10 perc

    public static Session session = new Session();

    public static String PLANTABLENAME = "terv";

    public static DefaultTableModel SFDC_MODEL = new DefaultTableModel();

    //4 ROLAND
    //Tervező segéd
    public static int SW_NOW_AND_FUTURE = 0;
    public static int SW_TERVEZOS_SEGED = 1; //Tervező segéd autómatikus indítása
    public static int SW_PAN = 1;  //Panelizáció ellenőrzése
    public static int SW_ENGINEER = 1; //Mérnöki gyártás ellenőrzése
    public static int VAL_FROM = 8; //Mérnöki gyártás indítási intervallum kezdeti
    public static int VAL_TO = 10; //Mérnöki gyártás indítási intervallum max
    public static int POP_UP = 10; //Észrevételek felugrásának gyakorisága időintervallumban
    public static float OPACITY_TERV_SEGED = 0.9f; //Tervező segéd átlátszósága
    public static int SW_QTY_LOST = 1; //elhagyott darabszámok ellenőrzése
    public static int SW_QTY_AUTO_CORRECT = 0; //elhagyott darabszámok autómatikus kiígazítása
    public static int SW_KIT = 1; //Kittekési folyamatok ellenőrzése
    public static int SW_PARTNUMBER_PLR = 0; //Partnumber meglétének ellenőrzése
    //Program beállítások
    //public static String DIR = "S:\\SiteData\\BUD1\\EMS\\planning\\Reports\\Robi\\Csekme\\Line_Plan\\Pl@nner\\TERV";
    public static String DIR = "S:\\SiteData\\BUD1\\EMS\\planning\\Gyartastervek";
    public static int LOGIN_REQ = 1; //Bejelentkezés megkövetelése
    //Control Panel
    /*
     *Na ez fasza
     *@see #Control panel autómatikus indulása
     */
    public static int SW_CONTROL_PANEL = 1; //Control panel autómatikus indulása
    public static float OPACITY_CONTROL_PANEL = 0.9f; //Control Panel átlátszósága

    //4 ROLAND
    public static sql_param my_db_param, plr_db_param;
    public static int DEFAULT_RUNUP_MIN = 30;
    public static int LAST_PRODUCED_CHECK = 90; //nap

    //  public static String DIR = "f:\\TERV";
    public static String[] list_honapok = new String[]{"Január", "Február", "Március", "Április", "Május", "Június", "Július", "Augusztus", "Szeptember", "Október", "November", "December"};
    public static String[] list_napok = new String[]{"Hétfő", "Kedd", "Szerda", "Csütörtök", "Péntek", "Szombat", "Vasárnap"};
    public static List<Plan> PLANS = new ArrayList<>();

    //4ROLAND
    public static List<DefaultTableModel> WO_LOGOK = new ArrayList<>();
    //4ROLAND

    public static int MSZAK = 12; // \n órásműszak
    public static Loader LOADER;
    public static NewProduct NEW_PROD; //Új termék ablak
    public static Properties PROP;
    public static MainForm MF;
    public static DownTimeWindow DOWN_TIME_WINDOW;
    public static SqlWindow SQL;
    public static StationEditor SE;
    public static NewPlanWindow NPW;
    public static NewStationWindow NSW;
    public static SplitWindow SP;
    public static JobRiportWindow JRW;
    public static OpenProject OP;
    public static Note NOTE;
    public static SearchWindow SW;
    public static StationProperties SProp;
    public static ControlPanel CP;
    public static About ABOUT;
    public static UpdateNotes UPDATE_NOTES;
    public static MailSender MS;
    public static PlanHelper PH;
    public static ErrorWindow EW;
    public static NewKitWindow NKW;
    public static Welcome_Screen WS;
    public static String[] SEQUENCES = new String[]{"TopLevel", "SMT1", "SMT2", "BE"};
    public static int MSZAK_REND = 12; //hány órás műszak
    public static XSSFModel xssf;
    public static DefaultTableModel HELPMODEL;
    public static DefaultTableModel BOMMODEL;

    public static DefaultTableModel JOBINFO;
    public static DefaultTableModel ALTERNATIVE_DB;
    public static ERRORMESSAGE MSG;
    public static int alternative_database;
    public static user USER;
    public static USERSETUP USR_SET;
    public static LengthSetup LS;
    public static PnMaintenance PnM;
    public static NewPartnumberWindow NewPW;
    public static WhereProducedWindow WPW;
    public static StationInformationBox SIB;
    public static String USER_PICS_DIR;
    public static PN_DATA_WINDOW PNDATA;
    public static Kapocshiba kapocsiha;

    //Roli változók kezdete
    public static Beallitasablak BEALLITAS_ABLAK;
    public static String inifile = "setup.ini";
    public static List<WO_LOG> WO = new ArrayList<>();
    public static Wini ini;
    public static Integer NumberOfCustomer;
    public static enum errorlogger {mentes,hiba,tervvaltozas};
    
    //Roli változók vége

    /*
     Létrehoztam a Mysql DB-t a 143.116.140.114-es bud1eudbgu01-es szerveren.
     DB: planningdb
     user: plan
     pw: plan500
     */
    public static MySQL PLR_DB;
    public static MySQL MYDB_DB;

    // public static MySQL MYDB_DB = new MySQL("143.116.140.114", "3306", "plan", "plan500", "planningdb");
    //public static MySQL PLR_DB= new MySQL("csekme.no-ip.org", "3306", "cpi", "cpi602", "plrdb");
    /**
     * @param args the command line arguments
     */
    /**
     *
     * @param pathname
     * @param filenames
     * @param args the command line arguments
     */
//    static Runnable WO_TASK = new Runnable() {
//
//        @Override
//        public void run() {
//
//        }
//    };

    public static void main(String[] args) throws ClassCastException {

        try {
            ini = new Wini(new File(inifile));
        } catch (IOException ex) {
            Logger.getLogger(PlNner.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Könyvtárszerkezet létrehozása:");
        new File(System.getProperty("user.home") + "\\Pl@nner").mkdir();
        new File(System.getProperty("user.home") + "\\Pl@nner\\Temp").mkdir();

        my_db_param = new sql_param();
        plr_db_param = new sql_param();

        try {

            FileInputStream FIS = new FileInputStream(new File(System.getProperty("user.home") + "\\Pl@nner\\session.dat"));
            System.out.println(System.getProperty("user.home") + "\\Pl@nner");
            ObjectInputStream OIS = new ObjectInputStream(FIS);
            session = (Session) OIS.readObject();
            FIS.close();
            OIS.close();

            session.pushValue();

        } catch (Exception er) {
            session = new Session();
        }
        //ellenőrizzük és létrehozzuk az íráshoz szükséges fileokat
        chkMake("from.txt");
        chkMake("to.txt");
        chkMake("message.txt");
        chkMake("subject.txt");

        //PLR
        try {
            plr_db_param.host = ini.get("Mysql", "PLR_DB" + "_Ip");
            plr_db_param.db = ini.get("Mysql", "PLR_DB" + "_database");
            plr_db_param.pass = ini.get("Mysql", "PLR_DB" + "_password");
            plr_db_param.port = ini.get("Mysql", "PLR_DB" + "_Port");
            plr_db_param.user = ini.get("Mysql", "PLR_DB" + "_user");

        } catch (Exception ex) {

            System.out.println("PLR adatbázis beállítási probléma...");

        }

        //MyDB
        try {
            my_db_param.host = ini.get("Mysql", "MY_DB" + "_Ip");
            my_db_param.db = ini.get("Mysql", "MY_DB" + "_database");
            my_db_param.pass = ini.get("Mysql", "MY_DB" + "_password");
            my_db_param.port = ini.get("Mysql", "MY_DB" + "_Port");
            my_db_param.user = ini.get("Mysql", "MY_DB" + "_user");

        } catch (Exception ex) {
            System.out.println("Pl@nner adatbázis beállítási probléma...");
        }
        MYDB_DB = new MySQL(my_db_param.host, my_db_param.port, my_db_param.user, my_db_param.pass, my_db_param.db);
        PLR_DB = new MySQL(plr_db_param.host, plr_db_param.port, plr_db_param.user, plr_db_param.pass, plr_db_param.db);

        if (!PLR_DB.isDBAlive()) {

            JOptionPane.showMessageDialog(null, "<html>PLR adatbázis kapcsolódási probléma!<br>Kérem jelezze a fejlesztőnek!</html>", "Figyelem!", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
        if (!MYDB_DB.isDBAlive()) {
            JOptionPane.showMessageDialog(null, "<html>Planning adatbázis kapcsolódási probléma!<br>Kérem jelezze a fejlesztőnek!</html>", "Figyelem!", JOptionPane.INFORMATION_MESSAGE);

            System.exit(0);
        }

        System.out.println("Program indítása...");
        System.out.println("Program verzió: v" + VERSION);

        isUPDATED = true;
        VER_FOLLOW = null;

        xssf = new XSSFModel();

        HELPMODEL = MYDB_DB.getDataTableModel("SELECT * from pn_data;");
        //BOMMODEL = MYDB_DB.getDataTableModel("SELECT * from indented_bom;");

        /*
        
         HELPMODEL = xssf.getExcelData("datas.xlsx", "datas");
         if (HELPMODEL == null) {
         HELPMODEL = new DefaultTableModel(new Object[]{"PartNumber", "OutSideCustomer", "Project", "Description", "InSideCustomer", "Comment"}, 0);
         }
         */
        JOBINFO = xssf.getExcelData("datas.xlsx", "jobinfo");
        if (JOBINFO == null) {
            JOBINFO = new DefaultTableModel(new Object[]{"JobInfo", "Comment"}, 0);
        }

        ALTERNATIVE_DB = xssf.getExcelData("datas.xlsx", "alternative_db");
        if (ALTERNATIVE_DB == null) {
            ALTERNATIVE_DB = new DefaultTableModel(new Object[]{"id", "oraclepn", "smtprogramname", "smtline", "sequence", "boardnumber", "mertido", "gyorsmeres", "kalkulalt", "eff"}, 0);
        }

        //ini file létrehozása
        //változók feltöltése ini file-ból
        if (ini.get("Setup", "alternative_database") != null) {
            alternative_database = Integer.parseInt(ini.get("Setup", "alternative_database"));
        }

        if (ini.get("Setup", "user_pics_dir") != null) {
            USER_PICS_DIR = ini.get("Setup", "user_pics_dir");
        }

        if (alternative_database == 1) {
            System.out.println("Alternatív adatbázis használata...");
        } else {
            System.out.println("PLR adatbázis használata...");
        }

        Wologvaltozokbekerese();

        LOGIN login = new LOGIN();
        login.setVisible(true);
        while (!can) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(PlNner.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PlNner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(PlNner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(PlNner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(PlNner.class.getName()).log(Level.SEVERE, null, ex);
        }

        MF = new MainForm();

        MF.setVisible(true);

    }

    //ellenorzo, letrehozo method
    public static void chkMake(String filename) {

        try {
            //hozzuk letre a levelkuldeshez szukseges fileoket
            FileInputStream FIS = new FileInputStream(new File(System.getProperty("user.home") + "\\Pl@nner\\" + filename));

        } catch (FileNotFoundException ex) {
            System.out.println("Nem letezik a " + filename + ", létrehozzuk!");
            try {
                FileWriter myWriter = new FileWriter(System.getProperty("user.home") + "\\Pl@nner\\" + filename);
                myWriter.write("<html><body>Az alap üzenet!</body></html>");
                myWriter.close();
                System.out.println(filename + " létrehozva!");
            } catch (Exception e) {
            }
        }

    }

    public static void Wologvaltozokbekerese() {

        if (ini.get("WO log", "NumberOfCustomer") != null) {
            NumberOfCustomer = Integer.parseInt(ini.get("WO log", "NumberOfCustomer"));

            for (int i = 0; i < NumberOfCustomer; i++) {

                WO.add(new WO_LOG());

                String[] temp_wologfilenev = null;
                if (ini.get("WO log", "wologfilenev") != null) {
                    temp_wologfilenev = ini.get("WO log", "wologfilenev").split(",");

                }
                String[] temp_wologpath = null;
                if (ini.get("WO log", "wologpath") != null) {
                    temp_wologpath = ini.get("WO log", "wologpath").split(",");

                }
                String[] temp_wologsheetname = null;
                if (ini.get("WO log", "wologsheetname") != null) {
                    temp_wologsheetname = ini.get("WO log", "wologsheetname").split(",");

                }
                String[] temp_wologjoboszlop = null;
                if (ini.get("WO log", "wologjoboszlop") != null) {
                    temp_wologjoboszlop = ini.get("WO log", "wologjoboszlop").split(",");

                }
                String[] temp_wologpnoszlop = null;
                if (ini.get("WO log", "wologpnoszlop") != null) {
                    temp_wologpnoszlop = ini.get("WO log", "wologpnoszlop").split(",");

                }
                String[] temp_wologqtyoszlop = null;
                if (ini.get("WO log", "wologqtyoszlop") != null) {
                    temp_wologqtyoszlop = ini.get("WO log", "wologqtyoszlop").split(",");

                }
                String[] temp_wologstatusoszlop = null;
                if (ini.get("WO log", "wologstatusoszlop") != null) {
                    temp_wologstatusoszlop = ini.get("WO log", "wologstatusoszlop").split(",");

                }

                WO.get(WO.size() - 1).wologfilenev = temp_wologfilenev[i];
                WO.get(WO.size() - 1).wologjoboszlop = Integer.parseInt(temp_wologjoboszlop[i]);
                WO.get(WO.size() - 1).wologpath = temp_wologpath[i];
                WO.get(WO.size() - 1).wologpnoszlop = Integer.parseInt(temp_wologpnoszlop[i]);
                WO.get(WO.size() - 1).wologqtyoszlop = Integer.parseInt(temp_wologqtyoszlop[i]);
                WO.get(WO.size() - 1).wologsheetname = temp_wologsheetname[i];
                WO.get(WO.size() - 1).wologstatusoszlop = Integer.parseInt(temp_wologstatusoszlop[i]);

            }
        }
    }

    public static DateTime getFirstMondayDayOfWeek(int year, int week) {
        DateTime start = new DateTime(year, 1, 1, 1, 0);
        for (int el = 0; el < 7; el++) {
            if (start.plusDays(el).getDayOfWeek() == 1) {
                start = start.plusDays(el);
                break;
            }
        }

        if (start.getDayOfYear() > 4) {
            start = start.minusWeeks(1);
        }

        start = start.plusWeeks(week - 1);

        return start;
    }

    public static String[] getAllJobNumberFromOpenedPlan() {

        String jobs = "";

        for (int p = 0; p < PLANS.size(); p++) {
            Plan plan = PLANS.get(p);

            for (int s = 0; s < plan.STATIONS.size(); s++) {

                Station station = plan.STATIONS.get(s);

                station.recalcJobs();

                for (int j = 0; j < station.JOBS.size(); j++) {

                    if (!jobs.contains(station.JOBS.get(j).jobname)) {
                        jobs += station.JOBS.get(j).jobname + ",";
                    }
                }

            }

        }

        if (jobs.length() > 2) {
            jobs = jobs.substring(0, jobs.length() - 1);
        }

        return jobs.split(",");
    }

    public static String getDateTimeShiftFormat(DateTime date) {

        String vissza = "";

        int hour = date.getHourOfDay();

        if ((hour >= 6) && (hour < 14)) {
            vissza += list_napok[date.getDayOfWeek() - 1] + " - ";
            vissza += "Nappal";
        } else if ((hour >= 14) && (hour < 22)) {
            vissza += list_napok[date.getDayOfWeek() - 1] + " - ";
            vissza += "Nappal";
        } else if ((hour >= 22) && (hour <= 23)) {
            vissza += list_napok[date.getDayOfWeek() - 1] + " - ";
            vissza += "Este";
        } else if ((hour >= 0) && (hour < 6)) {
            try {
                vissza += list_napok[date.minusDays(1).getDayOfWeek() - 1] + " - ";
                vissza += "Este";
            } catch (Exception e) {

            }

        }

        return vissza;

    }

    public static String replacer(String string) {
        String st = string;
        st = st.replaceAll(" ", "");
        st = st.replaceAll("ő", "o");
        st = st.replaceAll("Ő", "o");
        st = st.replaceAll("ö", "o");
        st = st.replaceAll("Ő", "o");
        st = st.replaceAll("ü", "u");
        st = st.replaceAll("Ü", "u");
        st = st.replaceAll("ű", "u");
        st = st.replaceAll("Ű", "u");
        st = st.replaceAll("ó", "o");
        st = st.replaceAll("Ó", "o");
        st = st.replaceAll("á", "a");
        st = st.replaceAll("Á", "a");
        st = st.replaceAll("é", "e");
        st = st.replaceAll("É", "e");
        st = st.replaceAll("í", "i");
        st = st.replaceAll("Í", "i");
        st = st.replaceAll("ú", "u");
        st = st.replaceAll("Ú", "u");
        st = st.replaceAll("-", "");
        return st;
    }

    public static String getCurrentDateTime(DateTime date) {
        return date.getYear() + ". " + list_honapok[date.getMonthOfYear() - 1] + ". " + date.getDayOfMonth() + ", " + list_napok[date.getDayOfWeek() - 1] + "  " + String.format("%02d", date.getHourOfDay()) + ":" + String.format("%02d", date.getMinuteOfHour()) + ":" + String.format("%02d", date.getSecondOfMinute());
    }

    public static String getCurrentDate(DateTime date) {
        return date.getYear() + ". " + list_honapok[date.getMonthOfYear() - 1] + ". " + date.getDayOfMonth() + ", " + list_napok[date.getDayOfWeek() - 1];
    }

    public static ImageIcon getPICS(String id) {

        try {

            if (new File(USER_PICS_DIR + "\\" + id + ".png").exists()) {
                return new ImageIcon(USER_PICS_DIR + "\\" + id + ".png");
            } else if (new File(USER_PICS_DIR + "\\" + id + ".jpg").exists()) {
                return new ImageIcon(USER_PICS_DIR + "\\" + id + ".jpg");
            } else {
                return new ImageIcon(PlNner.class.getResource("IMG/USR_001.png"));
            }

        } catch (Exception e) {

            return new ImageIcon(PlNner.class.getResource("IMG/USR_001.png"));

        }

    }

    public static void doVersionCheck() throws InterruptedException {
        Runnable CHECK = new Runnable() {

            @Override
            public void run() {
                try {
                    Object res = MYDB_DB.getCellValue("SELECT ver FROM versionf2 ORDER BY ID DESC LIMIT 1");
                    if (res != null) {
                        VER_FOLLOW = res.toString();
                        res = MYDB_DB.getCellValue("SELECT comment FROM versionf2 ORDER BY ID DESC LIMIT 1");
                        if (res != null) {
                            VER_COMMENT = res.toString();
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        };

        Thread szal = new Thread(CHECK);
        szal.start();
        //varjon ra a main szal
        //szal.join();

    }

    public static void doTempSave() throws InterruptedException {
        Runnable SAVE = new Runnable() {

            @Override
            public void run() {
                //megnezzuk, hogy milyen sori tervek vannak nyitva
                for (int i = 0; i < MainForm.TOP.getTabCount(); i++) {
                    try {

                        String sor = MainForm.TOP.getComponentAt(i).getName().substring(0, 1);
                        //System.out.println(sor);
                        File myObj = new File(System.getProperty("user.home") + "\\Pl@nner\\Temp\\" + sor + ".plan");
                        if (myObj.delete()) {
                            //System.out.println("Deleted the file: " + myObj.getName());
                        } else {
                            //System.out.println("Failed to delete the file.");
                        }
                        //lementjük
                        Plan plan = PlNner.PLANS.get(MainForm.TOP.getSelectedIndex());
                        Plan2StreamV6 PS = new Plan2StreamV6();
                        PS.load(plan);
                        ZipOutputStream ZOS = new ZipOutputStream(new FileOutputStream(System.getProperty("user.home") + "\\Pl@nner\\Temp\\" + sor + ".plan"));
                        ZOS.putNextEntry(new ZipEntry(System.getProperty("user.home") + "\\Pl@nner\\Temp\\" + sor + ".plan"));
                        BufferedOutputStream BOS = new BufferedOutputStream(ZOS);
                        ObjectOutputStream s = new ObjectOutputStream(BOS);

                        s.writeObject(PS);
                        //plan.NEED_TO_SAVE = false;
                        s.flush();
                        BOS.flush();

                        ZOS.closeEntry();
                        s.close();
                        BOS.close();

                    } catch (Exception e) {
                    }
                }
            }
        };

        Thread szal = new Thread(SAVE);

        szal.start();
        //varjon ra a main szal

        //szal.join();

    }

}

class sql_param {

    String port;
    String host;
    String user;
    String pass;
    String db;

}
