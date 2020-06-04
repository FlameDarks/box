package com.zx.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zx.bean.Calendar;
import com.zx.bean.Msg;
import com.zx.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/calendar")
public class CalendarController {
    @Autowired
    CalendarService calendarService;

    /**
     * 显示日程表列表
     * @param pn
     * @param id
     * @return
     */
    @RequestMapping("/selectCalendar")
    @ResponseBody
    public Msg getCalendarWithJson(@RequestParam(value = "pn",defaultValue = "1")Integer pn, @RequestParam(value = "userId")Integer id){
//        每页显示条数
        PageHelper.startPage(pn,3);
//        升序排列
        PageHelper.orderBy("calendar_id asc");
//        获取数据
        List<Calendar> calendars = calendarService.getAll(id);
//        连续显示的页数
        PageInfo pageInfo = new PageInfo(calendars,3);
        return Msg.success().add("calendar_pageInfo",pageInfo);
    }

    /**
     * 保存日程表
     * @param calendar
     * @return
     */
    @RequestMapping(value = "/saveCalendar",method = RequestMethod.POST)
    @ResponseBody
    public Msg save(Calendar calendar){
        calendarService.save(calendar);
        return Msg.success();
    }

    /**
     * 回显日程表
     * @param id
     * @return
     */
    @RequestMapping(value = "/echoCalendar",method = RequestMethod.GET)
    @ResponseBody
    public Msg echo(@RequestParam(value = "Id")Integer id){
        Calendar calendar = calendarService.echoNote(id);
        return Msg.success().add("note",calendar);
    }

    /**
     * 修改日程表
     * @param calendar
     * @return
     */
    @RequestMapping(value = "/editCalendar",method = RequestMethod.PUT)
    @ResponseBody
    public Msg edit(Calendar calendar){
        calendarService.update(calendar);
        return Msg.success();
    }

    /**
     * 删除日程表
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delCalendar",method = RequestMethod.DELETE)
    @ResponseBody
    public Msg delete(@RequestParam(value = "Id")String ids){
        if (ids.contains("-")){
            String[] str_ids = ids.split("-");
            List<Integer> del_ids = new ArrayList<>();
            for (String string : str_ids){
                del_ids.add(Integer.parseInt(string));
            }
            calendarService.deleteAll(del_ids);
            return Msg.success();
        }else {
            Integer id = Integer.parseInt(ids);
            calendarService.delete(id);
            return Msg.success();
        }
    }
}
