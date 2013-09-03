<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
        <script>window.jQuery || document.write('<script src="js/jquery-1.8.3.min.js"><\/script>')</script>

<script type="text/javascript">

$(document).ready(function() {

	

	$(".viewAll").click(function(e) {
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
