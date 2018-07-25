<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/webpage/include/jstree.jsp"%>
<%@ include file="/WEB-INF/webpage/include/jshow.jsp" %>
<script type="text/javascript">
	$(document).ready(function() {
		$('#ascontainer').jstree(
		{
			"core" : {
				"animation" : 2,
				"check_callback" : true,
				"multiple" : false,
				"themes" : {
					"variant" : "large",
					"dots" : true
				},
				'data' : {
					'url' : '/sys/role/tree',
					'data' : function(node) {
						return {
							'id' : node.id
						};
					}
				}
			},
			"plugins" : [ "sort"]
		})
		.on(
		"select_node.jstree",
		function(e, data) {
			var icon=$('#ascontainer').jstree(true).get_icon(data.node.id);
			if(!icon){
				$("#right").show();
				$('#roleMenus').jstree({
					'core' : {
					  'data' : {
					    'url' : '/sys/role/menus',
					    'data' : function (node) {
					    	var selecredArrays = $('#ascontainer').jstree(true).get_selected();
					     	return { 'id' : selecredArrays[0]};
					    }
					  }
					},
					"plugins" : [ "search","sort","checkbox" ]
				});
				if($('#roleMenus').jstree().is_loaded("#")){
					$('#roleMenus').jstree().load_node("#");
				}
			}else{
				$("#right").hide();
			}
		});
		
		
		 var to = false;
		 $('#akey').keyup(function () {
		   if(to) { clearTimeout(to); }
		   to = setTimeout(function () {
		     var v = $('#akey').val();
		     $('#roleMenus').jstree(true).search(v);
		   }, 250);
		 });
		  
				
		$("#plus").click(function(){
			var selecredArrays = $('#ascontainer').jstree(true).get_selected();
			gDialog.fCreate({
				title : '选择菜单',
				url : '/sys/role/permission_tree?type=menu&id='+selecredArrays[0],
				width : 500,
				height: 400
			}).show();
		});
		
		$("#assearch").click(function(){
			$("#assearchText").slideToggle(200);
			$("#akey").focus();
		});

		$("#minus").click(function(){
			var selecredRoles = $('#ascontainer').jstree(true).get_selected();
			var selecredfuns = $('#roleMenus').jstree(true).get_selected();
			if(selecredfuns.length==0){
				message_box.show("请选择删除项",'info');
			}else{
				$.ajax({
					type:'post', dataType:"json", url: '/sys/role/deleteIt', cache: false,
					data:{'selected':selecredfuns.toString(),
						  'roleId':selecredRoles[0],
						  'type':'role'
					},
					success: function(data){
						message_box.show(data.message,'info');
						$('#roleMenus').jstree(true).refresh("#");
					},
					error: function(data){
						message_box.show(data.message,'error');
					}
				});	
			}
		});
});
</script>	
<div class="row">
	<div class="col-xs-12 col-sm-7 col-md-7 col-lg-5">
		<div id="ascontainer"></div>
	</div>
	<div class="col-xs-12 col-sm-7 col-md-7 col-lg-7" id="right" style="display: none;">
 	  <div id="assearchText" style="padding:0px 0 7px 0px;display: none;">
		<label for="akey" style="float:left;padding:5px 5px 3px;">关键字：</label>
		<input type="text" class="empty" id="akey" name="akey" maxlength="50">
	  </div>
	  <div class="row" style="margin-top: 2px ">
		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6" id="roleMenus"></div>
		<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2 fa fa-fw fa-plus" title="添加" style="margin-top: 5px;cursor:pointer;float: left;" id="plus"></div>
		<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2 fa fa-fw fa-minus" title="删除" style="margin-top: 5px;cursor:pointer;float: left;" id="minus"></div>
		<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2 fa fa-fw fa-search" title="检索" style="margin-top: 5px;cursor:pointer;float: left;" id="assearch"></div>
	 </div>
	</div>
</div>