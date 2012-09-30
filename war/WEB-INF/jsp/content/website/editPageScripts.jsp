<%@ page  pageEncoding="UTF-8" %>
<script type="text/javascript" src="js/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="js/ckeditor/adapters/jquery.js"></script>

<script language="Javascript">

function resetAutocomplete(callOfProposals){
	$("#searchPhrase").autocomplete( 
			{source: funds,
			 minLength: 2,
			 highlight: true				 
		    }
	);
}
$(document).ready(function() {

	$.get('selectBoxFiller',{type:'fund'},function(data){
		funds = data.split(",,");
		resetAutocomplete(funds);   	   				
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
    	var str1 = $(this).val();
        var dt1  = str1.substring(0,2); 
        var mon1 = str1.substring(3,5); 
        var yr1  = str1.substring(6,10);  
        temp1 = mon1 +"/"+ dt1 +"/"+ yr1;
        var cfd = Date.parse(temp1);
        var date1 = new Date(cfd); 
        
        if(new Date()>date1){
      		$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
    		$("#genericDialog").dialog({ modal: false });
  			$("#genericDialog").dialog({ height: 200 });
			$("#genericDialog").dialog({ width: 400 });
      		openHelp("","תאריך זה כבר עבר");
        }
    }});	
	
	
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
			return false;
		});
	
	var config=	{
			toolbar_Full: [ ['Source','Save','-', 'Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo','-', 'Find','Replace','-','SelectAll','RemoveFormat' ],
			                [ 'Bold','Italic','Underline','Strike','Subscript','Superscript','-','NumberedList','BulletedList','-',
			              	'-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock','-','BidiLtr','BidiRtl', '-','Link','Unlink'],
			              	['Format','Font','FontSize' ],[ 'TextColor','BGColor' ]],
            uiColor:'#F4EEE4',
			contentsCss:'js/ckeditor/_samples/assets/output_xhtml.css',
			height:"120", 
			width:"800",
			fontSize_sizes : '10/10px;12/12px;14/14px;16/16px;24/24px;48/48px;',
			colorButton_enableMore : false
	};

	var ceditor; //This is for our CKEditor editor
	var ceditor_container; //Saves the container of our editor (DIV).
	var divcontent=""; //This will save the contents of our div

	$(".editor").click(function(e){
		    e.stopPropagation();//so not to start body click
	        //Destroy first our editor if it exists then rollback the previous value of our div
	        if(ceditor)
	        {
	            $(ceditor).ckeditorGet().destroy();
	            $(ceditor_container).html(divcontent);
	        }
	 
	        divcontent = $(this).html(); //Save the content of our div so we can rollback later
	 
	        //Insert the textarea inside the div with the contents of our div as it's value
	        //$(this).html("<textarea name='txtArea'>"+divcontent+"");
	 
	        //Time to replace the textarea to a CKEditor editor
	        //ceditor =  CKEDITOR.replace($(this).children("textarea").get(0),config);
			$(this).ckeditor(config);	
	        
	        ceditor_container = $(this);//Save the div container for retrieval later
	    });
	 
	    $("body").click(function(){
	        //Destroy the editor and rollback the previous value
	    	for ( var i in CKEDITOR.instances ){
	    		   var currentInstance = i;
	    		   break;
	    	}
	    	var ceditor   = CKEDITOR.instances[currentInstance];	       
	    	if(ceditor){
	        	ceditor.destroy();
	            ceditor = null; //Set it to null since upon the destroying the CKEditor, the value of the variable is not destroyed (not destroyed by reference)
	            $(ceditor_container).html(divcontent);
	        }
	    });

	    $(".editoropen").ckeditor(config);
	    
		$(".add").click(function(e){
		    e.stopPropagation();//so not to start body click 
		    e.preventDefault();//no refresh page 
		    var addedText= $('#addedText', $(this).closest("tr")).html();
		    var existingText = $(".editor", $(this).closest("table")).html();
		    $(".editor", $(this).closest("table")).html(existingText + "<br>" + addedText);
		});
		$(".addFile").click(function(e){
		    e.stopPropagation();//so not to start body click 
		    e.preventDefault();//no refresh page 
		    var addedText= $('#addedText', $(this).closest("tr")).html();
		    var existingText = $(".editor", $(this).closest("#fileTable")).html();
		    $(".editor", $(this).closest("#fileTable")).html(existingText + "<br>" + addedText);
		});
   
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