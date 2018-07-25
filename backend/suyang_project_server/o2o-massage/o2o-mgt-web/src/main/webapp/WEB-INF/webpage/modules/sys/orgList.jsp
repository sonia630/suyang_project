<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/webpage/include/jstree.jsp"%>
<style>
.smart-form footer {
	background: none;
}
.orgboard{
 	border: 1px solid #BBBBBB;
    border-radius: 4px;
    overflow: auto;
    padding: 10px 15px;
    position: relative;
}

.u-tree {
    border-right: 1px solid #DDDDDD !important;
    float: left;
    height: 350px;
    overflow-y: auto;
    position: relative;
}
.u-text {
    border-right: 1px solid #DDDDDD !important;
    float: left;
    height: 326px;
    overflow-y: auto;
    position: relative;
    margin-left: 20px;
}
</style>
<script src="/static/js/olist.js"></script>
<div data-widget-sortable="false" data-widget-custombutton="false"
	data-widget-fullscreenbutton="false" data-widget-deletebutton="false"
	data-widget-togglebutton="false" data-widget-editbutton="false"
	data-widget-colorbutton="false" id="wid-id-o3" class="jarviswidget well">
	<div>
		<div class="jarviswidget-editbox"></div>
		<div class="widget-body">
			<a href="javascript:void(0);" class="btn btn-primary" id="orga"><i class="fa fa-plus"></i> 添加</a>
			<a href="javascript:void(0);" class="btn btn-primary" id="orgd"><i class="fa fa-minus"></i> 删除</a>
			<a href="javascript:void(0);" class="btn btn-primary" id="usera"><i class="fa fa-user"></i> 分配用户</a>
			<a href="javascript:void(0);" class="btn btn-primary" id="rolea"><i class="fa fa-magic"></i> 分配角色</a>
			<hr class="simple">
			<div class="tab-content padding-10" id="myTabContent1">
				<div class="row">
					<div class="col-xs-12 col-sm-4 col-md-4 col-lg-4 orgboard u-tree">
						<div id="orgcontainer"></div>
					</div>
					<div class="col-xs-12 col-sm-7 col-md-7 col-lg-6 orgboard u-text">
						<input type="hidden" id="newCreateId" />
						<form id="orgForm" method="post" action="/sys/org/save" class="smart-form"
							onsubmit="return ajaxSubmit(this,'json',saveCallback)">
							<fieldset>
								<input type="hidden" name="save.type" id="save_type" />
								<input type="hidden" name="id" id="sysOrg_orgId" />
								<input type="hidden" name="parentId" id="sysOrg_pid" />
								<div class="row">
									<section class="col col-6">
										<label class="control-label">上级机构:</label>
										<label class="input">
											 <input type="text"	id="sysOrg_pname" />
										</label>
									</section>
									<section class="col col-6">
										<label class="control-label">已分配用户:</label>
										<label class="input">
											<a class="btn btn-success btn-sm" id="checkUser"><i class="fa fa-user"></i> 查看</a>
										</label>
									</section>
									<section class="col col-6">
										<label class="input">
											
										</label>
									</section>
								</div>
								<div class="row">
									<section class="col col-6">
										<label class="control-label">名称:</label> <label class="input">
											<input type="text" name="name" maxlength="50"
											class="required" id="sysOrg_name" required />
										</label>
										<div class="help-block with-errors"></div>
									</section>
								</div>
								<div class="row">
									<section class="col col-6">
										<label class="control-label">描述:</label> <label class="input">
											<input type="text" name="description" maxlength="50"
											id="sysOrg_description" />
										</label>
										<div class="help-block with-errors"></div>
									</section>
								</div>
							</fieldset>
							<footer>
								<input id="btnSubmit" class="btn btn-primary" type="submit"
									value="保存" />
							</footer>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>