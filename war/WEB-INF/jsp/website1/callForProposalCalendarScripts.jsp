<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>

<script src="js/jquery-1.10.2.min.js"></script>

<script type="text/javascript">

$(document).ready(function() {

	$(document).click(function() {
		$(".callForProposalsPerDay").each(function (e){
			if($(this).css('display')=='block')
				$(this).hide();
		});
	});
	
	$(".viewAll").click(function(e) {
		e.stopPropagation();
		e.preventDefault();
		if($(".callForProposalsPerDay", $(this).closest("td")).css('display')=='block')
			$(".callForProposalsPerDay").hide();
		else{
			$(".callForProposalsPerDay").hide();
			if($(".clearfix", $(this).closest("td")).html().length>21){
				$(".callForProposalsPerDay", $(this).closest("td")).show();
				var rowPos = $(this).position();
				bottomTop = rowPos.top - $(".callForProposalsPerDay", $(this).closest("td")).height();
				bottomLeft = rowPos.left - 100;
				$(".callForProposalsPerDay").css({
			   	 	top: bottomTop,
			    	left: bottomLeft
				});
			}
		}
	});	
	
	
	$(".callForProposalsPerDay").click(function(e) {
		e.preventDefault();
		if($(".callForProposalsPerDay", $(this).closest("td")).css('display')=='block')
			$(".callForProposalsPerDay", $(this).closest("td")).hide();
	});	

	
	$(".viewProposal").click(function(e) {
		e.preventDefault();
		var proposalId=$(this).attr("id");	
		window.open('callForProposal.html?id='+proposalId+'&t=1');
	});	
});




</script>
