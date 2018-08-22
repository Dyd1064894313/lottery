package top.duanyd.lottery.schedule;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.duanyd.lottery.entity.DataURLEntity;
import top.duanyd.lottery.entity.DltEntity;
import top.duanyd.lottery.entity.SsqEntity;
import top.duanyd.lottery.service.interfaces.IDataURLService;
import top.duanyd.lottery.service.interfaces.IDltService;
import top.duanyd.lottery.service.interfaces.ISsqService;
import top.duanyd.lottery.util.DateUtil;
import top.duanyd.lottery.util.HttpClientUtil;
import top.duanyd.lottery.util.LongUtil;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @Author: duanyandong
 * @Date: 2018/8/22 21:35
 * @Description:
 */
@Component
public class SsqSchedule implements Job {
    private static Log logger = LogFactory.getLog(SsqSchedule.class);
    private static String SSQ_CODE = "ssq";
    private int DLT_BEGIN_YEAR = 2003;  // 大乐透开始年
    @Autowired
    IDataURLService dataURLService;
    @Autowired
    ISsqService ssqService;

    public void getAllData() throws IOException {
        List<DataURLEntity> allDataURL = dataURLService.getAllDataURL();
        DataURLEntity ssqDataURLEntity = null;
        for(DataURLEntity dataURLEntity : allDataURL){
            if(SSQ_CODE.equals(dataURLEntity.getLotteryCode()) && dataURLEntity.getStatus() == 1){
                ssqDataURLEntity = dataURLEntity;
                break;
            }
        }
        if(ssqDataURLEntity == null){
            return;
        }
        String ssqUrl = ssqDataURLEntity.getUrl();

        Calendar cal = Calendar.getInstance();
        int nowYear = cal.get(Calendar.YEAR);
        int startYear = DLT_BEGIN_YEAR;
        boolean newBegin = true;
        int beginNum = 1;
        SsqEntity lastSsqEntity = ssqService.getLastSsqEntity();
        if(lastSsqEntity != null){
            String number = lastSsqEntity.getLotteryNo();
            if(StringUtils.isNotBlank(number) && number.length() == 7){
                startYear = Integer.parseInt(number.substring(0, 4));
                beginNum = Integer.parseInt(number.substring(4)) + 1;
                newBegin = false;
            }
        }

        List<SsqEntity> ssqEntityList = null;
        while(startYear <= nowYear){
            int num = 1;
            if(!newBegin) {
                num = beginNum;
                newBegin = true;
            }
            int falseTimes = 0; // 失败次数,如果失败次数达到3次表示本年已结束
            ssqEntityList = new ArrayList<>();
            while(true){
                String numStr = String.format("%d%03d", startYear, num);
                num++;
                String currUrl = ssqUrl.replace("{$no$}", numStr);
                String result = HttpClientUtil.postNoPara(currUrl);
                String content = getContent(result);
                if(content == null) {
                    if (falseTimes == 3) {
                        break;
                    }
                    falseTimes++;
                    continue;
                }
                SsqEntity ssqEntity = transToEntity(content);
                ssqEntityList.add(ssqEntity);
                logger.info(JSONObject.toJSONString(ssqEntity));
                logger.info("期号：" + numStr + "，结果：" + content);
            }
            if(!ssqEntityList.isEmpty()) {//数据1年保存一次
                ssqService.batchInsert(ssqEntityList);
            }
            startYear++;
        }

    }

    /**
     * 重原内容中获取所需字符串
     * @param raw
     * @return
     */
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

    /**
     * 将字符串转为实体类
     * @param content
     * @return
     */
    public SsqEntity transToEntity(String content){
        //如果字符串后面全部都是分隔符字符，则在分割时会把后面全部是分隔符的子串去掉，如1*2***，按*分隔只能得到["1","2"]，
        // 如果在字符串后面多加一个非分隔符就能全部分隔
        if("*".equals(content.substring(content.length() - 1))){
            content = content + " ";
        }
        String[] element = content.split("\\*");
        if(element.length < 19){//如果分割后数组元素少于32个，则new一个32位新数组，将原数组复制到新数组中，防止下面报下标越界
            String temp[] = new String[32];
            for (int i = 0; i < temp.length; i++) {
                temp[i] = "";
            }
            System.arraycopy(element, 0 ,temp, 0 , element.length);
            element = temp;
        }
        SsqEntity ssqEntity = new SsqEntity();
        ssqEntity.setLotteryNo(element[0]);
        ssqEntity.setLotteryDate(DateUtil.getDate(DateUtil.YYYY_MM_DD, element[1]));
        ssqEntity.setLotterySaleAmount(element[2]);
        ssqEntity.setLotteryPoolAmount(element[16]);
        ssqEntity.setRedOne(element[3]);
        ssqEntity.setRedTwo(element[4]);
        ssqEntity.setRedThree(element[5]);
        ssqEntity.setRedFour(element[6]);
        ssqEntity.setRedFive(element[7]);
        ssqEntity.setRedSix(element[8]);
        ssqEntity.setBlueOne(element[9]);
        if(StringUtils.isNoneBlank(element[10])){
            ssqEntity.setPrizeOneNum(LongUtil.parseLong(element[10]));
        }else{
            ssqEntity.setPrizeOneNum(0L);
        }
        if(StringUtils.isNoneBlank(element[17])){
            ssqEntity.setPrizeOneEveryMoney(LongUtil.parseLong(element[17]));
        }else{
            ssqEntity.setPrizeOneEveryMoney(0L);
        }
        if(StringUtils.isNoneBlank(element[11])){
            ssqEntity.setPrizeTwoNum(LongUtil.parseLong(element[11]));
        }else{
            ssqEntity.setPrizeTwoNum(0L);
        }
        if(StringUtils.isNoneBlank(element[18])){
            ssqEntity.setPrizeTwoEveryMoney(LongUtil.parseLong(element[18]));
        }else{
            ssqEntity.setPrizeTwoEveryMoney(0L);
        }
        if(StringUtils.isNoneBlank(element[12])){
            ssqEntity.setPrizeThreeNum(LongUtil.parseLong(element[12]));
        }else{
            ssqEntity.setPrizeThreeNum(0L);
        }
        ssqEntity.setPrizeThreeEveryMoney(0L);
        if(StringUtils.isNoneBlank(element[13])){
            ssqEntity.setPrizeFourNum(LongUtil.parseLong(element[13]));
        }else{
            ssqEntity.setPrizeFourNum(0L);
        }
        ssqEntity.setPrizeFourEveryMoney(0L);
        if(StringUtils.isNoneBlank(element[14])){
            ssqEntity.setPrizeFiveNum(LongUtil.parseLong(element[14]));
        }else{
            ssqEntity.setPrizeFiveNum(0L);
        }
        ssqEntity.setPrizeFiveEveryMoney(0L);
        if(StringUtils.isNoneBlank(element[15])){
            ssqEntity.setPrizeSixNum(LongUtil.parseLong(element[15]));
        }else{
            ssqEntity.setPrizeSixNum(0L);
        }
        ssqEntity.setPrizeSixEveryMoney(0L);

        Timestamp currTimestamp = DateUtil.getCurrTimestamp();
        ssqEntity.setCreateTime(currTimestamp);
        ssqEntity.setUpdateTime(currTimestamp);
        ssqEntity.setStatus(1);
        ssqEntity.setRemark(content);
        return ssqEntity;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            getAllData();
        } catch (IOException e) {
            logger.error("定时任务获取大乐透数据异常", e);
        }
    }
}
