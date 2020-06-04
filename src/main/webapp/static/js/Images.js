
var pagenum;

$(function () {
    image_to_page(1);
});

/**
 * 图片库列表当前页
 * @param pn
 */
function image_to_page(pn) {
    var path = $("#APP_PATH").val();
    $.ajax({
        url:path+"/image/selectImage",
        data:"pn="+pn+"&main="+"yes",
        type:"GET",
        async: false,
        success:function (result) {
            build_image_table(result);
            build_image_pageinfo(result);
            build_image_page(result);
        }
    });
}

/**
 * 显示当页图片库列表
 * @param result
 */
function build_image_table(result) {
    $("#images_table tbody").empty();
    var image = result.extend.image_pageInfo.list;
    console.log(image);
    $.each(image,function (index,item) {
        var checkBoxTd = $("<td><input type='checkbox' class='check_item'/></td>")
        checkBoxTd.find("input").attr("check_id",item.imageId);
        var imageIdTd = $("<td></td>").append(item.imageId);
        var imageNameTd = $("<td></td>").append(item.imageName);
        var delBtn = $("<button></button>").addClass("btn btn-danger btn-sm del")
            .append($("<span></span>").addClass("glyphicon glyphicon-trash").append("删除"));
        delBtn.attr("del_id",item.imageId);
        var btnTd = $("<td></td>").append(delBtn)
        console.log(2);
        $("<tr></tr>")
            .append(checkBoxTd)
            .append(imageIdTd)
            .append(imageNameTd)
            .append(btnTd)
            .appendTo("#images_table tbody");
    });
}

/**
 * 解析显示分页信息
 * @param result
 */
function build_image_pageinfo(result) {
    $("#images_pageinfo").empty();
    $("#images_pageinfo").append("第"+result.extend.image_pageInfo.pageNum+"页，总共"+result.extend.image_pageInfo.pages+"页，总共"+result.extend.image_pageInfo.total+"条记录")
    pagenum = result.extend.image_pageInfo.pageNum;
}

/**
 * 解析显示分页条数据
 * @param result
 */
function build_image_page(result) {
    $("#images_page").empty();
    var ul = $("<ul></ul>").addClass("pagination");
    var first = $("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
    var pre = $("<li></li>").append($("<a></a>").append("&laquo;"));
    //如果没有上一页
    if (result.extend.image_pageInfo.hasPreviousPage == false){
        first.addClass("disabled");
        pre.addClass("disabled");
    }else {
        first.click(function () {
            image_to_page(1);
        });
        pre.click(function () {
            image_to_page(result.extend.image_pageInfo.pageNum-1);
        });
    }

    var next = $("<li></li>").append($("<a></a>").append("&raquo;"));
    var last = $("<li></li>").append($("<a></a>").append("尾页").attr("href","#"));
    //如果没有下一页
    if (result.extend.image_pageInfo.hasNextPage == false){
        next.addClass("disabled");
        last.addClass("disabled");
    }else {
        next.click(function () {
            image_to_page(result.extend.image_pageInfo.pageNum+1);
        });
        last.click(function () {
            image_to_page(result.extend.image_pageInfo.pages);
        });
    }

    ul.append(first).append(pre);
    $.each(result.extend.image_pageInfo.navigatepageNums,function (index,item) {
        var num = $("<li></li>").append($("<a></a>").append(item));
        if (result.extend.image_pageInfo.pageNum == item){
            num.addClass("active");
        }
        num.click(function () {
            image_to_page(item);
        });
        ul.append(num);
    });
    ul.append(next).append(last);
    var navigation = $("<nav></nav>").append(ul);
    navigation.appendTo("#images_page");
}

/**
 * 添加图片保存按钮
 */
$(document).on("click", '#images_save_btn', function() {
    var path = $("#APP_PATH").val();
    var file = $('#imagesFile_add')[0].files[0];
    var allowsize = 20971520;
    if (file.size>allowsize){
        alert("文件不能超过20MB");
        return false;
    }
    var formData = new FormData();
    formData.append("data", file);
    console.log(123);
    $.ajax({
        url: path + "/image/saveImage",
        type: "POST",
        processData: false,
        contentType: false,
        cache: false,
        data: formData,
        success: function (result) {
            if (result.code == 100){
                document.getElementById("images_add_form").reset();
                $('#images_add').modal("hide");
                image_to_page(pagenum);
            }else {
                alert("上传出错！");
            }
        }
    });
});

/**
 * 删除图片按钮
 */
$(document).on("click", '.del', function() {
    var path = $("#APP_PATH").val();
    var title = $(this).parents("tr").find("td:eq(1)").text();
    if (confirm("确认删除"+title+"吗？")){
        $.ajax({
            url:path+"/image/delImage",
            data:"Id="+$(this).attr("del_id"),
            type:"DELETE",
            success:function () {
                image_to_page(pagenum);
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
$(document).on("click", '#images_del_btn', function() {
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
            url:path+"/image/delImage",
            data:"Id="+id,
            type:"DELETE",
            success:function () {
                image_to_page(pagenum);
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
    var content = $("#selectInput").val().trim();
    var type = $("#selectBtn").attr("select");
    var check = $("#check").val();
    if (content!=null || content!=undefined){
        $.ajax({
            data:"check="+check+"&data="+content+"&type="+type,
            url:path+"/select",
            type:"POST",
            success:function (result) {
                build_image_table(result);
                build_image_pageinfo(result);
                build_image_page(result);
            }
        });
    }
}