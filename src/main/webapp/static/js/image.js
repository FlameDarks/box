
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
    var main = $("#isMain").val();
    $.ajax({
        url:path+"/image/selectImage",
        data:"pn="+pn+"&main="+main,
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
 * 显示当页图片库
 * @param result
 */
function build_image_table(result) {
    $("#allImage").empty();
    var image = result.extend.image_pageInfo.list;
    $.each(image,function (index,item) {
        var imageUrlTd = $("<a></a>").addClass("pointimg").attr("href",item.imageUrl+"?download=0").append($("<img>").attr("src",item.imageUrl+"?download=0"));
        var markBtnTd = $("<button></button>").attr("style","margin-left:0.2cm").addClass("btn btn-primary imagemarks").attr("mark_id",item.imageId).text("收藏");
        var likeBtnTd = $("<p></p>").append($("<button></button>").addClass("btn btn-primary imagelikes imagemarks").attr("like_id",item.imageId).text("点赞")).append(markBtnTd);
        var imageNameTd = $("<div></div>").addClass("caption").append($("<p></p>").text(item.imageName)).append($("<p></p>").text("点赞数："+item.imageLike)).append(likeBtnTd);
        var all = $("<div></div>").addClass("thumbnail").append(imageUrlTd).append(imageNameTd);
        $("<div></div>")
            .addClass("col-sm-6 col-md-4")
            .append(all)
            .appendTo("#allImage");
    });
}

/**
 * 解析显示分页信息
 * @param result
 */
function build_image_pageinfo(result) {
    $("#image_pageinfo").empty();
    $("#image_pageinfo").append("第"+result.extend.image_pageInfo.pageNum+"页，总共"+result.extend.image_pageInfo.pages+"页，总共"+result.extend.image_pageInfo.total+"条记录")
    pagenum = result.extend.image_pageInfo.pageNum;
}

/**
 * 解析显示分页条数据
 * @param result
 */
function build_image_page(result) {
    $("#image_page").empty();
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
    navigation.appendTo("#image_page");
}

/**
 * 添加图片模态框
 */
$('#image_add_btn').click(function () {
    $('#image_add').modal({
        backdrop: 'static'
    });
});
/**
 * 保存图片的按钮
 */
$(document).on("click", '#image_save_btn', function() {
    var path = $("#APP_PATH").val();
    var file = $('#imageFile_add')[0].files[0];
    var allowsize = 20971520;
    if (file.size>allowsize){
        alert("文件不能超过20MB");
        return false;
    }
    var formData = new FormData();
    formData.append("data", file);
    $.ajax({
        url: path + "/image/saveImage",
        type: "POST",
        processData: false,
        contentType: false,
        cache: false,
        data: formData,
        success: function (result) {
            if (result.code == 100){
                document.getElementById("image_add_form").reset();
                $('#image_add').modal("hide");
                image_to_page(pagenum);
            }else {
                alert("上传出错！");
            }
        }
    });
});

/**
 * 点赞按钮
 */
$(document).on("click", '.imagelikes', function() {
    var path = $("#APP_PATH").val();
    user = sessionStorage.getItem("userId");
    var like = $(this).attr("like_id");
    $.ajax({
        url:path + "/image/isLike",
        data:"like="+like+"&user="+user,
        type:"POST",
        async:false,
        success: function (result) {
            if (result.code == 100){
                alert(result.extend.msg);
                image_to_page(pagenum);
            }else {
                alert(result.extend.msg);
            }
        }
    });
});
/**
 * 收藏按钮
 */
$(document).on("click", '.imagemarks', function() {
    var path = $("#APP_PATH").val();
    user = sessionStorage.getItem("userId");
    var like = $(this).attr("mark_id");
    $.ajax({
        url:path + "/image/isMark",
        data:"mark="+like+"&user="+user,
        type:"POST",
        async:false,
        success: function (result) {
            if (result.code == 100){
                alert(result.extend.msg);
                image_to_page(pagenum);
            }else {
                alert(result.extend.msg);
            }
        }
    });
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
            data:"check="+check+"&data="+content+"&type="+type+"&main=1",
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