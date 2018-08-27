package top.duanyd.lottery.web.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.duanyd.lottery.core.common.MyJdbcTemplet;
import top.duanyd.lottery.web.dao.interfaces.IDltDao;
import top.duanyd.lottery.core.entity.DltEntity;

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
