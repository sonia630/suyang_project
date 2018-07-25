<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<!doctype html>
<html>
<head>
  <jsp:include page="../include/header.jsp"/>

</head>

<body>
<!-- Topbar -->
<div class="topbar topbar-therapist">
    <ul class="am-g">
        <li class="title am-text-center am-u-sm-12">我的接单</li>

    </ul>

    <!--List header-->
    <div class="list-order-header receive-order-header">
        <ul>
            <li lid="1" <c:if test="${orderStatus==1}">class="active"</c:if>><span>待确认</span></li>
            <li lid="2" <c:if test="${orderStatus==2}">class="active"</c:if>><span>待付款</span></li>
            <li lid="3,4" <c:if test="${orderStatus==3||orderStatus==4}">class="active"</c:if>><span>待服务</span></li>
            <li lid="5" <c:if test="${orderStatus==5}">class="active"</c:if>><span>已完成</span></li>
            <li lid="6" <c:if test="${orderStatus==6}">class="active"</c:if>><span>已取消</span></li>
        </ul>
    </div>
</div>


<!-- List -->
<div class="my-receive-orders">
    <ul class="am-list" id="events-list"></ul>
</div>

<script type="text/x-handlebars-template" id="tpi-list-item">
    {{#each this}}
    <li class="am-g border8" onclick="liclick(this)" no="{{orderNo}}">
        <div class="am-fl">
            {{serviceName}}
        </div>
        <div class="am-fr">
            {{bookStartTime}}
        </div>
        <div class="address"><i class="am-icon-map-marker"></i>{{address}}</div>
    </li>
    {{/each}}
</script>

<jsp:include page="../include/provider_footer.jsp"/>
<jsp:include page="../include/js.jsp"/>
<script type="text/javascript" src="/static/js/handlebars.min.js"></script>
<script src="/static/js/refesh.js"></script>
<script>

    var currentTab = 1;

    $(function () {
        // slider()
        Template.init("#orders");
        load($(".active"));
        $("[lid]").click(function () {
            $(".am-list li").html("");
            currentTab = $(this).attr("lid");
            load($(this));
        });
    });


    function liclick(node) {
        window.location.href = "/order/provider_order_detail?orderStatus=${orderStatus}&orderNo=" + $(node).attr("no");
    }

    function load(li) {

        $(".list-order-header ul li").removeClass("active");
        $(li).addClass("active");
        var app = new EventsList({
             api: '/order/provider_json_orders',
             params: {
                 start: 0,
                 orderStatus: $(li).attr("lid"),
                 count: 10
             }
         });
        app.init();
        var loading = false;  //状态标记
        $(document.body).infinite().on("infinite", function() {
            if(loading) return;
            if (app.getOptions().params.orderStatus != currentTab) {
                return;
            }
            loading = true;
            app.handlePullUp();
            loading = false;
        });
    }
</script>
</body>

</html>

