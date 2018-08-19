package top.duanyd.lottery.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.duanyd.lottery.dao.interfaces.IDataURLDao;
import top.duanyd.lottery.entity.DataURLEntity;
import top.duanyd.lottery.service.interfaces.IDataURLService;

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
