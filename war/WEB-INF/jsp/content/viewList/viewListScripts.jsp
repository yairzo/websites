<script language="Javascript">
	<%@ include file="/WEB-INF/jsp/content/userMessageScripts.jsp" %>

	<c:if test="${false}">
	function resizeIframe() {
		i = parent.document.getElementById(window.name)
		iHeight = document.body.scrollHeight
		i.style.height = iHeight + 5 + "px"
	}
	</c:if>
</script>

<c:if test="${editMode}">
	<script type="text/javascript" src="js/tiny_mce/jquery.tinymce.js"></script>
</c:if>

<script language="Javascript">

function applyViewMode(){
	<c:if test="${iframeView}">
		$("#form").append("<input type=\"hidden\" name=\"iv\" value=\"1\"/>");
	</c:if>
	<c:if test="${print}">
		$("#form").append("<input type=\"hidden\" name=\"p\" value=\"1\"/>");
	</c:if>
	<c:if test="${editMode}">
		$("#form").append("<input type=\"hidden\" name=\"em\" value=\"1\"/>");
	</c:if>
}


$(document).ready(function() {

	$("a.updatePreface").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"updatePreface\"/>");
		applyViewMode();
		$("#form").submit();
	});

	$("a.updateFooter").click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"updateFooter\"/>");
		applyViewMode();
		$("#form").submit();
	});



<c:if test="${editMode}">

$("span.prefaceEdit").hide();
$("span.prefaceText").click(function(){
	$("span.prefaceText").hide();
	$("span.prefaceEdit").show();
});

$("span.footerEdit").hide();
$("span.footerText").click(function(){
	$("span.footerText").hide();
	$("span.footerEdit").show();
});


$('textarea.tinymce').tinymce({

// Location of TinyMCE script

script_url : 'js/tiny_mce/tiny_mce.js',

// General options

theme : "advanced",

plugins : "safari,pagebreak,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,inlinepopups,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template",

// Theme options

theme_advanced_buttons1 : "save,newdocument,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,styleselect,formatselect,fontselect,fontsizeselect",

theme_advanced_buttons2 : "cut,copy,paste,pastetext,pasteword,|,search,replace,|,bullist,numlist,|,outdent,indent,blockquote,|,undo,redo,|,link,unlink,anchor,image,cleanup,help,code,|,insertdate,inserttime,preview,|,forecolor,backcolor",

theme_advanced_buttons3 : "tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,emotions,iespell,media,advhr,|,print,|,ltr,rtl,|,fullscreen",

theme_advanced_buttons4 : "insertlayer,moveforward,movebackward,absolute,|,styleprops,|,cite,abbr,acronym,del,ins,attribs,|,visualchars,nonbreaking,template,pagebreak",

theme_advanced_toolbar_location : "top",

theme_advanced_toolbar_align : "left",

theme_advanced_statusbar_location : "bottom",

theme_advanced_resizing : true

});

</c:if>


<c:choose>
<c:when test="${aCompoundView}">

	<c:forEach items="${list.sublistsBeans}" var="listBean" varStatus="varStatusLists">

	$('table#listId${listBean.id} a.alignColumn').click(function(){
		$("#form").append("<input type=\"hidden\" name=\"sublistId\" value=\"${listBean.id}\"/>");
		$("#form").append("<input type=\"hidden\" name=\"columnIndex\" value=\""+$("table#listId${listBean.id} a.alignColumn").index($(this))+"\"/>");
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"alignColumn\"/>");
		applyViewMode();
		$("#form").submit();
	});

	$('table#listId${listBean.id} a.boldColumn').click(function(){
		$("#form").append("<input type=\"hidden\" name=\"sublistId\" value=\"${listBean.id}\"/>");
		$("#form").append("<input type=\"hidden\" name=\"columnIndex\" value=\""+$("table#listId${listBean.id} a.boldColumn").index($(this))+"\"/>");
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"boldColumn\"/>");
		applyViewMode();
		$("#form").submit();
	});

	$('table#listId${listBean.id} a.wideColumn').click(function(){
		$("#form").append("<input type=\"hidden\" name=\"sublistId\" value=\"${listBean.id}\"/>");
		$("#form").append("<input type=\"hidden\" name=\"columnIndex\" value=\""+$("table#listId${listBean.id} a.wideColumn").index($(this))+"\"/>");
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"wideColumn\"/>");
		applyViewMode();
		$("#form").submit();
	});

	$('table#listId${listBean.id} a.narrowColumn').click(function(){
		$("#form").append("<input type=\"hidden\" name=\"sublistId\" value=\"${listBean.id}\"/>");
		$("#form").append("<input type=\"hidden\" name=\"columnIndex\" value=\""+$("table#listId${listBean.id} a.narrowColumn").index($(this))+"\"/>");
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"narrowColumn\"/>");
		applyViewMode();
		$("#form").submit();
	});

	$('table#listId${listBean.id} a.nobrColumn').click(function(){
		$("#form").append("<input type=\"hidden\" name=\"sublistId\" value=\"${listBean.id}\"/>");
		$("#form").append("<input type=\"hidden\" name=\"columnIndex\" value=\""+$("table#listId${listBean.id} a.nobrColumn").index($(this))+"\"/>");
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"nobrColumn\"/>");
		applyViewMode();
		$("#form").submit();
	});

	$('table#listId${listBean.id} a.alignDisplayName').click(function(){
		$("#form").append("<input type=\"hidden\" name=\"sublistId\" value=\"${listBean.id}\"/>");
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"alignDisplayName\"/>");
		applyViewMode();
		$("#form").submit();
	});

	$('table#listId${listBean.id} a.increaseBottomPadding').click(function(){
		$("#form").append("<input type=\"hidden\" name=\"sublistId\" value=\"${listBean.id}\"/>");
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"increaseBottomPadding\"/>");
		applyViewMode();
		$("#form").submit();
	});
	$('table#listId${listBean.id} a.decreaseBottomPadding').click(function(){
		$("#form").append("<input type=\"hidden\" name=\"sublistId\" value=\"${listBean.id}\"/>");
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"decreaseBottomPadding\"/>");
		applyViewMode();
		$("#form").submit();
	});
	$('table#listId${listBean.id} a.showTableHeader').click(function(){
		$("#form").append("<input type=\"hidden\" name=\"sublistId\" value=\"${listBean.id}\"/>");
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"showTableHeader\"/>");
		applyViewMode();
		$("#form").submit();
	});

	<c:if test="${editMode}">
	 $('.viewList').css("border","1px black dotted");
	 $('.viewListDark').css("border","1px black dotted");
 	 $('.viewListBright').css("border","1px black dotted");
	</c:if>

	</c:forEach>


</c:when>
<c:otherwise>

	$('.alignColumn').click(function(){
		$("#form").append("<input type=\"hidden\" name=\"columnIndex\" value=\""+$("table#listId${listBean.id} a.alignColumn").index($(this))+"\"/>");
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"alignColumn\"/>");
		applyViewMode();
		$("#form").submit();
	});

	$('.boldColumn').click(function(){
		$("#form").append("<input type=\"hidden\" name=\"columnIndex\" value=\""+$("table#listId${listBean.id} a.boldColumn").index($(this))+"\"/>");
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"boldColumn\"/>");
		applyViewMode();
		$("#form").submit();
	});

	$('.nobrColumn').click(function(){
		$("#form").append("<input type=\"hidden\" name=\"columnIndex\" value=\""+$("table#listId${listBean.id} a.nobrColumn").index($(this))+"\"/>");
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"nobrColumn\"/>");
		applyViewMode();
		$("#form").submit();
	});

	$('.wideColumn').click(function(){
		$("#form").append("<input type=\"hidden\" name=\"columnIndex\" value=\""+$("table#listId${listBean.id} a.wideColumn").index($(this))+"\"/>");
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"wideColumn\"/>");
		applyViewMode();
		$("#form").submit();
	});

	$('.narrowColumn').click(function(){
		$("#form").append("<input type=\"hidden\" name=\"columnIndex\" value=\""+$("table#listId${listBean.id} a.narrowColumn").index($(this))+"\"/>");
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"narrowColumn\"/>");
		applyViewMode();
		$("#form").submit();
	});

	$('.alignDisplayName').click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"alignDisplayName\"/>");
		applyViewMode();
		$("#form").submit();
	});

	$('.increaseBottomPadding').click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"increaseBottomPadding\"/>");
		applyViewMode();
		$("#form").submit();
	});
	$('.decreaseBottomPadding').click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"decreaseBottomPadding\"/>");
		applyViewMode();
		$("#form").submit();
	});
	$('.showTableHeader').click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"showTableHeader\"/>");
		applyViewMode();
		$("#form").submit();
	});
	$('.hideTableHeader').click(function(){
		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"hideTableHeader\"/>");
		applyViewMode();
		$("#form").submit();
	});

	<c:if test="${editMode}">
	 $('.viewList').css("border","1px black dotted");
	 $('.viewListDark').css("border","1px black dotted");
 	 $('.viewListBright').css("border","1px black dotted");
	</c:if>

	var personsMap;

	$("div.addEntity").hide();
	$("a.addEntity").click(function(){
		$.get('selectBoxFiller',{type:'${listType}'},function(data){
   	   		var entitiesMap = data.split(",,");
   	   		$("div.addEntity").show();
   	   		$("input.addEntity").autocomplete(entitiesMap, {align: 'right', dir: 'rtl', scroll: 'true', scrollHeight: 90});
   	   	});
   	});

   	$("a.addEntityAction").click(function(){
   		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"addEntity\"/>");
		applyViewMode();
		$("#form").submit();
	});

	$("input.addPerson").focus(function(){
		$("input.addPerson").unautocomplete();
		$("input.addPerson").autocomplete(personsMap, {align: 'right', dir: 'rtl', scroll: 'true', scrollHeight: 90});
	});

</c:otherwise>
</c:choose>

});
</script>
