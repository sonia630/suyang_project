package com.o2o.nm.sys.controller;

import cn.jeeweb.modules.sys.utils.UserUtils;
import com.o2o.nm.sys.security.realm.UserRealm.Principal;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class LoginController extends BaseController {

    @RequestMapping(value = "/login")
    public ModelAndView login(HttpServletRequest request, HttpServletRequest response, Model model) {
        // 我的电脑有缓存问题
        Principal principal = UserUtils.getPrincipal(); // 如果已经登录，则跳转到管理首页
        if (principal != null) {
            return new ModelAndView("redirect:/");
        }
        return new ModelAndView("modules/sys/login");
    }

    @RequestMapping("/logout")
    public ModelAndView logout() {
        try {
            Subject subject = SecurityUtils.getSubject();
            if (subject != null && subject.isAuthenticated()) {
                subject.logout();
            }
            return new ModelAndView("modules/sys/login/login");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("modules/sys/login/index");
    }

}
