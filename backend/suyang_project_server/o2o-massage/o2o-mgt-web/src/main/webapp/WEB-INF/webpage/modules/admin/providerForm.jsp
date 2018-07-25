<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/webpage/include/validator.jsp"%>
<%@ include file="/WEB-INF/webpage/include/jshow.jsp"%>
<script src="/static/js/plugin/summernote/summernote.js"></script>
<script src="/static/js/plugin/summernote/summernote-zh-CN.js"></script>
<script type="text/javascript" src="/static/js/map.js"/>
<script type="text/javascript" src="/static/js/plugin/bootstrap-timepicker/bootstrap-timepicker.min.js"/>
<style>
	.autocomplete{
		position: absolute;
		z-index: 10;
		background: #fff;
	}

	.autocomplete ul li {
		display: block;
		text-indent: 2em;
		border: #999 1px solid;
		font-size: 17px;
		font-weight: bold;
		color: #474a68;
	}
</style>
<script>
	$(document).ready(function() {
        pageSetUp();
		$("#pDetailForm").validate({
		   submitHandler: function () {
               submit($("#pDetailForm"));
		   }
	  	 });
		$("#psb").click(function(){
            $("#pDetailForm").submit();
		});

		$("#ucancel").click(function () {
            $(".summernote").summernote( 'destroy' );
            close_address();
            backTo('/sys/user/')
        });

		$("#audit").click(function () {
            gDialog.fConfirm('确认执行', '你确定执行这个操作么？',
				 function (rs) {
					 if (rs) {
						 $.ajax({
							type: 'post',
							dataType: "json",
							data:{"userId":"${detail.userInfo.userId}"},
							url: "/providerInfo/audit",
							cache: false,
							success: function (content) {
								message_box.show(content.message, content.code);
								$("#audit").hide();
							},
							error: function (content) {
								message_box.show(content.message, content.code);
							}
						 });
					 }
				 }
            );
        });

        var $summernote = $('.summernote').summernote({
			  height: 280,
			  placeholder: '顾问简介',
			  lang:'zh-CN',
			  toolbar: [
				  ['style', ['style']],
				  ['font', ['bold', 'italic', 'underline', 'clear']],
				  ['fontname', ['fontname']],
				  ['color', ['color']],
				  ['para', ['ul', 'ol', 'paragraph']],
				  ['height', ['height']],
				  ['table', ['table']],
				  ['insert', ['link', 'picture', 'hr']],
				  ['view', ['fullscreen', 'codeview', 'help']]

			  ],
			  callbacks: {
				  onImageUpload: function(files) {
					  var data = new FormData();
					  data.append("file",files[0]);
					  $.ajax({
						 data: data,
						 type: "POST",
						 url: "/sys/tools/upload",
						 cache: false,
						 processData: false,
						 contentType: false,
						 success: function(url) {
							 $summernote.summernote('insertImage', url)
						 },
						 error:function(){
							 alert("上传失败.");
						 }
					 });
				  },
				  onInit: function() {
					  $('#summernote').summernote('code',$("#providerIntroduction").val());
				  }
			  }
		  });
        $('#commonAddress').bind('input propertychange', function () {
            address_changed();
        });
    });
    loadScript();

    function submit(form) {
        $("#providerIntroduction").val($('.summernote').summernote('code'));
        return ajaxSubmit(form, 'json', saveCallback);
    }

    function loadScript() {
        var script = document.createElement("script");
        script.src = "http://api.map.baidu.com/api?v=2.0&ak=7uEG0qE4p5ALZfO3a3x2U8LeYkY05Mud&callback=start_map";
        document.body.appendChild(script);
    }


    function saveCallback(json) {
        if (json.code == 'success') {
			message_box.show(json.message, 'success');
            /*$(".summernote").summernote( 'destroy' );*/
		} else {
			message_box.show(json.message, 'error');
		}
		return true;
	}
</script>

<div data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-togglebutton="false" data-widget-fullscreenbutton="false" data-widget-sortable="false">
	<div>
		<div class="jarviswidget-editbox"></div>
		<div class="widget-body">
			<form action="/providerInfo/save" method="post" id="pDetailForm" class="smart-form" onsubmit="return false">
				<input type="hidden" name="userInfo.userId" value="${detail.userInfo.userId}" />
				<input type="hidden" name="providerInfo.providerIntroduction" id="providerIntroduction"/>
				<input type="hidden" name="providerInfo.latitude" id="latitude" value="${detail.providerInfo.latitude}"/>
				<input type="hidden" name="providerInfo.longitude" id="longitude" value="${detail.providerInfo.longitude}"/>
				<input type="hidden" name="providerInfo.providerId" id="providerId" value="${detail.providerInfo.providerId}"/>

				<table id="user" class="table table-bordered" style="clear: both">
					<tbody>
				<tr>
					<td style="width: 10%;vertical-align: middle;">用户ID</td>
					<td>
						<label class="input">
							<input type="text" name="userInfo.name" value="${detail.userInfo.name}" maxlength="50" required />
						</label>
					</td>
					<td style="vertical-align: middle;">手机号</td>
					<td>
						<label class="input">
							<input type="text" name="userInfo.phone" value="${detail.userInfo.phone}" maxlength="11" minlength="11" required />
						</label>
					</td>
					<td style="vertical-align: middle;">用户名</td>
					<td>
						<label class="input">
							<input type="text" name="userInfo.realName" value="${detail.userInfo.realName}" required />
						</label>
					</td>
				</tr>
				<tr>
					<td style="width: 10%;vertical-align: middle;">身份证</td>
					<td>
						<label class="input">
							<input type="text" name="userInfo.idNum"  value="${detail.userInfo.idNum}" required/>
						</label>
					</td>
					<td style="width: 10%;vertical-align: middle;">电子邮箱:</td>
					<td>
						<label class="input">
							<input type="text" name="userInfo.email"  value="${detail.userInfo.email}" />
						</label>
					</td>
					<td style="width: 10%;vertical-align: middle;">性别:</td>
					<td>
						<div class="inline-group">
							<label class="radio">
								<input type="radio" name="userInfo.gender" value="1"
									   <c:if test="${detail.userInfo.gender==1}">checked="checked"</c:if>>
								<i></i>男</label>
							<label class="radio">
								<input type="radio" name="userInfo.gender" value="0"
									   <c:if test="${detail.userInfo.gender==0}">checked="checked"</c:if>>
								<i></i>女</label>
						</div>
					</td>
				</tr>
				</tbody>
				</table>
				<hr class="simple"/>
				<table class="table table-bordered" style="clear: both">
					<tbody>
					<tr>
						<td style="width: 10%;vertical-align: middle;">教育程度</td>
						<td>
							<label class="input">
								<input type="text" name="providerInfo.providerEduLev" value="${detail.providerInfo.providerEduLev}" maxlength="50" />
							</label>
						</td>
						<td style="vertical-align: middle;">籍贯</td>
						<td>
							<label class="input">
								<input type="text" name="providerInfo.providerBirthPlace" value="${detail.providerInfo.providerBirthPlace}" maxlength="50" />
							</label>
						</td>
						<td style="vertical-align: middle;">健康状况</td>
						<td>
							<label class="input">
								<input type="text" name="providerInfo.health" value="${detail.providerInfo.health}"/>
							</label>
						</td>
					</tr>
					<tr>
						<td style="width: 10%;vertical-align: middle;">健康报告时间</td>
						<td>
							<label class="input">
								<input type="text" class="form-control datepicker" id="healthDate" data-dateformat="dd/mm/yyyyy"
									   name="providerInfo.healthReportTimeString"
									   value="${detail.providerInfo.healthReportTimeString}"/>
							</label>
						</td>
						<td style="width: 10%;vertical-align: middle;">常用地址</td>
						<td>
							<label class="input">
								<input type="text" id="commonAddress" name="providerInfo.commonAddress"  placeholder="搜索地点(小区、街道或大厦)" value="${detail.providerInfo.commonAddress}" required autocomplete="off"/>
							</label>
							<div class="autocomplete">
								<ul id="autocomplete"></ul>
							</div>
						</td>
						<td style="width: 10%;vertical-align: middle;"></td>
						<td></td>
					</tr>
					</tbody>
				</table>
				<hr class="simple"/>
				<table class="table table-bordered" style="clear: both">
					<tbody>
					<tr>
						<td style="width: 10%;vertical-align: middle;">头像</td>
						<td>
							<section class="col col-4">
								<label class="input input-file">
									<div class="button">
										<input type="file" name="file" id="fileUpload">文件
									</div>
									<input type="text" placeholder="选择图片" readonly>
								</label>
							</section>
							<section class="col col-5">
								<input type="hidden" name="userInfo.headPic" id="pic"/>
								<img id="show" src="/sys/tools/download?name=${detail.userInfo.headPic}">
							</section>
						</td>
					</tr>
					</tbody>
				</table>
				<hr class="simple"/>
				<table>
					<tr>
						<section>
							<div class="summernote">${detail.providerInfo.providerIntroduction}</div>
						</section>
					</tr>
				</table>
			</form>
			<div class="widget-footer smart-form" style="background-color: #ccc0;border-top:0px">

				<div class="btn-group">
					<button class="btn btn-sm btn-primary" type="button" id="ucancel">
						<i class="fa fa-times"></i> 取消
					</button>
				</div>
				<c:if test="${detail.providerInfo.status==0}">
					<div class="btn-group">
						<button class="btn btn-sm btn-primary" type="button" id="audit">
							<i class="fa fa-times"></i> 审核
						</button>
					</div>
				</c:if>
				<div class="btn-group">
					<button class="btn btn-sm btn-success" type="button" id="psb">
						<i class="fa fa-check"></i> 保存
					</button>
				</div>
				<label class="checkbox pull-left">
				</label>
			</div>

		</div>
		<!-- end widget content -->

	</div>
	<div class="scroll" id="mapselect" style="display:none;">
		<div id="map"></div>
	</div>
</div>
<script>
    $('#healthDate').datepicker({
		dateFormat: 'dd/mm/yy',
		prevText: '<i class="fa fa-chevron-left"></i>',
		nextText: '<i class="fa fa-chevron-right"></i>'
   });
    $("#fileUpload").change(function () {
        console.log("123");
        $(this).parent().next().val($(this).val());
        var fileObj = document.getElementById("fileUpload").files[0]; // js 获取文件对象
        if (typeof (fileObj) == "undefined" || fileObj.size <= 0) {
            alert("请选择图片");
            return;
        }
        var formFile = new FormData();
        formFile.append("action", "UploadVMKImagePath");
        formFile.append("file", fileObj);
        $.ajax({
		   data: formFile,
		   type: "POST",
		   url: "/sys/tools/upload",
		   cache: false,
		   processData: false,
		   contentType: false,
		   success: function (url) {
			   $("#show").prop("src", "/sys/tools/download?name=" + url);
			   $("#pic").val(url);
		   },
		   error: function () {
			   alert("上传失败.");
		   }
	   });
    });
</script>