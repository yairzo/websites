<%@ page  pageEncoding="UTF-8" %>
<script language="Javascript">


$(document).ready(function() {
    
	 $("a.delete").click(function(){
			var ids ="";
		 	$("#chkboxName:checked").each(
				function() {
					ids +=  $(this).val()+",";
				}
			);
		 	if(ids!=""){
					$("form#editImage").append("<input type=\"hidden\" name=\"action\" value=\"delete\"/>");
					$("form#editImage").append("<input type=\"hidden\" name=\"imageIds\" value=\""+ids+"\"/>");
					$('form#editImage').submit();
					return false;
			}
		});
	 
	 $("a.approve").click(function(){
			var ids ="";
		 	$("#chkboxName:checked").each(
				function() {
					ids +=  $(this).val()+",";
				}
			);
		 	if(ids!=""){
				$("form#editImage").append("<input type=\"hidden\" name=\"action\" value=\"approve\"/>");
				$("form#editImage").append("<input type=\"hidden\" name=\"imageIds\" value=\""+ids+"\"/>");
				$('form#editImage').submit();
				return false;
		 	}
		});
	 $("a.disapprove").click(function(){
			var ids ="";
		 	$("#chkboxName:checked").each(
				function() {
					ids +=  $(this).val()+",";
				}
			);
		 	if(ids!=""){
				$("form#editImage").append("<input type=\"hidden\" name=\"action\" value=\"disapprove\"/>");
				$("form#editImage").append("<input type=\"hidden\" name=\"imageIds\" value=\""+ids+"\"/>");
				$('form#editImage').submit();
				return false;
		 	}
		});
	$('.cancel').click(function(){
		$('form#editImage').append('<input type=\"hidden\" name=\"action\" value=\"cancel\"/>');
		$('form#editImage').submit();
	});
	$('#cancelUpload').click(function(){
		$("form#editImage").append("<input type=\"hidden\" name=\"action\" value=\"delete\"/>");
		$("form#editImage").append("<input type=\"hidden\" name=\"imageIds\" value=\""+${command.id}+",\"/>");
		$('form#editImage').submit();
	});
	
	
	<c:if test="${userMessage!=null}">
		var userMessage = "${userMessage}";
		$.alerts.alert(userMessage);
	</c:if>
	
	var imgNameRegexp = /^([A-Z]([a-zA-Z0-9-])+_)+[A-Z][a-zA-Z0-9-]+\.[a-z]{2,4}$/;
	$('.title').change(function(event){
		var imgname = $('.title').val();			
		if (imgname.search(imgNameRegexp) == -1){
			alert('שם התמונה לא בפורמט: Word1_Word2_Word3.jpg');
		}
	});	

});


</script>