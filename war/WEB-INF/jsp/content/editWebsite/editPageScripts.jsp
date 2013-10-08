<%@ page  pageEncoding="UTF-8" %>
<script type="text/javascript" src="js/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="js/jquery.autosave.js"></script>
<style>
	.ui-autocomplete li {
		list-style-type: none;
	}

</style>

<script language="Javascript">

function resetAutocomplete(funds){
	$("#searchPhrase").autocomplete( 
			{ source: 'selectBoxFiller?type=fundsWithId',
			 minLength: 2,
			 highlight: true,				 
			 select: function(event, ui) {
				 	//alert(ui.item.label);
					$("#searchPhrase").val(ui.item.label);
					$("#fundId").val(ui.item.id);
					//alert("here");
					 $.get('objectQuery?type=fundDesk&id='+$("#fundId").val(), function(data) {
						 $("#deskId").val( data ).attr('selected',true);
					 });
				 },
			 change: function(event, ui) {
				 checkFund();
				 
				 }
		    }
	);
}

function countryAutocomplete(){
	$("#selectCountry").autocomplete( 
		{ source: 'selectBoxFiller?type=countries&localeId=${lang.localeId}',
			 minLength: 2,
			 highlight: true,				 
			 select: function(event, ui) {
				//alert(ui.item.label);
				$("#selectCountry").val(ui.item.label);
				$("#countryId").val(ui.item.id);
			 }
		}
	);
}

$(document).ready(function() {

	if($('#showDescriptionOnly').is(":checked"))
    	$(".notDescriptionOnly").hide();
	else
   		$(".notDescriptionOnly").show();


	$("#showDescriptionOnly").change(function(){		
		if($('#showDescriptionOnly').is(":checked"))
    		$(".notDescriptionOnly").hide();
		else
			$(".notDescriptionOnly").show();
	});
	
	if($("#fundId").val()!='0')
		$('#searchPhrase').prop("disabled", true);

	$("#searchPhrase").click(function(){
	    	$("#searchPhrase").val('');
	    	resetAutocomplete();
	});

	$("#selectCountry").click(function(){
    	$("#selectCountry").val('');
    	countryAutocomplete();
	});

	$(".langSelect").change(function(){
		insertIds();
  		$(".ajaxSubmit").remove();
   	 	$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"false\"/>");
		$('#form').submit();
	});
	
	if($('#allYearSubmission').is(":checked")){
		//alert("checked");
		$('.submissionDate').css("opacity","0.3");
		$('.submissionDate').prop("disabled", true);
		$('.submissionDate').val("");
	}
	else{
		//alert("not checked");
		$('.submissionDate').css("opacity","1");
		$('.submissionDate').prop("disabled", false);
	}
	
	$("#editFund").click(function(e){
		e.preventDefault();
		window.location="fund.html?id="+$("#fundId").val();
	});
	
	$("#newTempFund").click(function(e){
		e.preventDefault();
		window.location="fund.html?action=new&temporary=true";
	});

	$("#changeFund").click(function(e){
		e.preventDefault();
		$('#searchPhrase').prop("disabled", false);
		$("#fundId").val('0');
	});
	
	$('form').find('input:not([type=file],[type=button])').autoSave(function(){		
		insertIds();
		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"true\"/>");
		$('#form').ajaxSubmit();
	}, {delay: 2000});
	
	$('form').find('select:not([class*=deskId])').change(function(){
		insertIds();
		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"true\"/>");
	    $('#form').ajaxSubmit();
	});
	
	$('form').find('checkbox').change(function(){
		insertIds();
		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"true\"/>");
	    $('#form').ajaxSubmit();
	});
	
	$('#deskId').change(function(){
		insertIds();
   		$(".ajaxSubmit").remove();
   	 	$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"false\"/>");
	    $('#form').submit();
	});
	
	$('#allYearSubmission').change(function(){
		if($('#allYearSubmission').is(":checked")){
			//alert("checked");
			$('.submissionDate').css("opacity","0.3");
			$('.submissionDate').prop("disabled", true);
			$('.submissionDate').val("");
		}
		else{
			//alert("not checked");
			$('.submissionDate').css("opacity","1");
			$('.submissionDate').prop("disabled", false);
		}
	});
	
	$('#tempUrlTitle').change(function(e){
		e.preventDefault();
		$.get('objectQuery?type=callForProposalCheckUrlTitle&id='+$("#id").val()+'&title='+$("#tempUrlTitle").val(), function(data) {
			if(data>0){
				$("#errorurltitle").html('<font color="red"><fmt:message key="${lang.localeId}.duplicate.urlTitle"/><font color="red"><br>');
				$("#tempUrlTitle").val('');
			}
			else $("#errorurltitle").html('');
		});
	});	
	
	$('#tempTitle').change(function(e){
		e.preventDefault();
		$.get('objectQuery?type=callForProposalCheckTitle&id='+$("#id").val()+'&title='+$("#tempTitle").val(), function(data) {
			if(data>0){
				$("#errortitle").html('<font color="red"><fmt:message key="${lang.localeId}.duplicate.title"/><font color="red"><br>');
				$("#tempTitle").val('');
			}
			else $("#errortitle").html('');
		});
	});	
	
	/*$(".datetime").datetimepicker({dateFormat: 'dd/mm/yy',
		timeFormat:'hh:mm',
		onSelect: function(){
			var text='<fmt:message key="${lang.localeId}.callForProposal.datePassed"/>';
			var str = $(this).val();
   			var dt1  = str.substring(0,2); 
   			var mon1 = str.substring(3,5); 
   			var yr1  = str.substring(6,10);  
   			temp1 = mon1 +"/"+ dt1 +"/"+ yr1;
   			var cfd = Date.parse(temp1);
   			var date1 = new Date(cfd);
   			var date2 = new Date();
   			if(date2.setHours(0,0,0,0)>date1.setHours(0,0,0,0)){
  				$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
				$("#genericDialog").dialog({ modal: false });
				$("#genericDialog").dialog({ height: 200 });
				$("#genericDialog").dialog({ width: 400 });
  				openHelp("",text);
   			}
		}
	});*/
	
	$(".time").timepicker({timeFormat:'hh:mm'});	
	
	$(".date").datepicker({ dateFormat: 'dd/mm/yy', onSelect: function(){
		var text='<fmt:message key="${lang.localeId}.callForProposal.datePassed"/>';
    	var str = $(this).val();
       	var dt1  = str.substring(0,2); 
       	var mon1 = str.substring(3,5); 
       	var yr1  = str.substring(6,10);  
       	temp1 = mon1 +"/"+ dt1 +"/"+ yr1;
       	var cfd = Date.parse(temp1);
       	var date1 = new Date(cfd);
       	var date2 = new Date();
       	if(date2.setHours(0,0,0,0)>date1.setHours(0,0,0,0)){
      			$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
    			$("#genericDialog").dialog({ modal: false });
  				$("#genericDialog").dialog({ height: 200 });
				$("#genericDialog").dialog({ width: 400 });
      			openHelp("",text);
       	}
   	 }});	
	
	$(".date").change(function(event){
		var text='<fmt:message key="${lang.localeId}.callForProposal.dateFormat"/>';
		//check date format 
		var str=$(this).val();
		if(str.indexOf(".")>0)
			while (str.indexOf(".")>0)
				str=str.replace(".","/");
		if(str.indexOf("-")>0)
			while (str.indexOf("-")>0)
				str=str.replace("-","/");
		
		var dateRegex=/^((0[1-9])|([1-31]))\/(([1-9])|(0[1-9])|(1[0-2]))\/((19|20)\d\d)$/;
		$(this).val(str);
		if(!dateRegex.test(str)){
 			$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
			$("#genericDialog").dialog({ modal: false });
			$("#genericDialog").dialog({ height: 200 });
			$("#genericDialog").dialog({ width: 400 });
  			openHelp("",text);
  			$(this).val('');
    	}
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
	
    $("#genericDialog").dialog({
        autoOpen: false,
        show: 'fade',
        hide: 'fade',
        modal: true,
        open: function() { $(".ui-dialog").css("box-shadow","#000 5px 5px 5px");}
  });

	$('button.save').click(function(){
		insertIds();
  		$(".ajaxSubmit").remove();
  		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"false\"/>");
		$('form#form').submit();
	});
	
	$('button.delete').click(function(){
  		$(".ajaxSubmit").remove();
  		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"false\"/>");
 		$("#form").append("<input type=\"hidden\" name=\"action\" class=\"action\" value=\"delete\"/>");
		$('form#form').submit();
	});
	
	$('button.copy').click(function(){
		insertIds();
		$(".ajaxSubmit").remove();
  		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"false\"/>");
 		$("#form").append("<input type=\"hidden\" name=\"action\" class=\"action\" value=\"copy\"/>");
		$('form#form').submit();
	});
	
	var countryArr= new Array();
	<c:forEach items="${countries}" var="country" varStatus="varStatus">
	countryArr.push(${country.id});
	</c:forEach> 
	$(".countryArr").remove();
	$("#form").append("<input type=\"hidden\" name=\"countryArr\" class=\"countryArr\" value=\""+countryArr + "\"/>");
	if(countryArr.length>0)
		$("#deleteCountry").show();

	$('button.addCountry').click(function(e){
		e.preventDefault();
 		if($("#countryId").val()!='' && !contains(countryArr,$("#countryId").val())){
 			$("#countryDiv").html($("#countryDiv").html() + "<p><input type='checkbox' class='countryCheck' id='"+$("#countryId").val()+"'>"+ $("#selectCountry").val()+"</p>");
 			$("#deleteCountry").show(); 			
			countryArr.push($("#countryId").val());
 			//alert(countryArr);
 			$(".countryArr").remove();
 			$("#form").append("<input type=\"hidden\" name=\"countryArr\" class=\"countryArr\" value=\""+countryArr + "\"/>");
	 		$("#form").ajaxSubmit();
 		}
	});
	
	$(".deleteCountry").click(function(e){
		e.preventDefault();
		$('input.countryCheck').each(function(){
			if (this.checked){
				var id = this.id;
				for (var i = 0; i < countryArr.length; i++) {
			        if (countryArr[i] == id) 
		        		countryArr.splice(i,1);
			    }
				if(countryArr.length==0)
		 			$("#deleteCountry").hide(); 			
				$(".countryArr").remove();
		 		$("#form").append("<input type=\"hidden\" name=\"countryArr\" class=\"countryArr\" value=\""+countryArr + "\"/>"); 
		 		$("#form").ajaxSubmit();
		 		$(this).parents('p').remove(); 
			}
		});
		return false;
 	});

	$('#formAttach').change(function(event){
		insertIds();
		$(".ajaxSubmit").remove();
  		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"false\"/>");
		$('form#form').append('<input type=\"hidden\" name=\"addFile\" value=\"yes\"/>');
		$('#form').submit();
	});	

	$('button.online').click(function(){
		var text='<fmt:message key="${lang.localeId}.callForProposal.fieldsError"/>';
      	var errors = checkErrors();//validating fields
		if (errors){
	   		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
			$("#genericDialog").dialog({ modal: false });
			$("#genericDialog").dialog({ height: 200 });
			$("#genericDialog").dialog({ width: 400 });
			openHelp($(this),text);
			return false;
		}
		else{
			insertIds();
    		$('form#form').append('<input type=\"hidden\" name=\"online\" value=\"true\"/>');
    		$(".ajaxSubmit").remove();
    		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"false\"/>");
    		$('#form').submit();
    	   	return false;
		 }//else no errors
	});
	
	$('button.onlineUpdate').click(function(){
		var text='<fmt:message key="${lang.localeId}.callForProposal.fieldsError"/>';
      	var errors = checkErrors();//validating fields
		if (errors){
	   		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
			$("#genericDialog").dialog({ modal: false });
			$("#genericDialog").dialog({ height: 200 });
			$("#genericDialog").dialog({ width: 400 });
			openHelp($(this),text);
			return false;
		}
		else{
			insertIds();
    		$('form#form').append('<input type=\"hidden\" name=\"online\" value=\"true\"/>');
    		$(".ajaxSubmit").remove();
    		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"false\"/>");
    		$('#form').submit();
    	   	return false;
		 }//else no errors 
	});
	
	$('button.offline').click(function(){
		insertIds();
		$('form#form').append('<input type=\"hidden\" name=\"offline\" value=\"true\"/>');
   		$(".ajaxSubmit").remove();
   	 	$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"false\"/>");
		$('#form').submit();
	});

	$('button.post').click(function(){
		window.open('post.html?action=new&callForProposal=' + ${command.id});
		return false;
	});
	
	
	/* subjects list starts here */

	$('tbody.subSubjects').hide();

	$(".minus").hide();
	$(".plus").show();


	<c:forEach items="${rootSubject.subSubjects}" var="subject">
		showRightMultipleSelectImg($('tr#subject${subject.id}'));
	</c:forEach>

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
			
			//this is for autosave
			insertIds();
			$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"true\"/>");
		    $('#form').ajaxSubmit();

			return false;
		});
	
	
		CKEDITOR.disableAutoInline = true;
		if(CKEDITOR.instances['editor1']==null)
			CKEDITOR.inline('editor1');
		if(CKEDITOR.instances['editor2']==null)
			CKEDITOR.inline('editor2');
		if(CKEDITOR.instances['editor3']==null)
			CKEDITOR.inline('editor3');
		if(CKEDITOR.instances['editor4']==null)
			CKEDITOR.inline('editor4');
		if(CKEDITOR.instances['editor5']==null)
			CKEDITOR.inline('editor5');
		if(CKEDITOR.instances['editor6']==null)
			CKEDITOR.inline('editor6');
		if(CKEDITOR.instances['editor7']==null)
			CKEDITOR.inline('editor7');
		if(CKEDITOR.instances['editor8']==null)
			CKEDITOR.inline('editor8');
		if(CKEDITOR.instances['editor9']==null)
			CKEDITOR.inline('editor9');
		if(CKEDITOR.instances['editor10']==null)
			CKEDITOR.inline('editor10');
		if(CKEDITOR.instances['editor11']==null)
			CKEDITOR.inline('editor11');
		if(CKEDITOR.instances['editor12']==null)
			CKEDITOR.inline('editor12');
		if(CKEDITOR.instances['editor13']==null)
			CKEDITOR.inline('editor13');

	    $("#editor1").on('blur', function(e) {
	      	var text = replaceURLWithHTMLLinks($(this).html());
	      	if(text.length==0) text+="&nbsp;";
	      	$(this).html(text);
			$('.editorTextarea', $(this).closest("table")).val(text);
	    	autoSave(); 
	    }); 

		$("#editor2").on('blur', function(e) {
	      	var text = replaceURLWithHTMLLinks($(this).html());
	      	if(text.length==0) text+="&nbsp;";
	      	$(this).html(text);
			$('.editorTextarea', $(this).closest("table")).val(text);
	    	autoSave(); 
	    }); 

	    $("#editor3").on('blur', function(e) {
	      	var text = replaceURLWithHTMLLinks($(this).html());
	      	if(text.length==0) text+="&nbsp;";
	      	$(this).html(text);
			$('.editorTextarea', $(this).closest("table")).val(text);
	    	autoSave(); 
	    }); 

	    $("#editor4").on('blur', function(e) {
	      	var text = replaceURLWithHTMLLinks($(this).html());
	      	if(text.length==0) text+="&nbsp;";
	      	$(this).html(text);
			$('.editorTextarea', $(this).closest("table")).val(text);
	    	autoSave(); 
	    }); 

	    $("#editor5").on('blur', function(e) {
	      	var text = replaceURLWithHTMLLinks($(this).html());
	      	if(text.length==0) text+="&nbsp;";
	      	$(this).html(text);
			$('.editorTextarea', $(this).closest("table")).val(text);
	    	autoSave(); 
	    }); 

	    $("#editor6").on('blur', function(e) {
	      	var text = replaceURLWithHTMLLinks($(this).html());
	      	if(text.length==0) text+="&nbsp;";
	      	$(this).html(text);
			$('.editorTextarea', $(this).closest("table")).val(text);
	    	autoSave(); 
	    }); 

	    $("#editor7").on('blur', function(e) {
	      	var text = replaceURLWithHTMLLinks($(this).html());
	      	if(text.length==0) text+="&nbsp;";
	      	$(this).html(text);
			$('.editorTextarea', $(this).closest("table")).val(text);
	    	autoSave(); 
	    }); 

	    $("#editor8").on('blur', function(e) {
	      	var text = replaceURLWithHTMLLinks($(this).html());
	      	if(text.length==0) text+="&nbsp;";
	      	$(this).html(text);
			$('.editorTextarea', $(this).closest("table")).val(text);
	    	autoSave(); 
	    }); 
	    
	    $("#editor9").on('blur', function(e) {
	      	var text = replaceURLWithHTMLLinks($(this).html());
	      	if(text.length==0) text+="&nbsp;";
	      	$(this).html(text);
			$('.editorTextarea', $(this).closest("table")).val(text);
	    	autoSave(); 
	    }); 

	    $("#editor10").on('blur', function(e) {
	      	var text = replaceURLWithHTMLLinks($(this).html());
	      	if(text.length==0) text+="&nbsp;";
	      	$(this).html(text);
			$('.editorTextarea', $(this).closest("table")).val(text);
	    	autoSave(); 
	    }); 

	    $("#editor11").on('blur', function(e) {
	      	var text = replaceURLWithHTMLLinks($(this).html());
	      	if(text.length==0) text+="&nbsp;";
	      	$(this).html(text);
			$('.editorTextarea', $(this).closest("table")).val(text);
	    	autoSave(); 
	    }); 
	    $("#editor12").on('blur', function(e) {
	      	var text = replaceURLWithHTMLLinks($(this).html());
	      	if(text.length==0) text+="&nbsp;";
	      	$(this).html(text);
			$('.editorTextarea', $(this).closest("table")).val(text);
	    	autoSave(); 
	    }); 
	    $("#editor13").on('blur', function(e) {
	      	var text = replaceURLWithHTMLLinks($(this).html());
	      	if(text.length==0) text+="&nbsp;";
	      	$(this).html(text);
			$('.editorTextarea', $(this).closest("table")).val(text);
	    	autoSave(); 
	    }); 

 		$(".add").click(function(e){
		    e.preventDefault();//no refresh page 
		    var addedText= $('#addedText', $(this).closest("tr")).html();
		    $(".editor", $(this).closest("table")).html($(".editor", $(this).closest("table")).html() + "<br>" +addedText);
			$('.editorTextarea', $(this).closest("table")).val($(".editor", $(this).closest("table")).html());
	    	autoSave(); 
		});
		


		
	    
		$(".deleteAttachment").click(function(e){
			e.preventDefault();
			var attachId= this.id;
			$("#genericDialog").dialog({ modal: true });
	    	$("#genericDialog").dialog('option', 'buttons', {
	            "לא" : function() {
	                $(this).dialog("close");
	                return false;
	               },
	            "כן" : function() {
	                $(this).dialog("close");
	        		insertIds();
	           		$(".ajaxSubmit").remove();
	           	 	$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"false\"/>");
	        		$("#form").append("<input type=\"hidden\" name=\"deleteAttachment\" value=\""+attachId +"\"/>");
	    	   		$("#form").submit();
	    	        return true;
	            }
	        });
			openHelp(this,"האם הנך מאשר את מחיקת הקובץ?");
	    	return false;
		});	

});


function replaceURLWithHTMLLinks(text) {
    var exp = /<a href=\"([^\"]*\.(pdf|doc|docx|xls|xlsx))\".*>(?!<img)(.*)<\/a>/i;
    var match = exp.exec(text);
    while (match != null) {
        var icon=getIcon(match[2]);
        var img= "<img src='image/"+ icon+"' weight='15px' height='15px'/>";
        text=text.replace(exp,"<a href='$1'>"+ img +"$3</a>"); 
        match = exp.exec(text)
    }
    return text;
}

function getIcon(extension){
	var icon;
	if(extension=="pdf")
		icon= "icon_pdf.png";
	else if (extension=="doc")
		icon= "icon_word.gif";
	else if (extension=="docx")
		icon= "icon_word.gif";
	else if (extension=="xls")
		icon= "icon_excel.png";
	else if (extension=="xlsx")
		icon= "icon_excel.png";
	else
		icon+= "icon_somefile.gif";
	return icon;
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

function checkErrors(){ 
	var errors=false;
	if($("#tempTitle").val()==''){
		errors = true;
		$("#errortitle").html('<font color="red"><fmt:message key="${lang.localeId}.callForProposal.enterFieldSubject"/><font color="red"><br>');
	}
	else{
		$("#errortitle").html('');
	}
	if($("#tempUrlTitle").val()==''){
		errors = true;
		$("#errorurltitle").html('<font color="red"><fmt:message key="${lang.localeId}.callForProposal.enterUrlTitle"/><font color="red"><br>');
	}
	else{
		$("#errorurltitle").html('');
	}
	
	if($("#publicationTime").val()==''){
		errors = true;
		$("#errorpublicationTime").html('<font color="red"><fmt:message key="${lang.localeId}.callForProposal.enterPublicationDate"/><font color="red"><br>');
	}
	else{
		$("#errorpublicationTime").html('');
	}
	if($("#finalSubmissionTime").val()=='' && !$("#allYearSubmission").is(":checked")){
		errors = true;
		$("#errorfinalSubmissionTime").html('<font color="red"><fmt:message key="${lang.localeId}.callForProposal.enterFinalSubmissionDate"/><font color="red"><br>');
	}
	else{
		$("#errorfinalSubmissionTime").html('');
	}
	if($("#fundId").val()=='0'){
		errors = true;
		$("#errorfund").html('<font color="red"><fmt:message key="${lang.localeId}.callForProposal.enterFund"/><font color="red"><br>');
	}
	else{
		$("#errorfund").html('');
	}
	if($("#typeId").val()=='0'){
		errors = true;
		$("#errortype").html('<font color="red"><fmt:message key="${lang.localeId}.callForProposal.enterType"/><font color="red"><br>');
	}
	else{
		$("#errortype").html('');
	}
	if($("#deskId").val()=='0'){
		errors = true;
		$("#errordesk").html('<font color="red"><fmt:message key="${lang.localeId}.callForProposal.enterDesk"/><font color="red"><br>');
	}
	else{
		$("#errordesk").html('');
	}
	//if(countryArr.length==0){ 
	//	errors = true;
	//	$("#errorcountries").html('<font color="red"><fmt:message key="${lang.localeId}.callForProposal.enterCountry"/><font color="red"><br>');
	//} 
	//else{ 
	//	$("#errorcountries").html(''); 
	//} 

	var counter =0;
	$('input.subSubject').each(function(){
		if (this.checked)counter++;
	});
	if (counter==0){
		errors = true;
		$("#errorsubjects").html('<font color="red"><fmt:message key="${lang.localeId}.callForProposal.enterSubject"/><font color="red"><br>');
	}
	else{
		$("#errorsubjects").html('');
	}
	
	return errors;
}

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

function insertIds(){
	var ids="";
	$('input.subSubject').each(function(){
			if (this.checked){
				var id = this.id;
				id = id.substring(id.indexOf('.') + 1);
				if (ids !="")
					ids = ids + ",";
				ids = ids +id;
			}
	});
	$('.subjectsIdsString').remove();
	$('form#form').append('<input type=\"hidden\" name=\"subjectsIdsString\" class=\"subjectsIdsString\" value=\"'+ids+'\"/>');
}

function checkFund(){
	//alert($("#fundId").val());
	var text='<fmt:message key="${lang.localeId}.callForProposal.typeFundName"/>';
    if($("#fundId").val()=='0'){
  		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		$("#genericDialog").dialog({ modal: false });
			$("#genericDialog").dialog({ height: 200 });
		$("#genericDialog").dialog({ width: 400 });
  		openHelp("",text);
		$('#searchPhrase').prop("disabled", false);
    }
    else{
    	$('#searchPhrase').prop("disabled", true);
		//autosave
		insertIds();
		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"true\"/>");
	    $('#form').ajaxSubmit();
    }
}

function autoSave(){
	insertIds();
	$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"true\"/>");
    $('#form').ajaxSubmit();
}
function contains(a, obj) {
    for (var i = 0; i < a.length; i++) {
        if (a[i] === obj) {
            return true;
        }
    }
    return false;
}
</script>