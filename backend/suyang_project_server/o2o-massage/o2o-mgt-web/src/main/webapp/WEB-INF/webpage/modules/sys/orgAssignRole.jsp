<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/webpage/include/taglib.jsp" %>
<%@ include file="/WEB-INF/webpage/include/jshow.jsp" %>
<style>
.select2-container-multi{
    border: 0px;
}
</style>
<script type="text/javascript">

	var roles = new Array();
	
	$(document).ready(function() {
		
		
		var tmr = $("#roleIds").val().split(",");
		if(tmr[0]!=''){
			roles = roles.concat(tmr);
		}
		
		$('#oarscontainer').jstree(
		{
			"core" : {
				"animation" : 2,
				"check_callback" : true,
				"multiple" : false,
				"themes" : {
					"dots" : true
				},
				'data' : {
					'url' : '${ctx}/sys/role/tree',
					'data' : function(node) {
						return {
							'id' : node.id
						};
					}
				}
			},
			"plugins" : [ "sort"]
		});
		
		$('#oarscontainer').on("dblclick.jstree", ".jstree-anchor", $.proxy(function (e) {
			e.preventDefault();
			$(e.currentTarget).focus();
			obj = $('#oarscontainer').jstree(true).get_node(e.currentTarget);
			var index = jQuery.inArray(obj.id,roles);
			if(index == -1 && obj.id != '-1' && !obj.icon){
				$("#choice").append("<li class=\"select2-selection__choice\">"+
					"<div>"+obj.text+"</div>"+
					"<span onclick=\"removeChoice(this,'"+obj.id+"')\" class=\"select2-selection__choice__remove\" tabindex=\"-1\"></span>"+
					"</li>");
				roles.push(obj.id);
			}
		}, this));
		
		$("#orgARole").click(function(){
			$.post("${ctx}/sys/org/assignRole", { "orgId": '${orgId}', "roles": roles.toString()},
				function(data) {
					message_box.show(data.message,data.code);
					gDialog.fClose();
				},"json").fail(function(data) { message_box.show(data.message,data.code); });
		});
});
		 
	 function removeChoice(choice,id){
		 $(choice).closest(".select2-selection__choice").fadeOut('fast',function(){
			$(this).remove();
			roles = jQuery.grep(roles, function (a) { return a != id; });
		 });
	 }
</script>	
<div class="modal-body">
	<input id="roleIds" value="${roleIds}" type="hidden"/>
	<div class="row" style="margin-top: 15px;margin-left: 13px">
		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-5">
			<div id="oarscontainer"></div>
		</div>
		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-7 select2-container select2-container-multi select2">
			<ul class="select2-selection__rendered" id="choice">
				<c:forEach items="${roles}" var="role">
					<li class="select2-selection__choice">
						<div>${role.name}</div>
						<span onclick="removeChoice(this,'${role.id}')" class="select2-selection__choice__remove" tabindex="-1"></span>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</div>
<div class="modal-footer">
	<a href="#" class="btn" data-dismiss="modal" id="cl">关闭</a>
	<a class="btn btn-primary" id="orgARole">保存</a>
</div>