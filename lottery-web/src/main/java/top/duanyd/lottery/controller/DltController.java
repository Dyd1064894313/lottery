package top.duanyd.lottery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Administrator on 2018/8/22.
 */
@Controller()
@RequestMapping("/dlt")
public class DltController {

    @RequestMapping("/index")
    public ModelAndView toSsqMainPage(String menuCode){
        ModelAndView mv = new ModelAndView("dlt/dltMainPage");
        mv.addObject("menuCode", menuCode);
        return mv;
    }
}
