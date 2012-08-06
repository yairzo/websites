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
      <table width="1000" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#bca2a2" dir="rtl">
        <tr>
          <td valign="top" align="center"><br>
            <form:form id="form" name="form" method="POST" action="editConferenceProposal.html" commandName="command" enctype="multipart/form-data">
 			<form:hidden path="id"/>
 			<form:hidden path="internalId"/>
 			<form:hidden path="versionId"/>
 			<form:hidden path="personId"/>
 			<form:hidden path="deadline"/>
			
 			
			<c:if test="${approver || committee}">
 				<c:set var="readOnly" value="true"/>
 			</c:if>
			<c:if test="${!approver && !committee}">
				<c:set var="readOnly" value="false"/>
 			</c:if>
 			
 			<c:set var="compulsoryFieldSign" value=""></c:set>
			<c:if test="${!readOnly && !command.submitted}">
 				<c:set var="compulsoryFieldSign" value="<font color=\"red\">*</font>"></c:set>
 			</c:if>
 			
            <table border="0" align="center" cellpadding="2" cellspacing="0">

				<div id="genericDialog" title="עזרה" style="display:none" dir="rtl"><p>text put here</p></div>
				
                <tr>
                  <td colspan="4" align="center">
                  	<h1>בקשה מוועדת הכנסים לסיוע במימון כנס בינ"ל <br/>
                   	<a class='underline' href='http://admin-regulations.huji.ac.il/17-011.pdf' target='_blank'>
                  (לפי הוראת הנהלה 17-011)</a>
                  </h1>
                  </td>
                </tr>
				<c:if test="${!command.isInsideDeadline && command.submitted}">			
                <tr class="form">
					<td colspan="4" align="right">
						<font color="red">הצעה זו הוגשה אחרי תאריך היעד להגשות לקראת כינוס הועדה הקרובה</font>
					</td>
				</tr>
				</c:if>
                
                <tr class="form">
					<td colspan="4">
					מספר הזיהוי הפנימי של הבקשה:
					${internalIdString}
					</td>
				</tr>

                <tr class="form">
					<td colspan="4" align="right"><h3> פרטי המבקש 
					<c:if test="${!readOnly && !command.submitted}">
					<img src="image/questionmark.png" align="top" title="הסבר על השדה" width="25" height="25" id="dialogProposer"/>
					</c:if>
					</h3></td>
				</tr>
                <tr class="form">
                <td colspan="4">
                <table width="1000" style="border:1px #bca2a2 dotted" cellpadding="2" cellspacing="0" align="center">
                <tr class="form">
					<td width="300" style="border:1px #bca2a2 dotted">
						 שם חוקר:
						${command.researcher.degreeHebrew } ${command.researcher.firstNameHebrew } ${command.researcher.lastNameHebrew }
					</td>
					<td width="300" style="border:1px #bca2a2 dotted">
						 מחלקה:
					${command.researcher.department } 
					</td>
					<td width="300" style="border:1px #bca2a2 dotted">
						 פקולטה:
						${faculty } 
					</td>
				</tr>
                <tr class="form">
					<td style="border:1px #bca2a2 dotted">
						 טלפון:
						${command.researcher.phone } 
					</td>
					<td style="border:1px #bca2a2 dotted">
						 ת.ז.:
						${command.researcher.civilId } 
					</td>
					<td  style="border:1px #bca2a2 dotted" nowrap>
						 דואר אלקטרוני:
						${command.researcher.email } 
					</td>
				</tr>
				<tr class="form">
					<td style="border:1px #bca2a2 dotted" nowrap>
						 הגוף היוזם:
					<c:if test="${!readOnly && !command.submitted}">
						<form:input htmlEscape="true" cssClass="green medium200" path="initiatingBody" />
      		        	<img src="image/questionmark.png" align="top" title="הסבר על השדה" width="25" height="25" id="dialogInitiatingBody"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">
						<form:hidden path="initiatingBody"/>
 						${command.initiatingBody}
					</c:if>
					</td>
					
					<td style="border:1px #bca2a2 dotted" nowrap>
						 תפקיד המבקש בגוף היוזם:
					<c:if test="${!readOnly && !command.submitted}">
       					<form:select path="initiatingBodyRole" cssClass="green" >
      						<form:option value="0">בחר/י תפקיד</form:option>
      						<form:option value="1">ראש/מנהל הגוף</form:option>
      						<form:option value="2">עובד/חבר בגוף</form:option>
       		        	</form:select>
					</c:if>
					<c:if test="${readOnly || command.submitted}">
						<form:hidden path="initiatingBodyRole"/>
						<c:choose>
							<c:when test="${command.initiatingBodyRole == 1}">ראש/מנהל הגוף</c:when>
							<c:when test="${command.initiatingBodyRole == 2}">עובד/חבר בגוף</c:when>
						</c:choose>
					</c:if>
					</td>
					<td  style="border:1px #bca2a2 dotted" nowrap>
						 טלפון נייד:
						${command.researcher.cellPhone } 
					</td>
				</tr>
				</table>
				</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
                <tr class="form">
					<td colspan="4" align="right"><h3>תאור הכנס</h3></td>
				</tr>
				<tr class="form">
				<td colspan="4">
				<table width="1000" style="border:1px #bca2a2 dotted" cellpadding="2" cellspacing="0" align="center">
				<tr>
				<td colspan="4" style="border:1px #bca2a2 dotted">
				<table width="980">
				<tr>
					<td colspan="4">
						 ${compulsoryFieldSign}נושא הכנס (באנגלית):
					</td>
				</tr>
				<tr class="form">
					<c:if test="${!readOnly && !command.submitted}">
					<td colspan="4" align="center">
						<form:textarea cssClass="green" path="subject" id="subject" cols="80" rows="2"/>
					</td>
					</c:if>
					<c:if test="${readOnly || command.submitted}">
					<td colspan="4">
						<form:hidden path="subject"/>
						${command.subject}
					</td>
					</c:if>
				</tr>
				<tr>
				<td colspan="4"><div id="errorsubject" title="שגיאה" dir="rtl"><p></p></div>
				</td>
				</tr>
				</table>
				</td>
				</tr>

				<tr class="form">
					<td width="200" style="border:1px #bca2a2 dotted">
						${compulsoryFieldSign}תחילת הכנס:
					<c:if test="${!readOnly && !command.submitted}">
						<input type="text" class="green medium100" name="startConfDate" id="startConfDate" value="${startConfDate}" readonly="readonly"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">
						${startConfDate}
					</c:if>
					</td>
					<td width="200" style="border:1px #bca2a2 dotted">
						${compulsoryFieldSign}סיום הכנס:
					<c:if test="${!readOnly && !command.submitted}">
						<input type="text" class="green medium100" name="endConfDate" id="endConfDate" value="${endConfDate}" readonly="readonly"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">
						${endConfDate}
					</c:if>
					</td>
					<td style="border:1px #bca2a2 dotted">
						 ${compulsoryFieldSign}מיקום בארץ:
					<c:if test="${!readOnly && !command.submitted}">
      					<form:select path="location" cssClass="green" id="location">
      						<form:option value="0">בחר/י מיקום</form:option>
      						<form:option value="1">בקמפוס בהר הצופים</form:option>
      						<form:option value="2">בקמפוס בגבעת רם</form:option>
      						<form:option value="3">בקמפוס בעין כרם</form:option>
      						<form:option value="4">בקמפוס ברחובות</form:option>
      						<form:option value="5">בקמפוס באילת</form:option>
      						<form:option value="6">במקום אחר, פרט:</form:option>
        		    	</form:select>
					</c:if>
					<c:if test="${readOnly || command.submitted}">
						<form:hidden path="location"/>
						<c:choose>
							<c:when test="${command.location == 1}">בקמפוס בהר הצופים</c:when>
							<c:when test="${command.location == 2}">בקמפוס בגבעת רם</c:when>
							<c:when test="${command.location == 3}">בקמפוס בעין כרם</c:when>
							<c:when test="${command.location == 4}">בקמפוס ברחובות</c:when>
							<c:when test="${command.location == 5}">בקמפוס באילת</c:when>
							<c:when test="${command.location == 6}">:במקום אחר</c:when>
						</c:choose>
					</c:if>
					</td>
					<td class="locationDetails" style="border:1px #bca2a2 dotted; opacity: 0.3;filter: alpha(opacity=30);">
						פירוט מיקום:
					<c:if test="${!readOnly && !command.submitted}">
						<form:input htmlEscape="true" cssClass="green medium200" path="locationDetail" id="locationDetail" readonly="true"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">
						<form:hidden path="locationDetail"/>
						${command.locationDetail}
					</c:if>
					</td>
				</tr>
				<tr>
				<td colspan="4"><div id="errordetails" title="שגיאה" dir="rtl"><p></p></div>
				</td>
				</tr>
				<tr class="form">
					<td colspan="3" style="border:1px #bca2a2 dotted">
					   מספר משתתפים:	
					   <c:if test="${!readOnly && !command.submitted}">
					   <img src="image/questionmark.png" align="top" title="הסבר על השדה" width="25" height="25" id="dialogParticipants"/>						
					   </c:if>
				       <table style="border: 1px #bca2a2 dotted" cellpadding="2" cellspacing="0" align="center">
				       <tr>
				       <td style="border: 1px #bca2a2 dotted">&nbsp;</td>
				       <td style="border: 1px #bca2a2 dotted"> מרצים </td> 
				       <td style="border: 1px #bca2a2 dotted"> מוזמנים </td>
				       <td style="border: 1px #bca2a2 dotted"> קהל נוסף </td>
				       <td style="border: 1px #bca2a2 dotted">סה"כ</td>
					   </tr>
						<tr>
						<td style="border: 1px #bca2a2 dotted">
							מחו"ל:
						</td>
						<c:if test="${!readOnly && !command.submitted}">
						<td style="border: 1px #bca2a2 dotted">
							<form:input cssClass="greennoborder medium100 calcSum" path="foreignLecturers" />
						</td>
						</c:if>
						<c:if test="${readOnly || command.submitted}">
						<td width="100" style="border: 1px #bca2a2 dotted">
							<form:hidden path="foreignLecturers"/>
							${command.foreignLecturers}
						</td>
						</c:if>
						<c:if test="${!readOnly && !command.submitted}">
						<td style="border: 1px #bca2a2 dotted">
							<form:input cssClass="greennoborder medium100 calcSum" path="foreignGuests" />
						</td>
						</c:if>
						<c:if test="${readOnly || command.submitted}">
						<td width="100" style="border: 1px #bca2a2 dotted">
							<form:hidden path="foreignGuests"/>
							${command.foreignGuests}
						</td>
						</c:if>
						<c:if test="${!readOnly && !command.submitted}">
						<td style="border: 1px #bca2a2 dotted">
							<form:input cssClass="greennoborder medium100 calcSum" path="audienceLecturers" />
						</td>
						</c:if>
						<c:if test="${readOnly || command.submitted}">
						<td width="100" style="border: 1px #bca2a2 dotted">
							<form:hidden path="audienceLecturers"/>
							${command.audienceLecturers}
						</td>
						</c:if>						
						<td style="border: 1px #bca2a2 dotted"><span id="abroadCount"></span>
						</td>
						</tr>
						<tr>
						<td style="border: 1px #bca2a2 dotted">
							מהארץ:
						</td>
						<c:if test="${!readOnly && !command.submitted}">
						<td style="border: 1px #bca2a2 dotted">
							<form:input cssClass="greennoborder medium100 calcSum" path="localLecturers" />
						</td>
						</c:if>
						<c:if test="${readOnly || command.submitted}">
						<td width="100" style="border: 1px #bca2a2 dotted">
							<form:hidden path="localLecturers"/>
							${command.localLecturers}
						</td>
						</c:if>
						<c:if test="${!readOnly && !command.submitted}">
						<td style="border: 1px #bca2a2 dotted">
							<form:input cssClass="greennoborder medium100 calcSum" path="localGuests" />
						</td>
						</c:if>
						<c:if test="${readOnly || command.submitted}">
						<td width="100" style="border: 1px #bca2a2 dotted">
							<form:hidden path="localGuests"/>
							${command.localGuests}
						</td>
						</c:if>
						<c:if test="${!readOnly && !command.submitted}">
						<td style="border: 1px #bca2a2 dotted">
							<form:input cssClass="greennoborder medium100 calcSum" path="audienceGuests" />
						</td>
						</c:if>
						<c:if test="${readOnly || command.submitted}">
						<td width="100" style="border: 1px #bca2a2 dotted">
							<form:hidden path="audienceGuests"/>
							${command.audienceGuests}
						</td>
						</c:if>						
						<td style="border: 1px #bca2a2 dotted"><span id="localCount"></span>
						</td>
						</tr>
						<tr>
						<td style="border: 1px #bca2a2 dotted">
							סה"כ
						</td>
						<td style="border: 1px #bca2a2 dotted">
							<span id="lecturersCount"></span>
						</td>
						<td style="border: 1px #bca2a2 dotted">
							<span id="guestCount"></span>
						</td>
						<td style="border: 1px #bca2a2 dotted">
							<span id="otherCount"></span>
						</td>
						<td style="border: 1px #bca2a2 dotted">
							<span id="totalCount"></span>
						</td>
						</tr>
						</table>
						<br>
 				    </td>

				<td style="border:1px #bca2a2 dotted">
				<table align="center">
					<tr class="form">
						<td colspan="4">
						רשימת מרצים ומוזמנים:
						<c:if test="${!readOnly && !command.submitted}">
						<img src="image/questionmark.png" align="top" title="הסבר על השדה" width="25" height="25" id="dialogGuestsAttach"/>						
						</c:if>
						</br>
						<table>
						<tr>
						<td>
						<c:if test="${!readOnly && !command.submitted}">
							<div style="display: block; width: 60px; height: 27px; overflow: hidden;">
							<button class="green" style="width: 59px; height: 27px; position: relative; top: -1px; left: -1px;"><a href="javascript: void(0)">עיון...</a></button>
							<input type="file" style="font-size: 50px; width: 70px; opacity: 0; filter:alpha(opacity: 0);  position: relative; top: -40px; left: -5px" name="guestsAttach" id="guestsAttach"/>
							</div>
						</c:if>
						</td>
						<td>
						<span id="guestsAttachDiv">
						<c:if test="${fn:length(command.guestsAttach)>0}">
							<a href="fileViewer?conferenceProposalId=${command.id}&attachFile=guestsAttach&contentType=${command.guestsAttachContentType}&attachmentId=1"
								target="_blank"><img src="image/attach.jpg"/>&nbsp;רשימת מרצים ומוזמנים</a>
							<c:if test="${!readOnly && !command.submitted}">
								&nbsp;&nbsp;<a href="" id="deleteGuestsAttach">מחק</a>
							</c:if>
						</c:if>
						</span>
						</td>
						</tr>
						</table>
						</td>
					</tr>
					<tr class="form">
						<td colspan="4">
						תוכנית הכנס:
						<c:if test="${!readOnly && !command.submitted}">
						<img src="image/questionmark.png" align="top" title="הסבר על השדה" width="25" height="25" id="dialogProgramAttach"/>
						</c:if>
						</br>
						<table>
						<tr>
						<td>
						<c:if test="${!readOnly && !command.submitted}">
							<div style="display: block; width: 60px; height: 27px; overflow: hidden;">
							<button class="green" style="width: 59px; height: 27px; position: relative; top: -1px; left: -1px;"><a href="javascript: void(0)">עיון...</a></button>
							<input type="file" style="font-size: 50px; width: 70px; opacity: 0; filter:alpha(opacity: 0);  position: relative; top: -40px; left: -5px" name="programAttach" id="programAttach"/>
							</div>
						</c:if>
						</td>
						<td>
						<span id="programAttachDiv">
						<c:if test="${fn:length(command.programAttach)>0}">
						  <a href="fileViewer?conferenceProposalId=${command.id}&attachFile=programAttach&contentType=${command.programAttachContentType}&attachmentId=1"
							target="_blank"><img src="image/attach.jpg"/>&nbsp;תוכנית הכנס</a>
							<c:if test="${!readOnly && !command.submitted}">
								&nbsp;&nbsp;<a href="" id="deleteProgramAttach">מחק</a>
							</c:if>
						</c:if>
						</span>
						</td>
						</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>
				</tr>
				
				<tr>
				<td colspan="4" style="border:1px #bca2a2 dotted">
				<table width="980">
				<tr>
					<td colspan="4"> 
						${compulsoryFieldSign}התוכן העיוני של הכנס וחשיבותו לתחום:
						<c:if test="${!readOnly && !command.submitted}">
						<img src="image/questionmark.png" align="top" title="הסבר על השדה" width="25" height="25" id="dialogDescription"/>
						</c:if>
					</td>
				</tr>
				<tr class="form">
					<c:if test="${!readOnly && !command.submitted}">
					<td colspan="4" align="center">
						<form:textarea htmlEscape="true" cssClass="green" path="description" id="description" cols="80" rows="5" onkeyup="textlimiter()"/>
					</td>
					</c:if>
					<c:if test="${readOnly || command.submitted}">
					<td colspan="4" >
						<form:hidden path="description"/>
						${command.description}
					</td>
					</c:if>
				</tr>
				<tr><td colspan="4"><div id="errordescription" dir="rtl"><p></p></div></td></tr>
				<tr><td>&nbsp;</td></tr>
				</table>
				</td>
				</tr>
				</table>
				</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
	            <tr class="form">
		       		<td colspan="4" align="right"><h3>הארגון האקדמי של הכנס</h3></td>
				</tr>
				<tr>
				<td colspan="4" >
				<table width="1000"  style="border:1px #bca2a2 dotted" cellpadding="2" cellspacing="0" align="center">
				<tr>
					<td colspan="4" style="border:1px #bca2a2 dotted">
					ועדה מדעית/מארגנת:
					<c:if test="${!readOnly && !command.submitted}">
					<img src="image/questionmark.png" align="top" title="הסבר על השדה" width="25" height="25" id="dialogCommittee"/>
					</c:if>
 					<table width="725"  cellpadding="1" cellspacing="0" align="center">
				    <tr>
				    <th style="border: 1px #bca2a2 dotted"> שם </th> 
				    <th style="border: 1px #bca2a2 dotted"> מוסד </th>
				    <th style="border: 1px #bca2a2 dotted"> תפקיד במוסד </th>
				    <th style="border: 1px #bca2a2 dotted"> תפקיד בועדה המדעית </th>
				    <th style="border: 1px #bca2a2 dotted"> תפקיד בועדה המארגנת </th>
					</tr>
 					<c:choose>
 					<c:when test="${readOnly || command.submitted}">
           				<c:forEach items="${command.scientificCommittees}" var="committee">
						<tr>
						<td style="border: 1px #bca2a2 dotted">
							<c:out value="${committee.name}"></c:out>
						</td>
						<td style="border: 1px #bca2a2 dotted">
							<c:out value="${committee.institute}"></c:out>
						</td>
						<td style="border: 1px #bca2a2 dotted">
							<c:out value="${committee.instituteRole}"></c:out>
						</td>
						<td style="border: 1px #bca2a2 dotted">
							<c:choose>
							<c:when test="${committee.committeeRole == 1}">חבר</c:when>
							<c:when test="${committee.committeeRole == 2}">יושב ראש</c:when>
							<c:when test="${committee.committeeRole == 3}">לא שותף</c:when>
							</c:choose>
						</td>					
						<td style="border: 1px #bca2a2 dotted">
							<c:choose>
							<c:when test="${committee.committeeRoleOrganizing == 1}">חבר</c:when>
							<c:when test="${committee.committeeRoleOrganizing == 2}">יושב ראש</c:when>
							<c:when test="${committee.committeeRoleOrganizing == 3}">לא שותף</c:when>
							</c:choose>
						</td>					
						</tr>
						</c:forEach>
					</c:when>
 					<c:otherwise>					
           				<c:forEach items="${command.scientificCommittees}" var="committee" varStatus="varStatus">
           				<form:hidden path="scientificCommittees[${varStatus.index}].conferenceProposalId"/>
           				<form:hidden path="scientificCommittees[${varStatus.index}].type"/>
						<tr style="display: none;" class="scientificCommittee committee">
						<td style="border: 1px #bca2a2 dotted">
							<form:input htmlEscape="true" cssClass="greennoborder scientificCommittee medium170" path="scientificCommittees[${varStatus.index}].name"/>
						</td>
						<td style="border: 1px #bca2a2 dotted">
							<form:input htmlEscape="true" cssClass="greennoborder scientificCommittee medium170" path="scientificCommittees[${varStatus.index}].institute"/>
						</td>
						<td style="border: 1px #bca2a2 dotted">
							<form:input htmlEscape="true" cssClass="greennoborder scientificCommittee medium170" path="scientificCommittees[${varStatus.index}].instituteRole"/>
						</td>
						<td style="border: 1px #bca2a2 dotted">
						<form:select path="scientificCommittees[${varStatus.index}].committeeRole" cssClass="green scientificCommitte medium170">
      					<form:option value="">בחר/י תפקיד</form:option>
      					<form:option value="1">חבר</form:option>
      					<form:option value="2">יושב ראש</form:option>
      					<form:option value="3">לא שותף</form:option>
       		        	</form:select>
						</td>					
						<td style="border: 1px #bca2a2 dotted">
						<form:select path="scientificCommittees[${varStatus.index}].committeeRoleOrganizing" cssClass="green scientificCommitte medium170">
      					<form:option value="">בחר/י תפקיד</form:option>
      					<form:option value="1">חבר</form:option>
      					<form:option value="2">יושב ראש</form:option>
      					<form:option value="3">לא שותף</form:option>
       		        	</form:select>
						</td>					
						<td>
							<c:set var="committee" value="${command.scientificCommittees[varStatus.index]}"/>
							<img src="image/icon_delete.gif" class="deleteCommittee" title="מחיקת שורה"/>							
						</td>
						</tr> 
						</c:forEach>
					</c:otherwise>
					</c:choose>
					</table>
					<!-- <br>
					ועדה מארגנת:
 					<table width="725" cellpadding="1" cellspacing="0" align="center">
				    <tr>
				    <th style="border: 1px #bca2a2 dotted"> שם </th> 
				    <th style="border: 1px #bca2a2 dotted"> מוסד </th>
				    <th style="border: 1px #bca2a2 dotted"> תפקיד במוסד </th>
				    <th style="border: 1px #bca2a2 dotted"> תפקיד בועדה </th>
					</tr>
 					<c:choose>
 					<c:when test="${readOnly || command.submitted}">
           				<c:forEach items="${command.operationalCommittees}" var="committee">
						<tr>
						<td style="border: 1px #bca2a2 dotted">
							<c:out value="${committee.name}"></c:out>
						</td>
						<td style="border: 1px #bca2a2 dotted">
							<c:out value="${committee.institute}"></c:out>
						</td>
						<td style="border: 1px #bca2a2 dotted">
							<c:out value="${committee.instituteRole}"></c:out>
						</td>
						<td style="border: 1px #bca2a2 dotted">
							<c:out value="${committee.committeeRole}"></c:out>
						</td>					
						</tr>
						</c:forEach>

					</c:when>
 					<c:otherwise>					
           				<c:forEach items="${command.operationalCommittees}" var="committee" varStatus="varStatus">
           				<form:hidden path="operationalCommittees[${varStatus.index}].conferenceProposalId"/>
           				<form:hidden path="operationalCommittees[${varStatus.index}].type"/>
						<tr style="display: none;" class="operationalCommittee committee">

						<td style="border: 1px #bca2a2 dotted">
							<form:input htmlEscape="true" cssClass="greennoborder operationalCommittee medium170" path="operationalCommittees[${varStatus.index}].name"/>
						</td>
						<td style="border: 1px #bca2a2 dotted">
							<form:input htmlEscape="true" cssClass="greennoborder operationalCommittee medium170" path="operationalCommittees[${varStatus.index}].institute"/>
						</td>
						<td style="border: 1px #bca2a2 dotted">
							<form:input htmlEscape="true" cssClass="greennoborder operationalCommittee medium170" path="operationalCommittees[${varStatus.index}].instituteRole"/>
						</td>
						<td style="border: 1px #bca2a2 dotted">
							<form:input htmlEscape="true" cssClass="greennoborder operationalCommittee medium170" path="operationalCommittees[${varStatus.index}].committeeRole"/>
						</td>					
						<td>
							<c:set var="committee" value="${command.operationalCommittees[varStatus.index]}"/>
							<img src="image/icon_delete.gif" class="deleteCommittee" title="מחיקת שורה"/>							
						</td>
						</tr>
						</c:forEach>
					</c:otherwise>
					</c:choose>
					<tr><td>&nbsp;</td></tr>
					</table> -->
					</br>
					</td>
				</tr>
				</table>
				</td>
				</tr>
				
				<tr><td>&nbsp;</td></tr>
	            <tr class="form">
		       		<td colspan="4" align="right"><h3>הארגון המנהלתי של הכנס</h3></td>
				</tr>
				<tr>
				<td colspan="4" >
				<table width="1000"  style="border:1px #bca2a2 dotted" cellpadding="2" cellspacing="0" align="center">
	            <tr>
	            	<td colspan="4" style="border:1px #bca2a2 dotted">
					<c:if test="${!readOnly && !command.submitted}">			
	            		<input type="radio" name="organizingCompany" value="true" <c:if test="${command.organizingCompany}">checked</c:if> />הארגון המנהלתי של הכנס נמסר לחברה מסחרית
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="organizingCompany"/>
		       		    <input type="radio" disabled="disabled" value="" <c:if test="${command.organizingCompany}">checked</c:if> />הארגון המנהלתי של הכנס נמסר לחברה מסחרית
					</c:if>
					</td>
				</tr>
	            <tr class="form organizingCompanyPart">
		       		<td nowrap style="border:1px #bca2a2 dotted">שם החברה:
					<c:if test="${!readOnly && !command.submitted}">			
	   					<form:input htmlEscape="true" cssClass="green medium150" path="organizingCompanyName" />
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="organizingCompanyName"/>
	   					${command.organizingCompanyName}
					</c:if>
					</td>
		       		<!-- <td style="border:1px #bca2a2 dotted">טלפון:
					<c:if test="${!readOnly && !command.submitted}">			
	   					<form:input htmlEscape="true" cssClass="green medium150" path="organizingCompanyPhone" id="organizingCompanyPhone"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="organizingCompanyPhone"/>
	   					${command.organizingCompanyPhone}
					</c:if>
					</td>
		       		<td style="border:1px #bca2a2 dotted">פקס:
					<c:if test="${!readOnly && !command.submitted}">			
	   					<form:input htmlEscape="true" cssClass="green medium150" path="organizingCompanyFax" id="organizingCompanyFax"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="organizingCompanyFax"/>
	   					${command.organizingCompanyFax}
					</c:if>
	   				</td>
				    <td style="border:1px #bca2a2 dotted">אימייל:
					<c:if test="${!readOnly && !command.submitted}">			
	   					<form:input htmlEscape="true" cssClass="green medium250" path="organizingCompanyEmail" id="organizingCompanyEmail"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="organizingCompanyEmail"/>
	   					${command.organizingCompanyEmail}
					</c:if>
	   				</td> -->
	   				<td colspan="3" style="border:1px #bca2a2 dotted">
	   				<table>
					<tr>
					<td>
	   				הסכם חברה:
	   				<c:if test="${!readOnly && !command.submitted}">
	   				<img src="image/questionmark.png" align="top" title="הסבר על השדה" width="25" height="25" id="dialogCompany"/>
	   				</c:if>
	   				</td>
	   				<td>
	   					<c:if test="${!readOnly && !command.submitted}">
							<span style="display: block; width: 60px; height: 27px; overflow: hidden;">
							<button class="green" style="width: 59px; height: 27px; position: relative; top: -1px; left: -1px;"><a href="javascript: void(0)">עיון...</a></button>
							<input type="file" style="font-size: 50px; width: 70px; opacity: 0; filter:alpha(opacity: 0);  position: relative; top: -40px; left: -5px" name="companyAttach" id="companyAttach"/>
							</span>
						</c:if>
					</td>
					<td>
						<span id="companyAttachDiv">
						<c:if test="${fn:length(command.companyAttach)>0}">
						  <a href="fileViewer?conferenceProposalId=${command.id}&attachFile=companyAttach&contentType=${command.companyAttachContentType}&attachmentId=1"
							target="_blank"><img src="image/attach.jpg"/>&nbsp;הסכם חברה</a>
							<c:if test="${!readOnly && !command.submitted}">
								&nbsp;&nbsp;<a href="" id="deleteCompanyAttach">מחק</a>
							</c:if>
						</c:if>
						</span>
	   				</td>
	   				</tr>
	   				</table>
	   				</td>
				</tr>
	            <tr>
	            	<td colspan="4" style="border:1px #bca2a2 dotted">
					<c:if test="${!readOnly && !command.submitted}">			
	            		<input type="radio" name="organizingCompany" value="false" <c:if test="${!command.organizingCompany}">checked</c:if>/>הארגון המנהלתי של הכנס ייערך ע"י איש קשר 
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
		       		    <input type="radio" disabled="disabled" value="" <c:if test="${!command.organizingCompany}">checked</c:if> />הארגון המנהלתי של הכנס ייערך ע"י איש קשר
					</c:if>
				</tr>
	            <tr class="form organizingContactPart">
		       		<td nowrap style="border:1px #bca2a2 dotted">${compulsoryFieldSign}שם איש קשר:
					<c:if test="${!readOnly && !command.submitted}">			
	   					<form:input htmlEscape="true" cssClass="green medium150" path="contactPerson" id="contactPerson"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="contactPerson"/>
	   					${command.contactPerson}
					</c:if>
					</td>
		       		<td nowrap style="border:1px #bca2a2 dotted">תפקיד:
					<c:if test="${!readOnly && !command.submitted}">			
	   					<form:input htmlEscape="true" cssClass="green medium150" path="contactPersonRole" />
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="contactPersonRole"/>
	   					${command.contactPersonRole}
					</c:if>
					</td>
		       		<td nowrap style="border:1px #bca2a2 dotted">טלפון:
					<c:if test="${!readOnly && !command.submitted}">			
	   					<form:input htmlEscape="true" cssClass="green medium150" path="contactPersonPhone" id="contactPersonPhone"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="contactPersonPhone"/>
	   					${command.contactPersonPhone}
					</c:if>
					</td>
				    <td nowrap style="border:1px #bca2a2 dotted">אימייל:
					<c:if test="${!readOnly && !command.submitted}">			
	   					<form:input htmlEscape="true" cssClass="green medium250" path="contactPersonEmail" id="contactPersonEmail"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="contactPersonEmail"/>
	   					${command.contactPersonEmail}
 					</c:if>
	   				</td>
		       		</tr>
				</table>
				</td>
				</tr>
				<tr>
				<td colspan="4">
				<div id="errororganizingCompanyPhone" dir="rtl"><p></p></div>
				<div id="errororganizingCompanyFax" dir="rtl"><p></p></div>
				<div id="errororganizingCompanyEmail" dir="rtl"><p></p></div>
				<div id="errorcontactPersonName" dir="rtl"><p></p></div>
				<div id="errorcontactPersonPhone" dir="rtl"><p></p></div>
				<div id="errorcontactPersonEmail" dir="rtl"><p></p></div>
				</td>
				</tr>	
				
				<tr><td>&nbsp;</td></tr>
				
	            <tr class="form">
		       		<td colspan="4" align="right"><h3>תקציב הכנס 
		       		<c:if test="${!readOnly && !command.submitted}">
		       		<img src="image/questionmark.png" align="top" title="הסבר על השדה" width="25" height="25" id="dialogBudget"/>
		       		</c:if>
		       		</h3>
		       		</td>
				</tr>
	            <tr class="form">
	            	<td colspan="4">
					<table width="1000" style="border:1px #bca2a2 dotted" cellpadding="2" cellspacing="0" align="center">
	            	<tr class="form">
		       		<td style="border:1px #bca2a2 dotted">
	   					 ${compulsoryFieldSign}סה"כ ההוצאות לארגון הכנס (ללא השתתפות הוועדה):
					<c:if test="${!readOnly && !command.submitted}">
						<form:input cssClass="green medium100" path="totalCost"  id="totalCost"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">
						<form:hidden path="totalCost"/>
						${command.totalCost}
					</c:if>

	   				דולר
					<!--  <c:if test="${!readOnly && !command.submitted}">
       				<form:select path="totalCostCurrency" cssClass="green">
      					<form:option value="0">בחר/י מטבע</form:option>
      					<form:option value="1">שקל</form:option>
      					<form:option value="2">דולר</form:option>
       		        	</form:select>
					</c:if>
					<c:if test="${readOnly || command.submitted}">
						<form:hidden path="totalCostCurrency"/>
 						<c:choose>
							<c:when test="${command.totalCostCurrency == 1}">שקל</c:when>
							<c:when test="${command.totalCostCurrency == 2}">דולר</c:when>
						</c:choose>
					</c:if>-->
					<c:if test="${!readOnly && !command.submitted}">
					<img src="image/questionmark.png" align="top" title="הסבר על השדה" width="25" height="25" id="dialogTotalCost"/>
	   				</c:if>
	   				</td>
					<td style="border:1px #bca2a2 dotted">
					<table>
					<tr>
						<td>
					
				       תוכנית תקציבית:
				       </td>
				       <td>
						<c:if test="${!readOnly && !command.submitted}">
							<div style="display: block; width: 60px; height: 27px; overflow: hidden;">
							<button class="green" style="width: 59px; height: 27px; position: relative; top: -1px; left: -1px;"><a href="javascript: void(0)">עיון...</a></button>
							<input type="file" style="font-size: 50px; width: 70px; opacity: 0; filter:alpha(opacity: 0);  position: relative; top: -40px; left: -5px" name="financialAttach" id="financialAttach"/>
							</div>
						</c:if>
						</td>
						<td>
						<span id="financialAttachDiv">
						<c:if test="${fn:length(command.financialAttach)>0}">
						  <a href="fileViewer?conferenceProposalId=${command.id}&attachFile=financialAttach&contentType=${command.financialAttachContentType}&attachmentId=1"
							target="_blank"><img src="image/attach.jpg"/>&nbsp;תוכנית תקציבית</a>
							<c:if test="${!readOnly && !command.submitted}">
								&nbsp;&nbsp;<a href="" id="deleteFinancialAttach">מחק</a>
							</c:if>
						</c:if>
						</span>
						</td>
						</tr>
						</table>
					</td>
				</tr>				

				<tr>
					<td colspan="4" style="border:1px #bca2a2 dotted">
	           		פירוט מקורות המימון<br>
					משותפים לארגון:
					<table width="775" cellpadding="1" cellspacing="0" align="center">
 				    <tr>
				    <th style="border: 1px #bca2a2 dotted"> שם השותף </th> 
				    <th style="border: 1px #bca2a2 dotted"> סכום (דולר)</th>
				    <!-- <th style="border: 1px #bca2a2 dotted"> מטבע </th> -->
				    <th style="border: 1px #bca2a2 dotted">אסמכתא</th>
					<c:if test="${!readOnly && !command.submitted}">
 				    <th>
 				    <c:if test="${!readOnly && !command.submitted}">
 				    <img src="image/questionmark.png" align="top" title="הסבר על השדה" width="25" height="25" id="dialogDeleteFinancialSupport"/>
				    </c:if>
				    </th>
					</c:if>
					</tr>
					<c:choose>
 					<c:when test="${readOnly || command.submitted}">
           				<c:forEach items="${command.fromAssosiate}" var="fromAssosiate" varStatus="varStatus">
						<tr>
						<td style="border: 1px #bca2a2 dotted">
							<c:out value="${fromAssosiate.name}"></c:out>
						</td>
						<td style="border: 1px #bca2a2 dotted">
							<c:out value="${fromAssosiate.sum}"></c:out>
						</td>
						<td style="border: 1px #bca2a2 dotted" align="center">
							<c:if test="${fn:length(fromAssosiate.referenceFile)>0}">
								<a align="right" href="fileViewer?conferenceProposalId=${command.id}&assosiateId=${varStatus.index}&attachFile=assosiateAttach&contentType=${fromAssosiate.fileContentType}&attachmentId=1"
									target="_blank"><img src="image/attach.jpg"/>&nbsp;אסמכתא</a>
							</c:if>
							<c:if test="${fn:length(fromAssosiate.sum)>0 && fn:length(fromAssosiate.referenceFile)==0 }">לא צורף קובץ אסמכתא
							</c:if>
						</td>
						
						<!--  <td style="border: 1px #bca2a2 dotted">
							<c:if test="${fromAssosiate.currency==1}">שקל</c:if>
							<c:if test="${fromAssosiate.currency==2}">דולר</c:if>
						</td>-->
						</tr>
						</c:forEach>
					</c:when>
 					<c:otherwise>					
           				<c:forEach items="${command.fromAssosiate}" var="financialSupport" varStatus="varStatus">
           				<form:hidden path="fromAssosiate[${varStatus.index}].conferenceProposalId"/>
           				<form:hidden path="fromAssosiate[${varStatus.index}].type"/>

						<tr style="display: none;" class="assosiate financialSupport">

						<td width="400" style="border: 1px #bca2a2 dotted" align="center">
							<form:input htmlEscape="true" cssClass="greennoborder fillWidth assosiate" path="fromAssosiate[${varStatus.index}].name"/>
						</td>
						<td width="150" style="border: 1px #bca2a2 dotted" align="center">
							<form:input htmlEscape="true" cssClass="greennoborder fillWidth assosiate fee" path="fromAssosiate[${varStatus.index}].sum"/>
						</td>
						<!--<td width="150" style="border: 1px #bca2a2 dotted" align="center">
							<form:select cssClass="greennoborder medium220" path="fromAssosiate[${varStatus.index}].currency">
      						<form:option value="0">מטבע</form:option>
       						<form:option value="1">שקל</form:option>
      						<form:option value="2">דולר</form:option>
							</form:select>
						</td>-->
						<td width="250" style="border: 1px #bca2a2 dotted" align="center">
						<table>
						<tr>
							<td>
						 	<c:if test="${!readOnly && !command.submitted}">
							<span style="display: none; width: 60px; height: 26px; overflow: hidden;" class="fromAssosiateAttachFields">
							<button class="green" style="width: 59px; height: 26px; position: relative; top: -1px; left: -1px;"><a href="javascript: void(0)">עיון...</a></button>
							<input type="file" style="font-size: 50px; width: 70px; opacity: 0; filter:alpha(opacity: 0);  position: relative; top: -40px; left: -5px" class="fromAssosiateAttachFile" name="fromAssosiate[${varStatus.index}].referenceFile" id="fromAssosiate[${varStatus.index}].referenceFile"/>
							</span>
							</c:if>
							</td>
							<td>
							<span id="fromAssosiateAttachDiv" align="right">
							<c:if test="${fn:length(command.fromAssosiate[varStatus.index].referenceFile)>0}">
								<a align="right" href="fileViewer?conferenceProposalId=${command.id}&assosiateId=${varStatus.index}&attachFile=assosiateAttach&contentType=${command.fromAssosiate[varStatus.index].fileContentType}&attachmentId=1"
									target="_blank"><img src="image/attach.jpg"/>&nbsp;אסמכתא</a>
								<c:if test="${!readOnly && !command.submitted}">
									&nbsp;<a href="" class="deleteFromAssosiateAttachFile" id="${varStatus.index}">מחק</a>
								</c:if>
							</c:if>
							</span>
							</td>
							</tr>
						</table>
						</td>
						<td>
							<c:set var="financialSupport" value="${command.fromAssosiate[varStatus.index]}"/>
							<img src="image/icon_delete.gif" class="deleteFinancialSupport" title="מחיקת שורה"/>							
						</td>
						</tr>

						</c:forEach>
					</c:otherwise>
					</c:choose>
					<tr>
						<td width="400" style="border: 1px #bca2a2 dotted" align="right">
						סה"כ משותפים לארגון
						</td>
						<td width="150" style="border: 1px #bca2a2 dotted" align="right">
						<span id="fromAssosiateCount">&nbsp;</span>
						</td>
						<td style="border: 1px #bca2a2 dotted">&nbsp;</td>
					</tr>
					</table>
					<br>ממממן חיצוני:
					<table width="775" cellpadding="1" cellspacing="0" align="center">
				    <tr>
				    <th style="border: 1px #bca2a2 dotted"> שם המממן </th> 
				    <th style="border: 1px #bca2a2 dotted"> סכום (דולר)</th>
				    <!--<th style="border: 1px #bca2a2 dotted"> מטבע </th>-->
				    <th style="border: 1px #bca2a2 dotted">אסמכתא</th>
					</tr>
					<c:choose>
 					<c:when test="${readOnly || command.submitted}">
           			<c:forEach items="${command.fromExternal}" var="fromExternal" varStatus="varStatus">
						<tr>
						<td style="border: 1px #bca2a2 dotted">
						<c:out value="${fromExternal.name}"></c:out>
						</td>
						<td style="border: 1px #bca2a2 dotted">
						<c:out value="${fromExternal.sum}"></c:out>
						</td>
						<!--  <td style="border: 1px #bca2a2 dotted">
						<c:if test="${fromExternal.currency==1}">שקל</c:if>
						<c:if test="${fromExternal.currency==2}">דולר</c:if>
						</td>-->
						<td style="border: 1px #bca2a2 dotted" align="center">
							<c:if test="${fn:length(fromExternal.referenceFile)>0}">
								<a align="right" href="fileViewer?conferenceProposalId=${command.id}&externalId=${varStatus.index}&attachFile=externalAttach&contentType=${fromExternal.fileContentType}&attachmentId=1"
									target="_blank"><img src="image/attach.jpg"/>&nbsp;אסמכתא</a>
							</c:if>
							<c:if test="${fn:length(fromExternal.sum)>0 && fn:length(fromExternal.referenceFile)==0 }">לא צורף קובץ אסמכתא
							</c:if>
						</td>
						</tr>
					</c:forEach>
					</c:when>
 					<c:otherwise>					
           			  <c:forEach items="${command.fromExternal}" var="financialSupport" varStatus="varStatus">
           				<form:hidden path="fromExternal[${varStatus.index}].conferenceProposalId"/>
           				<form:hidden path="fromExternal[${varStatus.index}].type"/>

						<tr style="display: none;" class="external financialSupport">
						<td width="400" style="border: 1px #bca2a2 dotted" align="center">
							<form:input htmlEscape="true" cssClass="greennoborder fillWidth external" path="fromExternal[${varStatus.index}].name"/>
						</td>
						<td width="150" style="border: 1px #bca2a2 dotted" align="center">
							<form:input htmlEscape="true" cssClass="greennoborder fillWidth external fee" path="fromExternal[${varStatus.index}].sum"/>
						</td>
						<!--<td width="150" style="border: 1px #bca2a2 dotted" align="center">
							<form:select cssClass="greennoborder medium220" path="fromExternal[${varStatus.index}].currency">
      						<form:option value="0">מטבע</form:option>
       						<form:option value="1">שקל</form:option>
      						<form:option value="2">דולר</form:option>
							</form:select>
						</td>-->
						<td width="250" style="border: 1px #bca2a2 dotted" align="center">
						<table>
						<tr>
							<td>
						 	<c:if test="${!readOnly && !command.submitted}">
							<span style="display: none; width: 60px; height: 26px; overflow: hidden;" class="fromExternalAttachFields">
							<button class="green" style="width: 59px; height: 26px; position: relative; top: -1px; left: -1px;"><a href="javascript: void(0)">עיון...</a></button>
							<input type="file" style="font-size: 50px; width: 70px; opacity: 0; filter:alpha(opacity: 0);  position: relative; top: -40px; left: -5px" class="fromExternalAttachFile" name="fromExternal[${varStatus.index}].referenceFile" id="fromExternal[${varStatus.index}].referenceFile"/>
							</span>
							</c:if>
							</td>
							<td>
							<span id="fromExternalAttachDiv" align="right">
							<c:if test="${fn:length(command.fromExternal[varStatus.index].referenceFile)>0}">
								<a align="right" href="fileViewer?conferenceProposalId=${command.id}&externalId=${varStatus.index}&attachFile=externalAttach&contentType=${command.fromExternal[varStatus.index].fileContentType}&attachmentId=1"
									target="_blank"><img src="image/attach.jpg"/>&nbsp;אסמכתא</a>
								<c:if test="${!readOnly && !command.submitted}">
									&nbsp;<a href="" class="deleteFromExternalAttachFile" id="${varStatus.index}">מחק</a>
								</c:if>
							</c:if>
							</span>
							</td>
						</tr>
						</table>
						</td>
						<td>
							<c:set var="financialSupport" value="${command.fromExternal[varStatus.index]}"/>
							<img src="image/icon_delete.gif" class="deleteFinancialSupport" title="מחיקת שורה"/>							
						</td>
						</tr>
						</c:forEach>
					</c:otherwise>
					</c:choose>
					<tr>
						<td width="400" style="border: 1px #bca2a2 dotted" align="right">
						סה"כ מממנים חיצוניים
						</td>
						<td width="150" style="border: 1px #bca2a2 dotted" align="right">
						<span id="fromExternalCount">&nbsp;</span>
						</td>
						<td style="border: 1px #bca2a2 dotted">&nbsp;</td>
					</tr>
					
					</table>
					<br>מדמי הרשמה:
					<table width="775" cellpadding="1" cellspacing="0" align="center">
				    <tr>
				    <th style="border: 1px #bca2a2 dotted"> סכום למשתתף (דולר)</th>
				    <th style="border: 1px #bca2a2 dotted"> מספר המשלמים</th>
				    <th style="border: 1px #bca2a2 dotted"> סכום כולל (דולר)</th>
				    <!-- <th style="border: 1px #bca2a2 dotted"> מטבע </th> -->
				    <th style="border: 1px #bca2a2 dotted">אסמכתא</th>
					</tr>
					<c:choose>
 					<c:when test="${readOnly || command.submitted}">
           				<c:forEach items="${command.fromAdmitanceFee}" var="fromAdmitanceFee" varStatus="varStatus">
						<tr>
						<td style="border: 1px #bca2a2 dotted">
						<c:out value="${fromAdmitanceFee.sumPerson}"></c:out>
						</td>
						<td style="border: 1px #bca2a2 dotted">
						<c:out value="${fromAdmitanceFee.name}"></c:out>
						</td>
						<td style="border: 1px #bca2a2 dotted">
						<c:out value="${fromAdmitanceFee.sum}"></c:out>
						</td>
						<!-- <td style="border: 1px #bca2a2 dotted">
						<c:if test="${fromAdmitanceFee.currency==1}">שקל</c:if>
						<c:if test="${fromAdmitanceFee.currency==2}">דולר</c:if>
						</td> -->
						<td style="border: 1px #bca2a2 dotted" align="center">
							<c:if test="${fn:length(fromAdmitanceFee.referenceFile)>0}">
								<a align="right" href="fileViewer?conferenceProposalId=${command.id}&admitanceFeeId=${varStatus.index}&attachFile=admitanceFeeAttach&contentType=${fromAdmitanceFee.fileContentType}&attachmentId=1"
									target="_blank"><img src="image/attach.jpg"/>&nbsp;אסמכתא</a>
							</c:if>
							<c:if test="${fn:length(fromAdmitanceFee.sum)>0 && fn:length(fromAdmitanceFee.referenceFile)==0 }">לא צורף קובץ אסמכתא
							</c:if>
						</td>
						</tr>
						</c:forEach>
					</c:when>
 					<c:otherwise>					
           				<c:forEach items="${command.fromAdmitanceFee}" var="financialSupport" varStatus="varStatus">
           				<form:hidden path="fromAdmitanceFee[${varStatus.index}].conferenceProposalId"/>
           				<form:hidden path="fromAdmitanceFee[${varStatus.index}].type"/>

						<tr style="display: none;" class="admitanceFee financialSupport">
						<td width="200" style="border: 1px #bca2a2 dotted" align="center">
							<form:input htmlEscape="true" cssClass="greennoborder fillWidth admitanceFee fee sumPerson" path="fromAdmitanceFee[${varStatus.index}].sumPerson"/>
						</td>
						<td width="200" style="border: 1px #bca2a2 dotted" align="center">
							<form:input htmlEscape="true" cssClass="greennoborder fillWidth admitanceFee sumPersonNum" path="fromAdmitanceFee[${varStatus.index}].name"/>
						</td>
						<td width="150" style="border: 1px #bca2a2 dotted" align="center">
							<form:input htmlEscape="true" cssClass="greennoborder fillWidth admitanceFee fee sumPersons" path="fromAdmitanceFee[${varStatus.index}].sum"/>
						</td>
						<!--<td width="175" style="border: 1px #bca2a2 dotted" align="center">
							<form:select cssClass="greennoborder fillwidth" path="fromAdmitanceFee[${varStatus.index}].currency">
      						<form:option value="0">מטבע</form:option>
       						<form:option value="1">שקל</form:option>
      						<form:option value="2">דולר</form:option>
							</form:select>
						</td> -->
						<td width="250" style="border: 1px #bca2a2 dotted" align="center">
						<table>
						<tr>
							<td>
						 	<c:if test="${!readOnly && !command.submitted}">
							<span style="display: none; width: 60px; height: 26px; overflow: hidden;" class="fromAdmitanceFeeAttachFields">
							<button class="green" style="width: 59px; height: 26px; position: relative; top: -1px; left: -1px;"><a href="javascript: void(0)">עיון...</a></button>
							<input type="file" style="font-size: 50px; width: 70px; opacity: 0; filter:alpha(opacity: 0);  position: relative; top: -40px; left: -5px" class="fromAdmitanceFeeAttachFile" name="fromAdmitanceFee[${varStatus.index}].referenceFile" id="fromAdmitanceFee[${varStatus.index}].referenceFile"/>
							</span>
							</c:if>
							</td>
							<td>
							<span id="fromAdmitanceFeeAttachDiv" align="right">
							<c:if test="${fn:length(command.fromAdmitanceFee[varStatus.index].referenceFile)>0}">
								<a align="right" href="fileViewer?conferenceProposalId=${command.id}&admitanceFeeId=${varStatus.index}&attachFile=admitanceFeeAttach&contentType=${command.fromAdmitanceFee[varStatus.index].fileContentType}&attachmentId=1"
									target="_blank"><img src="image/attach.jpg"/>&nbsp;אסמכתא</a>
								<c:if test="${!readOnly && !command.submitted}">
									&nbsp;<a href="" class="deleteFromAdmitanceFeeAttachFile" id="${varStatus.index}">מחק</a>
								</c:if>
							</c:if>
							</span>
							</td>
						</tr>
						</table>
						</td>
						<td>
							<c:set var="financialSupport" value="${command.fromAdmitanceFee[varStatus.index]}"/>
							<img src="image/icon_delete.gif" class="deleteFinancialSupport" title="מחיקת שורה"/>							
						</td>
						</tr>
						</c:forEach>
					</c:otherwise>
					</c:choose>
					<tr>
						<td colspan="2" style="border: 1px #bca2a2 dotted" align="right">
						סה"כ מדמי הרשמה
						</td>
						<td style="border: 1px #bca2a2 dotted" align="right">
						<span id="fromAdmitanceFeeCount">&nbsp;</span>
						</td>
						<td style="border: 1px #bca2a2 dotted">&nbsp;</td>
					</tr>
					</table>
					<br>&nbsp;
					<table width="775" cellpadding="1" cellspacing="0" align="center">
				    <tr>
						<td width="300"  align="right">
						סה"כ ההכנסות הצפויות:
						</td>
						<td style="border: 1px #bca2a2 dotted" align="right">
						<span id="fromAllFeeCount"></span>
						</td>
				    </tr>
				    <tr><td>&nbsp;</td></tr>
				    <tr>
						<td align="right">
						סה"כ ההוצאות הצפויות:
						</td>
						<td style="border: 1px #bca2a2 dotted" align="right">
						<span id="allExpenses"></span>
						</td>
				    </tr>
				    <tr><td>&nbsp;</td></tr>
				    <tr>
						<td>
						ההפרש בין הוצאות הצפויות להכנסות הצפויות:
						</td>
						<td style="border: 1px #bca2a2 dotted" align="right">
						<span id="sumDifference"></span>
						</td>
				    </tr>
				    </table>
					<br>&nbsp;
					</td>
				</tr>
				</table>
				</td>
				</tr>
				<tr><td colspan="4"><span id="errortotalcost" dir="rtl"><p></p></span></td></tr>
				
				<tr><td>&nbsp;</td></tr>
				<tr class="form">
		       		<td colspan="4" align="right"><h3> הסיוע המבוקש מהועדה</h3></td>
				</tr>
				<tr>
				<td>
				<table width="1000"  style="border:1px #bca2a2 dotted" cellpadding="2" cellspacing="0" align="center">
	            <tr class="form">
		       		<td colspan="3" style="border:1px #bca2a2 dotted">
					מבוקשת הכרת הוועדה בכנס כבין לאומי
					<c:if test="${!readOnly && !command.submitted}">			
		       		    <form:checkbox cssClass="green" path="auditorium"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="auditorium"/>
						<input type="checkbox" disabled="disabled" value="" <c:if test="${command.auditorium}" > checked </c:if> />
					</c:if>
					&nbsp;&nbsp;
					<c:if test="${!readOnly && !command.submitted}">
					<img src="image/questionmark.png" align="top" title="הסבר על השדה" width="25" height="25" id="dialogInternational"/>
					</c:if>
					</td>
				</tr>
	            <tr class="form">
		       		<td colspan="3" style="border:1px #bca2a2 dotted">
	   				${compulsoryFieldSign}מבוקשת השתתפות הוועדה במימון הכנס בסכום:
					<c:if test="${!readOnly && !command.submitted}">			
						<form:input cssClass="green medium100" path="supportSum" id="supportSum"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="supportSum"/>
 						${command.supportSum}
					</c:if>
					דולר
					<c:if test="${!readOnly && !command.submitted}">
					<img src="image/questionmark.png" align="top" title="הסבר על השדה" width="25" height="25" id="dialogSupportSum"/>
					</c:if>
					</td>
					<!-- <td style="border:1px #bca2a2 dotted">
	   				מטבע:
					<c:if test="${!readOnly && !command.submitted}">			
        				<form:select path="supportCurrency" cssClass="green">
      					<form:option value="0">בחר/י מטבע</form:option>
      					<form:option value="1">שקל</form:option>
      					<form:option value="2">דולר</form:option>
       		        	</form:select>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="supportCurrency"/>
						<c:choose>
							<c:when test="${command.supportCurrency == 1}">שקל</c:when>
							<c:when test="${command.supportCurrency == 2}">דולר</c:when>
						</c:choose>
					</c:if>
					</td> 
					<td style="border:1px #bca2a2 dotted">&nbsp;</td>-->
				</tr>
	            <tr class="form">
		       		<td style="border:1px #bca2a2 dotted">
		       		מבוקש פטור מתשלום עבור אולם
					<c:if test="${!readOnly && !command.submitted}">			
						<form:checkbox cssClass="green" path="seminarRoom" id="seminarRoom"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="seminarRoom"/>
						<input type="checkbox" disabled="disabled"  value="" <c:if test="${command.seminarRoom}" > checked </c:if> />
					</c:if>
					<c:if test="${!readOnly && !command.submitted}">
					<img src="image/questionmark.png" align="top" title="הסבר על השדה" width="25" height="25" id="dialogRoomsNopay"/>
					</c:if>
					</td>
					<td style="border:1px #bca2a2 dotted; opacity:0.3;filter: alpha(opacity=30);" colspant="2" class="seminarRoomDetails">
					<c:if test="${!readOnly && !command.submitted}">			
	   					ל:
	   					<form:input cssClass="green medium50" path="participants" id="participants"/>
						אנשים. &nbsp;
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="participants"/>
						<c:if test="${command.seminarRoom}">
	   					ל:
	   					${command.participants}
						אנשים. &nbsp;
	   					</c:if>
					</c:if>
 					<c:if test="${!readOnly && !command.submitted}">			
        				<form:select path="prefferedCampus" cssClass="green" id="prefferedCampus">
      					<form:option value="0">בחר/י קמפוס</form:option>
      					<form:option value="1">גבעת רם</form:option>
      					<form:option value="2">הר הצופים</form:option>
      					<form:option value="3">עין כרם</form:option>
      					<form:option value="4">רחובות</form:option>
       		        	</form:select>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="prefferedCampus"/>
						<c:if test="${command.seminarRoom}">
	   				    רצוי בקמפוס:
						<c:choose>
							<c:when test="${command.prefferedCampus == 1}">גבעת רם</c:when>
							<c:when test="${command.prefferedCampus == 2}">הר הצופים</c:when>
							<c:when test="${command.prefferedCampus == 3}">עין כרם</c:when>
							<c:when test="${command.prefferedCampus == 4}">רחובות</c:when>
						</c:choose>
	   					</c:if>
					</c:if>
 					<c:if test="${!readOnly && !command.submitted}">
 					<img src="image/questionmark.png" align="top" title="הסבר על השדה" width="25" height="25" id="dialogRooms2"/>
       				</c:if>
       				</td>
				</tr>	
				</table>
				</td>
				</tr>
				<tr>
				<td colspan="4">
				<div id="errorparticipants" dir="rtl"><p></p></div>
				<div id="errorsupportsum" dir="rtl"><p></p></div>
				</td>
				</tr>


				<tr>
		             <td colspan="4"><h3>ההגשה לועדה</h3></td>
        		 </tr>
        		<tr>
        		<td colspan="4">
				<table width="1000"  style="border:1px #bca2a2 dotted" cellpadding="2" cellspacing="0" align="center">
        		<tr>
        		<td colspan="4" style="border:1px #bca2a2 dotted">
        		<table width="980">
				<tr>
					<td colspan="4">
					הערות מגיש הבקשה לועדה:
					<c:if test="${!readOnly && !command.submitted}">
					<img src="image/questionmark.png" align="top" title="הסבר על השדה" width="25" height="25" id="dialogRemarks"/>
					</c:if>
					</td>
				</tr>
				<tr>
					<c:if test="${!readOnly && !command.submitted}">			
					<td colspan="4" align="center">
						<form:textarea htmlEscape="true" cssClass="green" path="remarks" cols="80" rows="3"/>
					</td>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
	   				<td colspan="4">
						<form:hidden path="remarks"/>
	   					${command.remarks}
	   				</td>
					</c:if>
				</tr>																
				<tr><td>&nbsp;</td></tr>
	            <tr id="deanApproval" class="form">
		       		<td colspan="4">
	   				${compulsoryFieldSign}הדיקן הממליץ:
					<c:if test="${!readOnly && !command.submitted}">
					<img src="image/questionmark.png" align="top" title="הסבר על השדה" width="25" height="25" id="dialogApprover"/>
					</c:if>
					<c:if test="${!readOnly && !command.submitted}">			
        				<form:select id="deanSelect"  path="approverId" cssClass="green">
      					<form:option value="0">בחר/י דיקן ממליץ</form:option>
       					<c:forEach items="${deans}" var="deanPerson">
	        				<form:option htmlEscape="true" value="${deanPerson.id}"><c:out escapeXml="false" value="${deanPerson.title}"/></form:option>
       					</c:forEach>
       		        	</form:select>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="approverId"/>
						<c:if test="${command.approverId>0}">			
				    		${command.approver.degreeFullNameHebrew}
						</c:if>
					</c:if>
					</td>
				</tr>
				<tr>
				<td colspan="4"><div id="errordeanselect" dir="rtl"><p></p></div>
				</td>
				</tr>
				</table>
				</td>
				</tr>
				</table>
				</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				<c:if test="${admin || approver || committee}">
				<tr>
		             <td colspan="4"><h3>חוות דעת הדיקן הממליץ</h3></td>
        		 </tr>
				<tr>
				<td colspan="4">
				<table width="1000"  style="border:1px #bca2a2 dotted" cellpadding="2" cellspacing="0" align="center">
				<tr>
				<td colspan="4" style="border:1px #bca2a2 dotted">
				<table width="980">
	            <tr>
		       		<td>חוות דעת הדיקן הממליץ ${command.approver.degreeFullNameHebrew} בנוגע לבקשה:
		       		<c:if test="${command.submitted}">
					דירוג:
					${command.grade}
					&nbsp;מתוך
					${maxGrade}
					</c:if>
		       		</td>
				</tr>
				<tr>
				    <c:if test="${admin || approver}">
				    <td colspan="4" align="center">
						<form:textarea htmlEscape="true" cssClass="green" path="approverEvaluation" cols="80" rows="3"/>
					</td>
					</c:if>
					 <c:if test="${committee}">
				    <td colspan="4" align="right">
					 	<c:out value="${command.approverEvaluation}"></c:out>
					</td>
					 </c:if>
				</tr>
				</table>
				</td>
				</tr>
		       	<c:if test="${fn:length(command.deadlineRemarks)>0}">
				<tr>
				<td colspan="4" style="border:1px #bca2a2 dotted">
				<table width="980">
				<tr>
	   				<td colspan="4"> הערה כללית של הדיקן:&nbsp;&nbsp;
	   				${command.deadlineRemarks}
	   				</td>
				</tr>
				</table>
				</td>
				</tr>
				</c:if>
				</table>
				</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				</c:if>
				<c:if test="${admin}">
 				<tr>
		             <td colspan="4"><h3>הערות רכזת הועדה</h3></td>
        		 </tr>
				<tr>
				<td colspan="4">
				<table width="1000"  style="border:1px #bca2a2 dotted" cellpadding="2" cellspacing="0" align="center">
				<tr>
				<td colspan="4" style="border:1px #bca2a2 dotted">
				<table width="980">
				
				<tr>
					<td>הערות רכזת הועדה בנוגע לבקשה:</td>
				</tr>
				<tr>
					<td colspan="4" align="center">
						<form:textarea htmlEscape="true" cssClass="green" path="adminRemarks" cols="80" rows="3"/>
					</td>
				</tr>
				</table>
				</td>
				</tr>
				</table>
				</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				</c:if>


				<c:if test="${admin || committee}">
				<tr>
		             <td colspan="4"><h3>הערות חברי הועדה</h3></td>
        		 </tr>
				<tr>
				<td colspan="4">
				<table width="1000"  style="border:1px #bca2a2 dotted" cellpadding="2" cellspacing="0" align="center">
				<tr>
				<td colspan="4" style="border:1px #bca2a2 dotted">
				<table width="980">
				<tr>
					<td>הערות חברי הועדה בנוגע לבקשה:</td>
				</tr>
				<c:if test="${admin}">
 				<tr>
	   				<td colspan="4" align="center">
						<form:textarea htmlEscape="true" cssClass="green" path="committeeRemarks" cols="80" rows="3"/>
	   				</td>
				</tr>
				</c:if>
				<c:if test="${committee}">
 				<tr>
	   				<td colspan="4">
	   				${committeeRemarksWithLineBreaks}
	   				</td>
				</tr>
				<tr>
					<td>הוסף הערה:</td>
				</tr>
				<tr>
					<td colspan="4" align="center">
						<textarea htmlEscape="true" class="green newCommitteeRemarks" name="newCommitteeRemarks" cols="80" rows="3"></textarea>
					</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				</c:if>
				</table>
				</td>
				</tr>
				</table>
				</td>
				</tr>	
				</c:if>
				
				<c:if test="${!admin && !approver}">
						<form:hidden path="approverEvaluation"/>
				</c:if>
				<c:if test="${!admin}">
 						<form:hidden path="adminRemarks"/>
						<form:hidden path="committeeRemarks"/>
				</c:if>
				
				
				<c:if test="${admin && command.submitted}">
				<tr class="form">
					<td colspan="4">
				   		<input type="checkbox" class="green cancelSubmission" name="cancelSubmission"/>החזר למצב טיוטה
						<c:if test="${!command.isInsideDeadline}">			
				   			<input type="checkbox" class="green isInsideDeadline" name="isInsideDeadline"/>צרפ/י להגשות לקראת הועדה הקרובה
						</c:if>
				   	</td>
				</tr>
				</c:if>
				<c:if test="${(researcher || admin) && !command.submitted}">
				<tr class="form">
					<td colspan="4">
					<table width="100%">
					<tr><td>
		       		    <form:checkbox cssClass="green" path="acceptTerms" id="acceptTerms"/>
						</td>
						<td>
 				   		 ידוע לי שקבלת תמיכה כספית בהוצאות ארגון הכנס ו/או אישור להקצאת אולם ללא תשלום או בתשלום חלקי מותנית בפרסום <br/>
				   		  חסות האוניברסיטה בכל פרסומי הכנס ובהגשת מאזן תקציב מפורט תוך חודשיים ממועד סיום הכנס. אני מתחייב לעמוד בכך.
				   		 </td>
					</table>
 					</td>
				</tr>
				<tr>
					<td colspan="4"><div id="erroracceptTerms" dir="rtl"><p></p></div>
					</td>
				</tr>
				</c:if>
				<c:if test="${(!researcher && !admin) || command.submitted}">
					<form:hidden path="acceptTerms"/>
				</c:if>
				<tr><td>&nbsp;</td></tr>
		
		<tr class="form">
			<td colspan="4" align="center">
				<c:if test="${!command.deleted}">				
					<c:if test="${researcher && !command.submitted}">				
						<button title="שמירת פרטי ההצעה" class="grey submit" > שמירה </button>&nbsp;&nbsp;
					</c:if>
					<c:if test="${!researcher}">				
						<button title="שמירת פרטי ההצעה" class="grey submit" > שמירה </button>&nbsp;&nbsp;
					</c:if>
				</c:if>
				<c:if test="${researcher || admin}">				
					<c:if test="${!command.submitted && !command.deleted}">
						<button class="grey submitForGrading" title="שליחת ההצעה לדיקן" onclick="">הגשה</button>&nbsp;&nbsp;
					</c:if>
					<c:if test="${!command.deleted}">			
						<button class="grey delete" title="ביטול הבקשה" onclick="">ביטול הבקשה</button>&nbsp;&nbsp;
					</c:if>
				</c:if>
				<c:if test="${!readonly && !command.submitted && !command.deleted}">			
					<c:if test="${!firstVersion}">	
						<button class="grey" title="הצגת גרסה קודמת של ההצעה" onclick="window.location='editConferenceProposal.html?id=${command.id}&version=${previousVersion}';return false;"><font size="+1">&nbsp;&#x21B7;&nbsp;</font></button>
						<img src="image/questionmark.png" align="top" title="הסבר על השדה" width="25" height="25" id="dialogVersions"/>&nbsp;&nbsp;		
					</c:if>
					<c:if test="${!lastVersion}">		
						<c:if test="${nextVersionIsLast}">		
							<button class="grey" title="הצגת גרסה הבאה של ההצעה" onclick="window.location='editConferenceProposal.html?id=${command.id}';return false;"><font size="+1">&nbsp;&#x21B6;&nbsp;</font></button>&nbsp;&nbsp;
						</c:if>
						<c:if test="${!nextVersionIsLast}">		
							<button class="grey" title="הצגת גרסה הבאה של ההצעה" onclick="window.location='editConferenceProposal.html?id=${command.id}&version=${nextVersion}';return false;"><font size="+1">&nbsp;&#x21B6;&nbsp;</font></button>&nbsp;&nbsp;
						</c:if>
					</c:if>
					
				</c:if>
				<button class="grey" title="חזרה לתפריט הראשי"  onclick="window.location='welcome.html';return false;">חזרה לתפריט </button>&nbsp;&nbsp;		
				<button class="grey" title="חזרה"  onclick="history.back();return false;">חזרה לעמוד קודם </button>		
			</td>
		</tr>

    </table>
</form:form>
    </td>
  </tr>

</table>


</body>
</html>