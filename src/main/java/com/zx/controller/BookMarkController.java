package com.zx.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zx.bean.BookMark;
import com.zx.bean.Msg;
import com.zx.service.BookMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BookMarkController {
    @Autowired
    BookMarkService bookmarkService;
    @RequestMapping("/bookmark")
    @ResponseBody
    public Msg getContactWithJson(@RequestParam(value = "pn",defaultValue = "1")Integer pn, @RequestParam(value = "userId")Integer id){
//        每页显示条数
        PageHelper.startPage(pn,3);
//        升序排列
        PageHelper.orderBy("bookmark_id asc");
//        获取数据
        List<BookMark> bookmarks = bookmarkService.getAll(id);
//        连续显示的页数
        PageInfo pageInfo = new PageInfo(bookmarks,2);
        System.out.println("连续显示的页数："+pageInfo.getPages()+"一共有："+pageInfo.getTotal()
        );
        return Msg.success().add("bookmark_pageInfo",pageInfo);
    }
    //  保存
    @RequestMapping(value = "/saveBookMark",method = RequestMethod.POST)
    @ResponseBody
    public Msg save(BookMark bookmark){
        bookmarkService.save(bookmark);
        return Msg.success();
    }

    //  回显
    @RequestMapping(value = "/echoBookMark",method = RequestMethod.GET)
    @ResponseBody
    public Msg echo(@RequestParam(value = "Id")Integer id){
        BookMark bookmark = bookmarkService.echobookmarks(id);
        return Msg.success().add("bookmarks",bookmark);
    }

    //  修改
    @RequestMapping(value = "/editBookMark",method = RequestMethod.PUT)
    @ResponseBody
    public Msg edit(BookMark bookmark){
        bookmarkService.update(bookmark);
        return Msg.success();
    }

    //   删除
    @RequestMapping(value = "/delBookMark",method = RequestMethod.DELETE)
    @ResponseBody
    public Msg delete(@RequestParam(value = "Id")String ids){
        if (ids.contains("-")){
            String[] str_ids = ids.split("-");
            List<Integer> del_ids = new ArrayList<>();
            for (String string : str_ids){
                del_ids.add(Integer.parseInt(string));
            }
            bookmarkService.deleteAll(del_ids);
            return Msg.success();
        }else {
            Integer id = Integer.parseInt(ids);
            System.out.println("删除的id："+id);
            bookmarkService.delete(id);
            return Msg.success();
        }
    }
}

