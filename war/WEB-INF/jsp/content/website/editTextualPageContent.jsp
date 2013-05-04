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
            <form:form id="form" name="form" method="POST" action="editTextualPage.html" commandName="command" enctype="multipart/form-data">
 			<form:hidden path="id"/>
 			<form:hidden path="creatorId"/>
 			
 			<c:set var="compulsoryFieldSign" value="<font color=\"red\">*</font>"></c:set>
 			
            <table border="0" align="center" cellpadding="2" cellspacing="0">

				<div id="templateDialog" style="display:none" dir="${lang.dir}">
					<p><fmt:message key="${lang.localeId}.textualPage.templateTitle"/><input type="text" name="templateTitle" id="templateTitle"/></p>
				</div>
				<div id="genericDialog" style="display:none" dir="${lang.dir}"><p>text put here</p></div>
 				
                <tr>
                  <td colspan="4">
                	<table width="1000" cellpadding="2" cellspacing="0" align="center">
                	<tr VALIGN="TOP">
                 	 <td colspan="4" align="center">
                  	<h1><fmt:message key="${lang.localeId}.textualPage.pageTitle"/> <br/>
                   	</h1>
                  	</td>
                 	 </tr>
                 	 </table>
                 	</td>
                </tr>
 
 
                <tr class="form">
					<td colspan="4" align="right"><h3> <fmt:message key="${lang.localeId}.textualPage.detailsTitle"/> ${command.id}	</h3>
					<c:if test="${online}">
					 <fmt:message key="${lang.localeId}.callForProposal.onSite"/>
					&nbsp; <button class="grey" id="offline"><fmt:message key="${lang.localeId}.callForProposal.takeOffSite"/></button>
					&nbsp; <button class="grey" id="onlineUpdate"><fmt:message key="${lang.localeId}.callForProposal.updateSite"/></button>
					&nbsp; <button class="grey" onclick="window.open('/iws/textualPage.html?id=${command.id}','_blank');return false;"><fmt:message key="${lang.localeId}.callForProposal.viewOnSite"/></button>
					</c:if>
					<c:if test="${!online}">
					&nbsp; <button class="grey" id="online"><fmt:message key="${lang.localeId}.callForProposal.putOnSite"/></button>
					</c:if>
					&nbsp; <button class="grey" onclick="window.open('/iws/textualPage.html?id=${command.id}&draft=true','_blank');return false;"><fmt:message key="${lang.localeId}.callForProposal.preview"/></button>
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
					<td colspan="4" style="border:1px #bca2a2 dotted">
						 ${compulsoryFieldSign}<fmt:message key="${lang.localeId}.callForProposal.title"/>
						<form:input htmlEscape="true" cssClass="green long800" path="title"/>
					    <div id="errortitle">				
					</td>
				</tr>
				<tr class="form">
					<td width="300" style="border:1px #bca2a2 dotted" nowrap>
						<fmt:message key="${lang.localeId}.callForProposal.creator"/>
						${command.creator.degreeFullNameHebrew }
 					</td>
					<td width="300" style="border:1px #bca2a2 dotted" nowrap>
						<fmt:message key="${lang.localeId}.callForProposal.desk"/>
         				<form:select path="deskId" cssClass="green" >
      						<form:option value="0"><fmt:message key="${lang.localeId}.callForProposal.select"/></form:option>
       						<c:forEach items="${mopDesks}" var="mopDesk">
	        					<form:option htmlEscape="true" value="${mopDesk.id}"><c:out escapeXml="false" value="${mopDesk.hebrewName}"/></form:option>
       						</c:forEach>
        		        </form:select>
					 <br><font color="red"><form:errors path="deskId" /></font>				
					</td>
					<td width="300" style="border:1px #bca2a2 dotted" nowrap>
       					<form:checkbox cssClass="green" path="requireLogin"/>
						<fmt:message key="${lang.localeId}.callForProposal.requireLogin"/>
					</td>
				</tr>
				<tr class="form">
					<td colspan="3" style="border:1px #bca2a2 dotted" nowrap>
					${compulsoryFieldSign}<fmt:message key="${lang.localeId}.textualPage.inCategory"/>
        				<form:select path="categoryId" id="categoryId" cssClass="green" >
      						<form:option value="0"><fmt:message key="${lang.localeId}.callForProposal.select"/></form:option>
       						<c:forEach items="${categories}" var="category">
	        					<form:option htmlEscape="true" value="${category.id}"><c:out escapeXml="false" value="${category.name}"/></form:option>
       						</c:forEach>
        		        </form:select>
					    <div id="errorcategoryId" title="שגיאה">				
					</td>
				</tr>
 				<tr id="htmlView">
					<td colspan="4" style="border:1px #bca2a2 dotted">
					<table width="950">
  					<tr>
					<td colspan="4">
      					html:<br>
 					</td>
					</tr>				
 					<tr>
 					<td colspan="4" align="center">
           				<textarea class="green editor" id="html" name="html" cols="100" rows="1">${command.html }</textarea>
   					</td>
 					</tr>
  					<tr>
 					<td colspan="4">
						<fmt:message key="${lang.localeId}.textualPage.template"/>
         				<select name="templateId" id="templateId" class="green" >
      						<option value="0"><fmt:message key="${lang.localeId}.callForProposal.select"/></option>
       						<c:forEach items="${templates}" var="template">
	        					<option value="${template.id}"><c:out escapeXml="false" value="${template.title}"/></option>
       						</c:forEach>
        		        </select>
  						<button class="grey showTemplate" ><fmt:message key="${lang.localeId}.textualPage.importTemplate"/>  </button>
 						<button class="grey addTemplate" ><fmt:message key="${lang.localeId}.textualPage.saveTemplate"/>  </button>
 						<button class="grey updateTemplate" ><fmt:message key="${lang.localeId}.textualPage.updateTemplate"/>  </button>
 					</td>
 					</tr>
 					</table>
 					</td>
				</tr>	
  				<tr>
					<td colspan="4" style="border:1px #bca2a2 dotted">
					<table width="950">
  					<tr>
					<td colspan="4">
      					<fmt:message key="${lang.localeId}.textualPage.internalDescription"/><br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="4" align="center">
            			<form:textarea cols="85" rows="1" cssClass="green" path="description"/>
 					</td>
 					</tr>
 					</table>
 					</td>
				</tr>	
				<tr class="form">
					<td style="border:1px #bca2a2 dotted">
        				<form:checkbox cssClass="green" path="isMessage"/>
						<fmt:message key="${lang.localeId}.textualPage.isMessage"/>
					</td>
					<td style="border:1px #bca2a2 dotted"><fmt:message key="${lang.localeId}.textualPage.messageType"/>
       					<form:select path="messageType" cssClass="green" >
      						<form:option value="0"><fmt:message key="${lang.localeId}.callForProposal.select"/></form:option>
      						<form:option value="1">Funding</form:option>
      						<form:option value="2">ARD</form:option>
         		        </form:select>
					</td>
					<td colspan="2" style="border:1px #bca2a2 dotted" nowrap>
 						<fmt:message key="${lang.localeId}.callForProposal.keepInRollingMessagesExpiryTime"/>   
						<input type="text" class="green date medium100" name="keepInRollingMessagesExpiryTimeStr"  id="keepInRollingMessagesExpiryTime" value="${keepInRollingMessagesExpiryTime}"/>
					</td>
				</tr>
				<tr class="form">
					<td style="border:1px #bca2a2 dotted" nowrap>
       					<form:checkbox cssClass="green disableEditor" path="showFile"/>
						<fmt:message key="${lang.localeId}.textualPage.displayFile"/>
					</td>
					<td colspan="3" style="border:1px #bca2a2 dotted" nowrap>
						<table>
						<tr>
						<!-- <td>
						כתובת האינטרנט של הקובץ:
						<form:input cssClass="green" path="fileUrl"/>
						</td> -->
						<td>
 						<fmt:message key="${lang.localeId}.textualPage.file"/>
						<c:if test="${fn:length(command.attachment.file)>0}">
							<a style="text-decoration:underline" href="fileViewer?textualPageId=${command.id}&contentType=${command.attachment.contentType}&attachmentId=1"
								target="_blank"><fmt:message key="${lang.localeId}.textualPage.attachedFile"/></a>	
						</c:if>	
						</td>
						<td>				
						<span style="display: block; width: 60px; height: 27px; overflow: hidden;">
							<button class="green" style="width: 59px; height: 27px; position: relative; top: -1px; left: -1px;"><a href="javascript: void(0)"><fmt:message key="${lang.localeId}.general.browse"/></a></button>
							<input type="file" style="font-size: 50px; width: 70px; opacity: 0; filter:alpha(opacity: 0);  position: relative; top: -40px; left: -5px" name="textualPageFile" id="textualPageFile"/>
						</span>
						</td>
						</tr>
						</table>
					</td>
				</tr>
				
				<tr class="form">
					<td style="border:1px #bca2a2 dotted" nowrap>
        				<form:checkbox cssClass="green" path="wrapExternalPage"/>
						<fmt:message key="${lang.localeId}.textualPage.displayList"/>
					</td>
					<td colspan="3" style="border:1px #bca2a2 dotted" nowrap>
 						<table>
						<tr>
						<td><fmt:message key="${lang.localeId}.textualPage.selectList"/></td>
						<td>
       					<form:select path="externalPageUrl" cssClass="green" >
      						<form:option value="0"><fmt:message key="${lang.localeId}.callForProposal.select"/></form:option>
       						<c:forEach items="${alists}" var="alist">
	        					<form:option htmlEscape="true" value="${alist.id}"><c:out escapeXml="false" value="${alist.name}"/></form:option>
       						</c:forEach>
        		        </form:select>

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
			<td colspan="4" align="center">
				<button class="grey save"><fmt:message key="${lang.localeId}.general.save"/></button>&nbsp;&nbsp;
				<button class="grey delete"><fmt:message key="${lang.localeId}.general.delete"/></button>&nbsp;&nbsp;
				<button class="grey" title="חזרה לתפריט הראשי"  onclick="window.location='welcome.html';return false;"><fmt:message key="${lang.localeId}.callForProposal.mainMenu"/> </button>&nbsp;&nbsp;		
				<button class="grey" title="חזרה"  onclick="history.back();return false;"><fmt:message key="${lang.localeId}.callForProposal.prevPage"/> </button>		
			</td>
		</tr>
    </table>
</form:form>
    </td>
  </tr>

</table>



</body>
</html>