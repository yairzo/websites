<%@ page  pageEncoding="UTF-8" %>

<script type="text/javascript">

$(document).ready(function() {

	$('.cancel').click(function(){
		document.location = "temporaryFunds.html";
    	return false;
     });
     $('.save').click(function(){
 		$("#form").append("<input type=\"hidden\" name=\"action\" value=\"edit\"/>");
     	$("#form").submit();
		return false;

    });
     $(".delete").click(function(){
     	//alert("Deleting attribution");
     	$("form#form").append("<input type=\"hidden\" name=\"action\" value=\"delete\"/>");
     	$("form#form").submit();
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
			<form:form name="form" id="form"  method="POST" action="temporaryFund.html"	commandName="command">


				<form:hidden path="id" />
				<form:hidden path="temporary" />




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
							שם מקוצר:
						</td>
						<td>
							<form:input path="shortName"/>
						</td>
					</tr>

					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>

					<tr>
						<td>מדור:
						</td>
						<td>
         				<form:select path="deskId" id="deskId" cssClass="green deskId" >
      						<form:option value="0">בחר/י</form:option>
       						<c:forEach items="${mopDesks}" var="mopDesk">
	        					<form:option htmlEscape="true" value="${mopDesk.id}"><c:out escapeXml="false" value="${mopDesk.hebrewName}"/></form:option>
       						</c:forEach>
        		        </form:select>
						</td>
					</tr>

					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>
					<tr>
						<td>מספר מזהה במערכת הכספים:
						</td>
						<td>
							<form:input path="financialId"/>
						</td>
					</tr>


					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>
					<tr>
						<td valign="top">
							כתובת אתר:
						</td>
						<td>
							<form:input path="webAddress"/>
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
