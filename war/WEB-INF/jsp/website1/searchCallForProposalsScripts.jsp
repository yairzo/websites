<%@ page  pageEncoding="UTF-8" %>

		<script src="js/jquery-1.10.2.min.js"></script>
		<script src="js/form.js"></script>
		<script type="text/javascript" src="/iws/js/jquery-ui-1.10.3.custom.js"></script>
<link href="/iws/style/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css">		
<style>
	.ui-autocomplete li {
		list-style-type: none;
	}
.ui-autocomplete { height: 200px; overflow-y: scroll; overflow-x: hidden;}
</style>		
		<script type="text/javascript">

function resetAutocomplete(funds){
	$("#advanced_subject").autocomplete( 
			{ source: 'selectBoxFiller?type=fundsWithId',
			 minLength: 2,
			 messages: {
			        noResults: '',
			        results: function() {}
			    },
			 highlight: true,				 
			 select: function(event, ui) {
				 	//alert(ui.item.label);
					$("#advanced_subject").val(ui.item.label);
					$("#fundId").val(ui.item.id);
			 }
		    }
	);
}


$(document).ready(function() {
	$(document).click(function() {
		$('div.subSubjects').hide();
	});
	
	$('.check .open').on('click',function(e){
		e.stopPropagation();
	});
	
	$('.checkbox_box_sub_rtl, .checkbox_box_sub_ltr, .check label').click(function(e){
		e.stopPropagation();
	});
	
	
	
	
	$(".viewProposal").click(function(e) {
		e.preventDefault();
		var proposalId=$(this).attr("id");	   		
		$.ajax({
		    url : 'callForProposal.html?id='+proposalId+'&p=1&t=1',
		    type: 'GET',
		    success : handleData
		 })
	});
	
	$(function() {
		$(".popup_placeholder" ).draggable();
	});	
	
	$(document).click(function() {
	    $(".popup_placeholder").hide();
	});
	
	$(".popup_placeholder").click(function(e) {
		e.stopPropagation();
	});
	
	
	$("#advanced_subject").click(function(){
    	$("#advanced_subject").val('');
    	$("#fundId").val("0");    	
    	resetAutocomplete();
	});
	
	
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
            dateformat: 'dd/mm/yy',
            firstDay: 0,
            isRTL: true,
            showMonthAfterYear: false,
            yearSuffix: ''
        };
      	if(${lang.localeId=='iw_IL'}) 
        	$.datepicker.setDefaults($.datepicker.regional['he']);
        else 
        	$.datepicker.setDefaults(); 
     });
	
	$(function() {
		$(".date").datepicker({ dateFormat: 'dd/mm/yy'});	
		$("#advanced_cal_from").click(function(){ $("#advanced_date_from").datepicker("show"); });
		$("#advanced_cal_to").click(function(){ $("#advanced_date_to").datepicker("show"); });
    });	
	
	$('.advanced_submit').click(function(e){
		if($("#fundId").val()=='')
			$("#form").append("<input type=\"hidden\" name=\"fundId\" value=\"0\"/>");
		else
			$("#form").append("<input type=\"hidden\" name=\"fundId\" value=\""+$("#fundId").val() +"\"/>");

		if($("#searchOpen").prop('checked')== true)
			$("#form").append("<input type=\"hidden\" name=\"open\" value=\"false\"/>");
		else
			$("#form").append("<input type=\"hidden\" name=\"open\" value=\"true\"/>");

		if($("#searchExpired").prop('checked')== true)
			$("#form").append("<input type=\"hidden\" name=\"expired\" value=\"false\"/>");
		else
			$("#form").append("<input type=\"hidden\" name=\"expired\" value=\"true\"/>");

		if($("#searchByAllYear").prop('checked')== true)
			$("#form").append("<input type=\"hidden\" name=\"allYear\" value=\"false\"/>");
		else
			$("#form").append("<input type=\"hidden\" name=\"allYear\" value=\"true\"/>");

		if($("#searchByAllSubjects").prop('checked')== true)
			$("#form").append("<input type=\"hidden\" name=\"allSubjects\" value=\"false\"/>");
		else
			$("#form").append("<input type=\"hidden\" name=\"allSubjects\" value=\"true\"/>");

		var ids="";
		$('.selectSubSubject').each(function(){
			if ($(this).attr('check-value') == "true"){
					var id = $(this).attr('class').split(' ')[0];
					id = id.substring(id.indexOf('.') + 1);
					if (ids !="")
						ids = ids + ","
					ids = ids +id;
				}
		});
		$('.subjectsIdsString').remove();
		$('#form').append('<input type=\"hidden\" name=\"subjectsIdsString\" class=\"subjectsIdsString\" value=\"'+ids+'\"/>');
		$('#form').submit();
	});

	
	
	$(".advanced_clear").click(function(){
		$("#advanced_subject").val('');
    	$("#advanced_date_from").val('');
    	$("#advanced_date_to").val('');
       	$("#targetAudience").val(0);
     	$('#targetAudience').change();       	
     	$("#deskId").val(0);
     	$('#deskId').change();       	
     	$("#typeId").val(0);
     	$('#typeId').change();       	
       	$("#searchByAllYear").prop('checked', true);
        $("#searchExpired").prop('checked', true);
       	$("#searchOpen").prop('checked', true);
    	$("#searchByAllSubjects").prop('checked', true);
       	$("#selectAll").prop('checked', true);
    	$("input:checkbox.subject").each(function(){
			$(this).parent().children("span.checkbox").removeClass("checkboxPartial");
			this.checked = true;
			$(this).change();
		});
    	$("input:checkbox.subSubject").each(function(){
			this.checked = true;
			$(this).change();
		});
   	
  		$("#listViewPage").remove();
		$("#orderBy").remove();
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"search\"/>");
		$("#form").submit();
    	return true;
    });	
		
		
	

	$(".openSubSubjects").click(function(e){
		if($(this).parent().children(".subSubjects").css('display')=='none')	{	
			$('div.subSubjects').hide();
			var rowPos = $(this).position();
			bottomTop = rowPos.top+22;
			bottomLeft = rowPos.left;
			
			$(this).parent().children(".subSubjects").addClass('open');
			$(this).parent().children(".open").css({
				top: bottomTop,
	    		left: bottomLeft,
	    	});
			
			$(this).parent().children(".subSubjects").show();
		}
		else{
			$('div.subSubjects').hide();
		}
		
		e.stopPropagation();
		
	});
	
	
	$(".selectSubject").click(function(e){
		$(this).removeClass("checkboxPartial");
		if($(this).attr('check-value') == "false"){
			$(this).siblings(".subSubjects").find(".checkbox_box").each(function(){
				$(this).attr("check-value","true");
				$(this).removeClass("checkboxUncheckedSub");
				$(this).addClass("checkboxCheckedSub");
			});
			$(this).attr('check-value', "true");
			$(this).removeClass("checkboxUnchecked");
			$(this).removeClass("checkboxPartial");
			$(this).addClass("checkboxChecked");
			
		}
		else{
			$(this).siblings(".subSubjects").find(".checkbox_box").each(function(){
				$(this).attr("check-value","false");
				$(this).removeClass("checkboxCheckedSub");
				$(this).addClass("checkboxUncheckedSub");
			});
			$(this).attr('check-value', "false");
			$(this).removeClass("checkboxChecked");
			$(this).removeClass("checkboxPartial");
			$(this).addClass("checkboxUnchecked");
		}
		e.stopPropagation();
	});
	
	
	
	$(".selectSubSubject").click(function(e){
		if($(this).attr('check-value') == "false"){
			$(this).attr('check-value', "true");
			$(this).removeClass("checkboxUncheckedSub");
			$(this).addClass("checkboxCheckedSub");
		}
		else{
			$(this).attr("check-value","false");
			$(this).removeClass("checkboxCheckedSub");
			$(this).addClass("checkboxUncheckedSub");
		}
		
		var num_sub_subjects = $(this).parent().find(".checkbox_box").length;
		var num_checked_sub_subjects = $(this).parent().find(".checkboxCheckedSub").length;
		if (num_checked_sub_subjects == 0){			
			$(this).parent().prev().prev().removeClass("checkboxChecked");
			$(this).parent().prev().prev().removeClass("checkboxPartial");
			$(this).parent().prev().prev().addClass("checkboxUnchecked");
			
		}
		else if (num_checked_sub_subjects < num_sub_subjects){
			$(this).parent().prev().prev().removeClass("checkboxChecked");
			$(this).parent().prev().prev().removeClass("checkboxUnchecked");
			$(this).parent().prev().prev().addClass("checkboxPartial");		
		}
		else{
			$(this).parent().prev().prev().removeClass("checkboxUnchecked");
			$(this).parent().prev().prev().removeClass("checkboxPartial");
			$(this).parent().prev().prev().addClass("checkboxChecked");			
		}		
		e.stopPropagation();
	});
	
	$(".select_all").click(function(){
		if ($(this).attr('check-value') == "false"){
			$('.selectSubSubject').each(function(){
				$(this).attr("check-value", true);
				$(this).removeClass("checkboxUncheckedSub");
				$(this).addClass("checkboxCheckedSub");
			});
			$('.selectSubject').each(function(){
				$(this).attr("check-value", true);
				$(this).removeClass("checkboxUnchecked");
				$(this).removeClass("checkboxPartial");
				$(this).addClass("checkboxChecked");
			});
			$(this).attr("check-value", "true");
			$(this).removeClass("checkboxUnchecked");
			$(this).addClass("checkboxChecked");			
		}
		else{
			$('.selectSubSubject').each(function(){
				$(this).attr("check-value", false);
				$(this).removeClass("checkboxCheckedSub");
				$(this).addClass("checkboxUncheckedSub");
			});
			$('.selectSubject').each(function(){
				$(this).attr("check-value", false);
				$(this).removeClass("checkboxChecked");
				$(this).removeClass("checkboxPartial");
				$(this).addClass("checkboxUnchecked");
			});
			$(this).attr("check-value", "false");
			$(this).removeClass("checkboxChecked");
			$(this).addClass("checkboxUnchecked");
		}
	}); 

});


$(window).load(function(){
	$('.selectSubSubject').each(function(){
		if ($(this).attr("check-value") == "true"){
			$(this).removeClass("checkboxUncheckedSub");
			$(this).addClass("checkboxCheckedSub");
		}
	});
	
 //TODO: complete this
	$('.selectSubject').each(function(){
		var num_sub_subjects = $(this).parent().find(".checkbox_box").length - 1;
		var num_checked_sub_subjects = $(this).parent().find(".checkboxCheckedSub").length;
		if (num_checked_sub_subjects == 0){			
			$(this).removeClass("checkboxChecked");
			$(this).removeClass("checkboxPartial");
			$(this).addClass("checkboxUnchecked");			
		}
		else if (num_checked_sub_subjects > 0 && num_checked_sub_subjects < num_sub_subjects){			
			$(this).removeClass("checkboxChecked");
			$(this).removeClass("checkboxUnchecked");
			$(this).addClass("checkboxPartial");		
		}
		else{
			$(this).removeClass("checkboxUnchecked");
			$(this).removeClass("checkboxPartial");
			$(this).addClass("checkboxChecked");			
		}
	});
	
	$('select').show();
});



function handleData(data) {
	$(".popup_placeholder").html(data);
	$(".popup_placeholder").show();
	var pTop = ($(window).height() - $(".popup_placeholder").height())/2;
	var pLft = ($(window).width() - 600)/2;
	$(".popup_placeholder").css({
    	position: 'fixed',
    	left: pLft,
    	top: pTop
	});
}

</script>