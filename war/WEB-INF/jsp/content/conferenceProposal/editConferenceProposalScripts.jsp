<%@ page  pageEncoding="UTF-8" %>
<script type="text/javascript" src="js/jquery.autosave.js"></script>
<script language="Javascript">



$(document).ready(function() {

	$("#startConfDate").datepicker({ showOn: 'button', buttonImageOnly: true, buttonImage: 'image/icon_calendar.gif',dateFormat: 'dd/mm/yy' });	
	$("#endConfDate").datepicker({ showOn: 'button', buttonImageOnly: true, buttonImage: 'image/icon_calendar.gif',dateFormat: 'dd/mm/yy' });	

	$('.autosaveclass').autoSave(function(){
		if ("${command.versionId}"==0){
			$.alerts.alert("...saving");
		   var options = {
	       	 	url:       'editConferenceProposal.html' ,        
	       	 	type:      'POST'
	     	};
		    $('#form').ajaxSubmit(options);
		}
		else 
			return false;
    }, {delay: 2000});	
	
	

	if($('#company').attr('checked') || $('#companyViewOnly').attr('checked'))
		$('.organizingCompanyPart').show();
	else
		$('.organizingCompanyPart').hide();
	
	$('#company').click(function(){	
		if($('#company').attr('checked'))
			$('.organizingCompanyPart').show();
		else
			$('.organizingCompanyPart').hide();
	});
	
	
	
	$("button.fromAdmitanceFeeSave").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"fromAdmitanceFeeSave\"/>");
    	$("#form").submit();
    	return true;
    });
	
	$("button.fromExternalSave").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"fromExternalSave\"/>");
    	$("#form").submit();
    	return true;
    });
	

	$("button.fromAssosiateSave").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"fromAssosiateSave\"/>");
    	$("#form").submit();
    	return true;
    });
	
	$("button.scientificCommitteeSave").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"scientificCommitteeSave\"/>");
    	$("#form").submit();
    	return true;
    });

	$("button.operationalCommitteeSave").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"operationalCommitteeSave\"/>");
    	$("#form").submit();
    	return true;
    });
	
	$("button.deleteFinancialSupport").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"deleteFinancialSupport\"/>");
		var financialSupportId= this.id;
		$("#form").append("<input type=\"hidden\" name=\"financialSupportId\" value=\""+financialSupportId +"\"/>");
    	$("#form").submit();
    	return true;
    });	
	
	$("button.deleteCommittee").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"deleteCommittee\"/>");
		var committeeId= this.id;
		$("#form").append("<input type=\"hidden\" name=\"committeeId\" value=\""+committeeId +"\"/>");
    	$("#form").submit();
    	return true;
    });
	
	$("button.submitForGrading").click(function(){
		var errors=false;
		if($("#deanSelect").val()=='0'){
			errors = true;
			$("#errordeanselect").html('<font color="red">יש לבחור מאשר לפני הגשה<font color="red"><br>');
		}
		else{
			$("#errordeanselect").html('');
		}
		if($("#subject").val()==''){
			errors = true;
			$("#errorsubject").html('<font color="red">יש למלא שדה נושא הכנס<font color="red"><br>');
		}
		else{
			$("#errorsubject").html('');
		}
		var numberRegex=/^[+-]?\d+(\.\d+)?([eE][+-]?d+)?$/;
		var countRegex=/^\d+$/;
		var emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
		var phoneRegex = /^[\d]{2,3}-[\d]{7}$/;		
		if(!numberRegex.test($("#totalCost").val())){
			errors = true;
			$("#errortotalcost").html('<font color="red">יש להכניס ערך מספרי לשדה סכום<font color="red"><br>');
		}
		else{
			$("#errortotalcost").html('');
		}
		if(!numberRegex.test($("#supportSum").val())){
			errors = true;
			$("#errorsupportsum").html('<font color="red">יש להכניס ערך מספרי לשדה סכום הסיוע המבוקש<font color="red"><br>');
		}
		else{
			$("#errorsupportsum").html('');
		}
		if(!countRegex.test($("#participants").val())){
			errors = true;
			$("#errorparticipants").html('<font color="red">יש להכניס ערך מספרי לשדה משתתפים<font color="red"><br>');
		}
		else{
			$("#errorparticipants").html('');
		}
		if($("#organizingCompanyEmail").val()!='' && !emailRegex.test($("#organizingCompanyEmail").val())){
			errors = true;
			$("#errororganizingCompanyEmail").html('<font color="red">יש להזין כתובת אימייל חברה מארגנת תקנית<font color="red"><br>');
		}
		else{
			$("#errororganizingCompanyEmail").html('');
		}
		if($("#contactPersonEmail").val()!='' && !emailRegex.test($("#contactPersonEmail").val())){
			errors = true;
			$("#errorcontactPersonEmail").html('<font color="red">יש להזין כתובת אימייל איש קשר תקנית<font color="red"><br>');
		}
		else{
			$("#errorcontactPersonEmail").html('');
		}
		if($("#organizingCompanyPhone").val()!='' && !phoneRegex.test($("#organizingCompanyPhone").val())){
			errors = true;
			$("#errororganizingCompanyPhone").html('<font color="red">יש להזין מספר טלפון חברה מארגנת תקני<font color="red"><br>');
		}
		else{
			$("#errororganizingCompanyPhone").html('');
		}
		if($("#organizingCompanyFax").val()!='' && !phoneRegex.test($("#organizingCompanyFax").val())){
			errors = true;
			$("#errororganizingCompanyFax").html('<font color="red">יש להזין פקס חברה מארגנת תקני<font color="red"><br>');
		}
		else{
			$("#errororganizingCompanyFax").html('');
		}
		if($("#contactPersonPhone").val()!='' && !phoneRegex.test($("#contactPersonPhone").val())){
			errors = true;
			$("#errorcontactPersonPhone").html('<font color="red">יש להזין מספר טלפון איש קשר תקני<font color="red"><br>');
		}
		else{
			$("#errorcontactPersonPhone").html('');
		}
		if (errors)
			return false;
		else{
			$("#form").append("<input type=\"hidden\" name=\"action\" value=\"submitForGrading\"/>");
    		$("#form").submit();
    		return true;
		}
    });
	
	$("button.unsubmitForGrading").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"unsubmitForGrading\"/>");
    	$("#form").submit();
    	return true;
    });
	
	$("button.submitFaculty").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"submitFaculty\"/>");
    	$("#form").submit();
    	return true;
    });	
	
	$("button.submit").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"showMessage\" value=\"saved\"/>");
		$("#form").submit();
    	return true;
    	
    });	
	
    $("#genericDialog").dialog({
           autoOpen: false,
           show: 'fade',
           hide: 'fade',
           modal: false,
           width: 200,
           buttons: {
               "Close": function() {
                   $(this).dialog("close");
               }
           }
     });
    $(".ui-dialog-titlebar").hide();
    
     $("#dialogInitiatingBody").click(function(e) {
    	 	openHelp("#dialogInitiatingBody","הגוף שיוזם את הכנס");
      });
     
     $("#dialogInitiatingBodyRole").click(function(e) {
    	 openHelp("#dialogInitiatingBodyRole","תפקיד בגוף היוזם את הכנס");
   }); 
    function openHelp(name,mytext){
        linkOffset = $(name).position();
        linkWidth = $(name).width();
        linkHeight = $(name).height();
        scrolltop = $(window).scrollTop();
        $("#genericDialog").dialog("option", "position", [(linkOffset.left - 200/2) + linkWidth/2, linkOffset.top + linkHeight - scrolltop]);
        $("#genericDialog").text(mytext).dialog("open");
    } 

	<c:if test="${userMessage!=null}">
	var userMessage = "${userMessage}";
	$.alerts.alert(userMessage);
    </c:if> 
});


</script>