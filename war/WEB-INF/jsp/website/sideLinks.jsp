<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>

			
<div class="side sidelinks">
	<h3 style="background:#04bde5 url(/image/website/icon_links_${lang.dir}.png) no-repeat <c:choose><c:when test="${lang.rtl}">210px</c:when><c:otherwise>20px</c:otherwise></c:choose>"><fmt:message key="${lang.localeId}.website.links"/></h3>
	<ul>
		<li class="link_research"><a href="/page/<c:choose><c:when test="${lang.rtl}">Research_System_Hebrew</c:when><c:otherwise>Uploading_Proposals_To_Research_System</c:otherwise></c:choose>"><fmt:message key="${lang.localeId}.website.researchSystem"/></a></li>
		<li class="link_mail"><a href="/page/<c:choose><c:when test="${lang.rtl}">Mailing_List_Hebrew</c:when><c:otherwise>Mailing_List_English</c:otherwise></c:choose>"><fmt:message key="${lang.localeId}.website.listserv"/></a></li>
		<li class="link_guidelines"><a href="/page/<c:choose><c:when test="${lang.rtl}">Applying_For_Research_Gratns_Hebrew</c:when><c:otherwise>Submission_Guidelines</c:otherwise></c:choose>"><fmt:message key="${lang.localeId}.website.submissionGuidelines"/></a></li>
		<li class="link_grant"><a href="/page/<c:choose><c:when test="${lang.rtl}">Grants_Guidelines_Hebrew</c:when><c:otherwise>Grants_Guidelines</c:otherwise></c:choose>"><fmt:message key="${lang.localeId}.website.grantGuidelines"/></a></li>
		<li class="link_budget"><a href="#" onclick="window.open('https://www.huji.ac.il/dataj/controller/finance/')"><fmt:message key="${lang.localeId}.website.personalBudget"/></a></li>
		<li class="link_application"><a href="http://www.yissum.co.il/"><fmt:message key="${lang.localeId}.website.yissum"/></a></li>
	</ul>
</div>
