<%--
  Created by IntelliJ IDEA.
  User: FlameDark
  Date: 2020/3/28
  Time: 11:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>欢迎</title>
</head>
<%
    pageContext.setAttribute("APP_PATH",request.getContextPath());
%>
<link rel="stylesheet" href="${APP_PATH}/static/css/bootstrap.min.css">
<script type="text/javascript" src="${APP_PATH}/static/js/jquery-3.4.1.js"></script>
<script src="${APP_PATH}/static/js/bootstrap.min.js"></script>
<script>

    var success = false;
    $(function () {
        // $(document).on("click", '#login_btn', function() {
        $("#login_btn").click(function(){
            loginuser();
            if (success){
                document.loginform.action = "login";
            }else {
                console.log("xxxxxxxxxxxxxxxxxxxx");
            }
        });
        $(document).on("click", '#reg_btn', function() {
        // $('#reg_btn').click(function() {
            $('#user_reg').modal({
                backdrop: 'static'
            });
        });
        $("#user_reg_btn").click(function() {
            reguser();
        });
    });
//      登录请求
    function loginuser(){
        // if (!validate_login_form()){
        //     return false;
        // }
        $.ajax({
            url:"${APP_PATH}/user",
            data:$('#login_form').serialize(),
            type:"POST",
            async: false,
            success:function (result) {
                var id = result.extend.user_Info;
                sessionStorage.setItem("userId",id);
                // alert("找到用户："+result.extend.user_Info);
                success = true;
            }
        });
    }
//      注册请求
    function reguser() {
        $("#user_name").empty();
        $("#user_password").empty();
        if (!validate_reg_form()){
            return false;
        }
        $.ajax({
            url: "${APP_PATH}/saveuser",
            type: "POST",
            async:false,
            data: $('#user_reg form').serialize(),
            success:function () {
                alert("注册成功");
                $('#user_reg').modal("hide");
            }
        });
    }
//      注册校验
    function validate_reg_form() {
        var username = $("#user_name").val();
        var userpwd = $("#user_password").val();
        var regname = /(^[a-zA-Z0-9_-]{6,10})|(^[\u2E80-\u9FFF]{3,5})/;
        var regpwd = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z0-9_-]{8,16}$/;
        if (!regname.test(username)){
            // alert("用户名不合法，请设置6-10个英文和数字组合或者3-5个汉字");
            show_validate_msg("#user_name","error","6-10个英文和数字组合或者3-5个汉字");
            // $("#user_name").parent().addClass("has-error");
            // $("#user_name").next("span").text("6-10个英文和数字组合或者3-5个汉字");
            return false;
        }else {
            show_validate_msg("#user_name","success","");
            // $("#user_name").parent().addClass("has-success");
            // $("#user_name").next("span").text("");
        }
        if (!regpwd.test(userpwd)){
            // alert("密码不合法，应包含至少一个大写字母、小写字母和数字的8-16位组合");
            show_validate_msg("#user_password","error","应包含至少一个大写字母、小写字母和数字的8-16位组合");
            // $("#user_password").parent().addClass("has-error");
            // $("#user_password").next("span").text("应包含至少一个大写字母、小写字母和数字的8-16位组合");
            return false;
        }else {
            show_validate_msg("#user_password","success","");
            // $("#user_password").parent().addClass("has-success");
            // $("#user_password").next("span").text("");
        }
        alert("成功");
        return false;
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
</script>
<h1 align="center">用户登录</h1>
<%--注册--%>
<div class="modal fade" id="user_reg" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">注册用户</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="user_name" class="col-sm-2 control-label">用户名</label>
                        <div class="col-sm-10">
                            <input type="text" name="userName" class="form-control" id="user_name" placeholder="6-10个英文和数字组合或者3-5个汉字" autocomplete="off"/>
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="user_password" class="col-sm-2 control-label">密码</label>
                        <div class="col-sm-10">
                            <input type="password" name="userPassword" class="form-control" id="user_password" placeholder="应包含大写字母、小写字母和数字的8-16位组合" autocomplete="off"/>
                            <span class="help-block"></span>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="user_reg_btn">注册</button>
            </div>
        </div>
    </div>
</div>
<form action="" name="loginform" method="post" id="login_form">
    <table align="center">
        <tr>
            <td>用户名:</td>
            <td><input type="text" name="userName" maxlength="10" autocomplete="off" id="userName"/></td>
        </tr>
        <tr>
            <td>密码:</td>
            <td><input type="password" name="userPassword" maxlength="10" autocomplete="off" id="userPassword"/></td>
        </tr>
        <tr>
            <td colspan="1"><input class="btn btn-default" type="submit" id="login_btn" value="登录"></td>
            <td colspan="2"><button class="btn btn-default" type="button" id="reg_btn">注册</button></td>
        </tr>
    </table>
</form>
</html>
