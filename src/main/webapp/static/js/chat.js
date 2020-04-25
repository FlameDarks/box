
var user;
var path;

//接收人编号
var to="-1";

// 创建一个Socket实例
//参数为URL，ws表示WebSocket协议。onopen、onclose和onmessage方法把事件连接到Socket实例上。每个方法都提供了一个事件，以表示Socket的状态。
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
// console.log("ws://" + path + "ws");

//打开Socket,
websocket.onopen = function(event) {
    console.log("WebSocket:已连接");

}

//关闭Websocket连接
function closeWebsocket(){
    if (websocket != null) {
        websocket.close();
        websocket = null;
    }
}

// 监听消息
//onmessage事件提供了一个data属性，它可以包含消息的Body部分。消息的Body部分必须是一个字符串，可以进行序列化/反序列化操作，以便传递更多的数据。
websocket.onmessage = function(event) {
    console.log('Client received a message',event);
    //var data=JSON.parse(event.data);
    var data=$.parseJSON(event.data);
    console.log("WebSocket:收到一条消息",data);
    var userName = $("#loginUserName").val();
    //2种推送的消息
    //1.用户聊天信息：发送消息触发
    //2.系统消息：登录和退出触发

    //判断是否是欢迎消息（没用户编号的就是欢迎消息）
    //data.chatUserId==undefined||data.chatUserId==null||data.chatUserId==""
    if(data.chatType==0){
        //===系统消息
        // $("#contentUl").append("<li><b>"+data.date+"</b><em>系统消息：</em><span>"+data.text+"</span></li>");
        // //刷新在线用户列表
        // $("#chatOnline").val(data.userList.length);
        // $("#chatUserList").empty();
        // $(data.userList).each(function(){
        //     $("#chatUserList").append("<li>"+this.userName+"</li>");
        // });

        if (data.type){
            showNewUser(data);
            console.log("在线人数"+data.userList.length);
            showActiveUserNumber(data.userList.length);
            showLoginUser(data.userList);
        }else {
            showUserLogout(data);
            showActiveUserNumber(data.userList.length);
        }
    }
    if (data.chatType == 1){
        //===普通消息
        //处理一下个人信息的显示：
        if(data.userName==userName){
            showNewMessage("我",null,data.chatContent);
            // $("#contentUl").append("<li><span  style='display:block; float:right;'><em>"+data.userName+"</em><span>"+data.text+"</span><b>"+data.date+"</b></span></li><br/>");
        }else{
            // $("#contentUl").append("<li><b>"+data.date+"</b><em>"+data.userName+"</em><span>"+data.text+"</span></li><br/>");
            showNewMessage(data.userName,null,data.chatContent);
        }
    }
    if (data.chatType == 2){
        if(data.userName==userName){
            showNewImage("我",null,data.chatContent);
        }else{
            showNewImage(data.userName,null,data.chatContent);
        }
    }
    scrollToBottom();
};

// 监听WebSocket的关闭
websocket.onclose = function(event) {
    // $("#contentUl").append("<li><b>"+formatDate(null)+"</b><em>系统消息：</em><span>连接已断开！</span></li>");
    // scrollToBottom();
    showNewMessage("系统消息",null,"连接已断开！");
    console.log("WebSocket:已关闭：Client notified socket has closed",event);
};

//监听异常
websocket.onerror = function(event) {
    // $("#contentUl").append("<li><b>"+formatDate(null)+"</b><em>系统消息：</em><span>连接异常，建议重新登录</span></li>");
    // scrollToBottom();
    showNewMessage("系统消息",null,"连接发生异常！");
    console.log("WebSocket:发生错误 ",event);
};

//onload初始化
$(function(){
    /**
     //      * 键盘enter事件，用来发送消息
     //      */
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

//发送消息
function sendMsg(){
    //对象为空了
    if(websocket==undefined||websocket==null){
        //alert('WebSocket connection not established, please connect.');
        alert('您的连接已经丢失，请退出聊天重新进入');
        return;
    }
    //获取用户要发送的消息内容
    var msg=$("#messageInput").val();
    if(msg==""){
        return;
    }else{
        var data={};
        data["chatUserId"]=$("#loginUserId").val();
        data["userName"]=$("#loginUserName").val();
        // data["toUser"]=to;
        data["chatContent"]=msg.trim();
        //发送消息
        websocket.send(JSON.stringify(data));
        //发送完消息，清空输入框
        $("#messageInput").val("");
    }
}


function showLoginUser(userList) {
    $("#LoginUserList").empty();
    console.log(userList);
    $("<a></a>").addClass("list-group-item").append("在线成员").appendTo("#LoginUserList");
    $.each(userList,function (index,item) {
        console.log(item);
        $("<a></a>").addClass("list-group-item").append(item.userName).appendTo("#LoginUserList");
    });
}



//div滚动条(scrollbar)保持在最底部
function scrollToBottom(){
    //var div = document.getElementById('chatCon');
    var div = document.getElementById('historyMsg');
    div.scrollTop = div.scrollHeight;
}



function showUserLogout(message) {
    // var json = JSON.parse(message);
    console.log("登出信息："+message);
    var logoutUser = message.userName;
    var date = message.sendDate;
    var user = "系统消息";
    var msg = logoutUser + "离开了聊天室~";
    showNewMessage(user, date, msg);
    // showSubActiveUserNumber();
}
/**
 * 显示新用户登录的消息
 * @param message
 */
function showNewUser(message) {
    // var json = JSON.parse(message);
    var newUser = message.userName;
    var date = message.sendDate;
    var user = '系统消息';
    var msg = newUser + "加入聊天！";
    showNewMessage(user, date, msg);
    // showAddActiveUserNumber();

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
    // container.scrollTop = container.scrollHeight;
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

// 登出
$(document).on("click", '#exitBtn', function() {
    // $("#exitBtn").on("onclick",function(){
    logout()
});

function logout() {
    path = $("#APP_PATH").val();
    sessionStorage.clear();
    closeWebsocket();
    location.href=path;
}

//使用ctrl+回车快捷键发送消息
// function keySend(e) {
//     var theEvent = window.event || e;
//     var code = theEvent.keyCode || theEvent.which;
//     if (theEvent.ctrlKey && code == 13) {
//         var msg=$("#messageInput");
//         if (msg.innerHTML == "") {
//             msg.focus();
//             return false;
//         }
//         sendMsg();
//     }
// }

// Date.prototype.Format = function (fmt) { //author: meizz
//     var o = {
//         "M+": this.getMonth() + 1, //月份
//         "d+": this.getDate(), //日
//         "h+": this.getHours(), //小时
//         "m+": this.getMinutes(), //分
//         "s+": this.getSeconds(), //秒
//         "q+": Math.floor((this.getMonth() + 3) / 3), //季度
//         "S": this.getMilliseconds() //毫秒
//     };
//     if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
//     for (var k in o)
//         if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
//     return fmt;
// }

// /**
//  * 在线人数加1
//  */
// function showAddActiveUserNumber() {
//     var number = parseInt($("#status").text());
//     number = number + 1;
//     $("#status").text(number);
// }
// /**
//  * 在线人数减1
//  */
// function showSubActiveUserNumber() {
//     var number = parseInt($("#status").text());
//     number = number - 1;
//     $("#status").text(number);
// }

// var stompClient = null;
// $(function () {
//     first();
//
//     /**
//      * 键盘enter事件，用来发送消息
//      */
//     $("#messageInput").bind("keyup", function (event) {
//         if (event.keyCode == 13){
//             sendMessage();
//         }
//     });
//
//     /**
//      * 清除聊天窗口的所有内容
//      */
//     $("#clearBtn").click(function () {
//         $("#historyMsg").empty();
//         $("#messageInput").focus();
//     });
//
//     /**
//      * 上传图片发送
//      */
//     $("#sendImage").bind("change", function () {
//         if (this.files.length != 0){
//             $.ajax({
//                 url: $("#uploadUrl").val(),
//                 type: 'POST',
//                 cache: false,
//                 data: new FormData($('#sendImageForm')[0]),
//                 processData: false,
//                 contentType: false
//             }).done(function(res) {
//                 console.log(res);
//             }).fail(function(res) {
//                 console.log(res);
//             });
//         }
//     });
//     initEmoji();
//     $("#sendImageBtn").click(function () {
//         $("#sendImage").trigger("click");
//     })
//
// });
// function first() {
//     path = $("#APP_PATH").val();
//     userId = sessionStorage.getItem("userId");
//     userName = sessionStorage.getItem("userName");
//     var i = 0;
//     if (userId==null||userName==null){
//         i++;
//         console.log("有空值"+" i:"+i);
//     }else {
//         i++
//         console.log("有值"+" i:"+i+" path:"+path);
//         $.ajax({
//             url:path+"/chat",
//             data:"userId="+userId+"&userName="+userName,
//             type:"post",
//             success:function () {
//                 connect();
//             }
//         });
//     }
// }
// /**
//  * 预加载emoji图片
//  */
// function initEmoji() {
//     path = $("#APP_PATH").val();
//     var emojiContainer = $("#emojiWrapper");
//     var documentFragment = document.createDocumentFragment();
//     for (var i = 69; i > 0; i--){
//         var emojiItem = document.createElement("img");
//         emojiItem.src = path+"/static/images/emoji/" + i + ".gif";
//         emojiItem.title = i;
//         documentFragment.appendChild(emojiItem);
//     }
//     emojiContainer.append(documentFragment);
//
//     $("#emoji").click(function (event) {
//         emojiContainer.css("display", "block");
//         event.stopPropagation(); //阻止事件的传递，防止body监听到
//     });
//
//     $("body").click(function (event) {
//         if (event.target != emojiContainer){
//             emojiContainer.css("display", "none");
//         }
//     });
//
//     $("#emojiWrapper").click(function (event) {
//         var target = event.target;
//         if (target.nodeName.toLowerCase() == "img"){
//             var messageInput = $("#messageInput");
//             messageInput.val(messageInput.val() + "[EMOJI:" + target.title + "]");
//             messageInput.focus();
//         }
//     });
//
// }
// /**
//  * 客户端连接服务端websocket
//  * 并且订阅一系列频道，用来接收不同种类的消息
//  * /app/chat/participants ：当前在线人数的消息，只会接收一次
//  * /topic/login ： 新登录用户的消息
//  * /topic/chat/message ： 聊天内容消息
//  * /topic/logout : 用户离线的消息
//  * 服务器发回json实例{"userName":"chris","sendDate":1494664021793,"content":"hello","messageType":"text"}
//  * messageType分为：text与image
//  */
// function connect() {
//     console.log("进入0");
//     path = $("#APP_PATH").val();
//     var socket = new SockJS($("#websocketUrl").val().trim());
//     stompClient = Stomp.over(socket);
//     console.log("进入1");
//     console.log(stompClient);
//     stompClient.connect({}, function(){
//         console.log("进入1.5");
//         stompClient.subscribe("/app/online", function (message) {
//             showActiveUserNumber(message.body);
//             var user = "系统消息";
//             var msg = "123456加入聊天！";
//             showNewMessage(user, null, msg);
//         });
//         stompClient.subscribe("/topic/login", function (message) {
//             showActiveUserNumber(message.body);
//             showNewUser(message.body);
//         });
//         stompClient.subscribe("/topic/receive",function (message) {
//             console.log("监听到了");
//             var json = JSON.parse(message.body);
//                 // var messageType = json.messageType;
//             var user = sessionStorage.getItem("userName");
//             var date = json.sendDate;
//             var msg = json.content;
//                 // if (messageType == "text"){
//             showNewMessage(user, date, msg);
//                 // }else if (messageType == "image"){
//                 //     showNewImage(user, date, msg);
//                 // }
//         });
//         // stompClient.subscribe("/topic/chat/message", function (message) {
//         //     var json = JSON.parse(message.body);
//         //     // var messageType = json.messageType;
//         //     var user = sessionStorage.getItem("userName");
//         //     var date = json.sendDate;
//         //     var msg = json.content;
//         //     // if (messageType == "text"){
//         //         showNewMessage(user, date, msg);
//         //     // }else if (messageType == "image"){
//         //     //     showNewImage(user, date, msg);
//         //     // }
//         //
//         // })
//         stompClient.subscribe("/topic/logout", function (message) {
//             showUserLogout(message.body);
//         })
//
//     });
// }
// /**
//  * 显示用户离线消息
//  * @param message
//  */
// function showUserLogout(message) {
//     var json = JSON.parse(message);
//     var logoutUser = json.name;
//     var date = json.logoutDate;
//     var user = "系统消息";
//     var msg = logoutUser + "离开了聊天室~";
//     showNewMessage(user, date, msg);
//     showSubActiveUserNumber();
// }
// /**
//  * 显示新用户登录的消息
//  * @param message
//  */
// function showNewUser(message) {
//     var json = JSON.parse(message);
//     var newUser = json.userName;
//     var date = json.loginDate;
//     var user = '系统消息';
//     var msg = newUser + "加入聊天！";
//     showNewMessage(user, date, msg);
//     showAddActiveUserNumber();
//
// }
// /**
//  * 显示当前在线人数
//  * @param number
//  */
// function showActiveUserNumber(number) {
//     $("#status").text(number);
// }
// /**
//  * 在线人数加1
//  */
// function showAddActiveUserNumber() {
//     var number = parseInt($("#status").text());
//     number = number + 1;
//     $("#status").text(number);
// }
// /**
//  * 在线人数减1
//  */
// function showSubActiveUserNumber() {
//     var number = parseInt($("#status").text());
//     number = number - 1;
//     $("#status").text(number);
// }
// /**
//  * 格式化时间，参数为null显示当前客户端时间
//  * @param dateTime
//  * @returns {string}
//  */
// function formatDate(dateTime) {
//     var date = dateTime == null ? new Date() : new Date(dateTime);
//     var year = date.getFullYear();
//     var month = date.getMonth() + 1;
//     var day = date.getDate();
//     var hour = date.getHours();
//     hour = hour < 10  ? '0'+""+hour : hour;
//     var minute = date.getMinutes();
//     minute = minute < 10 ? '0'+""+minute : minute;
//     var second = date.getSeconds();
//     second = second < 10 ? '0'+""+second : second;
//     return year + "-" + month + "-" + day +" " + hour + ":" + minute + ":" + second;
// }
//
// /**
//  * 显示新消息
//  * @param user 发消息的用户或者‘系统消息’
//  * @param date 发消息的时间（未格式化）
//  * @param msg  消息内容
//  */
// function showNewMessage(user, date, msg) {
//     var container = document.getElementById("historyMsg");
//     var msgToDisplay = document.createElement('p');
//     if (user == "系统消息"){
//         msgToDisplay.style.color = 'red';
//     }
//     var dateTime = formatDate(date);
//     msg = showEmoji(msg);
//     msgToDisplay.innerHTML = '<span class="timespan">' + dateTime + '</span><br/>[' + user + "] : " + msg;
//     container.append(msgToDisplay);
//     container.scrollTop = container.scrollHeight;
// }
// /**
//  * 正则表达式显示消息中的emoji图片
//  * @param message
//  * @returns {*} 返回添加emoji图片标签后的消息
//  */
// function showEmoji(message) {
//     var result = message,
//         regrex = /\[EMOJI:\d+\]/g,
//         match;
//     while (match = regrex.exec(message)){
//         var emojiIndex = match[0].slice(7, -1);
//         var emojiUrl = $("#emojiBaseUri").val().trim() + emojiIndex + ".gif";
//         result = result.replace(match[0], '<img src="' + emojiUrl + '"/>');
//     }
//     return result;
// }
//
// /**
//  * 显示用户发送的图片
//  * @param user 用户名称
//  * @param date 用户发送的时间（未格式化）
//  * @param url 图片url
//  */
// // function showNewImage(user, date, url) {
// //     var container = document.getElementById("historyMsg");
// //     var msgToDisplay = document.createElement('p');
// //     var dateTime = formatDate(date);
// //     msgToDisplay.innerHTML = '<span class="timespan">' + dateTime + '</span><br/>[' + user + '] : <br/>' +
// //         '<img class="img-thumbnail" src="' + url + '"/>';
// //     container.append(msgToDisplay);
// //     container.scrollTop = container.scrollHeight;
// // }
// /**
//  * 发送输入框中的信息
//  */
// function sendMessage() {
//     userName = sessionStorage.getItem("userName");
//     var content = $("#messageInput").val();
//     if (content.trim().length != 0){
//         $("#messageInput").val('');
//         stompClient.send("/app/chat/message", {}, JSON.stringify({
//             'userName' : userName,
//             'content' : content
//         }));
//     }
// }

