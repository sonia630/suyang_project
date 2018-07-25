<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/webpage/include/validator.jsp" %>
<style>
    .jarviswidget > div {
        border-width: 0;
    }

    .smart-form footer {
        background: none;
    }
</style>

<script>
    $(document).ready(function () {
        $("#inputForm").validate();
    });

    function back() {
        gDialog.fClose();
        <%--backTo('${ctx}/sys/res/');--%>
    }

    function saveCallback() {
        gDialog.fClose();
        $('#rescontainer').jstree(true).refresh("#");
        return true;
    }
</script>

<div class="jarviswidget" id="wid-id-8" data-widget-editbutton="false"
     data-widget-custombutton="false">
    <div>
        <div class="jarviswidget-editbox">
            <!-- This area used as dropdown edit box -->
        </div>
        <!-- end widget edit box -->

        <!-- widget content -->
        <div class="widget-body no-padding">
            <form action="/sys/res/save" method="post" id="inputForm"
                  class="smart-form" onsubmit="return ajaxSubmit(this,'',saveCallback)">
                <input type="hidden" name="id" value="${res.id}"/>
                <input type="hidden" name="parentId" value="${pId}"/>
                <fieldset>
                    <div class="row">
                        <section class="col col-6">
                            <label class="control-label">上级分类:</label>
                            <label class="input state-disabled">
                                <input value="${pText}" disabled="disabled"/>
                            </label>
                        </section>
                        <section class="col col-6">
                            <label class="control-label">名称:</label>
                            <label class="input">
                                <input type="text" name="name"  value="${res.name}" maxlength="50" required/>
                            </label>
                            <div class="help-block with-errors"></div>
                        </section>
                    </div>
                    <div class="row">
                        <section class="col col-6">
                            <label class="control-label">链接:</label>
                            <label class="input">
                                <input type="text" name="url" value="${res.url}" maxlength="200" required/>
                            </label>
                            <div class="help-block with-errors"></div>
                        </section>
                    </div>
                </fieldset>
                <footer>
                    <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
                    <input id="btngoback" class="btn btn-primary" type="button" value="返 回" onclick="back()"/>
                </footer>
            </form>
        </div>
    </div>
</div>