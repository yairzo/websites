<%@ page  pageEncoding="UTF-8" %>

<script type="text/javascript">

<c:if test="${aNewOrganizationUnit}">
	window.location = "organizationUnit.html?id=${command.id}";
</c:if>

$(document).ready(function() {

	$('.cancel').click(function(){
		document.location = "organizationUnits.html";
    	return false;
     });
     $('.save').click(function(){
		$("form#form").submit();
     });
     $(".editAttribution").click(function(){
     	$("form#form").append("<input type=\"hidden\" name=\"action\" value=\"editAttribution\"/>");
     	$("form#form").submit();
     });
     $(".deleteAttribution").click(function(){
     	alert("Deleting attribution");
     	$("form#form").append("<input type=\"hidden\" name=\"action\" value=\"deleteAttribution\"/>");
     	$("form#form").submit();
     });
});

</script>



<tr>
	<td align="right" bgcolor="#787669" height="20">
		<c:set var="applicationName" value="מערכת רשימות"/>
        <c:set var="pageName" value="${pageName}"/>
       	<%@ include file="/WEB-INF/jsp/include/locationMenu.jsp" %>
	</td>
</tr>
</table>

</td>
</tr>
<tr>
	<td>
	<table width="700" border="1" align="center" cellpadding="0"
		cellspacing="0" bordercolor="#767468" dir="rtl">

		<tr>
			<td valign="top" align="center"><br>
			<form:form id="form" name="form" method="POST" action="organizationUnit.html"
				commandName="command" enctype="multipart/form-data">

				<form:hidden path="id" />

				<table width="600" border="0" align="center" cellpadding="2"
					cellspacing="0">

	<c:if test="${websiteName!='websiteNano' }">
					<tr>
						<td>
							סוג יחידה
						</td>
						<td>
							<form:select path="typeId" cssClass="green">
								<form:option value="0">בחר/י סוג יחידה</form:option>
								<c:forEach items="${organizationUnitTypes}" var="organizationUnitType">
									<form:option value="${organizationUnitType.id}">${organizationUnitType.nameHebrew}</form:option>
								</c:forEach>
							</form:select>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>
</c:if>

					<tr>
						<td>
							שם בעיברית
						</td>
						<td>
							<form:input cssClass="green" htmlEscape="true" path="nameHebrew"/>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>
					<tr>
						<td>
							שם באנגלית
						</td>
						<td>
							<form:input cssClass="green" path="nameEnglish"/>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>
	<c:if test="${websiteName=='websiteNano' }">
					<tr>
						<td valign="top">
							שם קצר:
						</td>
						<td>
							<form:input  cssClass="green" path="shortName"/>
						</td>
					</tr>

					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>
					<tr>
						<td valign="top">
							תקציר תיאור:
						</td>
						<td>
							<form:textarea  cssClass="green" path="descriptionSummary" cols="40" rows="10"/>
						</td>
					</tr>

					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>
					<tr>
						<td valign="top">
							תיאור:
						</td>
						<td>
							<form:textarea  cssClass="green" path="description" cols="40" rows="10"/>
						</td>
					</tr>

					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>
		</c:if>					
					<tr>
						<td valign="top">
							דואל:
						</td>
						<td>
							<form:input  cssClass="green" path="email"/>
						</td>
					</tr>

					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>

					<tr>
						<td>
							כתובת אתר אינטרנט:
						</td>
						<td>
							<form:input  cssClass="green" path="websiteUrl"/>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>

					<tr>
						<td>
							טלפון:
						</td>
						<td>
							<form:input  cssClass="green" path="phone"/>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>

					<tr>
						<td>
							פקס:
						</td>
						<td>
							<form:input  cssClass="green" path="fax"/>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>

					<tr>
						<td>
							כתובת:
						</td>
						<td>
							<form:input  cssClass="green" path="address"/>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>

					<tr>
						<td>
							איש קשר:
						</td>
						<td>
							<form:input  cssClass="green" path="contact"/>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>

					<tr>
						<td>
							פקולטה:
						</td>
						<td>
							<form:select cssClass="green" id="facultyIdSelect" path="facultyId">
						<form:option value="0">בחר\י פקולטה</form:option>
							<c:forEach items="${faculties}" var="faculty">
								<form:option value="${faculty.id}"><c:out value="${faculty.nameHebrew}"/></form:option>
							</c:forEach>
					</form:select>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>
			<tr>
				<td width="150">
					תמונה:
				</td>
				<td width="120">
					<c:if test="${fn:length(command.imageUrl)>0}"><img src="/imageViewer?urlTitle=${command.imageUrl}&amp;attachType=bodyImage" height="50px" width="50px"/></c:if>
					<input class="green" type="file" name="image" size="30"/>
				</td>
			</tr>






					<tr>
						<td colspan="2">&nbsp;<br>
						<img src="/image/hr.gif" width="580" height="10"><br>
						&nbsp;</td>
					</tr>

					<tr>
						<td>
						<button class="save grey" onclick="">שמור</button>

						&nbsp;&nbsp;

						<button class="cancel grey">סיים</button>
						</td>
					</tr>
				</table>

					<authz:authorize ifAnyGranted="ROLE_LISTS_ADMIN,ROLE_LISTS_EDITOR">

            <table width="600" border="0" align="center" cellpadding="3">

				<tr>
		             <td colspan="3"><img src="/image/hr.gif" width="580" height="10"></td>
        		 </tr>

              <tr>
                <td colspan="3" align="center"><h1>שיוכים לרשימות</h1>
                </td>
              </tr>


			   <c:choose>
              <c:when test="${fn:length(organizationUnitAttributions)>0}">
	              <tr>
    	            <th colspan="3" align="right">שם הרשימה</th>
	              </tr>
	           </c:when>
	           <c:otherwise>
	           		<th colspan="3" align="right">לא משוייך לרשימות</th>
	           	</c:otherwise>
	           	</c:choose>

           	<c:forEach items="${organizationUnitAttributions}" var="attrib">
				<tr>
					<td>
						<input type="radio" id="organizationUnitAttributionId" name="organizationUnitAttributionId" value="${attrib.id}"/>
						<c:out value="${attrib.list.name}"></c:out>
					</td>
				</tr>
			</c:forEach>

					<tr>
						<td>
 						    <button class="grey" onclick="window.location='organizationUnitAttribution.html?organizationUnitId=<c:out value="${command.id}"/>&cp=organizationUnit.html&cpoi=${command.id}'; return false;">הוסף שיוך</button>
 						    <c:if test="${fn:length(organizationUnitAttributions)>0}">
								<button class="grey editAttribution">ערוך</button>
								<button class="grey deleteAttribution">מחק</button>
							</c:if>
						</td>
					</tr>



            </table>
            </authz:authorize>

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
