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
	
});


</script>