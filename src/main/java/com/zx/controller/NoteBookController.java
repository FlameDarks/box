package com.zx.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zx.bean.Msg;
import com.zx.bean.NoteBook;
import com.zx.service.NoteBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
//        获取数据
        List<NoteBook> noteBooks = noteBookService.getAll();
//        连续显示的页数
        PageInfo pageInfo = new PageInfo(noteBooks,5);
        return Msg.success().add("notebook_pageInfo",pageInfo);
    }

//    public String getNoteBook(@RequestParam(value = "pn",defaultValue = "1")Integer pn, Model model){
//        PageHelper.startPage(pn,1);
//        List<NoteBook> noteBooks = noteBookService.getAll();
//        PageInfo pageInfo = new PageInfo(noteBooks,5);
//        model.addAttribute("notebook_pageInfo",pageInfo);
//        return "notebook";
//    }
}
