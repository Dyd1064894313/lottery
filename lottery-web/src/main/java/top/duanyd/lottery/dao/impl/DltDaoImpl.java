package top.duanyd.lottery.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.duanyd.lottery.common.MyJdbcTemplet;
import top.duanyd.lottery.dao.interfaces.IDltDao;
import top.duanyd.lottery.entity.DltEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/8/25.
 */
@Repository
public class DltDaoImpl implements IDltDao {
    @Autowired
    MyJdbcTemplet myJdbcTemplet;


    @Override
    public int getAllDltEntityCount() {
        return myJdbcTemplet.getCountAllData(DltEntity.class);
    }

    @Override
    public List<DltEntity> getAllDltEntity(int page, int size, String orderBy) {
        return myJdbcTemplet.queryAllData(DltEntity.class, page, size, orderBy);
    }
}
