package top.duanyd.lottery.schedule.service.interfaces;

import top.duanyd.lottery.core.entity.DltEntity;

import java.util.List;

/**
 * @Author: duanyandong
 * @Date: 2018/8/19 20:40
 * @Description:
 */
public interface IDltService {

    /**
     * 批量插入大乐透数据
     * @param entityList
     */
    void batchInsert(List<DltEntity> entityList);

    /**
     * 获取大乐透表中最后一条数据
     * @return
     */
    DltEntity getLastDltEntity();
}
