<%@ tag body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="well" tagdir="/WEB-INF/tags" %>
<%@ attribute name="label" required="true" %>
<%-- onclick and href are mutually exclusive --%>
<%@ attribute name="onclick" %>
<%@ attribute name="href" %>
<%@ attribute name="labelStyle" %>
<%@ attribute name="labelType" %>
<%@ attribute name="clickNoFalse" %>
<%@ attribute name="tabindex" type="java.lang.Integer" %>

<c:choose>
	<c:when test="${clickNoFalse != null}">
<table border=0 cellspacing=0 cellpadding=0<c:if test="${labelStyle != null}"> style="${labelStyle}"</c:if>><tr><td class="button20blue">
<a <c:if test="${href != null}">href="${href}"</c:if><c:if test="${onclick != null}">href="#" onclick="${onclick != null ? onclick : ''};"</c:if> class="${labelType}" ${tabindex != null ? 'tabindex="' : ''}${tabindex != null ? tabindex : ''}${tabindex != null ? '"' : ''}><div><div><div><b>${label}</b></div></div></div><div class="cbtnb"><div><div></div></div></div></a>
</td></tr></table>
	</c:when>
	<c:otherwise>
<table border=0 cellspacing=0 cellpadding=0<c:if test="${labelStyle != null}"> style="${labelStyle}"</c:if>><tr><td class="button20blue">
<a <c:if test="${href != null}">href="${href}"</c:if><c:if test="${onclick != null}">href="#" onclick="${onclick != null ? onclick : ''}; return false"</c:if> class="${labelType}" ${tabindex != null ? 'tabindex="' : ''}${tabindex != null ? tabindex : ''}${tabindex != null ? '"' : ''}><div><div><div><b>${label}</b></div></div></div><div class="cbtnb"><div><div></div></div></div></a>
</td></tr></table>
	</c:otherwise>
</c:choose>