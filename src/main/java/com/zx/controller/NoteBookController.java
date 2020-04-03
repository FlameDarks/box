package com.zx.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zx.bean.Msg;
import com.zx.bean.NoteBook;
import com.zx.service.NoteBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Controller
public class NoteBookController {

    @Autowired
    NoteBookService noteBookService;

    @RequestMapping("/notebook")
    @ResponseBody
    public Msg getNoteBookWithJson(@RequestParam(value = "pn",defaultValue = "1")Integer pn){
//        每页显示条数
        PageHelper.startPage(pn,1);
//        升序排列
        PageHelper.orderBy("notebook_id asc");
//        获取数据
        List<NoteBook> noteBooks = noteBookService.getAll();
//        连续显示的页数
        PageInfo pageInfo = new PageInfo(noteBooks,5);
        return Msg.success().add("notebook_pageInfo",pageInfo);
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public Msg save(NoteBook noteBook){
        noteBookService.save(noteBook);
        return Msg.success();
    }

//    public String getNoteBook(@RequestParam(value = "pn",defaultValue = "1")Integer pn, Model model){
//        PageHelper.startPage(pn,1);
//        List<NoteBook> noteBooks = noteBookService.getAll();
//        PageInfo pageInfo = new PageInfo(noteBooks,5);
//        model.addAttribute("notebook_pageInfo",pageInfo);
//        return "notebook";
//    }
}
