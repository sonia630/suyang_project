<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/webpage/include/taglib.jsp" %>
<%@ include file="/WEB-INF/webpage/include/jshow.jsp"%>
<style>
    .dataTables_length{
        padding-top: 5px;
    }

    .row_selected {
        background-color: #B0BED9;
    }
    a{
        cursor: pointer;
    }
</style>
<section id="widget-grid">
    <div class="jarviswidget jarviswidget-color-darken" id="wid-id-0"
         data-widget-editbutton="false">
        <header>
            <span class="widget-icon"> <i class="fa fa-table"></i> </span>
            <h2>列表</h2>
        </header>
        <div>
            <!-- widget edit box -->
            <div class="jarviswidget-editbox">
                <!-- This area used as dropdown edit box -->

            </div>
            <!-- end widget edit box -->
            <!-- widget content -->
            <div class="widget-body no-padding">
                <table cellpadding="0" cellspacing="0" border="0"
                       class="table table-bordered table-hover" id="serviceTable">
                    <thead>
                    <tr>
                        <th>
                            <div class="form-group">
                                <label class="checkbox-inline">
                                    <input type="checkbox" class="checkbox" id="checkAll"/>
                                    <span></span>
                                </label>
                            </div>
                        </th>
                        <th><i class="fa fa-fw fa-user text-muted hidden-md hidden-sm hidden-xs"></i> 服务名</th>
                        <th><i class="fa fa-fw fa-phone text-muted hidden-md hidden-sm hidden-xs"></i> 简介</th>
                        <th><i class="fa fa-fw fa-lock text-muted hidden-md hidden-sm hidden-xs"></i> 价格</th>
                        <th><i class="fa fa-fw fa-calendar txt-color-blue hidden-md hidden-sm hidden-xs"></i> 时长</th>
                        <th><i class="fa fa-fw fa-sort-numeric-asc text-muted hidden-md hidden-sm hidden-xs"></i> 排序</th>
                        <th><i class="fa fa-fw fa-sun-o text-muted hidden-md hidden-sm hidden-xs"></i> 是否有效</th>
                        <th><i class="fa fa-fw fa-map-marker txt-color-blue hidden-md hidden-sm hidden-xs"></i>操作</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</section>

<script type="text/javascript">
    $(document).ready(function () {
        pageSetUp();
        $('#serviceTable').dataTable({
             "sDom": "<'dt-toolbar'<'col-xs-12 col-sm-6 toolbar'f><'col-sm-6 col-xs-12 hidden-xs'l>r>"+
                     "t"+
                     "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-xs-12 col-sm-6'p>>",
             "oLanguage": {
                 "sSearch": '<span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>'
             },
             "order": [[ 5, "asc" ]],
             "lengthMenu": [ 25, 50, 75, 100 ],
             "ajax": "/serviceInfo/all_data",
             "columns": [
                {"data": "serviceInfo.serviceId", "orderable": false,"width": "3%"},
                {"data": "serviceInfo.serviceName"},
                {"data": "serviceInfo.serviceSummary"},
                {"data": "srvProviderSrvRel.priceString"},
                {"data": "srvProviderSrvRel.estimatedTime"},
                {"data": "serviceInfo.sortOrder"},
                {"data": "serviceInfo.status"},
                {"data": "serviceInfo.serviceId"}
            ],
            columnDefs: [
            {
              targets:0,
              "render":function (data, type, row, meta) {
                  return '<div class="form-group">\n'
                         + '     <label class="checkbox-inline">\n'
                         + '         <input type="checkbox" class="checkbox" vv="'+row.serviceInfo.serviceId+'"/>\n'
                         + '         <span></span>\n'
                         + '     </label>\n'
                         + ' </div>';
              }
            },
            {
                targets: 6,
                "render": function (data, type, row, meta) {
                   if(row.serviceInfo.status==1){
                       return "有效";
                   }else{
                       return "无效"
                   }
                }
            },
            {
                targets: 7,
                "render": function (data, type, row, meta) {
                    var value = '<a onclick="editAction(this)" url="' + '/serviceInfo/showEdit?id=' + row.serviceInfo.serviceId + '">修改</a>&nbsp;&nbsp;' +
                                '<a onclick="delService(this)" url="' + '/serviceInfo/del?ids=' + row.serviceInfo.serviceId + '">删除</a>&nbsp;&nbsp;';
                    if (row.serviceInfo.status == 1) {
                        return value + '<a onclick="statusAction(this)" url="' + '/serviceInfo/disable?ids=' + row.serviceInfo.serviceId + '">无效</a>';
                    } else {
                        return value + '<a onclick="statusAction(this)" url="' + '/serviceInfo/enable?ids=' + row.serviceInfo.serviceId + '">有效</a>';
                    }
                }
            }]
        });
        $("#checkAll").click(function () {
            if ($("#checkAll").is(":checked")) {
                $(":checkbox").not("#checkAll").prop("checked", "checked");
            } else {
                $(":checkbox").not("#checkAll").prop("checked", "");
            }
        });

        $(".dataTables_filter label").append("<button class='btn btn-primary' id='serviceAdd' style='margin-left: 10px'>添加</button>");

        $("#serviceAdd").click(function () {
            loadURL("/serviceInfo/showAdd",container);
        })
});
    function editAction(action) {
        loadURL($(action).attr("url"),container);
    }

    function statusAction(action) {
        gDialog.fConfirm('确认执行', '你确定执行这个操作么？',
             function (rs) {
                 if (rs) {

                     var ids = new Array();
                     $(":checkbox:checked").each(function () {
                         ids.push($(this).attr("vv"));
                     });

                    $.ajax({
                        type: 'post',
                        dataType: "json",
                        url: $(action).attr("url"),
                        data: {ids: ids.toString()},
                        cache: false,
                        success: function (content) {
                            HWTX.refreshTablePipeline($('#serviceTable'));
                            message_box.show(content.message, content.code);
                        },
                        error: function (content) {
                            message_box.show(content.message, content.code);
                        }
                    });
                 }
             });
    }

    function delService(action) {
        var url = $(action).attr("url");

        var ids = new Array();

        $(":checkbox:checked").each(function () {
            ids.push($(this).attr("vv"));
        });

        gDialog.fConfirm('确认执行', '你确定执行这个操作么？',
             function (rs) {
                 if (rs) {
                     $.ajax({
                        type: 'post',
                        dataType: "json",
                        url: url,
                        data: {ids: ids.toString()},
                        cache: false,
                        success: function (content) {
                            HWTX.refreshTablePipeline($('#serviceTable'));
                            message_box.show(content.message, content.code);
                        },
                        error: function (content) {
                            message_box.show(content.message, content.code);
                        }
                     });
                 }
             });
    }
</script>