<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<style>
    .jarviswidget > div {
        border-width: 0;
    }
    a{
        cursor: pointer;
    }

    .smart-form footer {
        background: none;
    }
</style>

<script>
    $(document).ready(function () {
        validateForm($("#userInputForm"),submitCallback);
        $("#sb").click(function () {
            $("#userInputForm").submit();
        });
    });

    function submitCallback() {
        ajaxSubmit($("#userInputForm"), 'json', saveCallback);
    }

    function saveCallback(json) {
        if (json.code == 'success') {
            message_box.show(json.message, 'success');
            HWTX.gDialogClose();
            HWTX.refreshTablePipeline($("#userTable"));
        } else {
            message_box.show(json.message, 'error');
        }
        return true;
    }
</script>
<div class="modal-body" style="margin-left: 20px">
    <div class="jarviswidget" id="wid-id-8" data-widget-editbutton="false"
         data-widget-custombutton="false">
        <div>
            <div class="jarviswidget-editbox">
                <!-- This area used as dropdown edit box -->
            </div>
            <!-- end widget edit box -->

            <!-- widget content -->
            <div class="widget-body no-padding">
                <form action="/sys/user/save" method="post" id="userInputForm"
                      class="smart-form">
                    <input type="hidden" name="userId" value="${userInfo.id}"/>
                    <div class="row">
                        <section class="col col-10">
                            <label class="control-label">用户名:</label> <label class="input">
                            <input type="text" name="name" value="${userInfo.name}"
                                   maxlength="50" required/>
                        </label>
                            <div class="help-block with-errors"></div>
                        </section>
                    </div>
                    <div class="row">
                        <section class="col col-10">
                            <label class="control-label">密码:</label> <label class="input">
                            <input type="password" name="password" maxlength="50"/>
                        </label>
                            <c:if test="${!empty userInfo.id}">
                                <div class="note">若不修改密码，请留空</div>
                            </c:if>
                            <div class="help-block with-errors"></div>
                        </section>
                    </div>
                    <div class="row">
                        <section class="col col-10">
                            <label class="control-label">类型:</label>
                            <div class="inline-group">
                                <c:forEach items="${userInfo.userTypes}" var="item">
                                    <label class="checkbox">
                                        <input type="checkbox" name="checkedTypes"
                                               <c:if test="${item.right!=0}">checked="checked"</c:if> value="${item.middle}">
                                        <i></i>${item.left}
                                    </label>
                                </c:forEach>
                            </div>
                        </section>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="modal-footer">
    <a class="btn" data-dismiss="modal" id="cl">关闭</a>
    <a class="btn btn-primary" id="sb">保存</a>
</div>