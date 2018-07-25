<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<head>
    <jsp:include page="../include/header.jsp"/>
</head>

<body style="background-color:#efefef;">
<div class="topbar">
    <ul class="am-g">
        <li  class="am-u-sm-1" id="back"><i class="am-icon-angle-left am-icon-sm"></i></li>
        <li class="title am-text-center am-u-sm-10">预约下单</li>
        <li class="am-u-sm-1 am-text-right"><i class="am-icon-ellipsis-v "></i></li>
    </ul>
</div>

<form id="form" method="post" onsubmit="return false">
    <input type="hidden" name="serviceId" value="${service.serviceInfo.serviceId}"/>
    <input type="hidden" id="longitude" name="longitude"/>
    <input type="hidden" id="latitude" name="latitude"/>
    <input type="hidden" name="providerUserId" value="${provider.userId}"/>
    <input type="hidden" name="memberId" id="memberId"/>
    <div class="service-order">
        <ul class="am-list">
            <li class="am-g border8">
                <image src="/tools/download?name=${provider.headPic}" alt="${provider.realName}" title="${provider.realName}" width="50px" height="50px"
                       class="am-img-thumbnail am-circle"></image>
                <span>${provider.realName}</span>
            </li>

            <li class="am-g">
                <div class="am-form-group am-form-icon am-form-feedback am-text-middle">
                    <label class="am-u-sm-5 am-form-label" style="text-align: left;width: 30%;padding-left: 6px;">${service.serviceInfo.serviceName}</label>
                    <div class="am-u-sm-6">
                        <input type="text" class="am-form-field" disabled value="<fmt:formatNumber value="${service.srvProviderSrvRel.price}" pattern="0.00"/>元">
                        <span class="am-icon-angle-right"></span>
                    </div>
                </div>
            </li>
            <li class="am-g">
                <div class="am-form-group am-form-icon am-form-feedback am-text-middle">
                    <label class="am-u-sm-3 am-form-label">服务份数</label>
                    <div class="am-u-sm-9 ">
                        <div class="numberStep am-fr">
                            <button class="am-btn am-btn-default"><i class="am-icon-minus"></i></button>
                            <input type="text" value="1" id="count" name="serviceCount"/>
                            <button class="am-btn am-btn-success"><i class="am-icon-plus"></i></button>
                        </div>
                    </div>
                </div>
            </li>

            <li class="am-g">
                <div class="am-form-group am-form-icon am-form-feedback am-text-middle">
                    <label class="am-u-sm-3 am-form-label">被服务人</label>
                    <div class="am-u-sm-9">
                        <a href="/customer/selectMemberPage?from=/customer/providerOrder&serviceId=${service.serviceInfo.serviceId}">
                            <input type="text" class="am-form-field" disabled value="本人" required id="memberName"/>
                            <span class="am-icon-angle-right"></span>
                        </a>
                    </div>
                </div>
            </li>
            <li class="am-g">
                <div class="am-form-group am-form-icon am-form-feedback am-text-middle">
                    <label class="am-u-sm-3 am-form-label">服务地址</label>
                    <div class="am-u-sm-9">
                        <a href="/common/location?from=/customer/providerOrder&serviceId=${service.serviceInfo.serviceId}">
                            <input type="text" class="am-form-field" disabled id="address" name="address" placeholder="小区、街道或大厦名称" required/>
                            <span class="am-icon-angle-right"></span>
                        </a>
                    </div>
                </div>
            </li>
            <li class="am-g">
                <div class="am-form-group am-form-icon am-form-feedback am-text-middle">
                    <label class="am-u-sm-3 am-form-label">上门时间</label>
                    <div class="am-u-sm-9">
                        <a id="time" href="/common/time?from=/customer/providerOrder&serviceId=${service.serviceInfo.serviceId}">
                            <input type="text" class="am-form-field" disabled placeholder="请选择上门时间" id="input-time" required>
                            <span class="am-icon-angle-right"></span>
                        </a>
                    </div>
                </div>
            </li>

            <li class="am-g border8">
                <div class="am-form-group am-form-icon am-form-feedback am-text-middle">
                    <label class="am-u-sm-3 am-form-label">联系电话</label>
                    <div class="am-u-sm-9">
                        <input type="text" class="am-form-field" placeholder="联系电话" value="${user.phone}" required/>
                    </div>
                </div>
            </li>
            <li class="cost">
                <div class="am-g">
                    <div class="am-u-sm-4">服务时长</div>
                    <div class="am-u-sm-4 r">${service.srvProviderSrvRel.estimatedTime} min</div>
                </div>

                <div class="am-g">
                    <div class="am-u-sm-4">服务费用</div>
                    <div class="am-u-sm-4 r"><fmt:formatNumber value="${service.srvProviderSrvRel.price}" pattern="0.00"/>元</div>
                </div>
                <div class="am-g">
                    <div class="am-u-sm-4">预计上门费用</div>
                    <div class="am-u-sm-4 r" id="journeyFee"></div>
                </div>
                <div class="am-g">
                    <div class="am-u-sm-4">预计实付</div>
                    <div class="am-u-sm-4 r" id="total"></div>
                </div>
            </li>
        </ul>
        <ul class="reminder">
            <li class="am-text-xs">不适宜按摩禁忌提示</li>
            <li class="am-text-xs">每天按摩、对提高免疫力、改善健康状况更有效果</li>
            <li class="am-text-xs">因故未能按时按摩，订单取消后支付金额远路返回，到账时间由支付方式决定</li>
        </ul>
</div>

    <div class="am-navbar navbar-button am-g">
        <div class="am-u-sm-12">
            <button class="am-btn am-btn-block am-btn-primary am-vertical-align-middle am-btn-lg" id="submit">确 认 预 约</button>
        </div>
    </div>
</form>
<jsp:include page="../include/js.jsp"/>
<script>
    $(function () {
        $("#back").click(function () {
            window.location.href="/provider/providerDetail?providerId=${provider.userId}";
        });

        $("#input-time").val($.cookie("s3_time"));
        var member = $.cookie('s3_member');
        if(member){
            var values=member.split("-");
            $("#memberId").val(values[0]);
            $("#memberName").val(values[1]);
        }

        var MAX = 10, MIN = 1;
        $(".am-icon-minus").click(function () {
            var number = parseInt($("#count").val() || "0") - 1
            if (number < MIN) number = MIN;
            $("#count").val(number)
        });

        $(".am-icon-plus").click(function () {
            var number = parseInt($("#count").val() || "0") + 1
            if (number > MAX) number = MAX;
            $("#count").val(number)
        });


        var localTime = localStorage.getItem("locationTime");

        if(localTime){
            if((new Date().getTime() - new Date(localTime).getTime()) / 1000 / 60 > 30){
                removeLocalItems();
                fillwithCurrentLocation();
            }else{
                $("#address").val(localStorage.getItem("commonAddress") + "" + localStorage.getItem("detailAddress"));
                var href = $("#time").attr("href");
                href += "&lng=" + localStorage.getItem("longitude") + "&lat=" + localStorage.getItem("latitude");
                $("#time").attr("href", href);
                $("#longitude").val(localStorage.getItem("longitude"));
                $("#latitude").val(localStorage.getItem("latitude"));
            }
        }

        $.getJSON("/provider/journeyFeeAndTotal?providerId=${provider.userId}&serviceId=${service.serviceInfo.serviceId}&userLng=" + $("#longitude").val() + "&userLat=" + $("#latitude").val())
            .then(function (e) {
                if (e.total) {
                    if(!e.journeyFee){
                        $("#journeyFee").html(e.journeyFee + "元");
                    }else{
                        $("#journeyFee").html("加" + e.journeyFee + "元");
                    }
                    $("#total").html(e.total + "元");
                }
            }, function () {
                $.toptip(e.message, e.status);
            })

        $("#submit").click(function () {
            var $form = $("#form");
            $.ajax({
                type: form.method || 'POST',
                url: "order/submit",
                data: $form.serializeArray(),
                dataType: "json",
                cache: false,
            }).done(function (e) {
                if (e.code == 0) {
                    $.cookie("s3_time", "");
                    $.cookie("s3_member", "");
                    removeLocalItems();
                    window.location.href = "order/order_appointment_ok?orderNo=" + e.data.orderNo;
                } else {
                    $.toptip(e.desc, 'error');
                }
            }).fail(function (e) {
                $.toptip('预约失败', 'error');
            });
        })
    })

    function removeLocalItems() {
        localStorage.removeItem("commonAddress");
        localStorage.removeItem("detailAddress");
        localStorage.removeItem("longitude");
        localStorage.removeItem("latitude");
        localStorage.removeItem("locationTime");
    }
</script>
</body>

</html>
