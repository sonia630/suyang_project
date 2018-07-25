<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fns" uri="/tags/o2o-functions" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<head>
    <jsp:include page="../include/header.jsp"/>
    <style>
        .selected {
            background-color: #F4AB63;
        }
    </style>
</head>

<body>

<!-- Topbar -->
<div class="topbar">
    <ul class="am-g">
        <li class="am-u-sm-1" id="back"><i class="am-icon-angle-left am-icon-sm"></i></li>
        <li class="title am-text-center am-u-sm-10">选择被服务人员</li>
        <li class="am-u-sm-1 am-text-right"><i class="am-icon-ellipsis-v "></i></li>
    </ul>
</div>

<div class="select-serviced-user">
    <div class="top"><label>您要为哪位下单服务?</label>
        <button class="am-btn am-btn-primary am-btn-xs am-round am-fr" id="add"><i class="am-icon-plus"></i> 添加新成员</button>
    </div>
    <ul class="am-list userlist">
        <li class="am-g">
            <c:forEach items="${members}" var="member">
                <div class="user" mid="${member.memberId}">
                    <div class="am-u-sm-4 am-margin-top-sm">
                        <image src="/static/images/user.jpg" width="80px" height="80px"
                               class="am-img-thumbnail am-circle"></image>
                    </div>
                    <div class="am-u-sm-8">
                        <div class="name">${member.memberName}</div>
                        <div><span>${member.gender==1?"男":"女"}</span><span class="am-fr">${fns:computeAge(member.memberBirthday)}</span></div>
                    </div>
                </div>
            </c:forEach>
        </li>

        <div class="am-u-sm-5">

        </div>

    </ul>
</div>


<!-- commit -->
<div class="am-navbar navbar-button am-g">
    <div class="am-u-sm-12">
        <button id="next" class="am-btn am-btn-block am-btn-primary am-vertical-align-middle am-btn-lg">下 一 步</button>
    </div>
</div>

<jsp:include page="../include/js.jsp"/>
<script>
    $(function () {
       if(${fn:length(members)==0}){
           $("#next").attr('disabled', "true");
       }
       $(".user").click(function () {
           $(".user").removeClass("selected");
           $(this).addClass("selected");
       });

       $("#back").click(function () {
           window.location.href = "${from}?serviceId=${serviceId}";
       });

        $("#add").click(function () {
            window.location.href = "/customer/addMemberPage?from=${from}&serviceId=${serviceId}";
       });
        $("#next").click(function () {
            if ($(".selected").length==0) {
                $.alert('请选择被服务者');
                return;
            }
            window.location.href = "/customer/addSympton?from=${from}&serviceId=${serviceId}&memberId="+$(".selected").attr("mid");
        });
    });
</script>
</body>


</html>

