<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<script src="/static/js/plugin/summernote/summernote.js"></script>
<script src="/static/js/plugin/summernote/summernote-zh-CN.js"></script>
<script src="/static/js/plugin/x-editable/x-editable.min.js"/>
<script>
    $(document).ready(function () {
        $('#sort').editable({
            validate: function (value) {
                if ($.trim(value) == '')
                    return '不能为空';
            },
            pk: 1
        });
        $('#price').editable({
             validate: function (value) {
                 if ($.trim(value) == '')
                     return '不能为空';
                 if(isNaN(value)){
                     return '必须为数字';
                 }
                 var numbers = /^-?[0-9]+\.[0-9]{2,2}$/;
                 if(!numbers.test(value)){
                     return '需要保存两位小数';
                 }
             },
             pk: 2
        });
        $('#estime').editable({
              validate: function (value) {
                  if ($.trim(value) == '')
                      return '不能为空';
              },
              pk: 3
        });


        $("#qx").click(function () {
            $.ajax({
               type: 'POST',
               url: "  /serviceInfo/deRelation",
               data: {"rId": "${service.srvProviderSrvRel.id}"},
               dataType: "json",
               cache: false,
               success: saveCallback,
               error: ajaxError
           });
        });

        $("#gl,#update").click(function () {
            var data={
                "sortOrder":$("#service tr:eq(1) td:eq(1) a").html(),
                "price":$("#service tr:eq(2) td:eq(1) a").html(),
                "estimatedTime":$("#service tr:eq(3) td:eq(1) a").html(),
                "providerUserId":"${userId}",
                "serviceId":"${serviceId}",
                "id":"${service.srvProviderSrvRel.id}"
            };
            $.ajax({
               type: 'POST',
               url: "/serviceInfo/relation_user",
               data: data,
               dataType: "json",
               cache: false,
               success: saveCallback,
               error: ajaxError
           });
        });
    });
    function saveCallback(json) {
        if (json.code == 'success') {
            HWTX.gDialogClose();
            HWTX.refreshTablePipeline($("#serviceTable"));
            message_box.show(json.message, 'success');
        } else {
            message_box.show(json.message, 'error');
        }
        return true;
    }
</script>
<div class="modal-body" style="margin-left: 20px">
    <div id="wid-id-8" data-widget-editbutton="false"
         data-widget-custombutton="false">
        <div>
            <div class="widget-body">
                <table id="service" class="table table-bordered table-striped" style="clear: both">
                    <tbody>
                        <tr>
                            <td style="width:35%;">服务名称</td>
                            <td style="width:65%">${service.serviceInfo.serviceName}</td>
                        </tr>
                        <tr>
                            <td style="width:35%;">顺序</td>
                            <td><a id="sort" data-type="text" data-pk="1" data-placeholder="Required" data-original-title="服务排序">${service.serviceInfo.sortOrder}</a></td>
                        </tr>
                        <tr>
                            <td style="width:35%;">价格</td>
                            <td><a id="price" data-type="text" data-placeholder="Required" data-original-title="服务价格">${service.srvProviderSrvRel.priceString}</a></td>
                        </tr>
                        <tr>
                            <td style="width:35%;">时长(分钟)</td>
                            <td><a id="estime" data-type="text" data-placeholder="Required" data-original-title="服务时长">${service.srvProviderSrvRel.estimatedTime}</a></td>
                        </tr>
                        <tr>
                            <td style="width:35%;">简介</td>
                            <td>${service.serviceInfo.serviceSummary}</td>
                        </tr>
                        <tr>
                            <td style="width:35%;">描述</td>
                            <td>${service.serviceInfo.description}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<div class="modal-footer">
    <a class="btn" data-dismiss="modal" id="cl">关闭</a>
    <c:choose>
        <c:when test="${relation}">
            <a class="btn btn-primary" id="update">更新</a>
            <a class="btn btn-primary" id="qx">取消关联</a>
        </c:when>
        <c:otherwise>
            <a class="btn btn-primary" id="gl">关联</a>
        </c:otherwise>
    </c:choose>
</div>
