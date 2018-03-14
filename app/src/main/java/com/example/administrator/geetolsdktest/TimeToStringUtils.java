package com.example.administrator.geetolsdktest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间：2018年1月16日12:44:01
 * 作者：zeoy
 * 描述：时间转换工具
 */
public class TimeToStringUtils {
    public static final String TIME_TYPE_1 = "yyyy-MM-dd HH:mm";
    public static final String TIME_TYPE_2 = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_TYPE_3 = "MM月dd日 HH:mm";

    public static final int HOUR = 3;
    public static final int MINUTE = 2;
    public static final int SECOND = 1;

    public static final int  HOUR_24 =  4;
    public  static  final  int HOUR_12 = -1;

    /**
     * 验证字符串是否是一个合法的日期格式
     */
    public static boolean isValidDate(String date, String template) {
        boolean convertSuccess = true;
        // 指定日期格式
        SimpleDateFormat format = new SimpleDateFormat(template, Locale.CHINA);
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2015/02/29会被接受，并转换成2015/03/01
            format.setLenient(false);
            format.parse(date);
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 输入时间字符串，获取他的时间值
     * @param data  “12:23:15”
     * @param timetype  要获取的类型  时分秒
     * @return
     */
    public static int getTimeInt(String data,int timetype){
        Calendar calendar = Calendar.getInstance();
        int i = -1;
        if (isValidDate(data,"HH:mm:ss")){
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss",Locale.CHINA);
            try {
                calendar.setTime(sdf.parse(data));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        switch (timetype){
            case HOUR:
                i =  calendar.get(Calendar.HOUR_OF_DAY);
                break;
            case MINUTE:
                i = calendar.get((Calendar.MINUTE));
                break;
            case SECOND:
                i = calendar.get(Calendar.SECOND);
                break;
            default:
                    break;
        }
        return i;
    }

    /**
     * 获取当前的时间
     * @param dataformat
     * @return
     */
    public static String getCurentTime(String dataformat){
        SimpleDateFormat sdf = new SimpleDateFormat(dataformat,Locale.CHINA);
        String time = sdf.format(new Date(System.currentTimeMillis()));
        return time;
    }

    /**
     * 获取微信的聊天格式时间字符串
     * @param calendar
     * @param part  表示微信的聊天时段 如：早晨  中午
     * @param hourType  时间制式
     * @return
     */
    public static String getWxChatTimeString(Calendar calendar,String part,int hourType){
        String timeformat = "yyyy年MM月dd日 "+part+"HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(timeformat,Locale.CHINA);
        Calendar curenttime = Calendar.getInstance();
        Date date = calendar.getTime();
        if (calendar.get(Calendar.YEAR)<curenttime.get(Calendar.YEAR)){
            return sdf.format(date);
        }else if (calendar.get(Calendar.MONTH)<curenttime.get(Calendar.MONTH)){
            timeformat = "MM月dd日 "+part+"HH:mm";
            sdf = new SimpleDateFormat(timeformat,Locale.CHINA);
            return sdf.format(date);
        }else if (curenttime.get(Calendar.DAY_OF_MONTH)-calendar.get(Calendar.DAY_OF_MONTH)>6){
            timeformat = "MM月dd日 "+part+"HH:mm";
            sdf = new SimpleDateFormat(timeformat,Locale.CHINA);
            return sdf.format(date);
        }else if (curenttime.get(Calendar.DAY_OF_MONTH)-calendar.get(Calendar.DAY_OF_MONTH)>1){
            int dayweek = calendar.get(Calendar.DAY_OF_WEEK);
            String week = "";
            switch (dayweek){
                case Calendar.MONDAY:
                    week = "周一";
                    break;
                case Calendar.TUESDAY:
                    week = "周二";
                    break;
                case Calendar.WEDNESDAY:
                    week = "周三";
                    break;
                case Calendar.THURSDAY:
                    week = "周四";
                    break;
                case Calendar.FRIDAY:
                    week = "周五";
                    break;
                case Calendar.SATURDAY:
                    week = "周六";
                    break;
                case Calendar.SUNDAY:
                    week = "周日";
                    break;
            }
            switch (hourType){
                case HOUR_12:
                    timeformat = week+" "+part+"HH:mm";
                    break;
                case HOUR_24:
                    timeformat = week+"HH:mm";
                    break;
            }
            sdf = new SimpleDateFormat(timeformat,Locale.CHINA);
            return sdf.format(date);
        }else if (curenttime.get(Calendar.DAY_OF_MONTH)-calendar.get(Calendar.DAY_OF_MONTH)==1){
            switch (hourType){
                case HOUR_12:
                    timeformat = "昨天 "+part+"HH:mm";
                    break;
                case HOUR_24:
                    timeformat = "昨天 "+"HH:mm";
                    break;
            }
            sdf = new SimpleDateFormat(timeformat,Locale.CHINA);
            return sdf.format(date);
        }else {
            switch (hourType){
                case HOUR_12:
                    timeformat = part+"HH:mm";
                    break;
                case HOUR_24:
                    timeformat = "HH:mm";
                    break;
            }
            sdf = new SimpleDateFormat(timeformat,Locale.CHINA);
            return sdf.format(date);
        }

    }
    /**
     * 获取微信的首页聊天格式时间字符串
     * @param calendar
     * @return
     */

    public static String getWxMainPageTimeString(Calendar calendar){
        String timeformat = "yyyy年MM月dd日";
        SimpleDateFormat sdf = new SimpleDateFormat(timeformat,Locale.CHINA);
        Calendar curenttime = Calendar.getInstance();
        Date date = calendar.getTime();
        if (calendar.get(Calendar.YEAR)<curenttime.get(Calendar.YEAR)){
            return sdf.format(date);
        }else if (calendar.get(Calendar.MONTH)<curenttime.get(Calendar.MONTH)){
            timeformat = "MM月dd日";
            sdf = new SimpleDateFormat(timeformat,Locale.CHINA);
            return sdf.format(date);
        }else if (curenttime.get(Calendar.DAY_OF_MONTH)-calendar.get(Calendar.DAY_OF_MONTH)>6){
            timeformat = "MM月dd日";
            sdf = new SimpleDateFormat(timeformat,Locale.CHINA);
            return sdf.format(date);
        }else if (curenttime.get(Calendar.DAY_OF_MONTH)-calendar.get(Calendar.DAY_OF_MONTH)>1){
            int dayweek = calendar.get(Calendar.DAY_OF_WEEK);
            String week = "";
            switch (dayweek){
                case Calendar.MONDAY:
                    week = "周一";
                    break;
                case Calendar.TUESDAY:
                    week = "周二";
                    break;
                case Calendar.WEDNESDAY:
                    week = "周三";
                    break;
                case Calendar.THURSDAY:
                    week = "周四";
                    break;
                case Calendar.FRIDAY:
                    week = "周五";
                    break;
                case Calendar.SATURDAY:
                    week = "周六";
                    break;
                case Calendar.SUNDAY:
                    week = "周日";
                    break;
            }
            switch (4){
                case HOUR_12:
                    timeformat = week;
                    break;
                case HOUR_24:
                    timeformat = week;
                    break;
            }
            sdf = new SimpleDateFormat(timeformat,Locale.CHINA);
            return sdf.format(date);
        }else if (curenttime.get(Calendar.DAY_OF_MONTH)-calendar.get(Calendar.DAY_OF_MONTH)==1){
            switch (4){
                case HOUR_12:
                    timeformat = "昨天";
                    break;
                case HOUR_24:
                    timeformat = "昨天";
                    break;
            }
            sdf = new SimpleDateFormat(timeformat,Locale.CHINA);
            return sdf.format(date);
        }else {
            switch (4){
                case HOUR_12:
                    timeformat = "HH:mm";
                    break;
                case HOUR_24:
                    timeformat = "HH:mm";
                    break;
            }
            sdf = new SimpleDateFormat(timeformat,Locale.CHINA);
            return sdf.format(date);
        }

    }

    /**
     * 获取指定格式的时间
     * @param calendar
     * @param timeformat
     * @return
     */
    public static String geFormatTime(Calendar calendar,String timeformat){
        SimpleDateFormat sdf = new SimpleDateFormat(timeformat,Locale.CHINA);
        Date date = calendar.getTime();
        return sdf.format(date);
    }

    /**
     * 将毫秒转化为00:00格式
     *
     *
     * @param p
     * @return
     */
    public static String getTime(int p) {
        StringBuilder builder = new StringBuilder();
        int i = p / 60;
        if (i < 10)
            builder.append("0");
        String min = String.valueOf(i);
        builder.append(min);
        builder.append(":");
        i = p % 60;
        if (i < 10)
            builder.append("0");
        min = String.valueOf(i);
        builder.append(min);
        return builder.toString();
    }
}
