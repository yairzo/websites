<%@ page  pageEncoding="UTF-8" %>

        	 <tr>
          		<td align="right" bgcolor="#787669" height="20">
           			<c:set var="applicationName" value="מערכת אינטרנט הרשות למו\"פ"/>
          	        <c:set var="pageName" value="פרטים אישיים"/>
       	          	<%@ include file="/WEB-INF/jsp/include/locationMenu.jsp" %>
    	      	 </td>
          	 </tr>
		</table>

    </td>
  </tr>
  <tr>
    <td>
      <c:choose>
      <c:when test="${command.singlePrivilege == 'ROLE_CONFERENCE_RESEARCHER'}">	
      	<c:set var="isConference" value="true"/>
      </c:when>
      <c:when test="${command.singlePrivilege == 'ROLE_POST_RESEARCHER'}">	
      	<c:set var="isPost" value="true"/>
      </c:when>
      <c:otherwise>
      	<authz:authorize ifAnyGranted="ROLE_LISTS_ADMIN,ROLE_LISTS_EDITOR">
      		<c:set var="isAdmin" value="true"/>
        </authz:authorize>
        <authz:authorize ifNotGranted="ROLE_LISTS_ADMIN,ROLE_LISTS_EDITOR">
      		<c:set var="isSelfEdit" value="true"/>
        </authz:authorize>
      </c:otherwise>
      </c:choose>     
      
      <table width="700" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#767468" dir="rtl">
        <tr>
          <td valign="top" align="center"><br>
            <form:form id="form" name="form" method="POST" action="person.html" commandName="command">
				<form:hidden path="id"/>
				<form:hidden path="selfSubscriber"/>
				<input type="hidden" name="singlePrivilege" value="${command.singlePrivilege}"/>
				<form:hidden path="yearFirstVisit"/>
				<input type="hidden" name="cp" value="${cp}"/>
				<input type="hidden" name="tc" value="${tc}"/>
      			<form:hidden path="passValidation"/>
				
				<div id="genericDialog" title="עזרה" style="display:none" dir="rtl"></div>

              <table width="600" border="0" align="center" cellpadding="2" cellspacing="0">
				<c:set var="compulsoryFieldSign" value="<font color=\"red\">*</font>"/>

				<authz:authorize ifAnyGranted="ROLE_EDIT_USER_DETAILS">
				<c:if test="${!isConference}">
				<tr>
				<td colspan="3">

       				<font color="red">
       					<fmt:message key="iw_IL.eqfSystem.editPerson.editDetailsOnFirstYearsVisit"/>
       				</font>

       			</td>
       			</tr>
       			</c:if>
       			</authz:authorize>

                <tr>
                  <td colspan="2" align="center"><h1><fmt:message key="iw_IL.eqfSystem.editPerson.title"/>
 					<c:if test="${isConference}"><img src="/image/questionmark.png" align="top" title="הסבר על השדה" width="25" height="25" id="dialogDetails"/></c:if>
                  </h1>
                  </td>
                </tr>
                <tr class="form">
						<td>
							${compulsoryFieldSign}תעודת זהות:
						</td>
						<td>
							<c:choose>
							<c:when test="${isAdmin}">
								<form:input cssClass="green" path="civilId"/>
							</c:when>
							<c:otherwise>
								${command.civilId}	
							</c:otherwise>
							</c:choose>
						</td>
				</tr>
				<tr>
					<td colspan="2">
						<font color="red"><form:errors cssClass="errors" path="civilId"/></font>
					</td>
				</tr>

                <tr class="form">
					<td width="250">${compulsoryFieldSign}
						שם פרטי בעברית:
					</td>
					<td width="120">
						<form:input htmlEscape="true" cssClass="green" path="firstNameHebrew"/>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<font color="red"><form:errors cssClass="errors" path="firstNameHebrew"/></font>
					</td>
				</tr>
				<tr class="form">
					<td>${compulsoryFieldSign}
						שם משפחה בעברית:
					</td>
					<td>
						<form:input  htmlEscape="true" cssClass="green" path="lastNameHebrew"/>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<font color="red"><form:errors cssClass="errors" path="lastNameHebrew"/></font>
					</td>
				</tr>

				<tr class="form">
					<td><c:if test="${!isAdmin}">${compulsoryFieldSign}</c:if>
						תואר אקדמי:
					</td>
					<td>
						<form:select cssStyle="" cssClass="green" path="degreeHebrew">
							<form:option value="">בחר/י</form:option>
							<form:option value="פרופ'">פרופ'</form:option>
							<form:option htmlEscape="true" value="ד\"ר">ד"ר</form:option>
							<form:option value="גב'">גב'</form:option>
							<form:option value="מר">מר</form:option>
							<form:option htmlEscape="true" value="רו\"ח">רו"ח</form:option>
							<form:option htmlEscape="true" value="עו\"ד">עו"ד</form:option>
						</form:select>

					</td>
				</tr>
				<tr>
					<td colspan="2">
						<font color="red"><form:errors cssClass="errors" path="degreeHebrew"/></font>
					</td>
				</tr>
				<c:choose>
				<c:when test="${!isConference}">
				<tr class="form">
					<td>${compulsoryFieldSign}
						שם פרטי באנגלית:
					</td>
					<td>
						<form:input  cssClass="green" path="firstNameEnglish"/>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<font color="red"><form:errors cssClass="errors" path="firstNameEnglish"/></font>
					</td>
				</tr>
				<tr class="form">
					<td>${compulsoryFieldSign}
						שם משפחה באנגלית:
					</td>
					<td>
						<form:input cssClass="green" path="lastNameEnglish"/>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<font color="red"><form:errors cssClass="errors" path="lastNameEnglish"/></font>
					</td>
				</tr>

				<tr class="form">
					<td><c:if test="${!isAdmin}">${compulsoryFieldSign}</c:if>
						תואר באנגלית:
					</td>
					<td>
						<form:select cssStyle="" cssClass="green" path="degreeEnglish">
							<form:option value="">Select</form:option>
							<form:option value="Prof.">Prof.</form:option>
							<form:option value="Dr.">Dr.</form:option>
							<form:option value="Ms.">Ms.</form:option>
							<form:option value="Mr.">Mr.</form:option>
						</form:select>

					</td>
				</tr>
				<tr>
					<td colspan="2">
						<font color="red"><form:errors cssClass="errors" path="degreeEnglish"/></font>
					</td>
				</tr>
				</c:when>
				<c:otherwise>
					<form:hidden path="firstNameEnglish"/>
					<form:hidden path="lastNameEnglish"/>
					<form:hidden path="degreeEnglish"/>
				</c:otherwise>
				</c:choose>
				<tr class="form">
						<td><c:if test="${!isAdmin}">${compulsoryFieldSign}</c:if>
							דואל:
						</td>
						<td>
							<form:input  cssClass="green email" path="email"/>
						</td>
				</tr>
				<tr>
					<td colspan="2">
						<font color="red"><form:errors cssClass="errors" path="email"/></font>
					</td>
				</tr>
                <tr class="form">
						<td><c:if test="${!isAdmin}">${compulsoryFieldSign}</c:if>
							טלפון
						</td>
						<td>
							<form:input  cssClass="green" path="phone" size="11"/>
						</td>
				</tr>
				<tr>
					<td colspan="2">
						<font color="red"><form:errors cssClass="errors" path="phone"/></font>
					</td>
				</tr>
						<tr class="form">
						<td><c:if test="${isConference}">${compulsoryFieldSign}</c:if>
							סלולרי: 
						</td>
						<td>
							<form:input  cssClass="green" path="cellPhone"/>
						</td>
				</tr>
				<tr>
					<td colspan="2">
						<font color="red"><form:errors cssClass="errors" path="cellPhone"/></font>
					</td>
				</tr>
				<c:choose>
				<c:when test="${!isConference}">
				<tr class="form">
						<td>
						 פקס
						</td>
						<td>
							<form:input  cssClass="green" path="fax" size="11"/>
						</td>
				</tr>
				<tr>
					<td colspan="2">
						<font color="red"><form:errors cssClass="errors" path="fax"/></font>
					</td>
				</tr>
				</c:when>
				<c:otherwise>
					<form:hidden path="fax"/>
				</c:otherwise>
				</c:choose>
				<tr class="form">
						<td><c:if test="${!isAdmin}">${compulsoryFieldSign}</c:if>
							מחלקה:
						</td>
						<td>
							<form:input  htmlEscape="true" cssClass="green" path="department"/>
						</td>
				</tr>
				<tr>
					<td colspan="2">
						<font color="red"><form:errors cssClass="errors" path="department"/></font>
					</td>
				</tr>
				<tr class="form">
						<td><c:if test="${!isAdmin}">${compulsoryFieldSign}</c:if>
							פקולטה / ביה"ס:
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
						<font color="red"><form:errors cssClass="errors" path="facultyId"/></font>
					</td>
				</tr>
				<c:choose>
				<c:when test="${!isConference}">
				<tr class="form">
						<td><c:if test="${!isAdmin}">${compulsoryFieldSign}</c:if>
						קמפוס:
						</td>
						<td>
							<form:select cssClass="green" id="campusIdSelect" path="campusId">
						<form:option value="0">בחר\י קמפוס</form:option>

								<form:option value="1"><c:out value="קריית אדמונד י' ספרא"/></form:option>
								<form:option value="2"><c:out value="הר הצופים"/></form:option>
								<form:option value="3"><c:out value="עין כרם"/></form:option>
     							<form:option value="4"><c:out value="רחובות"/></form:option>
     							<form:option value="5"><c:out value="מעבדה ימית אילת"/></form:option>
     							<form:option value="6"><c:out value="אחר"/></form:option>

					</form:select>
						</td>
				</tr>
				<tr>
					<td colspan="2">
						<font color="red"><form:errors cssClass="errors" path="campusId"/></font>
					</td>
				</tr>
				<tr class="form">
						<td><c:if test="${!isAdmin}">${compulsoryFieldSign}</c:if>
						תפקיד באוניברסיטה:
						</td>
						<td>
							<form:input  htmlEscape="true" cssClass="green" path="academicTitle"/>
						</td>
				</tr>
				<tr>
					<td colspan="2">
						<font color="red"><form:errors cssClass="errors" path="academicTitle"/></font>
					</td>
				</tr>
				<tr class="form">
					<td>
						כתובת אתר אינטרנט:
					</td>
					<td>
						<form:input  htmlEscape="true" cssClass="green" path="websiteUrl"/>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<font color="red"><form:errors cssClass="errors" path="websiteUrl"/></font>
					</td>
				</tr>
				</c:when>
				<c:otherwise>
					<form:hidden path="campusId"/>
					<form:hidden path="academicTitle"/>
					<form:hidden path="websiteUrl"/>					
				</c:otherwise>
				</c:choose>
				<c:choose>
				<c:when test="${isAdmin}">
					<tr class="form">
						<td>
							כתובת:
						</td>
						<td>
							<form:input  htmlEscape="true" cssClass="green" path="address"/>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<font color="red"><form:errors cssClass="errors" path="address"/></font>
						</td>
					</tr>
					<tr class="form">
						<td>
							טלפון בבית:
						</td>
						<td>
							<form:input  cssClass="green" path="homePhone"/>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<font color="red"><form:errors cssClass="errors" path="homePhone"/></font>
						</td>
					</tr>
					<tr class="form">
						<td>
							מספר חדר:
						</td>
						<td>
							<form:input  cssClass="green" path="roomNumber"/>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<font color="red"><form:errors cssClass="errors" path="roomNumber"/></font>
						</td>
					</tr>
				</c:when>
				<c:otherwise>
					<form:hidden path="address"/>
					<form:hidden path="homePhone"/>
					<form:hidden path="roomNumber"/>
				</c:otherwise>
				</c:choose>
				<c:choose>
				<c:when test="${!isConference}">
				<tr class="form">
					<td>
						שפה מועדפת לתכתובת:
					</td>
					<td>
						<form:select  cssClass="green" path="preferedLocaleId">
							<form:option value="">בחר\י שפה</form:option>
							<c:forEach items="${langs}" var="lang">
									<form:option value="${lang.localeId}">${lang.name}</form:option>
								</c:forEach>
							</form:select>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<font color="red"><form:errors cssClass="errors" path="preferedLocaleId"/></font>
					</td>
				</tr>
				</c:when>
				</c:choose>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
                <tr>
                  <td colspan="2" align="right">
             			<table border="0" cellpadding="0" cellspacing="0">
	                      <tr>
       			            <td><button class="grey" onclick="onSave();">שמור</button></td>
       			            <td>&nbsp;&nbsp;</td>
       			            <td>
       			           		<authz:authorize ifNotGranted="ROLE_EDIT_USER_DETAILS">
       			           			<button onclick="window.location='/persons.html';return false;" class="grey">סיים</button>
       			           		</authz:authorize>
       			           	</td>
	                      </tr>
	                    </table>
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
              <c:when test="${fn:length(personAttributions)>0}">
	              <tr>
    	            <th colspan="3" align="right">שם הרשימה</th>
	              </tr>
	           </c:when>
	           <c:otherwise>
	           		<th colspan="3" align="right">לא משוייך לרשימות</th>
	           	</c:otherwise>
	           	</c:choose>

           	<c:forEach items="${personAttributions}" var="attrib">
				<tr>
					<td>
						<form:radiobutton path="personAttributionId" value="${attrib.id}"/>
						<c:out value="${attrib.list.name}"></c:out>
					</td>
				</tr>
			</c:forEach>

					<tr>
						<td>
 						    <button class="grey" onclick="window.location='personAttribution.html?personId=<c:out value="${command.id}"/>'; return false;">הוסף שיוך</button>
 						    <c:if test="${fn:length(personAttributions)>0}">
								<button class="grey" onclick="onEdit();">ערוך</button>
								<button class="grey" onclick="onDelete();">מחק</button>
							</c:if>
						</td>
					</tr>



            </table>
            </authz:authorize>
            <authz:authorize ifAnyGranted="ROLE_LISTS_ADMIN,ROLE_LISTS_EDITOR">
            	<table width="600" border="0" align="center" cellpadding="3">
            	<tr>
		             <td colspan="3"><img src="/image/hr.gif" width="580" height="10"></td>
        		 </tr>
            	<tr>
                <td colspan="3" align="center"><h1>רישום למערכת הדיוור</h1>
                </td>
              </tr>
              <tr>
					<td>
						<a href="/personPost.html?id=${command.id}&cp=person.html&cpoi=${command.id}">צפייה/עריכת פרטי הרישום למערכת הדיוור</a>
					</td>
				</tr>
				</table>
            </authz:authorize>
            <authz:authorize ifAnyGranted="ROLE_LISTS_ADMIN">
            	<table width="600" border="0" align="center" cellpadding="3">
            	<tr>
		             <td colspan="3"><img src="/image/hr.gif" width="580" height="10"></td>
        		 </tr>
            	<tr>
                <td colspan="3" align="center"><h1>הרשאות</h1>
                </td>
              </tr>
              <tr>
					<td>
						<a href="/personPrivilege.html?id=${command.id}">עדכון הרשאות</a>
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
<%@ include file="/WEB-INF/jsp/include/footer.jsp" %>

<spring:bind path="command.*">
    <c:forEach items="${status.errorMessages}" var="error">
        <font color="red">Error code: <c:out value="${error}"/></font>
        <br><br>
    </c:forEach>
</spring:bind>


</body>
</html>
