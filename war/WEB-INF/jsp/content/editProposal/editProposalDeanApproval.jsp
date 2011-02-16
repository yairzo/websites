<%@ page  pageEncoding="UTF-8" %>
<%-- begin of dean approval --%>

				<c:if test="${command.proposalBean.stateId >= EXPERIMENT_APPROVED}">

						<tr>
							<td colspan="2" >
								&nbsp;
							</td>
        				</tr>
        				<tr>

        		<authz:authorize ifNotGranted="ROLE_EQF_DEAN">


 				<c:if test="${command.proposalBean.stateId == EXPERIMENT_APPROVED}">

 					<c:choose>
 						<c:when test="${viewDetails}">
 							<tr>
 								<td colspan="2">
 									על החוקר האחראי להביר את ההצעה לאישור הגורם המאשר
 								</td>

 							</tr>
 						</c:when>

 						<c:when test="${command.proposalBean.personProposalBean.typeId == MAIN_RESEARCHER}">

			            <tr id="deanApproval">
			        	   	<td colspan="2">
			        	       	<table>
			        	      		<tr>
        				       			<th align="right" class="medium300">
		        		   					בחירת הגורם המאשר:
		        		   				</tי>
   							        	<td>
        		        							<form:select id="deanSelect"  path="proposalBean.deanId" cssClass="green">
        		        								<form:option value="0">בחר/י גורם מאשר</form:option>
        		        								<c:forEach items="${deans}" var="deanPerson">
        		        									<form:option htmlEscape="true" value="${deanPerson.id}" ><c:out escapeXml="false" value="${deanPerson.degreeFullNameHebrew}"/> - <c:out escapeXml="false" value="${deanPerson.title}"/></form:option>
        		        								</c:forEach>
			        		        				</form:select>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<a href="">	הגורם המאשר הנדרש לא מופיע ברשימה</a>
										</td>
									</tr>
								</table>
							</td>
						</tr>


						</c:when>
						<c:otherwise>


							<tr>
								<th align="right" colspan="2">
											 יש להמתין שהחוקר האחראי ישלח את ההצעה לאישור הגורם המאשר - והגורם המאשר יזין את תשובתו
								</th>
							</tr>
						</c:otherwise>
						</c:choose>
					</c:if>





						<c:if test="${command.proposalBean.stateId >= DEAN_WAIT }">

							<c:if test="${!viewDetails}">
								<form:hidden path="proposalBean.deanId"/>
								<form:hidden path="proposalBean.deanApproved"/>
							</c:if>



						<tr>
							<th  align="right" colspan="2">
								  הצעת המחקר
									<c:choose>
										<c:when test="${command.proposalBean.stateId == DEAN_WAIT}">
											<fmt:message key="iw_IL.eqfSystem.editProposal.deanApprovalState.0"></fmt:message>
										</c:when>
										<c:when test="${command.proposalBean.stateId >= DEAN_APPROVED}">
											<fmt:message key="iw_IL.eqfSystem.editProposal.deanApprovalState.1"></fmt:message>
										</c:when>
										<c:otherwise>
											<fmt:message key="iw_IL.eqfSystem.editProposal.deanApprovalState.2"></fmt:message>
											<br>
											סיבת הדחייה:  &nbsp; <span style="font-weight: normal"><c:out value="${command.proposalBean.deanRefusalDetails }"/></span>
										</c:otherwise>
									</c:choose>
									&nbsp;<c:out value="${command.proposalBean.dean.degreeFullNameHebrew}"/> - <c:out value="${command.proposalBean.dean.title}"/>

							</td>
						</tr>

					</c:if>


						</authz:authorize>

						<authz:authorize ifAnyGranted="ROLE_EQF_DEAN">
							<c:choose>
							<c:when test="${command.proposalBean.stateId  == DEAN_WAIT && !viewDetails}">
						<tr>
        		        	<th align="right" class="medium300">
			        	    		שם הדיקן/מנהל ביה"ס או נציגו:
    	    			   	</th>
			        	   	<th align="right" class="medium300">
        				   		<c:out value="${command.proposalBean.dean.degreeFullNameHebrew}"/>
        				   	</th>
        		        </tr>
        		        <tr>
        		        	<td>
        		        		&nbsp;
        		        	</td>
        		        </tr>
			        	<tr>
        				   	<th align="right" class="medium300">
        		        		תפקיד:
			        	   	</td>
        				   	<th align="right" class="medium300">
        				   		<c:out value="${command.proposalBean.dean.title}"/>
			        	   	</th>
        				 </tr>
        				<tr>
			        	   	<td>&nbsp;</td>
        				 </tr>
        		        <tr>
		        		        		<th align="right" colspan="2">
        				        			<form:radiobutton  id="deanApproved" path="proposalBean.deanApproved" value="true" cssClass="green"/>מאשר
        				        		</th>
        		        			</tr>
			        		        <tr style="vertical-align: top">
        					        	<th align="right" colspan="2">
        				        			<form:radiobutton  id="deanApproved" path="proposalBean.deanApproved" value="false" cssClass="green"/>לא מאשר
        		        				</th>
        		        			</tr>
        		        			<tr>
        		        				<td id="deanRefusalDetails" >
		        		        		<table>
        				        			<tr>
        				        				<th align="right" class="medium300">
			        				        		סיבת הדחייה:
			        		    		    	</th>
			        		        			<td>
				       		        				<form:textarea path="proposalBean.deanRefusalDetails" cssClass="green" cols="30" rows="4"/></textarea>
						       		        	</td>
						       		        </tr>
					       		       </table>
					       		     	</td>
        		        			</tr>
        		        			<tr>
        		        				<td>
        		        					<button class="deanApproval grey">עדכן</button>
        		        				</td>
        		        			</tr>
        		        </c:when>
        		        <c:otherwise>
        		        	<c:if test="${!viewDetails}">
        		        		<form:hidden path="proposalBean.deanRefusalDetails"/>
        		        	</c:if>
        		        	<tr>
							<th align="right" colspan="2">
								הצעת המחקר
									<c:choose>
										<c:when test="${command.proposalBean.deanApprovalStateId == 1}">
											<fmt:message key="iw_IL.eqfSystem.editProposal.deanApprovalState.1"></fmt:message>
											&nbsp;<c:out value="${command.proposalBean.dean.degreeFullNameHebrew}"/> - <c:out value="${command.proposalBean.dean.title}"/>
										</c:when>
										<c:otherwise>
											<fmt:message key="iw_IL.eqfSystem.editProposal.deanApprovalState.2"></fmt:message>
											&nbsp;<c:out value="${command.proposalBean.dean.degreeFullNameHebrew}"/> - <c:out value="${command.proposalBean.dean.title}"/>
											<br>
											סיבת הדחייה: <span style="font-weight: normal"><c:out value="${command.proposalBean.deanRefusalDetails }"/></span>
										</c:otherwise>
									</c:choose>
							</td>
						</tr>
						</c:otherwise>
						</c:choose>

        		        </authz:authorize>
						</c:if>

