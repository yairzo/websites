<%@ page  pageEncoding="UTF-8" %>

<authz:authorize ifAnyGranted="ROLE_LISTS_ADMIN">
				  <tr>
                  <td colspan="3"><img src="/image/hr.gif" width="580" height="10"></td>
   		        </tr>
				<tr>
	                <td colspan="3" align="center"><h1>הגדרות רשימה</h1>
       			    </td>
	             </tr>
	              <tr>
    	            <th colspan="3" align="right">ההגדרות מופיעות לפי שם טבלת המקור</th>
	   	          </tr>
	           	<c:forEach items="${listInstructions}" var="instruct">
	 				<tr>
				    	<td>
				    		<form:radiobutton path="listInstructionId" value="${instruct.id}"/>
			    			<c:out value="${instruct.selectsFrom}"></c:out>
			    		</td>
				    </tr>
				</c:forEach>
					<tr>
						<td colspan="3">
							<a style="text-decoration: none" href="/listInstruction.html?listId=<c:out value="${command.id}"/>"><button class="grey" >הוסף</button></a>
							<button class="grey" onclick="onEdit();">ערוך</button>
							<button class="grey" onclick="onDelete();">מחק</button>
						</td>
					</tr>
					<tr>
		                  <td colspan="3"><img src="/image/hr.gif" width="580" height="10"></td>
       		        </tr>
	      </authz:authorize>