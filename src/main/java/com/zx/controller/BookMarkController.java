package com.zx.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zx.bean.BookMark;
import com.zx.service.BookMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BookMarkController {
    @Autowired
    BookMarkService bookMarkService;
    @RequestMapping("/notebook")
    public String getChat(@RequestParam(value = "pn",defaultValue = "1")Integer pn, Model model){
        PageHelper.startPage(pn,1);
        List<BookMark> bookMarks = bookMarkService.getAll();
        PageInfo pageInfo = new PageInfo(bookMarks,5);
        model.addAttribute("pageInfo",pageInfo);
        return "notebook";
    }
}

