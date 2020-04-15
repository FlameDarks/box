package com.zx.bean;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Message {
    public Message() {
    }

    public Message(Integer userId, String userName, Date sendDate, String content, String messageType, String toUser, Boolean type, List<User> userList) {
        this.userId = userId;
        this.userName = userName;
        this.sendDate = sendDate;
        this.content = content;
        this.messageType = messageType;
        this.toUser = toUser;
        this.type=type;
        this.userList = userList;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    @Expose
    private Integer userId;
    @Expose
    private String userName;  //发送者
    @Expose
    private Date sendDate;    //发送日期
    @Expose
    private String content;   //发送内容
    @Expose
    private String messageType;//发送消息类型（“text”文本，“image”图片）
    @Expose
    private String toUser;

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    @Expose
    private Boolean type;
    @Expose
    private List<User> userList = new ArrayList<>();

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", sendDate=" + sendDate +
                ", content='" + content + '\'' +
                ", messageType='" + messageType + '\'' +
                '}';
    }


    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }
}
