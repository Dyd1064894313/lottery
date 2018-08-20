package top.duanyd.lottery.util;

/**
 * Created by Administrator on 2018/8/20.
 */
public class LongUtil {

    public static Long parseLong(String s){
        if(s == null){
            return null;
        }
        s = s.replace(",", "");
        return Long.parseLong(s);
    }
}
