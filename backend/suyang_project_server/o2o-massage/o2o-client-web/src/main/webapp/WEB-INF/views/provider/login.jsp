<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<head>
    <jsp:include page="../include/header.jsp"/>
</head>

<body>
<!-- Topbar -->
<div class="login">
    <image src="/static/images/logo.svg" height="100" style="opacity: 0.7; margin-bottom: 10px;"></image>
    <div class="title">
        <h1 class="title am-text-primary">太阳树调理之家欢迎您</h1>
        <div class="tip ">使用手机号快速注册/登录</div>
    </div>
    <div class="am-form">
        <div class="form-group">
            <input type="text" id="phone" placeholder="输入手机号码" maxlength="11">
            <div style="top: 12px;">
                <button type="button" id="send" class="am-btn am-btn-success am-round am-btn-xs">获取验证码
                </button>
            </div>
        </div>

        <div class="form-group">
            <input type="text" name="smsCode" maxlength="6" id="smsCode" placeholder="输入验证码">
        </div>

        <div class="form-group">
            <input type="text" id="verify" name="verify" placeholder="输入图形验证码">
            <div style="top: 12px;">
                <image title="点击刷新" id="imgvcode" height="30"/>
            </div>
        </div>
        <button class="am-btn am-btn-block am-btn-primary am-btn-lg " id="sb">验 证 手 机</button>
    </div>


    <p class=agreement>点击“确定”，即表示同意 <a href="#">《太阳树小儿推拿使用协议》</a></p>
    <p class="copyright">Copyright@2018 www.tys.com</p>

</div>

</body>
<jsp:include page="../include/js.jsp"/>
<script type="text/javascript">
    $('#sb').click(function () {
        if ($(this).hasClass("am-disabled")) {
            return;
        }
        if (!$('#phone').val()) {
            $.alert('请填写手机号码');
            return;
        }
        if (!$('#smsCode').val()) {
            $.alert('请填写验证码');
            return;
        }
        $.ajax({
           type: "post",
           url: "/provider/login/do",
           async: false,
           data: {"phone": $("#phone").val(), "verifyCode": $("#smsCode").val()},
           dataType: "json"
       }).done(function (e) {
            if (e.code != -1) {
                $.toast('验证成功');
                $.cookie('s3_tt', e.data.token, {expires: 1, path: "/"});
                <c:choose>
                    <c:when test="${empty form}">
                         window.location.href = "/order/provider_orders";
                    </c:when>
                    <c:otherwise>
                         window.location.href = "${form}";
                    </c:otherwise>
                </c:choose>
            } else {
                $.toptip(e.desc, 'error');
            }
        }).fail(function () {
            $.alert('网络错误。');
        });
    });

    $(function () {
        var sendinterval = 0;
        $('#send').click(function (e) {
            e.preventDefault();
            var self = $(this);
            if ($("#phone").val() && $("#verify").val()) {
                $.ajax({
                   url: "/user/register/verifycode",
                   dataType: "json",
                   data: {phone: $("#phone").val(), captcha: $("#verify").val()},
                   type: "post"
                }).done(function (e) {
                    if (e.code == 0) {
                        var num = 60;
                        sendinterval = setInterval(function () {
                            num--;
                            if (num == 0) {
                                clearInterval(sendinterval);
                                sendinterval = 0;
                                self.removeClass('on').html('重新发送');
                                return;
                            }
                            self.html('重新发送(' + num + ')');
                        }, 1000);
                        self.html('重新发送(' + num + ')');
                        $("#send").attr('disabled', "true");
                    }else{
                        $.alert(e.desc);
                    }
                }).fail(function () {
                    self.text('发送失败！');
                    self.removeClass('on');
                });
            } else {
                $.alert('请填写手机号码和图形验证码');
            }
        });

        $("#imgvcode").click(function () {
            $("#imgvcode").attr('src', "/user/captcha?_" + Math.random());
        });
        $("#imgvcode").trigger("click");

        $("#verify,#phone,#smsCode").keypress(function () {
            if ($("#verify").val() && $("#phone").val() && $("#smsCode").val()) {
                $("#sb").removeClass("am-disabled");
            } else {
                if (!$("#sb").hasClass("am-disabled")) {
                    $("#sb").addClass("am-disabled");
                }
            }
        })
    });
</script>

</html>
