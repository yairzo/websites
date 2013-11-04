<%@ page  pageEncoding="UTF-8" %>

<script type="text/javascript">

$(document).ready(function() {

	<c:if test="${instituteBean.countryId>0}">
		$("#country").hide();
	</c:if>

    $("select#continent").change(function(){
    	$("#country").show();
    	var continentId= $("select#continent").val();
    	$("#countrySelect").empty();
    	$("#countrySelect").load("selectBoxFiller?type=country&continentId="+continentId);
    });

    $('.cancel').click(function(){
		document.location = "proposals.html";
    	return false;
     });

});

</script>



<tr>
	<td align="right" bgcolor="#787669" height="20">
		<c:set var="applicationName" value="מערכת אינטרנט הרשות למו"פ"/>
        <c:set var="pageName" value="עריכת פרטים של מוסד חיצוני"/>
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
			<form:form name="form" method="POST" action="institute.html"
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
							עיר:
						</td>
						<td>
							<form:input path="city"/>
						</td>
					</tr>

					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>

					<tr>
						<td>
							מדינה (ארה"ב בלבד)
						</td>
						<td>
							<form:input  path="state"/>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>

					<tr>
							<td>
								 יבשת:
							</td>
							<td>
        		        		<form:select id="continent" path="continentId" cssClass="green">
        		        			<form:option value="0">בחר/י</form:option>
        		        			<c:forEach items="${continents}" var="continent">
										<form:option value="${continent.id}"><c:out value="${continent.name}"/></form:option>
        		        			</c:forEach>
        		        		</form:select>
        		        	</td>
						</tr>
						<tr id="country">
							<td>
								 מדינה:
							</td>
							<td>
        		        		<form:select id="countrySelect" path="countryId" cssClass="green">
        		        			<c:forEach items="${countries}" var="country">
										<form:option value="${country.id}"><c:out value="${country.name}"/></form:option>
        		        			</c:forEach>
        		        		</form:select>
        		        	</td>
						</tr>





					<tr>
						<td colspan="2">&nbsp;<br>
						<img src="/image/hr.gif" width="580" height="10"><br>
						&nbsp;</td>
					</tr>

					<tr>
						<td>
						<button class="save" class="grey" onclick="">שמור</button>

						&nbsp;&nbsp;

						<button class="cancel" class="grey">סיים</button>
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
