package top.duanyd.lottery.schedule.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.duanyd.lottery.core.common.MyJdbcTemplet;
import top.duanyd.lottery.schedule.dao.interfaces.IDltDao;
import top.duanyd.lottery.core.entity.DltEntity;

import java.util.List;

/**
 * @Author: duanyandong
 * @Date: 2018/8/19 20:18
 * @Description:
 */
@Repository
public class DltDaoImpl implements IDltDao {

    @Autowired
    MyJdbcTemplet myJdbcTemplet;

    @Override
    public void batchInsert(List<DltEntity> entityList) {
        myJdbcTemplet.batchInsert(entityList);
    }

    @Override
    public List<DltEntity> getAllDltEntity(int page, int size, String orderBy) {
        return myJdbcTemplet.queryAllData(DltEntity.class, page, size, orderBy);
    }
}
