$(function () {
    $("#changepwd_save_btn").click(function() {
        changepwd();
    });
    $("#oldpwd").focusout(function () {
        // $(document).on("change", '#user_name', function() {
        check_user_pwd();
    });
    $("#newpwd").focusout(function () {
        validate_pwd_form();
    });
    $("#newpwds").focusout(function () {
        validate_pwd_form();
    });
});

//      修改密码请求
function changepwd() {
    if (!validate_pwd_form()){
        return false;
    }
    if($("#changepwd_save_btn").attr("ajax-va")=="error"){
        return false;
    }
    var userId = sessionStorage.getItem("userId");
    var userName = sessionStorage.getItem("userName");
    var userPassword = $("#newpwd").val().trim();
    var userPasswords = $("#newpwds").val().trim();
    var data = "userId="+userId+"&userName="+userName+"&userPassword="+userPassword+"&userPasswords="+userPasswords;
    var path = $("#APP_PATH").val();
    $.ajax({
        url: path+"/savepwd",
        type: "POST",
        async:false,
        data: data,
        success:function (result) {
            if (result.code == 100){
                alert("注册成功");
                $('#changepwd').modal("hide");
                logout();
            }else {
                // console.log(result);
                if (undefined != result.extend.errorFields.userPassword){
                    show_validate_msg("#newpwd","error",result.extend.errorFields.userPassword);
                }
                if (undefined != result.extend.errorFields.userPasswords){
                    show_validate_msg("#newpwds","error",result.extend.errorFields.userPasswords);
                }
                if (result.extend.errorFields=="密码不一样"){
                    show_validate_msg("#newpwds","error",result.extend.errorFields);
                }
            }

        }
    });
}
//      注册校验
function validate_pwd_form() {
    var newpwd = $("#newpwd").val().trim();
    var newpwds = $("#newpwds").val().trim();
    var regpwd = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z0-9_-]{8,16}$/;
    if (!regpwd.test(newpwd)){
        show_validate_msg("#newpwd","error","应包含至少一个大写字母、小写字母和数字的8-16位组合");
        return false;
    }else {
        show_validate_msg("#newpwd","success","");
    }
    if (!regpwd.test(newpwds)){
        show_validate_msg("#newpwds","error","应包含至少一个大写字母、小写字母和数字的8-16位组合");
        return false;
    }else {
        show_validate_msg("#newpwds","success","");
    }
    if (newpwd!=newpwds){
        show_validate_msg("#newpwds","error","请重新输入密码");
        return false;
    }else {
        show_validate_msg("#newpwds","success","");
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
// 校验原密码
function check_user_pwd() {
    var path = $("#APP_PATH").val();
    var userId = sessionStorage.getItem("userId");
    var oldpwd = $("#oldpwd").val().trim();
    $.ajax({
        url: path+"/checkpwd",
        type: "POST",
        data: "userId="+userId+"&userPassword="+oldpwd,
        success:function (result) {
            if (result.code==100){
                show_validate_msg("#oldpwd","success","原密码正确");
                $("#changepwd_save_btn").attr("ajax-va","success");
            }else{
                show_validate_msg("#oldpwd","error",result.extend.va_msg);
                $("#changepwd_save_btn").attr("ajax-va","error");
            }
        }
    });
}
// 重置提示信息
function reset_form(ele) {
    // $(ele)[0].reset;
    document.getElementById(ele).reset();
    var eles = "#"+ele;
    $(eles).find("*").removeClass("has-error has-success");
    $(eles).find(".help-block").text("");
}

// 登出
$(document).on("click", '#exitBtn', function() {
    // $("#exitBtn").on("onclick",function(){
    logout();
});

function logout() {
    path = $("#APP_PATH").val();
    sessionStorage.clear();

    location.href=path;
}

