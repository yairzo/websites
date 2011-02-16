<%@ page  pageEncoding="UTF-8" %>

<script type="text/javascript">

$(document).ready(function() {


   	var institutesNamesMap = "<c:forEach items="${institutes}" var="entry"><c:out value="${entry.key}"/>,,</c:forEach>".split(",,");
   	$("#instituteName").autocomplete(institutesNamesMap, {align: 'left'});


   	 $('.cancel').click(function(){
		document.location = "proposals.html";
    	return false;
     });

});


</script>




<tr>
	<td align="right" bgcolor="#787669" height="20">
		<c:set var="applicationName" value="מערכת אינטרנט הרשות למו\"פ"/>
          	        <c:set var="pageName" value="עריכה של פרטי שותף חיצוני"/>
       	          	<%@ include file="/WEB-INF/jsp/include/locationMenu.jsp" %>
	</td>
</tr>
</table>

</td>
</tr>
<tr>
	<td>
	<table width="700" border="1" align="center" cellpadding="0"
		cellspacing="0" bordercolor="#767468" dir="rtl">






		<tr>
			<td valign="top" align="center"><br>
			<form:form name="form" method="POST" action="partner.html"
				commandName="command">


				<form:hidden path="id" />




				<table width="600" border="0" align="center" cellpadding="2"
					cellspacing="0">

					<tr>
						<td>
							שם:
						</td>
						<td>
							<form:input path="name"/>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>

					<tr>
						<td valign="top">
							תואר
						</td>
						<td>
							<form:select path="degree">
								<form:option value="Prof.">Prof.</form:option>
								<form:option value="Dr.">Dr.</form:option>
								<form:option value="Mrs.">Mrs.</form:option>
								<form:option value="Mr.">Mr.</form:option>
							</form:select>
						</td>
					</tr>

					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>

					<tr>
						<td>
							מוסד אקדמי
						</td>
						<td>
							<form:input  path="instituteName"/>
						</td>
					</tr>



					<tr>
						<td colspan="2">&nbsp;<br>
						<img src="image/hr.gif" width="580" height="10"><br>
						&nbsp;</td>
					</tr>

					<tr>
						<td>
						<button class="save grey">שמור</button>

						&nbsp;&nbsp;

						<button class="cancel grey">סיים</button>
						</td>
					</tr>




				</table>

				<br></td>
		</tr>
	</table>

	</form:form></td>
</tr>

</table>


</body>
</html>
