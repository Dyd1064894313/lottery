package top.duanyd.lottery.web.service.interfaces;

import top.duanyd.lottery.core.entity.DltEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/25.
 */
public interface IDltService {

    /**
     * 获取全部大乐透数据总数
     * @return
     */
    public int getAllDltEntityCount();
    /**
     * 获取全部大乐透数据
     * @param page
     * @param size
     * @param orderBy
     * @return
     */
    public List<DltEntity> getAllDltEntity(int page, int size, String orderBy);

    /**
     * 获取星期维度的分析数据
     * @param lastNumbers   统计最近期数
     * @return
     */
    public Map getWeekData(int lastNumbers);
}
