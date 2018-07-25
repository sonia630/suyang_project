<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="/static/js/plugin/treeTable/themes/vsStyle/treeTable.min.css" rel="stylesheet"
      type="text/css"/>
<script src="/static/js/plugin/treeTable/jquery.treeTable.js" type="text/javascript"></script>
<%@ include file="/WEB-INF/webpage/include/jshow.jsp" %>
<style type="text/css">
    .table td i {
        margin: 0 2px;
    }

    a {
        cursor: pointer;
    }
</style>
<script type="text/javascript">
    $(document).ready(function () {
        $("#listForm a[id^='d']").click(function () {
            var url = $(this).attr("url");
            gDialog.fConfirm('确认执行', '你确定执行这个操作么？',
                 function (rs) {
                     if (rs) {
                         $.ajax({
                            type: 'post',
                            dataType: "html",
                            url: url,
                            cache: false,
                            success: function (content) {
                                $("#content").html(content);
                                message_box.show('删除成功！', 'success');
                            },
                            error: function (content) {
                                $("#content").html(content);
                                message_box.show('删除失败！', 'error');
                            }
                        });
                     }
                 }
            );
        });

        $("#treeTable").treeTable({expandLevel: 4});
        $("#listForm a[id^='m']").click(function () {
            var url = $(this).attr("url");
            $.ajax({
               type: "get",
               async: true,
               url: url
           }).done(function (msg) {
                $('#s2').html(msg);
                $('#ml1').removeClass("active");
                $('#ml2').addClass("active");
                $('#s1').removeClass("in active");
                $('#s2').addClass("in active");
            });
        });

        $("#ml2").click(function () {
            $.ajax({
               type: "get",
               async: false,
               url: "/sys/menu/form"
            }).done(function (msg) {
                $('#s2').html(msg);
            });
        });
    });

    function callback() {
        message_box.show('保存成功！', 'success');
    }
</script>
<div
        data-widget-sortable="false" data-widget-custombutton="false"
        data-widget-fullscreenbutton="false" data-widget-deletebutton="false"
        data-widget-togglebutton="false" data-widget-editbutton="false"
        data-widget-colorbutton="false" id="wid-id-3" class="jarviswidget well">
    <div>
        <div class="jarviswidget-editbox"></div>
        <div class="widget-body">
            <hr class="simple">
            <ul class="nav nav-tabs bordered" id="mtab">
                <li class="active" id="ml1">
                    <a data-toggle="tab" href="#s1"><i
                            class="fa fa-fw fa-lg fa-list-alt"></i>菜单列表</a>
                </li>
                <li id="ml2"><a data-toggle="tab" href="#s2"><i
                        class="fa fa-fw fa-lg fa-gear"></i>菜单操作</a>
                </li>
            </ul>

            <div class="tab-content padding-10">
                <div id="s1" class="tab-pane fade in active">
                    <form id="listForm" method="post"
                          onsubmit="return ajaxSubmit(this,'html',callback)"
                          action="/sys/menu/updateSort">

                        <table id="treeTable"
                               class="table table-striped table-bordered table-condensed">
                            <tr>
                                <th>名称</th>
                                <th>链接</th>
                                <th style="text-align: center;">排序</th>
                                <th>可见</th>
                                <th>操作</th>
                            </tr>
                            <c:forEach items="${list}" var="permission" varStatus="varStatus">
                                <tr id="${permission.id}"
                                    pId="${not empty permission.parentId ? permission.parentId:'0'}">
                                    <td>
                                        <i class="fa fa-fw ${not empty permission.menuIcon?permission.menuIcon:' hide'}"></i>
                                        <a url="/sys/menu/form?id=${permission.id}" id="mtt">${permission.name}</a>
                                    </td>
                                    <td>${permission.url}</td>
                                    <td style="text-align: center;">
                                        <input type="hidden" name="ids" value="${permission.id}"/>
                                        <input name="sequence" type="text" value="${permission.sort}"
                                               style="width: 50px; margin: 0; padding: 0; text-align: center;"
                                               required>
                                    </td>
                                    <td>${permission.show eq '1'?'显示':'隐藏'}</td>
                                    <td>
                                        <a url="/sys/menu/form?id=${permission.id}" id="m${varStatus.index}">修改</a>
                                        <a url="/sys/menu/delete?id=${permission.id}"  id="d${varStatus.index}">删除</a>
                                        <a url="/sys/menu/form?pId=${permission.id}" id="mac${varStatus.index}">添加下级菜单</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                        <div class="pagination-left">
                            <input id="btnSubmit" class="btn btn-primary" type="submit"
                                   value="保存排序"/>
                        </div>
                    </form>
                </div>
                <div id="s2" class="tab-pane fade"></div>
            </div>
        </div>
    </div>
</div>