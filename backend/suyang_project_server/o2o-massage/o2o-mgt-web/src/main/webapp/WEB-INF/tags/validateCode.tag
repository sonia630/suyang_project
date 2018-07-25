<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/webpage/include/taglib.jsp"%>
<%@ attribute name="name" type="java.lang.String" required="true" description="验证码输入框名称"%>
<%@ attribute name="inputCssStyle" type="java.lang.String" required="false" description="验证框样式"%>
<input type="text" id="${name}" name="${name}" maxlength="5" class="txt required" style="font-weight:bold;width:45px;${inputCssStyle}"/>
<img src="${pageContext.request.contextPath}/validateCodeService/gen" onclick="$('.${name}Refresh').click();" class="mid ${name}"/>
<a href="javascript:" onclick="$('.${name}').attr('src','${pageContext.request.contextPath}/validateCodeService/gen?'+new Date().getTime());" class="mid ${name}Refresh">看不清</a>