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
        $("#login_btn").click(function(){
            con();
            if (success){
                document.loginForm.action = "login";
            }else {
                console.log("xxxxxxxxxxxxxxxxxxxx");
            }
        });
    });



    function con(){
        console.log("还没成功");
        $.ajax({
            url:"${APP_PATH}/user",
            data:$('#login_form').serialize(),
            type:"POST",
            async: false,
            success:function (result) {
                var a = result.extend.user_Info;
                sessionStorage.setItem("userId",a);
                alert(result.extend.user_Info);
                success = true;
            },
            fail:function () {
                alert("登陆失败");
            }
        });
    }

    function validate_login_form() {

    }
</script>
<%--<script src="static/js/bootstrap.min.js">--%>
<%--    function login() {--%>
<%--        document.loginForm.action = "chat";--%>
<%--        document.loginForm.submit();--%>
<%--    }--%>

<%--    // function reg() {--%>
<%--    //     document.loginForm.action = "reg.jsp";--%>
<%--    // }--%>
<%--</script>--%>
<h1 align="center">用户登录</h1>
<form action="" name="loginForm" method="post" id="login_form">
    <table align="center">
        <tr>
            <td>用户名:</td>
            <td><input type="text" name="userName" maxlength="10" autocomplete="off"/></td>
        </tr>
        <tr>
            <td>密码:</td>
            <td><input type="password" name="userPassword" maxlength="10" autocomplete="off"/></td>
        </tr>
        <tr>
            <td colspan="1"><input class="btn btn-default" type="submit" id="login_btn" value="登录"/></td>
            <td colspan="2"><input class="btn btn-default" type="submit" id="reg" value="注册"/></td>
        </tr>
    </table>
</form>
</html>
