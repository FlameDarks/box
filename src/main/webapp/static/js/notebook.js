
var pagenum;
var user;

$(function () {
    notebook_to_page(1);
});

/**
 * 记事本列表当前页
 * @param pn
 */
function notebook_to_page(pn) {
    var path = $("#APP_PATH").val();
    user = sessionStorage.getItem("userId");
    $.ajax({
        url:path+"/notebook/selectNotebook",
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

/**
 * 显示当页记事本
 * @param result
 */
function build_notebook_table(result) {
    $("#notebook_table tbody").empty();
    var notebook = result.extend.notebook_pageInfo.list;
    $.each(notebook,function (index,item) {
        var checkBoxTd = $("<td><input type='checkbox' class='check_item'/></td>")
        checkBoxTd.find("input").attr("check_id",item.notebookId);
        var notebookTitleTd = $("<td></td>").append(item.notebookTitle);
        var notebookTimeTd = $("<td></td>").append(notebook_time(item.notebookTime));
        var editBtn = $("<button></button>").addClass("btn btn-primary btn-sm edit")
            .append($("<span></span>").addClass("glyphicon glyphicon-pencil").append("编辑"));
        editBtn.attr("edit_id",item.notebookId);
        var delBtn = $("<button></button>").addClass("btn btn-danger btn-sm del")
            .append($("<span></span>").addClass("glyphicon glyphicon-trash").append("删除"));
        delBtn.attr("del_id",item.notebookId);
        var btnTd = $("<td></td>").append(editBtn).append(" ").append(delBtn)
        $("<tr></tr>")
            .append(checkBoxTd)
            .append(notebookTitleTd)
            .append(notebookTimeTd)
            .append(btnTd)
            .appendTo("#notebook_table tbody");
    });
}

/**
 * 解析显示分页信息
 * @param result
 */
function build_notebook_pageinfo(result) {
    $("#notebook_pageinfo").empty();
    $("#notebook_pageinfo").append("第"+result.extend.notebook_pageInfo.pageNum+"页，总共"+result.extend.notebook_pageInfo.pages+"页，总共"+result.extend.notebook_pageInfo.total+"条记录")
    pagenum = result.extend.notebook_pageInfo.pageNum;
}

/**
 * 解析显示分页条数据
 * @param result
 */
function build_notebook_page(result) {
    $("#notebook_page").empty();
    var ul = $("<ul></ul>").addClass("pagination");
    var first = $("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
    var pre = $("<li></li>").append($("<a></a>").append("&laquo;"));
    //如果没有上一页
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
    //如果没有下一页
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

/**
 * 时间换算
 * @param time
 * @returns {string}
 */
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

/**
 * 添加记事本模态框
 */
$('#notebook_add_btn').click(function () {
    $('#notebook_add').modal({
        backdrop: 'static'
    });
});
/**
 * 保存记事本的按钮
 */
$(document).on("click", '#notebook_save_btn', function() {
    user = sessionStorage.getItem("userId");
    var path = $("#APP_PATH").val();
    $.ajax({
        url: path + "/notebook/saveNoteBook",
        type: "POST",
        async: false,
        data: $('#notebook_add form').serialize() + "&userId=" + user,
        success: function (result) {
            document.getElementById("notebook_add_form").reset();
            $('#notebook_add').modal("hide");
            notebook_to_page(pagenum);
        }
    });
});
/**
 * 编辑记事本模态框
 */
$(document).on("click", '.edit', function() {
    echo($(this).attr("edit_id"));
    $('#notebook_update_btn').attr("edit_id",$(this).attr("edit_id"));
    $('#notebook_update').modal({
        backdrop: 'static'
    });
});

/**
 * 回显信息
 * @param id
 */
function echo(id) {
    var path = $("#APP_PATH").val();
    $.ajax({
        url:path+"/notebook/echoNoteBook",
        data:"Id="+id,
        type:"GET",
        success:function (result) {
            var data = result.extend.note;
            $("#notebookTitle_update").val(data.notebookTitle);
            $("#notebookContent_update").val(data.notebookContent);
        }
    });
}

/**
 * 更新记事本的按钮
 */
$(document).on("click", '#notebook_update_btn', function() {
    var path = $("#APP_PATH").val();
    user = sessionStorage.getItem("userId");
    $.ajax({
        url:path+"/notebook/editNoteBook",
        data:"notebookId="+$(this).attr("edit_id")+"&"+$('#notebook_update form').serialize()+"&userId="+user,
        type:"PUT",
        success:function () {
            $('#notebook_update').modal("hide");
            notebook_to_page(pagenum);
        }
    });
});
/**
 * 删除记事本
 */
$(document).on("click", '.del', function() {
    var path = $("#APP_PATH").val();
    var title = $(this).parents("tr").find("td:eq(1)").text();
    if (confirm("确认删除"+title+"吗？")){
        $.ajax({
            url:path+"/notebook/delNoteBook",
            data:"Id="+$(this).attr("del_id"),
            type:"DELETE",
            success:function () {
                notebook_to_page(pagenum);
            }
        });
    }
});
/**
 * 选择checkbox
 */
$(document).on("click", '#check_all', function() {
    $(".check_item").prop("checked",$(this).prop("checked"));
});
/**
 * 全选或全不选
 */
$(document).on("click", '.check_item', function() {
    var flag = $(".check_item:checked").length==$(".check_item").length;
    $("#check_all").prop("checked",flag);
});

/**
 * 批量删除
 */
$(document).on("click", '#notebook_del_btn', function() {
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
            url:path+"/notebook/delNoteBook",
            data:"Id="+id,
            type:"DELETE",
            success:function () {
                notebook_to_page(pagenum);
            }
        });
    }
});
/**
 * 搜索
 */
$(document).on("click", '#selectBtn', function() {
    selectContent();
});

function selectContent() {
    var path = $("#APP_PATH").val();
    var content = $("#selectInput").val().trim();
    var type = $("#selectBtn").attr("select");
    var check = $("#check").val();
    if (content!=null || content!=undefined){
        $.ajax({
            data:"check="+check+"&data="+content+"&type="+type,
            url:path+"/select",
            type:"POST",
            success:function (result) {
                build_notebook_table(result);
                build_notebook_pageinfo(result);
                build_notebook_page(result);
            }
        });
    }
}



