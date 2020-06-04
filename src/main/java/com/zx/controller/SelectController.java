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
import java.util.ArrayList;
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
    @Autowired
    CalendarService calendarService;
    @Autowired
    ImageService imageService;

    /**
     * 搜索
     * @param check 选择项
     * @param data 搜索内容
     * @param type 不同页面的不同搜索内容
     * @param main 图片库三个不同的页面进行标识
     * @param request 获取Session
     * @return
     */
    @RequestMapping(value = "/select",method = RequestMethod.POST)
    @ResponseBody
    public Msg select(@RequestParam(value = "check")Integer check, @RequestParam(value = "data")String data, @RequestParam(value = "type")Integer type,@RequestParam(value = "main",required = false)Integer main, HttpServletRequest request){
        HttpSession session = request.getSession();
        Integer userId = ((User) session.getAttribute("loginUser")).getUserId();
        data = "%"+data+"%";
        List<ImageMark> imageMarks = new ArrayList<>();
        List<Image> images = new ArrayList<>();
        if (main==3){
            images = imageService.select(data);
            imageMarks = imageService.get(userId);
        }
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
        }else if (type == 6){
            PageHelper.orderBy("calendar_id asc");
            List<Calendar> list = calendarService.select(userId,data,check);
            PageInfo pageInfo = new PageInfo(list,3);
            return Msg.success().add("calendar_pageInfo",pageInfo);
        } else if (type == 7){
            if (main==1){
                PageHelper.orderBy("image_id asc");
                List<Image> list = imageService.select(data);
                PageInfo pageInfo = new PageInfo(list,3);
                return Msg.success().add("image_pageInfo",pageInfo);
            }else if (main==2){
                PageHelper.orderBy("image_like desc");
                List<Image> list = imageService.select(data);
                PageInfo pageInfo = new PageInfo(list,3);
                return Msg.success().add("image_pageInfo",pageInfo);
            }else {
                PageHelper.orderBy("image_id asc");
                List<Image> image = imageService.selectById(imageMarks,images);
                PageInfo pageInfo = new PageInfo(image,3);
                return Msg.success().add("image_pageInfo",pageInfo);
            }
        }
        return Msg.fail();
    }

    /**
     * 搜索用户列表
     * @param data
     * @param check
     * @return
     */
    @RequiresRoles({"admin"})
    private Msg selectAdmin(String data,Integer check){
        PageHelper.orderBy("user_id asc");
        List<User> list = adminService.select(data,check);
        PageInfo pageInfo = new PageInfo(list,3);
        return Msg.success().add("admin_pageInfo",pageInfo);
    }
}
