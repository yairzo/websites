<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>

			
<div class="side sidelinks">
	<h3 style="background:#04bde5 url(image/website/icon_links_${lang.dir}.png) no-repeat <c:choose><c:when test="${lang.rtl}">210px</c:when><c:otherwise>20px</c:otherwise></c:choose>"><fmt:message key="${lang.localeId}.website.links"/></h3>
	<ul>
		<li class="link_research"><a href="textualPage.html?id=<c:choose><c:when test="${lang.rtl}">2083</c:when><c:otherwise>2093</c:otherwise></c:choose>"><fmt:message key="${lang.localeId}.website.researchSystem"/></a></li>
		<li class="link_mail"><a href="textualPage.html?id=<c:choose><c:when test="${lang.rtl}">2034</c:when><c:otherwise>1896</c:otherwise></c:choose>"><fmt:message key="${lang.localeId}.website.listserv"/></a></li>
		<li class="link_guidelines"><a href="textualPage.html?id=<c:choose><c:when test="${lang.rtl}">1879</c:when><c:otherwise>2092</c:otherwise></c:choose>"><fmt:message key="${lang.localeId}.website.submissionGuidelines"/></a></li>
		<li class="link_grant"><a href="textualPage.html?id=1881"><fmt:message key="${lang.localeId}.website.grantGuidelines"/></a></li>
		<li class="link_budget"><a href="https://www.huji.ac.il/dataj/controller/finance/"><fmt:message key="${lang.localeId}.website.personalBudget"/></a></li>
		<li class="link_application"><a href="http://www.yissum.co.il/"><fmt:message key="${lang.localeId}.website.yissum"/></a></li>
	</ul>
</div>
