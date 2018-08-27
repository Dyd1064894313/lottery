package top.duanyd.lottery.core.exception;

/**
 * @Author: duanyandong
 * @Date: 2018/8/13 10:27
 * @Description:
 */
public class MyJdbcTempletAnnotationException extends RuntimeException {

    public MyJdbcTempletAnnotationException(){
        super();
    }

    public MyJdbcTempletAnnotationException(String message){
        super(message);
    }

    public MyJdbcTempletAnnotationException(Throwable cause){
        super(cause);
    }

    public MyJdbcTempletAnnotationException(String message, Throwable cause){
        super(message, cause);
    }
}
