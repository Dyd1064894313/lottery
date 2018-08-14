package top.duanyd.lottery.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.duanyd.lottery.dao.interfaces.IDataURLDao;
import top.duanyd.lottery.dao.interfaces.IUserDao;
import top.duanyd.lottery.entity.DataURLEntity;
import top.duanyd.lottery.entity.UserEntity;
import top.duanyd.lottery.service.interfaces.IDataURLConfigService;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/8/9.
 */
@Service
public class DataURLConfigServiceImpl implements IDataURLConfigService {
    private static Log logger = LogFactory.getLog(DataURLConfigServiceImpl.class);
    @Autowired
    IDataURLDao dataURLDao;

    @Autowired
    IUserDao userDao;
    @Override
    public List<DataURLEntity> getAllDataURL() {
        List<DataURLEntity> allDataURL = dataURLDao.getAllDataURL();
//        if(allDataURL != null && !allDataURL.isEmpty()){
//            DataURLEntity dataURLEntity = allDataURL.get(0);
//            dataURLEntity.setId(null);
//            dataURLDao.insert(dataURLEntity);
//        }
        Date now = new Date();
        UserEntity userEntity = new UserEntity();
        userEntity.setName("test1");
        userEntity.setPassword("123456");
        userEntity.setEmail("123@qq,com");
        userEntity.setPhone("13012345678");
        userEntity.setCreateTime(now);
        userEntity.setUpdateTime(now);
        userDao.insert(userEntity);
        UserEntity user = userDao.queryById(userEntity);
        logger.info(JSONObject.toJSONString(user));
        return allDataURL;
    }
}
