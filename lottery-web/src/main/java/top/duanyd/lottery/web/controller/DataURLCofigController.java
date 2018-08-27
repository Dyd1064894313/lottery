package top.duanyd.lottery.web.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.duanyd.lottery.core.entity.DataURLEntity;
import top.duanyd.lottery.web.service.interfaces.IDataURLConfigService;

import java.util.List;

/**
 * Created by Administrator on 2018/8/8.
 */
@Controller
@RequestMapping("/dataURLConfig")
public class DataURLCofigController {

    @Autowired
    IDataURLConfigService dataURLConfigService;

    @RequestMapping("/getAllDataURL")
    @ResponseBody
    public String getAllDataURL(){
        List<DataURLEntity> list = dataURLConfigService.getAllDataURL();
        return JSONObject.toJSONString(list);
    }
}
