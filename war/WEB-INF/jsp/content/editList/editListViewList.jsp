<%@ page  pageEncoding="UTF-8" %>
<tr>
	<td colspan="3" align="center">
		<h1>צפייה וסידור רשימה </h1>
    </td>
</tr>
<tr>
<c:if test="${command.editableAttribution}">
	<td>
		<button class="grey" onclick="window.location='listOrder.html?id=<c:out value="${command.id}"/>'; return false;">סדר רשימה </button></a>
	</td>
</c:if>
	<td>
		<button class="grey" onclick="window.location='viewList.html?id=<c:out value="${command.id}"/>'; return false;">צפייה ברשימה</button></a>
	</td>
</tr>