<script type="text/javascript" src="/js/ckeditor/ckeditor.js"></script>
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
    
	CKEDITOR.disableAutoInline = true;
	if(CKEDITOR.instances['editor1']==null)
		CKEDITOR.inline('editor1');
	if(CKEDITOR.instances['editor2']==null)
		CKEDITOR.inline('editor2');

	CKEDITOR.instances['editor1'].on('blur', function(e) {
		var text=replaceNbsps(CKEDITOR.instances['editor1'].getData());
	    CKEDITOR.instances['editor1'].setData(text);
		$('.editorTextarea', $("#editor1").closest("td")).val(CKEDITOR.instances['editor1'].getData());
	}); 
	CKEDITOR.instances['editor2'].on('blur', function(e) {
		var text=replaceNbsps(CKEDITOR.instances['editor2'].getData());
	    CKEDITOR.instances['editor2'].setData(text);
		$('.editorTextarea', $("#editor2").closest("td")).val(CKEDITOR.instances['editor2'].getData());
	});
});

function replaceNbsps(text) {
	text=text.replace(/&nbsp;/g,' ');
	return text;
}
</script>