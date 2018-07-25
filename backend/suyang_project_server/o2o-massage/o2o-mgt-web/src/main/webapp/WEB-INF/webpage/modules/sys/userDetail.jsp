<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        if(${user}){
            loadContent($("#s1"),"/sys/user/cDetail?id=${userId}","html");
        }
        $("#ml1").click(function () {
            loadContent($("#s1"),"/sys/user/cDetail?id=${userId}","html");
        })
        if(${provider}){
            loadContent($("#s2"),"/providerInfo/pDetail?id=${userId}","html");
        }
        $("#ml2").click(function () {
            loadContent($("#s2"),"/providerInfo/pDetail?id=${userId}","html");
        })
    });

</script>
<div
        data-widget-sortable="false" data-widget-custombutton="false"
        data-widget-fullscreenbutton="false" data-widget-deletebutton="false"
        data-widget-togglebutton="false" data-widget-editbutton="false"
        data-widget-colorbutton="false" id="wid-id-3" class="jarviswidget well">
    <div>
        <div class="jarviswidget-editbox"></div>
        <div class="widget-body">
            <ul class="nav nav-tabs bordered" id="mtab">
                <c:if test="${user}">
                    <li class="active" id="ml1">
                        <a data-toggle="tab" href="#s1">
                            <i class="fa fa-fw fa-lg fa-list-alt"></i>
                            用户
                        </a>
                    </li>
                </c:if>
                <c:if test="${provider}">
                    <li id="ml2">
                        <a data-toggle="tab" href="#s2">
                            <i class="fa fa-fw fa-lg fa-gear"></i>
                            顾问
                        </a>
                    </li>
                </c:if>
            </ul>

            <div class="tab-content padding-10">
                <c:if test="${user}">
                    <div id="s1" class="tab-pane fade <c:if test="${user}">in active"</c:if>>

                    </div>
                </c:if>
                <c:if test="${provider}">
                    <div id="s2" class="tab-pane fade <c:if test="${not user && provider}">in active"</c:if>">
                    </div>
                </c:if>
        </div>
    </div>
</div>
</div>