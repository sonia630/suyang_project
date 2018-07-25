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
        <li class="title am-text-center am-u-sm-10">订单详情</li>
        <li class="am-u-sm-1 am-text-right"><i class="am-icon-ellipsis-v "></i></li>
    </ul>
</div>

<!-- service order-->
<div class="order-detail">
    <ul class="am-list">
        <label>
            订单状态：<span>${order.order_status}</span>
        </label>

        <li class="am-g">
            <label class="am-fl am-form-label">联系电话：</label>
            <div class="am-fl">
                ${order.contact_phone}
            </div>
        </li>
        <li class="am-g">
            <label class="am-fl am-form-label">服务时间：</label>
            <div class="am-fl ">
                ${order.date}
            </div>
        </li>
        <li class="am-g">
            <label class="am-fl am-form-label">服务对象：</label>
            <div class="am-fl ">
                ${order.memberName}
            </div>
        </li>

        <li class="am-g">
            <label class="am-fl am-form-label">服务地址：</label>
            <div class="am-fl">
                <span class="am-icon-map-marker"></span> ${order.address}
            </div>
        </li>
        <li class="am-g border8">
            <label class="am-fl am-form-label">留&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;言：</label>
            <div class="am-fl">

            </div>
        </li>

        <li class="am-g border8">
            <div class="am-g text-lg">
                <label class="am-u-sm-4">服务总额</label>
                <div class="am-u-sm-4 am-text-right am-text-cost">¥ ${order.total_amount}</div>
            </div>

            <div class="am-g">
                <div class="am-u-sm-4"><span class="am-icon-circle"></span><label>服务金额</label></div>
                <div class="am-u-sm-4 am-text-right">¥ ${order.service_price}</div>
            </div>

            <div class="am-g">
                <div class="am-u-sm-4"><span class="am-icon-circle"></span><label>上门费用</label></div>
                <div class="am-u-sm-4 am-text-right">¥ ${order.journey_fee}</div>
            </div>

            <div class="am-g text-lg">
                <div class="am-u-sm-4"><label>服务时长</label></div>
                <div class="am-u-sm-4 am-text-right">30 min</div>
            </div>

            <div class="text-lg">
                <div class=" am-text-right">
                    <label class="am-margin-right-sm">实付款</label> <span class="am-text-cost">¥ ${order.total_amount}</span>
                </div>
            </div>
        </li>
    </ul>

    <div class="am-btn-group-xs am-text-center">
        <c:choose>
            <c:when test="${order.orderStatusCode==1}">
                <button class="am-btn am-radius am-btn-primary" id="confirm">确认接单</button>
                <button class="am-btn am-radius am-btn-primary" id="deny">拒绝接单</button>
            </c:when>
            <c:when test="${order.orderStatusCode==3}">
                <button class="am-btn am-radius am-btn-primary" id="start">开始服务</button>
                <button class="am-btn am-radius am-btn-primary" id="refund">申请退单</button>
            </c:when>
            <c:when test="${order.orderStatusCode==4}">
                <button class="am-btn am-radius am-btn-primary" id="end">结束服务</button>
            </c:when>
            <c:when test="${order.orderStatusCode==5}">
                <button class="am-btn am-radius am-btn-primary" id="record">填写病案</button>
            </c:when>
        </c:choose>
    </div>
</div>

<jsp:include page="../include/provider_footer.jsp"/>
<jsp:include page="../include/js.jsp"/>
<script>
    $(function () {
        Template.init("#orders");
        $("#deny").click(function () {
            action("/order/provider/deny");
        });
        $("#confirm").click(function () {
            action("/order/provider/confirm");
        });
        $("#start").click(function () {
            action("/order/provider/servicestart");
        });
        $("#end").click(function () {
            action("/order/provider/servicefinish");
        });
        $("#refund").click(function () {
            action("/order/provider/cancel");
        });
        $("#back").click(function () {
            window.location.href = "/order/provider_orders?orderStatus=${orderStatus}";
        });
        $("#record").click(function () {
            window.location.href = "/medical/index";
        })
    })

    function action(url) {
        $.ajax({
           type: "post",
           data: {"orderNo":"${orderNo}"},
           async: true,
           url: url,
        }).done(function (e) {
            if (e.code != 0) {
                $.toptip(e.desc, 'error');
            }else{
                window.location.href="/order/provider_orders"
            }
        }).fail(function (e) {
            $.toptip(e.desc, 'error');
        });
    }
</script>
</body>

</html>

