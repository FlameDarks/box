<%--
  Created by IntelliJ IDEA.
  User: FlameDark
  Date: 2020/3/28
  Time: 11:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<jsp:forward page="/notebook"/>--%>
<html>
<head>
    <title>记事本</title>
</head>
<%
    pageContext.setAttribute("APP_PATH",request.getContextPath());
%>
<link rel="stylesheet" href="${APP_PATH}/static/css/bootstrap.css">
<script type="text/javascript" src="${APP_PATH}/static/js/jquery-3.4.1.js" charset="utf-8"></script>
<script src="${APP_PATH}/static/js/bootstrap.js" charset="utf-8"></script>
<script type="text/javascript" src="${APP_PATH}/static/js/notebook.js"></script>
<%--<script type="text/javascript">--%>
<%--    function first() {--%>
<%--        var path = $("#APP_PATH").val();--%>
<%--        var userId = sessionStorage.getItem("userId");--%>
<%--        var userName = sessionStorage.getItem("userName");--%>
<%--        var i = 0;--%>
<%--        if (userId==null||userName==null){--%>
<%--            i++;--%>
<%--            console.log("有空值"+" i:"+i);--%>
<%--        }else {--%>
<%--            i++--%>
<%--            console.log("有值"+" i:"+i+" path:"+path);--%>
<%--            $.ajax({--%>
<%--                url:path+"/chat",--%>
<%--                data:"userId="+userId+"&userName="+userName,--%>
<%--                type:"post",--%>
<%--                success:function () {--%>

<%--                }--%>
<%--            });--%>
<%--        }--%>

<%--    }--%>
<%--</script>--%>
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
            <li role="presentation" class="active"><a href="${APP_PATH}/page1">记事本</a></li>
            <li role="presentation"><a href="${APP_PATH}/page2">通讯录</a></li>
            <li role="presentation"><a href="${APP_PATH}/page3">收藏夹</a></li>
            <li role="presentation"><a href="${APP_PATH}/page4">文件箱</a></li>
            <li role="presentation"><a href="${APP_PATH}/page5" onclick="first()">聊天室</a></li>
        </ul>
    </div>
    <%--    按钮--%>
    <div class="row">
        <div class="col-md-4 col-md-offset-8">
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
                <h4 class="modal-title">添加记事本</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
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
