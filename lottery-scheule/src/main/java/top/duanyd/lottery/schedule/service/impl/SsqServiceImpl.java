package top.duanyd.lottery.schedule.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.duanyd.lottery.schedule.dao.interfaces.ISsqDao;
import top.duanyd.lottery.core.entity.SsqEntity;
import top.duanyd.lottery.schedule.service.interfaces.ISsqService;

import java.util.List;

/**
 * @Author: duanyandong
 * @Date: 2018/8/19 20:48
 * @Description:
 */
@Service
public class SsqServiceImpl implements ISsqService {
    @Autowired
    ISsqDao ssqDao;

    @Override
    public void batchInsert(List<SsqEntity> entityList) {
        ssqDao.batchInsert(entityList);
    }

    @Override
    public SsqEntity getLastSsqEntity() {
        List<SsqEntity> allSsqEntity = ssqDao.getAllSsqEntity(0, 1, "id desc");
        if(allSsqEntity != null && !allSsqEntity.isEmpty()){
            return allSsqEntity.get(0);
        }
        return null;
    }
}
