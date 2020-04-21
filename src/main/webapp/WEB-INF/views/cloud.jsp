<%--
  Created by IntelliJ IDEA.
  User: FlameDark
  Date: 2020/4/7
  Time: 15:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>文件箱</title>
</head>
<%
    pageContext.setAttribute("APP_PATH",request.getContextPath());
%>
<link rel="stylesheet" href="${APP_PATH}/static/css/bootstrap3.css">
<script type="text/javascript" src="${APP_PATH}/static/js/jquery-3.4.1.js"></script>
<script src="${APP_PATH}/static/js/bootstrap3.js"></script>
<script type="text/javascript" src="${APP_PATH}/static/js/cloud.js"></script>
<body>
<input type="hidden" id="APP_PATH" value="${APP_PATH}" />
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
            <li role="presentation" class="active"><a href="${APP_PATH}/page4">文件箱</a></li>
            <li role="presentation"><a href="${APP_PATH}/page5">聊天室</a></li>
        </ul>
    </div>
    <%--    按钮--%>
    <div class="row">
        <div class="col-md-4 col-md-offset-8">
            <button class="btn btn-primary" id="cloud_add_btn" data-toggle="modal"  data-target="#cloud_add">新增</button>
<%--            <input class="btn btn-primary" id="cloud_add_btn" type="file" name="fileName"/>--%>
            <button class="btn btn-danger" id="cloud_del_btn">删除</button>
        </div>
    </div>
    <%--    表格--%>
    <div class="row">
        <div class="col-md-12">
            <table class="table table-hover" id="cloud_table">
                <thead>
                <tr>
                    <th class="col-md-2">
                        <input type="checkbox" id="check_all">
                    </th>
                    <th class="col-md-4">文件名</th>
                    <th class="col-md-4">时间</th>
                    <th class="col-md-2">操作</th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
    </div>
    <%--    页码--%>
    <div class="row">
        <div class="col-md-6" id="cloud_pageinfo"></div>
        <div class="col-md-6" id="cloud_page"></div>
    </div>
</div>



<%--新增--%>
<div class="modal fade" id="cloud_add" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加文件</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="clouds_add" class="col-sm-2 control-label">添加</label>
                        <div class="col-sm-10">
                            <input type="file" id="clouds_add" name="cloudFile">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="submit" class="btn btn-primary" id="cloud_save_btn" >保存</button>
            </div>
        </div>
    </div>
</div>

</body>
</html>
