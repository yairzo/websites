<%@ page  pageEncoding="UTF-8" %>
<script type="text/javascript" src="js/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="js/jquery.autosave.js"></script>

<script language="Javascript">


$(document).ready(function() {

	
	if($('.disableEditor').is(":checked"))
    	$("#htmlView").hide();
	else
   		$("#htmlView").show();
	 
	$('form').find('input:not([type=file],[type=button])').autoSave(function(){		
		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"true\"/>");
		$('#form').ajaxSubmit();
	}, {delay: 2000});
	
	$('form').find('select').change(function(){
		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"true\"/>");
	    $('#form').ajaxSubmit();
	});
	
	$('form').find('checkbox').change(function(){
		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"true\"/>");
	    $('#form').ajaxSubmit();
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
       	else{
    		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"true\"/>");
    		$('#form').ajaxSubmit();
       	}
   	 }});	
	
    $("#templateDialog").dialog({
        autoOpen: false,
        show: 'fade',
        hide: 'fade',
        modal: true,
        open: function() { $(".ui-dialog").css("box-shadow","#000 5px 5px 5px");}
  });
    $("#genericDialog").dialog({
        autoOpen: false,
        show: 'fade',
        hide: 'fade',
        modal: true,
        open: function() { $(".ui-dialog").css("box-shadow","#000 5px 5px 5px");}
  });
	$('button.save').click(function(e){
		e.preventDefault();
		$(".ajaxSubmit").remove();
		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"false\"/>");
		$('form#form').submit();
	});

	$('#textualPageFile').change(function(event){
  		$(".ajaxSubmit").remove();
  		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"false\"/>");
		$('#form').submit();
	});	
	
	$('#tempUrlTitle').change(function(e){
		e.preventDefault();
		$.get('objectQuery?type=textualPageCheckUrlTitle&id='+$("#id").val()+'&title='+$("#tempUrlTitle").val(), function(data) {
			if(data>0){
				$("#errorurltitle").html('<font color="red"><fmt:message key="${lang.localeId}.duplicate.urlTitle"/><font color="red"><br>');
				$("#tempUrlTitle").val('');
			}
			else $("#errorurltitle").html('');
		});
	});	
	
	$('#tempTitle').change(function(e){
		e.preventDefault();
		$.get('objectQuery?type=textualPageCheckTitle&id='+$("#id").val()+'&title='+$("#tempTitle").val(), function(data) {
			if(data>0){
				$("#errortitle").html('<font color="red"><fmt:message key="${lang.localeId}.duplicate.title"/><font color="red"><br>');
				$("#tempTitle").val('');
			}
			else $("#errortitle").html('');
		});
	});	

	$('button#online').click(function(){
		var text='<fmt:message key="${lang.localeId}.callForProposal.fieldsError"/>';
     	var errors = checkErrors();//validating fields
		if (errors){
		   	$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
			$("#genericDialog").dialog({ modal: false });
			$("#genericDialog").dialog({ height: 200 });
			$("#genericDialog").dialog({ width: 400 });
			openHelp('',text);
			return false;
		}
		else{
			$('form#form').append('<input type=\"hidden\" name=\"online\" value=\"true\"/>');
  			$(".ajaxSubmit").remove();
  			$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"false\"/>");
			$('#form').submit();
    	   	return false;
		}
	});
	$('button#onlineUpdate').click(function(){
		var text='<fmt:message key="${lang.localeId}.callForProposal.fieldsError"/>';
    	var errors = checkErrors();//validating fields
		if (errors){
		   	$("#genericDialog").dialog('option', 'buttons', {"סגור" : function() {  $(this).dialog("close");} });
			$("#genericDialog").dialog({ modal: false });
			$("#genericDialog").dialog({ height: 200 });
			$("#genericDialog").dialog({ width: 400 });
			openHelp('',text);
			return false;
		}
		else{
   			$('form#form').append('<input type=\"hidden\" name=\"online\" value=\"true\"/>');
    		$(".ajaxSubmit").remove();
    		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"false\"/>");
   			$('#form').submit();
			return false;
		}
	});	
	$('button#offline').click(function(){
		$('form#form').append('<input type=\"hidden\" name=\"offline\" value=\"true\"/>');
  		$(".ajaxSubmit").remove();
  		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"false\"/>");
		$('#form').submit();
	});

	
	$("#categoryId").change(function(){
		var topCategory = "${pageTopCategory}";
		if(topCategory!=$("#categoryId").val() && topCategory>0){
			alert('<fmt:message key="${lang.localeId}.website.categoryclash2"/>');
		}
	});

	$('button.addTemplate').click(function(){
   		$("#templateDialog").dialog('option', 'buttons', {
            "ביטול" : function() {
            	$(this).dialog("close");
             },
            "המשך" : function() {
        		$('form#form').append('<input type=\"hidden\" name=\"action\" value=\"addTemplate\"/>');
           		$('form#form').append('<input type=\"hidden\" name=\"templateTitle\" value=\"'+document.getElementById("templateTitle").value+'\"/>');
          		$(".ajaxSubmit").remove();
          		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"false\"/>");
       			$('#form').submit();
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
	  		$(".ajaxSubmit").remove();
	  		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"false\"/>");
			$('#form').submit();
		}
		else
			alert("יש לבחור תבנית");
		return false;
	});
	
	$('button.showTemplate').click(function(){
		if(document.getElementById("templateId").value>0){
       		$('form#form').append('<input type=\"hidden\" name=\"action\" value=\"showTemplate\"/>');
       		$('form#form').append('<input type=\"hidden\" name=\"templateId\" value=\"'+document.getElementById("templateId").value+'\"/>');
      		$(".ajaxSubmit").remove();
      		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"false\"/>");
   			$('#form').submit();
		}
		else
			alert("יש לבחור תבנית");
		return false;
	});
	$('button.delete').click(function(){
  		$(".ajaxSubmit").remove();
  		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"false\"/>");
 		$("#form").append("<input type=\"hidden\" name=\"action\" class=\"action\" value=\"delete\"/>");
		$('form#form').submit();
	});
	
	$(".langSelect").change(function(){
 		$(".ajaxSubmit").remove();
  		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"false\"/>");
		$('#form').submit();
	});
	

	
   CKEDITOR.on('instanceReady', function() {//(for ie) 
    if('${showTemplate}'){
		var ceditor   = CKEDITOR.instances['htmlEditor'];	  
   		ceditor.setData('${templateHtml}');
        $("#html").val('${templateHtml}');
    }
   });
   
   
    $(".disableEditor").change(function(){		
   	    if($('.disableEditor').is(":checked"))
    		$("#htmlView").hide();
		else
    		$("#htmlView").show();
	});
   
    
	CKEDITOR.disableAutoInline = true;
	if(CKEDITOR.instances['htmlEditor']==null)
		CKEDITOR.inline('htmlEditor');

	
	CKEDITOR.instances['htmlEditor'].on('blur', function() {
      	var text = replaceURLWithHTMLLinks($("#htmlEditor").html());
      	var ceditor   = CKEDITOR.instances['htmlEditor'];
      	if(text.length==0) text+="&nbsp;";
      	ceditor.setData(text);
        $("#html").val(text);
		$("#form").append("<input type=\"hidden\" name=\"ajaxSubmit\" class=\"ajaxSubmit\" value=\"true\"/>");
	    $('#form').ajaxSubmit();
    });  
    
    
});

function replaceURLWithHTMLLinks(text) {
    var exp = /<a href=\"([^\"]*\.(pdf|doc|docx|xls|xlsx))\".*>(?!<img)(.*)<\/a>/;
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
		$("#errortitle").html('<font color="red"><fmt:message key="iw_IL.required.titleCallForProposal"/><font color="red"><br>');
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
	if($("#categoryId").val()=='0'){
		errors = true;
		$("#errorcategoryId").html('<font color="red"><fmt:message key="iw_IL.required.categoryId"/><font color="red"><br>');
	}
	else{
		$("#errorcategoryId").html('');
	}
	return errors;
}


</script>