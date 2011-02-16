<%@ page  pageEncoding="UTF-8" %>
<%-- begin of funding agency response --%>

        		<c:if test="${command.proposalBean.stateId >= APPROVED_BY_ALL }">

        			<tr>
			             <td colspan="2" >
			             	&nbsp;
			             </td>
        		     </tr>




					<authz:authorize ifAnyGranted="ROLE_EQF_MOP">
					<c:choose>
					<c:when test="${command.proposalBean.stateId == APPROVED_BY_ALL && !viewDetails}">

						<tr>
							<th align="right">
     								תשובת הקרן:
 							</th>
 							<td>
								<form:select cssClass="fundingAgencyApproved green" path="proposalBean.fundingAgencyApproved">
									<form:option value="-1">בחר/י תשובה</form:option>
 									<form:option value="1">ההצעה אושרה</form:option>
									<form:option value="2">ההצעה נדחתה בציון גבוה</form:option>
									<form:option value="0">ההצעה נדחתה</form:option>
								</form:select>
 							</td>
 						</tr>

 						<tr>
        		        	<td colspan="2">
        		        		<span class="formError"><form:errors path="proposalBean.fundingAgencyDetails" /></span>
        		        	</td>
        		        </tr>
 					</c:when>
 					<c:otherwise>
 						<c:if test="${!viewDetails}">
 						<form:hidden path="proposalBean.fundingAgencyApproved"/>
 						</c:if>
 					<tr>
							<th align="right">
     								תשובת הקרן:
 							</th>
 							<th align="right">
 								<c:choose>
 									<c:when test="${command.proposalBean.fundingAgencyApproved==1}">
 										ההצעה אושרה על-ידי הקרן
 									</c:when>
 									<c:when test="${command.proposalBean.fundingAgencyApproved==2}">
 										ההצעה נדחתה על-ידי הקרן בציון גבוה
 									</c:when>
 									<c:otherwise>
 										ההצעה נדחתה על-ידי הקרן
 									</c:otherwise>
 								</c:choose>
 							</th>
 						</tr>
 					</c:otherwise>
 					</c:choose>
 					</authz:authorize>
 					<authz:authorize ifNotGranted="ROLE_EQF_MOP">
 					<c:if test="${!viewDetails}">
 						<form:hidden path="proposalBean.fundingAgencyApproved"/>
 					</c:if>
 					<c:choose>
 						<c:when test="${command.proposalBean.stateId == APPROVED_BY_ALL }">
							<tr>
								<th align="right" colspan="2">
 							ההצעה ממתינה לתשובת הקרן.&nbsp;&nbsp; התשובה תעודכן על-ידי הרשות למו"פ.
 								</th>
 							</tr>
 						</c:when>
 						<c:otherwise>
 					<tr>
							<th align="right">
     								תשובת הקרן:
 							</th>
 							<th align="right">
 								<c:choose>
 									<c:when test="${command.proposalBean.fundingAgencyApproved==1}">
 										ההצעה אושרה על-ידי הקרן
 									</c:when>
 									<c:when test="${command.proposalBean.fundingAgencyApproved==2}">
 										ההצעה נדחתה על-ידי הקרן בציון גבוה
 									</c:when>
 									<c:otherwise>
 										ההצעה נדחתה על-ידי הקרן
 									</c:otherwise>
 								</c:choose>
 							</th>
 						</tr>
 					 </c:otherwise>
 					</c:choose>
 					</authz:authorize>
 				</c:if>

 				<%-- end of funding agency response --%>