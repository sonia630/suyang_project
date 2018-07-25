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
        <li class="title am-text-center am-u-sm-10">客户评价</li>
        <li class="am-u-sm-1 am-text-right"><i class="am-icon-ellipsis-v "></i></li>
    </ul>
</div>

<!-- List -->
<div class="service-evaluate">

    <div class="comment-header">
        <div class="am-g top">
            <div class="am-u-sm-6" id="tj">推荐度：
                <span class="start-group am-fr am-margin-right-sm">
                <i class="am-icon-star active"></i>
                <i class="am-icon-star"></i>
                <i class="am-icon-star"></i>
                <i class="am-icon-star"></i>
                <i class="am-icon-star"></i>
             </span>
            </div>
            <div class="am-u-sm-6 am-text-right"><span class="am-text-orange" id="percent">好评 100%  </span></div>
        </div>
        <p class="evaluate-bage am-btn-group-xs">
            <button class="am-btn am-radius active" cId="1">全部 ${cat1}</button>
            <button class="am-btn am-radius" cId="2">好评 ${cat2}</button>
            <button class="am-btn am-radius" cId="3">中评 ${cat3}</button>
            <button class="am-btn am-radius" cId="4">差评 ${cat4}</button>
        </p>
        <p class="badge">
            <span>专业</span> <span>耐心</span> <span>有专长就是给力</span> <span>找专长药到病除</span> <span>专业</span>
            <span>有问题找专长</span>
            <span>专业</span> <span>耐心</span>
        <div class="am-text-center more"><i class="am-icon-chevron-down am-text-xs"></i></div>
        </p>
    </div>


    <ul class="am-comments-list" id="events-list">
    </ul>
    <div class="weui-loadmore">
        <i class="weui-loading"></i>
        <span class="weui-loadmore__tips">正在加载</span>
    </div>
</div>

<jsp:include page="../include/customer_footer.jsp"/>
<jsp:include page="../include/js.jsp"/>
<script type="text/javascript" src="/static/js/handlebars.min.js"></script>
<script src="/static/js/refesh.js"></script>

<script type="text/x-handlebars-template" id="tpi-list-item">
    {{#each this}}
    <li class="am-comment">
        <header class="am-comment-hd">
            <div class="am-comment-meta">
                <img src="/tools/download?name={{customerHeadPic}}"  class="am-img-thumbnail am-circle" width="30"
                     height="30"/>
                <span>{{customerName}}</span>
                <time class="am-fr">{{evalTime}}</time>
                <span class="start-group am-fr am-margin-right-sm">
                    <i class="am-icon-star {{#if star1}}active{{/if}}"></i>
                    <i class="am-icon-star {{#if star2}}active{{/if}}"></i>
                    <i class="am-icon-star {{#if star3}}active{{/if}}"></i>
                    <i class="am-icon-star {{#if star4}}active{{/if}}"></i>
                    <i class="am-icon-star {{#if star5}}active{{/if}}"></i>
                </span>
            </div>
        </header>

        <div class="am-comment-bd">
            {{description}}
        </div>
        <footer class="am-comment-footer am-comment-actions">
            <div class="am-fl">服务项：{{serviceName}}</div>
            <div class="am-fr">服务日期： <span>{{serviceTime}}</span></div>
        </footer>
    </li>
    {{/each}}
</script>

<script>

    var currentTab = 1;

    $(function () {
        Template.init("#provider");
        var score = localStorage.getItem("${providerId}-tj");
        $("#tj i:eq(" + score + ")").prevAll().addClass("active");
        var percent = localStorage.getItem("${providerId}-percent");
        $("#percent").html("好评 " + percent + "%");
        $("#back").click(function () {
            window.location.href="/provider/providerDetail?providerId=${providerId}";
        });
        load($(".evaluate-bage button.active"));
        $("[cId]").click(function () {
            $(".am-comments-list").html("");
            currentTab = $(this).attr("cId");
            load($(this));
        });
    })

    function load(button) {

        $(".evaluate-bage button").removeClass("active");
        $(button).addClass("active");
        var app = new EventsList({
            api: '/provider/evalListdata',
            params: {
                start: 0,
                catId: $(button).attr("cId"),
                providerId:"${providerId}",
                count: 10
            }
        });
        app.init();
        var loading = false;  //状态标记
        $(document.body).infinite().on("infinite", function() {
            if(loading) return;
            if (app.getOptions().params.catId != currentTab) {
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

