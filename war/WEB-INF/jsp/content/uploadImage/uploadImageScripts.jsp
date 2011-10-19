<script language="Javascript">


$(document).ready(function() {

	 $("a.delete").click(function(){
		 	$("input[@type=checkbox][@checked]").each(
				function() {
					var id = $(this).val();
					$("#form2").append("<input type=\"hidden\" name=\"action\" value=\"delete\"/>");
					$("#form2").append("<input type=\"hidden\" name=\"imageId\" value=\"" + id + "\"");
					$("#form2").ajaxSubmit( function(){
						$("span#img"+id).remove();
					});
				 }
			);
		});
	 $("a.approve").click(function(){
		 	$("input[@type=checkbox][@checked]").each(
				function() {
					var id = $(this).val();
					$("#form2").append("<input type=\"hidden\" name=\"action\" value=\"approve\"/>");
					$("#form2").append("<input type=\"hidden\" name=\"imageId\" value=\"" + id + "\"");
					submission = true;
					$('form#form2').submit();
				}
			);
		});
	 
		$('.cancel').click(function(){
			$('form#form2').append('<input type=\"hidden\" name=\"action\" value=\"cancel\"/>');
			submission = true;
			$('form#form2').submit();
	    });
	 
		<c:if test="${userMessage!=null}">
		var userMessage = "${userMessage}";
		$.alerts.alert(userMessage);
	   </c:if>
	 
	 
});
	
</script>