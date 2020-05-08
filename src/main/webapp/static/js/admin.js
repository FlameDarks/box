
var pagenum;
var user;

$(function () {
    admin_to_page(1);
});

/**
 * 用户列表当前页
 * @param pn
 */
function admin_to_page(pn) {
    var path = $("#APP_PATH").val();
    user = sessionStorage.getItem("userId");
    console.log(user);
    $.ajax({
        url:path+"/admin/selectAdmin",
        data:"pn="+pn,
        type:"GET",
        async: false,
        success:function (result) {
            build_admin_table(result);
            build_admin_pageinfo(result);
            build_admin_page(result);
        }
    });
}

/**
 * 显示当页用户列表
 * @param result
 */
function build_admin_table(result) {
    $("#admin_table tbody").empty();
    var admin = result.extend.admin_pageInfo.list;
    $.each(admin,function (index,item) {
        var checkBoxTd = $("<td><input type='checkbox' class='check_item'/></td>")
        checkBoxTd.find("input").attr("check_id",item.userId);
        var userIdTd = $("<td></td>").append(item.userId);
        var userNameTd = $("<td></td>").append(item.userName);
        var userTypeTd = $("<td></td>").append(admin_type(item.userType));
        var delBtn = $("<button></button>").addClass("btn btn-danger btn-sm del")
            .append($("<span></span>").addClass("glyphicon glyphicon-trash").append("删除"));
        delBtn.attr("del_id",item.userId);
        var btnTd = $("<td></td>").append(delBtn)
        $("<tr></tr>")
            .append(checkBoxTd)
            .append(userIdTd)
            .append(userNameTd)
            .append(userTypeTd)
            .append(btnTd)
            .appendTo("#admin_table tbody");
    });
}

/**
 * 解析显示分页信息
 * @param result
 */
function build_admin_pageinfo(result) {
    $("#admin_pageinfo").empty();
    $("#admin_pageinfo").append("第"+result.extend.admin_pageInfo.pageNum+"页，总共"+result.extend.admin_pageInfo.pages+"页，总共"+result.extend.admin_pageInfo.total+"条记录")
    pagenum = result.extend.admin_pageInfo.pageNum;
}

/**
 * 解析显示分页条数据
 * @param result
 */
function build_admin_page(result) {
    $("#admin_page").empty();
    var ul = $("<ul></ul>").addClass("pagination");
    var first = $("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
    var pre = $("<li></li>").append($("<a></a>").append("&laquo;"));
    //如果没有上一页
    if (result.extend.admin_pageInfo.hasPreviousPage == false){
        first.addClass("disabled");
        pre.addClass("disabled");
    }else {
        first.click(function () {
            admin_to_page(1);
        });
        pre.click(function () {
            admin_to_page(result.extend.admin_pageInfo.pageNum-1);
        });
    }

    var next = $("<li></li>").append($("<a></a>").append("&raquo;"));
    var last = $("<li></li>").append($("<a></a>").append("尾页").attr("href","#"));
    //如果没有下一页
    if (result.extend.admin_pageInfo.hasNextPage == false){
        next.addClass("disabled");
        last.addClass("disabled");
    }else {
        next.click(function () {
            admin_to_page(result.extend.admin_pageInfo.pageNum+1);
        });
        last.click(function () {
            admin_to_page(result.extend.admin_pageInfo.pages);
        });
    }

    ul.append(first).append(pre);
    $.each(result.extend.admin_pageInfo.navigatepageNums,function (index,item) {
        var num = $("<li></li>").append($("<a></a>").append(item));
        if (result.extend.admin_pageInfo.pageNum == item){
            num.addClass("active");
        }
        num.click(function () {
            admin_to_page(item);
        });
        ul.append(num);
    });
    ul.append(next).append(last);
    var navigation = $("<nav></nav>").append(ul);
    navigation.appendTo("#admin_page");
}

/**
 * 类别换算
 * @param type
 * @returns {string|null}
 */
function admin_type(type) {
    if (type == "user"){
        return "用户";
    }
    if (type == "admin"){
        return "管理员";
    }
    return null;
}
function user_type(type){
    if ($("#check").val()==3){
        if (type == "用户"){
            return "user";
        }
        if (type == "管理员"){
            return "admin";
        }
        alert("重新输入");
        return null;
    }
    return type;
}

/**
 * 添加用户模态框
 */
$('#admin_add_btn').click(function () {
    $('#admin_add').modal({
        backdrop: 'static'
    });
});
/**
 * 添加用户保存按钮
 */
$(document).on("click", '#admin_save_btn', function() {
    //需要执行的逻辑
    user = sessionStorage.getItem("userId");
    console.log($('#admin_add form').serialize());
    var path = $("#APP_PATH").val();
    $.ajax({
        url: path + "/admin/saveAdmin",
        type: "POST",
        async: false,
        data: $('#admin_add form').serialize(),
        success: function (result) {
            console.log(result.msg);
            $('#admin_add').modal("hide");
            admin_to_page(pagenum);
        }
    });
});
/**
 * 删除用户按钮
 */
$(document).on("click", '.del', function() {
    var path = $("#APP_PATH").val();
    var title = $(this).parents("tr").find("td:eq(1)").text();
    if (confirm("确认删除"+title+"吗？")){
        $.ajax({
            url:path+"/admin/delAdmin",
            data:"Id="+$(this).attr("del_id"),
            type:"DELETE",
            success:function () {
                admin_to_page(pagenum);
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
$(document).on("click", '#admin_del_btn', function() {
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
            url:path+"/admin/delAdmin",
            data:"Id="+id,
            type:"DELETE",
            success:function () {
                admin_to_page(pagenum);
            }
        });
    }
});
/**
 * 查询
 */
$(document).on("click", '#selectBtn', function() {
    selectContent();
});

function selectContent() {
    var path = $("#APP_PATH").val();
    var content = user_type($("#selectInput").val().trim());
    var type = $("#selectBtn").attr("select");
    var check = $("#check").val();
    if (content!=null || content!=undefined){
        $.ajax({
            data:"check="+check+"&data="+content+"&type="+type,
            url:path+"/select",
            type:"POST",
            success:function (result) {
                build_admin_table(result);
                build_admin_pageinfo(result);
                build_admin_page(result);
            }
        });
    }
}