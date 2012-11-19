<%@ page  pageEncoding="UTF-8" %>
<script type="text/javascript" src="js/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="js/ckeditor/adapters/jquery.js"></script>

<script language="Javascript">


$(document).ready(function() {
	 
	
    $("#templateDialog").dialog({
        autoOpen: false,
        show: 'fade',
        hide: 'fade',
        modal: true,
        open: function() { $(".ui-dialog").css("box-shadow","#000 5px 5px 5px");}
  });

	$('button.save').click(function(){
		$('form#form').submit();
	});

	$('#textualPageFile').change(function(event){
		$('#form').submit();
	});	

	$('button#online').click(function(){
		$('form#form').append('<input type=\"hidden\" name=\"online\" value=\"true\"/>');
		$('#form').submit();
	});
	
	$('button#offline').click(function(){
		$('form#form').append('<input type=\"hidden\" name=\"offline\" value=\"true\"/>');
		$('#form').submit();
	});
	
	$('button.addTemplate').click(function(){
   		$("#templateDialog").dialog('option', 'buttons', {
            "ביטול" : function() {
            	$(this).dialog("close");
             },
            "המשך" : function() {
        		$('form#form').append('<input type=\"hidden\" name=\"action\" value=\"addTemplate\"/>');
           		$('form#form').append('<input type=\"hidden\" name=\"templateTitle\" value=\"'+document.getElementById("templateTitle").value+'\"/>');
       			$('form#form').submit();
            	$(this).dialog("close");
             }
    	});
   		$("#templateDialog").dialog("option", "position", "center");
	    $("#templateDialog").dialog("open");
	    return false;
	});
	
	$('button.updateTemplate').click(function(){
		if(document.getElementById("templateId").value>0){
			$('form#form').append('<input type=\"hidden\" name=\"action\" value=\"updateTemplate\"/>');
			$('form#form').submit();
		}
		else
			alert("יש לבחור תבנית");
		return false;
	});
	
	$('button.showTemplate').click(function(){
		if(document.getElementById("templateId").value>0){
       		$('form#form').append('<input type=\"hidden\" name=\"action\" value=\"showTemplate\"/>');
       		$('form#form').append('<input type=\"hidden\" name=\"templateId\" value=\"'+document.getElementById("templateId").value+'\"/>');
   			$('form#form').submit();
		}
		else
			alert("יש לבחור תבנית");
		return false;
	});
	
	<c:if test="${showTemplate}">
		$("#html").html('${templateHtml}');
	</c:if>

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

    $(".editor").ckeditor(config);
	    
		
   
});




</script>