<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/webpage/include/taglib.jsp"%>
<%@ attribute name="provinces" type="java.util.Collection" required="true" description="省份" %>
<%@ attribute name="cities" type="java.util.Collection" required="true" description="城市" %>
<%@ attribute name="areas" type="java.util.Collection" required="true" description="地区" %>
<%@ attribute name="province_name" type="java.lang.String" required="true" description="省份name" %>
<%@ attribute name="city_name" type="java.lang.String" required="true" description="城市name" %>
<%@ attribute name="area_name" type="java.lang.String" required="true" description="地区name" %>
<%@ attribute name="province_value" type="java.lang.String" description="省份值" %>
<%@ attribute name="city_value" type="java.lang.String" description="城市值" %>
<%@ attribute name="area_value" type="java.lang.String" description="地区值" %>
<%@ attribute name="province_required" type="java.lang.String" description="省份必须" %>
<%@ attribute name="city_required" type="java.lang.String" description="城市必须" %>
<%@ attribute name="area_required" type="java.lang.String" description="地区必须" %>
<%@ attribute name="province_disabled" type="java.lang.Boolean" description="省份disable" %>
<%@ attribute name="city_disabled" type="java.lang.Boolean" description="城市disable" %>
<%@ attribute name="area_disabled" type="java.lang.Boolean" description="地区disable" %>
<style>
    .col-select {
        box-sizing: border-box;
        float: left;
        min-height: 1px;
    }
</style>

<c:choose>
    <c:when test="${empty province_required}">
        <c:set var="province_required" value="required"/>
    </c:when>
    <c:otherwise>
        <c:set var="province_required" value=""/>
    </c:otherwise>
</c:choose>

<c:choose>
    <c:when test="${empty city_required}">
        <c:set var="city_required" value="required"/>
    </c:when>
    <c:otherwise>
        <c:set var="city_required" value=""/>
    </c:otherwise>
</c:choose>

<c:choose>
    <c:when test="${empty area_required}">
        <c:set var="area_required" value="required"/>
    </c:when>
    <c:otherwise>
        <c:set var="area_required" value=""/>
    </c:otherwise>
</c:choose>

<section class="col-select">
    <label class="control-label">区域:</label>
    <label class="select <c:if test="${province_disabled}">state-disabled</c:if>">
        <select name="${province_name}" class="combox" ref="w_combox_city" refUrl="${ctx}/region/city"
        ${province_required} <c:if test="${province_disabled}">disabled="disabled"</c:if>>
            <c:if test="${provinces == null }">
                <option value="">所有省市</option>
            </c:if>
            <c:forEach items="${provinces}" var="pro">
                <c:choose>
                    <c:when test="${pro.code !=null && pro.code eq province_value}">
                        <option value="${pro.code}" selected="selected">${pro.name}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${pro.code}">${pro.name}</option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select> <i></i>
    </label>
</section>
<section class="col-select" style="padding-left: 15px;">
    <label class="control-label"></label>
    <label class="select <c:if test="${city_disabled}">state-disabled</c:if>">
        <select name="${city_name}" class="combox" id="w_combox_city" ref="w_combox_area"
                refUrl="${ctx}/region/area" ${city_required} <c:if test="${city_disabled}">disabled="disabled"</c:if>>
            <c:if test="${cities == null }">
                <option value="">所有城市</option>
            </c:if>
            <c:forEach items="${cities}" var="city">
                <c:choose>
                    <c:when test="${city.code!=null && city.code eq city_value}">
                        <option value="${city.code}" selected="selected">${city.name}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${city.code}">${city.name}</option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select> <i></i>
    </label>
</section>
<section class="col-select" style="padding-left: 15px;">
    <label class="control-label"></label>
    <label class="select <c:if test="${area_disabled}">state-disabled</c:if>">
        <select class="combox" id="w_combox_area" name="${area_name}" ${area_required}
                <c:if test="${area_disabled}">disabled="disabled"</c:if>>
            <c:if test="${areas == null }">
                <option value="">所有区县</option>
            </c:if>
            <c:forEach items="${areas}" var="area">
                <c:choose>
                    <c:when test="${area.code!=null && area.code eq area_value}">
                        <option value="${area.code}" selected="selected">${area.name}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${area.code}">${area.name}</option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select> <i></i>
    </label>
</section>