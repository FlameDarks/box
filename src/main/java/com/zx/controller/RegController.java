package com.zx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegController {
    @RequestMapping("/reg")
    public String getIndex(){
        return "reg";
    }
}
