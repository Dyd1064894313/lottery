package top.duanyd.lottery.dao.interfaces;

import top.duanyd.lottery.entity.SsqEntity;

import java.util.List;

/**
 * @Author: duanyandong
 * @Date: 2018/8/19 20:09
 * @Description:
 */
public interface ISsqDao {

    /**
     * 批量插入双色球数据
     * @param entityList
     */
    void batchInsert(List<SsqEntity> entityList);

    /**
     * 获取全部双色球数据
     * @param page
     * @param size
     * @param orderBy
     * @return
     */
    public List<SsqEntity> getAllSsqEntity(int page, int size, String orderBy);
}
