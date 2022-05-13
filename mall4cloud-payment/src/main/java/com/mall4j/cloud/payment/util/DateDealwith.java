package com.mall4j.cloud.payment.util;

import com.alibaba.cloud.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class DateDealwith {
    /**
     * 获取系统当前时间
     *
     * @return
     */
    public static Date getCurrDate() {
        // 获取系统当前时间
        SimpleDateFormat std = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 将字符串转换为Date格式
        Date date = null;
        try {
            date = std.parse(std.format(new Date()).toString());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 给定格式和参数格式要统一不然就会抛出异常
        return date;
    }

    public static String getCurrDateStr() {
        // 获取系统当前时间
        SimpleDateFormat std = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 将字符串转换为Date格式
        return std.format(new Date()).toString();
        // 给定格式和参数格式要统一不然就会抛出异常
    }

    /**
     * 获取系统当前年份
     *
     * @return
     */
    public static String getCurrYear() {
        // 获取系统当前时间
        SimpleDateFormat std = new SimpleDateFormat("yyyy");
        // 将字符串转换为Date格式
        System.out.println(std.format(new Date()).toString());
        return std.format(new Date()).toString();
    }

    /**
     * 获取两时间差
     *
     * @throws ParseException
     */
    public static long getshijiancha(Date da) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = df.parse(df.format(new Date()).toString());
        Date date = df.parse(df.format(da).toString());
        long l = now.getTime() - date.getTime();
        return l;
    }
    //生成唯一文件名
    public static String getSHC() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        UUID uuid = UUID.randomUUID();
        String str = sdf.format(date);
        return uuid.toString()+str;
    }

    public static String dateToString(Date date){
        String result = "";
        if (date !=null){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            result = format.format(date);
        }

        return result;
    }

    public static String dateToString1(Date date){
        String result = "";
        if (date !=null){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            result = format.format(date);
        }

        return result;
    }

    public static Date strToDate(String strDate) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            ParsePosition pos = new ParsePosition(0);
            Date strtodate = formatter.parse(strDate, pos);
            return strtodate;
         }
    public static String getIpAddress(HttpServletRequest request) {
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = XFor.indexOf(",");
            if(index != -1){
                return XFor.substring(0,index);
            }else{
                return XFor;
            }
        }
        XFor = Xip;
        if(StringUtils.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)){
            return XFor;
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getRemoteAddr();
        }
        return XFor;
    }


}
