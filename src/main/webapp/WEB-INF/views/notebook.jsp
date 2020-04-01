<%--
  Created by IntelliJ IDEA.
  User: FlameDark
  Date: 2020/3/30
  Time: 10:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<%
    pageContext.setAttribute("APP_PATH",request.getContextPath());
%>
<link rel="stylesheet" href="${APP_PATH}/static/css/bootstrap.min.css">
<script src="${APP_PATH}/static/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${APP_PATH}/static/js/jquery-3.4.1.js"></script>
<script type="text/javascript">
    $(function () {
        $.ajax({
            url:"${APP_PATH}/notebook",
            data:"pn=1",
            type:"GET",
            success:function (result) {
                console.log(result);
            }
        });
    });
</script>
<body>
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
            <button class="btn btn-primary">新增</button>
            <button class="btn btn-danger">删除</button>
        </div>
    </div>
    <%--    表格--%>
    <div class="row">
        <div class="col-md-12">
            <table class="table table-hover">
                <tr>
                    <th class="col-md-2">ID</th>
                    <th class="col-md-6">标题</th>
                    <th class="col-md-2">日期</th>
                    <th class="col-md-2">操作</th>
                </tr>
                <c:forEach items="${notebook_pageInfo.list}" var="notebook">
                    <tr>
                        <th>${notebook.notebookId}</th>
                        <th>${notebook.notebookTitle}</th>
                        <th><fmt:formatDate value="${notebook.notebookTime}" pattern="yyyy-MM-dd HH:mm:ss"/></th>
                        <th>
                            <button class="btn btn-primary btn-sm">
                                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                                编辑
                            </button>
                            <button class="btn btn-danger btn-sm">
                                <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                                删除
                            </button>
                        </th>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
    <%--    页码--%>
    <div class="row">
        <div class="col-md-6">
            当前页数:${notebook_pageInfo.pageNum}，总${notebook_pageInfo.pages}页，共${notebook_pageInfo.total}条记录
        </div>
        <div class="col-md-6">
            <nav aria-label="Page navigation">
                <ul class="pagination">
                    <li><a href="${APP_PATH}/notebook?pn=1">首页</a></li>
                    <c:if test="${notebook_pageInfo.hasPreviousPage}">
                        <li>
                            <a href="${APP_PATH}/notebook?pn=${notebook_pageInfo.pageNum-1}" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                    </c:if>
                    <c:forEach items="${notebook_pageInfo.navigatepageNums}" var="notebook_pagenum">
                        <c:if test="${notebook_pagenum == notebook_pageInfo.pageNum}">
                            <li class="active"><a href="#">${notebook_pagenum}</a></li>
                        </c:if>
                        <c:if test="${notebook_pagenum != notebook_pageInfo.pageNum}">
                            <li><a href="${APP_PATH}/notebook?pn=${notebook_pagenum}">${notebook_pagenum}</a></li>
                        </c:if>
                    </c:forEach>
                    <c:if test="${notebook_pageInfo.hasNextPage}">
                        <li>
                            <a href="${APP_PATH}/notebook?pn=${notebook_pageInfo.pageNum+1}" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </c:if>
                    <li><a href="${APP_PATH}/notebook?pn=${notebook_pageInfo.pages}">尾页</a></li>
                </ul>
            </nav>
        </div>
    </div>
</div>
</body>
</html>
