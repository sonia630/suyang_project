<%@ taglib prefix="fns" uri="/tags/o2o-functions" %>
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
        <li class="am-u-sm-1"><i class="am-icon-angle-left am-icon-sm"></i></li>
        <li class="title am-text-center am-u-sm-10">填写症状</li>
        <li class="am-u-sm-1 am-text-right"><i class="am-icon-ellipsis-v "></i></li>
    </ul>
</div>

<!-- service order-->
<div class="add-symptom">
    <label>客户基础信息</label>
    <div>
        <p><span>${member.memberName}</span><span>${member.gender==1?"男":"女"}</span><span>${fns:computeAge(member.memberBirthday)}</span></p>

        <textarea id="desc" class="am-form-field am-radius" placeholder="基础信息" cols="30" rows="10"></textarea>

    </div>
    <label class="am-margin-top-lg">上传资料</label>
    <form id="picform">
        <div class="am-form-group am-form-file am-margin-top-sm">

            <button type="button" class="am-btn am-btn-block am-btn-sm am-round am-btn-default">
                <i class="am-icon-cloud-upload "></i> 选择要上传的文件
            </button>
            <input id="doc-form-file" name="files" type="file" multiple>
        </div>
    </form>

    <ul id="file-list" class="am-list"></ul>
</div>
<input type="hidden" id="upload"/>
<!-- commit -->
<div class="am-navbar navbar-button am-g">
    <div class="am-u-sm-12">
        <button class="am-btn am-btn-block am-btn-primary am-vertical-align-middle am-btn-lg" id="ok">完 成</button>
    </div>
</div>

<jsp:include page="../include/js.jsp"/>

<script>
    $(function () {
        $('#doc-form-file').on('change', function () {
            var fileNames = ''
            $.each(this.files, function () {
                fileNames += '<li>' + this.name + '<i class="am-icon-close am-fr am-text-danger" "></i></li> '
            })
            doUpload($("#picform"),$("#upload"));
            $('#file-list').html(fileNames)
        })

        removeFile();

        $("#ok").click(function () {

            var data = {
                "desc":$("#desc").val(),
                "upload":$("#upload").val(),
                "memberId":${memberId}
            };

            $.ajax({
               url: '/customer/saveSympton',
               type: "post",
               async: false,
               dataType: "json",
               data: data,
               success: function (e) {
                   if (e.code != 0) {
                       $.toptip(e.desc, 'error');
                   }else{
                       $.cookie('s3_member',"${memberId}-${member.memberName}",{expires: 0.1, path: "/"});
                       window.location.href="${from}?serviceId=${serviceId}"
                   }
               },
               error: function () {
                   $.toptip("保存失败,稍后重试", 'error');
               }
           });

        });
    })

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

