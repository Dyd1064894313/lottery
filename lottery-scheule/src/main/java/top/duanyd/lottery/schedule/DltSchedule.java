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
import top.duanyd.lottery.service.interfaces.IDataURLService;
import top.duanyd.lottery.service.interfaces.IDltService;
import top.duanyd.lottery.util.DateUtil;
import top.duanyd.lottery.util.HttpClientUtil;
import top.duanyd.lottery.util.LongUtil;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author: duanyandong
 * @Date: 2018/8/19 20:59
 * @Description:
 */
@Component
public class DltSchedule implements Job {
    private static Log logger = LogFactory.getLog(DltSchedule.class);
    private static String DLT_CODE = "dlt";
    private int DLT_BEGIN_YEAR = 2007;  // 大乐透开始年
    @Autowired
    IDataURLService dataURLService;
    @Autowired
    IDltService dltService;

    public void getAllData() throws IOException {
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
        int nowYear = cal.get(Calendar.YEAR);
        int startYear = DLT_BEGIN_YEAR;
        boolean newBegin = true;
        int beginNum = 1;
        DltEntity lastDltEntity = dltService.getLastDltEntity();
        if(lastDltEntity != null){
            String number = lastDltEntity.getLotteryNo();
            if(StringUtils.isNotBlank(number) && number.length() == 7){
                startYear = Integer.parseInt(number.substring(0, 4));
                beginNum = Integer.parseInt(number.substring(4));
                newBegin = false;
            }
        }

        List<DltEntity> dltEntityList = null;
        while(startYear <= nowYear){
            int num = 1;
            if(!newBegin) {
                num = beginNum;
                newBegin = true;
            }
            int falseTimes = 0; // 失败次数,如果失败次数达到3次表示本年已结束
            dltEntityList = new ArrayList<>();
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
                DltEntity dltEntity = transToEntity(content);
                dltEntityList.add(dltEntity);
                logger.info(JSONObject.toJSONString(dltEntity));
                logger.info("期号：" + numStr + "，结果：" + content);
            }
            if(!dltEntityList.isEmpty()) {//数据1年保存一次
                dltService.batchInsert(dltEntityList);
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
    public DltEntity transToEntity(String content){
        //如果字符串后面全部都是分隔符字符，则在分割时会把后面全部是分隔符的子串去掉，如1*2***，按*分隔只能得到["1","2"]，
        // 如果在字符串后面多加一个非分隔符就能全部分隔
        if("*".equals(content.substring(content.length() - 1))){
            content = content + " ";
        }
        String[] element = content.split("\\*");
        if(element.length < 32){//如果分割后数组元素少于32个，则new一个32位新数组，将原数组复制到新数组中，防止下面报下标越界
            String temp[] = new String[32];
            for (int i = 0; i < temp.length; i++) {
                temp[i] = "";
            }
            System.arraycopy(element, 0 ,temp, 0 , element.length);
            element = temp;
        }
        DltEntity dltEntity = new DltEntity();
        dltEntity.setLotteryNo(element[0]);
        dltEntity.setLotteryDate(DateUtil.getDate(DateUtil.YYYY_MM_DD, element[1]));
        dltEntity.setLotterySaleAmount(element[2]);
        dltEntity.setLotteryPoolAmount(element[3]);
        dltEntity.setRedOne(element[4]);
        dltEntity.setRedTwo(element[5]);
        dltEntity.setRedThree(element[6]);
        dltEntity.setRedFour(element[7]);
        dltEntity.setRedFive(element[8]);
        dltEntity.setBlueOne(element[9]);
        dltEntity.setBlueTwo(element[10]);
        if(StringUtils.isNoneBlank(element[11])){
            dltEntity.setPrizeOneNum(LongUtil.parseLong(element[11]));
        }else{
            dltEntity.setPrizeOneNum(0L);
        }
        if(StringUtils.isNoneBlank(element[12])){
            dltEntity.setPrizeOneEveryMoney(LongUtil.parseLong(element[12]));
        }else{
            dltEntity.setPrizeOneEveryMoney(0L);
        }
        if(StringUtils.isNoneBlank(element[13])){
            dltEntity.setPrizeOneAdditionalNum(LongUtil.parseLong(element[13]));
        }else{
            dltEntity.setPrizeOneAdditionalNum(0L);
        }
        if(StringUtils.isNoneBlank(element[14])){
            dltEntity.setPrizeOneAdditionalEveryMoney(LongUtil.parseLong(element[14]));
        }else{
            dltEntity.setPrizeOneAdditionalEveryMoney(0L);
        }
        if(StringUtils.isNoneBlank(element[15])){
            dltEntity.setPrizeTwoNum(LongUtil.parseLong(element[15]));
        }else{
            dltEntity.setPrizeTwoNum(0L);
        }
        if(StringUtils.isNoneBlank(element[16])){
            dltEntity.setPrizeTwoEveryMoney(LongUtil.parseLong(element[16]));
        }else{
            dltEntity.setPrizeTwoEveryMoney(0L);
        }
        if(StringUtils.isNoneBlank(element[17])){
            dltEntity.setPrizeTwoAdditionalNum(LongUtil.parseLong(element[17]));
        }else{
            dltEntity.setPrizeTwoAdditionalNum(0L);
        }
        if(StringUtils.isNoneBlank(element[18])){
            dltEntity.setPrizeTwoAdditionalEveryMoney(LongUtil.parseLong(element[18]));
        }else{
            dltEntity.setPrizeTwoAdditionalEveryMoney(0L);
        }
        if(StringUtils.isNoneBlank(element[19])){
            dltEntity.setPrizeThreeNum(LongUtil.parseLong(element[19]));
        }else{
            dltEntity.setPrizeThreeNum(0L);
        }
        if(StringUtils.isNoneBlank(element[20])){
            dltEntity.setPrizeThreeEveryMoney(LongUtil.parseLong(element[20]));
        }else{
            dltEntity.setPrizeThreeEveryMoney(0L);
        }
        if(StringUtils.isNoneBlank(element[21])){
            dltEntity.setPrizeThreeAdditionalNum(LongUtil.parseLong(element[21]));
        }else{
            dltEntity.setPrizeThreeAdditionalNum(0L);
        }
        if(StringUtils.isNoneBlank(element[22])){
            dltEntity.setPrizeThreeAdditionalEveryMoney(LongUtil.parseLong(element[22]));
        }else{
            dltEntity.setPrizeThreeAdditionalEveryMoney(0L);
        }
        if(StringUtils.isNoneBlank(element[23])){
            dltEntity.setPrizeFourNum(LongUtil.parseLong(element[23]));
        }else{
            dltEntity.setPrizeFourNum(0L);
        }
        dltEntity.setPrizeFourEveryMoney(0L);
        if(StringUtils.isNoneBlank(element[24])){
            dltEntity.setPrizeFourAdditionalNum(LongUtil.parseLong(element[24]));
        }else{
            dltEntity.setPrizeFourAdditionalNum(0L);
        }
        dltEntity.setPrizeFourAdditionalEveryMoney(0L);
        if(StringUtils.isNoneBlank(element[25])){
            dltEntity.setPrizeFiveNum(LongUtil.parseLong(element[25]));
        }else{
            dltEntity.setPrizeFiveNum(0L);
        }
        dltEntity.setPrizeFiveEveryMoney(0L);
        if(StringUtils.isNoneBlank(element[26])){
            dltEntity.setPrizeFiveAdditionalNum(LongUtil.parseLong(element[26]));
        }else{
            dltEntity.setPrizeFiveAdditionalNum(0L);
        }
        dltEntity.setPrizeFiveAdditionalEveryMoney(0L);
        if(StringUtils.isNoneBlank(element[27])){
            dltEntity.setPrizeSixNum(LongUtil.parseLong(element[27]));
        }else{
            dltEntity.setPrizeSixNum(0L);
        }
        dltEntity.setPrizeSixEveryMoney(0L);
        if(StringUtils.isNoneBlank(element[28])){
            dltEntity.setPrizeSixAdditionalNum(LongUtil.parseLong(element[28]));
        }else{
            dltEntity.setPrizeSixAdditionalNum(0L);
        }
        dltEntity.setPrizeSixAdditionalEveryMoney(0L);
        if(StringUtils.isNoneBlank(element[29])){
            dltEntity.setPrizeSevenNum(LongUtil.parseLong(element[29]));
        }else{
            dltEntity.setPrizeSevenNum(0L);
        }
        dltEntity.setPrizeSevenEveryMoney(0L);
        if(StringUtils.isNoneBlank(element[30])){
            dltEntity.setPrizeSevenAdditionalNum(LongUtil.parseLong(element[30]));
        }else{
            dltEntity.setPrizeSevenAdditionalNum(0L);
        }
        dltEntity.setPrizeSevenAdditionalEveryMoney(0L);
        if(StringUtils.isNoneBlank(element[31])){
            dltEntity.setPrizeEightNum(LongUtil.parseLong(element[31]));
        }else{
            dltEntity.setPrizeEightNum(0L);
        }
        dltEntity.setPrizeEightEveryMoney(0L);
        dltEntity.setPrizeEightAdditionalNum(0L);
        dltEntity.setPrizeEightAdditionalEveryMoney(0L);
        Timestamp currTimestamp = DateUtil.getCurrTimestamp();
        dltEntity.setCreateTime(currTimestamp);
        dltEntity.setUpdateTime(currTimestamp);
        dltEntity.setStatus(1);
        dltEntity.setRemark(content);
        return dltEntity;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    }
}
