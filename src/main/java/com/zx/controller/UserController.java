package com.zx.controller;

import com.zx.bean.Msg;
import com.zx.bean.User;
import com.zx.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    /**
     * 登录
     * @param name
     * @param pwd
     * @param request
     * @return
     */
    @RequestMapping(value = "/selectUser",method = RequestMethod.POST)
    @ResponseBody
    public Msg getUserWithJson(@RequestParam(value = "userName")String name,@RequestParam(value = "userPassword")String pwd, HttpServletRequest request){
        HttpSession session = request.getSession();
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
                // 执行登录.
                currentUser.login(token);
            }
            catch (UnknownAccountException e){
                System.out.println("用户不存在！");
                return Msg.fail().add("error","用户不存在！");
            }
            catch (IncorrectCredentialsException e){
                System.out.println("密码不对！");
                return Msg.fail().add("error","密码不对！");
            }
            catch (LockedAccountException e){
                System.out.println("用户被锁定！");
                return Msg.fail().add("error","用户被锁定！");
            }
            // 所有认证时异常的父类.
            catch (AuthenticationException e) {
                System.out.println("登录失败！");
                return Msg.fail().add("error","登录失败！");
            }
        }
        User user = userService.getUserByName(currentUser.getPrincipal().toString());
        session.setAttribute("loginUser", user);
        return Msg.success().add("userId", user.getUserId()).add("userName",user.getUserName());
    }

    /**
     * 注册
     * @param user
     * @param result
     * @return
     */
    @RequestMapping(value = "/saveUser",method = RequestMethod.POST)
    @ResponseBody
    public Msg save(@Valid User user, BindingResult result){
        Map<String,Object> map = new HashMap<>();
//        查询是否不符合标准
        if (result.hasErrors()){
            List<FieldError> errors=result.getFieldErrors();
            for (FieldError fieldError:errors){
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

    /**
     * 验证用户名
     * @param username
     * @return
     */
    @RequestMapping("/checkUser")
    @ResponseBody
    public Msg checkuser(@RequestParam("user_name")String username){
        System.out.println(username);
        String reg = "(^[a-zA-Z0-9_-]{6,10})|(^[\\u2E80-\\u9FFF]{3,5})";
        if (!username.matches(reg)){
            return Msg.fail().add("va_msg","6-10个英文和数字组合或者3-5个汉字");
        }
        boolean b = userService.checkUser(username);
//        如果没找到
        if (!b){
            System.out.println("error");
            return Msg.fail().add("va_msg","用户名不可用");
        }else {
            System.out.println("success");
            return Msg.success();
        }
    }

    /**
     * 验证密码
     * @param userId
     * @param pwd
     * @return
     */
    @RequestMapping("/checkPwd")
    @ResponseBody
    public Msg checkpwd(@RequestParam("userId")Integer userId,@RequestParam("userPassword")String pwd){
        User user = userService.getUser(userId);
//        加盐加密
        String result = String.valueOf(new SimpleHash("MD5",pwd,user.getUserName(),1024));
        if (user.getUserPassword().equals(result)){
            return Msg.success();
        }
        return Msg.fail().add("va_msg","原密码错误");
    }

    /**
     * 修改密码
     * @param user
     * @param result
     * @return
     */
    @RequestMapping(value = "/savePwd",method = RequestMethod.POST)
    @ResponseBody
    public Msg savepwd(@Valid User user,BindingResult result){
        Map<String,Object> map = new HashMap<>();
//        查询是否不符合标准
        if (result.hasErrors()){
            List<FieldError> errors=result.getFieldErrors();
            for (FieldError fieldError:errors){
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
