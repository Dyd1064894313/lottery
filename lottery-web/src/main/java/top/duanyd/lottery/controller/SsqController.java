package top.duanyd.lottery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Administrator on 2018/8/22.
 */
@Controller()
@RequestMapping("/ssq")
public class SsqController {

    @RequestMapping("/index")
    public ModelAndView toSsqMainPage(){
        ModelAndView mv = new ModelAndView("ssq/ssqMainPage");
        return mv;
    }
}
