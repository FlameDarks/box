package com.zx.bean;

public class NoteBookKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notebook.notebook_id
     *
     * @mbg.generated Tue Mar 31 13:20:39 HKT 2020
     */
    private Integer notebookId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notebook.user_id
     *
     * @mbg.generated Tue Mar 31 13:20:39 HKT 2020
     */
    private Integer userId;

    public NoteBookKey(Integer notebookId, Integer userId) {
        this.notebookId = notebookId;
        this.userId = userId;
    }

    public NoteBookKey() {
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notebook.notebook_id
     *
     * @return the value of notebook.notebook_id
     *
     * @mbg.generated Tue Mar 31 13:20:39 HKT 2020
     */
    public Integer getNotebookId() {
        return notebookId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notebook.notebook_id
     *
     * @param notebookId the value for notebook.notebook_id
     *
     * @mbg.generated Tue Mar 31 13:20:39 HKT 2020
     */
    public void setNotebookId(Integer notebookId) {
        this.notebookId = notebookId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notebook.user_id
     *
     * @return the value of notebook.user_id
     *
     * @mbg.generated Tue Mar 31 13:20:39 HKT 2020
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notebook.user_id
     *
     * @param userId the value for notebook.user_id
     *
     * @mbg.generated Tue Mar 31 13:20:39 HKT 2020
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}