<%@ page  pageEncoding="UTF-8" %>
<script type="text/javascript" src="js/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="js/ckeditor/adapters/jquery.js"></script>
<script type="text/javascript" src="js/jquery.autosave.js"></script>
<style>
	.ui-autocomplete li {
		list-style-type: none;
	}

</style>

<script language="Javascript">
var ceditor; //This is for our CKEditor editor
var ceditor_container; //Saves the container of our editor (DIV).

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

$(document).ready(function() {

	if($("#fundId").val()!='0')
		$('#searchPhrase').prop("disabled", true);

	$("#searchPhrase").click(function(){
	    	$("#searchPhrase").val('');
	    	resetAutocomplete();
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
	
	$('#allYearCheckbox').change(function(){
		if($('#allYearCheckbox').is(":checked")){
			$('.submissionDate').css("opacity","0.3");
			$('.submissionDate').prop("disabled", true);
		}
		else{
			$('.submissionDate').css("opacity","1");
			$('.submissionDate').prop("disabled", false);
		}
	});
	
	$(".date").datepicker({ dateFormat: 'dd/mm/yy', onSelect: function(){
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
      			openHelp("","תאריך זה כבר עבר");
       	}
   	 }});	
	
	$(".date").change(function(event){
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
  			openHelp("","יש להזין תאריך בפורמט dd/MM/yyyy");
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
        $.datepicker.setDefaults($.datepicker.regional['he']);
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

	$('#formAttach').change(function(event){
		insertIds();
		$('form#form').append('<input type=\"hidden\" name=\"addFile\" value=\"yes\"/>');
		$('#form').submit();
	});	

	$('button#online').click(function(){
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
			insertIds();
    		$('form#form').append('<input type=\"hidden\" name=\"online\" value=\"true\"/>');
    		$(".ajaxSubmit").remove();
    		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"false\"/>");
    		$('#form').submit();
    	   	return false;
		 }//else no errors
	});
	
	$('button#onlineUpdate').click(function(){
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
			insertIds();
    		$('form#form').append('<input type=\"hidden\" name=\"online\" value=\"true\"/>');
    		$(".ajaxSubmit").remove();
    		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"false\"/>");
    		$('#form').submit();
    	   	return false;
		 }//else no errors 
	});
	
	$('button#offline').click(function(){
		insertIds();
		$('form#form').append('<input type=\"hidden\" name=\"offline\" value=\"true\"/>');
   		$(".ajaxSubmit").remove();
   	 	$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"false\"/>");
		$('#form').submit();
	});

	$('button.post').click(function(){
		window.open('post.html?action=new&callOfProposal=' + ${command.id});
		return false;
	});
	
	
	/* subjects list starts here */

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
			openSubject($(this).children("td.toggleSubject"));
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
	
	var config=	{
			toolbar_Full: [ ['Source','Save','-', 'Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo','-', 'Find','Replace','-','SelectAll','RemoveFormat' ],
			                [ 'Bold','Italic','Underline','Strike','Subscript','Superscript','-','NumberedList','BulletedList','-',
			              	'-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock','-','BidiLtr','BidiRtl', '-','Link','Unlink'],
			              	['Format','Font','FontSize' ],[ 'TextColor','BGColor' ]],
            uiColor:'#F4EEE4',
			contentsCss:'js/ckeditor/_samples/assets/output_xhtml.css',
			contentsLangDirection:'rtl',
			height:"120", 
			width:"800",
			fontSize_sizes : '10/10px;12/12px;14/14px;16/16px;24/24px;48/48px;',
			colorButton_enableMore : false
	};


	$(".editor").click(function(e){
			e.stopPropagation();//so not to start body click
		 	// pressed editor icons
		    if ($(e.target).attr("class")!=undefined && $(e.target).attr("class").indexOf("cke_")>=0) 
					return;
	        //if another editor is already open, close it
        	closeEditor();
 		   //open editor
	        $(".editorText", $(this).closest("table")).hide();
           $(".textareaEditorSpan", $(this).closest("table")).show();
            var textAreaId = $(".textareaEditor", $(this).closest("table")).attr("id");
	        ceditor =  CKEDITOR.replace(textAreaId,config);
	        //Save the div container to know which one to close later
            ceditor_container = $(this);
	  });
		
		$(".openEditor").click(function(e){
			e.stopPropagation();//so not to start body click
	    	e.preventDefault();//so href doesnt submit
	    	$('.editor', $(this).closest("td")).click();
		});
		$(".closeEditor").click(function(e){
		    e.preventDefault();//so href doesnt submit
			closeEditor();
		});
	 
	  $("body").click(function(event){
			if ($(event.target).attr("class")!=undefined && $(event.target).attr("class").indexOf("cke_")>=0) {
				//inside editor
			}
			else {
				closeEditor();
			}
	   });

    
		$(".add").click(function(e){
		    e.stopPropagation();//so not to start body click 
		    e.preventDefault();//no refresh page 
		    var addedText= $('#addedText', $(this).closest("tr")).html();
		    var instance = $('#addedText', $(this).closest("tr")).attr("class");
	    	var ceditor   = CKEDITOR.instances[instance];	       
	    	if(!ceditor){
		    	$(".editor", $(this).closest("table")).click();//open editor 
			    ceditor   = CKEDITOR.instances[instance];	       
			    //add text - insertHtml doesnt work just after opening so setData instead 
			    var oldstr=ceditor.getData();
			    oldstr=	oldstr.replace("</p>","");
		    	ceditor.setData( oldstr + "<br>" +addedText);	
	    	}
	    	else
	    		ceditor.insertHtml( "<br>" +addedText);	
		});

		CKEDITOR.on('instanceReady', function(ev) {//putting cursor at end of text (for ie) 
			 
	        ev.editor.focus();
	 
	        var s = ev.editor.getSelection(); // getting selection
	        var selected_ranges = s.getRanges(); // getting ranges
	        var node = selected_ranges[0].startContainer; // selecting the starting node
	        var parents = node.getParents(true);
	 
	        node = parents[parents.length - 2].getFirst();
	 
	        while (true) {
	            var x = node.getNext();
	            if (x == null) {
	                break;
	            }
	            node = x;
	        }
	 
	        s.selectElement(node);
	        selected_ranges = s.getRanges();
	        selected_ranges[0].collapse(false);  //  false collapses the range to the end of the selected node, true before the node.
	        s.selectRanges(selected_ranges);  // putting the current selection there 
	    });

		/*$("#formAttach").hover(function(e){
		    $("selector").css('cursor','pointer');
		});*/
});
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
	if($("#title").val()==''){
		errors = true;
		$("#errortitle").html('<font color="red">יש למלא שדה נושא קול קורא<font color="red"><br>');
	}
	else{
		$("#errortitle").html('');
	}
	if($("#publicationTime").val()==''){
		errors = true;
		$("#errorpublicationTime").html('<font color="red">יש למלא שדה תאריך פרסום<font color="red"><br>');
	}
	else{
		$("#errorpublicationTime").html('');
	}
	if($("#finalSubmissionTime").val()==''){
		errors = true;
		$("#errorfinalSubmissionTime").html('<font color="red">יש למלא שדה תאריך הגשה קובע<font color="red"><br>');
	}
	else{
		$("#errorfinalSubmissionTime").html('');
	}
	if($("#fundId").val()=='0'){
		errors = true;
		$("#errorfund").html('<font color="red">יש לבחור מממן<font color="red"><br>');
	}
	else{
		$("#errorfund").html('');
	}
	if($("#typeId").val()=='0'){
		errors = true;
		$("#errortype").html('<font color="red">יש לבחור את סוג הקול קורא<font color="red"><br>');
	}
	else{
		$("#errortype").html('');
	}
	if($("#deskId").val()=='0'){
		errors = true;
		$("#errordesk").html('<font color="red">יש לבחור מדור<font color="red"><br>');
	}
	else{
		$("#errordesk").html('');
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

function closeEditor(){
		for ( var i in CKEDITOR.instances ){
		   var currentInstance = i;
		   break;
		}
		var ceditor   = CKEDITOR.instances[currentInstance];	       
		if(ceditor){
    		$(".editorText", $(ceditor_container).closest("table")).show();
     		$(".textareaEditorSpan", $(ceditor_container).closest("table")).hide();
     		if(ceditor.getData()!='')
     			$(".editorText", $(ceditor_container).closest("table")).html(ceditor.getData());
     		else
     			$(".editorText", $(ceditor_container).closest("table")).html('&nbsp;');
     		ceditor.destroy();
     		ceditor = null; //Set it to null since upon the destroying the CKEditor, the value of the variable is not destroyed by reference
     		ceditor_container=null;
    		//autosave
    		insertIds();
    		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"true\"/>");
    	    $('#form').ajaxSubmit();
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
					ids = ids + ","
				ids = ids +id;
			}
	});
	$('.subjectsIdsString').remove();
	$('form#form').append('<input type=\"hidden\" name=\"subjectsIdsString\" class=\"subjectsIdsString\" value=\"'+ids+'\"/>');
}

function checkFund(){
	//alert($("#fundId").val());
    if($("#fundId").val()=='0'){
  		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
		$("#genericDialog").dialog({ modal: false });
			$("#genericDialog").dialog({ height: 200 });
		$("#genericDialog").dialog({ width: 400 });
  		openHelp("","יש להקליד את שם הגוף המממן ורשימה תיפתח לבחירה");
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
</script>