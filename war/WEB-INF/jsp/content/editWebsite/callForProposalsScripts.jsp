<%@ page  pageEncoding="UTF-8" %>
<style>
	.ui-autocomplete li {
		list-style-type: none;
	}

</style>

<script type="text/javascript">
<%@ include file="/WEB-INF/jsp/include/searchPaginationScripts.jsp" %>

function resetAutocomplete(funds){
	$("#searchWords").autocomplete( 
			{ source: 'selectBoxFiller?type=fundsWithId',
			 minLength: 2,
			 highlight: true,				 
			 select: function(event, ui) {
				 	//alert(ui.item.label);
					$("#searchWords").val(ui.item.label);
					$("#fundId").val(ui.item.id);
				 }
		    }
	);
}


$(document).ready(function() {


	$("#searchWords").click(function(){
    	$("#searchWords").val('');
    	resetAutocomplete();
	});
	
	
	$(function() {
		$(".date").datepicker({ dateFormat: 'dd/mm/yy'});	
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

	$('tbody.subSubjects').hide();

	$(".minus").hide();
	$(".plus").show();


	<c:forEach items="${rootSubject.subSubjects}" var="subject">
		showRightMultipleSelectImg($('tr#subject${subject.id}'));
	</c:forEach>

	$("#selectAll").click(function(){
		$("input:checkbox.subSubject").each(function(){
			this.checked = true;
		});
		$("tr.darker").each(function(){
			showRightMultipleSelectImg($(this));
		});
		this.checked = !this.checked;
	});

	$("#diselectAll").click(function(){
		$("input:checkbox.subSubject").each(function(){
			this.checked = false;
		});
		$("tr.darker").each(function(){
			showRightMultipleSelectImg($(this));
			closeSubject($(this).children("td.toggleSubject"));
		});
		this.checked = !this.checked;
	});

	$("#openAllSubjects").click(function(e){
		e.preventDefault();
		$("tr.darker").each(function(){
			showRightMultipleSelectImg($(this));
			openSubject($(this).children("td.toggleSubject"));
		});
	});
	$("#closeAllSubjects").click(function(e){
		e.preventDefault();
		$("tr.darker").each(function(){
			showRightMultipleSelectImg($(this));
			closeSubject($(this).children("td.toggleSubject"));
		});
	});



	$(".toggleSubject").click(function(){
		toggleSubject($(this));
	});

	$("input:checkbox.subSubject").click(function(){
		var id = $(this).attr("id");
		var parentSubjectId = id.substr(0, id.indexOf("."));
		showRightMultipleSelectImg($(this).parents().find("tr#subject"+parentSubjectId));
	});



	$(".selectSubject").click(function(){
			var id = $(this).parent().attr("id");
			var isAnyChecked = false;
			var isAllChecked = true;
			var isUniteAction = false;

			$("tbody#"+id+"Sub").children("tr").children("td").children("input:checkbox").each( function() {

				if (this.checked){
					isAnyChecked = true;
				}
				else{
					isAllChecked = false;
				}
			});

			if (isAnyChecked && ! isAllChecked){
				$("tbody#"+id+"Sub").children("tr").children("td").children("input:checkbox").each( function() {
					if (!this.checked){
						this.checked = true;
						isUniteAction=true;
					}
				});
				isAllChecked = true;
			}

			if (!isUniteAction){
				$("tbody#"+id+"Sub").children("tr").children("td").children("input:checkbox").each( function() {
					this.checked = !this.checked;
				});
				isAllChecked = ! isAllChecked;
				isAnyChecked = isAllChecked;
			}

			showRightMultipleSelectImg($(this).parent());

			if (isAllChecked){
			}
			else if (!isAnyChecked){
				closeSubject($(this).parent().children("td.toggleSubject"));
			}
			
			return false;
		});
    $("#genericDialog").dialog({
        autoOpen: false,
        show: 'fade',
        hide: 'fade',
        modal: true,
        open: function() { $(".ui-dialog").css("box-shadow","#000 5px 5px 5px");}
  });
	
	$('button.search').click(function(){
		if($("#fundId").val()=='')
			$("#form").append("<input type=\"hidden\" name=\"fundId\" value=\"0\"/>");
		else
			$("#form").append("<input type=\"hidden\" name=\"fundId\" value=\""+$("#fundId").val() +"\"/>");
		
		if($("#searchByTemporary").is(":checked"))
			$("#form").append("<input type=\"hidden\" name=\"temporaryFund\" value=\"true\"/>");
		else
			$("#form").append("<input type=\"hidden\" name=\"temporaryFund\" value=\"false\"/>");

		if($("#searchDeleted").is(":checked"))
			$("#form").append("<input type=\"hidden\" name=\"deleted\" value=\"true\"/>");
		else
			$("#form").append("<input type=\"hidden\" name=\"deleted\" value=\"false\"/>");
	
		if($("#searchOpen").is(":checked"))
			$("#form").append("<input type=\"hidden\" name=\"open\" value=\"true\"/>");
		else
			$("#form").append("<input type=\"hidden\" name=\"open\" value=\"false\"/>");

		if($("#searchExpired").is(":checked"))
			$("#form").append("<input type=\"hidden\" name=\"expired\" value=\"true\"/>");
		else
			$("#form").append("<input type=\"hidden\" name=\"expired\" value=\"false\"/>");

		if($("#searchByAllYear").is(":checked"))
			$("#form").append("<input type=\"hidden\" name=\"allYear\" value=\"true\"/>");
		else
			$("#form").append("<input type=\"hidden\" name=\"allYear\" value=\"false\"/>");

		if($("#searchByAllSubjects").is(":checked"))
			$("#form").append("<input type=\"hidden\" name=\"allSubjects\" value=\"true\"/>");
		else
			$("#form").append("<input type=\"hidden\" name=\"allSubjects\" value=\"false\"/>");

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
		
		$('form#form').append('<input type=\"hidden\" name=\"action\" value=\"search\"/>');
		$('form#form').submit();
	});
	
    $(".cleanSearch").click(function(){
    	$("#searchWords").val('');
    	$("#submissionDateFrom").val('');
    	$("#submissionDateTo").val('');
       	$("#searchByAllYear").prop('checked', false);
	   	$("#deskId").val('0');
       	$("#typeId").val('0');
       	$("#targetAudience").val('0');
    	$("#searchByTemporary").prop('checked', false);
    	$("#diselectAll").click();
      	$("#searchByAllSubjects").prop('checked', false);
      	$("#searchDeleted").prop('checked', false);
     	$("#searchOpen").prop('checked', false);
       	$("#searchExpired").prop('checked', false);
   		$("#listViewPage").remove();
		$("#orderBy").remove();
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"search\"/>");
		$("#form").submit();
    	return true;
    });	
    
	
    $(".subjectsRow").show();
	
});

function openSubject(element){
	$(element).children("img.plus").hide();
	$(element).children("img.minus").show();
	var targetId = $(element).parent().attr("id");
		$("tbody#"+targetId+"Sub").show();
}

function showRightMultipleSelectImg(parent){
	var isAnyChecked = false;
	var isAllChecked = true;
	var id = $(parent).attr("id");
	$("tbody#"+id+"Sub").children("tr").children("td").children("input:checkbox").each( function() {
		if (this.checked){
			isAnyChecked = true;
		}
		else{
			isAllChecked = false;
		}
	});
	if (isAnyChecked && ! isAllChecked){
		$(parent).children("td").children("img.empty").hide();
		$(parent).children("td").children("img.v").hide();
		$(parent).children("td").children("img.partly").show();
		openSubject($(parent));
	}
	else if (isAllChecked){
		$(parent).children("td").children("img.empty").hide();
		$(parent).children("td").children("img.partly").hide();
		$(parent).children("td").children("img.v").show();

	}
	else{
		$(parent).children("td").children("img.partly").hide();
		$(parent).children("td").children("img.v").hide();
		$(parent).children("td").children("img.empty").show();
	}
}


function closeSubject(element){
	$(element).children("img.minus").hide();
	$(element).children("img.plus").show();
	var targetId = $(element).parent().attr("id");
		$("tbody#"+targetId+"Sub").hide();
}

function toggleSubject(element){
		$(element).children("img.minus").toggle();
		$(element).children("img.plus").toggle();
		var targetId = $(element).parent().attr("id");
		$("tbody#"+targetId+"Sub").toggle();
}

</script>
