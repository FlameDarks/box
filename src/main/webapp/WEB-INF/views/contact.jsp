<%--
  Created by IntelliJ IDEA.
  User: FlameDark
  Date: 2020/3/30
  Time: 17:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>通讯录</title>
</head>
<%
    pageContext.setAttribute("APP_PATH",request.getContextPath());
%>
<link rel="stylesheet" href="${APP_PATH}/static/css/bootstrap3.css">
<link rel="stylesheet" href="${APP_PATH}/static/css/contact.css">
<script type="text/javascript" src="${APP_PATH}/static/js/jquery-3.4.1.js"></script>
<script type="text/javascript" src="${APP_PATH}/static/js/bootstrap3.js"></script>
<script type="text/javascript" src="${APP_PATH}/static/js/contact.js"></script>
<body>
<input type="hidden" id="APP_PATH" value="${APP_PATH}" />
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
                        <li><a id="changeBtn">修改密码</a></li>
                        <li><a id="exitBtn">登出</a></li>
                    </ul>
                </div>
            </div>
        </div>
    <div>
        <ul class="nav nav-pills">
            <li role="presentation"><a href="${APP_PATH}/page1">记事本</a></li>
            <li role="presentation" class="active"><a href="${APP_PATH}/page2">通讯录</a></li>
            <li role="presentation"><a href="${APP_PATH}/page3">收藏夹</a></li>
            <li role="presentation"><a href="${APP_PATH}/page4">文件箱</a></li>
            <li role="presentation"><a href="${APP_PATH}/page5">聊天室</a></li>
        </ul>
    </div>
    <%--    按钮--%>
    <div class="row">
        <div class="col-md-4 col-md-offset-8">
            <button class="btn btn-primary" id="contact_add_btn" data-toggle="modal"  data-target="#contact_add">新增</button>
            <button class="btn btn-danger" id="contact_del_btn">删除</button>
        </div>
    </div>
    <%--    表格--%>
    <div class="row">
        <div class="col-md-12">
            <table class="table table-hover" id="contact_table">
                <thead>
                <tr>
                    <th class="col-md-2">
                        <input type="checkbox" id="check_all">
                    </th>
                    <th class="col-md-2">名字</th>
                    <th class="col-md-3">电话</th>
                    <th class="col-md-3">住址</th>
                    <th class="col-md-2">操作</th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
    </div>
    <%--    页码--%>
    <div class="row">
        <div class="col-md-6" id="contact_pageinfo"></div>
        <div class="col-md-6" id="contact_page"></div>
    </div>
</div>



<%--新增--%>
<div class="modal fade" id="contact_add" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加通讯录</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="contactName_add" class="col-sm-2 control-label">姓名</label>
                        <div class="col-sm-10">
                            <input type="text" name="contactName" class="form-control" id="contactName_add" placeholder="姓名">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="contactPhone_add" class="col-sm-2 control-label">电话</label>
                        <div class="col-sm-10">
                            <input type="text" name="contactPhone" class="form-control" id="contactPhone_add" placeholder="电话">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="contactAddress_add" class="col-sm-2 control-label">住址</label>
                        <div class="col-sm-10">
                            <textarea name="contactAddress" class="form-control" rows="5" id="contactAddress_add" placeholder="住址"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="contact_save_btn">保存</button>
            </div>
        </div>
    </div>
</div>

<%--修改--%>
<div class="modal fade" id="contact_update" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">修改通讯录</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="contactName_update" class="col-sm-2 control-label">姓名</label>
                        <div class="col-sm-10">
                            <input type="text" name="contactName" class="form-control" id="contactName_update" placeholder="姓名">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="contactPhone_update" class="col-sm-2 control-label">电话</label>
                        <div class="col-sm-10">
                            <input type="text" name="contactPhone" class="form-control" id="contactPhone_update" placeholder="电话">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="contactAddress_update" class="col-sm-2 control-label">住址</label>
                        <div class="col-sm-10">
                            <textarea name="contactAddress" class="form-control" rows="5" id="contactAddress_update" placeholder="住址"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="contact_update_btn">修改</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
