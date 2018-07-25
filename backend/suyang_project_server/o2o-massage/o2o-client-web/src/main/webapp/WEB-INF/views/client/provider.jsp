<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<!doctype html>
<html>
<head>
    <jsp:include page="../include/header.jsp"/>
    <script type="text/javascript"
            src="http://api.map.baidu.com/api?v=2.0&ak=7uEG0qE4p5ALZfO3a3x2U8LeYkY05Mud"></script>
</head>

<body>
<!-- Topbar -->
<div class="topbar topbar-therapist">
    <ul class="am-g">
        <li  class="am-u-sm-3 country"><span class="cityname">北京</span><i class="am-icon-angle-down"></i></li>
        <li class="title am-text-center am-u-sm-6">顾问列表</li>
        <li class="am-u-sm-3 am-text-right"></li>
    </ul>

    <!--List header-->
    <div class="list-header">
        <ul>
            <li class="active"><span>全部</span></li>
            <c:forEach items="${cats}" var="cat">
                <li><span>${cat.catName}</span></li>
            </c:forEach>
        </ul>
    </div>
</div>

<div class="therapist-list clear">

    <!--List search-->
    <div class="list-search">
        <div class="am-fl">为您推荐</div>
        <div class="am-fr">搜索<i class="am-icon-search"></i></div>
    </div>
    <div class="clear">
        <!-- ul -->
        <ul class="am-list" id="events-list"></ul>
    </div>
</div>

<div style="display: none" id="container"></div>
<!-- js -->
<jsp:include page="../include/customer_footer.jsp"/>
<jsp:include page="../include/js.jsp"/>
<script type="text/javascript" src="/static/js/handlebars.min.js"></script>
<script src="/static/js/refesh.js"></script>

<script type="text/x-handlebars-template" id="tpi-list-item">
    {{#each this}}
        <li class="am-g" onclick="detail('{{providerId}}')">
            <div class="am-fl">
                <image src="/tools/download?name={{headPic}}" alt="{{realName}}" width="70px" height="70px"
                       class="am-img-thumbnail am-circle"></image>
            </div>
            <div class="am-fl">
                <div class="list-content">
                    <div class="am-fl">
                        <h3>{{realName}} <i class="am-icon-map-marker"></i><span>{{distance}}km</span></h3>
                        <div class="am-text-warning">
                            {{age}}岁.{{levelString}}
                        </div>
                    </div>
                    <div class="info">{{providerIntroduction}}</div>
                </div>
            </div>
        </li>
    {{/each}}
</script>

<script>
    $(function () {
        Template.init("#provider");
        var geolocation = new BMap.Geolocation();
        geolocation.getCurrentPosition(function (r) {
            var map = new BMap.Map("container");
            var mPoint = new BMap.Point(r.point.lng,r.point.lat);
            var circle = new BMap.Circle(mPoint, ${span});
            map.addOverlay(circle);        //添加一个圆形覆盖物
            var data = {
                "neLongitude": circle.getBounds().getNorthEast().lng,
                "neLatitude": circle.getBounds().getNorthEast().lat,
                "swLongitude": circle.getBounds().getSouthWest().lng,
                "swLatitude": circle.getBounds().getSouthWest().lat,
                "userLng":r.point.lng,
                "userLat":r.point.lat,
                "start": 0,
                "count": 10
            };
            load(data);
        });
    })
    function load(data) {
        var app = new EventsList({
            api: '/provider/providerList',
            params:data
        });
        app.init();
        var loading = false;  //状态标记
        $(document.body).infinite().on("infinite", function() {
            if(loading) return;
            loading = true;
            app.handlePullUp();
            loading = false;
        });
    }
    function detail(providerId) {
        window.location.href = "/provider/providerDetail?providerId=" + providerId;
    }
</script>

</body>

</html>
