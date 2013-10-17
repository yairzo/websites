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
			if(jQuery.trim($(".clearfix", $(this).closest("td")).html())){			
				$(".callForProposalsPerDay", $(this).closest("td")).show();
				
				var top_bottom = $(".callForProposalsPerDay", $(this).closest("td")).attr('class');
				top_bottom = top_bottom.split(' ')[1];
				var edge_middle = $(".callForProposalsPerDay", $(this).closest("td")).attr('class');
				edge_middle = edge_middle.split(' ')[2];
				
				var rowPos = $(this).closest("td").position();
				
				var triangle_class = "triangle_";
				
				if (top_bottom == "bottom"){
					bottom_top_height = rowPos.top - $(".callForProposalsPerDay", $(this).closest("td")).height();
					triangle_class += "down_";
				}
				else{
					bottom_top_height = rowPos.top + $(this).closest("td").height();
					triangle_class += "up_";
				}
				
				bottomCorner = rowPos.left;
				
				<c:if test="${lang.rtl}">
					bottomCorner = bottomCorner - $(this).closest("td").width() - 20;
				</c:if>
				
				
				
				if (edge_middle == "edge"){
					triangle_class += "${lang.opDir}";
					<c:choose>
					<c:when test="${lang.rtl}">
						bottomCorner = bottomCorner + 120;
					</c:when>
					<c:otherwise>
						bottomCorner = bottomCorner - 120;
					</c:otherwise>
					</c:choose>
				}
				else{
					triangle_class += "${lang.dir}";
				}
				
				$(".callForProposalsPerDay", $(this).closest("td")).find(".triangle").addClass(triangle_class);
				
				$(".callForProposalsPerDay").css({
			   	 	top: bottom_top_height,
			    	left: bottomCorner
				});
			}
		}
		e.stopPropagation()
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
