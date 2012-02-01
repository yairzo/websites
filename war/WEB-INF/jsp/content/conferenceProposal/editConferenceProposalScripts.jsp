<%@ page  pageEncoding="UTF-8" %>
<script type="text/javascript" src="js/jquery.autosave.js"></script>
<script>



$(document).ready(function() {
	
	hideExtraCommittee("scientificCommittee");
	hideExtraCommittee("operationalCommittee");
	hideExtraCommittee("admitanceFee");
	hideExtraCommittee("assosiate");
	hideExtraCommittee("external");
	
	$("button.guestsAttach").click(function(event){
		event.preventDefault();
        $('input#guestsAttach').click();
	});
	
	$("button.programAttach").click(function(event){
		event.preventDefault();
        $('input#programAttach').click();
	});
	$("button.financialAttach").click(function(event){
		event.preventDefault();
        $('input#financialAttach').click();
	});
	
	
	$("#startConfDate").datepicker({ showOn: 'both', buttonImageOnly: true, buttonImage: 'image/icon_calendar.gif',dateFormat: 'dd/mm/yy', onSelect: function(){
    	var str1 = $("#startConfDate").val();
        var dt1  = str1.substring(0,2); 
        var mon1 = str1.substring(3,5); 
        var yr1  = str1.substring(6,10);  
        temp1 = mon1 +"/"+ dt1 +"/"+ yr1;
        var cfd = Date.parse(temp1);
        var date1 = new Date(cfd); 
        if(new Date()>date1){
      	  $("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
    	  $("#genericDialog").dialog({ modal: false });
     	  openHelp("#startConfDate","תאריך זה כבר עבר");
        }
    }
	});	
	
	$("#endConfDate").datepicker({ showOn: 'both', buttonImageOnly: true, buttonImage: 'image/icon_calendar.gif',dateFormat: 'dd/mm/yy',onSelect: function(){
    	var str1 = $("#startConfDate").val();
        var str2 = $("#endConfDate").val();
        var dt1  = str1.substring(0,2); 
        var mon1 = str1.substring(3,5); 
        var yr1  = str1.substring(6,10);  
        var dt2  = str2.substring(0,2); 
        var mon2 = str2.substring(3,5); 
        var yr2  = str2.substring(6,10); 
        temp1 = mon1 +"/"+ dt1 +"/"+ yr1;
        temp2 = mon2 +"/"+ dt2 +"/"+ yr2;
        var cfd = Date.parse(temp1);
        var ctd2 = Date.parse(temp2);
        var date1 = new Date(cfd); 
        var date2 = new Date(ctd2);

      if(date1 > date2) {
      	  $("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
    	  $("#genericDialog").dialog({ modal: false });
    	  openHelp("#endConfDate","תאריך סיום לפני תאריך התחלה");
      } 
    } });	
	
	$(function() {
        $.datepicker.regional['he'] = {
            closeText: 'סגור',
            prevText: '&#x3c;הקודם',
            nextText: 'הבא&#x3e;',
            currentText: 'היום',
            monthNames: ['ינואר','פברואר','מרץ','אפריל','מאי','יוני',
            'יולי','אוגוסט','ספטמבר','אוקטובר','נובמבר','דצמבר'],
            monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
            dayNames: ['ראשון','שני','שלישי','רביעי','חמישי','שישי','שבת'],
            dayNamesShort: ['א\'','ב\'','ג\'','ד\'','ה\'','ו\'','ש\''],
            dayNamesMin: ['א\'','ב\'','ג\'','ד\'','ה\'','ו\'','ש\''],
            weekHeader: 'Wk',
            dateFormat: 'dd/mm/yy',
            firstDay: 0,
            isRTL: true,
            showMonthAfterYear: false,
            yearSuffix: ''
        };
        $.datepicker.setDefaults($.datepicker.regional['he']);
 
    });

	$('form').find('input').autoSave(function(){
		<c:if test="${command.versionId > 0}">
			return false;
		</c:if>
		var options = {
	       	 	url:       'editConferenceProposal.html' ,        
	       	 	type:      'POST'
     	};
   		$("#form").ajaxForm();
   		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" value=\"true\"/>");
	    $('#form').ajaxSubmit(options);
		var elementClass = $(this).attr('class');
		if (elementClass.indexOf("scientificCommittee")!=-1)
	    	hideExtraCommittee("scientificCommittee");
		if (elementClass.indexOf("operationalCommittee")!=-1)
			hideExtraCommittee("operationalCommittee");
		if (elementClass.indexOf("admitanceFee")!=-1)
			hideExtraCommittee("admitanceFee");
		if (elementClass.indexOf("assosiate")!=-1)
			hideExtraCommittee("assosiate");
		if (elementClass.indexOf("external")!=-1)
			hideExtraCommittee("external");
		}, {delay: 2000});
	
	$('form').find('select').change(function(){
		<c:if test="${command.versionId > 0}">
			return false;
		</c:if>
		$("#form").ajaxForm();
		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" value=\"true\"/>");
	    $('#form').ajaxSubmit();
	});

	
	$('#guestsAttach').change(function(){
		if ("${command.versionId}"==0){
		   var options = {
	       	 	url:       'editConferenceProposal.html' ,        
	       	 	type:      'POST'
	     	};
		    $('#form').ajaxSubmit(options);
		    $('#guestsAttachDiv').html("<a href='fileViewer?conferenceProposalId=${command.id}&attachFile=guestsAttach&attachmentId=1' target='_blank'>רשימת מוזמנים</a><img src='image/icon_somefile.gif'/>");
		}
		else 
			return false;
	});	
	$('#programAttach').change(function(){
		
		if ("${command.versionId}"==0){
		   var options = {
	       	 	url:       'editConferenceProposal.html' ,        
	       	 	type:      'POST'
	     	};
		    $('#form').ajaxSubmit(options);
		   	$('#programAttachDiv').html("<a href='fileViewer?conferenceProposalId=${command.id}&attachFile=programAttach&attachmentId=1' target='_blank'>תוכנית הכנס</a><img src='image/icon_somefile.gif'/>");
		}
		else 
			return false;
	});		
	$('#financialAttach').change(function(){
		if ("${command.versionId}"==0){
		   var options = {
	       	 	url:       'editConferenceProposal.html' ,        
	       	 	type:      'POST'
	     	};
		    $('#form').ajaxSubmit(options);
		    $('#financialAttachDiv').html("<a href='fileViewer?conferenceProposalId=${command.id}&attachFile=financialAttach&attachmentId=1' target='_blank'>תוכנית תקציבית</a><img src='image/icon_somefile.gif'/>");
		}
		else 
			return false;
	});		

	if($('#company').attr('checked') || $('#companyViewOnly').attr('checked'))
		$('.organizingCompanyPart').show();
	else
		$('.organizingCompanyPart').hide();
	
	$('#company').click(function(){	
		if($('#company').attr('checked'))
			$('.organizingCompanyPart').show();
		else
			$('.organizingCompanyPart').hide();
	});
	
	
	
	$(".fromAdmitanceFeeSave").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"fromAdmitanceFeeSave\"/>");
    	$("#form").submit();
    	return true;
    });
	
	$(".fromExternalSave").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"fromExternalSave\"/>");
    	$("#form").submit();
    	return true;
    });
	

	$(".fromAssosiateSave").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"fromAssosiateSave\"/>");
    	$("#form").submit();
    	return true;
    });
	
	$(".scientificCommitteeSave").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"scientificCommitteeSave\"/>");
    	$("#form").submit();
    	return true;
    });
	
	
	$(".operationalCommitteeSave").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"operationalCommitteeSave\"/>");
    	$("#form").submit();
    	return true;
    });
	
	
	$(".deleteFinancialSupport").click(function(e){
		e.preventDefault();
		var financialSupportId= this.id;
		var deleteButton = $(this);
	   	$("#genericDialog").dialog({ modal: true });
    	$("#genericDialog").dialog('option', 'buttons', {
            "לא" : function() {
                $(this).dialog("close");
                return false;
               },
            "כן" : function() {
                $(this).dialog("close");
    	   		deleteButton.parents('tr.financialSupport').remove();
    	   		$("#form").ajaxForm();
    	   		$("#form").ajaxSubmit();
    			return true;
               }
            });
    	openHelp(this,"האם הנך מאשר את מחיקת שורת ההכנסה?");
        return false;
   	});	
	
	$(".deleteCommittee").click(function(e){
		e.preventDefault();
		var committeeId= this.id;
		var deleteButton = $(this);
		$("#genericDialog").dialog({ modal: true });
    	$("#genericDialog").dialog('option', 'buttons', {
            "לא" : function() {
                $(this).dialog("close");
                return false;
               },
            "כן" : function() {
                $(this).dialog("close");
    	   		deleteButton.parents('tr.committee').remove();
    	   		$("#form").ajaxForm();
    	   		$("#form").ajaxSubmit();
    	        return true;
            }
        });
		openHelp(this,"האם הנך מאשר את מחיקת הועדה?");
    	return false;
	});	
	
	$("button.submitForGrading").click(function(){
		var errors=false;
		if($("#deanSelect").val()=='0'){
			errors = true;
			$("#errordeanselect").html('<font color="red">יש לבחור מאשר לפני הגשה<font color="red"><br>');
		}
		else{
			$("#errordeanselect").html('');
		}
		if($("#subject").val()==''){
			errors = true;
			$("#errorsubject").html('<font color="red">יש למלא שדה נושא הכנס<font color="red"><br>');
		}
		else{
			$("#errorsubject").html('');
		}
		var numberRegex=/^[+-]?\d+(\.\d+)?([eE][+-]?d+)?$/;
		var countRegex=/^\d+$/;
		var emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
		var phoneRegex = /^[\d]{2,3}-[\d]{7}$/;		
		if(!numberRegex.test($("#totalCost").val())){
			errors = true;
			$("#errortotalcost").html('<font color="red">יש להכניס ערך מספרי לשדה סכום<font color="red"><br>');
		}
		else{
			$("#errortotalcost").html('');
		}
		if(!numberRegex.test($("#supportSum").val())){
			errors = true;
			$("#errorsupportsum").html('<font color="red">יש להכניס ערך מספרי לשדה סכום הסיוע המבוקש<font color="red"><br>');
		}
		else{
			$("#errorsupportsum").html('');
		}
		if(!countRegex.test($("#participants").val())){
			errors = true;
			$("#errorparticipants").html('<font color="red">יש להכניס ערך מספרי לשדה משתתפים<font color="red"><br>');
		}
		else{
			$("#errorparticipants").html('');
		}
		if($("#organizingCompanyEmail").val()!='' && !emailRegex.test($("#organizingCompanyEmail").val())){
			errors = true;
			$("#errororganizingCompanyEmail").html('<font color="red">יש להזין כתובת אימייל חברה מארגנת תקנית<font color="red"><br>');
		}
		else{
			$("#errororganizingCompanyEmail").html('');
		}
		if($("#contactPersonEmail").val()!='' && !emailRegex.test($("#contactPersonEmail").val())){
			errors = true;
			$("#errorcontactPersonEmail").html('<font color="red">יש להזין כתובת אימייל איש קשר תקנית<font color="red"><br>');
		}
		else{
			$("#errorcontactPersonEmail").html('');
		}
		if($("#organizingCompanyPhone").val()!='' && !phoneRegex.test($("#organizingCompanyPhone").val())){
			errors = true;
			$("#errororganizingCompanyPhone").html('<font color="red">יש להזין מספר טלפון חברה מארגנת תקני<font color="red"><br>');
		}
		else{
			$("#errororganizingCompanyPhone").html('');
		}
		if($("#organizingCompanyFax").val()!='' && !phoneRegex.test($("#organizingCompanyFax").val())){
			errors = true;
			$("#errororganizingCompanyFax").html('<font color="red">יש להזין פקס חברה מארגנת תקני<font color="red"><br>');
		}
		else{
			$("#errororganizingCompanyFax").html('');
		}
		if($("#contactPersonPhone").val()!='' && !phoneRegex.test($("#contactPersonPhone").val())){
			errors = true;
			$("#errorcontactPersonPhone").html('<font color="red">יש להזין מספר טלפון איש קשר תקני<font color="red"><br>');
		}
		else{
			$("#errorcontactPersonPhone").html('');
		}
		if (errors)
			return false;
		else{
			$("#form").append("<input type=\"hidden\" name=\"action\" value=\"submitForGrading\"/>");
    		$("#form").submit();
    		return true;
		}
    });
	
	$("button.unsubmitForGrading").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"unsubmitForGrading\"/>");
    	$("#form").submit();
    	return true;
    });
	
	
	$("button.submit").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"showMessage\" value=\"saved\"/>");
		$("#form").submit();
    	return true;
    	
    });	
	
    
     $("#genericDialog").dialog({
           autoOpen: false,
           show: 'fade',
           hide: 'fade',
           width: 200,
           open: function() { $(".ui-dialog").css("box-shadow","#000 5px 5px 5px");}
     });
    
   $(".ui-dialog-titlebar").hide();
   $("#dialogInitiatingBody").click(function(e) {
   	$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
	$("#genericDialog").dialog({ modal: false });
    openHelp("#dialogInitiatingBody","הגוף שיוזם את הכנס");
    return false;
   });
     
   $("#dialogInitiatingBodyRole").click(function(e) {
	  $("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
	  $("#genericDialog").dialog({ modal: false });
	  openHelp("#dialogInitiatingBodyRole","תפקיד בגוף היוזם את הכנס");
	  return false;
   }); 
   
   var fieldname=""; 
   function openHelp(name,mytext){
	    fieldname=name;
   	 	if(fieldname=="")
	    	$("#genericDialog").dialog("option", "position", "center");
	    else
   	 		$('#genericDialog').dialog({position: { my: 'top', at: 'top', of: $(name)} });
   	 	
	    $("#genericDialog").text(mytext).dialog("open");
    } 
    
    $("#form,#genericDialog").click(function(e){
    	$("#genericDialog").dialog("close");
    });    
    
    $(window).scroll(function() {
    	if (fieldname=="") 
    		$("#genericDialog").dialog("option", "position", "center");
    });   
    
	<c:if test="${userMessage!=null}">
	var userMessage = "${userMessage}";
   	$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
	$("#genericDialog").dialog({ modal: false });
	openHelp("",userMessage);
    </c:if> 

    
   
});


function hideExtraCommittee(trCssClass){
	var emptyRowsCounter = 0;
	$('tr.'+trCssClass).each(function(){
		var row = $(this);
		var allInputsEmpty = true;
		row.find('input').each(function(){
			if ($(this).val()!=""){
				row.show();
				allInputsEmpty = false;
			}
		});
		if (allInputsEmpty){
			if (emptyRowsCounter ==0)
				row.show();
			emptyRowsCounter = emptyRowsCounter +1;
		}
	});
}


</script>