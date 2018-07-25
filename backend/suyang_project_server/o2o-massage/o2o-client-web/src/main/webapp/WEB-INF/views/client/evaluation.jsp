<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
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
        <li class="title am-text-center am-u-sm-10">服务评价晒单</li>
    </ul>
</div>

<!-- service order-->
<div class="evaluate">
    <div class="am-g">
        <div class="am-u-sm-3">
            <image src="/tools/download?name=${order.headPic}" width="70px" height="70px"
                   class="am-img-thumbnail am-circle"></image>
        </div>
        <div class="am-u-sm-9">
            <label>${order.providerName}</label>
            <div class="info">
                <span>
                     <c:choose>
                         <c:when test="${provider.level==1}">
                             初级顾问
                         </c:when>
                         <c:when test="${provider.level==2}">
                             中级顾问
                         </c:when>
                         <c:when test="${provider.level==3}">
                             高级顾问
                         </c:when>
                         <c:when test="${provider.level==4}">
                             特级顾问
                         </c:when>
                         <c:otherwise>
                             实习顾问
                         </c:otherwise>
                     </c:choose>
                </span>
                <span>服务${count}次</span>

            </div>
        </div>


    </div>
    <form class="am-form">
        <ul class="am-list am-list-border">
            <li>${order.serviceName}</li>
            <li>
                服务评分
                <span class="start-group">
                    <i class="am-icon-star active"></i>
                    <i class="am-icon-star"></i>
                    <i class="am-icon-star"></i>
                    <i class="am-icon-star"></i>
                    <i class="am-icon-star"></i>
                </span>

            </li>
            <li class="">
                <textarea id="desc" class="am-block" placeholder="评价超过10个字有机会获得优惠券" cols="30" rows="10"></textarea>

            </li>
        </ul>
    </form>

</div>

<!-- commit -->
<div class="am-navbar navbar-button am-g">
    <div class="am-u-sm-12">
        <button class="am-btn am-btn-block am-btn-primary am-vertical-align-middle am-btn-lg" id="ok">保 存</button>
    </div>
</div>

<jsp:include page="../include/js.jsp"/>

<script>
    $(function () {
       $(".start-group i").click(function () {
           $(".start-group i").removeClass("active");
           $(this).prevAll().addClass("active");
           $(this).addClass("active");
       });
       $("#back").click(function () {
           window.location.href="/order/order_detail?orderNo=${order.orderNo}"
       });
       $("#ok").click(function () {
           $.ajax({
              type: "post",
              async: false,
              data: {
                "providerUserId":"${order.providerUserId}",
                "orderNo":"${order.orderNo}",
                "desc":$("#desc").val(),
                "score":$(".start-group i.active").length,
                "serviceId":${order.serviceId}
              },
              dataType: "json",
              url: "/order/evaluation",
           }).done(function (e) {
               window.location.href = "/order/customer_orders?orderStatus=" + e;
           }).fail(function (e) {
               $.toptip(e.message, e.status);
           });
       })
    });
</script>
</body>

</html>

