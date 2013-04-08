<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>

<script type="text/javascript">

$(document).ready(function() {

	var dlg =$("#genericDialog").dialog({
	    autoOpen: false,
	    show: 'fade',
	    hide: 'fade',
	    modal: true,
	    open: function() { $(".ui-dialog").css("box-shadow","#000 5px 5px 5px");}
	});
	dlg.parent().appendTo($("#form"));
	
	$(".viewProposal").click(function(e) {
		e.preventDefault();
		
		var proposalId=$(this).attr("id");	
		dlg.dialog('option', 'buttons', {
        	"סגור" : function() {
        		dlg.dialog("close");
        	}
		});
		dlg.dialog({ modal: true });
		dlg.dialog({ height: 700 });
		dlg.dialog({ width: 700 });
		dlg.dialog("option", "position", "center");
		$.get('callForProposal.html?id='+proposalId, function(data) {
			dlg.html(data).dialog("open");
		});
	});	
	
	$(".viewAll").click(function(e) {
		e.preventDefault();
		if($(".moreCalls", $(this).closest("td")).css('display')=='block')
			$(".moreCalls", $(this).closest("td")).hide();
		else
			$(".moreCalls", $(this).closest("td")).show();
	});	
	$(".moreCalls").click(function(e) {
		e.preventDefault();
		if($(".moreCalls", $(this).closest("td")).css('display')=='block')
			$(".moreCalls", $(this).closest("td")).hide();
	});	

});




</script>
