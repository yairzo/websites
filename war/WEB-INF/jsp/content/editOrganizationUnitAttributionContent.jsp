<%@ page  pageEncoding="UTF-8" %>

         <tr>
          <td align="right" bgcolor="#787669" height="20">
          	<c:set var="applicationName" value="מערכת רשימות"/>
          	<c:set var="pageName" value="שיוך יחידה אירגונית לרשימה"/>
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
            <form:form method="POST" action="organizationUnitAttribution.html" commandName="command">
				<input type="hidden" name="cp" value="${cp}"/>
				<input type="hidden" name="cpoi" value="${cpoi}"/>
				<form:hidden path="organizationUnitId"/>


              <table width="250" border="0" align="center" cellpadding="2" cellspacing="0">

              <c:choose>
              <c:when test="${aNewAttribution}">
    				<tr>
    				<td align="right">
								בחר\י רשימה לשייך אליה את ${command.organizationUnit.nameHebrew}:
							</td>
							<td colspan="2" align="right">
								<form:select cssClass="green" id="listSelect" path="listId">
									<form:option value="0">בחר\י רשימה</form:option>
										<c:forEach items="${lists}" var="list">
											<form:option value="${list.id}"><c:out value="${list.name}"/></form:option>
										</c:forEach>
								</form:select>
							</td>
						</tr>
						<tr>
	              			<td colspan="2">
	              				<button class="grey save">שמור</button>
								<button class="grey cancel">סיים</button>
							</td>
						</tr>
			   </c:when>
			   <c:otherwise>
			   		<tr>
			   			<td>
					   		בשלב זה אין צורך בעריכת פרטי השיוך לרשימה
					   	</td>
					 </tr>
					 <tr>
	              		<td colspan="2">
	              			<button class="grey cancel">סיים</button>
						</td>
					</tr>
					</c:otherwise>
			   	</c:choose>
			 </table>
		</form:form>
	</td>
	</tr>
	</table>
</td>
</tr>
</table>
</html>





