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
<link rel="stylesheet" href="${APP_PATH}/static/css/bootstrap3.css">
<link rel="stylesheet" type="text/css" href="${APP_PATH}/static/css/login.css">
<%--<link href="https://fonts.googleapis.com/css?family=Montserrat:300,700" rel="stylesheet">--%>
<script type="text/javascript" src="${APP_PATH}/static/js/jquery-3.4.1.js"></script>
<script src="${APP_PATH}/static/js/bootstrap3.js"></script>
<script type="text/javascript" src="${APP_PATH}/static/js/login.js" charset="utf-8"></script>
<input type="hidden" id="APP_PATH" value="${APP_PATH}" />
<body>
<%--注册--%>
<div class="modal fade" id="user_reg" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">注册用户</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="user_reg_form">
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
                    <div class="form-group">
                        <label for="user_passwords" class="col-sm-2 control-label">重复密码</label>
                        <div class="col-sm-10">
                            <input type="password" name="userPasswords" class="form-control" id="user_passwords" placeholder="应包含大写字母、小写字母和数字的8-16位组合" autocomplete="off"/>
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
<div class="home">
    <img class="bgone" src="${APP_PATH}/static/images/image/home_bg.jpg"/>
    <img class="pic" src="${APP_PATH}/static/images/image/home_pic.png" />
    <div class="table">
        <div class="wel">企业网络百宝箱</div>
        <div class="wel1">QI YE WANG LUO BAI BAO XIANG</div>
        <form name="loginform" method="post" id="login_form">
            <div class="user">
                <div id="user_icon" style=""><img src="${APP_PATH}/static/images/image/home_user.png" /></div>
                <input type="text" name="userName" maxlength="10" autocomplete="off" id="userName" placeholder="请输入用户名" />
            </div>
            <div class="password">
                <div id="pwd_icon"><img src="${APP_PATH}/static/images/image/home_pwd.png" /></div>
                <input type="password" name="userPassword" maxlength="16" autocomplete="off" id="userPassword" placeholder="请输入密码"/>
            </div>

            <input class="loginbtn" type="submit" id="login_btn" value="登录">
            <button class="regbtn" type="button" id="reg_btn" >注册</button>
        </form>
    </div>
</div>
</body>
</html>
