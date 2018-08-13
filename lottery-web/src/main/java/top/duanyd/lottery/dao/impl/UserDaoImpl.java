package top.duanyd.lottery.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import top.duanyd.lottery.common.MyJdbcTemplet;
import top.duanyd.lottery.dao.interfaces.IUserDao;
import top.duanyd.lottery.entity.UserEntity;

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
}
