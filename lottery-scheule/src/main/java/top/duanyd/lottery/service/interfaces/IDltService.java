package top.duanyd.lottery.service.interfaces;

import top.duanyd.lottery.entity.DltEntity;

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
}
