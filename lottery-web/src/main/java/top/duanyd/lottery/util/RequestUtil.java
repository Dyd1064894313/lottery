package top.duanyd.lottery.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2018/8/25.
 */
public class RequestUtil {

    public static Integer getIntegerParam(HttpServletRequest request, String param, Integer def){
        String paramStr = request.getParameter(param);
        if(StringUtils.isBlank(paramStr)){
            return def;
        }
        try{
            return Integer.parseInt(paramStr);
        }
        catch (Exception e){

        }
        return def;
    }

    public static String getStringParam(HttpServletRequest request, String param, String def){
        String paramStr = request.getParameter(param);
        if(StringUtils.isBlank(paramStr)){
            return def;
        }
        return paramStr;
    }
}
