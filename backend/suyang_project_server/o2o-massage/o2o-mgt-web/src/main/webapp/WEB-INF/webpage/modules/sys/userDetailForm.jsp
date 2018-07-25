<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/webpage/include/validator.jsp"%>
<script>
	$(document).ready(function() {
        pageSetUp();
        $("#userDetailForm").validate({
		   submitHandler: function () {
			   submit($("#userDetailForm"));
		   }
	   	});
        $("#sb").click(function(){
            $("#userDetailForm").submit();
        });
	});

    function submit(form) {
        return ajaxSubmit(form, 'json', saveCallback);
    }

    function saveCallback(json) {
        if (json.code == 'success') {
			message_box.show(json.message, 'success');
		} else {
			message_box.show(json.message, 'error');
		}
		return true;
	}
</script>

<div data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-togglebutton="false" data-widget-fullscreenbutton="false" data-widget-sortable="false">
	<div>
		<div class="jarviswidget-editbox">
		</div>
		<!-- end widget edit box -->

		<!-- widget content -->
		<div class="widget-body">
			<form action="/sys/user/update" method="post" id="userDetailForm" class="smart-form" onsubmit="return false">
			<input type="hidden" name="userId" value="${detail.userId}" />
			<table id="user" class="table table-bordered" style="clear: both">
			<tbody>
				<tr>
					<td style="width: 15%;vertical-align: middle;">用户ID</td>
					<td>
						<label class="input">
							<input type="text" name="name" value="${detail.name}" maxlength="50" required />
						</label>
					</td>
					<td style="vertical-align: middle;">手机号</td>
					<td>
						<label class="input">
							<input type="text" name="phone" value="${detail.phone}" maxlength="50" required />
						</label>
					</td>
					<td style="vertical-align: middle;">用户名</td>
					<td>
						<label class="input">
							<input type="text" name="realName" value="${detail.realName}" required />
						</label>
					</td>
				</tr>
				<tr>
					<td style="width: 15%;vertical-align: middle;">身份证</td>
					<td>
						<label class="input">
							<input type="text" name="idNum"  value="${detail.idNum}" required/>
						</label>
					</td>
					<td style="width: 15%;vertical-align: middle;">电子邮箱:</td>
					<td>
						<label class="input">
							<input type="text" name="email"  value="${detail.email}"/>
						</label>
					</td>
					<td style="width: 15%;vertical-align: middle;">性别:</td>
					<td>
						<div class="inline-group">
							<label class="radio">
								<input type="radio" name="gender" value="1"
									   <c:if test="${detail.gender==1}">checked="checked"</c:if>>
								<i></i>男</label>
							<label class="radio">
								<input type="radio" name="gender" value="0"
									   <c:if test="${detail.gender==0}">checked="checked"</c:if>>
								<i></i>女</label>
						</div>
					</td>
				</tr>
				</tbody>
			</table>
			</form>
			<div class="widget-footer smart-form" style="background-color: #ccc0;border-top:0px">

				<div class="btn-group">

					<button class="btn btn-sm btn-primary" type="button" onclick="backTo('/sys/user/')">
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
		<!-- end widget content -->

	</div>
	<!-- end widget div -->

</div>
