<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<style>
    .jarviswidget > div {
        border-width: 0;
    }

    a {
        cursor: pointer;
    }

    .smart-form footer {
        background: none;
    }
</style>

<script>
    $(document).ready(function () {
        validateForm($("#serviceCatForm"), submitCallback);
        $("#sb").click(function () {
            $("#serviceCatForm").submit();
        });
    });

    function submitCallback() {
        ajaxSubmit($("#serviceCatForm"), 'json', saveCallback);
    }

    function saveCallback(json) {
        if (json.code == 'success') {
            message_box.show(json.message, 'success');
            HWTX.gDialogClose();
            HWTX.refreshTablePipeline($("#catTable"));
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
                <form action="/serviceCategory/save" method="post" id="serviceCatForm"
                      class="smart-form">
                    <input type="hidden" name="catId" value="${cat.catId}"/>
                    <div class="row">
                        <section class="col col-10">
                            <label class="control-label">分类名:</label> <label class="input">
                            <input type="text" name="catName" value="${cat.catName}"
                                   maxlength="50" required/>
                        </label>
                            <div class="help-block with-errors"></div>
                        </section>
                    </div>
                    <div class="row">
                        <section class="col col-10">
                            <label class="control-label">排序:</label> <label class="input">
                            <input type="text" name="sort" class="number" maxlength="10"
                                   value="${cat.sort}" required/>
                        </label>
                            <div class="help-block with-errors"></div>
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