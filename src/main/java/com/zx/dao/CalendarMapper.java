package com.zx.dao;

import com.zx.bean.Calendar;
import com.zx.bean.CalendarExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CalendarMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table calendar
     *
     * @mbg.generated Fri May 29 14:34:05 HKT 2020
     */
    long countByExample(CalendarExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table calendar
     *
     * @mbg.generated Fri May 29 14:34:05 HKT 2020
     */
    int deleteByExample(CalendarExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table calendar
     *
     * @mbg.generated Fri May 29 14:34:05 HKT 2020
     */
    int deleteByPrimaryKey(Integer calendarId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table calendar
     *
     * @mbg.generated Fri May 29 14:34:05 HKT 2020
     */
    int insert(Calendar record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table calendar
     *
     * @mbg.generated Fri May 29 14:34:05 HKT 2020
     */
    int insertSelective(Calendar record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table calendar
     *
     * @mbg.generated Fri May 29 14:34:05 HKT 2020
     */
    List<Calendar> selectByExample(CalendarExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table calendar
     *
     * @mbg.generated Fri May 29 14:34:05 HKT 2020
     */
    Calendar selectByPrimaryKey(Integer calendarId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table calendar
     *
     * @mbg.generated Fri May 29 14:34:05 HKT 2020
     */
    int updateByExampleSelective(@Param("record") Calendar record, @Param("example") CalendarExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table calendar
     *
     * @mbg.generated Fri May 29 14:34:05 HKT 2020
     */
    int updateByExample(@Param("record") Calendar record, @Param("example") CalendarExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table calendar
     *
     * @mbg.generated Fri May 29 14:34:05 HKT 2020
     */
    int updateByPrimaryKeySelective(Calendar record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table calendar
     *
     * @mbg.generated Fri May 29 14:34:05 HKT 2020
     */
    int updateByPrimaryKey(Calendar record);
}