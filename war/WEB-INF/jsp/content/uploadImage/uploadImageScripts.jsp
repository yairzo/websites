<%@ page  pageEncoding="UTF-8" %>
<script language="Javascript">


$(document).ready(function() {
    $("#editCaptionsDialog").dialog({
        autoOpen: false,
        show: 'fade',
        hide: 'fade',
        modal: true,
        open: function() { $(".ui-dialog").css("box-shadow","#000 5px 5px 5px");}
  });
    
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

	$('.cancel').click(function(){
		$('form#editImage').append('<input type=\"hidden\" name=\"action\" value=\"cancel\"/>');
		$('form#editImage').submit();
	});

	<c:if test="${userMessage!=null}">
		var userMessage = "${userMessage}";
		$.alerts.alert(userMessage);
	</c:if>
});

function editCaptions(id,name,captionHebrew,captionEnglish){
   	$("#editCaptionsDialog").dialog('option', 'buttons', {
          "ביטול" : function() {
          	$(this).dialog("close");
           },
          "שמירה" : function() {
     			$("form#editImage").append("<input type=\"hidden\" name=\"id\" value=\""+id+"\"/>");
    			$("form#editImage").append("<input type=\"hidden\" name=\"ename\" value=\""+$("#ename").val()+"\"/>");
    			$("form#editImage").append("<input type=\"hidden\" name=\"ecaptionHebrew\" value=\""+$("#ecaptionHebrew").val()+"\"/>");
    			$("form#editImage").append("<input type=\"hidden\" name=\"ecaptionEnglish\" value=\""+$("#ecaptionEnglish").val()+"\"/>");
      			$("form#editImage").append("<input type=\"hidden\" name=\"action\" value=\"edit\"/>");
     			$('form#editImage').submit();
          		$(this).dialog("close");
           }
  	});
   	var text="<table><tr class='form'><td>שם:<br><input type='text' class='green' id='ename' value='"+name+"'/></td></tr>";
   	text+="<tr class='form'><td>כותרת תמונה בעברית:<input type='text' class='green' id='ecaptionHebrew' value='"+captionHebrew+"'/></td></tr>";
   	text+="<tr class='form'><td>כותרת תמונה בעברית:<input type='text' class='green' id='ecaptionEnglish' value='"+captionEnglish+"'/></td></tr>";
   	text+="</table>";
   	$("#editCaptionsDialog").dialog("option", "position", "center");
   	$("#editCaptionsDialog").html(text).dialog("open");
}
</script>