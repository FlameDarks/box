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
<link rel="stylesheet" href="static/css/bootstrap.min.css">
<script type="text/javascript" src="static/js/jquery-3.4.1.js"></script>
<script>
    function login() {
        document.loginForm.action = "login";
        document.loginForm.submit();
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
<form action="" name="loginForm" method="post">
    <table align="center">
        <tr>
            <td>用户名:</td>
            <td><input type="text" name="username" maxlength="10"/></td>
        </tr>
        <tr>
            <td>密码:</td>
            <td><input type="password" name="password" maxlength="10"/></td>
        </tr>
        <tr>
            <td colspan="1"><input class="btn btn-default" type="submit" onClick="login()" value="登录"/></td>
            <td colspan="2"><input class="btn btn-default" type="submit" onClick="reg()" value="注册"/></td>
        </tr>
    </table>
</form>
</html>
