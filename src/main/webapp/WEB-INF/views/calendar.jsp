<%--
  Created by IntelliJ IDEA.
  User: FlameDark
  Date: 2020/5/29
  Time: 13:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>日程表</title>
</head>
<%
    pageContext.setAttribute("APP_PATH",request.getContextPath());
%>
<link rel="stylesheet" href="${APP_PATH}/static/css/bootstrap.css">
<link rel="stylesheet" href="${APP_PATH}/static/css/calendar.css">
<link rel="stylesheet" href="${APP_PATH}/static/css/all.css">
<script type="text/javascript" src="${APP_PATH}/static/js/jquery-3.4.1.js"></script>
<script type="text/javascript" src="${APP_PATH}/static/js/bootstrap.js"></script>
<script type="text/javascript" src="${APP_PATH}/static/js/calendar.js"></script>
<script type="text/javascript" src="${APP_PATH}/static/js/change.js"></script>
<body background="${APP_PATH}/static/images/image/bg.jpg">
<input class="bg" type="hidden" id="APP_PATH" value="${APP_PATH}" />
<div class="container">
    <%--    标题--%>
    <div class="row">
        <div class="col-md-11">
            <h1 align="center">企业网络百宝箱</h1>
        </div>
        <div class="userInfo">
            <div class="dropdown">
                <button class="btn btn-default dropdown-toggle userBtn" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                    ${loginUser.userName}
                </button>
                <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenu1">
                    <li><a id="changeBtn" data-toggle="modal"  data-target="#changepwd">修改密码</a></li>
                    <li><a id="exitBtn">登出</a></li>
                </ul>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-9">
            <ul class="nav nav-pills">
                <li role="presentation"><a href="${APP_PATH}/page8">地图</a></li>
                <li role="presentation"><a href="${APP_PATH}/page1">记事本</a></li>
                <li role="presentation"><a href="${APP_PATH}/page2">通讯录</a></li>
                <li role="presentation"><a href="${APP_PATH}/page3">收藏夹</a></li>
                <li role="presentation" class="active"><a href="${APP_PATH}/page7">日程表</a></li>
                <li role="presentation"><a href="${APP_PATH}/page4">文件箱</a></li>
                <li role="presentation"><a href="${APP_PATH}/page5">聊天室</a></li>
                <li role="presentation"><a href="${APP_PATH}/page9">图片库</a></li>
                <shiro:hasRole name="admin">
                    <li role="presentation"><a href="${APP_PATH}/page6">用户管理</a></li>
                    <li role="presentation"><a href="${APP_PATH}/page10">图片管理</a></li>
                </shiro:hasRole>
            </ul>
        </div>
        <div>
            <div class="row">
                <select class="form-control col-md-1" id="check">
                    <option value="1">标题</option>
                    <option value="2">内容</option>
                </select>
                <input class="col-md-1" placeholder="请输入" id="selectInput">
                <button class="btn btn-primary col-md-1" id="selectBtn" select="6">搜索</button>
            </div>
        </div>
    </div>
    <%--    按钮--%>
    <div class="row">
        <div class="col-md-3 col-md-offset-9">
            <button class="btn btn-primary" id="calendar_add_btn" data-toggle="modal"  data-target="#calendar_add">新增</button>
            <button class="btn btn-danger" id="calendar_del_btn">删除</button>
        </div>
    </div>
    <%--    表格--%>
    <div class="row">
        <div class="col-md-12">
            <table class="table table-hover" id="calendar_table">
                <thead>
                <tr>
                    <th class="col-md-2">
                        <input type="checkbox" id="check_all">
                    </th>
                    <th class="col-md-6">标题</th>
                    <th class="col-md-2">日期</th>
                    <th class="col-md-2">操作</th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
    </div>
    <%--    页码--%>
    <div class="row">
        <div class="col-md-3 col-md-offset-3" id="calendar_pageinfo"></div>
        <div class="col-md-6" id="calendar_page"></div>
    </div>
</div>
<%--修改密码--%>
<div class="modal fade" id="changepwd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">修改密码</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="change_pwd_form">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">用户名</label>
                        <div class="col-sm-10">
                            <p class="form-control-static">${loginUser.userName}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="oldpwd" class="col-sm-2 control-label">原密码</label>
                        <div class="col-sm-10">
                            <input type="password" name="userPassword" class="form-control" id="oldpwd" placeholder="原密码">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="newpwd" class="col-sm-2 control-label">新密码</label>
                        <div class="col-sm-10">
                            <input type="password" name="userPassword" class="form-control" id="newpwd" placeholder="新密码">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="newpwds" class="col-sm-2 control-label">重复密码</label>
                        <div class="col-sm-10">
                            <input type="password" name="userPasswords" class="form-control" id="newpwds" placeholder="重复新密码">
                            <span class="help-block"></span>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="changepwd_save_btn">保存</button>
            </div>
        </div>
    </div>
</div>

<%--新增--%>
<div class="modal fade" id="calendar_add" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加日程表</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="calendar_add_form">
                    <div class="form-group">
                        <label for="calendarTitle_add" class="col-sm-2 control-label">标题</label>
                        <div class="col-sm-10">
                            <input type="text" name="calendarTitle" class="form-control" id="calendarTitle_add" placeholder="标题">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="calendarTime_add" class="col-sm-2 control-label">日期</label>
                        <div class="col-sm-10" id="calendarTime_add">
                            <select class="col-md-3" id="year">
                                <option value="2020">2020</option>
                                <option value="2020">2021</option>
                                <option value="2020">2022</option>
                            </select>
                            <select class="col-md-3" id="month">
                                <option value="01">1</option>
                                <option value="02">2</option>
                                <option value="03">3</option>
                                <option value="04">4</option>
                                <option value="05">5</option>
                                <option value="06">6</option>
                                <option value="07">7</option>
                                <option value="08">8</option>
                                <option value="09">9</option>
                                <option value="10">10</option>
                                <option value="11">11</option>
                                <option value="12">12</option>
                            </select>
                            <select class="col-md-3" id="day">
                                <option value="01">1</option>
                                <option value="02">2</option>
                                <option value="03">3</option>
                                <option value="04">4</option>
                                <option value="05">5</option>
                                <option value="06">6</option>
                                <option value="07">7</option>
                                <option value="08">8</option>
                                <option value="09">9</option>
                                <option value="10">10</option>
                                <option value="11">11</option>
                                <option value="12">12</option>
                                <option value="13">13</option>
                                <option value="14">14</option>
                                <option value="15">15</option>
                                <option value="16">16</option>
                                <option value="17">17</option>
                                <option value="18">18</option>
                                <option value="19">19</option>
                                <option value="20">20</option>
                                <option value="21">21</option>
                                <option value="22">22</option>
                                <option value="23">23</option>
                                <option value="24">24</option>
                                <option value="25">25</option>
                                <option value="26">26</option>
                                <option value="27">27</option>
                                <option value="28">28</option>
                                <option value="29">29</option>
                                <option value="30">30</option>
                                <option value="31">31</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="calendarContent_add" class="col-sm-2 control-label">内容</label>
                        <div class="col-sm-10">
                            <textarea name="calendarContent" class="form-control" rows="5" id="calendarContent_add" placeholder="内容"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="calendar_save_btn">保存</button>
            </div>
        </div>
    </div>
</div>

<%--修改--%>
<div class="modal fade" id="calendar_update" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">修改日程表</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="calendarTitle_update" class="col-sm-2 control-label">标题</label>
                        <div class="col-sm-10">
                            <input type="text" name="calendarTitle" class="form-control" id="calendarTitle_update" placeholder="标题">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="calendarTime_add" class="col-sm-2 control-label">日期</label>
                        <div class="col-sm-10" id="calendarTime_update">
                            <select class="col-md-3" id="years">
                                <option value="2020">2020</option>
                                <option value="2020">2021</option>
                                <option value="2020">2022</option>
                            </select>
                            <select class="col-md-3" id="months">
                                <option value="01">1</option>
                                <option value="02">2</option>
                                <option value="03">3</option>
                                <option value="04">4</option>
                                <option value="05">5</option>
                                <option value="06">6</option>
                                <option value="07">7</option>
                                <option value="08">8</option>
                                <option value="09">9</option>
                                <option value="10">10</option>
                                <option value="11">11</option>
                                <option value="12">12</option>
                            </select>
                            <select class="col-md-3" id="days">
                                <option value="01">1</option>
                                <option value="02">2</option>
                                <option value="03">3</option>
                                <option value="04">4</option>
                                <option value="05">5</option>
                                <option value="06">6</option>
                                <option value="07">7</option>
                                <option value="08">8</option>
                                <option value="09">9</option>
                                <option value="10">10</option>
                                <option value="11">11</option>
                                <option value="12">12</option>
                                <option value="13">13</option>
                                <option value="14">14</option>
                                <option value="15">15</option>
                                <option value="16">16</option>
                                <option value="17">17</option>
                                <option value="18">18</option>
                                <option value="19">19</option>
                                <option value="20">20</option>
                                <option value="21">21</option>
                                <option value="22">22</option>
                                <option value="23">23</option>
                                <option value="24">24</option>
                                <option value="25">25</option>
                                <option value="26">26</option>
                                <option value="27">27</option>
                                <option value="28">28</option>
                                <option value="29">29</option>
                                <option value="30">30</option>
                                <option value="31">31</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="calendarContent_update" class="col-sm-2 control-label">内容</label>
                        <div class="col-sm-10">
                            <textarea name="calendarContent" class="form-control" rows="5" id="calendarContent_update" placeholder="内容"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="calendar_update_btn">修改</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
