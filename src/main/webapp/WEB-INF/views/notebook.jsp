<%--
  Created by IntelliJ IDEA.
  User: FlameDark
  Date: 2020/3/30
  Time: 10:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<%
    pageContext.setAttribute("APP_PATH",request.getContextPath());
%>
<link rel="stylesheet" href="${APP_PATH}/static/css/bootstrap.min.css">
<script type="text/javascript" src="${APP_PATH}/static/js/jquery-3.4.1.js"></script>
<script src="${APP_PATH}/static/js/bootstrap.min.js"></script>
<body>
<div class="container">
    <%--    标题--%>
    <div class="row">
        <div class="col-md-12">
            <h1 align="center">企业网络百宝箱</h1>
        </div>
    </div>
    <%--    按钮--%>
    <div class="row">
        <div class="col-md-4 col-md-offset-8">
            <button class="btn btn-primary">新增</button>
            <button class="btn btn-danger">删除</button>
        </div>
    </div>
    <%--    表格--%>
    <div class="row">
        <div class="col-md-12">
            <table>
                <tr>
                    <th>ID</th>
                    <th>标题</th>
                    <th>内容</th>
                </tr>
            </table>
        </div>
    </div>
    <%--    页码--%>
    <div class="row"></div>
</div>
</body>
</html>
