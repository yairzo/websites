<%@ page  pageEncoding="UTF-8" %>
<script type="text/javascript" src="/js/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="/js/jquery.autosave.js"></script>
<style>
	.ui-autocomplete li {
		list-style-type: none;
	}
	
.ui-autocomplete { height: 200px; overflow-y: scroll; overflow-x: hidden;}

</style>

<script language="Javascript">
var countryArr= new Array();
var urlTitleRegexp = /^([A-Z]([a-zA-Z0-9-])+_)+[A-Z0-9][a-zA-Z0-9-]+$/;
var filenameRegexp = /^([A-Z]([a-zA-Z0-9-])+_)+[A-Z0-9][a-zA-Z0-9-]+$/;

var editingFlag=false;

var duplicateUrlTitle=false;
var duplicateTitle=false;

function resetAutocomplete(){
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
		if($("#fundId").val()!='' && $("#fundId").val()!=0)
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
				duplicateUrlTitle=true;
				$("#errorurltitle").html('<font color="red"><fmt:message key="${lang.localeId}.duplicate.callForProposal.urlTitle"/>' +data + '<font color="red"><br>');
			}
			else $("#errorurltitle").html('');
		});
	});	
	
	$('#tempUrlTitle').blur(function(e){
		if($('#tempUrlTitle').val()!=''){
			var check = $('#tempUrlTitle').val();
			if (check.search(urlTitleRegexp) == -1){
	        	alert('<fmt:message key="${lang.localeId}.callForProposal.urlTitleFormat"/>');
	        	e.preventDefault();
	    	}
		}
	});
	
	$('#tempTitle').change(function(e){
		e.preventDefault();
		$.get('objectQuery?type=callForProposalCheckTitle&id='+$("#id").val()+'&title='+$("#tempTitle").val(), function(data) {
			if(data>0){
				duplicateTitle=true;
				$("#errortitle").html('<font color="red"><fmt:message key="${lang.localeId}.duplicate.callForProposal.title"/>' +data + '<font color="red"><br>');
			}
			else $("#errortitle").html('');
		});
		//create urlTitle based on title
		if('${lang.localeId}'=='en_US'){
			var title = $('#tempTitle').val();
			if(title!=''){
				var arr = title.split(/\s|_/);
		    	for(var i=0,l=arr.length; i<l; i++) {
		        	arr[i] = arr[i].substr(0,1).toUpperCase() + 
		                 (arr[i].length > 1 ? arr[i].substr(1).toLowerCase() : "");
		   		 }
		    	arr= arr.join("_");
		    	arr=arr.replace("'","");
		    	arr=arr.replace("\"","");
		    	$('#tempUrlTitle').val(arr);
			}
		}

	});	


	
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
		var counter=0;
		while(counter<3){
			if(!editingFlag){
				insertIds();
  				$(".ajaxSubmit").remove();
  				$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"false\"/>");
  				$('form#form').submit();
  				counter=3;
			}
			else{ //not finished blur from editor
			   	//alert("waiting");
				setTimeout(function(){/*nothing;*/ }, 1000);
				counter ++;
			}
		}
	});
	
	$('button.delete').click(function(e){
		e.preventDefault();
	   	$("#genericDialog").dialog({ modal: true });
    	$("#genericDialog").dialog('option', 'buttons', {
            "<fmt:message key='${lang.localeId}.general.no'/>" : function() {
                $(this).dialog("close");
                return false;
               },
            "<fmt:message key='${lang.localeId}.general.yes'/>" : function() {
        		$(".ajaxSubmit").remove();
          		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"false\"/>");
         		$("#form").append("<input type=\"hidden\" name=\"action\" class=\"action\" value=\"delete\"/>");
        		$('form#form').submit();
              }
            });

    	openHelp(this,'<fmt:message key="${lang.localeId}.callForProposal.confirmDelete"/>');
        return false;
	});
	
	$('button.copy').click(function(){
		insertIds();
		$(".ajaxSubmit").remove();
  		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"false\"/>");
 		$("#form").append("<input type=\"hidden\" name=\"action\" class=\"action\" value=\"copy\"/>");
		$('form#form').submit();
	});
	
	<c:forEach items="${countries}" var="country" varStatus="varStatus">
		countryArr.push(${country.id});
	</c:forEach> 
	<c:if test="${noCollaborationCountry}">
		countryArr.push('0');
	</c:if>
	$(".countryArr").remove();
	$("#form").append("<input type=\"hidden\" name=\"countryArr\" class=\"countryArr\" value=\""+countryArr + "\"/>");
	if('${noCollaborationCountry}'){
		$(".noCollaborationCountry").prop('checked', true);
		$("#selectCountry").prop('disabled', true);
		$("#selectCountry").css("opacity","0.3");
		$(".addCountry").prop('disabled', true);
		$(".addCountry").css("opacity","0.3");
	}
	else if(countryArr.length>0){
		$("#selectCountry").prop('disabled', false);
		$("#selectCountry").css("opacity","1");
		$(".addCountry").prop('disabled', false);
		$(".addCountry").css("opacity","1");
		$("#deleteCountry").show();
	}
	
	$(".noCollaborationCountry").click(function(e){
		if ($(".noCollaborationCountry").is(":checked")){
			$("#selectCountry").prop('disabled', true);
			$("#selectCountry").css("opacity","0.3");
			$(".addCountry").prop('disabled', true);
			$(".addCountry").css("opacity","0.3");
			
			countryArr =new Array();
    		countryArr.push('0');
			$(".countryArr").remove();
	 		$("#form").append("<input type=\"hidden\" name=\"countryArr\" class=\"countryArr\" value=\""+countryArr + "\"/>"); 
	 		$("#form").ajaxSubmit();
	 		$("#countryDiv").html(""); 
			$("#deleteCountry").hide(); 			
		}
		else{
			$("#selectCountry").prop('disabled', false);
			$("#selectCountry").css("opacity","1");
			$(".addCountry").prop('disabled', false);
			$(".addCountry").css("opacity","1");
			for (var i = 0; i < countryArr.length; i++) 
		        if (countryArr[i] == 0) 
	        		countryArr.splice(i,1);
		}
			
	});
	
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
		var filename = $(this).val();
		if (filename.search(filenameRegexp) == -1){
			filename = prompt('<fmt:message key="${lang.localeId}.callForProposal.urlTitleFormat"/>','');			
		}	
		if (filename.search(filenameRegexp) == -1){
			alert('<fmt:message key="${lang.localeId}.callForProposal.urlTitleFormat"/>');
		}
		else{
			insertIds();
			$(".ajaxSubmit").remove();
  			$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"true\"/>");
  			$("#form").append("<input type=\"hidden\" name=\"filename\" value=\"" + filename + "\"/>");
			$('form#form').append('<input type=\"hidden\" name=\"addFile\" value=\"yes\"/>');
			var options = { 
		   		success:    function() { 
		   		   	window.location.reload(); 
		    	} 
			}; 
			$('#form').ajaxSubmit(options);
		}
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
			var counter=0;
			while(counter<3){
				if(!editingFlag){
					insertIds();
    				$('form#form').append('<input type=\"hidden\" name=\"online\" value=\"true\"/>');
    				$(".ajaxSubmit").remove();
    				$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"false\"/>");
    				$('#form').submit();
     				counter=3;
 				}
				else{ //not finished blur from editor
					setTimeout(function(){/*nothing;*/ }, 1000);
					counter ++;
				}
			}
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
			var counter=0;
			while(counter<3){
				if(!editingFlag){
					insertIds();
    				$('form#form').append('<input type=\"hidden\" name=\"online\" value=\"true\"/>');
    				$(".ajaxSubmit").remove();
    				$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"false\"/>");
    				$('#form').submit();
     				counter=3;
 				}
				else{ //not finished blur from editor 
					setTimeout(function(){/*nothing;*/ }, 1000);
					counter ++;
				}
			}
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
		if(CKEDITOR.instances['editor1']==null){
			if('${lang.localeId}'=='iw_IL')
				CKEDITOR.inline('editor1',{scayt_autoStartup:false});
			else CKEDITOR.inline('editor1');
		}
		if(CKEDITOR.instances['editor2']==null){
			if('${lang.localeId}'=='iw_IL')
				CKEDITOR.inline('editor2',{scayt_autoStartup:false});
			else CKEDITOR.inline('editor2');
		}
		if(CKEDITOR.instances['editor3']==null){
			if('${lang.localeId}'=='iw_IL')
				CKEDITOR.inline('editor3',{scayt_autoStartup:false});
			else CKEDITOR.inline('editor3');
		}
		if(CKEDITOR.instances['editor4']==null){
			if('${lang.localeId}'=='iw_IL')
				CKEDITOR.inline('editor4',{scayt_autoStartup:false});
			else CKEDITOR.inline('editor4');
		}
		if(CKEDITOR.instances['editor5']==null){
			if('${lang.localeId}'=='iw_IL')
				CKEDITOR.inline('editor5',{scayt_autoStartup:false});
			else CKEDITOR.inline('editor5');
		}
		if(CKEDITOR.instances['editor6']==null){
			if('${lang.localeId}'=='iw_IL')
				CKEDITOR.inline('editor6',{scayt_autoStartup:false});
			else CKEDITOR.inline('editor6');
		}
		if(CKEDITOR.instances['editor7']==null){
			if('${lang.localeId}'=='iw_IL')
				CKEDITOR.inline('editor7',{scayt_autoStartup:false});
			else CKEDITOR.inline('editor7');
		}
		if(CKEDITOR.instances['editor8']==null){
			if('${lang.localeId}'=='iw_IL')
				CKEDITOR.inline('editor8',{scayt_autoStartup:false});
			else CKEDITOR.inline('editor8');
		}
		if(CKEDITOR.instances['editor9']==null){
			if('${lang.localeId}'=='iw_IL')
				CKEDITOR.inline('editor9',{scayt_autoStartup:false});
			else CKEDITOR.inline('editor9');
		}
		if(CKEDITOR.instances['editor10']==null){
			if('${lang.localeId}'=='iw_IL')
				CKEDITOR.inline('editor10',{scayt_autoStartup:false});
			else CKEDITOR.inline('editor10');
		}
		if(CKEDITOR.instances['editor11']==null){
			if('${lang.localeId}'=='iw_IL')
				CKEDITOR.inline('editor11',{scayt_autoStartup:false});
			else CKEDITOR.inline('editor11');
		}
		if(CKEDITOR.instances['editor12']==null){
			if('${lang.localeId}'=='iw_IL')
				CKEDITOR.inline('editor12',{scayt_autoStartup:false});
			else  CKEDITOR.inline('editor12');
		}
		if(CKEDITOR.instances['editor13']==null){
			if('${lang.localeId}'=='iw_IL')
				CKEDITOR.inline('editor13',{scayt_autoStartup:false});
			else CKEDITOR.inline('editor13');
		}

		CKEDITOR.instances['editor1'].on('key', function(e) {
			editingFlag=true;
		});
		CKEDITOR.instances['editor1'].on('blur', function(e) {
	    	var text=replaceNbsps(CKEDITOR.instances['editor1'].getData());
	    	CKEDITOR.instances['editor1'].setData(text);
			$('.editorTextarea', $("#editor1").closest("table")).val(CKEDITOR.instances['editor1'].getData());
	    	autoSave(); 
	    	editingFlag=false;
	    }); 

		CKEDITOR.instances['editor2'].on('key', function(e) {
			editingFlag=true;
		});
		CKEDITOR.instances['editor2'].on('blur', function(e) {
	    	var text=replaceNbsps(CKEDITOR.instances['editor2'].getData());
	    	CKEDITOR.instances['editor2'].setData(text);
			$('.editorTextarea', $("#editor2").closest("table")).val(CKEDITOR.instances['editor2'].getData());
			autoSave(); 
	    	editingFlag=false;
	    });
		
		CKEDITOR.instances['editor3'].on('key', function(e) {
			editingFlag=true;
		});
		CKEDITOR.instances['editor3'].on('blur', function(e) {
	    	var text=replaceNbsps(CKEDITOR.instances['editor3'].getData());
	    	CKEDITOR.instances['editor3'].setData(text);
			$('.editorTextarea', $("#editor3").closest("table")).val(CKEDITOR.instances['editor3'].getData());
	    	autoSave(); 
	    	editingFlag=false;
	    }); 

		CKEDITOR.instances['editor4'].on('key', function(e) {
			editingFlag=true;
		});
		CKEDITOR.instances['editor4'].on('blur', function(e) {
	    	var text=replaceNbsps(CKEDITOR.instances['editor4'].getData());
	    	CKEDITOR.instances['editor4'].setData(text);
			$('.editorTextarea', $("#editor4").closest("table")).val(CKEDITOR.instances['editor4'].getData());
	    	autoSave(); 
	    	editingFlag=false;
	    }); 

		CKEDITOR.instances['editor5'].on('key', function(e) {
			editingFlag=true;
		});
		CKEDITOR.instances['editor5'].on('blur', function(e) {
	    	var text=replaceNbsps(CKEDITOR.instances['editor5'].getData());
	    	CKEDITOR.instances['editor5'].setData(text);
			$('.editorTextarea', $("#editor5").closest("table")).val(CKEDITOR.instances['editor5'].getData());
	    	autoSave(); 
	    	editingFlag=false;
	    }); 

		CKEDITOR.instances['editor6'].on('key', function(e) {
			editingFlag=true;
		});
		CKEDITOR.instances['editor6'].on('blur', function(e) {
	    	var text=replaceNbsps(CKEDITOR.instances['editor6'].getData());
	    	CKEDITOR.instances['editor6'].setData(text);
			$('.editorTextarea', $("#editor6").closest("table")).val(CKEDITOR.instances['editor6'].getData());
	    	autoSave(); 
	    	editingFlag=false;
	    }); 

		CKEDITOR.instances['editor7'].on('key', function(e) {
			editingFlag=true;
		});
		CKEDITOR.instances['editor7'].on('blur', function(e) {
	    	var text=replaceNbsps(CKEDITOR.instances['editor7'].getData());
	    	CKEDITOR.instances['editor7'].setData(text);
			$('.editorTextarea', $('#editor7').closest("table")).val(CKEDITOR.instances['editor7'].getData());
	    	autoSave(); 
	    	editingFlag=false;
	    }); 

		CKEDITOR.instances['editor8'].on('key', function(e) {
			editingFlag=true;
		});
		CKEDITOR.instances['editor8'].on('blur', function(e) {
	    	var text=replaceNbsps(CKEDITOR.instances['editor8'].getData());
	    	CKEDITOR.instances['editor8'].setData(text);
			$('.editorTextarea', $('#editor8').closest("table")).val(CKEDITOR.instances['editor8'].getData());
	    	autoSave(); 
	    	editingFlag=false;
 	    }); 
	    
		CKEDITOR.instances['editor9'].on('key', function(e) {
			editingFlag=true;
		});
		CKEDITOR.instances['editor9'].on('blur', function(e) {
	    	var text=replaceNbsps(CKEDITOR.instances['editor9'].getData());
	    	CKEDITOR.instances['editor9'].setData(text);
			$('.editorTextarea', $('#editor9').closest("table")).val(CKEDITOR.instances['editor9'].getData());
	    	autoSave(); 
	    	editingFlag=false;
	    }); 

		CKEDITOR.instances['editor10'].on('key', function(e) {
			editingFlag=true;
		});
		CKEDITOR.instances['editor10'].on('blur', function(e) {
	    	var text=replaceNbsps(CKEDITOR.instances['editor10'].getData());
	    	CKEDITOR.instances['editor10'].setData(text);
			$('.editorTextarea', $('#editor10').closest("table")).val(CKEDITOR.instances['editor10'].getData());
	    	autoSave(); 
	    	editingFlag=false;
	    }); 

		CKEDITOR.instances['editor11'].on('key', function(e) {
			editingFlag=true;
		});
		CKEDITOR.instances['editor11'].on('blur', function(e) {
	    	var text=replaceNbsps(CKEDITOR.instances['editor11'].getData());
	    	CKEDITOR.instances['editor11'].setData(text);
			$('.editorTextarea', $('#editor11').closest("table")).val(CKEDITOR.instances['editor11'].getData());
	    	autoSave(); 
	    	editingFlag=false;
	    	//alert("blur editor11");
	    }); 
		
		CKEDITOR.instances['editor12'].on('key', function(e) {
			editingFlag=true;
		});
		CKEDITOR.instances['editor12'].on('blur', function(e) {
	    	var text=replaceNbsps(CKEDITOR.instances['editor12'].getData());
	    	CKEDITOR.instances['editor12'].setData(text);
			$('.editorTextarea', $('#editor12').closest("table")).val(CKEDITOR.instances['editor12'].getData());
	    	autoSave(); 
	    	editingFlag=false;
	    }); 
		CKEDITOR.instances['editor13'].on('key', function(e) {
			editingFlag=true;
		});
		CKEDITOR.instances['editor13'].on('blur', function(e) {
	    	var text=replaceNbsps(CKEDITOR.instances['editor13'].getData());
	    	CKEDITOR.instances['editor13'].setData(text);
			$('.editorTextarea', $('#editor13').closest("table")).val(CKEDITOR.instances['editor13'].getData());
	    	autoSave(); 
	    	editingFlag=false;
	    }); 

 		$(".add").click(function(e){
		    e.preventDefault();//no refresh page 
		    var addedText= $('#addedText', $(this).closest("tr")).html();
		    addedText = replaceURLWithHTMLLinks(addedText);
		    $(".editor", $(this).closest("table")).html($(".editor", $(this).closest("table")).html() + "<br>" +addedText);
			$('.editorTextarea', $(this).closest("table")).val($(".editor", $(this).closest("table")).html());
	    	autoSave(); 
		});
		
		$("#budgetPersonSelect").change(function(e){
		    e.preventDefault();//no refresh page 
		    var spanId=$(this).val();
		    var addedText= $('#'+spanId).html();
		    addedText = replaceURLWithHTMLLinks(addedText);
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
	           	 	$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"true\"/>");
	        		$("#form").append("<input type=\"hidden\" name=\"deleteAttachment\" value=\""+attachId +"\"/>");
	        		var options = { 
	        		   		success:    function() { 
	        		   		   	window.location.reload(); 
	        		    	} 
	        		}; 	    	   		
	        		$("#form").ajaxSubmit(options);
	    	        return true;
	            }
	        });
			openHelp(this,"האם הנך מאשר את מחיקת הקובץ?");
	    	return false;
		});	
		
		$("#originalCallWebAddress").on('blur',function(){
			if (!/^http/.test($("#originalCallWebAddress").val()) && !/^\//.test($("#originalCallWebAddress").val()) && $("#originalCallWebAddress").val()!=''){
				$("#originalCallWebAddress").val('http://' + $("#originalCallWebAddress").val());
			}
		});

});


function replaceURLWithHTMLLinks(text) {
    var exp = /<a (?:data-cke-saved-href=\".*?\" )?(target=\"_blank\" )?href=\"([^\"]*\.(pdf|doc|docx|xls|xlsx))\"[^>]*>((?!<img).*)<\/a>/i;
    var match = exp.exec(text);
     while (match != null) {
        alert(match);
        var icon=getIcon(match[3]);
        text=text.replace(exp,"<a href='$2' $1><img src='/image/"+ icon+"' weight='15px' height='15px'/>$4</a>"); 
        match = exp.exec(text)
    }
    return text;
}

function replaceNbsps(text) {
	text=text.replace(/&nbsp;/g,' ');
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
	else if (!duplicateTitle){
		$("#errortitle").html('');
	}
	if($("#tempUrlTitle").val()==''){
		errors = true;
		$("#errorurltitle").html('<font color="red"><fmt:message key="${lang.localeId}.callForProposal.enterUrlTitle"/><font color="red"><br>');
	}
	else if (!duplicateUrlTitle){
		$("#errorurltitle").html('');
	}
	if(duplicateTitle||duplicateUrlTitle){
		errors = true;
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
	if($("#originalCallWebAddress").val()==''){
		errors = true;
		$("#errororiginalCallWebAddress").html('<font color="red"><fmt:message key="${lang.localeId}.callForProposal.enterOriginalCallWebAddress"/><font color="red"><br>');
	}
	else{
		$("#errororiginalCallWebAddress").html('');
	}
	if($("#targetAudience").val()=='0'){
		errors = true;
		$("#errortargetAudience").html('<font color="red"><fmt:message key="${lang.localeId}.callForProposal.enterTargetAudience"/><font color="red"><br>');
	}
	else{
		$("#errortargetAudience").html('');
	}

	if(countryArr.length==0){ 
		errors = true;
		$("#errorcountries").html('<font color="red"><fmt:message key="${lang.localeId}.callForProposal.enterCountry"/><font color="red"><br>');
	} 
	else{ 
		$("#errorcountries").html(''); 
	} 

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
  		openHelp("#fundId",text);
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