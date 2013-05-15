/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Notes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 *
 * @author pokus
 */
public class MyCalendar {
    private Calendar c ;
    private SimpleDateFormat sdf;
    
    public MyCalendar(){
        c = new GregorianCalendar(TimeZone.getTimeZone("Europe/Prague"));
        sdf = new SimpleDateFormat();
        //c.set(2012, 1, 1);
        c.setFirstDayOfWeek(Calendar.FRIDAY);
    }    
    
    public void date(){
        System.out.println(c.getTime());
        Date d = c.getTime();
        System.out.println("Date: " + sdf.format(d));
    }
    
    public String getDate(){
        sdf.applyPattern("dd.MM.yyyy".intern());
        return sdf.format(c.getTime()).intern();
    }
    
    public String getTime(){
        sdf.applyPattern("HH:mm");
        return sdf.format(c.getTime()).intern();
    }
    
    public int getFirstDayOfWeek(int month){
        return c.get(Calendar.DAY_OF_WEEK);
    }
    
    /**Return actual date*/
    public int getDay(){
        return Integer.valueOf(getDate().substring(0, getDate().indexOf(".")));
    }   
    
    public int getMonth(){
        return Integer.valueOf(getDate().substring(3, 5));
    }
    
    public int getYear(){
        return Integer.valueOf(getDate().substring(6, 10));
    }   
    
    private int getFirstDayOfDate(int year, int month){
        Calendar cc = (Calendar) c.clone();
        cc.set(year, month - 1, 1);
        int day = cc.get(Calendar.DAY_OF_WEEK);
        return day;
    }
    
    private int getCountDays(int year, int month){
        Calendar cc = (Calendar) c.clone();
        cc.set(year, month - 1, 1);
        int days = cc.getActualMaximum(Calendar.DAY_OF_MONTH);
        return days;
    }
    
    public Object[][] getMonth(int year, int month){
        int tmp = getFirstDayOfDate(year, month) - 2;
        int numberDays = getCountDays(year, month);
        Object[][] cal = new Object[6][7];
        
        if(tmp < 0){
            cal[0][6] = new Integer(1);
            tmp = 6;
        } else {
            cal[0][tmp] = new Integer(1);
        }
        int day = 2;
        tmp++;
        for (int i = 0; i < cal.length; i++) {
            for (int j = tmp; j < cal[i].length && day <= numberDays; j++) {
                cal[i][j] = new Integer(day++);
            }
            tmp = 0;
        }
        return cal;
    }
    
}
