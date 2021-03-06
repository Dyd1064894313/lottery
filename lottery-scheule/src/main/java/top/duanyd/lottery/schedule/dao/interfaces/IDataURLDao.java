package top.duanyd.lottery.schedule.dao.interfaces;

import top.duanyd.lottery.core.entity.DataURLEntity;

import java.util.List;

/**
 * @Author: duanyandong
 * @Date: 2018/8/19 20:08
 * @Description:
 */
public interface IDataURLDao {

    /**
     * 查询所有dataURL数据
     * @return
     */
    List<DataURLEntity> getAllDataURL();
}
