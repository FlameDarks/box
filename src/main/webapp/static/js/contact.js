
var pagenum;
var user;

$(function () {
    contact_to_page(1);
});

/**
 * 通讯录列表当前页
 * @param pn
 */
function contact_to_page(pn) {
    var path = $("#APP_PATH").val();
    user = sessionStorage.getItem("userId");
    $.ajax({
        url:path+"/contact/selectContact",
        data:"pn="+pn+"&userId="+user,
        type:"GET",
        success:function (result) {
            build_contact_table(result);
            build_contact_pageinfo(result);
            build_contact_page(result);
        }
    });
}

/**
 * 显示当页记事本
 * @param result
 */
function build_contact_table(result) {
    $("#contact_table tbody").empty();
    var contact = result.extend.contact_pageInfo.list;
    $.each(contact,function (index,item) {
        var checkBoxTd = $("<td><input type='checkbox' class='check_item'/></td>")
        checkBoxTd.find("input").attr("check_id",item.contactId);
        var contactNameTd = $("<td></td>").append(item.contactName);
        var contactPhoneTd = $("<td></td>").append(item.contactPhone);
        var contactAddressTd = $("<td></td>").append(item.contactAddress);
        var editBtn = $("<button></button>").addClass("btn btn-primary btn-sm edit")
            .append($("<span></span>").addClass("glyphicon glyphicon-pencil").append("编辑"));
        editBtn.attr("edit_id",item.contactId);
        var delBtn = $("<button></button>").addClass("btn btn-danger btn-sm del")
            .append($("<span></span>").addClass("glyphicon glyphicon-trash").append("删除"));
        delBtn.attr("del_id",item.contactId);
        var btnTd = $("<td></td>").append(editBtn).append(" ").append(delBtn)
        $("<tr></tr>")
            .append(checkBoxTd)
            .append(contactNameTd)
            .append(contactPhoneTd)
            .append(contactAddressTd)
            .append(btnTd)
            .appendTo("#contact_table tbody");
    });
}

/**
 * 解析显示分页信息
 * @param result
 */
function build_contact_pageinfo(result) {
    $("#contact_pageinfo").empty();
    $("#contact_pageinfo").append("第"+result.extend.contact_pageInfo.pageNum+"页，总共"+result.extend.contact_pageInfo.pages+"页，总共"+result.extend.contact_pageInfo.total+"条记录")
    pagenum = result.extend.contact_pageInfo.pageNum;
}

/**
 * 解析显示分页条数据
 * @param result
 */
function build_contact_page(result) {
    $("#contact_page").empty();
    var ul = $("<ul></ul>").addClass("pagination");
    var first = $("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
    var pre = $("<li></li>").append($("<a></a>").append("&laquo;"));
    //如果没有上一页
    if (result.extend.contact_pageInfo.hasPreviousPage == false){
        first.addClass("disabled");
        pre.addClass("disabled");
    }else {
        first.click(function () {
            contact_to_page(1);
        });
        pre.click(function () {
            contact_to_page(result.extend.contact_pageInfo.pageNum-1);
        });
    }

    var next = $("<li></li>").append($("<a></a>").append("&raquo;"));
    var last = $("<li></li>").append($("<a></a>").append("尾页").attr("href","#"));
    //如果没有下一页
    if (result.extend.contact_pageInfo.hasNextPage == false){
        next.addClass("disabled");
        last.addClass("disabled");
    }else {
        next.click(function () {
            contact_to_page(result.extend.contact_pageInfo.pageNum+1);
        });
        last.click(function () {
            contact_to_page(result.extend.contact_pageInfo.pages);
        });
    }

    ul.append(first).append(pre);
    $.each(result.extend.contact_pageInfo.navigatepageNums,function (index,item) {
        var num = $("<li></li>").append($("<a></a>").append(item));
        if (result.extend.contact_pageInfo.pageNum == item){
            num.addClass("active");
        }
        num.click(function () {
            contact_to_page(item);
        });
        ul.append(num);
    });
    ul.append(next).append(last);
    var navigation = $("<nav></nav>").append(ul);
    navigation.appendTo("#contact_page");
}

/**
 * 添加通讯录的模态框
 */
$('#contact_add_btn').click(function () {
    $('#contact_add').modal({
        backdrop: 'static'
    });
});
/**
 * 保存通讯录的按钮
 */
$(document).on("click", '#contact_save_btn', function() {
    user = sessionStorage.getItem("userId");
    console.log($('#contact_add form').serialize() + "&userId=" + user);
    var path = $("#APP_PATH").val();
    $.ajax({
        url: path + "/contact/saveContact",
        type: "POST",
        async: false,
        data: $('#contact_add form').serialize() + "&userId=" + user,
        success: function (result) {
            console.log(result.msg);
            $('#contact_add').modal("hide");
            contact_to_page(pagenum);
        }
    });
});
/**
 * 编辑按钮的模态框
 */
$(document).on("click", '.edit', function() {
    echo($(this).attr("edit_id"));
    $('#contact_update_btn').attr("edit_id",$(this).attr("edit_id"));
    $('#contact_update').modal({
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
        url:path+"/contact/echoContact",
        data:"Id="+id,
        type:"GET",
        success:function (result) {
            var data = result.extend.contacts;
            $("#contactTitle_update").val(data.contactTitle);
            $("#contactContent_update").val(data.contactContent);
        }
    });
}

/**
 * 更新通讯录的按钮
 */
$(document).on("click", '#contact_update_btn', function() {
    var path = $("#APP_PATH").val();
    console.log("contactId="+$(this).attr("edit_id")+"&"+$('#contact_update form').serialize());
    user = sessionStorage.getItem("userId");
    $.ajax({
        url:path+"/contact/editContact",
        data:"contactId="+$(this).attr("edit_id")+"&"+$('#contact_update form').serialize()+"&userId="+user,
        type:"PUT",
        success:function () {
            $('#contact_update').modal("hide");
            contact_to_page(pagenum);
        }
    });
});
/**
 * 删除通讯录
 */
$(document).on("click", '.del', function() {
    var path = $("#APP_PATH").val();
    var title = $(this).parents("tr").find("td:eq(1)").text();
    if (confirm("确认删除"+title+"吗？")){
        $.ajax({
            url:path+"/contact/delContact",
            data:"Id="+$(this).attr("del_id"),
            type:"DELETE",
            success:function () {
                contact_to_page(pagenum);
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
$(document).on("click", '#contact_del_btn', function() {
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
            url:path+"/contact/delContact",
            data:"Id="+id,
            type:"DELETE",
            success:function () {
                contact_to_page(pagenum);
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
    alert(content);
    if (content!=null || content!=undefined){
        $.ajax({
            data:"check="+check+"&data="+content+"&type="+type,
            url:path+"/select",
            type:"POST",
            success:function (result) {
                build_contact_table(result);
                build_contact_pageinfo(result);
                build_contact_page(result);
            }
        });
    }
}