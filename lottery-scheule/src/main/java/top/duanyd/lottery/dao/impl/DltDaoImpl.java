package top.duanyd.lottery.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.duanyd.lottery.common.MyJdbcTemplet;
import top.duanyd.lottery.dao.interfaces.IDltDao;
import top.duanyd.lottery.entity.DltEntity;

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
}
