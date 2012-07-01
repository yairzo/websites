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
	if($('input[name=searchBySubmitted]:checked').val()==1)
		$('#searchByDeadlineSpan').show();
	else{
		$('#searchByDeadlineSpan').hide();
   		$('input[name=searchByDeadline]:checked').val(0);
	}

	$('.saveCheckbox').click(function(){
		   var options = {
	       	 	url:       'conferenceProposals.html?action=save&conferenceProposalId='+ this.id,        
	       	 	type:      'POST'
	     	};
		    $('#form').ajaxSubmit(options);
 	});	

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
    	if($('input[name=searchBySubmitted]:checked').val()==1)
    		$('#searchByDeadlineSpan').show();
    	else{
    		$('#searchByDeadlineSpan').hide();
    		$('input[name=searchByDeadline]:checked').val(0);
    	}

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
				title: "שליחת בקשה לדירוג",
			
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
     	  	$("#dialog-confirm").text("את/ה עומד/ת לשלוח בקשה לדירוג במייל לדיקן. האם את/ה מאשר/ת ?").dialog("open");
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
    /* linkOffset = $(name).position();
     linkWidth = $(name).width();
     linkHeight = $(name).height();
     scrolltop = $(window).scrollTop();
     $("#genericDialog").dialog("option", "position", [(linkOffset.left - 600/2) + linkWidth/2, linkOffset.top + linkHeight - scrolltop]);
    */ 
    $("#genericDialog").html(mytext).dialog("open");
  } 
  	
  $(".confirmLink").click(function(e){
    	e.preventDefault();
    	var targetUrl = $(this).attr("href");
       	$("#genericDialog").dialog('option', 'buttons', {
                "בטל" : function() {
                    $(this).dialog("close");
                 },
                "המשך להגשת הבקשה" : function() {
                	window.location.href = "editConferenceProposal.html?action=new";
                 }
        });
    	openHelp(this,"א. אני מצהיר בזאת שהנני חוקר/ת במסלול הרגיל ובשירות פעיל.<br/>ב. איש משותפי לארגון הכנס לא זכה בתמיכת ועדת הכנסים במהלך השנה שחלפה.");
       	return false;
       }); 
  
  $("#dialogNewConferenceProposal").click(function(e) {
		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		$("#genericDialog").dialog({ modal: false });
		$("#genericDialog").dialog({ height: 400 });
		var text="לחיצה כאן תאפשר לך לפתוח, למלא ולשלוח לוועדת הכנסים, טופס בקשה לסיוע במימון כנס.<br/><br/> ";
		text+="וודא עם שותפיך לארגון הכנס שלא פתחו כבר בקשה לכנס זה ולא הגישו בקשה אחרת למימון כנס במהלך השנה האחרונה.";
	    openHelp("#dialogNewConferenceProposal",text);
	    return false;
	   });
   
 <%@ include file="/WEB-INF/jsp/include/searchPaginationScripts.jsp" %>
});
</script>