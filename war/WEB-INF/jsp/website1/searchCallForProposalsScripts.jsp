<%@ page  pageEncoding="UTF-8" %>

		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
        <script>window.jQuery || document.write('<script src="js/jquery-1.8.3.min.js"><\/script>')</script>
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
	
	
	$("#advanced_subject").click(function(){
    	$("#advanced_subject").val('');
    	resetAutocomplete();
	});
	
	$(".date").datepicker({ dateFormat: 'dd/mm/yy'});	
	
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
        if(${lang.localeId=='iw_IL'})
        	$.datepicker.setDefaults($.datepicker.regional['he']);
        else
        	$.datepicker.setDefaults();
     });
	
	$('button.search').click(function(){
		if($("#fundId").val()=='')
			$("#form").append("<input type=\"hidden\" name=\"fundId\" value=\"0\"/>");
		else
			$("#form").append("<input type=\"hidden\" name=\"fundId\" value=\""+$("#fundId").val() +"\"/>");
		
		/*if($("#searchByTemporary").is(":checked"))
			$("#form").append("<input type=\"hidden\" name=\"temporaryFund\" value=\"true\"/>");
		else
			$("#form").append("<input type=\"hidden\" name=\"temporaryFund\" value=\"false\"/>");*/

		if($("#searchOpen").is(":checked"))
			$("#form").append("<input type=\"hidden\" name=\"open\" value=\"false\"/>");
		else
			$("#form").append("<input type=\"hidden\" name=\"open\" value=\"true\"/>");

		if($("#searchExpired").is(":checked"))
			$("#form").append("<input type=\"hidden\" name=\"expired\" value=\"false\"/>");
		else
			$("#form").append("<input type=\"hidden\" name=\"expired\" value=\"true\"/>");

		if($("#searchByAllYear").is(":checked"))
			$("#form").append("<input type=\"hidden\" name=\"allYear\" value=\"false\"/>");
		else
			$("#form").append("<input type=\"hidden\" name=\"allYear\" value=\"true\"/>");

		if($("#searchByAllSubjects").is(":checked"))
			$("#form").append("<input type=\"hidden\" name=\"allSubjects\" value=\"false\"/>");
		else
			$("#form").append("<input type=\"hidden\" name=\"allSubjects\" value=\"true\"/>");

		var ids="";
		$('input.subSubject').each(function(){
				if (this.checked){
					var id = this.id;
					id = id.substring(id.indexOf('.') + 1);
					if (ids !="")
						ids = ids + ","
					ids = ids +id;
				}
		});
		$('.subjectsIdsString').remove();
		$('form#form').append('<input type=\"hidden\" name=\"subjectsIdsString\" class=\"subjectsIdsString\" value=\"'+ids+'\"/>');
		
		$('form#form').submit();
	});

	

	/*$('div.check_all').on('click',function () {
		 var checkBox = $(this).find("input:checkbox");
		alert(checkBox.is(":checked"));
		});	*/
	
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
    	$("#diselectAll").click();
    	$("#searchByAllSubjects").prop('checked', true);
    	
  		$("#listViewPage").remove();
		$("#orderBy").remove();
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"search\"/>");
		$("#form").submit();
    	return true;
    });	
		
	$('div.subSubjects').hide();
	
	$(".scroll_col").click(function(){
		var rowPos = $(this).position();
		bottomTop = rowPos.top+20;
		bottomLeft = rowPos.left;
		$(this).children(".subSubjects").css({
			position:'absolute',
			top: bottomTop,
	    	left: bottomLeft,
			background:'#ffffff'
		});
		$(this).children(".subSubjects").toggle();
	});
	
	$(".check_all").click(function(){
		$("input:checkbox.subject").each(function(){
			this.checked = true;
			$(this).change();
		});
		$("input:checkbox.subSubject").each(function(){
			this.checked = true;
			$(this).change();
		});
		this.checked = !this.checked;
	});

});

		</script>