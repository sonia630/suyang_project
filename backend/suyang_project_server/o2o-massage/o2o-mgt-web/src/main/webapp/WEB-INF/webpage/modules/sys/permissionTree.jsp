<%@ page contentType="text/html;charset=UTF-8"%>

<script type="text/javascript">

	$(document).ready(function() {
		$("#cl").click(function(){
			gDialog.fClose();
		});

		var selected = [];

		$("#sb").click(function(){
			$.ajax({
				type:'post', dataType:"json", url: '/sys/role/assignIt', cache: false,
				data:{'selected':selected.toString(),
					  'roleId':'${roleId}',
					  'type':'${type}'
				},
				success: function(data){
					gDialog.fClose();
					message_box.show(data.message,'info');
					if("${type}"=="menu"){
						$('#roleMenus').jstree(true).refresh("#");
					}else{
						$('#resMenus').jstree(true).refresh("#");
					}
				},
				error: function(data){
					message_box.show(data.message,'error');
				}
			});
		});

		$('#co').jstree({
			"core" : {
				"animation" : 2,
				"check_callback" : true,
				"multiple" : true,
				"themes" : {
					"dots" : true
				},
				'data' : {
					'url' : '/sys/role/${excludeUrl}?roleId=${roleId}',
					'data' : function(node) {
						return {
							'id' :node.id
						};
					}
				}
			},
			"plugins" : [ "search", "sort", "unique","checkbox" ]
			})
			.on("select_node.jstree",function(e,data){
				var selecredArrays = $('#co').jstree(true).get_selected();
				selected = selecredArrays;
			})
			.on("deselect_node.jstree Event", function(e,data){
				selected = data.selected;
			});
	});
</script>
<div class="modal-body">
	<div id="co"></div>
</div>
<div class="modal-footer">
	<a href="#" class="btn" data-dismiss="modal" id="cl">关闭</a>
	<a class="btn btn-primary" id="sb">保存</a>
</div>