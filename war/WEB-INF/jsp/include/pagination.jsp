<c:choose>
<c:when test="${listView.firstPage==false}">
<a href="?orderBy=<c:out value="${listView.orderBy}"/>&page=<c:out value="1"/>"><img src="/image/rightArrowEnd.gif" border="0"></a>
<a href="?orderBy=<c:out value="${listView.orderBy}"/>&page=<c:out value="${listView.page-1}"/>"><img src="/image/rightArrow.gif" border="0"></a>
</c:when>
<c:otherwise>
<img src="/image/rightArrowEndFade.gif" border="0">  <img src="/image/rightArrowFade.gif" border="0">
</c:otherwise>
</c:choose>

<c:forEach items="${listView.nearPages}" var="nearPage">
	<c:out value=" "/><a style="text-decoration: none" href="?orderBy=<c:out value="${listView.orderBy}"/>&page=<c:out value="${nearPage.pageNum}"/>">
		<c:if test="${nearPage.pageNum==listView.page}"><font style="color: #b8b7b1"></c:if><c:out value="${nearPage.display}"/></a><c:if test="${nearPage.pageNum==listView.page}"></font></c:if><c:out value=" "/>
</c:forEach>

<c:choose>
<c:when test="${listView.lastPage==false}">
<a href="?orderBy=<c:out value="${listView.orderBy}"/>&page=<c:out value="${listView.page+1}"/>"><img src="/image/leftArrow.gif" border="0"></a>
<a href="?orderBy=<c:out value="${listView.orderBy}"/>&page=<c:out value="${listView.lastPage}"/>"><img src="/image/leftArrowEnd.gif" border="0"></a>
</c:when>
<c:otherwise>
<img src="/image/leftArrowFade.gif" border="0">  <img src="/image/leftArrowEndFade.gif" border="0">
</c:otherwise>
</c:choose>