<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fns" uri="/tags/o2o-functions" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<script src="/static/js/plugin/summernote/summernote.js"></script>
<script src="/static/js/plugin/summernote/summernote-zh-CN.js"></script>
<script type="text/javascript" src="/static/js/plugin/jshow/1.0/jshow.utils.js"></script>
<script>
	$(document).ready(function() {
        pageSetUp();
		$("#serviceInputForm").validate({
			submitHandler: function () {
				submit($("#serviceInputForm"));
			}
		});
		$("#sb").click(function(){
            $("#serviceInputForm").submit();
		});
        var $summernote = $('.summernote').summernote({
			height: 280,
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
                    $('#summernote').summernote('code',$("#serviceInfoDes").val());
                }
			}
		});
	});

    function submit(form) {
        $("#serviceInfoDes").val($('.summernote').summernote('code'));
        return ajaxSubmit(form, 'json', saveCallback);
    }

    function saveCallback(json) {
        if (json.code == 'success') {
			message_box.show(json.message, 'success');
            $(".summernote").summernote( 'destroy' );
            backTo("/serviceInfo/");
		} else {
			message_box.show(json.message, 'error');
		}
		return true;
	}
</script>

<div class="jarviswidget jarviswidget-color-blue" id="wid-id-0" data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-togglebutton="false" data-widget-fullscreenbutton="false" data-widget-sortable="false">
	<header>
		<span class="widget-icon"> <i class="fa fa-pencil"></i> </span>
		<h2>服务详细信息</h2>

	</header>

	<!-- widget div-->
	<div>

		<!-- widget edit box -->
		<div class="jarviswidget-editbox">
			<!-- This area used as dropdown edit box -->

		</div>
		<!-- end widget edit box -->

		<!-- widget content -->
		<div class="widget-body no-padding">
			<form action="/serviceInfo/save" method="post" id="serviceInputForm"
				  class="smart-form" onsubmit="return false">
				<input type="hidden" name="serviceInfo.serviceId" value="${service.serviceInfo.serviceId}" />
				<input type="hidden" name="serviceInfo.description" id="serviceInfoDes"/>
				<fieldset>
					<div class="row">
						<section class="col col-4">
							<label class="label">服务名称:</label>
							<label class="input"><i class="icon-append fa fa-tags"></i>
								<input type="text" name="serviceInfo.serviceName" value="${service.serviceInfo.serviceName}"
									   maxlength="50" required />
							</label>
						</section>
						<section class="col col-4">
							<label class="label">顺序:</label>
							<label class="input"><i class="icon-append fa fa-sort-amount-asc"></i>
								<input type="text" class="number digits" name="serviceInfo.sortOrder" value="${service.serviceInfo.sortOrder}" required/>
							</label>
						</section>
						<section class="col col-4">
							<label class="label">价格:</label>
							<label class="input"><i class="icon-append fa fa-money"></i>
								<input type="text" class="number rate2" name="srvProviderSrvRel.price" value="${service.srvProviderSrvRel.priceString}"
									   required />
							</label>
						</section>
					</div>
					<div class="row">
						<section class="col col-3">
							<label class="label">时长:</label>
							<label class="input"><i class="icon-append fa fa-tag"></i>
								<input type="text" class="number" name="srvProviderSrvRel.estimatedTime"  value="${service.srvProviderSrvRel.estimatedTime}" required/>
							</label>
						</section>
						<section class="col col-5">
							<label class="label">简介:</label>
							<label class="input"><i class="icon-append fa fa-file-text-o"></i>
								<input type="text" name="serviceInfo.serviceSummary"  value="${service.serviceInfo.serviceSummary}" maxlength="100" required/>
							</label>
						</section>
						<section class="col col-4">
							<label class="label">分类</label>
                            <select multiple style="width: 100%" class="select2" name="catIds">
                                <c:forEach items="${cats}" var="cat">
									<option value="${cat.id}" <c:if test="${fns:arrayContains(service.catIds, cat.id)}">selected</c:if>>${cat.catName}</option>
                                </c:forEach>
                            </select>
						</section>
					</div>
					<div class="row">
						<section class="col col-4">
							<label class="label">说明图片</label>
							<label class="input input-file">
								<div class="button">
									<input type="file" name="file" id="fileUpload">文件
								</div>
								<input type="text" placeholder="选择图片" readonly>
							</label>
						</section>
						<section class="col col-5">
							<input type="hidden" name="serviceInfo.pic" id="pic"/>
							<img id="show" src="/sys/tools/download?name=${service.serviceInfo.pic}">
						</section>
					</div>
				</fieldset>
			</form>
			<section class="col col-12">
				<label class="control-label" style="margin-left: 12px">描述:</label>
				<div class="summernote">${service.serviceInfo.description}</div>
			</section>
			<div class="widget-footer smart-form">

				<div class="btn-group">

					<button class="btn btn-sm btn-primary" type="button" onclick="backTo('/serviceInfo/')">
						<i class="fa fa-times"></i> 取消
					</button>

				</div>
				<div class="btn-group">

					<button class="btn btn-sm btn-success" type="button" id="sb">
						<i class="fa fa-check"></i> 保存
					</button>

				</div>

				<label class="checkbox pull-left">
				</label>

			</div>

		</div>
	</div>
</div>
<script>

	$("#fileUpload").change(function () {
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