<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<!doctype html>
<html>
<head>
    <jsp:include page="../include/header.jsp"/>
    <link href="/static/style/cropper.min.css"  rel="stylesheet">
    <style>
        .sr-only {
            position: absolute;
            width: 1px;
            height: 1px;
            padding: 0;
            overflow: hidden;
            clip: rect(0,0,0,0);
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
        <li class="title am-text-center am-u-sm-10">顾问注册</li>
        <li class="am-u-sm-1 am-text-right"><i class="am-icon-ellipsis-v "></i></li>
    </ul>
</div>


<!-- List -->
<div class="therapist-register">
    <!-- ul -->
    <div class="am-form am-form-horizontal">
        <div class="avatar am-text-center avatar-view">
            <label>
                <img src="<c:choose><c:when test="${empty provider.headPic}">/static/images/user.jpg</c:when><c:otherwise>/tools/download?name=${provider.headPic}</c:otherwise></c:choose>" width="80px" height="80px"
                       class="am-img-thumbnail am-circle" id="avatar"/>
                <input type="file" class="sr-only" alt="点击更换照片" id="input" name="image" accept="image/*">
            </label>
        </div>
        <ul class="am-list">
            <li class="am-g ">
                <div class="am-form-group-sm">
                    <label class="am-u-sm-3 am-form-label">真实姓名</label>
                    <div class="am-u-sm-9">
                        <input type="text" id="realName" class="am-form-field" value="${provider.realName}" placeholder="真实姓名"/>
                    </div>
                </div>
            </li>
            <li class="am-g ">
                <div class="am-form-group-sm">
                    <label class="am-u-sm-3 am-form-label">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别</label>
                    <div class="am-u-sm-9" style="margin-top: 9px;margin-bottom: -8px;">
                        <label> <input type="radio" name="sex" value="1" <c:if test="${provider.gender==1}">checked</c:if>> 男 </label>
                        <label> <input type="radio" name="sex" value="0" <c:if test="${provider.gender==0}">checked</c:if>> 女 </label>
                    </div>
                </div>
            </li>
            <li class="am-g ">
                <div class="am-form-group-sm">
                    <label class="am-u-sm-3 am-form-label">身份证号</label>
                    <div class="am-u-sm-9">
                        <input type="text" id="idNum" class="am-form-field" value="${provider.idNum}" placeholder="身份证号"/>
                    </div>
                </div>
            </li>
            <li class="am-g ">
                <div class="am-form-group-sm">
                    <label class="am-u-sm-3 am-form-label">出生年月</label>
                    <div class=" am-u-sm-9 am-input-group am-datepicker-date"
                         data-am-datepicker="{format: 'yyyy-mm-dd', viewMode: 'years'}">
                        <input type="text" id="birthday" class="am-form-field" placeholder="日历组件" value="<fmt:formatDate pattern='yyyy-MM-dd' value='${provider.birthday}'/>" readonly>
                        <span class="am-input-group-btn am-datepicker-add-on">
                                <button class="am-btn am-btn-default" type="button"><span
                                        class="am-icon-calendar"></span> </button>
                            </span>
                    </div>
                </div>
            </li>
            <li class="am-g ">
                <div class="am-form-group-sm">
                    <label class="am-u-sm-3 am-form-label">籍&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;贯</label>
                    <div class="am-u-sm-9">
                        <input type="text" class="am-form-field" placeholder="籍贯" id="providerBirthPlace" value="${provider.providerBirthPlace}"/>
                    </div>
                </div>
            </li>

            <li class="am-g ">
                <div class="am-form-group-sm">
                    <label class="am-u-sm-3 am-form-label">学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;历</label>
                    <div class="am-u-sm-9">
                        <input type="text" class="am-form-field" placeholder="学历" id="providerEduLev" value="${provider.providerEduLev}"/>
                    </div>
                </div>
            </li>
            <li class="am-g ">
                <div class="am-form-group-sm">
                    <label class="am-u-sm-3 am-form-label">常用地址</label>
                    <div class="am-u-sm-9">
                        <input class="am-form-field" type="text" id="commonAddress" placeholder="小区、街道或大厦名称" value="${provider.commonAddress}">
                    </div>
                    <div class="autocomplete" style="display: none;">
                        <ul id="autocomplete" class="am-list"></ul>
                    </div>
                </div>
            </li>
            <li class="am-g ">
                <div class="am-form-group-sm">
                    <label class="am-u-sm-3 am-form-label">健康报告</label>
                    <form id="health-form">
                        <div am-u-sm-9>
                            <div class="am-form-group am-form-file am-margin-top-sm">
                                <button type="button" class="am-btn am-btn-block am-btn-sm am-round am-btn-default" style="width: 95%;">
                                    <i class="am-icon-cloud-upload "></i> 选择要上传的健康报告
                                </button>
                                <input id="file-health-form-file" name="files" type="file" multiple>
                            </div>

                            <ul id="file-health-report-list" class="file-list">
                            </ul>
                        </div>
                    </form>
                </div>
            </li>
            <li class="am-g ">
                <div class="am-form-group-sm">
                    <label class="am-u-sm-3 am-form-label">报告时间</label>
                    <div class=" am-u-sm-9 am-input-group am-datepicker-date"
                         data-am-datepicker="{format: 'yyyy-mm-dd', viewMode: 'years'}">
                        <input type="text" id="healthReportTime" class="am-form-field" placeholder="日历组件" readonly value="<fmt:formatDate pattern='yyyy-MM-dd' value='${provider.healthReportTime}'/>">
                        <span class="am-input-group-btn am-datepicker-add-on">
                                <button class="am-btn am-btn-default" type="button"><span
                                        class="am-icon-calendar"></span> </button>
                            </span>
                    </div>
                </div>
            </li>
            <li class="am-g ">
                <div class="am-form-group-sm">
                    <label class="am-u-sm-3 am-form-label">资质证书</label>
                    <form id="certificate-form">
                        <div am-u-sm-9>
                            <div class="am-form-group am-form-file am-margin-top-sm">
                                <button type="button" class="am-btn am-btn-block am-btn-sm am-round am-btn-default" style="width: 95%;">
                                    <i class="am-icon-cloud-upload "></i> 选择要上传的资质证书
                                </button>
                                <input id="file-certificate-form-file" name="files" type="file" multiple>
                            </div>

                            <ul id="file-certificate-report-list" class="file-list">
                            </ul>
                        </div>
                    </form>
                </div>
            </li>
            <li class="am-g ">
                <div class="am-form-group-sm">
                    <label class="am-u-sm-3 am-form-label">个人简介</label>
                    <button class="am-btn am-u-sm-3 am-btn-xs am-round am-btn-primary" style="margin-right: 11px;">撰写我的故事</button>
                </div>
                <div class="am-form-group-sm">
                    <div class="am-u-sm-12 am-margin-top-sm">
                        <textarea id="providerIntroduction" class="am-form-field am-radius" placeholder="个人简介" cols="30"
                                  rows="10">${provider.providerIntroduction}</textarea>
                    </div>
                </div>
            </li>

        </ul>
    </div>
</div>

<input type="hidden" name="headPic" id="headPic" value="${provider.headPic}"/>
<input type="hidden" id="certificateReports"/>
<input type="hidden" id="healthReports"/>
<input type="hidden" id="latitude" value="${provider.latitude}"/>
<input type="hidden" id="longitude" value="${provider.longitude}"/>

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


<!-- commit -->
<div class="am-navbar navbar-button am-g">
    <div class="am-u-sm-12">
        <button class="am-btn am-btn-block am-btn-primary am-vertical-align-middle am-btn-lg" id="submit">提 交</button>
    </div>
</div>

<jsp:include page="../include/js.jsp"/>
<script src="/static/js/cropper.min.js"></script>
<script type="text/javascript" src="/static/js/map.js"></script>

<script>

    $(function () {
        loadScript();

        $("#commonAddress").blur(function () {
           $(".autocomplete").hide();
        });
        $('#commonAddress').bind('input propertychange', function() {
            address_changed();
        });

        $("#submit").click(function () {
            var data = {
                "headPic":$("#headPic").val(),
                "gender":$("input[name='sex'][checked]").val(),
                "idNum":$("#idNum").val(),
                "realName":$("#realName").val(),
                "birthday":$("#birthday").val(),
                "providerBirthPlace":$("#providerBirthPlace").val(),
                "providerEduLev":$("#providerEduLev").val(),
                "healthReport":$("#healthReports").val(),
                "healthReportTime":$("#healthReportTime").val(),
                "certificateReport":$("#certificateReports").val(),
                "providerIntroduction":$("#providerIntroduction").val(),
                "address":$("#commonAddress").val(),
                "longitude":$("#longitude").val(),
                "latitude":$("#latitude").val()
            };

            $.ajax({
               url: '/provider/completeInfo',
               type: "post",
               async: false,
               dataType: "json",
               data: data,
               success: function (e) {
                   if (e.code != 0) {
                       $.toptip(e.desc, 'error');
                   }else{
                       window.location.href="/provider/self/profile/page"
                   }
               },
               error: function () {
                    $.toptip("保存失败,稍后重试", 'error');
               }
            });

        });

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
                        $("#headPic").val(name);
                    },

                    error: function () {
                        $.toptip("上传失败,稍后重试", 'error');
                    }
                });
            });
            $modal.modal('close');
        });
    });

    function loadScript() {
        var script = document.createElement("script");
        script.src = "http://api.map.baidu.com/api?v=2.0&ak=7uEG0qE4p5ALZfO3a3x2U8LeYkY05Mud&callback=start_map";
        document.body.appendChild(script);
    }

</script>

<script>
    $(function () {
        fileupload();
        removeFile();
    });
    function fileupload () {
        $('#file-health-form-file').on('change', function () {
            var fileNames = ''
            $.each(this.files, function () {
                fileNames += '<li>' + this.name + '<i class="am-icon-close am-fr am-text-danger"></i></li> '
            });
            doUpload($("#health-form"),$("#certificateReports"));
            $('#file-health-report-list').html(fileNames)
        })

        $('#file-certificate-form-file').on('change', function () {
            var fileNames = ''
            $.each(this.files, function () {
                fileNames += '<li>' + this.name + '<i class="am-icon-close am-fr am-text-danger"></i></li> '
            })
            doUpload($("#certificate-form"),$("#healthReports"));
            $('#file-certificate-report-list').html(fileNames)
        })
    }


    function doUpload(form,input){
        var formData = new FormData($(form)[0]);
        $.ajax({
           url : "/tools/uploadMutil",
           type: 'POST',
           data: formData,
           async: false,
           cache: false,
           contentType: false,
           processData: false,
           success : function(data) {
               $(input).val(data);
           },
           error : function(data) {
               $.toptip(data, 'error');
           }
       });
    }

    function removeFile () {
        $('body').delegate('.am-text-danger', 'click', function () {
            $(this).parent().remove()
        })
    }
</script>

</body>

</html>