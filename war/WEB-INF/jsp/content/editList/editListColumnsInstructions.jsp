<%@ page  pageEncoding="UTF-8" %>

			<authz:authorize ifAnyGranted="ROLE_LISTS_ADMIN">

					<tr>
		                  <td colspan="3"><img src="/image/hr.gif" width="580" height="10"></td>
        		        </tr>
        		        <tr>
                <td colspan="3" align="center"><h1>עריכת הגדרות טורים</h1>
                </td>
              </tr>
              <tr>
              	<th colspan="3" align="right">
              		הגדרות הטורים:
				</th>
				</tr>

              <c:forEach items="${columnInstructions}" var="colInstruct">
				<tr>
			    	<td colspan="3">
			    		<form:radiobutton path="listColumnInstructionId" value="${colInstruct.id}"/>
			    		<c:choose>
			    		<c:when test="${colInstruct.actual}">
				    		<c:out value="${colInstruct.columnName}"></c:out>&nbsp;<c:if test="${! colInstruct.manuallyEdited}">- נוצר אוטומטית - יש לעורכו</c:if>
				    	</c:when>
				    	<c:otherwise>
				    		<c:out value="${colInstruct.columnName}"></c:out>&nbsp;-לא מופיע בהגדרות הרשימה - כדאי למחקו
				    	</c:otherwise>
				    	</c:choose>
			    	</td>
			    </tr>
			</c:forEach>
			<tr>
						<td colspan="3">
							<a style="text-decoration: none" href="/listColumnInstruction.html?listId=<c:out value="${command.id}"/>"><button class="grey" >הוסף</button></a>
							<button class="grey" onclick="onColumnEdit();">ערוך</button>
							<button class="grey" onclick="onColumnDelete();">מחק</button>
						</td>
					</tr>
		</authz:authorize>