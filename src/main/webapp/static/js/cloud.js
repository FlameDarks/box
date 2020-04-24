
var pagenum;
var user;

$(function () {
    cloud_to_page(1);
});

function cloud_to_page(pn) {
    var path = $("#APP_PATH").val();
    user = sessionStorage.getItem("userId");
    $.ajax({
        url:path+"/cloud",
        data:"pn="+pn+"&userId="+user,
        type:"GET",
        success:function (result) {
            build_cloud_table(result);
            build_cloud_pageinfo(result);
            build_cloud_page(result);
        }
    });
}

// 解析显示数据
function build_cloud_table(result) {
    $("#cloud_table tbody").empty();
    var cloud = result.extend.cloud_pageInfo.list;
    $.each(cloud,function (index,item) {
        // var cloudIdTd = $("<td></td>").append(item.cloudId);
        var checkBoxTd = $("<td><input type='checkbox' class='check_item'/></td>")
        checkBoxTd.find("input").attr("check_id",item.cloudId);
        var cloudNameTd = $("<td></td>").append(item.cloudName);
        var cloudTimeTd = $("<td></td>").append(cloud_time(item.cloudTime));
        var downBtn = $("<button></button>").addClass("btn btn-primary btn-sm down")
            .append($("<span></span>").addClass("glyphicon glyphicon-pencil").append("下载"));
        downBtn.attr("down_id",item.cloudId);
        var url = item.cloudUrl;
        downBtn.attr("onclick","window.location.href='"+url+"'");
        var delBtn = $("<button></button>").addClass("btn btn-danger btn-sm del")
            .append($("<span></span>").addClass("glyphicon glyphicon-trash").append("删除"));
        delBtn.attr("del_id",item.cloudId);
        var btnTd = $("<td></td>").append(downBtn).append(" ").append(delBtn)
        $("<tr></tr>")
            // .append(cloudIdTd)
            .append(checkBoxTd)
            .append(cloudNameTd)
            .append(cloudTimeTd)
            .append(btnTd)
            .appendTo("#cloud_table tbody");
    });
}

// 解析显示分页信息
function build_cloud_pageinfo(result) {
    $("#cloud_pageinfo").empty();
    $("#cloud_pageinfo").append("第"+result.extend.cloud_pageInfo.pageNum+"页，总共"+result.extend.cloud_pageInfo.pages+"页，总共"+result.extend.cloud_pageInfo.total+"条记录")
    pagenum = result.extend.cloud_pageInfo.pageNum;
}

// 解析显示分页条数据
function build_cloud_page(result) {
    $("#cloud_page").empty();
    var ul = $("<ul></ul>").addClass("pagination");
    var first = $("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
    var pre = $("<li></li>").append($("<a></a>").append("&laquo;"));
    if (result.extend.cloud_pageInfo.hasPreviousPage == false){
        first.addClass("disabled");
        pre.addClass("disabled");
    }else {
        first.click(function () {
            cloud_to_page(1);
        });
        pre.click(function () {
            cloud_to_page(result.extend.cloud_pageInfo.pageNum-1);
        });
    }

    var next = $("<li></li>").append($("<a></a>").append("&raquo;"));
    var last = $("<li></li>").append($("<a></a>").append("尾页").attr("href","#"));
    if (result.extend.cloud_pageInfo.hasNextPage == false){
        next.addClass("disabled");
        last.addClass("disabled");
    }else {
        next.click(function () {
            cloud_to_page(result.extend.cloud_pageInfo.pageNum+1);
        });
        last.click(function () {
            cloud_to_page(result.extend.cloud_pageInfo.pages);
        });
    }

    ul.append(first).append(pre);
    $.each(result.extend.cloud_pageInfo.navigatepageNums,function (index,item) {
        var num = $("<li></li>").append($("<a></a>").append(item));
        if (result.extend.cloud_pageInfo.pageNum == item){
            num.addClass("active");
        }
        num.click(function () {
            cloud_to_page(item);
        });
        ul.append(num);
    });
    ul.append(next).append(last);
    var navigation = $("<nav></nav>").append(ul);
    navigation.appendTo("#cloud_page");
}
// 时间换算
function cloud_time(time) {
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

$('#cloud_add_btn').click(function () {
    $('#cloud_add').modal({
        backdrop: 'static'
    });
});

$(document).on("click", '#cloud_save_btn', function() {
    //需要执行的逻辑
    user = sessionStorage.getItem("userId");
    var name = $("#cloudName_add").val();
    console.log(1);
    var path = $("#APP_PATH").val();
    console.log(2);
    var file = $('#clouds_add')[0].files[0];
    var allowsize = 10485760;
    if (file.size>allowsize){
        alert("文件不能超过10kb");
        return false;
    }
    console.log(3);
    var formData = new FormData();
    console.log(4);
    formData.append("data", file);
    console.log(6);
    formData.append("userId",user);
    console.log("data:"+formData.get("data"));
    console.log("userId:"+formData.get("userId"));
    $.ajax({
        url: path + "/saveCloud",
        type: "POST",
        processData: false,
        contentType: false,
        cache: false,
        data: formData,
        success: function (result) {
            if (result.code == 200){
                console.log(result.msg);
                $('#cloud_add').modal("hide");
                cloud_to_page(pagenum);
            }else {
                alert(result.extend.result);
            }

        }
    });
});


$(document).on("click", '.del', function() {
    var path = $("#APP_PATH").val();
    var title = $(this).parents("tr").find("td:eq(1)").text();
    if (confirm("确认删除"+title+"吗？")){
        $.ajax({
            url:path+"/delCloud",
            data:"Id="+$(this).attr("del_id"),
            type:"DELETE",
            success:function () {
                cloud_to_page(pagenum);
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
$(document).on("click", '#cloud_del_btn', function() {
// $("#cloud_del_btn").click(function () {
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
            url:path+"/delCloud",
            data:"Id="+id,
            type:"DELETE",
            success:function () {
                cloud_to_page(pagenum);
            }
        });
    }
});