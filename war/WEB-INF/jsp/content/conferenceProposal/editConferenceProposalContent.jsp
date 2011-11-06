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
            <form:form id="form" name="form" method="POST" action="editConferenceProposal.html" commandName="command" enctype="multipart/form-data">
 			<form:hidden path="id"/>
 			<form:hidden path="versionId"/>
 			<form:hidden path="personId"/>
            <table width="700" border="0" align="center" cellpadding="2" cellspacing="0">
                <tr>
                  <td colspan="2" align="center"><h1>בקשה למימון לכנס</h1>
                  </td>
                </tr>
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
						<form:input cssClass="green autosaveclass" path="subject"/>
					</td>
				</tr>
				<tr>
					<td width="250">
						 פרטי הכנס:
					</td>
					<td width="300">
						<form:textarea cssClass="green" path="description" cols="60" rows="3"/>
					</td>
				</tr>
				<tr>
					<td width="250">
						 מיקום בארץ:
					</td>
					<td width="300">
						<form:input cssClass="green" path="location"/>
					</td>
				</tr>
				<tr>
					<td width="250">
						 פירוט מיקום:
					</td>
					<td width="300">
						<form:input cssClass="green" path="locationDetail"/>
					</td>
				</tr>
					<!-- <tr>
						<td>
							תאריך התחלה:
						</td>
						<td>
							<form:input cssClass="green" path="fromDate" />
						</td>
					</tr>
					<tr>
						<td>
							תאריך סיום:
						</td>
						<td>
							<form:input cssClass="green" path="toDate" />
						</td>
					</tr>-->
					<tr>
					<td>מספר משתתפים:</td>
					<td>
				       <table width="400" border="1" cellpadding="2" cellspacing="0">
				       <tr>
				       <th></th>
				       <th> מרצים </th> 
				       <th> מוזמנים </th>
				       </tr>
						<tr>
						<td>
							מחו""ל:
						</td>
						<td>
							<form:input cssClass="green" path="foreignLecturers" />
						</td>
						<td>
							<form:input cssClass="green" path="foreignGuests" />
						</td>
				       </tr>
						<tr>
						<td>
							מהארץ:
						</td>
						<td>
							<form:input cssClass="green" path="localLecturers" />
						</td>
						<td>
							<form:input cssClass="green" path="localGuests" />
						</td>
				       </tr>
						<tr>
						<td>
							קהל נוסף:
						</td>
						<td>
							<form:input cssClass="green" path="audienceLecturers" />
						</td>
						<td>
							<form:input cssClass="green" path="audienceGuests" />
						</td>
					</tr>
					</table>
 					</td>
 					</tr>


					<tr>
				       <td>צרף קובץ קשימת מוזמנים:
				       </td>
						<td>
						<input class="green" type="file" name="guestsAttach"/> &nbsp; <button class="grey" onclick="">שמור</button>
						<c:if test="${fn:length(command.guestsAttach)>0}">
							<a href="fileViewer?conferenceProposalId=${command.id}&attachFile=guestsAttach&contentType=${command.guestsAttachContentType}&attachmentId=1"
								target="_blank">רשימת מוזמנים</a></td>
						</c:if>
						</td>
					</tr>
					<tr>
				       <td>צרף קובץ תוכנית הכנס:
				       </td>
						<td>
						<input class="green" type="file" name="programAttach"/> &nbsp; <button class="grey" onclick="">שמור</button>
						<c:if test="${fn:length(command.programAttach)>0}">
						  <a href="fileViewer?conferenceProposalId=${command.id}&attachFile=programAttach&contentType=${command.programAttachContentType}&attachmentId=1"
							target="_blank">תוכנית הכנס</a></td>
						</c:if>
						</td>
					</tr>
					<tr>
				       <td>צרף קובץ תוכנית תקציבית:
				       </td>
						<td>
						<input class="green" type="file" name="financialAttach"/> &nbsp; <button class="grey" onclick="">שמור</button>
						<c:if test="${fn:length(command.financialAttach)>0}">
						  <a href="fileViewer?conferenceProposalId=${command.id}&attachFile=financialAttach&contentType=${command.financialAttachContentType}&attachmentId=1"
							target="_blank">תוכנית תקציבית</a></td>
						</c:if>
						</td>
					</tr>
				
				
	            <tr id="deanApproval">
		       		<td>
	   					בחירת הגורם המאשר:
	   				</td>
				    <td>
        				<form:select id="deanSelect"  path="approverId" cssClass="green">
      					<form:option value="0">בחר/י גורם מאשר</form:option>
       					<c:forEach items="${deans}" var="deanPerson">
	        				<form:option htmlEscape="true" value="${deanPerson.id}" ><c:out escapeXml="false" value="${deanPerson.degreeFullNameHebrew}"/> - <c:out escapeXml="false" value="${deanPerson.title}"/></form:option>
       					</c:forEach>
       		        	</form:select>
					</td>
				</tr>
	            <tr >
		       		<td>
	   					חוות דעת הגורם המאשר:
	   				</td>
				    <td>
						<form:textarea cssClass="green" path="approverEvaluation" cols="60" rows="3"/>
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
				<a href="editConferenceProposal.html?id=${command.id}&version=${previousVersion}">צפה בגרסה קודמת</a>
				&nbsp;&nbsp;
				<a href="editConferenceProposal.html?id=${command.id}&version=${nextVersion}">צפה בגרסה הבאה</a>
			</td>
		</tr>



	
    </table>
</form:form>
    </td>
  </tr>

</table>


</body>
</html>