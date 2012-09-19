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
                  	<h1>עריכת דף תוכן <br/>
                   	</h1>
                  	</td>
                 	 </tr>
                 	 </table>
                 	</td>
                </tr>
 
 
                <tr class="form">
					<td colspan="4" align="right"><h3> פרטי דף טקסט מספר: 100
					</h3></td>
				</tr>
                <tr class="form">
                <td colspan="4">
                <table width="950" style="border:1px #bca2a2 dotted" cellpadding="2" cellspacing="0" align="center">
                <tr class="form">
					<td colspan="4" style="border:1px #bca2a2 dotted">
						 כותרת:
					 R&D Committees  
					</td>
				</tr>
				<tr class="form">
					<td width="250" style="border:1px #bca2a2 dotted" nowrap>
						בעל המסמך:
						יאיר זוהר
 					</td>
					<td width="220" style="border:1px #bca2a2 dotted" nowrap>
						דסק:
       					<select name="desk" class="green" >
      						<option value="0">ISR</option>
      						<option value="1">EU</option>
      						<option value="2">USA</option>
      						<option value="3">GER</option>
      						<option value="4">INT</option>
        		        </select>
					</td>
					<td width="220" style="border:1px #bca2a2 dotted" nowrap>
       					<input type="checkbox"/>
						להציג רק לבעלי סיסמה
					</td>
					<td width="220" style="border:1px #bca2a2 dotted" nowrap>
       					<input type="checkbox"/>
						להציג באתר
					</td>
				</tr>
				<tr>
					<td colspan="4" style="border:1px #bca2a2 dotted">
					<table width="950">
  					<tr>
					<td colspan="4">
      					html:<br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="4" align="center">
            			<textarea cols="85" rows="4" class="green editoropen">
<p>
	The academic responsibility for research and development is distributed among various university committees as follows:</p>
<ol>
	<li>
		<a href="http://ard.huji.ac.il/huard/pubPageWraper.jsp?ardNum=224">The Steering Committee of the Authority for Research and Development </a></li>
	<li>
		<a href="http://ard.huji.ac.il/huard/pubPageWraper.jsp?ardNum=318&amp;category=המחu  באוניברסיטה &amp;category=המחu  באוניברסיטה&amp;parentPageTitle=ועbים/ועbות לענייני מו">The Committee for Infrastructure</a></li>
	<li>
		<a href="http://ard.huji.ac.il/huard/pubPageWraper.jsp?ardNum=194&amp;category=המחu  באוניברסיטה &amp;category=המחu  באוניברסיטה&amp;parentPageTitle=ועbים/ועbות לענייני מו">Faculty research committees</a></li>
	<li>
		<a href="http://ard.huji.ac.il/huard/pubPageWraper.jsp?ardNum=179">Conference committee</a></li>
	<li>
		<a href="http://ard.huji.ac.il/huard/pubPageWraper.jsp?ardNum=275">The University Committee for Human Subjects Research</a></li>
	<li>
		<a href="http://ard.huji.ac.il/huard/pubPageWraper.jsp?ardNum=185&amp;category=המחu  באוניברסיטה&amp;category=המחu  באוניברסיטה&amp;parentPageTitle=ועbים/ועbות לענייני מו">The Ethics Committee for Animal Care and Experimentation and departmental subcommittees</a></li>
	<li>
		<a href="http://ard.huji.ac.il/huard/pubPageWraper.jsp?ardNum=383">The steering committee of the Authority for Animal Facilities</a>.</li>
	<li>
		<a href="http://ard.huji.ac.il/huard/pubPageWraper.jsp?ardNum=189&amp;category=המחu  באוניברסיטה&amp;category=המחu  באוניברסיטה&amp;parentPageTitle=ועbים/ועbות לענייני מו">The steering committee of the Authority for Information Technology</a></li>
	<li>
		<a href="http://ard.huji.ac.il/huard/pubPageWraper.jsp?ardNum=192&amp;category=המחu  באוניברסיטה">The Safety Committee</a></li>
	<li>
		<a href="http://ard.huji.ac.il/huard/pubPageWraper.jsp?ardNum=175">Deans</a></li>
	<li>
		<a href="http://ard.huji.ac.il/huard/pubPageViewer.jsp?ardNum=397">The University Committee for Treatment in Conflict of Interests</a></li>
</ol>
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
					<td colspan="4">
      					תאור (לשימוש פנימי):<br>
 					</td>
					</tr>				
 					<tr>
					<td colspan="4" align="center">
            			<textarea cols="85" rows="1" class="green"></textarea>
 					</td>
 					</tr>
 					</table>
 					</td>
				</tr>	
				<tr class="form">
					<td style="border:1px #bca2a2 dotted" nowrap>
       					<input type="checkbox"/>
						להציג תמונה מעל הדף
					</td>
					<td colspan="3" style="border:1px #bca2a2 dotted" nowrap>
						<table>
						<tr>
						<td style="width:200" >כתובת האינטרנט של התמונה:</td>
						<td><input type="text" class="green" style="width:400"/></td>
						</tr>
						</table>
       					
					</td>
				</tr>
				<tr class="form">
					<td style="border:1px #bca2a2 dotted" nowrap>
       					<input type="checkbox"/>
						להציג קובץ בדף
					</td>
					<td colspan="3" style="border:1px #bca2a2 dotted" nowrap>
						<table>
						<tr>
						<td style="width:200">כתובת האינטרנט של הקובץ:</td>
						<td><input type="text" class="green" style="width:400"/></td>
						</tr>
						</table>
 					</td>
				</tr>
				<tr>
					<td colspan="4" style="border:1px #bca2a2 dotted">
					<table width="950">
					<tr>
					<td style="width:300">
					קובץ:
						<a style="text-decoration:underline" href="fileViewer?conferenceProposalId=70&attachFile=guestsAttach&contentType=application/vnd.oasis.opendocument.spreadsheet&attachmentId=1"
								target="_blank">טופס הרשמה מספר 1123</a>						
					</td>
					<td colspan="3" align="right">
						<span style="display: block; width: 60px; height: 27px; overflow: hidden;">
							<button class="green" style="width: 59px; height: 27px; position: relative; top: -1px; left: -1px;"><a href="javascript: void(0)">עיון</a></button>
							<input type="file" style="font-size: 50px; width: 70px; opacity: 0; filter:alpha(opacity: 0);  position: relative; top: -40px; left: -5px" name="formAttach" id="formAttach"/>
						</span>
					</td>
					</tr>
					</table>
 					</td>
 				</tr>
				
				<tr class="form">
					<td style="border:1px #bca2a2 dotted" nowrap>
       					<input type="checkbox"/>
						לעטוף דף חיצוני
					</td>
					<td colspan="3" style="border:1px #bca2a2 dotted" nowrap>
 						<table>
						<tr>
						<td style="width:200">כתובת האינטרנט של הדף:</td>
						<td><input type="text" class="green" style="width:400"/></td>
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
				<button title="שמירה" class="grey submit" > שמירה והעברה לאתר </button>&nbsp;&nbsp;
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