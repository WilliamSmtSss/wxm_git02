package com.splan.bplan.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static Date stringToDate(String dateString){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static ArrayList<String> getFeatureDateList(int intervals ) {
        ArrayList<String> featureDaysList = new ArrayList<>();
        for (int i = 0; i <intervals; i++) {
            featureDaysList.add(getFeatureDate(i));
        }
        return featureDaysList;
    }

    /**
     * 获取未来 第 past 天的日期
     * @param past
     * @return
     */
    public static String getFeatureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        //Log.e(null, result);
        return result;
    }
     public static String getMytime(Date date){
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
     }

     //获取两个时间的日期差
     public static int differentDays(Date date1,Date date2)
     {
         Calendar cal1 = Calendar.getInstance();
         cal1.setTime(date1);

         Calendar cal2 = Calendar.getInstance();
         cal2.setTime(date2);
         int day1= cal1.get(Calendar.DAY_OF_YEAR);
         int day2 = cal2.get(Calendar.DAY_OF_YEAR);

         int year1 = cal1.get(Calendar.YEAR);
         int year2 = cal2.get(Calendar.YEAR);
         if(year1 != year2)   //同一年
         {
             int timeDistance = 0 ;
             for(int i = year1 ; i < year2 ; i ++)
             {
                 if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                 {
                     timeDistance += 366;
                 }
                 else    //不是闰年
                 {
                     timeDistance += 365;
                 }
             }

             return timeDistance + (day2-day1) ;
         }
         else    //不同年
         {
//             System.out.println("判断day2 - day1 : " + (day2-day1));
             return day2-day1;
         }
     }
}
