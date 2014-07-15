<%@ page  pageEncoding="UTF-8" %>
<script language="Javascript">

function checkThis(checkbox)
{
	jQuery.ajax({url : "articleAction.html?actionCommand=changeVisibility&articleId="+checkbox.name});
}

</script>