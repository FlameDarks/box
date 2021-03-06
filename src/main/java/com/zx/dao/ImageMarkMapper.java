package com.zx.dao;

import com.zx.bean.ImageMark;
import com.zx.bean.ImageMarkExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImageMarkMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table imagemark
     *
     * @mbg.generated Mon Jun 01 14:08:27 HKT 2020
     */
    long countByExample(ImageMarkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table imagemark
     *
     * @mbg.generated Mon Jun 01 14:08:27 HKT 2020
     */
    int deleteByExample(ImageMarkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table imagemark
     *
     * @mbg.generated Mon Jun 01 14:08:27 HKT 2020
     */
    int deleteByPrimaryKey(Integer imagemarkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table imagemark
     *
     * @mbg.generated Mon Jun 01 14:08:27 HKT 2020
     */
    int insert(ImageMark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table imagemark
     *
     * @mbg.generated Mon Jun 01 14:08:27 HKT 2020
     */
    int insertSelective(ImageMark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table imagemark
     *
     * @mbg.generated Mon Jun 01 14:08:27 HKT 2020
     */
    List<ImageMark> selectByExample(ImageMarkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table imagemark
     *
     * @mbg.generated Mon Jun 01 14:08:27 HKT 2020
     */
    ImageMark selectByPrimaryKey(Integer imagemarkId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table imagemark
     *
     * @mbg.generated Mon Jun 01 14:08:27 HKT 2020
     */
    int updateByExampleSelective(@Param("record") ImageMark record, @Param("example") ImageMarkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table imagemark
     *
     * @mbg.generated Mon Jun 01 14:08:27 HKT 2020
     */
    int updateByExample(@Param("record") ImageMark record, @Param("example") ImageMarkExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table imagemark
     *
     * @mbg.generated Mon Jun 01 14:08:27 HKT 2020
     */
    int updateByPrimaryKeySelective(ImageMark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table imagemark
     *
     * @mbg.generated Mon Jun 01 14:08:27 HKT 2020
     */
    int updateByPrimaryKey(ImageMark record);
}