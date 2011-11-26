<script type="text/javascript" src="js/jquery.autosave.js"></script>
<script language="Javascript">

<c:if test="${userMessage!=null}">
var userMessage = "${userMessage}";
$.alerts.alert(userMessage);
</c:if>


$(document).ready(function() {
	
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
		$('#organizingCompanyPart').show();
	else
		$('#organizingCompanyPart').hide();
	
	$('#company').click(function(){	
		if($('#company').attr('checked'))
			$('#organizingCompanyPart').show();
		else
			$('#organizingCompanyPart').hide();
	});
	
	
	
	$("button.fromAdmitanceFeeSave").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"fromAdmitanceFeeSave\"/>");
    	$("#form").submit();
    	return true;
    });
});


</script>