<%@ page  pageEncoding="UTF-8" %>

		<script src="js/jquery-1.10.2.min.js"></script>
		<script src="js/form.js"></script>
		<script type="text/javascript" src="/iws/js/jquery-ui-1.10.3.custom.js"></script>
<link href="/iws/style/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css">		
<style>
	.ui-autocomplete li {
		list-style-type: none;
	}

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
	
	$(".viewProposal").click(function(e) {
		e.preventDefault();
		
		var proposalId=$(this).attr("id");	   		
		var rowPos = $(this).position();
		bottomTop = rowPos.top - 100;
		bottomLeft = rowPos.left - 100;
		$(".popup_placeholder").css({
			position:'absolute',
			top: bottomTop,
	    	left: bottomLeft
		});
		$.get('callForProposal.html?id='+proposalId+'&p=1&t=1', function(data) {
			$(".popup_placeholder").html(data);
			$(".popup_placeholder").show();
		});
		
	});
	
	
	$(document).click(function() {
	     $(".popup_placeholder").hide();
	});
	$(".popup_placeholder").click(function(e) {
		e.stopPropagation();
	});
	
	$("#advanced_subject").click(function(){
    	$("#advanced_subject").val('');
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
	
	$('.advanced_submit').click(function(){
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
		$("input:checkbox.actual").each(function(){
				if (this.checked==true){
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
		$("#advanced_subject").val('')
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
		
		
	

	$(".openSubSubjects").click(function(){
		if($(this).parent().children(".subSubjects").css('display')=='none')	{	
			$('div.subSubjects').hide();
			var rowPos = $(this).position();
			bottomTop = rowPos.top+20;
			bottomLeft = rowPos.left-20;
			$(this).parent().children(".subSubjects").css({
				position:'absolute',
				top: bottomTop,
	    		left: bottomLeft,
	    		boxShadow: '10px 10px 5px #888888',
				background:'#ffffff',
				padding:10
			});
			$(this).parent().children(".subSubjects").show();
		}
		else{
			$('div.subSubjects').hide();
		}
		
	});
	
	$(".selectSubject").click(function(){
		$(this).change();
		$(this).children("span.checkbox").removeClass("checkboxPartial");
		var id = $(this).children("input:checkbox.subject").attr("id");
		if(!$(this).children("input:checkbox.subject").is(":checked")){
			$("input:checkbox[id^='"+id+".']").each(function(){
				this.checked = false;
				$(this).change();
			});
			$("input:checkbox[class^='" + id + ".']").each(function(){
				setCheckboxState($(this), "true");
			});
		}
		else{
			$("input:checkbox[id^='"+id+".']").each(function(){
				this.checked = true;
				$(this).change();
			});
			$("input:checkbox[class^='" + id + ".']").each(function(){
				setCheckboxState($(this), "false");
			});
		}
	});
	
	
	
	$(".selectSubSubject").click(function(e){
		var subject_element = $(this).parents(".check").find("input:checkbox.subject");		
		var checkbox_element = $(this).find(".actual");
		changeCheckboxState(checkbox_element);		
		var id = subject_element.attr("id");
		var type = getCheckboxImage(id);
		checkActionCheckbox(subject_element, type);		
	});
	
	$(".check_all").click(function(){
		if(!$("input:checkbox#selectAll").is(":checked")){
			$("input:checkbox.subject").each(function(){
				$(this).parent().children("span.checkbox").removeClass("checkboxPartial");
				this.checked = false;
			});
			$("input:checkbox.subSubject").each(function(){
				this.checked = false;
				$(this).change();
				setCheckboxState($(this), "false");
				
			});
			$("input:checkbox.actual").each(function(){
				setCheckboxState($(this), "true");
			});
		}
		else{
			$("input:checkbox.subject").each(function(){
				$(this).parent().children("span.checkbox").removeClass("checkboxPartial");
				this.checked = true;
			});
			$("input:checkbox.subSubject").each(function(){
				this.checked = true;
				$(this).change();
				setCheckboxState($(this), "true");
				
			});
			$("input:checkbox.actual").each(function(){
				setCheckboxState($(this), "false");
			});
		}
	});

});

function changeCheckboxState(element){
	var current_state = element.attr("checked");
	if (current_state == "true" || current_state== "checked")
		element.removeAttr("checked");
	else
		element.attr("checked","true");
}

function setCheckboxState(element, toState){
	element.removeAttr("checked");
	console.log("class: " + element.attr("class"));
	if (toState == "true")
		element.attr("checked", toState);
}

function enforceCheckboxStateFalse(element){
	element.removeAttr("checked");
	element.attr("checked", "false");
}


function checkActionCheckbox(element, type){
	element.removeAttr("checked");
	if(type==1){
		element.checked = true;
		element.prev().removeClass("checkboxUnchecked");
		element.prev().removeClass("checkboxChecked");
		element.prev().addClass("checkboxPartial");
		element.prev().attr("style","");		
	}
	else if (type==2){
		element.checked = false;
		element.change();
		element.prev().removeClass("checkboxUnchecked");
		element.prev().removeClass("checkboxPartial");
		element.prev().addClass("checkboxChecked");
		element.prev().attr("style","");		
	}
	else{
		element.checked = true;
		element.attr("checked","true");
		element.prev().removeClass("checkboxPartial");
		element.prev().removeClass("checkboxChecked");
		element.prev().addClass("checkboxUnchecked");
		element.prev().attr("style","");		
	}	
}
function getCheckboxImage(subjectid){
	var isAnyChecked = false;
	var isAllChecked = true;	
	$("input:checkbox[id^='"+subjectid+".']").each(function(){
		if (this.checked==false){
			isAnyChecked = true;
		}
		else{
			isAllChecked = false;
		}
	});	
	if (isAnyChecked && ! isAllChecked)
		return 1;
	else if (isAllChecked)
		return 2;
	else
		return 0;
}

function isAllSubSubjectsChecked(){
	var allChecked = true;
	$("input:checkbox.actual").each(function(){
		
		var checkedAttr = $(this).attr("checked");
		console.log("xxx: " + checkedAttr);
		if (typeof checkedAttr === "undefined" 
				|| ($(this).attr("checked") != "true" && $(this).attr("checked") != "checked")){
			allChecked = false;
			return;			
		}
	});
	console.log("im here");
	return allChecked;
}

$(window).load(function(){
	$(".selectSubject").each(function(e){
		var element = $(this).children("input:checkbox.subject"); 
		var id = element.attr("id");
		var type = getCheckboxImage(id);
		checkActionCheckbox(element, type);
	});
	$("select.styled").show();
	
	console.log("All subs: " + isAllSubSubjectsChecked());
	if (isAllSubSubjectsChecked()){
		console.log("All subjects checked");
		var element = $("input:checkbox#selectAll");
		element.checked=false;
		setCheckboxState(element, "false");
		element.change();
	}
	else {
		console.log("Not all subjects checked");
		var element = $("input:checkbox#selectAll");
		element.checked=true;
		setCheckboxState(element, "true");
		element.change();
	}
});

		</script>