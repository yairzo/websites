<%@ page  pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/include.jsp" %>

			<div class="container clearfix">
				<div class="breadcrumbs clearfix" style="direction: ${lang.dir}; text-align: ${lang.align};">
					<jsp:include page="location.jsp"/>
				</div>
				<jsp:include page="sideLinks.jsp"/>
				<div class="content" style="direction: ${copLang.dir};">
					<h1 class="maintitle">${command.title}</h1>
					<div class="clearfix mar_20">
						<div class="clearfix mar_20" style="direction: ${copLang.dir}; text-align: ${copLang.align};">
							<div class="kol open kol_${copLang.dir} general_info">
								<div class="clearfix">
									<h3 class="kol_title_${copLang.dir}"><img src="image/website1/kol_i.png" alt="" />&nbsp;&nbsp; <fmt:message key="${copLang.localeId}.callForProposal.generalInfo"/></h3>
									<a href="#" class="kol_arrow" style="float: ${copLang.align}"></a>
								</div>
								<div class="kol_content kol_content_${copLang.dir} kol_content_i">
										<p>
										<strong><fmt:message key="${copLang.localeId}.callForProposal.finalSubmissionTime"/></strong>
										<c:if test="${command.allYearSubmission}">
											<fmt:message key="${copLang.localeId}.callForProposal.allYearSubmission"/>
										</c:if>
										<c:if test="${!command.allYearSubmission}">
											${finalSubmissionTime}
										</c:if> 
										<br/>
										<c:if test="${fn:length(submissionDate1)>0}">
										<strong><fmt:message key="${copLang.localeId}.callForProposal.anotherSubmissionDate"/></strong>${submissionDate1}&nbsp;
										${submissionDate2}&nbsp;
										${submissionDate3}<br/>
										</c:if>
										<strong><fmt:message key="${copLang.localeId}.callForProposal.fund"/>:</strong>&nbsp;${command.fund.name}<br/>
     									<a href="${command.originalCallWebAddress}"><fmt:message key="${copLang.localeId}.website.originalCallWebAddress"/></a><br>
     									<a href="${command.fund.webAddress}"><fmt:message key="${copLang.localeId}.website.fundWebAddress"/></a><br>

										</p>
								</div>
							</div>
							
							<div class="kol open kol_${copLang.dir} description">
								<div class="clearfix">
									<h3 class="kol_title_${copLang.dir}"><img src="image/website1/kol_details.png" alt="" />&nbsp;&nbsp; <fmt:message key="${copLang.localeId}.callForProposal.description"/></h3>
									<a href="#" class="kol_arrow" style="float: ${copLang.align}"></a>
								</div>
								<div class="kol_content kol_content_${copLang.dir}">
									<p>${command.description}</p>
								</div>
							</div>
							
							<c:if test="${!authorized}">
							<fmt:message key="${copLang.localeId}.callForProposal.fullDetailsLogin"/> <a href="login.html?ilr=callForProposal.html?id=${command.id}&t=1"> <fmt:message key="${copLang.localeId}.callForProposal.loginLink"/></a>
							</c:if>			
							
							<c:if test="${authorized && !command.showDescriptionOnly}">
							
							<div class="kol kol_${copLang.dir}">
								<div class="clearfix">
									<h3 class="kol_title_${copLang.dir}"><img src="image/website1/kol_dolar.png" alt="" />&nbsp;&nbsp;<fmt:message key="${copLang.localeId}.callForProposal.fundingDetails"/></h3>
									<a href="#" class="kol_arrow" style="float: ${copLang.align}"></a>
								</div>
								<div class="kol_content kol_content_${copLang.dir} kol_content_i">
									<p>
										<c:if test="${fn:length(command.strippedFundingPeriod)>0}"><strong><fmt:message key="${copLang.localeId}.callForProposal.fundingPeriod"/></strong>&nbsp;${command.strippedFundingPeriod}<br /></c:if>
										<c:if test="${fn:length(command.strippedAmountOfGrant)>0}"><strong><fmt:message key="${copLang.localeId}.callForProposal.amountOfGrant"/></strong>&nbsp;${command.strippedAmountOfGrant}<br /></c:if>
										<c:if test="${fn:length(command.strippedEligibilityRequirements)>0}"><strong><fmt:message key="${copLang.localeId}.callForProposal.eligibilityRequirements"/>&nbsp;</strong>&nbsp;${command.strippedEligibilityRequirements}<br /></c:if>
										<c:if test="${fn:length(command.strippedActivityLocation)>0}"><strong><fmt:message key="${copLang.localeId}.callForProposal.activityLocation"/>&nbsp;</strong>${command.strippedActivityLocation}<br /></c:if>
										<c:if test="${fn:length(command.strippedPossibleCollaboration)>0}"><strong><fmt:message key="${copLang.localeId}.callForProposal.possibleCollaboration"/>&nbsp;</strong>${command.strippedPossibleCollaboration}</c:if>
									</p>
								</div>
							</div>
							
							<c:if test="${fn:length(command.formDetails)>0}">
							<div class="kol kol_${copLang.dir}">
								<div class="clearfix">
									<h3 class="kol_title_${copLang.dir}"><img src="image/website1/kol_v.png" alt="" />&nbsp;&nbsp;<fmt:message key="${copLang.localeId}.callForProposal.forms"/></h3>
									<a href="#" class="kol_arrow" style="float: ${copLang.align}"></a>
								</div>
								<div class="kol_content kol_content_${copLang.dir}">
									<p>${command.formDetails}</p>
								</div>
							</div>
							</c:if>
							
							<c:if test="${fn:length(command.submissionDetails)>0}">
							<div class="kol kol_${copLang.dir}">
								<div class="clearfix">
									<h3 class="kol_title_${copLang.dir}"><img src="image/website1/kol_envelope.png" alt="" />&nbsp;&nbsp; <fmt:message key="${copLang.localeId}.callForProposal.submissionDetails"/></h3>
									<a href="#" class="kol_arrow" style="float: ${copLang.align}"></a>
								</div>
								<div class="kol_content kol_content_${copLang.dir}">
									<p>${command.submissionDetails}</p>
								</div>
							</div>
							</c:if>
							
							<c:if test="${fn:length(command.budgetDetails)>0}">
							<div class="kol kol_${copLang.dir}">
								<div class="clearfix">
									<h3 class="kol_title_${copLang.dir}"><img src="image/website1/kol_chart.png" alt="" />&nbsp;&nbsp; <fmt:message key="${copLang.localeId}.callForProposal.budgetDetails"/></h3>
									<a href="#" class="kol_arrow" style="float: ${copLang.align}"></a>
								</div>
								<div class="kol_content kol_content_${copLang.dir}">
								<p>${command.budgetDetails }</p>
								</div>
							</div>
							</c:if>
							
							<c:if test="${fn:length(callForProposalContacts)>0}">
							<div class="kol kol_${copLang.dir}">
								<div class="clearfix">
									<h3 class="kol_title_${copLang.dir}"><img src="image/website1/kol_man.png" alt="" />&nbsp;&nbsp; <fmt:message key="${copLang.localeId}.callForProposal.contactPersons"/></h3>
									<a href="#" class="kol_arrow" style="float: ${copLang.align}"></a>
								</div>
								<div class="kol_content kol_content_${copLang.dir}">
									<c:if test="${fn:length(callForProposalContacts)>0}">
									<table class="table_kol">
										<tr>
											<th class="table_one"><fmt:message key="${copLang.localeId}.callForProposal.contactPersonName"/></th>
											<th class="table_two"><fmt:message key="${copLang.localeId}.callForProposal.contactPersonPosition"/></th>
											<th class="table_three"><fmt:message key="${copLang.localeId}.callForProposal.contactPersonPhone"/></th>
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
							</c:if>
							
							<c:if test="${fn:length(command.contactPersonDetails)>0}">
							<div class="kol kol_${copLang.dir}">
								<div class="clearfix">
									<h3 class="kol_title_${copLang.dir}"><img src="image/website1/kol_man.png" alt="" />&nbsp;&nbsp; <fmt:message key="${copLang.localeId}.callForProposal.contactPersonDetails"/></h3>
									<a href="#" class="kol_arrow" style="float: ${copLang.align}"></a>
								</div>
								<div class="kol_content kol_content_${copLang.dir}">
								<p>${command.contactPersonDetails }</p>
								</div>
							</div>
							</c:if>
							
							<c:if test="${fn:length(command.fundContact)>0}">
							<div class="kol kol_${copLang.dir}">
								<div class="clearfix">
									<h3 class="kol_title_${copLang.dir}"><img src="image/website1/kol_man.png" alt="" />&nbsp;&nbsp;<fmt:message key="${copLang.localeId}.callForProposal.contactAtFund"/></h3>
									<a href="#" class="kol_arrow" style="float: ${copLang.align}"></a>
								</div>
								<div class="kol_content kol_content_${copLang.dir}">
									<p>${command.fundContact}</p>								
								</div>
							</div>
							</c:if>
							
							<c:if test="${fn:length(command.additionalInformation)>0}">
							<div class="kol kol_${copLang.dir}">
								<div class="clearfix">									
									<h3 class="kol_title_${copLang.dir}"><img src="image/website1/kol_plus.png" alt="" />&nbsp;&nbsp; <fmt:message key="${copLang.localeId}.callForProposal.additionalInformation"/></h3>
									<a href="#" class="kol_arrow" style="float: ${copLang.align}"></a>									
								</div>
								<div class="kol_content kol_content_${copLang.dir}">
									<p>${command.additionalInformation }</p>
								</div>
							</div>
							</c:if>
							
							</c:if>
						</div>
					</div>
				</div>
			</div>
