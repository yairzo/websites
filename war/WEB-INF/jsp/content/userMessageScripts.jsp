<%@ page  pageEncoding="UTF-8" %>
$.alerts.okButton = 'אישור';
$.alerts.cancelButton = 'ביטול';


<c:if test="${userMessage!=null}">
	var userMessage = "${userMessage}";
	userMessage = userMessage.replace(/'/,"\'");
	$.alerts.alert(userMessage,'<fmt:message key="iw_IL.eqfSystem.editProposal.alert.title"/>');
</c:if>