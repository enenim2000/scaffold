
package com.enenim.scaffold.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DateUtil {
    private static final String YYYY_MM_DD = "yyyy-MM-dd";
    private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    private static final String HH_MM_SS = "HH:mm:ss";
    
    public static String formatDate(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YYYY_MM_DD);
        return simpleDateFormat.format(date);
    }
    
    public static String formatDateTime(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        return simpleDateFormat.format(date);
    }
    
    public static String formatTime(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(HH_MM_SS);
        return simpleDateFormat.format(date);
    }
    
    public static Date formatDateTime(String date){
        SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        Date parsed = null;
        try {
            parsed = format.parse(date);
        } catch (ParseException ex) {
            Logger.getLogger(DateUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parsed;
    }
    
    public static Date parse(String date){
        try {
            DateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
            return formatter.parse(date);
        } catch (ParseException ex) {
            Logger.getLogger(DateUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
