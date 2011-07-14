<%@ page  pageEncoding="UTF-8" %>

	    	<tr>
          	<td align="right" bgcolor="#787669" height="20">
          		<c:set var="applicationName" value="מערכת רשימות"/>
          	        <c:set var="pageName" value="עריכת רשימה"/>
       	          	<%@ include file="/WEB-INF/jsp/include/locationMenu.jsp" %>
          	</td>
        	</tr>
		</table>
	</td>
</tr>
<tr>
    <td>
      <table width="800" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#767468" dir="rtl">
        <tr>
          <td valign="top" align="center"><br>
            <form:form id="form" name="form" action="list.html" method="POST" commandName="command">

				<form:hidden path="id"/>

            <table width="700" border="0" align="center" cellpadding="2" cellspacing="0">
                <tr>
                  <td colspan="3" align="center"><h1>עריכת פרטי רשימה</h1>
                  </td>
                </tr>
                <tr class="form">
					<td width="250">
						שם רשימה:
					</td>
					<td width="300">
						<authz:authorize ifAnyGranted="ROLE_LISTS_ADMIN">
							<form:input htmlEscape="true" cssClass="green" path="name"/>
						</authz:authorize>
						<authz:authorize ifNotGranted="ROLE_LISTS_ADMIN">
							<c:out value="${command.name}"/>
						</authz:authorize>
					</td>
					<td width="150">
						&nbsp;
						<form:errors cssClass="errors" path="name"/>
					</td>
				</tr>
				<tr>
				<td width="250">
						הקדמה:
					</td>
					<td width="300">
						<authz:authorize ifAnyGranted="ROLE_LISTS_ADMIN">
							<form:textarea htmlEscape="true" cssClass="green" path="preface" cols="50" rows="7"/>
						</authz:authorize>
						<authz:authorize ifNotGranted="ROLE_LISTS_ADMIN">
							<c:out escapeXml="false" value="${command.preface}"/>
						</authz:authorize>
					</td>
					<td width="150">
						&nbsp;
						<form:errors cssClass="errors" path="preface"/>
					</td>
				</tr>
				<td width="250">
						אחרית דבר:
					</td>
					<td width="300">
						<authz:authorize ifAnyGranted="ROLE_LISTS_ADMIN">
							<form:textarea htmlEscape="true" cssClass="green" path="footer" cols="50" rows="7"/>
						</authz:authorize>
						<authz:authorize ifNotGranted="ROLE_LISTS_ADMIN">
							<c:out escapeXml="false" value="${command.footer}"/>
						</authz:authorize>
					</td>
					<td width="150">
						&nbsp;
						<form:errors cssClass="errors" path="footer"/>
					</td>
				</tr>

				<tr>
				<td width="250">
						שם בתצוגה:
					</td>
					<td width="300">
						<authz:authorize ifAnyGranted="ROLE_LISTS_ADMIN">
							<form:textarea htmlEscape="true" cssClass="green" path="displayName" cols="50" rows="7"/>
						</authz:authorize>
						<authz:authorize ifNotGranted="ROLE_LISTS_ADMIN">
							<c:out escapeXml="false" value="${command.displayName}"/>
						</authz:authorize>
					</td>
					<td width="150">
						&nbsp;
						<form:errors cssClass="errors" path="displayName"/>
					</td>
				</tr>
				<tr class="form">
					<td>
						שם התצוגה ייושר לצד, לפי כיוון השפה:
					</td>
					<td>
						<form:checkbox cssClass="green" path="displayNameAligned"/>
					</td>
					<td>
						<form:errors cssClass="errors" path="displayNameAligned"/>
					</td>
				</tr>
				<tr class="form">
					<td>
						יוצר הרשימה:
					</td>
					<td>
						<authz:authorize ifAnyGranted="ROLE_LISTS_ADMIN">
							<form:input htmlEscape="true" cssClass="green" path="owner"/>
						</authz:authorize>
						<authz:authorize ifNotGranted="ROLE_LISTS_ADMIN">
							<c:out value="${command.owner}"/>
						</authz:authorize>
					</td>
					<td>
						<form:errors cssClass="errors" path="owner"/>
					</td>
				</tr>
				<authz:authorize ifAnyGranted="ROLE_LISTS_ADMIN">
				<tr class="form">
					<td>
						אפשר שליחת מייל מקובץ לאנשי הרשימה:
					</td>
					<td>
						<form:checkbox cssClass="green" path="sendMailToListEnabled"/>
					</td>
					<td>
						<form:errors cssClass="errors" path="sendMailToListEnabled"/>
					</td>
				</tr>
				<tr class="form">
					<td>
						אפשר מיון:
					</td>
					<td>
						<form:checkbox cssClass="green" path="sortEnabled"/>
					</td>
					<td>
						<form:errors cssClass="errors" path="sortEnabled"/>
					</td>
				</tr>
				<tr class="form">
					<td>
						אפשר גישה לכל המשתמשים הרשומים:
					</td>
					<td>
						<form:checkbox cssClass="green" path="public"/>
					</td>
					<td>
						<form:errors cssClass="errors" path="public"/>
					</td>
				</tr>

				<tr class="form">
					<td>
						הרשימה פתוחה לציבור ללא צורך בזיהוי
					</td>
					<td>
						<form:checkbox cssClass="green" path="open"/>
					</td>
					<td>
						<form:errors cssClass="errors" path="open"/>
					</td>
				</tr>

				<tr class="form">
					<c:choose>
					<c:when test="${command.id==0}">

					<td>
						:הרשימה היא רשימה מורכבת
					</td>

					<td>
						<form:checkbox cssClass="green" path="compound"/>
					</td>
					<td>
						<form:errors cssClass="errors" path="compound"/>
					</td>
					</c:when>
					<c:otherwise>
						<td colspan="3"> <c:choose><c:when test="${command.compound}">הרשימה הינה מורכבת </c:when>
						<c:otherwise>הרשימה היא בסיסית</c:otherwise></c:choose></td>

					</c:otherwise>
					</c:choose>
				</tr>

				<tr class="form">
					<td>
						סוג הרשימה:
					</td>
					<td>
						<form:select cssClass="green" path="listTypeId">
							<form:option value="0">בחר/י סוג רשימה</form:option>
							<c:forEach items="${listTypes}" var="listType">
								<form:option value="${listType.id}"> ${listType.display}</form:option>
							</c:forEach>
						</form:select>
					</td>
					<td>
						<form:errors cssClass="errors" path="public"/>
					</td>
				</tr>

				<tr class="form">
					<td>
						עדכון אחרון:
					</td>
					<td>
						<c:out value="${command.lastUpdate}"/>
					</td>
					<td>
						<form:errors cssClass="errors" path="lastUpdate"/>
					</td>
				</tr>

		        <tr>
   		          <td colspan="2" align="right">
           			<table border="0" cellpadding="0" cellspacing="0">
                      <tr>
  			            <td><button class="grey" onclick="onSave();">שמור</button></td>
  			            <td><button class="cancel grey">סיים</button>
                      </tr>
                    </table>
   			     </td>
               </tr>


         </authz:authorize>


		<c:choose>
	      	<c:when test="${command.compound}">
				<%@ include file="/WEB-INF/jsp/content/editList/editListEditSublists.jsp" %>
	      	</c:when>
	      	<c:otherwise>


         <c:if test="${fn:length(listInstructions)>0}">


			<%@ include file="/WEB-INF/jsp/content/editList/editListInstructions.jsp" %>

			<%@ include file="/WEB-INF/jsp/content/editList/editListViewList.jsp" %>

			<%@ include file="/WEB-INF/jsp/content/editList/editListColumnsInstructions.jsp" %>


		</c:if>

		</c:otherwise>
		</c:choose>


            </table>
            <br>
          </td>
        </tr>
      </table>

      </form:form>
    </td>
  </tr>

</table>


</body>
</html>