package com.zx.dao;

import com.zx.bean.BookMark;
import com.zx.bean.BookMarkExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BookMarkMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bookmark
     *
     * @mbg.generated Mon Apr 06 09:27:53 HKT 2020
     */
    long countByExample(BookMarkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bookmark
     *
     * @mbg.generated Mon Apr 06 09:27:53 HKT 2020
     */
    int deleteByExample(BookMarkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bookmark
     *
     * @mbg.generated Mon Apr 06 09:27:53 HKT 2020
     */
    int deleteByPrimaryKey(Integer bookmarkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bookmark
     *
     * @mbg.generated Mon Apr 06 09:27:53 HKT 2020
     */
    int insert(BookMark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bookmark
     *
     * @mbg.generated Mon Apr 06 09:27:53 HKT 2020
     */
    int insertSelective(BookMark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bookmark
     *
     * @mbg.generated Mon Apr 06 09:27:53 HKT 2020
     */
    List<BookMark> selectByExample(BookMarkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bookmark
     *
     * @mbg.generated Mon Apr 06 09:27:53 HKT 2020
     */
    BookMark selectByPrimaryKey(Integer bookmarkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bookmark
     *
     * @mbg.generated Mon Apr 06 09:27:53 HKT 2020
     */
    int updateByExampleSelective(@Param("record") BookMark record, @Param("example") BookMarkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bookmark
     *
     * @mbg.generated Mon Apr 06 09:27:53 HKT 2020
     */
    int updateByExample(@Param("record") BookMark record, @Param("example") BookMarkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bookmark
     *
     * @mbg.generated Mon Apr 06 09:27:53 HKT 2020
     */
    int updateByPrimaryKeySelective(BookMark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bookmark
     *
     * @mbg.generated Mon Apr 06 09:27:53 HKT 2020
     */
    int updateByPrimaryKey(BookMark record);
}