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
                        <th><i class="fa fa-fw fa-user text-muted hidden-md hidden-sm hidden-xs"></i> 服务名</th>
                        <th><i class="fa fa-fw fa-phone text-muted hidden-md hidden-sm hidden-xs"></i> 简介</th>
                        <th><i class="fa fa-fw fa-lock text-muted hidden-md hidden-sm hidden-xs"></i> 价格</th>
                        <th><i class="fa fa-fw fa-calendar txt-color-blue hidden-md hidden-sm hidden-xs"></i> 时长(分钟)</th>
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
             "ajax": "/serviceInfo/relationData?userId=${userId}",
             "columns": [
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
                targets: 5,
                "render": function (data, type, row, meta) {
                   if(row.serviceInfo.status==1){
                       return "有效";
                   }else{
                       return "无效"
                   }
                }
            },
            {
                targets: 6,
                "render": function (data, type, row, meta) {
                    var value = '<a onclick="detail(this)" url="/serviceInfo/detail?userId=${userId}&owner='+row.srvProviderSrvRel.providerUserId+'&serviceId='+ row.serviceInfo.serviceId + '">查看</a>&nbsp;&nbsp;';
                    if (row.serviceInfo.status == 1){
                        if(row.srvProviderSrvRel.providerUserId!="${userId}"){
                            value +='<a onclick="relation(this)" url="' + '/serviceInfo/relation_direct?userId=${userId}&rId=' + row.srvProviderSrvRel.id + '">关联</a>&nbsp;&nbsp;';
                        }else{
                            value +='<a onclick="relation(this)" url="' + '/serviceInfo/deRelation?rId=' + row.srvProviderSrvRel.id + '">取消关联</a>&nbsp;&nbsp;';
                        }
                    }
                    return value;
                }
            }]
        });
});
    function relation(action) {
        gDialog.fConfirm('确认执行', '你确定执行这个操作么？',
             function (rs) {
                 if (rs) {
                     $.ajax({
                        type: 'post',
                        dataType: "json",
                        url: $(action).attr("url"),
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
             }
        );
    }
    function detail(action) {
        HWTX.gDialogCreate('服务详情', $(action).attr("url"),{}, 600, 400);
    }
</script>