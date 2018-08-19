package top.duanyd.lottery.schedule;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.duanyd.lottery.entity.DataURLEntity;
import top.duanyd.lottery.entity.DltEntity;
import top.duanyd.lottery.service.interfaces.IDataURLService;
import top.duanyd.lottery.service.interfaces.IDltService;
import top.duanyd.lottery.util.HttpClientUtil;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author: duanyandong
 * @Date: 2018/8/19 20:59
 * @Description:
 */
@Component
public class DltSchedule {
    private static Log logger = LogFactory.getLog(DltSchedule.class);
    private static String DLT_CODE = "dlt";
    private int DLT_BEGIN_YEAR = 2007;  // 大乐透开始年
    @Autowired
    IDataURLService dataURLService;
    @Autowired
    IDltService dltService;

    public void getAllLastData() throws IOException {
        List<DataURLEntity> allDataURL = dataURLService.getAllDataURL();
        DataURLEntity dltDataURLEntity = null;
        for(DataURLEntity dataURLEntity : allDataURL){
            if(DLT_CODE.equals(dataURLEntity.getLotteryCode()) && dataURLEntity.getStatus() == 1){
                dltDataURLEntity = dataURLEntity;
                break;
            }
        }
        if(dltDataURLEntity == null){
            return;
        }
        String dltUrl = dltDataURLEntity.getUrl();
        Calendar cal = Calendar.getInstance();
//        int nowYear = cal.get(Calendar.YEAR);
        int nowYear = 2007;
        int startYear = DLT_BEGIN_YEAR;
        while(startYear <= nowYear){
            int num = 1;
            int falseTimes = 0; // 失败次数
            while(true){
                String numStr = String.format("%d%03d", startYear, num);
                num++;
                String currUrl = dltUrl.replace("{$no$}", numStr);
                String result = HttpClientUtil.postNoPara(currUrl);
                String content = getContent(result);
                if(content == null) {
                    if (falseTimes == 3) {
                        break;
                    }
                    falseTimes++;
                    continue;
                }

                logger.info("期号：" + numStr + "，结果：" + content);
            }
            startYear++;
        }
    }

    public String getContent(String raw){
        if(StringUtils.isBlank(raw)){
            return null;
        }
        int begin = raw.indexOf("\"");
        int end = raw.lastIndexOf("\"");
        if(begin>=end -1){
            return null;
        }
        return raw.substring(begin + 1, end);
    }

    public DltEntity transToEntity(String content){
        String[] element = content.split("\\*");
        DltEntity dltEntity = new DltEntity();
        dltEntity.setLotteryNo(element[0]);
        return null;
    }
}
