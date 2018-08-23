package top.duanyd.lottery.util;

/**
 * Created by Administrator on 2018/8/20.
 */
public class LongUtil {

    public static Long parseLong(String s, Long defaule){
        if(s == null){
            return defaule;
        }
        Long num = null;
        try {
            s = s.replace(",", "");
            num = Long.parseLong(s);
        }catch (Exception e){
            num = defaule;
        }
        return num;
    }

    public static Long parseLong(String s){
        return parseLong(s, 0L);
    }
}
