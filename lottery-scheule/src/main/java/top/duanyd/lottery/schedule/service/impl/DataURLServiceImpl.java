package top.duanyd.lottery.schedule.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.duanyd.lottery.schedule.dao.interfaces.IDataURLDao;
import top.duanyd.lottery.core.entity.DataURLEntity;
import top.duanyd.lottery.schedule.service.interfaces.IDataURLService;

import java.util.List;

/**
 * @Author: duanyandong
 * @Date: 2018/8/19 20:43
 * @Description:
 */
@Service
public class DataURLServiceImpl implements IDataURLService {
    @Autowired
    IDataURLDao dataURLDao;

    @Override
    public List<DataURLEntity> getAllDataURL() {
        return dataURLDao.getAllDataURL();
    }
}
