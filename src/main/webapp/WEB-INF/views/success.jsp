<%--
  Created by IntelliJ IDEA.
  User: FlameDark
  Date: 2020/3/28
  Time: 11:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<jsp:forward page="/notebook"/>--%>
<html>
<head>
    <title>Title</title>
</head>
<%
    pageContext.setAttribute("APP_PATH",request.getContextPath());
%>
<link rel="stylesheet" href="${APP_PATH}/static/css/bootstrap.css">
<script type="text/javascript" src="${APP_PATH}/static/js/jquery-3.4.1.js"></script>
<script src="${APP_PATH}/static/js/bootstrap.js"></script>
<script type="text/javascript" src="${APP_PATH}/static/js/notebook.js"></script>
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
        <ul class="nav nav-tabs">
            <li role="presentation" class="active"><a href="#">记事本</a></li>
            <li role="presentation"><a href="#">通讯录</a></li>
            <li role="presentation"><a href="#">收藏夹</a></li>
            <li role="presentation"><a href="#">文件箱</a></li>
            <li role="presentation"><a href="#">聊天室</a></li>
        </ul>
    </div>
    <%--    按钮--%>
    <div class="row">
        <div class="col-md-4 col-md-offset-8">
            <button class="btn btn-primary" id="notebook_add_btn" data-toggle="modal"  data-target="#notebook_add">新增</button>
            <button class="btn btn-danger" id="notebook_del_btn" data-toggle="modal" data-target="#notebook_add">删除</button>
        </div>
    </div>
    <%--    表格--%>
    <div class="row">
        <div class="col-md-12">
            <table class="table table-hover" id="notebook_table">
                <thead>
                    <tr>
                        <th class="col-md-2">ID</th>
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
        <div class="col-md-6" id="notebook_pageinfo"></div>
        <div class="col-md-6" id="notebook_page"></div>
    </div>
</div>



<%--新增--%>
<div class="modal fade" id="notebook_add" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">添加记事本</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="notebookTitle" class="col-sm-2 control-label">标题</label>
                        <div class="col-sm-10">
                            <input type="text" name="notebookTitle" class="form-control" id="notebookTitle" placeholder="标题">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="notebookContent" class="col-sm-2 control-label">内容</label>
                        <div class="col-sm-10">
                            <textarea name="notebookContent" class="form-control" rows="5" id="notebookContent" placeholder="内容"></textarea>
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
</body>
</html>
