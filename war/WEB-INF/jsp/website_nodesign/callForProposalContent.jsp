<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>

			<table border="0" width="100%" dir="${lang.dir}">
 				<tbody>
                   	<tr>
                     	<td colspan="3">
                     	    <c:if test="${command.typeId==1}"><fmt:message key="${lang.localeId}.callForProposal.researchGrant"/></c:if>
       						<c:if test="${command.typeId==2}"><fmt:message key="${lang.localeId}.callForProposal.researcherExchange"/></c:if>
       						<c:if test="${command.typeId==3}"><fmt:message key="${lang.localeId}.callForProposal.conference"/></c:if>
       						<c:if test="${command.typeId==4}"><fmt:message key="${lang.localeId}.callForProposal.scholarship"/></c:if>
       						<c:if test="${command.typeId==5}"><fmt:message key="${lang.localeId}.callForProposal.prizes"/></c:if>
 
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
						<c:if test="${!command.allYearSubmission}">
							<fmt:message key="${lang.localeId}.callForProposal.finalSubmissionTime"/>${finalSubmissionTime}&nbsp;
							<c:if test="${fn:length(command.finalSubmissionHour)>0}"><fmt:message key="${lang.localeId}.callForProposal.finalSubmissionHour"/>${command.finalSubmissionHour}</c:if>
						</c:if>
 						</td>
					</tr>
					<c:if test="${fn:length(submissionDate1)>0}">
					<tr>
 						<td colspan="3"  style="border:1px #bca2a2 dotted">
						 <fmt:message key="${lang.localeId}.callForProposal.anotherSubmissionDate"/>${submissionDate1}&nbsp;
						${submissionDate2}&nbsp;
						${submissionDate3}
 						</td>
					</tr>
					</c:if>
                	<tr>
						<td colspan="3" style="border:1px #bca2a2 dotted">
						 <fmt:message key="${lang.localeId}.callForProposal.fund"/>${selectedFund}
						</td>
					</tr>
					<tr>
						<td colspan="3" style="border:1px #bca2a2 dotted">
      					<fmt:message key="${lang.localeId}.callForProposal.originalCallWebAddress"/><br><a href="${command.originalCallWebAddress}">${command.originalCallWebAddress}</a>
 						</td>
					</tr>
					<c:if test="${authorized && !command.showDescriptionOnly}">
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
					<c:if test="${!authorized}">
					<tr>
						<td colspan="3">
						<fmt:message key="${lang.localeId}.callForProposal.fullDetailsLogin"/> <a href="login.html?ilr=searchCallForProposals.html?callForProposalId=${command.id}"> <fmt:message key="${lang.localeId}.callForProposal.loginLink"/></a>
						</td>
					</tr>
					</c:if>			
					<c:if test="${authorized && !command.showDescriptionOnly}">
 					<tr>
						<td colspan="3" style="border:1px #bca2a2 dotted">
      					<fmt:message key="${lang.localeId}.callForProposal.contactPersons"/><br>${command.contactPersonDetails}
 						</td>
					</tr>				
 					<tr>
						<td colspan="3" style="border:1px #bca2a2 dotted">
      					<fmt:message key="${lang.localeId}.callForProposal.contactAtFund"/><br>${command.fundContact}
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
							<c:if test="${command.strippedFundingPeriod!=''}">
							<li><fmt:message key="${lang.localeId}.callForProposal.fundingPeriod"/>${command.strippedFundingPeriod}</li>
							</c:if>
							<c:if test="${command.strippedAmountOfGrant!=''}">
							<li><fmt:message key="${lang.localeId}.callForProposal.amountOfGrant"/>${command.strippedAmountOfGrant}</li>
							</c:if>
							<c:if test="${command.strippedEligibilityRequirements!=''}">
							<li><fmt:message key="${lang.localeId}.callForProposal.eligibilityRequirements"/>${command.strippedEligibilityRequirements}</li>
							</c:if>
							<c:if test="${command.strippedActivityLocation!=''}">
							<li><fmt:message key="${lang.localeId}.callForProposal.activityLocation"/>${command.strippedActivityLocation}</li>
							</c:if>
							<c:if test="${command.strippedPossibleCollaboration!=''}">
							<li><fmt:message key="${lang.localeId}.callForProposal.possibleCollaboration"/>${command.strippedPossibleCollaboration}</li>
							</c:if>
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
