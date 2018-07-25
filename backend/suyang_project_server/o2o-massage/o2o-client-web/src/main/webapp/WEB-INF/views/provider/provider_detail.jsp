<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<head>
   <jsp:include page="../include/header.jsp"/>
    <style type="text/css">
        .info p {
            height: 60px;
            overflow: hidden;
        }
    </style>
</head>

<body>
<!-- Topbar -->
<div class="topbar">
    <ul class="am-g">
        <li class="am-u-sm-1" id="back"><i class="am-icon-angle-left am-icon-sm"></i></li>
        <li class="title am-text-center am-u-sm-10">顾问信息</li>
        <li class="am-u-sm-1 am-text-right"><i class="am-icon-ellipsis-v "></i></li>
    </ul>
</div>

<!-- List -->
<div class="therapist-detail">

    <!-- people -->
    <div class="am-g people">
        <div class="am-u-sm-3">
            <image src="/tools/download?name=${detail.headPic}" alt="${detail.realName}" title="${detail.realName}" width="80px" height="80px"
                   class="am-img-thumbnail am-circle"></image>
            <i class="excellent"></i>
        </div>
        <div class="am-u-sm-9">
            <div>
                <label>${detail.realName}</label>
                <span class="am-text-primary">${detail.levelString}</span>
                <span class="am-fr">服务 <span>${detail.serviceTimes}次</span></span>
            </div>
            <div class="am-margin-top-sm">
                推荐度
                <span class="start-group">
                    <i class="am-icon-star"></i>
                    <i class="am-icon-star"></i>
                    <i class="am-icon-star"></i>
                    <i class="am-icon-star"></i>
                    <i class="am-icon-star"></i>
                </span>
            </div>
        </div>
    </div>
    <div class="info">
        <p>${detail.providerIntroduction}</p>
        <div class="am-text-center more"><i class="am-icon-chevron-down am-text-xs"></i></div>
    </div>
    <div class="am-g story border8">
        <div class="am-u-sm-6">
            <a href="">
                <span style="background-color:#83c7a1 " class="am-icon-street-view"></span>
                顾问故事
            </a>
        </div>

        <div class="am-u-sm-6">
            <a href="">
                <span style="background-color:#F4AB63 " class="am-icon-creative-commons"></span>
                资质证书
            </a>
        </div>

    </div>
    <div class="comment-header am-g">
        <div class="am-u-sm-6">他的评价 <span class="am-text-gray" id="total">（1800条）</span></div>
        <div class="am-u-sm-6 am-text-right"><span class="am-text-orange" id="percent">好评 100% <i
                class="am-icon-chevron-right am-text-xs"></i> </span></div>
    </div>

    <ul class="am-comments-list" id="eval-list">
    </ul>

    <div class="am-padding-sm border-bottom">
        Ta提供的服务
    </div>
    <div class="list-header">
        <ul>
            <c:forEach items="${cats}" var="cat" varStatus="status">
                <li lid="${cat.catId}" <c:if test="${status.index==0}">class="active"</c:if>><span>${cat.catName}</span></li>
            </c:forEach>
        </ul>
    </div>

    <div class="clear">
        <ul class="am-list" id="list">
        </ul>
    </div>
</div>

<jsp:include page="../include/js.jsp"/>
<script src="/static/js/handlebars.min.js"></script>
<script type="text/x-handlebars-template" id="tpi-list-item">
    {{#each this}}
        <li>
            <div class="am-g">
                <div class="am-u-sm-9">
                    <div class="list-content">
                        <div class="am-g">
                            <h3 class="am-u-sm-8" style="padding-left: 23px">{{serviceName}} </h3>
                            <h3 class="am-u-sm-4 am-text-primary">{{estimatedTime}}分钟</h3></div>
                        <div>{{serviceSummary}}</div>
                    </div>
                </div>
                <div class="am-u-sm-3">
                    <button class="am-btn am-btn-primary am-btn-xs am-radius" onclick="appointment('{{serviceId}}')">预约</button>
                </div>
            </div>
            <div class="am-g">
                <div class="am-text-cost am-text-xs am-u-sm-4">¥ {{price}}</div>
                <div class="am-u-sm-4 am-text-xs">{{serviceTimes}}条评价</div>
                <div class="am-u-sm-4 am-text-xs">100%好评</div>
            </div>
        </li>
    {{/each}}
</script>
<script type="text/x-handlebars-template" id="eval-item">
    {{#each this}}
        <li class="am-comment">
            <header class="am-comment-hd">
                <div class="am-comment-meta">
                    <img src="/tools/download?name={{customerHeadPic}}"  class="am-img-thumbnail am-circle" width="30"
                         height="30"/>
                    <span>{{customerName}}</span>
                    <time class="am-fr">{{evalTime}}</time>
                </div>
            </header>

            <div class="am-comment-bd">
                {{description}}
            </div>
            <footer class="am-comment-footer am-comment-actions">
                <span>服务项：{{serviceName}}</span>
                <span class="am-margin-left-sm">{{serviceTime}}</span>
            </footer>
        </li>
    {{/each}}
</script>
<script>
    $(function () {
        $(".start-group i:eq(${detail.averageScore})").prevAll().addClass("active");
        localStorage.setItem("${providerId}-tj",${detail.averageScore});
        initCat();
        initEval();
        initMore ();
        $("#back").click(function () {
            window.location.href = "/provider";
        });
        $("#percent").click(function () {
            window.location.href = "/provider/evalList?providerId=${providerId}";
        });
    })

    function appointment(serviceId) {
        window.location.href = "/customer/providerOrder?providerId=${providerId}&serviceId=" + serviceId;
    }
    
    function initCat() {
        loadService($(".list-header ul li.active").attr("lid"));
        $(".list-header ul li").click(function () {
            $(".list-header ul li").removeClass("active");
            $(this).addClass("active");
            loadService($(this).attr("lid"));
        });
    }
    
    function initEval() {
        var compiler = Handlebars.compile($('#eval-item').html());
        $.getJSON("/provider/evalFirst2?providerId=${providerId}").then(function (e) {
            $('#eval-list').prepend(compiler(e.data));
            $("#total").html("（" + e.eval.total + "条）");
            $("#percent").html("好评 " + e.eval.praisePercent + "% ");
            localStorage.setItem("${providerId}-percent", e.eval.praisePercent);
        }, function() {
            $.toptip(e.message, e.status);
        });
    }

    function loadService(lid) {
        var compiler = Handlebars.compile($('#tpi-list-item').html());
        $.getJSON("/provider/providerServiceCat?providerId=${providerId}&catId=" + lid).then(function (e) {
            $('#list').html(compiler(e));
        }, function() {
            $.toptip(e.message, e.status);
        });
    }

    function initMore () {
        $('.more i').on('click', function () {
            if ($(this).hasClass('am-icon-chevron-down')) {
                $('.info p').css('height', 'auto')
                $(this).removeClass('am-icon-chevron-down').addClass('am-icon-chevron-up')
            } else {
                $('.info p').css('height', '60px')
                $(this).addClass('am-icon-chevron-down').removeClass('am-icon-chevron-up')
            }
        })
    }
</script>

</body>

</html>

