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
            <form:form id="form" name="form" method="POST" action="callOfProposal.html" commandName="command" enctype="multipart/form-data">
 			<form:hidden path="id"/>
			
 			
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
					<h3>פרטי קול קורא מספר: ${command.id} </h3>  
					 מוצג כרגע באתר
					&nbsp; <button class="grey">הסר מהאתר</button>
					&nbsp; <button class="grey" onclick="window.open('http://ard.huji.ac.il/huard/infoPageViewer.jsp?ardNum=${command.id}','_blank');return false;">צפה בדף באתר</button>
					</td>
				</tr>
                <tr class="form">
                <td colspan="4">
                <table width="950" style="border:1px #bca2a2 dotted" cellpadding="2" cellspacing="0" align="center">
                <tr class="form">
					<td colspan="3" style="border:1px #bca2a2 dotted">
						 ${compulsoryFieldSign}כותרת:
						<input type="text" htmlEscape="true" class="green long800" name="title" value="${title}"/>
					</td>
				</tr>
				<tr class="form">
					<td  width="300" style="border:1px #bca2a2 dotted" nowrap>
						בעל המסמך:
						יאיר זוהר
					</td>
					<td  width="300" style="border:1px #bca2a2 dotted">
						 ${compulsoryFieldSign}תאריך פרסום:
						<input type="text" class="green date medium100" name="publication" value="${publicationTime}" readonly="readonly"/>
					</td>
					<td width="320" style="border:1px #bca2a2 dotted" nowrap>
						 ${compulsoryFieldSign}תאריך הגשה קובע:
						<input type="text" class="green date submissionDate medium100" name="finalSubmission" value="${finalSubmissionTime}" readonly="readonly"/>&nbsp;
						<form:checkbox cssClass="green" path="allYearSubmission"/>
						 כל השנה
 					</td>
				</tr>
				<tr class="form">
 					<td width="300" style="border:1px #bca2a2 dotted">
						 תאריך הגשה נוסף:
						<input type="text" class="green date medium100" name="submissionDate1" value="${submissionDate1}" readonly="readonly"/>
					</td>
					<td width="300" style="border:1px #bca2a2 dotted">
						תאריך הגשה נוסף:
						<input type="text" class="green date medium100" name="submissionDate2" value="${submissionDate2}" readonly="readonly"/>
 					</td>
					<td  style="border:1px #bca2a2 dotted">
						תאריך הגשה נוסף:
						<input type="text" class="green date medium100" name="submissionDate3" value="${submissionDate3}" readonly="readonly"/>
					</td>
				</tr>
                <tr class="form">
					<td colspan="2" style="border:1px #bca2a2 dotted">
						 מממן:
						 <input type="text" class="green long500" id="searchPhrase" value="${selectedFund}"/> 
						<form:hidden path="fundId" />
					</td>
					<td style="border:1px #bca2a2 dotted">
						סוג קול קורא:
       					<form:select path="typeId" cssClass="green" >
      						<form:option value="0">בחר/י</form:option>
      						<form:option value="1">מענק מחקר</form:option>
      						<form:option value="2">חילופי חוקרים</form:option>
      						<form:option value="3">כנס</form:option>
      						<form:option value="4">מלגה</form:option>
        		        </form:select>
					</td>
				</tr>
				<tr class="form">
					<td colspan="2" style="border:1px #bca2a2 dotted">
 						  ${compulsoryFieldSign}להציג בהודעות הנגללות,
						עד לתאריך:
						<input type="text" class="green date medium100" name="keepInRollingMessagesExpiry" value="${keepInRollingMessagesExpiryTime}" readonly="readonly"/>
					</td>
					<td  style="border:1px #bca2a2 dotted" nowrap>
						מדור:
         				<form:select path="deskId" cssClass="green" >
      						<form:option value="0">בחר/י</form:option>
       						<c:forEach items="${mopDesks}" var="mopDesk">
	        					<form:option htmlEscape="true" value="${mopDesk.id}"><c:out escapeXml="false" value="${mopDesk.hebrewName}"/></form:option>
       						</c:forEach>
        		        </form:select>
					</td>
				</tr>
				<tr class="form">
					<td colspan="3" style="border:1px #bca2a2 dotted">
						  כתובת הקול קורא המקורי:
						<form:input htmlEscape="true" cssClass="green long800" path="originalCallWebAddress" />
					</td>
				</tr>
                <tr class="form">
					<td  style="border:1px #bca2a2 dotted" nowrap>
       					<form:checkbox cssClass="green" path="requireLogin"/>
       					להציג רק לבעלי סיסמה
					</td>
					<td style="border:1px #bca2a2 dotted">
      					<form:checkbox cssClass="green" path="allYearSubmissionYearPassedAlert"/>
						לשלוח תזכורת לקראת סיום השנה
 					</td>
					<td  style="border:1px #bca2a2 dotted" nowrap>
       					<form:checkbox cssClass="green" path="showDescriptionOnly"/>
						להציג תיאור בלבד
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
					<td colspan="4" style="border:1px #bca2a2 dotted">
					<table width="950">
  					<tr>
					<td colspan="3">
      					פרטים הקשורים להגשה:<br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="3" align="center">
 					<div class="editor" style="text-align:right;direction:rtl;">
 						<span class="editorText" style="text-align:right;direction:rtl;">${command.submissionDetails }&nbsp;
						</span>
 						<span class="textareaEditorSpan" style="text-align:right;direction:rtl;">
           					<textarea class="textareaEditor" id="submissionDetailsTA" name="submissionDetailsTA" cols="100" rows="1" class="green" style="display:none;">${command.submissionDetails }</textarea>
						</span>
					</div>
 					</td>
 					</tr>
 					<tr>
					<td colspan="3">&nbsp;</td>
					</tr>
 					<tr>
					<td colspan="3" style="text-align:right">
					<span id="addedText">מקום ההגשה ברשות למו"פ, בתאריך </span>&nbsp;<button class="grey add"><span class="ui-icon ui-icon-arrowthick-1-n"></span></button>
					</td>
					</tr>
					<tr>
					<td colspan="3" style="text-align:right">	
 					<span id="addedText">הגשה ישירות לקרן </span>&nbsp;<button class="grey add"><span class="ui-icon ui-icon-arrowthick-1-n"></span></button><br/>	
					</td>
					</tr>
					<tr>
					<td colspan="3" style="text-align:right">	
  					<span id="addedText">שלח העתק בדואר אלקטרוני ל- Ms. Netanya Bar Cohva Assistant 02-6586668</span>&nbsp;<button class="grey add"><span class="ui-icon ui-icon-arrowthick-1-n"></span></button><br/>		
 					</td>
					</tr>
 					<tr>
					<td colspan="3" style="text-align:right">	
 					<span id="addedText">יש להעביר xxx עותקים לרשות למו"פ</span>&nbsp;<button class="grey add"><span class="ui-icon ui-icon-arrowthick-1-n"></span></button><br/>		
       				</td>
 					</tr>
 					</table>
 					</td>
				</tr>	
				<tr>
					<td colspan="4" style="border:1px #bca2a2 dotted">
					<table width="950">
  					<tr><td>אנשי קשר:</td>
  					</tr>
 					<tr>
					<td colspan="3" align="center">
 					<div class="editor" style="text-align:right;direction:rtl;">
 						<span class="editorText" style="text-align:right;direction:rtl;">${command.contactPersonDetails }&nbsp;
						</span>
 						<span class="textareaEditorSpan" style="text-align:right;direction:rtl;">
           					<textarea class="textareaEditor" id="contactPersonDetailsTA" name="contactPersonDetailsTA" cols="100" rows="1" class="green" style="display:none;">${command.contactPersonDetails }</textarea>
						</span>
					</div>
 					</td>
 					</tr>
					<tr>
					<td colspan="3">&nbsp;</td>
					</tr>
					<tr>
					<td colspan="3" style="text-align:right">
					<span id="addedText">Ms. Jane Turner Coordinator for Europe - European Union & others 02-6586676 </span>&nbsp;<button class="grey add"><span class="ui-icon ui-icon-arrowthick-1-n"></span></button>
					</td>
					</tr>
					<tr>
					<td colspan="3" style="text-align:right">
					<span id="addedText">Ms. Netanya Bar Cohva Assistant 02-6586668 </span>&nbsp;<button class="grey add"><span class="ui-icon ui-icon-arrowthick-1-n"></span></button>
					</td>
					</tr>
  					</table>
 					</td>
				</tr>	
				<tr>
					<td colspan="4" style="border:1px #bca2a2 dotted">
					<table width="950">
  					<tr><td>טפסים ומידע הקשור לטפסים:</td>
  					</tr>
 					<tr>
					<td colspan="3" align="center">
 					<div class="editor" style="text-align:right;direction:rtl;">
 						<span class="editorText" style="text-align:right;direction:rtl;">
							&nbsp;
						</span>
 						<span class="textareaEditorSpan" style="text-align:right;direction:rtl;">
           					<textarea class="textareaEditor" id="formsTA" name="formsTA" cols="100" rows="1" class="green" style="display:none;">
          					</textarea>
						</span>
					</div>
 					</td>
 					</tr>
					<tr>
					<td colspan="3">&nbsp;</td>
					</tr>
					<tr>
					<td colspan="3" style="text-align:right">
					<span id="addedText"><a style="text-decoration:underline" href="fileViewer?conferenceProposalId=70&attachFile=guestsAttach&contentType=application/vnd.oasis.opendocument.spreadsheet&attachmentId=1"
								target="_blank">טופס הרשמה מספר 1123</a> </span>&nbsp;<button class="grey add"><span class="ui-icon ui-icon-arrowthick-1-n"></span></button>
					

					</td>
					</tr>
					<tr>
					<td colspan="3" style="text-align:right">
					<span id="addedText"><a style="text-decoration:underline" href="fileViewer?conferenceProposalId=70&attachFile=guestsAttach&contentType=application/vnd.oasis.opendocument.spreadsheet&attachmentId=1"
								target="_blank">טופס הרשמה מספר 5600</a> </span>&nbsp;<button class="grey add"><span class="ui-icon ui-icon-arrowthick-1-n"></span></button>
					</td>
					</tr>
					<tr>
					<td>
						<div style="display: block; width: 100px; height: 27px; overflow: hidden;">
							<button class="green" style="width: 100px; height: 27px; position: relative; top: -1px; left: -1px;"><a href="javascript: void(0)">הוסף קובץ</a></button>
							<input type="file" style="font-size: 50px; width: 100px; opacity: 0; filter:alpha(opacity: 0);  position: relative; top: -40px; left: -5px" name="formAttach" id="formAttach"/>
						</div>
					</td>
					</tr>
  					</table>
 					</td>
 				</tr>
				<tr>
					<td colspan="4" style="border:1px #bca2a2 dotted">
					<table width="950">
  					<tr>
					<td colspan="3">
      					תאור מפורט:<br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="3" align="center">
 					<div class="editor" style="text-align:right;direction:rtl;">
 						<span class="editorText" style="text-align:right;direction:rtl;">${command.description }&nbsp;
						</span>
 						<span class="textareaEditorSpan" style="text-align:right;direction:rtl;">
           					<textarea class="textareaEditor" id="descriptionTA" name="descriptionTA" cols="100" rows="1" class="green" style="display:none;">
							${command.description }           					
							</textarea>
						</span>
					</div>
   					</td>
 					</tr>
 					</table>
 					</td>
				</tr>	
				<tr>
					<td colspan="4" style="border:1px #bca2a2 dotted">
					<table width="950">
  					<tr>
					<td colspan="3">
      					הדרישות לזכאות:<br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="3" align="center">
 					<div class="editor" style="text-align:right;direction:rtl;">
 						<span class="editorText" style="text-align:right;direction:rtl;">${command.eligibilityRequirements }&nbsp;
						</span>
 						<span class="textareaEditorSpan" style="text-align:right;direction:rtl;">
           					<textarea class="textareaEditor" id="eligibilityRequirementsTA"  name="eligibilityRequirementsTA" cols="100" rows="1" class="green" style="display:none;">
            					${command.eligibilityRequirements }							
            				</textarea>
						</span>
					</div>
 					</td>
 					</tr>
 					</table>
 					</td>
				</tr>											
				<tr>
					<td colspan="4" style="border:1px #bca2a2 dotted">
					<table width="950">
  					<tr>
					<td colspan="3">
      					מיקום הפעילות:<br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="3" align="center">
 					<div class="editor" style="text-align:right;direction:rtl;">
 						<span class="editorText" style="text-align:right;direction:rtl;">
            			${command.activityLocation }&nbsp;
						</span>
 						<span class="textareaEditorSpan" style="text-align:right;direction:rtl;">
           					<textarea class="textareaEditor" id="activityLocationTA" name="activityLocationTA" cols="100" rows="1" class="green" style="display:none;">
           					${command.activityLocation }
							</textarea>
						</span>
					</div>
 					</td>
 					</tr>
 					</table>
 					</td>
				</tr>											
				<tr>
					<td colspan="4" style="border:1px #bca2a2 dotted">
					<table width="950">
  					<tr>
					<td colspan="3">
      					שיתופי פעולה אפשריים:<br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="3" align="center">
					<div class="editor" style="text-align:right;direction:rtl;">
 						<span class="editorText" style="text-align:right;direction:rtl;">${command.possibleCollaboration }&nbsp;
						</span>
 						<span class="textareaEditorSpan" style="text-align:right;direction:rtl;">
           					<textarea class="textareaEditor" id="possibleCollaborationTA" name="possibleCollaborationTA" cols="100" rows="1" class="green" style="display:none;">${command.possibleCollaboration }
 							</textarea>
						</span>
					</div>
 					</td>
 					</tr>
 					</table>
 					</td>
				</tr>											
				<tr>
					<td colspan="4" style="border:1px #bca2a2 dotted">
					<table width="950">
  					<tr>
					<td colspan="3">
      					פרטים הקשורים לתקציב:<br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="3" align="center">
					<div class="editor" style="text-align:right;direction:rtl;">
 						<span class="editorText" style="text-align:right;direction:rtl;">${command.budgetDetails }&nbsp;
						</span>
 						<span class="textareaEditorSpan" style="text-align:right;direction:rtl;">
           					<textarea class="textareaEditor" id="budgetDetailsTA" name="budgetDetailsTA" cols="100" rows="1" class="green" style="display:none;">${command.budgetDetails }
 							</textarea>
						</span>
					</div>
 					</td>
 					</tr>
 					<tr>
					<td colspan="3" style="text-align:right">
      				<span id="addedText">התקציב דורש את אישורו של מנהל התקציב מר נועם גלזר 02-6586549 </span>&nbsp;	<button class="grey add"><span class="ui-icon ui-icon-arrowthick-1-n"></span></button>
      				</td>
 					</tr>
 					</table>
 					</td>
				</tr>											
				<tr>
					<td colspan="4" style="border:1px #bca2a2 dotted">
					<table width="950">
  					<tr>
					<td colspan="3">
      					הערות נוספות:<br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="3" align="center">
					<div class="editor" style="text-align:right;direction:rtl;">
 						<span class="editorText" style="text-align:right;direction:rtl;">${command.additionalInformation }&nbsp;
						</span>
 						<span class="textareaEditorSpan" style="text-align:right;direction:rtl;">
           					<textarea class="textareaEditor" id="additionalInformationTA" name="additionalInformationTA" cols="100" rows="1" class="green" style="display:none;">
							${command.additionalInformation } 							
							</textarea>
						</span>
  					</div>         			
 					</td>
 					</tr>
 					</table>
 					</td>
				</tr>											
				<tr class="form">
					<td colspan="4" style="border:1px #bca2a2 dotted">
					<table width="950">
  					<tr>
					<td colspan="3">
      					תקופת מימון מקסימלית:<br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="3" align="center">
					<div class="editor" style="text-align:right;direction:rtl;">
 						<span class="editorText" style="text-align:right;direction:rtl;">${command.fundingPeriod}&nbsp;
						</span>
 						<span class="textareaEditorSpan" style="text-align:right;direction:rtl;">
           					<textarea class="textareaEditor" id="fundingPeriodTA" name="fundingPeriodTA" cols="100" rows="1" class="green" style="display:none;">${command.fundingPeriod}
 							</textarea>
						</span>
					</div>
 					</td>
 					</tr>
  					</table>
 					</td>
 				</tr>
 				<tr>
					<td colspan="4" style="border:1px #bca2a2 dotted">
					<table width="950">
  					<tr>
					<td colspan="3">
      					סכום המענק:<br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="3" align="center">
					<div class="editor" style="text-align:right;direction:rtl;">
 						<span class="editorText" style="text-align:right;direction:rtl;">${command.amountOfGrant}&nbsp;
						</span>
 						<span class="textareaEditorSpan" style="text-align:right;direction:rtl;">
           					<textarea class="textareaEditor" id="amountOfGrantTA" name="amountOfGrantTA" cols="100" rows="1" class="green" style="display:none;">${command.amountOfGrant}
 							</textarea>
						</span>
					</div>
             			<textarea cols="100" rows="1" class="green" style="display:none;"></textarea>
						<div class="editor" style="text-align:right;direction:rtl;">
						
						</div>
 					</td>
 					</tr>
  					</table>
 					</td>
				</tr>

	
 		
		<tr class="form">
			<td colspan="4" align="center" style="border:1px #bca2a2 dotted">
				<button title="שמירה" class="grey save" > שמירה </button>&nbsp;&nbsp;
				<button class="grey" title="" >צור הודעה בפוסט </button>&nbsp;&nbsp;		
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