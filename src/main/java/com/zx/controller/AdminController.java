package com.zx.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zx.bean.Msg;
import com.zx.bean.User;
import com.zx.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    //    显示记事本列表
    @RequestMapping("/selectAdmin")
    @ResponseBody
    public Msg getNoteBookWithJson(@RequestParam(value = "pn",defaultValue = "1")Integer pn){
//        每页显示条数
        PageHelper.startPage(pn,3);
//        升序排列
        PageHelper.orderBy("user_id asc");
//        获取数据
        List<User> users = adminService.getAll();
//        连续显示的页数
        PageInfo pageInfo = new PageInfo(users,3);
        System.out.println("连续显示的页数："+pageInfo.getPages()+"时间戳："+System.currentTimeMillis());
        return Msg.success().add("admin_pageInfo",pageInfo);
    }

    //  添加用户
    @RequestMapping(value = "/saveAdmin",method = RequestMethod.POST)
    @ResponseBody
    public Msg save(User user){
        adminService.save(user);
        return Msg.success();
    }

    //   删除用户
    @RequestMapping(value = "/delAdmin",method = RequestMethod.DELETE)
    @ResponseBody
    public Msg delete(@RequestParam(value = "Id")String ids){
        if (ids.contains("-")){
            String[] str_ids = ids.split("-");
            List<Integer> del_ids = new ArrayList<>();
            for (String string : str_ids){
                del_ids.add(Integer.parseInt(string));
            }
            adminService.deleteAll(del_ids);
            return Msg.success();
        }else {
            Integer id = Integer.parseInt(ids);
            System.out.println("删除的id："+id);
            adminService.delete(id);
            return Msg.success();
        }
    }
}
