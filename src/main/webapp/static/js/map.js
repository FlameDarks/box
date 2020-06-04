var map;

window.onload = loadScript;

/**
 * 加载地图
 */
function a() {
    map = new BMap.Map("maps");     //创建map实例
    var point = new BMap.Point(116.404, 39.915);//初始化地图，设置坐标点
    map.addControl(new BMap.NavigationControl());//地图平移缩放控件
    map.addControl(new BMap.ScaleControl());//添加比例尺控件
    map.addControl(new BMap.MapTypeControl());//地图类型控件
    map.enableScrollWheelZoom(true);//开启鼠标滚轮缩放
    function myFun(result){
        var cityName = result.name;
        map.setCenter("许昌");
    }
    var myCity = new BMap.LocalCity();
    myCity.get(myFun);
    map.centerAndZoom(point, 15);
}

/**
 * 异步加载
 */
function loadScript() {
    var script = document.createElement("script");
    script.src = "http://api.map.baidu.com/api?v=3.0&ak=wj4qQcaZxNOjZlbG2psLS3f9Dg9FeeqW&callback=a";
    document.body.appendChild(script);
}

function select() {
    if ($("#check").val() == 1){
        city();
    }else {
        around();
    }
}

/**
 * 搜索城市
 */
function city() {
    function myFun(){
        var align = $("#selectInput").val();
        map.setCenter(align);
    }
    var myCity = new BMap.LocalCity();
    myCity.get(myFun);
    map.centerAndZoom(point, 15);
}

/**
 * 搜索周边
 */
function around() {
    var local = new BMap.LocalSearch(map, {
        renderOptions:{map: map}
    });
    local.search($("#selectInput").val().trim());
}

/**
 * 构建天气列表
 * @param result
 */
function build_weather_page(result) {
    var path = $("#APP_PATH").val();
    $.each(result.data,function (index,item) {
        var date = $("<td></td>").append(item.day);
        var img = $("<td></td>").append($("<img>").attr("src",path+"/static/images/weather/"+item.wea_img+".png").attr("style","width:45px;height:45px"));
        var weather = $("<td></td>").append($("<span></span>").append(item.tem).append($("<br>")).append($("<td></td>").append(item.wea)));
        var weathers = $("<td></td>").append(item.tem1+"~"+item.tem2);
        $("<tr></tr>")
            .append(date)
            .append(img)
            .append(weather)
            .append(weathers)
            .appendTo("#weather");
    });
}

/**
 * 获取最新的天气信息
 */
$(function () {
    $.ajax({
        url:"https://www.tianqiapi.com/api?version=v1&appid=21226239&appsecret=6GgRmnTB",
        type:"GET",
        success:function (result) {
            build_weather_page(result);
        }
    });
});