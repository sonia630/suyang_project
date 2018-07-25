package com.o2o.nm.sys.controller;


import com.o2o.nm.sys.util.ResConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@ResConfig(name = "dashboard", id = "dash")
public class DashBoardController {

    @RequestMapping("dashboard")
    @ResConfig(name = "dashboard页面", id = "dash1")
    public String dashboard() {
        return "modules/sys/dashboard";
    }
}
