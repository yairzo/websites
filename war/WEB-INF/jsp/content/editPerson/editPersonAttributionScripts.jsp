<script language="Javascript">


function reloadPage(){
	if (form.listSelect.value!='0'){
		window.location="personAttribution.html?personId=<c:out value="${command.personId}"/>&listId="+form.listSelect.value
	}
}


function reloadOnImportDetailsChange(){
	window.location="personAttribution.html?importDetails=true<c:if test="${command.id > 0}">&id=<c:out value="${command.id}"/></c:if>&personId=<c:out value="${command.personId}"/>&listId=<c:out value="${command.listId}"/>"
	return false;
}




$(document).ready(function() {

	$('.cancel').click(function(){
		$('form#form').append('<input type=\"hidden\" name=\"action\" value=\"cancel\"/>');
		$('form#form').submit();
    });

    $('#connectDetails').click(function(){
    	$('form#form').submit();
    });
});
</script>