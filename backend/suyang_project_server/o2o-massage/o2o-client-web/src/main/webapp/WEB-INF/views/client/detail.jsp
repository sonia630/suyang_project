<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<!doctype html>
<html>
<head>
    <jsp:include page="../include/header.jsp"/>

</head>

<body>
<div class="topbar">
    <ul class="am-g">
        <li class="am-u-sm-1"><i class="am-icon-angle-left am-icon-sm"></i></li>
        <li class="title am-text-center am-u-sm-10">${service.serviceInfo.serviceName}</li>
        <li class="am-u-sm-1 am-text-right"><i class="am-icon-ellipsis-v "></i></li>
    </ul>
</div>
<!-- service detail -->
<div class="service-detail">

    <image src="/static/images/service-lg.png" width="100%" height="200"/>
    <div class="service-detail-content">
        <!--<div>-->
        <!--<h1>感冒调理</h1>-->
        <!--</div>-->
        <div>
            <h2>服务内容 </h2>
            <p>
                通过对儿童手部、背部、腰腹部、头部消化道系统的相关穴位进行按摩，达到对各种原因引起的小儿腹泻等消化道疾病进行调理。达到健脾调中、理肠止泻、行滞消食、强身健体等功效。</p>

        </div>
        <div>
            <h2>服务对象：</h2>
            <p>
                0~12岁儿童
            </p>
        </div>
        <div>
            <h2>注意事项 </h2>
            <p>1、请不要吃太饱
                <br>
            </p>
        </div>
        <div>
            <h2>下单须知 </h2>
            <p>1、XXXX
                <br>2、XXXX
                <br>3、XXXX
                <br>
            </p>
        </div>
        <div>
            <h2>评价</h2>
            <p>
                XXXXXXXXXXXXXXX
                <br></p>

        </div>
    </div>

</div>
</div>
<!-- commit -->
<div class="am-navbar navbar-button">
    <div class="am-g">
        <div class="am-u-sm-6"><span class="cost"><span><fmt:formatNumber
                value="${service.srvProviderSrvRel.price}"
                pattern="0.00"/></span>元/${service.srvProviderSrvRel.estimatedTime}分钟</span></div>
        <div class="am-u-sm-6">
            <button class="am-btn am-btn-block am-btn-primary am-vertical-align-middle am-btn-lg"
                    id="order">立 即 预 约
            </button>
        </div>

    </div>


</div>

<jsp:include page="../include/js.jsp"/>

<script>
    $(function () {
        $(".am-icon-angle-left").click(function () {
            window.location.href = "/index";
        });

        $("#order").click(function () {
            window.location.href = "/service_order?serviceId=${serviceId}";
        })
    })


</script>
</body>

</html>