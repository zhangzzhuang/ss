package com.example.lenovo.myapplication.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lenovo on 2018/5/27.
 */

public class StreamTools {

    /**
     * 把输入流的内容转化成字符串
     * @param is
     * @return
     * @throws IOException
     */
    public static String readInputStream(InputStream is){
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            int len = 0;
            byte[] buffer = new byte[1024];
            while((len=is.read(buffer))!=-1){
                stream.write(buffer, 0, len);
            }
            is.close();
            stream.close();
            byte[] result = stream.toByteArray();
            //试着解析 result 里面的字符串
            String temp = new String(result);
            return temp;
        } catch (Exception e) {
            e.printStackTrace();
            return "获取失败";
        }

    }

    /**
     * 将date转换为String
     * @return
     */
    public static String DateToStr(Date date){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日" + "hh:mm:ss");
        String str = sdf.format(date);
        return str;
    }

    /**
     * 将String转换为Date
     * @param s
     * @return
     */
    public static Date StrToDate(String s){

        Date date = new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日 ");
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
             date = simpleDateFormat.parse(s);
        }catch (Exception e){
            e.printStackTrace();
        }

        return date;
    }



    //将字符串转化为日期用于判断时间差
    public static long getDatePoor(String Star,String End,Date starTime,Date endTime){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long days=0;
        try {
            starTime=df.parse(Star);
            endTime=df.parse(End);
            long diff=endTime.getTime()-starTime.getTime();
            days=diff/(1000*60*60*24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }
}
