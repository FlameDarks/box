var pagenum;
var user;

$(function () {
    bookmark_to_page(1);
});

function bookmark_to_page(pn) {
    var path = $("#APP_PATH").val();
    user = sessionStorage.getItem("userId");
    $.ajax({
        url:path+"/bookmark",
        data:"pn="+pn+"&userId="+user,
        type:"GET",
        success:function (result) {
            build_bookmark_table(result);
            build_bookmark_pageinfo(result);
            build_bookmark_page(result);
        }
    });
}

// 解析显示记事本数据
function build_bookmark_table(result) {
    $("#bookmark_table tbody").empty();
    var bookmark = result.extend.bookmark_pageInfo.list;
    $.each(bookmark,function (index,item) {
        // var bookmarkIdTd = $("<td></td>").append(item.bookmarkId);
        var checkBoxTd = $("<td><input type='checkbox' class='check_item'/></td>")
        checkBoxTd.find("input").attr("check_id",item.bookmarkId);
        var bookmarkTitleTd = $("<td></td>").append(item.bookmarkTitle);
        var bookmarkContentTd = $("<td></td>").append(item.bookmarkContent);
        var editBtn = $("<button></button>").addClass("btn btn-primary btn-sm edit")
            .append($("<span></span>").addClass("glyphicon glyphicon-pencil").append("编辑"));
        editBtn.attr("edit_id",item.bookmarkId);
        var delBtn = $("<button></button>").addClass("btn btn-danger btn-sm del")
            .append($("<span></span>").addClass("glyphicon glyphicon-trash").append("删除"));
        delBtn.attr("del_id",item.bookmarkId);
        var btnTd = $("<td></td>").append(editBtn).append(" ").append(delBtn)
        $("<tr></tr>")
            // .append(bookmarkIdTd)
            .append(checkBoxTd)
            .append(bookmarkTitleTd)
            .append(bookmarkContentTd)
            .append(btnTd)
            .appendTo("#bookmark_table tbody");
    });
}

// 解析显示分页信息
function build_bookmark_pageinfo(result) {
    $("#bookmark_pageinfo").empty();
    $("#bookmark_pageinfo").append("第"+result.extend.bookmark_pageInfo.pageNum+"页，总共"+result.extend.bookmark_pageInfo.pages+"页，总共"+result.extend.bookmark_pageInfo.total+"条记录")
    pagenum = result.extend.bookmark_pageInfo.pageNum;
}

// 解析显示分页条数据
function build_bookmark_page(result) {
    $("#bookmark_page").empty();
    var ul = $("<ul></ul>").addClass("pagination");
    var first = $("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
    var pre = $("<li></li>").append($("<a></a>").append("&laquo;"));
    if (result.extend.bookmark_pageInfo.hasPreviousPage == false){
        first.addClass("disabled");
        pre.addClass("disabled");
    }else {
        first.click(function () {
            bookmark_to_page(1);
        });
        pre.click(function () {
            bookmark_to_page(result.extend.bookmark_pageInfo.pageNum-1);
        });
    }

    var next = $("<li></li>").append($("<a></a>").append("&raquo;"));
    var last = $("<li></li>").append($("<a></a>").append("尾页").attr("href","#"));
    if (result.extend.bookmark_pageInfo.hasNextPage == false){
        next.addClass("disabled");
        last.addClass("disabled");
    }else {
        next.click(function () {
            bookmark_to_page(result.extend.bookmark_pageInfo.pageNum+1);
        });
        last.click(function () {
            bookmark_to_page(result.extend.bookmark_pageInfo.pages);
        });
    }

    ul.append(first).append(pre);
    $.each(result.extend.bookmark_pageInfo.navigatepageNums,function (index,item) {
        var num = $("<li></li>").append($("<a></a>").append(item));
        if (result.extend.bookmark_pageInfo.pageNum == item){
            num.addClass("active");
        }
        num.click(function () {
            bookmark_to_page(item);
        });
        ul.append(num);
    });
    ul.append(next).append(last);
    var navigation = $("<nav></nav>").append(ul);
    navigation.appendTo("#bookmark_page");
}
// 时间换算
function bookmark_time(time) {
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

$('#bookmark_add_btn').click(function () {
    $('#bookmark_add').modal({
        backdrop: 'static'
    });
});

$(document).on("click", '#bookmark_save_btn', function() {
    //需要执行的逻辑
    user = sessionStorage.getItem("userId");
    console.log($('#bookmark_add form').serialize() + "&userId=" + user);
    var path = $("#APP_PATH").val();
    $.ajax({
        url: path + "/saveBookMark",
        type: "POST",
        async: false,
        data: $('#bookmark_add form').serialize() + "&userId=" + user,
        success: function (result) {
            console.log(result.msg);
            $('#bookmark_add').modal("hide");
            bookmark_to_page(pagenum);
        }
    });
});

$(document).on("click", '.edit', function() {
    echo($(this).attr("edit_id"));
    $('#bookmark_update_btn').attr("edit_id",$(this).attr("edit_id"));
    $('#bookmark_update').modal({
        backdrop: 'static'
    });
});
// 回显信息
function echo(id) {
    var path = $("#APP_PATH").val();
    $.ajax({
        url:path+"/echoBookMark",
        data:"Id="+id,
        type:"GET",
        success:function (result) {
            var data = result.extend.bookmarks;
            $("#bookmarkTitle_update").val(data.bookmarkTitle);
            $("#bookmarkContent_update").val(data.bookmarkContent);
        }
    });
}

$(document).on("click", '#bookmark_update_btn', function() {
    var path = $("#APP_PATH").val();
    console.log("bookmarkId="+$(this).attr("edit_id")+"&"+$('#bookmark_update form').serialize());
    user = sessionStorage.getItem("userId");
    $.ajax({
        url:path+"/editBookMark",
        data:"bookmarkId="+$(this).attr("edit_id")+"&"+$('#bookmark_update form').serialize()+"&userId="+user,
        type:"PUT",
        success:function () {
            $('#bookmark_update').modal("hide");
            bookmark_to_page(pagenum);
        }
    });
});

$(document).on("click", '.del', function() {
    var path = $("#APP_PATH").val();
    var title = $(this).parents("tr").find("td:eq(1)").text();
    if (confirm("确认删除"+title+"吗？")){
        $.ajax({
            url:path+"/delBookMark",
            data:"Id="+$(this).attr("del_id"),
            type:"DELETE",
            success:function () {
                bookmark_to_page(pagenum);
            }
        });
    }
});
// 全选
$(document).on("click", '#check_all', function() {
    $(".check_item").prop("checked",$(this).prop("checked"));
});

$(document).on("click", '.check_item', function() {
    var flag = $(".check_item:checked").length==$(".check_item").length;
    $("#check_all").prop("checked",flag);
});

// 批量删除
$(document).on("click", '#bookmark_del_btn', function() {
// $("#bookmark_del_btn").click(function () {
    var title = "";
    var id = "";
    var path = $("#APP_PATH").val();
    $.each($(".check_item:checked"),function () {
        title += $(this).parents("tr").find("td:eq(1)").text()+"，";
        id += $(this).attr("check_id")+"-";
    });
    title = title.substring(0,title.length-1);
    id = id.substring(0,id.length-1);
    if (confirm("确认删除"+title+"吗？")){
        $.ajax({
            url:path+"/delBookMark",
            data:"Id="+id,
            type:"DELETE",
            success:function () {
                bookmark_to_page(pagenum);
            }
        });
    }
});