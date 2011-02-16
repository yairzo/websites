<c:choose>
<c:when test="${command.listView.firstPage==false}">
 <img class="rewindToFirst" src="image/rightArrowEnd.gif" border="0">
 <img class="rewindOnePage" src="image/rightArrow.gif" border="0">
</c:when>
<c:otherwise>
<img src="image/rightArrowEndFade.gif"> <img src="image/rightArrowFade.gif">
</c:otherwise>
</c:choose>

<c:forEach items="${command.listView.nearPages}" var="nearPage">
	<c:out value=" "/>
		<c:if test="${nearPage.pageNum==command.listView.page}"><font style="color: #b8b7b1"></c:if><span id="${nearPage.pageNum}" class="nearPage"><c:out value="${nearPage.display}"/></span><c:if test="${nearPage.pageNum==command.listView.page}"></font></c:if><c:out value=" "/>
</c:forEach>

<c:choose>
<c:when test="${command.listView.atLastPage==false}">
<img class="advanceOnePage" src="image/leftArrow.gif" border="0">
<img class="advanceToLast" src="image/leftArrowEnd.gif" border="0">
</c:when>
<c:otherwise>
<img src="image/leftArrowFade.gif">  <img src="image/leftArrowEndFade.gif">
</c:otherwise>
</c:choose>