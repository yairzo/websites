<%@ page  pageEncoding="UTF-8" %>


<tr>
          <td align="right" bgcolor="#787669" height="20">
          	<c:set var="applicationName" value="מערכת רשימות "/>
          	        <c:set var="pageName" value="עריכת שיוך אישיות לרשימה"/>
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
            <form:form id="form" name="form" action="personAttribution.html" method="POST" commandName="command" enctype="multipart/form-data">

				<form:hidden path="id"/>

				<form:hidden path="personId"/>

				<form:hidden path="listId"/>

				<form:hidden path="placeInList"/>

				<input type="hidden" name="cp" value="${cp}"/>
				<input type="hidden" name="cpoi" value="${cpoi}"/>

              <table width="600" border="0" align="center" cellpadding="2" cellspacing="0">

                <tr>
                  <td colspan="3" align="center"><h1>עריכת שיוך איש לרשימה</h1>
                  </td>
                </tr>

                <tr>
					<c:choose>
						<c:when test="${!listChosen}">
							<td align="right">
								בחר\י רשימה לשייך אליה את <c:out value="${command.person.firstNameHebrew}"></c:out>&nbsp;<c:out value="${command.person.lastNameHebrew}"></c:out>:
							</td>
							<td colspan="2" align="right">
								<form:select cssClass="green" id="listSelect" path="list.id" onchange="reloadPage()">
									<c:if test="${!listChosen}"><form:option value="0">בחר\י רשימה</form:option></c:if>
										<c:forEach items="${lists}" var="list">
											<form:option value="${list.id}"><c:out value="${list.name}"/></form:option>
										</c:forEach>
								</form:select>
							</td>
					</c:when>
					<c:otherwise>
						<th colspan="3" align="right">
							עריכת השיוך של <a href="/person.html?id=${command.person.id}"><c:out value="${command.person.firstNameHebrew}"></c:out>&nbsp;<c:out value="${command.person.lastNameHebrew}"></c:out></a> לרשימת <a href="list.html?id=${command.list.id}"><c:out value="${command.list.name}"/></a>
						</th>
					</c:otherwise>
				</c:choose>
			</tr>
<c:if test="${command.id > 0 || listChosen}">


	<tr>
				<td width="150">
					קשר נתונים לפרטים האישיים:
				</td>
				<td width="120">
					<form:checkbox id="connectDetails" cssClass="green" path="connectDetails" />
				</td>
				<td width="330">
					<form:errors path="connectDetails"/>
				</td>
			</tr>
	<c:if test="${! command.connectDetails}">
	<tr>
		<td colspan="3">
			השדות המופיעים כאן הם אלו הרלוונטים לרשימה זו:
		</td>
	</tr>
	</c:if>



	<c:set var="atLeastOneFieldToFill" value="false"></c:set>
	<c:set var="field" value="title"></c:set>
	<c:forEach items="${columns}" var="column">
		<c:if test="${column==field}">
			<c:set var="atLeastOneFieldToFill" value="true"></c:set>
			<tr>
				<td width="150">
					תפקיד:
				</td>
				<td width="120">
					<form:input  cssClass="green" path="title"/>
				</td>
				<td width="330">
					<form:errors path="title"/>
				</td>
			</tr>
		</c:if>
	</c:forEach>
	<c:set var="field" value="titleId"></c:set>
	<c:forEach items="${columns}" var="column">
		<c:if test="${column==field}">
		<tr>
				<td width="150">
				הגדרת תפקיד:
				</td>
				<td width="120">
					<form:select cssClass="green" id="titleIdSelect" path="titleId">
						<form:option value="0">בחר\י הגדרת תפקיד</form:option>
							<c:forEach items="${titles}" var="title">
								<form:option value="${title.id}"><c:out value="${title.name}"/></form:option>
							</c:forEach>
					</form:select>
				</td>
				<td width="330">
					<form:errors path="titleId"/>
				</td>
			</tr>
		</c:if>
		</c:forEach>

	<c:if test="${! command.connectDetails}">

	<c:set var="field" value="email"></c:set>
	<c:forEach items="${columns}" var="column">
		<c:if test="${column==field}">
			<c:set var="atLeastOneFieldToFill" value="true"></c:set>
			<tr>
				<td width="150">
					דואל:
				</td>
				<td width="120">
					<form:input cssClass="green"  path="email"/>
				</td>
				<td width="330">
					<form:errors path="email"/>
				</td>
			</tr>
		</c:if>
	</c:forEach>
	<c:set var="field" value="phone"></c:set>
	<c:forEach items="${columns}" var="column">
		<c:if test="${column==field}">
			<c:set var="atLeastOneFieldToFill" value="true"></c:set>
			<tr>
				<td width="150">
					טלפון:
				</td>
				<td width="120">
					<form:input cssClass="green"  path="phone"/>
				</td>
				<td width="330">
					<form:errors path="phone"/>
				</td>
			</tr>
		</c:if>
	</c:forEach>
	<c:set var="field" value="fax"></c:set>
	<c:forEach items="${columns}" var="column">
		<c:if test="${column==field}">
			<c:set var="atLeastOneFieldToFill" value="true"></c:set>
			<tr>
				<td width="150">
					פקס:
				</td>
				<td width="120">
					<form:input cssClass="green"  path="fax"/>
				</td>
				<td width="330">
					<form:errors path="fax"/>
				</td>
			</tr>
		</c:if>
	</c:forEach>
	<c:set var="field" value="address"></c:set>
	<c:forEach items="${columns}" var="column">
		<c:if test="${column==field}">
			<c:set var="atLeastOneFieldToFill" value="true"></c:set>
			<tr>
				<td width="150">
					כתובת:
				</td>
				<td width="120">
					<form:input htmlEscape="true" cssClass="green"  path="address"/>
				</td>
				<td width="330">
					<form:errors path="address"/>
				</td>
			</tr>
		</c:if>
	</c:forEach>
	<c:set var="field" value="department"></c:set>
	<c:forEach items="${columns}" var="column">
		<c:if test="${column==field}">
			<c:set var="atLeastOneFieldToFill" value="true"></c:set>
			<tr>
				<td width="150">
					יחידה:
				</td>
				<td width="120">
					<form:input htmlEscape="true" cssClass="green"  path="department"/>
				</td>
				<td width="330">
					<form:errors path="department"/>
				</td>
			</tr>
		</c:if>
	</c:forEach>
	<c:set var="field" value="imageUrl"></c:set>
	<c:forEach items="${columns}" var="column">
		<c:if test="${column==field}">
			<c:set var="atLeastOneFieldToFill" value="true"></c:set>
			<tr>
				<td width="150">
					תמונה:
				</td>
				<td width="120">
					<c:if test="${fn:length(command.imageUrl)>0}"><img src="/imageViewer?urlTitle=${command.imageUrl}&amp;attachType=bodyImage" height="50px" width="50px"/></c:if>
					<input class="green" type="file" name="image" size="30"/>
				</td>
			</tr>
		</c:if>
	</c:forEach>
	
	<c:set var="field" value="areaOfSpecialization"></c:set>
	<c:forEach items="${columns}" var="column">
		<c:if test="${column==field}">
			<c:set var="atLeastOneFieldToFill" value="true"></c:set>
			<tr>
				<td width="150">
					תחום התמחות:
				</td>
				<td width="120">
					<form:input htmlEscape="true" cssClass="green"  path="areaOfSpecialization"/>
				</td>
			</tr>
		</c:if>
	</c:forEach>

</c:if>

	<c:choose>
	<c:when test="${! atLeastOneFieldToFill && ! command.connectDetails}">
			<tr>
		<td colspan="3">
			<fmt:message key="editPersonAttribution.noFurtherDataNeeded"/>
		</td>
	</tr>
</c:when>
<c:otherwise>
	<tr>
		<td colspan="3">
			<c:if test="${command.newPersonAttribution && !command.connectDetails}"><button class="grey" onclick="return reloadOnImportDetailsChange();"/>ייבא נתונים אישיים</button></c:if>
			<button class="grey" onclick="submit();">שמור</button>
			&nbsp;&nbsp;
			<button class="cancel grey">סיים</button>

		</td>
	</tr>
</c:otherwise>
</c:choose>




</c:if>

</table>

</form:form>

</body>
</html>