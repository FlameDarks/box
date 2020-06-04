package com.zx.service;

import com.zx.bean.Calendar;
import com.zx.bean.CalendarExample;
import com.zx.dao.CalendarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalendarService {

    @Autowired
    CalendarMapper calendarMapper;

    /**
     * 获取用户ID下所有日程表信息
     * @param userId
     * @return
     */
    public List<Calendar> getAll(Integer userId) {
        CalendarExample calendarExample = new CalendarExample();
        CalendarExample.Criteria criteria = calendarExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        return calendarMapper.selectByExample(calendarExample);
    }

    /**
     * 插入日程表
     * @param calendar
     */
    public void save(Calendar calendar) {
        calendarMapper.insertSelective(calendar);
    }

    /**
     * 通过Id获取日程表信息
     * @param id
     * @return
     */
    public Calendar echoNote(Integer id) {
        return calendarMapper.selectByPrimaryKey(id);
    }

    /**
     * 修改日程表
     * @param calendar
     */
    public void update(Calendar calendar) {
        calendarMapper.updateByPrimaryKeySelective(calendar);
    }

    /**
     * 删除日程表
     * @param id
     */
    public void delete(Integer id) {
        calendarMapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除日程表
     * @param ids
     */
    public void deleteAll(List<Integer> ids) {
        CalendarExample calendarExample = new CalendarExample();
        CalendarExample.Criteria criteria = calendarExample.createCriteria();
        criteria.andCalendarIdIn(ids);
        calendarMapper.deleteByExample(calendarExample);
    }

    /**
     * 查询日程表
     * @param userId
     * @param data
     * @param check
     * @return
     */
    public List<Calendar> select(Integer userId, String data,Integer check){
        CalendarExample calendarExample = new CalendarExample();
        CalendarExample.Criteria criteria = calendarExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        if (check==1){
            criteria.andCalendarTitleLike(data);
        }else{
            criteria.andCalendarContentLike(data);
        }
        return calendarMapper.selectByExample(calendarExample);
    }
}
