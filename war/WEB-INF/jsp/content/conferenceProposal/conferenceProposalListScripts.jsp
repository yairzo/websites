<%@ page pageEncoding="UTF-8"%>
<script>
function resetAutocomplete(){		
	$("#searchPhrase").autocomplete( 
		{source: "/iws/personsHelper.html?role=ROLE_CONFERENCE_RESEARCHER",
		 minLength: 2,
		 highlight: true,
		 select: function(event, ui) {
			$("#searchPhrase").val(ui.item.label);
			window.location='/iws/conferenceProposal.html?action=new&researcherId='+ui.item.id
			event.preventDefault();					
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
	resetAutocomplete();	
	
	$("#searchPhrase").autocomplete({ position: { my : "right top", at: "right bottom" }});
	
	$("#searchPhrase").click(function(){
	   	$("input#searchPhrase").val('');
	   	resetAutocomplete();
	});
	
	//alert($('input[name=searchBySubmitted]').val());
	if($('input[name=searchBySubmitted]:checked').val()==1 || ${approver})
		$('#searchByDeadlineSpan').show();
	else
		$('#searchByDeadlineSpan').hide();
	

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
	     		       	 	url:       'conferenceProposals.html?action=save&conferenceProposalId='+ cpid,        
	     		       	 	type:      'POST'
	     		    };
	     			$('#form').ajaxSubmit(options);
	            }
	           });
			$("#genericDialog").dialog({ modal: false });
			$("#genericDialog").dialog({ height: 300 });
			$("#genericDialog").dialog({ width: 500 });
			openHelp(this,'ביטול סימון של בקשה שכבר דורגה על ידי הדיקן תוציא אותה מרשימת הבקשות לדיון ותבטל את דירוגה. האם להמשיך? ');
		}
		else{
            var options = {
 		       	 	url:       'conferenceProposals.html?action=save&conferenceProposalId='+ cpid,        
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
    
    $.get('selectBoxFiller',{type:'conference researchers'},function(data){
		var persons = data.split(",,");
		resetAutocomplete(persons);
		$("#searchPhrase").focus();
 	});
    
    $("#searchByApprover").change(function(){
    	$("#searchPhrase").empty();
    	$("#form").append("<input type=\"hidden\" name=\"action\" value=\"search\"/>");
		$("#form").submit();
    });
    
    $('.searchBySubmitted').click(function(){
    	if($('input[name=searchBySubmitted]:checked').val()==1 || ${approver}){
			$('input[name=searchByDeadline]:checked').val(1);
    		$('#searchByDeadlineSpan').show();
    	}
   		else{
    		//$('input[name=searchByDeadline]:checked').val(0);
    		$('#searchByDeadlineSpan').hide();
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
  	
  $(".confirmLink").click(function(e){
    	e.preventDefault();
    	var targetUrl = $(this).attr("href");
       	$("#genericDialog").dialog('option', 'buttons', {
                "בטל" : function() {
                    $(this).dialog("close");
                 },
                "המשך להגשת הבקשה" : function() {
                	window.location.href = "conferenceProposal.html?action=new";
                 }
        });
    	$("#genericDialog").dialog({ modal: false });
    	$("#genericDialog").dialog({ height: 350 });
    	$("#genericDialog").dialog({ width: 600 });

       	var text ="א. אני חוקר/ת במסלול הרגיל ובשירות פעיל.<br/>";
       	text+="ב. איש משותפי לארגון הכנס לא זכה בתמיכת ועדת הכנסים במהלך השנה שחלפה.<br/>";
    	text+="ג. אמלא את טופס הבקשה בהתאם ל <a href='http://ard.huji.ac.il/docs/AmotMidaKnasim.doc' target='_blank'><u>הוראות הנהלה מספר 17-011 לאמות המידה</u></a> של הועדה ולהנחיות (המסומנות ב <img src='image/questionmark.png' width='25' height='25'>) שבגוף הטופס.</br> ";
    	openHelp(this,text);
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