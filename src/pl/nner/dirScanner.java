/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.nner;

import java.io.File;

/**
 *
 * @author krisztian_csekme1
 */
class dirScanner{
    String file_text;
    
    public dirScanner(){
        
    }
     
    
    
    public void setRootDir(String dir){
       file_text="";
       listFile(dir);
    }
    
    String[] getFileNames(){
        if (file_text.length()>0){
            file_text.substring(0,file_text.length()-1);
        }
        return file_text.split(",");
    }
    
    private void listFile(String pathname) {
    File f = new File(pathname);
    File[] listfiles = f.listFiles();
    for (int i = 0; i < listfiles.length; i++) {
        if (listfiles[i].isDirectory()) {
            File[] internalFile = listfiles[i].listFiles();
            for (int j = 0; j < internalFile.length; j++) {
                
                if (internalFile[j].isDirectory()) {
                    String name = internalFile[j].getAbsolutePath();
                    listFile(name);
                }else{
                    file_text += internalFile[j]+",";
                }

            }
        } else {
            file_text += listfiles[i]+",";
        }

    }

}
}
