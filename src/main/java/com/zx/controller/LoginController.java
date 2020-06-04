package com.zx.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 跳转页面
 */
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
    @RequestMapping("/page7")
    public String getPage7(){
        return "calendar";
    }

    @RequestMapping("/page8")
    public String getPage8(){
        return "map";
    }

    @RequestMapping("/page9")
    public String getPage9(){
        return "image";
    }

    @RequestMapping("/page10")
    public String getPage10(){
        return "images";
    }

    @RequestMapping("/page11")
    public String getPage11(){
        return "hotimage";
    }

    @RequestMapping("/page12")
    public String getPage12(){
        return "myimage";
    }

    @RequestMapping("/page6")
    @RequiresRoles({"admin"})
    public String getPage6(){
        return "admin";
    }

}
