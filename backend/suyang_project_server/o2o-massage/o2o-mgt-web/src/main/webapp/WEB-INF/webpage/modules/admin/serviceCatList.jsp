<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/webpage/include/taglib.jsp" %>
<%@ include file="/WEB-INF/webpage/include/jshow.jsp" %>
<style>
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
                       class="table table-bordered table-hover" id="catTable">
                    <thead>
                    <tr>
                        <th><i class="fa fa-fw fa-user text-muted hidden-md hidden-sm hidden-xs"></i> 序号</th>
                        <th><i class="fa fa-fw fa-phone text-muted hidden-md hidden-sm hidden-xs"></i> 名称</th>
                        <th><i class="fa fa-fw fa-lock text-muted hidden-md hidden-sm hidden-xs"></i> 排序</th>
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
        $('#catTable').dataTable({
             "sDom": "<'dt-toolbar'<'col-xs-12 col-sm-6 toolbar'f><'col-sm-6 col-xs-12 hidden-xs'l>r>"+
                     "t"+
                     "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-xs-12 col-sm-6'p>>",
             "oLanguage": {
                 "sSearch": '<span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>'
             },
             "lengthMenu": [ 10, 15, 50, 100 ],
             "ajax": "/serviceCategory/all_data",
             "columns": [
                {"data": "catId"},
                {"data": "catName"},
                {"data": "sort"},
                {"data": "catId"}
             ],
             "rowCallback": function( row, data, index ) {
                 $("td:first", row).html(index + 1);
                 return row;
             },
             columnDefs: [
             {
                targets: 3,
                "render": function (data, type, row, meta) {
                    return '<a onclick="editAction(this)" url="' + '/serviceCategory/showEdit?catId=' + row.catId + '">修改</a>&nbsp;&nbsp;' +
                                '<a onclick="delService(this)" url="' + '/serviceCategory/delete?catId=' + row.catId + '">删除</a>&nbsp;&nbsp;';
                }
             }]
        });

        $(".dataTables_filter label").append("<button class='btn btn-primary' id='catAdd' style='margin-left: 10px'>添加</button>");

        $("#catAdd").click(function () {
            HWTX.gDialogCreate('添加服务分类', '/serviceCategory/showAdd', {}, 400, 280);
        })
});
    function editAction(action) {
        var url = $(action).attr("url");
        HWTX.gDialogCreate('修改服务分类', url, {}, 400, 280);
    }

    function delService(action) {
        var url = $(action).attr("url");

        gDialog.fConfirm('确认执行', '你确定执行这个操作么？',
             function (rs) {
                 if (rs) {
                     $.ajax({
                        type: 'post',
                        dataType: "json",
                        url: url,
                        cache: false,
                        success: function (content) {
                            HWTX.refreshTablePipeline($('#catTable'));
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