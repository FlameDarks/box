<%--
  Created by IntelliJ IDEA.
  User: FlameDark
  Date: 2020/3/30
  Time: 17:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>收藏夹</title>
</head>
<body>
<%
    pageContext.setAttribute("APP_PATH",request.getContextPath());
%>
<link rel="stylesheet" href="${APP_PATH}/static/css/bootstrap.css">
<script type="text/javascript" src="${APP_PATH}/static/js/jquery-3.4.1.js" charset="utf-8"></script>
<script src="${APP_PATH}/static/js/bootstrap.js" charset="utf-8"></script>
<script type="text/javascript" src="${APP_PATH}/static/js/bookmark.js"></script>
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
            <li role="presentation" class="active"><a href="${APP_PATH}/page3">收藏夹</a></li>
            <li role="presentation"><a href="${APP_PATH}/page4">文件箱</a></li>
            <li role="presentation"><a href="${APP_PATH}/page5">聊天室</a></li>
        </ul>
    </div>
    <%--    按钮--%>
    <div class="row">
        <div class="col-md-4 col-md-offset-8">
            <button class="btn btn-primary" id="bookmark_add_btn" data-toggle="modal"  data-target="#bookmark_add">新增</button>
            <button class="btn btn-danger" id="bookmark_del_btn">删除</button>
        </div>
    </div>
    <%--    表格--%>
    <div class="row">
        <div class="col-md-12">
            <table class="table table-hover" id="bookmark_table">
                <thead>
                <tr>
                    <th class="col-md-2">
                        <input type="checkbox" id="check_all">
                    </th>
                    <th class="col-md-2">书签名</th>
                    <th class="col-md-6">网址</th>
                    <th class="col-md-2">操作</th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
    </div>
    <%--    页码--%>
    <div class="row">
        <div class="col-md-6" id="bookmark_pageInfo"></div>
        <div class="col-md-6" id="bookmark_page"></div>
    </div>
</div>



<%--新增--%>
<div class="modal fade" id="bookmark_add" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加书签</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="bookmarkTitle_add" class="col-sm-2 control-label">书签名</label>
                        <div class="col-sm-10">
                            <input type="text" name="bookmarkTitle" class="form-control" id="bookmarkTitle_add" placeholder="姓名">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="bookmarkContent_add" class="col-sm-2 control-label">网址</label>
                        <div class="col-sm-10">
                            <input type="text" name="bookmarkContent" class="form-control" id="bookmarkContent_add" placeholder="电话">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="bookmark_save_btn">保存</button>
            </div>
        </div>
    </div>
</div>

<%--修改--%>
<div class="modal fade" id="bookmark_update" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">修改书签</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="bookmarkTitle_update" class="col-sm-2 control-label">书签名</label>
                        <div class="col-sm-10">
                            <input type="text" name="bookmarkTitle" class="form-control" id="bookmarkTitle_update" placeholder="姓名">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="bookmarkContent_update" class="col-sm-2 control-label">网址</label>
                        <div class="col-sm-10">
                            <input type="text" name="bookmarkContent" class="form-control" id="bookmarkContent_update" placeholder="电话">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="bookmark_update_btn">修改</button>
            </div>
        </div>
    </div>
</div>
</body>
</body>
</html>
