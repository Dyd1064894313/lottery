package top.duanyd.lottery.service.interfaces;

import top.duanyd.lottery.entity.SsqEntity;

import java.util.List;

/**
 * @Author: duanyandong
 * @Date: 2018/8/19 20:41
 * @Description:
 */
public interface ISsqService {

    /**
     * 批量插入双色球数据
     * @param entityList
     */
    public void batchInsert(List<SsqEntity> entityList);

    /**
     * 获取双色球表中最后一条数据
     * @return
     */
    public SsqEntity getLastSsqEntity();
}
