package com.dxy.common.time;
 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
/**
 * @author 丁向阳
 * @date 2015-7-23 17:49
 * @description 处理时间的工具类
 * @version 1.0
 */
public final class DateUtils {
    /**
     * 英文简写（默认）如：2010-12-01
     */
    public static String FORMAT_SHORT = "yyyy-MM-dd";
    /**
     * 英文全称 如：2010-12-01 23:15:06
     */
    public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
    /**
     * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.S
     */
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";
    /**
     * 中文简写 如：2010年12月01日
     */
    public static String FORMAT_SHORT_CN = "yyyy年MM月dd";
    /**
     * 中文全称 如：2010年12月01日 23时15分06秒
     */
    public static String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";
    /**
     * 精确到毫秒的完整中文时间
     */
    public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";
 
    /**
     * @author 丁向阳
     * @date 2015-7-23 23:20
     * @description 使用用户格式格式化日期
     * @eg DateUtils.format(new Date(), DateUtils.FORMAT_LONG)
     * @param date 日期
     * @param pattern 日期格式
     * @return
     */
    public static String format(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }
        return returnValue;
    }
 
 
    /**
     * @author 丁向阳
     * @date 2015-7-23 23:20
     * @description 使用用户格式提取字符串日期
     * @eg DateUtils.parse("2015-07-24", DateUtils.FORMAT_SHORT),DateUtils.parse("2015-07-24 12:12:12", DateUtils.FORMAT_LONG)
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return
     */
    public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
 
   
    /**
     * 
     * @author 丁向阳
     * @date 2015-7-23 23:20
     * @description 在日期上增加时间
     * @param date 日期
     * @param pattern 要增加时间类型(Calendar.YEAR,Calendar.MINUTE等)
     * @param n 要增加时间的大小
     * @return
     */
    public static Date addTime(Date date,int pattern, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(pattern, n);
        return cal.getTime();
    }
 
 
    /**
     * @author 丁向阳
     * @date 2015-7-23 18:00
     * @description 按用户格式字符串距离今天的天数
     * @eg DateUtils.countDays("2015-07-24", DateUtils.FORMAT_SHORT)
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return
     */
    public static int countDays(String strDate, String pattern) {
        long time = Calendar.getInstance().getTime().getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse(strDate, pattern));
        long time1 = cal.getTime().getTime();
        return (int) (time / 1000 - time1 / 1000) / 3600 / 24;
    }
    /**
     * @author 丁向阳
     * @date 2015-7-23 18:00
     * @description 获取指定时间是周几
     * @param date 指定时间的日期
     * @return week (1~6 周一到周六),(0 周日)
     */
    public static int getWeekOfDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return week;
    }
 
     
    /**
     * @author 丁向阳
     * @date 2015-7-23 18:20
     * @description 判断两个日期是否是同一周
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameWeekDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        if (0 == subYear) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
            // 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        }
        return false;
    }
    /**
     * @author 丁向阳
     * @date 2015-7-24 02:10
     * @description 获取两个时间之间的所有日期
     * @param beginDate 开始时间(格式为："2015-11-12")
     * @param endDate 结束时间
     * @return list 时间集合
     */
    public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {
        List<Date> list = new ArrayList<Date>();
        Calendar beginCal = Calendar.getInstance();
        beginCal.setTime(beginDate);
        beginCal.set(Calendar.HOUR_OF_DAY, 0);
        beginCal.set(Calendar.MINUTE, 0);
        beginCal.set(Calendar.SECOND, 0);
        beginCal.set(Calendar.MILLISECOND, 0);
         
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        endCal.set(Calendar.HOUR_OF_DAY, 0);
        endCal.set(Calendar.MINUTE, 0);
        endCal.set(Calendar.SECOND, 0);
        endCal.set(Calendar.MILLISECOND, 0);
         
        // 测试此日期是否在指定日期之后  
        while (endCal.getTime().after(beginCal.getTime())) {
            list.add(beginCal.getTime());
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量  
            beginCal.add(Calendar.DAY_OF_MONTH, 1);
        }
        //结束时间放入list
        list.add(endCal.getTime());
//      for(Date date : list){
//          System.out.println(DateUtils.format(date, DateUtils.FORMAT_LONG));
//      }
        return list;
    }
    public static void main(String[] args) {
        long date = Long.valueOf("1438272000000");
        System.out.println(DateUtils.format(new Date(date), DateUtils.FORMAT_LONG));
//      DateUtils.getDatesBetweenTwoDate(DateUtils.parse("2015-07-21 12:11:11", DateUtils.FORMAT_LONG), DateUtils.parse("2015-08-01 12:11:10", DateUtils.FORMAT_LONG));
    }
}