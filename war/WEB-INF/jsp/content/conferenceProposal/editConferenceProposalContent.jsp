<%@ page  pageEncoding="UTF-8" %>



       	 <tr>
          		<td align="right" bgcolor="#787669" height="20">
           			<c:set var="applicationName" value="מערכת אינטרנט הרשות למו\"פ"/>
          	        <c:set var="pageName" value="בקשה למימון כנס"/>
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
            <form:form id="form" name="form" method="POST" action="editConferenceProposal.html" commandName="command">
 			<form:hidden path="id"/>
 			<form:hidden path="versionId"/>
 			<form:hidden path="personId"/>
            <table width="700" border="0" align="center" cellpadding="2" cellspacing="0">
                <tr class="form">
					<td width="250">
						מספר בקשה:
					</td>
					<td width="300">${command.id}
					</td>
 				</tr>
				<tr>
					<td width="250">
						 שם חוקר:
					</td>
					<td width="300">
						${userPersonBean.degreeHebrew } ${userPersonBean.firstNameHebrew } ${userPersonBean.lastNameHebrew }
					</td>
				</tr>
				<tr>
					<td width="250">
						 מחלקה:
					</td>
					<td width="300">
						${userPersonBean.department } 
					</td>
				</tr>
				<tr>
					<td width="250">
						 פקולטה:
					</td>
					<td width="300">
						${faculty } 
					</td>
				</tr>
				<tr>
					<td width="250">
						 טלפון:
					</td>
					<td width="300">
						${userPersonBean.phone } 
					</td>
				</tr>
				<tr>
					<td width="250">
						 פקס:
					</td>
					<td width="300">
						${userPersonBean.fax } 
					</td>
				</tr>
				<tr>
					<td width="250">
						 כתובת מייל:
					</td>
					<td width="300">
						${userPersonBean.email } 
					</td>
				</tr>
				<tr>
					<td width="250">
						 נושא הכנס:
					</td>
					<td width="300">
						<form:input cssClass="green" path="description" />
					</td>
				</tr>
	            <tr id="deanApproval">
	        	   	<td colspan="2">
	        	       	<table>
	        	      		<tr>
      				       		<th align="right" class="medium300">
        		   					בחירת הגורם המאשר:
        		   				</th>
							    <td>
      		        				<form:select id="deanSelect"  path="approverId" cssClass="green">
      		        					<form:option value="0">בחר/י גורם מאשר</form:option>
      		        					<c:forEach items="${deans}" var="deanPerson">
      		        						<form:option htmlEscape="true" value="${deanPerson.id}" ><c:out escapeXml="false" value="${deanPerson.degreeFullNameHebrew}"/> - <c:out escapeXml="false" value="${deanPerson.title}"/></form:option>
       		        					</c:forEach>
		        		        	</form:select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				
				<tr>
					<td width="700" colspan="2" align="center">
						<input class="green" type="submit" name="submit" value="שמור"/>
 					</td>
				</tr>
		<tr>
			<td colspan="2">
				&nbsp;
			</td>
		</tr>

		<tr>
			<td width="700" colspan="2" align="center">
				<a href="editConferenceProposal.html?id=${command.id}&moveVer=prev&versionId=${command.versionId}">צפה בגרסה קודמת</a>
				&nbsp;&nbsp;
				<a href="editConferenceProposal.html?id=${command.id}&moveVer=next&versionId=${command.versionId}">צפה בגרסה הבאה</a>
			</td>
		</tr>



	
    </table>
</form:form>
    </td>
  </tr>

</table>


</body>
</html>