<%@ page pageEncoding="UTF-8"%>
<script>
$(document).ready(function() {

	$("button.buttonEdit").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"edit\"/>");
		var confId= this.id;
		$("#form").append("<input type=\"hidden\" name=\"conferenceProposalId\" value=\""+confId +"\"/>");
   		$("#form").submit();
    	return true;
    });
	
	$('.saveclass').blur(function(){
		   var options = {
	       	 	url:       'conferenceProposalsGrade.html?action=save&conferenceProposalId='+ this.id,        
	       	 	type:      'POST'
	     	};
		    $('#form').ajaxSubmit(options);
    });	
	

	$(".buttonUp").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"moveup\"/>");
		var confId= this.id;
		$("#form").append("<input type=\"hidden\" name=\"conferenceProposalId\" value=\""+confId +"\"/>");
   		$("#form").submit();
    	return true;
    });
	
	$(".buttonDown").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"movedown\"/>");
		var confId= this.id;
		$("#form").append("<input type=\"hidden\" name=\"conferenceProposalId\" value=\""+confId +"\"/>");
    	$("#form").submit();
    	return true;
    });
	

     $("#buttonStopGrading").click(function(){
   		var errors="";
 		if($("#deadlineRemarks").val()==''){
 			errors = "לא נרשמה הערה כללית לועדה. ";
 		}
 		var evaluation=false;
    	<c:forEach items="${conferenceProposals}" var="conferenceProposal">
 		if($("textarea#${conferenceProposal.id}").val()==''){
 	         evaluation=true;	
 		}
        </c:forEach>
		if(evaluation)
	        errors += " לא נרשמה חוות דעת לכל ההצעות. ";
        if(errors!=''){
        	errors += "האם ברצונך לסיים את תהליך ההגשה? ";
    	   	$("#genericDialog").dialog({ modal: true });
        	$("#genericDialog").dialog('option', 'buttons', {
                "לא" : function() {
                    $(this).dialog("close");
                },
                "כן" : function() {
                    $(this).dialog("close");
      	 	 		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"stopGrading\"/>");
          	   		$("#form").submit();
          	   		return true;
               }
            });
    		openHelp(this,errors);		
 		} 
        else{
            $(this).dialog("close");
	 	 	$("#form").append("<input type=\"hidden\" name=\"action\" value=\"stopGrading\"/>");
  	   		$("#form").submit();
  	   		return true;
        }
        return false;
 	});
     
     $("#deadlineRemarks").blur(function(){
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
         width: 300,
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
  	    $("#genericDialog").text(mytext).dialog("open");
      } 
      
      $("#form,#genericDialog").click(function(e){
      	$("#genericDialog").dialog("close");
      });    
      
      $(window).scroll(function() {
      	if (fieldname=="") 
      		$("#genericDialog").dialog("option", "position", "center");
       }); 
      
  	<c:if test="${userMessage!=null}">
	var userMessage = "${userMessage}";
   	$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
	$("#genericDialog").dialog({ modal: false });
	openHelp("",userMessage);
    </c:if> 


 <%@ include file="/WEB-INF/jsp/include/searchPaginationScripts.jsp" %>



});



</script>



<td align="right" bgcolor="#787669" height="20"><c:set
		var="applicationName" value="מערכת אינטרנט הרשות למו\"פ" /> <c:set
		var="pageName" value="רשימת ההצעות לכנסים" /> <%@ include
		file="/WEB-INF/jsp/include/locationMenu.jsp"%>

</td>

</tr>

</table>
</td>
</tr>
<tr>
	<td>
		<table width="900" border="1" align="center" cellpadding="0"
			cellspacing="0" bordercolor="#767468">
			<tr>
				<td valign="top" align="center"><br /> <form:form id="form"
						name="form" method="POST" commandName="command"
						action="conferenceProposalsGrade.html">
						<input type="hidden" id="listViewPage" name="listView.page"
							value="${command.listView.page}" />
						<input type="hidden" id="listViewOrderBy" name="listView.orderBy"
							value="${command.listView.orderBy}" />

								<table width="900" border="0" align="center" cellpadding="3"
									dir="rtl">
									<tr>
										<td colspan="2" align="center"><h1>רשימת ההצעות
												לדירוג</h1></td>
									</tr>
								</table>

						<c:choose>
							<c:when test="${fn:length(conferenceProposals) > 0}">
								<div id="genericDialog" title="כנסים" style="display: none"
									dir="rtl"></div>

								<table width="900" border="0" cellspacing=0 cellpadding=2
									dir="rtl">
									<thead>
										<tr>
											<td width="150">שם החוקר/ת</td>
											<td width="350">נושא הכנס</td>
											<td width="50">דירוג</td>
											<td width="300">חוות דעת</td>
											<td width="30"></td>
										</tr>
									</thead>

									<tbody>
										<c:forEach items="${conferenceProposals}"
											var="conferenceProposal" varStatus="varStatus">

											<tr
												class="<c:choose><c:when test="${varStatus.index%2==0}">darker</c:when><c:otherwise>brighter</c:otherwise></c:choose>">
												<td width="150"><c:out
															value="${conferenceProposal.researcher.firstNameHebrew}" />&nbsp;<c:out
															value="${conferenceProposal.researcher.lastNameHebrew}" />
												</td>
												<td width="350"><a
													href="editConferenceProposal.html?id=${conferenceProposal.id}"><c:out
															value="${conferenceProposal.subject}" />
												</a></td>
												<td width="50" align="center"><c:out
														value="${conferenceProposal.grade}" /></td>
												<td width="300"><textarea class="green saveclass evaluation"
														name="approverEvaluation${conferenceProposal.id}"
														id="${conferenceProposal.id}" rows="1" cols="40">${conferenceProposal.approverEvaluation}</textarea>
												</td>
												<td width="30">
													<button class="grey buttonUp" id="${conferenceProposal.id}">
														<span class="ui-icon ui-icon-arrowthick-1-n"></span>
													</button>
													<button class="grey buttonDown"
														id="${conferenceProposal.id}">
														<span class="ui-icon ui-icon-arrowthick-1-s"></span>
													</button></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
			<table width="900" border="0" align="center" cellpadding="3" dir="rtl">
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td colspan="5">
						<table width="900" dir="rtl">
							<tr>
								<td>הערה כללית לועדה:</td>
								<td align="center">
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
						<button id="buttonStopGrading" class="grey" />סיום הדירוג
						</button></td>
				</tr>
			</c:when>
			<c:otherwise>
				<tr>
					<td>
						<table width="900" border="0" align="center" cellpadding="3"
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
