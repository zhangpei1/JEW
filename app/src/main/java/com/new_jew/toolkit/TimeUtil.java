package com.new_jew.toolkit;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhangpei on 2016/8/30.
 */
public class TimeUtil {


    public static String getMothvalue(int add) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(new Date());
        Calendar cd = Calendar.getInstance();
        try {
            cd.setTime(sdf.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }

//                        cd.add(Calendar.DATE, 1);//增加一天
        //cal.add(Calendar.DATE, -1);      //减一天
        cd.add(Calendar.DATE, add);//增加一月
        Date date = cd.getTime();
        System.out.println(sdf.format(date));
        return sdf.format(date);
    }

    public static long gettimestamp(String time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(time);
        long timeStemp = (long) date.getTime();
        System.out.println(timeStemp);
        return timeStemp;
    }

    public static int getformatdata(String g) {
        String date = g;
        int t = 0;
//        date = date.replace("Z", " UTC");//注意是空格+UTC
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");//注意格式化的表达式
        try {
            Date d = format.parse(date);
//            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String str = format1.format(d);
//            Log.e("时间啊",str);
//            Log.e("时间啊",String.valueOf(d.getMonth()));
//            Log.e("时间啊",String.valueOf(d.getDay()));
            long time = System.currentTimeMillis() - d.getTime();
            t = (int) Math.ceil(time / (1000 * 60 * 60 * 24));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return t;
    }

    public static String getformatdata1(String a) {
        String date = a;
        int t = 0;
        String str = "";
        date = date.replace("Z", " UTC");//注意是空格+UTC
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");//注意格式化的表达式
        try {
            Date d = format.parse(date);
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            str = format1.format(d);
            Log.e("时间啊", str);
            Log.e("时间啊", String.valueOf(d.getMonth()));
            Log.e("时间啊", String.valueOf(d.getDay()));
//            long time=System.currentTimeMillis()- d.getTime();
//            t= (int) Math.ceil((time/1000)*60*60*24);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return str;
    }

    /*
 * 将时间转换为时间戳
 */
    public static long dateToStamp(String s) throws ParseException{
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return ts;
    }
    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
}
