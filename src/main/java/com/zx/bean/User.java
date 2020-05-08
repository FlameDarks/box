package com.zx.bean;

import javax.validation.constraints.Pattern;

public class User {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.user_id
     *
     * @mbg.generated Thu Apr 30 12:51:43 HKT 2020
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.user_name
     *
     * @mbg.generated Thu Apr 30 12:51:43 HKT 2020
     */
    @Pattern(regexp = "(^[a-zA-Z0-9_-]{6,10})|(^[\\u2E80-\\u9FFF]{3,5})"
            ,message = "6-10个英文和数字组合或者3-5个汉字")
    private String userName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.user_password
     *
     * @mbg.generated Thu Apr 30 12:51:43 HKT 2020
     */
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z0-9_-]{8,16}$"
            ,message = "应包含至少一个大写字母、小写字母和数字的8-16位组合")
    private String userPassword;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.user_type
     *
     * @mbg.generated Thu Apr 30 12:51:43 HKT 2020
     */
    private String userType;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.user_id
     *
     * @return the value of user.user_id
     *
     * @mbg.generated Thu Apr 30 12:51:43 HKT 2020
     */
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z0-9_-]{8,16}$"
            ,message = "应包含至少一个大写字母、小写字母和数字的8-16位组合")
    private String userPasswords;

    public User() {
    }

    public User(Integer userId, String userName, String userPassword, String userPasswords) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userPasswords = userPasswords;
    }


    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.user_id
     *
     * @param userId the value for user.user_id
     *
     * @mbg.generated Thu Apr 30 12:51:43 HKT 2020
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.user_name
     *
     * @return the value of user.user_name
     *
     * @mbg.generated Thu Apr 30 12:51:43 HKT 2020
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.user_name
     *
     * @param userName the value for user.user_name
     *
     * @mbg.generated Thu Apr 30 12:51:43 HKT 2020
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.user_password
     *
     * @return the value of user.user_password
     *
     * @mbg.generated Thu Apr 30 12:51:43 HKT 2020
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.user_password
     *
     * @param userPassword the value for user.user_password
     *
     * @mbg.generated Thu Apr 30 12:51:43 HKT 2020
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.user_type
     *
     * @return the value of user.user_type
     *
     * @mbg.generated Thu Apr 30 12:51:43 HKT 2020
     */
    public String getUserType() {
        return userType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.user_type
     *
     * @param userType the value for user.user_type
     *
     * @mbg.generated Thu Apr 30 12:51:43 HKT 2020
     */
    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    public String getUserPasswords() {
        return userPasswords;
    }

    public void setUserPasswords(String userPasswords) {
        this.userPasswords = userPasswords;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userType='" + userType + '\'' +
                ", userPasswords='" + userPasswords + '\'' +
                '}';
    }
}