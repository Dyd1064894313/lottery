package top.duanyd.lottery.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.duanyd.lottery.common.MyJdbcTemplet;
import top.duanyd.lottery.dao.interfaces.IDataURLDao;
import top.duanyd.lottery.entity.DataURLEntity;

import java.util.List;

/**
 * @Author: duanyandong
 * @Date: 2018/8/19 20:15
 * @Description:
 */
@Repository
public class DataURLDaoImol implements IDataURLDao {

    @Autowired
    MyJdbcTemplet myJdbcTemplet;
    @Override
    public List<DataURLEntity> getAllDataURL() {
        List<DataURLEntity> dataURLEntities = myJdbcTemplet.queryAllData(DataURLEntity.class, -1, -1, "");
        return dataURLEntities;
    }
}
