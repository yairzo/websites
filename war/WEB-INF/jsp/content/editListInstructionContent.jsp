<%@ page  pageEncoding="UTF-8" %>

<script type="text/javascript">


$(document).ready(function() {

	$('.cancel').click(function(){
		$('form#form').append('<input type=\"hidden\" name=\"action\" value=\"cancel\"/>');
		$('form#form').submit();
    });

    $('input.pageReloader').change(function() {
    	$('form#form').append('<input type=\"hidden\" name=\"action\" value=\"save\"/>');
		$('form#form').submit();
    });
});

</script>


         <tr>
          <td align="right" bgcolor="#787669" height="20">
          		<c:set var="applicationName" value="מערכת רשימות"/>
          	        <c:set var="pageName" value="עריכה של הגדרת טבלאות של רשימה"/>
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
            <form:form id="form" action="listInstruction.html" method="POST" commandName="command">

				<form:hidden path="id"/>
				<form:hidden path="listId"/>
				<form:hidden path="master"/>

              <table width="600" border="0" align="center" cellpadding="2" cellspacing="0">

                <tr>
                  <td colspan="3" align="center"><h1>עריכת הגדרה של רשימה</h1>
                  </td>
                </tr>
                <c:if test="${noSuchTable}">
					<tr>
						<td colspan="3">
							<font color="red"> הטבלה/אות הבאות לא קיימות במאגר הנתונים: <c:out value="${errTableName}"/> <c:out value="${errSubTableName}"/></font>
						</td>
					</tr>
				</c:if>

                <tr class="form">
					<td width="150">
						טבלת מקור ראשית:
					</td>
					<td width="120">
						<form:input  cssClass="pageReloader green" path="selectsFrom"/>
					</td>
					<td width="330">
						&nbsp;
						<form:errors cssClass="errors" path="selectsFrom"/>
					</td>
				</tr>
				<tr class="form">
					<td>
						טבלאות משנה וטור מקשר לטבלה הראשית:
					</td>
					<td>
						<form:input  cssClass="pageReloader green" path="subTables" onchange="reloadPage()"/>
					</td>
					<td>
						<form:errors cssClass="errors" path="subTables"/>
					</td>
				</tr>
				<tr class="form">

					<td>
						הרכב הטורים:
					</td>
					<td>
						<form:textarea  cssStyle="text-align: left" cols="80" rows="7" cssClass="green" path="columnsSelection"/>
					</td>
					<td>
						<form:errors cssClass="errors" path="columnsSelection"/>
					</td>
				</tr>

				<tr class="form">
					<td>
						טור ממיין (ברירת מחדל):
					</td>
					<td>
						<form:input  cssClass="green" path="defaultOrderByColumn"/>
					</td>
					<td>
						<form:errors cssClass="errors" path="defaultOrderByColumn"/>
					</td>
				</tr>

				<tr class="form">
					<td>
						כיוון המיון (ברירת המחדל):
					</td>
					<td>
						<form:input cssStyle="" cssClass="green" path="defaultOrderDirection"/>
					</td>
					<td>
						<form:errors cssClass="errors" path="defaultOrderDirection"/>
					</td>
					</tr>


					<tr class="form">
						<td>
							תנאי להצגה:
						</td>
						<td>
							<form:input  cssClass="green" path="partialViewConditions"/>
						</td>
						<td>
							<form:errors cssClass="errors" path="partialViewConditions"/>
						</td>
						</tr>
		                <tr>
        		          <td colspan="2" align="right">
                   			<table border="0" cellpadding="0" cellspacing="0">
		                      <tr>
           			            <td><button class="grey" onclick="onSave();">שמור</button></td>
           			            <td><button class="cancel grey">סיים</button></td>
		                      </tr>
		                    </table>
           			     </td>
		                </tr>

        		        <c:if test="${!command.master}">

        		        <tr>
		                  <td colspan="3"><img src="/listsManager/images/hr.gif" width="580" height="10"></td>
        		        </tr>

        		        <tr>
        		        	<th colspan="3">
		<div align="right">הרכב הטורים של הרשימה לפי ההגדרה הראשית של הרשימה הוא:</div>

        		        		<div align="left">
        		        		<c:forEach items="${mastersColumnsSelection}" var="column">
        		        			<c:out value="${column}"/>,
        		        		</c:forEach>
        		        		</div>

        		        		<div align="right">יש לשמור על הרכב טורים זהה.</div>

        		        	</th>
        		        </tr>

        		        </c:if>

        		        <tr>
		                  <td colspan="3"><img src="/listsManager/images/hr.gif" width="580" height="10"></td>
        		        </tr>

	              </table>

            <table width="600" border="0" align="center" cellpadding="3">
				 <c:choose>
				 <c:when test="${fn:length(selectsFromColumnsList)>0}">
	              <tr>
    	            <td colspan="3" align="center"><h1>טורים אפשריים להצגה מהטבלה ותת הטבלאות שצויינו למעלה</h1>
	                </td>
    	          </tr>
    	          <tr>
    	          <td colspan="3" align="right">
    	          	כאשר יש כפילות בשמות הטורים בין טבלת האם לטבלאות הבת יש לבחור תמיד את הטור מטבלת הבת
    	          	</td>
    	          	</tr>

    	          </c:when>
    	          <c:otherwise>
    	          	<tr>
    	            <td colspan="3" align="center"><h1>כאן תופיע רשימת טורים אפשריים להצגה מהטבלה ותת הטבלאות שיצוינו למעלה </h1>
	                </td>
    	          </tr>
    	          </c:otherwise>
    	          </c:choose>

        	   	<c:forEach items="${selectsFromColumnsList}" var="column" varStatus="varStatus">
					<tr class="<c:choose><c:when test="${varStatus.index%2==0}">darker</c:when><c:otherwise>brighter</c:otherwise></c:choose>">
						<td>
							<c:out value="${column}"></c:out>
						</td>
					</tr>
				</c:forEach>

            </table>
            <br>
          </td>
        </tr>
      </table>

      </form:form>
    </td>
  </tr>

</table>


</body>
</html>