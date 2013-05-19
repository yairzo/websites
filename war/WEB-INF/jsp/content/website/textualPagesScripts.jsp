<%@ page  pageEncoding="UTF-8" %>
<script>
$(document).ready(function() {

<%@ include file="/WEB-INF/jsp/include/searchPaginationScripts.jsp" %>
$('button.search').click(function(){
	if($("#searchDeleted").is(":checked"))
		$("#form").append("<input type=\"hidden\" name=\"deleted\" value=\"true\"/>");
	else
		$("#form").append("<input type=\"hidden\" name=\"deleted\" value=\"false\"/>");
	if($("#searchList").is(":checked"))
		$("#form").append("<input type=\"hidden\" name=\"isList\" value=\"true\"/>");
	else
		$("#form").append("<input type=\"hidden\" name=\"isList\" value=\"false\"/>");
	$('form#form').append('<input type=\"hidden\" name=\"action\" value=\"search\"/>');
	$('form#form').submit();
});
});
</script>