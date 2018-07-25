<%@ page contentType="text/html;charset=UTF-8" %>
<script src="/static/plugin/jquery.min.js"></script>
<script src="/static/plugin/amazeui/js/amazeui.min.js"></script>
<script type="text/javascript" src="/static/js/jquery.cookie.js"></script>
<script type="text/javascript" src="/static/plugin/jquery-weui/js/jquery-weui.js"></script>
<script src="/static/js/fastclick.js"></script>
<script type="text/javascript">
    var Template = {
        selectMenu: function (menu) {
            if (menu) {
                $(".footer").find("li").removeClass("active");
                $(menu).addClass("active");
            }
        },
        init: function (menu) {
            Template.selectMenu(menu);
        }
    }

    $(document).ready(function () {
        FastClick.attach(document.body);
    });
</script>