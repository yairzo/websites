<%@ page pageEncoding="UTF-8"%>
<script>
	function resetAutocomplete(persons){		
		$("#searchPhrase").autocomplete( 
				{source: persons,
				 minLength: 2,
				 highlight: true,
				 select: function(event, ui) {
					 
					$("input#listViewPage").remove();
					$("input#orderBy").remove();
					$("#searchPhrase").val(ui.item.value);
					event.preventDefault();					
					$("#form").append("<input type=\"hidden\" name=\"action\" value=\"search\"/>");
					$("#form").submit();				 
				 }
			    }
		);
	}
	
	function cleanSearch(){
		$("input#searchPhrase").val('');
		$("input#listViewPage").remove();
		$("input#orderBy").remove();
	}

$(document).ready(function() {


	$("#buttonEdit").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"edit\"/>");
    	$("#form").submit();
    	return true;
    });

    $("#buttonDelete").click(function(){
    	$.alerts.confirm('<fmt:message key="iw_IL.eqfSystem.editProposal.deleteProposal.confirm"/>', "מחיקת הצעה",
        function(confirm){
         	 	if (confirm==1){
         	      	$("#form").append("<input type=\"hidden\" name=\"action\" value=\"delete\"/>");
             	   	$("#form").submit();
             	   	return true;
         	 	}
         	 	else{
     				return false;
         	 	}
        });
    	return false;
    });

    $("#buttonSearch").click(function(){
    	$("input#listViewPage").remove();
		$("input#orderBy").remove();
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"search\"/>");
		$("#form").submit();
    	return true;
    });

    $("#buttonCleanSearch").click(function(){
    	$("input#searchPhrase").val('');
       	$("#searchByApprover").val('0');
       	$("#searchBySubmitted").val('0');
       	$("#searchByDeadline").val('0');
   		$("input#listViewPage").remove();
		$("input#orderBy").remove();
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"search\"/>");
		$("#form").submit();
    	return true;
    });
    $("#searchPhrase").click(function(){
    	cleanSearch();
    });
    
    $.get('selectBoxFiller',{type:'person'},function(data){
		var persons = data.split(",,");
		resetAutocomplete(persons)
		$("#searchPhrase").focus();
	});
    
    $("#searchByApprover").change(function(){
    	$("#searchPhrase").empty();
    	$("#form").append("<input type=\"hidden\" name=\"action\" value=\"search\"/>");
		$("#form").submit();
    });
    
    $('.searchBySubmitted').click(function(){
    	$("#form").append("<input type=\"hidden\" name=\"action\" value=\"search\"/>");
		$("#form").submit();
    });
    $('.searchByDeadline').click(function(){
    	$("#form").append("<input type=\"hidden\" name=\"action\" value=\"search\"/>");
		$("#form").submit();
    });
    $('#buttonStartGrading').click(function(event){
    	
    	$( "#dialog:ui-dialog" ).dialog( "destroy" );
    	$( "#dialog-confirm" ).dialog({
			resizable: false,
			height:250,
			width: 400,
			modal: true,
			title: "אישור שליחת בקשה לדירוג",
			
			buttons: {
				"שלח": function() {
					$("#form").append("<input type=\"hidden\" name=\"action\" value=\"startGrading\"/>");
			    	$("#form").submit();
				},
				"בטל": function() {
					$( this ).dialog( "close" );
				}
			}
		});
    	event.preventDefault();
    	return false;
    });

 <%@ include file="/WEB-INF/jsp/include/searchPaginationScripts.jsp" %>
});
</script>