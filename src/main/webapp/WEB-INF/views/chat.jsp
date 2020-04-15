<%--
  Created by IntelliJ IDEA.
  User: FlameDark
  Date: 2020/3/30
  Time: 10:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>聊天室</title>
</head>
<%
    pageContext.setAttribute("APP_PATH",request.getContextPath());
%>
<link rel="stylesheet" href="${APP_PATH}/static/css/bootstrap.min.css">
<script type="text/javascript" src="${APP_PATH}/static/js/jquery-3.4.1.js"></script>
<script src="${APP_PATH}/static/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/static/js/stomp.js"></script>
<%--<script src="https://cdn.bootcss.com/stomp.js/2.3.3/stomp.min.js"></script>--%>
<script src="${APP_PATH}/static/js/sockjs.js"></script>
<script type="text/javascript" src="${APP_PATH}/static/js/chat.js"></script>
<style rel="stylesheet">
    html, body {
        margin: 0;
        font-family: sans-serif;
    }

    .wrapper {
        width: 600px;
        height: 450px;
        padding: 5px;
        margin: 0 auto;
        background-color: #ddd;
    }

    #activeUserWraper {
        width: 100%;
        height: 30px;
        margin-top: 5px;
    }

    #activeUserWraper span {
        font-size: large;
    }

    .banner {
        height: 40px;
        width: 100%;
    }
    .banner span {
        float: left;
        display: inline-block;
    }
    .controls {
        height: 150px;
        margin: 5px 0px;
        position: relative;
    }


    #historyMsg {
        height: 250px;
        background-color: #fff;
        overflow: auto;
        padding: 2px;
    }
    #historyMsg p {
        font-size: 20px;
    }
    #historyMsg img {
        max-width: 90%;

    }
    .timespan {
        color: #ddd;
        font-size: 16px;
    }
    .items {
        height: 30px;
        margin-bottom: 8px;
    }
    /*custom the file input*/

    .imageLable {
        position: relative;
    }
    #sendImage {
        position: absolute;
        width: 1px;
        height: 1px;
        left: 0;
        opacity: 0;
        overflow: hidden;
    }
    /*end custom file input*/

    #messageInput {
        height: 100px;
        max-height: 170px;
    }

    #emojiWrapper {
        display: none;
        width: 500px;
        bottom: 105px;
        position: absolute;
        background-color: #aaa;
        box-shadow: 0 0 10px #555;
    }
    #emojiWrapper img {
        margin: 2px;
        padding: 2px;
        width: 25px;
        height: 25px;
    }
    #emojiWrapper img:hover {
        background-color: blue;
    }

    .emoji{
        display: inline;
    }
    footer {
        text-align: center;
    }
</style>
<body>
<input type="hidden" id="APP_PATH" value="${APP_PATH}" />
<input type="hidden" id="loginUserName" value="${loginUser.userName}"/>
<input type="hidden" id="loginUserId" value="${loginUser.userId}"/>
<div class="container">
<%--    标题--%>
    <div class="row">
        <div class="col-md-12">
            <h1 align="center">企业网络百宝箱</h1>
        </div>
    </div>
    <div>
        <ul class="nav nav-pills">
            <li role="presentation"><a href="${APP_PATH}/page1">记事本</a></li>
            <li role="presentation"><a href="${APP_PATH}/page2">通讯录</a></li>
            <li role="presentation"><a href="${APP_PATH}/page3">收藏夹</a></li>
            <li role="presentation"><a href="${APP_PATH}/page4">文件箱</a></li>
            <li role="presentation" class="active"><a href="${APP_PATH}/page5">聊天室</a></li>
        </ul>
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
            <input id="uploadUrl" type="hidden" value="<c:url value="/upload/image"/> "/>
            <input id="websocketUrl" type="hidden" value="<c:url value="/websocket"/> "/>
            <input id="emojiBaseUri" type="hidden"/>
            <form id="sendImageForm" enctype="multipart/form-data" method="post">
                <input id="emoji" class="btn btn-primary" type="button" value="emoji" title="emoji"/>
                <label for="sendImage" class="imageLable">
                    <input id="sendImageBtn" class="btn btn-success" type="button" value="发送图片"/>
                    <input id="sendImage" type="file" value="发送图片" name="image"
                           accept="image/jpg,image/jpeg,image/png,image/gif"/>
                </label>
                <input id="clearBtn" class="btn btn-warning" type="button" value="清屏" title="清除屏幕消息"/>
            </form>
        </div>
        <textarea class="form-control" id="messageInput" placeHolder="回车键发送"></textarea>
        <div id="emojiWrapper">
        </div>
    </div>
</div>

</body>
</html>
