<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<head>
 <jsp:include page="../include/header.jsp"/>
    <link href="/static/style/main.css"  rel="stylesheet">
    <link href="/static/style/cropper.min.css"  rel="stylesheet">

    <style>
        /*.weui-select-modal {*/
            /*height: 205px;*/
        /*}*/
        /*.weui-cell {*/
            /*padding: 0px 15px;*/
        /*}*/
        .am-form-field[readonly]{
            background-color: #fff;
        }

        .sr-only {
            position: absolute;
            width: 1px;
            height: 1px;
            padding: 0;
            overflow: hidden;
            clip: rect(0, 0, 0, 0);
            white-space: nowrap;
            -webkit-clip-path: inset(50%);
            clip-path: inset(50%);
            border: 0;
        }

        .avatar-wrapper img {
            max-width: 100%;
        }
    </style>
</head>

<body>
<!-- Topbar -->
<div class="topbar">
    <ul class="am-g">
        <li class="am-u-sm-1"><i class="am-icon-angle-left am-icon-sm"></i></li>
        <li class="title am-text-center am-u-sm-10">注册信息</li>
        <li class="am-u-sm-1 am-text-right"><i class="am-icon-ellipsis-v "></i></li>
    </ul>
</div>

<!-- service order-->
<div class="service-order mine">
    <ul class="am-list">
        <li class="am-g border8" id="crop-avatar">
            <label class="am-u-sm-3 am-form-label user">头像</label>
                <div class="am-u-sm-9 avatar-view">
                    <label style="display: block;margin-top: 5px;text-align: right;border-right:0px">
                        <img src="/static/images/user.jpg" width="60px" height="60px"
                             class="am-img-thumbnail am-circle" id="avatar"/>
                        <input type="file" class="sr-only" alt="点击更换照片" id="input" name="image" accept="image/*">
                    </label>
                </div>
        </li>
        <li class="am-g border8">
            <div class="am-form-group am-form-icon am-form-feedback am-text-middle">
                <label class="am-u-sm-3 am-form-label">手机号</label>
                <div class="am-u-sm-9">
                    <input type="text" id="phone" class="am-form-field" placeholder="手机号" readonly>
                    <span class="am-icon-angle-right"></span>
                </div>
            </div>
        </li>

        <li class="am-g border8">
            <div class="am-form-group am-form-icon am-form-feedback am-text-middle">
                <label class="am-u-sm-3 am-form-label">性别</label>
                <div class="am-u-sm-9">
                    <input type="text"  id="sex" class="am-form-field" placeholder="性别" readonly>
                    <span class="am-icon-angle-right"></span>
                </div>
            </div>
        </li>

        <li class="am-g border8">
            <div class="am-form-group am-form-icon am-form-feedback am-text-middle">
                <label class="am-u-sm-3 am-form-label">生日</label>
                <div class="am-u-sm-9">
                    <input type="text" id="birthday" class="am-form-field" placeholder="生日" value="" readonly data-am-datepicker>
                    <span class="am-icon-angle-right"></span>
                </div>
            </div>
        </li>

        <li class="am-g border8">
            <div class="am-form-group am-form-icon am-form-feedback am-text-middle">
                <label class="am-u-sm-3 am-form-label">真实姓名</label>
                <div class="am-u-sm-9">
                    <input type="text" id="realName" class="am-form-field" placeholder="真实姓名" readonly>
                    <span class="am-icon-angle-right"></span>
                </div>
            </div>
        </li>

        <li class="am-g border8">
            <div class="am-form-group am-form-icon am-form-feedback am-text-middle">
                <label class="am-u-sm-3 am-form-label">住址</label>
                <div class="am-u-sm-9">
                    <input type="text" id="address" class="am-form-field" placeholder="住址" readonly>
                    <span class="am-icon-angle-right"></span>
                </div>
            </div>
        </li>

        <li class="am-g border8">
            <div class="am-form-group am-form-icon am-form-feedback am-text-middle">
                <label class="am-u-sm-3 am-form-label">来源</label>
                <div class="am-u-sm-9">
                    <input type="text" class="am-form-field" placeholder="来源" value="微信" readonly>
                    <span class="am-icon-angle-right"></span>
                </div>
            </div>
        </li>
    </ul>
</div>

<div id="avatar-modal" class="am-modal am-modal-no-btn" tabindex="-1" id="doc-modal-1">
    <div class="am-modal-dialog" style="width: 80%;">
        <div class="am-modal-hd" style="padding-bottom: 10px">
            <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
        </div>
        <div class="am-modal-bd">
            <form class="avatar-form" action="/tools/upload" enctype="multipart/form-data" method="post" onsubmit="return false">
                <div class="avatar-wrapper" style="padding-bottom: 14px;">
                    <img id="image">
                </div>
                <div class="avatar-btns">
                    <button class="am-btn am-btn-block am-btn-primary am-btn-lg am-vertical-align-middle" id="save">保存修改</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div id="completeModal" class="am-modal am-modal-no-btn" tabindex="-1" style="display: none">
    <div class="am-modal-dialog">
        <div class="am-modal-bd complete-modal">

            <h3 class="am-text-lg am-text-primary">欢迎来到太阳树调理之家！</h3>
            <p class="am-text-left am-text-xs">您提供服务前，请您补全信息并耐心等待审核！</p>

            <button class="am-btn am-btn-block am-btn-primary am-btn-lg am-vertical-align-middle " id="complete">补全信息以获取更好的服务</button>
            <button class="am-btn am-btn-block am-btn-white am-btn-lg am-vertical-align-middle " id="cancel">暂时拒绝</button>
        </div>
    </div>
</div>

<jsp:include page="../include/customer_footer.jsp"/>
<jsp:include page="../include/js.jsp"/>

<script src="/static/js/cropper.min.js"></script>

<script>
    $(function () {
        Template.init("#user");
        $.getJSON("/customer/self/profile", {}, function (event) {
            var value = event.data;
            $("#phone").val(value.phone);
            $("#sex").attr("data-values",value.gender);
            $("#sex").val(value.gender ? "男" : "女");
            $("#realName").val(value.realName);
            $("#birthday").val(value.birthday);
            $("#address").val(value.defaultAddress);
            if(value.headPic){
                $("#avatar").attr("src", "/tools/download?name=" + value.headPic);
            }
        });

        $("#sex").select({
            title: "性别",
            items: [
                {
                    title: "男",
                    value: 1
                },
                {
                    title: "女",
                    value: 0
                },
            ],
            onClose:function () {
                $.ajax({
                   type: "post",
                   async: false,
                   dataType: "json",
                   data: {
                       "sex": $("#sex").attr("data-values")
                   },
                   url: "/user/updateSex",
               }).done(function () {
                    $.toast('修改成功');
               }).fail(function (e) {
                    $.toptip(e.message, e.status);
               });
            }
        });

        $('#birthday').datepicker().
            on('changeDate.datepicker.amui', function(event) {
                $.ajax({
                   type: "post",
                   async: false,
                   dataType: "json",
                   data: {"date":event.date},
                   url: "/user/updateBirthday",
               }).done(function () {
                    $.toast('修改成功');
               }).fail(function (e) {
                    $.toptip(e.message, e.status);
               });
            });

        $("#realName").click(function () {
            window.location.href="/common/my_name?from=/user&name="+$(this).val();
        })
        $("#address").click(function () {
            window.location.href="/common/customer_address?address="+$(this).val();
        })

        var avatar = document.getElementById('avatar');
        var image = document.getElementById('image');
        var $modal = $('#avatar-modal');
        var cropper;

        $("#input").change(function (e) {
            var files = e.target.files;
            var done = function (url) {
                $(this).val('');
                image.src = url;
                $modal.modal();
            };
            var reader;
            var file;
            if (files && files.length > 0) {
                file = files[0];

                if (URL) {
                    done(URL.createObjectURL(file));
                } else if (FileReader) {
                    reader = new FileReader();
                    reader.onload = function (e) {
                        done(reader.result);
                    };
                    reader.readAsDataURL(file);
                }
            }
        });

        $modal.on('open.modal.amui', function () {
            cropper = new Cropper(image, {
                aspectRatio: 1,
                viewMode: 3,
            });
        }).on('close.modal.amui', function () {
            cropper.destroy();
            cropper = null;
        });

        $("#save").click(function () {
            var initialAvatarURL;
            var canvas = cropper.getCroppedCanvas({
              width: 160,
              height: 160,
            });

            initialAvatarURL = avatar.src;
            avatar.src = canvas.toDataURL();
            canvas.toBlob(function (blob) {
                var formData = new FormData();

                formData.append('file', blob);

                $.ajax('/tools/upload', {
                    method: 'POST',
                    data: formData,
                    processData: false,
                    contentType: false,
                    success: function (name) {
                        avatar.src = "/tools/download?name=" + name;
                        $.ajax({
                           type: "post",
                           async: false,
                           dataType: "json",
                           data: {"name":name},
                           url: "/user/updateHeadPic",
                       } ).done(function () {
                            $.toast('修改成功');
                        }).fail(function (e) {
                            $.toptip(e.message, e.status);
                        });
                    },

                    error: function () {
                        $.toptip("上传失败,稍后重试", 'error');
                    }
                });
            });
            $modal.modal('close');
        });
    });
</script>
</body>

</html>

