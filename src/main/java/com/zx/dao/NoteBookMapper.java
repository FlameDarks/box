package com.zx.dao;

import com.zx.bean.NoteBook;
import com.zx.bean.NoteBookExample;
import com.zx.bean.NoteBookKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NoteBookMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notebook
     *
     * @mbg.generated Tue Mar 31 13:20:39 HKT 2020
     */
    long countByExample(NoteBookExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notebook
     *
     * @mbg.generated Tue Mar 31 13:20:39 HKT 2020
     */
    int deleteByExample(NoteBookExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notebook
     *
     * @mbg.generated Tue Mar 31 13:20:39 HKT 2020
     */
    int deleteByPrimaryKey(NoteBookKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notebook
     *
     * @mbg.generated Tue Mar 31 13:20:39 HKT 2020
     */
    int insert(NoteBook record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notebook
     *
     * @mbg.generated Tue Mar 31 13:20:39 HKT 2020
     */
    int insertSelective(NoteBook record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notebook
     *
     * @mbg.generated Tue Mar 31 13:20:39 HKT 2020
     */
    List<NoteBook> selectByExample(NoteBookExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notebook
     *
     * @mbg.generated Tue Mar 31 13:20:39 HKT 2020
     */
    NoteBook selectByPrimaryKey(NoteBookKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notebook
     *
     * @mbg.generated Tue Mar 31 13:20:39 HKT 2020
     */
    int updateByExampleSelective(@Param("record") NoteBook record, @Param("example") NoteBookExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notebook
     *
     * @mbg.generated Tue Mar 31 13:20:39 HKT 2020
     */
    int updateByExample(@Param("record") NoteBook record, @Param("example") NoteBookExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notebook
     *
     * @mbg.generated Tue Mar 31 13:20:39 HKT 2020
     */
    int updateByPrimaryKeySelective(NoteBook record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notebook
     *
     * @mbg.generated Tue Mar 31 13:20:39 HKT 2020
     */
    int updateByPrimaryKey(NoteBook record);
}