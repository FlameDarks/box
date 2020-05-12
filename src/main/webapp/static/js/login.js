//判断是否登陆成功
var success;

$(function () {
    $("#login_btn").click(function(){
        success=false;
        loginuser();
        if (success==true){
            document.loginform.action = "page1";
        }
    });
    $("#user_reg_btn").click(function() {
        reguser();
    });
});

/**
 * 登录请求
 */
function loginuser(){
    var path = $("#APP_PATH").val();
    $.ajax({
        url:path+"/user/selectUser",
        data:$('#login_form').serialize(),
        type:"POST",
        async: false,
        success:function (result) {
            if (result.code == 100){
                var id = result.extend.userId;
                var name = result.extend.userName;
                sessionStorage.setItem("userId",id);
                sessionStorage.setItem("userName",name);
                success = true;
            }else {
                alert(result.extend.error);
            }
        }
    });
}

/**
 * 注册请求
 * @returns {boolean}
 */
function reguser() {
    if (!validate_pwd_form()){
        return false;
    }
    if($("#user_reg_btn").attr("ajax-va")=="error"){
        return false;
    }
    var path = $("#APP_PATH").val();
    $.ajax({
        url: path+"/user/saveUser",
        type: "POST",
        async:false,
        data: $('#user_reg form').serialize(),
        success:function (result) {
            if (result.code == 100){
                alert("注册成功");
                $('#user_reg').modal("hide");
            }else {
                if (undefined != result.extend.errorFields.userName){
                    show_validate_msg("#user_name","error",result.extend.errorFields.userName);
                }
                if (undefined != result.extend.errorFields.userPassword){
                    show_validate_msg("#user_password","error",result.extend.errorFields.userPassword);
                }
                if (undefined != result.extend.errorFields.userPasswords){
                    show_validate_msg("#user_passwords","error",result.extend.errorFields.userPasswords);
                }
                if (result.extend.errorFields=="密码不一样"){
                    show_validate_msg("#user_passwords","error",result.extend.errorFields);
                }
            }

        }
    });
}

/**
 * 注册校验
 * @returns {boolean}
 */
function validate_pwd_form() {
    var userpwd = $("#user_password").val().trim();
    var userpwds = $("#user_passwords").val().trim();
    var regpwd = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z0-9_-]{8,16}$/;
    if (!regpwd.test(userpwd)){
        show_validate_msg("#user_password","error","应包含至少一个大写字母、小写字母和数字的8-16位组合");
        return false;
    }else {
        show_validate_msg("#user_password","success","");
    }
    if (!regpwd.test(userpwds)){
        show_validate_msg("#user_passwords","error","应包含至少一个大写字母、小写字母和数字的8-16位组合");
        return false;
    }else {
        show_validate_msg("#user_passwords","success","");
    }
    if (userpwd!=userpwds){
        show_validate_msg("#user_passwords","error","密码不一样");
        return false;
    }else {
        show_validate_msg("#user_passwords","success","");
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
    var username = $("#user_name").val().trim();
    $.ajax({
        url: path+"/user/checkUser",
        type: "POST",
        data: "user_name="+username,
        async:false,
        success:function (result) {
            if (result.code==100){
                show_validate_msg("#user_name","success","用户名可用");
                $("#user_reg_btn").attr("ajax-va","success");
            }else{
                show_validate_msg("#user_name","error",result.extend.va_msg);
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
 * 注册页面的模态框
 */
$(document).on("click", '#reg_btn', function() {
    reset_form("user_reg_form");
    $('#user_reg').modal({
        backdrop: 'static'
    });
    $("#user_name").focusout(function () {
        check_user_name();
    });
    $("#user_password").focusout(function () {
        validate_pwd_form();
    });
    $("#user_passwords").focusout(function () {
        validate_pwd_form();
    });
});