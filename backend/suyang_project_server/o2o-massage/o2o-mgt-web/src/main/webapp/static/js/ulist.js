function delAction(action) {
    var url = $(action).attr("url");
    var tr = $(action).parent().parent();
    var trs = []
    trs.push(tr);
    del(url, trs);
}

function editAction(action) {
    var url = $(action).attr("url");
    HWTX.gDialogCreate('修改用户', url, {}, 400, 280);
}

function del(url, trs) {
    var oTable = $('#userTable').dataTable();
    gDialog.fConfirm('确认执行', '你确定执行这个操作么？',
        function (rs) {
            if (rs) {
                $.ajax({
                    type: 'post',
                    dataType: "json",
                    url: url,
                    cache: false,
                    success: function (content) {
                        if (trs != null) {
                            for (tr in trs) {
                                oTable.fnDeleteRow(tr, null, false);
                            }
                            HWTX.refreshTablePipeline($('#userTable'));
                        }
                        message_box.show(content.message, content.code);
                    },
                    error: function (content) {
                        message_box.show(content.message, content.code);
                    }
                });
            }
        }
    );
}

function lockAction(action) {
    var url = $(action).attr("url");
    var tr = $(action).parent().parent();
    var trs = []
    trs.push(tr);
    lock(url, trs);
}

function newPage(action) {
    loadURL($(action).attr("url"),container);
}

function lock(url, trs) {
    gDialog.fConfirm('确认执行', '你确定执行这个操作么？',
        function (rs) {
            if (rs) {
                $.ajax({
                    type: 'post',
                    dataType: "json",
                    url: url,
                    cache: false,
                    success: function (content) {
                        if (trs != null) {
                            HWTX.refreshTablePipeline($('#userTable'));
                        }
                        message_box.show(content.message, content.code);
                    },
                    error: function (content) {
                        message_box.show(content.message, content.code);
                    }
                });
            }
        }
    );
}

function highlightRow() {
    $("#userTable tbody tr").click(function (e) {
        if ($(this).hasClass('row_selected')) {
            $(this).removeClass('row_selected');
        } else {
            $(this).addClass('row_selected');
        }
    });
}

$(document).ready(function () {

    $('#userTable').dataTable({
          "sDom": "<'dt-toolbar'<'col-xs-12 col-sm-8'<'toolbar'>><'col-sm-4 col-xs-12 hidden-xs'l>r>"+
                  "t"+
                  "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-xs-12 col-sm-6'p>>",
         "serverSide": true,
         "searching": false,
         "lengthMenu": [ 25, 50, 75, 100 ],
         "ajax": $.fn.dataTable.pipeline( {
              url: '/sys/user/data',
              pages: 2,
              data: {
                  "name": $("#userName").val(),
                  "startDate":$("#startdate").val(),
                  "finishDate":$("#finishdate").val(),
                  "userType":$("#userType").val()
              },
              method:'post'
          }),
         "drawCallback" : function(oSettings) {
             if ($('#userSubmit').attr("isclick")!=1) {
                 loadContent($("div.toolbar"), "/sys/user/showToolbar");
             }
             highlightRow();
         },
         "columns": [
             {"data": "num", "orderable": false},
             {"data": "name"},
             {"data": "phone"},
             {"data": "status"},
             {"data": "loginDate"},
             {"data": "num"}
         ],
         columnDefs: [
             {
                 targets: 1,
                 "render": function (data, type, row, meta) {
                     return '<div class=\"hide\">' + row.userId + '</div>'+data;
                 }
             },
             {
                 targets: 3,
                 "render": function (data, type, row, meta) {
                     if(row.status ==1){
                         return "正常";
                     }else if(row.status==2){
                         return "锁定";
                     }else{
                         return "删除";
                     }
                 }
             },
             {
                 targets: 5,
                 "render": function (data, type, row, meta) {
                     var value = '<a onclick="editAction(this)" url="' + '/sys/user/showEdit?id='
                                 + row.userId + '">修改</a>&nbsp;&nbsp;'
                     if(row.userType==1){
                         value +='<a onclick="delAction(this)" url="' + '/sys/user/d?ids=' + row.userId + '">删除</a>&nbsp;&nbsp;';
                     }

                     if(row.userType>1){
                         value +='<a onclick="newPage(this)" url="' + '/sys/user/detail?id=' + row.userId + '">用户信息</a>&nbsp;&nbsp;';
                     }
                     if(row.provider){
                         value +='<a onclick="newPage(this)" url="' + '/serviceInfo/showAssign?userId=' + row.userId + '">分配服务</a>&nbsp;&nbsp;';
                     }

                     if (row.status == 0) {
                         return value + '<a onclick="lockAction(this)" url="'
                                + '/sys/user/unlock?ids=' + row.userId + '">解锁</a>';
                     } else {
                         return value + '<a onclick="lockAction(this)" url="'
                                + '/sys/user/lock?ids=' + row.userId + '">锁定</a>';
                     }
                 }
             }
         ]
     });
});

function getSelectedTr() {
    var length = $("#userTable tbody tr.row_selected").length;
    if (length == 0 || length > 1) {
        alert("请选择一个用户");
        return "";
    }
    return $("#userTable tbody tr.row_selected").find("td:eq(1) div").html();
}