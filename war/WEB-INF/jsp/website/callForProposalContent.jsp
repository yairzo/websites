<%@ include file="/WEB-INF/jsp/include/include.jsp" %>

			<table border="0" width="100%" dir="${lang.dir}">
 				<tbody>
                   	<tr>
                     	<td colspan="3">
                     	    <c:if test="${command.typeId==1}"><fmt:message key="${lang.localeId}.callForProposal.researchGrant"/></c:if>
       						<c:if test="${command.typeId==2}"><fmt:message key="${lang.localeId}.callForProposal.researcherExchange"/></c:if>
       						<c:if test="${command.typeId==3}"><fmt:message key="${lang.localeId}.callForProposal.conference"/></c:if>
       						<c:if test="${command.typeId==4}"><fmt:message key="${lang.localeId}.callForProposal.scholarship"/></c:if>
 
                     		<c:if test="${command.expired}">
		      				<fmt:message key="${lang.localeId}.website.isExpired"/>
		      				</c:if>
		      			</td>
		      		</tr>
					<tr>
		      			<td colspan="2">
			      			<h1>${command.title}</h1>
                       	</td>
                      	<td>
                			<fmt:message key="${lang.localeId}.callForProposal.publicationTime"/>${publicationTime}
				         	<br>
				         	<fmt:message key="${lang.localeId}.website.lastUpdate"/>${updateTime}
                      	</td>
                    </tr>
					<tr>
						<td colspan="3" style="border:1px #bca2a2 dotted" nowrap>
						<c:if test="${command.allYearSubmission}"><fmt:message key="${lang.localeId}.callForProposal.allYearSubmission"/></c:if>
						<c:if test="${!command.allYearSubmission}"><fmt:message key="${lang.localeId}.callForProposal.finalSubmissionTime"/>${finalSubmissionTime}</c:if>
 						</td>
					</tr>
					<c:if test="${submissionDate1}">
					<tr>
 						<td style="border:1px #bca2a2 dotted">
						 <fmt:message key="${lang.localeId}.callForProposal.anotherSubmissionDate"/>${submissionDate1}
						</td>
						<td style="border:1px #bca2a2 dotted">
						<fmt:message key="${lang.localeId}.callForProposal.anotherSubmissionDate"/>${submissionDate2}
 						</td>
						<td style="border:1px #bca2a2 dotted">
						<fmt:message key="${lang.localeId}.callForProposal.anotherSubmissionDate"/>${submissionDate3}
						</td>
					</tr>
					</c:if>
                	<tr>
						<td colspan="3" style="border:1px #bca2a2 dotted">
						 <fmt:message key="${lang.localeId}.callForProposal.fund"/>${selectedFund}
						</td>
					</tr>
					<c:if test="${!command.showDescriptionOnly}">
 					<tr>
						<td colspan="3" style="border:1px #bca2a2 dotted">
      					<fmt:message key="${lang.localeId}.callForProposal.submissionDetails"/><br>${command.submissionDetails}
 						</td>
					</tr>				
					</c:if>
					<tr>
						<td colspan="3" style="border:1px #bca2a2 dotted">
      					<fmt:message key="${lang.localeId}.callForProposal.description"/><br>${command.description}
 						</td>
					</tr>				
					<c:if test="${!command.showDescriptionOnly}">
 					<tr>
						<td colspan="3" style="border:1px #bca2a2 dotted">
      					<fmt:message key="${lang.localeId}.callForProposal.contactPersons"/><br>${command.contactPersonDetails}
 						</td>
					</tr>				
 					<tr>
						<td colspan="3" style="border:1px #bca2a2 dotted">
      					<fmt:message key="${lang.localeId}.callForProposal.forms"/><br>${command.formDetails}
 						</td>
					</tr>				
 					<tr>
						<td colspan="3" style="border:1px #bca2a2 dotted">
      					<fmt:message key="${lang.localeId}.callForProposal.fundingDetails"/><br>
							<ul>
							<li><fmt:message key="${lang.localeId}.callForProposal.fundingPeriod"/>${strippedFundingPeriod}</li>
							<li><fmt:message key="${lang.localeId}.callForProposal.amountOfGrant"/>${strippedAmountOfGrant}</li>
							<li><fmt:message key="${lang.localeId}.callForProposal.eligibilityRequirements"/>${strippedEligibilityRequirements}</li>
							<li><fmt:message key="${lang.localeId}.callForProposal.activityLocation"/>${strippedActivityLocation}</li>
							<li><fmt:message key="${lang.localeId}.callForProposal.possibleCollaboration"/>${strippedPossibleCollaboration}</li>
							<li><a href="${command.originalCallWebAddress}"><fmt:message key="${lang.localeId}.website.originalCallWebAddress"/></a></li>
							</ul>
 						</td>
					</tr>				
					<tr>
						<td colspan="3" style="border:1px #bca2a2 dotted">
      					<fmt:message key="${lang.localeId}.callForProposal.budgetDetails"/><br>${command.budgetDetails }
 						</td>
					</tr>				
					<tr>
						<td colspan="3" style="border:1px #bca2a2 dotted">
      					<fmt:message key="${lang.localeId}.callForProposal.additionalInformation"/><br>${command.additionalInformation }
 						</td>
					</tr>				
					</c:if>
					
 				</tbody>
			</table>
