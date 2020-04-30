package com.zx.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @RequestMapping("/page1")
    public String getPage1(){
        return "notebook";
    }
    @RequestMapping("/page2")
    public String getPage2(){
        return "contact";
    }
    @RequestMapping("/page3")
    public String getPage3(){
        return "bookmark";
    }
    @RequestMapping("/page4")
    public String getPage4(){
        return "cloud";
    }
    @RequestMapping("/page5")
    public String getPage5(){
        return "chat";
    }
    @RequestMapping("/page6")
    public String getPage6(){
        return "admin";
    }
}
