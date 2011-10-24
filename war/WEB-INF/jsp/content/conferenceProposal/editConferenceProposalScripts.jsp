<script language="Javascript">

<c:if test="${userMessage!=null}">
var userMessage = "${userMessage}";
$.alerts.alert(userMessage);
</c:if>

</script>