<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/webpage/include/jstree.jsp"%>
<style>
    .smart-form footer {
        background: none;
    }
</style>
<script type="text/javascript">
    $(document).ready(function () {

        $('#container').jstree({
            "core": {
                "animation": 2,
                "check_callback": true,
                "multiple": false,
                "themes": {
                    "variant": "large",
                    "dots": true
                },
                'data': {
                    'url': '/sys/role/tree',
                    'data': function (node) {
                        return {
                            'id': node.id
                        };
                    }
                }
            },
            "plugins": [ "contextmenu", "dnd", "search", "sort", "unique" ]
        }).on("create_node.jstree", function (e, data) {
            $("#newCreateId").val(data.node.id);
            $('#container').jstree(true).deselect_node(data.node.parent);
            $('#container').jstree(true).select_node(data.node.id);
        }).on("move_node.jstree", function (e, data) {
            $.ajax({
                type: 'post',
                dataType: "json",
                url: '/sys/role/move',
                data: {
                    '_old_parent': data.old_parent,
                    '_new_parent': data.node.parent,
                    '_moved_id': data.node.id
                },
                cache: false,
                success: function (content) {
                    message_box.show(content.message, content.code);
                },
                error: function (content) {
                    message_box.show(content.message, content.code);
                }
            });
        }).on("delete_node.jstree", function (e, data) {
            $.ajax({
                type: 'post',
                dataType: "text",
                url: '/sys/role/delete?id=' + data.node.id,
                cache: false,
                success: function (content) {
                    message_box.show(content, 'success');
                },
                error: function (content) {
                    message_box.show(content, 'error');
                }
            });
        }).on("select_node.jstree", function (e, data) {

            var createId = $("#newCreateId").val();
            if (createId != "" && data.node.id != createId) {
                alert("请先保存新建角色!");
                jstree.select_node(createId);
                jstree.deselect_node(data.node.id);
                return;
            }

            if (data.node.id != '-1') {
                var text = data.node.text;
                var start = text.indexOf("<bb>");
                if (start != -1) {
                    var end = text.indexOf("</bb>");
                    text = text.substring(start + 4, end);
                }

                var roleId = data.node.id;
                if (createId != "") {
                    roleId = "";
                }

                $.ajax({
                    type: "post",
                    dataType: "html",
                    url:  '/sys/role/detail',
                    data: {
                        roleId: roleId,
                        pid: data.node.parent,
                        text: text
                    }
                }).done(function (msg) {
                    $("#roleContent").html(msg);
                });
            }
        });

        $("#ml2").click(function () {
            loadContent($('#s2'),"/sys/role/assign");
        });

        $("#ml3").click(function () {
            loadContent($('#s3'),"/sys/res/roleAssignRes");
        });
    });

    var jstree = $('#container').jstree(true);
</script>
<div data-widget-sortable="false" data-widget-custombutton="false"
     data-widget-fullscreenbutton="false" data-widget-deletebutton="false"
     data-widget-togglebutton="false" data-widget-editbutton="false"
     data-widget-colorbutton="false" id="wid-id-3" class="jarviswidget well">
    <div>
        <div class="jarviswidget-editbox"></div>
        <div class="widget-body">
            <hr class="simple">
            <ul class="nav nav-tabs bordered" id="roleTab">
                <li class="active" id="ml1"><a data-toggle="tab" href="#s1"><i
                        class="fa fa-fw fa-lg fa-list-alt"></i>角色列表</a></li>
                <li id="ml2"><a data-toggle="tab" href="#s2"><i
                        class="fa fa-fw fa-lg fa-gear"></i>分配菜单</a></li>
                <li id="ml3"><a data-toggle="tab" href="#s3"><i
                        class="fa fa-fw fa-lg fa-align-left"></i>分配资源</a></li>
            </ul>
            <div class="tab-content padding-10" id="myTabContent1">
                <input type="hidden" id="newCreateId"/>

                <div id="s1" class="tab-pane fade in active">
                    <div class="row">
                        <div class="col-xs-12 col-sm-5 col-md-5 col-lg-5">
                            <div id="container"></div>
                        </div>
                        <div class="col-xs-12 col-sm-7 col-md-7 col-lg-7" id="roleContent"></div>
                    </div>
                </div>
                <div id="s2" class="tab-pane fade"></div>
                <div id="s3" class="tab-pane fade"></div>
            </div>
        </div>
    </div>
</div>