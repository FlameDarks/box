<%--
  Created by IntelliJ IDEA.
  User: FlameDark
  Date: 2020/4/29
  Time: 14:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>用户管理</title>
</head>
<%
    pageContext.setAttribute("APP_PATH",request.getContextPath());
%>
<link rel="stylesheet" href="${APP_PATH}/static/css/bootstrap.css">
<link rel="stylesheet" href="${APP_PATH}/static/css/admin.css">
<link rel="stylesheet" href="${APP_PATH}/static/css/all.css">
<script type="text/javascript" src="${APP_PATH}/static/js/jquery-3.4.1.js"></script>
<script type="text/javascript" src="${APP_PATH}/static/js/bootstrap.js"></script>
<script type="text/javascript" src="${APP_PATH}/static/js/admin.js"></script>
<script type="text/javascript" src="${APP_PATH}/static/js/change.js"></script>
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
                    <li><a id="changeBtn" data-toggle="modal" data-target="#changepwd">修改密码</a></li>
                    <li><a id="exitBtn">登出</a></li>
                </ul>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-9">
            <ul class="nav nav-pills">
                <li role="presentation"><a href="${APP_PATH}/page8">地图</a></li>
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
                    <li role="presentation" class="active"><a href="${APP_PATH}/page6">用户管理</a></li>
                    <li role="presentation"><a href="${APP_PATH}/page10">图片管理</a></li>
                </shiro:hasRole>
            </ul>
        </div>
        <div>
            <div class="row">
                <select class="form-control col-md-1" id="check">
                    <option value="1">ID</option>
                    <option value="2">用户名</option>
                    <option value="3">类别</option>
                </select>
                <input class="col-md-1" placeholder="请输入" id="selectInput">
                <button class="btn btn-primary col-md-1" id="selectBtn" select="5">搜索</button>
            </div>
        </div>
    </div>
    <%--    按钮--%>
    <div class="row">
        <div class="col-md-3 col-md-offset-9">
            <button class="btn btn-primary" id="admin_add_btn" data-toggle="modal"  data-target="#admin_add">新增</button>
            <button class="btn btn-danger" id="admin_del_btn">删除</button>
        </div>
    </div>
    <%--    表格--%>
    <div class="row">
        <div class="col-md-12">
            <table class="table table-hover" id="admin_table">
                <thead>
                <tr>
                    <th class="col-md-2">
                        <input type="checkbox" id="check_all">
                    </th>
                    <th class="col-md-2">ID</th>
                    <th class="col-md-4">用户名</th>
                    <th class="col-md-2">类别</th>
                    <th class="col-md-2">操作</th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
    </div>
    <%--    页码--%>
    <div class="row">
        <div class="col-md-3 col-md-offset-3" id="admin_pageinfo"></div>
        <div class="col-md-6" id="admin_page"></div>
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
                <form class="form-horizontal">
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

<%--新增--%>
<div class="modal fade" id="admin_add" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加用户</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="admin_add_form">
                    <div class="form-group">
                        <label for="userName_add" class="col-sm-2 control-label">用户名</label>
                        <div class="col-sm-10">
                            <input type="text" name="userName" class="form-control" id="userName_add" placeholder="用户名">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="userPassword_add" class="col-sm-2 control-label">密码</label>
                        <div class="col-sm-10">
                            <input type="password" name="userPassword" class="form-control" id="userPassword_add" placeholder="密码">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="userPasswords_add" class="col-sm-2 control-label">重复密码</label>
                        <div class="col-sm-10">
                            <input type="password" name="userPasswords" class="form-control" id="userPasswords_add" placeholder="重复密码">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="userType_add" class="col-sm-2 control-label">类别</label>
                        <div class="col-sm-10" id="userType_add">
                            <label class="radio-inline">
                                <input type="radio" name="userType" id="userType_user" value="user" checked> 用户
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="userType" id="userType_admin" value="admin"> 管理员
                            </label>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="admin_save_btn">保存</button>
            </div>
        </div>
    </div>
</div>

</body>
</html>
