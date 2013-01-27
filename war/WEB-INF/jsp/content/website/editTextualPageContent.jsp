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
            <form:form id="form" name="form" method="POST" action="editTextualPage.html" commandName="command" enctype="multipart/form-data">
 			<form:hidden path="id"/>
 			
 			<c:set var="compulsoryFieldSign" value="<font color=\"red\">*</font>"></c:set>
 			
            <table border="0" align="center" cellpadding="2" cellspacing="0">

				<div id="templateDialog" style="display:none" dir="rtl">
					<p>רשום כותרת לתבנית:<input type="text" name="templateTitle" id="templateTitle"/></p>
				</div>
				<div id="genericDialog" title="עזרה" style="display:none" dir="rtl"><p>text put here</p></div>
 				
                <tr>
                  <td colspan="4">
                	<table width="1000" cellpadding="2" cellspacing="0" align="center">
                	<tr VALIGN="TOP">
                 	 <td colspan="4" align="center">
                  	<h1>עריכת דף תוכן <br/>
                   	</h1>
                  	</td>
                 	 </tr>
                 	 </table>
                 	</td>
                </tr>
 
 
                <tr class="form">
					<td colspan="4" align="right"><h3> פרטי דף טקסט מספר: ${command.id}	</h3>
					<c:if test="${online}">
					 מוצג כרגע באתר
					&nbsp; <button class="grey" onclick="window.open('http://ard.huji.ac.il/huard/pubPageViewer.jsp?ardNum=${command.id}?draft=true','_blank');return false;">תצוגה מקדימה</button>
					&nbsp; <button class="grey" onclick="window.open('http://ard.huji.ac.il/huard/pubPageViewer.jsp?ardNum=${command.id}','_blank');return false;">צפה בדף באתר</button>
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
					<td colspan="4" style="border:1px #bca2a2 dotted">
						 ${compulsoryFieldSign}כותרת:
						<form:input htmlEscape="true" cssClass="green long800" path="title"/>
					<br> <font color="red"><form:errors path="title" /></font>				
					</td>
				</tr>
				<tr class="form">
					<td width="300" style="border:1px #bca2a2 dotted" nowrap>
						בעל המסמך:
						${command.creator.degreeFullNameHebrew }
 					</td>
					<td width="300" style="border:1px #bca2a2 dotted" nowrap>
						מדור:
         				<form:select path="deskId" cssClass="green" >
      						<form:option value="0">בחר/י</form:option>
       						<c:forEach items="${mopDesks}" var="mopDesk">
	        					<form:option htmlEscape="true" value="${mopDesk.id}"><c:out escapeXml="false" value="${mopDesk.hebrewName}"/></form:option>
       						</c:forEach>
        		        </form:select>
					 <br><font color="red"><form:errors path="deskId" /></font>				
					</td>
					<td width="300" style="border:1px #bca2a2 dotted" nowrap>
       					<form:checkbox cssClass="green" path="requireLogin"/>
						להציג רק לבעלי סיסמה
					</td>
				</tr>
				<tr class="form">
					<td colspan="3" style="border:1px #bca2a2 dotted" nowrap>
					שיוך לקטגוריה:
        				<form:select path="categoryId" id="categoryId" cssClass="green" >
      						<form:option value="0">בחר/י</form:option>
       						<c:forEach items="${categories}" var="category">
	        					<form:option htmlEscape="true" value="${category.id}"><c:out escapeXml="false" value="${category.name}"/></form:option>
       						</c:forEach>
        		        </form:select>
					</td>
				</tr>
 				<tr id="htmlDiv">
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
						תבנית:
         				<select name="templateId" id="templateId" class="green" >
      						<option value="0">בחר/י</option>
       						<c:forEach items="${templates}" var="template">
	        					<option value="${template.id}"><c:out escapeXml="false" value="${template.title}"/></option>
       						</c:forEach>
        		        </select>
  						<button class="grey showTemplate" > ייבוא תבנית לעורך </button>
 						<button class="grey addTemplate" > שמירת תבנית </button>
 						<button class="grey updateTemplate" > עדכון תבנית </button>
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
      					תאור (לשימוש פנימי):<br>
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
					<td style="border:1px #bca2a2 dotted" nowrap>
        					<form:checkbox cssClass="green" path="isMessage"/>
						הודעה
					</td>
					<td colspan="3" style="border:1px #bca2a2 dotted" nowrap>
 						 להציג בהודעות הנגללות,
						עד לתאריך:
						<input type="text" class="green date medium100" name="keepInRollingMessagesExpiryTimeStr"  id="keepInRollingMessagesExpiryTime" value="${keepInRollingMessagesExpiryTime}"/>
					</td>
				</tr>
				<tr class="form">
					<td style="border:1px #bca2a2 dotted" nowrap>
       					<form:checkbox cssClass="green disableEditor" path="showFile"/>
						דף שמציג קובץ
					</td>
					<td colspan="3" style="border:1px #bca2a2 dotted" nowrap>
						<table>
						<tr>
						<!-- <td>
						כתובת האינטרנט של הקובץ:
						<form:input cssClass="green" path="fileUrl"/>
						</td> -->
						<td>
 						קובץ:
						<c:if test="${fn:length(command.attachment.file)>0}">
							<a style="text-decoration:underline" href="fileViewer?textualPageId=${command.id}&contentType=${command.attachment.contentType}&attachmentId=1"
								target="_blank">קובץ מצורף</a>	
						</c:if>	
						</td>
						<td>				
						<span style="display: block; width: 60px; height: 27px; overflow: hidden;">
							<button class="green" style="width: 59px; height: 27px; position: relative; top: -1px; left: -1px;"><a href="javascript: void(0)">עיון</a></button>
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
						דף שמציג רשימה
					</td>
					<td colspan="3" style="border:1px #bca2a2 dotted" nowrap>
 						<table>
						<tr>
						<td>בחר/י רשימה:</td>
						<td>
       					<form:select path="externalPageUrl" cssClass="green" >
      						<form:option value="0">בחר/י</form:option>
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
				<button title="שמירה" class="grey save" > שמירת טיוטה </button>&nbsp;&nbsp;
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