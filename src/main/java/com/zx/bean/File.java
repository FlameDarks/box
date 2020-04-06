package com.zx.bean;

import java.util.Date;

public class File {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file.file_id
     *
     * @mbg.generated Mon Apr 06 09:27:53 HKT 2020
     */
    private Integer fileId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file.file_name
     *
     * @mbg.generated Mon Apr 06 09:27:53 HKT 2020
     */
    private String fileName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file.file_time
     *
     * @mbg.generated Mon Apr 06 09:27:53 HKT 2020
     */
    private Date fileTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column file.user_id
     *
     * @mbg.generated Mon Apr 06 09:27:53 HKT 2020
     */
    private Integer userId;

    public File() {
    }

    public File(Integer fileId, String fileName, Date fileTime, Integer userId) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileTime = fileTime;
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file.file_id
     *
     * @return the value of file.file_id
     *
     * @mbg.generated Mon Apr 06 09:27:53 HKT 2020
     */
    public Integer getFileId() {
        return fileId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file.file_id
     *
     * @param fileId the value for file.file_id
     *
     * @mbg.generated Mon Apr 06 09:27:53 HKT 2020
     */
    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file.file_name
     *
     * @return the value of file.file_name
     *
     * @mbg.generated Mon Apr 06 09:27:53 HKT 2020
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file.file_name
     *
     * @param fileName the value for file.file_name
     *
     * @mbg.generated Mon Apr 06 09:27:53 HKT 2020
     */
    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file.file_time
     *
     * @return the value of file.file_time
     *
     * @mbg.generated Mon Apr 06 09:27:53 HKT 2020
     */
    public Date getFileTime() {
        return fileTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file.file_time
     *
     * @param fileTime the value for file.file_time
     *
     * @mbg.generated Mon Apr 06 09:27:53 HKT 2020
     */
    public void setFileTime(Date fileTime) {
        this.fileTime = fileTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column file.user_id
     *
     * @return the value of file.user_id
     *
     * @mbg.generated Mon Apr 06 09:27:53 HKT 2020
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column file.user_id
     *
     * @param userId the value for file.user_id
     *
     * @mbg.generated Mon Apr 06 09:27:53 HKT 2020
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}