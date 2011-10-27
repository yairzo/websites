<script language="Javascript">


$(document).ready(function() {

	 $("a.delete").click(function(){
		 	$("input[@type=checkbox][@checked]").each(
				function() {
					var id = $(this).val();
					$("form#editImage").append("<input type=\"hidden\" name=\"action\" value=\"delete\"/>");
					$("form#editImage").append("<input type=\"hidden\" name=\"imageId\" value=\""+id+"\"/>");
					$("form#editImage").ajaxSubmit( function(){
						$("span#img"+id).remove();
					});
				 }
			);
		});
	 $("a.approve").click(function(){
		 	$("input[@type=checkbox][@checked]").each(
				function() {
					var id = $(this).val();
					$("form#editImage").append("<input type=\"hidden\" name=\"action\" value=\"approve\"/>");
					$("form#editImage").append("<input type=\"hidden\" name=\"imageId\" value=\""+id+"\"/>");
					$('form#editImage').submit();
				}
			);
		});

	$('.cancel').click(function(){
		$('form#editImage').append('<input type=\"hidden\" name=\"action\" value=\"cancel\"/>');
		$('form#editImage').submit();
	});

	<c:if test="${userMessage!=null}">
		var userMessage = "${userMessage}";
		$.alerts.alert(userMessage);
	</c:if>
});

</script>