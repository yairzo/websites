<%@ page  pageEncoding="UTF-8" %>
<script type="text/javascript" src="js/jquery.autosave.js"></script>
<script>


$(document).ready(function() {

	$("#form").ajaxForm();

	hideExtraCommittee("scientificCommittee");
	hideExtraCommittee("operationalCommittee");
	hideExtraCommittee("admitanceFee");
	hideExtraCommittee("assosiate");
	hideExtraCommittee("external");
	if("${command.sumFromAssociate}">0)
		$('#fromAssosiateCount').html("${command.sumFromAssociate}");
	if("${command.sumFromExternal}">0)
		$('#fromExternalCount').html("${command.sumFromExternal}");
	if("${command.sumFromAdmitanceFee}">0)
		$('#fromAdmitanceFeeCount').html("${command.sumFromAdmitanceFee}");
	calcTotalFee();	
	calcParticipants();
	$("#allExpenses").html($('#totalCost').val());
	
	if($('#location').val()=='6'){
		$('td.locationDetails').css("opacity","1");
		$('#locationDetail').attr('readonly', false);
	}
	else{
		$('#locationDetail').attr('readonly', true);
		$('#locationDetail').val('');
	}
	
	$('#location').change(function(){
		if($('#location').val()=='6'){
			$('td.locationDetails').css("opacity","1");
			$('#locationDetail').attr('readonly', false);
		}
		else{
			$('td.locationDetails').css("opacity","0.3");
			$('#locationDetail').attr('readonly', true);
			$('#locationDetail').val('');
		}
	});
	
	
	$(".calcSum").keyup(function(e) {
		calcParticipants();
		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		$("#genericDialog").dialog({ modal: false });
		$("#genericDialog").dialog({ height: 200 });
		$("#genericDialog").dialog({ width: 400 });
		var numberRegex=/^\d+$/;
		if(!numberRegex.test($(this).val())){
			 $(this).val('0');
			 openHelp('','נא להכניס ערך מספרי שלם');
			 calcParticipants();
			 return false;
		}
		else if(totalParticipantsCounter>1000){
			 $(this).val('0');
			 openHelp('','כלל המשתתפים בכנס לא יעלה על 1000');
			 calcParticipants();
			 return false;
		}
	 });   
	
	$("button.guestsAttach").click(function(event){
		event.preventDefault();
        $('#attachmentsForm #guestsAttach').click();
	});
	
	var attachIndex=0;
	$("button.fromAssosiateAttach").click(function(event){
		event.preventDefault();
		attachIndex=$(this).attr("id");
		$('#attachmentsForm #fromAssosiateAttachFile').click();
        //$(this).siblings('input.fromAssosiateAttachFile').click();
	});
	
	$("button.fromExternalAttach").click(function(event){
		event.preventDefault();
		attachIndex=$(this).attr("id");
		$('#attachmentsForm #fromExternalAttachFile').click();
        //$(this).siblings('input.fromExternalAttachFile').click();
	});
	
	$("button.fromAdmitanceFeeAttach").click(function(event){
		event.preventDefault();
		attachIndex=$(this).attr("id");
		$('#attachmentsForm #fromAdmitanceFeeAttachFile').click();
        //$(this).siblings('input.fromAdmitanceFeeAttachFile').click();
	});

	$("button.programAttach").click(function(event){
		event.preventDefault();
       $('#attachmentsForm #programAttach').click();
       //$('input#programAttach').click();
	});
	$("button.financialAttach").click(function(event){
		event.preventDefault();
	    $('#attachmentsForm #financialAttach').click();
       // $('input#financialAttach').click();
	});
	$("button.companyAttach").click(function(event){
		event.preventDefault();
	    $('#attachmentsForm #companyAttach').click();
       //$('input#companyAttach').click();
	});
	
	
	$("#startConfDate").datepicker({ dateFormat: 'dd/mm/yy', onSelect: function(){
    	var str1 = $("#startConfDate").val();
        var dt1  = str1.substring(0,2); 
        var mon1 = str1.substring(3,5); 
        var yr1  = str1.substring(6,10);  
        temp1 = mon1 +"/"+ dt1 +"/"+ yr1;
        var cfd = Date.parse(temp1);
        var date1 = new Date(cfd); 
        
        var str2 = $("#endConfDate").val();
        var dt2  = str2.substring(0,2); 
        var mon2 = str2.substring(3,5); 
        var yr2  = str2.substring(6,10); 
        temp2 = mon2 +"/"+ dt2 +"/"+ yr2;
        var ctd2 = Date.parse(temp2);
        var date2 = new Date(ctd2);

        if(new Date()>date1){
      	  $("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
    	  $("#genericDialog").dialog({ modal: false });
  		$("#genericDialog").dialog({ height: 200 });
		$("#genericDialog").dialog({ width: 400 });
      	  openHelp("#startConfDate","תאריך זה כבר עבר");
        }
        else if(date1 > date2) {
        	  $("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
        	  $("#genericDialog").dialog({ modal: false });
      		$("#genericDialog").dialog({ height: 200 });
    		$("#genericDialog").dialog({ width: 400 });
       	  openHelp("#endConfDate","תאריך סיום לפני תאריך התחלה");
        } 

    }
	});	
	
	$("#endConfDate").datepicker({ dateFormat: 'dd/mm/yy',onSelect: function(){
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
  		$("#genericDialog").dialog({ height: 200 });
		$("#genericDialog").dialog({ width: 400 });
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
	
	$('form').find('input:not([class*=submit],[class*=cancelSubmission],[class*=isInsideDeadline],[type=file],[type=button])').autoSave(function(){		
		//alert("saving...");
		<c:if test="${command.versionId > 0}">
			return false;
		</c:if>
		var options = {
	       	 	url:       'editConferenceProposal.html' ,        
	       	 	type:      'POST'
     	};
		var elementClass = $(this).attr('class');
		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" id=\"ajaxSubmit\" value=\"true\"/>");
		$('#form').ajaxSubmit(options);
		if (elementClass.indexOf("scientificCommittee")!=-1)
	    		hideExtraCommittee("scientificCommittee");
		if (elementClass.indexOf("operationalCommittee")!=-1)
				hideExtraCommittee("operationalCommittee");
		if (elementClass.indexOf("admitanceFee")!=-1){
				hideExtraCommittee("admitanceFee");
				calcFee("fromAdmitanceFee");
				calcTotalFee();
		}
		if (elementClass.indexOf("assosiate")!=-1){
				hideExtraCommittee("assosiate");
				calcFee("fromAssosiate");
				calcTotalFee();
		}
		if (elementClass.indexOf("external")!=-1){
				hideExtraCommittee("external");
				calcFee("fromExternal");
				calcTotalFee();
		}
	}, {delay: 3000});
	
	$('form').find('select').change(function(){
		<c:if test="${command.versionId > 0}">
			return false;
		</c:if>
		
		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" id=\"ajaxSubmit\" value=\"true\"/>");
	    $('#form').ajaxSubmit();
	});
	
	$('form').find('textarea:not([class*=newCommitteeRemarks])').autoSave(function(){
		<c:if test="${command.versionId > 0}">
			return false;
		</c:if>
		
		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" id=\"ajaxSubmit\" value=\"true\"/>");
	    $('#form').ajaxSubmit();
	}, {delay: 3000});
	
	$('.cancelSubmission').change(function(event){
		if($('.cancelSubmission').is(":checked")){
	    	$("#genericDialog").dialog('option', 'buttons', {
	            "לא" : function() {
	                $(this).dialog("close");
	                $('.cancelSubmission').attr('checked',false);
	               },
	            "כן" : function() {
	                $(this).dialog("close");
	                $('.cancelSubmission').attr('checked',true);
	               }
	            });
			$("#genericDialog").dialog({ modal: false });
			$("#genericDialog").dialog({ height: 200 });
			$("#genericDialog").dialog({ width: 300 });
			openHelp(this,'ביטול סימון של בקשה שכבר דורגה על ידי הדיקן תוציא אותה מרשימת הבקשות לדיון ותבטל את דירוגה. האם להמשיך? ');
		}
	});
	
	$('.fee').change(function(event){
		event.preventDefault();

		var numberRegex=/^[+-]?\d+(\.\d+)?([eE][+-]?d+)?$/;
		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		$("#genericDialog").dialog({ modal: false });
		$("#genericDialog").dialog({ height: 200 });
		$("#genericDialog").dialog({ width: 400 });
		if(!numberRegex.test($(this).val())){
			 $(this).val('');
			 openHelp(this,'נא להכניס ערך מספרי לשדה זה');
			 return false;
		}
		else if(Number($(this).val())>150000){
			 $(this).val('');
			 openHelp(this,'נא להכניס ערך שאינו עולה על 150000');
			 return false;
		}
		return false;
	});
	
	$('#participants').change(function(event){
		event.preventDefault();
		var numberRegex=/^[+-]?\d+(\.\d+)?([eE][+-]?d+)?$/;
		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		$("#genericDialog").dialog({ modal: false });
		$("#genericDialog").dialog({ height: 200 });
		$("#genericDialog").dialog({ width: 400 });
		if(!numberRegex.test($(this).val())){
			 $(this).val('');
			 openHelp(this,'נא להכניס ערך מספרי לשדה זה');
			 return false;
		}
		else if(totalParticipantsCounter>0 ){
			if(Number($(this).val())>totalParticipantsCounter*1.05){
			 $(this).val('');
			 openHelp(this,'מספר האנשים באולם המבוקש לא יעלה על 5% ממספר המוזמנים לכנס');
			 return false;
			}
		}
		else if(Number($(this).val())>1000){
			 $(this).val('');
			 openHelp(this,'נא להכניס ערך שאינו עולה על 1000');
			 return false;
		}
		return false;
	});
	
	$('.participantsCheck').change(function(event){
		event.preventDefault();
		var numberRegex=/^\d+$/;
		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		$("#genericDialog").dialog({ modal: false });
		$("#genericDialog").dialog({ height: 200 });
		$("#genericDialog").dialog({ width: 400 });
		if(!numberRegex.test($(this).val())){
			 $(this).val('0');
			 openHelp(this,'נא להכניס ערך מספרי לשדה זה');
			 return false;
		}
		else if(totalParticipantsCounter>0 ){
			if(Number($(this).val())>totalParticipantsCounter){
			 $(this).val('0');
			 openHelp(this,'נא להכניס ערך שאינו עולה על סך המשתתפים כפי שסוכמו בטבלת המשתתפים');
			 return false;
			}
		}
		else if(Number($(this).val())>1000){
			 $(this).val('0');
			 openHelp(this,'נא להכניס ערך שאינו עולה על 1000');
			 return false;
		}
		
		if($('.sumPerson', $(this).closest("tr")).val()!=''){
				var t = Number($(this).val())* Number($('.sumPerson', $(this).closest("tr")).val());
				$(".sumPersons", $(this).closest("tr")).val(t);
				calcFee("fromAdmitanceFee");
				calcTotalFee();
		}
		return false;
	});	
	
	$('.sumPerson').change(function(event){
		event.preventDefault();
		var t = Number($(this).val())* Number($('.participantsCheck', $(this).closest("tr")).val());
		$(".sumPersons", $(this).closest("tr")).val(t);
		calcFee("fromAdmitanceFee");
		calcTotalFee();
		return false;
	});	
	
	$('#supportSum').change(function(event){
		event.preventDefault();
		var numberRegex=/^\d+$/;
		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		$("#genericDialog").dialog({ modal: false });
		$("#genericDialog").dialog({ height: 200 });
		$("#genericDialog").dialog({ width: 400 });
		if(!numberRegex.test($(this).val())){
			 $(this).val('0');
			 openHelp(this,'נא להכניס ערך מספרי שלם לשדה זה');
			 return false;
		}
		return false;
	});	
	
	$('#totalCost').change(function(event){
		//event.preventDefault();
		$("#allExpenses").html($('#totalCost').val());
		calcDiff();
		var numberRegex=/^\d+$/;
		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		$("#genericDialog").dialog({ modal: false });
		$("#genericDialog").dialog({ height: 200 });
		$("#genericDialog").dialog({ width: 400 });
		if(!numberRegex.test($(this).val())){
			 $(this).val('0');
			 openHelp(this,'נא להכניס ערך מספרי שלם לשדה זה');
			 return false;
		}
		//return false;
	});		
	
	$('.assosiate').change(function(event){
		event.preventDefault();
		var row = $(this).closest('tr');
		row.find('button.fromAssosiateAttach').show();
	});
	$('.external').change(function(event){
		event.preventDefault();
		var row = $(this).closest('tr');
		row.find('button.fromExternalAttach').show();
	});
	$('.admitanceFee').change(function(event){
		event.preventDefault();
		var row = $(this).closest('tr');
		row.find('button.fromAdmitanceFeeAttach').show();
	});
	
	$('#guestsAttach').change(function(event){
		<c:if test="${command.versionId > 0}">
			return false;
		</c:if>
		$("#attachmentsForm").append("<input type=\"hidden\" name=\"ajaxSubmit\" id=\"ajaxSubmit\" value=\"true\"/>");
		$("#attachmentsForm").append("<input type=\"hidden\" name=\"cpid\" id=\"cpid\" value=\"${command.id}\"/>");
		$('#attachmentsForm').ajaxSubmit();
		$('#guestsAttachDiv').html("<img src='image/attach.jpg'/><a href='fileViewer?conferenceProposalId=${command.id}&attachFile=guestsAttach&attachmentId=1' target='_blank'>רשימת מרצים ומוזמנים</a>");
		
	});	
	
	$('#programAttach').change(function(){
		<c:if test="${command.versionId > 0}">
			return false;
		</c:if>
		$("#attachmentsForm").append("<input type=\"hidden\" name=\"ajaxSubmit\" id=\"ajaxSubmit\" value=\"true\"/>");
		$("#attachmentsForm").append("<input type=\"hidden\" name=\"cpid\" id=\"cpid\" value=\"${command.id}\"/>");
		$('#attachmentsForm').ajaxSubmit();
		$('#programAttachDiv').html("<img src='image/attach.jpg'/><a href='fileViewer?conferenceProposalId=${command.id}&attachFile=programAttach&attachmentId=1' target='_blank'>תוכנית הכנס</a>");		
	});		
	$('#financialAttach').change(function(){
		<c:if test="${command.versionId > 0}">
			return false;
		</c:if>
		$("#attachmentsForm").append("<input type=\"hidden\" name=\"ajaxSubmit\" id=\"ajaxSubmit\" value=\"true\"/>");
		$("#attachmentsForm").append("<input type=\"hidden\" name=\"cpid\" id=\"cpid\" value=\"${command.id}\"/>");
		$('#attachmentsForm').ajaxSubmit();
		$('#financialAttachDiv').html("<img src='image/attach.jpg'/><a href='fileViewer?conferenceProposalId=${command.id}&attachFile=financialAttach&attachmentId=1' target='_blank'>תוכנית תקציבית</a>");
	});		
	$('#companyAttach').change(function(event){
		<c:if test="${command.versionId > 0}">
			return false;
		</c:if>
		$("#attachmentsForm").append("<input type=\"hidden\" name=\"ajaxSubmit\" id=\"ajaxSubmit\" value=\"true\"/>");
		$("#attachmentsForm").append("<input type=\"hidden\" name=\"cpid\" id=\"cpid\" value=\"${command.id}\"/>");
		$('#attachmentsForm').ajaxSubmit();
		$('#companyAttachDiv').html("<img src='image/attach.jpg'/><a href='fileViewer?conferenceProposalId=${command.id}&attachFile=companyAttach&attachmentId=1' target='_blank'>הסכם חברה</a>");
		
	});	
	
	$('#fromAssosiateAttachFile').change(function(event){
		<c:if test="${command.versionId > 0}">
			return false;
		</c:if>
		//alert(attachIndex);
		$("#attachmentsForm").append("<input type=\"hidden\" name=\"ajaxSubmit\" id=\"ajaxSubmit\" value=\"true\"/>");
		$("#attachmentsForm").append("<input type=\"hidden\" name=\"attachIndex\" id=\"attachIndex\" value=\""+attachIndex+"\"/>");
		$("#attachmentsForm").append("<input type=\"hidden\" name=\"cpid\" id=\"cpid\" value=\"${command.id}\"/>");
		$('#attachmentsForm').ajaxSubmit();
		$("#attachmentsForm #attachIndex").remove();
		//var filename = $(this).attr("id");
		//var aIndex = filename.substring(filename.indexOf("[")+1,filename.indexOf("]"));
		//$(this).siblings('#fromAssosiateAttachDiv').html("<img src='image/attach.jpg'/><a href='fileViewer?conferenceProposalId=${command.id}&assosiateId="+aIndex+"&attachFile=assosiateAttach&attachmentId=1' target='_blank'>אסמכתא</a>");
		var aIndex =attachIndex;
        var divname='#fromAssosiateAttachDiv'+aIndex;
		$(divname).html("<img src='image/attach.jpg'/><a href='fileViewer?conferenceProposalId=${command.id}&assosiateId="+aIndex+"&attachFile=assosiateAttach&attachmentId=1' target='_blank'>אסמכתא</a>");
	});	

	$('#fromExternalAttachFile').change(function(event){
		<c:if test="${command.versionId > 0}">
			return false;
		</c:if>
		$("#attachmentsForm").append("<input type=\"hidden\" name=\"ajaxSubmit\" id=\"ajaxSubmit\" value=\"true\"/>");
		$("#attachmentsForm").append("<input type=\"hidden\" name=\"attachIndex\" id=\"attachIndex\" value=\""+attachIndex+"\"/>");
		$("#attachmentsForm").append("<input type=\"hidden\" name=\"cpid\" id=\"cpid\" value=\"${command.id}\"/>");
		$('#attachmentsForm').ajaxSubmit();
		$("#attachmentsForm #attachIndex").remove();
		//var filename = $(this).attr("id");
		//var aIndex = filename.substring(filename.indexOf("[")+1,filename.indexOf("]"));
		//$(this).siblings('#fromExternalAttachDiv').html("<img src='image/attach.jpg'/><a href='fileViewer?conferenceProposalId=${command.id}&externalId="+aIndex+"&attachFile=externalAttach&attachmentId=1' target='_blank'>אסמכתא</a>");
		var aIndex =attachIndex;
        var divname='#fromExternalAttachDiv'+aIndex;
		$(divname).html("<img src='image/attach.jpg'/><a href='fileViewer?conferenceProposalId=${command.id}&externalId="+aIndex+"&attachFile=externalAttach&attachmentId=1' target='_blank'>אסמכתא</a>");
	});	
	
	$('#fromAdmitanceFeeAttachFile').change(function(event){
		<c:if test="${command.versionId > 0}">
			return false;
		</c:if>
		$("#attachmentsForm").append("<input type=\"hidden\" name=\"ajaxSubmit\" id=\"ajaxSubmit\" value=\"true\"/>");
		$("#attachmentsForm").append("<input type=\"hidden\" name=\"attachIndex\" id=\"attachIndex\" value=\""+attachIndex+"\"/>");
		$("#attachmentsForm").append("<input type=\"hidden\" name=\"cpid\" id=\"cpid\" value=\"${command.id}\"/>");
		$('#attachmentsForm').ajaxSubmit();
		$("#attachmentsForm #attachIndex").remove();
		//var filename = $(this).attr("id");
		//var aIndex = filename.substring(filename.indexOf("[")+1,filename.indexOf("]"));
		var aIndex =attachIndex;
        var divname='#fromAdmitanceFeeAttachDiv'+aIndex;
		$(divname).html("<img src='image/attach.jpg'/><a href='fileViewer?conferenceProposalId=${command.id}&admitanceFeeId="+aIndex+"&attachFile=admitanceFeeAttach&attachmentId=1' target='_blank'>אסמכתא</a>");
	});	



	/*if($('#company').attr('checked') || $('#companyViewOnly').attr('checked'))
		$('.organizingCompanyPart').show();
	else
		$('.organizingCompanyPart').hide();
	*/
	
	if(${command.organizingCompany}){
		$('.organizingCompanyPart').show();
		$('.organizingContactPart').hide();
	}
	else{
		$('.organizingCompanyPart').hide();
		$('.organizingContactPart').show();
	}

	$("input:radio[name=organizingCompany]").click(function() {
	    var value = $(this).val();
		if(value=="true"){
			$('.organizingCompanyPart').show();
			$('.organizingContactPart').hide();
		}
		else {
			$('.organizingCompanyPart').hide();
			$('.organizingContactPart').show();
		}
	});
	
	$("#location").change(function(e){
		if($('#location').val()=='6')
			$('#locationDetail').attr('readonly', false);
		else{
			$('#locationDetail').attr('readonly', true);
			$('#locationDetail').val('');
		}
	});

	/*$('#company').click(function(){	
		if($('#company').attr('checked'))
			$('.organizingCompanyPart').show();
		else
			$('.organizingCompanyPart').hide();
	});*/
	
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
    	   		//calcFee("fromExternal");
    	   		//calcFee("fromAssosiate");
    	   		//calcFee("fromAdmitanceFee");
     	   		//calcTotalFee();
     	   		var options = { 
        		  		success:    function() { 
        		   		   	window.location.reload(); 
        		    	} 
        			}; 
   				$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" id=\"ajaxSubmit\" value=\"true\"/>");
   				//$("#form #ajaxSubmit").remove(); 
   	   			//$("#form").submit();
   	   			$("#form").ajaxSubmit(options);
    			return false;
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
    	   		
    	   		$("#form").ajaxSubmit();
    	        return true;
            }
        });
		openHelp(this,"האם הנך מאשר את מחיקת חבר/ת הועדה?");
    	return false;
	});	
	
	$("button.delete").click(function(e){
		e.preventDefault();
	   	$("#genericDialog").dialog({ modal: true });
    	$("#genericDialog").dialog('option', 'buttons', {
            "לא" : function() {
                $(this).dialog("close");
                return false;
               },
            "כן" : function() {
                $(this).dialog("close");
        		var options = { 
        		   		success:    function() { 
        		   		   	window.location.reload(); 
        		    	} 
        			}; 

    	      	$("#form").append("<input type=\"hidden\" name=\"action\" value=\"delete\"/>");
    			$("#form").append("<input type=\"hidden\" name=\"showMessage\" value=\"deleted\"/>");
    			$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" id=\"ajaxSubmit\" value=\"true\"/>");
     	   		$("#form").ajaxSubmit(options);
    			return false;
               }
            });
    	openHelp('','האם הנך מאשר/ת את ביטול הבקשה?');
        return false;
   	});	
	
	$("button.submitForGrading").click(function(){
		
		if("${command.versionId}">0){
			$("#genericDialog").dialog('option', 'buttons', {
	        "לא" : function() {
	            $(this).dialog("close");
	            return false;
	           },
	        "כן" : function() {
	            $(this).dialog("close");
	            var errors = checkErrors();//validating fields
	    		if (errors){
	    		   	$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
	    			$("#genericDialog").dialog({ modal: false });
	    			$("#genericDialog").dialog({ height: 200 });
	    			$("#genericDialog").dialog({ width: 400 });
	    			openHelp('','ההצעה לא הוגשה: נא להתייחס להערות באדום ולהגיש שוב');
	    			return false;
	    		}
	    		else{
	    	    	$("#genericDialog").dialog('option', 'buttons', {
	    	            "לא" : function() {
	    	                $(this).dialog("close");
	    	                return false;
	    	               },
	    	            "כן" : function() {
	    	                $(this).dialog("close");
	    	    			var options = { 
	    	    		   			success:    function() { 
	    	    		   			   	window.location.reload(); 
	    	    		    		} 
	    	    				}; 
	    	    				$("#form").append("<input type=\"hidden\" name=\"action\" value=\"submitForGrading\"/>");
	    	    				$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" id=\"ajaxSubmit\" value=\"true\"/>");
	    	    				$("#form").append("<input type=\"hidden\" name=\"showMessage\" value=\"submitted\"/>");
	    	    				$("#form").ajaxSubmit(options);
	    	    	    		return false;
	    	               }
	    	         });
	    			openHelp('','האם ברצונך להגיש את הבקשה?');
	    	        return false;
	    		}
	          }
	   		});
			$("#genericDialog").dialog({ modal: false });
			$("#genericDialog").dialog({ height: 200 });
			$("#genericDialog").dialog({ width: 400 });
			var text ='אתה נמצא בגרסה ישנה של הבקשה. בלחיצה על כן תוגש הבקשה עם הנתונים של גרסה זו והיא תהפוך לגירסה העדכנית. האם להמשיך?';
			openHelp('',text);
			return false;
		}
		else{//not version
        	var errors = checkErrors();//validating fields
			if (errors){
		   		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
				$("#genericDialog").dialog({ modal: false });
				$("#genericDialog").dialog({ height: 200 });
				$("#genericDialog").dialog({ width: 400 });
				openHelp('','ההצעה לא הוגשה: נא להתייחס להערות באדום ולהגיש שוב');
				return false;
			}
			else{
	    	   $("#genericDialog").dialog('option', 'buttons', {
	           "לא" : function() {
	                $(this).dialog("close");
	                return false;
	               },
	           "כן" : function() {
	                $(this).dialog("close");
	    			var options = { 
	    		   		success:    function() { 
	    		   		window.location.reload(); 
	    		        } 
	    		    }; 
	    			$("#form").append("<input type=\"hidden\" name=\"action\" value=\"submitForGrading\"/>");
	    			$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" id=\"ajaxSubmit\" value=\"true\"/>");
	    			$("#form").append("<input type=\"hidden\" name=\"showMessage\" value=\"submitted\"/>");
	    			$("#form").ajaxSubmit(options);
	    	    	return false;
	            }
	           });
			   openHelp('','האם ברצונך להגיש את הבקשה?');
	           return false;
			 }//else no errors
		}//else not version

    });
	
	
	$("button.submit").click(function(){
		if("${command.versionId}">0){
			$("#genericDialog").dialog('option', 'buttons', {
	        "לא" : function() {
	            $(this).dialog("close");
	            return false;
	           },
	        "כן" : function() {
	            $(this).dialog("close");
	    		var options = { 
	    		       	url:       'editConferenceProposal.html' ,        
	    		       	type:      'POST',
	    		   		success:    function() { 
	    		   		   	window.location.reload(); 
	    		    	} 
	    			};
	    			$("#form").append("<input type=\"hidden\" class=\"test\" name=\"ajaxSubmit\" id=\"ajaxSubmit\" value=\"true\"/>");
	    			$("#form").append("<input type=\"hidden\" class=\"test\" name=\"showMessage\" value=\"saved\"/>");
	    			$("#form").ajaxSubmit(options);
	    	    	return false;
	          }
	   		});
			$("#genericDialog").dialog({ modal: false });
			$("#genericDialog").dialog({ height: 200 });
			$("#genericDialog").dialog({ width: 400 });
			var text ='אתה נמצא בגרסה ישנה של הבקשה. בלחיצה על כן תשמר הבקשה עם הנתונים של גרסה זו והיא תהפוך לגירסה העדכנית. האם להמשיך?';
			openHelp('',text);
			return false;
		}
		var options = { 
		       	url:       'editConferenceProposal.html' ,        
		       	type:      'POST',
		   		success:    function() { 
		   		   	window.location.reload(); 
		    	} 
		};
		$("#form").append("<input type=\"hidden\" class=\"test\" name=\"ajaxSubmit\" id=\"ajaxSubmit\" value=\"true\"/>");
		$("#form").append("<input type=\"hidden\" class=\"test\" name=\"showMessage\" value=\"saved\"/>");
		$("#form").ajaxSubmit(options);
	    return false;

    });
	
	
    
     $("#genericDialog").dialog({
           autoOpen: false,
           show: 'fade',
           hide: 'fade',
           modal: true,
           open: function() { $(".ui-dialog").css("box-shadow","#000 5px 5px 5px");}
     });
		<c:if test="${userMessage!=null}">
		var userMessage = "${userMessage}";
		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		$("#genericDialog").dialog({ modal: false });
		$("#genericDialog").dialog({ height: 200 });
		$("#genericDialog").dialog({ width: 400 });
		openHelp('',userMessage);
		</c:if>
    
   $(".ui-dialog-titlebar").hide();
   
   $("#dialogInitiatingBody").click(function(e) {
	$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
	$("#genericDialog").dialog({ modal: false });
	$("#genericDialog").dialog({ height: 200 });
	$("#genericDialog").dialog({ width: 400 });
    openHelp("#dialogInitiatingBody",' רשום את שמו של הגוף שיוזם את הכנס. גוף זה יכול שיהיה יחידה/מרכז בתוך האוניברסיטה או ישות מוגדרת מחוץ לאוניברסיטה או "קבוצת חוקרים" או "מגיש הבקשה".');
    return false;
   });
   
   $("#dialogStartEndDate").click(function(e) {
		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		$("#genericDialog").dialog({ modal: false });
		$("#genericDialog").dialog({ height: 200 });
		$("#genericDialog").dialog({ width: 400 });
	    openHelp("#dialogInitiatingBody",'לחיצה על השדה תפתח לוח שנה שיאפשר בחירת תאריך');
	    return false;
	   });

   $("#dialogDescription").click(function(e) {
		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		$("#genericDialog").dialog({ modal: false });
		$("#genericDialog").dialog({ height: 200 });
		$("#genericDialog").dialog({ width: 400 });
	    openHelp("#dialogDescription",'הקלד או הדבק עד 500 תווים');
	    return false;
	});
   $("#dialogTotalCost").click(function(e) {
		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		$("#genericDialog").dialog({ modal: false });
		$("#genericDialog").dialog({ height: 200 });
		$("#genericDialog").dialog({ width: 400 });
	    openHelp("#dialogTotalCost",'יש לרשום את סה"כ ההוצאות הצפויות לארגון הכנס ולביצועו. אין לכלול את הסכום המבוקש מהוועדה. הערך יהיה בדולר ע"פ השער היציג בעת מילוי הטופס. ');
	    return false;
	});   
   $("#dialogCommittee").click(function(e) {
		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		$("#genericDialog").dialog({ modal: false });
		$("#genericDialog").dialog({ height: 370 });
		$("#genericDialog").dialog({ width: 400 });
		var text ='תוכן חלק זה נועד להמחיש את האיכות האקדמית של הכנס, את רמת ארגונו ואת מידת "בין-לאומיותו".</br>';
		text+='	ועדה מדעית = מי שעוסק בקביעת התוכן האקדמי</br>';
		text+='ועדה מארגנת = מי שעוסק בקביעת מתכונת ביצוע הכנס, מרציו ומשתתפיו </br>';
		text+='מוסד = המוסד/ארגון אליו שייך הנקוב ברשומה</br>';
		text+='נא מלא הפרטים עד 10 רשומות</br></br>';
		text+='לחיצה על ה X מאפשרת מחיקת הרשומה';
	    openHelp("#dialogCommittee",text);
	    return false;
	   });


   $("#dialogParticipants").click(function(e) {
		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		$("#genericDialog").dialog({ modal: false });
		$("#genericDialog").dialog({ height: 300 });
		$("#genericDialog").dialog({ width: 400 });
	    openHelp("#dialogParticipants",' יש למלא את הערכים בתאים האפורים. הסיכומים יחושבו אוטומטית.</br>"מרצים"=מרצים+מנחים+פאנליסטים</br>"מוזמנים"=אלה המוזמנים כמאזינים בלבד</br>"קהל נוסף"=בני זוג+מלווים ותקשורת+אחרים');
	    return false;
	});
   $("#dialogCompany").click(function(e) {
		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		$("#genericDialog").dialog({ modal: false });
		$("#genericDialog").dialog({ height: 200 });
		$("#genericDialog").dialog({ width: 400 });
		  openHelp("#dialogCompany","הקלד את שם החברה וצרף את ההסכם עמה. אם עדיין לא נחתם הסכם עם החברה הקלד 'לא נבחרה' וצרף קובץ בו מתוארות המשימות שתדרשנה מהחברה.");
	    return false;
	});
   $("#dialogProposer").click(function(e) {
		  $("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		  $("#genericDialog").dialog({ modal: false });
			$("#genericDialog").dialog({ height: 200 });
			$("#genericDialog").dialog({ width: 400 });
		  openHelp("#dialogProposer","המבקש מייצג את מארגני הכנס בהליך בקשה זה");
		  return false;
	   }); 
   $("#dialogSupportSum").click(function(e) {
		  $("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		  $("#genericDialog").dialog({ modal: false });
			$("#genericDialog").dialog({ height: 200 });
			$("#genericDialog").dialog({ width: 400 });
		  openHelp("#dialogSupportSum","לפי השער היציג בעת ההגשה");
		  return false;
	   }); 
   $("#dialogGuestsAttach").click(function(e) {
		  $("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		  $("#genericDialog").dialog({ modal: false });
			$("#genericDialog").dialog({ height: 200 });
			$("#genericDialog").dialog({ width: 400 });
		  openHelp("#dialogGuestsAttach",'יש לצרף רשימה מפורטת של המשתתפים מהארץ ומחו"ל,תוך תיאור מעמדם המדעי, וציון המוסדות והארצות מהם הם באים.');
		  return false;
	   });   
   $("#dialogProgramAttach").click(function(e) {
		  $("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		  $("#genericDialog").dialog({ modal: false });
			$("#genericDialog").dialog({ height: 200 });
			$("#genericDialog").dialog({ width: 400 });
		  openHelp("#dialogProgramAttach","יש לצרף את תוכנית הכנס (ובמידת ההכרח רק תוכנית ראשונית)");
		  return false;
	   });   
   $("#dialogInternational").click(function(e) {
		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		$("#genericDialog").dialog({ modal: false });
		$("#genericDialog").dialog({ height: 200 });
		$("#genericDialog").dialog({ width: 400 });
		var texts='<p>';
		texts='חשיבות ההכרה בכנס כבין לאומי היא בכך שהיא מאפשרת לחייב את תקציב הכנס בתקורה מופחתת (5%).';
		texts+='</p>';	    
	    openHelp("#dialogInternational",texts);
	    return false;
	   });     
   $("#dialogBudget").click(function(e) {
		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		$("#genericDialog").dialog({ modal: false });
		$("#genericDialog").dialog({ height: 600 });
		$("#genericDialog").dialog({ width: 600 });
		var texts='<p><ol>';
   		texts +='<li>התקציב אמור לכסות את כל ההוצאות המתוכננות הנדרשות לביצוע הכנס, ברמה מכובדת אך, <u>כיאה למוסד ציבורי</u>.</li>';
   		texts +='<li>ועדת הכנסים מעודדת שיתוף של תלמידי מוסמך ודוקטוראט, בכנסים והיא תשקול בחיוב תמיכה בכנס שתקציבו כולל סעיפים המפרטים הוצאות הקשורות להשתתפות כזו.</li>';
   		texts +='<li>על פי תקנות מס הכנסה <u>חל חיוב במס הכנסה על כיבוד</u> המוגש במסגרת הכנס. התשלומים למס הכנסה, לפי הדין, יהיו <u>על חשבון תקציב הכנס</u> (בין אם הכיבוד הוזמן מגורם פנימי או מגורם חיצוני). כיום, שיעורי המס הם כדלהלן:</br>';
   		texts +=' - על כיבוד קל – לכל מנה, בסכום של עד 27 ₪ - 18% (לא קשור למע"מ).</br>';
   		texts +='- על כיבוד כגון ארוחה מלאה במזנון או בישיבה, לכל מנה, בסכום העולה על 27 ₪ לאדם – 90%.</br>';
   		texts +='על מארגני הכנס, לברר (באגף הכספים), בעת תכנון הכנס, מהו השיעור המעודכן.</br>';
   		texts +='פטור מתשלום מס הכנסה יחול במקרים אלה:</br>';
  		texts +='- כיבוד של אורחים מחו"ל ומארחיהם, בתנאי שהאירוח הוא במסגרת התפקיד.</br>';
   		texts +='- כיבוד משתתפים בכנס בינלאומי – בתנאי ששליש מהם מחו"ל.</br>';
   		texts +='- כאשר משתתפי הכנס משלמים דמי השתתפות ומצויין בפירוש שהתשלום כולל כיבוד.</br>'; 
   		texts +='לצורך הסדרת הפטור, יש לצרף לחשבונית המס רשימה מפורטת של המשתתפים, בציון המוסדות והארצות מהם הם באים ולציין את המספר הכולל של המשתתפים.</li>';
   		texts +='<li>יש לתכנן את הכנס כך <u>שההוצאות הצפויות תכוסנה ע"י ההכנסות הוודאיות</u>.</li>';
		texts+='</ol></p>';	    
	    openHelp('',texts);
	    return false;
	   });
   
   
    
   $("#dialogRooms").click(function(e) {
		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		$("#genericDialog").dialog({ modal: false });
		$("#genericDialog").dialog({ height: 200 });
		$("#genericDialog").dialog({ width: 400 });
		var texts='<p>';
		texts='החדרים/אולמות שועדת הכנסים מאשרת אינם בבית בלגיה, בית מאירסדורף ובית צרפת שהנם גופים מסחריים עצמאיים.';
		texts+='</p>';	    
	    openHelp("#dialogRooms",texts);
	    return false;
	   });
   $("#dialogRooms2").click(function(e) {
		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		$("#genericDialog").dialog({ modal: false });
		$("#genericDialog").dialog({ height: 200 });
		$("#genericDialog").dialog({ width: 400 });
		var texts='<p>';
		texts='החוקר אחראי לתיאום החדר/אולם. אישור הוועדה אינו מהווה התחייבות בנוגע לחדר/אולם המסוים.';
		texts+='</p>';	    
	    openHelp("#dialogRooms2",texts);
	    return false;
	   });
   $("#dialogRoomsNopay").click(function(e) {
		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		$("#genericDialog").dialog({ modal: false });
		$("#genericDialog").dialog({ height: 300 });
		$("#genericDialog").dialog({ width: 600 });
		var texts='<p>';
		texts='סימון התיבה יציין בקשה לפטור מתשלום עבור אולם או חדר סמינרים של האוניברסיטה, המחויב בדרך כלל בדמי שימוש.</br>';
		texts+='החדרים/אולמות שועדת הכנסים מאשרת אינם בבית בלגיה, בית מאירסדורף ובית צרפת שהינם גופים מסחריים עצמאיים.'
		texts+='</p>';	    
	    openHelp("#dialogRoomsNopay",texts);
	    return false;
	   }); 
   $("#dialogRemarks").click(function(e) {
		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		$("#genericDialog").dialog({ modal: false });
		$("#genericDialog").dialog({ height: 200 });
		$("#genericDialog").dialog({ width: 400 });
		var texts='<p>';
		texts='בשדה זה ניתן להקליד הערות העשויות להיות רלוונטיות להחלטות הוועדה, או הבהרות לגבי כל אחד מהשדות שבטופס.';
		texts+='</p>';	    
	    openHelp("#dialogRemarks",texts);
	    return false;
	   });   
   $("#dialogApprover").click(function(e) {
		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		$("#genericDialog").dialog({ modal: false });
		$("#genericDialog").dialog({ height: 200 });
		$("#genericDialog").dialog({ width: 400 });
		var texts='<p>';
		texts='בחר את הדיקן שיחווה את דעתו בפני הוועדה, בנוגע לבקשה.';
		texts+='</p>';	    
	    openHelp("#dialogApprover",texts);
	    return false;
	   });  
   
   $("#dialogVersions").click(function(e) {
		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		$("#genericDialog").dialog({ modal: false });
		$("#genericDialog").dialog({ height: 300 });
		$("#genericDialog").dialog({ width: 500 });
		var texts='<p>';
		texts+='כל שמירה של הטופס, כולל שמירות אוטומטיות שמתבצעות בעת עדכון ערכים בשדות הטופס, שומרת גרסה של הטופס.</br>';
		texts+='ניתן לדפדף ולצפות בגרסאות הקודמות של הטופס. </br>באם תתבצע שמירה או הגשה מגרסה ישנה היא תדרוס את הגרסה העדכנית.</br>';
		texts+='</p>';	    
	    openHelp("#dialogVersions",texts);
	    return false;
	   });
   
    $("#genericDialog").click(function(e){
    	$("#genericDialog").dialog("close");
    });    
    
    $(window).scroll(function() {
    	if (fieldname=="") 
    		$("#genericDialog").dialog("option", "position", "center");
    }); 
    

   
});

function textlimiter(){
	var tex = document.form.description.value;
	var len = tex.length;
	if(len > 500){
	    tex = tex.substring(0,500);
	    document.form.description.value =tex;
		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		$("#genericDialog").dialog({ modal: false });
		$("#genericDialog").dialog({ height: 200 });
		$("#genericDialog").dialog({ width: 400 });
		openHelp("","שדה התוכן העיוני מוגבל ל 500 תווים");
	}
    return false;
}

var fieldname=""; 
function openHelp(name,mytext){
	    fieldname=name;
	 	if(fieldname=="")
	    	$("#genericDialog").dialog("option", "position", "center");
	    else
	 		$('#genericDialog').dialog({position: { my: 'top', at: 'top', of: $(name)} });
	 	
	    $("#genericDialog").html(mytext).dialog("open");
} 


function hideExtraCommittee(trCssClass){
	var emptyRowsCounter = 0;
	$('tr.'+trCssClass).each(function(){
		var row = $(this);
		var allInputsEmpty = true;
		row.find('input').each(function(){
			if ($(this).val()!=""){
				row.show();
				row.find('button.fromAssosiateAttach').show();
				row.find('button.fromExternalAttach').show();
				row.find('button.fromAdmitanceFeeAttach').show();
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


function calcFee(feeType){
	var totalFee=0;
	var numberRegex=/^[+-]?\d+(\.\d+)?([eE][+-]?d+)?$/;
	for (var i=0;i<10;i++){
		var fieldName = feeType + '[' + i + ']' + ".sum";
		if(document.getElementById(fieldName)!=null && numberRegex.test(document.getElementById(fieldName).value)){
			totalFee +=Number(document.getElementById(fieldName).value);
		}
	}
	var totalname="#" + feeType + "Count";
	$(totalname).html(totalFee);
}

function calcTotalFee(){
	var totalIncome=0;
	var numberRegex=/^[+-]?\d+(\.\d+)?([eE][+-]?d+)?$/;
	if(numberRegex.test($("#fromExternalCount").text()))
		totalIncome=totalIncome + Number($("#fromExternalCount").text());
	if(numberRegex.test($("#fromAssosiateCount").text()))
		totalIncome+= Number($("#fromAssosiateCount").text());
	if(numberRegex.test($("#fromAdmitanceFeeCount").text()))
		totalIncome+=Number($("#fromAdmitanceFeeCount").text());
	$("#fromAllFeeCount").html(totalIncome);
	calcDiff();
}

function calcDiff(){
	var expenses = 0;
	if($('#totalCost').val()!=''){
		expenses = Number($('#totalCost').val());
		incomes =  Number($("#fromAllFeeCount").html());
		diff = incomes-expenses;
		var text="";
		if (diff<0){
			diff=Math.abs(diff);
			text = "<font color='red'>" + diff + "&nbsp;&nbsp;סכום ההכנסות המתוכננות קטן מסכום ההוצאות המתוכננות</font>";
		}
		else
			text= diff;
		$("#sumDifference").html(text);
	}
}

var totalParticipantsCounter=0;
function calcParticipants(){
	var localCount=0;
	var abroadCount=0;
	var guestCount=0;
	var lecturersCount=0;
	var otherCount=0;
	var countRegex = /^\d+$/;
	if(countRegex.test(document.form.foreignLecturers.value)){
		abroadCount+=Number(document.form.foreignLecturers.value);
		lecturersCount+=Number(document.form.foreignLecturers.value);
	}
	if(countRegex.test(document.form.foreignGuests.value)){
		abroadCount+=Number(document.form.foreignGuests.value);
		guestCount+=Number(document.form.foreignGuests.value);
	}
	if(countRegex.test(document.form.audienceLecturers.value)){
		abroadCount+=Number(document.form.audienceLecturers.value);
		otherCount+=Number(document.form.audienceLecturers.value);
	}
	if(countRegex.test(document.form.localLecturers.value)){
		localCount+=Number(document.form.localLecturers.value);
		lecturersCount+=Number(document.form.localLecturers.value);
	}
	if(countRegex.test(document.form.localGuests.value)){
		localCount+=Number(document.form.localGuests.value);
		guestCount+=Number(document.form.localGuests.value);
	}
	if(countRegex.test(document.form.audienceGuests.value)){
		localCount+=Number(document.form.audienceGuests.value);
		otherCount+=Number(document.form.audienceGuests.value);
	}
	$("#abroadCount").html(abroadCount);
	$("#localCount").html(localCount);
	$("#lecturersCount").html(lecturersCount);
	$("#guestCount").html(guestCount);
	$("#otherCount").html(otherCount);
	totalParticipantsCounter = abroadCount + localCount;
	$("#totalCount").html(abroadCount + localCount);
	
}
function checkErrors(){
	var errors=false;
	if(!$("#acceptTerms").attr('checked') && !"${admin}"){
		errors = true;
		$("#erroracceptTerms").html('<font color="red">יש לאשר קבלת תנאי ההגשה (לסמן את תיבת הסימון )<font color="red"><br>');
	}
	else{
		$("#erroracceptTerms").html('');
	}
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
	if($("#location").val()=='0'){
		errors = true;
		$("#errordetails").html('<font color="red">יש לבחור מיקום<font color="red"><br>');
	}
	else{
		$("#errordetails").html('');
	}
	if($("#description").val()==''){
		errors = true;
		$("#errordescription").html('<font color="red">יש למלא שדה התוכן העיוני של הכנס וחשיבותו לתחום<font color="red"><br>');
	}
	else{
		$("#errordescription").html('');
	}
	var numberRegex=/^[+-]?\d+(\.\d+)?([eE][+-]?d+)?$/;
	var countRegex=/^\d+$/;
	var emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
	var phoneRegex = /^[\d]{2,3}-[\d]{7}$/;		
	if($("#totalCost").val()=='0'){
		errors = true;
		$("#errortotalcost").html('<font color="red">יש להכניס ערך מספרי לשדה סכום<font color="red"><br>');
	}
	else{
		$("#errortotalcost").html('');
	}
	if($("#supportSum").val()=='0'){
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
	/*if($("#organizingCompanyEmail").val()!='' && !emailRegex.test($("#organizingCompanyEmail").val())){
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
	}*/
	if($('.organizingContactPart').is(":visible") && $('#contactPerson').val()==''){
		errors = true;
		$("#errorcontactPersonName").html('<font color="red">יש להזין שם איש קשר במידה והארגון המנהלתי של הכנס ייערך ע"י איש קשר<font color="red"><br>');
	}
	else{
		$("#errorcontactPersonName").html('');
	}
	if($("#contactPersonPhone").val()!='' && !phoneRegex.test($("#contactPersonPhone").val())){
		errors = true;
		$("#errorcontactPersonPhone").html('<font color="red">יש להזין מספר טלפון איש קשר תקני<font color="red"><br>');
	}
	else{
		$("#errorcontactPersonPhone").html('');
	}
	return errors;
}

</script>