/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nf.alarm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author ACER
 */
public class Validator {
    static Date rDate, pDate;
    public static Boolean validator(String dt, String tm, String nama) {  
    try{
        String pattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        String tanggal = dt + " " + tm;
        /* Check if date is 'null' */
        if (dt.trim().equals("")) {
            System.out.println(dt + " is Invalid Date format");
            return false;
        } /* Date is not 'null' */ else {
            /*
	     * Set preferred date format,
	     * For example MM-dd-yyyy, MM.dd.yyyy,dd.MM.yyyy etc.*/
            SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            sdfrmt.setLenient(false);
            /* Create Date object
	     * parse the string into date 
             */
            boolean flag = true;
            try {
                rDate = sdfrmt.parse(tanggal);
                pDate = sdfrmt.parse(date);
            } /* Date format is invalid */ catch (ParseException e) {
                flag = false;
                System.out.println(dt + " is Invalid Date format");
            }
            if (rDate.after(pDate)) {
                System.out.println("valid");
                return true;
                /* Return true if date format is valid */
            } else if (flag == false) {
                return false;
            }else if (nama == null){
                JOptionPane.showMessageDialog(null, "Enter");   
            }
            else {
                return false;
            }
        }

    }catch(Exception e){
        
        return false;
    }
        return null;
       
    } 
}
