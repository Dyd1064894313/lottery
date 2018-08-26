package top.duanyd.lottery.dao.interfaces;

import top.duanyd.lottery.entity.DltEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/8/25.
 */
public interface IDltDao {
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
    List<DltEntity> getAllDltEntity(int page, int size, String orderBy);
}
