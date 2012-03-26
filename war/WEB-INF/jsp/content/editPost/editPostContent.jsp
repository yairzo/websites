<%@ page  pageEncoding="UTF-8" %>



<tr>
	<td align="right" bgcolor="#787669" height="20">
		<c:set var="applicationName" value="מערכת דיוור"/>
          	        <c:set var="pageName" value="עריכת הודעה"/>
       	          	<%@ include file="/WEB-INF/jsp/include/locationMenu.jsp" %>
	</td>
</tr>
</table>

</td>
</tr>

  	
 	<c:choose>
	<c:when test="${command.verified}">
		<%@ include file="/WEB-INF/jsp/content/editPost/viewPost.jsp" %>
	</c:when>
	<c:otherwise>
		<authz:authorize ifAnyGranted="ROLE_POST_ADMIN,ROLE_POST_CREATOR">
		<%@ include file="/WEB-INF/jsp/content/editPost/editPost.jsp" %>
		</authz:authorize>
	</c:otherwise>
	</c:choose>

</table>


</body>
</html>
