var success;
$(function () {
    // $(document).on("click", '#login_btn', function() {
    $("#login_btn").click(function(){
        success=false;
        loginuser();
        if (success==true){
            document.loginform.action = "page1";
        }else {
            alert("登陆失败");
        }
    });
    $(document).on("click", '#reg_btn', function() {
        // $('#reg_btn').click(function() {
        // $("#user_reg_form")[0].reset;
        reset_form("user_reg_form");
        $('#user_reg').modal({
            backdrop: 'static'
        });
    });
    $("#user_reg_btn").click(function() {
        reguser();
    });
    $("#user_name").focusout(function () {
        // $(document).on("change", '#user_name', function() {
        check_user_name();
    });
    $("#user_password").focusout(function () {
        validate_reg_form();
    });
    $("#user_passwords").focusout(function () {
        validate_reg_form();
    });
});
//      登录请求
function loginuser(){
    // if (!validate_login_form()){
    //     return false;
    // }
    var path = $("#APP_PATH").val();
    $.ajax({
        url:path+"/user",
        data:$('#login_form').serialize(),
        type:"POST",
        async: false,
        success:function (result) {
            // alert("找到用户："+result.extend.user_Info);
            if (result.code == 100){
                var id = result.extend.userId;
                var name = result.extend.userName;
                sessionStorage.setItem("userId",id);
                sessionStorage.setItem("userName",name);
                success = true;
            }
        }
    });
}
//      注册请求
function reguser() {
    if (!validate_reg_form()){
        return false;
    }
    if($("#user_name").attr("ajax-va")=="error"){
        return false;
    }
    var path = $("#APP_PATH").val();
    $.ajax({
        url: path+"/saveuser",
        type: "POST",
        async:false,
        data: $('#user_reg form').serialize(),
        success:function (result) {
            if (result.code == 100){
                alert("注册成功");
                $('#user_reg').modal("hide");
            }else {
                // console.log(result);
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
//      注册校验
function validate_reg_form() {
    var username = $("#user_name").val().trim();
    var userpwd = $("#user_password").val().trim();
    var userpwds = $("#user_passwords").val().trim();
    var regname = /(^[a-zA-Z0-9_-]{6,10})|(^[\u2E80-\u9FFF]{3,5})/;
    var regpwd = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z0-9_-]{8,16}$/;
    if (!regname.test(username)){
        show_validate_msg("#user_name","error","6-10个英文和数字组合或者3-5个汉字");
        return false;
    }else {
        show_validate_msg("#user_name","success","");
    }
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
        show_validate_msg("#user_passwords","error","请重新输入密码");
        return false;
    }else {
        show_validate_msg("#user_passwords","success","");
    }
    return true;
}

// 注册校验提示信息
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
function check_user_name() {
    var path = $("#APP_PATH").val();
    var username = $("#user_name").val().trim();
    $.ajax({
        url: path+"/checkuser",
        type: "POST",
        data: "user_name="+username,
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

function reset_form(ele) {
    // $(ele)[0].reset;
    document.getElementById(ele).reset();
    var eles = "#"+ele;
    $(eles).find("*").removeClass("has-error has-success");
    $(eles).find(".help-block").text("");
}

//     登录校验
// function validate_login_form() {
//     var username = $("#username").val();
//     var userpwd = $("#userpassword").val();
//     var regname = /(^[a-zA-Z0-9_-]{6,10})|(^[\u2E80-\u9FFF]{3,5})/;
//     var regpwd = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z0-9_-]{8,16}$/
//     if (!regname.test(username) || !regpwd.test(userpwd)){
//         alert("用户名或密码不合法");
//         return false;
//     }
//     alert("成功");
//     return true;
// }