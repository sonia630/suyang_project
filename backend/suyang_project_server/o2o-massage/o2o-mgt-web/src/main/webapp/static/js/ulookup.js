
	function highlightRow(){
		$("#userLookupTable tbody tr").click( function( e ) {
	        if ($(this).hasClass('row_selected')) {
	            $(this).removeClass('row_selected');
	        }else {
	            $(this).addClass('row_selected');
	        }

	        var length = $("#userLookupTable tbody tr.row_selected").length;
			if( length == 0){
				if(!$("#deluser").hasClass("disabled")){
					$("#deluser").addClass("disabled")
				}
			}else{
				if($("#deluser").hasClass("disabled")){
					$("#deluser").removeClass("disabled")
				}
			}
	    });
	}

	function ajaxInvoke(url){

		var ids = new Array();
		var trs = new Array();
		$("#userLookupTable tbody tr.row_selected").each(function(){
			var td = $(this).children('td:eq(1)');
			ids.push(td.find("div").html());
			trs.push($(this));
		});
		var oTable = $('#userLookupTable').dataTable();
		var orgId = $("#uorgId").html();
		$.ajax({
			type : 'post',
			dataType : "json",
			url: url,
			data: {userIds : ids.toString(),orgId : orgId},
			cache : false,
			success : function(content) {
			    if(trs != null) {
			       for(tr in trs){
				       oTable.fnDeleteRow(tr,null,false);
			       }
                    HWTX.refreshTablePipeline($('#userLookupTable'));
			    }
				message_box.show(content.message,content.code);
				HWTX.gDialogClose();
				updateTree(orgId,content.userCount);
			},
			error : function(content){
				message_box.show(content.message,content.code);
			}
		});
	}

	function updateTree(orgId,ucount){
		$('#orgcontainer li[id="'+orgId+'"] span').html(ucount);
	}

	$(document).ready(function() {


        $('#userLookupTable').dataTable( {
			 "sDom": "<'dt-toolbar'<'col-xs-12 col-sm-6'<'toolbar'>><'col-sm-6 col-xs-12 hidden-xs'l>r>"+
					 "t"+
					 "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-xs-12 col-sm-6'p>>",
			 "serverSide": true,
			 "searching": false,
			 "lengthMenu": [ 25, 50, 75, 100 ],
			 "ajax": $.fn.dataTable.pipeline( {
				  url: '/sys/user/data',
				  pages: 2,
				  data: {
					  "userName": $("#userName").val(),
					  "startDate":$("#startdate").val(),
					  "finishDate":$("#finishdate").val(),
					  "orgId":$("#searchSysUserOrgId").val(),
					  "includeUser":$("#includeUser").val()
				  },
				  method:'post'
			 }),
			 "drawCallback" : function(oSettings) {
				 highlightRow();
             },
			 "columns": [
				 {"data": "num","orderable": false},
				 {"data": "name"},
                 {"data": "phone"},
				 {"data": "loginDate"}
			 ],
			 columnDefs: [
				 {
					 targets: 1,
					 "render": function (data, type, row, meta) {
                         return '<div class=\"hide\">'+row.userId+'</div>'+data;
					 }
				 }
			 ]
        }).on( 'init.dt', function () {
            $('table').removeAttr("style");
        });

	    document.onkeydown = function(e){
	        var ev = document.all ? window.event : e;
	        if(ev.keyCode==13) {
	        	$("#userSubmit").click();
	         }
	    }

	    $("#osb").click(function(){
			ajaxInvoke("/sys/org/assignUser");
	    });

	    $("#userSubmit").click(function(){
            HWTX.refreshTablePipeline($('#userLookupTable'));
	    });

	    $("#deluser").click(function(){
	    	ajaxInvoke("/sys/org/delAssignedUser");
	    });

		$('#startdate').datetimepicker({
			format: 'yyyy-mm-dd hh:ii:ss',
			autoclose: true,
			todayBtn: true,
			language : "zh-CN"
		});

		$('#finishdate').datetimepicker({
			format: 'yyyy-mm-dd hh:ii:ss',
			autoclose: true,
			todayBtn: true,
			language : "zh-CN"
		});

		$("#clearstart").click(function(){
			$("#startdate").val("");
		});
		$("#clearend").click(function(){
			$("#finishdate").val("");
		});

		$("#startdate").on("",function (e) {
            $('#finishdate').data("DateTimePicker").setMinDate(e.date);
        });

		$('#startdate').datetimepicker().on("changeDate", function(e){
			$('#finishdate').datetimepicker("setStartDate",e.date);
		});

		$("#finishdate").datetimepicker().on("changeDate",function (e) {
            $('#startdate').datetimepicker("setEndDate",e.date);
        });
	});