<%@ page  pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
</table>
   </td>
  </tr>

</table>

<center><h1>מאמרים</h1></center>
<div class="articleTable">
	<table width="80%" border="1" align="center" cellpadding="1" cellspacing="0" style="direction:rtl">
		<%-- Title Row --%>
		<tr  class="darker">
			<td><b>#</b></td>
			<td><b>כותרת</b></td>
			<td><b>מחברים</b></td>
			<td><b>תאריך פרסום</b></td>
			<td><b>הצג באתר?</b></td>
		</tr>
		<c:forEach items="${listOfArticles}" var="article" varStatus="line">
			<tr class=<c:choose><c:when test="${(line.index % 2) == 0 }">"darker"</c:when><c:otherwise>"brighter"</c:otherwise></c:choose>>
				<td>${line.index}</td>
				<td>
					<%-- Articles Column --%>
					<a href="http://${article.pathname}">
						${article.title}
					</a>
				</td>
				<td>
					<%-- Authors Column --%>
					<c:forEach items="${article.authors}" var="author" varStatus="status">
						<a href="person.html?id=${author.id}">
							${author.firstNameHebrew} ${author.lastNameHebrew}
						</a>
						<c:if test="${!status.last}" >, </c:if>
					</c:forEach>
				</td>
				<td>
					<%-- Publication Date Column --%>
					${article.humanPublicationDate}
				</td>
				<td>
					<%-- Visible/Hidden Column --%>
					<input type="checkbox" name="${article.id}" onclick="checkThis(this);" <c:if test="${article.visible == true}">checked</c:if>/>
				</td>
			</tr>
		</c:forEach>
	</table>
</div>

	
 
   
<%@ include file="/WEB-INF/jsp/include/footer.jsp" %>



</body>
</html>
