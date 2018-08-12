package top.duanyd.lottery.dao.interfaces;

import top.duanyd.lottery.dao.impl.DataURLDaoImpl;
import top.duanyd.lottery.entity.DataURLEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/8/8.
 */
public interface IDataURLDao {

    /**
     * 查询全部数据获取地址信息
     * @return
     */
    public List<DataURLEntity> getAllDataURL();

    /**
     * 查询全部数据获取地址信息
     * @return
     */
    public Long insert(DataURLEntity dataURLEntity);
}
