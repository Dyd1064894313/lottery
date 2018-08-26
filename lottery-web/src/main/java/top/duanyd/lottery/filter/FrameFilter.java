package top.duanyd.lottery.filter;

import org.apache.commons.lang3.StringUtils;
import top.duanyd.lottery.entity.MenuEntity;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/25.
 */
public class FrameFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uri = request.getRequestURI();
        if(uri.contains("/resources")){//如果包含resources路径则不过滤
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String contextPath = request.getContextPath();
        uri = uri.substring(contextPath.length());
        List<MenuEntity> menuList = getMenuList();
        for (MenuEntity menu : menuList){
            if(menu.getUrl().equals(uri)){
                menu.setSelected(true);
            }
        }
        request.getSession().setAttribute("menuList", menuList);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    private List getMenuList(){
        List<MenuEntity> menuList = new ArrayList<>();
        MenuEntity indexMenu = new MenuEntity();
        indexMenu.setCode("indexPage");
        indexMenu.setName("首页");
        indexMenu.setUrl("/index");
        menuList.add(indexMenu);
        MenuEntity ssqMenu = new MenuEntity();
        ssqMenu.setCode("ssqMainPage");
        ssqMenu.setName("双色球");
        ssqMenu.setUrl("/ssq/index");
        menuList.add(ssqMenu);
        MenuEntity dltMenu = new MenuEntity();
        dltMenu.setCode("dltMainPage");
        dltMenu.setName("大乐透");
        dltMenu.setUrl("/dlt/index");
        menuList.add(dltMenu);
        return menuList;
    }
}
