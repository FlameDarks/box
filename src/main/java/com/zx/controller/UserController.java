package com.zx.controller;


import com.zx.bean.Msg;
import com.zx.bean.User;
import com.zx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sound.midi.SoundbankResource;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/user",method = RequestMethod.POST)
    @ResponseBody
    public Msg getUserWithJson(@RequestParam(value = "userName")String name,@RequestParam(value = "userPassword")String pwd){
        System.out.println(name+"空空空"+pwd);
        String base = pwd.substring(0,1)+pwd+pwd.substring(pwd.length()-1,pwd.length());
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        System.out.println(name+"空空空"+md5);
        List<User> users = userService.getAll(name,md5);
        if (users==null){
            System.out.println("全错了!");
            return Msg.fail();
        }
        User user = new User();
        user = users.get(0);
        System.out.println(user.getUserName() + "///////" + user.getUserPassword());
        return Msg.success().add("user_Info", user.getUserId());


    }

}
