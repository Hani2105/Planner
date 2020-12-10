package pl.nner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Copyright (C) <2016> <Krisztián Csekme>
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
public class EMAIL {

    private int process_count = 0;
    private String cc, from, to, sub, mess;
    private File file_subject;
    private File file_from;
    private File file_to;
    private File file_message;
    private File file_sender;
    private OutputStream os = null;
    private OutputStreamWriter out = null;

    private final Random gen = new Random();
    public final static byte[] UTF8_BOM = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};

    public void setFrom(String _from) {
        try {
            this.file_from = new File(System.getProperty("user.home") + "\\Pl@nner\\from.txt");
            os = new FileOutputStream(file_from);
            out = new OutputStreamWriter(os, "8859_2");
            out.append(_from);
            out.flush();
            out.close();
            os.close();
            file_from.setReadable(true);
            process_count++;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EMAIL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(EMAIL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EMAIL.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ignored) {
            }
        }
    }

    public void setTo(String _to) {
        try {
            this.file_to = new File(System.getProperty("user.home") + "\\Pl@nner\\to.txt");
            os = new FileOutputStream(file_to);
            out = new OutputStreamWriter(os, "8859_2");
            out.append(_to);
            out.flush();
            out.close();
            os.close();
            file_to.setReadable(true);
            process_count++;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EMAIL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(EMAIL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EMAIL.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ignored) {
            }
        }
    }

    public void setSubject(String _sub) {
        try {
            this.file_subject = new File(System.getProperty("user.home") + "\\Pl@nner\\subject.txt");
            os = new FileOutputStream(file_subject);
            out = new OutputStreamWriter(os, "8859_2");
            out.append(_sub);
            out.flush();
            out.close();
            os.close();
            file_subject.setReadable(true);
            process_count++;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EMAIL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(EMAIL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EMAIL.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ignored) {
            }
        }
    }

    public void setMessage(String _message) {
        try {
            this.file_message = new File(System.getProperty("user.home") + "\\Pl@nner\\message.txt");
            os = new FileOutputStream(file_message);
            out = new OutputStreamWriter(os, "8859_2");
            out.append(_message);
            out.flush();
            out.close();
            os.close();
            file_message.setReadable(true);
            process_count++;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EMAIL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(EMAIL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EMAIL.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ignored) {
            }
        }
    }

    public void add_CC(String value) {

        cc = cc + value + ";";

    }

    public void send() {

        try {

            while (process_count < 4);

            //this.file_sender = new File(System.getProperty("user.home") + "\\Pl@nner\\send.vbs");
            this.file_sender = new File("S:\\SiteData\\BUD1\\EMS\\planning\\[DEV_CENTER]\\PlannerFacelift\\send.vbs");
            String script = file_sender.getAbsolutePath();
            String executable = "wscript.exe";
            //String[] cmdArr = { executable, script, (new File(System.getProperty("user.dir"))).getAbsolutePath()};
            String[] cmdArr = {executable, script};
            //Process process =  Runtime.getRuntime().exec(cmdArr);
            Runtime.getRuntime().exec(cmdArr);
        } catch (IOException ex) {
            Logger.getLogger(EMAIL.class.getName()).log(Level.SEVERE, null, ex);
        }
        process_count = 0;
    }
}
