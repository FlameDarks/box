package com.zx.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zx.bean.Contact;
import com.zx.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ContactController {
    @Autowired
    ContactService contactService;
    @RequestMapping("/contact")
    public String getContact(@RequestParam(value = "pn",defaultValue = "1")Integer pn, Model model){
        PageHelper.startPage(pn,1);
        List<Contact> contacts = contactService.getAll();
        PageInfo pageInfo = new PageInfo(contacts,5);
        model.addAttribute("contact_pageInfo",pageInfo);
        return "notebook";
    }
}

