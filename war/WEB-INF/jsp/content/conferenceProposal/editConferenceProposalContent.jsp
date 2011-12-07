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
                  <td colspan="4" align="center"><h1>בקשה למימון לכנס</h1>
                  </td>
                </tr>
                <tr class="form">
					<td  width="250">
						מספר בקשה:
					</td>
					<td  width="250">${command.id}
					</td>
				</tr>
                <tr class="form">
					<td  width="250">
						 שם חוקר:
					</td>
					<td  width="250">
						${userPersonBean.degreeHebrew } ${userPersonBean.firstNameHebrew } ${userPersonBean.lastNameHebrew }
					</td>
					<td  width="250">
						 מחלקה:
					</td>
					<td  width="250">
						${userPersonBean.department } 
					</td>
				</tr>
				<tr class="form">
					<td  width="250">
						 פקולטה:
					</td>
					<td  width="250">
						${faculty } 
					</td>
					<td  width="250">
						 טלפון:
					</td>
					<td  width="250">
						${userPersonBean.phone } 
					</td>
				</tr>
				<tr class="form">
					<td  width="250">
						 פקס:
					</td>
					<td  width="250">
						${userPersonBean.fax } 
					</td>
					<td  width="250">
						 כתובת מייל:
					</td>
					<td  width="250">
						${userPersonBean.email } 
					</td>
				
				</tr>
				<tr class="form">
					<td>
						 הגוף היוזם:
					</td>
					<td>
						<form:input cssClass="green" path="initiatingBody"/>
					</td>
					<td>
						 תפקיד בגוף היוזם:
					</td>
					<td>
       				<form:select path="initiatingBodyRole" cssClass="green">
      					<form:option value="0">בחר/י תפקיד</form:option>
      					<form:option value="1">מנהל גוף</form:option>
      					<form:option value="2">עובד בגוף</form:option>
      					<form:option value="3">ראש הגוף</form:option>
      					<form:option value="4">חבר בגוף</form:option>
      					<form:option value="5">חבר ניהולי</form:option>
       		        	</form:select>
					</td>
				</tr>
				<tr>
		             <td colspan="4"><img src="image/hr.gif" width="100%" height="10"></td>
        		 </tr>
				<tr class="form">
					<td>
						 נושא הכנס:
					</td>
					<td colspan="3">
						<form:textarea cssClass="green autosaveclass" path="subject" cols="60" rows="1"/>
					</td>
				</tr>
				<tr class="form">
					<td> 
						 פרטי הכנס:
					</td>
					<td colspan="3">
						<form:textarea cssClass="green" path="description" cols="60" rows="3"/>
					</td>
				</tr>
				<tr class="form">
					<td  width="250">
						 מיקום בארץ:
					</td>
					<td  width="250">
       				<form:select path="location" cssClass="green">
      					<form:option value="0">בחר/י מיקום</form:option>
      					<form:option value="1">איניברסיטה</form:option>
      					<form:option value="2">ירושלים</form:option>
      					<form:option value="3">בארץ</form:option>
       		        	</form:select>
 					</td>
					<td  width="250">
						 פירוט מיקום:
					</td>
					<td  width="250">
						<form:input cssClass="green" path="locationDetail"/>
					</td>
				</tr>
				<tr>
					<td width="250">
						תאריך התחלה:
					</td>
					<td width="250">
						<input type="text" class="green" name="startConfDate" id="startConfDate" value="${startConfDate}"/>
					</td>
					<td width="250">
						תאריך סיום:
					</td>
					<td width="250">
						<input type="text" class="green" name="endConfDate" id="endConfDate" value="${endConfDate}"/>
					</td>
				</tr>
				<tr class="form">
					<td>מספר משתתפים:</td>
					<td colspan="3">
				       <table border="1" cellpadding="2" cellspacing="0">
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


				<tr class="form">
				       <td>צרף קובץ קשימת מוזמנים:
				       </td>
						<td colspan="3">
						<input class="green" type="file" name="guestsAttach"/> &nbsp; <button class="grey" onclick="">שמור</button>
						<c:if test="${fn:length(command.guestsAttach)>0}">
							<a href="fileViewer?conferenceProposalId=${command.id}&attachFile=guestsAttach&contentType=${command.guestsAttachContentType}&attachmentId=1"
								target="_blank">רשימת מוזמנים</a></td>
						</c:if>
						</td>
				</tr>
				<tr class="form">
				       <td>צרף קובץ תוכנית הכנס:
				       </td>
						<td colspan="3">
						<input class="green" type="file" name="programAttach"/> &nbsp; <button class="grey" onclick="">שמור</button>
						<c:if test="${fn:length(command.programAttach)>0}">
						  <a href="fileViewer?conferenceProposalId=${command.id}&attachFile=programAttach&contentType=${command.programAttachContentType}&attachmentId=1"
							target="_blank">תוכנית הכנס</a></td>
						</c:if>
						</td>
				</tr>
				<tr class="form">
				       <td>צרף קובץ תוכנית תקציבית:
				       </td>
						<td colspan="3">
						<input class="green" type="file" name="financialAttach"/> &nbsp; <button class="grey" onclick="">שמור</button>
						<c:if test="${fn:length(command.financialAttach)>0}">
						  <a href="fileViewer?conferenceProposalId=${command.id}&attachFile=financialAttach&contentType=${command.financialAttachContentType}&attachmentId=1"
							target="_blank">תוכנית תקציבית</a></td>
						</c:if>
						</td>
				</tr>
				
	            <tr class="form">
		       		<td colspan="2">
	   					סה"כ התקציב לארגון הכנס:
	   				</td>
				</tr>
	            <tr class="form">
		       		<td>
	   					סכום:
	   				</td>
				    <td>
						<form:input cssClass="green" path="totalCost" />
					</td>
		       		<td>
	   					מטבע:
	   				</td>
				    <td>
        				<form:select path="totalCostCurrency" cssClass="green">
      					<form:option value="0">בחר/י מטבע</form:option>
      					<form:option value="1">שקל</form:option>
      					<form:option value="2">דולר</form:option>
       		        	</form:select>
					</td>
				</tr>
				<tr>
		             <td colspan="4"><img src="image/hr.gif" width="100%" height="10"></td>
        		 </tr>
	            <tr class="form">
		       		<td colspan="2">
	   					הכנסות צפויות:
	   				</td>
				</tr>
				<tr>
					<td> משותפים לארגון:</td>
					<td colspan="3">
					<table border="1" cellpadding="1" cellspacing="0">
				    <tr>
				    <th> שם </th> 
				    <th> סכום </th>
				    <th> מטבע </th>
					</tr>
           			<c:forEach items="${command.fromAssosiate}" var="fromAssosiate">
					<tr>
					<td>
						<c:out value="${fromAssosiate.name}"></c:out>
					</td>
					<td>
						<c:out value="${fromAssosiate.sum}"></c:out>
					</td>
					<td>
						<c:if test="${fromAssosiate.currency==1}">שקל</c:if>
						<c:if test="${fromAssosiate.currency==2}">דולר</c:if>
					</td>
					</tr>
					</c:forEach>
					<tr>
					<td>
						<input type="text" class="green" name="fromAssosiate_name"/>
					</td>
					<td>
						<input type="text" class="green" name="fromAssosiate_sum"/>
					</td>
					<td>
        				<select name="fromAssosiate_currency" cssClass="green">
      					<option value="0">בחר/י מטבע</option>
      					<option value="1">שקל</option>
      					<option value="2">דולר</option>
       		        	</select>
					</td>
					</tr>					
					</table>
					</td>
				</tr>
				<tr>
				<td></td>
				<td><button class="grey fromAssosiateSave" onclick="">הוסף</button></td>
				</tr>	
				<tr>
					<td>ממממן חיצוני:</td>
					<td colspan="3">
					<table border="1" cellpadding="1" cellspacing="0">
				    <tr>
				    <th> שם </th> 
				    <th> סכום </th>
				    <th> מטבע </th>
					</tr>
           			<c:forEach items="${command.fromExternal}" var="fromExternal">
					<tr>
					<td>
						<c:out value="${fromExternal.name}"></c:out>
					</td>
					<td>
						<c:out value="${fromExternal.sum}"></c:out>
					</td>
					<td>
						<c:if test="${fromExternal.currency==1}">שקל</c:if>
						<c:if test="${fromExternal.currency==2}">דולר</c:if>
					</td>
					</tr>
					</c:forEach>
					<tr>
					<td>
						<input type="text" class="green" name="fromExternal_name"/>
					</td>
					<td>
						<input type="text" class="green" name="fromExternal_sum"/>
					</td>
					<td>
        				<select name="fromExternal_currency" cssClass="green">
      					<option value="0">בחר/י מטבע</option>
      					<option value="1">שקל</option>
      					<option value="2">דולר</option>
       		        	</select>
					</td>
					</tr>					
					</table>
					</td>
				</tr>
				<tr>
				<td></td>
				<td><button class="grey fromExternalSave" onclick="">הוסף</button></td>
				</tr>				
				<tr>
					<td>מדמי הרשמה:</td>
					<td colspan="3">
					<table border="1" cellpadding="1" cellspacing="0">
				    <tr>
				    <th> שם </th> 
				    <th> סכום </th>
				    <th> מטבע </th>
					</tr>
           			<c:forEach items="${command.fromAdmitanceFee}" var="fromAdmitanceFee">
					<tr>
					<td>
						<c:out value="${fromAdmitanceFee.name}"></c:out>
					</td>
					<td>
						<c:out value="${fromAdmitanceFee.sum}"></c:out>
					</td>
					<td>
						<c:if test="${fromAdmitanceFee.currency==1}">שקל</c:if>
						<c:if test="${fromAdmitanceFee.currency==2}">דולר</c:if>
					</td>
					</tr>
					</c:forEach>
					<tr>
					<td>
						<input type="text" class="green" name="fromAdmitanceFee_name"/>
					</td>
					<td>
						<input type="text" class="green" name="fromAdmitanceFee_sum"/>
					</td>
					<td>
        				<select name="fromAdmitanceFee_currency" cssClass="green">
      					<option value="0">בחר/י מטבע</option>
      					<option value="1">שקל</option>
      					<option value="2">דולר</option>
       		        	</select>
					</td>
					</tr>					
					</table>
					</td>
				</tr>
				<tr>
				<td></td>
				<td><button class="grey fromAdmitanceFeeSave" onclick="">הוסף</button></td>
				</tr>
				<tr>
		             <td colspan="4"><img src="image/hr.gif" width="100%" height="10"></td>
        		 </tr>
	            <tr class="form">
		       		<td colspan="2">
	   					ועדה מארגנת:
	   				</td>
				</tr>
				<tr>
					<td> ועדה מדעית:</td>
					<td colspan="3">
					<table border="1" cellpadding="1" cellspacing="0">
				    <tr>
				    <th> שם </th> 
				    <th> מוסד </th>
				    <th> תפקיד במוסד </th>
				    <th> תפקיד בועדה </th>
					</tr>
           			<c:forEach items="${command.scientificCommittees}" var="committee">
					<tr>
					<td>
						<c:out value="${committee.name}"></c:out>
					</td>
					<td>
						<c:out value="${committee.institute}"></c:out>
					</td>
					<td>
						<c:out value="${committee.instituteRole}"></c:out>
					</td>
					<td>
						<c:out value="${committee.committeeRole}"></c:out>
					</td>					
					</tr>
					</c:forEach>
					<tr>
					<td>
						<input type="text" class="green" name="scientificCommittee_name"/>
					</td>
					<td>
						<input type="text" class="green" name="scientificCommittee_institute"/>
					</td>
					<td>
						<input type="text" class="green" name="scientificCommittee_instituteRole"/>
					</td>
					<td>
						<input type="text" class="green" name="scientificCommittee_committeeRole"/>
					</td>
					</tr>		
					</table>
					</td>
				</tr>
				<tr>
				<td></td>
				<td><button class="grey scientificCommitteeSave" onclick="">הוסף</button></td>
				</tr>
				<tr>
					<td>ועדה מבצעת:</td>
					<td colspan="3">
					<table border="1" cellpadding="1" cellspacing="0">
				    <tr>
				    <th> שם </th> 
				    <th> מוסד </th>
				    <th> תפקיד במוסד </th>
				    <th> תפקיד בועדה </th>
					</tr>
           			<c:forEach items="${command.operationalCommittees}" var="opcommittee">
					<tr>
					<td>
						<c:out value="${opcommittee.name}"></c:out>
					</td>
					<td>
						<c:out value="${opcommittee.institute}"></c:out>
					</td>
					<td>
						<c:out value="${opcommittee.instituteRole}"></c:out>
					</td>
					<td>
						<c:out value="${opcommittee.committeeRole}"></c:out>
					</td>					
					</tr>
					</c:forEach>
					<tr>
					<td>
						<input type="text" class="green" name="operationalCommittee_name"/>
					</td>
					<td>
						<input type="text" class="green" name="operationalCommittee_institute"/>
					</td>
					<td>
						<input type="text" class="green" name="operationalCommittee_instituteRole"/>
					</td>
					<td>
						<input type="text" class="green" name="operationalCommittee_committeeRole"/>
					</td>
					</tr>						
					</table>
					</td>
				</tr>			
				<tr>
				<td></td>
				<td><button class="grey operationalCommitteeSave" onclick="">הוסף</button></td>
				</tr>
				<tr>
		             <td colspan="4"><img src="image/hr.gif" width="100%" height="10"></td>
        		 </tr>
				<tr class="form">
		       		<td colspan="2">
	   					סוג הסיוע המבוקש:
	   				</td>
				</tr>
	            <tr>
		       		<td>
	   					סכום הסיוע המבוקש:
	   				</td>
				    <td>
						<form:input cssClass="green" path="supportSum" />
					</td>
		       		<td>
	   					מטבע:
	   				</td>
				    <td>
        				<form:select path="supportCurrency" cssClass="green">
      					<form:option value="0">בחר/י מטבע</form:option>
      					<form:option value="1">שקל</form:option>
      					<form:option value="2">דולר</form:option>
       		        	</form:select>
					</td>
				</tr>

	            <tr class="form">
		       		<td>
		       		    אולם<form:checkbox cssClass="green" path="auditorium"/>
					</td>
		       		<td>
						חדר סמנירים<form:checkbox cssClass="green" path="seminarRoom"/>
					</td>
				</tr>
				<tr>
		       		<td>
	   					מספר אנשים:
	   				</td>
	   				<td>
	   					<form:input cssClass="green" path="participants" />
	   				</td>
	   				
				    <td>
        				קמפוס מועדף:
        			</td>
        			<td>
        				<form:select path="prefferedCampus" cssClass="green">
      					<form:option value="0">בחר/י קמפוס</form:option>
      					<form:option value="1">גבעת רם</form:option>
      					<form:option value="2">הר הצופים</form:option>
      					<form:option value="3">עין כרם</form:option>
      					<form:option value="4">רחובות</form:option>
       		        	</form:select>
 					</td>
				</tr>	
				<tr>
		             <td colspan="4"><img src="image/hr.gif" width="100%" height="10"></td>
        		 </tr>
	            <tr class="form">
		       		<td colspan="2">
	   					ארגון הכנס:
	   				</td>
				</tr>
	            <tr class="form">
		       		<td colspan="2" width="300">
		       		    ארגון ע"י חברה מסחרית<form:checkbox cssClass="green" id="company" path="organizingCompany"/>
					</td>
				</tr>
				
	            <tr class="form organizingCompanyPart">
		       		<td>
	   					שם החברה:
					</td>
		       		<td>
	   					<form:input cssClass="green" path="organizingCompanyName" />
					</td>
		       		<td>
	   					מספר טלפון:
					</td>
		       		<td>
	   					<form:input cssClass="green" path="organizingCompanyPhone" />
					</td>
				</tr>
				<tr class="form organizingCompanyPart">
		       		<td>
	   					פקס:
	   				</td>
		       		<td>
	   					<form:input cssClass="green" path="organizingCompanyFax" />
	   				</td>
				    <td>
	   					כתובת אימייל:
					</td>
				    <td>
	   					<form:input cssClass="green" path="organizingCompanyEmail" />
					</td>
				</tr>					
					
	            <tr class="form">
		       		<td colspan="2">
	   					איש קשר:
	   				</td>
				</tr>
	            <tr class="form">
		       		<td>
	   					שם איש קשר:
					</td>
		       		<td>
	   					<form:input cssClass="green" path="contactPerson" />
					</td>
		       		<td>
	   					תפקיד:
					</td>
		       		<td>
	   					<form:input cssClass="green" path="contactPersonRole" />
					</td>
				</tr>
				<tr>
		       		<td>
	   					טלפון:
	   				</td>
		       		<td>
	   					<form:input cssClass="green" path="contactPersonPhone" />
	   				</td>
				    <td>
	   					כתובת אימייל:
					</td>
				    <td>
	   					<form:input cssClass="green" path="contactPersonEmail" />
					</td>
				</tr>				
				<tr>
		             <td colspan="4"><img src="image/hr.gif" width="100%" height="10"></td>
        		 </tr>
				<tr class="form">
					<td  width="250">
						 הערות לפני הגשה:
					</td>
					<td colspan="3"  >
						<form:textarea cssClass="green" path="remarks" cols="60" rows="3"/>
					</td>
				</tr>																
	            <tr id="deanApproval" class="form">
		       		<td>
	   					בחירת הגורם המאשר:
	   				</td>
				    <td colspan="3">
        				<form:select id="deanSelect"  path="approverId" cssClass="green">
      					<form:option value="0">בחר/י גורם מאשר</form:option>
       					<c:forEach items="${deans}" var="deanPerson">
	        				<form:option htmlEscape="true" value="${deanPerson.id}" ><c:out escapeXml="false" value="${deanPerson.degreeFullNameHebrew}"/> - <c:out escapeXml="false" value="${deanPerson.title}"/></form:option>
       					</c:forEach>
       		        	</form:select>
					</td>
				</tr>
	            <tr class="form">
		       		<td>
	   					חוות דעת הגורם המאשר:
	   				</td>
				    <td colspan="3">
						<form:textarea cssClass="green" path="approverEvaluation" cols="60" rows="3"/>
					</td>
				</tr>
				
				<tr class="form">
					<td width="700" colspan="4" align="center">
						<input class="green" type="submit" name="submit" value="שמור"/>
 					</td>
				</tr>
		<tr>
			<td colspan="2">
				&nbsp;
			</td>
		</tr>

		<tr>
			<td width="700" colspan="4" align="center">

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