<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<!doctype html>
<html>
<head>
  <jsp:include page="../include/header.jsp"/>
</head>

<body>

<!-- Topbar -->
<div class="topbar">
    <ul class="am-g">
        <li class="am-u-sm-1" id="back"><i class="am-icon-angle-left am-icon-sm"></i></li>
        <li class="title am-text-center am-u-sm-10">健康报告</li>
        <li class="am-u-sm-1 am-text-right"></li>
    </ul>
</div>

<div class="layout certificate">
    <form class="am-form am-form-horizontal" id="form">
        <h2>健康报告</h2>
        <input class="am-margin-bottom-sm" type="text" placeholder="健康狀況" value="${health.health}" id="health">

        <div class="am-margin-top-sm am-input-group am-datepicker-date"
             data-am-datepicker="{format: 'yyyy-mm-dd', viewMode: 'years'}">
            <input id="time" type="text" class="am-form-field" placeholder="健康报告时间"
                   value="<fmt:formatDate pattern='yyyy-MM-dd' value='${health.healthReportTime}'/>" readonly>
            <span class="am-input-group-btn am-datepicker-add-on">
                                <button class="am-btn am-btn-default" type="button"><span
                                        class="am-icon-calendar"></span> </button>
                            </span>
        </div>

        <h2 class="am-margin-top-sm">上传照片 </h2>
        <div class="weui-uploader__input-box">
            <input id="uploaderInput" class="weui-uploader__input" type="file" name="file"/>
        </div>
        <div class="am-u-sm-6">
            <image src="/tools/download?name=${health.healthReport}" width="100%"
                   style="height: 100px;<c:if test='${empty health.healthReport}'>display:  none;</c:if>" class="am-form-field" id="img"/>
        </div>
        <input type="hidden" id="reportPath" value="${health.healthReport}"/>
    </form>


</div>


<!-- commit -->
<div class="am-navbar navbar-button am-g">
    <div class="am-u-sm-12">
        <button class="am-btn am-btn-block am-btn-primary am-vertical-align-middle am-btn-lg" id="save">确  定</button>
    </div>
</div>

<jsp:include page="../include/js.jsp"/>
<script>
    $(function () {
        $("#back").click(function () {
            window.location.href = "/provider/self/profile/page";
        })

        $("#uploaderInput").change(function(){
            var formData = new FormData($("#form")[0]);
            $.ajax({
                url : "/tools/upload",
                type: 'POST',
                data: formData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success : function(data) {
                    $("#img").attr("src", "/tools/download?name=" + data);
                    $("#reportPath").val(data);
                    $("#img").show();
                },
                error : function(data) {
                    $.toptip(data, 'error');
                }
            });
        });

        $("#save").click(function () {
            $.ajax({
                type: "post",
                async: false,
                dataType: "json",
                data: {
                    "health": $("#health").val(),
                    "healthReport":$("#reportPath").val(),
                    "healthReportTime":$("#time").val()
                },
                url: "/provider/save_health",
            }).done(function (e) {
                if (e.code == 0) {
                    window.location.href = "/provider/self/profile/page";
                } else {
                    $.toptip(e.message, "error");
                }
            }).fail(function (e) {
                $.toptip(e.message, "error");
            });
        })
    })
</script>
</body>


</html>
