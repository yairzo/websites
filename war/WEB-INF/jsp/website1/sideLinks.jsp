<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>

			
<div class="side sidelinks">
	<h3 style="background:#04bde5 url(image/website1/icon_links_${lang.dir}.png) no-repeat <c:choose><c:when test="${lang.rtl}">210px</c:when><c:otherwise>20px</c:otherwise></c:choose>"><fmt:message key="${lang.localeId}.website.links"/></h3>
	<ul>
		<li class="link_research"><a href="#"><fmt:message key="${lang.localeId}.website.researchSystem"/></a></li>
		<li class="link_budget"><a href="#"><fmt:message key="${lang.localeId}.website.personalBudget"/></a></li>
		<li class="link_mail"><a href="#"><fmt:message key="${lang.localeId}.website.listserv"/></a></li>
		<li class="link_guidelines"><a href="#"><fmt:message key="${lang.localeId}.website.submissionGuidelines"/></a></li>
		<li class="link_grant"><a href="#"><fmt:message key="${lang.localeId}.website.grantGuidelines"/></a></li>
		<li class="link_application"><a href="#"><fmt:message key="${lang.localeId}.website.yissum"/></a></li>
	</ul>
</div>
