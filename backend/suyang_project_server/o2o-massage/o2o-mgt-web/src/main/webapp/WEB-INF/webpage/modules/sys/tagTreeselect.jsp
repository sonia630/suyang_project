<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/WEB-INF/webpage/include/jstree.jsp"%>
<script type="text/javascript">
	
	$(document).ready(function() {
		$("#cl").click(function(){
			gDialog.fClose();
		});
		
		var selectedId;
		var selectedName;
		
		$("#sb").click(function(){
			
			if(!selectedId){
				alert("请选择节点！");
				return;
			}
			
			$("#${key}Name").val(selectedName);
			$("#${key}Id").val(selectedId);
			gDialog.fClose();
		});
		
		
		$('#cot').jstree({
			"core" : {
				"animation" : 2,
				"check_callback" : true,
				"multiple" : false,
				"themes" : {
					"dots" : true
				},
				'data' : {
					'url' : '${url}?extId=${extId}&module=${module}&selectIds=${selectIds}',
					'data' : function(node) {
						return {
							'id' :node.id
						};
					}
				}
			},
			"plugins" : [ "search", "sort", "unique" ]
			})
			.on("select_node.jstree",function(e,data){
				selecredArrays = $('#cot').jstree(true).get_selected();
				selectedId = selecredArrays[0];
				selectedName = $('#cot').jstree(true).get_text(selectedId);
			})
		
			var to = false;
			 $('#key').keyup(function () {
			   if(to) { clearTimeout(to); }
			   to = setTimeout(function () {
			     var v = $('#key').val();
			     $('#cot').jstree(true).search(v);
			   }, 250);
			 });
			
			$("#st").click(function(){
				$("#search").slideToggle(200);
				$("#txt").toggle();
				$("#key").focus();
			});
	});
	
</script>
<div class="modal-body">
	<div style="position:absolute;right:25px;cursor:pointer;" id="st">
		<i class="fa fa-fw fa-search"></i><label id="txt">搜索</label>
	</div>
	<div id="search" style="padding:0 0 10px 15px; display: none;">
		<label for="key" style="float:left;">关键字：</label>
		<input type="text" class="empty" id="key" name="key" maxlength="50" style="width:180px;">
	</div>
	<div id="cot"></div>
</div>
<div class="modal-footer">
	<a href="#" class="btn" data-dismiss="modal" id="cl">关闭</a>
	<a class="btn btn-primary" id="sb">保存</a>
</div>
