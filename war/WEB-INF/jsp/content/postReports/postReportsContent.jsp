<%@ page  pageEncoding="UTF-8" %>

        	 <tr>
          		<td align="right" bgcolor="#787669" height="20">
           			<c:set var="applicationName" value="מערכת אינטרנט הרשות למו\"פ"/>
          	        <c:set var="pageName" value="דוחות מערכת דיוור"/>
       	          	<%@ include file="/WEB-INF/jsp/include/locationMenu.jsp" %>
    	      	 </td>
          	 </tr>
		</table>

    </td>
  </tr>
  <tr>
    <td>
      <table width="700" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#767468" dir="rtl">
        <tr>
          <td valign="top" align="center"><br>




              <table width="600" border="0" align="center" cellpadding="2" cellspacing="0">

				<tr>
			 		<th colspan="4" align="right">
			 		
			 	 השבוע נשלחו ${sentPostsCount} אימיילים.
			 	 &nbsp;  
			 		${notSentPostsCount} הודעות נכשלו בשליחה.
			 		</th>
				</tr>
			 	<tr>
			 		<td>
			 			&nbsp;
			 		</td>
			 	</tr>
			 	<tr>
			 		<th colspan="4">
			 			מספר רשומים כולל: ${subscribersCount}
			 		</th>
			 	</tr>
			 	<tr>
			 		<td>
			 			&nbsp;
			 		</td>
			 	</tr>
			 	<tr>
			 		<td colspan="4">
			 			<c:if test="$fullList">
							<a href="postReports.html">חזרה למסך הבקרה</a>
						</c:if>
					</td>
			 	</tr>
			 	<tr>
			 		<td>
			 			&nbsp;
			 		</td>
			 	</tr>
			 	<tr>
			 		<th colspan="3">
			 			<c:choose>
			 				<c:when test="$fullList">
								רשימת רשומים למערכת הדיוור
							</c:when>
							<c:otherwise>
					 			נרשמים אחרונים למערכת הדיוור
					 		</c:otherwise>
					 	</c:choose>
			 		</th>
			 	</tr>
			 	<tr>
			 		<td>
			 			&nbsp;
			 		</td>
			 	</tr>
			 	<c:forEach items="${subscribers}" var="person">
			 		<tr>
			 			<td>
			 				<a href="personPost.html?id=${person.id}" target="_blank">${person.degreeFullNameHebrew}</a>
			 			</td>
			 			<td>
			 				${person.civilId}
			 			</td>
			 			<td>
			 				${person.email}
			 			</td>
			 			<td>
			 				${person.lastLogin}
			 			</td>
			 		</tr>
			 	</c:forEach>
			 	<tr>
			 		<td>
			 			&nbsp;
			 		</td>
			 	</tr>
			 	<tr>
			 		<td colspan="4">
			 			<c:choose>
			 				<c:when test="$fullList">
								<a href="postReports.html">חזרה למסך הבקרה</a>
							</c:when>
							<c:otherwise>
						 		<a href="postReports.html?fl=1">לרשימה המלאה של הרשומים</a>
					 		</c:otherwise>
					 	</c:choose>

			 		</td>
			 	</tr>
			  </table>


			  <c:if test="${! empty subscribersNoSubjects}">
			  <br/>
			  <br/>
			  <table width="600" border="0" align="center" cellpadding="2" cellspacing="0">
			 	<tr>
			 		<th colspan="3">
			 			 ${subscribersNoSubjectsCount} רשומים ללא נושאים
			 		</th>
			 	</tr>
			 	<tr>
			 		<td>
			 			&nbsp;
			 		</td>
			 	</tr>
			 	<c:forEach items="${subscribersNoSubjects}" var="person">
			 		<tr>
			 			<td>
				 				<a href="personPost.html?id=${person.id}" target="_blank">${person.degreeFullNameHebrew}</a>
			 			</td>
			 			<td>
			 				${person.civilId}
			 			</td>
			 			<td>
			 				${person.email}
			 			</td>
			 			<td>
			 				${person.lastLogin}
			 			</td>
			 		</tr>
			 	</c:forEach>
			  </table>
			  </c:if>


			 
			  <c:if test="${! empty subscribersDisabled}">
			  <br/>
			  <br/>
			  <table width="600" border="0" align="center" cellpadding="2" cellspacing="0">
			 	<tr>
			 		<th colspan="3">
			 		${subscribersDisabledCount} 	בעיות רישום
			 		</th>
			 	</tr>
			 	<tr>
			 		<td>
			 			&nbsp;
			 		</td>
			 	</tr>
			 	<c:forEach items="${subscribersDisabled}" var="person">
			 		<tr>
			 			<td>
			 				<a href="personPost.html?id=${person.id}" target="_blank">${person.degreeFullNameHebrew}</a>
			 			</td>
			 			<td>
			 				${person.civilId}
			 			</td>
			 			<td>
			 				${person.email}
			 			</td>
			 			<td>
			 				${person.lastLogin}
			 			</td>
			 		</tr>
			 	</c:forEach>
			  </table>
			  </c:if>


			  <c:if test="${!fullList && ! empty openPosts}">
					<br/>
					<br/>
					<table width="800" border="0" align="center" cellpadding="2" cellspacing="0">
						<tr>
							<th colspan="2">
								${openPostsCount} פוסטים בתהליך שליחה
							</th>
						</tr>
						<tr>
							<td>
								&nbsp;
							</td>
						</tr>
						<c:forEach items="${openPosts}" var="post">
							<tr>
								<td>
									${post.messageSubject}
								</td>
								<td>
									${post.sendTime}
								</td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
				<c:if test="${!fullList}">
			  	<br/>
			  	<br/>
			  	<table width="800" border="0" align="center" cellpadding="2" cellspacing="0">
			  		<tr>
			  			<th colspan="2">
			  				שליפת רשומים על-פי נושאים
			  			</th>
			  		</tr>
			  		<tr>
			 			<td>
			 				&nbsp;
			 			</td>
			 		</tr>
			  		<tr>
			  			<td>
				  			<%@ include file="/WEB-INF/jsp/content/editPost/plainSubjects.jsp" %>
			  			</td>
			  			<td valign="top">
			  				<c:if test="${! empty subscribersSubject }">
								<table>
								<c:forEach items="${subscribersSubject}" var="person">
											<tr>
			 									<td>
									 				<a href="personPost.html?id=${person.id}" target="_blank">${person.degreeFullNameHebrew}</a>
			 									</td>
			 									<td>
			 										${person.civilId}
			 									</td>
			 									<td>
			 										${person.email}
			 									</td>
			 								</tr>
			 					</c:forEach>
			 					 </table>
			 				</c:if>
			 			</td>
			 		</tr>
			 	</table>
			 </c:if>
      </td>
  </tr>

</table>
<%@ include file="/WEB-INF/jsp/include/footer.jsp" %>


</body>
</html>
