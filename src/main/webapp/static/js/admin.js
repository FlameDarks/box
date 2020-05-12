
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
$(document).on("click", '#admin_add_btn', function() {
// $('#admin_add_btn').click(function () {
    reset_form("admin_add_form");
    $('#admin_add').modal({
        backdrop: 'static'
    });
    $("#userName_add").focusout(function () {
        check_user_name();
    });
    $("#userPassword_add").focusout(function () {
        validate_pwds_form();
    });
    $("#userPasswords_add").focusout(function () {
        validate_pwds_form();
    });
});
/**
 * 添加用户保存按钮
 */
$(document).on("click", '#admin_save_btn', function() {
    if (!validate_pwds_form()){
        return false;
    }
    if($("#admin_save_btn").attr("ajax-va")=="error"){
        return false;
    }
    var path = $("#APP_PATH").val();
    $.ajax({
        url: path + "/admin/saveAdmin",
        type: "POST",
        async: false,
        data: $('#admin_add form').serialize(),
        success: function (result) {
            if (result.code == 100){
                document.getElementById("admin_add_form").reset();
                $('#admin_add').modal("hide");
                admin_to_page(pagenum);
            }
        }
    });
});

/**
 * 注册校验
 * @returns {boolean}
 */
function validate_pwds_form() {
    console.log(1);
    var userpwd = $("#userPassword_add").val().trim();
    var userpwds = $("#userPasswords_add").val().trim();
    var regpwd = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z0-9_-]{8,16}$/;
    if (!regpwd.test(userpwd)){
        show_validate_msg("#userPassword_add","error","应包含至少一个大写字母、小写字母和数字的8-16位组合");
        return false;
    }else {
        show_validate_msg("#userPassword_add","success","");
    }
    if (!regpwd.test(userpwds)){
        show_validate_msg("#userPasswords_add","error","应包含至少一个大写字母、小写字母和数字的8-16位组合");
        return false;
    }else {
        show_validate_msg("#userPasswords_add","success","");
    }
    if (userpwd!=userpwds){
        show_validate_msg("#userPasswords_add","error","密码不一样");
        return false;
    }else {
        show_validate_msg("#userPasswords_add","success","");
    }
    return true;
}

/**
 * 注册校验提示信息
 * @param element  目标ID
 * @param status  状态（符合或不符合）
 * @param msg    显示信息
 */
function show_validate_msg(element,status,msg) {
    $(element).parent().removeClass("has-success has-error");
    $(element).next("span").text("");
    if ("success"==status){
        $(element).parent().addClass("has-success");
        $(element).next("span").text(msg);
    }else if ("error"==status){
        $(element).parent().addClass("has-error");
        $(element).next("span").text(msg);
    }
}

/**
 * 验证用户名是否重复
 */
function check_user_name() {
    var path = $("#APP_PATH").val();
    var username = $("#userName_add").val().trim();
    $.ajax({
        url: path+"/user/checkUser",
        type: "POST",
        data: "user_name="+username,
        async:false,
        success:function (result) {
            if (result.code==100){
                show_validate_msg("#userName_add","success","用户名可用");
                $("#user_reg_btn").attr("ajax-va","success");
            }else{
                show_validate_msg("#userName_add","error",result.extend.va_msg);
                $("#user_reg_btn").attr("ajax-va","error");
            }
        }
    });
}

/**
 * 重置提示信息
 * @param ele  目标ID
 */
function reset_form(ele) {
    document.getElementById(ele).reset();
    var eles = "#"+ele;
    $(eles).find("*").removeClass("has-error has-success");
    $(eles).find(".help-block").text("");
}

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