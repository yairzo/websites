<script language="Javascript">


$(document).ready(function() {

	 $("a.delete").click(function(){
		 	$("input[@type=checkbox][@checked]").each(
				function() {
					var id = $(this).val();
					$("#form").append("<input type=\"hidden\" name=\"action\" value=\"delete\"/>");
					$("#form").append("<input type=\"hidden\" name=\"imageId\" value=\"" + id + "\"");
					$("#form").ajaxSubmit( function(){
						$("span#img"+id).remove();
					});
				 }
			);
		});
	 
		$('.cancel').click(function(){
			$('form#form').append('<input type=\"hidden\" name=\"action\" value=\"cancel\"/>');
			submission = true;
			$('form#form').submit();
	    });
	 
	 
	 
});
	
</script>