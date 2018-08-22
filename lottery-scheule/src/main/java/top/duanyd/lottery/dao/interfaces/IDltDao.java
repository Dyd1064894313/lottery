package top.duanyd.lottery.dao.interfaces;

import top.duanyd.lottery.entity.DltEntity;

import java.util.List;

/**
 * @Author: duanyandong
 * @Date: 2018/8/19 20:09
 * @Description:
 */
public interface IDltDao {

    /**
     * 批量插入大乐透数据
     * @param entityList
     */
    void batchInsert(List<DltEntity> entityList);

    /**
     * 获取全部大乐透数据
     * @param page
     * @param size
     * @param orderBy
     * @return
     */
    List<DltEntity> getAllDltEntity(int page, int size, String orderBy);
}
