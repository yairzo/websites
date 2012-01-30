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
					<!--  <td nowrap>
						<font>מספר בקשה:</font>
					${command.id}
					</td>-->
					<td>
					מספר פנימי:
					${internalIdString}
					</td>
					<td></td>
					<td></td>
				</tr>

                <tr class="form">
					<td colspan="4" align="right"><h3>פרטי המבקש</h3></td>
				</tr>
                <tr class="form">
                <td colspan="4">
                <table width="800" cellpadding="2" cellspacing="0">
                <tr class="form">
					<td>
						 <font>שם חוקר:</font>
						${command.researcher.degreeHebrew } ${command.researcher.firstNameHebrew } ${command.researcher.lastNameHebrew }
					</td>
					<td>
						 <font>מחלקה:</font>
					${command.researcher.department } 
					</td>
					<td>
						 <font>פקולטה:</font>
						${faculty } 
					</td>
				</tr>
                <tr class="form">
					<td>
						 <font>טלפון:</font>
						${command.researcher.phone } 
					</td>
					<td>
						 <font>פקס:</font>
						${command.researcher.fax } 
					</td>
					<td nowrap>
						 <font>דואר אלקטרוני:</font>
						${command.researcher.email } 
					</td>
				</tr>
				<tr class="form">
					<td nowrap>
						<font> הגוף היוזם:</font>
					<c:if test="${!readOnly && !command.submitted}">
						<form:input cssClass="green" path="initiatingBody" /><img src="image/icon-docs-info.gif" id="dialogInitiatingBody"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">
						<form:hidden path="initiatingBody"/>
 						${command.initiatingBody}
					</c:if>
					</td>
					
					<td nowrap>
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
				</tr>
				</table>
				</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
                <tr class="form">
					<td colspan="4" align="right"><h3>פרטים על הכנס</h3></td>
				</tr>
				<tr class="form">
					<td colspan="4" align="right">
						 <font>נושא הכנס (באנגלית):</font>
					</td>
				</tr>
				<tr class="form">
					<c:if test="${!readOnly && !command.submitted}">
					<td colspan="4">
						<form:textarea cssClass="green autosaveclass" path="subject" id="subject" cols="100" rows="2"/>
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

				<tr class="form">
					<td colspan="4">
					<table width="900">
					<tr>
					<td>
						<font>תחילת הכנס:</font>
					<c:if test="${!readOnly && !command.submitted}">
						<input type="text" class="green medium100" name="startConfDate" id="startConfDate" value="${startConfDate}"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">
						${startConfDate}
					</c:if>
					</td>
					<td>
					<font>	סיום הכנס:</font>
					<c:if test="${!readOnly && !command.submitted}">
						<input type="text" class="green medium100" name="endConfDate" id="endConfDate" value="${endConfDate}"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">
						${endConfDate}
					</c:if>
					</td>
					<td>
						<font> מיקום:</font>
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
					<td>
						<font> פירוט מיקום:
					<c:if test="${!readOnly && !command.submitted}">
						<form:input cssClass="green" path="locationDetail"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">
						<form:hidden path="locationDetail"/>
						${command.locationDetail}
					</c:if>
					</td>
					</tr>
					</table>
					</td>
				</tr>
				<tr><td>&nbsp;</td>
				</tr>
				<tr class="form">
					<td colspan="2">
				       <table style="border: 1px black dotted" cellpadding="2" cellspacing="0">
				       <tr>
				       <td style="border: 1px black dotted" colspan="3">מספר משתתפים:</td>
				       </tr>
				       <tr>
				       <td style="border: 1px black dotted">  </td>
				       <td style="border: 1px black dotted"> מרצים </td> 
				       <td style="border: 1px black dotted"> מוזמנים </td>
					   </tr>
						<tr>
						<td style="border: 1px black dotted">
							מחו"ל:
						</td>
						<c:if test="${!readOnly && !command.submitted}">
						<td style="border: 1px black dotted">
							<form:input cssClass="green" path="foreignLecturers" />
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
							<form:input cssClass="green" path="foreignGuests" />
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
							<form:input cssClass="green" path="localLecturers" />
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
							<form:input cssClass="green" path="localGuests" />
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
							<form:input cssClass="green" path="audienceLecturers" />
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
							<form:input cssClass="green" path="audienceGuests" />
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
 				</td>

				<td colspan="2">
				<table>
					<tr class="form">
				       <td><font>רשימת מוזמנים:</font>
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
				       <td><font>תוכנית הכנס:</font>
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

				
				</table>
				</td>
				</tr>
				
				
				<tr class="form">
					<td colspan="4"> 
						<font> יש לפרט את התוכן העיוני של הכנס וחשיבותו לתחום:</font>
					</td>
				</tr>
				<tr class="form">
					<c:if test="${!readOnly && !command.submitted}">
					<td colspan="4">
						<form:textarea cssClass="green" path="description" cols="100" rows="4"/>
					</td>
					</c:if>
					<c:if test="${readOnly || command.submitted}">
					<td colspan="4">
						<form:hidden path="description"/>
						${command.description}
					</td>
					</c:if>
				</tr>
				<tr><td>&nbsp;</td></tr>
	            <tr class="form">
		       		<td colspan="4" align="right"><h3>תקציב הכנס</h3></td>
				</tr>
	            <tr class="form">
		       		<td colspan="2">
	   					<font>סה"כ התקציב לארגון הכנס:</font>
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
				<td colspan="2">
				<table>
					<tr class="form">
				       <td><font>תוכנית תקציבית:</font>
				       </td>
						<td>
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
				<tr>
				<td colspan="4"><div id="errortotalcost" dir="rtl"><p></p></div>
				</td>
				</tr>

	            <tr class="form">
		       		<td>
	   					<font>הכנסות צפויות:</font>
	   				</td>
				</tr>

				<tr class="form">
					<td colspan="4">משותפים לארגון:
					<table width="500" style="border: 1px black dotted" cellpadding="1" cellspacing="0" align="center">
				    <tr>
				    <th width="150" style="border: thin black dotted"> שם </th> 
				    <th width="150" style="border: thin black dotted"> סכום </th>
				    <th width="150" style="border: thin black dotted"> מטבע </th>
					</tr>
					<c:if test="${readOnly || command.submitted}">
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
       					   <select class="green autosaveclass medium170" name="financialSupport_currency_${financialSupport.id}">
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
        					<select class="green fromAssosiateSave medium170" name="fromAssosiate_currency" >
      						<option value="0">מטבע</option>
      						<option value="1">שקל</option>
      						<option value="2">דולר</option>
       		        		</select>
						</td>
						</tr>
						<tr>
						<td>
							<input type="text" class="green fromAssosiateSave" name="fromAssosiate_name2"/>
						</td>
						<td>
							<input type="text" class="green fromAssosiateSave" name="fromAssosiate_sum2"/>
						</td> 
						<td>
        					<select class="green fromAssosiateSave medium170" name="fromAssosiate_currency2" >
      						<option value="0">מטבע</option>
      						<option value="1">שקל</option>
      						<option value="2">דולר</option>
       		        		</select>
						</td>
						</tr>					
						<tr><td><a href="#" class="fromAssosiateSave">הוסף שורות</a></td></tr>
						</c:if>
					</table>
					</td>
				</tr>

				<tr class="form">
					<td colspan="4">ממממן חיצוני:
					<table width="500" style="border: 1px black dotted" cellpadding="1" cellspacing="0" align="center">
				    <tr>
				    <th width="150" style="border: 1px black dotted"> שם </th> 
				    <th width="150" style="border: 1px black dotted"> סכום </th>
				    <th width="150" style="border: 1px black dotted"> מטבע </th>
					</tr>
					<c:if test="${readOnly || command.submitted}">
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
       					   <select class="green autosaveclass medium170" name="financialSupport_currency_${financialSupport.id}">
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
							<input type="text" class="green autosaveclass" name="fromExternal_name"/>
						</td>
						<td>
							<input type="text" class="green autosaveclass" name="fromExternal_sum"/>
						</td>
						<td>
        					<select class="green autosaveclass medium170" name="fromExternal_currency" >
      						<option value="0">מטבע</option>
       						<option value="1" <c:if test="${financialSupport.currency==1}">selected</c:if>>שקל</option>
      						<option value="2" <c:if test="${financialSupport.currency==2}">selected</c:if>>דולר</option>
       		        		</select>
						</td>
						</tr>					
						<tr>
						<td>
							<input type="text" class="green autosaveclass" name="fromExternal_name2"/>
						</td>
						<td>
							<input type="text" class="green autosaveclass" name="fromExternal_sum2"/>
						</td>
						<td>
        					<select class="green autosaveclass medium170" name="fromExternal_currency2" >
      						<option value="0">מטבע</option>
       						<option value="1" <c:if test="${financialSupport.currency==1}">selected</c:if>>שקל</option>
      						<option value="2" <c:if test="${financialSupport.currency==2}">selected</c:if>>דולר</option>
       		        		</select>
						</td>
						</tr>					
						<tr><td><a href="#" class="fromExternalSave">הוסף שורות</a></td></tr>
					</c:if>
					</table>
					</td>
				</tr>
				<tr class="form">
					<td colspan="4">מדמי הרשמה:
					<table width="500" style="border: 1px black dotted" cellpadding="1" cellspacing="0" align="center">
				    <tr>
				    <th width="150" style="border: 1px black dotted"> שם </th> 
				    <th width="150" style="border: 1px black dotted"> סכום </th>
				    <th width="150" style="border: 1px black dotted"> מטבע </th>
					</tr>
 					<c:if test="${readOnly || command.submitted}">
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
       					   <select class="green autosaveclass medium170" name="financialSupport_currency_${financialSupport.id}">
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
							<input type="text" class="green autosaveclass" name="fromAdmitanceFee_name" value="משתתפים"/>
						</td>
						<td>
							<input type="text" class="green autosaveclass" name="fromAdmitanceFee_sum" />
						</td>
						<td>
        					<select name="fromAdmitanceFee_currency" class="green autosaveclass medium170">
      						<option value="0">מטבע</option>
       						<option value="1" <c:if test="${financialSupport.currency==1}">selected</c:if>>שקל</option>
      						<option value="2" <c:if test="${financialSupport.currency==2}">selected</c:if>>דולר</option>
       		        		</select>
						</td>
						</tr>					
						<tr>
						<td>
							<input type="text" class="green autosaveclass" name="fromAdmitanceFee_name2" value="משתתפים"/>
						</td>
						<td>
							<input type="text" class="green autosaveclass" name="fromAdmitanceFee_sum2" />
						</td>
						<td>
        					<select name="fromAdmitanceFee_currency2" class="green autosaveclass medium170">
      						<option value="0">מטבע</option>
       						<option value="1" <c:if test="${financialSupport.currency==1}">selected</c:if>>שקל</option>
      						<option value="2" <c:if test="${financialSupport.currency==2}">selected</c:if>>דולר</option>
       		        		</select>
						</td>
						</tr>					
						<tr><td><a href="#" class="fromAdmitanceFeeSave">הוסף שורות</a></td></tr>
					</c:if>
					</table>
					</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
	            <tr class="form">
		       		<td colspan="4" align="right"><h3>ועדה מארגנת</h3></td>
				</tr>
				<tr class="form">
		

					<td colspan="4">ועדה מדעית:
 					<table width="600" style="border: 1px black dotted" cellpadding="1" cellspacing="0" align="center">
				    <tr>
				    <th width="150" style="border: 1px black dotted"> שם </th> 
				    <th width="150" style="border: 1px black dotted"> מוסד </th>
				    <th width="150" style="border: 1px black dotted"> תפקיד במוסד </th>
				    <th width="150" style="border: 1px black dotted"> תפקיד בועדה </th>
					</tr>
 					<c:if test="${readOnly || command.submitted}">
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
					</c:if>
 					<c:if test="${!readOnly && !command.submitted}">					
           				<c:forEach items="${command.scientificCommittees}" var="committee" varStatus="varStatus">
           				<form:hidden path="scientificCommittees[${varStatus.index}].conferenceProposalId"/>
           				<form:hidden path="scientificCommittees[${varStatus.index}].type"/>
						<tr style="display: none;" class="scientificCommittee">
						<td>
							<form:input cssClass="green autosaveclass" path="scientificCommittees[${varStatus.index}].name"/>
						</td>
						<td>
							<form:input cssClass="green autosaveclass" path="scientificCommittees[${varStatus.index}].institute"/>
						</td>
						<td>
							<form:input cssClass="green autosaveclass" path="scientificCommittees[${varStatus.index}].instituteRole"/>
						</td>
						<td>
							<form:input cssClass="green autosaveclass" path="scientificCommittees[${varStatus.index}].committeeRole"/>
						</td>					
						<td>
							<c:set var="committee" value="${command.scientificCommittees[varStatus.index]}"/>
							<img src="image/icon_delete.gif" class="deleteCommittee"/>							
						</td>
						</tr>
						</c:forEach>
						<tr><td><a href="#" class="scientificCommitteeSave" onclick="hideExtraScientificCommittee();">הוסף שורות</a></td></tr>
					</c:if>
					</table>
					</td>
				</tr>
				<tr class="form">
					<td colspan="4">ועדה מבצעת:
					<table width="600" style="border: 1px black dotted" cellpadding="1" cellspacing="0" align="center">
				    <tr>
				    <th width="150" style="border: 1px black dotted"> שם </th> 
				    <th width="150" style="border: 1px black dotted"> מוסד </th>
				    <th width="150" style="border: 1px black dotted"> תפקיד במוסד </th>
				    <th width="150" style="border: 1px black dotted"> תפקיד בועדה </th>
					</tr>
 					<c:if test="${readOnly || command.submitted}">
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
					</c:if>
 					<c:if test="${!readOnly && !command.submitted}">					
           				<c:forEach items="${command.operationalCommittees}" var="ocommittee" varStatus="varStatus">
           				<form:hidden path="operationalCommittees[${varStatus.index}].conferenceProposalId"/>
           				<form:hidden path="operationalCommittees[${varStatus.index}].type"/>
						<tr style="display: none;" class="operationalCommittee">
						<td>
							<form:input cssClass="green autosaveclass" path="operationalCommittees[${varStatus.index}].name"/>
						</td>
						<td>
							<form:input cssClass="green autosaveclass"  path="operationalCommittees[${varStatus.index}].institute"/>
						</td>
						<td>
							<form:input cssClass="green autosaveclass" path="operationalCommittees[${varStatus.index}].instituteRole"/>
						</td>
						<td>
							<form:input cssClass="green autosaveclass" path="operationalCommittees[${varStatus.index}].committeeRole"/>
						</td>					
						<td>
							<c:set var="ocommittee" value="${command.operationalCommittees[varStatus.index]}"/>
							<img src="image/icon_delete.gif" class="deleteCommittee"/>							
						</td>
						</tr>
						</c:forEach>
						<tr><td><a href="#" class="operationalCommitteeSave" onclick="hideExtraOperationalCommittee();">הוסף שורות</a></td></tr>
					</c:if>
					</table>
					</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
				<tr class="form">
		       		<td colspan="4" align="right"><h3>
	   					סוג הסיוע המבוקש
	   				</h3></td>
				</tr>
	            <tr class="form">
		       		<td colspan="4">
	   				<font>סכום הסיוע המבוקש:</font>
					<c:if test="${!readOnly && !command.submitted}">			
						<form:input cssClass="green medium100" path="supportSum" id="supportSum"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="supportSum"/>
 						${command.supportSum}
					</c:if>
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
				</tr>
				<tr>
				<td colspan="4"><div id="errorsupportsum" dir="rtl"><p></p></div>
				</td>
				</tr>
	            <tr class="form">
		       		<td colspan="4">
					<c:if test="${!readOnly && !command.submitted}">			
		       		   <font> אולם</font><form:checkbox cssClass="green" path="auditorium"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="auditorium"/>
						<font>אולם</font><input type="checkbox" disabled="disabled" value="" <c:if test="${command.auditorium}" > checked </c:if> />
					</c:if>
					<c:if test="${!readOnly && !command.submitted}">			
						<font>חדר סמנירים</font><form:checkbox cssClass="green" path="seminarRoom"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="seminarRoom"/>
						<font>חדר סמנירים</font><input type="checkbox" disabled="disabled" value="" <c:if test="${command.seminarRoom}" > checked </c:if> />
					</c:if>
	   					<font>ל:</font>
					<c:if test="${!readOnly && !command.submitted}">			
	   					<form:input cssClass="green medium100" path="participants" id="participants"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="participants"/>
	   					${command.participants}
					</c:if>
	   				<font> אנשים, בקמפוס מועדף:</font>
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
				<tr>
				<td colspan="4"><div id="errorparticipants" dir="rtl"><p></p></div>
				</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
	            <tr class="form">
		       		<td colspan="4" align="right">
	   					<h3>ארגון הכנס</h3>
	   				</td>
				</tr>
	            <tr class="form">
					<c:if test="${!readOnly && !command.submitted}">			
		       		<td colspan="2">
		       		   <font> ארגון ע"י חברה מסחרית</font><form:checkbox cssClass="green" id="company" path="organizingCompany"/>
					</td>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
					<form:hidden path="organizingCompany"/>
		       		<td colspan="2">
		       		   <font> ארגון ע"י חברה מסחרית</font><input type="checkbox" disabled="disabled" id="companyViewOnly" value="" <c:if test="${command.organizingCompany}">checked</c:if> />
					</td>
					</c:if>
				</tr>
				
	             <tr class="form organizingCompanyPart">
		       		<td colspan="4">
		       		<table >
		       		<tr>
		       		<td nowrap>
	   					<font>שם החברה:</font>&nbsp;&nbsp;&nbsp;&nbsp;
					<c:if test="${!readOnly && !command.submitted}">			
	   					<form:input cssClass="green medium170" path="organizingCompanyName" />
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="organizingCompanyName"/>
	   					${command.organizingCompanyName}
					</c:if>
					</td>
		       		<td nowrap>
	   				<font>טלפון:</font>&nbsp;&nbsp;
					<c:if test="${!readOnly && !command.submitted}">			
	   					<form:input cssClass="green medium170" path="organizingCompanyPhone" id="organizingCompanyPhone"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="organizingCompanyPhone"/>
	   					${command.organizingCompanyPhone}
					</c:if>
					</td>
		       		<td nowrap>
	   				<font>פקס:<font>&nbsp;&nbsp;
					<c:if test="${!readOnly && !command.submitted}">			
	   					<form:input cssClass="green medium170" path="organizingCompanyFax" id="organizingCompanyFax"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="organizingCompanyFax"/>
	   					${command.organizingCompanyFax}
					</c:if>
	   				</td>
				    <td nowrap>
	   					<font>אימייל:</font>
					<c:if test="${!readOnly && !command.submitted}">			
	   					<form:input cssClass="green medium170" path="organizingCompanyEmail" id="organizingCompanyEmail"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="organizingCompanyEmail"/>
	   					${command.organizingCompanyEmail}
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
				</td>
				</tr>
	            <tr class="form">
		       		<td colspan="4">
		       		<table>
		       		<tr>
		       		<td nowrap>
	   					<font>שם איש קשר:</font>
	   				</td>
	   				<td>
					<c:if test="${!readOnly && !command.submitted}">			
	   					<form:input cssClass="green medium170" path="contactPerson" />
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="contactPerson"/>
	   					${command.contactPerson}
					</c:if>
					</td>
		       		<td>
	   					<font>תפקיד:</font>
	   				</td>
	   				<td>
					<c:if test="${!readOnly && !command.submitted}">			
	   					<form:input cssClass="green medium170" path="contactPersonRole" />
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="contactPersonRole"/>
	   					${command.contactPersonRole}
					</c:if>
					</td>
		       		<td>
	   					<font>טלפון:</font>
	   				</td>
	   				<td>
					<c:if test="${!readOnly && !command.submitted}">			
	   					<form:input cssClass="green medium170" path="contactPersonPhone" id="contactPersonPhone"/>
					</c:if>
					<c:if test="${readOnly || command.submitted}">			
						<form:hidden path="contactPersonPhone"/>
	   					${command.contactPersonPhone}
					</c:if>
					</td>
				    <td>
	   					<font>אימייל:</font>
	   				</td>
	   				<td>
					<c:if test="${!readOnly && !command.submitted}">			
	   					<form:input cssClass="green medium170" path="contactPersonEmail" id="contactPersonEmail"/>
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
				<div id="errorcontactPersonPhone" dir="rtl"><p></p></div>
				<div id="errorcontactPersonEmail" dir="rtl"><p></p></div>
				</td>
				</tr>			
				<tr>
		             <td colspan="4"><h3>הגשה</h3></td>
        		 </tr>
				<tr class="form">
					<td>
						<font> הערות לועדה:</font>
					</td>
				</tr>
				<tr>
					<c:if test="${!readOnly && !command.submitted}">			
					<td colspan="4" >
						<form:textarea cssClass="green" path="remarks" cols="100" rows="3"/>
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
				
				<tr>
		             <td colspan="4"><h3>&nbsp;</h3></td>
        		 </tr>
				
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
	            <tr class="form">
		       		<td>
	   					<font>חוות דעת הדיקן המאשר:</font>
	   				</td>
				</tr>
				<tr>
				    <td colspan="4">
						<form:textarea cssClass="green" path="approverEvaluation" cols="100" rows="3"/>
					</td>
				</tr>
				</authz:authorize>
				<authz:authorize ifAnyGranted="ROLE_CONFERENCE_ADMIN">
				<tr class="form">
					<td><font>הערות הרשות למו"פ:</font></td>
				</tr>
				<tr>
					<td colspan="4">
						<form:textarea cssClass="green" path="adminRemarks" cols="100" rows="3"/>
					</td>
				</tr>
				</authz:authorize>
				<authz:authorize ifAnyGranted="ROLE_CONFERENCE_ADMIN,ROLE_CONFERENCE_APPROVER,ROLE_CONFERENCE_COMMITTEE">
				<tr class="form">
					<td><font>הערות הועדה:</font></td>
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
						<textarea class="green" name="newCommitteeRemarks" cols="100" rows="1"></textarea>
					</td>
				</tr>
				</authz:authorize>
				
		<tr class="form">
			<td colspan="4" align="center">
				<authz:authorize ifAnyGranted="ROLE_CONFERENCE_RESEARCHER">
					<c:if test="${!command.submitted}">
						<button class="grey submit" onclick="" id="saveId">שמירה </button>&nbsp;&nbsp;
					</c:if>
				</authz:authorize>
				<authz:authorize ifNotGranted="ROLE_CONFERENCE_RESEARCHER">
					<button class="grey submit" onclick="" id="saveId">שמירה </button>&nbsp;&nbsp;
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