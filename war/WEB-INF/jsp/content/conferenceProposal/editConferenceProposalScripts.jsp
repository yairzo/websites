<%@ page  pageEncoding="UTF-8" %>
<script type="text/javascript" src="js/jquery.autosave.js"></script>
<script language="Javascript">



$(document).ready(function() {

	$("#startConfDate").datepicker({ showOn: 'button', buttonImageOnly: true, buttonImage: 'image/icon_calendar.gif',dateFormat: 'dd/mm/yy' });	
	$("#endConfDate").datepicker({ showOn: 'button', buttonImageOnly: true, buttonImage: 'image/icon_calendar.gif',dateFormat: 'dd/mm/yy' });	

	$('.autosaveclass').autoSave(function(){
		if ("${command.versionId}"==0){
			$.alerts.alert("...saving");
		   var options = {
	       	 	url:       'editConferenceProposal.html' ,        
	       	 	type:      'POST'
	     	};
		    $('#form').ajaxSubmit(options);
		}
		else 
			return false;
    }, {delay: 2000});	
	
	

	if($('#company').attr('checked'))
		$('.organizingCompanyPart').show();
	else
		$('.organizingCompanyPart').hide();
	
	$('#company').click(function(){	
		if($('#company').attr('checked'))
			$('.organizingCompanyPart').show();
		else
			$('.organizingCompanyPart').hide();
	});
	
	
	
	$("button.fromAdmitanceFeeSave").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"fromAdmitanceFeeSave\"/>");
    	$("#form").submit();
    	return true;
    });
	
	$("button.fromExternalSave").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"fromExternalSave\"/>");
    	$("#form").submit();
    	return true;
    });
	
	$("button.fromAssosiateSave").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"fromAssosiateSave\"/>");
    	$("#form").submit();
    	return true;
    });
	
	$("button.scientificCommitteeSave").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"scientificCommitteeSave\"/>");
    	$("#form").submit();
    	return true;
    });

	$("button.operationalCommitteeSave").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"operationalCommitteeSave\"/>");
    	$("#form").submit();
    	return true;
    });
	
	$("button.submitForGrading").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"submitForGrading\"/>");
    	$("#form").submit();
    	return true;
    });
	$("button.unsubmitForGrading").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"unsubmitForGrading\"/>");
    	$("#form").submit();
    	return true;
    });
	
	
	
    $("#genericDialog").dialog({
           autoOpen: false,
           show: 'fade',
           hide: 'fade',
           modal: false,
           width: 200,
           buttons: {
               "Close": function() {
                   $(this).dialog("close");
               }
           }
     });
    $(".ui-dialog-titlebar").hide();
    
     $("#dialogInitiatingBody").mouseover(function(e) {
    	 	openHelp("#dialogInitiatingBody","הגוף היוזם את הכנס");
      });
     
     $("#dialogInitiatingBodyRole").mouseover(function(e) {
    	 openHelp("#dialogInitiatingBodyRole","תפקיד בגוף היוזם את הכנס");
   }); 
    function openHelp(name,mytext){
        linkOffset = $(name).position();
        linkWidth = $(name).width();
        linkHeight = $(name).height();
        scrolltop = $(window).scrollTop();
        $("#genericDialog").dialog("option", "position", [(linkOffset.left - 200/2) + linkWidth/2, linkOffset.top + linkHeight - scrolltop]);
        $("#genericDialog").text(mytext).dialog("open");
    } 

 
});


</script>