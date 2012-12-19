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
            <form:form id="form" name="form" method="POST" action="callForProposal.html" commandName="command" enctype="multipart/form-data">
 			<form:hidden path="id"/>
 			<form:hidden path="creatorId"/>
			
 			
 			<c:set var="compulsoryFieldSign" value="<font color=\"red\">*</font>"></c:set>
 			
            <table border="0" align="center" cellpadding="2" cellspacing="0">

				<div id="genericDialog" title="עזרה" style="display:none" dir="rtl"><p>text put here</p></div>
                <tr>
                  <td colspan="4">
                	<table width="1000" cellpadding="2" cellspacing="0" align="center">
                	<tr VALIGN="TOP">
                 	 <td colspan="4" align="center">
                  	<h1>עריכת קול קורא <br/>
                   	</h1>
                  	</td>
                 	 </tr>
                 	 </table>
                 	</td>
                </tr>
 
 
                <tr class="form">
					<td colspan="4" align="right">
					<h3>פרטי קול קורא מספר: ${command.id}</h3>  
					<c:if test="${online}">
					 מוצג כרגע באתר
					&nbsp; <button class="grey" onclick="window.open('http://ard.huji.ac.il/huard/infoPageViewer.jsp?ardNum=${command.id}?draft=true','_blank');return false;">תצוגה מקדימה</button>
					&nbsp; <button class="grey" onclick="window.open('http://ard.huji.ac.il/huard/infoPageViewer.jsp?ardNum=${command.id}','_blank');return false;">צפה בדף באתר</button>
					&nbsp; <button class="grey" id="offline">הסר מהאתר</button>
					&nbsp; <button class="grey" id="onlineUpdate">עדכן האתר</button>
					</c:if>
					<c:if test="${!online}">
					&nbsp; <button class="grey" id="online">העלה לאתר</button>
					</c:if>
					</td>
				</tr>
                <tr class="form">
                <td colspan="4">
                <table width="950" style="border:1px #bca2a2 dotted" cellpadding="2" cellspacing="0" align="center">
                <tr class="form">
					<td colspan="3" style="border:1px #bca2a2 dotted">
						 ${compulsoryFieldSign}כותרת:
						<input type="text" htmlEscape="true" class="green long800" name="title" id="title" value="${title}"/>
					    <div id="errortitle" title="שגיאה" dir="rtl">				
					</td>
				</tr>
				<tr class="form">
					<td  width="300" style="border:1px #bca2a2 dotted" nowrap>
						בעל המסמך:
						${command.creator.degreeFullNameHebrew }
					</td>
					<td  width="300" style="border:1px #bca2a2 dotted">
						 ${compulsoryFieldSign}תאריך פרסום:
						<input type="text" class="green date medium100" name="publicationTimeStr" id="publicationTime" value="${publicationTime}"/>
					    <div id="errorpublicationTime" title="שגיאה" dir="rtl">				
					</td>
					<td width="320" style="border:1px #bca2a2 dotted" nowrap>
						 ${compulsoryFieldSign}תאריך הגשה קובע:
						<input type="text" class="green date submissionDate medium100" name="finalSubmissionTimeStr" id="finalSubmissionTime" value="${finalSubmissionTime}"/>&nbsp;
						<form:checkbox cssClass="green" path="allYearSubmission" id="allYearSubmission"/>
						 כל השנה
					    <div id="errorfinalSubmissionTime" title="שגיאה" dir="rtl">				
 					</td>
				</tr>
				<tr class="form">
 					<td width="300" style="border:1px #bca2a2 dotted">
						 תאריך הגשה נוסף:
						<input type="text" class="green date medium100" name="submissionDate1" value="${submissionDate1}"/>
					</td>
					<td width="300" style="border:1px #bca2a2 dotted">
						תאריך הגשה נוסף:
						<input type="text" class="green date medium100" name="submissionDate2" value="${submissionDate2}"/>
 					</td>
					<td  style="border:1px #bca2a2 dotted">
						תאריך הגשה נוסף:
						<input type="text" class="green date medium100" name="submissionDate3" value="${submissionDate3}"/>
					</td>
				</tr>
                <tr class="form">
					<td colspan="2" style="border:1px #bca2a2 dotted">
						 ${compulsoryFieldSign}מממן:
						 <input type="text" class="green long500" id="searchPhrase" value="${selectedFund}"/> 
						<form:hidden path="fundId" id="fundId"/>
						<a href="" id="changeFund">שנה/י</a>&nbsp;
						<a href="temporaryFund.html?action=new" target="_blank">הוסף/י זמני</a>
						<div id="errorfund" title="שגיאה" dir="rtl">
					</td>
					<td style="border:1px #bca2a2 dotted">
						${compulsoryFieldSign}סוג קול קורא:
       					<form:select path="typeId" cssClass="green" >
      						<form:option value="0">בחר/י</form:option>
      						<form:option value="1">מענק מחקר</form:option>
      						<form:option value="2">חילופי חוקרים</form:option>
      						<form:option value="3">כנס</form:option>
      						<form:option value="4">מלגה</form:option>
        		        </form:select>
						<div id="errortype" title="שגיאה" dir="rtl">
					</td>
				</tr>
				<tr class="form">
					<td colspan="2" style="border:1px #bca2a2 dotted">
 						 להציג בהודעות הנגללות,
						עד לתאריך:
						<input type="text" class="green date medium100" name="keepInRollingMessagesExpiryTimeStr"  id="keepInRollingMessagesExpiryTime" value="${keepInRollingMessagesExpiryTime}"/>
 					</td>
					<td  style="border:1px #bca2a2 dotted" nowrap>
						${compulsoryFieldSign}מדור:
         				<form:select path="deskId" id="deskId" cssClass="green deskId" >
      						<form:option value="0">בחר/י</form:option>
       						<c:forEach items="${mopDesks}" var="mopDesk">
	        					<form:option htmlEscape="true" value="${mopDesk.id}"><c:out escapeXml="false" value="${mopDesk.hebrewName}"/></form:option>
       						</c:forEach>
        		        </form:select>
						<div id="errordesk" title="שגיאה" dir="rtl">
					</td>
				</tr>
				<tr class="form">
					<td colspan="3" style="border:1px #bca2a2 dotted">
						  כתובת הקול קורא המקורי:
						<form:input htmlEscape="true" cssClass="green long500" path="originalCallWebAddress" />
					</td>
				</tr>
                <tr class="form">
					<td  style="border:1px #bca2a2 dotted" nowrap>
       					<form:checkbox cssClass="green" path="requireLogin"/>
       					להציג רק לבעלי סיסמה
					</td>
					<td  style="border:1px #bca2a2 dotted" nowrap>
       					<form:checkbox cssClass="green" path="showDescriptionOnly"/>
						להציג תיאור בלבד
					</td>
					<td style="border:1px #bca2a2 dotted">
      					<!--<form:checkbox cssClass="green" path="allYearSubmissionYearPassedAlert"/>
						לשלוח תזכורת לקראת סיום השנה -->&nbsp;
 					</td>
				</tr>
 				</table>
				</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
                <tr class="form">
					<td colspan="4" align="right"><h3>נושאי המחקר</h3></td>
				</tr>
				<tr class="form">
				<td colspan="4">
				<table width="950" style="border:1px #bca2a2 dotted" cellpadding="2" cellspacing="0" align="center">
				<tr>
					<td colspan="4" style="border:1px #bca2a2 dotted">
					<table width="950">
					<tr>
						<td colspan="4">
						נא לבחור את הנושאים הרלוונטיים למחקר:
						</td>
					</tr>
					<tr class="form">
						<td colspan="4" align="center">
						<%@ include file="/WEB-INF/jsp/content/editPost/subjects.jsp" %>					
						</td>
					</tr>
					</table>
					</td>
				</table>
				</td>
				</tr>
				<tr><td>&nbsp;</td></tr>
                <tr class="form">
					<td colspan="4" align="right"><h3> פירוט</h3></td>
				</tr>
				<tr>
					<td colspan="4" >
					<table width="950" style="border:1px #bca2a2 dotted" cellpadding="2" cellspacing="0" align="center">
  					<tr>
					<td colspan="3" style="border:1px #bca2a2 dotted">
					<table width="950">
					<tr>
					<td colspan="3">
      					פרטים הקשורים להגשה:<br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="3" align="center">
 					<div class="editor" style="align:center;">
 						<div class="editorText" style="text-align:right;direction:rtl;border:1px #bca2a2 dotted;width:800;padding-right: 10px;">
 						${command.submissionDetails}
 						<c:if test="${fn:length(command.submissionDetails)==0}"><p>טרם הוזן מידע</p></c:if>
						</div>
 						<span class="textareaEditorSpan" style="align:center;">
           					<textarea class="textareaEditor" id="submissionDetails" name="submissionDetails" cols="100" rows="1" class="green" style="display:none;">${command.submissionDetails }</textarea>
						</span>
					</div>
					<div style="width:800;align:center;text-align:left;">
 					<img src="image/icon_edit.gif" class="openEditor" />&nbsp;&nbsp;&nbsp;<img src="image/icon_save.gif" class="closeEditor"/>
 					</div>
 					</td>
 					</tr>
 					<tr>
					<td colspan="3" style="text-align:right">
					<button class="grey add"><span class="ui-icon ui-icon-arrowthick-1-n"></span></button>&nbsp;<span id="addedText" class="submissionDetails">מקום ההגשה ברשות למו"פ, בתאריך </span>
					</td>
					</tr>
					<tr>
					<td colspan="3" style="text-align:right">	
 					<button class="grey add"><span class="ui-icon ui-icon-arrowthick-1-n"></span></button>&nbsp;<span id="addedText" class="submissionDetails">הגשה ישירות לקרן </span><br/>	
					</td>
					</tr>
 					<tr>
					<td colspan="3" style="text-align:right">	
 					<button class="grey add"><span class="ui-icon ui-icon-arrowthick-1-n"></span></button>&nbsp;<span id="addedText" class="submissionDetails">יש להעביר xxx עותקים לרשות למו"פ</span><br/>		
       				</td>
 					</tr>
					<c:forEach items="${deskAssistants}" var="deskAssistant" varStatus="varStatus">
					<tr>
					<td colspan="3" style="text-align:right">
					<button class="grey add"><span class="ui-icon ui-icon-arrowthick-1-n"></span></button>&nbsp;<span id="addedText" class="submissionDetails">שלח העתק בדואר אלקטרוני ל- <c:out value="${deskAssistant.degreeFullNameHebrew}"></c:out> <c:out value="${deskAssistant.title}"></c:out> <c:out value="${deskAssistant.phone}"></c:out></span>
					</td>
					</tr>
					</c:forEach>
					</table>
					</td>
					</tr>
 					</table>
 					</td>
				</tr>
				<tr><td>&nbsp;</td></tr>	
				<tr>
					<td colspan="4">
					<table width="950"style="border:1px #bca2a2 dotted" cellpadding="2" cellspacing="0" align="center">
  					<tr>
					<td colspan="3" style="border:1px #bca2a2 dotted">
					<table width="950">
  					<tr><td>אנשי קשר:</td>
  					</tr>
 					<tr>
					<td colspan="3" align="center">
 					<div class="editor" style="align:center;">
  						<div class="editorText" style="text-align:right;direction:rtl;border:1px #bca2a2 dotted;width:800;padding-right: 10px;">
 						${command.contactPersonDetails }
 						<c:if test="${fn:length(command.contactPersonDetails)==0}"><p>טרם הוזן מידע</p></c:if>
						</div>
 						<span class="textareaEditorSpan" style="align:center;">
           					<textarea class="textareaEditor" id="contactPersonDetails" name="contactPersonDetails" cols="100" rows="1" class="green" style="display:none;">${command.contactPersonDetails }</textarea>
						</span>
					</div>
					<div style="width:800;align:center;text-align:left;">
					<img src="image/icon_edit.gif" class="openEditor" />&nbsp;&nbsp;&nbsp;<img src="image/icon_save.gif" class="closeEditor"/>
 					</div>
 					</td>
 					</tr>
					<c:forEach items="${deskPersons}" var="deskPerson" varStatus="varStatus">
					<tr>
					<td colspan="3" style="text-align:right">
					<button class="grey add"><span class="ui-icon ui-icon-arrowthick-1-n"></span></button>&nbsp;<span id="addedText" class="contactPersonDetails"><a href="mailto:${deskPerson.email}"><c:out value="${deskPerson.degreeFullNameHebrew}"></c:out></a>
					<img src="image/bullet_orange_website.gif" width="12" height="8"><c:out value="${deskPerson.title}"></c:out>
					<img src="image/bullet_orange_website.gif" width="12" height="8"><c:out value="${deskPerson.phone}"></c:out></span>
					</td>
					</tr>
					</c:forEach>
 					</table>
 					</td>
 					</tr>
 					</table>
				</tr>
				<tr><td>&nbsp;</td></tr>	
				<tr>
					<td colspan="4">
					<table width="950"style="border:1px #bca2a2 dotted" cellpadding="2" cellspacing="0" align="center">
  					<tr>
					<td colspan="3" style="border:1px #bca2a2 dotted">
					<table width="950">
  					<tr><td>טפסים ומידע הקשור לטפסים:</td>
  					</tr>
 					<tr>
					<td colspan="3" align="center">
 					<div class="editor" style="align:center;">
  						<div class="editorText" style="text-align:right;direction:rtl;border:1px #bca2a2 dotted;width:800;padding-right: 10px;">
							${command.formDetails }
 							<c:if test="${fn:length(command.formDetails)==0}"><p>טרם הוזן מידע</p></c:if>
						</div>
 						<span class="textareaEditorSpan" style="align:center;">
           					<textarea class="textareaEditor" id="formDetails" name="formDetails" cols="100" rows="1" class="green" style="display:none;">${command.formDetails }
          					</textarea>
						</span>
					</div>
 					<div style="width:800;align:center;text-align:left;">
					<img src="image/icon_edit.gif" class="openEditor" />&nbsp;&nbsp;&nbsp;<img src="image/icon_save.gif" class="closeEditor"/>
 					</div>
 					</td>
 					</tr>
  					<tr><td>טעינת קבצים:</td></tr>
					<c:forEach items="${command.attachments}" var="attachment" varStatus="varStatus">
					<tr>
					<td colspan="3" style="text-align:right">
						<button class="grey add"><span class="ui-icon ui-icon-arrowthick-1-n"></span></button>&nbsp;<span id="addedText" class="formDetails"><a style="text-decoration:underline" href="fileViewer?callOfProposalId=${command.id}&attachmentId=${attachment.id}&contentType=${attachment.contentType}"
								target="_blank">${attachment.title}</a> </span>
					</td>
					</tr>
					</c:forEach>
					<tr>
  					<tr>
  					<td colspan="2" text-align="right">
  						<div style="display: block; width: 90px; height: 27px; overflow: hidden;">
							<button class="grey" style="width: 90px; height: 24px; position: relative; top: -1px; left: -1px;"><a href="javascript: void(0)">הוסף קובץ</a></button>
							<input type="file" style="font-size: 50px; width: 90px; opacity: 0; filter:alpha(opacity: 0);  position: relative; top: -40px; left: -5px" name="formAttach" id="formAttach"/> 
						</div>
  					</td>
  					</tr>
					<td>
					</td>
					</tr>
  					</table>
  					</td>
  					</tr>
  					</table>
 					</td>
 				</tr>
				<tr><td>&nbsp;</td></tr>	
				<tr>
					<td colspan="4">
					<table width="950"style="border:1px #bca2a2 dotted" cellpadding="2" cellspacing="0" align="center">
  					<tr>
					<td colspan="3" style="border:1px #bca2a2 dotted">
					<table width="950">
  					<tr>
					<td colspan="3">
      					תאור מפורט:<br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="3" align="center">
 					<div class="editor" style="align:center;">
  						<div class="editorText" style="text-align:right;direction:rtl;border:1px #bca2a2 dotted;width:800;padding-right: 10px;">
 							${command.description }
							<c:if test="${fn:length(command.description)==0}"><p>טרם הוזן מידע</p></c:if>
						</div>
 						<span class="textareaEditorSpan" style="align:center;">
           					<textarea class="textareaEditor" id="description" name="description" cols="100" rows="1" class="green" style="display:none;">
							${command.description }           					
							</textarea>
						</span>
					</div>
					<div style="width:800;align:center;text-align:left;">
					<img src="image/icon_edit.gif" class="openEditor" />&nbsp;&nbsp;&nbsp;<img src="image/icon_save.gif" class="closeEditor"/>
 					</div>
   					</td>
 					</tr>
 					</table>
 					</td>
 					</tr>
 					</table>
 					</td>
				</tr>	
				<tr><td>&nbsp;</td></tr>	
				<tr>
					<td colspan="4">
					<table width="950" style="border:1px #bca2a2 dotted" cellpadding="2" cellspacing="0" align="center">
  					<tr>
					<td colspan="3" style="border:1px #bca2a2 dotted">
					<table width="950">
  					<tr>
					<td colspan="3">
      					הדרישות לזכאות:<br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="3" align="center">
 					<div class="editor" style="align:center;">
 						<div class="editorText" style="text-align:right;direction:rtl;border:1px #bca2a2 dotted;width:800;padding-right: 10px;">
 							${command.eligibilityRequirements}
							<c:if test="${fn:length(command.eligibilityRequirements)==0}"><p>טרם הוזן מידע</p></c:if>
						</div>
 						<span class="textareaEditorSpan" style="align:center;">
           					<textarea class="textareaEditor" id="eligibilityRequirements"  name="eligibilityRequirements" cols="100" rows="1" class="green" style="display:none;">
            					${command.eligibilityRequirements }							
            				</textarea>
						</span>
					</div>
					<div style="width:800;align:center;text-align:left;">
 					<img src="image/icon_edit.gif" class="openEditor" />&nbsp;&nbsp;&nbsp;<img src="image/icon_save.gif" class="closeEditor"/>
 					</div>
 					</td>
 					</tr>
 					</table>
 					</td>
 					</tr>
 					</table>
 					</td>
				</tr>											
				<tr><td>&nbsp;</td></tr>	
				<tr>
					<td colspan="4">
					<table width="950" style="border:1px #bca2a2 dotted" cellpadding="2" cellspacing="0" align="center">
  					<tr>
					<td colspan="3" style="border:1px #bca2a2 dotted">
					<table width="950">
  					<tr>
					<td colspan="3">
      					מיקום הפעילות:<br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="3" align="center">
 					<div class="editor" style="align:center;">
 						<div class="editorText" style="text-align:right;direction:rtl;border:1px #bca2a2 dotted;width:800;padding-right: 10px;">
 							${command.activityLocation }
							<c:if test="${fn:length(command.activityLocation)==0}"><p>טרם הוזן מידע</p></c:if>
						</div>
 						<span class="textareaEditorSpan" style="align:center;">
           					<textarea class="textareaEditor" id="activityLocation" name="activityLocation" cols="100" rows="1" class="green" style="display:none;">
           					${command.activityLocation }
							</textarea>
						</span>
					</div>
					<div style="width:800;align:center;text-align:left;">
					<img src="image/icon_edit.gif" class="openEditor" />&nbsp;&nbsp;&nbsp;<img src="image/icon_save.gif" class="closeEditor"/>
 					</div>
 					</td>
 					</tr>
 					</table>
 					</td>
 					</tr>
 					</table>
 					</td>
				</tr>											
				<tr><td>&nbsp;</td></tr>	
				<tr>
					<td colspan="4">
					<table width="950" style="border:1px #bca2a2 dotted" cellpadding="2" cellspacing="0" align="center">
  					<tr>
					<td colspan="3" style="border:1px #bca2a2 dotted">
					<table width="950">
  					<tr>
					<td colspan="3">
      					שיתופי פעולה אפשריים:<br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="3" align="center">
					<div class="editor" style="align:center;">
 						<div class="editorText" style="text-align:right;direction:rtl;border:1px #bca2a2 dotted;width:800;padding-right: 10px;">
 							${command.possibleCollaboration}
							<c:if test="${fn:length(command.possibleCollaboration)==0}"><p>טרם הוזן מידע</p></c:if>
						</div>
 						<span class="textareaEditorSpan" style="align:center;">
           					<textarea class="textareaEditor" id="possibleCollaboration" name="possibleCollaboration" cols="100" rows="1" class="green" style="display:none;">${command.possibleCollaboration }
 							</textarea>
						</span>
					</div>
					<div style="width:800;align:center;text-align:left;">
 					<img src="image/icon_edit.gif" class="openEditor" />&nbsp;&nbsp;&nbsp;<img src="image/icon_save.gif" class="closeEditor"/>
 					</div>
 					</td>
 					</tr>
 					</table>
 					</td>
 					</tr>
 					</table>
 					</td>
				</tr>											
				<tr><td>&nbsp;</td></tr>	
				<tr>
					<td colspan="4">
					<table width="950" style="border:1px #bca2a2 dotted" cellpadding="2" cellspacing="0" align="center">
  					<tr>
					<td colspan="3" style="border:1px #bca2a2 dotted">
					<table width="950">
  					<tr>
					<td colspan="3">
      					פרטים הקשורים לתקציב:<br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="3" align="center">
					<div class="editor" style="align:center;">
 						<div class="editorText" style="text-align:right;direction:rtl;border:1px #bca2a2 dotted;width:800;padding-right: 10px;">
 							${command.budgetDetails }
							<c:if test="${fn:length(command.budgetDetails)==0}"><p>טרם הוזן מידע</p></c:if>
						</div>
 						<span class="textareaEditorSpan" style="align:center;">
           					<textarea class="textareaEditor" id="budgetDetails" name="budgetDetails" cols="100" rows="1" class="green" style="display:none;">${command.budgetDetails }
 							</textarea>
						</span>
					</div>
					<div style="width:800;align:center;text-align:left;">
					<img src="image/icon_edit.gif" class="openEditor" />&nbsp;&nbsp;&nbsp;<img src="image/icon_save.gif" class="closeEditor"/>
 					</div>
 					</td>
 					</tr>
 					<c:forEach items="${deskBudgetPersons}" var="deskBudgetPerson" varStatus="varStatus">
					<tr>
					<td colspan="3" style="text-align:right">
					<button class="grey add"><span class="ui-icon ui-icon-arrowthick-1-n"></span></button>&nbsp;<span id="addedText" class="budgetDetails">התקציב דורש את אישורו של <c:out value="${deskBudgetPerson.degreeFullNameHebrew}"></c:out> <c:out value="${deskBudgetPerson.title}"></c:out> <c:out value="${deskBudgetPerson.phone}"></c:out></span>
					</td>
					</tr>
					</c:forEach>
 					</table>
 					</td>
 					</tr>
 					</table>
 					</td>
				</tr>											
				<tr><td>&nbsp;</td></tr>	
				<tr>
					<td colspan="4">
					<table width="950" style="border:1px #bca2a2 dotted" cellpadding="2" cellspacing="0" align="center">
  					<tr>
					<td colspan="3" style="border:1px #bca2a2 dotted">
					<table width="950">
  					<tr>
					<td colspan="3">
      					הערות נוספות:<br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="3" align="center">
					<div class="editor" style="align:center;">
  						<div class="editorText" style="text-align:right;direction:rtl;border:1px #bca2a2 dotted;width:800;padding-right: 10px;">
							${command.additionalInformation } 
							<c:if test="${fn:length(command.additionalInformation)==0}"><p>טרם הוזן מידע</p></c:if>
						</div>
 						<span class="textareaEditorSpan" style="align:center;">
           					<textarea class="textareaEditor" id="additionalInformation" name="additionalInformation" cols="100" rows="1" class="green" style="display:none;">
							${command.additionalInformation } 							
							</textarea>
						</span>
  					</div>         			
					<div style="width:800;align:center;text-align:left;">
					<img src="image/icon_edit.gif" class="openEditor" />&nbsp;&nbsp;&nbsp;<img src="image/icon_save.gif" class="closeEditor"/>
 					</div>
 					</td>
 					</tr>
 					</table>
 					</td>
 					</tr>
 					</table>
 					</td>
				</tr>											
				<tr><td>&nbsp;</td></tr>	
				<tr class="form">
					<td colspan="4">
					<table width="950" style="border:1px #bca2a2 dotted" cellpadding="2" cellspacing="0" align="center">
  					<tr>
					<td colspan="3" style="border:1px #bca2a2 dotted">
					<table width="950">
  					<tr>
					<td colspan="3">
      					תקופת מימון מקסימלית:<br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="3" align="center">
					<div class="editor" style="align:center;">
  						<div class="editorText" style="text-align:right;direction:rtl;border:1px #bca2a2 dotted;width:800;padding-right: 10px;">
 							${command.fundingPeriod}
							<c:if test="${fn:length(command.fundingPeriod)==0}"><p>טרם הוזן מידע</p></c:if>
						</div>
 						<span class="textareaEditorSpan" style="align:center;">
           					<textarea class="textareaEditor" id="fundingPeriod" name="fundingPeriod" cols="100" rows="1" class="green" style="display:none;">${command.fundingPeriod}
 							</textarea>
						</span>
					</div>
					<div style="width:800;align:center;text-align:left;">
					<img src="image/icon_edit.gif" class="openEditor" />&nbsp;&nbsp;&nbsp;<img src="image/icon_save.gif" class="closeEditor"/>
 					</div>
 					</td>
 					</tr>
  					</table>
  					</td>
  					</tr>
  					</table>
 					</td>
 				</tr>
 				<tr><td>&nbsp;</td></tr>	
 				<tr>
					<td colspan="4">
					<table width="950" style="border:1px #bca2a2 dotted" cellpadding="2" cellspacing="0" align="center">
  					<tr>
					<td colspan="3" style="border:1px #bca2a2 dotted">
					<table width="950">
  					<tr>
					<td colspan="3">
      					סכום המענק:<br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="3" align="center">
					<div class="editor" style="align:center;">
  						<div class="editorText" style="text-align:right;direction:rtl;border:1px #bca2a2 dotted;width:800;padding-right: 10px;">
 							${command.amountOfGrant}
							<c:if test="${fn:length(command.amountOfGrant)==0}"><p>טרם הוזן מידע</p></c:if>
						</div>
 						<span class="textareaEditorSpan" style="align:center;">
           					<textarea class="textareaEditor" id="amountOfGrant" name="amountOfGrant" cols="100" rows="1" class="green" style="display:none;">${command.amountOfGrant}
 							</textarea>
						</span>
					</div>
					<div style="width:800;align:center;text-align:left;">
					<img src="image/icon_edit.gif" class="openEditor" />&nbsp;&nbsp;&nbsp;<img src="image/icon_save.gif" class="closeEditor"/>
 					</div>
 					</td>
 					</tr>
  					</table>
  					</td>
  					</tr>
  					</table>
 					</td>
				</tr>

	
 		
		<tr class="form">
			<td colspan="4" align="center">
				<button title="שמירה" class="grey save" > שמירה </button>&nbsp;&nbsp;
				<c:if test="${online}">
					<button class="grey post" title="" >צור הודעה בפוסט </button>&nbsp;&nbsp;
				</c:if>		
				<button class="grey" title="חזרה לתפריט הראשי"  onclick="window.location='welcome.html';return false;">חזרה לתפריט ראשי </button>&nbsp;&nbsp;		
				<button class="grey" title="חזרה"  onclick="history.back();return false;">חזרה למסך קודם </button>		
			</td>
		</tr>
    </table>
</form:form>
    </td>
  </tr>

</table>



</body>
</html>