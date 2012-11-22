<c:choose>
<c:when test="${command.listView.firstPage==false}">
 <img class="rewindToFirst pagination" src="image/rightArrowEnd.gif" border="0">
 <img class="rewindOnePage pagination" src="image/rightArrow.gif" border="0">
</c:when>
<c:otherwise>
<img class="pagination" src="image/rightArrowEndFade.gif"> <img class="pagination" src="image/rightArrowFade.gif">
</c:otherwise>
</c:choose>

<c:forEach items="${command.listView.nearPages}" var="nearPage">
	<c:out value=" "/>
		<c:if test="${nearPage.pageNum==command.listView.page}"><font style="color: #383838 "><b></c:if><span id="${nearPage.pageNum}" class="nearPage pagination"><c:out value="${nearPage.display}"/></span><c:if test="${nearPage.pageNum==command.listView.page}"></b></font></c:if><c:out value=" "/>
</c:forEach>

<c:choose>
<c:when test="${command.listView.atLastPage==false}">
<img class="advanceOnePage pagination" src="image/leftArrow.gif" border="0">
<img class="advanceToLast pagination" src="image/leftArrowEnd.gif" border="0">
</c:when>
<c:otherwise>
<img class="pagination" src="image/leftArrowFade.gif">  <img class="pagination" src="image/leftArrowEndFade.gif">
</c:otherwise>
</c:choose>