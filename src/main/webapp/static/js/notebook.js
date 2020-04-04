
var pagenum;
var user;

$(function () {

    notebook_to_page(1);
});

function notebook_to_page(pn) {
    var path = $("#APP_PATH").val();
    user = sessionStorage.getItem("userId");
    $.ajax({
        url:path+"/notebook",
        data:"pn="+pn+"&userId="+user,
        type:"GET",
        async: false,
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
    var path = $("#APP_PATH").val();
    user = sessionStorage.getItem("userId");
    $.ajax({
        url: path+"/save",
        type: "POST",
        async:false,
        data: $('#notebook_add form').serialize()+"&userId="+user,
        success:function (result) {
            console.log(result.msg);
            $('#notebook_add').modal("hide");
            notebook_to_page(pagenum);
        }
    });
});