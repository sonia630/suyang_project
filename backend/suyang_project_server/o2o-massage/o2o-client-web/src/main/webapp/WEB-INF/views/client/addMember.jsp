<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
   <jsp:include page="../include/header.jsp"/>
    <style>
        .am-form-field[readonly]{
            background-color: #fff;
        }
        .weui-picker-modal{
            height: 22rem;
        }
    </style>
</head>

<body>
<!-- Topbar -->
<div class="topbar">
    <ul class="am-g">
        <li class="am-u-sm-1" id="back"><i class="am-icon-angle-left am-icon-sm"></i></li>
        <li class="title am-text-center am-u-sm-10">添加新成员</li>
        <li class="am-u-sm-1 am-text-right"><i class="am-icon-ellipsis-v "></i></li>
    </ul>
</div>

<!-- service order-->
<div class="service-order add-user">
    <ul class="am-list">

        <li class="am-g">
            <div class="am-form-group am-form-icon am-form-feedback am-text-middle">
                <label class="am-u-sm-4 am-form-label">输入姓名</label>
                <div class="am-u-sm-8">
                    <input type="text" class="am-form-field" placeholder="输入姓名" id="name">
                </div>
            </div>
        </li>

        <li class="am-g">
            <div class="am-form-group am-form-icon am-form-feedback am-text-middle">
                <label class="am-u-sm-4 am-form-label">输入性别</label>
                <div class="am-u-sm-8 radios">

                    <label> <input type="radio" checked name="sex"  value="1"> 男 </label>
                    <label> <input type="radio" name="sex"  value="0"> 女 </label>
                </div>
            </div>
        </li>

        <li class="am-g">
            <div class="am-form-group am-form-icon am-form-feedback am-text-middle">
                <label class="am-u-sm-4 am-form-label">出生年月</label>
                <div class=" am-u-sm-8 am-input-group am-datepicker-date"
                     data-am-datepicker="{format: 'yyyy-mm-dd', viewMode: 'years'}">
                    <input type="text" id="birthday" class="am-form-field" placeholder="日历组件" readonly>
                    <span class="am-input-group-btn am-datepicker-add-on">
                                <button class="am-btn am-btn-default" type="button" style="background-color: #ffffff;border-color: #fff;"><span
                                        class="am-icon-calendar"></span> </button>
                            </span>
                </div>
            </div>
        </li>
        <li class="am-g">
            <div class="am-form-group am-form-icon am-form-feedback am-text-middle">
                <label class="am-u-sm-4 am-form-label">所在城市</label>
                <div class="am-u-sm-8">
                    <input type="text" id="city-picker" class="am-form-field" readonly placeholder="城市">
                    <span class="am-icon-angle-right"></span>
                </div>
            </div>
        </li>

        <li class="am-g">
            <div class="am-form-group am-form-icon am-form-feedback am-text-middle">
                <label class="am-u-sm-4 am-form-label">与下单人关系</label>
                <div class="am-u-sm-8">
                    <input type="text" class="am-form-field" placeholder="与下单人关系" id="relation">
                </div>
            </div>
        </li>

        <li class="am-g">
            <div class="am-form-group am-form-icon am-form-feedback am-text-middle">
                <label class="am-u-sm-4 am-form-label">手机号码</label>
                <div class="am-u-sm-8">
                    <input type="text" class="am-form-field" placeholder="手机号码" id="phone">
                </div>
            </div>
        </li>
    </ul>

</div>

<!-- commit -->
<div class="am-navbar navbar-button am-g">
    <div class="am-u-sm-12">
        <button id="save" class="am-btn am-btn-block am-btn-primary am-vertical-align-middle am-btn-lg">保 存</button>
    </div>
</div>

<jsp:include page="../include/js.jsp"/>
<script src="/static/js/city-picker.min.js"></script>
<script>
    $(function () {
        $("#city-picker").cityPicker({
           title: "选择城市",
           showDistrict: false,
           onChange: function (picker, values, displayValues) {
               console.log(values, displayValues);
           }
        });
        $("#back").click(function () {
            window.location.href="/customer/selectMemberPage?form=${from}&serviceId=${serviceId}";
        })

        $("#save").click(function () {

            var data = {
                "memberName":$("#name").val(),
                "memberBirthday":$("#birthday").val(),
                "relation":$("#relation").val(),
                "city":$("#city-picker").val(),
                "cityCode":$("#city-picker").attr("data-codes"),
                "gender":$("input[name='sex'][checked]").val()
            }


            $.ajax({
               type: "post",
               async: false,
               dataType: "json",
               data: data,
               url: "/customer/saveMember",
           }).done(function () {
                window.location.href="/customer/selectMemberPage?from=${from}&serviceId=${serviceId}";
           }).fail(function (e) {
                $.toptip(e.message, e.status);
           });
        });
    });

</script>

</body>

</html>

