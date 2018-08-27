package top.duanyd.lottery.schedule.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.duanyd.lottery.core.common.MyJdbcTemplet;
import top.duanyd.lottery.schedule.dao.interfaces.ISsqDao;
import top.duanyd.lottery.core.entity.SsqEntity;

import java.util.List;

/**
 * @Author: duanyandong
 * @Date: 2018/8/19 20:23
 * @Description:
 */
@Repository
public class SsqDaoImpl implements ISsqDao {
    @Autowired
    MyJdbcTemplet myJdbcTemplet;

    @Override
    public void batchInsert(List<SsqEntity> entityList){
        myJdbcTemplet.batchInsert(entityList);
    }

    @Override
    public List<SsqEntity> getAllSsqEntity(int page, int size, String orderBy) {
        return myJdbcTemplet.queryAllData(SsqEntity.class, page, size, orderBy);
    }
}
