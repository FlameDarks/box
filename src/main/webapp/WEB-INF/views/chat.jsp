<%--
  Created by IntelliJ IDEA.
  User: FlameDark
  Date: 2020/3/30
  Time: 10:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>聊天室</title>
</head>
<%
    pageContext.setAttribute("APP_PATH",request.getContextPath());
%>
<link rel="stylesheet" href="${APP_PATH}/static/css/bootstrap3.css">
<link rel="stylesheet" href="${APP_PATH}/static/css/chat.css">
<link rel="stylesheet" href="${APP_PATH}/static/css/all.css">
<script type="text/javascript" src="${APP_PATH}/static/js/jquery-3.4.1.js"></script>
<script type="text/javascript" src="${APP_PATH}/static/js/bootstrap3.js"></script>
<script type="text/javascript" src="${APP_PATH}/static/js/stomp.js"></script>
<script type="text/javascript" src="${APP_PATH}/static/js/sockjs.js"></script>
<script type="text/javascript" src="${APP_PATH}/static/js/chat.js"></script>
<script type="text/javascript" src="${APP_PATH}/static/js/change.js"></script>
<body background="${APP_PATH}/static/images/image/bg.jpg">
<input type="hidden" id="APP_PATH" value="${APP_PATH}" />
<input type="hidden" id="loginUserName" value="${loginUser.userName}"/>
<div class="container">
<%--    标题--%>
    <div class="row">
        <div class="col-md-11">
            <h1 align="center">企业网络百宝箱</h1>
        </div>
        <div class="userInfo">
            <div class="dropdown">
                <button class="btn btn-default dropdown-toggle userBtn" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                    ${loginUser.userName}
                </button>
                <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenu1">
                    <li><a id="changeBtn" data-toggle="modal"  data-target="#changepwd">修改密码</a></li>
                    <li><a id="exitBtn">登出</a></li>
                </ul>
            </div>
        </div>
    </div>
    <div>
        <ul class="nav nav-pills">
            <li role="presentation"><a href="${APP_PATH}/page1">记事本</a></li>
            <li role="presentation"><a href="${APP_PATH}/page2">通讯录</a></li>
            <li role="presentation"><a href="${APP_PATH}/page3">收藏夹</a></li>
            <li role="presentation"><a href="${APP_PATH}/page4">文件箱</a></li>
            <li role="presentation" class="active"><a href="${APP_PATH}/page5">聊天室</a></li>
            <shiro:hasRole name="admin"><li role="presentation"><a href="${APP_PATH}/page6">用户管理</a></li></shiro:hasRole>
        </ul>
    </div>
</div>
<div class="chatroom">
<div class="userList">
    <div class="list-group" id="LoginUserList">
<%--        <a href="#" class="list-group-item active">--%>
<%--        <a href="#" class="list-group-item" id="LoginUser">在线成员</a>--%>
    </div>
</div>
<div class="wrapper">
    <div class="banner">
        <div id="activeUserWraper">
            <span class="label label-info" id="status"></span><span class="label">位用户当前在线</span>
        </div>
    </div>
    <div id="historyMsg">
    </div>
    <div class="controls">
        <div class="items">
<%--            <input id="uploadUrl" type="hidden" value="<c:url value="/upload/image"/> "/>--%>
            <input id="websocketUrl" type="hidden" value="<c:url value="/websocket"/> "/>
            <form id="sendImageForm" enctype="multipart/form-data" method="post">
                <input id="emoji" class="btn btn-primary" type="button" value="emoji" title="emoji"/>
                <label for="sendImage" class="imageLable">
                    <input id="sendImageBtn" class="btn btn-success" type="button" value="发送图片"/>
                    <input id="sendImage" type="file" value="发送图片" name="image"
                           accept="image/jpg,image/jpeg,image/png,image/gif"/>
                </label>
                <input id="clearBtn" class="btn btn-warning" type="button" value="清屏" title="清除屏幕消息"/>
                <input type="hidden" id="loginUserId" name="userId" value="${loginUser.userId}"/>
            </form>
        </div>
        <textarea class="form-control" id="messageInput" placeHolder="回车键发送"></textarea>
        <div id="emojiWrapper">
        </div>
    </div>
</div>
</div>

<%--修改密码--%>
<div class="modal fade" id="changepwd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">修改密码</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="change_pwd_form">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">用户名</label>
                        <div class="col-sm-10">
                            <p class="form-control-static">${loginUser.userName}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="oldpwd" class="col-sm-2 control-label">原密码</label>
                        <div class="col-sm-10">
                            <input type="password" name="userPassword" class="form-control" id="oldpwd" placeholder="原密码">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="newpwd" class="col-sm-2 control-label">新密码</label>
                        <div class="col-sm-10">
                            <input type="password" name="userPassword" class="form-control" id="newpwd" placeholder="新密码">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="newpwds" class="col-sm-2 control-label">重复新密码</label>
                        <div class="col-sm-10">
                            <input type="password" name="userPasswords" class="form-control" id="newpwds" placeholder="重复新密码">
                            <span class="help-block"></span>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="changepwd_save_btn">保存</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
