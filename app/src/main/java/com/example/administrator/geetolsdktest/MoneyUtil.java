package com.example.administrator.geetolsdktest;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Administrator on 2018\1\31 0031.
 * 自动添加金额的后面的.00
 */

public class MoneyUtil {
    /**
     * 获取金额的格式
     * @param s
     * @return
     */
    public static String getCharge(String s) {
        String rsult;
        if(!s.contains(".")){
            rsult = s +".00";
        }else {
            String[] lis = s.split("\\.");
            if (lis.length>1){
                switch (lis[1].length()){
                    case 0:
                        rsult = s+"00";
                        break;
                    case 1:
                        rsult = s+"0";
                        break;
                    default:
                        rsult = lis[0]+"."+lis[1].substring(0,2);
                        break;
                        
                }
            }else {
                rsult = s+"00";
            }
        }
        return rsult;
    }

    /**
     * 获取一个随机的长35位qq订单号
     * @param time
     * @return
     */
    public static String getQQLongDealNum(String time){
       SimpleDateFormat dateFormat = new SimpleDateFormat(TimeToStringUtils.TIME_TYPE_1, Locale.CHINA);
        Date date = new Date();
        try {
            date = dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateFormat = new SimpleDateFormat("yyMMdd",Locale.CHINA);
        String result = "1000046901"+dateFormat.format(date)+getRandomInt(19);
        return result;
    }
    /**
     * 获取一个随机的长35位qq订单号
     * @param time
     * @return
     */
    public static String getWxDeal28DealNum(String time){
        SimpleDateFormat dateFormat = new SimpleDateFormat(TimeToStringUtils.TIME_TYPE_1, Locale.CHINA);
        Date date = new Date();
        try {
            date = dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateFormat = new SimpleDateFormat("yyMMdd",Locale.CHINA);
        String result = "4200000066"+dateFormat.format(date)+getRandomInt(11);
        return result;
    }

    /**
     * 获取一个随机的qq订单号
     * @param time
     * @return
     */
    public static String getQQDealNum(String time){
        SimpleDateFormat dateFormat = new SimpleDateFormat(TimeToStringUtils.TIME_TYPE_1, Locale.CHINA);
        Date date = new Date();
        try {
            date = dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateFormat = new SimpleDateFormat("yyMMdd",Locale.CHINA);
        String result = "44301"+dateFormat.format(date)+getRandomInt(10);
        return result;
    }


    /**
     * 获取指定位数的随机排列数
     * @param num
     * @return
     */
    public static String getRandomInt(int num) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0;i<num;i++){
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public static  String getTimenumber(String time){
        SimpleDateFormat dateFormat = new SimpleDateFormat(TimeToStringUtils.TIME_TYPE_1, Locale.CHINA);
        Date date = new Date();
        try {
            date = dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateFormat = new SimpleDateFormat("yyyyMMdd",Locale.CHINA);
        return dateFormat.format(date);
    }
    public static String getZfBDealNumber(String stringExtra) {
        return getTimenumber(stringExtra)+"200040011100"+getRandomInt(12);
    }

    public static String getRandonNumber() {
        String[] starts = {"139","138","137","136","135","134","159",
                "158","157","150","151","152","188","130","131","132",
                "156","155","133","153","189","166"};
        Random random = new Random();
        String s = starts[random.nextInt(starts.length)]+ MoneyUtil.getRandomInt(8);
        return s;
    }

    public static String fmtMicrometer(String text) {
        BigDecimal a=new BigDecimal(text);
        //DecimalFormat df=new DecimalFormat(",###,##0"); //没有小数
        DecimalFormat df=new DecimalFormat(",###,##0.00"); //保留一位小数
        return df.format(a);
    }
}
