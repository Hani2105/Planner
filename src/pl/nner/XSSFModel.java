/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.nner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author krisztian_csekme
 */
public class XSSFModel {

    private DefaultTableModel model = null;

    public XSSFModel() {

    }

    public DefaultTableModel getExcelData(String filename, String sheetname) {
        DefaultTableModel model = null;
        FileInputStream FIS = null;
        try {
            FIS = new FileInputStream(filename);
            XSSFWorkbook workbook = new XSSFWorkbook(FIS);

            XSSFSheet sheet = workbook.getSheet(sheetname);

            
            int row_count = sheet.getLastRowNum()+1;
            int col_count = sheet.getRow(0).getLastCellNum();
            String[] oszlopok = new String[col_count];
            
            for (int i=0; i< oszlopok.length; i++){
                oszlopok[i] = sheet.getRow(0).getCell(i).getStringCellValue();
            }
            
            // A DefaultTable egy elemmel(sorral) kissebb az excel fejléce végett
            model = new DefaultTableModel(oszlopok,row_count - 1);

            for (int sor = 0; sor < row_count; sor++) {
                if (sor > 0) { //A második sortól számolunk a hasznos adatokkal
                    for (int oszlop = 0; oszlop < col_count; oszlop++) {
                        Object value=null;
                        if (sheet.getRow(sor).getCell(oszlop)!=null){
                            
                            switch (sheet.getRow(sor).getCell(oszlop).getCellType()){
                                
                                case XSSFCell.CELL_TYPE_STRING:
                                   value = sheet.getRow(sor).getCell(oszlop).getStringCellValue();
                                    break;
                                case XSSFCell.CELL_TYPE_NUMERIC:
                                     value = (int)sheet.getRow(sor).getCell(oszlop).getNumericCellValue();
                                    break;
                                case XSSFCell.CELL_TYPE_FORMULA:
                                   value = sheet.getRow(sor).getCell(oszlop).getStringCellValue();
                                    break;
                            }
                        }else{
                            
                        }
                        
                       
                        model.setValueAt(value, sor-1, oszlop);
                    }
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(XSSFModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XSSFModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                FIS.close();
            } catch (IOException ex) {
                Logger.getLogger(XSSFModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return model;
    }

}
