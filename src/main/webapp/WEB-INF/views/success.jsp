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

<script type="text/javascript">

    var pagenum;

    $(function () {
        notebook_to_page(1);
    });

    function notebook_to_page(pn) {
        $.ajax({
            url:"${APP_PATH}/notebook",
            data:"pn="+pn,
            type:"GET",
            success:function (result) {
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
        pagenum = result.extend.notebook_pageInfo.pageNum;
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

    $('#notebook_add_btn').click(function () {
        $('#notebook_add').modal({
            backdrop: 'static'
        });
    });

    $("#notebook_del_btn").click(function () {
        $("#notebook_add").modal({
            backdrop: "static"
        });
    });

        $(document).on("click", '#notebook_save_btn', function() {
            //需要执行的逻辑
            console.log($('#notebook_add form').serialize()+"&userId=1");
            $.ajax({
                url: "${APP_PATH}/save",
                type: "POST",
                data: $('#notebook_add form').serialize()+"&userId=1",
                success:function (result) {
                    console.log(result.msg);
                    $('#notebook_add').modal("hide");
                    notebook_to_page(pagenum);
                }
            });
        });

    <%--$("#notebook_save_btn").click(function () {--%>


    <%--});--%>

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
