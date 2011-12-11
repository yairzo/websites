<script type="text/javascript" src="js/jquery.autosave.js"></script>
<script language="Javascript">

<c:if test="${userMessage!=null}">
var userMessage = "${userMessage}";
$.alerts.alert(userMessage);
</c:if>


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
	
	

	if($('#company').attr('checked'))
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
	
	$("button.submitForGrading").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"submitForGrading\"/>");
    	$("#form").submit();
    	return true;
    });
});


</script>