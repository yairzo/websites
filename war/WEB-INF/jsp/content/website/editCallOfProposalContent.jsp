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
      <table width="1000" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#bca2a2" dir="${lang.dir}">
        <tr>
          <td valign="top" align="center"><br>
            <form:form id="form" name="form" method="POST" action="editCallForProposal.html" commandName="command" enctype="multipart/form-data">
 			<form:hidden path="id"/>
 			<form:hidden path="creatorId"/>
			
 			
 			<c:set var="compulsoryFieldSign" value="<font color=\"red\">*</font>"></c:set>
 			
            <table border="0" align="center" cellpadding="2" cellspacing="0">

				<div id="genericDialog" style="display:none" dir="${lang.dir}"><p></p></div>
                <tr>
                  <td colspan="4">
                	<table width="1000" cellpadding="2" cellspacing="0" align="center">
                	<tr VALIGN="TOP">
                 	 <td colspan="4" align="center">
                  	<h1><fmt:message key="${lang.localeId}.callForProposal.pageTitle"/>
                   	</h1>
                  	</td>
                 	 </tr>
                 	 </table>
                 	</td>
                </tr>
 
 
                <tr class="form">
					<td colspan="4" align="${lang.align}">
					<h3><fmt:message key="${lang.localeId}.callForProposal.subtitle"/>${command.id}</h3>  
					<c:if test="${online}">
					<fmt:message key="${lang.localeId}.callForProposal.onSite"/> 
					&nbsp; <button class="grey" onclick="window.open('http://ard.huji.ac.il/huard/infoPageViewer.jsp?ardNum=${command.id}?draft=true','_blank');return false;"><fmt:message key="${lang.localeId}.callForProposal.preview"/></button>
					&nbsp; <button class="grey" onclick="window.open('http://ard.huji.ac.il/huard/infoPageViewer.jsp?ardNum=${command.id}','_blank');return false;"><fmt:message key="${lang.localeId}.callForProposal.viewOnSite"/></button>
					&nbsp; <button class="grey" id="offline"><fmt:message key="${lang.localeId}.callForProposal.takeOffSite"/></button>
					&nbsp; <button class="grey" id="onlineUpdate"><fmt:message key="${lang.localeId}.callForProposal.updateSite"/></button>
					</c:if>
					<c:if test="${!online}">
					&nbsp; <button class="grey" id="online"><fmt:message key="${lang.localeId}.callForProposal.putOnSite"/></button>
					</c:if>
					</td>
				</tr>
				<tr class="form">
					<td  colspan="4">
						<fmt:message key="${lang.localeId}.callForProposal.language"/>
						<form:select cssClass ="green langSelect" path="localeId">
							<c:forEach items="${langs}" var="lang">
								<form:option value="${lang.localeId}">${lang.name}</form:option>
							</c:forEach>
						</form:select>
					</td>
				</tr>
				
                <tr class="form">
                <td colspan="4">
                <table width="950" style="border:1px #bca2a2 dotted" cellpadding="2" cellspacing="0" align="center">
                <tr class="form">
					<td colspan="3" style="border:1px #bca2a2 dotted">
						 ${compulsoryFieldSign}<fmt:message key="${lang.localeId}.callForProposal.title"/>
						<input type="text" htmlEscape="true" class="green long800" name="title" id="title" value="${title}"/>
					    <div id="errortitle" title="שגיאה" dir="${lang.dir}">				
					</td>
				</tr>
				<tr class="form">
					<td width="300" style="border:1px #bca2a2 dotted" nowrap>
						<fmt:message key="${lang.localeId}.callForProposal.creator"/>
						<c:if test="${lang.name=='Hebrew'}">${command.creator.degreeFullNameHebrew }</c:if>
   						<c:if test="${lang.name=='English'}">${command.creator.degreeFullNameEnglish }</c:if>
						
					</td>
					<td  width="300" style="border:1px #bca2a2 dotted">
						 ${compulsoryFieldSign}<fmt:message key="${lang.localeId}.callForProposal.publicationTime"/>
						<form:input htmlEscape="true" cssClass="green date medium100" path="publicationTimeString" id="publicationTime"/>
					    <div id="errorpublicationTime" title="שגיאה" dir="${lang.dir}">				
					</td>
					<td width="320" style="border:1px #bca2a2 dotted" nowrap>
						 ${compulsoryFieldSign}<fmt:message key="${lang.localeId}.callForProposal.finalSubmissionTime"/>
						<form:input htmlEscape="true" cssClass="green date submissionDate medium100" path="finalSubmissionTimeString" id="finalSubmissionTime"/>&nbsp;
						<form:checkbox cssClass="green" path="allYearSubmission" id="allYearSubmission"/>
						<fmt:message key="${lang.localeId}.callForProposal.allYearSubmission"/>
					    <div id="errorfinalSubmissionTime" title="שגיאה" dir="${lang.dir}">				
 					</td>
				</tr>
				<tr class="form">
				    <c:forEach items="${command.submissionDatesList}" var="anotherSubmissionDate" varStatus="varStatus">
					<td width="300" style="border:1px #bca2a2 dotted">
						 <fmt:message key="${lang.localeId}.callForProposal.anotherSubmissionDate"/>
						<form:input htmlEscape="true" cssClass="green date medium100" path="submissionDatesList[${varStatus.index}]"/>
					</td>
					</c:forEach>
				
				</tr> 
                <tr class="form">
					<td colspan="2" style="border:1px #bca2a2 dotted">
						 ${compulsoryFieldSign}<fmt:message key="${lang.localeId}.callForProposal.fund"/>
						 <input type="text" class="green long500" id="searchPhrase" value="${selectedFund}"/> 
						<form:hidden path="fundId" id="fundId"/>
						<a href="" id="changeFund"><fmt:message key="${lang.localeId}.callForProposal.changeFund"/></a>&nbsp;
						<a href="temporaryFund.html?action=new" target="_blank"><fmt:message key="${lang.localeId}.callForProposal.addFund"/></a>
						<div id="errorfund" title="שגיאה" dir="${lang.dir}">
					</td>
					<td style="border:1px #bca2a2 dotted">
						${compulsoryFieldSign}<fmt:message key="${lang.localeId}.callForProposal.type"/>
       					<form:select path="typeId" cssClass="green" >
      						<form:option value="0"><fmt:message key="${lang.localeId}.callForProposal.select"/></form:option>
      						<form:option value="1"><fmt:message key="${lang.localeId}.callForProposal.researchGrant"/></form:option>
      						<form:option value="2"><fmt:message key="${lang.localeId}.callForProposal.researcherExchange"/></form:option>
      						<form:option value="3"><fmt:message key="${lang.localeId}.callForProposal.conference"/></form:option>
      						<form:option value="4"><fmt:message key="${lang.localeId}.callForProposal.scholarship"/></form:option>
        		        </form:select>
						<div id="errortype" title="שגיאה" dir="${lang.dir}">
					</td>
				</tr>
				<tr class="form">
					<td colspan="2" style="border:1px #bca2a2 dotted">
 						<fmt:message key="${lang.localeId}.callForProposal.keepInRollingMessagesExpiryTime"/>
						<form:input htmlEscape="true" cssClass="green date medium100" path="keepInRollingMessagesExpiryTimeString" id="keepInRollingMessagesExpiryTime"/>
 					</td>
					<td  style="border:1px #bca2a2 dotted" nowrap>
						${compulsoryFieldSign}<fmt:message key="${lang.localeId}.callForProposal.desk"/>
         				<form:select path="deskId" id="deskId" cssClass="green deskId" >
      						<form:option value="0"><fmt:message key="${lang.localeId}.callForProposal.select"/></form:option>
       						<c:forEach items="${mopDesks}" var="mopDesk">
	        					<form:option htmlEscape="true" value="${mopDesk.id}">
	        					<c:if test="${lang.name=='Hebrew'}"><c:out escapeXml="false" value="${mopDesk.hebrewName}"/></c:if>
	        					<c:if test="${lang.name=='English'}"><c:out escapeXml="false" value="${mopDesk.englishName}"/></c:if>
	        					</form:option>
       						</c:forEach>
        		        </form:select>
						<div id="errordesk" title="שגיאה" dir="${lang.dir}">
					</td>
				</tr>
				<tr class="form">
					<td colspan="3" style="border:1px #bca2a2 dotted">
						<fmt:message key="${lang.localeId}.callForProposal.originalCallWebAddress"/>  
						<form:input htmlEscape="true" cssClass="green long500" path="originalCallWebAddress" />
					</td>
				</tr>
                <tr class="form">
					<td  style="border:1px #bca2a2 dotted" nowrap>
       					<form:checkbox cssClass="green" path="requireLogin"/>
       					<fmt:message key="${lang.localeId}.callForProposal.requireLogin"/>
					</td>
					<td  style="border:1px #bca2a2 dotted" nowrap>
       					<form:checkbox cssClass="green" path="showDescriptionOnly"/>
						<fmt:message key="${lang.localeId}.callForProposal.showDescriptionOnly"/>
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
					<td colspan="4" align="${lang.align}"><h3><fmt:message key="${lang.localeId}.callForProposal.researchSubjects"/></h3></td>
				</tr>
				<tr class="form">
				<td colspan="4">
				<table width="950" style="border:1px #bca2a2 dotted" cellpadding="2" cellspacing="0" align="center">
				<tr>
					<td colspan="4" style="border:1px #bca2a2 dotted">
					<table width="950">
					<tr>
						<td colspan="4">
						<fmt:message key="${lang.localeId}.callForProposal.selectSubjects"/>
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
					<td colspan="4" align="${lang.align}"><h3><fmt:message key="${lang.localeId}.callForProposal.details"/></h3></td>
				</tr>
				<tr>
					<td colspan="4" >
					<table width="950" style="border:1px #bca2a2 dotted" cellpadding="2" cellspacing="0" align="center">
  					<tr>
					<td colspan="3" style="border:1px #bca2a2 dotted">
					<table width="950">
					<tr>
					<td colspan="3">
      				<fmt:message key="${lang.localeId}.callForProposal.submissionDetails"/>	<br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="3" align="center">
 					<div class="editor" style="align:center;">
 						<div class="editorText" style="text-align:${lang.align};direction:${lang.dir};border:1px #bca2a2 dotted;width:800;padding-right: 10px;">
 						${command.submissionDetails}
 						<c:if test="${fn:length(command.submissionDetails)==0}"><p><fmt:message key="${lang.localeId}.callForProposal.noInfo"/></p></c:if>
						</div>
 						<span class="textareaEditorSpan" style="align:center;">
           					<textarea class="textareaEditor" id="submissionDetails" name="submissionDetails" cols="100" rows="1" class="green" style="display:none;">${command.submissionDetails }</textarea>
						</span>
					</div>
					<c:if test="${lang.align=='left'}">
 						<c:set var="opositeAlign" value="right"/>
					</c:if>
					<c:if test="${lang.align=='right'}">
 						<c:set var="opositeAlign" value="left"/>
					</c:if>
					<div style="width:800;align:center;text-align:${opositeAlign};">
 					<img src="image/icon_edit.gif" class="openEditor" />&nbsp;&nbsp;&nbsp;<img src="image/icon_save.gif" class="closeEditor"/>
 					</div>
 					</td>
 					</tr>
 					<tr>
					<td colspan="3" style="text-align:${lang.align}">
					<button class="grey add"><span class="ui-icon ui-icon-arrowthick-1-n"></span></button>&nbsp;<span id="addedText" class="submissionDetails"><fmt:message key="${lang.localeId}.callForProposal.submissionAtAuthority"/> </span>
					</td>
					</tr>
					<tr>
					<td colspan="3" style="text-align:${lang.align}">	
 					<button class="grey add"><span class="ui-icon ui-icon-arrowthick-1-n"></span></button>&nbsp;<span id="addedText" class="submissionDetails"><fmt:message key="${lang.localeId}.callForProposal.submissionAtFund"/> </span><br/>	
					</td>
					</tr>
 					<tr>
					<td colspan="3" style="text-align:${lang.align}">	
 					<button class="grey add"><span class="ui-icon ui-icon-arrowthick-1-n"></span></button>&nbsp;<span id="addedText" class="submissionDetails"><fmt:message key="${lang.localeId}.callForProposal.submissionCopies"/></span><br/>		
       				</td>
 					</tr>
					<c:forEach items="${deskAssistants}" var="deskAssistant" varStatus="varStatus">
					<tr>
					<td colspan="3" style="text-align:${lang.align}">
					<button class="grey add"><span class="ui-icon ui-icon-arrowthick-1-n"></span></button>&nbsp;<span id="addedText" class="submissionDetails"><fmt:message key="${lang.localeId}.callForProposal.submissionEmail"/> 
					<c:if test="${lang.name=='Hebrew'}"><c:out escapeXml="false" value="${deskAssistant.degreeFullNameHebrew}"/></c:if>
   					<c:if test="${lang.name=='English'}"><c:out escapeXml="false" value="${deskAssistant.degreeFullNameEnglish}"/></c:if>
					<c:out value="${deskAssistant.title}"></c:out> 
					<c:out value="${deskAssistant.phone}"></c:out></span>
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
  					<tr><td><fmt:message key="${lang.localeId}.callForProposal.contactPersons"/></td>
  					</tr>
 					<tr>
					<td colspan="3" align="center">
 					<div class="editor" style="align:center;">
  						<div class="editorText" style="text-align:${lang.align};direction:${lang.dir};border:1px #bca2a2 dotted;width:800;padding-right: 10px;">
 						${command.contactPersonDetails }
 						<c:if test="${fn:length(command.contactPersonDetails)==0}"><p><fmt:message key="${lang.localeId}.callForProposal.noInfo"/></p></c:if>
						</div>
 						<span class="textareaEditorSpan" style="align:center;">
           					<textarea class="textareaEditor" id="contactPersonDetails" name="contactPersonDetails" cols="100" rows="1" class="green" style="display:none;">${command.contactPersonDetails }</textarea>
						</span>
					</div>
					<div style="width:800;align:center;text-align:${opositeAlign};">
					<img src="image/icon_edit.gif" class="openEditor" />&nbsp;&nbsp;&nbsp;<img src="image/icon_save.gif" class="closeEditor"/>
 					</div>
 					</td>
 					</tr>
					<c:forEach items="${deskPersons}" var="deskPerson" varStatus="varStatus">
					<tr>
					<td colspan="3" style="text-align:${lang.align}">
					<button class="grey add"><span class="ui-icon ui-icon-arrowthick-1-n"></span></button>&nbsp;<span id="addedText" class="contactPersonDetails">
					<a href="mailto:${deskPerson.email}">
					<c:if test="${lang.name=='Hebrew'}"><c:out escapeXml="false" value="${deskPerson.degreeFullNameHebrew}"/></c:if>
   					<c:if test="${lang.name=='English'}"><c:out escapeXml="false" value="${deskPerson.degreeFullNameEnglish}"/></c:if>
					</a>
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
  					<tr><td><fmt:message key="${lang.localeId}.callForProposal.forms"/></td>
  					</tr>
 					<tr>
					<td colspan="3" align="center">
 					<div class="editor" style="align:center;">
  						<div class="editorText" style="text-align:${lang.align};direction:${lang.dir};border:1px #bca2a2 dotted;width:800;padding-right: 10px;">
							${command.formDetails }
 							<c:if test="${fn:length(command.formDetails)==0}">
 							<p><fmt:message key="${lang.localeId}.callForProposal.noInfo"/></p>
 							</c:if>
						</div>
 						<span class="textareaEditorSpan" style="align:center;">
           					<textarea class="textareaEditor" id="formDetails" name="formDetails" cols="100" rows="1" class="green" style="display:none;">${command.formDetails }
          					</textarea>
						</span>
					</div>
 					<div style="width:800;align:center;text-align:${opositeAlign};">
					<img src="image/icon_edit.gif" class="openEditor" />&nbsp;&nbsp;&nbsp;<img src="image/icon_save.gif" class="closeEditor"/>
 					</div>
 					</td>
 					</tr>
  					<tr><td><fmt:message key="${lang.localeId}.callForProposal.uploadForms"/></td></tr>
					<c:forEach items="${command.attachments}" var="attachment" varStatus="varStatus">
					<tr>
					<td colspan="3" style="text-align:${lang.align}">
						<button class="grey add"><span class="ui-icon ui-icon-arrowthick-1-n"></span></button>&nbsp;<span id="addedText" class="formDetails"><a style="text-decoration:underline" href="fileViewer?callOfProposalId=${command.id}&attachmentId=${attachment.id}&contentType=${attachment.contentType}"
								target="_blank">${attachment.title}</a> </span>
					</td>
					</tr>
					</c:forEach>
					<tr>
  					<tr>
  					<td colspan="2" text-align="${lang.align}">
  						<div style="display: block; width: 90px; height: 27px; overflow: hidden;">
							<button class="grey" style="width: 90px; height: 24px; position: relative; top: -1px; left: -1px;"><a href="javascript: void(0)"><fmt:message key="${lang.localeId}.callForProposal.addFile"/></a></button>
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
      					<fmt:message key="${lang.localeId}.callForProposal.description"/><br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="3" align="center">
 					<div class="editor" style="align:center;">
  						<div class="editorText" style="text-align:${lang.align};direction:${lang.dir};border:1px #bca2a2 dotted;width:800;padding-right: 10px;">
 							${command.description }
							<c:if test="${fn:length(command.description)==0}"><p><fmt:message key="${lang.localeId}.callForProposal.noInfo"/></p></c:if>
						</div>
 						<span class="textareaEditorSpan" style="align:center;">
           					<textarea class="textareaEditor" id="description" name="description" cols="100" rows="1" class="green" style="display:none;">
							${command.description }           					
							</textarea>
						</span>
					</div>
					<div style="width:800;align:center;text-align:${opositeAlign};">
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
      					<fmt:message key="${lang.localeId}.callForProposal.eligibilityRequirements"/><br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="3" align="center">
 					<div class="editor" style="align:center;">
 						<div class="editorText" style="text-align:${lang.align};direction:${lang.dir};border:1px #bca2a2 dotted;width:800;padding-right: 10px;">
 							${command.eligibilityRequirements}
							<c:if test="${fn:length(command.eligibilityRequirements)==0}"><p><fmt:message key="${lang.localeId}.callForProposal.noInfo"/></p></c:if>
						</div>
 						<span class="textareaEditorSpan" style="align:center;">
           					<textarea class="textareaEditor" id="eligibilityRequirements"  name="eligibilityRequirements" cols="100" rows="1" class="green" style="display:none;">
            					${command.eligibilityRequirements }							
            				</textarea>
						</span>
					</div>
					<div style="width:800;align:center;text-align:${opositeAlign};">
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
      				<fmt:message key="${lang.localeId}.callForProposal.activityLocation"/>	<br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="3" align="center">
 					<div class="editor" style="align:center;">
 						<div class="editorText" style="text-align:${lang.align};direction:${lang.dir};border:1px #bca2a2 dotted;width:800;padding-right: 10px;">
 							${command.activityLocation }
							<c:if test="${fn:length(command.activityLocation)==0}"><p><fmt:message key="${lang.localeId}.callForProposal.noInfo"/></p></c:if>
						</div>
 						<span class="textareaEditorSpan" style="align:center;">
           					<textarea class="textareaEditor" id="activityLocation" name="activityLocation" cols="100" rows="1" class="green" style="display:none;">
           					${command.activityLocation }
							</textarea>
						</span>
					</div>
					<div style="width:800;align:center;text-align:${opositeAlign};">
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
      				<fmt:message key="${lang.localeId}.callForProposal.possibleCollaboration"/>	<br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="3" align="center">
					<div class="editor" style="align:center;">
 						<div class="editorText" style="text-align:${lang.align};direction:${lang.dir};border:1px #bca2a2 dotted;width:800;padding-right: 10px;">
 							${command.possibleCollaboration}
							<c:if test="${fn:length(command.possibleCollaboration)==0}"><p><fmt:message key="${lang.localeId}.callForProposal.noInfo"/></p></c:if>
						</div>
 						<span class="textareaEditorSpan" style="align:center;">
           					<textarea class="textareaEditor" id="possibleCollaboration" name="possibleCollaboration" cols="100" rows="1" class="green" style="display:none;">${command.possibleCollaboration }
 							</textarea>
						</span>
					</div>
					<div style="width:800;align:center;text-align:${opositeAlign};">
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
      				<fmt:message key="${lang.localeId}.callForProposal.budgetDetails"/>	<br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="3" align="center">
					<div class="editor" style="align:center;">
 						<div class="editorText" style="text-align:${lang.align};direction:${lang.dir};border:1px #bca2a2 dotted;width:800;padding-right: 10px;">
 							${command.budgetDetails }
							<c:if test="${fn:length(command.budgetDetails)==0}"><p><fmt:message key="${lang.localeId}.callForProposal.noInfo"/></p></c:if>
						</div>
 						<span class="textareaEditorSpan" style="align:center;">
           					<textarea class="textareaEditor" id="budgetDetails" name="budgetDetails" cols="100" rows="1" class="green" style="display:none;">${command.budgetDetails }
 							</textarea>
						</span>
					</div>
					<div style="width:800;align:center;text-align:${opositeAlign};">
					<img src="image/icon_edit.gif" class="openEditor" />&nbsp;&nbsp;&nbsp;<img src="image/icon_save.gif" class="closeEditor"/>
 					</div>
 					</td>
 					</tr>
 					<c:forEach items="${deskBudgetPersons}" var="deskBudgetPerson" varStatus="varStatus">
					<tr>
					<td colspan="3" style="text-align:${lang.align}">
					<button class="grey add"><span class="ui-icon ui-icon-arrowthick-1-n"></span></button>&nbsp;
					<span id="addedText" class="budgetDetails">
					<fmt:message key="${lang.localeId}.callForProposal.budgetApprover"/> 
					<a href="mailto:${deskBudgetPerson.email}">
					<c:if test="${lang.name=='Hebrew'}"><c:out escapeXml="false" value="${deskBudgetPerson.degreeFullNameHebrew}"/></c:if>
   					<c:if test="${lang.name=='English'}"><c:out escapeXml="false" value="${deskBudgetPerson.degreeFullNameEnglish}"/></c:if>
					</a>
					<img src="image/bullet_orange_website.gif" width="12" height="8"><c:out value="${deskBudgetPerson.title}"></c:out> 
					<img src="image/bullet_orange_website.gif" width="12" height="8"><c:out value="${deskBudgetPerson.phone}"></c:out>
					</span>
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
      				<fmt:message key="${lang.localeId}.callForProposal.additionalInformation"/>	<br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="3" align="center">
					<div class="editor" style="align:center;">
  						<div class="editorText" style="text-align:${lang.align};direction:${lang.dir};border:1px #bca2a2 dotted;width:800;padding-right: 10px;">
							${command.additionalInformation } 
							<c:if test="${fn:length(command.additionalInformation)==0}"><p><fmt:message key="${lang.localeId}.callForProposal.noInfo"/></p></c:if>
						</div>
 						<span class="textareaEditorSpan" style="align:center;">
           					<textarea class="textareaEditor" id="additionalInformation" name="additionalInformation" cols="100" rows="1" class="green" style="display:none;">
							${command.additionalInformation } 							
							</textarea>
						</span>
  					</div>         			
					<div style="width:800;align:center;text-align:${opositeAlign};">
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
      				<fmt:message key="${lang.localeId}.callForProposal.fundingPeriod"/>	<br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="3" align="center">
					<div class="editor" style="align:center;">
  						<div class="editorText" style="text-align:${lang.align};direction:${lang.dir};border:1px #bca2a2 dotted;width:800;padding-right: 10px;">
 							${command.fundingPeriod}
							<c:if test="${fn:length(command.fundingPeriod)==0}"><p><fmt:message key="${lang.localeId}.callForProposal.noInfo"/></p></c:if>
						</div>
 						<span class="textareaEditorSpan" style="align:center;">
           					<textarea class="textareaEditor" id="fundingPeriod" name="fundingPeriod" cols="100" rows="1" class="green" style="display:none;">${command.fundingPeriod}
 							</textarea>
						</span>
					</div>
					<div style="width:800;align:center;text-align:${opositeAlign};">
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
      				<fmt:message key="${lang.localeId}.callForProposal.amountOfGrant"/>	<br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="3" align="center">
					<div class="editor" style="align:center;">
  						<div class="editorText" style="text-align:${lang.align};direction:${lang.dir};border:1px #bca2a2 dotted;width:800;padding-right: 10px;">
 							${command.amountOfGrant}
							<c:if test="${fn:length(command.amountOfGrant)==0}"><p><fmt:message key="${lang.localeId}.callForProposal.noInfo"/></p></c:if>
						</div>
 						<span class="textareaEditorSpan" style="align:center;">
           					<textarea class="textareaEditor" id="amountOfGrant" name="amountOfGrant" cols="100" rows="1" class="green" style="display:none;">${command.amountOfGrant}
 							</textarea>
						</span>
					</div>
					<div style="width:800;align:center;text-align:${opositeAlign};">
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
				<button class="grey save"><fmt:message key="${lang.localeId}.callForProposal.save"/></button>&nbsp;&nbsp;
				<c:if test="${online}">
					<button class="grey post"><fmt:message key="${lang.localeId}.callForProposal.createPost"/> </button>&nbsp;&nbsp;
				</c:if>		
				<button class="grey" onclick="window.location='welcome.html';return false;"><fmt:message key="${lang.localeId}.callForProposal.mainMenu"/> </button>&nbsp;&nbsp;		
				<button class="grey" onclick="history.back();return false;"><fmt:message key="${lang.localeId}.callForProposal.prevPage"/> </button>		
			</td>
		</tr>
    </table>
</form:form>
    </td>
  </tr>

</table>



</body>
</html>