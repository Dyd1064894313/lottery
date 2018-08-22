package top.duanyd.lottery;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import top.duanyd.lottery.schedule.DltSchedule;
import top.duanyd.lottery.schedule.SsqSchedule;

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
        dltSchedule.getAllData();
        /*SsqSchedule ssqSchedule = (SsqSchedule) ac.getBean("ssqSchedule");
        ssqSchedule.getAllData();*/
    }
}
