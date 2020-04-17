package com.zx.controller;


import com.zx.bean.Msg;
import com.zx.bean.User;
import com.zx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/user",method = RequestMethod.POST)
    @ResponseBody
    public Msg getUserWithJson(@RequestParam(value = "userName")String name,@RequestParam(value = "userPassword")String pwd, HttpServletRequest request){
        HttpSession session = request.getSession();
        // 登录操作
        // 判断是否是一个已经登录的用户，没有则登录
        if (null != session.getAttribute("loginUser")) {
            // 清除旧的用户
            session.removeAttribute("loginUser");
        }
        String base = pwd.substring(0,1)+pwd+pwd.substring(pwd.length()-1,pwd.length());
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        List<User> users = userService.getAll(name,md5);
        if (users==null){
            System.out.println("全错了!");
            return Msg.fail();
        }
        User user = users.get(0);
        user.setUserPassword(null);
        System.out.println("准备");
        System.out.println(user.toString());
        session.setAttribute("loginUser", user);
        System.out.println("获取的："+user.getUserName() + "///////" + user.getUserPassword());
        return Msg.success().add("userId", user.getUserId()).add("userName",user.getUserName());

    }

    @RequestMapping(value = "/saveuser",method = RequestMethod.POST)
    @ResponseBody
    public Msg save(@Valid User user, BindingResult result){
        System.out.println("进入Controller");
        System.out.println(user.toString());
        Map<String,Object> map = new HashMap<>();
        if (result.hasErrors()){
            List<FieldError> errors=result.getFieldErrors();
            for (FieldError fieldError:errors){
                System.out.println("错误字段名："+fieldError.getField());
                System.out.println("错误信息"+fieldError.getDefaultMessage());
                map.put(fieldError.getField(),fieldError.getDefaultMessage());
            }
            return Msg.fail().add("errorFields",map);
        }
        if (user.getUserPassword().equals(user.getUserPasswords())){
            userService.reg(user);
            return Msg.success();
        }
        return Msg.fail().add("errorFields","密码不一样");

    }

    @RequestMapping("/checkuser")
    @ResponseBody
    public Msg checkuser(@RequestParam("user_name")String username){
        String reg = "(^[a-zA-Z0-9_-]{6,10})|(^[\\u2E80-\\u9FFF]{3,5})";
        if (!username.matches(reg)){
            return Msg.fail().add("va_msg","6-10个英文和数字组合或者3-5个汉字");
        }
        boolean b = userService.checkUser(username);
        System.out.println("输入的名字：");
        if (b){
            return Msg.success();
        }else {
            return Msg.fail().add("va_msg","用户名不可用");
        }
    }

}
