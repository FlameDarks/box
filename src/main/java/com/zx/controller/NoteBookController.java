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

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/notebook")
public class NoteBookController {

    @Autowired
    NoteBookService noteBookService;

    /**
     * 显示记事本列表
     * @param pn
     * @param id
     * @return
     */
    @RequestMapping("/selectNotebook")
    @ResponseBody
    public Msg getNoteBookWithJson(@RequestParam(value = "pn",defaultValue = "1")Integer pn,@RequestParam(value = "userId")Integer id){
//        每页显示条数
        PageHelper.startPage(pn,3);
//        升序排列
        PageHelper.orderBy("notebook_id asc");
//        获取数据
        List<NoteBook> noteBooks = noteBookService.getAll(id);
//        连续显示的页数
        PageInfo pageInfo = new PageInfo(noteBooks,3);
        return Msg.success().add("notebook_pageInfo",pageInfo);
    }

    /**
     * 保存记事本
     * @param noteBook
     * @return
     */
    @RequestMapping(value = "/saveNoteBook",method = RequestMethod.POST)
    @ResponseBody
    public Msg save(NoteBook noteBook){
        noteBookService.save(noteBook);
        return Msg.success();
    }

    /**
     * 回显记事本
     * @param id
     * @return
     */
    @RequestMapping(value = "/echoNoteBook",method = RequestMethod.GET)
    @ResponseBody
    public Msg echo(@RequestParam(value = "Id")Integer id){
        NoteBook noteBook = noteBookService.echoNote(id);
        return Msg.success().add("note",noteBook);
    }

    /**
     * 修改记事本
     * @param noteBook
     * @return
     */
    @RequestMapping(value = "/editNoteBook",method = RequestMethod.PUT)
    @ResponseBody
    public Msg edit(NoteBook noteBook){
        noteBookService.update(noteBook);
        return Msg.success();
    }

    /**
     * 删除记事本
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delNoteBook",method = RequestMethod.DELETE)
    @ResponseBody
    public Msg delete(@RequestParam(value = "Id")String ids){
        if (ids.contains("-")){
            String[] str_ids = ids.split("-");
            List<Integer> del_ids = new ArrayList<>();
            for (String string : str_ids){
                del_ids.add(Integer.parseInt(string));
            }
            noteBookService.deleteAll(del_ids);
            return Msg.success();
        }else {
            Integer id = Integer.parseInt(ids);
            noteBookService.delete(id);
            return Msg.success();
        }
    }





//    public String getNoteBook(@RequestParam(value = "pn",defaultValue = "1")Integer pn, Model model){
//        PageHelper.startPage(pn,1);
//        List<NoteBook> noteBooks = noteBookService.getAll();
//        PageInfo pageInfo = new PageInfo(noteBooks,5);
//        model.addAttribute("notebook_pageInfo",pageInfo);
//        return "notebook";
//    }
}
