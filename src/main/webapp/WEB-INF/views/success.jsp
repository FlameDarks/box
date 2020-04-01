<%--
  Created by IntelliJ IDEA.
  User: FlameDark
  Date: 2020/3/28
  Time: 11:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%--<jsp:forward page="/notebook"/>--%>
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
<script type="text/javascript">

    $(function () {
        notebook_to_page(1);
    });

    function notebook_to_page(pn) {
        $.ajax({
            url:"${APP_PATH}/notebook",
            data:"pn="+pn,
            type:"GET",
            success:function (result) {
                console.log(result);
                build_notebook_table(result);
                build_notebook_pageinfo(result);
                build_notebook_page(result);
            }
        });
    }

    // 解析显示记事本数据
    function build_notebook_table(result) {
        $("#notebook_table tbody").empty();
        var notebook = result.extend.notebook_pageInfo.list;
        $.each(notebook,function (index,item) {
            var notebookIdTd = $("<td></td>").append(item.notebookId);
            var notebookTitleTd = $("<td></td>").append(item.notebookTitle);
            var notebookTimeTd = $("<td></td>").append(notebook_time(item.notebookTime));
            var editBtn = $("<button></button>").addClass("btn btn-primary btn-sm")
                .append($("<span></span>").addClass("glyphicon glyphicon-pencil").append("编辑"));
            var delBtn = $("<button></button>").addClass("btn btn-danger btn-sm")
                .append($("<span></span>").addClass("glyphicon glyphicon-trash").append("删除"));
            var btnTd = $("<td></td>").append(editBtn).append(" ").append(delBtn)
            $("<tr></tr>").append(notebookIdTd)
                .append(notebookTitleTd)
                .append(notebookTimeTd)
                .append(btnTd)
                .appendTo("#notebook_table tbody");
        });
    }

    // 解析显示分页信息
    function build_notebook_pageinfo(result) {
        $("#notebook_pageinfo").empty();
        $("#notebook_pageinfo").append("第"+result.extend.notebook_pageInfo.pageNum+"页，总共"+result.extend.notebook_pageInfo.pages+"页，总共"+result.extend.notebook_pageInfo.total+"条记录")
    }

    // 解析显示分页条数据
    function build_notebook_page(result) {
        $("#notebook_page").empty();
        var ul = $("<ul></ul>").addClass("pagination");
        var first = $("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
        var pre = $("<li></li>").append($("<a></a>").append("&laquo;"));
        if (result.extend.notebook_pageInfo.hasPreviousPage == false){
            first.addClass("disabled");
            pre.addClass("disabled");
        }else {
            first.click(function () {
                notebook_to_page(1);
            });
            pre.click(function () {
                notebook_to_page(result.extend.notebook_pageInfo.pageNum-1);
            });
        }

        var next = $("<li></li>").append($("<a></a>").append("&raquo;"));
        var last = $("<li></li>").append($("<a></a>").append("尾页").attr("href","#"));
        if (result.extend.notebook_pageInfo.hasNextPage == false){
            next.addClass("disabled");
            last.addClass("disabled");
        }else {
            next.click(function () {
                notebook_to_page(result.extend.notebook_pageInfo.pageNum+1);
            });
            last.click(function () {
                notebook_to_page(result.extend.notebook_pageInfo.pages);
            });
        }

        ul.append(first).append(pre);
        $.each(result.extend.notebook_pageInfo.navigatepageNums,function (index,item) {
            var num = $("<li></li>").append($("<a></a>").append(item));
            if (result.extend.notebook_pageInfo.pageNum == item){
                num.addClass("active");
            }
            num.click(function () {
                notebook_to_page(item);
            });
            ul.append(num);
        });
        ul.append(next).append(last);
        var navigation = $("<nav></nav>").append(ul);
        navigation.appendTo("#notebook_page");
    }
    // 时间换算
    function notebook_time(time) {
        var datetime = new Date();
        datetime.setTime(time);
        var year = datetime.getFullYear();
        var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
        var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
        var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
        var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
        var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
        return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second;
    }
    
</script>
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
            <table class="table table-hover" id="notebook_table">
                <thead>
                    <tr>
                        <th class="col-md-2">ID</th>
                        <th class="col-md-6">标题</th>
                        <th class="col-md-2">日期</th>
                        <th class="col-md-2">操作</th>
                    </tr>
                </thead>
                <tbody>

                </tbody>
<%--                <c:forEach items="${notebook_pageInfo.list}" var="notebook">--%>
<%--                    <tr>--%>
<%--                        <th>${notebook.notebookId}</th>--%>
<%--                        <th>${notebook.notebookTitle}</th>--%>
<%--                        <th><fmt:formatDate value="${notebook.notebookTime}" pattern="yyyy-MM-dd HH:mm:ss"/></th>--%>
<%--                        <th>--%>
<%--                            <button class="btn btn-primary btn-sm">--%>
<%--                                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>--%>
<%--                                编辑--%>
<%--                            </button>--%>
<%--                            <button class="btn btn-danger btn-sm">--%>
<%--                                <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>--%>
<%--                                删除--%>
<%--                            </button>--%>
<%--                        </th>--%>
<%--                    </tr>--%>
<%--                </c:forEach>--%>
            </table>
        </div>
    </div>
    <%--    页码--%>
    <div class="row">
        <div class="col-md-6" id="notebook_pageinfo">
<%--            当前页数:${notebook_pageInfo.pageNum}，总${notebook_pageInfo.pages}页，共${notebook_pageInfo.total}条记录--%>
        </div>
        <div class="col-md-6" id="notebook_page">
<%--            <nav aria-label="Page navigation">--%>
<%--                <ul class="pagination">--%>
<%--                    <li><a href="${APP_PATH}/notebook?pn=1">首页</a></li>--%>
<%--                    <c:if test="${notebook_pageInfo.hasPreviousPage}">--%>
<%--                        <li>--%>
<%--                            <a href="${APP_PATH}/notebook?pn=${notebook_pageInfo.pageNum-1}" aria-label="Previous">--%>
<%--                                <span aria-hidden="true">&laquo;</span>--%>
<%--                            </a>--%>
<%--                        </li>--%>
<%--                    </c:if>--%>
<%--                    <c:forEach items="${notebook_pageInfo.navigatepageNums}" var="notebook_pagenum">--%>
<%--                        <c:if test="${notebook_pagenum == notebook_pageInfo.pageNum}">--%>
<%--                            <li class="active"><a href="#">${notebook_pagenum}</a></li>--%>
<%--                        </c:if>--%>
<%--                        <c:if test="${notebook_pagenum != notebook_pageInfo.pageNum}">--%>
<%--                            <li><a href="${APP_PATH}/notebook?pn=${notebook_pagenum}">${notebook_pagenum}</a></li>--%>
<%--                        </c:if>--%>
<%--                    </c:forEach>--%>
<%--                    <c:if test="${notebook_pageInfo.hasNextPage}">--%>
<%--                        <li>--%>
<%--                            <a href="${APP_PATH}/notebook?pn=${notebook_pageInfo.pageNum+1}" aria-label="Next">--%>
<%--                                <span aria-hidden="true">&raquo;</span>--%>
<%--                            </a>--%>
<%--                        </li>--%>
<%--                    </c:if>--%>
<%--                    <li><a href="${APP_PATH}/notebook?pn=${notebook_pageInfo.pages}">尾页</a></li>--%>
<%--                </ul>--%>
<%--            </nav>--%>
        </div>
    </div>
</div>
</body>
</html>
