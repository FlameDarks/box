package com.zx.controller;


import com.zx.bean.Msg;
import com.zx.bean.User;
import com.zx.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/selectUser",method = RequestMethod.POST)
    @ResponseBody
    public Msg getUserWithJson(@RequestParam(value = "userName")String name,@RequestParam(value = "userPassword")String pwd, HttpServletRequest request){
        HttpSession session = request.getSession();
        // 登录操作
        // 判断是否是一个已经登录的用户，没有则登录
        if (null != session.getAttribute("loginUser")) {
            // 清除旧的用户
            session.removeAttribute("loginUser");
        }
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isAuthenticated()) {
            // 把用户名和密码封装为 UsernamePasswordToken 对象
            UsernamePasswordToken token = new UsernamePasswordToken(name, pwd);
            // rememberme
            token.setRememberMe(false);
            try {
                System.out.println("1. " + token.hashCode());
                // 执行登录.
                currentUser.login(token);
            }
            // ... catch more exceptions here (maybe custom ones specific to your application?
            // 所有认证时异常的父类.
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
                System.out.println("登录失败: " + ae.getMessage());
                return Msg.fail();
            }
        }
//        String base = pwd.substring(0,1)+pwd+pwd.substring(pwd.length()-1,pwd.length());
//        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
//        List<User> users = userService.getAll(name,md5);

        System.out.println("来到了");
//        System.out.println(currentUser.getPrincipal().toString());
//        System.out.println(currentUser.getPrincipals().toString());
//        System.out.println(currentUser.getSession().toString());
//        User user = users.get(0);
//        user.setUserPassword(null);
        User user = userService.getUserByName(currentUser.getPrincipal().toString());
        System.out.println(user.toString());
        session.setAttribute("loginUser", user);
        System.out.println("获取的："+user.getUserName() + "\t" + user.getUserPassword());
        return Msg.success().add("userId", user.getUserId()).add("userName",user.getUserName());
//        return Msg.success();
    }

    @RequestMapping(value = "/saveUser",method = RequestMethod.POST)
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

    @RequestMapping("/checkUser")
    @ResponseBody
    public Msg checkuser(@RequestParam("user_name")String username){
        System.out.println(username);
        String reg = "(^[a-zA-Z0-9_-]{6,10})|(^[\\u2E80-\\u9FFF]{3,5})";
        if (!username.matches(reg)){
            return Msg.fail().add("va_msg","6-10个英文和数字组合或者3-5个汉字");
        }
        boolean b = userService.checkUser(username);
        System.out.println("验证用户名中");
        if (!b){
            System.out.println("error");
            return Msg.fail().add("va_msg","用户名不可用");
        }else {
            System.out.println("success");
            return Msg.success();
        }
    }

    @RequestMapping("/checkPwd")
    @ResponseBody
    public Msg checkpwd(@RequestParam("userId")Integer userId,@RequestParam("userPassword")String pwd){
        System.out.println("进来了!!!!!!!");
        User user = userService.getUser(userId);
        String result = String.valueOf(new SimpleHash("MD5",pwd,user.getUserName(),1024));
        if (user.getUserPassword().equals(result)){
            return Msg.success();
        }
        return Msg.fail().add("va_msg","原密码错误");
    }

    @RequestMapping(value = "/savePwd",method = RequestMethod.POST)
    @ResponseBody
//    public Msg savepwd(@RequestParam("userId")Integer userId,@Valid @RequestParam("userPassword") String userPassword, @Valid @RequestParam("userPasswords") String userPasswords,BindingResult result){
    public Msg savepwd(@Valid User user,BindingResult result){
        System.out.println("进入Controller");
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
            userService.update(user);
            return Msg.success();
        }
        return Msg.fail().add("errorFields","密码不一样");
    }
}
