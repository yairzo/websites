<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>

			<div class="container clearfix">
				<div class="breadcrumbs clearfix" dir="${lang.dir}" align="${lang.align}">
					<jsp:include page="location.jsp"/>
				</div>
				<jsp:include page="sideLinks.jsp"/>
				<div class="content">
					<h1 class="maintitle">${command.title}</h1>
					<div class="clearfix mar_20">
						<div class="clearfix mar_20">
							<div class="kol open">
								<div class="clearfix">
									<h3 class="kol_title"><img src="image/website1/kol_i.png" alt="" />&nbsp;&nbsp; <fmt:message key="${lang.localeId}.callForProposal.generalInfo"/></h3>
									<a href="#" class="kol_arrow"></a>
								</div>
								<div class="kol_content kol_content_i">
										<p>
										<strong><fmt:message key="${lang.localeId}.callForProposal.finalSubmissionTime"/></strong>&nbsp;
										<c:if test="${command.allYearSubmission}">
											<fmt:message key="${lang.localeId}.callForProposal.allYearSubmission"/>
										</c:if>
										<c:if test="${!command.allYearSubmission}">
											${finalSubmissionTime}
										</c:if> 
										<br/>
										<c:if test="${fn:length(submissionDate1)>0}">
										<strong><fmt:message key="${lang.localeId}.callForProposal.anotherSubmissionDate"/></strong>&nbsp;${submissionDate1}&nbsp;
										${submissionDate2}&nbsp;
										${submissionDate3}<br/>
										</c:if>
										<strong><fmt:message key="${lang.localeId}.callForProposal.fund"/>:</strong>&nbsp;${command.fund.name}<br/>
     									<a href="${command.originalCallWebAddress}"><fmt:message key="${lang.localeId}.website.originalCallWebAddress"/></a><br>
     									<a href="${command.fund.webAddress}"><fmt:message key="${lang.localeId}.website.fundWebAddress"/></a><br>
										</p>
								</div>
							</div>
							
							<div class="kol open">
								<div class="clearfix">
									<h3 class="kol_title"><img src="image/website1/kol_details.png" alt="" />&nbsp;&nbsp; <fmt:message key="${lang.localeId}.callForProposal.description"/></h3>
									<a href="#" class="kol_arrow"></a>
								</div>
								<div class="kol_content">
									<p>${command.description}</p>
								</div>
							</div>
							
							<c:if test="${!authorized}">
							<fmt:message key="${lang.localeId}.callForProposal.fullDetailsLogin"/> <a href="login.html?ilr=callForProposal.html?id=${command.id}&t=1"> <fmt:message key="${lang.localeId}.callForProposal.loginLink"/></a>
							</c:if>			
							
							<c:if test="${authorized && !command.showDescriptionOnly}">
							<div class="kol">
								<div class="clearfix">
									<h3 class="kol_title"><img src="image/website1/kol_dolar.png" alt="" />&nbsp;&nbsp;<fmt:message key="${lang.localeId}.callForProposal.fundingDetails"/></h3>
									<a href="#" class="kol_arrow"></a>
								</div>
								<div class="kol_content kol_content_i">
									<p>
										<strong><fmt:message key="${lang.localeId}.callForProposal.fundingPeriod"/></strong>&nbsp;${command.strippedFundingPeriod}<br />
										<strong><fmt:message key="${lang.localeId}.callForProposal.amountOfGrant"/></strong>&nbsp;${command.strippedAmountOfGrant}<br />
										<strong><fmt:message key="${lang.localeId}.callForProposal.eligibilityRequirements"/></strong>&nbsp;${command.strippedEligibilityRequirements}<br />
										<strong><fmt:message key="${lang.localeId}.callForProposal.activityLocation"/></strong>&nbsp;${command.strippedActivityLocation}<br />
										<strong><fmt:message key="${lang.localeId}.callForProposal.possibleCollaboration"/></strong>&nbsp;${command.strippedPossibleCollaboration}</p>
								</div>
							</div>
							
							<div class="kol">
								<div class="clearfix">
									<h3 class="kol_title"><img src="image/website1/kol_v.png" alt="" />&nbsp;&nbsp;<fmt:message key="${lang.localeId}.callForProposal.forms"/></h3>
									<a href="#" class="kol_arrow"></a>
								</div>
								<div class="kol_content">
									<p>${command.formDetails}</p>
								</div>
							</div>
							
							<div class="kol">
								<div class="clearfix">
									<h3 class="kol_title"><img src="image/website1/kol_envelope.png" alt="" />&nbsp;&nbsp; <fmt:message key="${lang.localeId}.callForProposal.submissionDetails"/></h3>
									<a href="#" class="kol_arrow"></a>
								</div>
								<div class="kol_content">
									<p>${command.submissionDetails}</p>
								</div>
							</div>
							
							<div class="kol">
								<div class="clearfix">
									<h3 class="kol_title kol_chart"><img src="image/website1/kol_chart.png" alt="" />&nbsp;&nbsp; <fmt:message key="${lang.localeId}.callForProposal.budgetDetails"/></h3>
									<a href="#" class="kol_arrow"></a>
								</div>
								<div class="kol_content">
								<p>${command.budgetDetails }</p>
								</div>
							</div>
							
							<div class="kol">
								<div class="clearfix">
									<h3 class="kol_title"><img src="image/website1/kol_man.png" alt="" />&nbsp;&nbsp; <fmt:message key="${lang.localeId}.callForProposal.contactPersons"/></h3>
									<a href="#" class="kol_arrow"></a>
								</div>
								<div class="kol_content">
									<c:if test="${fn:length(callForProposalContacts)>0}">
									<table class="table_kol">
										<tr>
											<th class="table_one"><fmt:message key="${lang.localeId}.callForProposal.contactPersonName"/></th>
											<th class="table_two"><fmt:message key="${lang.localeId}.callForProposal.contactPersonPosition"/></th>
											<th class="table_three"><fmt:message key="${lang.localeId}.callForProposal.contactPersonPhone"/></th>
										</tr>
								    	<c:forEach items="${callForProposalContacts}" var="callForProposalContact">
								    	<tr>
											<td class="table_one">${callForProposalContact.name}</td>
											<td class="table_two">${callForProposalContact.position}</td>
											<td class="table_three">${callForProposalContact.phone}</td>
										</tr>
								    	</c:forEach>
									</table>
									</c:if>
								</div>
							</div>
							
							<div class="kol">
								<div class="clearfix">
									<h3 class="kol_title kol_chart"><img src="image/website1/kol_man.png" alt="" />&nbsp;&nbsp; <fmt:message key="${lang.localeId}.callForProposal.contactPersonDetails"/></h3>
									<a href="#" class="kol_arrow"></a>
								</div>
								<div class="kol_content">
								<p>${command.contactPersonDetails }</p>
								</div>
							</div>
	
							
							<div class="kol">
								<div class="clearfix">
									<h3 class="kol_title"><img src="image/website1/kol_man.png" alt="" />&nbsp;&nbsp;<fmt:message key="${lang.localeId}.callForProposal.contactAtFund"/></h3>
									<a href="#" class="kol_arrow"></a>
								</div>
								<div class="kol_content">
									<p>${command.fundContact}</p>								
								</div>
							</div>
							
							<div class="kol">
								<div class="clearfix">
									<h3 class="kol_title"><img src="image/website1/kol_plus.png" alt="" />&nbsp;&nbsp; <fmt:message key="${lang.localeId}.callForProposal.additionalInformation"/></h3>
									<a href="#" class="kol_arrow"></a>
								</div>
								<div class="kol_content">
									<p>${command.additionalInformation }</p>
								</div>
							</div>
							</c:if>
						</div>
					</div>
				</div>
			</div>
