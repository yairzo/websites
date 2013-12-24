<%@ page  pageEncoding="UTF-8" %>

		<script src="/js/jquery-1.10.2.min.js"></script>
		<script src="/js/form.js"></script>
		<script type="text/javascript" src="/js/jquery-ui-1.10.3.custom.js"></script>
 <link href="/style/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css">	 
<script type="text/javascript" src="/js/jquery.form.js"></script>	
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
	
	
	$('.scroll_col').click(function(e){
		e.stopPropagation();
		if($(this).children('.checkbox_list').is(":visible"))
			$('.checkbox_list').css({"display":"none"});
		else{
			$('.checkbox_list').css({"display":"none"});
			$(this).children('.checkbox_list').css({"display":"block","z-index":1});
		}
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
		var url_title=$(this).attr("id");	   		
		$.ajax({
		    url : '/call_for_proposal/' + url_title + '/popup',
		    type: 'GET',
		    success : handleData
		 })
	});
	
	$(function() {
		$(".popup_placeholder" ).draggable();
	});	
	
	$(document).click(function() {
	    $(".popup_placeholder").hide();
	    $('.checkbox_list').css({"display":"none"});
	});
	
	$(".popup_placeholder").click(function(e) {
		e.stopPropagation();
	});
	
	
	$("#advanced_subject").click(function(e){
		e.stopPropagation();
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
		$(".date").datepicker({ dateFormat: 'dd/mm/yy', 
			onSelect: function(){
				$(".search_open").attr("check-value","false");
				$(".search_open").removeClass("active");
				$(".search_expired").attr("check-value","false");
				$(".search_expired").removeClass("active");
				$(".search_by_all_year").attr("check-value","false");
				$(".search_by_all_year").removeClass("active");
			}
		});	
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
		$(".search_by_all_year").removeClass("active");
		$(".search_by_all_year").addClass("none");
 		$(".search_expired").attr("check-value", "false");
		$(".search_expired").removeClass("active");
		$(".search_expired").addClass("none");
       	$(".search_open").attr("check-value", "true");
		$(".search_open").removeClass("none");
		$(".search_open").addClass("active");		
		$(".only_all_subjects").attr("check-value", "false");
		$(".only_all_subjects").removeClass("active");
		$(".only_all_subjects").addClass("none");
		$(".select_all").attr("check-value", "false");
		$(".select_all").removeClass("active");
		$(".select_all").addClass("none");
		$('.selectSubSubject').each(function(){
			$(this).attr("check-value", false);
			$(this).removeClass("active");
			$(this).addClass("none");
		});
		$('.selectSubject').each(function(){
			$(this).attr("check-value", false);
			$(this).removeClass("active");
			$(this).removeClass("semi");
			$(this).addClass("none");
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
				$(this).removeClass("none");
				$(this).addClass("active");
			});
			$(this).attr('check-value', "true");
			$(this).removeClass("none");
			$(this).removeClass("semi");
			$(this).addClass("active");
			
		}
		else{
			$(this).siblings(".checkbox_list").find(".checkbox_box").each(function(){
				$(this).attr("check-value","false");
				$(this).removeClass("active");
				$(this).addClass("none");
			});
			$(this).attr('check-value', "false");
			$(this).removeClass("active");
			$(this).removeClass("semi");
			$(this).addClass("none");
		}
		$(".only_all_subjects").attr("check-value", "false");
		$(".only_all_subjects").removeClass("active");
		$(".only_all_subjects").addClass("none");
		$(".only_all_subjects").attr("check-value", "false");
		e.stopPropagation();
	});
	
	
	
	$(".selectSubSubject").click(function(e){
		if($(this).attr('check-value') == "false"){
			console.log($(this).attr("check-value"));
			$(this).attr('check-value', "true");
			$(this).removeClass("none");
			$(this).addClass("active");
		}
		else{
			console.log("2" + $(this).attr("check-value"));
			$(this).attr("check-value","false");
			$(this).removeClass("active");
			$(this).addClass("none");
		}
		
		var num_sub_subjects = $(this).parent().parent().parent().find(".checkbox_box").length;
		var num_checked_sub_subjects = $(this).parent().parent().parent().find(".active").length;
		
		var select_subject_element = $(this).parent().parent().parent().parent().prev().prev();
		if (num_checked_sub_subjects == 0){			
			select_subject_element.removeClass("active");
			select_subject_element.removeClass("semi");
			select_subject_element.addClass("none");
			select_subject_element.attr("check-value","false");
			
		}
		else if (num_checked_sub_subjects < num_sub_subjects){
			select_subject_element.removeClass("active");
			select_subject_element.removeClass("none");			
			select_subject_element.addClass("semi");
			select_subject_element.attr("check-value","true");
		}
		else{
			select_subject_element.removeClass("none");	
			select_subject_element.removeClass("semi");
			select_subject_element.addClass("active");
			select_subject_element.attr("check-value","true");
		}
		$(".only_all_subjects").attr("check-value", "false");
		$(".only_all_subjects").removeClass("active");
		$(".only_all_subjects").addClass("none");
		$(".only_all_subjects").attr("check-value", "false");
		e.stopPropagation();
	});
	
	$(".only_all_subjects").click(function(){
		if ($(this).attr("check-value") == "false"){
			$('.selectSubSubject').each(function(){
				$(this).attr("check-value", false);
				$(this).removeClass("active");
				$(this).addClass("none");
			});
			$('.selectSubject').each(function(){
				$(this).attr("check-value", false);
				$(this).removeClass("active");
				$(this).removeClass("semi");
				$(this).addClass("none");
			});
			
			$(".select_all").attr("check-value", "false");
			$(".select_all").removeClass("active");
			$(".select_all").addClass("none");
			
			$(this).attr("check-value", "true");
			$(this).removeClass("none");
			$(this).addClass("active");
		}
		else{
			$(this).attr("check-value", "false");
			$(this).removeClass("active");
			$(this).addClass("none");
		}
		
	});
	
	$(".select_all").click(function(){
		if ($(this).attr('check-value') == "false"){
			$('.selectSubSubject').each(function(){
				$(this).attr("check-value", true);
				$(this).removeClass("none");
				$(this).addClass("active");
			});
			$('.selectSubject').each(function(){
				$(this).attr("check-value", true);
				$(this).removeClass("none");
				$(this).removeClass("semi");
				$(this).addClass("active");
			});
			$(this).attr("check-value", "true");
			$(this).removeClass("none");
			$(this).addClass("active");			
		}
		else{
			$('.selectSubSubject').each(function(){
				$(this).attr("check-value", false);
				$(this).removeClass("active");
				$(this).addClass("none");
			});
			$('.selectSubject').each(function(){
				$(this).attr("check-value", false);
				$(this).removeClass("active");
				$(this).removeClass("semi");
				$(this).addClass("none");
			});
			$(this).attr("check-value", "false");
			$(this).removeClass("active");
			$(this).addClass("none");
		}
		$(".only_all_subjects").attr("check-value", "false");
		$(".only_all_subjects").removeClass("active");
		$(".only_all_subjects").addClass("none");
	}); 
	
	$(".search_by_all_year").click(function(){
		if ($(this).attr("check-value") == "false"){
			$(this).attr("check-value", "true");
			$(this).addClass("active");
			$(".search_open").attr("check-value","false");
			$(".search_open").removeClass("active");
			$(".search_expired").attr("check-value","false");
			$(".search_expired").removeClass("active");
		}
		else{
			$(this).attr("check-value", "false");
			$(this).removeClass("active");
		}
		$(".date").val("");
	});
	
	$(".search_open").click(function(){
		if ($(this).attr("check-value") == "false"){
			$(this).attr("check-value", "true");
			$(this).addClass("active");
			$(".search_by_all_year").attr("check-value","false");
			$(".search_by_all_year").removeClass("active");
			$(".search_expired").attr("check-value","false");
			$(".search_expired").removeClass("active");
		}
		else{
			$(this).attr("check-value", "false");
			$(this).removeClass("active");
		}			
		$(".date").val("");
	});
	
	$(".search_expired").click(function(){
		if ($(this).attr("check-value") == "false"){
			$(this).attr("check-value", "true");
			$(this).addClass("active");
			$(".search_open").attr("check-value","false");
			$(".search_open").removeClass("active");
			$(".search_by_all_year").attr("check-value","false");
			$(".search_by_all_year").removeClass("active");
		}
		else{
			$(this).attr("check-value", "false");
			$(this).removeClass("active");
		}			
		$(".date").val("");
	});
	
	$("#login_open").click(function(e) {
		var loginBox = $(".login_box_cp");
		//var topT=$("#login_open").offset().top+$("#login_open").height()*1.5; //under the button 
		//var leftT = $("#login_open").offset().left-loginBox.width()/4; //under the button 
		var topT=($(window).height() - loginBox.height())/2;//middle of page (also change to fixed) 
		var leftT = ($(window).width() - loginBox.width())/2; 
		$(".login_box_cp").css({
			position: 'fixed',
	   	 	top: topT,
	    	left: leftT
		});
		if (loginBox.is(":visible"))
		loginBox.fadeOut("fast");
		else
		loginBox.fadeIn("fast");
		return false;
	});

	$(".login_box_cp").hover(function(){
		mouse_is_inside=true;
	}, function(){
		mouse_is_inside=false;
	});
	$("body").click(function(){
		if(! mouse_is_inside) $(".login_box_cp").fadeOut("fast");
	});
	$('.login_forgot').click(function(e){
		  e.preventDefault();
		  var userName = $("#j_username").val();
		  if($("#j_username").val()=="")
			  alert("הכנס שם משתמש");
		  else
		 	window.location="/sendPasswordEmail.html?username="+ userName;     
	  });


});


$(window).load(function(){
	$('.selectSubSubject').each(function(){
		if ($(this).attr("check-value") == "true"){
			$(this).removeClass("none");
			$(this).addClass("active");
		}
	});
	
 //TODO: complete this
	var all_checked = true;
 	$('.selectSubject').each(function(){
		num_sub_subjects = $(this).parent().find(".checkbox_box_sub").length;
		num_checked_sub_subjects = $(this).parent().find(".active").length;
		
		console.log(num_sub_subjects + " " + num_checked_sub_subjects);
		if (num_checked_sub_subjects == 0){			
			$(this).removeClass("active");
			$(this).removeClass("semi");
			$(this).addClass("none");
			$(this).attr("check-value","false");
			all_checked = false;
		}
		else if (num_checked_sub_subjects > 0 && num_checked_sub_subjects < num_sub_subjects){			
			$(this).removeClass("active");
			$(this).removeClass("none");
			$(this).addClass("semi");
			$(this).attr("check-value","true");
			all_checked = false;
		}
		else{
			$(this).removeClass("none");
			$(this).removeClass("semi");
			$(this).addClass("active");
			$(this).attr("check-value","true");
		}
	});
 	
 	if (all_checked){
 		$(".select_all").attr("check-value","true");
 		$(".select_all").removeClass("none");
 		$(".select_all").addClass("active");
 	}
	
	$('select').show();
	if ($(".only_all_subjects").attr("check-value") == "true"){
		$(".only_all_subjects").removeClass("none");
		$(".only_all_subjects").addClass("active");		
	}
	else{
		$(".only_all_subjects").addClass("none");
	}
	
	if ($(".search_by_all_year").attr("check-value") == "true"){
		$(".search_by_all_year").removeClass("none");
		$(".search_by_all_year").addClass("active");		
	}
	else{
		$(".search_by_all_year").addClass("none");
	}
	
	if ($(".search_open").attr("check-value") == "true"){
		$(".search_open").removeClass("none");
		$(".search_open").addClass("active");		
	}
	else{
		$(".search_open").addClass("none");
	}
	
	if ($(".search_expired").attr("check-value") == "true"){
		$(".search_expired").removeClass("none");
		$(".search_expired").addClass("active");		
	}
	else{
		$(".search_expired").addClass("none");
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