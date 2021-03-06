<%--
  Created by IntelliJ IDEA.
  User: FlameDark
  Date: 2020/5/29
  Time: 15:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<%
    pageContext.setAttribute("APP_PATH",request.getContextPath());
%>
<head>
    <title>地图</title>
</head>
<link rel="stylesheet" href="${APP_PATH}/static/css/bootstrap.css">
<link rel="stylesheet" href="${APP_PATH}/static/css/all.css">
<script type="text/javascript" src="${APP_PATH}/static/js/jquery-3.4.1.js"></script>
<script type="text/javascript" src="${APP_PATH}/static/js/bootstrap.js"></script>
<script type="text/javascript" src="${APP_PATH}/static/js/change.js"></script>
<script type="text/javascript" src="${APP_PATH}/static/js/map.js"></script>

<body background="${APP_PATH}/static/images/image/bg.jpg">
<input class="bg" type="hidden" id="APP_PATH" value="${APP_PATH}" />
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
    <div class="row">
        <div class="col-md-9">
            <ul class="nav nav-pills">
                <li role="presentation" class="active"><a href="${APP_PATH}/page8">地图</a></li>
                <li role="presentation"><a href="${APP_PATH}/page1">记事本</a></li>
                <li role="presentation"><a href="${APP_PATH}/page2">通讯录</a></li>
                <li role="presentation"><a href="${APP_PATH}/page3">收藏夹</a></li>
                <li role="presentation"><a href="${APP_PATH}/page7">日程表</a></li>
                <li role="presentation"><a href="${APP_PATH}/page4">文件箱</a></li>
                <li role="presentation"><a href="${APP_PATH}/page5">聊天室</a></li>
                <li role="presentation" class="dropdown">
                    <a class="dropdown-toggle active" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">
                        图片库 <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li role="presentation"><a href="${APP_PATH}/page9">所有图片</a></li>
                        <li role="presentation"><a href="${APP_PATH}/page11">热门图片</a></li>
                        <li role="presentation"><a href="${APP_PATH}/page12">我的收藏</a></li>
                    </ul>
                </li>
                <shiro:hasRole name="admin">
                    <li role="presentation"><a href="${APP_PATH}/page6">用户管理</a></li>
                    <li role="presentation"><a href="${APP_PATH}/page10">图片管理</a></li>
                </shiro:hasRole>
            </ul>
        </div>
        <div class="row">
            <select class="form-control col-md-1" id="check">
                <option value="1">城市</option>
                <option value="2">周边</option>
            </select>
            <input class="col-md-1" placeholder="请输入" id="selectInput">
            <button class="btn btn-primary col-md-1" id="selectBtn" select="1" onclick="select()">搜索</button>
        </div>
    </div>
    <div class="col-sm-6 thumbnail" style="height: 450px;width: 48%;margin-right: 0.5cm">
        <div class="row">
            <table class="table" style="margin-left: 0.1cm;">
                <tbody id="weather"></tbody>
            </table>
        </div>
    </div>

    <div id="maps" style="height: 450px;width: 48%;margin-right: 10px"></div>
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
                        <label for="newpwds" class="col-sm-2 control-label">重复密码</label>
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
