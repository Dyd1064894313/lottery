package top.duanyd.lottery.web.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import top.duanyd.lottery.web.consts.CommonConst;
import top.duanyd.lottery.web.dto.PageResponseDto;
import top.duanyd.lottery.web.dto.WeekDataDto;
import top.duanyd.lottery.core.entity.DltEntity;
import top.duanyd.lottery.web.service.interfaces.IDltService;
import top.duanyd.lottery.web.util.RequestUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/22.
 */
@Controller()
@RequestMapping("/dlt")
public class DltController {
    @Autowired
    IDltService dltService;

    @RequestMapping("/index")
    public ModelAndView toSsqMainPage(String menuCode){
        ModelAndView mv = new ModelAndView("dlt/dltMainPage");
        mv.addObject("menuCode", menuCode);
        return mv;
    }

    @RequestMapping("/getAllData")
    @ResponseBody
    public String getAllData(HttpServletRequest request){
        int page = RequestUtil.getIntegerParam(request, "page", 1);
        int limit = RequestUtil.getIntegerParam(request, "limit", 10);
        page = page -1;
        List<DltEntity> allDltEntity = dltService.getAllDltEntity(page, limit, "id desc");
        int count = dltService.getAllDltEntityCount();
        PageResponseDto responseDto = new PageResponseDto();
        responseDto.setCode(0);
        responseDto.setCount(count);
        responseDto.setData(allDltEntity);
        return JSONObject.toJSONString(responseDto);
    }

    @RequestMapping("/getWeekData")
    @ResponseBody
    public String getWeekData(HttpServletRequest request){
        int numbers = RequestUtil.getIntegerParam(request, "numbers", 0);
        Map<String, Map<String, Long>> weekData = dltService.getWeekData(numbers);
        List<WeekDataDto> list = new ArrayList<>(47);
        for(int i = 1; i <= 35; i++){
            WeekDataDto dto = new WeekDataDto();
            dto.setNum(String.format("%02d", i));
            dto.setColor(WeekDataDto.COLOR.red.toString());
            list.add(dto);
        }
        for(int i = 1; i <= 12; i++){
            WeekDataDto dto = new WeekDataDto();
            dto.setNum(String.format("%02d", i));
            dto.setColor(WeekDataDto.COLOR.blue.toString());
            list.add(dto);
        }
        long totle = 0;
        for(WeekDataDto dto : list){
            Map<String, Long> weekCount = null;
            if(WeekDataDto.COLOR.red.toString().equals(dto.getColor())){
                weekCount = weekData.get(CommonConst.ColorPrefix.RED_PREFIX + dto.getNum());
            }else{
                weekCount = weekData.get(CommonConst.ColorPrefix.BLUE_PREFIX + dto.getNum());
            }

            if(weekCount == null){
                continue;
            }
            if(weekCount.get("星期一") != null){
                dto.setMondayCount(weekCount.get("星期一"));
            }
            if(weekCount.get("星期二") != null){
                dto.setTuesdayCount(weekCount.get("星期二"));
            }
            if(weekCount.get("星期三") != null){
                dto.setWednesdayCount(weekCount.get("星期三"));
            }
            if(weekCount.get("星期四") != null){
                dto.setThurdayCount(weekCount.get("星期四"));
            }
            if(weekCount.get("星期五") != null){
                dto.setFridayCount(weekCount.get("星期五"));
            }
            if(weekCount.get("星期六") != null){
                dto.setSaturdayCount(weekCount.get("星期六"));
            }
            if(weekCount.get("星期日") != null){
                dto.setSundayCount(weekCount.get("星期日"));
            }
            dto.setTotleCount(dto.getMondayCount() + dto.getTuesdayCount() + dto.getWednesdayCount() + dto.getThurdayCount() +
                                dto.getFridayCount() + dto.getSaturdayCount() + dto.getSundayCount());

            dto.setMondayPercent((double)dto.getMondayCount() / dto.getTotleCount());
            dto.setTuesdayPercent((double)dto.getTuesdayCount() / dto.getTotleCount());
            dto.setWednesdayPercent((double)dto.getWednesdayCount() / dto.getTotleCount());
            dto.setThurdayPercent((double)dto.getThurdayCount() / dto.getTotleCount());
            dto.setFridayPercent((double)dto.getFridayCount() / dto.getTotleCount());
            dto.setSaturdayPercent((double)dto.getSaturdayCount() / dto.getTotleCount());
            dto.setSundayPercent((double)dto.getSundayCount() / dto.getTotleCount());
            totle = totle + dto.getTotleCount();
        }
        for(WeekDataDto dto : list){
            dto.setTotlePercent((double)dto.getTotleCount() / totle);
        }
        PageResponseDto responseDto = new PageResponseDto();
        responseDto.setCode(0);
        responseDto.setData(list);
        return JSONObject.toJSONString(responseDto);
    }
}
