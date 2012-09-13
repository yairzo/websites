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
					<td colspan="4" align="right"><h3> פרטי קול קורא מספר: 2471
					</h3></td>
				</tr>
                <tr class="form">
                <td colspan="4">
                <table width="950" style="border:1px #bca2a2 dotted" cellpadding="2" cellspacing="0" align="center">
                <tr class="form">
					<td colspan="3" style="border:1px #bca2a2 dotted">
						 כותרת:
					 מלגות אהרון ואפרים קציר בתחומי מדעי הטבע וההנדסה 
					</td>
				</tr>
				<tr class="form">
					<td width="300" style="border:1px #bca2a2 dotted">
						 תאריך פרסום:
						<input type="text" class="green date medium100" value="16/11/11"/>
					</td>
					<td width="300" style="border:1px #bca2a2 dotted" nowrap>
						 תאריך הגשה:
						<input type="text" class="green date medium100" value="01/01/12"/>&nbsp;
						<input type="checkbox"/>
						 כל השנה
 					</td>
					<td  style="border:1px #bca2a2 dotted" nowrap>
						בעל המסמך:
						יאיר זוהר
					</td>
				</tr>
				<tr class="form">
					<td width="300" style="border:1px #bca2a2 dotted">
						 תאריך הגשה נוסף 1:
						<input type="text" class="green date medium100" value=""/>
					</td>
					<td width="300" style="border:1px #bca2a2 dotted">
						תאריך הגשה נוסף 2:
						<input type="text" class="green date medium100" value=""/>
 					</td>
					<td  style="border:1px #bca2a2 dotted">
						תאריך הגשה נוסף 3:
						<input type="text" class="green date medium100" value=""/>
					</td>
				</tr>
                <tr class="form">
					<td style="border:1px #bca2a2 dotted">
						 קרן:
       					<select name="fund" class="green" >
      						<option value="0">AAF</option>
      						<option value="1">ABISCH-FRENKEL</option>
      						<option value="2">ADDF</option>
      						<option value="3">AES</option>
      						<option value="4">ALS</option>
        		        </select>
					</td>
					<td style="border:1px #bca2a2 dotted">
						סוג קול קורא:
       					<select name="" class="green" >
      						<option value="0">scholarship</option>
      						<option value="1">research grant</option>
      						<option value="2">research exchange</option>
      						<option value="3">conference</option>
        		        </select>
					</td>
					<td  style="border:1px #bca2a2 dotted" nowrap>
						דסק:
       					<select name="desk" class="green" >
      						<option value="0">ISR</option>
      						<option value="1">EU</option>
      						<option value="2">USA</option>
      						<option value="3">GER</option>
      						<option value="4">INT</option>
        		        </select>
					</td>
				</tr>
               <tr class="form">
					<td style="border:1px #bca2a2 dotted">
      					<input type="checkbox"/>
						  להציג באתר
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
				<tr class="form">
					<td  style="border:1px #bca2a2 dotted" nowrap>
       					<input type="checkbox"/>
						להציג רק לבעלי סיסמה
					</td>
					<td colspan="2" style="border:1px #bca2a2 dotted">
       					<input type="checkbox"/>&nbsp;
						  להציג בהודעות הנגללות,
						עד לתאריך:
       					<input type="text" class="date green medium100"/>
					</td>
				</tr>
				<tr class="form">
					<td colspan="3" style="border:1px #bca2a2 dotted">
						  כתובת אתר:
       					<input type="text" class="green" style="width:700px"/>
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
      					ההגשה ל:
            			<select name="fund" class="green" >
      						<option value="0">בחר/י</option>
      						<option value="1">קרן</option>
      						<option value="2">אוניברסיטה</option>
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
				</tr>
				<tr class="form">
						<td width="340" style="border:1px #bca2a2 dotted">
      					תקופת מימון מקסימלית:
            			<input type="text" class="green medium170" value="1-3 ימים"/>
 						</td>
						<td width="300" style="border:1px #bca2a2 dotted">
      					סכום המענק:
            			<input type="text" class="green medium170" value="עד 15,000 אירו"/>
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
            			<textarea cols="85" rows="1" class="green editor"></textarea>
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
      					דסק ואנשי קשר:<br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="3" align="center">
            			<textarea cols="85" rows="1" class="green editor"></textarea>
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
      					טפסים:<br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="3" align="center">
            			<textarea cols="85" rows="1" class="green editor">
						<p>
						<a href="http://www2.esf.org/WD110AWP/WD110Awp.exe/CONNECT/EW_Application_Form">http://www2.esf.org/WD110AWP/WD110Awp.exe/CONNECT/EW_Application_Form</a>
						</p>            			
						</textarea>
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
            			<textarea cols="85" rows="1" class="green editor">
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
            			</textarea>
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
            			<textarea cols="85" rows="1" class="green editor">
            			 Applicants (convenors) must be scientists or scholars from European universities or research institutes in countries having agencies that are Member Organisations of the ESF. <b> Proposals may have co-applicants from other countries.</b>
            			</textarea>
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
            			<textarea cols="85" rows="1" class="green editor">
						The workshop must take place in an ESF Member Organisation country between <b> 1 February and 31 December 2009</b>.            			
						</textarea>
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
            			<textarea cols="85" rows="1" class="green editor"></textarea>
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
      					פירוט תקציב:<br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="3" align="center">
            			<textarea cols="85" rows="1" class="green editor"></textarea>
 					</td>
 					</tr>
 					<tr>
					<td colspan="3">
      					<input type="checkbox"/>
						להציג שורת מנהל תקציב
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
            			<textarea cols="85" rows="1" class="green editor">
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
 </ul> <br>            			</textarea>
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
				<button title="שמירה" class="grey submit" > שמירה </button>&nbsp;&nbsp;
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