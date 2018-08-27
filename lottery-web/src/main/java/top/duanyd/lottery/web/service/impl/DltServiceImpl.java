package top.duanyd.lottery.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.duanyd.lottery.web.consts.CommonConst;
import top.duanyd.lottery.web.dao.interfaces.IDltDao;
import top.duanyd.lottery.core.entity.DltEntity;
import top.duanyd.lottery.web.service.interfaces.IDltService;
import top.duanyd.lottery.core.util.DateUtil;

import java.util.*;

/**
 * Created by Administrator on 2018/8/25.
 */
@Service
public class DltServiceImpl implements IDltService{
    @Autowired
    IDltDao dltDao;

    @Override
    public int getAllDltEntityCount() {
        return dltDao.getAllDltEntityCount();
    }

    @Override
    public List<DltEntity> getAllDltEntity(int page, int size, String orderBy) {
        return dltDao.getAllDltEntity(page, size, orderBy);
    }

    @Override
    public Map getWeekData(int lastNumbers) {
        List<DltEntity> dltEntityList = null;
        if(lastNumbers <= 0){
            dltEntityList = dltDao.getAllDltEntity(-1, -1, null);
        }else{
            dltEntityList = dltDao.getAllDltEntity(0, lastNumbers, "id desc");
        }
        Map<String, Map<String, Long>> weekData = new HashMap();
        for(DltEntity dlt : dltEntityList){
            Date lotteryDate = dlt.getLotteryDate();
            String week = DateUtil.getWeekOfDate(lotteryDate);
            setWeekData(weekData, CommonConst.ColorPrefix.RED_PREFIX + dlt.getRedOne(), week);
            setWeekData(weekData, CommonConst.ColorPrefix.RED_PREFIX + dlt.getRedTwo(), week);
            setWeekData(weekData, CommonConst.ColorPrefix.RED_PREFIX + dlt.getRedThree(), week);
            setWeekData(weekData, CommonConst.ColorPrefix.RED_PREFIX + dlt.getRedFour(), week);
            setWeekData(weekData, CommonConst.ColorPrefix.RED_PREFIX + dlt.getRedFive(), week);
            setWeekData(weekData, CommonConst.ColorPrefix.BLUE_PREFIX + dlt.getBlueOne(), week);
            setWeekData(weekData, CommonConst.ColorPrefix.BLUE_PREFIX + dlt.getBlueTwo(), week);
        }
        return weekData;
    }

    private void setWeekData(Map<String, Map<String, Long>> weekData, String num, String weekName){
        if(weekData.get(num) == null){
            Map<String, Long> weeksCount = new HashMap<>();
            weeksCount.put(weekName, 1L);
            weekData.put(num, weeksCount);
        }else{
            Map<String, Long> weeksCount = weekData.get(num);
            if(weeksCount.get(weekName) == null){
                weeksCount.put(weekName, 1L);
            }else{
                Long count = weeksCount.get(weekName) + 1;
                weeksCount.put(weekName, count);
            }
        }
    }
}
