<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<!doctype html>
<html>
<head>
    <jsp:include page="../include/header.jsp"/>
</head>

<body>

<!-- Topbar -->
<div class="topbar">
    <ul class="am-g">
        <li class="am-u-sm-1" id="back"><i class="am-icon-angle-left am-icon-sm"></i></li>
        <li class="title am-text-center am-u-sm-10">资质证书</li>
        <li class="am-u-sm-1 am-text-right"></li>
    </ul>
</div>

<div class="layout certificate">
    <div class="top"><label>资质证书</label>
        <button id="addCert" class="am-btn am-btn-primary am-btn-xs am-round am-fr" style="margin-top: -4px"><i class="am-icon-plus"></i> 添加新证书</button>
    </div>
    <ul class="am-list">
        <li class="am-g">
            <c:forEach items="${certs}" var="cert" varStatus="status">
                <div class="pic <c:if test="${status.index%2==0}">am-margin-right-sm</c:if>">
                    <div class="am-u-sm-12 am-margin-top-sm">
                        <image src="/tools/download?name=${cert.pic}" width="100%" style="height: 100px"
                               class="am-img-thumbnail "></image>
                    </div>
                    <div class="am-u-sm-12">
                        ${cert.name}
                    </div>
                </div>
            </c:forEach>
        </li>
    </ul>
</div>

<!-- commit -->
<div class="am-navbar navbar-button am-g">
    <div class="am-u-sm-12">
        <button class="am-btn am-btn-block am-btn-primary am-vertical-align-middle am-btn-lg" id="cancel">取 消</button>
    </div>
</div>

<jsp:include page="../include/js.jsp"/>
<script>
    $(function () {
        $("#back,#cancel").click(function () {
            window.location.href = "/provider/self/profile/page";
        })

        $("#addCert").click(function () {
            window.location.href="/provider/add_cert";
        })
    })
</script>
</body>


</html>

