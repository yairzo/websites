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
 			<form:hidden path="title"/>
 			<form:hidden path="urlTitle"/>
 			
 			<c:set var="compulsoryFieldSign" value="<font color=\"red\">*</font>"></c:set>
 			
            <table border="0" align="center" cellpadding="2" cellspacing="0">
			   		<div id="picture_popup" style="display:none"></div>

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
					<td colspan="4" align="${lang.align}"><h3> <fmt:message key="${lang.localeId}.textualPage.detailsTitle"/> ${command.id}	</h3>
 					<c:if test="${online}">
					<fmt:message key="${lang.localeId}.callForProposal.onSite"/>
 					</c:if>
 				</td>
				</tr>
 
              <tr class="form">
					<td colspan="4" align="${lang.align}">
					<button class="grey save"><fmt:message key="${lang.localeId}.general.save"/></button>&nbsp;
					<c:if test="${online}">
					<button class="grey" id="offline"><fmt:message key="${lang.localeId}.callForProposal.takeOffSite"/></button>&nbsp; 
					<button class="grey" id="onlineUpdate"><fmt:message key="${lang.localeId}.callForProposal.updateSite"/></button>&nbsp; 
					<button class="grey" onclick="window.open('/page/${command.urlTitle}','_blank');return false;"><fmt:message key="${lang.localeId}.callForProposal.viewOnSite"/></button>&nbsp; 
					</c:if>
					<c:if test="${!online}">
					<button class="grey" id="online"><fmt:message key="${lang.localeId}.callForProposal.putOnSite"/></button>&nbsp; 
					</c:if>
					<button class="grey" onclick="window.open('/textualPage.html?id=${command.id}&draft=true','_blank');return false;"><fmt:message key="${lang.localeId}.callForProposal.preview"/></button>&nbsp; 
					<button class="grey delete"><fmt:message key="${lang.localeId}.general.delete"/></button>&nbsp;
					<button class="grey" onclick="window.location='welcome.html';return false;"><fmt:message key="${lang.localeId}.callForProposal.mainMenu"/> </button>&nbsp;		
					<button class="grey" onclick="window.location='textualPages.html';return false;"><fmt:message key="${lang.localeId}.callForProposal.prevPage"/> </button>		
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
						<input type="text" htmlEscape="true" class="green long800" name="tempTitle" id="tempTitle" value="${title}"/>
					    <div id="errortitle">				
					</td>
				</tr>
                <tr class="form">
					<td colspan="4" style="border:1px #bca2a2 dotted">
						 ${compulsoryFieldSign}<fmt:message key="${lang.localeId}.callForProposal.urlTitle"/>
						<input type="text" dir="ltr" htmlEscape="true" class="green long800" name="tempUrlTitle" id="tempUrlTitle" value="${urlTitle}"/>
					    <div id="errorurltitle">				
					</td>
				</tr>
				<c:if test="${websiteName!='websiteNano' }">
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
				</c:if>
				<c:if test="${websiteName=='websiteNano' }">
				<tr class="form">
					<td colspan="3" style="border:1px #bca2a2 dotted" nowrap>
						<fmt:message key="${lang.localeId}.callForProposal.creator"/>
						${command.creator.degreeFullNameHebrew }
 					</td>
 				</tr>
				</c:if>
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
 						<div id="htmlEditor" contenteditable="true" style="border:black thin dotted;text-align:${lang.align};">
 							${command.html}	<c:if test="${fn:length(command.html)<5}">&nbsp;&nbsp;</c:if>
  						</div>
           				<textarea class="green" id="html" name="html" cols="100" rows="1" style="display:none">${command.html}</textarea>
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
						<br><form:checkbox cssClass="green" path="neverExpires" id="neverExpires"/>
						<fmt:message key="${lang.localeId}.textualPage.neverExpires"/>
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
							<a style="text-decoration:underline" href="/page/${command.attachment.filename}"
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
					<td colspan="4" align="${lang.align}">
					<button class="grey save"><fmt:message key="${lang.localeId}.general.save"/></button>&nbsp;
					<c:if test="${online}">
					<button class="grey" id="offline"><fmt:message key="${lang.localeId}.callForProposal.takeOffSite"/></button>&nbsp; 
					<button class="grey" id="onlineUpdate"><fmt:message key="${lang.localeId}.callForProposal.updateSite"/></button>&nbsp; 
					<button class="grey" onclick="window.open('/page/${command.urlTitle}','_blank');return false;"><fmt:message key="${lang.localeId}.callForProposal.viewOnSite"/></button>&nbsp; 
					</c:if>
					<c:if test="${!online}">
					<button class="grey" id="online"><fmt:message key="${lang.localeId}.callForProposal.putOnSite"/></button>&nbsp; 
					</c:if>
					<button class="grey" onclick="window.open('/textualPage.html?id=${command.id}&draft=true','_blank');return false;"><fmt:message key="${lang.localeId}.callForProposal.preview"/></button>&nbsp; 
					<button class="grey delete"><fmt:message key="${lang.localeId}.general.delete"/></button>&nbsp;
					<button class="grey" onclick="window.location='welcome.html';return false;"><fmt:message key="${lang.localeId}.callForProposal.mainMenu"/> </button>&nbsp;		
					<button class="grey" onclick="history.back();return false;"><fmt:message key="${lang.localeId}.callForProposal.prevPage"/> </button>		
				</td>
			</tr>
			
			<tr class="form">
			<td colspan="4" >
               <table width="950" style="border:1px #bca2a2 dotted" cellpadding="2" cellspacing="0" align="center">
 				<tr><td colspan="8" align="${lang.align}"><fmt:message key="${lang.localeId}.picturePool"/>:</td></tr>
 				<tr>
				<c:forEach items="${images}" var="image" varStatus="varStatus">
  						<c:choose>
  						<c:when test="${image.approved==1}">
  							<c:set var="borderStyle" value="border: 2px solid green;"/>
  						</c:when>
  						<c:otherwise>
  							<c:set var="borderStyle" value="border: 2px solid grey;"/>
  						</c:otherwise>
  						</c:choose>
  						<td width="60px">
  							<span id="img${image.id}">
  								<img id="${image.id}" class="galleryImage" style="${borderStyle}" src="/imageViewer?imageId=${image.id}&attachType=bodyImage" width="42" height="42">
	  							<br/>
  								${image.name}&nbsp;
  								<br/>
  							</span>
    						</td>
 				</c:forEach>
			  	</tr>
			   	<tr>
			  		<td align="center" colspan="8">
				  		<a href="/editTextualPage.html?id=${command.id}&page=${previousPage} ">&lt;</a>
				  		&nbsp;
				  		&nbsp;
				  		<a href="/editTextualPage.html?id=${command.id}&page=${nextPage}">&gt;</a>
					</td>
			  	</tr>
			   	<tr>
			   	
			  		<td align="${lang.align}" colspan="8">
				  		<a id="addPicture" target="_blank"><fmt:message key="${lang.localeId}.picturePool.addPicture"/></a>
					</td>
			  	</tr>
		    </table>
		</td>
		</tr>
    </table>
</form:form>
    </td>
  </tr>

</table>



</body>
</html>