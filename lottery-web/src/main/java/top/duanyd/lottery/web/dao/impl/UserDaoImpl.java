package top.duanyd.lottery.web.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.duanyd.lottery.core.common.MyJdbcTemplet;
import top.duanyd.lottery.web.dao.interfaces.IUserDao;
import top.duanyd.lottery.core.entity.UserEntity;

/**
 * @Author: duanyandong
 * @Date: 2018/8/13 9:52
 * @Description:
 */
@Repository
public class UserDaoImpl implements IUserDao {
    private static Log logger = LogFactory.getLog(UserDaoImpl.class);
    @Autowired
    MyJdbcTemplet myJdbcTemplet;
    @Override
    public void insert(UserEntity userEntity){
        myJdbcTemplet.insert(userEntity);
    }

    @Override
    public UserEntity queryById(UserEntity userEntity) {
        return myJdbcTemplet.queryById(userEntity, UserEntity.class);
    }
}
