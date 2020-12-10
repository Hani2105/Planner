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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author krisztian_csekme
 */
public class MeshWebInterface {

    private final String user = "MESR_ReadOnly";
    private final String pass = "MESR_ReadOnly";
    private final String connectionString = "jdbc:sqlserver://BUD1EUMESW01";

    
    
    
    
    
    public DefaultTableModel getJobResult(String[] jobnumbers){
        if (jobnumbers.length>0){
        String query = "SELECT serial.Serial_Number, Shop_Order.Shop_Order_Number, part.Part_Number, Location.Workstation, serial.Qty, unit_status.Unit_Status, serial.Location_DateTime FROM MESR.dbo.serial LEFT JOIN MESR.dbo.Shop_Order ON Shop_Order.PK_Id = serial.shop_order_pk_id LEFT JOIN MESR.dbo.Location ON Location.Pk_Id = serial.Location_PK_Id LEFT JOIN MESR.dbo.part ON part.Pk_Id = serial.part_pk_id LEFT JOIN MESR.dbo.unit_status ON unit_status.Pk_Id = serial.unit_status_pk_id WHERE Shop_Order.Shop_Order_Number IN (partnumber)";
        String jobs="";
        
        
        
         
        
        for (int t=0; t<jobnumbers.length; t++){
            jobs+="'" + jobnumbers[t] + "',";
        }
        
        jobs = jobs.substring(0,jobs.length()-1);
        
        return getDataTableModel(query.replace("partnumber", jobs));
        }else{
            return null;
        }
    }
    
    
    
    
    
    
    public DefaultTableModel getDataTableModel(String query) {
        DefaultTableModel adatok = new DefaultTableModel();
        Connection con = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionString, user, pass);
            Statement stmc = con.createStatement();
            stmc.executeQuery(query);
            ResultSet rs = stmc.getResultSet();
            ResultSetMetaData rsMetaData = rs.getMetaData();
            Vector<String> head = new Vector<String>();

            for (int i = 1; i < rsMetaData.getColumnCount() + 1; i++) {
                head.add(rsMetaData.getColumnName(i));
            }

            adatok = new DefaultTableModel(head, 0);


                while (rs.next()) {
                    Object[] adatsor = new Object[rsMetaData.getColumnCount()];

                    for (int i = 1; i < rsMetaData.getColumnCount() + 1; i++) {
                        adatsor[i - 1] = (rs.getString(i));

                    }
                    adatok.addRow(adatsor);

                }

            con.close();

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return adatok;
    }

}
