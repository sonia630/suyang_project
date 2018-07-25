<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<!doctype html>
<html>
<head>
    <jsp:include page="include/header.jsp"/>
</head>

<body class="am-animation-slide-right">

<!-- Topbar -->
<div class="topbar">
    <ul class="am-g">
        <li class="am-u-sm-1" id="back"><i class="am-icon-angle-left am-icon-sm"></i></li>
        <li class="title am-text-center am-u-sm-10">顾问姓名</li>
    </ul>
</div>

<div class="select-location">
    <form class="am-form am-form-horizontal">
        <div class="am-form-group am-form-group-sm">
            <label class="am-u-sm-3 am-form-label am-text-right">姓名</label>
            <div class="am-u-sm-9">
                <input class="am-form-field" type="text" id="realName" value="${name}" placeholder="姓名">
            </div>
        </div>
    </form>
    <div class="am-u-sm-12">
        <button class="am-btn am-btn-block am-btn-primary am-vertical-align-middle am-btn-lg" onclick="ok()">确 定</button>
    </div>
</div>


<jsp:include page="include/js.jsp"/>

<script>
    $(function () {
        slideRight ();
        $("#back").click(function () {
            window.location.href="${from}";
        });
    });

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
            "name":$("#realName").val(),
        };
        $.ajax({
           type: "post",
           async: false,
           dataType: "json",
           data: data,
           url: "/common/update_realName",
       }).done(function () {
            window.location.href="${from}";
       }).fail(function (e) {
            $.toptip(e.message, "error");
       });
    }

</script>
</body>
</html>
