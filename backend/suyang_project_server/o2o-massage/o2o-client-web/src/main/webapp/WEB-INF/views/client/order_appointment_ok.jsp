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
        <li class="title am-text-center am-u-sm-10">订单详情
        </li>
    </ul>
</div>

<!-- service order-->
<div class="order-detail">
    <ul class="am-list">
        <label>
            订单状态：<span>${order.order_status}</span>
        </label>

        <li class="am-g">
            <div class="am-fl">
                <image src="/tools/download?name=${order.head_pic}" width="60px" height="60px"
                       class="am-img-thumbnail am-circle"></image>
            </div>

            <div class="am-fl am-margin-left-sm ">
                <label>${order.provider_name}</label>
                <div>${order.service_name}</div>
            </div>
        </li>

        <li class="am-g">
            <label class="am-fl">联系电话：</label>
            <div class="am-fl">
                ${order.contact_phone}
            </div>
        </li>
        <li class="am-g">
            <label class="am-fl">服务时间：</label>
            <div class="am-fl ">
                2月8日 周四 10:30
            </div>
        </li>

        <li class="am-g">
            <label class="am-fl">服务地址：</label>
            <div class="am-fl">
                <span class="am-icon-map-marker"></span> ${order.address}
            </div>
        </li>
        <li class="am-g border8">
            <label class="am-fl">留&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;言：</label>
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
                <div class="am-u-sm-4 am-text-right">${order.est} min</div>
            </div>

            <div class="text-lg">
                <div class=" am-text-right">
                    <label class="am-margin-right-sm">实付款</label> <span class="am-text-cost">¥ ${order.total_amount}</span></div>
            </div>

        </li>

    </ul>

    <div class="am-btn-group-sm am-text-center">
        <button class="am-btn am-radius am-btn-primary" id="orders">查看订单</button>
    </div>

</div>

<jsp:include page="../include/js.jsp"/>

<script>
    $(function () {
        $("#back").click(function () {
            window.location.href="/service_order?serviceId=${order.serviceId}";
        });
        $("#orders").click(function () {
            window.location.href="/order/customer_orders?orderStatus=1";
        });
    });
</script>
</body>

</html>
