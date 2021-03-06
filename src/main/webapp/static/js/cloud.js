﻿
var pagenum;
var user;

$(function () {
    cloud_to_page(1);
});
/**
 * 文件列表当前页
 * @param pn
 */
function cloud_to_page(pn) {
    var path = $("#APP_PATH").val();
    user = sessionStorage.getItem("userId");
    $.ajax({
        url:path+"/cloud/selectCloud",
        data:"pn="+pn+"&userId="+user,
        type:"GET",
        success:function (result) {
            build_cloud_table(result);
            build_cloud_pageinfo(result);
            build_cloud_page(result);
        }
    });
}

/**
 * 显示当页文件
 * @param result
 */
function build_cloud_table(result) {
    $("#cloud_table tbody").empty();
    var cloud = result.extend.cloud_pageInfo.list;
    $.each(cloud,function (index,item) {
        var url = trueUrl(item.cloudId);
        var checkBoxTd = $("<td><input type='checkbox' class='check_item'/></td>")
        checkBoxTd.find("input").attr("check_id",item.cloudId);
        var cloudNameTd = $("<td></td>").append(item.cloudName);
        var cloudTimeTd = $("<td></td>").append(cloud_time(item.cloudTime));
        var downBtn = $("<button></button>").addClass("btn btn-primary btn-sm down down")
            .append($("<span></span>").addClass("glyphicon glyphicon-cloud-download").append("下载"));
        downBtn.attr("url",url).attr("filename",item.cloudName);
        var delBtn = $("<button></button>").addClass("btn btn-danger btn-sm del")
            .append($("<span></span>").addClass("glyphicon glyphicon-trash").append("删除"));
        delBtn.attr("del_id",item.cloudId);
        var btnTd = $("<td></td>").append(downBtn).append(" ").append(delBtn);
        $("<tr></tr>")
            .append(checkBoxTd)
            .append(cloudNameTd)
            .append(cloudTimeTd)
            .append(btnTd)
            .appendTo("#cloud_table tbody");
    });
}

/**
 * 解析显示分页信息
 * @param result
 */
function build_cloud_pageinfo(result) {
    $("#cloud_pageinfo").empty();
    $("#cloud_pageinfo").append("第"+result.extend.cloud_pageInfo.pageNum+"页，总共"+result.extend.cloud_pageInfo.pages+"页，总共"+result.extend.cloud_pageInfo.total+"条记录")
    pagenum = result.extend.cloud_pageInfo.pageNum;
}

/**
 * 解析显示分页条数据
 * @param result
 */
function build_cloud_page(result) {
    $("#cloud_page").empty();
    var ul = $("<ul></ul>").addClass("pagination");
    var first = $("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
    var pre = $("<li></li>").append($("<a></a>").append("&laquo;"));
    //如果没有上一页
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
    //如果没有下一页
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

/**
 * 时间换算
 * @param time
 * @returns {string}
 */
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

/**
 * 添加文件模态框
 */
$('#cloud_add_btn').click(function () {
    $('#cloud_add').modal({
        backdrop: 'static'
    });
});
/**
 * 上传文件按钮
 */
$(document).on("click", '#cloud_save_btn', function() {
    user = sessionStorage.getItem("userId");
    var path = $("#APP_PATH").val();
    var file = $('#clouds_add')[0].files[0];
    var allowsize = 20971520;
    if (file.size>allowsize){
        alert("文件不能超过20MB");
        return false;
    }
    var formData = new FormData();
    formData.append("data", file);
    formData.append("userId",user);
    $.ajax({
        url: path + "/cloud/saveCloud",
        type: "POST",
        processData: false,
        contentType: false,
        cache: false,
        data: formData,
        success: function (result) {
            if (result.code == 100){
                document.getElementById("cloud_add_form").reset();
                $('#cloud_add').modal("hide");
                cloud_to_page(pagenum);
            }else {
                alert("上传出错！");
            }
        }
    });
});

/**
 * 删除文件
 */
$(document).on("click", '.del', function() {
    var path = $("#APP_PATH").val();
    var title = $(this).parents("tr").find("td:eq(1)").text();
    if (confirm("确认删除"+title+"吗？")){
        $.ajax({
            url:path+"/cloud/delCloud",
            data:"Id="+$(this).attr("del_id"),
            type:"DELETE",
            success:function () {
                cloud_to_page(pagenum);
            }
        });
    }
});

/**
 * 获取真实的URL
 * @param Id
 * @returns {*}
 */
function trueUrl(Id) {
    var url;
    var path = $("#APP_PATH").val();
    $.ajax({
        url:path+"/cloud/download",
        data:"Id="+Id,
        type:"POST",
        async:false,
    })
    .done(function(result) {
        url = result.extend.url;
    });
    return url;
}

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
$(document).on("click", '#cloud_del_btn', function() {
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
            url:path+"/cloud/delCloud",
            data:"Id="+id,
            type:"DELETE",
            success:function () {
                cloud_to_page(pagenum);
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
                build_cloud_table(result);
                build_cloud_pageinfo(result);
                build_cloud_page(result);
            }
        });
    }
}

/**
 * 获取 Blob
 * @param  {String} url 目标文件地址
 * @return {Promise}
 */
function getBlob(url) {
    return new Promise(resolve => {// Promise异步处理
        const xhr = new XMLHttpRequest();

        xhr.open('GET', url, true);//请求方式，地址，是否异步
        xhr.responseType = 'blob';
        xhr.onload = () => {
            if (xhr.status === 200) {
                resolve(xhr.response);//resolve成功，response是一个包含二进制数据的Blob对象
            }
        };
        xhr.send();
    });
}

/**
 * 保存
 * @param  {Blob} blob
 * @param  {String} filename 想要保存的文件名称
 */
function saveAs(blob, filename) {
    if (window.navigator.msSaveOrOpenBlob) {
        navigator.msSaveBlob(blob, filename);//允许用户在客户端上保存文件，提供保存
    } else {
        const link = document.createElement('a');//创建一个按钮
        const body = document.querySelector('body');//获取body元素

        link.href = window.URL.createObjectURL(blob);//创建一个新的URL对象
        link.download = filename;//设定文件名

        link.style.display = 'none';//隐藏元素
        body.appendChild(link);//添加列表项

        link.click();
        body.removeChild(link);

        window.URL.revokeObjectURL(link.href);//释放URL对象
    }
}

/**
 * 下载
 * @param  {String} url 目标文件地址
 * @param  {String} filename 想要保存的文件名称
 */
function download(url, filename) {
    getBlob(url).then(blob => {
        saveAs(blob, filename);
    });
}

/**
 * 下载点击按钮
 */
$(document).on("click", '.down', function() {
    download($(this).attr("url"),$(this).attr("filename"));
});