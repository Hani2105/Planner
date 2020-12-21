/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.nner;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.joda.time.format.DateTimeFormat;

/**
 *
 * @author gabor_hanacsek
 */
public class ErrorLogger extends Thread {

    String message;

    @Override
    public void run() {
        //szedjuk ki a terveket

        Plan pl = PlNner.PLANS.get(MainForm.TOP.getSelectedIndex());
        String pattern = "yyyy-MM-dd HH:mm:ss";
        org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
        
        message = "<html><body><table border=\"2\"><thead><tr><th>PartNumber</th><th>Job</th><th>Qty</th><th>QtyTeny</th><th>StartTime</th><th>Komment</th><th>Mérnöki</th></tr></thead><tbody>";
        for (int i = 0; i < pl.STATIONS.size(); i++) {
            for (int n = 0; n < pl.STATIONS.get(i).PRODUCTS.size(); n++) {
                Product p = pl.STATIONS.get(i).PRODUCTS.get(n);
                String starttime = formatter.print(p.getStartTime());
                message += "<tr><td>" + p.getPartnumber() + "</td><td>" + p.getJobnumber() + "</td><td>" + p.getQty() + "</td><td>" + p.getSumTeny() + "</td><td>" + starttime + "</td><td>" + p.getComment() + "</td><td>"+p.isEngeenering()+"</td></tr>";

            }

        }

        message += "</tbody></table></body></html>";
        EMAIL mail = new EMAIL();
        mail.setFrom("Planner@sanmina.com");

        mail.setTo("gabor.hanacsek@sanmina.com");
        mail.setSubject("Terv mentés");
        mail.setMessage(message);
        mail.send();

       
    }

}
