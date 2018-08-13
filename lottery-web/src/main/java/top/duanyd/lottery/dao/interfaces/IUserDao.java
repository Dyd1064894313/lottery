package top.duanyd.lottery.dao.interfaces;

import top.duanyd.lottery.entity.UserEntity;

/**
 * @Author: duanyandong
 * @Date: 2018/8/13 9:51
 * @Description:
 */
public interface IUserDao {

    /**
     * 插入数据
     * @param userEntity
     */
    public void insert(UserEntity userEntity);
}
