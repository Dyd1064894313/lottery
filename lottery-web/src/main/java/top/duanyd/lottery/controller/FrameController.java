package top.duanyd.lottery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: duanyandong
 * @Date: 2018/8/17 9:33
 * @Description:
 */
@Controller
public class FrameController {

    @RequestMapping("/index")
    public ModelAndView toIndex(){
        ModelAndView vm = new ModelAndView("index");
        return vm;
    }

    @RequestMapping("/login")
    public ModelAndView toLogin(){
        ModelAndView vm = new ModelAndView("login");
        return vm;
    }
}
