<%@ page contentType="text/html;charset=UTF-8" %>
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
        <li class="title am-text-center am-u-sm-10">个人简介</li>
        <li class="am-u-sm-1 am-text-right"></li>
    </ul>
</div>

<div class="layout certificate">
    <form class="am-form am-form-horizontal">
        <h2>个人简介</h2>
        <textarea id="intro" class="am-form-field  am-radius" placeholder="个人简介" cols="30"
                  rows="15">${intro}</textarea>
    </form>
</div>


<!-- commit -->
<div class="am-navbar navbar-button am-g">
    <div class="am-u-sm-12">
        <button class="am-btn am-btn-block am-btn-primary am-vertical-align-middle am-btn-lg" id="save">保 存</button>
    </div>
</div>

<jsp:include page="../include/js.jsp"/>
<script>
    $(function () {
        $("#back").click(function () {
            window.location.href = "/provider/self/profile/page";
        });
        $("#save").click(function () {
            $.ajax({
                type: "post",
                async: false,
                dataType: "json",
                data: {"introduction": $("#intro").val()},
                url: "/provider/save_introduction",
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

