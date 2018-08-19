package top.duanyd.lottery.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.duanyd.lottery.common.MyJdbcTemplet;
import top.duanyd.lottery.dao.interfaces.ISsqDao;
import top.duanyd.lottery.entity.SsqEntity;

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
}
