<%@ page contentType="text/html;charset=UTF-8"%>
<!doctype html>
<html>
<head>
    <jsp:include page="../include/header.jsp"/>
    <link rel="stylesheet" href="/static/plugin/fullcalendar/fullcalendar.min.css"/>
    <style>

        body {
            margin: 40px 10px;
            padding: 0;
            font-family: "Lucida Grande", Helvetica, Arial, Verdana, sans-serif;
            font-size: 14px;
        }

        #calendar {
            max-width: 400px;
            margin: 0 auto;
            font-size: 11px;
            overflow: hidden;
        }

    </style>
</head>
<body>

<!-- Topbar -->
<div class="topbar">
    <ul class="am-g">
        <li class="am-u-sm-1"><i class="am-icon-angle-left am-icon-sm"></i></li>
        <li class="title am-text-center am-u-sm-10">服务评价</li>
        <li class="am-u-sm-1 am-text-right"><i class="am-icon-ellipsis-v "></i></li>
    </ul>
</div>

<div class="schedule">
    <!-- calendar -->
    <div id='calendar'></div>

    <!-- schedule content -->
    <div id="scheduleContent" class="weui-popup__container popup-bottom">
        <div class="weui-popup__overlay"></div>
        <div class="weui-popup__modal">
            <div class="toolbar">
                <div class="toolbar-inner">
                    <a href="javascript:;" class="picker-button close-popup">关闭</a>
                    <h1 class="title" id="date">2月28日</h1>
                </div>
            </div>
            <div class="modal-content">
                <ul class="am-list">
                    <li class="am-g">
                        <div class="am-u-sm-9" id="serviceName">

                        </div>
                        <div class="am-u-sm-3" id="time">
                        </div>
                        <div class="am-u-sm-12"><i class="am-icon-map-marker"></i><span id="address"></span></div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>


<jsp:include page="../include/provider_footer.jsp"/>
<jsp:include page="../include/js.jsp"/>
<script src="/static/plugin/moment.min.js"></script>
<script src="/static/plugin/fullcalendar/fullcalendar.min.js"></script>
<script>

    $(function () {
        initCalendar()
        $(".fc-next-button").click(function () {
            getDate();
        });
    })

    function getDate() {
        var moment = $('#calendar').fullCalendar('getDate');
        return moment.format('YYYY-MM-DD');
    }

    function initCalendar () {
        $('#calendar').fullCalendar({
            height: 'auto',
            header: {
                left: ' today',
                center: 'title',
                right: 'prev,next',
                titleFormat: 'YYYY-MM-DD'
            },
            views: {
                agendaWeek: {
                    titleFormat: 'YYYY, MM, DD',
                    allDaySlot: false,
                    minTime: '08:00:00',
                    maxTime: '20:30:00',
                }
            },
            columnHeaderHtml: function (mom) {
                console.log('mom', mom, mom.date(), mom.month())
                let html = '<b>' + mom.format('DD/MM') + '</b>'

                if (mom.weekday() === 1) {
                    return html + '<b>星期一</b>'
                } else if (mom.weekday() === 2) {
                    return html + '<b>星期二</b>'
                } else if (mom.weekday() === 3) {
                    return html + '<b>星期三</b>'
                } else if (mom.weekday() === 4) {
                    return html + '<b>星期四</b>'
                } else if (mom.weekday() === 5) {
                    return html + '<b>星期五</b>'
                } else if (mom.weekday() === 6) {
                    return html + '<b>星期六</b>'
                } else if (mom.weekday() === 0) {
                    return html + '<b>星期日</b>'
                }
            },
            defaultView: 'agendaWeek',
            // defaultDate: moment(),
            navLinks: false, // can click day/week names to navigate views
            editable: false,
            eventLimit: true, // allow "more" link when too many events

            resources: [
                {id: 1, eventColor: 'red'}
            ],
            events: "/provider/calendar/latest",
            eventClick: function (calEvent, jsEvent, view) {
                console.log('calevent', calEvent, view)
                $('.fc-event-container a').removeClass('active')
                $(this).addClass('active')

                let id = calEvent.id
                showCalendarContent(id)
            }
        })
    }

    function showCalendarContent (id) {
        $.ajax({
           type: "get",
           async: false,
           data: {
               "orderNo":id
           },
           dataType: "json",
           url: "/order/provider/program_order_info",
       }).done(function (e) {
            $("#serviceName").html(e.data.serviceName);
            $("#date").html(e.data.date);
            if (e.data.time.indexOf("AM") >= 0) {
                $("#time").html("上午" + e.data.time.substring(2));
            }else{
                $("#time").html("下午" + e.data.time.substring(2));
            }
            $("#address").html(e.data.address);
       }).fail(function (e) {
            $.toptip(e.message, e.status);
       });
       $('#scheduleContent').popup()
    }

</script>

</body>

</html>
