package com.dingxy.common.math;
 
import java.math.BigDecimal;
 
/**
 * @author 丁向阳
 * @date 2015-7-23 17:49
 * @description 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精确的浮点数运算，包括加减乘除和四舍五入。(BigDecimal(dobule)会出现浮点，构造时参数使用String类型)
 * @version 1.0
 */
public final class BigDecimalUtils {
    /**
     * @author 丁向阳
     * @date 2015-7-23 23:20
     * @description 提供精确的加法运算
     * @param d1 加数
     * @param d2 被加数
     * @return 两个参数的和
     */
    public static double add(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.add(b2).doubleValue();
    }
 
    /**
     * @author 丁向阳
     * @date 2015-7-23 23:20
     * @description 提供精确的减法运算 
     * @param d1 减数
     * @param d2 被减数
     * @return 两个参数的差
     */
    public static double sub(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.subtract(b2).doubleValue();
    }
 
    /**
     * @author 丁向阳
     * @date 2015-7-23 23:20
     * @description 提供精确的乘法运算
     * @param d1 乘数
     * @param d2 被乘数
     * @return 两个参数的积
     */
    public static double mul(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.multiply(b2).doubleValue();
    }
 
    /**
     * @author 丁向阳
     * @date 2015-7-23 23:20
     * @description 提供（相对）精确的除法运算,保留到小数点以后几位(四舍五入)
     * @param d1 除 数
     * @param d2  被除数
     * @param len 表示需要精确到小数点以后几位
     * @return 两个参数的商
     */
    public static double div(double d1, double d2, int len) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
     
    /**
     * @author 丁向阳
     * @date 2015-7-23 23:20
     * @description 数字保留小时(四舍五入)
     * @param d1 要格式化的字符串类型的数字
     * @param len 表示需要精确到小数点以后几位
     * @return
     */
    public static double format(double d1, int len){
        BigDecimal b = new BigDecimal(Double.toString(d1));  
        return  b.setScale(len,BigDecimal.ROUND_HALF_UP).doubleValue();  
    }
}