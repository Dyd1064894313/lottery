package top.duanyd.lottery.service.interfaces;

import top.duanyd.lottery.entity.DataURLEntity;

import java.util.List;

/**
 * @Author: duanyandong
 * @Date: 2018/8/19 20:38
 * @Description:
 */
public interface IDataURLService {

    /**
     * 获取dataURL全部数据
     * @return
     */
    List<DataURLEntity> getAllDataURL();
}
