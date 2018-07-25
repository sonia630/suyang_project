<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<head>
    <jsp:include page="../include/header.jsp"/>

</head>

<body class="am-animation-slide-right">

<!-- Topbar -->
<div class="topbar">
    <ul class="am-g">
        <li class="am-u-sm-1" id="back"><i class="am-icon-angle-left am-icon-sm"></i></li>
        <li class="title am-text-center am-u-sm-10">选择上门时间</li>
        <li class="am-u-sm-1 am-text-right"><i class="am-icon-ellipsis-v "></i></li>
    </ul>
</div>

<div class="select-time am-panel-group">
    <div class="am-panel am-panel-default">
        <div class="am-panel-hd am-g">
            <h3 class="am-panel-title am-u-sm-9">未来一周可预约时间（北京时间）</h3>
            <h3 class="am-panel-title am-u-sm-3" id="month"></h3>
        </div>

        <div id="calendar" class="am-margin-bottom-sm time-weekly">

            <div class="picker-calendar-week-days">
               <%-- <a href="javascript:prev();" id="prev"
                   class="link icon-only picker-calendar-prev-year"><i
                        class="icon icon-prev"></i></a>--%>
                <div class="picker-calendar-week-days am-g" id="week">

                </div>

               <%-- <a href="javascript:next();" id="next"
                   class="link icon-only picker-calendar-prev-year"><i
                        class="icon icon-next"></i></a>--%>
            </div>
            <div class="picker-calendar-row" id="date">
            </div>

        </div>
    </div>


    <div class="am-panel am-panel-default">
        <div class="am-panel-hd">
            <h3 class="am-panel-title">上午</h3>
        </div>
        <div class="am-panel-bd time">
            <span time="1">08:00</span>
            <span time="2">08:30</span>
            <span time="3">09:00</span>
            <span time="4">09:30</span>
            <span time="5">10:00</span>
            <span time="6">10:30</span>
            <span time="7">11:00</span>
            <span time="8">11:30</span>
            <span time="9">12:00</span>
            <span time="10">12:30</span>
        </div>
    </div>


    <div class="am-panel am-panel-default">
        <div class="am-panel-hd">
            <h3 class="am-panel-title">下午</h3>
        </div>
        <div class="am-panel-bd time">
            <span time="11">13:00</span>
            <span time="12">13:30</span>
            <span time="13">14:00</span>
            <span time="14">14:30</span>
            <span time="15">15:00</span>
            <span time="16">15:30</span>
            <span time="17">16:00</span>
            <span time="18">16:30</span>
            <span time="19">17:00</span>
            <span time="20">17:30</span>
            <span time="21">18:00</span>
        </div>
    </div>

    <div class="am-panel am-panel-default">
        <div class="am-panel-hd">
            <h3 class="am-panel-title">晚上</h3>
        </div>
        <div class="am-panel-bd time">
            <span time="22">18:30</span>
            <span time="23">19:00</span>
            <span time="24">19:30</span>
            <span time="25">20:00</span>
        </div>
    </div>

</div>

<!-- commit -->
<div class="am-navbar navbar-button am-g">
    <div class="am-u-sm-6 time-result am-margin-top-sm">
        <i class="am-icon-calendar"></i>
        <span id="day"></span><span id="xq"></span>
    </div>
    <div class="am-u-sm-5">
        <button class="am-btn am-btn-block am-btn-primary am-vertical-align-middle am-btn-lg" id="ok">确
            定
        </button>
    </div>

</div>

<jsp:include page="../include/js.jsp"/>
<script src="/static/plugin/moment.min.js"></script>
</body>

<script>
    $(function () {
        slideRight()
        initCalendar()
        $(".time span").click(function () {
           if($(this).hasClass("freeze")){
                return;
           }
           if($(this).hasClass("active")){
               $(this).removeClass("active");
           }else{
               $(this).addClass("active");
           }
        });
        $("#back").click(function () {
           window.location.href="/provider/self/profile/page";
        });

        $("#ok").click(function () {

            var strCookie = document.cookie;
            var arrCookie = strCookie.split(";");
            var data = [];
            var storeCookies = [];
            var date = $(".picker-calendar-day-selected").attr("data-date");
            for(var i = 0; i < arrCookie.length; i++){
                var arr = arrCookie[i].split("=");
                if (arr[0].indexOf("s3_schedule_") > 0) {
                    if (date != arr[0].substring(13)) {
                        data.push(arr[0].substring(13) + "->" + arr[1]);
                        storeCookies.push($.trim(arr[0]));
                    }
                }
            }

            if ($(".time span.active").length > 0) {
                var value;
                if (!$.cookie("s3_schedule_" + date)) {
                    value = getSelectedDayAndTime($(".picker-calendar-day-selected"),false);
                }else{
                    value = getSelectedDayAndTime($(".picker-calendar-day-selected"),true);
                }
                if (value.times.length > 0) {
                    var selected = value.times.join("-");
                    data.push(value.date + "->" + selected);
                    $.cookie('s3_schedule_' + value.date, selected, {expires: 0.1, path: "/"});
                    storeCookies.push("s3_schedule_" + value.date);
                }
            }

            $.ajax({
               type: "post",
               async: false,
               data: {
                   "data": data.join(","),
               },
               dataType: "json",
               url: "/provider/schedule",
            }).done(function (e) {
                if (e.code != 0) {
                    $.toptip(e.message, "error");
                    return;
                }
                for (i in storeCookies) {
                    $.cookie(storeCookies[i], "", {expires: -1, path: "/"});
                }
                window.location.href = "/provider/self/profile/page";
            }).fail(function (e) {
                $.toptip(e.message, e.status);
            });
        });
    })

    function slideRight() {
        $('.topbar').css('position', 'initial')

        let clientHeight = document.documentElement.clientHeight - 43
        $('.select-time').css({'height': clientHeight}).addClass('slideBarLeft')

        // $('.topbar').css('position', 'fixed')
        setTimeout(function () {
            $('body').removeClass('am-animation-slide-right')
            $('.select-time').css({'height': 'auto'}).addClass('sliderMargin')
                .removeClass('slideBarLeft')
            $('.topbar').css('position', 'fixed')
        }, 400)

    }

    // function slideLeft() {
    //     $('body').addClass('am-animation-slide-left')
    //     setTimeout(function () {
    //         window.open('serviceorder.html')
    //     }, 400)
    // }

    function initCalendar() {
        // let now = ;
        initTime('init')
        $(".first").addClass("picker-calendar-day-selected");
        fillShowDay();
        selectDay();
        initTimes();
        restoreSelectedDay($(".picker-calendar-day-selected"));
    }
    
    function initTimes() {
        var date = $(".picker-calendar-day-selected").attr("data-date");
        $.ajax({
           type: "get",
           async: false,
           dataType: "json",
           url: "/provider/scheduled",
       }).done(function (e) {
            var times = [];
           $.each(e,function (index,value) {
               $.each(value,function(i,v){
                   times.push(v.slot + "_" + v.orderNo);
               });
               $.cookie('s3_schedule_' + index, times.join("-"), {expires: 0.1, path: "/"});
               times = [];
           });
        }).fail(function (e) {
            $.toptip(e.message, e.status);
        });
    }


    function fillShowDay() {
        var date = $(".picker-calendar-day-selected").attr("data-date");
        var _month = date.split("-");
        var mm = _month[1];
        if (mm.charAt(0) == "0") {
            mm = mm.substring(1);
        }
        $("#day").html(mm + "月" + $(".picker-calendar-day-selected").html() + "日");
        $("#xq").html($(".picker-calendar-day-selected").attr("data-week"));
    }

    function selectDay() {
        $("body").on("click", ".picker-calendar-day", function () {
            saveSelectedDay($(".picker-calendar-day-selected"));
            $(".picker-calendar-day").removeClass("picker-calendar-day-selected")
            $(".time span.active").removeClass("freeze");
            $(".time span.active").removeClass("active");
            $(this).addClass("picker-calendar-day-selected");
            restoreSelectedDay($(".picker-calendar-day-selected"));
            fillShowDay();
        })
    }

    function saveSelectedDay(selected) {
        var data = getSelectedDayAndTime(selected,true);
        $.cookie('s3_schedule_' + data.date, data.times.join("-"), {expires: 0.1, path: "/"});
    }

    function getSelectedDayAndTime(selected,all) {
        var date = $(selected).attr("data-date");
        var times = [];
        $(".active").each(function () {
            if (all || !$(this).hasClass("freeze")) {
                if($(this).hasClass("freeze")){
                    times.push($(this).attr("time")+"_freeze");
                }else{
                    times.push($(this).attr("time"));
                }
            }
        });
        return {"date": date, "times": times};
    }
    
    function restoreSelectedDay(selected) {
        var date = $(selected).attr("data-date");
        var items = $.cookie('s3_schedule_' + date);
        if (items) {
            var _item = items.split("-");
            for (i in _item) {
                var tp = _item[i].split("_");
                $(".time span[time='" + tp[0] + "']").addClass("active");
                if (tp[1] && tp[1] != "!1") {
                    $(".time span[time='" + tp[0] + "']").addClass("freeze");
                }
            }
        }
    }

    function next() {
        initTime('next')
    }

    function prev() {
        initTime('prev')
    }

    function initTime(method) {
        //for month
        let monthHtml = ""
        if (method == 'init') {
            monthHtml = moment().format('YYYY-MM');
        } else {
            monthHtml = moment($('.picker-calendar-day').eq(0).attr('data-date')).format('YYYY-MM');
        }
        $('#month').html(monthHtml);

        // for calendar
        let start = moment();
        if (method == 'next') {
            start = moment($('.picker-calendar-day').eq(6).attr('data-date'))
        } else if (method == 'prev') {
            start = moment($('.picker-calendar-day').eq(0).attr('data-date')).subtract(8, 'days')
        }
        let weekHtml = '', dateHtml = ''
        $('#week').html('')
        $('#date').html('')

        for (let i = 0; i < 7; i++) {
            let date = start.add(1, 'days')
            let day = date.format('YYYY-MM-DD'), DD = date.format('DD'), d = date.format('d')
            d = parseChinese(d)

            $('#week').append('<div class="picker-calendar-week-day"> ' + d + '</div>')
            if (i == 0) {
                $('#date').append('<div class="picker-calendar-day first" data-week="'+d+'" data-date="' + day + '"><span>' + DD
                                  + '</span></div>')
            }else{
                $('#date').append('<div class="picker-calendar-day" data-week="'+d+'" data-date="' + day + '"><span>' + DD
                                  + '</span></div>')
            }
        }
    }

    function parseChinese(d) {
        switch (d) {
            case '1':
                return '星期一'
            case '2':
                return '星期二'
            case '3':
                return '星期三'
            case '4':
                return '星期四'
            case '5':
                return '星期五'
            case '6':
                return '星期六'
            case '0':
                return '星期日'
            default:
                return ''
        }
    }
</script>
</html>

