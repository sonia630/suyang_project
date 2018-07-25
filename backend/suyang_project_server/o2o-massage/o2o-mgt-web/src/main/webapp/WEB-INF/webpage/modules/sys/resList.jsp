<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/webpage/include/jstree.jsp"%>
<style>
    .smart-form footer {
        background: none;
    }

    .orgboard {
        border: 1px solid #BBBBBB;
        border-radius: 4px;
        overflow: auto;
        padding: 10px 15px;
        position: relative;
    }

    .u-tree {
        border-right: 1px solid #DDDDDD !important;
        float: left;
        height: 350px;
        overflow-y: auto;
        position: relative;
    }

    .u-text {
        border-right: 1px solid #DDDDDD !important;
        float: left;
        height: 326px;
        overflow-y: auto;
        position: relative;
        margin-left: 20px;
    }
</style>
<script type="text/javascript">
    $('#rescontainer').jstree({
        "core": {
            "animation": 2,
            "check_callback": true,
            "multiple": false,
            "themes": {
                "variant": "large",
                "dots": true
            },
            'data': {
                'url': '/sys/res/tree',
                'data': function (node) {
                    return {
                        'id': node.id
                    };
                }
            }
        },
        "plugins": [ "dnd", "search", "sort", "unique" ]
    }).on("move_node.jstree", function (e, data) {
        $.ajax({
            type: 'post',
            dataType: "json",
            url: '/sys/res/move',
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
    }).on("select_node.jstree", function (e, data) {

    });
    var jstree = $('#rescontainer').jstree(true);

    $("#resa").click(function () {
        var selected = jstree.get_selected(true);
        if (selected.length == 0 || selected.length > 1) {
            alert("请选择资源分类");
            return;
        }
        HWTX.gDialogCreate("添加资源", "${ctx}/sys/res/showAdd", {"pId": selected[0].id, "pText": selected[0].text});
    });

    $("#resd").click(function () {
        var selected = jstree.get_selected(true);
        if (selected.length == 0 || selected.length > 1) {
            alert("请选择资源分类");
            return;
        }
        gDialog.fConfirm('确认执行', '你确定执行这个操作么？',
            function (rs) {
                if (rs) {
                    $.ajax({
                        type: 'post',
                        dataType: "json",
                        url: '/sys/res/delete',
                        data: {
                            'id': selected[0].id
                        },
                        cache: false,
                        success: function (content) {
                            message_box.show(content.message, content.code);
                            $('#rescontainer').jstree(true).refresh("#");
                        },
                        error: function (content) {
                            message_box.show("删除失败", "error");
                        }
                    });
                }
            });
    });

    $('#rescontainer').on("dblclick.jstree", ".jstree-anchor", $.proxy(function (e) {
        e.preventDefault();
        $(e.currentTarget).focus();
        obj = jstree.get_node(e.currentTarget);
        if (obj.id == "-1") {
            alert("不能编辑根节点.");
            return;
        }
        parent = jstree.get_node(jstree.get_parent(obj));
        HWTX.gDialogCreate("编辑资源", "/sys/res/showEdit", {"id": obj.id, "pText": parent.text});
    }, this));

</script>
<div data-widget-sortable="false" data-widget-custombutton="false"
     data-widget-fullscreenbutton="false" data-widget-deletebutton="false"
     data-widget-togglebutton="false" data-widget-editbutton="false"
     data-widget-colorbutton="false" id="wid-id-o3" class="jarviswidget well">
    <div>
        <div class="jarviswidget-editbox"></div>
        <div class="widget-body">
            <a href="javascript:void(0);" class="btn btn-primary" id="resa"><i class="fa fa-plus"></i> 添加</a>
            <a href="javascript:void(0);" class="btn btn-primary" id="resd"><i class="fa fa-minus"></i> 删除</a>
            <hr class="simple">
            <div class="tab-content padding-10" id="myTabContent1">
                <div class="row">
                    <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                        <div id="rescontainer"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>