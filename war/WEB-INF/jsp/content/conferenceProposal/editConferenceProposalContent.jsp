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
      <table  width="900" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#767468" dir="rtl">
        <tr>
          <td valign="top" align="center"><br>
            <form:form id="form" name="form" method="POST" action="editConferenceProposal.html" commandName="command" enctype="multipart/form-data">
 			<form:hidden path="id"/>
 			<form:hidden path="internalId"/>
 			<form:hidden path="versionId"/>
 			<form:hidden path="personId"/>
 			
			<authz:authorize ifAnyGranted="ROLE_CONFERENCE_APPROVER,ROLE_CONFERENCE_COMMITTEE">
 				<c:set var="readOnly" value="true"/>
 			</authz:authorize>
			<authz:authorize ifNotGranted="ROLE_CONFERENCE_APPROVER,ROLE_CONFERENCE_COMMITTEE">
 				<c:set var="readOnly" value="false"/>
 			</authz:authorize>
 			
            <table border="0" align="center" cellpadding="2" cellspacing="0">

 				<div id="genericDialog" title="עזרה" style="display:none" dir="rtl">
				<p>text put here</p>
				</div>
				
                <tr>
                  <td colspan="4" align="center"><h1>בקשה למימון לכנס</h1>
                  </td>
                </tr>
				<c:if test="${!command.isInsideDeadline}">			
                <tr class="form">
					<td colspan="4" align="right">
						<font color="red">הצעה זו הוגשה אחרי תאריך היעד להגשות לקראת כינוס הועדה הקרובה</font>
					</td>
				</tr>
				</c:if>
                
                <tr class="form">
					<td nowrap>
						מספר בקשה:
					${command.id}
					</td>
					<td>
						מספר פנימי:
					${command.internalId}
					</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
		             <td colspan="4"><img src="image/hr.gif" width="100%" height="10"></td>
        		</tr>
                <tr class="form">
					<th colspan="4" align="right">פרטי המבקש</th>
				</tr>
                <tr class="form">
					<td>
						 שם חוקר:
						${command.researcher.degreeHebrew } ${command.researcher.firstNameHebrew } ${command.researcher.lastNameHebrew }
					</td>
					<td>
						 מחלקה:
					${command.researcher.department } 
					</td>
				</tr>
				<tr class="form">
					<td>
						 פקולטה:
						${faculty } 
					</td>
					<td>
						 טלפון:
						${command.researcher.phone } 
					</td>
					<td>
						 פקס:
						${command.researcher.fax } 
					</td>
					<td nowrap>
						 דואר אלקטרוני:
						${command.researcher.email } 
					</td>
				</tr>
				<tr class="form">
					<td>
						 הגוף היוזם:
					<c:if test="${!readOnly && !command.submitted}">
						<form:input cssClass="green" path="initiatingBody" /><img src="image/icon-docs-info.gif" id="dialogInitiatingBody"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">
						${command.initiatingBody}
					</c:if>
					</td>
					
					<td>
						 תפקיד בגוף היוזם:
					<c:if test="${!readOnly && !command.submitted}">
       					<form:select path="initiatingBodyRole" cssClass="green" >
      						<form:option value="0">בחר/י תפקיד</form:option>
      						<form:option value="1">מנהל גוף</form:option>
      						<form:option value="2">עובד בגוף</form:option>
      						<form:option value="3">ראש הגוף</form:option>
      						<form:option value="4">חבר בגוף</form:option>
      						<form:option value="5">חבר ניהולי</form:option>
       		        	</form:select>
       		        	<img src="image/icon-docs-info.gif" id="dialogInitiatingBodyRole"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">
						<c:choose>
							<c:when test="${command.initiatingBodyRole == 1}">מנהל גוף</c:when>
							<c:when test="${command.initiatingBodyRole == 2}">עובד בגוף</c:when>
							<c:when test="${command.initiatingBodyRole == 3}">ראש הגוף</c:when>
							<c:when test="${command.initiatingBodyRole == 4}">חבר בגוף</c:when>
							<c:when test="${command.initiatingBodyRole == 5}">חבר ניהולי</c:when>
						</c:choose>
					</c:if>
					</td>
				</tr>
			 	<tr>
		             <td colspan="4"><img src="image/hr.gif" width="100%" height="10"></td>
        		 </tr>
                <tr class="form">
					<th colspan="4" align="right">פרטים על הכנס</th>
				</tr>
				<tr class="form">
					<td colspan="4" align="right">
						 נושא הכנס (באנגלית):
					</td>
				</tr>
				<tr class="form">
					<c:if test="${!readOnly && !command.submitted}">
					<td colspan="4">
						<form:textarea cssClass="green autosaveclass" path="subject" id="subject" cols="120" rows="2"/>
					</td>
					</c:if>
					<c:if test="${readOnly || command.submitted}">
					<td colspan="4">
						${command.subject}
					</td>
					</c:if>
				</tr>
				<tr>
				<td colspan="4"><div id="errorsubject" title="שגיאה" dir="rtl"><p></p></div>
				</td>
				</tr>

				<tr class="form">
					<td nowrap>
						תחילת הכנס:
					<c:if test="${!readOnly && !command.submitted}">
						<input type="text" class="green" name="startConfDate" id="startConfDate" value="${startConfDate}"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">
						${startConfDate}
					</c:if>
					</td>
					<td nowrap>
						סיום הכנס:
					<c:if test="${!readOnly && !command.submitted}">
						<input type="text" class="green" name="endConfDate" id="endConfDate" value="${endConfDate}"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">
						${endConfDate}
					</c:if>
					</td>
					<td nowrap>
						 מיקום:
					<c:if test="${!readOnly && !command.submitted}">
      					<form:select path="location" cssClass="green">
      						<form:option value="0">בחר/י מיקום</form:option>
      						<form:option value="1">אוניברסיטה</form:option>
      						<form:option value="2">ירושלים</form:option>
      						<form:option value="3">מקום אחר, פרט:</form:option>
        		    	</form:select>
					</c:if>
					<c:if test="${readOnly || command.submitted}">
						<c:choose>
							<c:when test="${command.location == 1}">אוניברסיטה</c:when>
							<c:when test="${command.location == 2}">ירושלים</c:when>
							<c:when test="${command.location == 3}">אחר</c:when>
						</c:choose>
					</c:if>
					</td>
					<td nowrap>
						 פירוט מיקום:
					<c:if test="${!readOnly && !command.submitted}">
						<form:input cssClass="green" path="locationDetail"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">
						${command.locationDetail}
					</c:if>
					</td>
				</tr>
				<tr><td>&nbsp;</td>
				</tr>
				<tr class="form">
					<td colspan="2">
				       <table border="1" cellpadding="2" cellspacing="0">
				       <tr>
				       <td colspan="3">מספר משתתפים:</td>
				       </tr>
				       <tr>
				       <td></td>
				       <td> מרצים </td> 
				       <td> מוזמנים </td>
					   </tr>
						<tr>
						<td>
							מחו""ל:
						</td>
						<c:if test="${!readOnly && !command.submitted}">
						<td>
							<form:input cssClass="green" path="foreignLecturers" />
						</td>
						</c:if>
						<c:if test="${readOnly || command.submitted}">
						<td>
							${command.foreignLecturers}
						</td>
						</c:if>
						<c:if test="${!readOnly && !command.submitted}">
						<td>
							<form:input cssClass="green" path="foreignGuests" />
						</td>
						</c:if>
						<c:if test="${readOnly || command.submitted}">
						<td>
							${command.foreignGuests}
						</td>
						</c:if>
						</tr>
						<tr>
						<td>
							מהארץ:
						</td>
						<c:if test="${!readOnly && !command.submitted}">
						<td>
							<form:input cssClass="green" path="localLecturers" />
						</td>
						</c:if>
						<c:if test="${readOnly || command.submitted}">
						<td>
							${command.localLecturers}
						</td>
						</c:if>
						<c:if test="${!readOnly && !command.submitted}">
						<td>
							<form:input cssClass="green" path="localGuests" />
						</td>
						</c:if>
						<c:if test="${readOnly || command.submitted}">
						<td>
							${command.localGuests}
						</td>
						</c:if>
						</tr>
						<tr>
						<td>
							קהל נוסף:
						</td>
						<c:if test="${!readOnly && !command.submitted}">
						<td>
							<form:input cssClass="green" path="audienceLecturers" />
						</td>
						</c:if>
						<c:if test="${readOnly || command.submitted}">
						<td>
							${command.audienceLecturers}
						</td>
						</c:if>
						<c:if test="${!readOnly && !command.submitted}">
						<td>
							<form:input cssClass="green" path="audienceGuests" />
						</td>
						</c:if>
						<c:if test="${readOnly || command.submitted}">
						<td>
							${command.audienceGuests}
						</td>
						</c:if>
						</tr>
						</table>
 				</td>

				<td colspan="2">
				<table>
					<tr class="form">
				       <td>רשימת מוזמנים:
				       </td>
						<td colspan="3">
						<c:if test="${!readOnly && !command.submitted}">
						<input style="width: 0px; height: 0px;" class="green" type="file" name="guestsAttach" id="guestsAttach"/>
						<button class="green guestsAttach">עיון ...</button>
						
						</c:if>
						<span id="guestsAttachDiv">
						<c:if test="${fn:length(command.guestsAttach)>0}">
							<a href="fileViewer?conferenceProposalId=${command.id}&attachFile=guestsAttach&contentType=${command.guestsAttachContentType}&attachmentId=1"
								target="_blank"><img src="image/icon_somefile.gif"/>&nbsp;רשימת מוזמנים</a>
						</c:if>
						</span>
						</td>
					</tr>
					<tr class="form">
				       <td>תוכנית הכנס:
				       </td>
						<td colspan="3">
						<c:if test="${!readOnly && !command.submitted}">
						<input style="width: 0px; height: 0px;" class="green" type="file" name="programAttach" id="programAttach"/>
						<button class="green programAttach">עיון ...</button>  
						</c:if>
						<span id="programAttachDiv">
						<c:if test="${fn:length(command.programAttach)>0}">
						  <a href="fileViewer?conferenceProposalId=${command.id}&attachFile=programAttach&contentType=${command.programAttachContentType}&attachmentId=1"
							target="_blank"><img src="image/icon_somefile.gif"/>&nbsp;תוכנית הכנס</a>
						</c:if>
						</span>
						</td>
					</tr>
					<tr class="form">
				       <td>תוכנית תקציבית:
				       </td>
						<td colspan="3">
						<c:if test="${!readOnly && !command.submitted}">
						<input style="width: 0px; height: 0px;" class="green" type="file" name="financialAttach" id="financialAttach"/>
						<button class="green financialAttach">עיון ...</button> 
						</c:if>
						<span id="financialAttachDiv">
						<c:if test="${fn:length(command.financialAttach)>0}">
						  <a href="fileViewer?conferenceProposalId=${command.id}&attachFile=financialAttach&contentType=${command.financialAttachContentType}&attachmentId=1"
							target="_blank"><img src="image/icon_somefile.gif"/>&nbsp;תוכנית תקציבית</a>
						</c:if>
						</span>
						</td>
					</tr>
				
				</table>
				</td>
				</tr>
				
				
				<tr class="form">
					<td colspan="4"> 
						 יש לפרט את התוכן העיוני של הכנס וחשיבותו לתחום:
					</td>
				</tr>
				<tr class="form">
					<c:if test="${!readOnly && !command.submitted}">
					<td colspan="4">
						<form:textarea cssClass="green" path="description" cols="120" rows="4"/>
					</td>
					</c:if>
					<c:if test="${readOnly || command.submitted}">
					<td colspan="4">
						${command.description}
					</td>
					</c:if>
				</tr>
				<tr>
		             <td colspan="4"><img src="image/hr.gif" width="100%" height="10"></td>
        		 </tr>
				
	            <tr class="form">
		       		<th colspan="4" align="right">תקציב הכנס</td>
				</tr>
	            <tr class="form">
		       		<td colspan="4">
	   					סה"כ התקציב לארגון הכנס:
					<c:if test="${!readOnly && !command.submitted}">
						<form:input cssClass="green" path="totalCost"  id="totalCost"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">
						${command.totalCost}
					</c:if>

	   					מטבע:
					<c:if test="${!readOnly && !command.submitted}">
        				<form:select path="totalCostCurrency" cssClass="green">
      					<form:option value="0">בחר/י מטבע</form:option>
      					<form:option value="1">שקל</form:option>
      					<form:option value="2">דולר</form:option>
       		        	</form:select>
					</c:if>
					<c:if test="${readOnly || command.submitted}">
						<c:choose>
							<c:when test="${command.totalCostCurrency == 1}">שקל</c:when>
							<c:when test="${command.totalCostCurrency == 2}">דולר</c:when>
						</c:choose>
					</c:if>
	   				</td>
				</tr>
				<tr>
				<td colspan="4"><div id="errortotalcost" dir="rtl"><p></p></div>
				</td>
				</tr>
				
	            <tr class="form">
		       		<td>
	   					הכנסות צפויות:
	   				</td>
				</tr>

				<tr class="form">
					<td colspan="4">משותפים לארגון:
					<table border="1" cellpadding="1" cellspacing="0">
				    <tr>
				    <th> שם </th> 
				    <th> סכום </th>
				    <th> מטבע </th>
				    <th></th>
					</tr>
					<c:if test="${readOnly || command.submitted}">
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
					</c:if>
					<c:if test="${!readOnly && !command.submitted}">
           				<c:forEach items="${command.fromAssosiate}" var="financialSupport">
						<tr>
						<td>
							<input type="text" class="green autosaveclass" name="financialSupport_name_${financialSupport.id}" value="${financialSupport.name}"/>
						</td>
						<td>
							<input type="text" class="green autosaveclass" name="financialSupport_sum_${financialSupport.id}" value="${financialSupport.sum}"/>
						</td>
						<td>
       					   <select class="green autosaveclass" name="financialSupport_currency_${financialSupport.id}">
      						<option value="0">מטבע</option>
      						<option value="1" <c:if test="${financialSupport.currency==1}">selected</c:if>>שקל</option>
      						<option value="2" <c:if test="${financialSupport.currency==2}">selected</c:if>>דולר</option>
       		        		</select>
						</td>
						<td>
							<img src="image/icon_delete.gif" class="deleteFinancialSupport" id="${financialSupport.id}"/>
						</td>
						</tr>
						</c:forEach>
						<tr>
						<td>
							<input type="text" class="green fromAssosiateSave" name="fromAssosiate_name"/>
						</td>
						<td>
							<input type="text" class="green fromAssosiateSave" name="fromAssosiate_sum"/>
						</td> 
						<td>
        					<select class="green fromAssosiateSave" name="fromAssosiate_currency" >
      						<option value="0">מטבע</option>
      						<option value="1">שקל</option>
      						<option value="2">דולר</option>
       		        		</select>
						</td>
						</tr>
					</c:if>
					</table>
					</td>
				</tr>

				<tr class="form">
					<td colspan="4">ממממן חיצוני:
					<table border="1" cellpadding="1" cellspacing="0">
				    <tr>
				    <th> שם </th> 
				    <th> סכום </th>
				    <th> מטבע </th>
					</tr>
					<c:if test="${readOnly || command.submitted}">
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
					</c:if>
					<c:if test="${!readOnly && !command.submitted}">
          				<c:forEach items="${command.fromExternal}" var="financialSupport">
						<tr>
						<td>
							<input type="text" class="green autosaveclass" name="financialSupport_name_${financialSupport.id}" value="${financialSupport.name}"/>
						</td>
						<td>
							<input type="text" class="green autosaveclass" name="financialSupport_sum_${financialSupport.id}" value="${financialSupport.sum}"/>
						</td>
						<td>
       					   <select class="green autosaveclass" name="financialSupport_currency_${financialSupport.id}">
      						<option value="0">מטבע</option>
      						<option value="1" <c:if test="${financialSupport.currency==1}">selected</c:if>>שקל</option>
      						<option value="2" <c:if test="${financialSupport.currency==2}">selected</c:if>>דולר</option>
       		        		</select>
						</td>
						<td>
							<img src="image/icon_delete.gif" class="deleteFinancialSupport" id="${financialSupport.id}"/>
						</td>
						</tr>
						</c:forEach>
						<tr>
						<td>
							<input type="text" class="green fromExternalSave" name="fromExternal_name"/>
						</td>
						<td>
							<input type="text" class="green fromExternalSave" name="fromExternal_sum"/>
						</td>
						<td>
        					<select class="green fromExternalSave" name="fromExternal_currency" >
      						<option value="0">מטבע</option>
       						<option value="1" <c:if test="${financialSupport.currency==1}">selected</c:if>>שקל</option>
      						<option value="2" <c:if test="${financialSupport.currency==2}">selected</c:if>>דולר</option>
       		        		</select>
						</td>
						</tr>					
					</c:if>
					</table>
					</td>
				</tr>

				<tr class="form">
					<td colspan="4">מדמי הרשמה:
					<table border="1" cellpadding="1" cellspacing="0">
				    <tr>
				    <th> שם </th> 
				    <th> סכום </th>
				    <th> מטבע </th>
					</tr>
 					<c:if test="${readOnly || command.submitted}">
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
					</c:if>
					<c:if test="${!readOnly && !command.submitted}">
           				<c:forEach items="${command.fromAdmitanceFee}" var="financialSupport">
						<tr>
						<td>
							<input type="text" class="green autosaveclass" name="financialSupport_name_${financialSupport.id}" value="${financialSupport.name}"/>
						</td>
						<td>
							<input type="text" class="green autosaveclass" name="financialSupport_sum_${financialSupport.id}" value="${financialSupport.sum}"/>
						</td>
						<td>
       					   <select class="green autosaveclass" name="financialSupport_currency_${financialSupport.id}">
      						<option value="0">מטבע</option>
      						<option value="1" <c:if test="${financialSupport.currency==1}">selected</c:if>>שקל</option>
      						<option value="2" <c:if test="${financialSupport.currency==2}">selected</c:if>>דולר</option>
       		        		</select>
						</td>
						<td>
							<img src="image/icon_delete.gif" class="deleteFinancialSupport" id="${financialSupport.id}"/>
						</td>
						</tr>
						</c:forEach>
						<tr>
						<td>
							<input type="text" class="green fromAdmitanceFeeSave" name="fromAdmitanceFee_name" value="משתתפים"/>
						</td>
						<td>
							<input type="text" class="green fromAdmitanceFeeSave" name="fromAdmitanceFee_sum" />
						</td>
						<td>
        					<select name="fromAdmitanceFee_currency" class="green fromAdmitanceFeeSave">
      						<option value="0">מטבע</option>
       						<option value="1" <c:if test="${financialSupport.currency==1}">selected</c:if>>שקל</option>
      						<option value="2" <c:if test="${financialSupport.currency==2}">selected</c:if>>דולר</option>
       		        		</select>
						</td>
						</tr>					
					</c:if>
					</table>
					</td>
				</tr>

				<tr>
		             <td colspan="4"><img src="image/hr.gif" width="100%" height="10"></td>
        		 </tr>
	            <tr class="form">
		       		<th colspan="4" align="right">ועדה מארגנת</th>
				</tr>
				<tr class="form">
					<td colspan="4">ועדה מדעית:
					<table border="1" cellpadding="1" cellspacing="0">
				    <tr>
				    <th> שם </th> 
				    <th> מוסד </th>
				    <th> תפקיד במוסד </th>
				    <th> תפקיד בועדה </th>
				    <th></th>
					</tr>
 					<c:if test="${readOnly || command.submitted}">
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
					</c:if>
 					<c:if test="${!readOnly && !command.submitted}">					
           				<c:forEach items="${command.scientificCommittees}" var="committee">
						<tr>
						<td>
							<input type="text" class="green autosaveclass" name="committee_name_${committee.id}" value="${committee.name}"/>
						</td>
						<td>
							<input type="text" class="green autosaveclass" name="committee_institute_${committee.id}" value="${committee.institute}"/>
						</td>
						<td>
							<input type="text" class="green autosaveclass" name="committee_instituteRole_${committee.id}" value="${committee.instituteRole}"/>
						</td>
						<td>
							<input type="text" class="green autosaveclass" name="committee_committeeRole_${committee.id}" value="${committee.committeeRole}"/>
						</td>					
						<td>
							<img src="image/icon_delete.gif" class="deleteCommittee" id="${committee.id}"/>
						</td>
						</tr>
						</c:forEach>
						<tr>
						<td>
							<input type="text" class="green scientificCommitteeSave" name="scientificCommittee_name"/>
						</td>
						<td>
							<input type="text" class="green scientificCommitteeSave" name="scientificCommittee_institute"/>
						</td>
						<td>
							<input type="text" class="green scientificCommitteeSave" name="scientificCommittee_instituteRole"/>
						</td>
						<td>
							<input type="text" class="green scientificCommitteeSave" name="scientificCommittee_committeeRole"/>
						</td>
						</tr>		
					</c:if>
					</table>
					</td>
				</tr>

				<tr class="form">
					<td colspan="4">ועדה מבצעת:
					<table border="1" cellpadding="1" cellspacing="0">
				    <tr>
				    <th> שם </th> 
				    <th> מוסד </th>
				    <th> תפקיד במוסד </th>
				    <th> תפקיד בועדה </th>
					</tr>
 					<c:if test="${readOnly || command.submitted}">
           				<c:forEach items="${command.operationalCommittees}" var="committee">
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
					</c:if>
					<c:if test="${!readOnly && !command.submitted}">
           				<c:forEach items="${command.operationalCommittees}" var="committee">
						<tr>
						<td>
							<input type="text" class="green autosaveclass" name="committee_name_${committee.id}" value="${committee.name}"/>
						</td>
						<td>
							<input type="text" class="green autosaveclass" name="committee_institute_${committee.id}" value="${committee.institute}"/>
						</td>
						<td>
							<input type="text" class="green autosaveclass" name="committee_instituteRole_${committee.id}" value="${committee.instituteRole}"/>
						</td>
						<td>
							<input type="text" class="green autosaveclass" name="committee_committeeRole_${committee.id}" value="${committee.committeeRole}"/>
						</td>					
						<td>
							<img src="image/icon_delete.gif" class="deleteCommittee" id="${committee.id}"/>
						</td>
						</tr>
						</c:forEach>
						<tr>
						<td>
							<input type="text" class="green operationalCommitteeSave" name="operationalCommittee_name"/>
						</td>
						<td>
							<input type="text" class="green operationalCommitteeSave" name="operationalCommittee_institute"/>
						</td>
						<td>
							<input type="text" class="green operationalCommitteeSave" name="operationalCommittee_instituteRole"/>
						</td>
						<td>
							<input type="text" class="green operationalCommitteeSave" name="operationalCommittee_committeeRole"/>
						</td>
						</tr>						
					</c:if>
					</table>
					</td>
				</tr>

				<tr>
		             <td colspan="4"><img src="image/hr.gif" width="100%" height="10"></td>
        		 </tr>
				<tr class="form">
		       		<th colspan="4" align="right">
	   					סוג הסיוע המבוקש
	   				</th>
				</tr>
	            <tr class="form">
		       		<td nowrap>
	   					סכום הסיוע המבוקש:
					<c:if test="${!readOnly && !command.submitted}">			
						<form:input cssClass="green" path="supportSum" id="supportSum"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						${command.supportSum}
					</c:if>
					</td>
		       		<td>
	   					מטבע:
					<c:if test="${!readOnly && !command.submitted}">			
        				<form:select path="supportCurrency" cssClass="green">
      					<form:option value="0">בחר/י מטבע</form:option>
      					<form:option value="1">שקל</form:option>
      					<form:option value="2">דולר</form:option>
       		        	</form:select>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<c:choose>
							<c:when test="${command.supportCurrency == 1}">שקל</c:when>
							<c:when test="${command.supportCurrency == 2}">דולר</c:when>
						</c:choose>
					</c:if>
					</td>
				</tr>
				<tr>
				<td colspan="4"><div id="errorsupportsum" dir="rtl"><p></p></div>
				</td>
				</tr>
	            <tr class="form">
		       		<td colspan="4">
					<c:if test="${!readOnly && !command.submitted}">			
		       		    אולם<form:checkbox cssClass="green" path="auditorium"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						אולם<input type="checkbox" disabled="disabled" value="" <c:if test="${command.auditorium}" > checked </c:if> />
					</c:if>
					<c:if test="${!readOnly && !command.submitted}">			
						חדר סמנירים<form:checkbox cssClass="green" path="seminarRoom"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						חדר סמנירים<input type="checkbox" disabled="disabled" value="" <c:if test="${command.seminarRoom}" > checked </c:if> />
					</c:if>
	   					ל:
					<c:if test="${!readOnly && !command.submitted}">			
	   					<form:input cssClass="green" path="participants" id="participants"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
	   					${command.participants}
					</c:if>
	   				 אנשים,
        				בקמפוס מועדף:
 					<c:if test="${!readOnly && !command.submitted}">			
        				<form:select path="prefferedCampus" cssClass="green">
      					<form:option value="0">בחר/י קמפוס</form:option>
      					<form:option value="1">גבעת רם</form:option>
      					<form:option value="2">הר הצופים</form:option>
      					<form:option value="3">עין כרם</form:option>
      					<form:option value="4">רחובות</form:option>
       		        	</form:select>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<c:choose>
							<c:when test="${command.prefferedCampus == 1}">גבעת רם</c:when>
							<c:when test="${command.prefferedCampus == 2}">הר הצופים</c:when>
							<c:when test="${command.prefferedCampus == 3}">עין כרם</c:when>
							<c:when test="${command.prefferedCampus == 4}">רחובות</c:when>
						</c:choose>
					</c:if>
       			</td>
				</tr>	
				<tr>
				<td colspan="4"><div id="errorparticipants" dir="rtl"><p></p></div>
				</td>
				</tr>
				<tr>
		             <td colspan="4"><img src="image/hr.gif" width="100%" height="10"></td>
        		 </tr>
	            <tr class="form">
		       		<th colspan="4" align="right">
	   					ארגון הכנס
	   				</th>
				</tr>
	            <tr class="form">
					<c:if test="${!readOnly && !command.submitted}">			
		       		<td colspan="2">
		       		    ארגון ע"י חברה מסחרית<form:checkbox cssClass="green" id="company" path="organizingCompany"/>
					</td>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
		       		<td colspan="2">
		       		    ארגון ע"י חברה מסחרית<input type="checkbox" disabled="disabled" id="companyViewOnly" value="" <c:if test="${command.organizingCompany}">checked</c:if> />
					</td>
					</c:if>
				</tr>
				
	             <tr class="form organizingCompanyPart">
		       		<td>
	   					שם החברה:&nbsp;&nbsp;&nbsp;
					<c:if test="${!readOnly && !command.submitted}">			
	   					<form:input cssClass="green" path="organizingCompanyName" />
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
	   					${command.organizingCompanyName}
					</c:if>
					</td>
		       		<td>
	   				טלפון:&nbsp;
					<c:if test="${!readOnly && !command.submitted}">			
	   					<form:input cssClass="green" path="organizingCompanyPhone" id="organizingCompanyPhone"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
	   					${command.organizingCompanyPhone}
					</c:if>
					</td>
		       		<td>
	   					פקס:&nbsp;
					<c:if test="${!readOnly && !command.submitted}">			
	   					<form:input cssClass="green" path="organizingCompanyFax" id="organizingCompanyFax"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
	   					${command.organizingCompanyFax}
					</c:if>
	   				</td>
				    <td>
	   					כתובת אימייל:
					<c:if test="${!readOnly && !command.submitted}">			
	   					<form:input cssClass="green" path="organizingCompanyEmail" id="organizingCompanyEmail"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
	   					${command.organizingCompanyEmail}
					</c:if>
	   				</td>
				</tr>
				<tr>
				<td colspan="4">
				<div id="errororganizingCompanyPhone" dir="rtl"><p></p></div>
				<div id="errororganizingCompanyFax" dir="rtl"><p></p></div>
				<div id="errororganizingCompanyEmail" dir="rtl"><p></p></div>
				</td>
				</tr>
	            <tr class="form">
		       		<td>
	   					שם איש קשר:
					<c:if test="${!readOnly && !command.submitted}">			
	   					<form:input cssClass="green" path="contactPerson" />
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
	   					${command.contactPerson}
					</c:if>
					</td>
		       		<td>
	   					תפקיד:
					<c:if test="${!readOnly && !command.submitted}">			
	   					<form:input cssClass="green" path="contactPersonRole" />
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
	   					${command.contactPersonRole}
					</c:if>
					</td>
		       		<td nowrap>
	   					טלפון:
					<c:if test="${!readOnly && !command.submitted}">			
	   					<form:input cssClass="green" path="contactPersonPhone" id="contactPersonPhone"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
	   					${command.contactPersonPhone}
					</c:if>
					</td>
				    <td>
	   					כתובת אימייל:
					<c:if test="${!readOnly && !command.submitted}">			
	   					<form:input cssClass="green" path="contactPersonEmail" id="contactPersonEmail"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
	   					${command.contactPersonEmail}
 					</c:if>
					</td>
				</tr>
				<tr>
				<td colspan="4">
				<div id="errorcontactPersonPhone" dir="rtl"><p></p></div>
				<div id="errorcontactPersonEmail" dir="rtl"><p></p></div>
				</td>
				</tr>			
				<tr>
		             <td colspan="4"><img src="image/hr.gif" width="100%" height="10"></td>
        		 </tr>
				<tr class="form">
					<td>
						 הערות לועדה:
					</td>
				</tr>
				<tr>
					<c:if test="${!readOnly && !command.submitted}">			
					<td colspan="4" >
						<form:textarea cssClass="green" path="remarks" cols="120" rows="3"/>
					</td>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
	   				<td colspan="3">
	   					${command.remarks}
	   				</td>
					</c:if>
				</tr>																
	            <tr id="deanApproval" class="form">
		       		<td>
	   				הגורם המאשר:
					<c:if test="${!readOnly && !command.submitted}">			
        				<form:select id="deanSelect"  path="approverId" cssClass="green">
      					<form:option value="0">בחר/י גורם מאשר</form:option>
       					<c:forEach items="${deans}" var="deanPerson">
	        				<form:option htmlEscape="true" value="${deanPerson.id}" ><c:out escapeXml="false" value="${deanPerson.degreeFullNameHebrew}"/> - <c:out escapeXml="false" value="${deanPerson.title}"/></form:option>
       					</c:forEach>
       		        	</form:select>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
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
				
				<c:if test="${!readOnly && !command.submitted}">			
				<tr class="form">
					<td colspan="4" align="center">
				   		<button class="grey submit" onclick="">שמירה </button>&nbsp;&nbsp;
						<button class="grey submitForGrading" onclick="">הגשה</button>
 					</td>
				</tr>
				</c:if>
				<tr>
		             <td colspan="4"><img src="image/hr.gif" width="100%" height="10"></td>
        		 </tr>
				
				<authz:authorize ifAnyGranted="ROLE_CONFERENCE_ADMIN">			
				<c:if test="${command.submitted}">			
				<tr class="form">
					<td></td>
					<td>
				   		<input type="checkbox" class="green" name="cancelSubmission"/>ביטול הגשה
 					</td>
					<c:if test="${!command.isInsideDeadline}">			
					<td colspan=2>
				   		<input type="checkbox" class="green" name="isInsideDeadline"/>צרפ/י להגשות לקראת הועדה הקרובה
				   	</td>
					</c:if>
				</tr>
				</c:if>
				</authz:authorize>
				
				<authz:authorize ifAnyGranted="ROLE_CONFERENCE_ADMIN,ROLE_CONFERENCE_APPROVER">
	            <tr class="form">
		       		<td>
	   					חוות דעת הדיקן המאשר:
	   				</td>
				</tr>
				<tr>
				    <td colspan="4">
						<form:textarea cssClass="green" path="approverEvaluation" cols="120" rows="3"/>
					</td>
				</tr>
				</authz:authorize>
				<authz:authorize ifAnyGranted="ROLE_CONFERENCE_ADMIN">
				<tr class="form">
					<td>הערות הרשות למו"פ:</td>
				</tr>
				<tr>
					<td colspan="4">
						<form:textarea cssClass="green" path="adminRemarks" cols="120" rows="3"/>
					</td>
				</tr>
				</authz:authorize>
				<authz:authorize ifAnyGranted="ROLE_CONFERENCE_ADMIN,ROLE_CONFERENCE_APPROVER,ROLE_CONFERENCE_COMMITTEE">
				<tr class="form">
					<td>הערות הועדה:</td>
				</tr>
				<tr>
	   				<td colspan="4">
	   					${command.committeeRemarks}
	   				</td>
				</tr>
				<tr>
					<td>הוסף הערה:</td>
				</tr>
				<tr>
					<td colspan="4">
						<textarea class="green" name="newCommitteeRemarks" cols="120" rows="1"></textarea>
					</td>
				</tr>
				</authz:authorize>
				<authz:authorize ifAnyGranted="ROLE_CONFERENCE_ADMIN,ROLE_CONFERENCE_APPROVER,ROLE_CONFERENCE_COMMITTEE">
				<tr class="form">
					<td colspan="4" align="center">
				   		<button class="grey submitFaculty" onclick="">שמירה</button>
 					</td>
				</tr>
				</authz:authorize>
				
				<tr class="form">
					<td colspan="4" align="left">
						<a href="welcome.html">חזרה לתפריט</a>
 					</td>
				</tr>
		<tr>
			<td>&nbsp;
			</td>
		</tr>

		<c:if test="${!command.submitted}">			
		<tr>
		<td colspan="4" align="center">
		<c:if test="${!firstVersion}">			
				<a href="editConferenceProposal.html?id=${command.id}&version=${previousVersion}">צפה בגרסה קודמת</a>
		</c:if>
			&nbsp;&nbsp;
		<c:if test="${!lastVersion}">			
				<a href="editConferenceProposal.html?id=${command.id}&version=${nextVersion}">צפה בגרסה הבאה</a>
		</c:if>
		</td>
		</tr>
		</c:if>



	
    </table>
</form:form>
    </td>
  </tr>

</table>


</body>
</html>