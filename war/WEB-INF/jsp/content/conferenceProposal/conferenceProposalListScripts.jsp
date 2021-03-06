<%@ page pageEncoding="UTF-8"%>
<style>
	.ui-autocomplete {
		direction: rtl;
	}
	
	.ui-autocomplete li {
		list-style-type: none;
	}

</style>
<script>
function resetAutocomplete(persons){		
	$("#searchPhrase").autocomplete( 
			{ source: 'selectBoxFiller?type=conference researchers',
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
	
	$("#searchPhrase").autocomplete({ position: { my : "right top", at: "right bottom" }});
	
	if($('#searchByStatus').val()==0){
		$('#searchByDeadlineSpan').hide();
	}
	else{
 		$('#searchByDeadlineSpan').show();
	}

	$('.saveCheckbox').click(function(){
		var insideDeadlineCheckbox=$(this);
		var cpid=$(this).attr("id");
		if(insideDeadlineCheckbox.is(':checked')==false){
	    	$("#genericDialog").dialog('option', 'buttons', {
	            "לא" : function() {
	                $(this).dialog("close");
	                insideDeadlineCheckbox.attr('checked',true);
	               },
	            "כן" : function() {
	                $(this).dialog("close");
	     		    var options = {
	     		       	 	url:       'conferenceProposals.html?action=removeFromDeadline&conferenceProposalId='+ cpid,        
	     		       	 	type:      'POST'
	     		    };
	     			$('#form').ajaxSubmit(options);
	            }
	           });
			$("#genericDialog").dialog({ modal: true });
			$("#genericDialog").dialog({ height: 300 });
			$("#genericDialog").dialog({ width: 500 });
			openHelp(this,'ביטול סימון של בקשה שכבר דורגה על ידי הדיקן תוציא אותה מרשימת הבקשות לדיון ותבטל את דירוגה. האם להמשיך? ');
		}
		else{
            var options = {
 		       	 	url:       'conferenceProposals.html?action=addToDeadline&conferenceProposalId='+ cpid,        
 		       	 	type:      'POST'
 		    };
 			$('#form').ajaxSubmit(options);	              

		}
 	});	

	$("#buttonEdit").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"edit\"/>");
    	$("#form").submit();
    	return true;
    });
	
	$(".cleanSearch").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"cleanSearch\"/>");
		$("#form").submit();
    	return true;
    });

    
    $("#searchPhrase").click(function(){
    	cleanSearch();
       	resetAutocomplete();
    });
    
    
    $("#searchByApprover").change(function(){
    	$("#searchPhrase").empty();
    	$("#form").append("<input type=\"hidden\" name=\"action\" value=\"search\"/>");
		$("#form").submit();
    });
    
    $('#searchByStatus').change(function(){
    	if($('#searchByStatus').val()==0){
    		$('#searchByDeadlineSpan').hide();
    	}
   		else{
			$('input[name=searchByDeadline]').val(1);
     		$('#searchByDeadlineSpan').show();
    	}
    	//each new search should start at page 1
		$("input#listViewPage").remove();
		$("form#form").append('<input type=\"hidden\" name=\"listView.page\" value=\"1\"/>');
	
    	$("#form").append("<input type=\"hidden\" name=\"action\" value=\"search\"/>");
		$("#form").submit();
    });
    
    $('.searchByDeadline').click(function(){
    	$("#form").append("<input type=\"hidden\" name=\"action\" value=\"search\"/>");
		$("#form").submit();
    });
    
    $('#buttonStartGrading').click(function(event){
		$( "#dialog:ui-dialog" ).dialog( "destroy" );
    	if($('#approver').val()==0){
 			$( "#dialog-confirm" ).dialog({
 		        autoOpen: false,
				resizable: false,
				height:250,
				width: 400,
				modal: true,
				title: "שליחת בקשה לדירוג וחוות דעת",
			
				buttons: {
					"סגור": function() {
					$( this ).dialog( "close" );
					}
				}
			});
 	  	    $("#dialog-confirm").text("לא נבחר דיקן מאשר").dialog("open");
		}
   		else{
    			$( "#dialog-confirm" ).dialog({
     		    autoOpen: false,
				resizable: false,
				height:250,
				width: 400,
				modal: true,
				title: "שליחת בקשה לדירוג",
			
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
     	  	$("#dialog-confirm").text("את/ה עומד/ת לשלוח בקשה לדירוג חוות דעת במייל לדיקן. האם את/ה מאשר/ת ?").dialog("open");
    	}
    	event.preventDefault();
    	return false;
    	
    });

   
    
    
    $("#genericDialog").dialog({
        autoOpen: false,
        show: 'fade',
        hide: 'fade',
        modal: true,
        width: 600,
        height:300,
        title: "מערכת הכנסים"
    });
 

  function openHelp(name,mytext){
     $("#genericDialog").html(mytext).dialog("open");
  } 
  	
  
  $("#dialogNewConferenceProposal").click(function(e) {
		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		$("#genericDialog").dialog({ modal: false });
		$("#genericDialog").dialog({ height: 400 });
		var text="לחיצה כאן תאפשר לך לפתוח, למלא ולשלוח לוועדת הכנסים, טופס בקשה לסיוע במימון כנס.<br/><br/> ";
		text+="וודא עם שותפיך לארגון הכנס שלא פתחו כבר בקשה לכנס זה ולא הגישו בקשה אחרת למימון כנס במהלך השנה האחרונה.";
	    openHelp("#dialogNewConferenceProposal",text);
	    return false;
	   });
  
  $("#dialogList").click(function(e) {
		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		$("#genericDialog").dialog({ modal: false });
		$("#genericDialog").dialog({ height: 400 });
		var text="הקש על בקשה שבה ברצונך לעיין או שאותה ברצונך לעדכן (העדכון אפשרי רק אם  היא עדיין בסטטוס טיוטה)";
	    openHelp("#dialogList",text);
	    return false;
	   });   
 <%@ include file="/WEB-INF/jsp/include/searchPaginationScripts.jsp" %>
});
</script>