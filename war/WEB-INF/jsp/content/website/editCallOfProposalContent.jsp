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
					<h3>פרטי קול קורא מספר: 255 </h3>  
					 מוצג כרגע באתר
					&nbsp; <button class="grey">הסר מהאתר</button>
					&nbsp; <button class="grey" onclick="window.open('http://ard.huji.ac.il/huard/infoPageViewer.jsp?ardNum=255','_blank');return false;">צפה בדף באתר</button>
					</td>
				</tr>
                <tr class="form">
                <td colspan="4">
                <table width="950" style="border:1px #bca2a2 dotted" cellpadding="2" cellspacing="0" align="center">
                <tr class="form">
					<td colspan="3" style="border:1px #bca2a2 dotted">
						 כותרת:
					<font style="font-weight:bold;"> ESF Exploratory Workshops 2008 </font>
					
					</td>
				</tr>
				<tr class="form">
					<td  width="300" style="border:1px #bca2a2 dotted" nowrap>
						בעל המסמך:
						יאיר זוהר
					</td>
					<td  width="300" style="border:1px #bca2a2 dotted">
						 תאריך פרסום:
						<input type="text" class="green date medium100" value="16/11/11"/>
					</td>
					<td width="300" style="border:1px #bca2a2 dotted" nowrap>
						 תאריך הגשה קובע:
						<input type="text" class="green date submissionDate medium100" value="01/01/12"/>&nbsp;
						<input type="checkbox" id="allYearCheckbox"/>
						 כל השנה
 					</td>
				</tr>
				<tr class="form">
					<td width="300" style="border:1px #bca2a2 dotted">
						 תאריך הגשה נוסף 1:
						<input type="text" class="green date submissionDate medium100" value=""/>
					</td>
					<td width="300" style="border:1px #bca2a2 dotted">
						תאריך הגשה נוסף 2:
						<input type="text" class="green date submissionDate medium100" value=""/>
 					</td>
					<td  style="border:1px #bca2a2 dotted">
						תאריך הגשה נוסף 3:
						<input type="text" class="green date submissionDate medium100" value=""/>
					</td>
				</tr>
                <tr class="form">
					<td colspan="2" style="border:1px #bca2a2 dotted">
						 מממן:
						 <input type="text" class="green long500" id="searchPhrase"/>
					</td>
					<td style="border:1px #bca2a2 dotted">
						סוג קול קורא:
       					<select name="" class="green" >
      						<option value="0">מלגה</option>
      						<option value="1">מענק מחקר</option>
      						<option value="2">חילופי חוקרים</option>
      						<option value="3">כנס</option>
        		        </select>
					</td>
				</tr>
				<tr class="form">
					<td colspan="2" style="border:1px #bca2a2 dotted">
 						  להציג בהודעות הנגללות,
						עד לתאריך:
       					<input type="text" class="date green medium100"/>
					</td>
					<td  style="border:1px #bca2a2 dotted" nowrap>
						מדור:
       					<select name="desk" class="green" >
      						<option value="0">מדור ישראל</option>
      						<option value="1">מדור אירופה</option>
      						<option value="2">מדור ארצות הברית</option>
      						<option value="3">מדור גרמניה</option>
        		        </select>
					</td>
				</tr>
				<tr class="form">
					<td colspan="3" style="border:1px #bca2a2 dotted">
						  כתובת הקול קורא המקורי:
       					<input type="text" class="green" style="width:700px"/>
					</td>
				</tr>
                <tr class="form">
					<td  style="border:1px #bca2a2 dotted" nowrap>
       					<input type="checkbox"/>
						להציג רק לבעלי סיסמה
					</td>
					<td style="border:1px #bca2a2 dotted">
      					<input type="checkbox"/>
						לשלוח תזכורת לקראת סיום השנה
 					</td>
					<td  style="border:1px #bca2a2 dotted" nowrap>
       					<input type="checkbox"/>
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
 						<span class="editorText" style="text-align:right;direction:rtl;">יש להעביר 3 עותקים לרשות למו"פ
						</span>
 						<span class="textareaEditorSpan" style="text-align:right;direction:rtl;">
           					<textarea class="textareaEditor" id="textareaEditor1" cols="100" rows="1" class="green" style="display:none;">יש להעביר 3 עותקים לרשות למו"פ</textarea>
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
 						<span class="editorText" style="text-align:right;direction:rtl;">&nbsp;
						</span>
 						<span class="textareaEditorSpan" style="text-align:right;direction:rtl;">
           					<textarea class="textareaEditor" id="textareaEditor2" cols="100" rows="1" class="green" style="display:none;"></textarea>
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
						<a href="http://www2.esf.org/WD110AWP/WD110Awp.exe/CONNECT/EW_Application_Form">http://www2.esf.org/WD110AWP/WD110Awp.exe/CONNECT/EW_Application_Form</a>						
						</span>
 						<span class="textareaEditorSpan" style="text-align:right;direction:rtl;">
           					<textarea class="textareaEditor" id="textareaEditor3" cols="100" rows="1" class="green" style="display:none;">
           					<a href="http://www2.esf.org/WD110AWP/WD110Awp.exe/CONNECT/EW_Application_Form">http://www2.esf.org/WD110AWP/WD110Awp.exe/CONNECT/EW_Application_Form</a>
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
 						<span class="editorText" style="text-align:right;direction:rtl;">
            			<p> כאן יכנס תאור מפורט </p>  
  						<b> מחקר כלשהו עם התייחסות לנושאים הבאים: </b><br> 
 								<ul>  <li> ביו רפואה </li> 
 								<li> מדעי הסביבה </li> 
 								<li> הנדסה ופיזיקה</li> 
 								<li> מדעי החברה </li> 
 						</ul> 
						<p>הצעות יתקבלו מה  <b>  1 לפברואר עד 31 לדצמבר 2012 </b>.</p>  
 						<br> 
						</span>
 						<span class="textareaEditorSpan" style="text-align:right;direction:rtl;">
           					<textarea class="textareaEditor" id="textareaEditor4" cols="100" rows="1" class="green" style="display:none;">
            			<p> כאן יכנס תאור מפורט </p>  
  						<b> מחקר כלשהו עם התייחסות לנושאים הבאים: </b><br> 
 								<ul>  <li> ביו רפואה </li> 
 								<li> מדעי הסביבה </li> 
 								<li> הנדסה ופיזיקה</li> 
 								<li> מדעי החברה </li> 
 						</ul> 
						<p>הצעות יתקבלו מה <b>  1 לפברואר עד 31 לדצמבר 2012 </b>.</p>  
 						<br> 
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
 						<span class="editorText" style="text-align:right;direction:rtl;">
            			המגישים הינם חוקרים או בעלי מלגה מאוניברסטאות אירופאיות או מרכזי מחקר בארצות שכהגלחהנ. <b>לא יתקבלו הצעות עם שותפים מארצות אחרות.</b>
						</span>
 						<span class="textareaEditorSpan" style="text-align:right;direction:rtl;">
           					<textarea class="textareaEditor" id="textareaEditor5" cols="100" rows="1" class="green" style="display:none;">
            			המגישים הינם חוקרים או בעלי מלגה מאוניברסטאות אירופאיות או מרכזי מחקר בארצות שכהגלחהנ. <b>לא יתקבלו הצעות עם שותפים מארצות אחרות.</b>
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
            			הסדנה תתקיים במדינה החברה בארגון ESF בין התאריכים 1 לפברואר ל 31 לדצמבר.
						</span>
 						<span class="textareaEditorSpan" style="text-align:right;direction:rtl;">
           					<textarea class="textareaEditor" id="textareaEditor6" cols="100" rows="1" class="green" style="display:none;">
           				הסדנה תתקיים במדינה החברה בארגון ESF בין התאריכים 1 לפברואר ל 31 לדצמבר.
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
 						<span class="editorText" style="text-align:right;direction:rtl;">&nbsp;
						</span>
 						<span class="textareaEditorSpan" style="text-align:right;direction:rtl;">
           					<textarea class="textareaEditor" id="textareaEditor7" cols="100" rows="1" class="green" style="display:none;">
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
 						<span class="editorText" style="text-align:right;direction:rtl;">&nbsp;
						</span>
 						<span class="textareaEditorSpan" style="text-align:right;direction:rtl;">
           					<textarea class="textareaEditor" id="textareaEditor8" cols="100" rows="1" class="green" style="display:none;">
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
 						<span class="editorText" style="text-align:right;direction:rtl;">
				<br> 
 				<b>פורמט ההגשה </b><br> 
				<ul><li>  הגשת המועמדות כולל קורות חיים תהיה באורך עמוד אחד בפורמט וורד או pdf  </li> 
       			<li> פונט לא קטן מ 10 </li> 
 				</ul> <br>   
 				<b> תהליך ההגשה</b><br>  
       			<ul><li> יש להגיש את ההצעות <b> און ליין </b>.</li> 
       			<li> הגשות ראשונות ייחשבו לסופיות ( <b> אין קבלת שינויים </b> ). </li> 
 				</ul> <br>   
						</span>
 						<span class="textareaEditorSpan" style="text-align:right;direction:rtl;">
           					<textarea class="textareaEditor" id="textareaEditor9" cols="100" rows="1" class="green" style="display:none;">
				<br> 
 				<b>פורמט ההגשה </b><br> 
				<ul><li>  הגשת המועמדות כולל קורות חיים תהיה באורך עמוד אחד בפורמט וורד או pdf  </li> 
        			<li> פונט לא קטן מ 10 </li> 
 				</ul> <br>   
 				<b> תהליך ההגשה</b><br>  
       			<ul><li> יש להגיש את ההצעות <b> און ליין </b>.</li> 
       			<li> הגשות ראשונות ייחשבו לסופיות ( <b> אין קבלת שינויים </b> ). </li> 
 				</ul> <br>   
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
 						<span class="editorText" style="text-align:right;direction:rtl;">שנתיים
						</span>
 						<span class="textareaEditorSpan" style="text-align:right;direction:rtl;">
           					<textarea class="textareaEditor" id="textareaEditor10" cols="100" rows="1" class="green" style="display:none;">
 							שנתיים
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
 						<span class="editorText" style="text-align:right;direction:rtl;">עד 15,000 אירו
						</span>
 						<span class="textareaEditorSpan" style="text-align:right;direction:rtl;">
           					<textarea class="textareaEditor" id="textareaEditor11" cols="100" rows="1" class="green" style="display:none;">
 							עד 15,000 אירו
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
				<button title="שמירה" class="grey submit" > שמירה </button>&nbsp;&nbsp;
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