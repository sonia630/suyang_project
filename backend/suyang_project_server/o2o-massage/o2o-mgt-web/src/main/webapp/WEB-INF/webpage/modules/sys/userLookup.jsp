<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/webpage/include/taglib.jsp" %>
<%@ include file="/WEB-INF/webpage/include/datetimepicker.jsp"%>
<%@ include file="/WEB-INF/webpage/include/jshow.jsp" %>
<style>
.row_selected{
	background-color: #B0BED9;
}
</style>
<script src="/static/js/ulookup.js"></script>
<div id="uorgId" class="hide">${orgId}</div>
<div class="modal-body">
<div class="jarviswidget jarviswidget-color-darken" data-widget-editbutton="false" style="margin: -16px 0 30px;">
	<div>
		<div class="widget-body no-padding">
			<div class="widget-body-toolbar" style="margin: -1px;border-bottom: 2px solid #CCCCCC;">
				<form action="/sys/user/" method="post" id="userLookupForm"
					onsubmit="return false" class="form-inline" role="form">
					<input name="orgId" type="hidden" id="searchSysUserOrgId" value="${orgId}"/>
					<input name="includeUser" type="hidden" id="includeUser" value="${includeUser}"/>
					<fieldset style="top: 3px">
						<c:if test="${! empty includeUser}">
							<button class="btn btn-primary disabled" id="deluser">删除</button>
						</c:if>
						<div class="form-group">
							<div class="input-icon-right">
								<i class="fa fa-user"></i> <input type="text" placeholder="用户名"
									id="userName" class="form-control"
									name="name">
							</div>
						</div>
						<div class="form-group">
							<div class="input-icon-right">
								<i class="fa fa-times" style="cursor: pointer;" id="clearstart">
									<i class="fa fa-calendar"></i>
								</i> <input type="text" data-format="yyyy-MM-dd HH:mm:ss"
									name="startDate" id="startdate"
									placeholder="开始时间" class="form-control">
							</div>
						</div>
						<div class="form-group">
							<div class="input-icon-right">
								<i class="fa fa-times" style="cursor: pointer;" id="clearend">
									<i class="fa fa-calendar"></i>
								</i> <input type="text" data-format="yyyy-MM-dd" name="
									finishDate" id="finishdate" placeholder="结束时间"
									class="form-control">
							</div>
						</div>
						<button class="btn btn-primary" id="userSubmit">查询</button>
					</fieldset>
				</form>
			</div>
			<table cellpadding="0" cellspacing="0" border="0" class="table table-bordered table-hover" id="userLookupTable">
				<thead>
				<tr>
					<th>序号</th>
					<th><i class="fa fa-fw fa-user text-muted hidden-md hidden-sm hidden-xs"></i>用户名</th>
					<th><i class="fa fa-fw fa-phone text-muted hidden-md hidden-sm hidden-xs"></i>手机</th>
					<th><i class="fa fa-fw fa-calendar txt-color-blue hidden-md hidden-sm hidden-xs"></i> 登陆时间</th>
				</tr>
				</thead>
			</table>
		</div>
	</div>
</div>
</div>
<div class="modal-footer">
	<a href="#" class="btn" data-dismiss="modal" id="cl">关闭</a>
	<c:if test="${empty includeUser}">
		<a class="btn btn-primary" id="osb">保存</a>
	</c:if>
</div>