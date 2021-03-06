package com.zx.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zx.bean.Contact;
import com.zx.bean.Msg;
import com.zx.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    ContactService contactService;

    /**
     * 获取通讯录列表
     * @param pn
     * @param id
     * @return
     */
    @RequestMapping("/selectContact")
    @ResponseBody
    public Msg getContactWithJson(@RequestParam(value = "pn",defaultValue = "1")Integer pn,@RequestParam(value = "userId")Integer id){
//        每页显示条数
        PageHelper.startPage(pn,3);
//        升序排列
        PageHelper.orderBy("contact_id asc");
//        获取数据
        List<Contact> contacts = contactService.getAll(id);
//        连续显示的页数
        PageInfo pageInfo = new PageInfo(contacts,2);
        return Msg.success().add("contact_pageInfo",pageInfo);
    }

    /**
     * 保存通讯录
     * @param contact
     * @return
     */
    @RequestMapping(value = "/saveContact",method = RequestMethod.POST)
    @ResponseBody
    public Msg save(Contact contact){
        contactService.save(contact);
        return Msg.success();
    }

    /**
     * 回显通讯录
     * @param id
     * @return
     */
    @RequestMapping(value = "/echoContact",method = RequestMethod.GET)
    @ResponseBody
    public Msg echo(@RequestParam(value = "Id")Integer id){
        Contact contact = contactService.echocontacts(id);
        return Msg.success().add("contacts",contact);
    }

    /**
     * 修改通讯录
     * @param contact
     * @return
     */
    @RequestMapping(value = "/editContact",method = RequestMethod.PUT)
    @ResponseBody
    public Msg edit(Contact contact){
        contactService.update(contact);
        return Msg.success();
    }

    /**
     * 删除通讯录
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delContact",method = RequestMethod.DELETE)
    @ResponseBody
    public Msg delete(@RequestParam(value = "Id")String ids){
        if (ids.contains("-")){
            String[] str_ids = ids.split("-");
            List<Integer> del_ids = new ArrayList<>();
            for (String string : str_ids){
                del_ids.add(Integer.parseInt(string));
            }
            contactService.deleteAll(del_ids);
            return Msg.success();
        }else {
            Integer id = Integer.parseInt(ids);
            contactService.delete(id);
            return Msg.success();
        }
    }
}

