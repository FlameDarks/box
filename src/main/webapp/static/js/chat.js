
var user;
var path;

// 创建一个Socket实例
var websocket;
//不同浏览器的WebSocket对象类型不同
//alert("ws://" + path + "/ws?uid="+uid);
// if ('WebSocket' in window) {
//     console.log(sessionStorage.getItem("userId"));
//     path = $("#APP_PATH").val();
//     console.log(path);
//     console.log("ws://" + "box_war_exploded" + "/ws");
//     websocket = new WebSocket("ws://localhost:8080/box_war_exploded/ws");
//     console.log("=============WebSocket");
//     //火狐
// } else if ('MozWebSocket' in window) {
//     path = $("#APP_PATH").val();
//     websocket = new MozWebSocket("ws://" + "localhost:8080/box_war_exploded" + "/ws");
//     console.log("=============MozWebSocket");
// } else {
//     path = $("#APP_PATH").val();
    websocket = new SockJS("http://localhost:8080/box_war_exploded" + "/ws/sockjs");
    console.log("=============SockJS");
// }
console.log(websocket);


//onload初始化
$(function(){
    /**
     * 键盘enter事件，用来发送消息
     */
    $("#messageInput").bind("keyup", function (event) {
        if (event.keyCode == 13){
            sendMsg();
        }
    });

    /**
     * 清除聊天窗口的所有内容
     */
    $("#clearBtn").click(function () {
        $("#historyMsg").empty();
        $("#messageInput").focus();
    });

    /**
     * 上传图片发送
     */
    $("#sendImage").bind("change", function () {
        path = $("#APP_PATH").val();
        if (this.files.length != 0){
            $.ajax({
                url: path+"/upload/image",
                type: 'POST',
                cache: false,
                data: new FormData($('#sendImageForm')[0]),
                processData: false,
                contentType: false,
                success:function () {
                    scrollToBottom();
                }
            }).done(function(res) {
                console.log(res);
            }).fail(function(res) {
                console.log(res);
            });
        }
    });
    initEmoji();
    $("#sendImageBtn").click(function () {
        $("#sendImage").trigger("click");
    })

    //初始化时如果有消息，则滚动条到最下面：
    scrollToBottom();

});

function showUserList(userList) {
    $("#getout").empty();
    console.log(userList);
    $.each(userList,function (index,item) {
        console.log(item);
        $("<li></li>").append($("<a></a>").addClass("getout").attr("out",item.userName).append(item.userName)).appendTo("#getout");
    });
}

/**
 * 打开Websocket连接
 * @param event
 */
websocket.onopen = function(event) {
    console.log("WebSocket:已连接");

}

/**
 * 关闭Websocket连接
 */
function closeWebsocket(){
    if (websocket != null) {
        websocket.close();
        websocket = null;
    }
}

/**
 * 监听WebSocket的关闭
 * @param event
 */
websocket.onclose = function(event) {
    showNewMessage("系统消息",null,"连接已断开！");
    console.log("WebSocket:已关闭：Client notified socket has closed",event);
};

/**
 * 监听异常
 * @param event
 */
websocket.onerror = function(event) {
    showNewMessage("系统消息",null,"连接发生异常！");
    console.log("WebSocket:发生错误 ",event);
};

/**
 * 监听消息
 * @param event
 */
websocket.onmessage = function(event) {
    console.log('Client received a message',event);
    var data=$.parseJSON(event.data);
    console.log("WebSocket:收到一条消息",data);
    var userName = $("#loginUserName").val();
    //系统信息
    if(data.chatType==0){
        //上线
        if (data.type == 0){
            showNewUser(data);
            console.log("在线人数"+data.userList.length);
            shows(data);
        }
        // 被禁言
        if (data.type == 1 && data.ban == true){
            showGetOut(data);
            shows(data);
        }
        // 离线
        if(data.type == 2) {
            showUserLogout(data);
            shows(data);
        }

    }
    //聊天文字信息
    if (data.chatType == 1){
        // 如果是自己发的
        if(data.userName==userName){
            showNewMessage("我",null,data.chatContent);

        }else{
            showNewMessage(data.userName,null,data.chatContent);
        }
    }
    // 聊天图片信息
    if (data.chatType == 2){
        // 如果是自己发的
        if(data.userName==userName){
            showNewImage("我",null,data.chatContent);
        }else{
            showNewImage(data.userName,null,data.chatContent);
        }
    }
    // 滚动至最下方
    scrollToBottom();
};

/**
 * 显示被禁言的消息
 * @param data
 */
function showGetOut(data) {
    var date = data.chatTime;
    var user = "系统消息";
    var msg = data.chatContent;
    showNewMessage(user, date, msg);
}

/**
 * 显示在线人物、人数
 * @param data
 */
function shows(data){
    showActiveUserNumber(data.userList.length);
    showLoginUser(data.userList);
    showUserList(data.userList);
}

/**
 * 发送消息
 */
function sendMsg(){
    /**
     * 对象为空
     */
    if(websocket==undefined||websocket==null){
        alert('您的连接已经丢失，请退出聊天重新进入');
        return;
    }
    /**
     * 获取用户要发送的消息内容
     * @type {jQuery|string|undefined}
     */
    var msg=$("#messageInput").val();
    if(msg==""){
        return;
    }else{
        var data={};
        data["chatUserId"]=$("#loginUserId").val();
        data["userName"]=$("#loginUserName").val();
        data["chatContent"]=msg.trim();
        //发送消息
        websocket.send(JSON.stringify(data));
        //发送完消息，清空输入框
        $("#messageInput").val("");
    }
}

/**
 * 显示在线人数列表
 * @param userList
 */
function showLoginUser(userList) {
    $("#LoginUserList").empty();
    console.log(userList);
    $("<a></a>").addClass("list-group-item").append("在线成员").appendTo("#LoginUserList");
    $.each(userList,function (index,item) {
        console.log(item);
        $("<a></a>").addClass("list-group-item").append(item.userName).appendTo("#LoginUserList");
    });
}

/**
 * div滚动条(scrollbar)保持在最底部
 */
function scrollToBottom(){
    //var div = document.getElementById('chatCon');
    var div = document.getElementById('historyMsg');
    div.scrollTop = div.scrollHeight;
}

/**
 * 显示登出信息
 * @param message
 */
function showUserLogout(message) {
    var logoutUser = message.userName;
    var date = message.chatTime;
    var user = "系统消息";
    var msg = logoutUser + "离开了聊天室~";
    showNewMessage(user, date, msg);
}
/**
 * 显示新用户登录的消息
 * @param message
 */
function showNewUser(message) {
    var newUser = message.userName;
    var date = message.sendDate;
    var user = '系统消息';
    var msg = newUser + "加入聊天！";
    showNewMessage(user, date, msg);

}
/**
 * 显示当前在线人数
 * @param number
 */
function showActiveUserNumber(number) {
    $("#status").text(number);
}

/**
 * 格式化时间，参数为null显示当前客户端时间
 * @param dateTime
 * @returns {string}
 */
function formatDate(dateTime) {
    var date = dateTime == null ? new Date() : new Date(dateTime);
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    var hour = date.getHours();
    hour = hour < 10  ? '0'+""+hour : hour;
    var minute = date.getMinutes();
    minute = minute < 10 ? '0'+""+minute : minute;
    var second = date.getSeconds();
    second = second < 10 ? '0'+""+second : second;
    return year + "-" + month + "-" + day +" " + hour + ":" + minute + ":" + second;
}

/**
 * 显示新消息
 * @param user 发消息的用户或者‘系统消息’
 * @param date 发消息的时间（未格式化）
 * @param msg  消息内容
 */
function showNewMessage(user, date, msg) {
    var container = document.getElementById("historyMsg");
    var msgToDisplay = document.createElement('p');
    if (user == "系统消息"){
        msgToDisplay.style.color = 'red';
    }
    var dateTime = formatDate(date);
    msg = showEmoji(msg);
    msgToDisplay.innerHTML = '<span class="timespan">' + dateTime + '</span><br/>[' + user + "] : " + msg;
    container.append(msgToDisplay);
}
/**
 * 正则表达式显示消息中的emoji图片
 * @param message
 * @returns {*} 返回添加emoji图片标签后的消息
 */
function showEmoji(message) {
    path = $("#APP_PATH").val()+"/static/images/emoji/";
    var result = message,
        regrex = /\[EMOJI:\d+\]/g,
        match;
    while (match = regrex.exec(message)){
        var emojiIndex = match[0].slice(7, -1);
        var emojiUrl = path + emojiIndex + ".gif";
        result = result.replace(match[0], '<img src="' + emojiUrl + '"/>');
    }
    return result;
}

/**
 * 显示用户发送的图片
 * @param user 用户名称
 * @param date 用户发送的时间（未格式化）
 * @param url 图片url
 */
function showNewImage(user, date, url) {
    var container = document.getElementById("historyMsg");
    var msgToDisplay = document.createElement('p');
    var dateTime = formatDate(date);
    msgToDisplay.innerHTML = '<span class="timespan">' + dateTime + '</span><br/>[' + user + '] : <br/>' +
        '<img class="img-thumbnail" src="' + url + '"/>';
    container.append(msgToDisplay);
    container.scrollTop = container.scrollHeight;
}
/**
  * 预加载emoji图片
  */
function initEmoji() {
    path = $("#APP_PATH").val();
    var emojiContainer = $("#emojiWrapper");
    var documentFragment = document.createDocumentFragment();
    for (var i = 69; i > 0; i--) {
        var emojiItem = document.createElement("img");
        emojiItem.src = path + "/static/images/emoji/" + i + ".gif";
        emojiItem.title = i;
        documentFragment.appendChild(emojiItem);
    }
    emojiContainer.append(documentFragment);

    $("#emoji").click(function (event) {
        emojiContainer.css("display", "block");
        event.stopPropagation(); //阻止事件的传递，防止body监听到
    });

    $("body").click(function (event) {
        if (event.target != emojiContainer) {
            emojiContainer.css("display", "none");
        }
    });

    $("#emojiWrapper").click(function (event) {
        var target = event.target;
        if (target.nodeName.toLowerCase() == "img") {
            var messageInput = $("#messageInput");
            messageInput.val(messageInput.val() + "[EMOJI:" + target.title + "]");
            messageInput.focus();
        }
    });
}
/**
 * 登出
 */
$(document).on("click", '#exitBtn', function() {
    logout()
});

function logout() {
    path = $("#APP_PATH").val();
    sessionStorage.clear();
    closeWebsocket();
    location.href="/logout";
}

/**
 * 禁言请求
 */
$(document).on("click", '.getout', function() {
    path = $("#APP_PATH").val();
    alert("getout");
    $.ajax({
        url:path+"/getout",
        data: "userName="+$(this).attr("out"),
        type: "POST",
        success:function (result) {
            if (result.code == 100){
                alert("已踢出");
            }
        }
    });
});