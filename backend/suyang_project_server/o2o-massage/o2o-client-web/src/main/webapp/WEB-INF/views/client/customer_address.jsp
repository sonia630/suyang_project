<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<!doctype html>
<html>
<head>
    <jsp:include page="../include/header.jsp"/>
</head>

<body class="am-animation-slide-right">

<!-- Topbar -->
<div class="topbar">
    <ul class="am-g">
        <li class="am-u-sm-1" id="back"><i class="am-icon-angle-left am-icon-sm"></i></li>
        <li class="title am-text-center am-u-sm-10">常用地址</li>
    </ul>
</div>

<div class="select-location">
    <form class="am-form am-form-horizontal border8">
        <input type="hidden" id="addressId"/>
        <input type="hidden" id="latitude"/>
        <input type="hidden" id="longitude"/>
        <div class="am-form-group am-form-group-sm">
            <label class="am-u-sm-3 am-form-label am-text-right">常用地址</label>
            <div class="am-u-sm-9">
                <input class="am-form-field" type="text" id="commonAddress" placeholder="小区、街道或大厦名称">
            </div>
            <div class="autocomplete" style="display: none;">
                <ul id="autocomplete" class="am-list"></ul>
            </div>
        </div>

        <div class="am-form-group am-form-group-sm">
            <label class="am-u-sm-3 am-form-label am-text-right">门牌号码</label>
            <div class="am-u-sm-9">
                <input class="am-form-field" id="detailAddress" type="text" placeholder="楼栋、单元及门牌号等详细信息">
            </div>
        </div>
    </form>
    <div class="local-history">
        <label>已有地址</label>
        <ul class="am-list">
            <c:forEach items="${locations}" var="location">
                <li lid="${location.id}" latitude="${location.latitude}" longitude="${location.longitude}">
                    <div>${location.detailAddress}</div>
                    <div class="am-text-xs">${location.address}</div>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>

<div class="am-navbar navbar-button am-g">
    <div class="am-u-sm-12">
        <button class="am-btn am-btn-block am-btn-primary am-vertical-align-middle am-btn-lg" onclick="ok()">确 定</button>
    </div>
</div>

<jsp:include page="../include/js.jsp"/>
<script type="text/javascript" src="/static/js/map.js"></script>

<script>
    $(function () {
        slideRight ();
        loadScript();
        $("#commonAddress").blur(function () {
            $(".autocomplete").hide();
        });
        $("#back").click(function () {
            window.location.href="/user";
        });

        $('#commonAddress').bind('input propertychange', function() {
            address_changed();
        });

        $("[lid]").click(function () {
            $("#addressId").val($(this).attr("lid"));
            $("#latitude").val($(this).attr("latitude"));
            $("#longitude").val($(this).attr("longitude"));
            $("#commonAddress").val($(this).children("div").eq(1).html());
            $("#detailAddress").val($(this).children("div").eq(0).html());
        })
    });


    function loadScript() {
        var script = document.createElement("script");
        script.src = "http://api.map.baidu.com/api?v=2.0&ak=7uEG0qE4p5ALZfO3a3x2U8LeYkY05Mud&callback=start_map";
        document.body.appendChild(script);
    }


    function slideRight () {
        $(".topbar").css("position","initial")
        setTimeout(function () {
            $("body").removeClass("am-animation-slide-right");
            $(".select-location").addClass("sliderMargin");
            $(".topbar").css("position","fixed")
        },400)
    }

    function ok() {
        var data={
            "address":$("#commonAddress").val(),
            "detailAddress":$("#detailAddress").val(),
            "id":$("#addressId").val(),
            "longitude":$("#longitude").val(),
            "latitude":$("#latitude").val()
        };
        $.ajax({
           type: "post",
           async: false,
           dataType: "json",
           data: data,
           url: "/common/save_defaultLocation",
        }).done(function () {
            window.location.href = "/user";
        }).fail(function (e) {
            $.toptip(e.message, e.status);
        });
    }

</script>
</body>
</html>

