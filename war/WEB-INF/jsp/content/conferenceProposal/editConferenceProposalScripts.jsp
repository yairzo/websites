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
	calcFee("fromExternal");
	calcFee("fromAssosiate");
	calcFee("fromAdmitanceFee");
	
	calcParticipants();
	
	$(".calcSum").change(function(e) {
		calcParticipants();
	 });   
	
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
	
	
	$("#startConfDate").datepicker({ dateFormat: 'dd/mm/yy', onSelect: function(){
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
		<c:if test="${command.versionId > 0}">
			return false;
		</c:if>
		var options = {
	       	 	url:       'editConferenceProposal.html' ,        
	       	 	type:      'POST'
     	};
   		
   		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" id=\"ajaxSubmit\" value=\"true\"/>");
	    $('#form').ajaxSubmit(options);
		var elementClass = $(this).attr('class');
		if (elementClass.indexOf("scientificCommittee")!=-1)
	    	hideExtraCommittee("scientificCommittee");
		if (elementClass.indexOf("operationalCommittee")!=-1)
			hideExtraCommittee("operationalCommittee");
		if (elementClass.indexOf("admitanceFee")!=-1){
			hideExtraCommittee("admitanceFee");
			calcFee("fromAdmitanceFee");
		}
		if (elementClass.indexOf("assosiate")!=-1){
			hideExtraCommittee("assosiate");
			calcFee("fromAssosiate");
		}
		if (elementClass.indexOf("external")!=-1){
			hideExtraCommittee("external");
			calcFee("fromExternal");
		}

	}, {delay: 2000});
	
	$('form').find('select').change(function(){
		<c:if test="${command.versionId > 0}">
			return false;
		</c:if>
		
		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" id=\"ajaxSubmit\" value=\"true\"/>");
	    $('#form').ajaxSubmit();
	});
	
	$('form').find('textarea').autoSave(function(){
		<c:if test="${command.versionId > 0}">
			return false;
		</c:if>
		
		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" id=\"ajaxSubmit\" value=\"true\"/>");
	    $('#form').ajaxSubmit();
	});
	
	$('#guestsAttach').change(function(event){
		<c:if test="${command.versionId > 0}">
			return false;
		</c:if>
		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" id=\"ajaxSubmit\" value=\"true\"/>");
		$('#form').ajaxSubmit();
		$('#guestsAttachDiv').html("<img src='image/attach.jpg'/><a href='fileViewer?conferenceProposalId=${command.id}&attachFile=guestsAttach&attachmentId=1' target='_blank'>רשימת מוזמנים</a>");
		
	});	
	
	$('#programAttach').change(function(){
		<c:if test="${command.versionId > 0}">
			return false;
		</c:if>
		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" id=\"ajaxSubmit\" value=\"true\"/>");
		$('#form').ajaxSubmit();
		$('#programAttachDiv').html("<img src='image/attach.jpg'/><a href='fileViewer?conferenceProposalId=${command.id}&attachFile=programAttach&attachmentId=1' target='_blank'>תוכנית הכנס</a>");		
	});		
	$('#financialAttach').change(function(){
		<c:if test="${command.versionId > 0}">
			return false;
		</c:if>
		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" id=\"ajaxSubmit\" value=\"true\"/>");
		$('#form').ajaxSubmit();
		$('#financialAttachDiv').html("<img src='image/attach.jpg'/><a href='fileViewer?conferenceProposalId=${command.id}&attachFile=financialAttach&attachmentId=1' target='_blank'>תוכנית תקציבית</a>");
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
    	   		
    	   		$("#form").ajaxSubmit();
    	        return true;
            }
        });
		openHelp(this,"האם הנך מאשר את מחיקת הועדה?");
    	return false;
	});	
	
	$("button.submitForGrading").click(function(){
		var errors=false;
		if(!$("#acceptTerms").attr('checked')){
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
		if($("#totalCost").val()=='0.0' || !numberRegex.test($("#totalCost").val())){
			errors = true;
			$("#errortotalcost").html('<font color="red">יש להכניס ערך מספרי לשדה סכום<font color="red"><br>');
		}
		else{
			$("#errortotalcost").html('');
		}
		if($("#supportSum").val()=='0.0' || !numberRegex.test($("#supportSum").val())){
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
		if (errors){
		   	$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
			$("#genericDialog").dialog({ modal: false });
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
	    	var text = 'ידוע לי שקבלת תמיכה כספית בהוצאות ארגון הכנס ו/או אישור להקצאת אולם ללא תשלום או בתשלום חלקי ';
			text += 'מותנית בפרסום חסות האוניברסיטה בכל פרסומי הכנס ובהגשת מאזן תקציבי מפורט תוך חודשיים ממועד סיום הכנס.<br/> ';
	    	text += 'האם ברצונך להמשיך להגשה?';
			openHelp('',text);
	        return false;
		}
    });
	
	
	$("button.submit").click(function(){
		var options = { 
	   		success:    function() { 
	   		   	window.location.reload(); 
	    	} 
		}; 
		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" id=\"ajaxSubmit\" value=\"true\"/>");
		$("#form").append("<input type=\"hidden\" name=\"showMessage\" value=\"saved\"/>");
		$("#form").ajaxSubmit(options);
    	return false;
    });
	
	
    
     $("#genericDialog").dialog({
           autoOpen: false,
           show: 'fade',
           hide: 'fade',
           modal: true,
           width: 400,
           open: function() { $(".ui-dialog").css("box-shadow","#000 5px 5px 5px");}
     });
		<c:if test="${userMessage!=null}">
		var userMessage = "${userMessage}";
		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		$("#genericDialog").dialog({ modal: false });
		openHelp('',userMessage);
		</c:if>
    
   $(".ui-dialog-titlebar").hide();
   $("#dialogInitiatingBody").click(function(e) {
	$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
	$("#genericDialog").dialog({ modal: false });
    openHelp("#dialogInitiatingBody","רשום את שמו של הגוף שיוזם את הכנס. גוף זה יכול שיהיה יחידה/מרכז בתוך האוניברסיטה או ישות מוגדרת מחוץ לאוניברסיטה");
    return false;
   });
     
   $("#dialogInitiatingBodyRole").click(function(e) {
	  $("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
	  $("#genericDialog").dialog({ modal: false });
	  openHelp("#dialogInitiatingBodyRole","תפקיד בגוף היוזם את הכנס");
	  return false;
   }); 
   $("#dialogProposer").click(function(e) {
		  $("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		  $("#genericDialog").dialog({ modal: false });
		  openHelp("#dialogProposer","המבקש מייצג את מארגני הכנס בהליך בקשה זה");
		  return false;
	   }); 
   $("#dialogSupportSum").click(function(e) {
		  $("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		  $("#genericDialog").dialog({ modal: false });
		  openHelp("#dialogSupportSum","לפי השער היציג בעת ההגשה");
		  return false;
	   }); 
   $("#dialogGuestsAttach").click(function(e) {
		  $("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		  $("#genericDialog").dialog({ modal: false });
		  openHelp("#dialogGuestsAttach",'יש לצרף רשימה מפורטת של המשתתפים מהארץ ומחו"ל,תוך תיאור מעמדם המדעי, ושל המוסדות והארצות מהם הם באים.');
		  return false;
	   });   
   $("#dialogProgramAttach").click(function(e) {
		  $("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		  $("#genericDialog").dialog({ modal: false });
		  openHelp("#dialogProgramAttach","יש לצרף את תוכנית הכנס (ובמידת ההכרח רק תוכנית ראשונית)");
		  return false;
	   });   
   $("#dialogBudget").click(function(e) {
		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		$("#genericDialog").dialog({ modal: false });
		var texts='<p>';
		texts='על פי תקנות מס הכנסה <b>חל חיוב מס הכנסה על כיבוד</b>.';
		texts +='התשלומים למס הכנסה יהיו על חשבון תקציב הכנס (בין אם הכיבוד הוזמן מגורם פנימי או מגורם חיצוני) וכלהלן:<br/>';
		texts += '<b>18% - על כיבוד קל</b> (לא קשור למע"מ) - לכל מנה <b>בסכום של עד 27 ש"ח</b>.<br/>';
		texts += '<b>90% - על כיבוד כגון ארוחה מלאה במזנון או בישיבה</b> - לכל מנה בסכום <b>העולה על 27 ש"ח לאדם</b>.<br/>';
		texts += 'פטור מתשלום מס הכנסה על כיבוד יחול במקרים שלהן:<br/>';
		texts += '1. כיבוד של אורחים מחו"ל ומארחיהם <b>בתנאי שהאירוח הוא במסגרת התפקיד</b>.<br/>';
		texts += '2. כיבוד משתתפים בכנס בינלאומי <b>בתנאי ששליש מהם מחו"ל</b> - לצורך הסדרת הפטור, <br/>';
		texts += '	יש לצרף לחשבונית המס רשימה מפורטת של שמות המשתתפים בציון המוסדות והארצות מהם הם באים ולציין את המספר הכולל של המשתתפים.<br/>';
		texts += '3. כאשר משתתפי הכנס משלמים דמי השתתפות ומצוין במפורש שהתשלום כולל כיבוד.<br/>';
		texts += '<b>ועדת הכנסים מעודדת שיתוף דור ההמשך של החוקרים (תלמידי מוסמך ודוקטורט) <br/>';
		texts += 'בכנסים ותשקול בחיוב בקשה לתמיכה בכנס הכוללת סעיפים תקציביים המפרטים הוצאות הקשורות להשתתפות זו.<b>'
		texts+='</p>';	    
	    openHelp("#dialogBudget",texts);
	    return false;
	   });
   $("#dialogRooms").click(function(e) {
		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		$("#genericDialog").dialog({ modal: false });
		var texts='<p>';
		texts='החדרים/אולמות שועדת הכנסים מאשרת אינם בבית בלגיה, בית מאירסדורף ובית צרפת שהנם גופים מסחריים עצמאיים.';
		texts+='</p>';	    
	    openHelp("#dialogRooms",texts);
	    return false;
	   });
   $("#dialogRooms2").click(function(e) {
		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		$("#genericDialog").dialog({ modal: false });
		var texts='<p>';
		texts='החוקר אחראי לתיאום החדר/אולם. אישור הוועדה אינו מהווה התחייבות בנוגע לחדר/אולם המסוים.';
		texts+='</p>';	    
	    openHelp("#dialogRooms2",texts);
	    return false;
	   });
    
    $("#form,#genericDialog").click(function(e){
    	$("#genericDialog").dialog("close");
    });    
    
    $(window).scroll(function() {
    	if (fieldname=="") 
    		$("#genericDialog").dialog("option", "position", "center");
    }); 
    

   
});



var fieldname=""; 
function openHelp(name,mytext){
		if(name=="#dialogBudget")
			$("#genericDialog").dialog({ width: 600 });
		else
			$("#genericDialog").dialog({ width: 400 });
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
		if(numberRegex.test(document.getElementById(fieldName).value)){
			totalFee +=parseInt(document.getElementById(fieldName).value);
		}
	}
	alert(feeType +" total:" + totalFee);
}

function calcParticipants(){
	var localCount=0;
	var abroadCount=0;
	var guestCount=0;
	var lecturersCount=0;
	var otherCount=0;
	var countRegex = /^\d+$/;
	if(countRegex.test(document.form.foreignLecturers.value)){
		abroadCount+=parseInt(document.form.foreignLecturers.value);
		lecturersCount+=parseInt(document.form.foreignLecturers.value);
	}
	if(countRegex.test(document.form.foreignGuests.value)){
		abroadCount+=parseInt(document.form.foreignGuests.value);
		guestCount+=parseInt(document.form.foreignGuests.value);
	}
	if(countRegex.test(document.form.audienceLecturers.value)){
		abroadCount+=parseInt(document.form.audienceLecturers.value);
		otherCount+=parseInt(document.form.audienceLecturers.value);
	}
	if(countRegex.test(document.form.localLecturers.value)){
		localCount+=parseInt(document.form.localLecturers.value);
		lecturersCount+=parseInt(document.form.localLecturers.value);
	}
	if(countRegex.test(document.form.localGuests.value)){
		localCount+=parseInt(document.form.localGuests.value);
		guestCount+=parseInt(document.form.localGuests.value);
	}
	if(countRegex.test(document.form.audienceGuests.value)){
		localCount+=parseInt(document.form.audienceGuests.value);
		otherCount+=parseInt(document.form.audienceGuests.value);
	}
	$("#abroadCount").html(abroadCount);
	$("#localCount").html(localCount);
	$("#lecturersCount").html(lecturersCount);
	$("#guestCount").html(guestCount);
	$("#otherCount").html(otherCount);
	$("#totalCount").html(abroadCount + localCount);

}


</script>