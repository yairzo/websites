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
					<td colspan="4" align="right"><h3> פירוט
					</h3></td>
				</tr>
	       		<tr class="form">
        		<td colspan="4">
				<table width="950"  style="border:1px #bca2a2 dotted" cellpadding="2" cellspacing="0" align="center">
				<tr class="form">
						<td style="border:1px #bca2a2 dotted">
      					מקום ההגשה:
            			<select name="fund" class="green" >
      						<option value="0">בחר/י</option>
      						<option value="1">המממן</option>
      						<option value="2">הרשות למו"פ</option>
         		        </select>
      					
 						</td>
						<td style="border:1px #bca2a2 dotted">
      					<input type="checkbox"/>
						שלח/י העתק בדואר אלקטרוני
 						</td>
						<td  style="border:1px #bca2a2 dotted" nowrap>
						תאריך הגשה בקרן:
						<input type="text" class="green date medium100"/>
						</td>
 						<td style="border:1px #bca2a2 dotted">מספר עותקים:
            			<input type="text" class="green medium100" value="0"/>
 						</td>
				</tr>
				<tr>
					<td colspan="4" style="border:1px #bca2a2 dotted">
					<table width="950">
  					<tr>
					<td colspan="3">
      					הערות לגבי תאריך הגשה:<br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="3" align="center">
            			<textarea cols="100" rows="1" class="green" style="display:none;"></textarea>
						<div class="editor" style="text-align:left;direction:ltr;">
						&nbsp;
						</div>
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
  					<tr><td style="text-align:left">
  					Ms. Jane Turner Coordinator for Europe - European Union & others 02-6586676<br>
					Ms. Netanya Bar Cohva Assistant 02-6586668<br>
					Ms. Ronit Lavi Assistant 02-6586668 <br>
  					</td>
  					</tr>
  					<tr>
					<td colspan="3">
      					הערות נוספות לגבי אנשי הקשר:<br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="3" align="center">
            			<textarea cols="100" rows="1" class="green" style="display:none;"></textarea>
						<div class="editor" style="text-align:left;direction:ltr;">
						&nbsp;
						</div>
 					</td>
 					</tr>
 					</table>
 					</td>
				</tr>	
				<tr>
					<td colspan="4" style="border:1px #bca2a2 dotted">
					<table width="950" id="fileTable">
  					<tr>
					<td colspan="3">
      					טפסים ומידע הקשור לטפסים:<br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="3" align="center">
           			<textarea cols="100" rows="1" class="green" style="display:none;"></textarea>
					<div class="editor" style="text-align:left;direction:ltr;">
						<p>
						<a href="http://www2.esf.org/WD110AWP/WD110Awp.exe/CONNECT/EW_Application_Form">http://www2.esf.org/WD110AWP/WD110Awp.exe/CONNECT/EW_Application_Form</a>
						</p>            			
					</div>
  					</td>
 					</tr>
  					<tr>
					<td colspan="3">
					ניהול הקבצים:
					<table width="800" align="center">
					<tr>
					<td>
						<span id="addedText">
						<a style="text-decoration:underline" href="fileViewer?conferenceProposalId=70&attachFile=guestsAttach&contentType=application/vnd.oasis.opendocument.spreadsheet&attachmentId=1"
								target="_blank">טופס הרשמה מספר 1123</a>
						</span>&nbsp;&nbsp;&nbsp;
						<a href="" class="addFile">הוסף לעורך</a>						
					</td>
					</tr>
					<tr>
					<td>
						<span id="addedText">
						<a style="text-decoration:underline" href="fileViewer?conferenceProposalId=70&attachFile=guestsAttach&contentType=application/vnd.oasis.opendocument.spreadsheet&attachmentId=1"
								target="_blank">טופס הרשמה מספר 5600</a>
						</span>&nbsp;&nbsp;&nbsp;
						<a href="" class="addFile">הוסף לעורך</a>
					</td>
					</tr>
					<tr>
					<td>
						<div style="display: block; width: 60px; height: 27px; overflow: hidden;">
							<button class="green" style="width: 59px; height: 27px; position: relative; top: -1px; left: -1px;"><a href="javascript: void(0)">עיון...</a></button>
							<input type="file" style="font-size: 50px; width: 70px; opacity: 0; filter:alpha(opacity: 0);  position: relative; top: -40px; left: -5px" name="formAttach" id="formAttach"/>
						</div>
					</td>
					</tr>
					</table>
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
            			<textarea cols="100" rows="1" class="green" style="display:none;"></textarea>
 						<div class="editor" id="description" style="text-align:left;direction:ltr;">
             			<p> Each year, ESF supports approximately 50 Exploratory Workshops across all scientific domains. </p>  
  						<p> <b> The focus of the scheme is on projects aiming to open up new directions in research or to explore emerging research fields with potential impact on new developments in science. Proposals should demonstrate the potential  for initiating follow-up research activities and/or developing future collaborative actions. Interdisciplinary topics are encouraged. </b></p>    
							Proposals may be submitted in any or across several of the following broad scientific fields: <br> 
 								<ul>  <li> Biomedical Sciences </li> 
 								<li> Life, Earth and Environmental Sciences </li> 
 								<li> Physical and Engineering Sciences </li> 
 								<li> Social Sciences </li> 
 								<li> Humanities </li> 
 								<li> Science driven issues of Research Infrastructures in any of the above fields </li> 
 						</ul> 
						<p> The  <b> 2008 Call </b>  for Proposals is for workshops to be held between  <b> 1 February and 31 December 2009 </b>.</p>  
 						<br> 
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
            			<textarea cols="100" rows="1" class="green" style="display:none;"></textarea>
            			<div class="editor" style="text-align:left;direction:ltr;"> 
            			Applicants (convenors) must be scientists or scholars from European universities or research institutes in countries having agencies that are Member Organisations of the ESF. <b> Proposals may have co-applicants from other countries.</b>
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
            			<textarea cols="100" rows="1" class="green" style="display:none;"></textarea>
            			<div class="editor" style="text-align:left;direction:ltr;"> 
						The workshop must take place in an ESF Member Organisation country between <b> 1 February and 31 December 2009</b>.            			
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
            			<textarea cols="100" rows="1" class="green" style="display:none;"></textarea>
						<div class="editor" style="text-align:left;direction:ltr;">
						&nbsp;
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
            			<textarea cols="100" rows="1" class="green" style="display:none;"></textarea>
						<div class="editor" style="text-align:left;direction:ltr;">
						&nbsp;
						</div>
 					</td>
 					</tr>
 					<tr>
					<td colspan="3" style="text-align:left">
					<a href="" class="add">הוסף לעורך</a> &nbsp;
       				<span id="addedText">The budget proposal needs the approval of the budget officer Mr. Noam Glatzer 02-6586549 </span>	
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
            			<textarea cols="100" rows="1" class="green" style="display:none;"></textarea>
            			<div class="editor" style="text-align:left;direction:ltr;"> 
 				<br> 
 				<b> Submission format </b>  <br> 
				<ul>        <li> Applications (including CV) should be prepared as 1 document only in Word, rtf or pdf format. </li> 
       			<li> All margins should be at least 3 cm and headers and footers not less than 2 cm.
				 </li>     
       			<li> Font size must be no smaller than 10. </li> 
 				</ul> <br>   
 				<b> Submission procedure </b> <br>  
       			<ul>  <li> Proposals should be submitted  <b> on-line </b> . </li> 
       			<li> First submissions will be considered final ( <b> no revisions accepted </b> ). </li> 
 				</ul> <br>   
 					</div>         			
 					</td>
 					</tr>
 					</table>
 					</td>
				</tr>											
				<tr class="form">
						<td colspan="2" style="border:1px #bca2a2 dotted">
      					תקופת מימון מקסימלית:
            			<input type="text" class="green medium170" value="שנתיים"/>
 						</td>
						<td colspan="2" style="border:1px #bca2a2 dotted">
      					סכום המענק:
            			<input type="text" class="green medium170" value="עד 15,000 אירו"/>
 						</td>
				</tr>
				</table>
				</td>
				</tr>
				<tr><td>&nbsp;</td></tr>

	
 		
		<tr class="form">
			<td colspan="4" align="center">
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