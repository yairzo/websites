
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>

<c:if test="${view=='full'}">
<c:set var="templateScripts" value="/WEB-INF/jsp/website/callForProposalScripts.jsp"/>
<c:set var="templateBody" value="/WEB-INF/jsp/website/callForProposalContent.jsp"/>
<%@ include file="/WEB-INF/jsp/website/generalTemplate.jsp" %>
</c:if>


<%@ include file="/WEB-INF/jsp/website/callForProposalScripts.jsp" %>
<%@ include file="/WEB-INF/jsp/website/callForProposalContent.jsp" %>
