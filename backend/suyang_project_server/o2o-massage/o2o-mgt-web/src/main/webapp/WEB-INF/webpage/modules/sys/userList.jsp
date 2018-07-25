<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/webpage/include/taglib.jsp" %>
<%@ include file="/WEB-INF/webpage/include/datetimepicker.jsp" %>
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
<script type="text/javascript">
    $(document).ready(function () {
        pageSetUp();
    });
</script>
<script src="/static/js/ulist.js"></script>
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
                       class="table table-bordered table-hover" id="userTable">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th><i class="fa fa-fw fa-user text-muted hidden-md hidden-sm hidden-xs"></i>用户名</th>
                        <th><i class="fa fa-fw fa-phone text-muted hidden-md hidden-sm hidden-xs"></i>手机</th>
                        <th><i class="fa fa-fw fa-lock text-muted hidden-md hidden-sm hidden-xs"></i>状态</th>
                        <th><i class="fa fa-fw fa-calendar txt-color-blue hidden-md hidden-sm hidden-xs"></i> 登陆时间</th>
                        <th><i class="fa fa-fw fa-map-marker txt-color-blue hidden-md hidden-sm hidden-xs"></i>操作</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</section>