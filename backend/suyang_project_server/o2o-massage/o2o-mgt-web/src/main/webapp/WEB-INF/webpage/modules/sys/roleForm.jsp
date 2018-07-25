<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/webpage/include/taglib.jsp" %>
<script>
    $(document).ready(function () {
        $("#roleForm").validate({
            submitHandler: function () {
                roleSubmit($("#roleForm"));
            }
        });
    });
    var jstree = $('#container').jstree(false);

    function saveCallback(json) {
        if (json.code == 'success') {
            message_box.show(json.message, json.code);
            jstree.set_text(json.id, json.name);
            if (json.type == '1') {
                jstree.hide_icon(json.id);
            } else if (json.type == '0') {
                jstree.show_icon(json.id);
            }
        } else {
            message_box.show(json.message, json.code);
            jstree.delete_node($("#newCreateId").val());
        }
        $("#newCreateId").val("")
        return true;
    }

    function roleSubmit(form) {

        var pid = $("#sysRole_pid").val();
        var p = jstree.get_icon(pid);
        if (!p) {
            alert("保存失败,父节点类型必须是分类");
            jstree.delete_node($("#newCreateId").val());
            $("#newCreateId").val("");
            return false;
        }
        if ($("#newCreateId").val()) {
            $("#sysRole_roleId").val($("#newCreateId").val());
        }
        return ajaxSubmit(form, 'json', saveCallback);
    }
</script>

<form id="roleForm" method="post" action="/sys/role/save" class="smart-form"
      onsubmit="return false">
    <fieldset>
        <input type="hidden" name="id" id="sysRole_roleId" value="${sysRole.id}"/>
        <input type="hidden" name="parentId" id="sysRole_pid" value="${sysRole.parentId}"/>

        <div class="row">
            <section class="col col-6">
                <label class="control-label">归属:</label>
                <label class="input state-disabled">
                    <input type="text" id="sysRole_pname" value="${sysRole.pname}"/>
                </label>
            </section>
            <section class="col col-6">
                <label class="control-label">名称:</label>
                <label class="input">
                    <input type="text" name="name" maxlength="50" value="${sysRole.name}"
                           class="required" id="sysRole_name" required/>
                </label>

                <div class="help-block with-errors"></div>
            </section>
        </div>
        <div class="row">
            <section class="col col-6">
                <label class="control-label">描述:</label>
                <label class="input">
                    <input type="text" name="description" maxlength="50"
                           id="sysRole_description" value="${sysRole.description}"/>
                </label>

                <div class="help-block with-errors"></div>
            </section>
            <section class="col col-6">
                <label class="control-label">类型:</label>

                <div class="inline-group">
                    <label class="radio">
                        <input type="radio" name="type" value="0"
                               <c:if test="${sysRole.type==0}">checked="checked" </c:if>/><i></i>分类
                    </label>
                    <label class="radio">
                        <input type="radio" name="type" value="1"
                               <c:if test="${sysRole.type==1}">checked="checked" </c:if>/><i></i>角色
                    </label>
                </div>
            </section>
        </div>
        <div class="row">
            <section class="col col-9">
                <label class="control-label">角色标识:</label>
                <label class="input state-disabled">
                    <input type="text" value="${sysRole.id}" disabled/>
                </label>
            </section>
        </div>
    </fieldset>
    <footer>
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="保存"/>
    </footer>
</form>