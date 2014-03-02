<%@ page pageEncoding="UTF-8"%>
<script>
var idleTime = 0;
function timerIncrement() {
    idleTime = idleTime + 1;
}
function clearTimer(){
   idleTime = 0;
}

$(document).ready(function() {

	var idleInterval = setInterval(timerIncrement, 60000); // every 1 minute 

	$(this).keypress(function (e) {
		if (idleTime >4)  // 5 minutes
	        window.location.reload();
	});
	
	<c:if test="${(GradingFinished && !admin) || locked}">
		locked();
	</c:if>

	/*$("button.buttonEdit").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"edit\"/>");
		var confId= this.id;
		$("#form").append("<input type=\"hidden\" name=\"conferenceProposalId\" value=\""+confId +"\"/>");
   		$("#form").submit();
    	return true;
    });*/
	
	$('.saveclass').blur(function(){
			clearTimer();
		    var options = {
	       	 	url:       'conferenceProposalsGrade.html?action=save&conferenceProposalId='+ this.id,        
	       	 	type:      'POST'
	     	};
		    $('#form').ajaxSubmit(options);
    });	
	$('input:checkbox.saveclass').click(function(){
			clearTimer();		   
			var options = {
	       	 	url:       'conferenceProposalsGrade.html?action=save&conferenceProposalId='+ this.id,        
	       	 	type:      'POST'
	     	};
		    $('#form').ajaxSubmit(options);
 	});

	$(".buttonUp").click(function(){
		<c:if test="${(GradingFinished && !admin) || locked}">
			locked();
			return false;
		</c:if>
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"moveup\"/>");
		var confId= this.id;
		$("#form").append("<input type=\"hidden\" name=\"conferenceProposalId\" value=\""+confId +"\"/>");
   		$("#form").submit();
    	return true;
    });
	
	$(".buttonDown").click(function(){
		<c:if test="${(GradingFinished && !admin) || locked}">
			locked();
			return false;
		</c:if>
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"movedown\"/>");
		var confId= this.id;
		$("#form").append("<input type=\"hidden\" name=\"conferenceProposalId\" value=\""+confId +"\"/>");
    	$("#form").submit();
    	return true;
    });
	

     $("#buttonStopGrading").click(function(){
 		<c:if test="${(GradingFinished && !admin) || locked}">
			locked();
			return false;
		</c:if>
		
 		var errorFlag=false;
		if($("#deadlineRemarks").val()=='' || $("#deadlineRemarks").val().length<6){
			errorFlag=true;
 		}
    	<c:forEach items="${conferenceProposals}" var="conferenceProposal">
 		if($("textarea#${conferenceProposal.id}").val()==''){
 			errorFlag=true;	
 		}
        </c:forEach>
		if(errorFlag){
	        var text = " לא מיצית את האפשרויות לרשום חוות דעת והערה כללית לועדה.";
	  		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
     		$("#genericDialog").dialog({ height: 200 });
    		$("#genericDialog").dialog({ width: 400 });
    		openHelp(this,text);		
 		} 
        else{
	 	 	$("#form").append("<input type=\"hidden\" name=\"action\" value=\"stopGrading\"/>");
  	   		$("#form").submit();
        }
        return false;
 	});
     
     $("#deadlineRemarks").blur(function(){
    		clearTimer();		   
    		var options = {
		       	 	url:       'conferenceProposalsGrade.html?action=saveDeadlineRemarks',        
		       	 	type:      'POST'
		     	};
			 $('#form').ajaxSubmit(options);
 	 });
     
     $("#genericDialog").dialog({
         autoOpen: false,
         show: 'fade',
         hide: 'fade',
         open: function() { $(".ui-dialog").css("box-shadow","#000 5px 5px 5px");}
   	 });
     $(".ui-dialog-titlebar").hide();
     
     var fieldname=""; 
     function openHelp(name,mytext){
  	    fieldname=name;
     	if(fieldname=="")
  	    	$("#genericDialog").dialog("option", "position", "center");
  	    else
     	 	$('#genericDialog').dialog({position: { my: 'top', at: 'top', of: $(fieldname)} });
  	    $("#genericDialog").html(mytext).dialog("open");
      } 
      
      $("#form,#genericDialog").click(function(e){
      	$("#genericDialog").dialog("close");
      });    
      
      $(window).scroll(function() {
      	if (fieldname=="") 
      		$("#genericDialog").dialog("option", "position", "center");
       }); 

      $("#dialogGrade").click(function(e) {
  		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
  		$("#genericDialog").dialog({ modal: false });
		$("#genericDialog").dialog({ height: 200 });
		$("#genericDialog").dialog({ width: 400 });
  		var texts='<p>';
  		texts+='ככל שהדירוג נמוך יותר - עדיפותה של הבקשה לתמיכה גבוהה יותר. ע"י לחיצה על החיצים תוכל/י לשנות את מיקום הבקשה (כלומר חשיבותה היחסית).';
  		texts+='</p>';	    
  	    openHelp("#dialogGrade",texts);
  	    return false;
  	   });
      $("#dialogDeadlineRemarks").click(function(e) {
    		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
    		$("#genericDialog").dialog({ modal: false });
    		$("#genericDialog").dialog({ height: 200 });
    		$("#genericDialog").dialog({ width: 400 });
    		var texts='<p>';
    		texts+='כל מה שלדעת הדיקן רלוונטי לשיקולי הוועדה, בנוגע לבקשות שבהמלצתו';
    		texts+='</p>';	    
    	    openHelp("#dialogDeadlineRemarks",texts);
    	    return false;
    	});
      $("#dialogStopGrading").click(function(e) {
  		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
  		$("#genericDialog").dialog({ modal: false });
		$("#genericDialog").dialog({ height: 200 });
		$("#genericDialog").dialog({ width: 400 });
  		var texts='<p>';
  		texts+='לחיצה תשלח לרכז/ת הועדה מייל המודיע שהדיקן סיים את הדירוג. היא תופעל רק אם מולאו כל חוות הדעת.';
  		texts+='</p>';	    
  	    openHelp("#dialogStopGrading",texts);
  	    return false;
  	   });
      $("#dialogGradeTitle").click(function(e) {
    		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
    		$("#genericDialog").dialog({ modal: false });
    		$("#genericDialog").dialog({ height: 200 });
    		$("#genericDialog").dialog({ width: 400 });
   		var texts='<p>';
    		texts+='באמצעות חלון זה, הדיקן מביע את התייחסותו לבקשות שמגישיהן הם מיחידתו. ע"י הקלקה על רשומה הבקשה תפתח לעיונך. ';
    		texts+='ניתן לשנות את פרטי התייחסותך עד לשיגורה לוועדה.';
    		texts+='</p>';	    
    	    openHelp("#dialogGradeTitle",texts);
    	    return false;
      });
      $("#dialogGradeHeader").click(function(e) {
  		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
  		$("#genericDialog").dialog({ modal: false });
		$("#genericDialog").dialog({ height: 200 });
		$("#genericDialog").dialog({ width: 400 });
 		var texts='<p>';
  		texts+='הדירוג משקף את החשיבות היחסית כאשר 1 = הכי חשובה למימון.';
  		texts+='</p>';	    
  	    openHelp("#dialogGradeHeader",texts);
  	    return false;
    });
      $("#dialogGradeOpinion").click(function(e) {
    		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
    		$("#genericDialog").dialog({ modal: false });
    		$("#genericDialog").dialog({ height: 200 });
    		$("#genericDialog").dialog({ width: 400 });
    		var texts='<p>';
    		texts+='הקלד את דעתך לבקשה המסוימת ובין השאר: חשיבות הכנס והאפשרות לממשו וכד.';
    		texts+='</p>';	    
    	    openHelp("#dialogGradeOpinion",texts);
    	    return false;
      });
      
      
  	<c:if test="${userMessage!=null}">
	var userMessage = "${userMessage}";
   	$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
	$("#genericDialog").dialog({ modal: false });
	openHelp("",userMessage);
    </c:if> 


 <%@ include file="/WEB-INF/jsp/include/searchPaginationScripts.jsp" %>

 function locked(){
		$("#genericDialog").dialog({ height: 200 });
		$("#genericDialog").dialog({ width: 400 });
		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		var text='הבקשות לדיון הקרוב כבר דורגו ונשלחו לוועדת הכנסים. לא ניתן לדרג אלא לאחר שרכז/ת הכנסים ישלח בקשה נוספת לדירוג.<br/>רכז/ת הכנסים יכול/ה לדרג בשמך.';
		if('${locked}')
			text="הדירוג או אחת הבקשות נעולים על ידי משתמש אחר. ";
		openHelp('',text);
		//make fields readonly 
		$("#deadlineRemarks").attr('readonly','readonly');
		$(".saveclass").attr('readonly','readonly');
	}

});



</script>



<td align="right" bgcolor="#787669" height="20"><c:set
		var="applicationName" value="הרשות למו\"פ" /> <c:set
		var="pageName" value="רשימת בקשות למימון לכנסים" /> <%@ include
		file="/WEB-INF/jsp/include/locationMenu.jsp"%>

</td>

</tr>

</table>
</td>
</tr>
<tr>
	<td>
		<table width="940" border="1" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#767468">
			<tr>
				<td valign="top" align="center"><br /> <form:form id="form"
						name="form" method="POST" commandName="command"
						action="conferenceProposalsGrade.html">
						<input type="hidden" id="listViewPage" name="listView.page"
							value="${command.listView.page}" />
						<input type="hidden" id="listViewOrderBy" name="listView.orderBy"
							value="${command.listView.orderBy}" />

								<table width="940" border="0" align="center" cellpadding="3"
									dir="rtl">
									<tr>
										<td colspan="2" align="center"><h1>רשימת הבקשות
												להתייחסות הדיקן	<img src="/image/questionmark.png" align="top" title="הסבר על השדה" width="25" height="25" id="dialogGradeTitle"/>
												</h1></td>
									</tr>
								</table>

						<c:choose>
							<c:when test="${fn:length(conferenceProposals) > 0}">
								<div id="genericDialog" title="כנסים" style="display: none"
									dir="rtl"></div>
								<div style="text-align:right" dir="rtl"> אנא אשר כי החוקר הינו חוקר מן המסלול הרגיל הרשאי להגיש בקשה זו לפי כללי האוניברסיטה.במידה והחוקר אינו רשאי להגיש, אנא ציין זאת בהערות הדירוג כדי שרכזת הוועדה תוכל לפנות אליו.</div>
								<br>
								<table width="940" border="0" cellspacing=0 cellpadding=2
									dir="rtl">
									<thead>
										<tr>
											<td width="35">מאשר/ת</td>
											<td width="150">שם החוקר/ת</td>
											<td width="350">נושא הכנס</td>
											<td width="100">דירוג <img src="/image/questionmark.png" align="top" title="הסבר על השדה" width="25" height="25" id="dialogGradeHeader"/></td>
											<td width="300">חוות דעת <img src="/image/questionmark.png" align="top" title="הסבר על השדה" width="25" height="25" id="dialogGradeOpinion"/></td>
    										<authz:authorize ifAnyGranted="ROLE_CONFERENCE_APPROVER,ROLE_CONFERENCE_ADMIN">
											<td width="30"><img src="/image/questionmark.png" align="top" title="הסבר על השדה" width="25" height="25" id="dialogGrade"/></td>
											</authz:authorize>
										</tr>
									</thead>

									<tbody>
										<c:forEach items="${conferenceProposals}"
											var="conferenceProposal" varStatus="varStatus">

											<tr class="<c:choose><c:when test="${varStatus.index%2==0}">darker</c:when><c:otherwise>brighter</c:otherwise></c:choose>">
												<td width="35">
												<input type="checkbox" class="saveclass" name="approverVerified${conferenceProposal.id}" id="${conferenceProposal.id}" <c:if test="${conferenceProposal.approverVerified}" > checked </c:if> />
												</td>
												<td width="150"><c:out
															value="${conferenceProposal.researcher.firstNameHebrew}" />&nbsp;<c:out
															value="${conferenceProposal.researcher.lastNameHebrew}" />
												</td>
												<td width="350" onClick="document.location='conferenceProposal.html?id=${conferenceProposal.id}';"><a
													href="/conferenceProposal.html?id=${conferenceProposal.id}"><c:out
															value="${conferenceProposal.subject}" />
												</a></td>
												<td width="100" align="right">&nbsp;&nbsp;<c:out
														value="${conferenceProposal.grade}" /></td>
												<td width="300"><textarea class="green saveclass evaluation"
														name="approverEvaluation${conferenceProposal.id}"
														id="${conferenceProposal.id}" rows="3" cols="40">${conferenceProposal.approverEvaluation}</textarea>
												</td>
   												<authz:authorize ifAnyGranted="ROLE_CONFERENCE_APPROVER,ROLE_CONFERENCE_ADMIN">
												<td width="30">
													<button class="grey buttonUp" id="${conferenceProposal.id}">
														<span class="ui-icon ui-icon-arrowthick-1-n"></span>
													</button>
													<button class="grey buttonDown"
														id="${conferenceProposal.id}">
														<span class="ui-icon ui-icon-arrowthick-1-s"></span>
													</button>
												</td>
												</authz:authorize>
												
											</tr>
										</c:forEach>
									</tbody>
								</table>
			<table width="900" border="0" align="center" cellpadding="3" dir="rtl">
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="5">הערת רכזת הועדה לדיקן:&nbsp;
						<c:out value="${adminDeadlineRemarks}" />
					</td>
				</tr>
				<tr>
					<td colspan="5">
						<table width="930" dir="rtl">
							<tr>
								<td>הערה כללית לועדה:<br/><img src="/image/questionmark.png" align="top" title="הסבר על השדה" width="25" height="25" id="dialogDeadlineRemarks"/></td>
								<td align="right">
								<textarea class="green"	name="deadlineRemarks" id="deadlineRemarks" rows="3" cols="80">${deadlineRemarks}</textarea>
								</td>
							</tr>
						</table></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="5" align="center">
						<button id="buttonStopGrading" class="grey"/> שלח לוועדה את התייחסותך</button>
						<img src="/image/questionmark.png" align="top" title="הסבר על השדה" width="25" height="25" id="dialogStopGrading"/>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<button  class="grey" title="חזרה לתפריט הראשי"  onclick="window.location='welcome.html';return false;">חזרה לתפריט </button>		
					</td>
				</tr>
			</c:when>
			<c:otherwise>
				<tr>
					<td>
						<table width="930" border="0" align="center" cellpadding="3"
							dir="rtl">
							<tr>
								<td colspan="2" align="center"><h2>לא נמצאו בקשות
										לדירוג</h2></td>
							</tr>
						</table></td>
				</tr>
			</c:otherwise>
			</c:choose>

		</table>								
		</td>
		</tr>

		</table>
	</td>
</tr>
</table>

<br>
</td>
</tr>
</table>
</form:form>
</td>
</tr>
</table>
</body>
</html>
