package com.zx.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zx.bean.NoteBook;
import com.zx.service.NoteBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class NoteBookController {


    @Autowired
    NoteBookService noteBookService;
    @RequestMapping("/notebook")
    public String getNoteBook(@RequestParam(value = "pn",defaultValue = "1")Integer pn, Model model){
        PageHelper.startPage(pn,1);
        List<NoteBook> noteBooks = noteBookService.getAll();
        PageInfo pageInfo = new PageInfo(noteBooks,5);
        model.addAttribute("notebook_pageInfo",pageInfo);
        return "notebook";
    }
}
