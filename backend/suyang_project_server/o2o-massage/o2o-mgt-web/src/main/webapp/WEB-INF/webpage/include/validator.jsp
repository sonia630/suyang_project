<%@ page contentType="text/html;charset=UTF-8" %>
<link rel="stylesheet" href="/static/js/plugin/jquery-validate/css/screen.css">
<script src="/static/js/plugin/jquery-validate/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="/static/js/plugin/jquery-validate/js/messages_zh.js" type="text/javascript"></script>
<script>
    $(document).ready(function () {
        $.validator.addMethod("rate1", function (value, element) {
            var numbers = /^-?[0-9]+\.[0-9]{1,1}$/;
            return numbers.test(value);
        }, "需保留一位小数");
        $.validator.addMethod("rate2", function (value, element) {
            var numbers = /^-?[0-9]+\.[0-9]{2,2}$/;
            return numbers.test(value);
        }, "需保留两位小数");
        $.validator.addMethod("rate3", function (value, element) {
            var numbers = /^-?[0-9]+\.[0-9]{3,3}$/;
            return numbers.test(value);
        }, "需保留三位小数");
        $.validator.addMethod("rate4", function (value, element) {
            var numbers = /^-?[0-9]+\.[0-9]{4,4}$/;
            return numbers.test(value);
        }, "需保留四位小数");
    });
</script>