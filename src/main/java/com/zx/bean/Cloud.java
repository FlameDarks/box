package com.zx.bean;

import java.util.Date;

public class Cloud {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cloud.cloud_id
     *
     * @mbg.generated Fri May 08 16:04:33 HKT 2020
     */
    private Integer cloudId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cloud.cloud_name
     *
     * @mbg.generated Fri May 08 16:04:33 HKT 2020
     */
    private String cloudName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cloud.cloud_url
     *
     * @mbg.generated Fri May 08 16:04:33 HKT 2020
     */
    private String cloudUrl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cloud.cloud_md5
     *
     * @mbg.generated Fri May 08 16:04:33 HKT 2020
     */
    private String cloudMd5;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cloud.cloud_time
     *
     * @mbg.generated Fri May 08 16:04:33 HKT 2020
     */
    private Date cloudTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cloud.user_id
     *
     * @mbg.generated Fri May 08 16:04:33 HKT 2020
     */
    private Integer userId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cloud.cloud_id
     *
     * @return the value of cloud.cloud_id
     *
     * @mbg.generated Fri May 08 16:04:33 HKT 2020
     */
    public Integer getCloudId() {
        return cloudId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cloud.cloud_id
     *
     * @param cloudId the value for cloud.cloud_id
     *
     * @mbg.generated Fri May 08 16:04:33 HKT 2020
     */
    public void setCloudId(Integer cloudId) {
        this.cloudId = cloudId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cloud.cloud_name
     *
     * @return the value of cloud.cloud_name
     *
     * @mbg.generated Fri May 08 16:04:33 HKT 2020
     */
    public String getCloudName() {
        return cloudName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cloud.cloud_name
     *
     * @param cloudName the value for cloud.cloud_name
     *
     * @mbg.generated Fri May 08 16:04:33 HKT 2020
     */
    public void setCloudName(String cloudName) {
        this.cloudName = cloudName == null ? null : cloudName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cloud.cloud_url
     *
     * @return the value of cloud.cloud_url
     *
     * @mbg.generated Fri May 08 16:04:33 HKT 2020
     */
    public String getCloudUrl() {
        return cloudUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cloud.cloud_url
     *
     * @param cloudUrl the value for cloud.cloud_url
     *
     * @mbg.generated Fri May 08 16:04:33 HKT 2020
     */
    public void setCloudUrl(String cloudUrl) {
        this.cloudUrl = cloudUrl == null ? null : cloudUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cloud.cloud_md5
     *
     * @return the value of cloud.cloud_md5
     *
     * @mbg.generated Fri May 08 16:04:33 HKT 2020
     */
    public String getCloudMd5() {
        return cloudMd5;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cloud.cloud_md5
     *
     * @param cloudMd5 the value for cloud.cloud_md5
     *
     * @mbg.generated Fri May 08 16:04:33 HKT 2020
     */
    public void setCloudMd5(String cloudMd5) {
        this.cloudMd5 = cloudMd5 == null ? null : cloudMd5.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cloud.cloud_time
     *
     * @return the value of cloud.cloud_time
     *
     * @mbg.generated Fri May 08 16:04:33 HKT 2020
     */
    public Date getCloudTime() {
        return cloudTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cloud.cloud_time
     *
     * @param cloudTime the value for cloud.cloud_time
     *
     * @mbg.generated Fri May 08 16:04:33 HKT 2020
     */
    public void setCloudTime(Date cloudTime) {
        this.cloudTime = cloudTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cloud.user_id
     *
     * @return the value of cloud.user_id
     *
     * @mbg.generated Fri May 08 16:04:33 HKT 2020
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cloud.user_id
     *
     * @param userId the value for cloud.user_id
     *
     * @mbg.generated Fri May 08 16:04:33 HKT 2020
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}