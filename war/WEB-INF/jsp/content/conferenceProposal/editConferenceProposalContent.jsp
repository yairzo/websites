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
      <table width="900" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#767468" dir="rtl">
        <tr>
          <td valign="top" align="center"><br>
            <form:form id="form" name="form" method="POST" action="editConferenceProposal.html" commandName="command" enctype="multipart/form-data">
 			<form:hidden path="id"/>
 			<form:hidden path="internalId"/>
 			<form:hidden path="versionId"/>
 			<form:hidden path="personId"/>
 			<form:hidden path="deadline"/>
 			
			<authz:authorize ifAnyGranted="ROLE_CONFERENCE_APPROVER,ROLE_CONFERENCE_COMMITTEE">
 				<c:set var="readOnly" value="true"/>
 			</authz:authorize>
			<authz:authorize ifNotGranted="ROLE_CONFERENCE_APPROVER,ROLE_CONFERENCE_COMMITTEE">
 				<c:set var="readOnly" value="false"/>
 			</authz:authorize>
 			
            <table border="0" align="center" cellpadding="2" cellspacing="0">

				<div id="genericDialog" title="עזרה" style="display:none" dir="rtl"></div>
				
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
					<td>
					מספר פנימי:
					${internalIdString}
					</td>
				</tr>

                <tr class="form">
					<td colspan="4" align="right"><h3>פרטי המבקש</h3></td>
				</tr>
                <tr class="form">
                <td colspan="4">
                <table width="900" style="border:1px black dotted" cellpadding="2" cellspacing="0" align="center">
                <tr class="form">
					<td style="border:1px black dotted">
						 <font>שם חוקר:</font>
						${command.researcher.degreeHebrew } ${command.researcher.firstNameHebrew } ${command.researcher.lastNameHebrew }
					</td>
					<td style="border:1px black dotted">
						 <font>מחלקה:</font>
					${command.researcher.department } 
					</td>
					<td style="border:1px black dotted">
						 <font>פקולטה:</font>
						${faculty } 
					</td>
				</tr>
                <tr class="form">
					<td style="border:1px black dotted">
						 <font>טלפון:</font>
						${command.researcher.phone } 
					</td>
					<td style="border:1px black dotted">
						 <font>פקס:</font>
						${command.researcher.fax } 
					</td>
					<td  style="border:1px black dotted" nowrap>
						 <font>דואר אלקטרוני:</font>
						${command.researcher.email } 
					</td>
				</tr>
				<tr class="form">
					<td style="border:1px black dotted" nowrap>
						<font> הגוף היוזם:</font>
					<c:if test="${!readOnly && !command.submitted}">
						<form:input htmlEscape="true" cssClass="green" path="initiatingBody" /><img src="image/icon-docs-info.gif" id="dialogInitiatingBody"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">
						<form:hidden path="initiatingBody"/>
 						${command.initiatingBody}
					</c:if>
					</td>
					
					<td style="border:1px black dotted" nowrap>
						 <font>תפקיד בגוף היוזם:</font>
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
						<form:hidden path="initiatingBodyRole"/>
						<c:choose>
							<c:when test="${command.initiatingBodyRole == 1}">מנהל גוף</c:when>
							<c:when test="${command.initiatingBodyRole == 2}">עובד בגוף</c:when>
							<c:when test="${command.initiatingBodyRole == 3}">ראש הגוף</c:when>
							<c:when test="${command.initiatingBodyRole == 4}">חבר בגוף</c:when>
							<c:when test="${command.initiatingBodyRole == 5}">חבר ניהולי</c:when>
						</c:choose>
					</c:if>
					</td>
					<td style="border:1px black dotted">&nbsp;</td>
				</tr>
				</table>
				</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
                <tr class="form">
					<td colspan="4" align="right"><h3>פרטים על הכנס</h3></td>
				</tr>
				<tr class="form">
				<td colspan="4">
				<table width="900" style="border:1px black dotted" cellpadding="2" cellspacing="0" align="center">
				<tr>
				<td colspan="4" style="border:1px black dotted">
				<table>
				<tr>
					<td colspan="4">
						 <font>נושא הכנס (באנגלית):</font>
					</td>
				</tr>
				<tr class="form">
					<c:if test="${!readOnly && !command.submitted}">
					<td colspan="4">
						<form:textarea cssClass="green" path="subject" id="subject" cols="95" rows="2"/>
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
					<td style="border:1px black dotted" align="center">
						תחילת הכנס:
					<c:if test="${!readOnly && !command.submitted}">
						<input type="text" class="green medium100" name="startConfDate" id="startConfDate" value="${startConfDate}" readonly="readonly"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">
						${startConfDate}
					</c:if>
					</td>
					<td style="border:1px black dotted" align="center">
						סיום הכנס:
					<c:if test="${!readOnly && !command.submitted}">
						<input type="text" class="green medium100" name="endConfDate" id="endConfDate" value="${endConfDate}" readonly="readonly"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">
						${endConfDate}
					</c:if>
					</td>
					<td style="border:1px black dotted" align="center">
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
						<form:hidden path="location"/>
						<c:choose>
							<c:when test="${command.location == 1}">אוניברסיטה</c:when>
							<c:when test="${command.location == 2}">ירושלים</c:when>
							<c:when test="${command.location == 3}">אחר</c:when>
						</c:choose>
					</c:if>
					</td>
					<td style="border:1px black dotted" align="center">
						פירוט מיקום:
					<c:if test="${!readOnly && !command.submitted}">
						<form:input htmlEscape="true" cssClass="green" path="locationDetail"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">
						<form:hidden path="locationDetail"/>
						${command.locationDetail}
					</c:if>
					</td>
				</tr>
				<tr class="form">
					<td colspan="2" style="border:1px black dotted">
					   <br>
				       <table style="border: 1px black dotted" cellpadding="2" cellspacing="0" align="center">
				       <tr>
				       <td style="border: 1px black dotted" colspan="3">מספר משתתפים:</td>
				       </tr>
				       <tr>
				       <td style="border: 1px black dotted">&nbsp;</td>
				       <td style="border: 1px black dotted"> מרצים </td> 
				       <td style="border: 1px black dotted"> מוזמנים </td>
					   </tr>
						<tr>
						<td style="border: 1px black dotted">
							מחו"ל:
						</td>
						<c:if test="${!readOnly && !command.submitted}">
						<td style="border: 1px black dotted">
							<form:input cssClass="green medium50" path="foreignLecturers" />
						</td>
						</c:if>
						<c:if test="${readOnly || command.submitted}">
						<td style="border: 1px black dotted">
							<form:hidden path="foreignLecturers"/>
							${command.foreignLecturers}
						</td>
						</c:if>
						<c:if test="${!readOnly && !command.submitted}">
						<td style="border: 1px black dotted">
							<form:input cssClass="green medium50" path="foreignGuests" />
						</td>
						</c:if>
						<c:if test="${readOnly || command.submitted}">
						<td style="border: 1px black dotted">
							<form:hidden path="foreignGuests"/>
							${command.foreignGuests}
						</td>
						</c:if>
						</tr>
						<tr>
						<td style="border: 1px black dotted">
							מהארץ:
						</td>
						<c:if test="${!readOnly && !command.submitted}">
						<td style="border: 1px black dotted">
							<form:input cssClass="green medium50" path="localLecturers" />
						</td>
						</c:if>
						<c:if test="${readOnly || command.submitted}">
						<td style="border: 1px black dotted">
							<form:hidden path="localLecturers"/>
							${command.localLecturers}
						</td>
						</c:if>
						<c:if test="${!readOnly && !command.submitted}">
						<td style="border: 1px black dotted">
							<form:input cssClass="green medium50" path="localGuests" />
						</td>
						</c:if>
						<c:if test="${readOnly || command.submitted}">
						<td style="border: 1px black dotted">
							<form:hidden path="localGuests"/>
							${command.localGuests}
						</td>
						</c:if>
						</tr>
						<tr>
						<td style="border: 1px black dotted">
							קהל נוסף:
						</td>
						<c:if test="${!readOnly && !command.submitted}">
						<td style="border: 1px black dotted">
							<form:input cssClass="green medium50" path="audienceLecturers" />
						</td>
						</c:if>
						<c:if test="${readOnly || command.submitted}">
						<td style="border: 1px black dotted">
							<form:hidden path="audienceLecturers"/>
							${command.audienceLecturers}
						</td>
						</c:if>
						<c:if test="${!readOnly && !command.submitted}">
						<td style="border: 1px black dotted">
							<form:input cssClass="green medium50" path="audienceGuests" />
						</td>
						</c:if>
						<c:if test="${readOnly || command.submitted}">
						<td style="border: 1px black dotted">
							<form:hidden path="audienceGuests"/>
							${command.audienceGuests}
						</td>
						</c:if>
						</tr>
						</table>
						<br>
 				    </td>

				<td colspan="2" style="border:1px black dotted">
				<table align="center">
					<tr class="form">
				       <td><font>רשימת מוזמנים:</font></td>
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
				       <td><font>תוכנית הכנס:</font></td>
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
				</table>
				</td>
				</tr>
				
				<tr>
				<td colspan="4" style="border:1px black dotted">
				<table>
				<tr>
					<td colspan="4"> 
						יש לפרט את התוכן העיוני של הכנס וחשיבותו לתחום:
					</td>
				</tr>
				<tr class="form">
					<c:if test="${!readOnly && !command.submitted}">
					<td colspan="4" >
						<form:textarea htmlEscape="true" cssClass="green" path="description" cols="95" rows="4"/>
					</td>
					</c:if>
					<c:if test="${readOnly || command.submitted}">
					<td colspan="4" >
						<form:hidden path="description"/>
						${command.description}
					</td>
					</c:if>
				</tr>
				</table>
				</td>
				</tr>
				</table>
				</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
	            <tr class="form">
		       		<td colspan="4" align="right"><h3>תקציב הכנס</h3></td>
				</tr>
	            <tr class="form">
	            	<td colspan="4">
					<table width="900" style="border:1px black dotted" cellpadding="2" cellspacing="0" align="center">
	            	<tr class="form">
		       		<td style="border:1px black dotted" align="center">
	   					סה"כ התקציב לארגון הכנס:
					<c:if test="${!readOnly && !command.submitted}">
						<form:input cssClass="green medium100" path="totalCost"  id="totalCost"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">
						<form:hidden path="totalCost"/>
						${command.totalCost}
					</c:if>

	   				<font>מטבע:</font>
					<c:if test="${!readOnly && !command.submitted}">
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
					</c:if>
	   				</td>
					<td style="border:1px black dotted">
				       תוכנית תקציבית:
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
				<tr style="display: none;">
				<td colspan="4"><div id="errortotalcost" dir="rtl"><p></p></div>
				</td>


				<tr>
				<td colspan="4" style="border:1px black dotted">
				<table width="900"  cellpadding="2" cellspacing="0" align="center">
				</tr>
	            <tr>
		       		<td>
	   					<font>הכנסות צפויות:</font>
	   				</td>
				</tr>

				<tr>
					<td colspan="4">משותפים לארגון:
					<table width="500" cellpadding="1" cellspacing="0" align="center">
				    <tr>
				    <th width="150" style="border: thin black dotted"> שם </th> 
				    <th width="150" style="border: thin black dotted"> סכום </th>
				    <th width="150" style="border: thin black dotted"> מטבע </th>
					</tr>
					<c:choose>
 					<c:when test="${readOnly || command.submitted}">
           				<c:forEach items="${command.fromAssosiate}" var="fromAssosiate">
						<tr>
						<td style="border: thin black dotted">
							<c:out value="${fromAssosiate.name}"></c:out>
						</td>
						<td style="border: thin black dotted">
							<c:out value="${fromAssosiate.sum}"></c:out>
						</td>
						<td style="border: thin black dotted">
							<c:if test="${fromAssosiate.currency==1}">שקל</c:if>
							<c:if test="${fromAssosiate.currency==2}">דולר</c:if>
						</td>
						</tr>
						</c:forEach>
					</c:when>
 					<c:otherwise>					
           				<c:forEach items="${command.fromAssosiate}" var="financialSupport" varStatus="varStatus">
           				<form:hidden path="fromAssosiate[${varStatus.index}].conferenceProposalId"/>
           				<form:hidden path="fromAssosiate[${varStatus.index}].type"/>

						<tr style="display: none;" class="assosiate financialSupport">

						<td style="border: 1px black dotted">
							<form:input htmlEscape="true" cssClass="green assosiate" path="fromAssosiate[${varStatus.index}].name"/>
						</td>
						<td style="border: 1px black dotted">
							<form:input htmlEscape="true" cssClass="green assosiate" path="fromAssosiate[${varStatus.index}].sum"/>
						</td>
						<td style="border: 1px black dotted">
							<form:select cssClass="green medium170" path="fromAssosiate[${varStatus.index}].currency">
      						<form:option value="0">מטבע</form:option>
       						<form:option value="1">שקל</form:option>
      						<form:option value="2">דולר</form:option>
							</form:select>
						</td>
						<td>
							<c:set var="financialSupport" value="${command.fromAssosiate[varStatus.index]}"/>
							<img src="image/icon_delete.gif" class="deleteFinancialSupport"/>							
						</td>
						</tr>
						</c:forEach>
					</c:otherwise>
					</c:choose>
					</table>
					</td>
				</tr>

				<tr class="form">
					<td colspan="4">ממממן חיצוני:
					<table width="500" cellpadding="1" cellspacing="0" align="center">
				    <tr>
				    <th width="150" style="border: 1px black dotted"> שם </th> 
				    <th width="150" style="border: 1px black dotted"> סכום </th>
				    <th width="150" style="border: 1px black dotted"> מטבע </th>
					</tr>
					<c:choose>
 					<c:when test="${readOnly || command.submitted}">
           			<c:forEach items="${command.fromExternal}" var="fromExternal">
						<tr>
						<td style="border: thin black dotted">
						<c:out value="${fromExternal.name}"></c:out>
						</td>
						<td style="border: thin black dotted">
						<c:out value="${fromExternal.sum}"></c:out>
						</td>
						<td style="border: thin black dotted">
						<c:if test="${fromExternal.currency==1}">שקל</c:if>
						<c:if test="${fromExternal.currency==2}">דולר</c:if>
						</td>
						</tr>
					</c:forEach>
					</c:when>
 					<c:otherwise>					
           				<c:forEach items="${command.fromExternal}" var="financialSupport" varStatus="varStatus">
           				<form:hidden path="fromExternal[${varStatus.index}].conferenceProposalId"/>
           				<form:hidden path="fromExternal[${varStatus.index}].type"/>

						<tr style="display: none;" class="external financialSupport">
						<td style="border: 1px black dotted">
							<form:input htmlEscape="true" cssClass="green external" path="fromExternal[${varStatus.index}].name"/>
						</td>
						<td style="border: 1px black dotted">
							<form:input htmlEscape="true" cssClass="green external" path="fromExternal[${varStatus.index}].sum"/>
						</td>
						<td style="border: 1px black dotted">
							<form:select cssClass="green medium170" path="fromExternal[${varStatus.index}].currency">
      						<form:option value="0">מטבע</form:option>
       						<form:option value="1">שקל</form:option>
      						<form:option value="2">דולר</form:option>
							</form:select>
						</td>
						<td>
							<c:set var="financialSupport" value="${command.fromExternal[varStatus.index]}"/>
							<img src="image/icon_delete.gif" class="deleteFinancialSupport"/>							
						</td>
						</tr>
						</c:forEach>
					</c:otherwise>
					</c:choose>
					</table>
					</td>
				</tr>
				<tr>
					<td colspan="4">מדמי הרשמה:
					<table width="500" cellpadding="1" cellspacing="0" align="center">
				    <tr>
				    <th width="150" style="border: 1px black dotted"> שם </th> 
				    <th width="150" style="border: 1px black dotted"> סכום </th>
				    <th width="150" style="border: 1px black dotted"> מטבע </th>
					</tr>
 					<c:choose>
 					<c:when test="${readOnly || command.submitted}">
           				<c:forEach items="${command.fromAdmitanceFee}" var="fromAdmitanceFee">
						<tr>
						<td style="border: thin black dotted">
						<c:out value="${fromAdmitanceFee.name}"></c:out>
						</td>
						<td style="border: thin black dotted">
						<c:out value="${fromAdmitanceFee.sum}"></c:out>
						</td>
						<td style="border: thin black dotted">
						<c:if test="${fromAdmitanceFee.currency==1}">שקל</c:if>
						<c:if test="${fromAdmitanceFee.currency==2}">דולר</c:if>
						</td>
						</tr>
						</c:forEach>
					</c:when>
 					<c:otherwise>					
           				<c:forEach items="${command.fromAdmitanceFee}" var="financialSupport" varStatus="varStatus">
           				<form:hidden path="fromAdmitanceFee[${varStatus.index}].conferenceProposalId"/>
           				<form:hidden path="fromAdmitanceFee[${varStatus.index}].type"/>

						<tr style="display: none;" class="admitanceFee financialSupport">
						<td style="border: 1px black dotted">
							<form:input htmlEscape="true" cssClass="green admitanceFee" path="fromAdmitanceFee[${varStatus.index}].name"/>
						</td>
						<td style="border: 1px black dotted">
							<form:input htmlEscape="true" cssClass="green admitanceFee" path="fromAdmitanceFee[${varStatus.index}].sum"/>
						</td>
						<td style="border: 1px black dotted">
							<form:select cssClass="green medium170" path="fromAdmitanceFee[${varStatus.index}].currency">
      						<form:option value="0">מטבע</form:option>
       						<form:option value="1">שקל</form:option>
      						<form:option value="2">דולר</form:option>
							</form:select>
						</td>
						<td>
							<c:set var="financialSupport" value="${command.fromAdmitanceFee[varStatus.index]}"/>
							<img src="image/icon_delete.gif" class="deleteFinancialSupport"/>							
						</td>
						</tr>
						</c:forEach>
					</c:otherwise>
					</c:choose>
					</table>
					</td>
				</tr>
				</table>
				</td>
				</tr>
				</table>
				</td>
				</tr>

	            <tr class="form">
		       		<td colspan="4" align="right"><h3>ועדה מארגנת</h3></td>
				</tr>
				<tr>
				<td colspan="4" >
				<table width="900"  style="border:1px black dotted" cellpadding="2" cellspacing="0" align="center">
				<tr>
					<td colspan="4" style="border:1px black dotted">
					ועדה מדעית:
 					<table width="600"  cellpadding="1" cellspacing="0" align="center">
				    <tr>
				    <th width="150" style="border: 1px black dotted"> שם </th> 
				    <th width="150" style="border: 1px black dotted"> מוסד </th>
				    <th width="150" style="border: 1px black dotted"> תפקיד במוסד </th>
				    <th width="150" style="border: 1px black dotted"> תפקיד בועדה </th>
					</tr>
 					<c:choose>
 					<c:when test="${readOnly || command.submitted}">
           				<c:forEach items="${command.scientificCommittees}" var="committee">
						<tr>
						<td style="border: thin black dotted">
							<c:out value="${committee.name}"></c:out>
						</td>
						<td style="border: thin black dotted">
							<c:out value="${committee.institute}"></c:out>
						</td>
						<td style="border: thin black dotted">
							<c:out value="${committee.instituteRole}"></c:out>
						</td>
						<td style="border: thin black dotted">
							<c:out value="${committee.committeeRole}"></c:out>
						</td>					
						</tr>
						</c:forEach>
					</c:when>
 					<c:otherwise>					
           				<c:forEach items="${command.scientificCommittees}" var="committee" varStatus="varStatus">
           				<form:hidden path="scientificCommittees[${varStatus.index}].conferenceProposalId"/>
           				<form:hidden path="scientificCommittees[${varStatus.index}].type"/>
						<tr style="display: none;" class="scientificCommittee committee">
						<td style="border: 1px black dotted">
							<form:input htmlEscape="true" cssClass="green scientificCommittee" path="scientificCommittees[${varStatus.index}].name"/>
						</td>
						<td style="border: 1px black dotted">
							<form:input htmlEscape="true" cssClass="green scientificCommittee" path="scientificCommittees[${varStatus.index}].institute"/>
						</td>
						<td style="border: 1px black dotted">
							<form:input htmlEscape="true" cssClass="green scientificCommittee" path="scientificCommittees[${varStatus.index}].instituteRole"/>
						</td>
						<td style="border: 1px black dotted">
							<form:input htmlEscape="true" cssClass="green scientificCommittee" path="scientificCommittees[${varStatus.index}].committeeRole"/>
						</td>					
						<td>
							<c:set var="committee" value="${command.scientificCommittees[varStatus.index]}"/>
							<img src="image/icon_delete.gif" class="deleteCommittee"/>							
						</td>
						</tr>
						</c:forEach>
					</c:otherwise>
					</c:choose>
					</table>
					<br>
					ועדה מארגנת:
 					<table width="600" cellpadding="1" cellspacing="0" align="center">
				    <tr>
				    <th width="150" style="border: 1px black dotted"> שם </th> 
				    <th width="150" style="border: 1px black dotted"> מוסד </th>
				    <th width="150" style="border: 1px black dotted"> תפקיד במוסד </th>
				    <th width="150" style="border: 1px black dotted"> תפקיד בועדה </th>
					</tr>
 					<c:choose>
 					<c:when test="${readOnly || command.submitted}">
           				<c:forEach items="${command.operationalCommittees}" var="committee">
						<tr>
						<td style="border: thin black dotted">
							<c:out value="${committee.name}"></c:out>
						</td>
						<td style="border: thin black dotted">
							<c:out value="${committee.institute}"></c:out>
						</td>
						<td style="border: thin black dotted">
							<c:out value="${committee.instituteRole}"></c:out>
						</td>
						<td style="border: thin black dotted">
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

						<td style="border: 1px black dotted">
							<form:input htmlEscape="true" cssClass="green operationalCommittee" path="operationalCommittees[${varStatus.index}].name"/>
						</td>
						<td style="border: 1px black dotted">
							<form:input htmlEscape="true" cssClass="green operationalCommittee" path="operationalCommittees[${varStatus.index}].institute"/>
						</td>
						<td style="border: 1px black dotted">
							<form:input htmlEscape="true" cssClass="green operationalCommittee" path="operationalCommittees[${varStatus.index}].instituteRole"/>
						</td>
						<td style="border: 1px black dotted">
							<form:input htmlEscape="true" cssClass="green operationalCommittee" path="operationalCommittees[${varStatus.index}].committeeRole"/>
						</td>					
						<td>
							<c:set var="committee" value="${command.operationalCommittees[varStatus.index]}"/>
							<img src="image/icon_delete.gif" class="deleteCommittee"/>							
						</td>
						</tr>
						</c:forEach>
					</c:otherwise>
					</c:choose>
					</table>
					</td>
				</tr>
				</table>
				</td>
				</tr>
				
				<tr><td>&nbsp;</td></tr>
				<tr class="form">
		       		<td colspan="4" align="right"><h3>סוג הסיוע המבוקש</h3></td>
				</tr>
				<tr>
				<td>
				<table width="900"  style="border:1px black dotted" cellpadding="2" cellspacing="0" align="center">
	            <tr class="form">
		       		<td width="300" style="border:1px black dotted">
	   				סכום הסיוע המבוקש:
					<c:if test="${!readOnly && !command.submitted}">			
						<form:input cssClass="green medium100" path="supportSum" id="supportSum"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="supportSum"/>
 						${command.supportSum}
					</c:if>
					</td>
					<td style="border:1px black dotted">
	   				<font>מטבע:</font>
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
					<td style="border:1px black dotted">&nbsp;</td>
				</tr>
				<tr style="display:none;">
				<td colspan="4"><div id="errorsupportsum" dir="rtl"><p></p></div>
				</td>
				</tr>
	            <tr class="form">
		       		<td style="border:1px black dotted">
					<c:if test="${!readOnly && !command.submitted}">			
		       		    אולם<form:checkbox cssClass="green" path="auditorium"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="auditorium"/>
						אולם<input type="checkbox" disabled="disabled" value="" <c:if test="${command.auditorium}" > checked </c:if> />
					</c:if>
					&nbsp;&nbsp;
					<c:if test="${!readOnly && !command.submitted}">			
						חדר סמנירים<form:checkbox cssClass="green" path="seminarRoom"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="seminarRoom"/>
						חדר סמנירים<input type="checkbox" disabled="disabled" value="" <c:if test="${command.seminarRoom}" > checked </c:if> />
					</c:if>
					</td>
					<td style="border:1px black dotted">
	   					מספר משתתפים:
					<c:if test="${!readOnly && !command.submitted}">			
	   					<form:input cssClass="green medium50" path="participants" id="participants"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="participants"/>
	   					${command.participants}
					</c:if>
					</td>
					<td style="border:1px black dotted">
	   				  קמפוס מועדף:
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
						<form:hidden path="prefferedCampus"/>
						<c:choose>
							<c:when test="${command.prefferedCampus == 1}">גבעת רם</c:when>
							<c:when test="${command.prefferedCampus == 2}">הר הצופים</c:when>
							<c:when test="${command.prefferedCampus == 3}">עין כרם</c:when>
							<c:when test="${command.prefferedCampus == 4}">רחובות</c:when>
						</c:choose>
					</c:if>
       				</td>
				</tr>	
				<tr style="display:none;">
				<td colspan="4"><div id="errorparticipants" dir="rtl"><p></p></div>
				</td>
				</tr>
				</table>
				</td>
				</tr>
				<tr><td>&nbsp;</td></tr>

	            <tr class="form">
		       		<td colspan="4" align="right">
	   					<h3>ארגון הכנס</h3>
	   				</td>
				</tr>
	            <tr>
	            <td colspan="4">
				<table width="900"  style="border:1px black dotted" cellpadding="2" cellspacing="0" align="center">
	            <tr>
	            	<td colspan="4" style="border:1px black dotted">
					<c:if test="${!readOnly && !command.submitted}">			
		       		   <font> ארגון ע"י חברה מסחרית</font><form:checkbox cssClass="green" id="company" path="organizingCompany"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
					<form:hidden path="organizingCompany"/>
		       		   <font> ארגון ע"י חברה מסחרית</font><input type="checkbox" disabled="disabled" id="companyViewOnly" value="" <c:if test="${command.organizingCompany}">checked</c:if> />
					</c:if>
					</td>
				</tr>
	            <tr class="form organizingCompanyPart">
		       		<td nowrap style="border:1px black dotted">שם החברה:&nbsp;&nbsp;&nbsp;&nbsp;
					<c:if test="${!readOnly && !command.submitted}">			
	   					<form:input htmlEscape="true" cssClass="green medium150" path="organizingCompanyName" />
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="organizingCompanyName"/>
	   					${command.organizingCompanyName}
					</c:if>
					</td>
		       		<td style="border:1px black dotted">טלפון:&nbsp;&nbsp;
					<c:if test="${!readOnly && !command.submitted}">			
	   					<form:input htmlEscape="true" cssClass="green medium150" path="organizingCompanyPhone" id="organizingCompanyPhone"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="organizingCompanyPhone"/>
	   					${command.organizingCompanyPhone}
					</c:if>
					</td>
		       		<td style="border:1px black dotted">פקס:&nbsp;
					<c:if test="${!readOnly && !command.submitted}">			
	   					<form:input htmlEscape="true" cssClass="green medium150" path="organizingCompanyFax" id="organizingCompanyFax"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="organizingCompanyFax"/>
	   					${command.organizingCompanyFax}
					</c:if>
	   				</td>
				    <td style="border:1px black dotted">אימייל:
					<c:if test="${!readOnly && !command.submitted}">			
	   					<form:input htmlEscape="true" cssClass="green medium200" path="organizingCompanyEmail" id="organizingCompanyEmail"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="organizingCompanyEmail"/>
	   					${command.organizingCompanyEmail}
					</c:if>
	   				</td>
				</tr>
	            <tr class="form">
		       		<td nowrap style="border:1px black dotted">שם איש קשר:
					<c:if test="${!readOnly && !command.submitted}">			
	   					<form:input htmlEscape="true" cssClass="green medium150" path="contactPerson" />
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="contactPerson"/>
	   					${command.contactPerson}
					</c:if>
					</td>
		       		<td nowrap style="border:1px black dotted">תפקיד:
					<c:if test="${!readOnly && !command.submitted}">			
	   					<form:input htmlEscape="true" cssClass="green medium150" path="contactPersonRole" />
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="contactPersonRole"/>
	   					${command.contactPersonRole}
					</c:if>
					</td>
		       		<td nowrap style="border:1px black dotted">טלפון:
					<c:if test="${!readOnly && !command.submitted}">			
	   					<form:input htmlEscape="true" cssClass="green medium150" path="contactPersonPhone" id="contactPersonPhone"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="contactPersonPhone"/>
	   					${command.contactPersonPhone}
					</c:if>
					</td>
				    <td nowrap style="border:1px black dotted">אימייל:
					<c:if test="${!readOnly && !command.submitted}">			
	   					<form:input htmlEscape="true" cssClass="green medium200" path="contactPersonEmail" id="contactPersonEmail"/>
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
				<div id="errorcontactPersonPhone" dir="rtl"><p></p></div>
				<div id="errorcontactPersonEmail" dir="rtl"><p></p></div>
				</td>
				</tr>	

				<tr>
		             <td colspan="4"><h3>הגשה</h3></td>
        		 </tr>
        		<tr>
        		<td colspan="4">
				<table width="900"  style="border:1px black dotted" cellpadding="2" cellspacing="0" align="center">
        		<tr>
        		<td colspan="4" style="border:1px black dotted">
        		<table>
				<tr>
					<td colspan="4">הערות לועדה:
					</td>
				</tr>
				<tr>
					<c:if test="${!readOnly && !command.submitted}">			
					<td colspan="4" >
						<form:textarea htmlEscape="true" cssClass="green" path="remarks" cols="95" rows="3"/>
					</td>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
	   				<td colspan="4">
						<form:hidden path="remarks"/>
	   					${command.remarks}
	   				</td>
					</c:if>
				</tr>																
	            <tr id="deanApproval" class="form">
		       		<td colspan="4">
	   				<font>הגורם המאשר:</font>
					<c:if test="${!readOnly && !command.submitted}">			
        				<form:select id="deanSelect"  path="approverId" cssClass="green">
      					<form:option value="0">בחר/י גורם מאשר</form:option>
       					<c:forEach items="${deans}" var="deanPerson">
	        				<form:option htmlEscape="true" value="${deanPerson.id}" ><c:out escapeXml="false" value="${deanPerson.degreeFullNameHebrew}"/> - <c:out escapeXml="false" value="${deanPerson.title}"/></form:option>
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
				
				<authz:authorize ifAnyGranted="ROLE_CONFERENCE_ADMIN,ROLE_CONFERENCE_APPROVER,ROLE_CONFERENCE_COMMITTEE">
				<tr>
		             <td colspan="4"><h3>&nbsp;</h3></td>
        		 </tr>
				<tr>
				<td colspan="4">
				<table width="900"  style="border:1px black dotted" cellpadding="2" cellspacing="0" align="center">
				<tr>
				<td colspan="4" style="border:1px black dotted">
				<table>

				<authz:authorize ifAnyGranted="ROLE_CONFERENCE_ADMIN">			
				<c:if test="${command.submitted}">			
				<tr class="form">
					<td>
				   		<input type="checkbox" class="green" name="cancelSubmission"/><font>ביטול הגשה</font>
 					</td>
					<c:if test="${!command.isInsideDeadline}">			
					<td colspan=3>
				   		<input type="checkbox" class="green" name="isInsideDeadline"/><font>צרפ/י להגשות לקראת הועדה הקרובה</font>
				   	</td>
					</c:if>
				</tr>
				</c:if>
				</authz:authorize>
				
				<authz:authorize ifAnyGranted="ROLE_CONFERENCE_ADMIN,ROLE_CONFERENCE_APPROVER">
	            <tr>
		       		<td>חוות דעת הדיקן המאשר:</td>
				</tr>
				<tr>
				    <td colspan="4">
						<form:textarea htmlEscape="true" cssClass="green" path="approverEvaluation" cols="95" rows="3"/>
					</td>
				</tr>
				</authz:authorize>
				<authz:authorize ifAnyGranted="ROLE_CONFERENCE_ADMIN">
				<tr>
					<td>הערות הרשות למו"פ:</td>
				</tr>
				<tr>
					<td colspan="4">
						<form:textarea htmlEscape="true" cssClass="green" path="adminRemarks" cols="95" rows="3"/>
					</td>
				</tr>
				</authz:authorize>
				<authz:authorize ifAnyGranted="ROLE_CONFERENCE_ADMIN,ROLE_CONFERENCE_APPROVER,ROLE_CONFERENCE_COMMITTEE">
				<tr>
					<td>הערות הועדה:</td>
				</tr>
				<tr>
	   				<td colspan="4">
	   					${command.committeeRemarks}
	   				</td>
				</tr>
				<tr>
					<td><font>הוסף הערה:</font></td>
				</tr>
				<tr>
					<td colspan="4">
						<textarea htmlEscape="true" class="green" name="newCommitteeRemarks" cols="95" rows="1"></textarea>
					</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				</authz:authorize>
				</table>
				</td>
				</tr>
				</table>
				</td>
				</tr>	
				</authz:authorize>
		
		<tr class="form">
			<td colspan="4" align="center">
				<authz:authorize ifAnyGranted="ROLE_CONFERENCE_RESEARCHER">
					<c:if test="${!command.submitted}">
						<button class="grey submit" >שמירה </button>&nbsp;&nbsp;
					</c:if>
				</authz:authorize>
				<authz:authorize ifNotGranted="ROLE_CONFERENCE_RESEARCHER">
					<button class="grey submit" >שמירה </button>&nbsp;&nbsp;
				</authz:authorize>
				<authz:authorize ifAnyGranted="ROLE_CONFERENCE_RESEARCHER,ROLE_CONFERENCE_ADMIN">
					<c:if test="${!command.submitted}">
						<button class="grey submitForGrading" onclick="">הגשה</button>
					</c:if>
				</authz:authorize>
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

		<tr class="form">
			<td colspan="4" align="left">
				<a href="welcome.html">חזרה לתפריט</a>
 			</td>
		</tr>
	
    </table>
</form:form>
    </td>
  </tr>

</table>


</body>
</html>