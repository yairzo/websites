<%@ page  pageEncoding="UTF-8" %>
<script type="text/javascript">


$(document).ready(function() {

	$('.cancel').click(function(){
		$('form#form').append('<input type=\"hidden\" name=\"action\" value=\"cancel\"/>');
		$('form#form').submit();
    });
    $('.pageReloader').click(function(){
		$('form#form').submit();
    });
});
</script>

         <tr>
          <td align="right" bgcolor="#787669" height="20">
				<c:set var="applicationName" value="מערכת רשימות"/>
          	        <c:set var="pageName" value="עריכה של הגדרות טורי רשימה"/>
       	          	<%@ include file="/WEB-INF/jsp/include/locationMenu.jsp" %>
			</td>
        </tr>
	</table>

    </td>
  </tr>
  <tr>
    <td>
      <table width="700" border="1" align="center" cellpadding="0" cellspacing="0" bordercolor="#767468" dir="rtl">
        <tr>
          <td valign="top" align="center"><br>
            <form:form id="form" action="listColumnInstruction.html" name="form" method="POST" commandName="command">


					<form:hidden path="id"/>

					<form:hidden path="listId"/>

              <table width="600" border="0" align="center" cellpadding="2" cellspacing="0">

                <tr>
                  <td colspan="3" align="center"><h1>עריכת פרטי טור של רשימה</h1>
                  </td>
                </tr>
                <tr class="form">
					<td width="180">
						שם הטור:
					</td>
					<td width="270">
						<c:choose>
							<c:when test="${command.id == 0}">
								<form:input cssClass="green" path="columnName"/>
						</c:when>
						<c:otherwise>
							<c:out value="${command.columnName}"/>
							<form:hidden path="columnName"/>
						</c:otherwise>
					</c:choose>
					</td>

					<td width="150">
						&nbsp;<form:errors cssClass="errors" path="columnName"/>
					</td>

				</tr>
				<tr class="form">
					<td>
						הטור חבוי:
					</td>
					<td>
						<form:checkbox  id="hidden" cssClass="pageReloader green"  path="hidden"/>
					</td>
					<td>
						&nbsp;<form:errors cssClass="errors" path="hidden"/>
					</td>
				</tr>
			<c:if test="${!command.hidden}">
				<tr class="form">
					<td>
						שם התצוגה של הטור:
					</td>
					<td>
						<form:input  htmlEscape="true" cssClass="green" path="columnDisplayName"/>
					</td>
					<td>
						&nbsp;<form:errors cssClass="errors" path="columnDisplayName"/>
					</td>
				</tr>
				<tr class="form">
					<td>
						סידור ברירת המחדל של הטור:
					</td>
					<td>
						<form:input  cssClass="green" path="orderBy"/>
					</td>
					<td>
						&nbsp;<form:errors cssClass="errors" path="orderBy"/>
					</td>
				</tr>

				<tr class="form">
					<td>
						רוחב הטור:
					</td>
					<td>
						<form:input  cssClass="green" path="width"/>
					</td>
					<td>
						&nbsp;<form:errors cssClass="errors" path="width"/>
					</td>
				</tr>

				<tr class="form">
					<td>
						הצמדה:
					</td>
					<td>
						<form:input  cssClass="green" path="align"/>
					</td>
					<td>
						&nbsp;<form:errors cssClass="errors" path="align"/>
					</td>
				</tr>
				<tr class="form">
					<td>
						ערכים מודגשים:
					</td>
					<td>
						<form:checkbox cssClass="green" path="bold"/>
					</td>
					<td>
						&nbsp;<form:errors cssClass="errors" path="bold"/>
					</td>
				</tr>

				<tr class="form">
					<td>
						טור זה משמש לקישור לכתובת דואל:
					</td>
					<td>
						<form:checkbox  cssClass="green" path="mailAddress"/>
					</td>
					<td>
						&nbsp;<form:errors cssClass="errors" path="mailAddress"/>
					</td>
				</tr>
				<tr class="form">
					<td>
						 טור זה משמש לקישור לכתובת אינטרנט:
					</td>
					<td>
						<form:checkbox  cssClass="green" path="webAddress"/>
					</td>
					<td>
						&nbsp;<form:errors cssClass="errors" path="webAddress"/>
					</td>
				</tr>
				<tr class="form">
					<td>
						 טור זה משמש לקישור לתמונה:
					</td>
					<td>
						<form:checkbox  cssClass="green" path="image"/>
					</td>
					<td>
						&nbsp;<form:errors cssClass="errors" path="image"/>
					</td>
				</tr>

				<tr class="form">
					<td>
						הצג את תוכן הטור כקישור שכתובתו לקוחה מהטור הבא:
					</td>
					<td>
						<form:input  cssClass="green" path="linkTargetFromColumn"/>
					</td>
					<td>
						&nbsp;<form:errors cssClass="errors" path="linkTargetFromColumn"/>
					</td>
				</tr>
				<tr class="form">
					<td>
						אפשר מיון:
					</td>
					<td>
						<form:checkbox cssClass="green" path="sortable"/>
					</td>
					<td>
						&nbsp;<form:errors cssClass="errors" path="sortable"/>
					</td>
				</tr>

			</c:if>

			<tr class="form">
					<td>
						השתמש בטבלת עזר לתרגום ערכי הטור:
					</td>
					<td>
						<form:checkbox  id="useHelperTable" cssClass="green"  path="useHelperTable" />
					</td>
					<td>
						&nbsp;<form:errors cssClass="errors" path="useHelperTable"/>
					</td>
				</tr>

				<tr class="form">
					<td>
						שם טבלת העזר לשימוש הטור:
					</td>
					<td>
						<form:input  id="helperTableName" cssClass="green"  path="helperTableName" />
					</td>
					<td>
						&nbsp;<form:errors cssClass="errors" path="helperTableName"/>
					</td>
				</tr>

				<tr class="form">
					<td>
						שם הטור להצגה מטבלת העזר:
					</td>
					<td>
						<form:input  id="helperTableDisplayColumnName" cssClass="green"  path="helperTableDisplayColumnName" />
					</td>
					<td>
						&nbsp;<form:errors cssClass="errors" path="helperTableDisplayColumnName"/>
					</td>
				</tr>


				        <tr>
        		          <td colspan="3" align="right">
        		          	<table>
        		          		<tr>
        		          			<td>
                   						<button class="grey" onclick="submit()">שמור</button>
                   					</td>
                   					<td>
                   						<td><button class="cancel grey">סיים</button></td>
                   					</td>
                   				</tr>
                   			</table>
           			     </td>
		                </tr>
	              </table>
             </td>
        </tr>
      </table>

      </form:form>
    </td>
  </tr>

</table>


</body>
</html>