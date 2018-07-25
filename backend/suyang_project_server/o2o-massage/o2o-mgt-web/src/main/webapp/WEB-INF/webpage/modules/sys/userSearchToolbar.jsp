<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/webpage/include/jshow.jsp" %>
<div style="margin-bottom: 10px">
    <form action="/sys/user/" method="post" id="userForm"
          onsubmit="return false" class="form-inline" role="form">
        <fieldset style="top: 3px">
            <div class="btn-group">
                <button class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                    操作<span class="caret"></span>
                </button>
                <ul class="dropdown-menu" style="min-width: 59px">
                    <li><a id="usera"><i class="fa fa-plus"></i> 添加</a></li>
                    <li><a id="userm"><i class="fa fa-maxcdn"></i> 修改</a></li>
                    <li><a id="delBat"><i class="fa fa-minus"></i> 删除</a></li>
                    <li class="divider"></li>
                    <li><a id="userAssignRole"><i class="fa fa-magnet"></i> 分配角色</a></li>
                </ul>
            </div>
            <div class="form-group">
                <div class="input-icon-right">
                    <i class="fa fa-user"></i> <input type="text" placeholder="用户名"
                                                      id="userName" class="form-control"
                                                      name="name">
                </div>
            </div>
            <div class="form-group">
                <div class="input-icon-right">
                    <i class="fa fa-times" style="cursor: pointer;" id="clearstart">
                        <i class="fa fa-calendar"></i>
                    </i> <input type="text" data-format="yyyy-MM-dd HH:mm:ss"
                                name="startDate" id="startdate"
                                placeholder="开始时间" class="form-control">
                </div>
            </div>
            <div class="form-group">
                <div class="input-icon-right">
                    <i class="fa fa-times" style="cursor: pointer;" id="clearend">
                        <i class="fa fa-calendar"></i>
                    </i> <input type="text" data-format="yyyy-MM-dd" name="finishDate"
                                id="finishdate" placeholder="结束时间"
                                class="form-control">
                </div>
            </div>
            <div class="form-group">
                <div>
                <select class="form-control" id="userType">
                    <option value="0">全部用户</option>
                    <option value="1">系统用户</option>
                    <option value="2">顾问</option>
                    <option value="4">客户</option>
                </select>
                </div>
            </div>
            <button class="btn btn-primary" id="userSubmit">查询</button>
        </fieldset>
    </form>
</div>
<script type="application/javascript">


    function getPostServerData() {
        return {
            "name": $("#userName").val(),
            "startDate": $("#startdate").val(),
            "finishDate": $("#finishdate").val(),
            "userType": $("#userType").val()
        };
    }

    $("#userSubmit").click(function () {
        conf.data = getPostServerData();
        $(this).attr("isclick",1);
        HWTX.refreshTablePipeline($('#userTable'));
    });

    $('#startdate').datetimepicker({
       format: 'yyyy-mm-dd hh:ii:ss',
       autoclose: true,
       todayBtn: true,
       language: "zh-CN"
    });

    $('#finishdate').datetimepicker({
        format: 'yyyy-mm-dd hh:ii:ss',
        autoclose: true,
        todayBtn: true,
        language: "zh-CN"
    });

    $("#clearstart").click(function () {
        $("#startdate").val("");
    });
    $("#clearend").click(function () {
        $("#finishdate").val("");
    });

    $("#startdate").on("", function (e) {
        $('#finishdate').data("DateTimePicker").setMinDate(e.date);
    });

    $('#startdate').datetimepicker().on("changeDate", function (e) {
        $('#finishdate').datetimepicker("setStartDate", e.date);
    });

    $("#finishdate").datetimepicker().on("changeDate", function (e) {
        $('#startdate').datetimepicker("setEndDate", e.date);
    });

    $("#delBat").click(function () {
        var ids = new Array();
        var trs = new Array();

        var length = $("#userTable tbody tr.row_selected").length;
        if (length == 0) {
            alert("请选择一个用户");
            return;
        }

        $("#userTable tbody tr.row_selected").each(function () {
            ids.push($(this).children('td').eq(1).html());
            trs.push($(this));
        });
        del("/sys/user/d/" + ids.toString(), trs);
    });

    $("#usera").click(function () {
        HWTX.gDialogCreate('添加用户', '/sys/user/showAdd', {}, 400, 280);
    });

    $("#userm").click(function () {
        var id = getSelectedTr();
        if (id != "") {
            HWTX.gDialogCreate('修改用户', '/sys/user/showEdit?id=' + id, {}, 500, 204);
        }
    });

    $("#userAssignRole").click(function () {
        var id = getSelectedTr();
        if (id != "") {
            HWTX.gDialogCreate('分配角色', '/sys/user/showAssignRole?id=' + id, {}, 550, 350);
        }
    });
</script>
