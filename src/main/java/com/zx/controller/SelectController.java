package com.zx.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zx.bean.*;
import com.zx.service.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class SelectController {
    @Autowired
    NoteBookService noteBookService;
    @Autowired
    BookMarkService bookMarkService;
    @Autowired
    CloudService cloudService;
    @Autowired
    ContactService contactService;
    @Autowired
    AdminService adminService;

    @RequestMapping(value = "/select",method = RequestMethod.POST)
    @ResponseBody
    public Msg select(@RequestParam(value = "check")Integer check, @RequestParam(value = "data")String data, @RequestParam(value = "type")Integer type, HttpServletRequest request){
        System.out.println(data+"\t"+type);
        HttpSession session = request.getSession();
        Integer userId = ((User) session.getAttribute("loginUser")).getUserId();
        data = "%"+data+"%";
        PageHelper.startPage(1,3);
        if (type == 1){
            PageHelper.orderBy("notebook_id asc");
            List<NoteBook> list = noteBookService.select(userId,data,check);
            PageInfo pageInfo = new PageInfo(list,3);
            return Msg.success().add("notebook_pageInfo",pageInfo);
        }else if (type == 2){
            PageHelper.orderBy("contact_id asc");
            List<Contact> list = contactService.select(userId,data,check);
            PageInfo pageInfo = new PageInfo(list,3);
            return Msg.success().add("contact_pageInfo",pageInfo);
        }else if (type == 3){
            PageHelper.orderBy("bookmark_id asc");
            List<BookMark> list = bookMarkService.select(userId,data,check);
            PageInfo pageInfo = new PageInfo(list,3);
            return Msg.success().add("bookmark_pageInfo",pageInfo);
        }else if (type == 4){
            PageHelper.orderBy("cloud_id asc");
            List<Cloud> list = cloudService.select(userId,data);
            PageInfo pageInfo = new PageInfo(list,3);
            return Msg.success().add("cloud_pageInfo",pageInfo);
        }else if (type == 5){
            return selectAdmin(data,check);
        }
        return Msg.fail();
    }

    @RequiresRoles({"admin"})
    private Msg selectAdmin(String data,Integer check){
        PageHelper.orderBy("user_id asc");
        List<User> list = adminService.select(data,check);
        PageInfo pageInfo = new PageInfo(list,3);
        return Msg.success().add("admin_pageInfo",pageInfo);
    }
}
