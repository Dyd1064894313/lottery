package top.duanyd.lottery;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import top.duanyd.lottery.schedule.DltSchedule;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        DltSchedule dltSchedule = (DltSchedule) ac.getBean("dltSchedule");
        dltSchedule.getAllLastData();
    }
}
