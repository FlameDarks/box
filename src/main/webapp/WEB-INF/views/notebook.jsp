<%--
  Created by IntelliJ IDEA.
  User: FlameDark
  Date: 2020/3/28
  Time: 11:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>记事本</title>
</head>
<%
    pageContext.setAttribute("APP_PATH",request.getContextPath());
%>
<link rel="stylesheet" href="${APP_PATH}/static/css/bootstrap.css">
<link rel="stylesheet" href="${APP_PATH}/static/css/notebook.css">
<link rel="stylesheet" href="${APP_PATH}/static/css/all.css">
<script type="text/javascript" src="${APP_PATH}/static/js/jquery-3.4.1.js"></script>
<script type="text/javascript" src="${APP_PATH}/static/js/bootstrap.js"></script>
<script type="text/javascript" src="${APP_PATH}/static/js/notebook.js"></script>
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
                    <li><a id="changeBtn" data-toggle="modal"  data-target="#changepwd">修改密码</a></li>
                    <li><a id="exitBtn">登出</a></li>
                </ul>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            <ul class="nav nav-pills">
                <li role="presentation" class="active"><a href="${APP_PATH}/page1">记事本</a></li>
                <li role="presentation"><a href="${APP_PATH}/page2">通讯录</a></li>
                <li role="presentation"><a href="${APP_PATH}/page3">收藏夹</a></li>
                <li role="presentation"><a href="${APP_PATH}/page4">文件箱</a></li>
                <li role="presentation"><a href="${APP_PATH}/page5">聊天室</a></li>
                <shiro:hasRole name="admin"><li role="presentation"><a href="${APP_PATH}/page6">用户管理</a></li></shiro:hasRole>
            </ul>
        </div>
        <div class="col-md-6">
            <div class="row">
                <select class="form-control col-md-1" id="check">
                    <option value="1">标题</option>
                    <option value="2">内容</option>
                </select>
                <input class="col-md-4" placeholder="请输入" id="selectInput">
                <button class="btn btn-primary col-md-1" id="selectBtn" select="1">搜索</button>
            </div>
        </div>
    </div>
    <%--    按钮--%>
        <div class="row">
            <div class="col-md-3 col-md-offset-9">
                <button class="btn btn-primary" id="notebook_add_btn" data-toggle="modal"  data-target="#notebook_add">新增</button>
                <button class="btn btn-danger" id="notebook_del_btn">删除</button>
            </div>
        </div>
    <%--    表格--%>
    <div class="row">
        <div class="col-md-12">
            <table class="table table-hover" id="notebook_table">
                <thead>
                    <tr>
                        <th class="col-md-2">
                            <input type="checkbox" id="check_all">
                        </th>
                        <th class="col-md-6">标题</th>
                        <th class="col-md-2">日期</th>
                        <th class="col-md-2">操作</th>
                    </tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
    </div>
    <%--    页码--%>
    <div class="row">
        <div class="col-md-3 col-md-offset-3" id="notebook_pageinfo"></div>
        <div class="col-md-6" id="notebook_page"></div>
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
<div class="modal fade" id="notebook_add" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加记事本</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="notebook_add_form">
                    <div class="form-group">
                        <label for="notebookTitle_add" class="col-sm-2 control-label">标题</label>
                        <div class="col-sm-10">
                            <input type="text" name="notebookTitle" class="form-control" id="notebookTitle_add" placeholder="标题">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="notebookContent_add" class="col-sm-2 control-label">内容</label>
                        <div class="col-sm-10">
                            <textarea name="notebookContent" class="form-control" rows="5" id="notebookContent_add" placeholder="内容"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="notebook_save_btn">保存</button>
            </div>
        </div>
    </div>
</div>

<%--修改--%>
<div class="modal fade" id="notebook_update" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">修改记事本</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="notebookTitle_update" class="col-sm-2 control-label">标题</label>
                        <div class="col-sm-10">
                            <input type="text" name="notebookTitle" class="form-control" id="notebookTitle_update" placeholder="标题">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="notebookContent_update" class="col-sm-2 control-label">内容</label>
                        <div class="col-sm-10">
                            <textarea name="notebookContent" class="form-control" rows="5" id="notebookContent_update" placeholder="内容"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="notebook_update_btn">修改</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
