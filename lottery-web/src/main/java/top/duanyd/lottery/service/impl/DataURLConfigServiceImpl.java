package top.duanyd.lottery.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.duanyd.lottery.dao.interfaces.IDataURLDao;
import top.duanyd.lottery.entity.DataURLEntity;
import top.duanyd.lottery.service.interfaces.IDataURLConfigService;

import java.util.List;

/**
 * Created by Administrator on 2018/8/9.
 */
@Service
public class DataURLConfigServiceImpl implements IDataURLConfigService {
    @Autowired
    IDataURLDao dataURLDao;
    @Override
    public List<DataURLEntity> getAllDataURL() {
        List<DataURLEntity> allDataURL = dataURLDao.getAllDataURL();
        if(allDataURL != null && !allDataURL.isEmpty()){
            DataURLEntity dataURLEntity = allDataURL.get(0);
            dataURLEntity.setId(null);
            dataURLDao.insert(dataURLEntity);
        }
        return allDataURL;
    }
}
