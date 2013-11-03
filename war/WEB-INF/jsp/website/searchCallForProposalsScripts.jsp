<%@ page  pageEncoding="UTF-8" %>

		<script src="js/jquery-1.10.2.min.js"></script>
		<script src="js/form.js"></script>
		<script type="text/javascript" src="/iws/js/jquery-ui-1.10.3.custom.js"></script>
 <link href="/iws/style/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css">	 
<script type="text/javascript" src="js/jquery.form.js"></script>	
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
	

	
	$('.check .open').on('click',function(e){
		e.stopPropagation();
	});
	
	$('.checkbox_box_sub_rtl, .checkbox_box_sub_ltr, .check label').click(function(e){
		e.stopPropagation();
	});
	
	
	$("#deskId").change(function () {
	    if($(this).val() == "0") 
	    	$(this).parent("div.advanced_select_select").css("color","#AFAFAF");	
	    else
	    	$(this).parent("div.advanced_select_select").css("color","#666666");	
	});
	$("#deskId").change();	
	
	$("#typeId").change(function () {
	    if($(this).val() == "0") 
	    	$(this).parent("div.advanced_select_select").css("color","#AFAFAF");	
	    else
	    	$(this).parent("div.advanced_select_select").css("color","#666666");	
	});
	$("#typeId").change();	
	
	$("#targetAudience").change(function () {
	    if($(this).val() == "0") 
	    	$(this).parent("div.advanced_select_select").css("color","#AFAFAF");	
	    else
	    	$(this).parent("div.advanced_select_select").css("color","#666666");	
	});
	$("#targetAudience").change();

	$(".viewProposal").click(function(e) {
		e.preventDefault();
		var proposalId=$(this).attr("id");	   		
		$.ajax({
		    url : 'callForProposal.html?id='+proposalId+'&p=1',
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

		
		var search_open_value = $(".search_open").attr('check-value');
		$("#form").append("<input type=\"hidden\" name=\"open\" value=\"" + search_open_value +"\"/>");
		
		var search_expired_value = $(".search_expired").attr('check-value');
		$("#form").append("<input type=\"hidden\" name=\"expired\" value=\"" + search_expired_value +"\"/>");
		
		var search_by_all_year_value = $(".search_by_all_year").attr('check-value');
		$("#form").append("<input type=\"hidden\" name=\"allYear\" value=\"" + search_by_all_year_value +"\"/>");

		var only_all_subjects_value = $(".only_all_subjects").attr('check-value');
		$("#form").append("<input type=\"hidden\" name=\"allSubjects\" value=\"" + only_all_subjects_value +"\"/>");
		

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
		$('#action').remove();
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
       	//$("#searchByAllYear").prop('checked', true);
		$(".search_by_all_year").attr("check-value", "false");
		$(".search_by_all_year").removeClass("checkboxChecked");
		$(".search_by_all_year").addClass("checkboxUnchecked");
 		$(".search_expired").attr("check-value", "false");
		$(".search_expired").removeClass("checkboxChecked");
		$(".search_expired").addClass("checkboxUnchecked");
       	$(".search_open").attr("check-value", "true");
		$(".search_open").removeClass("checkboxUnchecked");
		$(".search_open").addClass("checkboxChecked");		
		$(".only_all_subjects").attr("check-value", "false");
		$(".only_all_subjects").removeClass("checkboxChecked");
		$(".only_all_subjects").addClass("checkboxUnchecked");
		$(".select_all").attr("check-value", "false");
		$(".select_all").removeClass("checkboxChecked");
		$(".select_all").addClass("checkboxUnchecked");
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
  		$("#listViewPage").remove();
		$("#orderBy").remove();
		$("#form").append("<input type=\"hidden\" name=\"action\" id=\"action\" value=\"cleanSearch\"/>");
		$("#form").ajaxSubmit();
    	return true;
    });	
		
		
	
	$(".selectSubject").click(function(e){
		$(this).removeClass("semi");
		if($(this).attr('check-value') == "false"){
			$(this).siblings(".checkbox_list").find(".checkbox_box").each(function(){
				$(this).attr("check-value","true");
				//$(this).removeClass("checkboxUncheckedSub");
				$(this).addClass("active");
			});
			$(this).attr('check-value', "true");
			//$(this).removeClass("checkboxUnchecked");
			$(this).removeClass("semi");
			$(this).addClass("active");
			
		}
		else{
			$(this).siblings(".checkbox_list").find(".checkbox_box").each(function(){
				$(this).attr("check-value","false");
				$(this).removeClass("active");
				//$(this).addClass("checkboxUncheckedSub");
			});
			$(this).attr('check-value', "false");
			$(this).removeClass("active");
			$(this).removeClass("semi");
			//$(this).addClass("checkboxUnchecked");
		}
		$(".only_all_subjects").attr("check-value", "false");
		$(".only_all_subjects").removeClass("active");
		//$(".only_all_subjects").addClass("checkboxUnchecked");
		$(".only_all_subjects").attr("check-value", "false");
		e.stopPropagation();
	});
	
	
	
	$(".selectSubSubject").click(function(e){
		if($(this).attr('check-value') == "false"){
			$(this).attr('check-value', "true");
			//$(this).removeClass("checkboxUncheckedSub");
			$(this).addClass("active");
		}
		else{
			$(this).attr("check-value","false");
			$(this).removeClass("active");
			//$(this).addClass("checkboxUncheckedSub");
		}
		
		var num_sub_subjects = $(this).parent().find(".checkbox_box").length;
		var num_checked_sub_subjects = $(this).parent().find(".active").length;

		if (num_checked_sub_subjects == 0){			
			$(this).parent().prev().prev().removeClass("active");
			$(this).parent().prev().prev().removeClass("semi");
			//$(this).parent().prev().prev().addClass("checkboxUnchecked");
			$(this).attr("check-value","false");
			
		}
		else if (num_checked_sub_subjects < num_sub_subjects){
			$(this).parent().prev().prev().removeClass("active");
			//$(this).parent().prev().prev().removeClass("checkboxUnchecked");
			$(this).parent().prev().prev().addClass("semi");
			$(this).attr("check-value","true");
		}
		else{
			//$(this).parent().prev().prev().removeClass("checkboxUnchecked");
			$(this).parent().prev().prev().removeClass("semi");
			$(this).parent().prev().prev().addClass("active");
			$(this).attr("check-value","true");
		}
		$(".only_all_subjects").attr("check-value", "false");
		$(".only_all_subjects").removeClass("active");
		//$(".only_all_subjects").addClass("checkboxUnchecked");
		$(".only_all_subjects").attr("check-value", "false");
		e.stopPropagation();
	});
	
	$(".only_all_subjects").click(function(){
		if ($(this).attr("check-value") == "false"){
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
			
			$(".select_all").attr("check-value", "false");
			$(".select_all").removeClass("checkboxChecked");
			$(".select_all").addClass("checkboxUnchecked");
			
			$(this).attr("check-value", "true");
			$(this).removeClass("checkboxUnchecked");
			$(this).addClass("checkboxChecked");
		}
		else{
			$(this).attr("check-value", "false");
			$(this).removeClass("checkboxChecked");
			$(this).addClass("checkboxUnchecked");
		}
		
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
		$(".only_all_subjects").attr("check-value", "false");
		$(".only_all_subjects").removeClass("checkboxChecked");
		$(".only_all_subjects").addClass("checkboxUnchecked");
		$(".only_all_subjects").attr("check-value", "false");
	}); 
	
	$(".search_by_all_year").click(function(){
		if ($(this).attr("check-value") == "false"){
			$(this).attr("check-value", "true");
			$(this).removeClass("checkboxUnchecked");
			$(this).addClass("checkboxChecked");
			$(".search_open").attr("check-value","false");
			$(".search_open").removeClass("checkboxChecked");
			$(".search_open").addClass("checkboxUnchecked");
			$(".search_expired").attr("check-value","false");
			$(".search_expired").removeClass("checkboxChecked");
			$(".search_expired").addClass("checkboxUnchecked");
		}
		else{
			$(this).attr("check-value", "false");
			$(this).removeClass("checkboxChecked");
			$(this).addClass("checkboxUnchecked");			
		}			
	});
	
	$(".search_open").click(function(){
		if ($(this).attr("check-value") == "false"){
			$(this).attr("check-value", "true");
			$(this).removeClass("checkboxUnchecked");
			$(this).addClass("checkboxChecked");
			$(".search_by_all_year").attr("check-value","false");
			$(".search_by_all_year").removeClass("checkboxChecked");
			$(".search_by_all_year").addClass("checkboxUnchecked");
			$(".search_expired").attr("check-value","false");
			$(".search_expired").removeClass("checkboxChecked");
			$(".search_expired").addClass("checkboxUnchecked");
		}
		else{
			$(this).attr("check-value", "false");
			$(this).removeClass("checkboxChecked");
			$(this).addClass("checkboxUnchecked");			
		}			
	});
	
	$(".search_expired").click(function(){
		if ($(this).attr("check-value") == "false"){
			$(this).attr("check-value", "true");
			$(this).removeClass("checkboxUnchecked");
			$(this).addClass("checkboxChecked");
			$(".search_open").attr("check-value","false");
			$(".search_open").removeClass("checkboxChecked");
			$(".search_open").addClass("checkboxUnchecked");
			$(".search_by_all_year").attr("check-value","false");
			$(".search_by_all_year").removeClass("checkboxChecked");
			$(".search_by_all_year").addClass("checkboxUnchecked")
		}
		else{
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
			$(this).attr("check-value","false");
		}
		else if (num_checked_sub_subjects > 0 && num_checked_sub_subjects < num_sub_subjects){			
			$(this).removeClass("checkboxChecked");
			$(this).removeClass("checkboxUnchecked");
			$(this).addClass("checkboxPartial");
			$(this).attr("check-value","true");
		}
		else{
			$(this).removeClass("checkboxUnchecked");
			$(this).removeClass("checkboxPartial");
			$(this).addClass("checkboxChecked");
			$(this).attr("check-value","true");
		}
	});
	
	$('select').show();
	if ($(".only_all_subjects").attr("check-value") == "true"){
		$(".only_all_subjects").removeClass("checkboxUnchecked");
		$(".only_all_subjects").addClass("checkboxChecked");		
	}
	else{
		$(".only_all_subjects").addClass("checkboxUnchecked");
	}
	
	if ($(".search_by_all_year").attr("check-value") == "true"){
		$(".search_by_all_year").removeClass("checkboxUnchecked");
		$(".search_by_all_year").addClass("checkboxChecked");		
	}
	else{
		$(".search_by_all_year").addClass("checkboxUnchecked");
	}
	
	if ($(".search_open").attr("check-value") == "true"){
		$(".search_open").removeClass("checkboxUnchecked");
		$(".search_open").addClass("checkboxChecked");		
	}
	else{
		$(".search_open").addClass("checkboxUnchecked");
	}
	
	if ($(".search_expired").attr("check-value") == "true"){
		$(".search_expired").removeClass("checkboxUnchecked");
		$(".search_expired").addClass("checkboxChecked");		
	}
	else{
		$(".search_expired").addClass("checkboxUnchecked");
	}
});



function handleData(data) {
	$(".popup_placeholder").html(data);
	$(".popup_placeholder").show();
	var pTop = ($(window).height() - $(".popup_placeholder").height())/2;
	var pLft = ($(window).width() - 600)/2;
	$(".popup_placeholder").css({
		zIndex:100,
    	position: 'fixed',
    	left: pLft,
    	top: pTop
	});
}

</script>