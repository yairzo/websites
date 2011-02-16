<%@ page  pageEncoding="UTF-8" %>

			<c:if test="${command.proposalBean.stateId>=FUND_APPROVED}">

			<tr>
				<td colspan="2">
					&nbsp;
				</td>
			</tr>



			<authz:authorize ifNotGranted="ROLE_EQF_YISSUM">

			<c:choose>
			<c:when test="${! command.proposalBean.yissumResearcherHandled}">

				<c:choose>
					<c:when test="${viewDetails}">

						<tr>
        		        	<th align="right" colspan="2">
        		        			טרם סומן האם המחקר יישומי
        		        	</th>
        		 		</tr>
        		 	</c:when>
        		 	<c:otherwise>



			<authz:authorize ifAnyGranted="ROLE_EQF_RESEARCHER">

					<tr>
        		        	<th align="right" colspan="2">
        		        					<input type="radio"  id="yissum" name="proposalBean.needsYissumApproval" value="0"/>המחקר אינו יישומי
        		        					<br>
        		        					<input type="radio"  id="yissum" name="proposalBean.needsYissumApproval" value="1"/>המחקר יישומי, שלח לאישור חברת יישום
 							</td>
 					</tr>
 			</authz:authorize>
 			<authz:authorize ifNotGranted="ROLE_EQF_RESEARCHER">
 				<tr>
        		        	<th align="right" colspan="2">
        		        			טרם סומן האם המחקר יישומי
        		        	</th>
        		 </tr>
       		</authz:authorize>
       			</c:otherwise>
       			</c:choose>


       		</c:when>
       		<c:otherwise>
       			<c:if test="${!viewDetails}">
       				<form:hidden path="proposalBean.yissumResearcherHandled"/>
       				<form:hidden path="proposalBean.needsYissumApproval"/>
       				<form:hidden path="proposalBean.yissumSend"/>
       				<form:hidden path="proposalBean.yissumApproved"/>
       				<form:hidden path="proposalBean.yissumHandled"/>
       				<form:hidden path="proposalBean.yissumRefusalDetails"/>
       			</c:if>

       			<c:choose>
       				<c:when test="${! command.proposalBean.needsYissumApproval}">
       					<tr>
       						<th align="right" colspan="2">
       							המחקר אינו יישומי
       						</th>
       					</tr>
       				</c:when>
       				<c:otherwise>

       						<c:choose>
       							<c:when test="${!command.proposalBean.yissumHandled}">
									<tr>
			       						<th align="right" colspan="2">
       											ההצעה נשלחה לאישור חברת יישום
       									</th>
       								</tr>
       							</c:when>
       							<c:otherwise>
       								<c:choose>
       									<c:when test="${command.proposalBean.yissumApproved}">
       										<tr>
					       						<th align="right" colspan="2">
       													ההצעה אושרה על-ידי חברת יישום
       											</td>
       										</tr>
       									</c:when>
       									<c:otherwise>
											<tr>
					       						<th align="right" colspan="2">
       												ההצעה נדחתה על-ידי חברת יישום
       											</th>
       										</tr>
       										<tr>
       											<th  align="right">
       													 סיבת הדחייה:
       											</th>
       											<td>
       												<c:out value="${command.proposalBean.yissumRefusalDetails}"/>
       											</td>
       										</tr>
       									</c:otherwise>
       								</c:choose>
       							</c:otherwise>
       						</c:choose>
       					</c:otherwise>
       				</c:choose>
       			</c:otherwise>
       		</c:choose>
       			</authz:authorize>


       			<authz:authorize ifAnyGranted="ROLE_EQF_YISSUM">
					<c:if test="${!viewDetails}">
						<form:hidden path="proposalBean.yissumResearcherHandled"/>
	       				<form:hidden path="proposalBean.yissumSend"/>
	       			</c:if>

       				<c:choose>
						<c:when test="${! command.proposalBean.yissumApproved && !viewDetails}">
							<tr>
								<td>
									<form:radiobutton id="yissumApproval" path="proposalBean.yissumApproved" value="true"/>
								</td>
								<td>
									ההנני מאשר שנתוני הטופס וכללי האוניברסיטה הובאו בחשבון, בהסכם עם המממן (הוראת הנהלה 15-011)
								</td>

							</tr>
							<tr>
								<td>
									<form:radiobutton id="yissumApproval" path="proposalBean.yissumApproved" value="false"/>
								</td>
								<th align="right">
									אינני מאשר את ההצעה:
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<table>
										<tr>
											<th align="right" valign="top">
									סיבת הדחייה:
										</td>
										<td>
						       		    	<form:textarea path="proposalBean.yissumRefusalDetails" cssClass="green" cols="30" rows="4"/></textarea>

						       		</td>
						       		</tr>
						       		</table>
						      </tr>
						</c:when>
						<c:otherwise>
								<c:choose>
       									<c:when test="${command.proposalBean.yissumApproved}">
       										<tr>
					       						<th align="right" colspan="2">
       													ההצעה אושרה על-ידי חברת יישום
       											</th>
       										</tr>
       									</c:when>
       									<c:otherwise>
											<tr>
					       						<th align="right" colspan="2">
       												ההצעה נדחתה על-ידי חברת יישום
       											</th>
       										</tr>
       										<tr>
       											<th align="right">
       													 סיבת הדחייה:
       											</th>
       											<td>
       												<c:out value="${command.proposalBean.yissumRefusalDetails}"/>
       											</td>
       										</tr>
       									</c:otherwise>
       								</c:choose>
       							</c:otherwise>
       						</c:choose>
       					</authz:authorize>
       			</c:if>





