package top.duanyd.lottery.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.duanyd.lottery.dao.interfaces.IDltDao;
import top.duanyd.lottery.entity.DataURLEntity;
import top.duanyd.lottery.entity.DltEntity;
import top.duanyd.lottery.service.interfaces.IDataURLService;
import top.duanyd.lottery.service.interfaces.IDltService;

import java.util.List;

/**
 * @Author: duanyandong
 * @Date: 2018/8/19 20:45
 * @Description:
 */
@Service
public class DltServiceImpl implements IDltService {
    @Autowired
    IDltDao dltDao;

    @Override
    public void batchInsert(List<DltEntity> entityList) {
        dltDao.batchInsert(entityList);
    }

    @Override
    public DltEntity getLastDltEntity() {
        List<DltEntity> dltEntityList = dltDao.getAllDltEntity(0, 1, "id desc");
        if(dltEntityList != null && !dltEntityList.isEmpty()){
            return dltEntityList.get(0);
        }
        return null;
    }
}
