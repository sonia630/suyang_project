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
        <li class="title am-text-center am-u-sm-10">资质证书上传</li>
        <li class="am-u-sm-1 am-text-right"></li>
    </ul>
</div>

<div class="layout certificate">
    <form class="am-form am-form-horizontal" id="form" enctype="multipart/form-data">
        <h2>资质证书信息</h2>
        <input id="name" class="am-margin-bottom-sm" type="text" placeholder="证书名称">
        <textarea id="desc" class="am-form-field  am-radius" placeholder="详细描述" cols="30"
                  rows="4"></textarea>


        <div class="am-margin-top-sm am-input-group am-datepicker-date"
             data-am-datepicker="{format: 'yyyy-mm-dd', viewMode: 'years'}">
            <input type="text" class="am-form-field" placeholder="证书获得时间" id="time" readonly>
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
            <image width="100%" style="height: 100px;display: none" class="am-form-field" id="img"/>
        </div>
        <input type="hidden" id="pic">
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
            window.location.href="/provider/cert";
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
                    $("#pic").val(data);
                    $("#img").attr("src", "/tools/download?name=" + data);
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
                    "name": $("#name").val(),
                    "descr":$("#desc").val(),
                    "pic":$("#pic").val()
                },
                url: "/provider/save_cert",
            }).done(function (e) {
                if (e.code == 0) {
                    window.location.href="/provider/cert";
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

