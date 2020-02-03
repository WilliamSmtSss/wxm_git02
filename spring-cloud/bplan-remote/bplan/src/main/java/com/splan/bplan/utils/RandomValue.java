package com.splan.bplan.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RandomValue {

    /**
     * 返回手机号码
     */
    private static String[] telFirst = "134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153,181,182,183,185,186,187,188,189,173,171,177".split(",");

    public static String getTel() {
        int index = getNum(0, telFirst.length - 1);
        String first = telFirst[index];
        //String second = String.valueOf(getNum(1, 888) + 10000).substring(1);
        String second = "****";
        String third = String.valueOf(getNum(1, 9100) + 10000).substring(1);
        return first + second + third;
    }

    public static int getNum(int start, int end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }

    public static int getOrderNum(int start, int end) {
        return (int) (Math.random() * (end - start + 1) + start) * 10;
    }

    /**
     * 获取随机日期
     *
     * @param beginDate 起始日期，格式为：yyyy-MM-dd
     * @param endDate   结束日期，格式为：yyyy-MM-dd
     * @return
     */
    private static Date randomDate(String beginDate, String endDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = format.parse(beginDate);  // 构造开始日期  
            Date end = format.parse(endDate);  // 构造结束日期  
            // getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
            if (start.getTime() >= end.getTime()) {
                return null;
            }

            long date = random(start.getTime(), end.getTime());

            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取随机日期
     *

     * @return
     */
    public static Date randomDate(Date nowDate) {
        try {

            long start = nowDate.getTime();

            long date = randomd(start, 1000*60*10);

            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static long randomd(long now, long exit) {
        long rtn = now - (long) (Math.random() * exit);
        // 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值
        if (rtn == now ) {
            return randomd(now, exit);
        }
        return rtn;
    }

    private static long random(long begin, long end) {
        long rtn = begin + (long) (Math.random() * (end - begin));
        // 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值
        if (rtn == begin || rtn == end) {
            return random(begin, end);
        }
        return rtn;
    }

    public static String masaike(String mobile){
        char[] m = mobile.toCharArray();
        for (int i = 0; i < m.length; i++) {
            if (i>2 && i<7){
                m[i] = '*';
            }

        }
        return String.valueOf(m);
    }


    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            System.out.println(getOrderNum(1, 2000));
        }

    }
}
