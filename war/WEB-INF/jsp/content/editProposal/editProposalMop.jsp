<%@ page  pageEncoding="UTF-8" %>
<%-- begin of dean approval --%>

				<c:if test="${command.proposalBean.stateId >= DEAN_APPROVED}">

				<tr>
							<td colspan="2" >
								&nbsp;
							</td>
        				</tr>
        				<tr>





 				<c:choose>
 				<c:when test="${command.proposalBean.stateId == DEAN_APPROVED}">

 				<c:choose>
 						<c:when test="${command.proposalBean.personProposalBean.typeId == MOP_DESK}">

        		        <c:choose>
        		        	<c:when test="${!viewDetails}">
        		        <tr>
        		        	<th align="right">
        		        		העברה לתקציבאי לפתיחת תקציב
        		        	</th>
        		        	<td>
        		        			<form:select id="budgetOfficerSelect" cssClass="green" path="proposalBean.budgetOfficerId">
        		        				<form:option value="0">בחר תקציבאי לטיפול בפתיחת התקציב</form:option>
        		        					<c:forEach items="${budgetOfficers}" var="budgetOfficer">
        		        							<form:option value="${budgetOfficer.id}"> <c:out value="${budgetOfficer.degreeFullNameHebrew}"/> </form:option>
        		        					</c:forEach>
        		        			</form:select>

        		        	</td>
        		        </tr>
        		        	</c:when>
        		        	<c:otherwise>
        		        		<tr>
        		        			<td colspan="2">
        		        		יש להעביר לתקציבאי לפתיחת תקציב
        		        			</td>
        		        		</tr>
        		        	</c:otherwise>
        		        </c:choose>

        		       </c:when>
        		       <c:otherwise>
							<tr>
        		        	<th align="right">
        		        			יש להמתין לפתיחת התקציב ברשות למו"פ
        		        	</th>
        		        </tr>
					  </c:otherwise>

        		      </c:choose>
        		    </c:when>
        		    <c:when test="${command.proposalBean.stateId > DEAN_APPROVED}">

        		    	<tr>
        		    		<th align="right">
        		    			תקציבאי/ת מטפל/ת:
        		    		</th>
        		    		<th align="right">
        		    			<c:out value="${command.proposalBean.budgetOfficer.degreeFullNameHebrew}"/>
        		    		</th>
        		    	</tr>
        		    	<c:choose>
        		    	<c:when test="${command.proposalBean.stateId == PASSED_MOP_BUDGET_OFFICER
        		    		&& command.proposalBean.personProposalBean.typeId == BUDGET_OFFICER && !viewDetails}">
        		    			<tr>
        		    				<th align="right">
        		    					מספר תקציב:
        		    				</th>
        		    				<td align="right">
        		    					<form:input id="budgetHujiId" path="proposalBean.budgetHujiId" cssClass="green"/>
        		    				</td>
        		    			</tr>
        		    			<tr>
        		    				<th align="right" colspan="2">
										<button class="budgetApproved grey">מאשר כי נפתח תקציב וכי זהו מספרו</button>
        		    				</th>
        		    			</tr>
        		    	</c:when>
        		    	<c:otherwise>
        		    		<tr>
        		    				<th align="right">
        		    					מספר תקציב:
        		    				</th>
        		    				<th align="right">
        		    					<c:out value="${command.proposalBean.budgetHujiId}"/>
        		    					<c:if test="${command.proposalBean.stateId < MOP_BUDGET_OPENED}">
        		    						טרם נפתח תקציב
        		    					</c:if>
        		    				</th>
        		    			</tr>
        		    	</c:otherwise>
        		    	</c:choose>


        		    </c:when>
        		    </c:choose>

				</c:if>
