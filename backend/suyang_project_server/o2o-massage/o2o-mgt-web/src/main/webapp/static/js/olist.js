$(document).ready(
    function () {
        $('#orgcontainer').jstree(
            {
                "core": {
                    "animation": 2,
                    "check_callback": true,
                    "themes": {
                        "variant": "large",
                        "dots": true
                    },
                    'data': {
                        'url': '/sys/org/treeData',
                        'data': function (node) {
                            return {
                                'id': node.id
                            };
                        }
                    }
                },
                "contextmenu": {
                    'items': contextMenu
                },
                "plugins": [ "contextmenu", "search", "sort", "dnd",
                    "unique" ]
            }).on(
            "create_node.jstree",
            function (e, data) {
                $("#save_type").val("INSERT");
                $("#newCreateId").val(data.node.id);
                $('#orgcontainer').jstree(true).deselect_node(
                    data.node.parent);
                $('#orgcontainer').jstree(true).select_node(
                    data.node.id);
            }).on("move_node.jstree", function (e, data) {
                $.ajax({
                    type: 'post',
                    dataType: "json",
                    url: '/sys/org/move',
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

            }).on("select_node.jstree", function (e, data) {

                if (data.node.id == 'top') {
                    if (!$("#orgd").hasClass("disabled")) {
                        $("#orgd").addClass("disabled");
                    }
                } else {
                    $("#orgd").removeClass("disabled");
                }

                var createId = $("#newCreateId").val();
                if (createId != "" && data.node.id != createId) {
                    alert("请先保存新建机构!");
                    jstree.select_node(createId);
                    jstree.deselect_node(data.node.id);
                    return;
                }

                $("#sysOrg_orgId").val("");
                $("#sysOrg_pid").val("");
                $("#sysOrg_name").val();
                $("#sysOrg_description").val("");
                $("#sysOrg_orgId").val(data.node.id);
                $("#sysOrg_pid").val(data.node.parent);
                var text = data.node.text;
                var start = text.indexOf("<bb>");
                if (start != -1) {
                    var end = text.indexOf("</bb>");
                    text = text.substring(start + 4, end);
                }
                $("#sysOrg_name").val(text);
                var save_type = $("#save_type").val();
                if (data.node.id != 'top') {

                    if (save_type == "" || save_type != "INSERT") {
                        $.getJSON('/sys/org/detail?orgId='
                            + data.node.id, function (result) {
                            if (result != null) {
                                $("#sysOrg_description").val(result.des);
                            }
                        });
                        $("#save_type").val("UPDATE");
                    }
                }
                var ptext = $('#orgcontainer').jstree(true).get_text(data.node.parent);
                if (ptext == false) {
                    $("#btnSubmit").hide();
                } else {
                    var start = ptext.indexOf("<bb>");
                    if (start != -1) {
                        var end = ptext.indexOf("</bb>");
                        ptext = ptext.substring(start + 4, end);
                    }
                    $("#btnSubmit").show();
                    $("#sysOrg_pname").val(ptext);
                }
            });

        var jstree = $('#orgcontainer').jstree(true);

        $("#orga").click(function () {

            var selected = jstree.get_selected(true);
            if (selected.length == 0 || selected.length > 1) {
                alert("请选择上级机构");
                return;
            }
            var newId = jstree.create_node(selected[0], {'text': '新机构'}, "last");
            $("#newCreateId").val(newId);
        });

        $("#checkUser").click(function () {

            if ($("#newCreateId").val()) {
                alert("请保存新建机构");
                return;
            }

            var selected = jstree.get_selected(true);
            if (selected.length == 0 || selected.length > 1) {
                alert("请选择一个机构");
                return;
            }
            HWTX.gDialogCreate("用户列表", "/sys/user/lookup?includeUser=1", {"orgId": selected[0].id});
        });

        $("#orgd").click(function () {

            var selected = jstree.get_selected(true);

            if ($("#save_type").val() == "INSERT") {
                jstree.delete_node(selected);
                $("#save_type").val("");
                $("#newCreateId").val("");
                return;
            }

            var ids = "";
            for (i in selected) {
                ids += selected[i].id;
                ids += ",";
            }
            $.ajax({
                type: 'post',
                dataType: "json",
                url: '/sys/org/delete?ids=' + ids,
                cache: false,
                success: function (content) {
                    if (content.code == "success") {
                        message_box.show(content.message, content.code);
                        jstree.delete_node(selected);
                    } else {
                        var ids = content.message.split(",");
                        var mm = "";
                        for (i in ids) {
                            mm += jstree.get_node(ids[i]).text;
                        }
                        message_box.show("无法删除" + mm, content.code);
                    }
                },
                error: function (content) {
                    message_box.show(content.message, content.code);
                }
            });
        });

        $("#usera").click(function () {
            if ($("#newCreateId").val()) {
                alert("请保存新建机构");
                return;
            }
            var selected = jstree.get_selected(true);
            if (selected.length == 0 || selected.length > 1) {
                alert("请选择一个机构");
                return;
            }
            HWTX.gDialogCreate("用户列表", "/sys/user/lookup", {"orgId": selected[0].id});
        });

        $("#rolea").click(function () {

            if ($("#newCreateId").val()) {
                alert("请保存新建机构");
                return;
            }

            var selected = jstree.get_selected(true);
            if (selected.length == 0 || selected.length > 1) {
                alert("请选择一个机构");
                return;
            }
            HWTX.gDialogCreate('分配角色', '/sys/org/showAssignRole', {"orgId": selected[0].id}, 550, 350);
        });
    });

function saveCallback(json) {
    if (json.code == 'success') {
        message_box.show(json.message, 'success');
        $('#orgcontainer').jstree(true).set_text(json.id, json.name);
        if (json.type == '1') {
            $('#orgcontainer').jstree(true).hide_icon(json.id);
        } else if (json.type == '0') {
            $('#orgcontainer').jstree(true).show_icon(json.id);
        }
    } else {
        message_box.show(json.message, 'error');
    }
    $("#save_type").val("");
    $("#newCreateId").val("");
    return true;
}

function contextMenu() {
    return {
        "create": {
            "separator_before": false,
            "separator_after": true,
            "_disabled": false, // (this.check("create_node",
            // data.reference, {}, "last")),
            "label": "Create",
            "action": function (data) {
                var inst = $.jstree.reference(data.reference), obj = inst
                    .get_node(data.reference);
                inst.create_node(obj, {}, "last", function (new_node) {
                    setTimeout(function () {
                        inst.edit(new_node);
                    }, 0);
                });
            }
        },
        "ccp": {
            "separator_before": true,
            "icon": false,
            "separator_after": false,
            "label": "Edit",
            "action": false,
            "submenu": {
                "cut": {
                    "separator_before": false,
                    "separator_after": false,
                    "label": "Cut",
                    "action": function (data) {
                        var inst = $.jstree.reference(data.reference), obj = inst
                            .get_node(data.reference);
                        if (inst.is_selected(obj)) {
                            inst.cut(inst.get_selected());
                        } else {
                            inst.cut(obj);
                        }
                    }
                },
                "copy": {
                    "separator_before": false,
                    "icon": false,
                    "separator_after": false,
                    "label": "Copy",
                    "action": function (data) {
                        var inst = $.jstree.reference(data.reference), obj = inst
                            .get_node(data.reference);
                        if (inst.is_selected(obj)) {
                            inst.copy(inst.get_selected());
                        } else {
                            inst.copy(obj);
                        }
                    }
                },
                "paste": {
                    "separator_before": false,
                    "icon": false,
                    "_disabled": function (data) {
                        return !$.jstree.reference(data.reference).can_paste();
                    },
                    "separator_after": false,
                    "label": "Paste",
                    "action": function (data) {
                        var inst = $.jstree.reference(data.reference), obj = inst
                            .get_node(data.reference);
                        inst.paste(obj);
                    }
                }
            }
        }
    };
}