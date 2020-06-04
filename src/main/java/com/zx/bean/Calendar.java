package com.zx.bean;

public class Calendar {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column calendar.calendar_id
     *
     * @mbg.generated Fri May 29 14:34:05 HKT 2020
     */
    private Integer calendarId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column calendar.calendar_title
     *
     * @mbg.generated Fri May 29 14:34:05 HKT 2020
     */
    private String calendarTitle;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column calendar.calendar_content
     *
     * @mbg.generated Fri May 29 14:34:05 HKT 2020
     */
    private String calendarContent;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column calendar.calendar_time
     *
     * @mbg.generated Fri May 29 14:34:05 HKT 2020
     */
    private String calendarTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column calendar.user_id
     *
     * @mbg.generated Fri May 29 14:34:05 HKT 2020
     */
    private Integer userId;

    public Calendar() {
    }

    public Calendar(Integer calendarId, String calendarTitle, String calendarContent, String calendarTime, Integer userId) {
        this.calendarId = calendarId;
        this.calendarTitle = calendarTitle;
        this.calendarContent = calendarContent;
        this.calendarTime = calendarTime;
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column calendar.calendar_id
     *
     * @return the value of calendar.calendar_id
     *
     * @mbg.generated Fri May 29 14:34:05 HKT 2020
     */
    public Integer getCalendarId() {
        return calendarId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column calendar.calendar_id
     *
     * @param calendarId the value for calendar.calendar_id
     *
     * @mbg.generated Fri May 29 14:34:05 HKT 2020
     */
    public void setCalendarId(Integer calendarId) {
        this.calendarId = calendarId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column calendar.calendar_title
     *
     * @return the value of calendar.calendar_title
     *
     * @mbg.generated Fri May 29 14:34:05 HKT 2020
     */
    public String getCalendarTitle() {
        return calendarTitle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column calendar.calendar_title
     *
     * @param calendarTitle the value for calendar.calendar_title
     *
     * @mbg.generated Fri May 29 14:34:05 HKT 2020
     */
    public void setCalendarTitle(String calendarTitle) {
        this.calendarTitle = calendarTitle == null ? null : calendarTitle.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column calendar.calendar_content
     *
     * @return the value of calendar.calendar_content
     *
     * @mbg.generated Fri May 29 14:34:05 HKT 2020
     */
    public String getCalendarContent() {
        return calendarContent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column calendar.calendar_content
     *
     * @param calendarContent the value for calendar.calendar_content
     *
     * @mbg.generated Fri May 29 14:34:05 HKT 2020
     */
    public void setCalendarContent(String calendarContent) {
        this.calendarContent = calendarContent == null ? null : calendarContent.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column calendar.calendar_time
     *
     * @return the value of calendar.calendar_time
     *
     * @mbg.generated Fri May 29 14:34:05 HKT 2020
     */
    public String getCalendarTime() {
        return calendarTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column calendar.calendar_time
     *
     * @param calendarTime the value for calendar.calendar_time
     *
     * @mbg.generated Fri May 29 14:34:05 HKT 2020
     */
    public void setCalendarTime(String calendarTime) {
        this.calendarTime = calendarTime == null ? null : calendarTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column calendar.user_id
     *
     * @return the value of calendar.user_id
     *
     * @mbg.generated Fri May 29 14:34:05 HKT 2020
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column calendar.user_id
     *
     * @param userId the value for calendar.user_id
     *
     * @mbg.generated Fri May 29 14:34:05 HKT 2020
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}