/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.nner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author krisztian_csekme1
 */
public class MySQL {

    private String Database_name;
    private String Host;
    private String Port;
    private String User;
    private String Pass;

    private Connection connection = null;
    private Statement statement = null;

    public MySQL(String host, String port, String user, String pass, String db) {
        Host = host;
        Port = port;
        User = user;
        Pass = pass;
        Database_name = db;
    }

    public void insertCommand(String command) {
        //letryoljuk a connectiont

        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://" + Host + ":" + Port + "/" + Database_name + "?user=" + User + "&password=" + Pass + "&useUnicode=true&characterEncoding=utf8&characterSetResults=utf8&autoReconnect=true&failOverReadOnly=false&maxReconnects=5");

            if (Port.equals("localhost")) {
                con.close();
                con = DriverManager.getConnection("jdbc:mysql://" + Host + "/" + Database_name + "?user=" + User + "&password=" + Pass + "&useUnicode=true&characterEncoding=utf8&characterSetResults=utf8&autoReconnect=true&failOverReadOnly=false&maxReconnects=5");
            }

            Statement st;

            st = con.createStatement();
            st.executeUpdate(command);

            st.close();
            con.close();

        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage().toString());
            hiba(e.getMessage());
        }

    }

    public void insertObjectArraytoTable(String table, Object[] data) {

        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://" + Host + ":" + Port + "/" + Database_name + "?user=" + User + "&password=" + Pass + "&useUnicode=true&characterEncoding=utf8&characterSetResults=utf8&autoReconnect=true&failOverReadOnly=false&maxReconnects=5");

            if (Port.equals("localhost")) {
                con.close();
                con = DriverManager.getConnection("jdbc:mysql://" + Host + "/" + Database_name + "?user=" + User + "&password=" + Pass + "&useUnicode=true&characterEncoding=utf8&characterSetResults=utf8&autoReconnect=true&failOverReadOnly=false&maxReconnects=5");
            }

            String ertek = "";
            String h2 = "";

            Statement stmc = con.createStatement();
            Statement st;
            try (ResultSet rs = stmc.executeQuery("SELECT * from " + table)) {
                ResultSetMetaData rsMetaData = rs.getMetaData();
                int numberOfColumns = rsMetaData.getColumnCount();
                for (int i = 1; i < numberOfColumns + 1; i++) {
                    String columnName = rsMetaData.getColumnName(i);

                    h2 = h2 + columnName + ",";
                }
                h2 = h2.substring(0, h2.length() - 1);
                st = con.createStatement();
                ertek = "";
                for (int col = 0; col < data.length; col++) {
                    ertek = ertek + "'" + data[col] + "',";

                }
                ertek = ertek.substring(0, ertek.length() - 1);
                st.executeUpdate("INSERT INTO " + table.toLowerCase() + " (" + h2 + ")" + " VALUES (" + ertek + ")");
                //st.executeUpdate("REPLACE INTO " + table.toLowerCase() + " (" + h2 + ")" + " VALUES (" + ertek + ")");
            }
            st.close();
            con.close();

        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
            hiba(e.getMessage());
        }

    }

    public boolean isDBAlive() {
        try {
            Connection con = null;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://" + Host + ":" + Port + "/" + Database_name + "?user=" + User + "&password=" + Pass + "&useUnicode=true&characterEncoding=utf8&characterSetResults=utf8&autoReconnect=true&failOverReadOnly=false&maxReconnects=5");

            if (Port.equals("localhost")) {
                con.close();
                con = DriverManager.getConnection("jdbc:mysql://" + Host + "/" + Database_name + "?user=" + User + "&password=" + Pass + "&useUnicode=true&characterEncoding=utf8&characterSetResults=utf8&autoReconnect=true&failOverReadOnly=false&maxReconnects=5");

            }

            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            hiba(ex.getMessage());
            return false;
        }

    }

    public Object getCellValue(String query) {

        Object res = null;
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://" + Host + ":" + Port + "/" + Database_name + "?user=" + User + "&password=" + Pass + "&useUnicode=true&characterEncoding=utf8&characterSetResults=utf8&autoReconnect=true&failOverReadOnly=false&maxReconnects=5");

            if (Port.equals("localhost")) {
                con.close();
                con = DriverManager.getConnection("jdbc:mysql://" + Host + "/" + Database_name + "?user=" + User + "&password=" + Pass + "&useUnicode=true&characterEncoding=utf8&characterSetResults=utf8&autoReconnect=true&failOverReadOnly=false&maxReconnects=5");
            }
            Statement stmc = con.createStatement();
            stmc.executeQuery(query);
            ResultSet rs = stmc.getResultSet();
            if (rs != null) {
                rs.beforeFirst();
                rs.next();
                res = rs.getObject(1);
            }
            con.close();

        } catch (InstantiationException e) {
            hiba(e.getMessage());
        } catch (IllegalAccessException e) {
            hiba(e.getMessage());
        } catch (ClassNotFoundException e) {
            hiba(e.getMessage());
        } catch (SQLException e) {
            hiba(e.getMessage());
            return -1;
        }

        return res;
    }

    public DefaultComboBoxModel getComboBoxModel(String query, int Column) {

        DefaultComboBoxModel adatok = new DefaultComboBoxModel();
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://" + Host + ":" + Port + "/" + Database_name + "?user=" + User + "&password=" + Pass + "&useUnicode=true&characterEncoding=utf8&characterSetResults=utf8&autoReconnect=true&failOverReadOnly=false&maxReconnects=5");

            if (Port.equals("localhost")) {
                con.close();
                con = DriverManager.getConnection("jdbc:mysql://" + Host + "/" + Database_name + "?user=" + User + "&password=" + Pass + "&useUnicode=true&characterEncoding=utf8&characterSetResults=utf8&autoReconnect=true&failOverReadOnly=false&maxReconnects=5");
            }

            Statement stmc = con.createStatement();
            stmc.executeQuery(query);
            ResultSet rs = stmc.getResultSet();

            int rows = 0;
            while (rs.next()) {
                rows++;
            }
            rs.beforeFirst();

            if (rows > 0) {
                while (rs.next()) {

                    adatok.addElement(rs.getString(Column + 1));

                }
            }

            rs.beforeFirst();
            con.close();

        } catch (ClassNotFoundException e) {
            hiba(e.getMessage());
        } catch (SQLException e) {
            hiba(e.getMessage());
        } catch (InstantiationException ex) {
            hiba(ex.getMessage());
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            hiba(ex.getMessage());
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }

        return adatok;
    }

    public DefaultTableModel getDataTableModel(String query) {

        DefaultTableModel adatok = new DefaultTableModel();
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://" + Host + ":" + Port + "/" + Database_name + "?user=" + User + "&password=" + Pass + "&useUnicode=true&characterEncoding=utf8&characterSetResults=utf8&autoReconnect=true&failOverReadOnly=false&maxReconnects=5");

            if (Port.equals("localhost")) {
                con.close();
                con = DriverManager.getConnection("jdbc:mysql://" + Host + "/" + Database_name + "?user=" + User + "&password=" + Pass + "&useUnicode=true&characterEncoding=utf8&characterSetResults=utf8&autoReconnect=true&failOverReadOnly=false&maxReconnects=5");
            }

            Statement stmc = con.createStatement();
            stmc.executeQuery(query);
            ResultSet rs = stmc.getResultSet();
            ResultSetMetaData rsMetaData = rs.getMetaData();
            Vector<String> head = new Vector<String>();

            for (int i = 1; i < rsMetaData.getColumnCount() + 1; i++) {
                head.add(rsMetaData.getColumnName(i));
            }

            adatok = new DefaultTableModel(head, 0);

            int rows = 0;
            while (rs.next()) {
                rows++;
            }
            rs.beforeFirst();

            if (rows > 0) {
                while (rs.next()) {
                    Object[] adatsor = new Object[rsMetaData.getColumnCount()];

                    for (int i = 1; i < rsMetaData.getColumnCount() + 1; i++) {
                        adatsor[i - 1] = (rs.getString(i));

                    }
                    adatok.addRow(adatsor);

                }
            }

            rs.beforeFirst();
            con.close();
        } catch (InstantiationException e) {
            System.out.println(e.getMessage());
            hiba(e.getMessage());
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
            hiba(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            hiba(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            hiba(e.getMessage());
        }

        return adatok;
    }

    private void hiba(String message) {

        //custom title, error icon
        JOptionPane.showMessageDialog(null,
                "<html>Adatbázis kapcsolódási probléma!<br>" + message + "</html>",
                "Kapcsolat hiba!",
                JOptionPane.ERROR_MESSAGE);

    }

}
