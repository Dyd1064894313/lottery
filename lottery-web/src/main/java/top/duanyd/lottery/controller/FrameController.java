package top.duanyd.lottery.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import top.duanyd.lottery.entity.MenuEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: duanyandong
 * @Date: 2018/8/17 9:33
 * @Description:
 */
@Controller
public class FrameController {

    @RequestMapping("/index")
    public ModelAndView toIndex(String menuCode){
        ModelAndView vm = new ModelAndView("index");
        vm.addObject("menuCode", menuCode);
        return vm;
    }

    @RequestMapping("/login")
    public ModelAndView toLogin(){
        ModelAndView vm = new ModelAndView("login");
        return vm;
    }

    @RequestMapping("/header")
    public ModelAndView getHeader(String menuCode){
        ModelAndView mv = new ModelAndView("model/header");
        List<MenuEntity> menuList = new ArrayList<>();
        MenuEntity indexMenu = new MenuEntity();
        indexMenu.setCode("indexPage");
        indexMenu.setName("首页");
        indexMenu.setUrl("/index");
        if(indexMenu.getCode().equals(menuCode) || StringUtils.isBlank(menuCode)){
            indexMenu.setSelected(true);
        }
        menuList.add(indexMenu);
        MenuEntity ssqMenu = new MenuEntity();
        ssqMenu.setCode("ssqMainPage");
        ssqMenu.setName("双色球");
        ssqMenu.setUrl("/ssq/index");
        if(ssqMenu.getCode().equals(menuCode)){
            ssqMenu.setSelected(true);
        }
        menuList.add(ssqMenu);
        MenuEntity dltMenu = new MenuEntity();
        dltMenu.setCode("dltMainPage");
        dltMenu.setName("大乐透");
        dltMenu.setUrl("/dlt/index");
        if(dltMenu.getCode().equals(menuCode)){
            dltMenu.setSelected(true);
        }
        menuList.add(dltMenu);
        mv.addObject("menuList", menuList);
        return mv;
    }

    @RequestMapping("/footer")
    public ModelAndView getFooter(String menuCode){
        ModelAndView mv = new ModelAndView("model/footer");
        return mv;
    }
}
