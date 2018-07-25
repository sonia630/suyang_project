<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/webpage/include/jstree.jsp"%>
<%@ include file="/WEB-INF/webpage/include/jshow.jsp" %>
<script type="text/javascript">
    $(document).ready(function () {
        $('#rescontainer').jstree(
                {
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
                    "plugins": [ "sort"]
                })
                .on(
                "select_node.jstree",
                function (e, data) {
                    var icon = $('#rescontainer').jstree(true).get_icon(data.node.id);
                    if (!icon) {
                        $("#resRight").show();
                        $('#resMenus').jstree({
                            'core': {
                                'data': {
                                    'url': '/sys/role/resources',
                                    'data': function (node) {
                                        var selecredArrays = $('#rescontainer').jstree(true).get_selected();
                                        return { 'id': selecredArrays[0]};
                                    }
                                }
                            },
                            "plugins": [ "search", "sort", "checkbox" ]
                        });
                        if ($('#resMenus').jstree().is_loaded("#")) {
                            $('#resMenus').jstree().load_node("#");
                        }
                    } else {
                        $("#resRight").hide();
                    }
                });


        var to = false;
        $('#reskey').keyup(function () {
            if (to) {
                clearTimeout(to);
            }
            to = setTimeout(function () {
                var v = $('#reskey').val();
                $('#resMenus').jstree(true).search(v);
            }, 250);
        });


        $("#resplus").click(function () {
            var selecredArrays = $('#rescontainer').jstree(true).get_selected();
            gDialog.fCreate({
                title: '选择资源',
                url: '/sys/role/permission_tree?type=res&id=' + selecredArrays[0],
                width: 500,
                height: 400
            }).show();
        });

        $("#ressearch").click(function () {
            $("#ressearchText").slideToggle(200);
            $("#reskey").focus();
        });

        $("#resminus").click(function () {
            var selecredRoles = $('#rescontainer').jstree(true).get_selected();
            var selecredfuns = $('#resMenus').jstree(true).get_selected();
            if (selecredfuns.length == 0) {
                message_box.show("请选择删除项", 'info');
            } else {
                if (selecredfuns.length == 1 && selecredfuns[0] == '-1') {
                    alert("无法删除顶层元素");
                } else {
                    $.ajax({
                        type: 'post', dataType: "json", url: '/sys/role/deleteIt', cache: false,
                        data: {'selected': selecredfuns.toString(),
                            'roleId': selecredRoles[0],
                            'type': 'res'
                        },
                        success: function (data) {
                            message_box.show(data.message, 'info');
                            $('#resMenus').jstree(true).refresh("#");
                        },
                        error: function (data) {
                            message_box.show(data.message, 'error');
                        }
                    });
                }
            }
        });
    });
</script>
<div class="row">
    <div class="col-xs-12 col-sm-7 col-md-7 col-lg-5">
        <div id="rescontainer"></div>
    </div>
    <div class="col-xs-12 col-sm-7 col-md-7 col-lg-7" id="resRight" style="display: none;">
        <div id="ressearchText" style="padding:0px 0 7px 0px;display: none;">
            <label for="reskey" style="float:left;padding:5px 5px 3px;">关键字：</label>
            <input type="text" class="empty" id="reskey" name="reskey" maxlength="50">
        </div>
        <div class="row" style="margin-top: 2px ">
            <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6" id="resMenus"></div>
            <div class="col-xs-2 col-sm-2 col-md-2 col-lg-2 fa fa-fw fa-plus" title="添加"
                 style="margin-top: 5px;cursor:pointer;float: left;" id="resplus"></div>
            <div class="col-xs-2 col-sm-2 col-md-2 col-lg-2 fa fa-fw fa-minus" title="删除"
                 style="margin-top: 5px;cursor:pointer;float: left;" id="resminus"></div>
            <div class="col-xs-2 col-sm-2 col-md-2 col-lg-2 fa fa-fw fa-search" title="检索"
                 style="margin-top: 5px;cursor:pointer;float: left;" id="ressearch"></div>
        </div>
    </div>
</div>