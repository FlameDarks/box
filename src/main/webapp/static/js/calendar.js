
var pagenum;
var user;

$(function () {
    calendar_to_page(1);
});

/**
 * 记事本列表当前页
 * @param pn
 */
function calendar_to_page(pn) {
    var path = $("#APP_PATH").val();
    user = sessionStorage.getItem("userId");
    $.ajax({
        url:path+"/calendar/selectCalendar",
        data:"pn="+pn+"&userId="+user,
        type:"GET",
        async: false,
        success:function (result) {
            build_calendar_table(result);
            build_calendar_pageinfo(result);
            build_calendar_page(result);
        }
    });
}

/**
 * 显示当页记事本
 * @param result
 */
function build_calendar_table(result) {
    $("#calendar_table tbody").empty();
    var calendar = result.extend.calendar_pageInfo.list;
    $.each(calendar,function (index,item) {
        var checkBoxTd = $("<td><input type='checkbox' class='check_item'/></td>")
        checkBoxTd.find("input").attr("check_id",item.calendarId);
        var calendarTitleTd = $("<td></td>").append(item.calendarTitle);
        var calendarTimeTd = $("<td></td>").append(item.calendarTime);
        var editBtn = $("<button></button>").addClass("btn btn-primary btn-sm edit")
            .append($("<span></span>").addClass("glyphicon glyphicon-pencil").append("编辑"));
        editBtn.attr("edit_id",item.calendarId);
        var delBtn = $("<button></button>").addClass("btn btn-danger btn-sm del")
            .append($("<span></span>").addClass("glyphicon glyphicon-trash").append("删除"));
        delBtn.attr("del_id",item.calendarId);
        var btnTd = $("<td></td>").append(editBtn).append(" ").append(delBtn)
        $("<tr></tr>")
            .append(checkBoxTd)
            .append(calendarTitleTd)
            .append(calendarTimeTd)
            .append(btnTd)
            .appendTo("#calendar_table tbody");
    });
}

/**
 * 解析显示分页信息
 * @param result
 */
function build_calendar_pageinfo(result) {
    $("#calendar_pageinfo").empty();
    $("#calendar_pageinfo").append("第"+result.extend.calendar_pageInfo.pageNum+"页，总共"+result.extend.calendar_pageInfo.pages+"页，总共"+result.extend.calendar_pageInfo.total+"条记录")
    pagenum = result.extend.calendar_pageInfo.pageNum;
}

/**
 * 解析显示分页条数据
 * @param result
 */
function build_calendar_page(result) {
    $("#calendar_page").empty();
    var ul = $("<ul></ul>").addClass("pagination");
    var first = $("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
    var pre = $("<li></li>").append($("<a></a>").append("&laquo;"));
    //如果没有上一页
    if (result.extend.calendar_pageInfo.hasPreviousPage == false){
        first.addClass("disabled");
        pre.addClass("disabled");
    }else {
        first.click(function () {
            calendar_to_page(1);
        });
        pre.click(function () {
            calendar_to_page(result.extend.calendar_pageInfo.pageNum-1);
        });
    }

    var next = $("<li></li>").append($("<a></a>").append("&raquo;"));
    var last = $("<li></li>").append($("<a></a>").append("尾页").attr("href","#"));
    //如果没有下一页
    if (result.extend.calendar_pageInfo.hasNextPage == false){
        next.addClass("disabled");
        last.addClass("disabled");
    }else {
        next.click(function () {
            calendar_to_page(result.extend.calendar_pageInfo.pageNum+1);
        });
        last.click(function () {
            calendar_to_page(result.extend.calendar_pageInfo.pages);
        });
    }

    ul.append(first).append(pre);
    $.each(result.extend.calendar_pageInfo.navigatepageNums,function (index,item) {
        var num = $("<li></li>").append($("<a></a>").append(item));
        if (result.extend.calendar_pageInfo.pageNum == item){
            num.addClass("active");
        }
        num.click(function () {
            calendar_to_page(item);
        });
        ul.append(num);
    });
    ul.append(next).append(last);
    var navigation = $("<nav></nav>").append(ul);
    navigation.appendTo("#calendar_page");
}

/**
 * 添加记事本模态框
 */
$('#calendar_add_btn').click(function () {
    $('#calendar_add').modal({
        backdrop: 'static'
    });
});

function validate(year, month, day) {
    if (month == 4 || month == 6 || month == 9 || month == 11){
        if (day == 31){
            return false;
        }
    }
    if (month == 2){
        if (year == 2020){
            if (day >= 30){
                return false;
            }
        }else {
            if (day >= 29){
                return false;
            }
        }
    }
    return true;
}

/**
 * 保存记事本的按钮
 */
$(document).on("click", '#calendar_save_btn', function() {
    if (validate($("#year").val(),$("#month").val(),$("#day").val()) != true){
        alert("日期错误！");
        return false;
    }
    user = sessionStorage.getItem("userId");
    var path = $("#APP_PATH").val();
    var time = $("#year").val()+"-"+$("#month").val()+"-"+$("#day").val();
    var data = "calendarTitle="+$("#calendarTitle_add").val()+"&calendarTime="+time+"&calendarContent="+$("#calendarContent_add").val();
    console.log(data);
    $.ajax({
        url: path + "/calendar/saveCalendar",
        type: "POST",
        async: false,
        data: data + "&userId=" + user,
        success: function (result) {
            document.getElementById("calendar_add_form").reset();
            $('#calendar_add').modal("hide");
            calendar_to_page(pagenum);
        }
    });
});
/**
 * 编辑记事本模态框
 */
$(document).on("click", '.edit', function() {
    echo($(this).attr("edit_id"));
    $('#calendar_update_btn').attr("edit_id",$(this).attr("edit_id"));
    $('#calendar_update').modal({
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
        url:path+"/calendar/echoCalendar",
        data:"Id="+id,
        type:"GET",
        success:function (result) {
            var data = result.extend.note;
            var time = data.calendarTime.split("-");
            $("#years").val(time[0]);
            $("#months").val(time[1]);
            $("#days").val(time[2]);
            $("#calendarTitle_update").val(data.calendarTitle);
            $("#calendarContent_update").val(data.calendarContent);
        }
    });
}

/**
 * 更新记事本的按钮
 */
$(document).on("click", '#calendar_update_btn', function() {
    if (validate($("#years").val(),$("#months").val(),$("#days").val()) != true){
        alert("日期错误！");
        return false;
    }
    var path = $("#APP_PATH").val();
    user = sessionStorage.getItem("userId");
    var time = $("#years").val()+"-"+$("#months").val()+"-"+$("#days").val();
    var data = "calendarTitle="+$("#calendarTitle_update").val()+"&calendarTime="+time+"&calendarContent="+$("#calendarContent_update").val();
    $.ajax({
        url:path+"/calendar/editCalendar",
        data:"calendarId="+$(this).attr("edit_id")+"&"+data+"&userId="+user,
        type:"PUT",
        success:function () {
            $('#calendar_update').modal("hide");
            calendar_to_page(pagenum);
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
            url:path+"/calendar/delCalendar",
            data:"Id="+$(this).attr("del_id"),
            type:"DELETE",
            success:function () {
                calendar_to_page(pagenum);
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
$(document).on("click", '#calendar_del_btn', function() {
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
            url:path+"/calendar/delCalendar",
            data:"Id="+id,
            type:"DELETE",
            success:function () {
                calendar_to_page(pagenum);
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
                build_calendar_table(result);
                build_calendar_pageinfo(result);
                build_calendar_page(result);
            }
        });
    }
}



